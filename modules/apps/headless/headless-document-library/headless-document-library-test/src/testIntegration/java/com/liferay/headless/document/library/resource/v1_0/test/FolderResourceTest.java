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
import com.liferay.portal.kernel.util.StringUtil;

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
		Folder folder = invokePostDocumentsRepositoryFolder(
			testGroup.getGroupId(), _buildFolder());

		invokeDeleteFolder(folder.getId());

		Response response = invokeGetFolderResponse(folder.getId());

		assertEquals(404, response.getStatusCode());
	}

	@Test
	public void testGetFolder() throws Exception {
		Folder folder = invokePostDocumentsRepositoryFolder(
			testGroup.getGroupId(), _buildFolder());

		assertThat(toJSON(invokeGetFolder(folder.getId())), sameJSONAs(folder));
	}

	@Test
	public void testPostDocumentsRepositoryFolder() throws Exception {
		Folder folder = _buildFolder();

		assertThat(
			toJSON(
				invokePostDocumentsRepositoryFolder(
					testGroup.getGroupId(), folder)),
			sameJSONAs(folder));
	}

	@Test
	public void testPostFolderFolder() throws Exception {
		Folder parentFolder = invokePostDocumentsRepositoryFolder(
			testGroup.getGroupId(), _buildFolder());

		Folder subfolder = _buildFolder();

		assertThat(
			toJSON(invokePostFolderFolder(parentFolder.getId(), subfolder)),
			sameJSONAs(subfolder));
	}

	@Test
	public void testPutFolder() throws Exception {
		Folder folder = invokePostDocumentsRepositoryFolder(
			testGroup.getGroupId(), _buildFolder());

		Folder inputUpdateFolder = _buildFolder();

		Folder updatedFolder = invokePutFolder(
			folder.getId(), inputUpdateFolder);

		assertThat(toJSON(updatedFolder), sameJSONAs(inputUpdateFolder));

		assertThat(
			toJSON(invokeGetFolder(updatedFolder.getId())),
			sameJSONAs(inputUpdateFolder));
	}

	private Folder _buildFolder() {
		Folder folder = new Folder();

		folder.setDescription(StringUtil.randomString(10));
		folder.setDocumentsRepositoryId(testGroup.getGroupId());
		folder.setName(StringUtil.randomString(10));

		return folder;
	}

}