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

import com.liferay.headless.delivery.client.dto.v1_0.WikiNode;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.WikiNodeResource;
import com.liferay.headless.delivery.client.serdes.v1_0.WikiNodeSerDes;
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
public abstract class BaseWikiNodeResourceTestCase {

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

		_wikiNodeResource.setContextCompany(testCompany);

		WikiNodeResource.Builder builder = WikiNodeResource.builder();

		wikiNodeResource = builder.locale(
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

		WikiNode wikiNode1 = randomWikiNode();

		String json = objectMapper.writeValueAsString(wikiNode1);

		WikiNode wikiNode2 = WikiNodeSerDes.toDTO(json);

		Assert.assertTrue(equals(wikiNode1, wikiNode2));
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

		WikiNode wikiNode = randomWikiNode();

		String json1 = objectMapper.writeValueAsString(wikiNode);
		String json2 = WikiNodeSerDes.toJSON(wikiNode);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		WikiNode wikiNode = randomWikiNode();

		wikiNode.setDescription(regex);
		wikiNode.setName(regex);

		String json = WikiNodeSerDes.toJSON(wikiNode);

		Assert.assertFalse(json.contains(regex));

		wikiNode = WikiNodeSerDes.toDTO(json);

		Assert.assertEquals(regex, wikiNode.getDescription());
		Assert.assertEquals(regex, wikiNode.getName());
	}

	@Test
	public void testGetSiteWikiNodesPage() throws Exception {
		Page<WikiNode> page = wikiNodeResource.getSiteWikiNodesPage(
			testGetSiteWikiNodesPage_getSiteId(), RandomTestUtil.randomString(),
			null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long siteId = testGetSiteWikiNodesPage_getSiteId();
		Long irrelevantSiteId = testGetSiteWikiNodesPage_getIrrelevantSiteId();

		if ((irrelevantSiteId != null)) {
			WikiNode irrelevantWikiNode = testGetSiteWikiNodesPage_addWikiNode(
				irrelevantSiteId, randomIrrelevantWikiNode());

			page = wikiNodeResource.getSiteWikiNodesPage(
				irrelevantSiteId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantWikiNode),
				(List<WikiNode>)page.getItems());
			assertValid(page);
		}

		WikiNode wikiNode1 = testGetSiteWikiNodesPage_addWikiNode(
			siteId, randomWikiNode());

		WikiNode wikiNode2 = testGetSiteWikiNodesPage_addWikiNode(
			siteId, randomWikiNode());

		page = wikiNodeResource.getSiteWikiNodesPage(
			siteId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(wikiNode1, wikiNode2),
			(List<WikiNode>)page.getItems());
		assertValid(page);

		wikiNodeResource.deleteWikiNode(wikiNode1.getId());

		wikiNodeResource.deleteWikiNode(wikiNode2.getId());
	}

	@Test
	public void testGetSiteWikiNodesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteWikiNodesPage_getSiteId();

		WikiNode wikiNode1 = randomWikiNode();

		wikiNode1 = testGetSiteWikiNodesPage_addWikiNode(siteId, wikiNode1);

		for (EntityField entityField : entityFields) {
			Page<WikiNode> page = wikiNodeResource.getSiteWikiNodesPage(
				siteId, null,
				getFilterString(entityField, "between", wikiNode1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(wikiNode1),
				(List<WikiNode>)page.getItems());
		}
	}

	@Test
	public void testGetSiteWikiNodesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteWikiNodesPage_getSiteId();

		WikiNode wikiNode1 = testGetSiteWikiNodesPage_addWikiNode(
			siteId, randomWikiNode());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		WikiNode wikiNode2 = testGetSiteWikiNodesPage_addWikiNode(
			siteId, randomWikiNode());

		for (EntityField entityField : entityFields) {
			Page<WikiNode> page = wikiNodeResource.getSiteWikiNodesPage(
				siteId, null, getFilterString(entityField, "eq", wikiNode1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(wikiNode1),
				(List<WikiNode>)page.getItems());
		}
	}

	@Test
	public void testGetSiteWikiNodesPageWithPagination() throws Exception {
		Long siteId = testGetSiteWikiNodesPage_getSiteId();

		WikiNode wikiNode1 = testGetSiteWikiNodesPage_addWikiNode(
			siteId, randomWikiNode());

		WikiNode wikiNode2 = testGetSiteWikiNodesPage_addWikiNode(
			siteId, randomWikiNode());

		WikiNode wikiNode3 = testGetSiteWikiNodesPage_addWikiNode(
			siteId, randomWikiNode());

		Page<WikiNode> page1 = wikiNodeResource.getSiteWikiNodesPage(
			siteId, null, null, Pagination.of(1, 2), null);

		List<WikiNode> wikiNodes1 = (List<WikiNode>)page1.getItems();

		Assert.assertEquals(wikiNodes1.toString(), 2, wikiNodes1.size());

		Page<WikiNode> page2 = wikiNodeResource.getSiteWikiNodesPage(
			siteId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<WikiNode> wikiNodes2 = (List<WikiNode>)page2.getItems();

		Assert.assertEquals(wikiNodes2.toString(), 1, wikiNodes2.size());

		Page<WikiNode> page3 = wikiNodeResource.getSiteWikiNodesPage(
			siteId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(wikiNode1, wikiNode2, wikiNode3),
			(List<WikiNode>)page3.getItems());
	}

	@Test
	public void testGetSiteWikiNodesPageWithSortDateTime() throws Exception {
		testGetSiteWikiNodesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, wikiNode1, wikiNode2) -> {
				BeanUtils.setProperty(
					wikiNode1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetSiteWikiNodesPageWithSortInteger() throws Exception {
		testGetSiteWikiNodesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, wikiNode1, wikiNode2) -> {
				BeanUtils.setProperty(wikiNode1, entityField.getName(), 0);
				BeanUtils.setProperty(wikiNode2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetSiteWikiNodesPageWithSortString() throws Exception {
		testGetSiteWikiNodesPageWithSort(
			EntityField.Type.STRING,
			(entityField, wikiNode1, wikiNode2) -> {
				Class<?> clazz = wikiNode1.getClass();

				Method method = clazz.getMethod(
					"get" +
						StringUtil.upperCaseFirstLetter(entityField.getName()));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						wikiNode1, entityField.getName(),
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						wikiNode2, entityField.getName(),
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else {
					BeanUtils.setProperty(
						wikiNode1, entityField.getName(),
						"Aaa" + RandomTestUtil.randomString());
					BeanUtils.setProperty(
						wikiNode2, entityField.getName(),
						"Bbb" + RandomTestUtil.randomString());
				}
			});
	}

	protected void testGetSiteWikiNodesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, WikiNode, WikiNode, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long siteId = testGetSiteWikiNodesPage_getSiteId();

		WikiNode wikiNode1 = randomWikiNode();
		WikiNode wikiNode2 = randomWikiNode();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, wikiNode1, wikiNode2);
		}

		wikiNode1 = testGetSiteWikiNodesPage_addWikiNode(siteId, wikiNode1);

		wikiNode2 = testGetSiteWikiNodesPage_addWikiNode(siteId, wikiNode2);

		for (EntityField entityField : entityFields) {
			Page<WikiNode> ascPage = wikiNodeResource.getSiteWikiNodesPage(
				siteId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(wikiNode1, wikiNode2),
				(List<WikiNode>)ascPage.getItems());

			Page<WikiNode> descPage = wikiNodeResource.getSiteWikiNodesPage(
				siteId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(wikiNode2, wikiNode1),
				(List<WikiNode>)descPage.getItems());
		}
	}

	protected WikiNode testGetSiteWikiNodesPage_addWikiNode(
			Long siteId, WikiNode wikiNode)
		throws Exception {

		return wikiNodeResource.postSiteWikiNode(siteId, wikiNode);
	}

	protected Long testGetSiteWikiNodesPage_getSiteId() throws Exception {
		return testGroup.getGroupId();
	}

	protected Long testGetSiteWikiNodesPage_getIrrelevantSiteId()
		throws Exception {

		return irrelevantGroup.getGroupId();
	}

	@Test
	public void testGraphQLGetSiteWikiNodesPage() throws Exception {
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
				"wikiNodes",
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

		JSONObject wikiNodesJSONObject = dataJSONObject.getJSONObject(
			"wikiNodes");

		Assert.assertEquals(0, wikiNodesJSONObject.get("totalCount"));

		WikiNode wikiNode1 = testGraphQLWikiNode_addWikiNode();
		WikiNode wikiNode2 = testGraphQLWikiNode_addWikiNode();

		jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		dataJSONObject = jsonObject.getJSONObject("data");

		wikiNodesJSONObject = dataJSONObject.getJSONObject("wikiNodes");

		Assert.assertEquals(2, wikiNodesJSONObject.get("totalCount"));

		assertEqualsJSONArray(
			Arrays.asList(wikiNode1, wikiNode2),
			wikiNodesJSONObject.getJSONArray("items"));
	}

	@Test
	public void testPostSiteWikiNode() throws Exception {
		WikiNode randomWikiNode = randomWikiNode();

		WikiNode postWikiNode = testPostSiteWikiNode_addWikiNode(
			randomWikiNode);

		assertEquals(randomWikiNode, postWikiNode);
		assertValid(postWikiNode);
	}

	protected WikiNode testPostSiteWikiNode_addWikiNode(WikiNode wikiNode)
		throws Exception {

		return wikiNodeResource.postSiteWikiNode(
			testGetSiteWikiNodesPage_getSiteId(), wikiNode);
	}

	@Test
	public void testGraphQLPostSiteWikiNode() throws Exception {
		WikiNode randomWikiNode = randomWikiNode();

		WikiNode wikiNode = testGraphQLWikiNode_addWikiNode(randomWikiNode);

		Assert.assertTrue(
			equalsJSONObject(
				randomWikiNode,
				JSONFactoryUtil.createJSONObject(
					JSONFactoryUtil.serialize(wikiNode))));
	}

	@Test
	public void testDeleteWikiNode() throws Exception {
		WikiNode wikiNode = testDeleteWikiNode_addWikiNode();

		assertHttpResponseStatusCode(
			204, wikiNodeResource.deleteWikiNodeHttpResponse(wikiNode.getId()));

		assertHttpResponseStatusCode(
			404, wikiNodeResource.getWikiNodeHttpResponse(wikiNode.getId()));

		assertHttpResponseStatusCode(
			404, wikiNodeResource.getWikiNodeHttpResponse(0L));
	}

	protected WikiNode testDeleteWikiNode_addWikiNode() throws Exception {
		return wikiNodeResource.postSiteWikiNode(
			testGroup.getGroupId(), randomWikiNode());
	}

	@Test
	public void testGraphQLDeleteWikiNode() throws Exception {
		WikiNode wikiNode = testGraphQLWikiNode_addWikiNode();

		GraphQLField graphQLField = new GraphQLField(
			"mutation",
			new GraphQLField(
				"deleteWikiNode",
				new HashMap<String, Object>() {
					{
						put("wikiNodeId", wikiNode.getId());
					}
				}));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(dataJSONObject.getBoolean("deleteWikiNode"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			graphQLField = new GraphQLField(
				"query",
				new GraphQLField(
					"wikiNode",
					new HashMap<String, Object>() {
						{
							put("wikiNodeId", wikiNode.getId());
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
	public void testGetWikiNode() throws Exception {
		WikiNode postWikiNode = testGetWikiNode_addWikiNode();

		WikiNode getWikiNode = wikiNodeResource.getWikiNode(
			postWikiNode.getId());

		assertEquals(postWikiNode, getWikiNode);
		assertValid(getWikiNode);
	}

	protected WikiNode testGetWikiNode_addWikiNode() throws Exception {
		return wikiNodeResource.postSiteWikiNode(
			testGroup.getGroupId(), randomWikiNode());
	}

	@Test
	public void testGraphQLGetWikiNode() throws Exception {
		WikiNode wikiNode = testGraphQLWikiNode_addWikiNode();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"wikiNode",
				new HashMap<String, Object>() {
					{
						put("wikiNodeId", wikiNode.getId());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equalsJSONObject(
				wikiNode, dataJSONObject.getJSONObject("wikiNode")));
	}

	@Test
	public void testPutWikiNode() throws Exception {
		WikiNode postWikiNode = testPutWikiNode_addWikiNode();

		WikiNode randomWikiNode = randomWikiNode();

		WikiNode putWikiNode = wikiNodeResource.putWikiNode(
			postWikiNode.getId(), randomWikiNode);

		assertEquals(randomWikiNode, putWikiNode);
		assertValid(putWikiNode);

		WikiNode getWikiNode = wikiNodeResource.getWikiNode(
			putWikiNode.getId());

		assertEquals(randomWikiNode, getWikiNode);
		assertValid(getWikiNode);
	}

	protected WikiNode testPutWikiNode_addWikiNode() throws Exception {
		return wikiNodeResource.postSiteWikiNode(
			testGroup.getGroupId(), randomWikiNode());
	}

	@Test
	public void testPutWikiNodeSubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		WikiNode wikiNode = testPutWikiNodeSubscribe_addWikiNode();

		assertHttpResponseStatusCode(
			204,
			wikiNodeResource.putWikiNodeSubscribeHttpResponse(
				wikiNode.getId()));

		assertHttpResponseStatusCode(
			404, wikiNodeResource.putWikiNodeSubscribeHttpResponse(0L));
	}

	protected WikiNode testPutWikiNodeSubscribe_addWikiNode() throws Exception {
		return wikiNodeResource.postSiteWikiNode(
			testGroup.getGroupId(), randomWikiNode());
	}

	@Test
	public void testPutWikiNodeUnsubscribe() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		WikiNode wikiNode = testPutWikiNodeUnsubscribe_addWikiNode();

		assertHttpResponseStatusCode(
			204,
			wikiNodeResource.putWikiNodeUnsubscribeHttpResponse(
				wikiNode.getId()));

		assertHttpResponseStatusCode(
			404, wikiNodeResource.putWikiNodeUnsubscribeHttpResponse(0L));
	}

	protected WikiNode testPutWikiNodeUnsubscribe_addWikiNode()
		throws Exception {

		return wikiNodeResource.postSiteWikiNode(
			testGroup.getGroupId(), randomWikiNode());
	}

	protected WikiNode testGraphQLWikiNode_addWikiNode() throws Exception {
		return testGraphQLWikiNode_addWikiNode(randomWikiNode());
	}

	protected WikiNode testGraphQLWikiNode_addWikiNode(WikiNode wikiNode)
		throws Exception {

		StringBuilder sb = new StringBuilder("{");

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("description", additionalAssertFieldName)) {
				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = wikiNode.getDescription();

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

				Object value = wikiNode.getId();

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

				Object value = wikiNode.getName();

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
					"numberOfWikiPages", additionalAssertFieldName)) {

				sb.append(additionalAssertFieldName);
				sb.append(": ");

				Object value = wikiNode.getNumberOfWikiPages();

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

				Object value = wikiNode.getSiteId();

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

				Object value = wikiNode.getSubscribed();

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
				"createSiteWikiNode",
				new HashMap<String, Object>() {
					{
						put("siteKey", "\"" + testGroup.getGroupId() + "\"");
						put("wikiNode", sb.toString());
					}
				},
				graphQLFields.toArray(new GraphQLField[0])));

		JSONDeserializer<WikiNode> jsonDeserializer =
			JSONFactoryUtil.createJSONDeserializer();

		String object = invoke(graphQLField.toString());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(object);

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		return jsonDeserializer.deserialize(
			String.valueOf(dataJSONObject.getJSONObject("createSiteWikiNode")),
			WikiNode.class);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(WikiNode wikiNode1, WikiNode wikiNode2) {
		Assert.assertTrue(
			wikiNode1 + " does not equal " + wikiNode2,
			equals(wikiNode1, wikiNode2));
	}

	protected void assertEquals(
		List<WikiNode> wikiNodes1, List<WikiNode> wikiNodes2) {

		Assert.assertEquals(wikiNodes1.size(), wikiNodes2.size());

		for (int i = 0; i < wikiNodes1.size(); i++) {
			WikiNode wikiNode1 = wikiNodes1.get(i);
			WikiNode wikiNode2 = wikiNodes2.get(i);

			assertEquals(wikiNode1, wikiNode2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<WikiNode> wikiNodes1, List<WikiNode> wikiNodes2) {

		Assert.assertEquals(wikiNodes1.size(), wikiNodes2.size());

		for (WikiNode wikiNode1 : wikiNodes1) {
			boolean contains = false;

			for (WikiNode wikiNode2 : wikiNodes2) {
				if (equals(wikiNode1, wikiNode2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				wikiNodes2 + " does not contain " + wikiNode1, contains);
		}
	}

	protected void assertEqualsJSONArray(
		List<WikiNode> wikiNodes, JSONArray jsonArray) {

		for (WikiNode wikiNode : wikiNodes) {
			boolean contains = false;

			for (Object object : jsonArray) {
				if (equalsJSONObject(wikiNode, (JSONObject)object)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				jsonArray + " does not contain " + wikiNode, contains);
		}
	}

	protected void assertValid(WikiNode wikiNode) {
		boolean valid = true;

		if (wikiNode.getDateCreated() == null) {
			valid = false;
		}

		if (wikiNode.getDateModified() == null) {
			valid = false;
		}

		if (wikiNode.getId() == null) {
			valid = false;
		}

		if (!Objects.equals(wikiNode.getSiteId(), testGroup.getGroupId())) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (wikiNode.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (wikiNode.getCreator() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (wikiNode.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (wikiNode.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfWikiPages", additionalAssertFieldName)) {

				if (wikiNode.getNumberOfWikiPages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (wikiNode.getSubscribed() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (wikiNode.getViewableBy() == null) {
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

	protected void assertValid(Page<WikiNode> page) {
		boolean valid = false;

		java.util.Collection<WikiNode> wikiNodes = page.getItems();

		int size = wikiNodes.size();

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

	protected boolean equals(WikiNode wikiNode1, WikiNode wikiNode2) {
		if (wikiNode1 == wikiNode2) {
			return true;
		}

		if (!Objects.equals(wikiNode1.getSiteId(), wikiNode2.getSiteId())) {
			return false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiNode1.getActions(), wikiNode2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("creator", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiNode1.getCreator(), wikiNode2.getCreator())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiNode1.getDateCreated(),
						wikiNode2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiNode1.getDateModified(),
						wikiNode2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiNode1.getDescription(),
						wikiNode2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(wikiNode1.getId(), wikiNode2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiNode1.getName(), wikiNode2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"numberOfWikiPages", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						wikiNode1.getNumberOfWikiPages(),
						wikiNode2.getNumberOfWikiPages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscribed", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiNode1.getSubscribed(), wikiNode2.getSubscribed())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("viewableBy", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						wikiNode1.getViewableBy(), wikiNode2.getViewableBy())) {

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
		WikiNode wikiNode, JSONObject jsonObject) {

		for (String fieldName : getAdditionalAssertFieldNames()) {
			if (Objects.equals("description", fieldName)) {
				if (!Objects.deepEquals(
						wikiNode.getDescription(),
						jsonObject.getString("description"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", fieldName)) {
				if (!Objects.deepEquals(
						wikiNode.getId(), jsonObject.getLong("id"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", fieldName)) {
				if (!Objects.deepEquals(
						wikiNode.getName(), jsonObject.getString("name"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfWikiPages", fieldName)) {
				if (!Objects.deepEquals(
						wikiNode.getNumberOfWikiPages(),
						jsonObject.getInt("numberOfWikiPages"))) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscribed", fieldName)) {
				if (!Objects.deepEquals(
						wikiNode.getSubscribed(),
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

		if (!(_wikiNodeResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_wikiNodeResource;

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
		EntityField entityField, String operator, WikiNode wikiNode) {

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
						DateUtils.addSeconds(wikiNode.getDateCreated(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(wikiNode.getDateCreated(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(wikiNode.getDateCreated()));
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
						DateUtils.addSeconds(wikiNode.getDateModified(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(wikiNode.getDateModified(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(wikiNode.getDateModified()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(wikiNode.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(wikiNode.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("numberOfWikiPages")) {
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

	protected WikiNode randomWikiNode() throws Exception {
		return new WikiNode() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				numberOfWikiPages = RandomTestUtil.randomInt();
				siteId = testGroup.getGroupId();
				subscribed = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected WikiNode randomIrrelevantWikiNode() throws Exception {
		WikiNode randomIrrelevantWikiNode = randomWikiNode();

		randomIrrelevantWikiNode.setSiteId(irrelevantGroup.getGroupId());

		return randomIrrelevantWikiNode;
	}

	protected WikiNode randomPatchWikiNode() throws Exception {
		return randomWikiNode();
	}

	protected WikiNodeResource wikiNodeResource;
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
		BaseWikiNodeResourceTestCase.class);

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
	private com.liferay.headless.delivery.resource.v1_0.WikiNodeResource
		_wikiNodeResource;

}