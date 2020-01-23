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

package com.liferay.data.engine.rest.resource.v2_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.data.engine.rest.client.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.pagination.Page;
import com.liferay.data.engine.rest.client.pagination.Pagination;
import com.liferay.data.engine.rest.client.permission.Permission;
import com.liferay.data.engine.rest.client.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.client.serdes.v2_0.DataDefinitionSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
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
 * @author Jeyvison Nascimento
 * @generated
 */
@Generated("")
public abstract class BaseDataDefinitionResourceTestCase {

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

		_dataDefinitionResource.setContextCompany(testCompany);

		DataDefinitionResource.Builder builder =
			DataDefinitionResource.builder();

		dataDefinitionResource = builder.locale(
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

		DataDefinition dataDefinition1 = randomDataDefinition();

		String json = objectMapper.writeValueAsString(dataDefinition1);

		DataDefinition dataDefinition2 = DataDefinitionSerDes.toDTO(json);

		Assert.assertTrue(equals(dataDefinition1, dataDefinition2));
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

		DataDefinition dataDefinition = randomDataDefinition();

		String json1 = objectMapper.writeValueAsString(dataDefinition);
		String json2 = DataDefinitionSerDes.toJSON(dataDefinition);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DataDefinition dataDefinition = randomDataDefinition();

		dataDefinition.setContentType(regex);
		dataDefinition.setDataDefinitionKey(regex);
		dataDefinition.setDefaultLanguageId(regex);
		dataDefinition.setStorageType(regex);

		String json = DataDefinitionSerDes.toJSON(dataDefinition);

		Assert.assertFalse(json.contains(regex));

		dataDefinition = DataDefinitionSerDes.toDTO(json);

		Assert.assertEquals(regex, dataDefinition.getContentType());
		Assert.assertEquals(regex, dataDefinition.getDataDefinitionKey());
		Assert.assertEquals(regex, dataDefinition.getDefaultLanguageId());
		Assert.assertEquals(regex, dataDefinition.getStorageType());
	}

	@Test
	public void testGetDataDefinitionByContentTypeContentTypePage()
		throws Exception {

		Page<DataDefinition> page =
			dataDefinitionResource.
				getDataDefinitionByContentTypeContentTypePage(
					testGetDataDefinitionByContentTypeContentTypePage_getContentType(),
					RandomTestUtil.randomString(), Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		String contentType =
			testGetDataDefinitionByContentTypeContentTypePage_getContentType();
		String irrelevantContentType =
			testGetDataDefinitionByContentTypeContentTypePage_getIrrelevantContentType();

		if ((irrelevantContentType != null)) {
			DataDefinition irrelevantDataDefinition =
				testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
					irrelevantContentType, randomIrrelevantDataDefinition());

			page =
				dataDefinitionResource.
					getDataDefinitionByContentTypeContentTypePage(
						irrelevantContentType, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDataDefinition),
				(List<DataDefinition>)page.getItems());
			assertValid(page);
		}

		DataDefinition dataDefinition1 =
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				contentType, randomDataDefinition());

		DataDefinition dataDefinition2 =
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				contentType, randomDataDefinition());

		page =
			dataDefinitionResource.
				getDataDefinitionByContentTypeContentTypePage(
					contentType, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition1, dataDefinition2),
			(List<DataDefinition>)page.getItems());
		assertValid(page);

		dataDefinitionResource.deleteDataDefinition(dataDefinition1.getId());

		dataDefinitionResource.deleteDataDefinition(dataDefinition2.getId());
	}

	@Test
	public void testGetDataDefinitionByContentTypeContentTypePageWithPagination()
		throws Exception {

		String contentType =
			testGetDataDefinitionByContentTypeContentTypePage_getContentType();

		DataDefinition dataDefinition1 =
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				contentType, randomDataDefinition());

		DataDefinition dataDefinition2 =
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				contentType, randomDataDefinition());

		DataDefinition dataDefinition3 =
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				contentType, randomDataDefinition());

		Page<DataDefinition> page1 =
			dataDefinitionResource.
				getDataDefinitionByContentTypeContentTypePage(
					contentType, null, Pagination.of(1, 2), null);

		List<DataDefinition> dataDefinitions1 =
			(List<DataDefinition>)page1.getItems();

		Assert.assertEquals(
			dataDefinitions1.toString(), 2, dataDefinitions1.size());

		Page<DataDefinition> page2 =
			dataDefinitionResource.
				getDataDefinitionByContentTypeContentTypePage(
					contentType, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataDefinition> dataDefinitions2 =
			(List<DataDefinition>)page2.getItems();

		Assert.assertEquals(
			dataDefinitions2.toString(), 1, dataDefinitions2.size());

		Page<DataDefinition> page3 =
			dataDefinitionResource.
				getDataDefinitionByContentTypeContentTypePage(
					contentType, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition1, dataDefinition2, dataDefinition3),
			(List<DataDefinition>)page3.getItems());
	}

	@Test
	public void testGetDataDefinitionByContentTypeContentTypePageWithSortDateTime()
		throws Exception {

		testGetDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, dataDefinition1, dataDefinition2) -> {
				BeanUtils.setProperty(
					dataDefinition1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDataDefinitionByContentTypeContentTypePageWithSortInteger()
		throws Exception {

		testGetDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type.INTEGER,
			(entityField, dataDefinition1, dataDefinition2) -> {
				BeanUtils.setProperty(
					dataDefinition1, entityField.getName(), 0);
				BeanUtils.setProperty(
					dataDefinition2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDataDefinitionByContentTypeContentTypePageWithSortString()
		throws Exception {

		testGetDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type.STRING,
			(entityField, dataDefinition1, dataDefinition2) -> {
				Class<?> clazz = dataDefinition1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						dataDefinition1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						dataDefinition2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						dataDefinition1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						dataDefinition2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, DataDefinition, DataDefinition, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String contentType =
			testGetDataDefinitionByContentTypeContentTypePage_getContentType();

		DataDefinition dataDefinition1 = randomDataDefinition();
		DataDefinition dataDefinition2 = randomDataDefinition();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, dataDefinition1, dataDefinition2);
		}

		dataDefinition1 =
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				contentType, dataDefinition1);

		dataDefinition2 =
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				contentType, dataDefinition2);

		for (EntityField entityField : entityFields) {
			Page<DataDefinition> ascPage =
				dataDefinitionResource.
					getDataDefinitionByContentTypeContentTypePage(
						contentType, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(dataDefinition1, dataDefinition2),
				(List<DataDefinition>)ascPage.getItems());

			Page<DataDefinition> descPage =
				dataDefinitionResource.
					getDataDefinitionByContentTypeContentTypePage(
						contentType, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(dataDefinition2, dataDefinition1),
				(List<DataDefinition>)descPage.getItems());
		}
	}

	protected DataDefinition
			testGetDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				String contentType, DataDefinition dataDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDataDefinitionByContentTypeContentTypePage_getContentType()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDataDefinitionByContentTypeContentTypePage_getIrrelevantContentType()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDataDefinitionByContentType() throws Exception {
		DataDefinition randomDataDefinition = randomDataDefinition();

		DataDefinition postDataDefinition =
			testPostDataDefinitionByContentType_addDataDefinition(
				randomDataDefinition);

		assertEquals(randomDataDefinition, postDataDefinition);
		assertValid(postDataDefinition);
	}

	protected DataDefinition
			testPostDataDefinitionByContentType_addDataDefinition(
				DataDefinition dataDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDataDefinitionDataDefinitionFieldFieldTypes()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteDataDefinition() throws Exception {
		DataDefinition dataDefinition =
			testDeleteDataDefinition_addDataDefinition();

		assertHttpResponseStatusCode(
			204,
			dataDefinitionResource.deleteDataDefinitionHttpResponse(
				dataDefinition.getId()));

		assertHttpResponseStatusCode(
			404,
			dataDefinitionResource.getDataDefinitionHttpResponse(
				dataDefinition.getId()));

		assertHttpResponseStatusCode(
			404, dataDefinitionResource.getDataDefinitionHttpResponse(0L));
	}

	protected DataDefinition testDeleteDataDefinition_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteDataDefinition() throws Exception {
		DataDefinition dataDefinition =
			testGraphQLDataDefinition_addDataDefinition();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteDataDefinition",
				new HashMap<String, Object>() {
					{
						put("dataDefinitionId", dataDefinition.getId());
					}
				}));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(dataJSONObject.getBoolean("deleteDataDefinition"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			graphQLField = new GraphQLField(
				"query",
				new GraphQLField(
					"dataDefinition",
					new HashMap<String, Object>() {
						{
							put("dataDefinitionId", dataDefinition.getId());
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
	public void testGetDataDefinition() throws Exception {
		DataDefinition postDataDefinition =
			testGetDataDefinition_addDataDefinition();

		DataDefinition getDataDefinition =
			dataDefinitionResource.getDataDefinition(
				postDataDefinition.getId());

		assertEquals(postDataDefinition, getDataDefinition);
		assertValid(getDataDefinition);
	}

	protected DataDefinition testGetDataDefinition_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetDataDefinition() throws Exception {
		DataDefinition dataDefinition =
			testGraphQLDataDefinition_addDataDefinition();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"dataDefinition",
				new HashMap<String, Object>() {
					{
						put("dataDefinitionId", dataDefinition.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				dataDefinition,
				dataJSONObject.getJSONObject("dataDefinition")));
	}

	@Test
	public void testPutDataDefinition() throws Exception {
		DataDefinition postDataDefinition =
			testPutDataDefinition_addDataDefinition();

		DataDefinition randomDataDefinition = randomDataDefinition();

		DataDefinition putDataDefinition =
			dataDefinitionResource.putDataDefinition(
				postDataDefinition.getId(), randomDataDefinition);

		assertEquals(randomDataDefinition, putDataDefinition);
		assertValid(putDataDefinition);

		DataDefinition getDataDefinition =
			dataDefinitionResource.getDataDefinition(putDataDefinition.getId());

		assertEquals(randomDataDefinition, getDataDefinition);
		assertValid(getDataDefinition);
	}

	protected DataDefinition testPutDataDefinition_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDataDefinitionDataDefinitionFieldLinks()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testPutDataDefinitionPermission() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		DataDefinition dataDefinition =
			testPutDataDefinitionPermission_addDataDefinition();

		assertHttpResponseStatusCode(
			204,
			dataDefinitionResource.putDataDefinitionPermissionHttpResponse(
				dataDefinition.getId(),
				new Permission[] {
					new Permission() {
						{
							setActionIds(new String[] {"VIEW"});
							setRoleName("Guest");
						}
					}
				}));

		assertHttpResponseStatusCode(
			404,
			dataDefinitionResource.putDataDefinitionPermissionHttpResponse(
				0L,
				new Permission[] {
					new Permission() {
						{
							setActionIds(new String[] {"-"});
							setRoleName("-");
						}
					}
				}));
	}

	protected DataDefinition testPutDataDefinitionPermission_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSiteDataDefinitionByContentTypeContentTypePage()
		throws Exception {

		Page<DataDefinition> page =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId(),
					testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType(),
					RandomTestUtil.randomString(), Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getIrrelevantSiteId();
		String contentType =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType();
		String irrelevantContentType =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getIrrelevantContentType();

		if ((irrelevantSiteId != null) && (irrelevantContentType != null)) {
			DataDefinition irrelevantDataDefinition =
				testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
					irrelevantSiteId, irrelevantContentType,
					randomIrrelevantDataDefinition());

			page =
				dataDefinitionResource.
					getSiteDataDefinitionByContentTypeContentTypePage(
						irrelevantSiteId, irrelevantContentType, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDataDefinition),
				(List<DataDefinition>)page.getItems());
			assertValid(page);
		}

		DataDefinition dataDefinition1 =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId, contentType, randomDataDefinition());

		DataDefinition dataDefinition2 =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId, contentType, randomDataDefinition());

		page =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					siteId, contentType, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition1, dataDefinition2),
			(List<DataDefinition>)page.getItems());
		assertValid(page);

		dataDefinitionResource.deleteDataDefinition(dataDefinition1.getId());

		dataDefinitionResource.deleteDataDefinition(dataDefinition2.getId());
	}

	@Test
	public void testGetSiteDataDefinitionByContentTypeContentTypePageWithPagination()
		throws Exception {

		Long siteId =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId();
		String contentType =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType();

		DataDefinition dataDefinition1 =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId, contentType, randomDataDefinition());

		DataDefinition dataDefinition2 =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId, contentType, randomDataDefinition());

		DataDefinition dataDefinition3 =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId, contentType, randomDataDefinition());

		Page<DataDefinition> page1 =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					siteId, contentType, null, Pagination.of(1, 2), null);

		List<DataDefinition> dataDefinitions1 =
			(List<DataDefinition>)page1.getItems();

		Assert.assertEquals(
			dataDefinitions1.toString(), 2, dataDefinitions1.size());

		Page<DataDefinition> page2 =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					siteId, contentType, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DataDefinition> dataDefinitions2 =
			(List<DataDefinition>)page2.getItems();

		Assert.assertEquals(
			dataDefinitions2.toString(), 1, dataDefinitions2.size());

		Page<DataDefinition> page3 =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeContentTypePage(
					siteId, contentType, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(dataDefinition1, dataDefinition2, dataDefinition3),
			(List<DataDefinition>)page3.getItems());
	}

	@Test
	public void testGetSiteDataDefinitionByContentTypeContentTypePageWithSortDateTime()
		throws Exception {

		testGetSiteDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, dataDefinition1, dataDefinition2) -> {
				BeanUtils.setProperty(
					dataDefinition1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteDataDefinitionByContentTypeContentTypePageWithSortInteger()
		throws Exception {

		testGetSiteDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type.INTEGER,
			(entityField, dataDefinition1, dataDefinition2) -> {
				BeanUtils.setProperty(
					dataDefinition1, entityField.getName(), 0);
				BeanUtils.setProperty(
					dataDefinition2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteDataDefinitionByContentTypeContentTypePageWithSortString()
		throws Exception {

		testGetSiteDataDefinitionByContentTypeContentTypePageWithSort(
			EntityField.Type.STRING,
			(entityField, dataDefinition1, dataDefinition2) -> {
				Class<?> clazz = dataDefinition1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						dataDefinition1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						dataDefinition2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						dataDefinition1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						dataDefinition2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void
			testGetSiteDataDefinitionByContentTypeContentTypePageWithSort(
				EntityField.Type type,
				UnsafeTriConsumer
					<EntityField, DataDefinition, DataDefinition, Exception>
						unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId();
		String contentType =
			testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType();

		DataDefinition dataDefinition1 = randomDataDefinition();
		DataDefinition dataDefinition2 = randomDataDefinition();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, dataDefinition1, dataDefinition2);
		}

		dataDefinition1 =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId, contentType, dataDefinition1);

		dataDefinition2 =
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				siteId, contentType, dataDefinition2);

		for (EntityField entityField : entityFields) {
			Page<DataDefinition> ascPage =
				dataDefinitionResource.
					getSiteDataDefinitionByContentTypeContentTypePage(
						siteId, contentType, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(dataDefinition1, dataDefinition2),
				(List<DataDefinition>)ascPage.getItems());

			Page<DataDefinition> descPage =
				dataDefinitionResource.
					getSiteDataDefinitionByContentTypeContentTypePage(
						siteId, contentType, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(dataDefinition2, dataDefinition1),
				(List<DataDefinition>)descPage.getItems());
		}
	}

	protected DataDefinition
			testGetSiteDataDefinitionByContentTypeContentTypePage_addDataDefinition(
				Long siteId, String contentType, DataDefinition dataDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetSiteDataDefinitionByContentTypeContentTypePage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long
			testGetSiteDataDefinitionByContentTypeContentTypePage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	protected String
			testGetSiteDataDefinitionByContentTypeContentTypePage_getContentType()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetSiteDataDefinitionByContentTypeContentTypePage_getIrrelevantContentType()
		throws Exception {

		return null;
	}

	@Test
	public void testPostSiteDataDefinitionByContentType() throws Exception {
		DataDefinition randomDataDefinition = randomDataDefinition();

		DataDefinition postDataDefinition =
			testPostSiteDataDefinitionByContentType_addDataDefinition(
				randomDataDefinition);

		assertEquals(randomDataDefinition, postDataDefinition);
		assertValid(postDataDefinition);
	}

	protected DataDefinition
			testPostSiteDataDefinitionByContentType_addDataDefinition(
				DataDefinition dataDefinition)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetSiteDataDefinitionByContentTypeByDataDefinitionKey()
		throws Exception {

		DataDefinition postDataDefinition =
			testGetSiteDataDefinitionByContentTypeByDataDefinitionKey_addDataDefinition();

		DataDefinition getDataDefinition =
			dataDefinitionResource.
				getSiteDataDefinitionByContentTypeByDataDefinitionKey(
					postDataDefinition.getSiteId(),
					postDataDefinition.getContentType(),
					postDataDefinition.getDataDefinitionKey());

		assertEquals(postDataDefinition, getDataDefinition);
		assertValid(getDataDefinition);
	}

	protected DataDefinition
			testGetSiteDataDefinitionByContentTypeByDataDefinitionKey_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetSiteDataDefinitionByContentTypeByDataDefinitionKey()
		throws Exception {

		DataDefinition dataDefinition =
			testGraphQLDataDefinition_addDataDefinition();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"dataDefinitionByContentTypeByDataDefinitionKey",
				new HashMap<String, Object>() {
					{
						put("siteId", dataDefinition.getSiteId());
						put("contentType", dataDefinition.getContentType());
						put(
							"dataDefinitionKey",
							dataDefinition.getDataDefinitionKey());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				dataDefinition,
				dataJSONObject.getJSONObject(
					"dataDefinitionByContentTypeByDataDefinitionKey")));
	}

	protected DataDefinition testGraphQLDataDefinition_addDataDefinition()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		DataDefinition dataDefinition1, DataDefinition dataDefinition2) {

		Assert.assertTrue(
			dataDefinition1 + " does not equal " + dataDefinition2,
			equals(dataDefinition1, dataDefinition2));
	}

	protected void assertEquals(
		List<DataDefinition> dataDefinitions1,
		List<DataDefinition> dataDefinitions2) {

		Assert.assertEquals(dataDefinitions1.size(), dataDefinitions2.size());

		for (int i = 0; i < dataDefinitions1.size(); i++) {
			DataDefinition dataDefinition1 = dataDefinitions1.get(i);
			DataDefinition dataDefinition2 = dataDefinitions2.get(i);

			assertEquals(dataDefinition1, dataDefinition2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DataDefinition> dataDefinitions1,
		List<DataDefinition> dataDefinitions2) {

		Assert.assertEquals(dataDefinitions1.size(), dataDefinitions2.size());

		for (DataDefinition dataDefinition1 : dataDefinitions1) {
			boolean contains = false;

			for (DataDefinition dataDefinition2 : dataDefinitions2) {
				if (equals(dataDefinition1, dataDefinition2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				dataDefinitions2 + " does not contain " + dataDefinition1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<DataDefinition> dataDefinitions, JSONArray jsonArray) {

		for (DataDefinition dataDefinition : dataDefinitions) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(dataDefinition, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + dataDefinition, contains);
		}
	}

	protected void assertValid(DataDefinition dataDefinition) {
		boolean valid = true;

		if (dataDefinition.getDateCreated() == null) {
			valid = false;
		}

		if (dataDefinition.getDateModified() == null) {
			valid = false;
		}

		if (dataDefinition.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				dataDefinition.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"availableLanguageIds", additionalAssertFieldName)) {

				if (dataDefinition.getAvailableLanguageIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (dataDefinition.getContentType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"dataDefinitionFields", additionalAssertFieldName)) {

				if (dataDefinition.getDataDefinitionFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"dataDefinitionKey", additionalAssertFieldName)) {

				if (dataDefinition.getDataDefinitionKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultLanguageId", additionalAssertFieldName)) {

				if (dataDefinition.getDefaultLanguageId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (dataDefinition.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (dataDefinition.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("storageType", additionalAssertFieldName)) {
				if (dataDefinition.getStorageType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (dataDefinition.getUserId() == null) {
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

	protected void assertValid(Page<DataDefinition> page) {
		boolean valid = false;

		java.util.Collection<DataDefinition> dataDefinitions = page.getItems();

		int size = dataDefinitions.size();

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
		DataDefinition dataDefinition1, DataDefinition dataDefinition2) {

		if (dataDefinition1 == dataDefinition2) {
			return true;
		}

		if (!Objects.equals(
				dataDefinition1.getSiteId(), dataDefinition2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"availableLanguageIds", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						dataDefinition1.getAvailableLanguageIds(),
						dataDefinition2.getAvailableLanguageIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("contentType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getContentType(),
						dataDefinition2.getContentType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"dataDefinitionFields", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						dataDefinition1.getDataDefinitionFields(),
						dataDefinition2.getDataDefinitionFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"dataDefinitionKey", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						dataDefinition1.getDataDefinitionKey(),
						dataDefinition2.getDataDefinitionKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getDateCreated(),
						dataDefinition2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getDateModified(),
						dataDefinition2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"defaultLanguageId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						dataDefinition1.getDefaultLanguageId(),
						dataDefinition2.getDefaultLanguageId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getDescription(),
						dataDefinition2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getId(), dataDefinition2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getName(), dataDefinition2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("storageType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getStorageType(),
						dataDefinition2.getStorageType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						dataDefinition1.getUserId(),
						dataDefinition2.getUserId())) {

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
		DataDefinition dataDefinition, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("contentType", fieldName)) {
				if (!Objects.deepEquals(
						dataDefinition.getContentType(),
						jsonObject.getString("contentType"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataDefinitionKey", fieldName)) {
				if (!Objects.deepEquals(
						dataDefinition.getDataDefinitionKey(),
						jsonObject.getString("dataDefinitionKey"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("defaultLanguageId", fieldName)) {
				if (!Objects.deepEquals(
						dataDefinition.getDefaultLanguageId(),
						jsonObject.getString("defaultLanguageId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						dataDefinition.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("storageType", fieldName)) {
				if (!Objects.deepEquals(
						dataDefinition.getStorageType(),
						jsonObject.getString("storageType"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userId", fieldName)) {
				if (!Objects.deepEquals(
						dataDefinition.getUserId(),
						jsonObject.getLong("userId"))) {

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

		if (!(_dataDefinitionResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_dataDefinitionResource;

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
		DataDefinition dataDefinition) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("availableLanguageIds")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("contentType")) {
			sb.append("'");
			sb.append(String.valueOf(dataDefinition.getContentType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("dataDefinitionFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataDefinitionKey")) {
			sb.append("'");
			sb.append(String.valueOf(dataDefinition.getDataDefinitionKey()));
			sb.append("'");

			return sb.toString();
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
							dataDefinition.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							dataDefinition.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(dataDefinition.getDateCreated()));
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
							dataDefinition.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							dataDefinition.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(dataDefinition.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("defaultLanguageId")) {
			sb.append("'");
			sb.append(String.valueOf(dataDefinition.getDefaultLanguageId()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("storageType")) {
			sb.append("'");
			sb.append(String.valueOf(dataDefinition.getStorageType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("userId")) {
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

	protected DataDefinition randomDataDefinition() throws Exception {
		return new DataDefinition() {
			{
				contentType = RandomTestUtil.randomString();
				dataDefinitionKey = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				defaultLanguageId = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
				storageType = RandomTestUtil.randomString();
				userId = RandomTestUtil.randomLong();
			}
		};
	}

	protected DataDefinition randomIrrelevantDataDefinition() throws Exception {
		DataDefinition randomIrrelevantDataDefinition = randomDataDefinition();

		randomIrrelevantDataDefinition.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantDataDefinition;
	}

	protected DataDefinition randomPatchDataDefinition() throws Exception {
		return randomDataDefinition();
	}

	protected DataDefinitionResource dataDefinitionResource;
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
		BaseDataDefinitionResourceTestCase.class);

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
	private com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource
		_dataDefinitionResource;

}