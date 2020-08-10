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

import com.liferay.headless.delivery.client.dto.v1_0.WikiPage;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.WikiPageResource;
import com.liferay.headless.delivery.client.serdes.v1_0.WikiPageSerDes;
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
import com.liferay.portal.search.test.util.SearchTestRule;
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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseWikiPageResourceTestCase {

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

		_wikiPageResource.setContextCompany(testCompany);

		WikiPageResource.Builder builder = WikiPageResource.builder();

		wikiPageResource = builder.authentication(
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

		WikiPage wikiPage1 = randomWikiPage();

		String json = objectMapper.writeValueAsString(wikiPage1);

		WikiPage wikiPage2 = WikiPageSerDes.toDTO(json);

		Assert.assertTrue(equals(wikiPage1, wikiPage2));
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

		WikiPage wikiPage = randomWikiPage();

		String json1 = objectMapper.writeValueAsString(wikiPage);
		String json2 = WikiPageSerDes.toJSON(wikiPage);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		WikiPage wikiPage = randomWikiPage();

		wikiPage.setContent(regex);
		wikiPage.setDescription(regex);
		wikiPage.setEncodingFormat(regex);
		wikiPage.setHeadline(regex);

		String json = WikiPageSerDes.toJSON(wikiPage);

		Assert.assertFalse(json.contains(regex));

		wikiPage = WikiPageSerDes.toDTO(json);

		Assert.assertEquals(regex, wikiPage.getContent());
		Assert.assertEquals(regex, wikiPage.getDescription());
		Assert.assertEquals(regex, wikiPage.getEncodingFormat());
		Assert.assertEquals(regex, wikiPage.getHeadline());
	}

	@Test
	public void testGetWikiNodeWikiPagesPage() throws Exception {
		Page<WikiPage> page = wikiPageResource.getWikiNodeWikiPagesPage(
			testGetWikiNodeWikiPagesPage_getWikiNodeId(),
			RandomTestUtil.randomString(), null, null, Pagination.of(1, 2),
			null);

		Assert.assertEquals(0, page.getTotalCount());

		Long wikiNodeId = testGetWikiNodeWikiPagesPage_getWikiNodeId();
		Long irrelevantWikiNodeId =
			testGetWikiNodeWikiPagesPage_getIrrelevantWikiNodeId();

		if ((irrelevantWikiNodeId != null)) {
			WikiPage irrelevantWikiPage =
				testGetWikiNodeWikiPagesPage_addWikiPage(
					irrelevantWikiNodeId, randomIrrelevantWikiPage());

			page = wikiPageResource.getWikiNodeWikiPagesPage(
				irrelevantWikiNodeId, null, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantWikiPage),
				(List<WikiPage>)page.getItems());
			assertValid(page);
		}

		WikiPage wikiPage1 = testGetWikiNodeWikiPagesPage_addWikiPage(
			wikiNodeId, randomWikiPage());

		WikiPage wikiPage2 = testGetWikiNodeWikiPagesPage_addWikiPage(
			wikiNodeId, randomWikiPage());

		page = wikiPageResource.getWikiNodeWikiPagesPage(
			wikiNodeId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(wikiPage1, wikiPage2),
			(List<WikiPage>)page.getItems());
		assertValid(page);

		wikiPageResource.deleteWikiPage(wikiPage1.getId());

		wikiPageResource.deleteWikiPage(wikiPage2.getId());
	}

	@Test
	public void testGetWikiNodeWikiPagesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long wikiNodeId = testGetWikiNodeWikiPagesPage_getWikiNodeId();

		WikiPage wikiPage1 = randomWikiPage();

		wikiPage1 = testGetWikiNodeWikiPagesPage_addWikiPage(
			wikiNodeId, wikiPage1);

		for (EntityField entityField : entityFields) {
			Page<WikiPage> page = wikiPageResource.getWikiNodeWikiPagesPage(
				wikiNodeId, null, null,
				getFilterString(entityField, "between", wikiPage1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(wikiPage1),
				(List<WikiPage>)page.getItems());
		}
	}

	@Test
	public void testGetWikiNodeWikiPagesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long wikiNodeId = testGetWikiNodeWikiPagesPage_getWikiNodeId();

		WikiPage wikiPage1 = testGetWikiNodeWikiPagesPage_addWikiPage(
			wikiNodeId, randomWikiPage());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		WikiPage wikiPage2 = testGetWikiNodeWikiPagesPage_addWikiPage(
			wikiNodeId, randomWikiPage());

		for (EntityField entityField : entityFields) {
			Page<WikiPage> page = wikiPageResource.getWikiNodeWikiPagesPage(
				wikiNodeId, null, null,
				getFilterString(entityField, "eq", wikiPage1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(wikiPage1),
				(List<WikiPage>)page.getItems());
		}
	}

	@Test
	public void testGetWikiNodeWikiPagesPageWithPagination() throws Exception {
		Long wikiNodeId = testGetWikiNodeWikiPagesPage_getWikiNodeId();

		WikiPage wikiPage1 = testGetWikiNodeWikiPagesPage_addWikiPage(
			wikiNodeId, randomWikiPage());

		WikiPage wikiPage2 = testGetWikiNodeWikiPagesPage_addWikiPage(
			wikiNodeId, randomWikiPage());

		WikiPage wikiPage3 = testGetWikiNodeWikiPagesPage_addWikiPage(
			wikiNodeId, randomWikiPage());

		Page<WikiPage> page1 = wikiPageResource.getWikiNodeWikiPagesPage(
			wikiNodeId, null, null, null, Pagination.of(1, 2), null);

		List<WikiPage> wikiPages1 = (List<WikiPage>)page1.getItems();

		Assert.assertEquals(wikiPages1.toString(), 2, wikiPages1.size());

		Page<WikiPage> page2 = wikiPageResource.getWikiNodeWikiPagesPage(
			wikiNodeId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<WikiPage> wikiPages2 = (List<WikiPage>)page2.getItems();

		Assert.assertEquals(wikiPages2.toString(), 1, wikiPages2.size());

		Page<WikiPage> page3 = wikiPageResource.getWikiNodeWikiPagesPage(
			wikiNodeId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(wikiPage1, wikiPage2, wikiPage3),
			(List<WikiPage>)page3.getItems());
	}

	@Test
	public void testGetWikiNodeWikiPagesPageWithSortDateTime()
		throws Exception {

		testGetWikiNodeWikiPagesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, wikiPage1, wikiPage2) -> {
				BeanUtils.setProperty(
					wikiPage1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetWikiNodeWikiPagesPageWithSortInteger() throws Exception {
		testGetWikiNodeWikiPagesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, wikiPage1, wikiPage2) -> {
				BeanUtils.setProperty(wikiPage1, entityField.getName(), 0);
				BeanUtils.setProperty(wikiPage2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetWikiNodeWikiPagesPageWithSortString() throws Exception {
		testGetWikiNodeWikiPagesPageWithSort(
			EntityField.Type.STRING,
			(entityField, wikiPage1, wikiPage2) -> {
				Class<?> clazz = wikiPage1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						wikiPage1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						wikiPage2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						wikiPage1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						wikiPage2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						wikiPage1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						wikiPage2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetWikiNodeWikiPagesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, WikiPage, WikiPage, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long wikiNodeId = testGetWikiNodeWikiPagesPage_getWikiNodeId();

		WikiPage wikiPage1 = randomWikiPage();
		WikiPage wikiPage2 = randomWikiPage();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, wikiPage1, wikiPage2);
		}

		wikiPage1 = testGetWikiNodeWikiPagesPage_addWikiPage(
			wikiNodeId, wikiPage1);

		wikiPage2 = testGetWikiNodeWikiPagesPage_addWikiPage(
			wikiNodeId, wikiPage2);

		for (EntityField entityField : entityFields) {
			Page<WikiPage> ascPage = wikiPageResource.getWikiNodeWikiPagesPage(
				wikiNodeId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(wikiPage1, wikiPage2),
				(List<WikiPage>)ascPage.getItems());

			Page<WikiPage> descPage = wikiPageResource.getWikiNodeWikiPagesPage(
				wikiNodeId, null, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(wikiPage2, wikiPage1),
				(List<WikiPage>)descPage.getItems());
		}
	}

	protected WikiPage testGetWikiNodeWikiPagesPage_addWikiPage(
			Long wikiNodeId, WikiPage wikiPage)
		throws Exception {

		return wikiPageResource.postWikiNodeWikiPage(wikiNodeId, wikiPage);
	}

	protected Long testGetWikiNodeWikiPagesPage_getWikiNodeId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetWikiNodeWikiPagesPage_getIrrelevantWikiNodeId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostWikiNodeWikiPage() throws Exception {
		WikiPage randomWikiPage = randomWikiPage();

		WikiPage postWikiPage = testPostWikiNodeWikiPage_addWikiPage(
			randomWikiPage);

		assertEquals(randomWikiPage, postWikiPage);
		assertValid(postWikiPage);
	}

	protected WikiPage testPostWikiNodeWikiPage_addWikiPage(WikiPage wikiPage)
		throws Exception {

		return wikiPageResource.postWikiNodeWikiPage(
			testGetWikiNodeWikiPagesPage_getWikiNodeId(), wikiPage);
	}

	@Test
	public void testGetWikiPageWikiPagesPage() throws Exception {
		Page<WikiPage> page = wikiPageResource.getWikiPageWikiPagesPage(
			testGetWikiPageWikiPagesPage_getParentWikiPageId());

		Assert.assertEquals(0, page.getTotalCount());

		Long parentWikiPageId =
			testGetWikiPageWikiPagesPage_getParentWikiPageId();
		Long irrelevantParentWikiPageId =
			testGetWikiPageWikiPagesPage_getIrrelevantParentWikiPageId();

		if ((irrelevantParentWikiPageId != null)) {
			WikiPage irrelevantWikiPage =
				testGetWikiPageWikiPagesPage_addWikiPage(
					irrelevantParentWikiPageId, randomIrrelevantWikiPage());

			page = wikiPageResource.getWikiPageWikiPagesPage(
				irrelevantParentWikiPageId);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantWikiPage),
				(List<WikiPage>)page.getItems());
			assertValid(page);
		}

		WikiPage wikiPage1 = testGetWikiPageWikiPagesPage_addWikiPage(
			parentWikiPageId, randomWikiPage());

		WikiPage wikiPage2 = testGetWikiPageWikiPagesPage_addWikiPage(
			parentWikiPageId, randomWikiPage());

		page = wikiPageResource.getWikiPageWikiPagesPage(parentWikiPageId);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(wikiPage1, wikiPage2),
			(List<WikiPage>)page.getItems());
		assertValid(page);

		wikiPageResource.deleteWikiPage(wikiPage1.getId());

		wikiPageResource.deleteWikiPage(wikiPage2.getId());
	}

	protected WikiPage testGetWikiPageWikiPagesPage_addWikiPage(
			Long parentWikiPageId, WikiPage wikiPage)
		throws Exception {

		return wikiPageResource.postWikiPageWikiPage(
			parentWikiPageId, wikiPage);
	}

	protected Long testGetWikiPageWikiPagesPage_getParentWikiPageId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetWikiPageWikiPagesPage_getIrrelevantParentWikiPageId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostWikiPageWikiPage() throws Exception {
		WikiPage randomWikiPage = randomWikiPage();

		WikiPage postWikiPage = testPostWikiPageWikiPage_addWikiPage(
			randomWikiPage);

		assertEquals(randomWikiPage, postWikiPage);
		assertValid(postWikiPage);
	}

	protected WikiPage testPostWikiPageWikiPage_addWikiPage(WikiPage wikiPage)
		throws Exception {

		return wikiPageResource.postWikiPageWikiPage(
			testGetWikiPageWikiPagesPage_getParentWikiPageId(), wikiPage);
	}

	@Test
	public void testDeleteWikiPage() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		WikiPage wikiPage = testDeleteWikiPage_addWikiPage();

		assertHttpResponseStatusCode(
			204, wikiPageResource.deleteWikiPageHttpResponse(wikiPage.getId()));

		assertHttpResponseStatusCode(
			404, wikiPageResource.getWikiPageHttpResponse(wikiPage.getId()));

		assertHttpResponseStatusCode(
			404, wikiPageResource.getWikiPageHttpResponse(0L));
	}

	protected WikiPage testDeleteWikiPage_addWikiPage() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteWikiPage() throws Exception {
		WikiPage wikiPage = testGraphQLWikiPage_addWikiPage();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteWikiPage",
						new HashMap<String, Object>() {
							{
								put("wikiPageId", wikiPage.getId());
							}
						})),
				"JSONObject/data", "Object/deleteWikiPage"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"wikiPage",
						new HashMap<String, Object>() {
							{
								put("wikiPageId", wikiPage.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetWikiPage() throws Exception {
		WikiPage postWikiPage = testGetWikiPage_addWikiPage();

		WikiPage getWikiPage = wikiPageResource.getWikiPage(
			postWikiPage.getId());

		assertEquals(postWikiPage, getWikiPage);
		assertValid(getWikiPage);
	}

	protected WikiPage testGetWikiPage_addWikiPage() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetWikiPage() throws Exception {
		WikiPage wikiPage = testGraphQLWikiPage_addWikiPage();

		Assert.assertTrue(
			equals(
				wikiPage,
				WikiPageSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"wikiPage",
								new HashMap<String, Object>() {
									{
										put("wikiPageId", wikiPage.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/wikiPage"))));
	}

	@Test
	public void testGraphQLGetWikiPageNotFound() throws Exception {
		Long irrelevantWikiPageId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"wikiPage",
						new HashMap<String, Object>() {
							{
								put("wikiPageId", irrelevantWikiPageId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPutWikiPage() throws Exception {
		WikiPage postWikiPage = testPutWikiPage_addWikiPage();

		WikiPage randomWikiPage = randomWikiPage();

		WikiPage putWikiPage = wikiPageResource.putWikiPage(
			postWikiPage.getId(), randomWikiPage);

		assertEquals(randomWikiPage, putWikiPage);
		assertValid(putWikiPage);

		WikiPage getWikiPage = wikiPageResource.getWikiPage(
			putWikiPage.getId());

		assertEquals(randomWikiPage, getWikiPage);
		assertValid(getWikiPage);
	}

	protected WikiPage testPutWikiPage_addWikiPage() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutWikiPageSubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		WikiPage wikiPage = testPutWikiPageSubscribe_addWikiPage();

		assertHttpResponseStatusCode(
			204,
			wikiPageResource.putWikiPageSubscribeHttpResponse(
				wikiPage.getId()));

		assertHttpResponseStatusCode(
			404, wikiPageResource.putWikiPageSubscribeHttpResponse(0L));
	}

	protected WikiPage testPutWikiPageSubscribe_addWikiPage() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutWikiPageUnsubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		WikiPage wikiPage = testPutWikiPageUnsubscribe_addWikiPage();

		assertHttpResponseStatusCode(
			204,
			wikiPageResource.putWikiPageUnsubscribeHttpResponse(
				wikiPage.getId()));

		assertHttpResponseStatusCode(
			404, wikiPageResource.putWikiPageUnsubscribeHttpResponse(0L));
	}

	protected WikiPage testPutWikiPageUnsubscribe_addWikiPage()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected WikiPage testGraphQLWikiPage_addWikiPage() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(WikiPage wikiPage1, WikiPage wikiPage2) {
		Assert.assertTrue(
			wikiPage1 + " does not equal " + wikiPage2,
			equals(wikiPage1, wikiPage2));
	}

	protected void assertEquals(
		List<WikiPage> wikiPages1, List<WikiPage> wikiPages2) {

		Assert.assertEquals(wikiPages1.size(), wikiPages2.size());

		for (int i = 0; i < wikiPages1.size(); i++) {
			WikiPage wikiPage1 = wikiPages1.get(i);
			WikiPage wikiPage2 = wikiPages2.get(i);

			assertEquals(wikiPage1, wikiPage2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<WikiPage> wikiPages1, List<WikiPage> wikiPages2) {

		Assert.assertEquals(wikiPages1.size(), wikiPages2.size());

		for (WikiPage wikiPage1 : wikiPages1) {
			boolean contains = false;

			for (WikiPage wikiPage2 : wikiPages2) {
				if (equals(wikiPage1, wikiPage2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				wikiPages2 + " does not contain " + wikiPage1, contains);
		}
	}

	protected void assertValid(WikiPage wikiPage) throws Exception {
		boolean valid = true;

		if (wikiPage.getDateCreated() == null) {
			valid = false;
		}

		if (wikiPage.getDateModified() == null) {
			valid = false;
		}

		if (wikiPage.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(wikiPage.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (wikiPage.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (wikiPage.getAggregateRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (wikiPage.getContent() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (wikiPage.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (wikiPage.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (wikiPage.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (wikiPage.getEncodingFormat() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("headline", additionalAssertFieldName)) {
				if (wikiPage.getHeadline() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (wikiPage.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfAttachments", additionalAssertFieldName)) {

				if (wikiPage.getNumberOfAttachments() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfWikiPages", additionalAssertFieldName)) {

				if (wikiPage.getNumberOfWikiPages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("parentWikiPageId", additionalAssertFieldName)) {
				if (wikiPage.getParentWikiPageId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (wikiPage.getRelatedContents() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (wikiPage.getSubscribed() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryBriefs", additionalAssertFieldName)) {

				if (wikiPage.getTaxonomyCategoryBriefs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (wikiPage.getTaxonomyCategoryIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (wikiPage.getViewableBy() == null) {
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

	protected void assertValid(Page<WikiPage> page) {
		boolean valid = false;

		java.util.Collection<WikiPage> wikiPages = page.getItems();

		int size = wikiPages.size();

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
					com.liferay.headless.delivery.dto.v1_0.WikiPage.class)) {

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

	protected boolean equals(WikiPage wikiPage1, WikiPage wikiPage2) {
		if (wikiPage1 == wikiPage2) {
			return true;
		}

		if (!Objects.equals(wikiPage1.getSiteId(), wikiPage2.getSiteId())) {
			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)wikiPage1.getActions(),
						(Map)wikiPage2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiPage1.getAggregateRating(),
						wikiPage2.getAggregateRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("content", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiPage1.getContent(), wikiPage2.getContent())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiPage1.getCreator(), wikiPage2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiPage1.getCustomFields(),
						wikiPage2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiPage1.getDateCreated(),
						wikiPage2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiPage1.getDateModified(),
						wikiPage2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiPage1.getDescription(),
						wikiPage2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiPage1.getEncodingFormat(),
						wikiPage2.getEncodingFormat())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("headline", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiPage1.getHeadline(), wikiPage2.getHeadline())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(wikiPage1.getId(), wikiPage2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiPage1.getKeywords(), wikiPage2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfAttachments", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						wikiPage1.getNumberOfAttachments(),
						wikiPage2.getNumberOfAttachments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfWikiPages", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						wikiPage1.getNumberOfWikiPages(),
						wikiPage2.getNumberOfWikiPages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("parentWikiPageId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiPage1.getParentWikiPageId(),
						wikiPage2.getParentWikiPageId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiPage1.getRelatedContents(),
						wikiPage2.getRelatedContents())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiPage1.getSubscribed(), wikiPage2.getSubscribed())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryBriefs", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						wikiPage1.getTaxonomyCategoryBriefs(),
						wikiPage2.getTaxonomyCategoryBriefs())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						wikiPage1.getTaxonomyCategoryIds(),
						wikiPage2.getTaxonomyCategoryIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiPage1.getViewableBy(), wikiPage2.getViewableBy())) {

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

		if (!(_wikiPageResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_wikiPageResource;

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
		EntityField entityField, String operator, WikiPage wikiPage) {

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

		if (entityFieldName.equals("aggregateRating")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("content")) {
			sb.append("'");
			sb.append(String.valueOf(wikiPage.getContent()));
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
						DateUtils.addSeconds(wikiPage.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(wikiPage.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(wikiPage.getDateCreated()));
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
						DateUtils.addSeconds(wikiPage.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(wikiPage.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(wikiPage.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(wikiPage.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			sb.append("'");
			sb.append(String.valueOf(wikiPage.getEncodingFormat()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("headline")) {
			sb.append("'");
			sb.append(String.valueOf(wikiPage.getHeadline()));
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

		if (entityFieldName.equals("numberOfAttachments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfWikiPages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentWikiPageId")) {
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

		if (entityFieldName.equals("subscribed")) {
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

	protected WikiPage randomWikiPage() throws Exception {
		return new WikiPage() {
			{
				content = StringUtil.toLowerCase(RandomTestUtil.randomString());
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				encodingFormat = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				headline = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				numberOfAttachments = RandomTestUtil.randomInt();
				numberOfWikiPages = RandomTestUtil.randomInt();
				parentWikiPageId = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
				subscribed = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected WikiPage randomIrrelevantWikiPage() throws Exception {
		WikiPage randomIrrelevantWikiPage = randomWikiPage();

		randomIrrelevantWikiPage.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantWikiPage;
	}

	protected WikiPage randomPatchWikiPage() throws Exception {
		return randomWikiPage();
	}

	protected WikiPageResource wikiPageResource;
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
		BaseWikiPageResourceTestCase.class);

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
	private com.liferay.headless.delivery.resource.v1_0.WikiPageResource
		_wikiPageResource;

}