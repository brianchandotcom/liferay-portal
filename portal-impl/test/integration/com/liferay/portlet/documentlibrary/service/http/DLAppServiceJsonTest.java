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

package com.liferay.portlet.documentlibrary.service.http;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.BaseJsonClientTestCase;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alexander Chow
 */
public class DLAppServiceJsonTest extends BaseJsonClientTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		String name = "Test Folder";
		String description = "This is a test folder.";

		HttpPost httpPost = new HttpPost(_URL_DELETE_FOLDER);

		MultipartEntityBuilder multipartEntityBuilder =
			getMultipartEntityBuilder(
				new String[] {"repositoryId", "parentFolderId", "name"},
				new Object[] {
					_group.getGroupId(),
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name
				});

		httpPost.setEntity(multipartEntityBuilder.build());

		executeRequest(httpPost);

		httpPost = new HttpPost(_URL_ADD_FOLDER);

		multipartEntityBuilder = getMultipartEntityBuilder(
			new String[] {
				"repositoryId", "parentFolderId", "name", "description"
			},
			new Object[] {
				_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				name, description
			});

		httpPost.setEntity(multipartEntityBuilder.build());

		String responseContent = executeRequest(httpPost);

		checkException(responseContent);

		_folderId = GetterUtil.getLong(
			parseResponseContent(responseContent, "folderId", false));

		Assert.assertNotSame(0, _folderId);
	}

	@After
	public void tearDown() throws Exception {
		if (_folderId != 0) {
			HttpPost httpPost = new HttpPost(_URL_DELETE_FOLDER);

			MultipartEntityBuilder multipartEntityBuilder =
				getMultipartEntityBuilder(
					new String[] {"folderId"}, new Object[] {_folderId});

			httpPost.setEntity(multipartEntityBuilder.build());

			executeRequest(httpPost);
		}
	}

	@Test
	public void testAddFileEntry() throws Exception {
		String responseContent = addFileEntry("Test Add.txt");

		checkException(responseContent);
	}

	@Test
	public void testDeleteFileEntry() throws Exception {
		String responseContent = addFileEntry("Test Delete.txt");

		checkException(responseContent);

		long fileEntryId = GetterUtil.getLong(
			parseResponseContent(responseContent, "fileEntryId", false));

		Assert.assertNotSame(0, fileEntryId);

		HttpPost httpPost = new HttpPost(_URL_DELETE_FILE_ENTRY);

		MultipartEntityBuilder multipartEntityBuilder =
			getMultipartEntityBuilder(
				new String[] {"fileEntryId"}, new Object[] {fileEntryId});

		httpPost.setEntity(multipartEntityBuilder.build());

		responseContent = executeRequest(httpPost);

		checkException(responseContent);
	}

	@Test
	public void testGetFileEntry() throws Exception {
		String responseContent = addFileEntry("Test Get.txt");

		checkException(responseContent);

		String uuid = parseResponseContent(responseContent, "uuid", true);
		String groupId = String.valueOf(_group.getGroupId());

		String url = StringUtil.replace(
			_URL_GET_FILE_ENTRY_BY_UUID_AND_GROUP_ID,
			new String[] {_UUID, _GROUP_ID}, new String[] {uuid, groupId});

		HttpGet httpGet = new HttpGet(url);

		responseContent = executeRequest(httpGet);

		checkException(responseContent);
	}

	protected String addFileEntry(String title) throws Exception {
		long repositoryId = _group.getGroupId();
		long folderId = _folderId;
		String mimeType = ContentTypes.TEXT_PLAIN;
		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;
		byte[] bytes = _CONTENT.getBytes();

		HttpPost httpPost = new HttpPost(_URL_ADD_FILE_ENTRY);

		MultipartEntityBuilder multipartEntityBuilder =
			getMultipartEntityBuilder(
				new String[] {
					"repositoryId", "folderId", "sourceFileName", "mimeType",
					"title", "description", "changeLog"
				},
				new Object[] {
					repositoryId, folderId, title, mimeType, title, description,
					changeLog
				});

		multipartEntityBuilder.addPart(
			"file", getByteArrayBody(bytes, mimeType, title));

		httpPost.setEntity(multipartEntityBuilder.build());

		return executeRequest(httpPost);
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	private static final String _GROUP_ID = "[$GROUP_ID$]";

	private static final String _URL_ADD_FILE_ENTRY =
		URL_JSONWS + "/dlapp/add-file-entry";

	private static final String _URL_ADD_FOLDER =
		URL_JSONWS + "/dlapp/add-folder";

	private static final String _URL_DELETE_FILE_ENTRY =
		URL_JSONWS + "/dlapp/delete-file-entry";

	private static final String _URL_DELETE_FOLDER =
		URL_JSONWS + "/dlapp/delete-folder";

	private static final String _URL_GET_FILE_ENTRY_BY_UUID_AND_GROUP_ID =
		URL_JSONWS + "/dlapp/get-file-entry-by-uuid-and-group-id/uuid/" +
			DLAppServiceJsonTest._UUID + "/group-id/" + _GROUP_ID;

	private static final String _UUID = "[$UUID$]";

	private long _folderId;
	private Group _group;

}