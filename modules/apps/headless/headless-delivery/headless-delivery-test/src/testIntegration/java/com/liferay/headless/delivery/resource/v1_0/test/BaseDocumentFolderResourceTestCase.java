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

import com.liferay.headless.delivery.client.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.DocumentFolderResource;
import com.liferay.headless.delivery.client.serdes.v1_0.DocumentFolderSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
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
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

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

		testCompany = CompanyLocalServiceUtil.getCompany(
			testGroup.getCompanyId());

		_documentFolderResource.setContextCompany(testCompany);

		DocumentFolderResource.Builder builder =
			DocumentFolderResource.builder();

		documentFolderResource = builder.locale(
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

		DocumentFolder documentFolder = randomDocumentFolder();

		String json1 = objectMapper.writeValueAsString(documentFolder);
		String json2 = DocumentFolderSerDes.toJSON(documentFolder);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DocumentFolder documentFolder = randomDocumentFolder();

		documentFolder.setDescription(regex);
		documentFolder.setName(regex);

		String json = DocumentFolderSerDes.toJSON(documentFolder);

		Assert.assertFalse(json.contains(regex));

		documentFolder = DocumentFolderSerDes.toDTO(json);

		Assert.assertEquals(regex, documentFolder.getDescription());
		Assert.assertEquals(regex, documentFolder.getName());
	}

	@Test
	public void testDeleteDocumentFolder() throws Exception {
		DocumentFolder documentFolder =
			testDeleteDocumentFolder_addDocumentFolder();

		assertHttpResponseStatusCode(
			204,
			documentFolderResource.deleteDocumentFolderHttpResponse(
				documentFolder.getId()));

		assertHttpResponseStatusCode(
			404,
			documentFolderResource.getDocumentFolderHttpResponse(
				documentFolder.getId()));

		assertHttpResponseStatusCode(
			404, documentFolderResource.getDocumentFolderHttpResponse(0L));
	}

	protected DocumentFolder testDeleteDocumentFolder_addDocumentFolder()
		throws Exception {

		return documentFolderResource.postSiteDocumentFolder(
			testGroup.getGroupId(), randomDocumentFolder());
	}

	@Test
	public void testGraphQLDeleteDocumentFolder() throws Exception {
		DocumentFolder documentFolder =
			testGraphQLDocumentFolder_addDocumentFolder();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteDocumentFolder",
				new HashMap<String, Object>() {
					{
						put("documentFolderId", documentFolder.getId());
					}
				}));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(dataJSONObject.getBoolean("deleteDocumentFolder"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			graphQLField = new GraphQLField(
				"query",
				new GraphQLField(
					"documentFolder",
					new HashMap<String, Object>() {
						{
							put("documentFolderId", documentFolder.getId());
						}
					},
					new GraphQLField("id")));

			jsonObject = JSONFactoryUtil.createJSONObject(
				invoke(graphQLField.toString()));

			JSONArray errorsJSONArray = jsonObject.getJSONArray("errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetDocumentFolder() throws Exception {
		DocumentFolder postDocumentFolder =
			testGetDocumentFolder_addDocumentFolder();

		DocumentFolder getDocumentFolder =
			documentFolderResource.getDocumentFolder(
				postDocumentFolder.getId());

		assertEquals(postDocumentFolder, getDocumentFolder);
		assertValid(getDocumentFolder);
	}

	protected DocumentFolder testGetDocumentFolder_addDocumentFolder()
		throws Exception {

		return documentFolderResource.postSiteDocumentFolder(
			testGroup.getGroupId(), randomDocumentFolder());
	}

	@Test
	public void testGraphQLGetDocumentFolder() throws Exception {
		DocumentFolder documentFolder =
			testGraphQLDocumentFolder_addDocumentFolder();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"documentFolder",
				new HashMap<String, Object>() {
					{
						put("documentFolderId", documentFolder.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				documentFolder,
				dataJSONObject.getJSONObject("documentFolder")));
	}

	@Test
	public void testPatchDocumentFolder() throws Exception {
		DocumentFolder postDocumentFolder =
			testPatchDocumentFolder_addDocumentFolder();

		DocumentFolder randomPatchDocumentFolder = randomPatchDocumentFolder();

		DocumentFolder patchDocumentFolder =
			documentFolderResource.patchDocumentFolder(
				postDocumentFolder.getId(), randomPatchDocumentFolder);

		DocumentFolder expectedPatchDocumentFolder =
			(DocumentFolder)BeanUtils.cloneBean(postDocumentFolder);

		_beanUtilsBean.copyProperties(
			expectedPatchDocumentFolder, randomPatchDocumentFolder);

		DocumentFolder getDocumentFolder =
			documentFolderResource.getDocumentFolder(
				patchDocumentFolder.getId());

		assertEquals(expectedPatchDocumentFolder, getDocumentFolder);
		assertValid(getDocumentFolder);
	}

	protected DocumentFolder testPatchDocumentFolder_addDocumentFolder()
		throws Exception {

		return documentFolderResource.postSiteDocumentFolder(
			testGroup.getGroupId(), randomDocumentFolder());
	}

	@Test
	public void testPutDocumentFolder() throws Exception {
		DocumentFolder postDocumentFolder =
			testPutDocumentFolder_addDocumentFolder();

		DocumentFolder randomDocumentFolder = randomDocumentFolder();

		DocumentFolder putDocumentFolder =
			documentFolderResource.putDocumentFolder(
				postDocumentFolder.getId(), randomDocumentFolder);

		assertEquals(randomDocumentFolder, putDocumentFolder);
		assertValid(putDocumentFolder);

		DocumentFolder getDocumentFolder =
			documentFolderResource.getDocumentFolder(putDocumentFolder.getId());

		assertEquals(randomDocumentFolder, getDocumentFolder);
		assertValid(getDocumentFolder);
	}

	protected DocumentFolder testPutDocumentFolder_addDocumentFolder()
		throws Exception {

		return documentFolderResource.postSiteDocumentFolder(
			testGroup.getGroupId(), randomDocumentFolder());
	}

	@Test
	public void testPutDocumentFolderSubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		DocumentFolder documentFolder =
			testPutDocumentFolderSubscribe_addDocumentFolder();

		assertHttpResponseStatusCode(
			204,
			documentFolderResource.putDocumentFolderSubscribeHttpResponse(
				documentFolder.getId()));

		assertHttpResponseStatusCode(
			404,
			documentFolderResource.putDocumentFolderSubscribeHttpResponse(0L));
	}

	protected DocumentFolder testPutDocumentFolderSubscribe_addDocumentFolder()
		throws Exception {

		return documentFolderResource.postSiteDocumentFolder(
			testGroup.getGroupId(), randomDocumentFolder());
	}

	@Test
	public void testPutDocumentFolderUnsubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		DocumentFolder documentFolder =
			testPutDocumentFolderUnsubscribe_addDocumentFolder();

		assertHttpResponseStatusCode(
			204,
			documentFolderResource.putDocumentFolderUnsubscribeHttpResponse(
				documentFolder.getId()));

		assertHttpResponseStatusCode(
			404,
			documentFolderResource.putDocumentFolderUnsubscribeHttpResponse(
				0L));
	}

	protected DocumentFolder
			testPutDocumentFolderUnsubscribe_addDocumentFolder()
		throws Exception {

		return documentFolderResource.postSiteDocumentFolder(
			testGroup.getGroupId(), randomDocumentFolder());
	}

	@Test
	public void testGetDocumentFolderDocumentFoldersPage() throws Exception {
		Page<DocumentFolder> page =
			documentFolderResource.getDocumentFolderDocumentFoldersPage(
				testGetDocumentFolderDocumentFoldersPage_getParentDocumentFolderId(),
				null, RandomTestUtil.randomString(), null, Pagination.of(1, 2),
				null);

		Assert.assertEquals(0, page.getTotalCount());

		Long parentDocumentFolderId =
			testGetDocumentFolderDocumentFoldersPage_getParentDocumentFolderId();
		Long irrelevantParentDocumentFolderId =
			testGetDocumentFolderDocumentFoldersPage_getIrrelevantParentDocumentFolderId();

		if ((irrelevantParentDocumentFolderId != null)) {
			DocumentFolder irrelevantDocumentFolder =
				testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
					irrelevantParentDocumentFolderId,
					randomIrrelevantDocumentFolder());

			page = documentFolderResource.getDocumentFolderDocumentFoldersPage(
				irrelevantParentDocumentFolderId, null, null, null,
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

		page = documentFolderResource.getDocumentFolderDocumentFoldersPage(
			parentDocumentFolderId, null, null, null, Pagination.of(1, 2),
			null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(documentFolder1, documentFolder2),
			(List<DocumentFolder>)page.getItems());
		assertValid(page);

		documentFolderResource.deleteDocumentFolder(documentFolder1.getId());

		documentFolderResource.deleteDocumentFolder(documentFolder2.getId());
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
				documentFolderResource.getDocumentFolderDocumentFoldersPage(
					parentDocumentFolderId, null, null,
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
				documentFolderResource.getDocumentFolderDocumentFoldersPage(
					parentDocumentFolderId, null, null,
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

		Page<DocumentFolder> page1 =
			documentFolderResource.getDocumentFolderDocumentFoldersPage(
				parentDocumentFolderId, null, null, null, Pagination.of(1, 2),
				null);

		List<DocumentFolder> documentFolders1 =
			(List<DocumentFolder>)page1.getItems();

		Assert.assertEquals(
			documentFolders1.toString(), 2, documentFolders1.size());

		Page<DocumentFolder> page2 =
			documentFolderResource.getDocumentFolderDocumentFoldersPage(
				parentDocumentFolderId, null, null, null, Pagination.of(2, 2),
				null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DocumentFolder> documentFolders2 =
			(List<DocumentFolder>)page2.getItems();

		Assert.assertEquals(
			documentFolders2.toString(), 1, documentFolders2.size());

		Page<DocumentFolder> page3 =
			documentFolderResource.getDocumentFolderDocumentFoldersPage(
				parentDocumentFolderId, null, null, null, Pagination.of(1, 3),
				null);

		assertEqualsIgnoringOrder(
			Arrays.asList(documentFolder1, documentFolder2, documentFolder3),
			(List<DocumentFolder>)page3.getItems());
	}

	@Test
	public void testGetDocumentFolderDocumentFoldersPageWithSortDateTime()
		throws Exception {

		testGetDocumentFolderDocumentFoldersPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, documentFolder1, documentFolder2) -> {
				BeanUtils.setProperty(
					documentFolder1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDocumentFolderDocumentFoldersPageWithSortInteger()
		throws Exception {

		testGetDocumentFolderDocumentFoldersPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, documentFolder1, documentFolder2) -> {
				BeanUtils.setProperty(
					documentFolder1, entityField.getName(), 0);
				BeanUtils.setProperty(
					documentFolder2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDocumentFolderDocumentFoldersPageWithSortString()
		throws Exception {

		testGetDocumentFolderDocumentFoldersPageWithSort(
			EntityField.Type.STRING,
			(entityField, documentFolder1, documentFolder2) -> {
				Class<?> clazz = documentFolder1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						documentFolder1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						documentFolder2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						documentFolder1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						documentFolder2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetDocumentFolderDocumentFoldersPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, DocumentFolder, DocumentFolder, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentDocumentFolderId =
			testGetDocumentFolderDocumentFoldersPage_getParentDocumentFolderId();

		DocumentFolder documentFolder1 = randomDocumentFolder();
		DocumentFolder documentFolder2 = randomDocumentFolder();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, documentFolder1, documentFolder2);
		}

		documentFolder1 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, documentFolder1);

		documentFolder2 =
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				parentDocumentFolderId, documentFolder2);

		for (EntityField entityField : entityFields) {
			Page<DocumentFolder> ascPage =
				documentFolderResource.getDocumentFolderDocumentFoldersPage(
					parentDocumentFolderId, null, null, null,
					Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(documentFolder1, documentFolder2),
				(List<DocumentFolder>)ascPage.getItems());

			Page<DocumentFolder> descPage =
				documentFolderResource.getDocumentFolderDocumentFoldersPage(
					parentDocumentFolderId, null, null, null,
					Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(documentFolder2, documentFolder1),
				(List<DocumentFolder>)descPage.getItems());
		}
	}

	protected DocumentFolder
			testGetDocumentFolderDocumentFoldersPage_addDocumentFolder(
				Long parentDocumentFolderId, DocumentFolder documentFolder)
		throws Exception {

		return documentFolderResource.postDocumentFolderDocumentFolder(
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

		return documentFolderResource.postDocumentFolderDocumentFolder(
			testGetDocumentFolderDocumentFoldersPage_getParentDocumentFolderId(),
			documentFolder);
	}

	@Test
	public void testGetSiteDocumentFoldersPage() throws Exception {
		Page<DocumentFolder> page =
			documentFolderResource.getSiteDocumentFoldersPage(
				testGetSiteDocumentFoldersPage_getSiteId(), null,
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteDocumentFoldersPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteDocumentFoldersPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			DocumentFolder irrelevantDocumentFolder =
				testGetSiteDocumentFoldersPage_addDocumentFolder(
					irrelevantSiteId, randomIrrelevantDocumentFolder());

			page = documentFolderResource.getSiteDocumentFoldersPage(
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

		page = documentFolderResource.getSiteDocumentFoldersPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(documentFolder1, documentFolder2),
			(List<DocumentFolder>)page.getItems());
		assertValid(page);

		documentFolderResource.deleteDocumentFolder(documentFolder1.getId());

		documentFolderResource.deleteDocumentFolder(documentFolder2.getId());
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
			Page<DocumentFolder> page =
				documentFolderResource.getSiteDocumentFoldersPage(
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
			Page<DocumentFolder> page =
				documentFolderResource.getSiteDocumentFoldersPage(
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

		Page<DocumentFolder> page1 =
			documentFolderResource.getSiteDocumentFoldersPage(
				siteId, null, null, null, Pagination.of(1, 2), null);

		List<DocumentFolder> documentFolders1 =
			(List<DocumentFolder>)page1.getItems();

		Assert.assertEquals(
			documentFolders1.toString(), 2, documentFolders1.size());

		Page<DocumentFolder> page2 =
			documentFolderResource.getSiteDocumentFoldersPage(
				siteId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DocumentFolder> documentFolders2 =
			(List<DocumentFolder>)page2.getItems();

		Assert.assertEquals(
			documentFolders2.toString(), 1, documentFolders2.size());

		Page<DocumentFolder> page3 =
			documentFolderResource.getSiteDocumentFoldersPage(
				siteId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(documentFolder1, documentFolder2, documentFolder3),
			(List<DocumentFolder>)page3.getItems());
	}

	@Test
	public void testGetSiteDocumentFoldersPageWithSortDateTime()
		throws Exception {

		testGetSiteDocumentFoldersPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, documentFolder1, documentFolder2) -> {
				BeanUtils.setProperty(
					documentFolder1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteDocumentFoldersPageWithSortInteger()
		throws Exception {

		testGetSiteDocumentFoldersPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, documentFolder1, documentFolder2) -> {
				BeanUtils.setProperty(
					documentFolder1, entityField.getName(), 0);
				BeanUtils.setProperty(
					documentFolder2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteDocumentFoldersPageWithSortString()
		throws Exception {

		testGetSiteDocumentFoldersPageWithSort(
			EntityField.Type.STRING,
			(entityField, documentFolder1, documentFolder2) -> {
				Class<?> clazz = documentFolder1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						documentFolder1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						documentFolder2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						documentFolder1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						documentFolder2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetSiteDocumentFoldersPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, DocumentFolder, DocumentFolder, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteDocumentFoldersPage_getSiteId();

		DocumentFolder documentFolder1 = randomDocumentFolder();
		DocumentFolder documentFolder2 = randomDocumentFolder();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, documentFolder1, documentFolder2);
		}

		documentFolder1 = testGetSiteDocumentFoldersPage_addDocumentFolder(
			siteId, documentFolder1);

		documentFolder2 = testGetSiteDocumentFoldersPage_addDocumentFolder(
			siteId, documentFolder2);

		for (EntityField entityField : entityFields) {
			Page<DocumentFolder> ascPage =
				documentFolderResource.getSiteDocumentFoldersPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(documentFolder1, documentFolder2),
				(List<DocumentFolder>)ascPage.getItems());

			Page<DocumentFolder> descPage =
				documentFolderResource.getSiteDocumentFoldersPage(
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

		return documentFolderResource.postSiteDocumentFolder(
			siteId, documentFolder);
	}

	protected Long testGetSiteDocumentFoldersPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteDocumentFoldersPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteDocumentFoldersPage() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		List<GraphQLField> itemsGraphQLFields = getGraphQLFields();

		graphQLFields.add(
			new GraphQLField(
				"items", itemsGraphQLFields.toArray(new GraphQLField[0])));

		graphQLFields.add(new GraphQLField("page"));
		graphQLFields.add(new GraphQLField("totalCount"));

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"documentFolders",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 2);
						put("siteId", testGroup.getGroupId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject documentFoldersJSONObject = dataJSONObject.getJSONObject(
			"documentFolders");

		Assert.assertEquals(0, documentFoldersJSONObject.get("totalCount"));

		DocumentFolder documentFolder1 =
			testGraphQLDocumentFolder_addDocumentFolder();
		DocumentFolder documentFolder2 =
			testGraphQLDocumentFolder_addDocumentFolder();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		documentFoldersJSONObject = dataJSONObject.getJSONObject(
			"documentFolders");

		Assert.assertEquals(2, documentFoldersJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(documentFolder1, documentFolder2),
			documentFoldersJSONObject.getJSONArray("items"));
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

		return documentFolderResource.postSiteDocumentFolder(
			testGetSiteDocumentFoldersPage_getSiteId(), documentFolder);
	}

	@Test
	public void testGraphQLPostSiteDocumentFolder() throws Exception {
		DocumentFolder randomDocumentFolder = randomDocumentFolder();

		DocumentFolder documentFolder =
			testGraphQLDocumentFolder_addDocumentFolder(randomDocumentFolder);

		Assert.assertTrue(
			equalsJSONObject(
				randomDocumentFolder,
				JSONFactoryUtil.createJSONObject(
					JSONFactoryUtil.serialize(documentFolder))));
	}

	protected DocumentFolder testGraphQLDocumentFolder_addDocumentFolder()
		throws Exception {

		return testGraphQLDocumentFolder_addDocumentFolder(
			randomDocumentFolder());
	}

	protected DocumentFolder testGraphQLDocumentFolder_addDocumentFolder(
			DocumentFolder documentFolder)
		throws Exception {

		StringBuilder sb = new StringBuilder("{");

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("description", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = documentFolder.getDescription();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = documentFolder.getId();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = documentFolder.getName();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals(
					"numberOfDocumentFolders", additionalAssertFieldName)) {

				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = documentFolder.getNumberOfDocumentFolders();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals(
					"numberOfDocuments", additionalAssertFieldName)) {

				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = documentFolder.getNumberOfDocuments();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("siteId", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = documentFolder.getSiteId();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = documentFolder.getSubscribed();

				if (value instanceof String) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}

				sb.append(", ");
			}
		}

		sb.append("}");

		List<GraphQLField> graphQLFields = getGraphQLFields();

		graphQLFields.add(new GraphQLField("id"));

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"createSiteDocumentFolder",
				new HashMap<String, Object>() {
					{
						put("siteId", testGroup.getGroupId());
						put("documentFolder", sb.toString());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONDeserializer<DocumentFolder> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		String object = invoke(graphQLField.toString());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(object);

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		return jsonDeserializer.deserialize(
			String.valueOf(
				dataJSONObject.getJSONObject("createSiteDocumentFolder")),
			DocumentFolder.class);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
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

	protected void assertEqualsJSONArray(
		List<DocumentFolder> documentFolders, JSONArray jsonArray) {

		for (DocumentFolder documentFolder : documentFolders) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(documentFolder, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + documentFolder, contains);
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

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (documentFolder.getCustomFields() == null) {
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

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (documentFolder.getSubscribed() == null) {
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

		java.util.Collection<DocumentFolder> documentFolders = page.getItems();

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

	protected List<GraphQLField> getGraphQLFields() {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			graphQLFields.add(new GraphQLField(additionalAssertFieldName));
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
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

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						documentFolder1.getCustomFields(),
						documentFolder2.getCustomFields())) {

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

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						documentFolder1.getSubscribed(),
						documentFolder2.getSubscribed())) {

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

	protected boolean equalsJSONObject(
		DocumentFolder documentFolder, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("description", fieldName)) {
				if (!Objects.deepEquals(
						documentFolder.getDescription(),
						jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						documentFolder.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						documentFolder.getName(),
						jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfDocumentFolders", fieldName)) {
				if (!Objects.deepEquals(
						documentFolder.getNumberOfDocumentFolders(),
						jsonObject.getInt("numberOfDocumentFolders"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfDocuments", fieldName)) {
				if (!Objects.deepEquals(
						documentFolder.getNumberOfDocuments(),
						jsonObject.getInt("numberOfDocuments"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscribed", fieldName)) {
				if (!Objects.deepEquals(
						documentFolder.getSubscribed(),
						jsonObject.getBoolean("subscribed"))) {

					return false;
				}

				continue;
			}

			throw new IllegalArgumentException(
				"Invalid field name " + fieldName);
		}

		return true;
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

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

		if (entityFieldName.equals("subscribed")) {
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

	protected DocumentFolder randomDocumentFolder() throws Exception {
		return new DocumentFolder() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				numberOfDocumentFolders = RandomTestUtil.randomInt();
				numberOfDocuments = RandomTestUtil.randomInt();
				siteId = testGroup.getGroupId();
				subscribed = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected DocumentFolder randomIrrelevantDocumentFolder() throws Exception {
		DocumentFolder randomIrrelevantDocumentFolder = randomDocumentFolder();

		randomIrrelevantDocumentFolder.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantDocumentFolder;
	}

	protected DocumentFolder randomPatchDocumentFolder() throws Exception {
		return randomDocumentFolder();
	}

	protected DocumentFolderResource documentFolderResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected class GraphQLField {

		public GraphQLField(String key, GraphQLField... graphQLFields) {
			this(key, new HashMap<>(), graphQLFields);
		}

		public GraphQLField(
			String key, Map<String, Object> parameterMap,
			GraphQLField... graphQLFields) {

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

			if (_graphQLFields.length > 0) {
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

		private final GraphQLField[] _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

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
	private com.liferay.headless.delivery.resource.v1_0.DocumentFolderResource
		_documentFolderResource;

}