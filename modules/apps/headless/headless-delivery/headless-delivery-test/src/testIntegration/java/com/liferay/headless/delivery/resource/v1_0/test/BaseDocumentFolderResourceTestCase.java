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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.delivery.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.resource.v1_0.DocumentFolderResource;
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
public abstract class BaseDocumentFolderResourceTestCase {

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
			"http://localhost:8080/o/headless-delivery/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetContentSpaceDocumentFoldersPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceDocumentFoldersPage_getContentSpaceId();
		Long irrelevantContentSpaceId =
			testGetContentSpaceDocumentFoldersPage_getIrrelevantContentSpaceId();

		if ((irrelevantContentSpaceId != null)) {
			DocumentFolder irrelevantDocumentFolder =
				testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
					irrelevantContentSpaceId, randomIrrelevantDocumentFolder());

			Page<DocumentFolder> page =
				invokeGetContentSpaceDocumentFoldersPage(
					irrelevantContentSpaceId, null, null, null,
					Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDocumentFolder),
				(List<DocumentFolder>)page.getItems());
			assertValid(page);
		}

		DocumentFolder documentFolder1 =
			testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
				contentSpaceId, randomDocumentFolder());

		DocumentFolder documentFolder2 =
			testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
				contentSpaceId, randomDocumentFolder());

		Page<DocumentFolder> page = invokeGetContentSpaceDocumentFoldersPage(
			contentSpaceId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(documentFolder1, documentFolder2),
			(List<DocumentFolder>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceDocumentFoldersPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDocumentFoldersPage_getContentSpaceId();

		DocumentFolder documentFolder1 = randomDocumentFolder();
		DocumentFolder documentFolder2 = randomDocumentFolder();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				documentFolder1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		documentFolder1 =
			testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
				contentSpaceId, documentFolder1);

		Thread.sleep(1000);

		documentFolder2 =
			testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
				contentSpaceId, documentFolder2);

		for (EntityField entityField : entityFields) {
			Page<DocumentFolder> page =
				invokeGetContentSpaceDocumentFoldersPage(
					contentSpaceId, null, null,
					getFilterString(entityField, "eq", documentFolder1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(documentFolder1),
				(List<DocumentFolder>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceDocumentFoldersPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDocumentFoldersPage_getContentSpaceId();

		DocumentFolder documentFolder1 =
			testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
				contentSpaceId, randomDocumentFolder());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DocumentFolder documentFolder2 =
			testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
				contentSpaceId, randomDocumentFolder());

		for (EntityField entityField : entityFields) {
			Page<DocumentFolder> page =
				invokeGetContentSpaceDocumentFoldersPage(
					contentSpaceId, null, null,
					getFilterString(entityField, "eq", documentFolder1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(documentFolder1),
				(List<DocumentFolder>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceDocumentFoldersPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceDocumentFoldersPage_getContentSpaceId();

		DocumentFolder documentFolder1 =
			testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
				contentSpaceId, randomDocumentFolder());

		DocumentFolder documentFolder2 =
			testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
				contentSpaceId, randomDocumentFolder());

		DocumentFolder documentFolder3 =
			testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
				contentSpaceId, randomDocumentFolder());

		Page<DocumentFolder> page1 = invokeGetContentSpaceDocumentFoldersPage(
			contentSpaceId, null, null, null, Pagination.of(1, 2), null);

		List<DocumentFolder> documentFolders1 =
			(List<DocumentFolder>)page1.getItems();

		Assert.assertEquals(
			documentFolders1.toString(), 2, documentFolders1.size());

		Page<DocumentFolder> page2 = invokeGetContentSpaceDocumentFoldersPage(
			contentSpaceId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DocumentFolder> documentFolders2 =
			(List<DocumentFolder>)page2.getItems();

		Assert.assertEquals(
			documentFolders2.toString(), 1, documentFolders2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(documentFolder1, documentFolder2, documentFolder3),
			new ArrayList<DocumentFolder>() {
				{
					addAll(documentFolders1);
					addAll(documentFolders2);
				}
			});
	}

	@Test
	public void testGetContentSpaceDocumentFoldersPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDocumentFoldersPage_getContentSpaceId();

		DocumentFolder documentFolder1 = randomDocumentFolder();
		DocumentFolder documentFolder2 = randomDocumentFolder();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				documentFolder1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		documentFolder1 =
			testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
				contentSpaceId, documentFolder1);

		Thread.sleep(1000);

		documentFolder2 =
			testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
				contentSpaceId, documentFolder2);

		for (EntityField entityField : entityFields) {
			Page<DocumentFolder> ascPage =
				invokeGetContentSpaceDocumentFoldersPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(documentFolder1, documentFolder2),
				(List<DocumentFolder>)ascPage.getItems());

			Page<DocumentFolder> descPage =
				invokeGetContentSpaceDocumentFoldersPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(documentFolder2, documentFolder1),
				(List<DocumentFolder>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentSpaceDocumentFoldersPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDocumentFoldersPage_getContentSpaceId();

		DocumentFolder documentFolder1 = randomDocumentFolder();
		DocumentFolder documentFolder2 = randomDocumentFolder();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				documentFolder1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				documentFolder2, entityField.getName(), "Bbb");
		}

		documentFolder1 =
			testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
				contentSpaceId, documentFolder1);

		documentFolder2 =
			testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
				contentSpaceId, documentFolder2);

		for (EntityField entityField : entityFields) {
			Page<DocumentFolder> ascPage =
				invokeGetContentSpaceDocumentFoldersPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(documentFolder1, documentFolder2),
				(List<DocumentFolder>)ascPage.getItems());

			Page<DocumentFolder> descPage =
				invokeGetContentSpaceDocumentFoldersPage(
					contentSpaceId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(documentFolder2, documentFolder1),
				(List<DocumentFolder>)descPage.getItems());
		}
	}

	protected DocumentFolder
			testGetContentSpaceDocumentFoldersPage_addDocumentFolder(
				Long contentSpaceId, DocumentFolder documentFolder)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentSpaceDocumentFoldersPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetContentSpaceDocumentFoldersPage_getIrrelevantContentSpaceId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<DocumentFolder> invokeGetContentSpaceDocumentFoldersPage(
			Long contentSpaceId, Boolean flatten, String search,
			String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/document-folders",
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

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<DocumentFolder>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceDocumentFoldersPageResponse(
			Long contentSpaceId, Boolean flatten, String search,
			String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/document-folders",
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
	public void testPostContentSpaceDocumentFolder() throws Exception {
		DocumentFolder randomDocumentFolder = randomDocumentFolder();

		DocumentFolder postDocumentFolder =
			testPostContentSpaceDocumentFolder_addDocumentFolder(
				randomDocumentFolder);

		assertEquals(randomDocumentFolder, postDocumentFolder);
		assertValid(postDocumentFolder);
	}

	protected DocumentFolder
			testPostContentSpaceDocumentFolder_addDocumentFolder(
				DocumentFolder documentFolder)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DocumentFolder invokePostContentSpaceDocumentFolder(
			Long contentSpaceId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(documentFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/document-folders",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, DocumentFolder.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostContentSpaceDocumentFolderResponse(
			Long contentSpaceId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(documentFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/document-folders",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteDocumentFolder() throws Exception {
		DocumentFolder documentFolder =
			testDeleteDocumentFolder_addDocumentFolder();

		assertResponseCode(
			204, invokeDeleteDocumentFolderResponse(documentFolder.getId()));

		assertResponseCode(
			404, invokeGetDocumentFolderResponse(documentFolder.getId()));
	}

	protected DocumentFolder testDeleteDocumentFolder_addDocumentFolder()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteDocumentFolder(Long documentFolderId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{documentFolderId}", documentFolderId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteDocumentFolderResponse(
			Long documentFolderId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{documentFolderId}", documentFolderId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetDocumentFolder() throws Exception {
		DocumentFolder postDocumentFolder =
			testGetDocumentFolder_addDocumentFolder();

		DocumentFolder getDocumentFolder = invokeGetDocumentFolder(
			postDocumentFolder.getId());

		assertEquals(postDocumentFolder, getDocumentFolder);
		assertValid(getDocumentFolder);
	}

	protected DocumentFolder testGetDocumentFolder_addDocumentFolder()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DocumentFolder invokeGetDocumentFolder(Long documentFolderId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{documentFolderId}", documentFolderId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, DocumentFolder.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetDocumentFolderResponse(
			Long documentFolderId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{documentFolderId}", documentFolderId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPatchDocumentFolder() throws Exception {
		DocumentFolder postDocumentFolder =
			testPatchDocumentFolder_addDocumentFolder();

		DocumentFolder randomPatchDocumentFolder = randomPatchDocumentFolder();

		DocumentFolder patchDocumentFolder = invokePatchDocumentFolder(
			postDocumentFolder.getId(), randomPatchDocumentFolder);

		DocumentFolder expectedPatchDocumentFolder =
			(DocumentFolder)BeanUtils.cloneBean(postDocumentFolder);

		_beanUtilsBean.copyProperties(
			expectedPatchDocumentFolder, randomPatchDocumentFolder);

		DocumentFolder getDocumentFolder = invokeGetDocumentFolder(
			patchDocumentFolder.getId());

		assertEquals(expectedPatchDocumentFolder, getDocumentFolder);
		assertValid(getDocumentFolder);
	}

	protected DocumentFolder testPatchDocumentFolder_addDocumentFolder()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DocumentFolder invokePatchDocumentFolder(
			Long documentFolderId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(documentFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{documentFolderId}", documentFolderId);

		options.setLocation(location);

		options.setPatch(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, DocumentFolder.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePatchDocumentFolderResponse(
			Long documentFolderId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(documentFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{documentFolderId}", documentFolderId);

		options.setLocation(location);

		options.setPatch(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutDocumentFolder() throws Exception {
		DocumentFolder postDocumentFolder =
			testPutDocumentFolder_addDocumentFolder();

		DocumentFolder randomDocumentFolder = randomDocumentFolder();

		DocumentFolder putDocumentFolder = invokePutDocumentFolder(
			postDocumentFolder.getId(), randomDocumentFolder);

		assertEquals(randomDocumentFolder, putDocumentFolder);
		assertValid(putDocumentFolder);

		DocumentFolder getDocumentFolder = invokeGetDocumentFolder(
			putDocumentFolder.getId());

		assertEquals(randomDocumentFolder, getDocumentFolder);
		assertValid(getDocumentFolder);
	}

	protected DocumentFolder testPutDocumentFolder_addDocumentFolder()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DocumentFolder invokePutDocumentFolder(
			Long documentFolderId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(documentFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{documentFolderId}", documentFolderId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, DocumentFolder.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutDocumentFolderResponse(
			Long documentFolderId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(documentFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{documentFolderId}", documentFolderId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetDocumentFolderDocumentFoldersPage() throws Exception {
		Long parentDocumentFolderId =
			testGetDocumentFolderDocumentFoldersPage_getParentDocumentFolderId();
		Long irrelevantParentDocumentFolderId =
			testGetDocumentFolderDocumentFoldersPage_getIrrelevantParentDocumentFolderId();

		if ((irrelevantParentDocumentFolderId != null)) {
			DocumentFolder irrelevantDocumentFolder =
				testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
					irrelevantParentDocumentFolderId,
					randomIrrelevantDocumentFolder());

			Page<DocumentFolder> page =
				invokeGetDocumentFolderDocumentFoldersPage(
					irrelevantParentDocumentFolderId, null, null,
					Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDocumentFolder),
				(List<DocumentFolder>)page.getItems());
			assertValid(page);
		}

		DocumentFolder documentFolder1 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, randomDocumentFolder());

		DocumentFolder documentFolder2 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, randomDocumentFolder());

		Page<DocumentFolder> page = invokeGetDocumentFolderDocumentFoldersPage(
			parentDocumentFolderId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(documentFolder1, documentFolder2),
			(List<DocumentFolder>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetDocumentFolderDocumentFoldersPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentDocumentFolderId =
			testGetDocumentFolderDocumentFoldersPage_getParentDocumentFolderId();

		DocumentFolder documentFolder1 = randomDocumentFolder();
		DocumentFolder documentFolder2 = randomDocumentFolder();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				documentFolder1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		documentFolder1 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, documentFolder1);

		Thread.sleep(1000);

		documentFolder2 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, documentFolder2);

		for (EntityField entityField : entityFields) {
			Page<DocumentFolder> page =
				invokeGetDocumentFolderDocumentFoldersPage(
					parentDocumentFolderId, null,
					getFilterString(entityField, "eq", documentFolder1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(documentFolder1),
				(List<DocumentFolder>)page.getItems());
		}
	}

	@Test
	public void testGetDocumentFolderDocumentFoldersPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentDocumentFolderId =
			testGetDocumentFolderDocumentFoldersPage_getParentDocumentFolderId();

		DocumentFolder documentFolder1 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, randomDocumentFolder());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DocumentFolder documentFolder2 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, randomDocumentFolder());

		for (EntityField entityField : entityFields) {
			Page<DocumentFolder> page =
				invokeGetDocumentFolderDocumentFoldersPage(
					parentDocumentFolderId, null,
					getFilterString(entityField, "eq", documentFolder1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(documentFolder1),
				(List<DocumentFolder>)page.getItems());
		}
	}

	@Test
	public void testGetDocumentFolderDocumentFoldersPageWithPagination()
		throws Exception {

		Long parentDocumentFolderId =
			testGetDocumentFolderDocumentFoldersPage_getParentDocumentFolderId();

		DocumentFolder documentFolder1 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, randomDocumentFolder());

		DocumentFolder documentFolder2 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, randomDocumentFolder());

		DocumentFolder documentFolder3 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, randomDocumentFolder());

		Page<DocumentFolder> page1 = invokeGetDocumentFolderDocumentFoldersPage(
			parentDocumentFolderId, null, null, Pagination.of(1, 2), null);

		List<DocumentFolder> documentFolders1 =
			(List<DocumentFolder>)page1.getItems();

		Assert.assertEquals(
			documentFolders1.toString(), 2, documentFolders1.size());

		Page<DocumentFolder> page2 = invokeGetDocumentFolderDocumentFoldersPage(
			parentDocumentFolderId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DocumentFolder> documentFolders2 =
			(List<DocumentFolder>)page2.getItems();

		Assert.assertEquals(
			documentFolders2.toString(), 1, documentFolders2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(documentFolder1, documentFolder2, documentFolder3),
			new ArrayList<DocumentFolder>() {
				{
					addAll(documentFolders1);
					addAll(documentFolders2);
				}
			});
	}

	@Test
	public void testGetDocumentFolderDocumentFoldersPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentDocumentFolderId =
			testGetDocumentFolderDocumentFoldersPage_getParentDocumentFolderId();

		DocumentFolder documentFolder1 = randomDocumentFolder();
		DocumentFolder documentFolder2 = randomDocumentFolder();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				documentFolder1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		documentFolder1 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, documentFolder1);

		Thread.sleep(1000);

		documentFolder2 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, documentFolder2);

		for (EntityField entityField : entityFields) {
			Page<DocumentFolder> ascPage =
				invokeGetDocumentFolderDocumentFoldersPage(
					parentDocumentFolderId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(documentFolder1, documentFolder2),
				(List<DocumentFolder>)ascPage.getItems());

			Page<DocumentFolder> descPage =
				invokeGetDocumentFolderDocumentFoldersPage(
					parentDocumentFolderId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(documentFolder2, documentFolder1),
				(List<DocumentFolder>)descPage.getItems());
		}
	}

	@Test
	public void testGetDocumentFolderDocumentFoldersPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentDocumentFolderId =
			testGetDocumentFolderDocumentFoldersPage_getParentDocumentFolderId();

		DocumentFolder documentFolder1 = randomDocumentFolder();
		DocumentFolder documentFolder2 = randomDocumentFolder();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				documentFolder1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				documentFolder2, entityField.getName(), "Bbb");
		}

		documentFolder1 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, documentFolder1);

		documentFolder2 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, documentFolder2);

		for (EntityField entityField : entityFields) {
			Page<DocumentFolder> ascPage =
				invokeGetDocumentFolderDocumentFoldersPage(
					parentDocumentFolderId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(documentFolder1, documentFolder2),
				(List<DocumentFolder>)ascPage.getItems());

			Page<DocumentFolder> descPage =
				invokeGetDocumentFolderDocumentFoldersPage(
					parentDocumentFolderId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(documentFolder2, documentFolder1),
				(List<DocumentFolder>)descPage.getItems());
		}
	}

	protected DocumentFolder
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				Long parentDocumentFolderId, DocumentFolder documentFolder)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDocumentFolderDocumentFoldersPage_getParentDocumentFolderId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDocumentFolderDocumentFoldersPage_getIrrelevantParentDocumentFolderId()
		throws Exception {

		return null;
	}

	protected Page<DocumentFolder> invokeGetDocumentFolderDocumentFoldersPage(
			Long parentDocumentFolderId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{parentDocumentFolderId}/document-folders",
					parentDocumentFolderId);

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

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<DocumentFolder>>() {
			});
	}

	protected Http.Response invokeGetDocumentFolderDocumentFoldersPageResponse(
			Long parentDocumentFolderId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{parentDocumentFolderId}/document-folders",
					parentDocumentFolderId);

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
	public void testPostDocumentFolderDocumentFolder() throws Exception {
		DocumentFolder randomDocumentFolder = randomDocumentFolder();

		DocumentFolder postDocumentFolder =
			testPostDocumentFolderDocumentFolder_addDocumentFolder(
				randomDocumentFolder);

		assertEquals(randomDocumentFolder, postDocumentFolder);
		assertValid(postDocumentFolder);
	}

	protected DocumentFolder
			testPostDocumentFolderDocumentFolder_addDocumentFolder(
				DocumentFolder documentFolder)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected DocumentFolder invokePostDocumentFolderDocumentFolder(
			Long parentDocumentFolderId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(documentFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{parentDocumentFolderId}/document-folders",
					parentDocumentFolderId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, DocumentFolder.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostDocumentFolderDocumentFolderResponse(
			Long parentDocumentFolderId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			inputObjectMapper.writeValueAsString(documentFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{parentDocumentFolderId}/document-folders",
					parentDocumentFolderId);

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

	protected void assertEquals(
		DocumentFolder documentFolder1, DocumentFolder documentFolder2) {

		Assert.assertTrue(
			documentFolder1 + " does not equal " + documentFolder2,
			equals(documentFolder1, documentFolder2));
	}

	protected void assertEquals(
		List<DocumentFolder> documentFolders1,
		List<DocumentFolder> documentFolders2) {

		Assert.assertEquals(documentFolders1.size(), documentFolders2.size());

		for (int i = 0; i < documentFolders1.size(); i++) {
			DocumentFolder documentFolder1 = documentFolders1.get(i);
			DocumentFolder documentFolder2 = documentFolders2.get(i);

			assertEquals(documentFolder1, documentFolder2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DocumentFolder> documentFolders1,
		List<DocumentFolder> documentFolders2) {

		Assert.assertEquals(documentFolders1.size(), documentFolders2.size());

		for (DocumentFolder documentFolder1 : documentFolders1) {
			boolean contains = false;

			for (DocumentFolder documentFolder2 : documentFolders2) {
				if (equals(documentFolder1, documentFolder2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				documentFolders2 + " does not contain " + documentFolder1,
				contains);
		}
	}

	protected void assertValid(DocumentFolder documentFolder) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<DocumentFolder> page) {
		boolean valid = false;

		Collection<DocumentFolder> documentFolders = page.getItems();

		int size = documentFolders.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(
		DocumentFolder documentFolder1, DocumentFolder documentFolder2) {

		if (documentFolder1 == documentFolder2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_documentFolderResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_documentFolderResource;

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
		EntityField entityField, String operator,
		DocumentFolder documentFolder) {

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
			sb.append(_dateFormat.format(documentFolder.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(documentFolder.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(documentFolder.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(documentFolder.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("numberOfDocumentFolders")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfDocuments")) {
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

	protected DocumentFolder randomDocumentFolder() {
		return new DocumentFolder() {
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

	protected DocumentFolder randomIrrelevantDocumentFolder() {
		return randomDocumentFolder();
	}

	protected DocumentFolder randomPatchDocumentFolder() {
		return randomDocumentFolder();
	}

	protected static final ObjectMapper inputObjectMapper = new ObjectMapper() {
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
	protected static final ObjectMapper outputObjectMapper =
		new ObjectMapper() {
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

	protected String contentType = "application/json";
	protected Group irrelevantGroup;
	protected Group testGroup;
	protected String userNameAndPassword = "test@liferay.com:test";

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

		String encodedUserNameAndPassword = Base64.encode(
			userNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedUserNameAndPassword);

		options.addHeader("Content-Type", contentType);

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
		BaseDocumentFolderResourceTestCase.class);

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

	@Inject
	private DocumentFolderResource _documentFolderResource;

	private URL _resourceURL;

}