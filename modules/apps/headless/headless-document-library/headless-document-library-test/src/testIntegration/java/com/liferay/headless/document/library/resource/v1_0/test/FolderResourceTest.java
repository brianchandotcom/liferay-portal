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
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import java.util.Objects;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class FolderResourceTest extends BaseFolderResourceTestCase {

	@Override
	protected void assertValid(Folder folder) {
		boolean valid = false;

		if (Objects.equals(
				folder.getContentSpaceId(), testGroup.getGroupId()) &&
			(folder.getDateCreated() != null) &&
			(folder.getDateModified() != null) && (folder.getId() != null)) {

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
		return new Folder() {
			{
				description = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
			}
		};
	}

	protected Folder randomPatchFolder() {
		return new Folder() {
			{
				description = RandomTestUtil.randomString();
			}
		};
	}

	@Override
	protected Folder testDeleteFolder_addFolder() throws Exception {
		return invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());
	}

	@Override
	protected Folder testGetContentSpaceFoldersPage_addFolder(
			Long contentSpaceId, Folder folder)
		throws Exception {

		return invokePostContentSpaceFolder(contentSpaceId, folder);
	}

	@Override
	protected Folder testGetFolder_addFolder() throws Exception {
		Folder postFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());

		Assert.assertEquals(0, postFolder.getNumberOfDocuments());

		DLAppTestUtil.addFileEntryWithWorkflow(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(), postFolder.getId(), StringPool.BLANK,
			RandomTestUtil.randomString(10), true, new ServiceContext());

		Folder getFolder = invokeGetFolder(postFolder.getId());

		Assert.assertEquals(1, getFolder.getNumberOfDocuments());

		return postFolder;
	}

	@Override
	protected Folder testGetFolderFoldersPage_addFolder(
			Long folderId, Folder folder)
		throws Exception {

		return invokePostFolderFolder(folderId, folder);
	}

	@Override
	protected Long testGetFolderFoldersPage_getFolderId() throws Exception {
		Folder folder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());

		return folder.getId();
	}

	@Override
	protected Folder testPatchFolder_addFolder() throws Exception {
		return invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());
	}

	@Override
	protected Folder testPostContentSpaceFolder_addFolder(Folder folder)
		throws Exception {

		Folder postFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), folder);

		Assert.assertEquals(0, postFolder.getNumberOfDocuments());

		return postFolder;
	}

	@Override
	protected Folder testPostFolderFolder_addFolder(Folder folder)
		throws Exception {

		Folder parentFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());

		return invokePostFolderFolder(parentFolder.getId(), folder);
	}

	@Override
	protected Folder testPutFolder_addFolder() throws Exception {
		return invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());
	}

}