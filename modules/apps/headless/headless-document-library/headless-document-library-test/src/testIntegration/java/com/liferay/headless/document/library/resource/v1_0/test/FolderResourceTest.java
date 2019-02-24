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

package com.liferay.headless.document.library.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

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
public class FolderResourceTest extends BaseFolderResourceTestCase {

	@Test
	public void testDeleteFolder() throws Exception {
		Folder postFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());

		assertResponseCode(200, invokeDeleteFolderResponse(postFolder.getId()));

		assertResponseCode(404, invokeGetFolderResponse(postFolder.getId()));
	}

	@Test
	public void testGetContentSpaceFoldersPage() throws Exception {
		Folder randomFolder1 = randomFolder();

		invokePostContentSpaceFolder(testGroup.getGroupId(), randomFolder1);

		Folder randomFolder2 = randomFolder();

		invokePostContentSpaceFolder(testGroup.getGroupId(), randomFolder2);

		Page<Folder> page = invokeGetContentSpaceFoldersPage(
			testGroup.getGroupId(), Pagination.of(2, 1));

		assertEquals(
			Arrays.asList(randomFolder1, randomFolder2),
			(List<Folder>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetFolder() throws Exception {
		Folder postFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());

		Folder getFolder = invokeGetFolder(postFolder.getId());

		assertEquals(postFolder, getFolder);
		assertValid(getFolder);
	}

	@Test
	public void testGetFolderFoldersPage() throws Exception {
		Folder postContentSpaceFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());
		Folder randomFolder1 = randomFolder();

		invokePostFolderFolder(postContentSpaceFolder.getId(), randomFolder1);

		Folder randomFolder2 = randomFolder();

		invokePostFolderFolder(postContentSpaceFolder.getId(), randomFolder2);

		Page<Folder> page = invokeGetFolderFoldersPage(
			postContentSpaceFolder.getId(), Pagination.of(2, 1));

		assertEquals(
			Arrays.asList(randomFolder1, randomFolder2),
			(List<Folder>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetFolderWithDocuments() throws Exception {
		Folder postFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());

		Assert.assertEquals(false, postFolder.getHasDocuments());

		_addDocument(postFolder.getId());

		Folder getFolder = invokeGetFolder(postFolder.getId());

		Assert.assertEquals(true, getFolder.getHasDocuments());
	}

	@Test
	public void testPostContentSpaceFolder() throws Exception {
		Folder randomFolder = randomFolder();

		Folder postFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder);

		assertEquals(randomFolder, postFolder);
		assertValid(postFolder);
	}

	@Test
	public void testPostFolderFolder() throws Exception {
		Folder randomFolder = randomFolder();

		Folder postContentSpaceFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder);

		Assert.assertFalse(postContentSpaceFolder.getHasFolders());

		Folder postFolderFolder = invokePostFolderFolder(
			postContentSpaceFolder.getId(), randomFolder);

		assertEquals(randomFolder, postFolderFolder);
		assertValid(postFolderFolder);

		Folder postContentSpaceFolderHasFolders = invokeGetFolder(
			postContentSpaceFolder.getId());

		Assert.assertTrue(postContentSpaceFolderHasFolders.getHasFolders());
	}

	@Test
	public void testPutFolder() throws Exception {
		Folder postFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());

		Folder randomFolder = randomFolder();

		Folder putFolder = invokePutFolder(postFolder.getId(), randomFolder);

		assertEquals(randomFolder, putFolder);
		assertValid(putFolder);

		Folder getFolder = invokeGetFolder(putFolder.getId());

		assertEquals(randomFolder, getFolder);
		assertValid(getFolder);
	}

	protected void assertEquals(Folder folder1, Folder folder2) {
		boolean equals = false;

		if (Objects.equals(
				folder1.getDescription(), folder2.getDescription()) &&
			Objects.equals(folder1.getName(), folder2.getName())) {

			equals = true;
		}

		Assert.assertTrue(equals);
	}

	protected void assertValid(Folder folder) {
		boolean valid = false;

		if ((folder.getDateCreated() != null) &&
			(folder.getDateModified() != null) && (folder.getId() != null) &&
			Objects.equals(folder.getRepositoryId(), testGroup.getGroupId())) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Page<Folder> page) {
		boolean valid = false;

		Collection<Folder> folders = page.getItems();

		int size = folders.size();

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(Folder folder1, Folder folder2) {
		if (Objects.equals(
				folder1.getDescription(), folder2.getDescription()) &&
			Objects.equals(folder1.getName(), folder2.getName())) {

			return true;
		}

		return false;
	}

	@Override
	protected Folder randomFolder() {
		return new FolderImpl() {
			{
				description = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
			}
		};
	}

	private void _addDocument(long folderId) throws Exception {
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			testGroup.getCompanyId());

		DLAppTestUtil.addFileEntryWithWorkflow(
			defaultUserId, testGroup.getGroupId(), folderId, StringPool.BLANK,
			RandomTestUtil.randomString(10), true, new ServiceContext());
	}

}