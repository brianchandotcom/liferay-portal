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

import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.headless.delivery.client.dto.v1_0.Document;
import com.liferay.headless.delivery.client.dto.v1_0.Rating;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.DocumentResource;
import com.liferay.headless.delivery.client.serdes.v1_0.DocumentSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.io.File;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Level;

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

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		testDepotEntry = DepotEntryLocalServiceUtil.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			null,
			new ServiceContext() {
				{
					setCompanyId(testGroup.getCompanyId());
					setUserId(TestPropsValues.getUserId());
				}
			});

		_documentResource.setContextCompany(testCompany);

		DocumentResource.Builder builder = DocumentResource.builder();

		documentResource = builder.authentication(
			"test@liferay.com", "test"
		).locale(
			LocaleUtil.getDefault()
		).build();
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
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Document document = randomDocument();

		document.setAssetLibraryKey(regex);
		document.setContentUrl(regex);
		document.setContentValue(regex);
		document.setDescription(regex);
		document.setEncodingFormat(regex);
		document.setFileExtension(regex);
		document.setTitle(regex);

		String json = DocumentSerDes.toJSON(document);

		Assert.assertFalse(json.contains(regex));

		document = DocumentSerDes.toDTO(json);

		Assert.assertEquals(regex, document.getAssetLibraryKey());
		Assert.assertEquals(regex, document.getContentUrl());
		Assert.assertEquals(regex, document.getContentValue());
		Assert.assertEquals(regex, document.getDescription());
		Assert.assertEquals(regex, document.getEncodingFormat());
		Assert.assertEquals(regex, document.getFileExtension());
		Assert.assertEquals(regex, document.getTitle());
	}

	@Test
	public void testGetAssetLibraryDocumentsPage() throws Exception {
		Page<Document> page = documentResource.getAssetLibraryDocumentsPage(
			testGetAssetLibraryDocumentsPage_getAssetLibraryId(), null,
			RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
			null);

		Assert.assertEquals(0, page.getTotalCount());

		Long assetLibraryId =
			testGetAssetLibraryDocumentsPage_getAssetLibraryId();
		Long irrelevantAssetLibraryId =
			testGetAssetLibraryDocumentsPage_getIrrelevantAssetLibraryId();

		if ((irrelevantAssetLibraryId != null)) {
			Document irrelevantDocument =
				testGetAssetLibraryDocumentsPage_addDocument(
					irrelevantAssetLibraryId, randomIrrelevantDocument());

			page = documentResource.getAssetLibraryDocumentsPage(
				irrelevantAssetLibraryId, null, null, null, null,
				Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDocument),
				(List<Document>)page.getItems());
			assertValid(page);
		}

		Document document1 = testGetAssetLibraryDocumentsPage_addDocument(
			assetLibraryId, randomDocument());

		Document document2 = testGetAssetLibraryDocumentsPage_addDocument(
			assetLibraryId, randomDocument());

		page = documentResource.getAssetLibraryDocumentsPage(
			assetLibraryId, null, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(document1, document2),
			(List<Document>)page.getItems());
		assertValid(page);

		documentResource.deleteDocument(document1.getId());

		documentResource.deleteDocument(document2.getId());
	}

	@Test
	public void testGetAssetLibraryDocumentsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryDocumentsPage_getAssetLibraryId();

		Document document1 = randomDocument();

		document1 = testGetAssetLibraryDocumentsPage_addDocument(
			assetLibraryId, document1);

		for (EntityField entityField : entityFields) {
			Page<Document> page = documentResource.getAssetLibraryDocumentsPage(
				assetLibraryId, null, null, null,
				getFilterString(entityField, "between", document1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(document1),
				(List<Document>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryDocumentsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryDocumentsPage_getAssetLibraryId();

		Document document1 = testGetAssetLibraryDocumentsPage_addDocument(
			assetLibraryId, randomDocument());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Document document2 = testGetAssetLibraryDocumentsPage_addDocument(
			assetLibraryId, randomDocument());

		for (EntityField entityField : entityFields) {
			Page<Document> page = documentResource.getAssetLibraryDocumentsPage(
				assetLibraryId, null, null, null,
				getFilterString(entityField, "eq", document1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(document1),
				(List<Document>)page.getItems());
		}
	}

	@Test
	public void testGetAssetLibraryDocumentsPageWithPagination()
		throws Exception {

		Long assetLibraryId =
			testGetAssetLibraryDocumentsPage_getAssetLibraryId();

		Document document1 = testGetAssetLibraryDocumentsPage_addDocument(
			assetLibraryId, randomDocument());

		Document document2 = testGetAssetLibraryDocumentsPage_addDocument(
			assetLibraryId, randomDocument());

		Document document3 = testGetAssetLibraryDocumentsPage_addDocument(
			assetLibraryId, randomDocument());

		Page<Document> page1 = documentResource.getAssetLibraryDocumentsPage(
			assetLibraryId, null, null, null, null, Pagination.of(1, 2), null);

		List<Document> documents1 = (List<Document>)page1.getItems();

		Assert.assertEquals(documents1.toString(), 2, documents1.size());

		Page<Document> page2 = documentResource.getAssetLibraryDocumentsPage(
			assetLibraryId, null, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Document> documents2 = (List<Document>)page2.getItems();

		Assert.assertEquals(documents2.toString(), 1, documents2.size());

		Page<Document> page3 = documentResource.getAssetLibraryDocumentsPage(
			assetLibraryId, null, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(document1, document2, document3),
			(List<Document>)page3.getItems());
	}

	@Test
	public void testGetAssetLibraryDocumentsPageWithSortDateTime()
		throws Exception {

		testGetAssetLibraryDocumentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, document1, document2) -> {
				BeanUtils.setProperty(
					document1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAssetLibraryDocumentsPageWithSortInteger()
		throws Exception {

		testGetAssetLibraryDocumentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, document1, document2) -> {
				BeanUtils.setProperty(document1, entityField.getName(), 0);
				BeanUtils.setProperty(document2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAssetLibraryDocumentsPageWithSortString()
		throws Exception {

		testGetAssetLibraryDocumentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, document1, document2) -> {
				Class<?> clazz = document1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						document1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						document2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						document1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						document2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						document1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						document2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAssetLibraryDocumentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Document, Document, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long assetLibraryId =
			testGetAssetLibraryDocumentsPage_getAssetLibraryId();

		Document document1 = randomDocument();
		Document document2 = randomDocument();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, document1, document2);
		}

		document1 = testGetAssetLibraryDocumentsPage_addDocument(
			assetLibraryId, document1);

		document2 = testGetAssetLibraryDocumentsPage_addDocument(
			assetLibraryId, document2);

		for (EntityField entityField : entityFields) {
			Page<Document> ascPage =
				documentResource.getAssetLibraryDocumentsPage(
					assetLibraryId, null, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(document1, document2),
				(List<Document>)ascPage.getItems());

			Page<Document> descPage =
				documentResource.getAssetLibraryDocumentsPage(
					assetLibraryId, null, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(document2, document1),
				(List<Document>)descPage.getItems());
		}
	}

	protected Document testGetAssetLibraryDocumentsPage_addDocument(
			Long assetLibraryId, Document document)
		throws Exception {

		return documentResource.postAssetLibraryDocument(
			assetLibraryId, document, getMultipartFiles());
	}

	protected Long testGetAssetLibraryDocumentsPage_getAssetLibraryId()
		throws Exception {

		return testDepotEntry.getDepotEntryId();
	}

	protected Long
			testGetAssetLibraryDocumentsPage_getIrrelevantAssetLibraryId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAssetLibraryDocument() throws Exception {
		Document randomDocument = randomDocument();

		Map<String, File> multipartFiles = getMultipartFiles();

		Document postDocument = testPostAssetLibraryDocument_addDocument(
			randomDocument, multipartFiles);

		assertEquals(randomDocument, postDocument);
		assertValid(postDocument);

		assertValid(postDocument, multipartFiles);
	}

	protected Document testPostAssetLibraryDocument_addDocument(
			Document document, Map<String, File> multipartFiles)
		throws Exception {

		return documentResource.postAssetLibraryDocument(
			testGetAssetLibraryDocumentsPage_getAssetLibraryId(), document,
			multipartFiles);
	}

	@Test
	public void testGetDocumentFolderDocumentsPage() throws Exception {
		Page<Document> page = documentResource.getDocumentFolderDocumentsPage(
			testGetDocumentFolderDocumentsPage_getDocumentFolderId(), null,
			RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
			null);

		Assert.assertEquals(0, page.getTotalCount());

		Long documentFolderId =
			testGetDocumentFolderDocumentsPage_getDocumentFolderId();
		Long irrelevantDocumentFolderId =
			testGetDocumentFolderDocumentsPage_getIrrelevantDocumentFolderId();

		if ((irrelevantDocumentFolderId != null)) {
			Document irrelevantDocument =
				testGetDocumentFolderDocumentsPage_addDocument(
					irrelevantDocumentFolderId, randomIrrelevantDocument());

			page = documentResource.getDocumentFolderDocumentsPage(
				irrelevantDocumentFolderId, null, null, null, null,
				Pagination.of(1, 2), null);

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

		page = documentResource.getDocumentFolderDocumentsPage(
			documentFolderId, null, null, null, null, Pagination.of(1, 2),
			null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(document1, document2),
			(List<Document>)page.getItems());
		assertValid(page);

		documentResource.deleteDocument(document1.getId());

		documentResource.deleteDocument(document2.getId());
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
				documentResource.getDocumentFolderDocumentsPage(
					documentFolderId, null, null, null,
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
				documentResource.getDocumentFolderDocumentsPage(
					documentFolderId, null, null, null,
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

		Page<Document> page1 = documentResource.getDocumentFolderDocumentsPage(
			documentFolderId, null, null, null, null, Pagination.of(1, 2),
			null);

		List<Document> documents1 = (List<Document>)page1.getItems();

		Assert.assertEquals(documents1.toString(), 2, documents1.size());

		Page<Document> page2 = documentResource.getDocumentFolderDocumentsPage(
			documentFolderId, null, null, null, null, Pagination.of(2, 2),
			null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Document> documents2 = (List<Document>)page2.getItems();

		Assert.assertEquals(documents2.toString(), 1, documents2.size());

		Page<Document> page3 = documentResource.getDocumentFolderDocumentsPage(
			documentFolderId, null, null, null, null, Pagination.of(1, 3),
			null);

		assertEqualsIgnoringOrder(
			Arrays.asList(document1, document2, document3),
			(List<Document>)page3.getItems());
	}

	@Test
	public void testGetDocumentFolderDocumentsPageWithSortDateTime()
		throws Exception {

		testGetDocumentFolderDocumentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, document1, document2) -> {
				BeanUtils.setProperty(
					document1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDocumentFolderDocumentsPageWithSortInteger()
		throws Exception {

		testGetDocumentFolderDocumentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, document1, document2) -> {
				BeanUtils.setProperty(document1, entityField.getName(), 0);
				BeanUtils.setProperty(document2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDocumentFolderDocumentsPageWithSortString()
		throws Exception {

		testGetDocumentFolderDocumentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, document1, document2) -> {
				Class<?> clazz = document1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						document1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						document2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						document1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						document2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						document1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						document2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetDocumentFolderDocumentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Document, Document, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long documentFolderId =
			testGetDocumentFolderDocumentsPage_getDocumentFolderId();

		Document document1 = randomDocument();
		Document document2 = randomDocument();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, document1, document2);
		}

		document1 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, document1);

		document2 = testGetDocumentFolderDocumentsPage_addDocument(
			documentFolderId, document2);

		for (EntityField entityField : entityFields) {
			Page<Document> ascPage =
				documentResource.getDocumentFolderDocumentsPage(
					documentFolderId, null, null, null, null,
					Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(document1, document2),
				(List<Document>)ascPage.getItems());

			Page<Document> descPage =
				documentResource.getDocumentFolderDocumentsPage(
					documentFolderId, null, null, null, null,
					Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(document2, document1),
				(List<Document>)descPage.getItems());
		}
	}

	protected Document testGetDocumentFolderDocumentsPage_addDocument(
			Long documentFolderId, Document document)
		throws Exception {

		return documentResource.postDocumentFolderDocument(
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
		Document randomDocument = randomDocument();

		Map<String, File> multipartFiles = getMultipartFiles();

		Document postDocument = testPostDocumentFolderDocument_addDocument(
			randomDocument, multipartFiles);

		assertEquals(randomDocument, postDocument);
		assertValid(postDocument);

		assertValid(postDocument, multipartFiles);
	}

	protected Document testPostDocumentFolderDocument_addDocument(
			Document document, Map<String, File> multipartFiles)
		throws Exception {

		return documentResource.postDocumentFolderDocument(
			testGetDocumentFolderDocumentsPage_getDocumentFolderId(), document,
			multipartFiles);
	}

	@Test
	public void testDeleteDocument() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Document document = testDeleteDocument_addDocument();

		assertHttpResponseStatusCode(
			204, documentResource.deleteDocumentHttpResponse(document.getId()));

		assertHttpResponseStatusCode(
			404, documentResource.getDocumentHttpResponse(document.getId()));

		assertHttpResponseStatusCode(
			404, documentResource.getDocumentHttpResponse(0L));
	}

	protected Document testDeleteDocument_addDocument() throws Exception {
		return documentResource.postSiteDocument(
			testGroup.getGroupId(), randomDocument(), getMultipartFiles());
	}

	@Test
	public void testGraphQLDeleteDocument() throws Exception {
		Document document = testGraphQLDocument_addDocument();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteDocument",
						new HashMap<String, Object>() {
							{
								put("documentId", document.getId());
							}
						})),
				"JSONObject/data", "Object/deleteDocument"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"document",
						new HashMap<String, Object>() {
							{
								put("documentId", document.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetDocument() throws Exception {
		Document postDocument = testGetDocument_addDocument();

		Document getDocument = documentResource.getDocument(
			postDocument.getId());

		assertEquals(postDocument, getDocument);
		assertValid(getDocument);
	}

	protected Document testGetDocument_addDocument() throws Exception {
		return documentResource.postSiteDocument(
			testGroup.getGroupId(), randomDocument(), getMultipartFiles());
	}

	@Test
	public void testGraphQLGetDocument() throws Exception {
		Document document = testGraphQLDocument_addDocument();

		Assert.assertTrue(
			equals(
				document,
				DocumentSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"document",
								new HashMap<String, Object>() {
									{
										put("documentId", document.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/document"))));
	}

	@Test
	public void testGraphQLGetDocumentNotFound() throws Exception {
		Long irrelevantDocumentId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"document",
						new HashMap<String, Object>() {
							{
								put("documentId", irrelevantDocumentId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchDocument() throws Exception {
		Document postDocument = testPatchDocument_addDocument();

		Document randomPatchDocument = randomPatchDocument();

		Map<String, File> multipartFiles = getMultipartFiles();

		Document patchDocument = documentResource.patchDocument(
			postDocument.getId(), randomPatchDocument, multipartFiles);

		Document expectedPatchDocument = postDocument.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchDocument, randomPatchDocument);

		Document getDocument = documentResource.getDocument(
			patchDocument.getId());

		assertEquals(expectedPatchDocument, getDocument);
		assertValid(getDocument);

		assertValid(getDocument, multipartFiles);
	}

	protected Document testPatchDocument_addDocument() throws Exception {
		return documentResource.postSiteDocument(
			testGroup.getGroupId(), randomDocument(), getMultipartFiles());
	}

	@Test
	public void testPutDocument() throws Exception {
		Document postDocument = testPutDocument_addDocument();

		Document randomDocument = randomDocument();

		Map<String, File> multipartFiles = getMultipartFiles();

		Document putDocument = documentResource.putDocument(
			postDocument.getId(), randomDocument, multipartFiles);

		assertEquals(randomDocument, putDocument);
		assertValid(putDocument);

		Document getDocument = documentResource.getDocument(
			putDocument.getId());

		assertEquals(randomDocument, getDocument);
		assertValid(getDocument);

		assertValid(getDocument, multipartFiles);
	}

	protected Document testPutDocument_addDocument() throws Exception {
		return documentResource.postSiteDocument(
			testGroup.getGroupId(), randomDocument(), getMultipartFiles());
	}

	@Test
	public void testDeleteDocumentMyRating() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Document document = testDeleteDocumentMyRating_addDocument();

		assertHttpResponseStatusCode(
			204,
			documentResource.deleteDocumentMyRatingHttpResponse(
				document.getId()));

		assertHttpResponseStatusCode(
			404,
			documentResource.getDocumentMyRatingHttpResponse(document.getId()));

		assertHttpResponseStatusCode(
			404, documentResource.getDocumentMyRatingHttpResponse(0L));
	}

	protected Document testDeleteDocumentMyRating_addDocument()
		throws Exception {

		return documentResource.postSiteDocument(
			testGroup.getGroupId(), randomDocument(), getMultipartFiles());
	}

	@Test
	public void testGetSiteDocumentsPage() throws Exception {
		Page<Document> page = documentResource.getSiteDocumentsPage(
			testGetSiteDocumentsPage_getSiteId(), null,
			RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
			null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteDocumentsPage_getSiteId();
		Long irrelevantSiteId = testGetSiteDocumentsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			Document irrelevantDocument = testGetSiteDocumentsPage_addDocument(
				irrelevantSiteId, randomIrrelevantDocument());

			page = documentResource.getSiteDocumentsPage(
				irrelevantSiteId, null, null, null, null, Pagination.of(1, 2),
				null);

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

		page = documentResource.getSiteDocumentsPage(
			siteId, null, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(document1, document2),
			(List<Document>)page.getItems());
		assertValid(page);

		documentResource.deleteDocument(document1.getId());

		documentResource.deleteDocument(document2.getId());
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
			Page<Document> page = documentResource.getSiteDocumentsPage(
				siteId, null, null, null,
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
			Page<Document> page = documentResource.getSiteDocumentsPage(
				siteId, null, null, null,
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

		Page<Document> page1 = documentResource.getSiteDocumentsPage(
			siteId, null, null, null, null, Pagination.of(1, 2), null);

		List<Document> documents1 = (List<Document>)page1.getItems();

		Assert.assertEquals(documents1.toString(), 2, documents1.size());

		Page<Document> page2 = documentResource.getSiteDocumentsPage(
			siteId, null, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Document> documents2 = (List<Document>)page2.getItems();

		Assert.assertEquals(documents2.toString(), 1, documents2.size());

		Page<Document> page3 = documentResource.getSiteDocumentsPage(
			siteId, null, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(document1, document2, document3),
			(List<Document>)page3.getItems());
	}

	@Test
	public void testGetSiteDocumentsPageWithSortDateTime() throws Exception {
		testGetSiteDocumentsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, document1, document2) -> {
				BeanUtils.setProperty(
					document1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteDocumentsPageWithSortInteger() throws Exception {
		testGetSiteDocumentsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, document1, document2) -> {
				BeanUtils.setProperty(document1, entityField.getName(), 0);
				BeanUtils.setProperty(document2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteDocumentsPageWithSortString() throws Exception {
		testGetSiteDocumentsPageWithSort(
			EntityField.Type.STRING,
			(entityField, document1, document2) -> {
				Class<?> clazz = document1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						document1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						document2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						document1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						document2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						document1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						document2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSiteDocumentsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Document, Document, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteDocumentsPage_getSiteId();

		Document document1 = randomDocument();
		Document document2 = randomDocument();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, document1, document2);
		}

		document1 = testGetSiteDocumentsPage_addDocument(siteId, document1);

		document2 = testGetSiteDocumentsPage_addDocument(siteId, document2);

		for (EntityField entityField : entityFields) {
			Page<Document> ascPage = documentResource.getSiteDocumentsPage(
				siteId, null, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(document1, document2),
				(List<Document>)ascPage.getItems());

			Page<Document> descPage = documentResource.getSiteDocumentsPage(
				siteId, null, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(document2, document1),
				(List<Document>)descPage.getItems());
		}
	}

	protected Document testGetSiteDocumentsPage_addDocument(
			Long siteId, Document document)
		throws Exception {

		return documentResource.postSiteDocument(
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
	public void testGraphQLGetSiteDocumentsPage() throws Exception {
		Long siteId = testGetSiteDocumentsPage_getSiteId();

		GraphQLField graphQLField = new GraphQLField(
			"documents",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);

					put("siteKey", "\"" + siteId + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject documentsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/documents");

		Assert.assertEquals(0, documentsJSONObject.get("totalCount"));

		Document document1 = testGraphQLDocument_addDocument();
		Document document2 = testGraphQLDocument_addDocument();

		documentsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/documents");

		Assert.assertEquals(2, documentsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(document1, document2),
			Arrays.asList(
				DocumentSerDes.toDTOs(documentsJSONObject.getString("items"))));
	}

	@Test
	public void testPostSiteDocument() throws Exception {
		Document randomDocument = randomDocument();

		Map<String, File> multipartFiles = getMultipartFiles();

		Document postDocument = testPostSiteDocument_addDocument(
			randomDocument, multipartFiles);

		assertEquals(randomDocument, postDocument);
		assertValid(postDocument);

		assertValid(postDocument, multipartFiles);
	}

	protected Document testPostSiteDocument_addDocument(
			Document document, Map<String, File> multipartFiles)
		throws Exception {

		return documentResource.postSiteDocument(
			testGetSiteDocumentsPage_getSiteId(), document, multipartFiles);
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	@Test
	public void testGetDocumentMyRating() throws Exception {
		Document postDocument = testGetDocument_addDocument();

		Rating postRating = testGetDocumentMyRating_addRating(
			postDocument.getId(), randomRating());

		Rating getRating = documentResource.getDocumentMyRating(
			postDocument.getId());

		assertEquals(postRating, getRating);
		assertValid(getRating);
	}

	protected Rating testGetDocumentMyRating_addRating(
			long documentId, Rating rating)
		throws Exception {

		return documentResource.postDocumentMyRating(documentId, rating);
	}

	@Test
	public void testPostDocumentMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutDocumentMyRating() throws Exception {
		Document postDocument = testPutDocument_addDocument();

		testPutDocumentMyRating_addRating(postDocument.getId(), randomRating());

		Rating randomRating = randomRating();

		Rating putRating = documentResource.putDocumentMyRating(
			postDocument.getId(), randomRating);

		assertEquals(randomRating, putRating);
		assertValid(putRating);
	}

	protected Rating testPutDocumentMyRating_addRating(
			long documentId, Rating rating)
		throws Exception {

		return documentResource.postDocumentMyRating(documentId, rating);
	}

	protected void appendGraphQLFieldValue(StringBuilder sb, Object value)
		throws Exception {

		if (value instanceof Object[]) {
			StringBuilder arraySB = new StringBuilder("[");

			for (Object object : (Object[])value) {
				if (arraySB.length() > 1) {
					arraySB.append(",");
				}

				arraySB.append("{");

				Class<?> clazz = object.getClass();

				for (Field field :
						ReflectionUtil.getDeclaredFields(
							clazz.getSuperclass())) {

					arraySB.append(field.getName());
					arraySB.append(": ");

					appendGraphQLFieldValue(arraySB, field.get(object));

					arraySB.append(",");
				}

				arraySB.setLength(arraySB.length() - 1);

				arraySB.append("}");
			}

			arraySB.append("]");

			sb.append(arraySB.toString());
		}
		else if (value instanceof String) {
			sb.append("\"");
			sb.append(value);
			sb.append("\"");
		}
		else {
			sb.append(value);
		}
	}

	protected Document testGraphQLDocument_addDocument() throws Exception {
		return testGraphQLDocument_addDocument(randomDocument());
	}

	protected Document testGraphQLDocument_addDocument(Document document)
		throws Exception {

		JSONDeserializer<Document> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		StringBuilder sb = new StringBuilder("{");

		for (Field field : ReflectionUtil.getDeclaredFields(Document.class)) {
			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append(field.getName());
			sb.append(": ");

			appendGraphQLFieldValue(sb, field.get(document));
		}

		sb.append("}");

		List<GraphQLField> graphQLFields = getGraphQLFields();

		graphQLFields.add(new GraphQLField("id"));

		return jsonDeserializer.deserialize(
			JSONUtil.getValueAsString(
				invokeGraphQLMutation(
					new GraphQLField(
						"createSiteDocument",
						new HashMap<String, Object>() {
							{
								put(
									"siteKey",
									"\"" + testGroup.getGroupId() + "\"");
								put("document", sb.toString());
							}
						},
						graphQLFields)),
				"JSONObject/data", "JSONObject/createSiteDocument"),
			Document.class);
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

	protected void assertEquals(Rating rating1, Rating rating2) {
		Assert.assertTrue(
			rating1 + " does not equal " + rating2, equals(rating1, rating2));
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

	protected void assertValid(Document document) throws Exception {
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

		Group group = testDepotEntry.getGroup();

		if (!Objects.equals(
				document.getAssetLibraryKey(), group.getGroupKey()) &&
			!Objects.equals(document.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (document.getActions() == null) {
					valid = false;
				}

				continue;
			}

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

			if (Objects.equals("assetLibraryKey", additionalAssertFieldName)) {
				if (document.getAssetLibraryKey() == null) {
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

			if (Objects.equals("contentValue", additionalAssertFieldName)) {
				if (document.getContentValue() == null) {
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

			if (Objects.equals("documentType", additionalAssertFieldName)) {
				if (document.getDocumentType() == null) {
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
					"taxonomyCategoryBriefs", additionalAssertFieldName)) {

				if (document.getTaxonomyCategoryBriefs() == null) {
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

	protected void assertValid(
			Document document, Map<String, File> multipartFiles)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertValid(Page<Document> page) {
		boolean valid = false;

		java.util.Collection<Document> documents = page.getItems();

		int size = documents.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	protected void assertValid(Rating rating) {
		boolean valid = true;

		if (rating.getDateCreated() == null) {
			valid = false;
		}

		if (rating.getDateModified() == null) {
			valid = false;
		}

		if (rating.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalRatingAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (rating.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("bestRating", additionalAssertFieldName)) {
				if (rating.getBestRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (rating.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("ratingValue", additionalAssertFieldName)) {
				if (rating.getRatingValue() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("worstRating", additionalAssertFieldName)) {
				if (rating.getWorstRating() == null) {
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

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected String[] getAdditionalRatingAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("siteId"));

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.headless.delivery.dto.v1_0.Document.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (Field field : fields) {
			com.liferay.portal.vulcan.graphql.annotation.GraphQLField
				vulcanGraphQLField = field.getAnnotation(
					com.liferay.portal.vulcan.graphql.annotation.GraphQLField.
						class);

			if (vulcanGraphQLField != null) {
				Class<?> clazz = field.getType();

				if (clazz.isArray()) {
					clazz = clazz.getComponentType();
				}

				List<GraphQLField> childrenGraphQLFields = getGraphQLFields(
					ReflectionUtil.getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(Document document1, Document document2) {
		if (document1 == document2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)document1.getActions(),
						(Map)document2.getActions())) {

					return false;
				}

				continue;
			}

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

			if (Objects.equals("contentValue", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getContentValue(),
						document2.getContentValue())) {

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

			if (Objects.equals("documentType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						document1.getDocumentType(),
						document2.getDocumentType())) {

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
					"taxonomyCategoryBriefs", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						document1.getTaxonomyCategoryBriefs(),
						document2.getTaxonomyCategoryBriefs())) {

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

	protected boolean equals(
		Map<String, Object> map1, Map<String, Object> map2) {

		if (Objects.equals(map1.keySet(), map2.keySet())) {
			for (Map.Entry<String, Object> entry : map1.entrySet()) {
				if (entry.getValue() instanceof Map) {
					if (!equals(
							(Map)entry.getValue(),
							(Map)map2.get(entry.getKey()))) {

						return false;
					}
				}
				else if (!Objects.deepEquals(
							entry.getValue(), map2.get(entry.getKey()))) {

					return false;
				}
			}
		}

		return true;
	}

	protected boolean equals(Rating rating1, Rating rating2) {
		if (rating1 == rating2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalRatingAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getActions(), rating2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("bestRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getBestRating(), rating2.getBestRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getCreator(), rating2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getDateCreated(), rating2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getDateModified(), rating2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(rating1.getId(), rating2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("ratingValue", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getRatingValue(), rating2.getRatingValue())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("worstRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						rating1.getWorstRating(), rating2.getWorstRating())) {

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

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

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

		java.util.Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField ->
				Objects.equals(entityField.getType(), type) &&
				!ArrayUtil.contains(
					getIgnoredEntityFieldNames(), entityField.getName())
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

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("adaptedImages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("aggregateRating")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("assetLibraryKey")) {
			sb.append("'");
			sb.append(String.valueOf(document.getAssetLibraryKey()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("contentUrl")) {
			sb.append("'");
			sb.append(String.valueOf(document.getContentUrl()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("contentValue")) {
			sb.append("'");
			sb.append(String.valueOf(document.getContentValue()));
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

		if (entityFieldName.equals("documentType")) {
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

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sizeInBytes")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxonomyCategoryBriefs")) {
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

	protected String invoke(String query) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.body(
			JSONUtil.put(
				"query", query
			).toString(),
			"application/json");
		httpInvoker.httpMethod(HttpInvoker.HttpMethod.POST);
		httpInvoker.path("http://localhost:8080/o/graphql");
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	protected JSONObject invokeGraphQLMutation(GraphQLField graphQLField)
		throws Exception {

		GraphQLField mutationGraphQLField = new GraphQLField(
			"mutation", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(mutationGraphQLField.toString()));
	}

	protected JSONObject invokeGraphQLQuery(GraphQLField graphQLField)
		throws Exception {

		GraphQLField queryGraphQLField = new GraphQLField(
			"query", graphQLField);

		return JSONFactoryUtil.createJSONObject(
			invoke(queryGraphQLField.toString()));
	}

	protected Document randomDocument() throws Exception {
		return new Document() {
			{
				assetLibraryKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				contentUrl = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				contentValue = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				documentFolderId = RandomTestUtil.randomLong();
				encodingFormat = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				fileExtension = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				numberOfComments = RandomTestUtil.randomInt();
				siteId = testGroup.getGroupId();
				sizeInBytes = RandomTestUtil.randomLong();
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected Document randomIrrelevantDocument() throws Exception {
		Document randomIrrelevantDocument = randomDocument();

		randomIrrelevantDocument.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantDocument;
	}

	protected Document randomPatchDocument() throws Exception {
		return randomDocument();
	}

	protected Rating randomRating() throws Exception {
		return new Rating() {
			{
				bestRating = RandomTestUtil.randomDouble();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				ratingValue = RandomTestUtil.randomDouble();
				worstRating = RandomTestUtil.randomDouble();
			}
		};
	}

	protected DocumentResource documentResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected DepotEntry testDepotEntry;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(String key, List<GraphQLField> graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = Arrays.asList(graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			List<GraphQLField> graphQLFields) {

			_key = key;
			_parameterMap = parameterMap;
			_graphQLFields = graphQLFields;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder(_key);

			if (!_parameterMap.isEmpty()) {
				sb.append("(");

				for (Map.Entry<String, Object> entry :
						_parameterMap.entrySet()) {

					sb.append(entry.getKey());
					sb.append(":");
					sb.append(entry.getValue());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(",");
				}

				sb.setLength(sb.length() - 1);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

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
	private com.liferay.headless.delivery.resource.v1_0.DocumentResource
		_documentResource;

}