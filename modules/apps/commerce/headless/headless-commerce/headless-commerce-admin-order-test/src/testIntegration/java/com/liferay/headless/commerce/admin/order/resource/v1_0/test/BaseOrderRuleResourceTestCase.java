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

package com.liferay.headless.commerce.admin.order.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRule;
import com.liferay.headless.commerce.admin.order.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.headless.commerce.admin.order.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderRuleResource;
import com.liferay.headless.commerce.admin.order.client.serdes.v1_0.OrderRuleSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

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

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public abstract class BaseOrderRuleResourceTestCase {

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

		_orderRuleResource.setContextCompany(testCompany);

		OrderRuleResource.Builder builder = OrderRuleResource.builder();

		orderRuleResource = builder.authentication(
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

		OrderRule orderRule1 = randomOrderRule();

		String json = objectMapper.writeValueAsString(orderRule1);

		OrderRule orderRule2 = OrderRuleSerDes.toDTO(json);

		Assert.assertTrue(equals(orderRule1, orderRule2));
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

		OrderRule orderRule = randomOrderRule();

		String json1 = objectMapper.writeValueAsString(orderRule);
		String json2 = OrderRuleSerDes.toJSON(orderRule);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		OrderRule orderRule = randomOrderRule();

		orderRule.setAuthor(regex);
		orderRule.setDescription(regex);
		orderRule.setExternalReferenceCode(regex);
		orderRule.setName(regex);
		orderRule.setType(regex);
		orderRule.setTypeSettings(regex);

		String json = OrderRuleSerDes.toJSON(orderRule);

		Assert.assertFalse(json.contains(regex));

		orderRule = OrderRuleSerDes.toDTO(json);

		Assert.assertEquals(regex, orderRule.getAuthor());
		Assert.assertEquals(regex, orderRule.getDescription());
		Assert.assertEquals(regex, orderRule.getExternalReferenceCode());
		Assert.assertEquals(regex, orderRule.getName());
		Assert.assertEquals(regex, orderRule.getType());
		Assert.assertEquals(regex, orderRule.getTypeSettings());
	}

	@Test
	public void testGetOrderRulesPage() throws Exception {
		Page<OrderRule> page = orderRuleResource.getOrderRulesPage(
			null, null, Pagination.of(1, 10), null);

		long totalCount = page.getTotalCount();

		OrderRule orderRule1 = testGetOrderRulesPage_addOrderRule(
			randomOrderRule());

		OrderRule orderRule2 = testGetOrderRulesPage_addOrderRule(
			randomOrderRule());

		page = orderRuleResource.getOrderRulesPage(
			null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(totalCount + 2, page.getTotalCount());

		assertContains(orderRule1, (List<OrderRule>)page.getItems());
		assertContains(orderRule2, (List<OrderRule>)page.getItems());
		assertValid(page);

		orderRuleResource.deleteOrderRule(orderRule1.getId());

		orderRuleResource.deleteOrderRule(orderRule2.getId());
	}

	@Test
	public void testGetOrderRulesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		OrderRule orderRule1 = randomOrderRule();

		orderRule1 = testGetOrderRulesPage_addOrderRule(orderRule1);

		for (EntityField entityField : entityFields) {
			Page<OrderRule> page = orderRuleResource.getOrderRulesPage(
				null, getFilterString(entityField, "between", orderRule1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(orderRule1),
				(List<OrderRule>)page.getItems());
		}
	}

	@Test
	public void testGetOrderRulesPageWithFilterStringEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		OrderRule orderRule1 = testGetOrderRulesPage_addOrderRule(
			randomOrderRule());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		OrderRule orderRule2 = testGetOrderRulesPage_addOrderRule(
			randomOrderRule());

		for (EntityField entityField : entityFields) {
			Page<OrderRule> page = orderRuleResource.getOrderRulesPage(
				null, getFilterString(entityField, "eq", orderRule1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(orderRule1),
				(List<OrderRule>)page.getItems());
		}
	}

	@Test
	public void testGetOrderRulesPageWithPagination() throws Exception {
		Page<OrderRule> totalPage = orderRuleResource.getOrderRulesPage(
			null, null, null, null);

		int totalCount = GetterUtil.getInteger(totalPage.getTotalCount());

		OrderRule orderRule1 = testGetOrderRulesPage_addOrderRule(
			randomOrderRule());

		OrderRule orderRule2 = testGetOrderRulesPage_addOrderRule(
			randomOrderRule());

		OrderRule orderRule3 = testGetOrderRulesPage_addOrderRule(
			randomOrderRule());

		Page<OrderRule> page1 = orderRuleResource.getOrderRulesPage(
			null, null, Pagination.of(1, totalCount + 2), null);

		List<OrderRule> orderRules1 = (List<OrderRule>)page1.getItems();

		Assert.assertEquals(
			orderRules1.toString(), totalCount + 2, orderRules1.size());

		Page<OrderRule> page2 = orderRuleResource.getOrderRulesPage(
			null, null, Pagination.of(2, totalCount + 2), null);

		Assert.assertEquals(totalCount + 3, page2.getTotalCount());

		List<OrderRule> orderRules2 = (List<OrderRule>)page2.getItems();

		Assert.assertEquals(orderRules2.toString(), 1, orderRules2.size());

		Page<OrderRule> page3 = orderRuleResource.getOrderRulesPage(
			null, null, Pagination.of(1, totalCount + 3), null);

		assertContains(orderRule1, (List<OrderRule>)page3.getItems());
		assertContains(orderRule2, (List<OrderRule>)page3.getItems());
		assertContains(orderRule3, (List<OrderRule>)page3.getItems());
	}

	@Test
	public void testGetOrderRulesPageWithSortDateTime() throws Exception {
		testGetOrderRulesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, orderRule1, orderRule2) -> {
				BeanUtils.setProperty(
					orderRule1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetOrderRulesPageWithSortInteger() throws Exception {
		testGetOrderRulesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, orderRule1, orderRule2) -> {
				BeanUtils.setProperty(orderRule1, entityField.getName(), 0);
				BeanUtils.setProperty(orderRule2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetOrderRulesPageWithSortString() throws Exception {
		testGetOrderRulesPageWithSort(
			EntityField.Type.STRING,
			(entityField, orderRule1, orderRule2) -> {
				Class<?> clazz = orderRule1.getClass();

				String entityFieldName = entityField.getName();

				java.lang.reflect.Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						orderRule1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						orderRule2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						orderRule1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						orderRule2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						orderRule1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						orderRule2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetOrderRulesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, OrderRule, OrderRule, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		OrderRule orderRule1 = randomOrderRule();
		OrderRule orderRule2 = randomOrderRule();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, orderRule1, orderRule2);
		}

		orderRule1 = testGetOrderRulesPage_addOrderRule(orderRule1);

		orderRule2 = testGetOrderRulesPage_addOrderRule(orderRule2);

		for (EntityField entityField : entityFields) {
			Page<OrderRule> ascPage = orderRuleResource.getOrderRulesPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(orderRule1, orderRule2),
				(List<OrderRule>)ascPage.getItems());

			Page<OrderRule> descPage = orderRuleResource.getOrderRulesPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(orderRule2, orderRule1),
				(List<OrderRule>)descPage.getItems());
		}
	}

	protected OrderRule testGetOrderRulesPage_addOrderRule(OrderRule orderRule)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetOrderRulesPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"orderRules",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject orderRulesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/orderRules");

		long totalCount = orderRulesJSONObject.getLong("totalCount");

		OrderRule orderRule1 = testGraphQLOrderRule_addOrderRule();
		OrderRule orderRule2 = testGraphQLOrderRule_addOrderRule();

		orderRulesJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/orderRules");

		Assert.assertEquals(
			totalCount + 2, orderRulesJSONObject.getLong("totalCount"));

		assertContains(
			orderRule1,
			Arrays.asList(
				OrderRuleSerDes.toDTOs(
					orderRulesJSONObject.getString("items"))));
		assertContains(
			orderRule2,
			Arrays.asList(
				OrderRuleSerDes.toDTOs(
					orderRulesJSONObject.getString("items"))));
	}

	@Test
	public void testPostOrderRule() throws Exception {
		OrderRule randomOrderRule = randomOrderRule();

		OrderRule postOrderRule = testPostOrderRule_addOrderRule(
			randomOrderRule);

		assertEquals(randomOrderRule, postOrderRule);
		assertValid(postOrderRule);
	}

	protected OrderRule testPostOrderRule_addOrderRule(OrderRule orderRule)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteOrderRuleByExternalReferenceCode() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		OrderRule orderRule =
			testDeleteOrderRuleByExternalReferenceCode_addOrderRule();

		assertHttpResponseStatusCode(
			204,
			orderRuleResource.
				deleteOrderRuleByExternalReferenceCodeHttpResponse(
					orderRule.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			orderRuleResource.getOrderRuleByExternalReferenceCodeHttpResponse(
				orderRule.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			orderRuleResource.getOrderRuleByExternalReferenceCodeHttpResponse(
				orderRule.getExternalReferenceCode()));
	}

	protected OrderRule
			testDeleteOrderRuleByExternalReferenceCode_addOrderRule()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetOrderRuleByExternalReferenceCode() throws Exception {
		OrderRule postOrderRule =
			testGetOrderRuleByExternalReferenceCode_addOrderRule();

		OrderRule getOrderRule =
			orderRuleResource.getOrderRuleByExternalReferenceCode(
				postOrderRule.getExternalReferenceCode());

		assertEquals(postOrderRule, getOrderRule);
		assertValid(getOrderRule);
	}

	protected OrderRule testGetOrderRuleByExternalReferenceCode_addOrderRule()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetOrderRuleByExternalReferenceCode()
		throws Exception {

		OrderRule orderRule = testGraphQLOrderRule_addOrderRule();

		Assert.assertTrue(
			equals(
				orderRule,
				OrderRuleSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"orderRuleByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												orderRule.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/orderRuleByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetOrderRuleByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"orderRuleByExternalReferenceCode",
						new HashMap<String, Object>() {
							{
								put(
									"externalReferenceCode",
									irrelevantExternalReferenceCode);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchOrderRuleByExternalReferenceCode() throws Exception {
		OrderRule postOrderRule =
			testPatchOrderRuleByExternalReferenceCode_addOrderRule();

		OrderRule randomPatchOrderRule = randomPatchOrderRule();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		OrderRule patchOrderRule =
			orderRuleResource.patchOrderRuleByExternalReferenceCode(
				postOrderRule.getExternalReferenceCode(), randomPatchOrderRule);

		OrderRule expectedPatchOrderRule = postOrderRule.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchOrderRule, randomPatchOrderRule);

		OrderRule getOrderRule =
			orderRuleResource.getOrderRuleByExternalReferenceCode(
				patchOrderRule.getExternalReferenceCode());

		assertEquals(expectedPatchOrderRule, getOrderRule);
		assertValid(getOrderRule);
	}

	protected OrderRule testPatchOrderRuleByExternalReferenceCode_addOrderRule()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteOrderRule() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		OrderRule orderRule = testDeleteOrderRule_addOrderRule();

		assertHttpResponseStatusCode(
			204,
			orderRuleResource.deleteOrderRuleHttpResponse(orderRule.getId()));

		assertHttpResponseStatusCode(
			404, orderRuleResource.getOrderRuleHttpResponse(orderRule.getId()));

		assertHttpResponseStatusCode(
			404, orderRuleResource.getOrderRuleHttpResponse(orderRule.getId()));
	}

	protected OrderRule testDeleteOrderRule_addOrderRule() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteOrderRule() throws Exception {
		OrderRule orderRule = testGraphQLOrderRule_addOrderRule();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteOrderRule",
						new HashMap<String, Object>() {
							{
								put("id", orderRule.getId());
							}
						})),
				"JSONObject/data", "Object/deleteOrderRule"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"orderRule",
					new HashMap<String, Object>() {
						{
							put("id", orderRule.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetOrderRule() throws Exception {
		OrderRule postOrderRule = testGetOrderRule_addOrderRule();

		OrderRule getOrderRule = orderRuleResource.getOrderRule(
			postOrderRule.getId());

		assertEquals(postOrderRule, getOrderRule);
		assertValid(getOrderRule);
	}

	protected OrderRule testGetOrderRule_addOrderRule() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetOrderRule() throws Exception {
		OrderRule orderRule = testGraphQLOrderRule_addOrderRule();

		Assert.assertTrue(
			equals(
				orderRule,
				OrderRuleSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"orderRule",
								new HashMap<String, Object>() {
									{
										put("id", orderRule.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/orderRule"))));
	}

	@Test
	public void testGraphQLGetOrderRuleNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"orderRule",
						new HashMap<String, Object>() {
							{
								put("id", irrelevantId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchOrderRule() throws Exception {
		OrderRule postOrderRule = testPatchOrderRule_addOrderRule();

		OrderRule randomPatchOrderRule = randomPatchOrderRule();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		OrderRule patchOrderRule = orderRuleResource.patchOrderRule(
			postOrderRule.getId(), randomPatchOrderRule);

		OrderRule expectedPatchOrderRule = postOrderRule.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchOrderRule, randomPatchOrderRule);

		OrderRule getOrderRule = orderRuleResource.getOrderRule(
			patchOrderRule.getId());

		assertEquals(expectedPatchOrderRule, getOrderRule);
		assertValid(getOrderRule);
	}

	protected OrderRule testPatchOrderRule_addOrderRule() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected OrderRule testGraphQLOrderRule_addOrderRule() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		OrderRule orderRule, List<OrderRule> orderRules) {

		boolean contains = false;

		for (OrderRule item : orderRules) {
			if (equals(orderRule, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			orderRules + " does not contain " + orderRule, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(OrderRule orderRule1, OrderRule orderRule2) {
		Assert.assertTrue(
			orderRule1 + " does not equal " + orderRule2,
			equals(orderRule1, orderRule2));
	}

	protected void assertEquals(
		List<OrderRule> orderRules1, List<OrderRule> orderRules2) {

		Assert.assertEquals(orderRules1.size(), orderRules2.size());

		for (int i = 0; i < orderRules1.size(); i++) {
			OrderRule orderRule1 = orderRules1.get(i);
			OrderRule orderRule2 = orderRules2.get(i);

			assertEquals(orderRule1, orderRule2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<OrderRule> orderRules1, List<OrderRule> orderRules2) {

		Assert.assertEquals(orderRules1.size(), orderRules2.size());

		for (OrderRule orderRule1 : orderRules1) {
			boolean contains = false;

			for (OrderRule orderRule2 : orderRules2) {
				if (equals(orderRule1, orderRule2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				orderRules2 + " does not contain " + orderRule1, contains);
		}
	}

	protected void assertValid(OrderRule orderRule) throws Exception {
		boolean valid = true;

		if (orderRule.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (orderRule.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (orderRule.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("author", additionalAssertFieldName)) {
				if (orderRule.getAuthor() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (orderRule.getCreateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (orderRule.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("displayDate", additionalAssertFieldName)) {
				if (orderRule.getDisplayDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("expirationDate", additionalAssertFieldName)) {
				if (orderRule.getExpirationDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (orderRule.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (orderRule.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("neverExpire", additionalAssertFieldName)) {
				if (orderRule.getNeverExpire() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderRuleAccount", additionalAssertFieldName)) {
				if (orderRule.getOrderRuleAccount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleAccountGroup", additionalAssertFieldName)) {

				if (orderRule.getOrderRuleAccountGroup() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderRuleChannel", additionalAssertFieldName)) {
				if (orderRule.getOrderRuleChannel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleOrderType", additionalAssertFieldName)) {

				if (orderRule.getOrderRuleOrderType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (orderRule.getPriority() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (orderRule.getType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("typeSettings", additionalAssertFieldName)) {
				if (orderRule.getTypeSettings() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"workflowStatusInfo", additionalAssertFieldName)) {

				if (orderRule.getWorkflowStatusInfo() == null) {
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

	protected void assertValid(Page<OrderRule> page) {
		boolean valid = false;

		java.util.Collection<OrderRule> orderRules = page.getItems();

		int size = orderRules.size();

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

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.commerce.admin.order.dto.v1_0.
						OrderRule.class)) {

			if (!ArrayUtil.contains(
					getAdditionalAssertFieldNames(), field.getName())) {

				continue;
			}

			graphQLFields.addAll(getGraphQLFields(field));
		}

		return graphQLFields;
	}

	protected List<GraphQLField> getGraphQLFields(
			java.lang.reflect.Field... fields)
		throws Exception {

		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field : fields) {
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
					getDeclaredFields(clazz));

				graphQLFields.add(
					new GraphQLField(field.getName(), childrenGraphQLFields));
			}
		}

		return graphQLFields;
	}

	protected String[] getIgnoredEntityFieldNames() {
		return new String[0];
	}

	protected boolean equals(OrderRule orderRule1, OrderRule orderRule2) {
		if (orderRule1 == orderRule2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)orderRule1.getActions(),
						(Map)orderRule2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRule1.getActive(), orderRule2.getActive())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("author", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRule1.getAuthor(), orderRule2.getAuthor())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRule1.getCreateDate(),
						orderRule2.getCreateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRule1.getDescription(),
						orderRule2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("displayDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRule1.getDisplayDate(),
						orderRule2.getDisplayDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("expirationDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRule1.getExpirationDate(),
						orderRule2.getExpirationDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderRule1.getExternalReferenceCode(),
						orderRule2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRule1.getId(), orderRule2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRule1.getName(), orderRule2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("neverExpire", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRule1.getNeverExpire(),
						orderRule2.getNeverExpire())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderRuleAccount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRule1.getOrderRuleAccount(),
						orderRule2.getOrderRuleAccount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleAccountGroup", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderRule1.getOrderRuleAccountGroup(),
						orderRule2.getOrderRuleAccountGroup())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderRuleChannel", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRule1.getOrderRuleChannel(),
						orderRule2.getOrderRuleChannel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleOrderType", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderRule1.getOrderRuleOrderType(),
						orderRule2.getOrderRuleOrderType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRule1.getPriority(), orderRule2.getPriority())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRule1.getType(), orderRule2.getType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("typeSettings", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRule1.getTypeSettings(),
						orderRule2.getTypeSettings())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"workflowStatusInfo", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderRule1.getWorkflowStatusInfo(),
						orderRule2.getWorkflowStatusInfo())) {

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

			return true;
		}

		return false;
	}

	protected java.lang.reflect.Field[] getDeclaredFields(Class clazz)
		throws Exception {

		Stream<java.lang.reflect.Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			java.lang.reflect.Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_orderRuleResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_orderRuleResource;

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
		EntityField entityField, String operator, OrderRule orderRule) {

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

		if (entityFieldName.equals("active")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("author")) {
			sb.append("'");
			sb.append(String.valueOf(orderRule.getAuthor()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("createDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(orderRule.getCreateDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(orderRule.getCreateDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(orderRule.getCreateDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(orderRule.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("displayDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(orderRule.getDisplayDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(orderRule.getDisplayDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(orderRule.getDisplayDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("expirationDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							orderRule.getExpirationDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							orderRule.getExpirationDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(orderRule.getExpirationDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(orderRule.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(orderRule.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("neverExpire")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderRuleAccount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderRuleAccountGroup")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderRuleChannel")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderRuleOrderType")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priority")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("type")) {
			sb.append("'");
			sb.append(String.valueOf(orderRule.getType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("typeSettings")) {
			sb.append("'");
			sb.append(String.valueOf(orderRule.getTypeSettings()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("workflowStatusInfo")) {
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

	protected OrderRule randomOrderRule() throws Exception {
		return new OrderRule() {
			{
				active = RandomTestUtil.randomBoolean();
				author = StringUtil.toLowerCase(RandomTestUtil.randomString());
				createDate = RandomTestUtil.nextDate();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				displayDate = RandomTestUtil.nextDate();
				expirationDate = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				neverExpire = RandomTestUtil.randomBoolean();
				priority = RandomTestUtil.randomDouble();
				type = StringUtil.toLowerCase(RandomTestUtil.randomString());
				typeSettings = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected OrderRule randomIrrelevantOrderRule() throws Exception {
		OrderRule randomIrrelevantOrderRule = randomOrderRule();

		return randomIrrelevantOrderRule;
	}

	protected OrderRule randomPatchOrderRule() throws Exception {
		return randomOrderRule();
	}

	protected OrderRuleResource orderRuleResource;
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
					sb.append(": ");
					sb.append(entry.getValue());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append(")");
			}

			if (!_graphQLFields.isEmpty()) {
				sb.append("{");

				for (GraphQLField graphQLField : _graphQLFields) {
					sb.append(graphQLField.toString());
					sb.append(", ");
				}

				sb.setLength(sb.length() - 2);

				sb.append("}");
			}

			return sb.toString();
		}

		private final List<GraphQLField> _graphQLFields;
		private final String _key;
		private final Map<String, Object> _parameterMap;

	}

	private static final com.liferay.portal.kernel.log.Log _log =
		LogFactoryUtil.getLog(BaseOrderRuleResourceTestCase.class);

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
		com.liferay.headless.commerce.admin.order.resource.v1_0.
			OrderRuleResource _orderRuleResource;

}