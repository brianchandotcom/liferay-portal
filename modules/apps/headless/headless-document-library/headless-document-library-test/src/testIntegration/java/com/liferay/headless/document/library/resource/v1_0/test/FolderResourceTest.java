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

import java.util.Objects;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@Ignore
@RunWith(Arquillian.class)
public class FolderResourceTest extends BaseFolderResourceTestCase {

	@Test
	public void testDeleteFolder() throws Exception {
		Folder randomFolder = randomFolder();

		invokePostContentSpaceFolder(testGroup.getGroupId(), randomFolder);

		assertResponseCode(
			202, invokeDeleteFolderResponse(randomFolder.getId()));

		assertResponseCode(404, invokeGetFolderResponse(randomFolder.getId()));
	}

	@Test
	public void testGetFolder() throws Exception {
		Folder postFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());

		Folder getFolder = invokeGetFolder(postFolder.getId());

		assertEquals(postFolder, getFolder);
	}

	@Test
	public void testPostContentSpaceFolder() throws Exception {
		Folder randomFolder = randomFolder();

		Folder postFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder);

		assertEquals(randomFolder, postFolder);
	}

	@Test
	public void testPostFolderFolder() throws Exception {
		Folder randomFolder = randomFolder();

		Folder postContentSpaceFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder);

		Folder postFolderFolder = invokePostFolderFolder(
			postContentSpaceFolder.getId(), randomFolder);

		assertEquals(randomFolder, postFolderFolder);
	}

	@Test
	public void testPutFolder() throws Exception {
		Folder postFolder = invokePostContentSpaceFolder(
			testGroup.getGroupId(), randomFolder());

		Folder randomFolder = randomFolder();

		Folder putFolder = invokePutFolder(postFolder.getId(), randomFolder);

		assertEquals(randomFolder, putFolder);

		Folder getFolder = invokeGetFolder(putFolder.getId());

		assertEquals(randomFolder, getFolder);
	}

	protected void assertEquals(Folder folder1, Folder folder2) {
		boolean equals = false;

		if (Objects.equals(
				folder1.getDateCreated(), folder2.getDateCreated()) &&
			Objects.equals(folder1.getId(), folder2.getId())) {

			equals = true;
		}

		Assert.assertTrue(equals);
	}

}