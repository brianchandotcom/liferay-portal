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

import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.headless.document.library.resource.v1_0.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import io.restassured.response.Response;

import java.net.MalformedURLException;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunAsClient
@RunWith(Arquillian.class)
public class FolderResourceTest extends BaseFolderResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_groupId = GroupTestUtil.addGroup(getUrl());
	}

	@After
	public void tearDown() throws MalformedURLException {
		GroupTestUtil.deleteGroup(getUrl(), _groupId);
	}

	@Ignore
	@Test
	public void testDeleteFolders() throws Exception {
		Folder folder = invokePostDocumentsRepositoriesFolders(
			_groupId, _buildFolder());

		invokeDeleteFolders(folder.getId());

		invokeGetFolders(folder.getId());

		Response response = invokeGetFoldersResponse(folder.getId());

		assertEquals(404, response.getStatusCode());
	}

	@Ignore
	@Test
	public void testGetFolders() throws Exception {
		Folder folder = invokePostDocumentsRepositoriesFolders(
			_groupId, _buildFolder());

		assertThat(
			toJSON(invokeGetFolders(folder.getId())), sameJSONAs(folder));
	}

	@Ignore
	@Test
	public void testPostDocumentsRepositoriesFolders() throws Exception {
		Folder folder = _buildFolder();

		assertThat(
			toJSON(invokePostDocumentsRepositoriesFolders(_groupId, folder)),
			sameJSONAs(folder));
	}

	@Ignore
	@Test
	public void testPostFoldersFolders() throws Exception {
		Folder parentFolder = invokePostDocumentsRepositoriesFolders(
			_groupId, _buildFolder());

		Folder subfolder = _buildFolder();

		assertThat(
			toJSON(invokePostFoldersFolders(parentFolder.getId(), subfolder)),
			sameJSONAs(subfolder));
	}

	@Ignore
	@Test
	public void testPutFolders() throws Exception {
		Folder folder = invokePostDocumentsRepositoriesFolders(
			_groupId, _buildFolder());

		Folder inputUpdateFolder = _buildFolder();

		Folder updatedFolder = invokePutFolders(
			folder.getId(), inputUpdateFolder);

		assertThat(toJSON(updatedFolder), sameJSONAs(inputUpdateFolder));

		assertThat(
			toJSON(invokeGetFolders(updatedFolder.getId())),
			sameJSONAs(inputUpdateFolder));
	}

	private Folder _buildFolder() {
		Folder folder = new Folder();

		folder.setDescription(StringUtil.randomString(10));
		folder.setDocumentsRepositoryId(_groupId);
		folder.setName(StringUtil.randomString(10));

		return folder;
	}

	private long _groupId;

}