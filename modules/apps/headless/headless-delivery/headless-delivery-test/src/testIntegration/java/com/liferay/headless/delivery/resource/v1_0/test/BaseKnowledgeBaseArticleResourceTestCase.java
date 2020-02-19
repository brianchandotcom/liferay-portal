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

import com.liferay.headless.delivery.client.dto.v1_0.KnowledgeBaseArticle;
import com.liferay.headless.delivery.client.dto.v1_0.Rating;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.KnowledgeBaseArticleResource;
import com.liferay.headless.delivery.client.serdes.v1_0.KnowledgeBaseArticleSerDes;
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
public abstract class BaseKnowledgeBaseArticleResourceTestCase {

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

		_knowledgeBaseArticleResource.setContextCompany(testCompany);

		KnowledgeBaseArticleResource.Builder builder =
			KnowledgeBaseArticleResource.builder();

		knowledgeBaseArticleResource = builder.locale(
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

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();

		String json = objectMapper.writeValueAsString(knowledgeBaseArticle1);

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			KnowledgeBaseArticleSerDes.toDTO(json);

		Assert.assertTrue(equals(knowledgeBaseArticle1, knowledgeBaseArticle2));
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

		KnowledgeBaseArticle knowledgeBaseArticle =
			randomKnowledgeBaseArticle();

		String json1 = objectMapper.writeValueAsString(knowledgeBaseArticle);
		String json2 = KnowledgeBaseArticleSerDes.toJSON(knowledgeBaseArticle);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		KnowledgeBaseArticle knowledgeBaseArticle =
			randomKnowledgeBaseArticle();

		knowledgeBaseArticle.setArticleBody(regex);
		knowledgeBaseArticle.setDescription(regex);
		knowledgeBaseArticle.setEncodingFormat(regex);
		knowledgeBaseArticle.setFriendlyUrlPath(regex);
		knowledgeBaseArticle.setTitle(regex);

		String json = KnowledgeBaseArticleSerDes.toJSON(knowledgeBaseArticle);

		Assert.assertFalse(json.contains(regex));

		knowledgeBaseArticle = KnowledgeBaseArticleSerDes.toDTO(json);

		Assert.assertEquals(regex, knowledgeBaseArticle.getArticleBody());
		Assert.assertEquals(regex, knowledgeBaseArticle.getDescription());
		Assert.assertEquals(regex, knowledgeBaseArticle.getEncodingFormat());
		Assert.assertEquals(regex, knowledgeBaseArticle.getFriendlyUrlPath());
		Assert.assertEquals(regex, knowledgeBaseArticle.getTitle());
	}

	@Test
	public void testDeleteKnowledgeBaseArticle() throws Exception {
		KnowledgeBaseArticle knowledgeBaseArticle =
			testDeleteKnowledgeBaseArticle_addKnowledgeBaseArticle();

		assertHttpResponseStatusCode(
			204,
			knowledgeBaseArticleResource.deleteKnowledgeBaseArticleHttpResponse(
				knowledgeBaseArticle.getId()));

		assertHttpResponseStatusCode(
			404,
			knowledgeBaseArticleResource.getKnowledgeBaseArticleHttpResponse(
				knowledgeBaseArticle.getId()));

		assertHttpResponseStatusCode(
			404,
			knowledgeBaseArticleResource.getKnowledgeBaseArticleHttpResponse(
				0L));
	}

	protected KnowledgeBaseArticle
			testDeleteKnowledgeBaseArticle_addKnowledgeBaseArticle()
		throws Exception {

		return knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
			testGroup.getGroupId(), randomKnowledgeBaseArticle());
	}

	@Test
	public void testGraphQLDeleteKnowledgeBaseArticle() throws Exception {
		KnowledgeBaseArticle knowledgeBaseArticle =
			testGraphQLKnowledgeBaseArticle_addKnowledgeBaseArticle();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteKnowledgeBaseArticle",
				new HashMap<String, Object>() {
					{
						put(
							"knowledgeBaseArticleId",
							knowledgeBaseArticle.getId());
					}
				}));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			dataJSONObject.getBoolean("deleteKnowledgeBaseArticle"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			graphQLField = new GraphQLField(
				"query",
				new GraphQLField(
					"knowledgeBaseArticle",
					new HashMap<String, Object>() {
						{
							put(
								"knowledgeBaseArticleId",
								knowledgeBaseArticle.getId());
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
	public void testGetKnowledgeBaseArticle() throws Exception {
		KnowledgeBaseArticle postKnowledgeBaseArticle =
			testGetKnowledgeBaseArticle_addKnowledgeBaseArticle();

		KnowledgeBaseArticle getKnowledgeBaseArticle =
			knowledgeBaseArticleResource.getKnowledgeBaseArticle(
				postKnowledgeBaseArticle.getId());

		assertEquals(postKnowledgeBaseArticle, getKnowledgeBaseArticle);
		assertValid(getKnowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle
			testGetKnowledgeBaseArticle_addKnowledgeBaseArticle()
		throws Exception {

		return knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
			testGroup.getGroupId(), randomKnowledgeBaseArticle());
	}

	@Test
	public void testGraphQLGetKnowledgeBaseArticle() throws Exception {
		KnowledgeBaseArticle knowledgeBaseArticle =
			testGraphQLKnowledgeBaseArticle_addKnowledgeBaseArticle();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"knowledgeBaseArticle",
				new HashMap<String, Object>() {
					{
						put(
							"knowledgeBaseArticleId",
							knowledgeBaseArticle.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				knowledgeBaseArticle,
				dataJSONObject.getJSONObject("knowledgeBaseArticle")));
	}

	@Test
	public void testPatchKnowledgeBaseArticle() throws Exception {
		KnowledgeBaseArticle postKnowledgeBaseArticle =
			testPatchKnowledgeBaseArticle_addKnowledgeBaseArticle();

		KnowledgeBaseArticle randomPatchKnowledgeBaseArticle =
			randomPatchKnowledgeBaseArticle();

		KnowledgeBaseArticle patchKnowledgeBaseArticle =
			knowledgeBaseArticleResource.patchKnowledgeBaseArticle(
				postKnowledgeBaseArticle.getId(),
				randomPatchKnowledgeBaseArticle);

		KnowledgeBaseArticle expectedPatchKnowledgeBaseArticle =
			postKnowledgeBaseArticle.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchKnowledgeBaseArticle, randomPatchKnowledgeBaseArticle);

		KnowledgeBaseArticle getKnowledgeBaseArticle =
			knowledgeBaseArticleResource.getKnowledgeBaseArticle(
				patchKnowledgeBaseArticle.getId());

		assertEquals(
			expectedPatchKnowledgeBaseArticle, getKnowledgeBaseArticle);
		assertValid(getKnowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle
			testPatchKnowledgeBaseArticle_addKnowledgeBaseArticle()
		throws Exception {

		return knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
			testGroup.getGroupId(), randomKnowledgeBaseArticle());
	}

	@Test
	public void testPutKnowledgeBaseArticle() throws Exception {
		KnowledgeBaseArticle postKnowledgeBaseArticle =
			testPutKnowledgeBaseArticle_addKnowledgeBaseArticle();

		KnowledgeBaseArticle randomKnowledgeBaseArticle =
			randomKnowledgeBaseArticle();

		KnowledgeBaseArticle putKnowledgeBaseArticle =
			knowledgeBaseArticleResource.putKnowledgeBaseArticle(
				postKnowledgeBaseArticle.getId(), randomKnowledgeBaseArticle);

		assertEquals(randomKnowledgeBaseArticle, putKnowledgeBaseArticle);
		assertValid(putKnowledgeBaseArticle);

		KnowledgeBaseArticle getKnowledgeBaseArticle =
			knowledgeBaseArticleResource.getKnowledgeBaseArticle(
				putKnowledgeBaseArticle.getId());

		assertEquals(randomKnowledgeBaseArticle, getKnowledgeBaseArticle);
		assertValid(getKnowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle
			testPutKnowledgeBaseArticle_addKnowledgeBaseArticle()
		throws Exception {

		return knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
			testGroup.getGroupId(), randomKnowledgeBaseArticle());
	}

	@Test
	public void testDeleteKnowledgeBaseArticleMyRating() throws Exception {
		KnowledgeBaseArticle knowledgeBaseArticle =
			testDeleteKnowledgeBaseArticleMyRating_addKnowledgeBaseArticle();

		assertHttpResponseStatusCode(
			204,
			knowledgeBaseArticleResource.
				deleteKnowledgeBaseArticleMyRatingHttpResponse(
					knowledgeBaseArticle.getId()));

		assertHttpResponseStatusCode(
			404,
			knowledgeBaseArticleResource.
				getKnowledgeBaseArticleMyRatingHttpResponse(
					knowledgeBaseArticle.getId()));

		assertHttpResponseStatusCode(
			404,
			knowledgeBaseArticleResource.
				getKnowledgeBaseArticleMyRatingHttpResponse(0L));
	}

	protected KnowledgeBaseArticle
			testDeleteKnowledgeBaseArticleMyRating_addKnowledgeBaseArticle()
		throws Exception {

		return knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
			testGroup.getGroupId(), randomKnowledgeBaseArticle());
	}

	@Test
	public void testPutKnowledgeBaseArticleSubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		KnowledgeBaseArticle knowledgeBaseArticle =
			testPutKnowledgeBaseArticleSubscribe_addKnowledgeBaseArticle();

		assertHttpResponseStatusCode(
			204,
			knowledgeBaseArticleResource.
				putKnowledgeBaseArticleSubscribeHttpResponse(
					knowledgeBaseArticle.getId()));

		assertHttpResponseStatusCode(
			404,
			knowledgeBaseArticleResource.
				putKnowledgeBaseArticleSubscribeHttpResponse(0L));
	}

	protected KnowledgeBaseArticle
			testPutKnowledgeBaseArticleSubscribe_addKnowledgeBaseArticle()
		throws Exception {

		return knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
			testGroup.getGroupId(), randomKnowledgeBaseArticle());
	}

	@Test
	public void testPutKnowledgeBaseArticleUnsubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		KnowledgeBaseArticle knowledgeBaseArticle =
			testPutKnowledgeBaseArticleUnsubscribe_addKnowledgeBaseArticle();

		assertHttpResponseStatusCode(
			204,
			knowledgeBaseArticleResource.
				putKnowledgeBaseArticleUnsubscribeHttpResponse(
					knowledgeBaseArticle.getId()));

		assertHttpResponseStatusCode(
			404,
			knowledgeBaseArticleResource.
				putKnowledgeBaseArticleUnsubscribeHttpResponse(0L));
	}

	protected KnowledgeBaseArticle
			testPutKnowledgeBaseArticleUnsubscribe_addKnowledgeBaseArticle()
		throws Exception {

		return knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
			testGroup.getGroupId(), randomKnowledgeBaseArticle());
	}

	@Test
	public void testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage()
		throws Exception {

		Page<KnowledgeBaseArticle> page =
			knowledgeBaseArticleResource.
				getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId(),
					null, RandomTestUtil.randomString(), null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long parentKnowledgeBaseArticleId =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId();
		Long irrelevantParentKnowledgeBaseArticleId =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getIrrelevantParentKnowledgeBaseArticleId();

		if ((irrelevantParentKnowledgeBaseArticleId != null)) {
			KnowledgeBaseArticle irrelevantKnowledgeBaseArticle =
				testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
					irrelevantParentKnowledgeBaseArticleId,
					randomIrrelevantKnowledgeBaseArticle());

			page =
				knowledgeBaseArticleResource.
					getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
						irrelevantParentKnowledgeBaseArticleId, null, null,
						null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKnowledgeBaseArticle),
				(List<KnowledgeBaseArticle>)page.getItems());
			assertValid(page);
		}

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, randomKnowledgeBaseArticle());

		page =
			knowledgeBaseArticleResource.
				getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					parentKnowledgeBaseArticleId, null, null, null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
			(List<KnowledgeBaseArticle>)page.getItems());
		assertValid(page);

		knowledgeBaseArticleResource.deleteKnowledgeBaseArticle(
			knowledgeBaseArticle1.getId());

		knowledgeBaseArticleResource.deleteKnowledgeBaseArticle(
			knowledgeBaseArticle2.getId());
	}

	@Test
	public void testGetKnowledgeBaseArticleKnowledgeBaseArticlesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentKnowledgeBaseArticleId =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();

		knowledgeBaseArticle1 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, knowledgeBaseArticle1);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				knowledgeBaseArticleResource.
					getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
						parentKnowledgeBaseArticleId, null, null,
						getFilterString(
							entityField, "between", knowledgeBaseArticle1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)page.getItems());
		}
	}

	@Test
	public void testGetKnowledgeBaseArticleKnowledgeBaseArticlesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentKnowledgeBaseArticleId =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, randomKnowledgeBaseArticle());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, randomKnowledgeBaseArticle());

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				knowledgeBaseArticleResource.
					getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
						parentKnowledgeBaseArticleId, null, null,
						getFilterString(
							entityField, "eq", knowledgeBaseArticle1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)page.getItems());
		}
	}

	@Test
	public void testGetKnowledgeBaseArticleKnowledgeBaseArticlesPageWithPagination()
		throws Exception {

		Long parentKnowledgeBaseArticleId =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle3 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, randomKnowledgeBaseArticle());

		Page<KnowledgeBaseArticle> page1 =
			knowledgeBaseArticleResource.
				getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					parentKnowledgeBaseArticleId, null, null, null,
					Pagination.of(1, 2), null);

		List<KnowledgeBaseArticle> knowledgeBaseArticles1 =
			(List<KnowledgeBaseArticle>)page1.getItems();

		Assert.assertEquals(
			knowledgeBaseArticles1.toString(), 2,
			knowledgeBaseArticles1.size());

		Page<KnowledgeBaseArticle> page2 =
			knowledgeBaseArticleResource.
				getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					parentKnowledgeBaseArticleId, null, null, null,
					Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<KnowledgeBaseArticle> knowledgeBaseArticles2 =
			(List<KnowledgeBaseArticle>)page2.getItems();

		Assert.assertEquals(
			knowledgeBaseArticles2.toString(), 1,
			knowledgeBaseArticles2.size());

		Page<KnowledgeBaseArticle> page3 =
			knowledgeBaseArticleResource.
				getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
					parentKnowledgeBaseArticleId, null, null, null,
					Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				knowledgeBaseArticle1, knowledgeBaseArticle2,
				knowledgeBaseArticle3),
			(List<KnowledgeBaseArticle>)page3.getItems());
	}

	@Test
	public void testGetKnowledgeBaseArticleKnowledgeBaseArticlesPageWithSortDateTime()
		throws Exception {

		testGetKnowledgeBaseArticleKnowledgeBaseArticlesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, knowledgeBaseArticle1, knowledgeBaseArticle2) -> {
				BeanUtils.setProperty(
					knowledgeBaseArticle1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetKnowledgeBaseArticleKnowledgeBaseArticlesPageWithSortInteger()
		throws Exception {

		testGetKnowledgeBaseArticleKnowledgeBaseArticlesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, knowledgeBaseArticle1, knowledgeBaseArticle2) -> {
				BeanUtils.setProperty(
					knowledgeBaseArticle1, entityField.getName(), 0);
				BeanUtils.setProperty(
					knowledgeBaseArticle2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetKnowledgeBaseArticleKnowledgeBaseArticlesPageWithSortString()
		throws Exception {

		testGetKnowledgeBaseArticleKnowledgeBaseArticlesPageWithSort(
			EntityField.Type.STRING,
			(entityField, knowledgeBaseArticle1, knowledgeBaseArticle2) -> {
				Class<?> clazz = knowledgeBaseArticle1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						knowledgeBaseArticle1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						knowledgeBaseArticle2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						knowledgeBaseArticle1, entityField.getName(),
						"Aaa" + RandomTestUtil.randomString());
					BeanUtils.setProperty(
						knowledgeBaseArticle2, entityField.getName(),
						"Bbb" + RandomTestUtil.randomString());
				}
			});
	}

	protected void testGetKnowledgeBaseArticleKnowledgeBaseArticlesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, KnowledgeBaseArticle, KnowledgeBaseArticle,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long parentKnowledgeBaseArticleId =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			randomKnowledgeBaseArticle();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, knowledgeBaseArticle1, knowledgeBaseArticle2);
		}

		knowledgeBaseArticle1 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, knowledgeBaseArticle1);

		knowledgeBaseArticle2 =
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, knowledgeBaseArticle2);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> ascPage =
				knowledgeBaseArticleResource.
					getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
						parentKnowledgeBaseArticleId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
				(List<KnowledgeBaseArticle>)ascPage.getItems());

			Page<KnowledgeBaseArticle> descPage =
				knowledgeBaseArticleResource.
					getKnowledgeBaseArticleKnowledgeBaseArticlesPage(
						parentKnowledgeBaseArticleId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle2, knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)descPage.getItems());
		}
	}

	protected KnowledgeBaseArticle
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				Long parentKnowledgeBaseArticleId,
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return knowledgeBaseArticleResource.
			postKnowledgeBaseArticleKnowledgeBaseArticle(
				parentKnowledgeBaseArticleId, knowledgeBaseArticle);
	}

	protected Long
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getIrrelevantParentKnowledgeBaseArticleId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostKnowledgeBaseArticleKnowledgeBaseArticle()
		throws Exception {

		KnowledgeBaseArticle randomKnowledgeBaseArticle =
			randomKnowledgeBaseArticle();

		KnowledgeBaseArticle postKnowledgeBaseArticle =
			testPostKnowledgeBaseArticleKnowledgeBaseArticle_addKnowledgeBaseArticle(
				randomKnowledgeBaseArticle);

		assertEquals(randomKnowledgeBaseArticle, postKnowledgeBaseArticle);
		assertValid(postKnowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle
			testPostKnowledgeBaseArticleKnowledgeBaseArticle_addKnowledgeBaseArticle(
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return knowledgeBaseArticleResource.
			postKnowledgeBaseArticleKnowledgeBaseArticle(
				testGetKnowledgeBaseArticleKnowledgeBaseArticlesPage_getParentKnowledgeBaseArticleId(),
				knowledgeBaseArticle);
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage()
		throws Exception {

		Page<KnowledgeBaseArticle> page =
			knowledgeBaseArticleResource.
				getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId(),
					null, RandomTestUtil.randomString(), null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long knowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId();
		Long irrelevantKnowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getIrrelevantKnowledgeBaseFolderId();

		if ((irrelevantKnowledgeBaseFolderId != null)) {
			KnowledgeBaseArticle irrelevantKnowledgeBaseArticle =
				testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
					irrelevantKnowledgeBaseFolderId,
					randomIrrelevantKnowledgeBaseArticle());

			page =
				knowledgeBaseArticleResource.
					getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
						irrelevantKnowledgeBaseFolderId, null, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKnowledgeBaseArticle),
				(List<KnowledgeBaseArticle>)page.getItems());
			assertValid(page);
		}

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, randomKnowledgeBaseArticle());

		page =
			knowledgeBaseArticleResource.
				getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					knowledgeBaseFolderId, null, null, null,
					Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
			(List<KnowledgeBaseArticle>)page.getItems());
		assertValid(page);

		knowledgeBaseArticleResource.deleteKnowledgeBaseArticle(
			knowledgeBaseArticle1.getId());

		knowledgeBaseArticleResource.deleteKnowledgeBaseArticle(
			knowledgeBaseArticle2.getId());
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseArticlesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long knowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();

		knowledgeBaseArticle1 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, knowledgeBaseArticle1);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				knowledgeBaseArticleResource.
					getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
						knowledgeBaseFolderId, null, null,
						getFilterString(
							entityField, "between", knowledgeBaseArticle1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)page.getItems());
		}
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseArticlesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long knowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, randomKnowledgeBaseArticle());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, randomKnowledgeBaseArticle());

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				knowledgeBaseArticleResource.
					getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
						knowledgeBaseFolderId, null, null,
						getFilterString(
							entityField, "eq", knowledgeBaseArticle1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)page.getItems());
		}
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseArticlesPageWithPagination()
		throws Exception {

		Long knowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle3 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, randomKnowledgeBaseArticle());

		Page<KnowledgeBaseArticle> page1 =
			knowledgeBaseArticleResource.
				getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					knowledgeBaseFolderId, null, null, null,
					Pagination.of(1, 2), null);

		List<KnowledgeBaseArticle> knowledgeBaseArticles1 =
			(List<KnowledgeBaseArticle>)page1.getItems();

		Assert.assertEquals(
			knowledgeBaseArticles1.toString(), 2,
			knowledgeBaseArticles1.size());

		Page<KnowledgeBaseArticle> page2 =
			knowledgeBaseArticleResource.
				getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					knowledgeBaseFolderId, null, null, null,
					Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<KnowledgeBaseArticle> knowledgeBaseArticles2 =
			(List<KnowledgeBaseArticle>)page2.getItems();

		Assert.assertEquals(
			knowledgeBaseArticles2.toString(), 1,
			knowledgeBaseArticles2.size());

		Page<KnowledgeBaseArticle> page3 =
			knowledgeBaseArticleResource.
				getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
					knowledgeBaseFolderId, null, null, null,
					Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				knowledgeBaseArticle1, knowledgeBaseArticle2,
				knowledgeBaseArticle3),
			(List<KnowledgeBaseArticle>)page3.getItems());
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseArticlesPageWithSortDateTime()
		throws Exception {

		testGetKnowledgeBaseFolderKnowledgeBaseArticlesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, knowledgeBaseArticle1, knowledgeBaseArticle2) -> {
				BeanUtils.setProperty(
					knowledgeBaseArticle1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseArticlesPageWithSortInteger()
		throws Exception {

		testGetKnowledgeBaseFolderKnowledgeBaseArticlesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, knowledgeBaseArticle1, knowledgeBaseArticle2) -> {
				BeanUtils.setProperty(
					knowledgeBaseArticle1, entityField.getName(), 0);
				BeanUtils.setProperty(
					knowledgeBaseArticle2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetKnowledgeBaseFolderKnowledgeBaseArticlesPageWithSortString()
		throws Exception {

		testGetKnowledgeBaseFolderKnowledgeBaseArticlesPageWithSort(
			EntityField.Type.STRING,
			(entityField, knowledgeBaseArticle1, knowledgeBaseArticle2) -> {
				Class<?> clazz = knowledgeBaseArticle1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						knowledgeBaseArticle1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						knowledgeBaseArticle2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						knowledgeBaseArticle1, entityField.getName(),
						"Aaa" + RandomTestUtil.randomString());
					BeanUtils.setProperty(
						knowledgeBaseArticle2, entityField.getName(),
						"Bbb" + RandomTestUtil.randomString());
				}
			});
	}

	protected void testGetKnowledgeBaseFolderKnowledgeBaseArticlesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, KnowledgeBaseArticle, KnowledgeBaseArticle,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long knowledgeBaseFolderId =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			randomKnowledgeBaseArticle();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, knowledgeBaseArticle1, knowledgeBaseArticle2);
		}

		knowledgeBaseArticle1 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, knowledgeBaseArticle1);

		knowledgeBaseArticle2 =
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				knowledgeBaseFolderId, knowledgeBaseArticle2);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> ascPage =
				knowledgeBaseArticleResource.
					getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
						knowledgeBaseFolderId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
				(List<KnowledgeBaseArticle>)ascPage.getItems());

			Page<KnowledgeBaseArticle> descPage =
				knowledgeBaseArticleResource.
					getKnowledgeBaseFolderKnowledgeBaseArticlesPage(
						knowledgeBaseFolderId, null, null, null,
						Pagination.of(1, 2), entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle2, knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)descPage.getItems());
		}
	}

	protected KnowledgeBaseArticle
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				Long knowledgeBaseFolderId,
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return knowledgeBaseArticleResource.
			postKnowledgeBaseFolderKnowledgeBaseArticle(
				knowledgeBaseFolderId, knowledgeBaseArticle);
	}

	protected Long
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getIrrelevantKnowledgeBaseFolderId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostKnowledgeBaseFolderKnowledgeBaseArticle()
		throws Exception {

		KnowledgeBaseArticle randomKnowledgeBaseArticle =
			randomKnowledgeBaseArticle();

		KnowledgeBaseArticle postKnowledgeBaseArticle =
			testPostKnowledgeBaseFolderKnowledgeBaseArticle_addKnowledgeBaseArticle(
				randomKnowledgeBaseArticle);

		assertEquals(randomKnowledgeBaseArticle, postKnowledgeBaseArticle);
		assertValid(postKnowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle
			testPostKnowledgeBaseFolderKnowledgeBaseArticle_addKnowledgeBaseArticle(
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return knowledgeBaseArticleResource.
			postKnowledgeBaseFolderKnowledgeBaseArticle(
				testGetKnowledgeBaseFolderKnowledgeBaseArticlesPage_getKnowledgeBaseFolderId(),
				knowledgeBaseArticle);
	}

	@Test
	public void testGetSiteKnowledgeBaseArticlesPage() throws Exception {
		Page<KnowledgeBaseArticle> page =
			knowledgeBaseArticleResource.getSiteKnowledgeBaseArticlesPage(
				testGetSiteKnowledgeBaseArticlesPage_getSiteId(), null,
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteKnowledgeBaseArticlesPage_getSiteId();
		Long irrelevantSiteId =
			testGetSiteKnowledgeBaseArticlesPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			KnowledgeBaseArticle irrelevantKnowledgeBaseArticle =
				testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
					irrelevantSiteId, randomIrrelevantKnowledgeBaseArticle());

			page =
				knowledgeBaseArticleResource.getSiteKnowledgeBaseArticlesPage(
					irrelevantSiteId, null, null, null, Pagination.of(1, 2),
					null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantKnowledgeBaseArticle),
				(List<KnowledgeBaseArticle>)page.getItems());
			assertValid(page);
		}

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, randomKnowledgeBaseArticle());

		page = knowledgeBaseArticleResource.getSiteKnowledgeBaseArticlesPage(
			siteId, null, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
			(List<KnowledgeBaseArticle>)page.getItems());
		assertValid(page);

		knowledgeBaseArticleResource.deleteKnowledgeBaseArticle(
			knowledgeBaseArticle1.getId());

		knowledgeBaseArticleResource.deleteKnowledgeBaseArticle(
			knowledgeBaseArticle2.getId());
	}

	@Test
	public void testGetSiteKnowledgeBaseArticlesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteKnowledgeBaseArticlesPage_getSiteId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();

		knowledgeBaseArticle1 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, knowledgeBaseArticle1);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				knowledgeBaseArticleResource.getSiteKnowledgeBaseArticlesPage(
					siteId, null, null,
					getFilterString(
						entityField, "between", knowledgeBaseArticle1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)page.getItems());
		}
	}

	@Test
	public void testGetSiteKnowledgeBaseArticlesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteKnowledgeBaseArticlesPage_getSiteId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, randomKnowledgeBaseArticle());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, randomKnowledgeBaseArticle());

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> page =
				knowledgeBaseArticleResource.getSiteKnowledgeBaseArticlesPage(
					siteId, null, null,
					getFilterString(entityField, "eq", knowledgeBaseArticle1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)page.getItems());
		}
	}

	@Test
	public void testGetSiteKnowledgeBaseArticlesPageWithPagination()
		throws Exception {

		Long siteId = testGetSiteKnowledgeBaseArticlesPage_getSiteId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, randomKnowledgeBaseArticle());

		KnowledgeBaseArticle knowledgeBaseArticle3 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, randomKnowledgeBaseArticle());

		Page<KnowledgeBaseArticle> page1 =
			knowledgeBaseArticleResource.getSiteKnowledgeBaseArticlesPage(
				siteId, null, null, null, Pagination.of(1, 2), null);

		List<KnowledgeBaseArticle> knowledgeBaseArticles1 =
			(List<KnowledgeBaseArticle>)page1.getItems();

		Assert.assertEquals(
			knowledgeBaseArticles1.toString(), 2,
			knowledgeBaseArticles1.size());

		Page<KnowledgeBaseArticle> page2 =
			knowledgeBaseArticleResource.getSiteKnowledgeBaseArticlesPage(
				siteId, null, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<KnowledgeBaseArticle> knowledgeBaseArticles2 =
			(List<KnowledgeBaseArticle>)page2.getItems();

		Assert.assertEquals(
			knowledgeBaseArticles2.toString(), 1,
			knowledgeBaseArticles2.size());

		Page<KnowledgeBaseArticle> page3 =
			knowledgeBaseArticleResource.getSiteKnowledgeBaseArticlesPage(
				siteId, null, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				knowledgeBaseArticle1, knowledgeBaseArticle2,
				knowledgeBaseArticle3),
			(List<KnowledgeBaseArticle>)page3.getItems());
	}

	@Test
	public void testGetSiteKnowledgeBaseArticlesPageWithSortDateTime()
		throws Exception {

		testGetSiteKnowledgeBaseArticlesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, knowledgeBaseArticle1, knowledgeBaseArticle2) -> {
				BeanUtils.setProperty(
					knowledgeBaseArticle1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteKnowledgeBaseArticlesPageWithSortInteger()
		throws Exception {

		testGetSiteKnowledgeBaseArticlesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, knowledgeBaseArticle1, knowledgeBaseArticle2) -> {
				BeanUtils.setProperty(
					knowledgeBaseArticle1, entityField.getName(), 0);
				BeanUtils.setProperty(
					knowledgeBaseArticle2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteKnowledgeBaseArticlesPageWithSortString()
		throws Exception {

		testGetSiteKnowledgeBaseArticlesPageWithSort(
			EntityField.Type.STRING,
			(entityField, knowledgeBaseArticle1, knowledgeBaseArticle2) -> {
				Class<?> clazz = knowledgeBaseArticle1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						knowledgeBaseArticle1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						knowledgeBaseArticle2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						knowledgeBaseArticle1, entityField.getName(),
						"Aaa" + RandomTestUtil.randomString());
					BeanUtils.setProperty(
						knowledgeBaseArticle2, entityField.getName(),
						"Bbb" + RandomTestUtil.randomString());
				}
			});
	}

	protected void testGetSiteKnowledgeBaseArticlesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, KnowledgeBaseArticle, KnowledgeBaseArticle,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteKnowledgeBaseArticlesPage_getSiteId();

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			randomKnowledgeBaseArticle();
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			randomKnowledgeBaseArticle();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, knowledgeBaseArticle1, knowledgeBaseArticle2);
		}

		knowledgeBaseArticle1 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, knowledgeBaseArticle1);

		knowledgeBaseArticle2 =
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				siteId, knowledgeBaseArticle2);

		for (EntityField entityField : entityFields) {
			Page<KnowledgeBaseArticle> ascPage =
				knowledgeBaseArticleResource.getSiteKnowledgeBaseArticlesPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
				(List<KnowledgeBaseArticle>)ascPage.getItems());

			Page<KnowledgeBaseArticle> descPage =
				knowledgeBaseArticleResource.getSiteKnowledgeBaseArticlesPage(
					siteId, null, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(knowledgeBaseArticle2, knowledgeBaseArticle1),
				(List<KnowledgeBaseArticle>)descPage.getItems());
		}
	}

	protected KnowledgeBaseArticle
			testGetSiteKnowledgeBaseArticlesPage_addKnowledgeBaseArticle(
				Long siteId, KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
			siteId, knowledgeBaseArticle);
	}

	protected Long testGetSiteKnowledgeBaseArticlesPage_getSiteId()
		throws Exception {

		return testGroup.getGroupId();
	}

	protected Long testGetSiteKnowledgeBaseArticlesPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteKnowledgeBaseArticlesPage() throws Exception {
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
				"knowledgeBaseArticles",
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

		JSONObject knowledgeBaseArticlesJSONObject =
			dataJSONObject.getJSONObject("knowledgeBaseArticles");

		Assert.assertEquals(
			0, knowledgeBaseArticlesJSONObject.get("totalCount"));

		KnowledgeBaseArticle knowledgeBaseArticle1 =
			testGraphQLKnowledgeBaseArticle_addKnowledgeBaseArticle();
		KnowledgeBaseArticle knowledgeBaseArticle2 =
			testGraphQLKnowledgeBaseArticle_addKnowledgeBaseArticle();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		knowledgeBaseArticlesJSONObject = dataJSONObject.getJSONObject(
			"knowledgeBaseArticles");

		Assert.assertEquals(
			2, knowledgeBaseArticlesJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(knowledgeBaseArticle1, knowledgeBaseArticle2),
			knowledgeBaseArticlesJSONObject.getJSONArray("items"));
	}

	@Test
	public void testPostSiteKnowledgeBaseArticle() throws Exception {
		KnowledgeBaseArticle randomKnowledgeBaseArticle =
			randomKnowledgeBaseArticle();

		KnowledgeBaseArticle postKnowledgeBaseArticle =
			testPostSiteKnowledgeBaseArticle_addKnowledgeBaseArticle(
				randomKnowledgeBaseArticle);

		assertEquals(randomKnowledgeBaseArticle, postKnowledgeBaseArticle);
		assertValid(postKnowledgeBaseArticle);
	}

	protected KnowledgeBaseArticle
			testPostSiteKnowledgeBaseArticle_addKnowledgeBaseArticle(
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		return knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
			testGetSiteKnowledgeBaseArticlesPage_getSiteId(),
			knowledgeBaseArticle);
	}

	@Test
	public void testGraphQLPostSiteKnowledgeBaseArticle() throws Exception {
		KnowledgeBaseArticle randomKnowledgeBaseArticle =
			randomKnowledgeBaseArticle();

		KnowledgeBaseArticle knowledgeBaseArticle =
			testGraphQLKnowledgeBaseArticle_addKnowledgeBaseArticle(
				randomKnowledgeBaseArticle);

		Assert.assertTrue(
			equalsJSONObject(
				randomKnowledgeBaseArticle,
				JSONFactoryUtil.createJSONObject(
					JSONFactoryUtil.serialize(knowledgeBaseArticle))));
	}

	@Test
	public void testPutSiteKnowledgeBaseArticleSubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		KnowledgeBaseArticle knowledgeBaseArticle =
			testPutSiteKnowledgeBaseArticleSubscribe_addKnowledgeBaseArticle();

		assertHttpResponseStatusCode(
			204,
			knowledgeBaseArticleResource.
				putSiteKnowledgeBaseArticleSubscribeHttpResponse(
					testGroup.getGroupId()));

		assertHttpResponseStatusCode(
			404,
			knowledgeBaseArticleResource.
				putSiteKnowledgeBaseArticleSubscribeHttpResponse(
					testGroup.getGroupId()));
	}

	protected KnowledgeBaseArticle
			testPutSiteKnowledgeBaseArticleSubscribe_addKnowledgeBaseArticle()
		throws Exception {

		return knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
			testGroup.getGroupId(), randomKnowledgeBaseArticle());
	}

	@Test
	public void testPutSiteKnowledgeBaseArticleUnsubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		KnowledgeBaseArticle knowledgeBaseArticle =
			testPutSiteKnowledgeBaseArticleUnsubscribe_addKnowledgeBaseArticle();

		assertHttpResponseStatusCode(
			204,
			knowledgeBaseArticleResource.
				putSiteKnowledgeBaseArticleUnsubscribeHttpResponse(
					testGroup.getGroupId()));

		assertHttpResponseStatusCode(
			404,
			knowledgeBaseArticleResource.
				putSiteKnowledgeBaseArticleUnsubscribeHttpResponse(
					testGroup.getGroupId()));
	}

	protected KnowledgeBaseArticle
			testPutSiteKnowledgeBaseArticleUnsubscribe_addKnowledgeBaseArticle()
		throws Exception {

		return knowledgeBaseArticleResource.postSiteKnowledgeBaseArticle(
			testGroup.getGroupId(), randomKnowledgeBaseArticle());
	}

	@Test
	public void testGetKnowledgeBaseArticleMyRating() throws Exception {
		KnowledgeBaseArticle postKnowledgeBaseArticle =
			testGetKnowledgeBaseArticle_addKnowledgeBaseArticle();

		Rating postRating = testGetKnowledgeBaseArticleMyRating_addRating(
			postKnowledgeBaseArticle.getId(), randomRating());

		Rating getRating =
			knowledgeBaseArticleResource.getKnowledgeBaseArticleMyRating(
				postKnowledgeBaseArticle.getId());

		assertEquals(postRating, getRating);
		assertValid(getRating);
	}

	protected Rating testGetKnowledgeBaseArticleMyRating_addRating(
			long knowledgeBaseArticleId, Rating rating)
		throws Exception {

		return knowledgeBaseArticleResource.postKnowledgeBaseArticleMyRating(
			knowledgeBaseArticleId, rating);
	}

	@Test
	public void testPostKnowledgeBaseArticleMyRating() throws Exception {
		Assert.assertTrue(true);
	}

	@Test
	public void testPutKnowledgeBaseArticleMyRating() throws Exception {
		KnowledgeBaseArticle postKnowledgeBaseArticle =
			testPutKnowledgeBaseArticle_addKnowledgeBaseArticle();

		testPutKnowledgeBaseArticleMyRating_addRating(
			postKnowledgeBaseArticle.getId(), randomRating());

		Rating randomRating = randomRating();

		Rating putRating =
			knowledgeBaseArticleResource.putKnowledgeBaseArticleMyRating(
				postKnowledgeBaseArticle.getId(), randomRating);

		assertEquals(randomRating, putRating);
		assertValid(putRating);
	}

	protected Rating testPutKnowledgeBaseArticleMyRating_addRating(
			long knowledgeBaseArticleId, Rating rating)
		throws Exception {

		return knowledgeBaseArticleResource.postKnowledgeBaseArticleMyRating(
			knowledgeBaseArticleId, rating);
	}

	protected KnowledgeBaseArticle
			testGraphQLKnowledgeBaseArticle_addKnowledgeBaseArticle()
		throws Exception {

		return testGraphQLKnowledgeBaseArticle_addKnowledgeBaseArticle(
			randomKnowledgeBaseArticle());
	}

	protected KnowledgeBaseArticle
			testGraphQLKnowledgeBaseArticle_addKnowledgeBaseArticle(
				KnowledgeBaseArticle knowledgeBaseArticle)
		throws Exception {

		StringBuilder sb = new StringBuilder("{");

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("articleBody", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = knowledgeBaseArticle.getArticleBody();

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

			if (Objects.equals("description", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = knowledgeBaseArticle.getDescription();

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

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = knowledgeBaseArticle.getEncodingFormat();

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

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = knowledgeBaseArticle.getFriendlyUrlPath();

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

				Object value = knowledgeBaseArticle.getId();

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
					"numberOfAttachments", additionalAssertFieldName)) {

				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = knowledgeBaseArticle.getNumberOfAttachments();

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
					"numberOfKnowledgeBaseArticles",
					additionalAssertFieldName)) {

				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value =
					knowledgeBaseArticle.getNumberOfKnowledgeBaseArticles();

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
					"parentKnowledgeBaseFolderId", additionalAssertFieldName)) {

				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value =
					knowledgeBaseArticle.getParentKnowledgeBaseFolderId();

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

				Object value = knowledgeBaseArticle.getSiteId();

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

				Object value = knowledgeBaseArticle.getSubscribed();

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

			if (Objects.equals("title", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = knowledgeBaseArticle.getTitle();

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
				"createSiteKnowledgeBaseArticle",
				new HashMap<String, Object>() {
					{
						put("siteKey", "\"" + testGroup.getGroupId() + "\"");
						put("knowledgeBaseArticle", sb.toString());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONDeserializer<KnowledgeBaseArticle> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		String object = invoke(graphQLField.toString());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(object);

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		return jsonDeserializer.deserialize(
			String.valueOf(
				dataJSONObject.getJSONObject("createSiteKnowledgeBaseArticle")),
			KnowledgeBaseArticle.class);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		KnowledgeBaseArticle knowledgeBaseArticle1,
		KnowledgeBaseArticle knowledgeBaseArticle2) {

		Assert.assertTrue(
			knowledgeBaseArticle1 + " does not equal " + knowledgeBaseArticle2,
			equals(knowledgeBaseArticle1, knowledgeBaseArticle2));
	}

	protected void assertEquals(
		List<KnowledgeBaseArticle> knowledgeBaseArticles1,
		List<KnowledgeBaseArticle> knowledgeBaseArticles2) {

		Assert.assertEquals(
			knowledgeBaseArticles1.size(), knowledgeBaseArticles2.size());

		for (int i = 0; i < knowledgeBaseArticles1.size(); i++) {
			KnowledgeBaseArticle knowledgeBaseArticle1 =
				knowledgeBaseArticles1.get(i);
			KnowledgeBaseArticle knowledgeBaseArticle2 =
				knowledgeBaseArticles2.get(i);

			assertEquals(knowledgeBaseArticle1, knowledgeBaseArticle2);
		}
	}

	protected void assertEquals(Rating rating1, Rating rating2) {
		Assert.assertTrue(
			rating1 + " does not equal " + rating2, equals(rating1, rating2));
	}

	protected void assertEqualsIgnoringOrder(
		List<KnowledgeBaseArticle> knowledgeBaseArticles1,
		List<KnowledgeBaseArticle> knowledgeBaseArticles2) {

		Assert.assertEquals(
			knowledgeBaseArticles1.size(), knowledgeBaseArticles2.size());

		for (KnowledgeBaseArticle knowledgeBaseArticle1 :
				knowledgeBaseArticles1) {

			boolean contains = false;

			for (KnowledgeBaseArticle knowledgeBaseArticle2 :
					knowledgeBaseArticles2) {

				if (equals(knowledgeBaseArticle1, knowledgeBaseArticle2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				knowledgeBaseArticles2 + " does not contain " +
					knowledgeBaseArticle1,
				contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<KnowledgeBaseArticle> knowledgeBaseArticles, JSONArray jsonArray) {

		for (KnowledgeBaseArticle knowledgeBaseArticle :
				knowledgeBaseArticles) {

			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(
						knowledgeBaseArticle, (JSONObject)object)) {

					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + knowledgeBaseArticle,
				contains);
		}
	}

	protected void assertValid(KnowledgeBaseArticle knowledgeBaseArticle) {
		boolean valid = true;

		if (knowledgeBaseArticle.getDateCreated() == null) {
			valid = false;
		}

		if (knowledgeBaseArticle.getDateModified() == null) {
			valid = false;
		}

		if (knowledgeBaseArticle.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(
				knowledgeBaseArticle.getSiteId(), testGroup.getGroupId())) {

			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getAggregateRating() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("articleBody", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getArticleBody() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getEncodingFormat() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getFriendlyUrlPath() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfAttachments", additionalAssertFieldName)) {

				if (knowledgeBaseArticle.getNumberOfAttachments() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfKnowledgeBaseArticles",
					additionalAssertFieldName)) {

				if (knowledgeBaseArticle.getNumberOfKnowledgeBaseArticles() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"parentKnowledgeBaseFolder", additionalAssertFieldName)) {

				if (knowledgeBaseArticle.getParentKnowledgeBaseFolder() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"parentKnowledgeBaseFolderId", additionalAssertFieldName)) {

				if (knowledgeBaseArticle.getParentKnowledgeBaseFolderId() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getRelatedContents() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getSubscribed() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategories", additionalAssertFieldName)) {

				if (knowledgeBaseArticle.getTaxonomyCategories() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (knowledgeBaseArticle.getTaxonomyCategoryIds() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (knowledgeBaseArticle.getViewableBy() == null) {
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

	protected void assertValid(Page<KnowledgeBaseArticle> page) {
		boolean valid = false;

		java.util.Collection<KnowledgeBaseArticle> knowledgeBaseArticles =
			page.getItems();

		int size = knowledgeBaseArticles.size();

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
		KnowledgeBaseArticle knowledgeBaseArticle1,
		KnowledgeBaseArticle knowledgeBaseArticle2) {

		if (knowledgeBaseArticle1 == knowledgeBaseArticle2) {
			return true;
		}

		if (!Objects.equals(
				knowledgeBaseArticle1.getSiteId(),
				knowledgeBaseArticle2.getSiteId())) {

			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getActions(),
						knowledgeBaseArticle2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("aggregateRating", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getAggregateRating(),
						knowledgeBaseArticle2.getAggregateRating())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("articleBody", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getArticleBody(),
						knowledgeBaseArticle2.getArticleBody())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getCreator(),
						knowledgeBaseArticle2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getCustomFields(),
						knowledgeBaseArticle2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getDateCreated(),
						knowledgeBaseArticle2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getDateModified(),
						knowledgeBaseArticle2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getDescription(),
						knowledgeBaseArticle2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getEncodingFormat(),
						knowledgeBaseArticle2.getEncodingFormat())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getFriendlyUrlPath(),
						knowledgeBaseArticle2.getFriendlyUrlPath())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getId(),
						knowledgeBaseArticle2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getKeywords(),
						knowledgeBaseArticle2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfAttachments", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getNumberOfAttachments(),
						knowledgeBaseArticle2.getNumberOfAttachments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfKnowledgeBaseArticles",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseArticle1.
							getNumberOfKnowledgeBaseArticles(),
						knowledgeBaseArticle2.
							getNumberOfKnowledgeBaseArticles())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"parentKnowledgeBaseFolder", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getParentKnowledgeBaseFolder(),
						knowledgeBaseArticle2.getParentKnowledgeBaseFolder())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"parentKnowledgeBaseFolderId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getParentKnowledgeBaseFolderId(),
						knowledgeBaseArticle2.
							getParentKnowledgeBaseFolderId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("relatedContents", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getRelatedContents(),
						knowledgeBaseArticle2.getRelatedContents())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getSubscribed(),
						knowledgeBaseArticle2.getSubscribed())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategories", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getTaxonomyCategories(),
						knowledgeBaseArticle2.getTaxonomyCategories())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxonomyCategoryIds", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getTaxonomyCategoryIds(),
						knowledgeBaseArticle2.getTaxonomyCategoryIds())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getTitle(),
						knowledgeBaseArticle2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle1.getViewableBy(),
						knowledgeBaseArticle2.getViewableBy())) {

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

	protected boolean equalsJSONObject(
		KnowledgeBaseArticle knowledgeBaseArticle, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("articleBody", fieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle.getArticleBody(),
						jsonObject.getString("articleBody"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", fieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle.getDescription(),
						jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("encodingFormat", fieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle.getEncodingFormat(),
						jsonObject.getString("encodingFormat"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("friendlyUrlPath", fieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle.getFriendlyUrlPath(),
						jsonObject.getString("friendlyUrlPath"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle.getId(),
						jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfAttachments", fieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle.getNumberOfAttachments(),
						jsonObject.getInt("numberOfAttachments"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfKnowledgeBaseArticles", fieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle.getNumberOfKnowledgeBaseArticles(),
						jsonObject.getInt("numberOfKnowledgeBaseArticles"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("parentKnowledgeBaseFolderId", fieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle.getParentKnowledgeBaseFolderId(),
						jsonObject.getLong("parentKnowledgeBaseFolderId"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscribed", fieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle.getSubscribed(),
						jsonObject.getBoolean("subscribed"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", fieldName)) {
				if (!Objects.deepEquals(
						knowledgeBaseArticle.getTitle(),
						jsonObject.getString("title"))) {

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

		if (!(_knowledgeBaseArticleResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_knowledgeBaseArticleResource;

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
		KnowledgeBaseArticle knowledgeBaseArticle) {

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

		if (entityFieldName.equals("articleBody")) {
			sb.append("'");
			sb.append(String.valueOf(knowledgeBaseArticle.getArticleBody()));
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
						DateUtils.addSeconds(
							knowledgeBaseArticle.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							knowledgeBaseArticle.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(knowledgeBaseArticle.getDateCreated()));
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
							knowledgeBaseArticle.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							knowledgeBaseArticle.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(knowledgeBaseArticle.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(knowledgeBaseArticle.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("encodingFormat")) {
			sb.append("'");
			sb.append(String.valueOf(knowledgeBaseArticle.getEncodingFormat()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("friendlyUrlPath")) {
			sb.append("'");
			sb.append(
				String.valueOf(knowledgeBaseArticle.getFriendlyUrlPath()));
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

		if (entityFieldName.equals("numberOfKnowledgeBaseArticles")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentKnowledgeBaseFolder")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentKnowledgeBaseFolderId")) {
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
			sb.append(String.valueOf(knowledgeBaseArticle.getTitle()));
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

	protected KnowledgeBaseArticle randomKnowledgeBaseArticle()
		throws Exception {

		return new KnowledgeBaseArticle() {
			{
				articleBody = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				encodingFormat = RandomTestUtil.randomString();
				friendlyUrlPath = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				numberOfAttachments = RandomTestUtil.randomInt();
				numberOfKnowledgeBaseArticles = RandomTestUtil.randomInt();
				parentKnowledgeBaseFolderId = RandomTestUtil.randomLong();
				siteId = testGroup.getGroupId();
				subscribed = RandomTestUtil.randomBoolean();
				title = RandomTestUtil.randomString();
			}
		};
	}

	protected KnowledgeBaseArticle randomIrrelevantKnowledgeBaseArticle()
		throws Exception {

		KnowledgeBaseArticle randomIrrelevantKnowledgeBaseArticle =
			randomKnowledgeBaseArticle();

		randomIrrelevantKnowledgeBaseArticle.setSiteId(
			irrelevantGroup.getGroupId());

		return randomIrrelevantKnowledgeBaseArticle;
	}

	protected KnowledgeBaseArticle randomPatchKnowledgeBaseArticle()
		throws Exception {

		return randomKnowledgeBaseArticle();
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

	protected KnowledgeBaseArticleResource knowledgeBaseArticleResource;
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
		BaseKnowledgeBaseArticleResourceTestCase.class);

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
	private
		com.liferay.headless.delivery.resource.v1_0.KnowledgeBaseArticleResource
			_knowledgeBaseArticleResource;

}