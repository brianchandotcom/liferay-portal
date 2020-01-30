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

package com.liferay.headless.admin.taxonomy.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.taxonomy.client.dto.v1_0.Keyword;
import com.liferay.headless.admin.taxonomy.client.http.HttpInvoker;
import com.liferay.headless.admin.taxonomy.client.pagination.Page;
import com.liferay.headless.admin.taxonomy.client.pagination.Pagination;
import com.liferay.headless.admin.taxonomy.client.resource.v1_0.KeywordResource;
import com.liferay.headless.admin.taxonomy.client.serdes.v1_0.KeywordSerDes;
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
public abstract class BaseKeywordResourceTestCase {

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

		_keywordResource.setContextCompany(testCompany);

		KeywordResource.Builder builder = KeywordResource.builder();

		keywordResource = builder.locale(
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

		Keyword keyword1 = randomKeyword();

		String json = objectMapper.writeValueAsString(keyword1);

		Keyword keyword2 = KeywordSerDes.toDTO(json);

		Assert.assertTrue(equals(keyword1, keyword2));
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

		Keyword keyword = randomKeyword();

		String json1 = objectMapper.writeValueAsString(keyword);
		String json2 = KeywordSerDes.toJSON(keyword);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Keyword keyword = randomKeyword();

		keyword.setName(regex);

		String json = KeywordSerDes.toJSON(keyword);

		Assert.assertFalse(json.contains(regex));

		keyword = KeywordSerDes.toDTO(json);

		Assert.assertEquals(regex, keyword.getName());
	}

	@Test
	public void testDeleteKeyword() throws Exception {
		Keyword keyword = testDeleteKeyword_addKeyword();

		assertHttpResponseStatusCode(
			204, keywordResource.deleteKeywordHttpResponse(keyword.getId()));

		assertHttpResponseStatusCode(
			404, keywordResource.getKeywordHttpResponse(keyword.getId()));

		assertHttpResponseStatusCode(
			404, keywordResource.getKeywordHttpResponse(0L));
	}

	protected Keyword testDeleteKeyword_addKeyword() throws Exception {
		return keywordResource.postSiteKeyword(
			testGroup.getGroupId(), randomKeyword());
	}

	@Test
	public void testGraphQLDeleteKeyword() throws Exception {
		Keyword keyword = testGraphQLKeyword_addKeyword();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteKeyword",
				new HashMap<String, Object>() {
					{
						put("keywordId", keyword.getId());
					}
				}));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(dataJSONObject.getBoolean("deleteKeyword"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			graphQLField = new GraphQLField(
				"query",
				new GraphQLField(
					"keyword",
					new HashMap<String, Object>() {
						{
							put("keywordId", keyword.getId());
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
	public void testGetKeyword() throws Exception {
		Keyword postKeyword = testGetKeyword_addKeyword();

		Keyword getKeyword = keywordResource.getKeyword(postKeyword.getId());

		assertEquals(postKeyword, getKeyword);
		assertValid(getKeyword);
	}

	protected Keyword testGetKeyword_addKeyword() throws Exception {
		return keywordResource.postSiteKeyword(
			testGroup.getGroupId(), randomKeyword());
	}

	@Test
	public void testGraphQLGetKeyword() throws Exception {
		Keyword keyword = testGraphQLKeyword_addKeyword();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"keyword",
				new HashMap<String, Object>() {
					{
						put("keywordId", keyword.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(keyword, dataJSONObject.getJSONObject("keyword")));
	}

	@Test
	public void testPutKeyword() throws Exception {
		Keyword postKeyword = testPutKeyword_addKeyword();

		Keyword randomKeyword = randomKeyword();

		Keyword putKeyword = keywordResource.putKeyword(
			postKeyword.getId(), randomKeyword);

		assertEquals(randomKeyword, putKeyword);
		assertValid(putKeyword);

		Keyword getKeyword = keywordResource.getKeyword(putKeyword.getId());

		assertEquals(randomKeyword, getKeyword);
		assertValid(getKeyword);
	}

	protected Keyword testPutKeyword_addKeyword() throws Exception {
		return keywordResource.postSiteKeyword(
			testGroup.getGroupId(), randomKeyword());
	}

	@Test
	public void testGetSiteKeywordsPage() throws Exception {
		Page<Keyword> page = keywordResource.getSiteKeywordsPage(
			testGetSiteKeywordsPage_getSiteId(), RandomTestUtil.randomString(),
			null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteKeywordsPage_getSiteId();
		Long irrelevantSiteId = testGetSiteKeywordsPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			Keyword irrelevantKeyword = testGetSiteKeywordsPage_addKeyword(
				irrelevantSiteId, randomIrrelevantKeyword());

			page = keywordResource.getSiteKeywordsPage(
				irrelevantSiteId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKeyword),
				(List<Keyword>)page.getItems());
			assertValid(page);
		}

		Keyword keyword1 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		Keyword keyword2 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		page = keywordResource.getSiteKeywordsPage(
			siteId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2), (List<Keyword>)page.getItems());
		assertValid(page);

		keywordResource.deleteKeyword(keyword1.getId());

		keywordResource.deleteKeyword(keyword2.getId());
	}

	@Test
	public void testGetSiteKeywordsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteKeywordsPage_getSiteId();

		Keyword keyword1 = randomKeyword();

		keyword1 = testGetSiteKeywordsPage_addKeyword(siteId, keyword1);

		for (EntityField entityField : entityFields) {
			Page<Keyword> page = keywordResource.getSiteKeywordsPage(
				siteId, null, getFilterString(entityField, "between", keyword1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(keyword1),
				(List<Keyword>)page.getItems());
		}
	}

	@Test
	public void testGetSiteKeywordsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteKeywordsPage_getSiteId();

		Keyword keyword1 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Keyword keyword2 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		for (EntityField entityField : entityFields) {
			Page<Keyword> page = keywordResource.getSiteKeywordsPage(
				siteId, null, getFilterString(entityField, "eq", keyword1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(keyword1),
				(List<Keyword>)page.getItems());
		}
	}

	@Test
	public void testGetSiteKeywordsPageWithPagination() throws Exception {
		Long siteId = testGetSiteKeywordsPage_getSiteId();

		Keyword keyword1 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		Keyword keyword2 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		Keyword keyword3 = testGetSiteKeywordsPage_addKeyword(
			siteId, randomKeyword());

		Page<Keyword> page1 = keywordResource.getSiteKeywordsPage(
			siteId, null, null, Pagination.of(1, 2), null);

		List<Keyword> keywords1 = (List<Keyword>)page1.getItems();

		Assert.assertEquals(keywords1.toString(), 2, keywords1.size());

		Page<Keyword> page2 = keywordResource.getSiteKeywordsPage(
			siteId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Keyword> keywords2 = (List<Keyword>)page2.getItems();

		Assert.assertEquals(keywords2.toString(), 1, keywords2.size());

		Page<Keyword> page3 = keywordResource.getSiteKeywordsPage(
			siteId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(keyword1, keyword2, keyword3),
			(List<Keyword>)page3.getItems());
	}

	@Test
	public void testGetSiteKeywordsPageWithSortDateTime() throws Exception {
		testGetSiteKeywordsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, keyword1, keyword2) -> {
				BeanUtils.setProperty(
					keyword1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteKeywordsPageWithSortInteger() throws Exception {
		testGetSiteKeywordsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, keyword1, keyword2) -> {
				BeanUtils.setProperty(keyword1, entityField.getName(), 0);
				BeanUtils.setProperty(keyword2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteKeywordsPageWithSortString() throws Exception {
		testGetSiteKeywordsPageWithSort(
			EntityField.Type.STRING,
			(entityField, keyword1, keyword2) -> {
				Class<?> clazz = keyword1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						keyword1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						keyword2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						keyword1, entityField.getName(), "Aaa");
					BeanUtils.setProperty(
						keyword2, entityField.getName(), "Bbb");
				}
			});
	}

	protected void testGetSiteKeywordsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Keyword, Keyword, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteKeywordsPage_getSiteId();

		Keyword keyword1 = randomKeyword();
		Keyword keyword2 = randomKeyword();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, keyword1, keyword2);
		}

		keyword1 = testGetSiteKeywordsPage_addKeyword(siteId, keyword1);

		keyword2 = testGetSiteKeywordsPage_addKeyword(siteId, keyword2);

		for (EntityField entityField : entityFields) {
			Page<Keyword> ascPage = keywordResource.getSiteKeywordsPage(
				siteId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(keyword1, keyword2),
				(List<Keyword>)ascPage.getItems());

			Page<Keyword> descPage = keywordResource.getSiteKeywordsPage(
				siteId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(keyword2, keyword1),
				(List<Keyword>)descPage.getItems());
		}
	}

	protected Keyword testGetSiteKeywordsPage_addKeyword(
			Long siteId, Keyword keyword)
		throws Exception {

		return keywordResource.postSiteKeyword(siteId, keyword);
	}

	protected Long testGetSiteKeywordsPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteKeywordsPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteKeywordsPage() throws Exception {
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
				"keywords",
				new HashMap<String, Object>() {
					{
						put("page", 1);
						put("pageSize", 2);
						put("siteKey", "\"" + testGroup.getGroupId() + "\"");
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		JSONObject keywordsJSONObject = dataJSONObject.getJSONObject(
			"keywords");

		Assert.assertEquals(0, keywordsJSONObject.get("totalCount"));

		Keyword keyword1 = testGraphQLKeyword_addKeyword();
		Keyword keyword2 = testGraphQLKeyword_addKeyword();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		keywordsJSONObject = dataJSONObject.getJSONObject("keywords");

		Assert.assertEquals(2, keywordsJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(keyword1, keyword2),
			keywordsJSONObject.getJSONArray("items"));
	}

	@Test
	public void testPostSiteKeyword() throws Exception {
		Keyword randomKeyword = randomKeyword();

		Keyword postKeyword = testPostSiteKeyword_addKeyword(randomKeyword);

		assertEquals(randomKeyword, postKeyword);
		assertValid(postKeyword);
	}

	protected Keyword testPostSiteKeyword_addKeyword(Keyword keyword)
		throws Exception {

		return keywordResource.postSiteKeyword(
			testGetSiteKeywordsPage_getSiteId(), keyword);
	}

	@Test
	public void testGraphQLPostSiteKeyword() throws Exception {
		Keyword randomKeyword = randomKeyword();

		Keyword keyword = testGraphQLKeyword_addKeyword(randomKeyword);

		Assert.assertTrue(
			equalsJSONObject(
				randomKeyword,
				JSONFactoryUtil.createJSONObject(
					JSONFactoryUtil.serialize(keyword))));
	}

	protected Keyword testGraphQLKeyword_addKeyword() throws Exception {
		return testGraphQLKeyword_addKeyword(randomKeyword());
	}

	protected Keyword testGraphQLKeyword_addKeyword(Keyword keyword)
		throws Exception {

		StringBuilder sb = new StringBuilder("{");

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("id", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = keyword.getId();

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
					"keywordUsageCount", additionalAssertFieldName)) {

				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = keyword.getKeywordUsageCount();

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

				Object value = keyword.getName();

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

				Object value = keyword.getSiteId();

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
				"createSiteKeyword",
				new HashMap<String, Object>() {
					{
						put("siteKey", "\"" + testGroup.getGroupId() + "\"");
						put("keyword", sb.toString());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONDeserializer<Keyword> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		String object = invoke(graphQLField.toString());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(object);

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		return jsonDeserializer.deserialize(
			String.valueOf(dataJSONObject.getJSONObject("createSiteKeyword")),
			Keyword.class);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Keyword keyword1, Keyword keyword2) {
		Assert.assertTrue(
			keyword1 + " does not equal " + keyword2,
			equals(keyword1, keyword2));
	}

	protected void assertEquals(
		List<Keyword> keywords1, List<Keyword> keywords2) {

		Assert.assertEquals(keywords1.size(), keywords2.size());

		for (int i = 0; i < keywords1.size(); i++) {
			Keyword keyword1 = keywords1.get(i);
			Keyword keyword2 = keywords2.get(i);

			assertEquals(keyword1, keyword2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Keyword> keywords1, List<Keyword> keywords2) {

		Assert.assertEquals(keywords1.size(), keywords2.size());

		for (Keyword keyword1 : keywords1) {
			boolean contains = false;

			for (Keyword keyword2 : keywords2) {
				if (equals(keyword1, keyword2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				keywords2 + " does not contain " + keyword1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<Keyword> keywords, JSONArray jsonArray) {

		for (Keyword keyword : keywords) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(keyword, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + keyword, contains);
		}
	}

	protected void assertValid(Keyword keyword) {
		boolean valid = true;

		if (keyword.getDateCreated() == null) {
			valid = false;
		}

		if (keyword.getDateModified() == null) {
			valid = false;
		}

		if (keyword.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(keyword.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (keyword.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (keyword.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"keywordUsageCount", additionalAssertFieldName)) {

				if (keyword.getKeywordUsageCount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (keyword.getName() == null) {
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

	protected void assertValid(Page<Keyword> page) {
		boolean valid = false;

		java.util.Collection<Keyword> keywords = page.getItems();

		int size = keywords.size();

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

	protected boolean equals(Keyword keyword1, Keyword keyword2) {
		if (keyword1 == keyword2) {
			return true;
		}

		if (!Objects.equals(keyword1.getSiteId(), keyword2.getSiteId())) {
			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						keyword1.getActions(), keyword2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						keyword1.getCreator(), keyword2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						keyword1.getDateCreated(), keyword2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						keyword1.getDateModified(),
						keyword2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(keyword1.getId(), keyword2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals(
					"keywordUsageCount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						keyword1.getKeywordUsageCount(),
						keyword2.getKeywordUsageCount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						keyword1.getName(), keyword2.getName())) {

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

	protected boolean equalsJSONObject(Keyword keyword, JSONObject jsonObject) {
		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						keyword.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywordUsageCount", fieldName)) {
				if (!Objects.deepEquals(
						keyword.getKeywordUsageCount(),
						jsonObject.getInt("keywordUsageCount"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						keyword.getName(), jsonObject.getString("name"))) {

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

		if (!(_keywordResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_keywordResource;

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
		EntityField entityField, String operator, Keyword keyword) {

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
						DateUtils.addSeconds(keyword.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(keyword.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(keyword.getDateCreated()));
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
						DateUtils.addSeconds(keyword.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(keyword.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(keyword.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("keywordUsageCount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(keyword.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("siteId")) {
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

	protected Keyword randomKeyword() throws Exception {
		return new Keyword() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				id = RandomTestUtil.randomLong();
				keywordUsageCount = RandomTestUtil.randomInt();
				name = RandomTestUtil.randomString();
				siteId = testGroup.getGroupId();
			}
		};
	}

	protected Keyword randomIrrelevantKeyword() throws Exception {
		Keyword randomIrrelevantKeyword = randomKeyword();

		randomIrrelevantKeyword.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantKeyword;
	}

	protected Keyword randomPatchKeyword() throws Exception {
		return randomKeyword();
	}

	protected KeywordResource keywordResource;
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
		BaseKeywordResourceTestCase.class);

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
	private com.liferay.headless.admin.taxonomy.resource.v1_0.KeywordResource
		_keywordResource;

}