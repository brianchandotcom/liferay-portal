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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceList;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.PriceListResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.PriceListSerDes;
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
public abstract class BasePriceListResourceTestCase {

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

		_priceListResource.setContextCompany(testCompany);

		PriceListResource.Builder builder = PriceListResource.builder();

		priceListResource = builder.authentication(
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

		PriceList priceList1 = randomPriceList();

		String json = objectMapper.writeValueAsString(priceList1);

		PriceList priceList2 = PriceListSerDes.toDTO(json);

		Assert.assertTrue(equals(priceList1, priceList2));
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

		PriceList priceList = randomPriceList();

		String json1 = objectMapper.writeValueAsString(priceList);
		String json2 = PriceListSerDes.toJSON(priceList);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		PriceList priceList = randomPriceList();

		priceList.setAuthor(regex);
		priceList.setCatalogName(regex);
		priceList.setCurrencyCode(regex);
		priceList.setExternalReferenceCode(regex);
		priceList.setName(regex);

		String json = PriceListSerDes.toJSON(priceList);

		Assert.assertFalse(json.contains(regex));

		priceList = PriceListSerDes.toDTO(json);

		Assert.assertEquals(regex, priceList.getAuthor());
		Assert.assertEquals(regex, priceList.getCatalogName());
		Assert.assertEquals(regex, priceList.getCurrencyCode());
		Assert.assertEquals(regex, priceList.getExternalReferenceCode());
		Assert.assertEquals(regex, priceList.getName());
	}

	@Test
	public void testGetPriceListsPage() throws Exception {
		Page<PriceList> page = priceListResource.getPriceListsPage(
			RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		PriceList priceList1 = testGetPriceListsPage_addPriceList(
			randomPriceList());

		PriceList priceList2 = testGetPriceListsPage_addPriceList(
			randomPriceList());

		page = priceListResource.getPriceListsPage(
			null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceList1, priceList2),
			(List<PriceList>)page.getItems());
		assertValid(page);

		priceListResource.deletePriceList(priceList1.getId());

		priceListResource.deletePriceList(priceList2.getId());
	}

	@Test
	public void testGetPriceListsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		PriceList priceList1 = randomPriceList();

		priceList1 = testGetPriceListsPage_addPriceList(priceList1);

		for (EntityField entityField : entityFields) {
			Page<PriceList> page = priceListResource.getPriceListsPage(
				null, getFilterString(entityField, "between", priceList1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceList1),
				(List<PriceList>)page.getItems());
		}
	}

	@Test
	public void testGetPriceListsPageWithFilterStringEquals() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		PriceList priceList1 = testGetPriceListsPage_addPriceList(
			randomPriceList());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceList priceList2 = testGetPriceListsPage_addPriceList(
			randomPriceList());

		for (EntityField entityField : entityFields) {
			Page<PriceList> page = priceListResource.getPriceListsPage(
				null, getFilterString(entityField, "eq", priceList1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceList1),
				(List<PriceList>)page.getItems());
		}
	}

	@Test
	public void testGetPriceListsPageWithPagination() throws Exception {
		PriceList priceList1 = testGetPriceListsPage_addPriceList(
			randomPriceList());

		PriceList priceList2 = testGetPriceListsPage_addPriceList(
			randomPriceList());

		PriceList priceList3 = testGetPriceListsPage_addPriceList(
			randomPriceList());

		Page<PriceList> page1 = priceListResource.getPriceListsPage(
			null, null, Pagination.of(1, 2), null);

		List<PriceList> priceLists1 = (List<PriceList>)page1.getItems();

		Assert.assertEquals(priceLists1.toString(), 2, priceLists1.size());

		Page<PriceList> page2 = priceListResource.getPriceListsPage(
			null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceList> priceLists2 = (List<PriceList>)page2.getItems();

		Assert.assertEquals(priceLists2.toString(), 1, priceLists2.size());

		Page<PriceList> page3 = priceListResource.getPriceListsPage(
			null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(priceList1, priceList2, priceList3),
			(List<PriceList>)page3.getItems());
	}

	@Test
	public void testGetPriceListsPageWithSortDateTime() throws Exception {
		testGetPriceListsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, priceList1, priceList2) -> {
				BeanUtils.setProperty(
					priceList1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetPriceListsPageWithSortInteger() throws Exception {
		testGetPriceListsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, priceList1, priceList2) -> {
				BeanUtils.setProperty(priceList1, entityField.getName(), 0);
				BeanUtils.setProperty(priceList2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetPriceListsPageWithSortString() throws Exception {
		testGetPriceListsPageWithSort(
			EntityField.Type.STRING,
			(entityField, priceList1, priceList2) -> {
				Class<?> clazz = priceList1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						priceList1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						priceList2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						priceList1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						priceList2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						priceList1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						priceList2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetPriceListsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, PriceList, PriceList, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		PriceList priceList1 = randomPriceList();
		PriceList priceList2 = randomPriceList();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, priceList1, priceList2);
		}

		priceList1 = testGetPriceListsPage_addPriceList(priceList1);

		priceList2 = testGetPriceListsPage_addPriceList(priceList2);

		for (EntityField entityField : entityFields) {
			Page<PriceList> ascPage = priceListResource.getPriceListsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(priceList1, priceList2),
				(List<PriceList>)ascPage.getItems());

			Page<PriceList> descPage = priceListResource.getPriceListsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(priceList2, priceList1),
				(List<PriceList>)descPage.getItems());
		}
	}

	protected PriceList testGetPriceListsPage_addPriceList(PriceList priceList)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetPriceListsPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"priceLists",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject priceListsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/priceLists");

		Assert.assertEquals(0, priceListsJSONObject.get("totalCount"));

		PriceList priceList1 = testGraphQLPriceList_addPriceList();
		PriceList priceList2 = testGraphQLPriceList_addPriceList();

		priceListsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/priceLists");

		Assert.assertEquals(2, priceListsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(priceList1, priceList2),
			Arrays.asList(
				PriceListSerDes.toDTOs(
					priceListsJSONObject.getString("items"))));
	}

	@Test
	public void testPostPriceList() throws Exception {
		PriceList randomPriceList = randomPriceList();

		PriceList postPriceList = testPostPriceList_addPriceList(
			randomPriceList);

		assertEquals(randomPriceList, postPriceList);
		assertValid(postPriceList);

		randomPriceList = randomPriceList();

		assertHttpResponseStatusCode(
			404,
			priceListResource.getPriceListByExternalReferenceCodeHttpResponse(
				randomPriceList.getExternalReferenceCode()));

		testPostPriceList_addPriceList(randomPriceList);

		assertHttpResponseStatusCode(
			200,
			priceListResource.getPriceListByExternalReferenceCodeHttpResponse(
				randomPriceList.getExternalReferenceCode()));
	}

	protected PriceList testPostPriceList_addPriceList(PriceList priceList)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeletePriceListByExternalReferenceCode() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceList priceList =
			testDeletePriceListByExternalReferenceCode_addPriceList();

		assertHttpResponseStatusCode(
			204,
			priceListResource.
				deletePriceListByExternalReferenceCodeHttpResponse(
					priceList.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			priceListResource.getPriceListByExternalReferenceCodeHttpResponse(
				priceList.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			priceListResource.getPriceListByExternalReferenceCodeHttpResponse(
				priceList.getExternalReferenceCode()));
	}

	protected PriceList
			testDeletePriceListByExternalReferenceCode_addPriceList()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetPriceListByExternalReferenceCode() throws Exception {
		PriceList postPriceList =
			testGetPriceListByExternalReferenceCode_addPriceList();

		PriceList getPriceList =
			priceListResource.getPriceListByExternalReferenceCode(
				postPriceList.getExternalReferenceCode());

		assertEquals(postPriceList, getPriceList);
		assertValid(getPriceList);
	}

	protected PriceList testGetPriceListByExternalReferenceCode_addPriceList()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetPriceListByExternalReferenceCode()
		throws Exception {

		PriceList priceList = testGraphQLPriceList_addPriceList();

		Assert.assertTrue(
			equals(
				priceList,
				PriceListSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"priceListByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												priceList.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/priceListByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetPriceListByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"priceListByExternalReferenceCode",
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
	public void testPatchPriceListByExternalReferenceCode() throws Exception {
		PriceList postPriceList =
			testPatchPriceListByExternalReferenceCode_addPriceList();

		PriceList randomPatchPriceList = randomPatchPriceList();

		PriceList patchPriceList =
			priceListResource.patchPriceListByExternalReferenceCode(
				postPriceList.getExternalReferenceCode(), randomPatchPriceList);

		PriceList expectedPatchPriceList = postPriceList.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchPriceList, randomPatchPriceList);

		PriceList getPriceList =
			priceListResource.getPriceListByExternalReferenceCode(
				patchPriceList.getExternalReferenceCode());

		assertEquals(expectedPatchPriceList, getPriceList);
		assertValid(getPriceList);
	}

	protected PriceList testPatchPriceListByExternalReferenceCode_addPriceList()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeletePriceList() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceList priceList = testDeletePriceList_addPriceList();

		assertHttpResponseStatusCode(
			204,
			priceListResource.deletePriceListHttpResponse(priceList.getId()));

		assertHttpResponseStatusCode(
			404, priceListResource.getPriceListHttpResponse(priceList.getId()));

		assertHttpResponseStatusCode(
			404, priceListResource.getPriceListHttpResponse(priceList.getId()));
	}

	protected PriceList testDeletePriceList_addPriceList() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeletePriceList() throws Exception {
		PriceList priceList = testGraphQLPriceList_addPriceList();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deletePriceList",
						new HashMap<String, Object>() {
							{
								put("id", priceList.getId());
							}
						})),
				"JSONObject/data", "Object/deletePriceList"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"priceList",
						new HashMap<String, Object>() {
							{
								put("id", priceList.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetPriceList() throws Exception {
		PriceList postPriceList = testGetPriceList_addPriceList();

		PriceList getPriceList = priceListResource.getPriceList(
			postPriceList.getId());

		assertEquals(postPriceList, getPriceList);
		assertValid(getPriceList);
	}

	protected PriceList testGetPriceList_addPriceList() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetPriceList() throws Exception {
		PriceList priceList = testGraphQLPriceList_addPriceList();

		Assert.assertTrue(
			equals(
				priceList,
				PriceListSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"priceList",
								new HashMap<String, Object>() {
									{
										put("id", priceList.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/priceList"))));
	}

	@Test
	public void testGraphQLGetPriceListNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"priceList",
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
	public void testPatchPriceList() throws Exception {
		PriceList postPriceList = testPatchPriceList_addPriceList();

		PriceList randomPatchPriceList = randomPatchPriceList();

		PriceList patchPriceList = priceListResource.patchPriceList(
			postPriceList.getId(), randomPatchPriceList);

		PriceList expectedPatchPriceList = postPriceList.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchPriceList, randomPatchPriceList);

		PriceList getPriceList = priceListResource.getPriceList(
			patchPriceList.getId());

		assertEquals(expectedPatchPriceList, getPriceList);
		assertValid(getPriceList);
	}

	protected PriceList testPatchPriceList_addPriceList() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected PriceList testGraphQLPriceList_addPriceList() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(PriceList priceList1, PriceList priceList2) {
		Assert.assertTrue(
			priceList1 + " does not equal " + priceList2,
			equals(priceList1, priceList2));
	}

	protected void assertEquals(
		List<PriceList> priceLists1, List<PriceList> priceLists2) {

		Assert.assertEquals(priceLists1.size(), priceLists2.size());

		for (int i = 0; i < priceLists1.size(); i++) {
			PriceList priceList1 = priceLists1.get(i);
			PriceList priceList2 = priceLists2.get(i);

			assertEquals(priceList1, priceList2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<PriceList> priceLists1, List<PriceList> priceLists2) {

		Assert.assertEquals(priceLists1.size(), priceLists2.size());

		for (PriceList priceList1 : priceLists1) {
			boolean contains = false;

			for (PriceList priceList2 : priceLists2) {
				if (equals(priceList1, priceList2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				priceLists2 + " does not contain " + priceList1, contains);
		}
	}

	protected void assertValid(PriceList priceList) throws Exception {
		boolean valid = true;

		if (priceList.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (priceList.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (priceList.getActive() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("author", additionalAssertFieldName)) {
				if (priceList.getAuthor() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"catalogBasePriceList", additionalAssertFieldName)) {

				if (priceList.getCatalogBasePriceList() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("catalogId", additionalAssertFieldName)) {
				if (priceList.getCatalogId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("catalogName", additionalAssertFieldName)) {
				if (priceList.getCatalogName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (priceList.getCreateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("currencyCode", additionalAssertFieldName)) {
				if (priceList.getCurrencyCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (priceList.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("displayDate", additionalAssertFieldName)) {
				if (priceList.getDisplayDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("expirationDate", additionalAssertFieldName)) {
				if (priceList.getExpirationDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (priceList.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (priceList.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("netPrice", additionalAssertFieldName)) {
				if (priceList.getNetPrice() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("neverExpire", additionalAssertFieldName)) {
				if (priceList.getNeverExpire() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"parentPriceListId", additionalAssertFieldName)) {

				if (priceList.getParentPriceListId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priceEntries", additionalAssertFieldName)) {
				if (priceList.getPriceEntries() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListAccountGroups", additionalAssertFieldName)) {

				if (priceList.getPriceListAccountGroups() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListAccounts", additionalAssertFieldName)) {

				if (priceList.getPriceListAccounts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListChannels", additionalAssertFieldName)) {

				if (priceList.getPriceListChannels() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListDiscounts", additionalAssertFieldName)) {

				if (priceList.getPriceListDiscounts() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priceModifiers", additionalAssertFieldName)) {
				if (priceList.getPriceModifiers() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (priceList.getPriority() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (priceList.getType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"workflowStatusInfo", additionalAssertFieldName)) {

				if (priceList.getWorkflowStatusInfo() == null) {
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

	protected void assertValid(Page<PriceList> page) {
		boolean valid = false;

		java.util.Collection<PriceList> priceLists = page.getItems();

		int size = priceLists.size();

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
						PriceList.class)) {

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

	protected boolean equals(PriceList priceList1, PriceList priceList2) {
		if (priceList1 == priceList2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)priceList1.getActions(),
						(Map)priceList2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("active", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getActive(), priceList2.getActive())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("author", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getAuthor(), priceList2.getAuthor())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"catalogBasePriceList", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceList1.getCatalogBasePriceList(),
						priceList2.getCatalogBasePriceList())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("catalogId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getCatalogId(), priceList2.getCatalogId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("catalogName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getCatalogName(),
						priceList2.getCatalogName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getCreateDate(),
						priceList2.getCreateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("currencyCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getCurrencyCode(),
						priceList2.getCurrencyCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!equals(
						(Map)priceList1.getCustomFields(),
						(Map)priceList2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("displayDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getDisplayDate(),
						priceList2.getDisplayDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("expirationDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getExpirationDate(),
						priceList2.getExpirationDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceList1.getExternalReferenceCode(),
						priceList2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getId(), priceList2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getName(), priceList2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("netPrice", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getNetPrice(), priceList2.getNetPrice())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("neverExpire", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getNeverExpire(),
						priceList2.getNeverExpire())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"parentPriceListId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceList1.getParentPriceListId(),
						priceList2.getParentPriceListId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priceEntries", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getPriceEntries(),
						priceList2.getPriceEntries())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListAccountGroups", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceList1.getPriceListAccountGroups(),
						priceList2.getPriceListAccountGroups())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListAccounts", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceList1.getPriceListAccounts(),
						priceList2.getPriceListAccounts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListChannels", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceList1.getPriceListChannels(),
						priceList2.getPriceListChannels())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListDiscounts", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceList1.getPriceListDiscounts(),
						priceList2.getPriceListDiscounts())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priceModifiers", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getPriceModifiers(),
						priceList2.getPriceModifiers())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priority", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getPriority(), priceList2.getPriority())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceList1.getType(), priceList2.getType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"workflowStatusInfo", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceList1.getWorkflowStatusInfo(),
						priceList2.getWorkflowStatusInfo())) {

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

		if (!(_priceListResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_priceListResource;

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
		EntityField entityField, String operator, PriceList priceList) {

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
			sb.append(String.valueOf(priceList.getAuthor()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("catalogBasePriceList")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("catalogId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("catalogName")) {
			sb.append("'");
			sb.append(String.valueOf(priceList.getCatalogName()));
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
						DateUtils.addSeconds(priceList.getCreateDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(priceList.getCreateDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(priceList.getCreateDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("currencyCode")) {
			sb.append("'");
			sb.append(String.valueOf(priceList.getCurrencyCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("customFields")) {
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
						DateUtils.addSeconds(priceList.getDisplayDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(priceList.getDisplayDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(priceList.getDisplayDate()));
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
							priceList.getExpirationDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							priceList.getExpirationDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(priceList.getExpirationDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(priceList.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(priceList.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("netPrice")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("neverExpire")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("parentPriceListId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceEntries")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceListAccountGroups")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceListAccounts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceListChannels")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceListDiscounts")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceModifiers")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priority")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("type")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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

	protected PriceList randomPriceList() throws Exception {
		return new PriceList() {
			{
				active = RandomTestUtil.randomBoolean();
				author = StringUtil.toLowerCase(RandomTestUtil.randomString());
				catalogBasePriceList = RandomTestUtil.randomBoolean();
				catalogId = RandomTestUtil.randomLong();
				catalogName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				createDate = RandomTestUtil.nextDate();
				currencyCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				displayDate = RandomTestUtil.nextDate();
				expirationDate = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				netPrice = RandomTestUtil.randomBoolean();
				neverExpire = RandomTestUtil.randomBoolean();
				parentPriceListId = RandomTestUtil.randomLong();
				priority = RandomTestUtil.randomDouble();
			}
		};
	}

	protected PriceList randomIrrelevantPriceList() throws Exception {
		PriceList randomIrrelevantPriceList = randomPriceList();

		return randomIrrelevantPriceList;
	}

	protected PriceList randomPatchPriceList() throws Exception {
		return randomPriceList();
	}

	protected PriceListResource priceListResource;
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
		BasePriceListResourceTestCase.class);

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
			PriceListResource _priceListResource;

}