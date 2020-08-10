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

package com.liferay.app.builder.rest.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.app.builder.rest.client.dto.v1_0.App;
import com.liferay.app.builder.rest.client.http.HttpInvoker;
import com.liferay.app.builder.rest.client.pagination.Page;
import com.liferay.app.builder.rest.client.pagination.Pagination;
import com.liferay.app.builder.rest.client.resource.v1_0.AppResource;
import com.liferay.app.builder.rest.client.serdes.v1_0.AppSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
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
 * @author Gabriel Albuquerque
 * @generated
 */
@Generated("")
public abstract class BaseAppResourceTestCase {

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

		_appResource.setContextCompany(testCompany);

		AppResource.Builder builder = AppResource.builder();

		appResource = builder.authentication(
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

		App app1 = randomApp();

		String json = objectMapper.writeValueAsString(app1);

		App app2 = AppSerDes.toDTO(json);

		Assert.assertTrue(equals(app1, app2));
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

		App app = randomApp();

		String json1 = objectMapper.writeValueAsString(app);
		String json2 = AppSerDes.toJSON(app);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		App app = randomApp();

		app.setDataDefinitionName(regex);
		app.setScope(regex);
		app.setVersion(regex);

		String json = AppSerDes.toJSON(app);

		Assert.assertFalse(json.contains(regex));

		app = AppSerDes.toDTO(json);

		Assert.assertEquals(regex, app.getDataDefinitionName());
		Assert.assertEquals(regex, app.getScope());
		Assert.assertEquals(regex, app.getVersion());
	}

	@Test
	public void testGetAppsPage() throws Exception {
		Page<App> page = appResource.getAppsPage(
			null, null, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		App app1 = testGetAppsPage_addApp(randomApp());

		App app2 = testGetAppsPage_addApp(randomApp());

		page = appResource.getAppsPage(
			null, null, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(app1, app2), (List<App>)page.getItems());
		assertValid(page);

		appResource.deleteApp(app1.getId());

		appResource.deleteApp(app2.getId());
	}

	@Test
	public void testGetAppsPageWithPagination() throws Exception {
		App app1 = testGetAppsPage_addApp(randomApp());

		App app2 = testGetAppsPage_addApp(randomApp());

		App app3 = testGetAppsPage_addApp(randomApp());

		Page<App> page1 = appResource.getAppsPage(
			null, null, null, null, null, Pagination.of(1, 2), null);

		List<App> apps1 = (List<App>)page1.getItems();

		Assert.assertEquals(apps1.toString(), 2, apps1.size());

		Page<App> page2 = appResource.getAppsPage(
			null, null, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<App> apps2 = (List<App>)page2.getItems();

		Assert.assertEquals(apps2.toString(), 1, apps2.size());

		Page<App> page3 = appResource.getAppsPage(
			null, null, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(app1, app2, app3), (List<App>)page3.getItems());
	}

	@Test
	public void testGetAppsPageWithSortDateTime() throws Exception {
		testGetAppsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, app1, app2) -> {
				BeanUtils.setProperty(
					app1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAppsPageWithSortInteger() throws Exception {
		testGetAppsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, app1, app2) -> {
				BeanUtils.setProperty(app1, entityField.getName(), 0);
				BeanUtils.setProperty(app2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAppsPageWithSortString() throws Exception {
		testGetAppsPageWithSort(
			EntityField.Type.STRING,
			(entityField, app1, app2) -> {
				Class<?> clazz = app1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						app1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						app2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						app1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						app2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						app1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						app2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAppsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, App, App, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		App app1 = randomApp();
		App app2 = randomApp();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, app1, app2);
		}

		app1 = testGetAppsPage_addApp(app1);

		app2 = testGetAppsPage_addApp(app2);

		for (EntityField entityField : entityFields) {
			Page<App> ascPage = appResource.getAppsPage(
				null, null, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(app1, app2), (List<App>)ascPage.getItems());

			Page<App> descPage = appResource.getAppsPage(
				null, null, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(app2, app1), (List<App>)descPage.getItems());
		}
	}

	protected App testGetAppsPage_addApp(App app) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetAppsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"apps",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject appsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/apps");

		Assert.assertEquals(0, appsJSONObject.get("totalCount"));

		App app1 = testGraphQLApp_addApp();
		App app2 = testGraphQLApp_addApp();

		appsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/apps");

		Assert.assertEquals(2, appsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(app1, app2),
			Arrays.asList(AppSerDes.toDTOs(appsJSONObject.getString("items"))));
	}

	@Test
	public void testDeleteApp() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		App app = testDeleteApp_addApp();

		assertHttpResponseStatusCode(
			204, appResource.deleteAppHttpResponse(app.getId()));

		assertHttpResponseStatusCode(
			404, appResource.getAppHttpResponse(app.getId()));

		assertHttpResponseStatusCode(404, appResource.getAppHttpResponse(0L));
	}

	protected App testDeleteApp_addApp() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteApp() throws Exception {
		App app = testGraphQLApp_addApp();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteApp",
						new HashMap<String, Object>() {
							{
								put("appId", app.getId());
							}
						})),
				"JSONObject/data", "Object/deleteApp"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"app",
						new HashMap<String, Object>() {
							{
								put("appId", app.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetApp() throws Exception {
		App postApp = testGetApp_addApp();

		App getApp = appResource.getApp(postApp.getId());

		assertEquals(postApp, getApp);
		assertValid(getApp);
	}

	protected App testGetApp_addApp() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetApp() throws Exception {
		App app = testGraphQLApp_addApp();

		Assert.assertTrue(
			equals(
				app,
				AppSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"app",
								new HashMap<String, Object>() {
									{
										put("appId", app.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/app"))));
	}

	@Test
	public void testGraphQLGetAppNotFound() throws Exception {
		Long irrelevantAppId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"app",
						new HashMap<String, Object>() {
							{
								put("appId", irrelevantAppId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPutApp() throws Exception {
		App postApp = testPutApp_addApp();

		App randomApp = randomApp();

		App putApp = appResource.putApp(postApp.getId(), randomApp);

		assertEquals(randomApp, putApp);
		assertValid(putApp);

		App getApp = appResource.getApp(putApp.getId());

		assertEquals(randomApp, getApp);
		assertValid(getApp);
	}

	protected App testPutApp_addApp() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutAppDeploy() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testPutAppUndeploy() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetDataDefinitionAppsPage() throws Exception {
		Page<App> page = appResource.getDataDefinitionAppsPage(
			testGetDataDefinitionAppsPage_getDataDefinitionId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long dataDefinitionId =
			testGetDataDefinitionAppsPage_getDataDefinitionId();
		Long irrelevantDataDefinitionId =
			testGetDataDefinitionAppsPage_getIrrelevantDataDefinitionId();

		if ((irrelevantDataDefinitionId != null)) {
			App irrelevantApp = testGetDataDefinitionAppsPage_addApp(
				irrelevantDataDefinitionId, randomIrrelevantApp());

			page = appResource.getDataDefinitionAppsPage(
				irrelevantDataDefinitionId, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantApp), (List<App>)page.getItems());
			assertValid(page);
		}

		App app1 = testGetDataDefinitionAppsPage_addApp(
			dataDefinitionId, randomApp());

		App app2 = testGetDataDefinitionAppsPage_addApp(
			dataDefinitionId, randomApp());

		page = appResource.getDataDefinitionAppsPage(
			dataDefinitionId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(app1, app2), (List<App>)page.getItems());
		assertValid(page);

		appResource.deleteApp(app1.getId());

		appResource.deleteApp(app2.getId());
	}

	@Test
	public void testGetDataDefinitionAppsPageWithPagination() throws Exception {
		Long dataDefinitionId =
			testGetDataDefinitionAppsPage_getDataDefinitionId();

		App app1 = testGetDataDefinitionAppsPage_addApp(
			dataDefinitionId, randomApp());

		App app2 = testGetDataDefinitionAppsPage_addApp(
			dataDefinitionId, randomApp());

		App app3 = testGetDataDefinitionAppsPage_addApp(
			dataDefinitionId, randomApp());

		Page<App> page1 = appResource.getDataDefinitionAppsPage(
			dataDefinitionId, null, null, Pagination.of(1, 2), null);

		List<App> apps1 = (List<App>)page1.getItems();

		Assert.assertEquals(apps1.toString(), 2, apps1.size());

		Page<App> page2 = appResource.getDataDefinitionAppsPage(
			dataDefinitionId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<App> apps2 = (List<App>)page2.getItems();

		Assert.assertEquals(apps2.toString(), 1, apps2.size());

		Page<App> page3 = appResource.getDataDefinitionAppsPage(
			dataDefinitionId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(app1, app2, app3), (List<App>)page3.getItems());
	}

	@Test
	public void testGetDataDefinitionAppsPageWithSortDateTime()
		throws Exception {

		testGetDataDefinitionAppsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, app1, app2) -> {
				BeanUtils.setProperty(
					app1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDataDefinitionAppsPageWithSortInteger()
		throws Exception {

		testGetDataDefinitionAppsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, app1, app2) -> {
				BeanUtils.setProperty(app1, entityField.getName(), 0);
				BeanUtils.setProperty(app2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDataDefinitionAppsPageWithSortString() throws Exception {
		testGetDataDefinitionAppsPageWithSort(
			EntityField.Type.STRING,
			(entityField, app1, app2) -> {
				Class<?> clazz = app1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						app1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						app2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						app1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						app2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						app1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						app2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetDataDefinitionAppsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, App, App, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long dataDefinitionId =
			testGetDataDefinitionAppsPage_getDataDefinitionId();

		App app1 = randomApp();
		App app2 = randomApp();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, app1, app2);
		}

		app1 = testGetDataDefinitionAppsPage_addApp(dataDefinitionId, app1);

		app2 = testGetDataDefinitionAppsPage_addApp(dataDefinitionId, app2);

		for (EntityField entityField : entityFields) {
			Page<App> ascPage = appResource.getDataDefinitionAppsPage(
				dataDefinitionId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(app1, app2), (List<App>)ascPage.getItems());

			Page<App> descPage = appResource.getDataDefinitionAppsPage(
				dataDefinitionId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(app2, app1), (List<App>)descPage.getItems());
		}
	}

	protected App testGetDataDefinitionAppsPage_addApp(
			Long dataDefinitionId, App app)
		throws Exception {

		return appResource.postDataDefinitionApp(dataDefinitionId, app);
	}

	protected Long testGetDataDefinitionAppsPage_getDataDefinitionId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDataDefinitionAppsPage_getIrrelevantDataDefinitionId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDataDefinitionApp() throws Exception {
		App randomApp = randomApp();

		App postApp = testPostDataDefinitionApp_addApp(randomApp);

		assertEquals(randomApp, postApp);
		assertValid(postApp);
	}

	protected App testPostDataDefinitionApp_addApp(App app) throws Exception {
		return appResource.postDataDefinitionApp(
			testGetDataDefinitionAppsPage_getDataDefinitionId(), app);
	}

	@Test
	public void testGetSiteAppsPage() throws Exception {
		Page<App> page = appResource.getSiteAppsPage(
			testGetSiteAppsPage_getSiteId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteAppsPage_getSiteId();
		Long irrelevantSiteId = testGetSiteAppsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			App irrelevantApp = testGetSiteAppsPage_addApp(
				irrelevantSiteId, randomIrrelevantApp());

			page = appResource.getSiteAppsPage(
				irrelevantSiteId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantApp), (List<App>)page.getItems());
			assertValid(page);
		}

		App app1 = testGetSiteAppsPage_addApp(siteId, randomApp());

		App app2 = testGetSiteAppsPage_addApp(siteId, randomApp());

		page = appResource.getSiteAppsPage(
			siteId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(app1, app2), (List<App>)page.getItems());
		assertValid(page);

		appResource.deleteApp(app1.getId());

		appResource.deleteApp(app2.getId());
	}

	@Test
	public void testGetSiteAppsPageWithPagination() throws Exception {
		Long siteId = testGetSiteAppsPage_getSiteId();

		App app1 = testGetSiteAppsPage_addApp(siteId, randomApp());

		App app2 = testGetSiteAppsPage_addApp(siteId, randomApp());

		App app3 = testGetSiteAppsPage_addApp(siteId, randomApp());

		Page<App> page1 = appResource.getSiteAppsPage(
			siteId, null, null, Pagination.of(1, 2), null);

		List<App> apps1 = (List<App>)page1.getItems();

		Assert.assertEquals(apps1.toString(), 2, apps1.size());

		Page<App> page2 = appResource.getSiteAppsPage(
			siteId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<App> apps2 = (List<App>)page2.getItems();

		Assert.assertEquals(apps2.toString(), 1, apps2.size());

		Page<App> page3 = appResource.getSiteAppsPage(
			siteId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(app1, app2, app3), (List<App>)page3.getItems());
	}

	@Test
	public void testGetSiteAppsPageWithSortDateTime() throws Exception {
		testGetSiteAppsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, app1, app2) -> {
				BeanUtils.setProperty(
					app1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteAppsPageWithSortInteger() throws Exception {
		testGetSiteAppsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, app1, app2) -> {
				BeanUtils.setProperty(app1, entityField.getName(), 0);
				BeanUtils.setProperty(app2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteAppsPageWithSortString() throws Exception {
		testGetSiteAppsPageWithSort(
			EntityField.Type.STRING,
			(entityField, app1, app2) -> {
				Class<?> clazz = app1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						app1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						app2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						app1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						app2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						app1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						app2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetSiteAppsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, App, App, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteAppsPage_getSiteId();

		App app1 = randomApp();
		App app2 = randomApp();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, app1, app2);
		}

		app1 = testGetSiteAppsPage_addApp(siteId, app1);

		app2 = testGetSiteAppsPage_addApp(siteId, app2);

		for (EntityField entityField : entityFields) {
			Page<App> ascPage = appResource.getSiteAppsPage(
				siteId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(app1, app2), (List<App>)ascPage.getItems());

			Page<App> descPage = appResource.getSiteAppsPage(
				siteId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(app2, app1), (List<App>)descPage.getItems());
		}
	}

	protected App testGetSiteAppsPage_addApp(Long siteId, App app)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetSiteAppsPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteAppsPage_getIrrelevantSiteId() throws Exception {
		return irrelevantGroup.getGroupId();
	}

	protected App testGraphQLApp_addApp() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(App app1, App app2) {
		Assert.assertTrue(app1 + " does not equal " + app2, equals(app1, app2));
	}

	protected void assertEquals(List<App> apps1, List<App> apps2) {
		Assert.assertEquals(apps1.size(), apps2.size());

		for (int i = 0; i < apps1.size(); i++) {
			App app1 = apps1.get(i);
			App app2 = apps2.get(i);

			assertEquals(app1, app2);
		}
	}

	protected void assertEqualsIgnoringOrder(List<App> apps1, List<App> apps2) {
		Assert.assertEquals(apps1.size(), apps2.size());

		for (App app1 : apps1) {
			boolean contains = false;

			for (App app2 : apps2) {
				if (equals(app1, app2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(apps2 + " does not contain " + app1, contains);
		}
	}

	protected void assertValid(App app) throws Exception {
		boolean valid = true;

		if (app.getDateCreated() == null) {
			valid = false;
		}

		if (app.getDateModified() == null) {
			valid = false;
		}

		if (app.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(app.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (app.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("appDeployments", additionalAssertFieldName)) {
				if (app.getAppDeployments() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataDefinitionId", additionalAssertFieldName)) {
				if (app.getDataDefinitionId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"dataDefinitionName", additionalAssertFieldName)) {

				if (app.getDataDefinitionName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataLayoutId", additionalAssertFieldName)) {
				if (app.getDataLayoutId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dataListViewId", additionalAssertFieldName)) {
				if (app.getDataListViewId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"dataRecordCollectionId", additionalAssertFieldName)) {

				if (app.getDataRecordCollectionId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (app.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("scope", additionalAssertFieldName)) {
				if (app.getScope() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (app.getUserId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("version", additionalAssertFieldName)) {
				if (app.getVersion() == null) {
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

	protected void assertValid(Page<App> page) {
		boolean valid = false;

		java.util.Collection<App> apps = page.getItems();

		int size = apps.size();

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

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		graphQLFields.add(new GraphQLField("siteId"));

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.app.builder.rest.dto.v1_0.App.class)) {

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

	protected boolean equals(App app1, App app2) {
		if (app1 == app2) {
			return true;
		}

		if (!Objects.equals(app1.getSiteId(), app2.getSiteId())) {
			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(app1.getActive(), app2.getActive())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("appDeployments", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						app1.getAppDeployments(), app2.getAppDeployments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataDefinitionId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						app1.getDataDefinitionId(),
						app2.getDataDefinitionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"dataDefinitionName", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						app1.getDataDefinitionName(),
						app2.getDataDefinitionName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataLayoutId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						app1.getDataLayoutId(), app2.getDataLayoutId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dataListViewId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						app1.getDataListViewId(), app2.getDataListViewId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"dataRecordCollectionId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						app1.getDataRecordCollectionId(),
						app2.getDataRecordCollectionId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						app1.getDateCreated(), app2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						app1.getDateModified(), app2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(app1.getId(), app2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!equals((Map)app1.getName(), (Map)app2.getName())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("scope", additionalAssertFieldName)) {
				if (!Objects.deepEquals(app1.getScope(), app2.getScope())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("userId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(app1.getUserId(), app2.getUserId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("version", additionalAssertFieldName)) {
				if (!Objects.deepEquals(app1.getVersion(), app2.getVersion())) {
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

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_appResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_appResource;

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
		EntityField entityField, String operator, App app) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("active")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("appDeployments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataDefinitionId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataDefinitionName")) {
			sb.append("'");
			sb.append(String.valueOf(app.getDataDefinitionName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("dataLayoutId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataListViewId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dataRecordCollectionId")) {
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
						DateUtils.addSeconds(app.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(app.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(app.getDateCreated()));
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
						DateUtils.addSeconds(app.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(app.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(app.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("scope")) {
			sb.append("'");
			sb.append(String.valueOf(app.getScope()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("siteId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("userId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("version")) {
			sb.append("'");
			sb.append(String.valueOf(app.getVersion()));
			sb.append("'");

			return sb.toString();
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

	protected App randomApp() throws Exception {
		return new App() {
			{
				active = RandomTestUtil.randomBoolean();
				dataDefinitionId = RandomTestUtil.randomLong();
				dataDefinitionName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				dataLayoutId = RandomTestUtil.randomLong();
				dataListViewId = RandomTestUtil.randomLong();
				dataRecordCollectionId = RandomTestUtil.randomLong();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				scope = StringUtil.toLowerCase(RandomTestUtil.randomString());
				siteId = testGroup.getGroupId();
				userId = RandomTestUtil.randomLong();
				version = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected App randomIrrelevantApp() throws Exception {
		App randomIrrelevantApp = randomApp();

		randomIrrelevantApp.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantApp;
	}

	protected App randomPatchApp() throws Exception {
		return randomApp();
	}

	protected AppResource appResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
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
		BaseAppResourceTestCase.class);

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
	private com.liferay.app.builder.rest.resource.v1_0.AppResource _appResource;

}