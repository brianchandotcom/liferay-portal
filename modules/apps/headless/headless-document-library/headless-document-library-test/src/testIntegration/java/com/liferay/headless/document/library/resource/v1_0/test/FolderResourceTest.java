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

import static com.liferay.portal.kernel.util.Http.Response;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.document.library.dto.v1_0.Folder;

import java.util.Date;

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
		Folder inputFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());

		Response deleteResponse = invokeDeleteFolderResponse(
			inputFolder.getId());

		Assert.assertEquals(200, deleteResponse.getResponseCode());

		Response getResponse = invokeGetFolderResponse(inputFolder.getId());

		Assert.assertEquals(404, getResponse.getResponseCode());
	}

	@Test
	public void testGetFolder() throws Exception {
		Folder inputFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());

		Folder folder = invokeGetFolder(inputFolder.getId());

		Assert.assertEquals(toJSON(inputFolder), toJSON(folder));
	}

	@Test
	public void testPostContentSpaceFolder() throws Exception {
		Folder inputFolder = randomFolder();

		Folder folder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), inputFolder);

		Assert.assertEquals(toJSON(inputFolder), toJSON(folder));
	}

	@Test
	public void testPostFolderFolder() throws Exception {
		Folder parentFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());

		Folder inputSubfolder = randomFolder();

		Folder subfolder = invokePostFolderFolder(
			parentFolder.getId(), inputSubfolder);

		Assert.assertEquals(toJSON(inputSubfolder), toJSON(subfolder));
	}

	@Test
	public void testPutFolder() throws Exception {
		Folder folder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());

		Folder inputUpdateFolder = randomFolder();

		Folder updatedFolder = invokePutFolder(
			folder.getId(), inputUpdateFolder);

		Assert.assertEquals(toJSON(inputUpdateFolder), toJSON(updatedFolder));

		Folder getFolder = invokeGetFolder(updatedFolder.getId());

		Assert.assertEquals(toJSON(inputUpdateFolder), toJSON(getFolder));
	}

	@Override
	protected Folder randomFolder() {
		Folder folder = super.randomFolder();

		folder.setDateCreated((Date)null);
		folder.setDateModified((Date)null);
		folder.setHasDocuments((Boolean)null);
		folder.setHasFolders((Boolean)null);
		folder.setId((Long)null);
		folder.setRepositoryId(testGroup.getGroupId());

		return folder;
	}

}