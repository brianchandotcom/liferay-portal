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
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.serdes.v1_0.DocumentFolderSerDes;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
		testLocale = LocaleUtil.getDefault();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-delivery/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testClientSerDesToDTO() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		DocumentFolder documentFolder1 = randomDocumentFolder();

		String json = objectMapper.writeValueAsString(documentFolder1);

		DocumentFolder documentFolder2 = DocumentFolderSerDes.toDTO(json);

		Assert.assertTrue(equals(documentFolder1, documentFolder2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				setDateFormat(new ISO8601DateFormat());
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
			}
		};

		DocumentFolder documentFolder = randomDocumentFolder();

		String json1 = objectMapper.writeValueAsString(documentFolder);
		String json2 = DocumentFolderSerDes.toJSON(documentFolder);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
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

		return invokePostSiteDocumentFolder(
			testGroup.getGroupId(), randomDocumentFolder());
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

		return invokePostSiteDocumentFolder(
			testGroup.getGroupId(), randomDocumentFolder());
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
			return DocumentFolderSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

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

		return invokePostSiteDocumentFolder(
			testGroup.getGroupId(), randomDocumentFolder());
	}

	protected DocumentFolder invokePatchDocumentFolder(
			Long documentFolderId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DocumentFolderSerDes.toJSON(documentFolder),
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
			return DocumentFolderSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePatchDocumentFolderResponse(
			Long documentFolderId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DocumentFolderSerDes.toJSON(documentFolder),
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

		return invokePostSiteDocumentFolder(
			testGroup.getGroupId(), randomDocumentFolder());
	}

	protected DocumentFolder invokePutDocumentFolder(
			Long documentFolderId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DocumentFolderSerDes.toJSON(documentFolder),
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
			return DocumentFolderSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePutDocumentFolderResponse(
			Long documentFolderId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DocumentFolderSerDes.toJSON(documentFolder),
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

		documentFolder1 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, documentFolder1);

		for (EntityField entityField : entityFields) {
			Page<DocumentFolder> page =
				invokeGetDocumentFolderDocumentFoldersPage(
					parentDocumentFolderId, null,
					getFilterString(entityField, "between", documentFolder1),
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

		return invokePostDocumentFolderDocumentFolder(
			parentDocumentFolderId, documentFolder);
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

		return Page.of(string, DocumentFolderSerDes::toDTO);
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

		return invokePostDocumentFolderDocumentFolder(
			testGetDocumentFolderDocumentFoldersPage_getParentDocumentFolderId(),
			documentFolder);
	}

	protected DocumentFolder invokePostDocumentFolderDocumentFolder(
			Long parentDocumentFolderId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DocumentFolderSerDes.toJSON(documentFolder),
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
			return DocumentFolderSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePostDocumentFolderDocumentFolderResponse(
			Long parentDocumentFolderId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DocumentFolderSerDes.toJSON(documentFolder),
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

	@Test
	public void testGetSiteDocumentFoldersPage() throws Exception {
		Long siteId = testGetSiteDocumentFoldersPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteDocumentFoldersPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			DocumentFolder irrelevantDocumentFolder =
				testGetSiteDocumentFoldersPage_addDocumentFolder(
					irrelevantSiteId, randomIrrelevantDocumentFolder());

			Page<DocumentFolder> page = invokeGetSiteDocumentFoldersPage(
				irrelevantSiteId, null, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDocumentFolder),
				(List<DocumentFolder>)page.getItems());
			assertValid(page);
		}

		DocumentFolder documentFolder1 =
			testGetSiteDocumentFoldersPage_addDocumentFolder(
				siteId, randomDocumentFolder());

		DocumentFolder documentFolder2 =
			testGetSiteDocumentFoldersPage_addDocumentFolder(
				siteId, randomDocumentFolder());

		Page<DocumentFolder> page = invokeGetSiteDocumentFoldersPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(documentFolder1, documentFolder2),
			(List<DocumentFolder>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteDocumentFoldersPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteDocumentFoldersPage_getSiteId();

		DocumentFolder documentFolder1 = randomDocumentFolder();

		documentFolder1 = testGetSiteDocumentFoldersPage_addDocumentFolder(
			siteId, documentFolder1);

		for (EntityField entityField : entityFields) {
			Page<DocumentFolder> page = invokeGetSiteDocumentFoldersPage(
				siteId, null, null,
				getFilterString(entityField, "between", documentFolder1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(documentFolder1),
				(List<DocumentFolder>)page.getItems());
		}
	}

	@Test
	public void testGetSiteDocumentFoldersPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteDocumentFoldersPage_getSiteId();

		DocumentFolder documentFolder1 =
			testGetSiteDocumentFoldersPage_addDocumentFolder(
				siteId, randomDocumentFolder());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DocumentFolder documentFolder2 =
			testGetSiteDocumentFoldersPage_addDocumentFolder(
				siteId, randomDocumentFolder());

		for (EntityField entityField : entityFields) {
			Page<DocumentFolder> page = invokeGetSiteDocumentFoldersPage(
				siteId, null, null,
				getFilterString(entityField, "eq", documentFolder1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(documentFolder1),
				(List<DocumentFolder>)page.getItems());
		}
	}

	@Test
	public void testGetSiteDocumentFoldersPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteDocumentFoldersPage_getSiteId();

		DocumentFolder documentFolder1 =
			testGetSiteDocumentFoldersPage_addDocumentFolder(
				siteId, randomDocumentFolder());

		DocumentFolder documentFolder2 =
			testGetSiteDocumentFoldersPage_addDocumentFolder(
				siteId, randomDocumentFolder());

		DocumentFolder documentFolder3 =
			testGetSiteDocumentFoldersPage_addDocumentFolder(
				siteId, randomDocumentFolder());

		Page<DocumentFolder> page1 = invokeGetSiteDocumentFoldersPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		List<DocumentFolder> documentFolders1 =
			(List<DocumentFolder>)page1.getItems();

		Assert.assertEquals(
			documentFolders1.toString(), 2, documentFolders1.size());

		Page<DocumentFolder> page2 = invokeGetSiteDocumentFoldersPage(
			siteId, null, null, null, Pagination.of(2, 2), null);

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
	public void testGetSiteDocumentFoldersPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteDocumentFoldersPage_getSiteId();

		DocumentFolder documentFolder1 = randomDocumentFolder();
		DocumentFolder documentFolder2 = randomDocumentFolder();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				documentFolder1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		documentFolder1 = testGetSiteDocumentFoldersPage_addDocumentFolder(
			siteId, documentFolder1);

		documentFolder2 = testGetSiteDocumentFoldersPage_addDocumentFolder(
			siteId, documentFolder2);

		for (EntityField entityField : entityFields) {
			Page<DocumentFolder> ascPage = invokeGetSiteDocumentFoldersPage(
				siteId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(documentFolder1, documentFolder2),
				(List<DocumentFolder>)ascPage.getItems());

			Page<DocumentFolder> descPage = invokeGetSiteDocumentFoldersPage(
				siteId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(documentFolder2, documentFolder1),
				(List<DocumentFolder>)descPage.getItems());
		}
	}

	@Test
	public void testGetSiteDocumentFoldersPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteDocumentFoldersPage_getSiteId();

		DocumentFolder documentFolder1 = randomDocumentFolder();
		DocumentFolder documentFolder2 = randomDocumentFolder();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				documentFolder1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(
				documentFolder2, entityField.getName(), "Bbb");
		}

		documentFolder1 = testGetSiteDocumentFoldersPage_addDocumentFolder(
			siteId, documentFolder1);

		documentFolder2 = testGetSiteDocumentFoldersPage_addDocumentFolder(
			siteId, documentFolder2);

		for (EntityField entityField : entityFields) {
			Page<DocumentFolder> ascPage = invokeGetSiteDocumentFoldersPage(
				siteId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(documentFolder1, documentFolder2),
				(List<DocumentFolder>)ascPage.getItems());

			Page<DocumentFolder> descPage = invokeGetSiteDocumentFoldersPage(
				siteId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(documentFolder2, documentFolder1),
				(List<DocumentFolder>)descPage.getItems());
		}
	}

	protected DocumentFolder testGetSiteDocumentFoldersPage_addDocumentFolder(
			Long siteId, DocumentFolder documentFolder)
		throws Exception {

		return invokePostSiteDocumentFolder(siteId, documentFolder);
	}

	protected Long testGetSiteDocumentFoldersPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteDocumentFoldersPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<DocumentFolder> invokeGetSiteDocumentFoldersPage(
			Long siteId, Boolean flatten, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/sites/{siteId}/document-folders", siteId);

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

		return Page.of(string, DocumentFolderSerDes::toDTO);
	}

	protected Http.Response invokeGetSiteDocumentFoldersPageResponse(
			Long siteId, Boolean flatten, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/sites/{siteId}/document-folders", siteId);

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
	public void testPostSiteDocumentFolder() throws Exception {
		DocumentFolder randomDocumentFolder = randomDocumentFolder();

		DocumentFolder postDocumentFolder =
			testPostSiteDocumentFolder_addDocumentFolder(randomDocumentFolder);

		assertEquals(randomDocumentFolder, postDocumentFolder);
		assertValid(postDocumentFolder);
	}

	protected DocumentFolder testPostSiteDocumentFolder_addDocumentFolder(
			DocumentFolder documentFolder)
		throws Exception {

		return invokePostSiteDocumentFolder(
			testGetSiteDocumentFoldersPage_getSiteId(), documentFolder);
	}

	protected DocumentFolder invokePostSiteDocumentFolder(
			Long siteId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DocumentFolderSerDes.toJSON(documentFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/sites/{siteId}/document-folders", siteId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return DocumentFolderSerDes.toDTO(string);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to process HTTP response: " + string, e);
			}

			throw e;
		}
	}

	protected Http.Response invokePostSiteDocumentFolderResponse(
			Long siteId, DocumentFolder documentFolder)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setBody(
			DocumentFolderSerDes.toJSON(documentFolder),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);

		String location =
			_resourceURL + _toPath("/sites/{siteId}/document-folders", siteId);

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
		boolean valid = true;

		if (documentFolder.getDateCreated() == null) {
			valid = false;
		}

		if (documentFolder.getDateModified() == null) {
			valid = false;
		}

		if (documentFolder.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				documentFolder.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (documentFolder.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (documentFolder.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (documentFolder.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfDocumentFolders", additionalAssertFieldName)) {

				if (documentFolder.getNumberOfDocumentFolders() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfDocuments", additionalAssertFieldName)) {

				if (documentFolder.getNumberOfDocuments() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (documentFolder.getViewableBy() == null) {
					valid = false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		Assert.assertTrue(valid);
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

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected boolean equals(
		DocumentFolder documentFolder1, DocumentFolder documentFolder2) {

		if (documentFolder1 == documentFolder2) {
			return true;
		}

		if (!Objects.equals(
				documentFolder1.getSiteId(), documentFolder2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						documentFolder1.getCreator(),
						documentFolder2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						documentFolder1.getDateCreated(),
						documentFolder2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						documentFolder1.getDateModified(),
						documentFolder2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						documentFolder1.getDescription(),
						documentFolder2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						documentFolder1.getId(), documentFolder2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						documentFolder1.getName(), documentFolder2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfDocumentFolders", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						documentFolder1.getNumberOfDocumentFolders(),
						documentFolder2.getNumberOfDocumentFolders())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfDocuments", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						documentFolder1.getNumberOfDocuments(),
						documentFolder2.getNumberOfDocuments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						documentFolder1.getViewableBy(),
						documentFolder2.getViewableBy())) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid additional assert field name " +
					additionalAssertFieldName);
		}

		return true;
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

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							documentFolder.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							documentFolder.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(documentFolder.getDateCreated()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							documentFolder.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							documentFolder.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(documentFolder.getDateModified()));
			}

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

		if (entityFieldName.equals("siteId")) {
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
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected DocumentFolder randomIrrelevantDocumentFolder() {
		DocumentFolder randomIrrelevantDocumentFolder = randomDocumentFolder();

		randomIrrelevantDocumentFolder.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantDocumentFolder;
	}

	protected DocumentFolder randomPatchDocumentFolder() {
		return randomDocumentFolder();
	}

	protected Group irrelevantGroup;
	protected String testContentType = "application/json";
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");
		options.addHeader(
			"Accept-Language", LocaleUtil.toW3cLanguageId(testLocale));

		String encodedTestUserNameAndPassword = Base64.encode(
			testUserNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedTestUserNameAndPassword);

		options.addHeader("Content-Type", testContentType);

		return options;
	}

	private String _toJSON(Map<String, String> map) {
		if (map == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		Set<Map.Entry<String, String>> set = map.entrySet();

		Iterator<Map.Entry<String, String>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = iterator.next();

			sb.append("\"" + entry.getKey() + "\": ");

			if (entry.getValue() == null) {
				sb.append("null");
			}
			else {
				sb.append("\"" + entry.getValue() + "\"");
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
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