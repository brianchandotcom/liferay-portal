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
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class VocabularyResourceTest extends BaseVocabularyResourceTestCase {

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

		assertEquals(
			Arrays.asList(randomVocabulary1, randomVocabulary2),
			(List<Vocabulary>)page.getItems());
		assertValid(page);
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
	protected Vocabulary randomVocabulary() {
		return new VocabularyImpl() {
			{
				contentSpace = testGroup.getGroupId();
				description = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
			}
		};
	}

}