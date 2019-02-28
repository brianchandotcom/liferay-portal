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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.foundation.dto.v1_0.Vocabulary;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.beanutils.BeanUtils;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@Ignore
@RunWith(Arquillian.class)
public class VocabularyResourceTest extends BaseVocabularyResourceTestCase {

	@Override
	@Test
	public void testDeleteVocabulary() throws Exception {
		Vocabulary vocabulary = invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary());

		assertResponseCode(
			200, invokeDeleteVocabularyResponse(vocabulary.getId()));

		assertResponseCode(
			404, invokeGetVocabularyResponse(vocabulary.getId()));
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

		for (EntityField entityField :
				getEntityFields(EntityField.Type.DATE_TIME)) {

			Page<Vocabulary> page = invokeGetContentSpaceVocabulariesPage(
				testGroup.getGroupId(),
				getFilterString(entityField, "eq", vocabulary),
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

		for (EntityField entityField :
				getEntityFields(EntityField.Type.STRING)) {

			Page<Vocabulary> page = invokeGetContentSpaceVocabulariesPage(
				testGroup.getGroupId(),
				getFilterString(entityField, "eq", vocabulary),
				Pagination.of(2, 1), null);

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
	public void testGetContentSpaceVocabulariesPageWithSortDateTime()
		throws Exception {

		Vocabulary randomVocabulary1 = randomVocabulary();

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary1);

		Vocabulary randomVocabulary2 = randomVocabulary();

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary2);

		List<EntityField> stringEntityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		for (EntityField entityField : stringEntityFields) {
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
	public void testGetContentSpaceVocabulariesPageWithSortString()
		throws Exception {

		Vocabulary randomVocabulary1 = randomVocabulary();
		Vocabulary randomVocabulary2 = randomVocabulary();

		List<EntityField> stringEntityFields = getEntityFields(
			EntityField.Type.STRING);

		for (EntityField entityField : stringEntityFields) {
			BeanUtils.setProperty(
				randomVocabulary1, entityField.getName(), "Value1");

			BeanUtils.setProperty(
				randomVocabulary2, entityField.getName(), "Value2");
		}

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary1);

		invokePostContentSpaceVocabulary(
			testGroup.getGroupId(), randomVocabulary2);

		for (EntityField entityField : stringEntityFields) {
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

}