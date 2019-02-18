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

import static junit.framework.TestCase.assertEquals;

import static org.hamcrest.MatcherAssert.assertThat;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.document.library.dto.v1_0.Folder;

import com.liferay.headless.document.library.resource.v1_0.test.dto.FolderImpl;
import io.restassured.response.Response;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class FolderResourceTest extends BaseFolderResourceTestCase {

	@Test
	public void testDeleteFolder() throws Exception {
		Response postResponse = invokePostDocumentsRepositoryFolder(
			testGroup.getGroupId(), randomFolder());

		Folder folder = postResponse.as(FolderImpl.class);

		invokeDeleteFolder(folder.getId());

		Response getResponse = invokeGetFolder(folder.getId());

		assertEquals(404, getResponse.getStatusCode());
	}

	@Test
	public void testGetFolder() throws Exception {
		Response postResponse = invokePostDocumentsRepositoryFolder(
			testGroup.getGroupId(), randomFolder());

		Folder folder = postResponse.as(FolderImpl.class);

		Response getResponse = invokeGetFolder(folder.getId());

		assertThat(
			toJSON(getResponse.as(FolderImpl.class)), sameJSONAs(folder));
	}

	@Test
	public void testPostDocumentsRepositoryFolder() throws Exception {
		Folder folder = randomFolder();

		Response response = invokePostDocumentsRepositoryFolder(
			testGroup.getGroupId(), folder);

		assertThat(toJSON(response.as(FolderImpl.class)), sameJSONAs(folder));
	}

	@Test
	public void testPostFolderFolder() throws Exception {
		Response postParentFolderResponse = invokePostDocumentsRepositoryFolder(
			testGroup.getGroupId(), randomFolder());

		Folder parentFolder = postParentFolderResponse.as(FolderImpl.class);

		Folder subfolder = randomFolder();

		Response postSubFolderResponse = invokePostFolderFolder(
			parentFolder.getId(), subfolder);

		assertThat(
			toJSON(postSubFolderResponse.as(FolderImpl.class)),
			sameJSONAs(subfolder));
	}

	@Test
	public void testPutFolder() throws Exception {
		Response postResponse = invokePostDocumentsRepositoryFolder(
			testGroup.getGroupId(), randomFolder());

		Folder folder = postResponse.as(FolderImpl.class);

		Folder inputUpdateFolder = randomFolder();

		Response putResponse = invokePutFolder(
			folder.getId(), inputUpdateFolder);

		Folder updatedFolder = putResponse.as(FolderImpl.class);

		assertThat(toJSON(updatedFolder), sameJSONAs(inputUpdateFolder));

		Response getResponse = invokeGetFolder(updatedFolder.getId());

		assertThat(
			toJSON(getResponse.as(FolderImpl.class)),
			sameJSONAs(inputUpdateFolder));
	}

}