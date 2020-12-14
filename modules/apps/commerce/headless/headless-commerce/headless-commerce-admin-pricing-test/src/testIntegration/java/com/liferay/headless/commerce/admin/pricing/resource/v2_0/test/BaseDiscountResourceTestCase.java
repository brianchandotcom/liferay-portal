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

package com.liferay.headless.commerce.admin.pricing.resource.v2_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.Discount;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.DiscountResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.DiscountSerDes;
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
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public abstract class BaseDiscountResourceTestCase {

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

		_discountResource.setContextCompany(testCompany);

		DiscountResource.Builder builder = DiscountResource.builder();

		discountResource = builder.authentication(
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

		Discount discount1 = randomDiscount();

		String json = objectMapper.writeValueAsString(discount1);

		Discount discount2 = DiscountSerDes.toDTO(json);

		Assert.assertTrue(equals(discount1, discount2));
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

		Discount discount = randomDiscount();

		String json1 = objectMapper.writeValueAsString(discount);
		String json2 = DiscountSerDes.toJSON(discount);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Discount discount = randomDiscount();

		discount.setAmountFormatted(regex);
		discount.setCouponCode(regex);
		discount.setExternalReferenceCode(regex);
		discount.setLevel(regex);
		discount.setLimitationType(regex);
		discount.setTarget(regex);
		discount.setTitle(regex);

		String json = DiscountSerDes.toJSON(discount);

		Assert.assertFalse(json.contains(regex));

		discount = DiscountSerDes.toDTO(json);

		Assert.assertEquals(regex, discount.getAmountFormatted());
		Assert.assertEquals(regex, discount.getCouponCode());
		Assert.assertEquals(regex, discount.getExternalReferenceCode());
		Assert.assertEquals(regex, discount.getLevel());
		Assert.assertEquals(regex, discount.getLimitationType());
		Assert.assertEquals(regex, discount.getTarget());
		Assert.assertEquals(regex, discount.getTitle());
	}

	@Test
	public void testGetDiscountsPage() throws Exception {
		Page<Discount> page = discountResource.getDiscountsPage(
			RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Discount discount1 = testGetDiscountsPage_addDiscount(randomDiscount());

		Discount discount2 = testGetDiscountsPage_addDiscount(randomDiscount());

		page = discountResource.getDiscountsPage(
			null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discount1, discount2),
			(List<Discount>)page.getItems());
		assertValid(page);

		discountResource.deleteDiscount(discount1.getId());

		discountResource.deleteDiscount(discount2.getId());
	}

	@Test
	public void testGetDiscountsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Discount discount1 = randomDiscount();

		discount1 = testGetDiscountsPage_addDiscount(discount1);

		for (EntityField entityField : entityFields) {
			Page<Discount> page = discountResource.getDiscountsPage(
				null, getFilterString(entityField, "between", discount1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discount1),
				(List<Discount>)page.getItems());
		}
	}

	@Test
	public void testGetDiscountsPageWithFilterStringEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Discount discount1 = testGetDiscountsPage_addDiscount(randomDiscount());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Discount discount2 = testGetDiscountsPage_addDiscount(randomDiscount());

		for (EntityField entityField : entityFields) {
			Page<Discount> page = discountResource.getDiscountsPage(
				null, getFilterString(entityField, "eq", discount1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discount1),
				(List<Discount>)page.getItems());
		}
	}

	@Test
	public void testGetDiscountsPageWithPagination() throws Exception {
		Discount discount1 = testGetDiscountsPage_addDiscount(randomDiscount());

		Discount discount2 = testGetDiscountsPage_addDiscount(randomDiscount());

		Discount discount3 = testGetDiscountsPage_addDiscount(randomDiscount());

		Page<Discount> page1 = discountResource.getDiscountsPage(
			null, null, Pagination.of(1, 2), null);

		List<Discount> discounts1 = (List<Discount>)page1.getItems();

		Assert.assertEquals(discounts1.toString(), 2, discounts1.size());

		Page<Discount> page2 = discountResource.getDiscountsPage(
			null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<Discount> discounts2 = (List<Discount>)page2.getItems();

		Assert.assertEquals(discounts2.toString(), 1, discounts2.size());

		Page<Discount> page3 = discountResource.getDiscountsPage(
			null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(discount1, discount2, discount3),
			(List<Discount>)page3.getItems());
	}

	@Test
	public void testGetDiscountsPageWithSortDateTime() throws Exception {
		testGetDiscountsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, discount1, discount2) -> {
				BeanUtils.setProperty(
					discount1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDiscountsPageWithSortInteger() throws Exception {
		testGetDiscountsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, discount1, discount2) -> {
				BeanUtils.setProperty(discount1, entityField.getName(), 0);
				BeanUtils.setProperty(discount2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDiscountsPageWithSortString() throws Exception {
		testGetDiscountsPageWithSort(
			EntityField.Type.STRING,
			(entityField, discount1, discount2) -> {
				Class<?> clazz = discount1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						discount1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						discount2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						discount1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						discount2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						discount1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						discount2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetDiscountsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, Discount, Discount, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Discount discount1 = randomDiscount();
		Discount discount2 = randomDiscount();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, discount1, discount2);
		}

		discount1 = testGetDiscountsPage_addDiscount(discount1);

		discount2 = testGetDiscountsPage_addDiscount(discount2);

		for (EntityField entityField : entityFields) {
			Page<Discount> ascPage = discountResource.getDiscountsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discount1, discount2),
				(List<Discount>)ascPage.getItems());

			Page<Discount> descPage = discountResource.getDiscountsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discount2, discount1),
				(List<Discount>)descPage.getItems());
		}
	}

	protected Discount testGetDiscountsPage_addDiscount(Discount discount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetDiscountsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"discounts",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject discountsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/discounts");

		Assert.assertEquals(0, discountsJSONObject.get("totalCount"));

		Discount discount1 = testGraphQLDiscount_addDiscount();
		Discount discount2 = testGraphQLDiscount_addDiscount();

		discountsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/discounts");

		Assert.assertEquals(2, discountsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(discount1, discount2),
			Arrays.asList(
				DiscountSerDes.toDTOs(discountsJSONObject.getString("items"))));
	}

	@Test
	public void testPostDiscount() throws Exception {
		Discount randomDiscount = randomDiscount();

		Discount postDiscount = testPostDiscount_addDiscount(randomDiscount);

		assertEquals(randomDiscount, postDiscount);
		assertValid(postDiscount);

		randomDiscount = randomDiscount();

		assertHttpResponseStatusCode(
			404,
			discountResource.getDiscountByExternalReferenceCodeHttpResponse(
				randomDiscount.getExternalReferenceCode()));

		testPostDiscount_addDiscount(randomDiscount);

		assertHttpResponseStatusCode(
			200,
			discountResource.getDiscountByExternalReferenceCodeHttpResponse(
				randomDiscount.getExternalReferenceCode()));
	}

	protected Discount testPostDiscount_addDiscount(Discount discount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteDiscountByExternalReferenceCode() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Discount discount =
			testDeleteDiscountByExternalReferenceCode_addDiscount();

		assertHttpResponseStatusCode(
			204,
			discountResource.deleteDiscountByExternalReferenceCodeHttpResponse(
				discount.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			discountResource.getDiscountByExternalReferenceCodeHttpResponse(
				discount.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			discountResource.getDiscountByExternalReferenceCodeHttpResponse(
				discount.getExternalReferenceCode()));
	}

	protected Discount testDeleteDiscountByExternalReferenceCode_addDiscount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDiscountByExternalReferenceCode() throws Exception {
		Discount postDiscount =
			testGetDiscountByExternalReferenceCode_addDiscount();

		Discount getDiscount =
			discountResource.getDiscountByExternalReferenceCode(
				postDiscount.getExternalReferenceCode());

		assertEquals(postDiscount, getDiscount);
		assertValid(getDiscount);
	}

	protected Discount testGetDiscountByExternalReferenceCode_addDiscount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetDiscountByExternalReferenceCode()
		throws Exception {

		Discount discount = testGraphQLDiscount_addDiscount();

		Assert.assertTrue(
			equals(
				discount,
				DiscountSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"discountByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												discount.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/discountByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetDiscountByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"discountByExternalReferenceCode",
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
	public void testPatchDiscountByExternalReferenceCode() throws Exception {
		Discount postDiscount =
			testPatchDiscountByExternalReferenceCode_addDiscount();

		Discount randomPatchDiscount = randomPatchDiscount();

		Discount patchDiscount =
			discountResource.patchDiscountByExternalReferenceCode(
				postDiscount.getExternalReferenceCode(), randomPatchDiscount);

		Discount expectedPatchDiscount = postDiscount.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchDiscount, randomPatchDiscount);

		Discount getDiscount =
			discountResource.getDiscountByExternalReferenceCode(
				patchDiscount.getExternalReferenceCode());

		assertEquals(expectedPatchDiscount, getDiscount);
		assertValid(getDiscount);
	}

	protected Discount testPatchDiscountByExternalReferenceCode_addDiscount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteDiscount() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Discount discount = testDeleteDiscount_addDiscount();

		assertHttpResponseStatusCode(
			204, discountResource.deleteDiscountHttpResponse(discount.getId()));

		assertHttpResponseStatusCode(
			404, discountResource.getDiscountHttpResponse(discount.getId()));

		assertHttpResponseStatusCode(
			404, discountResource.getDiscountHttpResponse(discount.getId()));
	}

	protected Discount testDeleteDiscount_addDiscount() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteDiscount() throws Exception {
		Discount discount = testGraphQLDiscount_addDiscount();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteDiscount",
						new HashMap<String, Object>() {
							{
								put("id", discount.getId());
							}
						})),
				"JSONObject/data", "Object/deleteDiscount"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"discount",
						new HashMap<String, Object>() {
							{
								put("id", discount.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetDiscount() throws Exception {
		Discount postDiscount = testGetDiscount_addDiscount();

		Discount getDiscount = discountResource.getDiscount(
			postDiscount.getId());

		assertEquals(postDiscount, getDiscount);
		assertValid(getDiscount);
	}

	protected Discount testGetDiscount_addDiscount() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetDiscount() throws Exception {
		Discount discount = testGraphQLDiscount_addDiscount();

		Assert.assertTrue(
			equals(
				discount,
				DiscountSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"discount",
								new HashMap<String, Object>() {
									{
										put("id", discount.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/discount"))));
	}

	@Test
	public void testGraphQLGetDiscountNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"discount",
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
	public void testPatchDiscount() throws Exception {
		Discount postDiscount = testPatchDiscount_addDiscount();

		Discount randomPatchDiscount = randomPatchDiscount();

		Discount patchDiscount = discountResource.patchDiscount(
			postDiscount.getId(), randomPatchDiscount);

		Discount expectedPatchDiscount = postDiscount.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchDiscount, randomPatchDiscount);

		Discount getDiscount = discountResource.getDiscount(
			patchDiscount.getId());

		assertEquals(expectedPatchDiscount, getDiscount);
		assertValid(getDiscount);
	}

	protected Discount testPatchDiscount_addDiscount() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected Discount testGraphQLDiscount_addDiscount() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Discount discount1, Discount discount2) {
		Assert.assertTrue(
			discount1 + " does not equal " + discount2,
			equals(discount1, discount2));
	}

	protected void assertEquals(
		List<Discount> discounts1, List<Discount> discounts2) {

		Assert.assertEquals(discounts1.size(), discounts2.size());

		for (int i = 0; i < discounts1.size(); i++) {
			Discount discount1 = discounts1.get(i);
			Discount discount2 = discounts2.get(i);

			assertEquals(discount1, discount2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Discount> discounts1, List<Discount> discounts2) {

		Assert.assertEquals(discounts1.size(), discounts2.size());

		for (Discount discount1 : discounts1) {
			boolean contains = false;

			for (Discount discount2 : discounts2) {
				if (equals(discount1, discount2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				discounts2 + " does not contain " + discount1, contains);
		}
	}

	protected void assertValid(Discount discount) throws Exception {
		boolean valid = true;

		if (discount.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (discount.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (discount.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("amountFormatted", additionalAssertFieldName)) {
				if (discount.getAmountFormatted() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("couponCode", additionalAssertFieldName)) {
				if (discount.getCouponCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (discount.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountAccountGroups", additionalAssertFieldName)) {

				if (discount.getDiscountAccountGroups() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountAccounts", additionalAssertFieldName)) {
				if (discount.getDiscountAccounts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountCategories", additionalAssertFieldName)) {

				if (discount.getDiscountCategories() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountChannels", additionalAssertFieldName)) {
				if (discount.getDiscountChannels() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountProductGroups", additionalAssertFieldName)) {

				if (discount.getDiscountProductGroups() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountProducts", additionalAssertFieldName)) {
				if (discount.getDiscountProducts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountRules", additionalAssertFieldName)) {
				if (discount.getDiscountRules() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("displayDate", additionalAssertFieldName)) {
				if (discount.getDisplayDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("expirationDate", additionalAssertFieldName)) {
				if (discount.getExpirationDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (discount.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("level", additionalAssertFieldName)) {
				if (discount.getLevel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("limitationTimes", additionalAssertFieldName)) {
				if (discount.getLimitationTimes() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"limitationTimesPerAccount", additionalAssertFieldName)) {

				if (discount.getLimitationTimesPerAccount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("limitationType", additionalAssertFieldName)) {
				if (discount.getLimitationType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"maximumDiscountAmount", additionalAssertFieldName)) {

				if (discount.getMaximumDiscountAmount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("neverExpire", additionalAssertFieldName)) {
				if (discount.getNeverExpire() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("numberOfUse", additionalAssertFieldName)) {
				if (discount.getNumberOfUse() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel1", additionalAssertFieldName)) {
				if (discount.getPercentageLevel1() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel2", additionalAssertFieldName)) {
				if (discount.getPercentageLevel2() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel3", additionalAssertFieldName)) {
				if (discount.getPercentageLevel3() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel4", additionalAssertFieldName)) {
				if (discount.getPercentageLevel4() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("rulesConjunction", additionalAssertFieldName)) {
				if (discount.getRulesConjunction() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("target", additionalAssertFieldName)) {
				if (discount.getTarget() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (discount.getTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("useCouponCode", additionalAssertFieldName)) {
				if (discount.getUseCouponCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("usePercentage", additionalAssertFieldName)) {
				if (discount.getUsePercentage() == null) {
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

	protected void assertValid(Page<Discount> page) {
		boolean valid = false;

		java.util.Collection<Discount> discounts = page.getItems();

		int size = discounts.size();

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

		for (Field field :
				ReflectionUtil.getDeclaredFields(
					com.liferay.headless.commerce.admin.pricing.dto.v2_0.
						Discount.class)) {

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

	protected boolean equals(Discount discount1, Discount discount2) {
		if (discount1 == discount2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)discount1.getActions(),
						(Map)discount2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getActive(), discount2.getActive())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("amountFormatted", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getAmountFormatted(),
						discount2.getAmountFormatted())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("couponCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getCouponCode(), discount2.getCouponCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!equals(
						(Map)discount1.getCustomFields(),
						(Map)discount2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountAccountGroups", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discount1.getDiscountAccountGroups(),
						discount2.getDiscountAccountGroups())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountAccounts", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getDiscountAccounts(),
						discount2.getDiscountAccounts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountCategories", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discount1.getDiscountCategories(),
						discount2.getDiscountCategories())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountChannels", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getDiscountChannels(),
						discount2.getDiscountChannels())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountProductGroups", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discount1.getDiscountProductGroups(),
						discount2.getDiscountProductGroups())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountProducts", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getDiscountProducts(),
						discount2.getDiscountProducts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountRules", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getDiscountRules(),
						discount2.getDiscountRules())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("displayDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getDisplayDate(),
						discount2.getDisplayDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("expirationDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getExpirationDate(),
						discount2.getExpirationDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discount1.getExternalReferenceCode(),
						discount2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(discount1.getId(), discount2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("level", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getLevel(), discount2.getLevel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("limitationTimes", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getLimitationTimes(),
						discount2.getLimitationTimes())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"limitationTimesPerAccount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discount1.getLimitationTimesPerAccount(),
						discount2.getLimitationTimesPerAccount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("limitationType", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getLimitationType(),
						discount2.getLimitationType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"maximumDiscountAmount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discount1.getMaximumDiscountAmount(),
						discount2.getMaximumDiscountAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("neverExpire", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getNeverExpire(),
						discount2.getNeverExpire())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("numberOfUse", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getNumberOfUse(),
						discount2.getNumberOfUse())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel1", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getPercentageLevel1(),
						discount2.getPercentageLevel1())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel2", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getPercentageLevel2(),
						discount2.getPercentageLevel2())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel3", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getPercentageLevel3(),
						discount2.getPercentageLevel3())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("percentageLevel4", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getPercentageLevel4(),
						discount2.getPercentageLevel4())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("rulesConjunction", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getRulesConjunction(),
						discount2.getRulesConjunction())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("target", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getTarget(), discount2.getTarget())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("title", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getTitle(), discount2.getTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("useCouponCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getUseCouponCode(),
						discount2.getUseCouponCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("usePercentage", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discount1.getUsePercentage(),
						discount2.getUsePercentage())) {

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

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_discountResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_discountResource;

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
		EntityField entityField, String operator, Discount discount) {

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

		if (entityFieldName.equals("amountFormatted")) {
			sb.append("'");
			sb.append(String.valueOf(discount.getAmountFormatted()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("couponCode")) {
			sb.append("'");
			sb.append(String.valueOf(discount.getCouponCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("customFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountAccountGroups")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountAccounts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountCategories")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountChannels")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountProductGroups")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountProducts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountRules")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("displayDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(discount.getDisplayDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(discount.getDisplayDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(discount.getDisplayDate()));
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
							discount.getExpirationDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(discount.getExpirationDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(discount.getExpirationDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(discount.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("level")) {
			sb.append("'");
			sb.append(String.valueOf(discount.getLevel()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("limitationTimes")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("limitationTimesPerAccount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("limitationType")) {
			sb.append("'");
			sb.append(String.valueOf(discount.getLimitationType()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("maximumDiscountAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("neverExpire")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("numberOfUse")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("percentageLevel1")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("percentageLevel2")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("percentageLevel3")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("percentageLevel4")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("rulesConjunction")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("target")) {
			sb.append("'");
			sb.append(String.valueOf(discount.getTarget()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("title")) {
			sb.append("'");
			sb.append(String.valueOf(discount.getTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("useCouponCode")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("usePercentage")) {
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

	protected Discount randomDiscount() throws Exception {
		return new Discount() {
			{
				active = RandomTestUtil.randomBoolean();
				amountFormatted = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				couponCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				displayDate = RandomTestUtil.nextDate();
				expirationDate = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				level = StringUtil.toLowerCase(RandomTestUtil.randomString());
				limitationTimes = RandomTestUtil.randomInt();
				limitationTimesPerAccount = RandomTestUtil.randomInt();
				limitationType = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				neverExpire = RandomTestUtil.randomBoolean();
				numberOfUse = RandomTestUtil.randomInt();
				rulesConjunction = RandomTestUtil.randomBoolean();
				target = StringUtil.toLowerCase(RandomTestUtil.randomString());
				title = StringUtil.toLowerCase(RandomTestUtil.randomString());
				useCouponCode = RandomTestUtil.randomBoolean();
				usePercentage = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected Discount randomIrrelevantDiscount() throws Exception {
		Discount randomIrrelevantDiscount = randomDiscount();

		return randomIrrelevantDiscount;
	}

	protected Discount randomPatchDiscount() throws Exception {
		return randomDiscount();
	}

	protected DiscountResource discountResource;
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
		BaseDiscountResourceTestCase.class);

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
		com.liferay.headless.commerce.admin.pricing.resource.v2_0.
			DiscountResource _discountResource;

}