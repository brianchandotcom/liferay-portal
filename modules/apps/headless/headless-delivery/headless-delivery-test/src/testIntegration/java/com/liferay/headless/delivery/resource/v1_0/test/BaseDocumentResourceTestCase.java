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

import com.liferay.headless.delivery.dto.v1_0.Document;
import com.liferay.headless.delivery.dto.v1_0.Rating;
import com.liferay.headless.delivery.resource.v1_0.DocumentResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;
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
public abstract class BaseDocumentResourceTestCase {

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
	public void testGetContentSpaceDocumentsPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceDocumentsPage_getContentSpaceId();
		Long irrelevantContentSpaceId =
			testGetContentSpaceDocumentsPage_getIrrelevantContentSpaceId();

		if ((irrelevantContentSpaceId != null)) {
			Document irrelevantDocument =
				testGetContentSpaceDocumentsPage_addDocument(
					irrelevantContentSpaceId, randomIrrelevantDocument());

			Page<Document> page = invokeGetContentSpaceDocumentsPage(
				irrelevantContentSpaceId, null, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDocument),
				(List<Document>)page.getItems());
			assertValid(page);
		}

		Document document1 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, randomDocument());

		Document document2 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, randomDocument());

		Page<Document> page = invokeGetContentSpaceDocumentsPage(
			contentSpaceId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(document1, document2),
			(List<Document>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetContentSpaceDocumentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDocumentsPage_getContentSpaceId();

		Document document1 = randomDocument();
		Document document2 = randomDocument();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				document1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		document1 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, document1);

		Thread.sleep(1000);

		document2 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, document2);

		for (EntityField entityField : entityFields) {
			Page<Document> page = invokeGetContentSpaceDocumentsPage(
				contentSpaceId, null, null,
				getFilterString(entityField, "eq", document1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(document1),
				(List<Document>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceDocumentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDocumentsPage_getContentSpaceId();

		Document document1 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, randomDocument());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Document document2 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, randomDocument());

		for (EntityField entityField : entityFields) {
			Page<Document> page = invokeGetContentSpaceDocumentsPage(
				contentSpaceId, null, null,
				getFilterString(entityField, "eq", document1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(document1),
				(List<Document>)page.getItems());
		}
	}

	@Test
	public void testGetContentSpaceDocumentsPageWithPagination()
		throws Exception {

		Long contentSpaceId =
			testGetContentSpaceDocumentsPage_getContentSpaceId();

		Document document1 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, randomDocument());

		Document document2 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, randomDocument());

		Document document3 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, randomDocument());

		Page<Document> page1 = invokeGetContentSpaceDocumentsPage(
			contentSpaceId, null, null, null, Pagination.of(1, 2), null);

		List<Document> documents1 = (List<Document>)page1.getItems();

		Assert.assertEquals(documents1.toString(), 2, documents1.size());

		Page<Document> page2 = invokeGetContentSpaceDocumentsPage(
			contentSpaceId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Document> documents2 = (List<Document>)page2.getItems();

		Assert.assertEquals(documents2.toString(), 1, documents2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(document1, document2, document3),
			new ArrayList<Document>() {
				{
					addAll(documents1);
					addAll(documents2);
				}
			});
	}

	@Test
	public void testGetContentSpaceDocumentsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDocumentsPage_getContentSpaceId();

		Document document1 = randomDocument();
		Document document2 = randomDocument();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				document1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		document1 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, document1);

		Thread.sleep(1000);

		document2 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, document2);

		for (EntityField entityField : entityFields) {
			Page<Document> ascPage = invokeGetContentSpaceDocumentsPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(document1, document2),
				(List<Document>)ascPage.getItems());

			Page<Document> descPage = invokeGetContentSpaceDocumentsPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(document2, document1),
				(List<Document>)descPage.getItems());
		}
	}

	@Test
	public void testGetContentSpaceDocumentsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long contentSpaceId =
			testGetContentSpaceDocumentsPage_getContentSpaceId();

		Document document1 = randomDocument();
		Document document2 = randomDocument();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(document1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(document2, entityField.getName(), "Bbb");
		}

		document1 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, document1);

		document2 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, document2);

		for (EntityField entityField : entityFields) {
			Page<Document> ascPage = invokeGetContentSpaceDocumentsPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(document1, document2),
				(List<Document>)ascPage.getItems());

			Page<Document> descPage = invokeGetContentSpaceDocumentsPage(
				contentSpaceId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(document2, document1),
				(List<Document>)descPage.getItems());
		}
	}

	protected Document testGetContentSpaceDocumentsPage_addDocument(
			Long contentSpaceId, Document document)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetContentSpaceDocumentsPage_getContentSpaceId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetContentSpaceDocumentsPage_getIrrelevantContentSpaceId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected Page<Document> invokeGetContentSpaceDocumentsPage(
			Long contentSpaceId, Boolean flatten, String search,
			String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/documents",
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
			new TypeReference<Page<Document>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceDocumentsPageResponse(
			Long contentSpaceId, Boolean flatten, String search,
			String filterString, Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/documents",
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
	public void testPostContentSpaceDocument() throws Exception {
		Assert.assertTrue(true);
	}

	protected Document testPostContentSpaceDocument_addDocument(
			Document document)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Document invokePostContentSpaceDocument(
			Long contentSpaceId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.addPart(
			"document",
			inputObjectMapper.writeValueAsString(multipartBody.getValues()));

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		options.addFilePart(
			"file", binaryFile.getFileName(),
			FileUtil.getBytes(binaryFile.getInputStream()), contentType,
			"UTF-8");

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/documents",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Document.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostContentSpaceDocumentResponse(
			Long contentSpaceId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/content-spaces/{contentSpaceId}/documents",
					contentSpaceId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetDocumentFolderDocumentsPage() throws Exception {
		Long documentFolderId =
			testGetDocumentFolderDocumentsPage_getDocumentFolderId();
		Long irrelevantDocumentFolderId =
			testGetDocumentFolderDocumentsPage_getIrrelevantDocumentFolderId();

		if ((irrelevantDocumentFolderId != null)) {
			Document irrelevantDocument =
				testGetDocumentFolderDocumentsPage_addDocument(
					irrelevantDocumentFolderId, randomIrrelevantDocument());

			Page<Document> page = invokeGetDocumentFolderDocumentsPage(
				irrelevantDocumentFolderId, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDocument),
				(List<Document>)page.getItems());
			assertValid(page);
		}

		Document document1 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, randomDocument());

		Document document2 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, randomDocument());

		Page<Document> page = invokeGetDocumentFolderDocumentsPage(
			documentFolderId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(document1, document2),
			(List<Document>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetDocumentFolderDocumentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long documentFolderId =
			testGetDocumentFolderDocumentsPage_getDocumentFolderId();

		Document document1 = randomDocument();
		Document document2 = randomDocument();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				document1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		document1 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, document1);

		Thread.sleep(1000);

		document2 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, document2);

		for (EntityField entityField : entityFields) {
			Page<Document> page = invokeGetDocumentFolderDocumentsPage(
				documentFolderId, null,
				getFilterString(entityField, "eq", document1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(document1),
				(List<Document>)page.getItems());
		}
	}

	@Test
	public void testGetDocumentFolderDocumentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long documentFolderId =
			testGetDocumentFolderDocumentsPage_getDocumentFolderId();

		Document document1 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, randomDocument());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Document document2 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, randomDocument());

		for (EntityField entityField : entityFields) {
			Page<Document> page = invokeGetDocumentFolderDocumentsPage(
				documentFolderId, null,
				getFilterString(entityField, "eq", document1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(document1),
				(List<Document>)page.getItems());
		}
	}

	@Test
	public void testGetDocumentFolderDocumentsPageWithPagination()
		throws Exception {

		Long documentFolderId =
			testGetDocumentFolderDocumentsPage_getDocumentFolderId();

		Document document1 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, randomDocument());

		Document document2 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, randomDocument());

		Document document3 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, randomDocument());

		Page<Document> page1 = invokeGetDocumentFolderDocumentsPage(
			documentFolderId, null, null, Pagination.of(1, 2), null);

		List<Document> documents1 = (List<Document>)page1.getItems();

		Assert.assertEquals(documents1.toString(), 2, documents1.size());

		Page<Document> page2 = invokeGetDocumentFolderDocumentsPage(
			documentFolderId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Document> documents2 = (List<Document>)page2.getItems();

		Assert.assertEquals(documents2.toString(), 1, documents2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(document1, document2, document3),
			new ArrayList<Document>() {
				{
					addAll(documents1);
					addAll(documents2);
				}
			});
	}

	@Test
	public void testGetDocumentFolderDocumentsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long documentFolderId =
			testGetDocumentFolderDocumentsPage_getDocumentFolderId();

		Document document1 = randomDocument();
		Document document2 = randomDocument();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				document1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		document1 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, document1);

		Thread.sleep(1000);

		document2 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, document2);

		for (EntityField entityField : entityFields) {
			Page<Document> ascPage = invokeGetDocumentFolderDocumentsPage(
				documentFolderId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(document1, document2),
				(List<Document>)ascPage.getItems());

			Page<Document> descPage = invokeGetDocumentFolderDocumentsPage(
				documentFolderId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(document2, document1),
				(List<Document>)descPage.getItems());
		}
	}

	@Test
	public void testGetDocumentFolderDocumentsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long documentFolderId =
			testGetDocumentFolderDocumentsPage_getDocumentFolderId();

		Document document1 = randomDocument();
		Document document2 = randomDocument();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(document1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(document2, entityField.getName(), "Bbb");
		}

		document1 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, document1);

		document2 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, document2);

		for (EntityField entityField : entityFields) {
			Page<Document> ascPage = invokeGetDocumentFolderDocumentsPage(
				documentFolderId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(document1, document2),
				(List<Document>)ascPage.getItems());

			Page<Document> descPage = invokeGetDocumentFolderDocumentsPage(
				documentFolderId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(document2, document1),
				(List<Document>)descPage.getItems());
		}
	}

	protected Document testGetDocumentFolderDocumentsPage_addDocument(
			Long documentFolderId, Document document)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDocumentFolderDocumentsPage_getDocumentFolderId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetDocumentFolderDocumentsPage_getIrrelevantDocumentFolderId()
		throws Exception {

		return null;
	}

	protected Page<Document> invokeGetDocumentFolderDocumentsPage(
			Long documentFolderId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{documentFolderId}/documents",
					documentFolderId);

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
			new TypeReference<Page<Document>>() {
			});
	}

	protected Http.Response invokeGetDocumentFolderDocumentsPageResponse(
			Long documentFolderId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{documentFolderId}/documents",
					documentFolderId);

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
	public void testPostDocumentFolderDocument() throws Exception {
		Assert.assertTrue(true);
	}

	protected Document testPostDocumentFolderDocument_addDocument(
			Document document)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Document invokePostDocumentFolderDocument(
			Long documentFolderId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.addPart(
			"document",
			inputObjectMapper.writeValueAsString(multipartBody.getValues()));

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		options.addFilePart(
			"file", binaryFile.getFileName(),
			FileUtil.getBytes(binaryFile.getInputStream()), contentType,
			"UTF-8");

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{documentFolderId}/documents",
					documentFolderId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Document.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostDocumentFolderDocumentResponse(
			Long documentFolderId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/document-folders/{documentFolderId}/documents",
					documentFolderId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteDocument() throws Exception {
		Document document = testDeleteDocument_addDocument();

		assertResponseCode(204, invokeDeleteDocumentResponse(document.getId()));

		assertResponseCode(404, invokeGetDocumentResponse(document.getId()));
	}

	protected Document testDeleteDocument_addDocument() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteDocument(Long documentId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL + _toPath("/documents/{documentId}", documentId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteDocumentResponse(Long documentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL + _toPath("/documents/{documentId}", documentId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetDocument() throws Exception {
		Document postDocument = testGetDocument_addDocument();

		Document getDocument = invokeGetDocument(postDocument.getId());

		assertEquals(postDocument, getDocument);
		assertValid(getDocument);
	}

	protected Document testGetDocument_addDocument() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Document invokeGetDocument(Long documentId) throws Exception {
		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/documents/{documentId}", documentId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Document.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetDocumentResponse(Long documentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/documents/{documentId}", documentId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPatchDocument() throws Exception {
		Assert.assertTrue(true);
	}

	protected Document testPatchDocument_addDocument() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Document invokePatchDocument(
			Long documentId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.addPart(
			"document",
			inputObjectMapper.writeValueAsString(multipartBody.getValues()));

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		options.addFilePart(
			"file", binaryFile.getFileName(),
			FileUtil.getBytes(binaryFile.getInputStream()), contentType,
			"UTF-8");

		String location =
			_resourceURL + _toPath("/documents/{documentId}", documentId);

		options.setLocation(location);

		options.setPatch(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Document.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePatchDocumentResponse(
			Long documentId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/documents/{documentId}", documentId);

		options.setLocation(location);

		options.setPatch(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutDocument() throws Exception {
		Assert.assertTrue(true);
	}

	protected Document testPutDocument_addDocument() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Document invokePutDocument(
			Long documentId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.addPart(
			"document",
			inputObjectMapper.writeValueAsString(multipartBody.getValues()));

		BinaryFile binaryFile = multipartBody.getBinaryFile("file");

		options.addFilePart(
			"file", binaryFile.getFileName(),
			FileUtil.getBytes(binaryFile.getInputStream()), contentType,
			"UTF-8");

		String location =
			_resourceURL + _toPath("/documents/{documentId}", documentId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Document.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutDocumentResponse(
			Long documentId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL + _toPath("/documents/{documentId}", documentId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testDeleteDocumentMyRating() throws Exception {
		Document document = testDeleteDocumentMyRating_addDocument();

		assertResponseCode(
			204, invokeDeleteDocumentMyRatingResponse(document.getId()));

		assertResponseCode(
			404, invokeGetDocumentMyRatingResponse(document.getId()));
	}

	protected Document testDeleteDocumentMyRating_addDocument()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void invokeDeleteDocumentMyRating(Long documentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath("/documents/{documentId}/my-rating", documentId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}
	}

	protected Http.Response invokeDeleteDocumentMyRatingResponse(
			Long documentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		String location =
			_resourceURL +
				_toPath("/documents/{documentId}/my-rating", documentId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetDocumentMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	protected Rating invokeGetDocumentMyRating(Long documentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/documents/{documentId}/my-rating", documentId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetDocumentMyRatingResponse(Long documentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/documents/{documentId}/my-rating", documentId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPostDocumentMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	protected Rating invokePostDocumentMyRating(Long documentId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/documents/{documentId}/my-rating", documentId);

		options.setLocation(location);

		options.setPost(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePostDocumentMyRatingResponse(
			Long documentId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/documents/{documentId}/my-rating", documentId);

		options.setLocation(location);

		options.setPost(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testPutDocumentMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	protected Rating invokePutDocumentMyRating(Long documentId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/documents/{documentId}/my-rating", documentId);

		options.setLocation(location);

		options.setPut(true);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, Rating.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokePutDocumentMyRatingResponse(
			Long documentId, Rating rating)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/documents/{documentId}/my-rating", documentId);

		options.setLocation(location);

		options.setPut(true);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(Document document1, Document document2) {
		Assert.assertTrue(
			document1 + " does not equal " + document2,
			equals(document1, document2));
	}

	protected void assertEquals(
		List<Document> documents1, List<Document> documents2) {

		Assert.assertEquals(documents1.size(), documents2.size());

		for (int i = 0; i < documents1.size(); i++) {
			Document document1 = documents1.get(i);
			Document document2 = documents2.get(i);

			assertEquals(document1, document2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Document> documents1, List<Document> documents2) {

		Assert.assertEquals(documents1.size(), documents2.size());

		for (Document document1 : documents1) {
			boolean contains = false;

			for (Document document2 : documents2) {
				if (equals(document1, document2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				documents2 + " does not contain " + document1, contains);
		}
	}

	protected void assertValid(Document document) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<Document> page) {
		boolean valid = false;

		Collection<Document> documents = page.getItems();

		int size = documents.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected boolean equals(Document document1, Document document2) {
		if (document1 == document2) {
			return true;
		}

		return false;
	}

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_documentResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_documentResource;

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
		EntityField entityField, String operator, Document document) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("adaptedImages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("aggregateRating")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentUrl")) {
			sb.append("'");
			sb.append(String.valueOf(document.getContentUrl()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("creator")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(document.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(document.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(document.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("documentFolderId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("encodingFormat")) {
			sb.append("'");
			sb.append(String.valueOf(document.getEncodingFormat()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("fileExtension")) {
			sb.append("'");
			sb.append(String.valueOf(document.getFileExtension()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("keywords")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfComments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sizeInBytes")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategories")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategoryIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(document.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("viewableBy")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected Document randomDocument() {
		return new Document() {
			{
				contentUrl = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				documentFolderId = RandomTestUtil.randomLong();
				encodingFormat = RandomTestUtil.randomString();
				fileExtension = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected Document randomIrrelevantDocument() {
		return randomDocument();
	}

	protected Document randomPatchDocument() {
		return randomDocument();
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
		BaseDocumentResourceTestCase.class);

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
	private DocumentResource _documentResource;

	private URL _resourceURL;

}