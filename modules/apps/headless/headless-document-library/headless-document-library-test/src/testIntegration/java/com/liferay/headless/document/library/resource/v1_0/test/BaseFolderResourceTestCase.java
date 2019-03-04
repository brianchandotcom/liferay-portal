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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.headless.document.library.resource.v1_0.FolderResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseFolderResourceTestCase {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");
	}

	@Before
	public void setUp() throws Exception {
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-document-library/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testDeleteFolder() throws Exception {
		Folder folder = testDeleteFolder_addFolder();

		assertResponseCode(200, invokeDeleteFolderResponse(folder.getId()));

		assertResponseCode(404, invokeGetFolderResponse(folder.getId()));
	}

	@Test
	public void testGetContentSpaceFoldersPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceFoldersPage_getContentSpaceId();

		Folder folder1 = testGetContentSpaceFoldersPage_addFolder(
			contentSpaceId, randomFolder());
		Folder folder2 = testGetContentSpaceFoldersPage_addFolder(
			contentSpaceId, randomFolder());

		Page<Folder> page = invokeGetContentSpaceFoldersPage(
			contentSpaceId, Pagination.of(2, 1));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(folder1, folder2), (List<Folder>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceFoldersPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceFoldersPage_getContentSpaceId();

		Folder folder1 = testGetContentSpaceFoldersPage_addFolder(
			contentSpaceId, randomFolder());
		Folder folder2 = testGetContentSpaceFoldersPage_addFolder(
			contentSpaceId, randomFolder());
		Folder folder3 = testGetContentSpaceFoldersPage_addFolder(
			contentSpaceId, randomFolder());

		Page<Folder> page1 = invokeGetContentSpaceFoldersPage(
			contentSpaceId, Pagination.of(2, 1));

		List<Folder> folders1 = (List<Folder>)page1.getItems();

		Assert.assertEquals(folders1.toString(), 2, folders1.size());

		Page<Folder> page2 = invokeGetContentSpaceFoldersPage(
			contentSpaceId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Folder> folders2 = (List<Folder>)page2.getItems();

		Assert.assertEquals(folders2.toString(), 1, folders2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(folder1, folder2, folder3),
			new ArrayList<Folder>() {
				{
					addAll(folders1);
					addAll(folders2);
				}
			});
	}

	@Test
	public void testGetFolder() throws Exception {
		Folder postFolder = testGetFolder_addFolder();

		Folder getFolder = invokeGetFolder(postFolder.getId());

		assertEquals(postFolder, getFolder);
		assertValid(getFolder);
	}

	@Test
	public void testGetFolderFoldersPage() throws Exception {
		Long folderId = testGetFolderFoldersPage_getFolderId();

		Folder folder1 = testGetFolderFoldersPage_addFolder(
			folderId, randomFolder());
		Folder folder2 = testGetFolderFoldersPage_addFolder(
			folderId, randomFolder());

		Page<Folder> page = invokeGetFolderFoldersPage(
			folderId, Pagination.of(2, 1));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(folder1, folder2), (List<Folder>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetFolderFoldersPageWithPagination() throws Exception {
		Long folderId = testGetFolderFoldersPage_getFolderId();

		Folder folder1 = testGetFolderFoldersPage_addFolder(
			folderId, randomFolder());
		Folder folder2 = testGetFolderFoldersPage_addFolder(
			folderId, randomFolder());
		Folder folder3 = testGetFolderFoldersPage_addFolder(
			folderId, randomFolder());

		Page<Folder> page1 = invokeGetFolderFoldersPage(
			folderId, Pagination.of(2, 1));

		List<Folder> folders1 = (List<Folder>)page1.getItems();

		Assert.assertEquals(folders1.toString(), 2, folders1.size());

		Page<Folder> page2 = invokeGetFolderFoldersPage(
			folderId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Folder> folders2 = (List<Folder>)page2.getItems();

		Assert.assertEquals(folders2.toString(), 1, folders2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(folder1, folder2, folder3),
			new ArrayList<Folder>() {
				{
					addAll(folders1);
					addAll(folders2);
				}
			});
	}

	@Test
	public void testPatchFolder() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostContentSpaceFolder() throws Exception {
		Folder randomFolder = randomFolder();

		Folder postFolder = testPostContentSpaceFolder_addFolder(randomFolder);

		assertEquals(randomFolder, postFolder);
		assertValid(postFolder);
	}

	@Test
	public void testPostFolderFolder() throws Exception {
		Folder randomFolder = randomFolder();

		Folder postFolder = testPostFolderFolder_addFolder(randomFolder);

		assertEquals(randomFolder, postFolder);
		assertValid(postFolder);
	}

	@Test
	public void testPutFolder() throws Exception {
		Folder postFolder = testPutFolder_addFolder();

		Folder randomFolder = randomFolder();

		Folder putFolder = invokePutFolder(postFolder.getId(), randomFolder);

		assertEquals(randomFolder, putFolder);
		assertValid(putFolder);

		Folder getFolder = invokeGetFolder(putFolder.getId());

		assertEquals(randomFolder, getFolder);
		assertValid(getFolder);
	}

	protected void assertEquals(Folder folder1, Folder folder2) {
		Assert.assertTrue(
			folder1 + " does not equal " + folder2, equals(folder1, folder2));
	}

	protected void assertEquals(List<Folder> folders1, List<Folder> folders2) {
		Assert.assertEquals(folders1.size(), folders2.size());

		for (int i = 0; i < folders1.size(); i++) {
			Folder folder1 = folders1.get(i);
			Folder folder2 = folders2.get(i);

			assertEquals(folder1, folder2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Folder> folders1, List<Folder> folders2) {

		Assert.assertEquals(folders1.size(), folders2.size());

		for (Folder folder1 : folders1) {
			boolean contains = false;

			for (Folder folder2 : folders2) {
				if (equals(folder1, folder2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				folders2 + " does not contain " + folder1, contains);
		}
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertValid(Folder folder) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
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

	protected boolean equals(Folder folder1, Folder folder2) {
		if (folder1 == folder2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_folderResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_folderResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type)
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, Folder folder) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("contentSpaceId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(folder.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(folder.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(folder.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("hasDocuments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("hasFolders")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(folder.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("viewableBy")) {
			sb.append("'");
			sb.append(String.valueOf(folder.getViewableBy()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected boolean invokeDeleteFolder(Long folderId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}", folderId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Boolean.class);
	}

	protected Http.Response invokeDeleteFolderResponse(Long folderId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}", folderId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<Folder> invokeGetContentSpaceFoldersPage(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/folders",
					contentSpaceId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Folder>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceFoldersPageResponse(
			Long contentSpaceId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/folders",
					contentSpaceId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Folder invokeGetFolder(Long folderId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}", folderId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Folder.class);
	}

	protected Page<Folder> invokeGetFolderFoldersPage(
			Long folderId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}/folders", folderId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Folder>>() {
			});
	}

	protected Http.Response invokeGetFolderFoldersPageResponse(
			Long folderId, Pagination pagination)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}/folders", folderId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Http.Response invokeGetFolderResponse(Long folderId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}", folderId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Folder invokePatchFolder(Long folderId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}", folderId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Folder.class);
	}

	protected Http.Response invokePatchFolderResponse(
			Long folderId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}", folderId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Folder invokePostContentSpaceFolder(
			Long contentSpaceId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/folders",
					contentSpaceId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Folder.class);
	}

	protected Http.Response invokePostContentSpaceFolderResponse(
			Long contentSpaceId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/folders",
					contentSpaceId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Folder invokePostFolderFolder(Long folderId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}/folders", folderId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Folder.class);
	}

	protected Http.Response invokePostFolderFolderResponse(
			Long folderId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}/folders", folderId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Folder invokePutFolder(Long folderId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}", folderId));

		options.setPut(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Folder.class);
	}

	protected Http.Response invokePutFolderResponse(
			Long folderId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}", folderId));

		options.setPut(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Folder randomFolder() {
		return new Folder() {
			{
				contentSpaceId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				hasDocuments = RandomTestUtil.randomBoolean();
				hasFolders = RandomTestUtil.randomBoolean();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				viewableBy = RandomTestUtil.randomString();
			}
		};
	}

	protected Folder testDeleteFolder_addFolder() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Folder testGetContentSpaceFoldersPage_addFolder(
			Long contentSpaceId, Folder folder)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentSpaceFoldersPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Folder testGetFolder_addFolder() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Folder testGetFolderFoldersPage_addFolder(
			Long folderId, Folder folder)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetFolderFoldersPage_getFolderId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Folder testPostContentSpaceFolder_addFolder(Folder folder)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Folder testPostFolderFolder_addFolder(Folder folder)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Folder testPutFolder_addFolder() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getItemsPerPage() {
			return itemsPerPage;
		}

		public long getLastPageNumber() {
			return lastPageNumber;
		}

		public long getPageNumber() {
			return pageNumber;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty("pageSize")
		protected long itemsPerPage;

		@JsonProperty
		protected long lastPageNumber;

		@JsonProperty("page")
		protected long pageNumber;

		@JsonProperty
		protected long totalCount;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");

		String userNameAndPassword = "test@liferay.com:test";

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", "application/json");

		return options;
	}

	private String _toPath(String template, Object value) {
		return template.replaceFirst("\\{.*\\}", String.valueOf(value));
	}

	private static DateFormat _dateFormat;
	private static final ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private static final ObjectMapper _outputObjectMapper = new ObjectMapper();

	@Inject
	private FolderResource _folderResource;

	private URL _resourceURL;

}