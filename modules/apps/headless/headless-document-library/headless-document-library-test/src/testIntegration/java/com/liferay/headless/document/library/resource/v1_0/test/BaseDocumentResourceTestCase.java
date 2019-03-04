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

import com.liferay.headless.document.library.dto.v1_0.Document;
import com.liferay.headless.document.library.resource.v1_0.DocumentResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.URLCodec;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.multipart.MultipartBody;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

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

import org.apache.commons.beanutils.BeanUtils;
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
		testGroup = GroupTestUtil.addGroup();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-document-library/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testDeleteDocument() throws Exception {
		Document document = testDeleteDocument_addDocument();

		assertResponseCode(200, invokeDeleteDocumentResponse(document.getId()));

		assertResponseCode(404, invokeGetDocumentResponse(document.getId()));
	}

	@Test
	public void testGetContentSpaceDocumentsPage() throws Exception {
		Long contentSpaceId =
			testGetContentSpaceDocumentsPage_getContentSpaceId();

		Document document1 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, randomDocument());
		Document document2 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, randomDocument());

		Page<Document> page = invokeGetContentSpaceDocumentsPage(
			contentSpaceId, (String)null, Pagination.of(2, 1), (String)null);

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
				contentSpaceId, getFilterString(entityField, "eq", document1),
				Pagination.of(2, 1), (String)null);

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
		Document document2 = testGetContentSpaceDocumentsPage_addDocument(
			contentSpaceId, randomDocument());

		for (EntityField entityField : entityFields) {
			Page<Document> page = invokeGetContentSpaceDocumentsPage(
				contentSpaceId, getFilterString(entityField, "eq", document1),
				Pagination.of(2, 1), (String)null);

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
			contentSpaceId, (String)null, Pagination.of(2, 1), (String)null);

		List<Document> documents1 = (List<Document>)page1.getItems();

		Assert.assertEquals(documents1.toString(), 2, documents1.size());

		Page<Document> page2 = invokeGetContentSpaceDocumentsPage(
			contentSpaceId, (String)null, Pagination.of(2, 2), (String)null);

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
				contentSpaceId, (String)null, Pagination.of(2, 1),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(document1, document2),
				(List<Document>)ascPage.getItems());

			Page<Document> descPage = invokeGetContentSpaceDocumentsPage(
				contentSpaceId, (String)null, Pagination.of(2, 1),
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
				contentSpaceId, (String)null, Pagination.of(2, 1),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(document1, document2),
				(List<Document>)ascPage.getItems());

			Page<Document> descPage = invokeGetContentSpaceDocumentsPage(
				contentSpaceId, (String)null, Pagination.of(2, 1),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(document2, document1),
				(List<Document>)descPage.getItems());
		}
	}

	@Test
	public void testGetDocument() throws Exception {
		Document postDocument = testGetDocument_addDocument();

		Document getDocument = invokeGetDocument(postDocument.getId());

		assertEquals(postDocument, getDocument);
		assertValid(getDocument);
	}

	@Test
	public void testGetFolderDocumentsPage() throws Exception {
		Long folderId = testGetFolderDocumentsPage_getFolderId();

		Document document1 = testGetFolderDocumentsPage_addDocument(
			folderId, randomDocument());
		Document document2 = testGetFolderDocumentsPage_addDocument(
			folderId, randomDocument());

		Page<Document> page = invokeGetFolderDocumentsPage(
			folderId, (String)null, Pagination.of(2, 1), (String)null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(document1, document2),
			(List<Document>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetFolderDocumentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long folderId = testGetFolderDocumentsPage_getFolderId();

		Document document1 = randomDocument();
		Document document2 = randomDocument();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				document1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		document1 = testGetFolderDocumentsPage_addDocument(folderId, document1);

		Thread.sleep(1000);

		document2 = testGetFolderDocumentsPage_addDocument(folderId, document2);

		for (EntityField entityField : entityFields) {
			Page<Document> page = invokeGetFolderDocumentsPage(
				folderId, getFilterString(entityField, "eq", document1),
				Pagination.of(2, 1), (String)null);

			assertEquals(
				Collections.singletonList(document1),
				(List<Document>)page.getItems());
		}
	}

	@Test
	public void testGetFolderDocumentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long folderId = testGetFolderDocumentsPage_getFolderId();

		Document document1 = testGetFolderDocumentsPage_addDocument(
			folderId, randomDocument());
		Document document2 = testGetFolderDocumentsPage_addDocument(
			folderId, randomDocument());

		for (EntityField entityField : entityFields) {
			Page<Document> page = invokeGetFolderDocumentsPage(
				folderId, getFilterString(entityField, "eq", document1),
				Pagination.of(2, 1), (String)null);

			assertEquals(
				Collections.singletonList(document1),
				(List<Document>)page.getItems());
		}
	}

	@Test
	public void testGetFolderDocumentsPageWithPagination() throws Exception {
		Long folderId = testGetFolderDocumentsPage_getFolderId();

		Document document1 = testGetFolderDocumentsPage_addDocument(
			folderId, randomDocument());
		Document document2 = testGetFolderDocumentsPage_addDocument(
			folderId, randomDocument());
		Document document3 = testGetFolderDocumentsPage_addDocument(
			folderId, randomDocument());

		Page<Document> page1 = invokeGetFolderDocumentsPage(
			folderId, (String)null, Pagination.of(2, 1), (String)null);

		List<Document> documents1 = (List<Document>)page1.getItems();

		Assert.assertEquals(documents1.toString(), 2, documents1.size());

		Page<Document> page2 = invokeGetFolderDocumentsPage(
			folderId, (String)null, Pagination.of(2, 2), (String)null);

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
	public void testGetFolderDocumentsPageWithSortDateTime() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long folderId = testGetFolderDocumentsPage_getFolderId();

		Document document1 = randomDocument();
		Document document2 = randomDocument();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				document1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		document1 = testGetFolderDocumentsPage_addDocument(folderId, document1);

		Thread.sleep(1000);

		document2 = testGetFolderDocumentsPage_addDocument(folderId, document2);

		for (EntityField entityField : entityFields) {
			Page<Document> ascPage = invokeGetFolderDocumentsPage(
				folderId, (String)null, Pagination.of(2, 1),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(document1, document2),
				(List<Document>)ascPage.getItems());

			Page<Document> descPage = invokeGetFolderDocumentsPage(
				folderId, (String)null, Pagination.of(2, 1),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(document2, document1),
				(List<Document>)descPage.getItems());
		}
	}

	@Test
	public void testGetFolderDocumentsPageWithSortString() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long folderId = testGetFolderDocumentsPage_getFolderId();

		Document document1 = randomDocument();
		Document document2 = randomDocument();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(document1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(document2, entityField.getName(), "Bbb");
		}

		document1 = testGetFolderDocumentsPage_addDocument(folderId, document1);
		document2 = testGetFolderDocumentsPage_addDocument(folderId, document2);

		for (EntityField entityField : entityFields) {
			Page<Document> ascPage = invokeGetFolderDocumentsPage(
				folderId, (String)null, Pagination.of(2, 1),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(document1, document2),
				(List<Document>)ascPage.getItems());

			Page<Document> descPage = invokeGetFolderDocumentsPage(
				folderId, (String)null, Pagination.of(2, 1),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(document2, document1),
				(List<Document>)descPage.getItems());
		}
	}

	@Test
	public void testPatchDocument() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostContentSpaceDocument() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostFolderDocument() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutDocument() throws Exception {
		Assert.assertTrue(true);
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

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertValid(Document document) {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<Document> page) {
		boolean valid = false;

		Collection<Document> documents = page.getItems();

		int size = documents.size();

		if ((page.getItemsPerPage() > 0) && (page.getLastPageNumber() > 0) &&
			(page.getPageNumber() > 0) && (page.getTotalCount() > 0) &&
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

		if (entityFieldName.equals("categories")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("categoryIds")) {
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

		if (entityFieldName.equals("folderId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("keywords")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sizeInBytes")) {
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
			sb.append("'");
			sb.append(String.valueOf(document.getViewableBy()));
			sb.append("'");

			return sb.toString();
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected boolean invokeDeleteDocument(Long documentId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Boolean.class);
	}

	protected Http.Response invokeDeleteDocumentResponse(Long documentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setDelete(true);

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<Document> invokeGetContentSpaceDocumentsPage(
			Long contentSpaceId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getContentSpaceDocumentsLocation(
				contentSpaceId, filterString, pagination, sortString));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Document>>() {
			});
	}

	protected Http.Response invokeGetContentSpaceDocumentsPageResponse(
			Long contentSpaceId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getContentSpaceDocumentsLocation(
				contentSpaceId, filterString, pagination, sortString));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Document invokeGetDocument(Long documentId) throws Exception {
		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Document.class);
	}

	protected Http.Response invokeGetDocumentResponse(Long documentId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Page<Document> invokeGetFolderDocumentsPage(
			Long folderId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getFolderDocumentsLocation(
				folderId, filterString, pagination, sortString));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options),
			new TypeReference<Page<Document>>() {
			});
	}

	protected Http.Response invokeGetFolderDocumentsPageResponse(
			Long folderId, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_getFolderDocumentsLocation(
				folderId, filterString, pagination, sortString));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Document invokePatchDocument(
			Long documentId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Document.class);
	}

	protected Http.Response invokePatchDocumentResponse(
			Long documentId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Document invokePostContentSpaceDocument(
			Long contentSpaceId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/documents",
					contentSpaceId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Document.class);
	}

	protected Http.Response invokePostContentSpaceDocumentResponse(
			Long contentSpaceId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/documents",
					contentSpaceId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Document invokePostFolderDocument(
			Long folderId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}/documents", folderId));

		options.setPost(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Document.class);
	}

	protected Http.Response invokePostFolderDocumentResponse(
			Long folderId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/folders/{folder-id}/documents", folderId));

		options.setPost(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Document invokePutDocument(
			Long documentId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		options.setPut(true);

		return _outputObjectMapper.readValue(
			HttpUtil.URLtoString(options), Document.class);
	}

	protected Http.Response invokePutDocumentResponse(
			Long documentId, MultipartBody multipartBody)
		throws Exception {

		Http.Options options = _createHttpOptions();

		options.setLocation(
			_resourceURL + _toPath("/documents/{document-id}", documentId));

		options.setPut(true);

		HttpUtil.URLtoString(options);

		return options.getResponse();
	}

	protected Document randomDocument() {
		return new Document() {
			{
				contentUrl = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				encodingFormat = RandomTestUtil.randomString();
				fileExtension = RandomTestUtil.randomString();
				folderId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
				viewableBy = RandomTestUtil.randomString();
			}
		};
	}

	protected Document testDeleteDocument_addDocument() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
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

	protected Document testGetDocument_addDocument() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Document testGetFolderDocumentsPage_addDocument(
			Long folderId, Document document)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetFolderDocumentsPage_getFolderId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Document testPostContentSpaceDocument_addDocument(
			Document document)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Document testPostFolderDocument_addDocument(Document document)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Document testPutDocument_addDocument() throws Exception {
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

	private String _getContentSpaceDocumentsLocation(
		Long contentSpaceId, String filterString, Pagination pagination,
		String sortString) {

		String url =
			_resourceURL +
				_toPath(
					"/content-spaces/{content-space-id}/documents",
					contentSpaceId);

		url += "?filter=" + URLCodec.encodeURL(filterString);
		url += "&page=" + pagination.getPageNumber();
		url += "&pageSize=" + pagination.getItemsPerPage();
		url += "&sort=" + URLCodec.encodeURL(sortString);

		return url;
	}

	private String _getFolderDocumentsLocation(
		Long folderId, String filterString, Pagination pagination,
		String sortString) {

		String url =
			_resourceURL + _toPath("/folders/{folder-id}/documents", folderId);

		url += "?filter=" + URLCodec.encodeURL(filterString);
		url += "&page=" + pagination.getPageNumber();
		url += "&pageSize=" + pagination.getItemsPerPage();
		url += "&sort=" + URLCodec.encodeURL(sortString);

		return url;
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
	private DocumentResource _documentResource;

	private URL _resourceURL;

}