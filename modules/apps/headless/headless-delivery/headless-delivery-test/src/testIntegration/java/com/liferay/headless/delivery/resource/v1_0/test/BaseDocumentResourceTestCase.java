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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.Document;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.DocumentResource;
import com.liferay.headless.delivery.client.serdes.v1_0.DocumentSerDes;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.io.File;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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
		testLocale = LocaleUtil.getDefault();
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
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				enable(SerializationFeature.INDENT_OUTPUT);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		Document document1 = randomDocument();

		String json = objectMapper.writeValueAsString(document1);

		Document document2 = DocumentSerDes.toDTO(json);

		Assert.assertTrue(equals(document1, document2));
	}

	@Test
	public void testClientSerDesToJSON() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
				configure(
					SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
				setDateFormat(new ISO8601DateFormat());
				setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
				setSerializationInclusion(JsonInclude.Include.NON_NULL);
				setVisibility(
					PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
				setVisibility(
					PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
			}
		};

		Document document = randomDocument();

		String json1 = objectMapper.writeValueAsString(document);
		String json2 = DocumentSerDes.toJSON(document);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
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

			Page<Document> page =
				DocumentResource.getDocumentFolderDocumentsPage(
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

		Page<Document> page = DocumentResource.getDocumentFolderDocumentsPage(
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

		document1 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, document1);

		for (EntityField entityField : entityFields) {
			Page<Document> page =
				DocumentResource.getDocumentFolderDocumentsPage(
					documentFolderId, null,
					getFilterString(entityField, "between", document1),
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
			Page<Document> page =
				DocumentResource.getDocumentFolderDocumentsPage(
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

		Page<Document> page1 = DocumentResource.getDocumentFolderDocumentsPage(
			documentFolderId, null, null, Pagination.of(1, 2), null);

		List<Document> documents1 = (List<Document>)page1.getItems();

		Assert.assertEquals(documents1.toString(), 2, documents1.size());

		Page<Document> page2 = DocumentResource.getDocumentFolderDocumentsPage(
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

		document2 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, document2);

		for (EntityField entityField : entityFields) {
			Page<Document> ascPage =
				DocumentResource.getDocumentFolderDocumentsPage(
					documentFolderId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(document1, document2),
				(List<Document>)ascPage.getItems());

			Page<Document> descPage =
				DocumentResource.getDocumentFolderDocumentsPage(
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
			Page<Document> ascPage =
				DocumentResource.getDocumentFolderDocumentsPage(
					documentFolderId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(document1, document2),
				(List<Document>)ascPage.getItems());

			Page<Document> descPage =
				DocumentResource.getDocumentFolderDocumentsPage(
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

		return DocumentResource.postDocumentFolderDocument(
			documentFolderId, document, getMultipartFiles());
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

	@Test
	public void testPostDocumentFolderDocument() throws Exception {
		Assert.assertTrue(true);
	}

	protected Document testPostDocumentFolderDocument_addDocument(
			Document document)
		throws Exception {

		return DocumentResource.postDocumentFolderDocument(
			testGetDocumentFolderDocumentsPage_getDocumentFolderId(), document,
			getMultipartFiles());
	}

	@Test
	public void testDeleteDocument() throws Exception {
		Document document = testDeleteDocument_addDocument();

		assertHttpResponseStatusCode(
			204, DocumentResource.deleteDocumentHttpResponse(document.getId()));

		assertHttpResponseStatusCode(
			404, DocumentResource.getDocumentHttpResponse(document.getId()));

		assertHttpResponseStatusCode(
			404, DocumentResource.getDocumentHttpResponse(0L));
	}

	protected Document testDeleteDocument_addDocument() throws Exception {
		return DocumentResource.postSiteDocument(
			testGroup.getGroupId(), randomDocument(), getMultipartFiles());
	}

	@Test
	public void testGetDocument() throws Exception {
		Document postDocument = testGetDocument_addDocument();

		Document getDocument = DocumentResource.getDocument(
			postDocument.getId());

		assertEquals(postDocument, getDocument);
		assertValid(getDocument);
	}

	protected Document testGetDocument_addDocument() throws Exception {
		return DocumentResource.postSiteDocument(
			testGroup.getGroupId(), randomDocument(), getMultipartFiles());
	}

	@Test
	public void testPatchDocument() throws Exception {
		Assert.assertTrue(true);
	}

	protected Document testPatchDocument_addDocument() throws Exception {
		return DocumentResource.postSiteDocument(
			testGroup.getGroupId(), randomDocument(), getMultipartFiles());
	}

	@Test
	public void testPutDocument() throws Exception {
		Assert.assertTrue(true);
	}

	protected Document testPutDocument_addDocument() throws Exception {
		return DocumentResource.postSiteDocument(
			testGroup.getGroupId(), randomDocument(), getMultipartFiles());
	}

	@Test
	public void testDeleteDocumentMyRating() throws Exception {
		Document document = testDeleteDocumentMyRating_addDocument();

		assertHttpResponseStatusCode(
			204,
			DocumentResource.deleteDocumentMyRatingHttpResponse(
				document.getId()));

		assertHttpResponseStatusCode(
			404,
			DocumentResource.getDocumentMyRatingHttpResponse(document.getId()));

		assertHttpResponseStatusCode(
			404, DocumentResource.getDocumentMyRatingHttpResponse(0L));
	}

	protected Document testDeleteDocumentMyRating_addDocument()
		throws Exception {

		return DocumentResource.postSiteDocument(
			testGroup.getGroupId(), randomDocument(), getMultipartFiles());
	}

	@Test
	public void testGetDocumentMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPostDocumentMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutDocumentMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testGetSiteDocumentsPage() throws Exception {
		Long siteId = testGetSiteDocumentsPage_getSiteId();
		Long irrelevantSiteId = testGetSiteDocumentsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			Document irrelevantDocument = testGetSiteDocumentsPage_addDocument(
				irrelevantSiteId, randomIrrelevantDocument());

			Page<Document> page = DocumentResource.getSiteDocumentsPage(
				irrelevantSiteId, null, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDocument),
				(List<Document>)page.getItems());
			assertValid(page);
		}

		Document document1 = testGetSiteDocumentsPage_addDocument(
			siteId, randomDocument());

		Document document2 = testGetSiteDocumentsPage_addDocument(
			siteId, randomDocument());

		Page<Document> page = DocumentResource.getSiteDocumentsPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(document1, document2),
			(List<Document>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetSiteDocumentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteDocumentsPage_getSiteId();

		Document document1 = randomDocument();

		document1 = testGetSiteDocumentsPage_addDocument(siteId, document1);

		for (EntityField entityField : entityFields) {
			Page<Document> page = DocumentResource.getSiteDocumentsPage(
				siteId, null, null,
				getFilterString(entityField, "between", document1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(document1),
				(List<Document>)page.getItems());
		}
	}

	@Test
	public void testGetSiteDocumentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteDocumentsPage_getSiteId();

		Document document1 = testGetSiteDocumentsPage_addDocument(
			siteId, randomDocument());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Document document2 = testGetSiteDocumentsPage_addDocument(
			siteId, randomDocument());

		for (EntityField entityField : entityFields) {
			Page<Document> page = DocumentResource.getSiteDocumentsPage(
				siteId, null, null,
				getFilterString(entityField, "eq", document1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(document1),
				(List<Document>)page.getItems());
		}
	}

	@Test
	public void testGetSiteDocumentsPageWithPagination() throws Exception {
		Long siteId = testGetSiteDocumentsPage_getSiteId();

		Document document1 = testGetSiteDocumentsPage_addDocument(
			siteId, randomDocument());

		Document document2 = testGetSiteDocumentsPage_addDocument(
			siteId, randomDocument());

		Document document3 = testGetSiteDocumentsPage_addDocument(
			siteId, randomDocument());

		Page<Document> page1 = DocumentResource.getSiteDocumentsPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		List<Document> documents1 = (List<Document>)page1.getItems();

		Assert.assertEquals(documents1.toString(), 2, documents1.size());

		Page<Document> page2 = DocumentResource.getSiteDocumentsPage(
			siteId, null, null, null, Pagination.of(2, 2), null);

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
	public void testGetSiteDocumentsPageWithSortDateTime() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteDocumentsPage_getSiteId();

		Document document1 = randomDocument();
		Document document2 = randomDocument();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				document1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		document1 = testGetSiteDocumentsPage_addDocument(siteId, document1);

		document2 = testGetSiteDocumentsPage_addDocument(siteId, document2);

		for (EntityField entityField : entityFields) {
			Page<Document> ascPage = DocumentResource.getSiteDocumentsPage(
				siteId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(document1, document2),
				(List<Document>)ascPage.getItems());

			Page<Document> descPage = DocumentResource.getSiteDocumentsPage(
				siteId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(document2, document1),
				(List<Document>)descPage.getItems());
		}
	}

	@Test
	public void testGetSiteDocumentsPageWithSortString() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteDocumentsPage_getSiteId();

		Document document1 = randomDocument();
		Document document2 = randomDocument();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(document1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(document2, entityField.getName(), "Bbb");
		}

		document1 = testGetSiteDocumentsPage_addDocument(siteId, document1);

		document2 = testGetSiteDocumentsPage_addDocument(siteId, document2);

		for (EntityField entityField : entityFields) {
			Page<Document> ascPage = DocumentResource.getSiteDocumentsPage(
				siteId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(document1, document2),
				(List<Document>)ascPage.getItems());

			Page<Document> descPage = DocumentResource.getSiteDocumentsPage(
				siteId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(document2, document1),
				(List<Document>)descPage.getItems());
		}
	}

	protected Document testGetSiteDocumentsPage_addDocument(
			Long siteId, Document document)
		throws Exception {

		return DocumentResource.postSiteDocument(
			siteId, document, getMultipartFiles());
	}

	protected Long testGetSiteDocumentsPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteDocumentsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testPostSiteDocument() throws Exception {
		Assert.assertTrue(true);
	}

	protected Document testPostSiteDocument_addDocument(Document document)
		throws Exception {

		return DocumentResource.postSiteDocument(
			testGetSiteDocumentsPage_getSiteId(), document,
			getMultipartFiles());
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
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
		boolean valid = true;

		if (document.getDateCreated() == null) {
			valid = false;
		}

		if (document.getDateModified() == null) {
			valid = false;
		}

		if (document.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("adaptedImages", additionalAssertFieldName)) {
				if (document.getAdaptedImages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (document.getAggregateRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("contentUrl", additionalAssertFieldName)) {
				if (document.getContentUrl() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (document.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (document.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (document.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("documentFolderId", additionalAssertFieldName)) {
				if (document.getDocumentFolderId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (document.getEncodingFormat() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("fileExtension", additionalAssertFieldName)) {
				if (document.getFileExtension() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (document.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", additionalAssertFieldName)) {
				if (document.getNumberOfComments() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (document.getRelatedContents() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sizeInBytes", additionalAssertFieldName)) {
				if (document.getSizeInBytes() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategories", additionalAssertFieldName)) {

				if (document.getTaxonomyCategories() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (document.getTaxonomyCategoryIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (document.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (document.getViewableBy() == null) {
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

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected boolean equals(Document document1, Document document2) {
		if (document1 == document2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("adaptedImages", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getAdaptedImages(),
						document2.getAdaptedImages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getAggregateRating(),
						document2.getAggregateRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("contentUrl", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getContentUrl(), document2.getContentUrl())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getCreator(), document2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getCustomFields(),
						document2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getDateCreated(),
						document2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getDateModified(),
						document2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getDescription(),
						document2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("documentFolderId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getDocumentFolderId(),
						document2.getDocumentFolderId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getEncodingFormat(),
						document2.getEncodingFormat())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("fileExtension", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getFileExtension(),
						document2.getFileExtension())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(document1.getId(), document2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getKeywords(), document2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfComments", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getNumberOfComments(),
						document2.getNumberOfComments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getRelatedContents(),
						document2.getRelatedContents())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sizeInBytes", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getSizeInBytes(),
						document2.getSizeInBytes())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategories", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						document1.getTaxonomyCategories(),
						document2.getTaxonomyCategories())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						document1.getTaxonomyCategoryIds(),
						document2.getTaxonomyCategoryIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getTitle(), document2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getViewableBy(), document2.getViewableBy())) {

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

		if (entityFieldName.equals("customFields")) {
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
						DateUtils.addSeconds(document.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(document.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(document.getDateCreated()));
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
						DateUtils.addSeconds(document.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(document.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(document.getDateModified()));
			}

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

		if (entityFieldName.equals("relatedContents")) {
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

	protected Map<String, File> getMultipartFiles() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Document randomDocument() throws Exception {
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
				sizeInBytes = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected Document randomIrrelevantDocument() throws Exception {
		Document randomIrrelevantDocument = randomDocument();

		return randomIrrelevantDocument;
	}

	protected Document randomPatchDocument() throws Exception {
		return randomDocument();
	}

	protected Group irrelevantGroup;
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

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
	private com.liferay.headless.delivery.resource.v1_0.DocumentResource
		_documentResource;

}