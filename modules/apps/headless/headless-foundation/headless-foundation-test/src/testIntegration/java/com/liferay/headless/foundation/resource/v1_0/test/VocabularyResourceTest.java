/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.headless.foundation.resource.v1_0.test;

import static com.liferay.portal.odata.entity.EntityField.Type;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.foundation.dto.v1_0.Vocabulary;
import com.liferay.headless.foundation.resource.v1_0.VocabularyResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class VocabularyResourceTest extends BaseVocabularyResourceTestCase {

	@Override
	@Test
	public void testDeleteVocabulary() throws Exception {
		Vocabulary postVocabulary = invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary());

		assertResponseCode(
			200, invokeDeleteVocabularyResponse(postVocabulary.getId()));

		assertResponseCode(
			404, invokeGetVocabularyResponse(postVocabulary.getId()));
	}

	@Override
	@Test
	public void testGetContentSpaceVocabulariesPage() throws Exception {
		Vocabulary randomVocabulary1 = randomVocabulary();

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary1);

		Vocabulary randomVocabulary2 = randomVocabulary();

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary2);

		Page<Vocabulary> page = invokeGetContentSpaceVocabulariesPage(
			testGroup.getGroupId(), null, Pagination.of(2, 1), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(randomVocabulary1, randomVocabulary2),
			(List<Vocabulary>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceVocabulariesPageWithFilterDateTimeEquals()
		throws Exception {

		Vocabulary vocabulary = invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary());

		Thread.sleep(1000);

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary());

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		for (EntityField entityField : _getEntityFields(Type.DATE_TIME)) {
			Date date = null;

			String entityFieldName = entityField.getName();

			if (entityFieldName.equals("dateCreated")) {
				date = vocabulary.getDateCreated();
			}
			else if (entityFieldName.equals("dateModified")) {
				date = vocabulary.getDateModified();
			}
			else {
				throw new IllegalArgumentException();
			}

			Page<Vocabulary> page = invokeGetContentSpaceVocabulariesPage(
				testGroup.getGroupId(),
				entityField.getName() + " eq " + dateFormat.format(date),
				Pagination.of(2, 1), null);

			assertEquals(
				Collections.singletonList(vocabulary),
				(List<Vocabulary>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceVocabulariesPageWithFilterStringEquals()
		throws Exception {

		Vocabulary vocabulary = randomVocabulary();

		invokePostContentSpaceVocabulary(testGroup.getGroupId(), vocabulary);

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary());

		for (EntityField entityField : _getEntityFields(Type.STRING)) {
			StringBundler sb = new StringBundler(4);

			String entityFieldName = entityField.getName();

			sb.append(entityFieldName);

			sb.append(" eq '");

			if (entityFieldName.equals("name")) {
				sb.append(vocabulary.getName());
			}
			else {
				throw new IllegalArgumentException();
			}

			sb.append("'");

			Page<Vocabulary> page = invokeGetContentSpaceVocabulariesPage(
				testGroup.getGroupId(), sb.toString(), Pagination.of(2, 1),
				null);

			assertEquals(
				Collections.singletonList(vocabulary),
				(List<Vocabulary>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceVocabulariesPageWithPagination()
		throws Exception {

		Vocabulary randomVocabulary1 = randomVocabulary();

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary1);

		Vocabulary randomVocabulary2 = randomVocabulary();

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary2);

		Vocabulary randomVocabulary3 = randomVocabulary();

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary3);

		Page<Vocabulary> page1 = invokeGetContentSpaceVocabulariesPage(
			testGroup.getGroupId(), null, Pagination.of(2, 1), null);

		List<Vocabulary> vocabularies1 = (List<Vocabulary>)page1.getItems();

		Assert.assertEquals(vocabularies1.toString(), 2, vocabularies1.size());

		Page<Vocabulary> page2 = invokeGetContentSpaceVocabulariesPage(
			testGroup.getGroupId(), null, Pagination.of(2, 2), null);

		List<Vocabulary> vocabularies2 = (List<Vocabulary>)page2.getItems();

		Assert.assertEquals(vocabularies2.toString(), 1, vocabularies2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(
				randomVocabulary1, randomVocabulary2, randomVocabulary3),
			new ArrayList<Vocabulary>() {
				{
					addAll(vocabularies1);
					addAll(vocabularies2);
				}
			});
	}

	@Test
	public void testGetContentSpaceVocabulariesPageWithSort() throws Exception {
		Vocabulary randomVocabulary1 = randomVocabulary();

		randomVocabulary1.setName("Vocabulary1");

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary1);

		Vocabulary randomVocabulary2 = randomVocabulary();

		randomVocabulary2.setName("Vocabulary2");

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary2);

		Collection<EntityField> entityFields = _getEntityFields();

		for (EntityField entityField : entityFields) {
			Page<Vocabulary> ascPage = invokeGetContentSpaceVocabulariesPage(
				testGroup.getGroupId(), null, Pagination.of(2, 1),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(randomVocabulary1, randomVocabulary2),
				(List<Vocabulary>)ascPage.getItems());

			Page<Vocabulary> descPage = invokeGetContentSpaceVocabulariesPage(
				testGroup.getGroupId(), null, Pagination.of(2, 1),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(randomVocabulary2, randomVocabulary1),
				(List<Vocabulary>)descPage.getItems());
		}
	}

	@Override
	@Test
	public void testGetVocabulary() throws Exception {
		Vocabulary postVocabulary = invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary());

		Vocabulary getVocabulary = invokeGetVocabulary(postVocabulary.getId());

		assertEquals(postVocabulary, getVocabulary);
		assertValid(getVocabulary);
	}

	@Override
	@Test
	public void testPostContentSpaceVocabulary() throws Exception {
		Vocabulary randomVocabulary = randomVocabulary();

		Vocabulary postVocabulary = invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary);

		assertEquals(randomVocabulary, postVocabulary);
		assertValid(postVocabulary);
	}

	@Override
	@Test
	public void testPutVocabulary() throws Exception {
		Vocabulary postVocabulary = invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary());

		Vocabulary randomVocabulary = randomVocabulary();

		Vocabulary putVocabulary = invokePutVocabulary(
			postVocabulary.getId(), randomVocabulary);

		assertEquals(randomVocabulary, putVocabulary);
		assertValid(putVocabulary);

		Vocabulary getVocabulary = invokeGetVocabulary(putVocabulary.getId());

		assertEquals(randomVocabulary, getVocabulary);
		assertValid(getVocabulary);
	}

	protected void assertValid(Vocabulary vocabulary) {
		boolean valid = false;

		if (Objects.equals(
				vocabulary.getContentSpace(), testGroup.getGroupId()) &&
			(vocabulary.getDateCreated() != null) &&
			(vocabulary.getDateModified() != null) &&
			(vocabulary.getId() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(Vocabulary vocabulary1, Vocabulary vocabulary2) {
		if (Objects.equals(
				vocabulary1.getDescription(), vocabulary2.getDescription()) &&
			Objects.equals(vocabulary1.getName(), vocabulary2.getName())) {

			return true;
		}

		return false;
	}

	@Override
	protected Vocabulary randomVocabulary() {
		return new Vocabulary() {
			{
				contentSpace = testGroup.getGroupId();
				description = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
			}
		};
	}

	private Collection<EntityField> _getEntityFields() throws Exception {

		EntityModel entityModel =
			((EntityModelResource)_vocabularyResource).getEntityModel(null);

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	private List<EntityField> _getEntityFields(EntityField.Type type)
		throws Exception {

		Collection<EntityField> entityFields = _getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type)
		).collect(
			Collectors.toList()
		);
	}

	@Inject
	private VocabularyResource _vocabularyResource;

}