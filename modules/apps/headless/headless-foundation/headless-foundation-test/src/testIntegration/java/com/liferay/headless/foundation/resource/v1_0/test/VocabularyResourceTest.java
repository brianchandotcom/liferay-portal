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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.text.DateFormat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class VocabularyResourceTest extends BaseVocabularyResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testDeleteVocabulary() throws Exception {
		Vocabulary postVocabulary = invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary());

		assertResponseCode(
			200, invokeDeleteVocabularyResponse(postVocabulary.getId()));

		assertResponseCode(
			404, invokeGetVocabularyResponse(postVocabulary.getId()));
	}

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

		Vocabulary vocabulary1 = invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary());

		Vocabulary randomVocabulary2 = randomVocabulary();

		Thread.sleep(1000);

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary2);

		List<EntityField> stringEntityFields = _getEntityFields(Type.DATE_TIME);

		Map<String, Function<Vocabulary, Date>> map =
			getDateTimeEntityNameGetterMap();

		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		for (EntityField entityField : stringEntityFields) {
			Date date = map.get(entityField.getName()).apply(vocabulary1);

			Page<Vocabulary> page = invokeGetContentSpaceVocabulariesPage(
				testGroup.getGroupId(),
				entityField.getName() + " eq " + dateFormat.format(date),
				Pagination.of(2, 1), null);

			assertEquals(
				Collections.singletonList(vocabulary1),
				(List<Vocabulary>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceVocabulariesPageWithFilterStringEquals()
		throws Exception {

		Vocabulary randomVocabulary1 = randomVocabulary();

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary1);

		Vocabulary randomVocabulary2 = randomVocabulary();

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary2);

		List<EntityField> stringEntityFields = _getEntityFields(Type.STRING);

		Map<String, Function<Vocabulary, String>> map =
			getStringEntityNameGetterMap();

		for (EntityField entityField : stringEntityFields) {
			StringBundler sb = new StringBundler(4);

			sb.append(entityField.getName());
			sb.append(" eq '");
			sb.append(map.get(entityField.getName()).apply(randomVocabulary1));
			sb.append("'");

			Page<Vocabulary> page = invokeGetContentSpaceVocabulariesPage(
				testGroup.getGroupId(), sb.toString(), Pagination.of(2, 1),
				null);

			assertEquals(
				Collections.singletonList(randomVocabulary1),
				(List<Vocabulary>)page.getItems());
		}
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

	@Test
	public void testGetVocabulary() throws Exception {
		Vocabulary postVocabulary = invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary());

		Vocabulary getVocabulary = invokeGetVocabulary(postVocabulary.getId());

		assertEquals(postVocabulary, getVocabulary);
		assertValid(getVocabulary);
	}

	@Test
	public void testPostContentSpaceVocabulary() throws Exception {
		Vocabulary randomVocabulary = randomVocabulary();

		Vocabulary postVocabulary = invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary);

		assertEquals(randomVocabulary, postVocabulary);
		assertValid(postVocabulary);
	}

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

	protected void assertValid(Page<Vocabulary> page) {
		boolean valid = false;

		Collection<Vocabulary> folders = page.getItems();

		int size = folders.size();

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Vocabulary folder) {
		boolean valid = false;

		if ((folder.getDateCreated() != null) &&
			(folder.getDateModified() != null) && (folder.getId() != null) &&
			Objects.equals(folder.getContentSpace(), testGroup.getGroupId())) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(Vocabulary folder1, Vocabulary folder2) {
		if (Objects.equals(
				folder1.getDescription(), folder2.getDescription()) &&
			Objects.equals(folder1.getName(), folder2.getName())) {

			return true;
		}

		return false;
	}

	@Override
	protected Map<String, Function<Vocabulary, Date>>
		getDateTimeEntityNameGetterMap() {

		return new HashMap<String, Function<Vocabulary, Date>>() {
			{
				put("dateCreated", Vocabulary::getDateCreated);
				put("dateModified", Vocabulary::getDateModified);
			}
		};
	}

	@Override
	protected Map<String, Function<Vocabulary, String>>
		getStringEntityNameGetterMap() {

		return new HashMap<String, Function<Vocabulary, String>>() {
			{
				put("name", Vocabulary::getName);
			}
		};
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
		EntityModel entityModel = _entityModelResource.getEntityModel(null);

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
	private EntityModelResource _entityModelResource;

}