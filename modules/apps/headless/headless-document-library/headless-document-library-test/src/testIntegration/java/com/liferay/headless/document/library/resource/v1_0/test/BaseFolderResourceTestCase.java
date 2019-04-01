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
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.document.library.dto.v1_0.Folder;
import com.liferay.headless.document.library.resource.v1_0.FolderResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
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

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;

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
		irrelevantGroup = GroupTestUtil.addGroup();
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-document-library/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceFoldersPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceFoldersPage_getContentSpaceId();
		Long irrelevantContentSpaceId =
			testGetContentSpaceFoldersPage_getIrrelevantContentSpaceId();

		if ((irrelevantContentSpaceId != null)) {
			Folder irrelevantFolder = testGetContentSpaceFoldersPage_addFolder(
				irrelevantContentSpaceId, randomIrrelevantFolder());

			Page<Folder> page = invokeGetContentSpaceFoldersPage(
				irrelevantContentSpaceId, null, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantFolder), (List<Folder>)page.getItems());
			assertValid(page);
		}

		Folder folder1 = testGetContentSpaceFoldersPage_addFolder(
			contentSpaceId, randomFolder());

		Folder folder2 = testGetContentSpaceFoldersPage_addFolder(
			contentSpaceId, randomFolder());

		Page<Folder> page = invokeGetContentSpaceFoldersPage(
			contentSpaceId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(folder1, folder2), (List<Folder>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceFoldersPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceFoldersPage_getContentSpaceId();

		Folder folder1 = randomFolder();
		Folder folder2 = randomFolder();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				folder1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		folder1 = testGetContentSpaceFoldersPage_addFolder(
			contentSpaceId, folder1);

		Thread.sleep(1000);

		folder2 = testGetContentSpaceFoldersPage_addFolder(
			contentSpaceId, folder2);

		for (EntityField entityField : entityFields) {
			Page<Folder> page = invokeGetContentSpaceFoldersPage(
				contentSpaceId, null, null,
				getFilterString(entityField, "eq", folder1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(folder1),
				(List<Folder>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceFoldersPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceFoldersPage_getContentSpaceId();

		Folder folder1 = testGetContentSpaceFoldersPage_addFolder(
			contentSpaceId, randomFolder());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Folder folder2 = testGetContentSpaceFoldersPage_addFolder(
			contentSpaceId, randomFolder());

		for (EntityField entityField : entityFields) {
			Page<Folder> page = invokeGetContentSpaceFoldersPage(
				contentSpaceId, null, null,
				getFilterString(entityField, "eq", folder1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(folder1),
				(List<Folder>)page.getItems());
		}
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
			contentSpaceId, null, null, null, Pagination.of(1, 2), null);

		List<Folder> folders1 = (List<Folder>)page1.getItems();

		Assert.assertEquals(folders1.toString(), 2, folders1.size());

		Page<Folder> page2 = invokeGetContentSpaceFoldersPage(
			contentSpaceId, null, null, null, Pagination.of(2, 2), null);

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
	public void testGetContentSpaceFoldersPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceFoldersPage_getContentSpaceId();

		Folder folder1 = randomFolder();
		Folder folder2 = randomFolder();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				folder1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		folder1 = testGetContentSpaceFoldersPage_addFolder(
			contentSpaceId, folder1);

		Thread.sleep(1000);

		folder2 = testGetContentSpaceFoldersPage_addFolder(
			contentSpaceId, folder2);

		for (EntityField entityField : entityFields) {
			Page<Folder> ascPage = invokeGetContentSpaceFoldersPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(folder1, folder2),
				(List<Folder>)ascPage.getItems());

			Page<Folder> descPage = invokeGetContentSpaceFoldersPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(folder2, folder1),
				(List<Folder>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentSpaceFoldersPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceFoldersPage_getContentSpaceId();

		Folder folder1 = randomFolder();
		Folder folder2 = randomFolder();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(folder1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(folder2, entityField.getName(), "Bbb");
		}

		folder1 = testGetContentSpaceFoldersPage_addFolder(
			contentSpaceId, folder1);

		folder2 = testGetContentSpaceFoldersPage_addFolder(
			contentSpaceId, folder2);

		for (EntityField entityField : entityFields) {
			Page<Folder> ascPage = invokeGetContentSpaceFoldersPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(folder1, folder2),
				(List<Folder>)ascPage.getItems());

			Page<Folder> descPage = invokeGetContentSpaceFoldersPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(folder2, folder1),
				(List<Folder>)descPage.getItems());
		}
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

	protected Long testGetContentSpaceFoldersPage_getIrrelevantContentSpaceId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<Folder> invokeGetContentSpaceFoldersPage(
			Long contentSpaceId, Boolean flatten, String search,
			String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/folders",
					contentSpaceId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<Folder>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceFoldersPageResponse(
			Long contentSpaceId, Boolean flatten, String search,
			String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/folders",
					contentSpaceId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostContentSpaceFolder() throws Exception {
		Folder randomFolder = randomFolder();

		Folder postFolder = testPostContentSpaceFolder_addFolder(randomFolder);

		assertEquals(randomFolder, postFolder);
		assertValid(postFolder);
	}

	protected Folder testPostContentSpaceFolder_addFolder(Folder folder)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Folder invokePostContentSpaceFolder(
			Long contentSpaceId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/folders",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, Folder.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostContentSpaceFolderResponse(
			Long contentSpaceId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/folders",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteFolder() throws Exception {
		Folder folder = testDeleteFolder_addFolder();

		assertResponseCode(204, invokeDeleteFolderResponse(folder.getId()));

		assertResponseCode(404, invokeGetFolderResponse(folder.getId()));
	}

	protected Folder testDeleteFolder_addFolder() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteFolder(Long folderId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL + _toPath("/folders/{folder-id}", folderId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteFolderResponse(Long folderId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL + _toPath("/folders/{folder-id}", folderId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetFolder() throws Exception {
		Folder postFolder = testGetFolder_addFolder();

		Folder getFolder = invokeGetFolder(postFolder.getId());

		assertEquals(postFolder, getFolder);
		assertValid(getFolder);
	}

	protected Folder testGetFolder_addFolder() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Folder invokeGetFolder(Long folderId) throws Exception {
		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/folders/{folder-id}", folderId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, Folder.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetFolderResponse(Long folderId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/folders/{folder-id}", folderId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPatchFolder() throws Exception {
		Folder postFolder = testPatchFolder_addFolder();

		Folder randomPatchFolder = randomPatchFolder();

		Folder patchFolder = invokePatchFolder(
			postFolder.getId(), randomPatchFolder);

		Folder expectedPatchFolder = (Folder)BeanUtils.cloneBean(postFolder);

		_beanUtilsBean.copyProperties(expectedPatchFolder, randomPatchFolder);

		Folder getFolder = invokeGetFolder(patchFolder.getId());

		assertEquals(expectedPatchFolder, getFolder);
		assertValid(getFolder);
	}

	protected Folder testPatchFolder_addFolder() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Folder invokePatchFolder(Long folderId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/folders/{folder-id}", folderId);

		options.setLocation(location);

		options.setPatch(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, Folder.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePatchFolderResponse(
			Long folderId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/folders/{folder-id}", folderId);

		options.setLocation(location);

		options.setPatch(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
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

	protected Folder testPutFolder_addFolder() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Folder invokePutFolder(Long folderId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/folders/{folder-id}", folderId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, Folder.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutFolderResponse(
			Long folderId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/folders/{folder-id}", folderId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetFolderFoldersPage() throws Exception {
		Long folderId = testGetFolderFoldersPage_getFolderId();
		Long irrelevantFolderId =
			testGetFolderFoldersPage_getIrrelevantFolderId();

		if ((irrelevantFolderId != null)) {
			Folder irrelevantFolder = testGetFolderFoldersPage_addFolder(
				irrelevantFolderId, randomIrrelevantFolder());

			Page<Folder> page = invokeGetFolderFoldersPage(
				irrelevantFolderId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantFolder), (List<Folder>)page.getItems());
			assertValid(page);
		}

		Folder folder1 = testGetFolderFoldersPage_addFolder(
			folderId, randomFolder());

		Folder folder2 = testGetFolderFoldersPage_addFolder(
			folderId, randomFolder());

		Page<Folder> page = invokeGetFolderFoldersPage(
			folderId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(folder1, folder2), (List<Folder>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetFolderFoldersPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long folderId = testGetFolderFoldersPage_getFolderId();

		Folder folder1 = randomFolder();
		Folder folder2 = randomFolder();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				folder1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		folder1 = testGetFolderFoldersPage_addFolder(folderId, folder1);

		Thread.sleep(1000);

		folder2 = testGetFolderFoldersPage_addFolder(folderId, folder2);

		for (EntityField entityField : entityFields) {
			Page<Folder> page = invokeGetFolderFoldersPage(
				folderId, null, getFilterString(entityField, "eq", folder1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(folder1),
				(List<Folder>)page.getItems());
		}
	}

	@Test
	public void testGetFolderFoldersPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long folderId = testGetFolderFoldersPage_getFolderId();

		Folder folder1 = testGetFolderFoldersPage_addFolder(
			folderId, randomFolder());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Folder folder2 = testGetFolderFoldersPage_addFolder(
			folderId, randomFolder());

		for (EntityField entityField : entityFields) {
			Page<Folder> page = invokeGetFolderFoldersPage(
				folderId, null, getFilterString(entityField, "eq", folder1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(folder1),
				(List<Folder>)page.getItems());
		}
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
			folderId, null, null, Pagination.of(1, 2), null);

		List<Folder> folders1 = (List<Folder>)page1.getItems();

		Assert.assertEquals(folders1.toString(), 2, folders1.size());

		Page<Folder> page2 = invokeGetFolderFoldersPage(
			folderId, null, null, Pagination.of(2, 2), null);

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
	public void testGetFolderFoldersPageWithSortDateTime() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long folderId = testGetFolderFoldersPage_getFolderId();

		Folder folder1 = randomFolder();
		Folder folder2 = randomFolder();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				folder1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		folder1 = testGetFolderFoldersPage_addFolder(folderId, folder1);

		Thread.sleep(1000);

		folder2 = testGetFolderFoldersPage_addFolder(folderId, folder2);

		for (EntityField entityField : entityFields) {
			Page<Folder> ascPage = invokeGetFolderFoldersPage(
				folderId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(folder1, folder2),
				(List<Folder>)ascPage.getItems());

			Page<Folder> descPage = invokeGetFolderFoldersPage(
				folderId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(folder2, folder1),
				(List<Folder>)descPage.getItems());
		}
	}

	@Test
	public void testGetFolderFoldersPageWithSortString() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long folderId = testGetFolderFoldersPage_getFolderId();

		Folder folder1 = randomFolder();
		Folder folder2 = randomFolder();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(folder1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(folder2, entityField.getName(), "Bbb");
		}

		folder1 = testGetFolderFoldersPage_addFolder(folderId, folder1);

		folder2 = testGetFolderFoldersPage_addFolder(folderId, folder2);

		for (EntityField entityField : entityFields) {
			Page<Folder> ascPage = invokeGetFolderFoldersPage(
				folderId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(folder1, folder2),
				(List<Folder>)ascPage.getItems());

			Page<Folder> descPage = invokeGetFolderFoldersPage(
				folderId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(folder2, folder1),
				(List<Folder>)descPage.getItems());
		}
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

	protected Long testGetFolderFoldersPage_getIrrelevantFolderId()
		throws Exception {

		return null;
	}

	protected Page<Folder> invokeGetFolderFoldersPage(
			Long folderId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/folders/{folder-id}/folders", folderId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return _outputObjectMapper.readValue(
			string,
			new TypeReference<Page<Folder>>() {
			});
	}

	protected Http.Response invokeGetFolderFoldersPageResponse(
			Long folderId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/folders/{folder-id}/folders", folderId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostFolderFolder() throws Exception {
		Folder randomFolder = randomFolder();

		Folder postFolder = testPostFolderFolder_addFolder(randomFolder);

		assertEquals(randomFolder, postFolder);
		assertValid(postFolder);
	}

	protected Folder testPostFolderFolder_addFolder(Folder folder)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Folder invokePostFolderFolder(Long folderId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/folders/{folder-id}/folders", folderId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return _outputObjectMapper.readValue(string, Folder.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostFolderFolderResponse(
			Long folderId, Folder folder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			_inputObjectMapper.writeValueAsString(folder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/folders/{folder-id}/folders", folderId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
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

	protected void assertValid(Folder folder) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<Folder> page) {
		boolean valid = false;

		Collection<Folder> folders = page.getItems();

		int size = folders.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
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

		if (entityFieldName.equals("creator")) {
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

		if (entityFieldName.equals("numberOfDocuments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfFolders")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("viewableBy")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Folder randomFolder() {
		return new Folder() {
			{
				contentSpaceId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
			}
		};
	}

	protected Folder randomIrrelevantFolder() {
		return randomFolder();
	}

	protected Folder randomPatchFolder() {
		return randomFolder();
	}

	protected Group irrelevantGroup;
	protected Group testGroup;

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getLastPage() {
			return lastPage;
		}

		public long getPage() {
			return page;
		}

		public long getPageSize() {
			return pageSize;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty
		protected long lastPage;

		@JsonProperty
		protected long page;

		@JsonProperty
		protected long pageSize;

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

	private String _toPath(String template, Object... values) {
		if (ArrayUtil.isEmpty(values)) {
			return template;
		}

		for (int i = 0; i < values.length; i++) {
			template = template.replaceFirst(
				"\\{.*?\\}", String.valueOf(values[i]));
		}

		return template;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFolderResourceTestCase.class);

	private static BeanUtilsBean _beanUtilsBean = new BeanUtilsBean() {

		@Override
		public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {

			if (value != null) {
				super.copyProperty(bean, name, value);
			}
		}

	};
	private static DateFormat _dateFormat;
	private final static ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private final static ObjectMapper _outputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
		}
	};

	@Inject
	private FolderResource _folderResource;

	private URL _resourceURL;

}