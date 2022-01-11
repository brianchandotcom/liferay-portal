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

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderItem;
import com.liferay.headless.commerce.admin.order.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.headless.commerce.admin.order.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderItemResource;
import com.liferay.headless.commerce.admin.order.client.serdes.v1_0.OrderItemSerDes;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

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
public abstract class BaseOrderItemResourceTestCase {

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

		_orderItemResource.setContextCompany(testCompany);

		OrderItemResource.Builder builder = OrderItemResource.builder();

		orderItemResource = builder.authentication(
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

		OrderItem orderItem1 = randomOrderItem();

		String json = objectMapper.writeValueAsString(orderItem1);

		OrderItem orderItem2 = OrderItemSerDes.toDTO(json);

		Assert.assertTrue(equals(orderItem1, orderItem2));
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

		OrderItem orderItem = randomOrderItem();

		String json1 = objectMapper.writeValueAsString(orderItem);
		String json2 = OrderItemSerDes.toJSON(orderItem);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		OrderItem orderItem = randomOrderItem();

		orderItem.setDeliveryGroup(regex);
		orderItem.setExternalReferenceCode(regex);
		orderItem.setFormattedQuantity(regex);
		orderItem.setOptions(regex);
		orderItem.setOrderExternalReferenceCode(regex);
		orderItem.setPrintedNote(regex);
		orderItem.setSku(regex);
		orderItem.setSkuExternalReferenceCode(regex);
		orderItem.setUnitOfMeasure(regex);

		String json = OrderItemSerDes.toJSON(orderItem);

		Assert.assertFalse(json.contains(regex));

		orderItem = OrderItemSerDes.toDTO(json);

		Assert.assertEquals(regex, orderItem.getDeliveryGroup());
		Assert.assertEquals(regex, orderItem.getExternalReferenceCode());
		Assert.assertEquals(regex, orderItem.getFormattedQuantity());
		Assert.assertEquals(regex, orderItem.getOptions());
		Assert.assertEquals(regex, orderItem.getOrderExternalReferenceCode());
		Assert.assertEquals(regex, orderItem.getPrintedNote());
		Assert.assertEquals(regex, orderItem.getSku());
		Assert.assertEquals(regex, orderItem.getSkuExternalReferenceCode());
		Assert.assertEquals(regex, orderItem.getUnitOfMeasure());
	}

	@Test
	public void testDeleteOrderItemByExternalReferenceCode() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		OrderItem orderItem =
			testDeleteOrderItemByExternalReferenceCode_addOrderItem();

		assertHttpResponseStatusCode(
			204,
			orderItemResource.
				deleteOrderItemByExternalReferenceCodeHttpResponse(
					orderItem.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			orderItemResource.getOrderItemByExternalReferenceCodeHttpResponse(
				orderItem.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			orderItemResource.getOrderItemByExternalReferenceCodeHttpResponse(
				orderItem.getExternalReferenceCode()));
	}

	protected OrderItem
			testDeleteOrderItemByExternalReferenceCode_addOrderItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetOrderItemByExternalReferenceCode() throws Exception {
		OrderItem postOrderItem =
			testGetOrderItemByExternalReferenceCode_addOrderItem();

		OrderItem getOrderItem =
			orderItemResource.getOrderItemByExternalReferenceCode(
				postOrderItem.getExternalReferenceCode());

		assertEquals(postOrderItem, getOrderItem);
		assertValid(getOrderItem);
	}

	protected OrderItem testGetOrderItemByExternalReferenceCode_addOrderItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetOrderItemByExternalReferenceCode()
		throws Exception {

		OrderItem orderItem = testGraphQLOrderItem_addOrderItem();

		Assert.assertTrue(
			equals(
				orderItem,
				OrderItemSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"orderItemByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												orderItem.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/orderItemByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetOrderItemByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"orderItemByExternalReferenceCode",
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
	public void testPatchOrderItemByExternalReferenceCode() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteOrderItem() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		OrderItem orderItem = testDeleteOrderItem_addOrderItem();

		assertHttpResponseStatusCode(
			204,
			orderItemResource.deleteOrderItemHttpResponse(orderItem.getId()));

		assertHttpResponseStatusCode(
			404, orderItemResource.getOrderItemHttpResponse(orderItem.getId()));

		assertHttpResponseStatusCode(
			404, orderItemResource.getOrderItemHttpResponse(orderItem.getId()));
	}

	protected OrderItem testDeleteOrderItem_addOrderItem() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteOrderItem() throws Exception {
		OrderItem orderItem = testGraphQLOrderItem_addOrderItem();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteOrderItem",
						new HashMap<String, Object>() {
							{
								put("id", orderItem.getId());
							}
						})),
				"JSONObject/data", "Object/deleteOrderItem"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"orderItem",
					new HashMap<String, Object>() {
						{
							put("id", orderItem.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetOrderItem() throws Exception {
		OrderItem postOrderItem = testGetOrderItem_addOrderItem();

		OrderItem getOrderItem = orderItemResource.getOrderItem(
			postOrderItem.getId());

		assertEquals(postOrderItem, getOrderItem);
		assertValid(getOrderItem);
	}

	protected OrderItem testGetOrderItem_addOrderItem() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetOrderItem() throws Exception {
		OrderItem orderItem = testGraphQLOrderItem_addOrderItem();

		Assert.assertTrue(
			equals(
				orderItem,
				OrderItemSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"orderItem",
								new HashMap<String, Object>() {
									{
										put("id", orderItem.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/orderItem"))));
	}

	@Test
	public void testGraphQLGetOrderItemNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"orderItem",
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
	public void testPatchOrderItem() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetOrderByExternalReferenceCodeOrderItemsPage()
		throws Exception {

		String externalReferenceCode =
			testGetOrderByExternalReferenceCodeOrderItemsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetOrderByExternalReferenceCodeOrderItemsPage_getIrrelevantExternalReferenceCode();

		Page<OrderItem> page =
			orderItemResource.getOrderByExternalReferenceCodeOrderItemsPage(
				externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantExternalReferenceCode != null) {
			OrderItem irrelevantOrderItem =
				testGetOrderByExternalReferenceCodeOrderItemsPage_addOrderItem(
					irrelevantExternalReferenceCode,
					randomIrrelevantOrderItem());

			page =
				orderItemResource.getOrderByExternalReferenceCodeOrderItemsPage(
					irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantOrderItem),
				(List<OrderItem>)page.getItems());
			assertValid(page);
		}

		OrderItem orderItem1 =
			testGetOrderByExternalReferenceCodeOrderItemsPage_addOrderItem(
				externalReferenceCode, randomOrderItem());

		OrderItem orderItem2 =
			testGetOrderByExternalReferenceCodeOrderItemsPage_addOrderItem(
				externalReferenceCode, randomOrderItem());

		page = orderItemResource.getOrderByExternalReferenceCodeOrderItemsPage(
			externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(orderItem1, orderItem2),
			(List<OrderItem>)page.getItems());
		assertValid(page);

		orderItemResource.deleteOrderItem(orderItem1.getId());

		orderItemResource.deleteOrderItem(orderItem2.getId());
	}

	@Test
	public void testGetOrderByExternalReferenceCodeOrderItemsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetOrderByExternalReferenceCodeOrderItemsPage_getExternalReferenceCode();

		OrderItem orderItem1 =
			testGetOrderByExternalReferenceCodeOrderItemsPage_addOrderItem(
				externalReferenceCode, randomOrderItem());

		OrderItem orderItem2 =
			testGetOrderByExternalReferenceCodeOrderItemsPage_addOrderItem(
				externalReferenceCode, randomOrderItem());

		OrderItem orderItem3 =
			testGetOrderByExternalReferenceCodeOrderItemsPage_addOrderItem(
				externalReferenceCode, randomOrderItem());

		Page<OrderItem> page1 =
			orderItemResource.getOrderByExternalReferenceCodeOrderItemsPage(
				externalReferenceCode, Pagination.of(1, 2));

		List<OrderItem> orderItems1 = (List<OrderItem>)page1.getItems();

		Assert.assertEquals(orderItems1.toString(), 2, orderItems1.size());

		Page<OrderItem> page2 =
			orderItemResource.getOrderByExternalReferenceCodeOrderItemsPage(
				externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<OrderItem> orderItems2 = (List<OrderItem>)page2.getItems();

		Assert.assertEquals(orderItems2.toString(), 1, orderItems2.size());

		Page<OrderItem> page3 =
			orderItemResource.getOrderByExternalReferenceCodeOrderItemsPage(
				externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(orderItem1, orderItem2, orderItem3),
			(List<OrderItem>)page3.getItems());
	}

	protected OrderItem
			testGetOrderByExternalReferenceCodeOrderItemsPage_addOrderItem(
				String externalReferenceCode, OrderItem orderItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrderByExternalReferenceCodeOrderItemsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrderByExternalReferenceCodeOrderItemsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostOrderByExternalReferenceCodeOrderItem()
		throws Exception {

		OrderItem randomOrderItem = randomOrderItem();

		OrderItem postOrderItem =
			testPostOrderByExternalReferenceCodeOrderItem_addOrderItem(
				randomOrderItem);

		assertEquals(randomOrderItem, postOrderItem);
		assertValid(postOrderItem);
	}

	protected OrderItem
			testPostOrderByExternalReferenceCodeOrderItem_addOrderItem(
				OrderItem orderItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetOrderIdOrderItemsPage() throws Exception {
		Long id = testGetOrderIdOrderItemsPage_getId();
		Long irrelevantId = testGetOrderIdOrderItemsPage_getIrrelevantId();

		Page<OrderItem> page = orderItemResource.getOrderIdOrderItemsPage(
			id, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantId != null) {
			OrderItem irrelevantOrderItem =
				testGetOrderIdOrderItemsPage_addOrderItem(
					irrelevantId, randomIrrelevantOrderItem());

			page = orderItemResource.getOrderIdOrderItemsPage(
				irrelevantId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantOrderItem),
				(List<OrderItem>)page.getItems());
			assertValid(page);
		}

		OrderItem orderItem1 = testGetOrderIdOrderItemsPage_addOrderItem(
			id, randomOrderItem());

		OrderItem orderItem2 = testGetOrderIdOrderItemsPage_addOrderItem(
			id, randomOrderItem());

		page = orderItemResource.getOrderIdOrderItemsPage(
			id, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(orderItem1, orderItem2),
			(List<OrderItem>)page.getItems());
		assertValid(page);

		orderItemResource.deleteOrderItem(orderItem1.getId());

		orderItemResource.deleteOrderItem(orderItem2.getId());
	}

	@Test
	public void testGetOrderIdOrderItemsPageWithPagination() throws Exception {
		Long id = testGetOrderIdOrderItemsPage_getId();

		OrderItem orderItem1 = testGetOrderIdOrderItemsPage_addOrderItem(
			id, randomOrderItem());

		OrderItem orderItem2 = testGetOrderIdOrderItemsPage_addOrderItem(
			id, randomOrderItem());

		OrderItem orderItem3 = testGetOrderIdOrderItemsPage_addOrderItem(
			id, randomOrderItem());

		Page<OrderItem> page1 = orderItemResource.getOrderIdOrderItemsPage(
			id, Pagination.of(1, 2));

		List<OrderItem> orderItems1 = (List<OrderItem>)page1.getItems();

		Assert.assertEquals(orderItems1.toString(), 2, orderItems1.size());

		Page<OrderItem> page2 = orderItemResource.getOrderIdOrderItemsPage(
			id, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<OrderItem> orderItems2 = (List<OrderItem>)page2.getItems();

		Assert.assertEquals(orderItems2.toString(), 1, orderItems2.size());

		Page<OrderItem> page3 = orderItemResource.getOrderIdOrderItemsPage(
			id, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(orderItem1, orderItem2, orderItem3),
			(List<OrderItem>)page3.getItems());
	}

	protected OrderItem testGetOrderIdOrderItemsPage_addOrderItem(
			Long id, OrderItem orderItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrderIdOrderItemsPage_getId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrderIdOrderItemsPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostOrderIdOrderItem() throws Exception {
		OrderItem randomOrderItem = randomOrderItem();

		OrderItem postOrderItem = testPostOrderIdOrderItem_addOrderItem(
			randomOrderItem);

		assertEquals(randomOrderItem, postOrderItem);
		assertValid(postOrderItem);
	}

	protected OrderItem testPostOrderIdOrderItem_addOrderItem(
			OrderItem orderItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected OrderItem testGraphQLOrderItem_addOrderItem() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		OrderItem orderItem, List<OrderItem> orderItems) {

		boolean contains = false;

		for (OrderItem item : orderItems) {
			if (equals(orderItem, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			orderItems + " does not contain " + orderItem, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(OrderItem orderItem1, OrderItem orderItem2) {
		Assert.assertTrue(
			orderItem1 + " does not equal " + orderItem2,
			equals(orderItem1, orderItem2));
	}

	protected void assertEquals(
		List<OrderItem> orderItems1, List<OrderItem> orderItems2) {

		Assert.assertEquals(orderItems1.size(), orderItems2.size());

		for (int i = 0; i < orderItems1.size(); i++) {
			OrderItem orderItem1 = orderItems1.get(i);
			OrderItem orderItem2 = orderItems2.get(i);

			assertEquals(orderItem1, orderItem2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<OrderItem> orderItems1, List<OrderItem> orderItems2) {

		Assert.assertEquals(orderItems1.size(), orderItems2.size());

		for (OrderItem orderItem1 : orderItems1) {
			boolean contains = false;

			for (OrderItem orderItem2 : orderItems2) {
				if (equals(orderItem1, orderItem2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				orderItems2 + " does not contain " + orderItem1, contains);
		}
	}

	protected void assertValid(OrderItem orderItem) throws Exception {
		boolean valid = true;

		if (orderItem.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("bookedQuantityId", additionalAssertFieldName)) {
				if (orderItem.getBookedQuantityId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (orderItem.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("decimalQuantity", additionalAssertFieldName)) {
				if (orderItem.getDecimalQuantity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("deliveryGroup", additionalAssertFieldName)) {
				if (orderItem.getDeliveryGroup() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountAmount", additionalAssertFieldName)) {
				if (orderItem.getDiscountAmount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel1", additionalAssertFieldName)) {

				if (orderItem.getDiscountPercentageLevel1() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel1WithTaxAmount",
					additionalAssertFieldName)) {

				if (orderItem.getDiscountPercentageLevel1WithTaxAmount() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel2", additionalAssertFieldName)) {

				if (orderItem.getDiscountPercentageLevel2() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel2WithTaxAmount",
					additionalAssertFieldName)) {

				if (orderItem.getDiscountPercentageLevel2WithTaxAmount() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel3", additionalAssertFieldName)) {

				if (orderItem.getDiscountPercentageLevel3() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel3WithTaxAmount",
					additionalAssertFieldName)) {

				if (orderItem.getDiscountPercentageLevel3WithTaxAmount() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel4", additionalAssertFieldName)) {

				if (orderItem.getDiscountPercentageLevel4() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel4WithTaxAmount",
					additionalAssertFieldName)) {

				if (orderItem.getDiscountPercentageLevel4WithTaxAmount() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountWithTaxAmount", additionalAssertFieldName)) {

				if (orderItem.getDiscountWithTaxAmount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (orderItem.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("finalPrice", additionalAssertFieldName)) {
				if (orderItem.getFinalPrice() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"finalPriceWithTaxAmount", additionalAssertFieldName)) {

				if (orderItem.getFinalPriceWithTaxAmount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"formattedQuantity", additionalAssertFieldName)) {

				if (orderItem.getFormattedQuantity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (orderItem.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("options", additionalAssertFieldName)) {
				if (orderItem.getOptions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"orderExternalReferenceCode", additionalAssertFieldName)) {

				if (orderItem.getOrderExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderId", additionalAssertFieldName)) {
				if (orderItem.getOrderId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("printedNote", additionalAssertFieldName)) {
				if (orderItem.getPrintedNote() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("promoPrice", additionalAssertFieldName)) {
				if (orderItem.getPromoPrice() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"promoPriceWithTaxAmount", additionalAssertFieldName)) {

				if (orderItem.getPromoPriceWithTaxAmount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("quantity", additionalAssertFieldName)) {
				if (orderItem.getQuantity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"requestedDeliveryDate", additionalAssertFieldName)) {

				if (orderItem.getRequestedDeliveryDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shippedQuantity", additionalAssertFieldName)) {
				if (orderItem.getShippedQuantity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shippingAddress", additionalAssertFieldName)) {
				if (orderItem.getShippingAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingAddressId", additionalAssertFieldName)) {

				if (orderItem.getShippingAddressId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (orderItem.getSku() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"skuExternalReferenceCode", additionalAssertFieldName)) {

				if (orderItem.getSkuExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("skuId", additionalAssertFieldName)) {
				if (orderItem.getSkuId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("subscription", additionalAssertFieldName)) {
				if (orderItem.getSubscription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("unitOfMeasure", additionalAssertFieldName)) {
				if (orderItem.getUnitOfMeasure() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("unitPrice", additionalAssertFieldName)) {
				if (orderItem.getUnitPrice() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"unitPriceWithTaxAmount", additionalAssertFieldName)) {

				if (orderItem.getUnitPriceWithTaxAmount() == null) {
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

	protected void assertValid(Page<OrderItem> page) {
		boolean valid = false;

		java.util.Collection<OrderItem> orderItems = page.getItems();

		int size = orderItems.size();

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
						OrderItem.class)) {

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

	protected boolean equals(OrderItem orderItem1, OrderItem orderItem2) {
		if (orderItem1 == orderItem2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("bookedQuantityId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getBookedQuantityId(),
						orderItem2.getBookedQuantityId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!equals(
						(Map)orderItem1.getCustomFields(),
						(Map)orderItem2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("decimalQuantity", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getDecimalQuantity(),
						orderItem2.getDecimalQuantity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("deliveryGroup", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getDeliveryGroup(),
						orderItem2.getDeliveryGroup())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountAmount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getDiscountAmount(),
						orderItem2.getDiscountAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel1", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getDiscountPercentageLevel1(),
						orderItem2.getDiscountPercentageLevel1())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel1WithTaxAmount",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getDiscountPercentageLevel1WithTaxAmount(),
						orderItem2.
							getDiscountPercentageLevel1WithTaxAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel2", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getDiscountPercentageLevel2(),
						orderItem2.getDiscountPercentageLevel2())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel2WithTaxAmount",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getDiscountPercentageLevel2WithTaxAmount(),
						orderItem2.
							getDiscountPercentageLevel2WithTaxAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel3", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getDiscountPercentageLevel3(),
						orderItem2.getDiscountPercentageLevel3())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel3WithTaxAmount",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getDiscountPercentageLevel3WithTaxAmount(),
						orderItem2.
							getDiscountPercentageLevel3WithTaxAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel4", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getDiscountPercentageLevel4(),
						orderItem2.getDiscountPercentageLevel4())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountPercentageLevel4WithTaxAmount",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getDiscountPercentageLevel4WithTaxAmount(),
						orderItem2.
							getDiscountPercentageLevel4WithTaxAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountWithTaxAmount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getDiscountWithTaxAmount(),
						orderItem2.getDiscountWithTaxAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getExternalReferenceCode(),
						orderItem2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("finalPrice", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getFinalPrice(),
						orderItem2.getFinalPrice())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"finalPriceWithTaxAmount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getFinalPriceWithTaxAmount(),
						orderItem2.getFinalPriceWithTaxAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"formattedQuantity", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getFormattedQuantity(),
						orderItem2.getFormattedQuantity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getId(), orderItem2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!equals(
						(Map)orderItem1.getName(), (Map)orderItem2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("options", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getOptions(), orderItem2.getOptions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"orderExternalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getOrderExternalReferenceCode(),
						orderItem2.getOrderExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getOrderId(), orderItem2.getOrderId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("printedNote", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getPrintedNote(),
						orderItem2.getPrintedNote())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("promoPrice", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getPromoPrice(),
						orderItem2.getPromoPrice())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"promoPriceWithTaxAmount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getPromoPriceWithTaxAmount(),
						orderItem2.getPromoPriceWithTaxAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("quantity", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getQuantity(), orderItem2.getQuantity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"requestedDeliveryDate", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getRequestedDeliveryDate(),
						orderItem2.getRequestedDeliveryDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shippedQuantity", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getShippedQuantity(),
						orderItem2.getShippedQuantity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shippingAddress", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getShippingAddress(),
						orderItem2.getShippingAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingAddressId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getShippingAddressId(),
						orderItem2.getShippingAddressId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getSku(), orderItem2.getSku())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"skuExternalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getSkuExternalReferenceCode(),
						orderItem2.getSkuExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("skuId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getSkuId(), orderItem2.getSkuId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscription", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getSubscription(),
						orderItem2.getSubscription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("unitOfMeasure", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getUnitOfMeasure(),
						orderItem2.getUnitOfMeasure())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("unitPrice", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderItem1.getUnitPrice(), orderItem2.getUnitPrice())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"unitPriceWithTaxAmount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderItem1.getUnitPriceWithTaxAmount(),
						orderItem2.getUnitPriceWithTaxAmount())) {

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

		if (!(_orderItemResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_orderItemResource;

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
		EntityField entityField, String operator, OrderItem orderItem) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("bookedQuantityId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("customFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("decimalQuantity")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("deliveryGroup")) {
			sb.append("'");
			sb.append(String.valueOf(orderItem.getDeliveryGroup()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("discountAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountPercentageLevel1")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountPercentageLevel1WithTaxAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountPercentageLevel2")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountPercentageLevel2WithTaxAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountPercentageLevel3")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountPercentageLevel3WithTaxAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountPercentageLevel4")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountPercentageLevel4WithTaxAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("discountWithTaxAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(orderItem.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("finalPrice")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("finalPriceWithTaxAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("formattedQuantity")) {
			sb.append("'");
			sb.append(String.valueOf(orderItem.getFormattedQuantity()));
			sb.append("'");

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

		if (entityFieldName.equals("options")) {
			sb.append("'");
			sb.append(String.valueOf(orderItem.getOptions()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("orderExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(orderItem.getOrderExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("orderId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("printedNote")) {
			sb.append("'");
			sb.append(String.valueOf(orderItem.getPrintedNote()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("promoPrice")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("promoPriceWithTaxAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("quantity")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("requestedDeliveryDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							orderItem.getRequestedDeliveryDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							orderItem.getRequestedDeliveryDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(orderItem.getRequestedDeliveryDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("shippedQuantity")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingAddress")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingAddressId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sku")) {
			sb.append("'");
			sb.append(String.valueOf(orderItem.getSku()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("skuExternalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(orderItem.getSkuExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("skuId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subscription")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("unitOfMeasure")) {
			sb.append("'");
			sb.append(String.valueOf(orderItem.getUnitOfMeasure()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("unitPrice")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("unitPriceWithTaxAmount")) {
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

	protected OrderItem randomOrderItem() throws Exception {
		return new OrderItem() {
			{
				bookedQuantityId = RandomTestUtil.randomLong();
				deliveryGroup = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				formattedQuantity = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				options = StringUtil.toLowerCase(RandomTestUtil.randomString());
				orderExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				orderId = RandomTestUtil.randomLong();
				printedNote = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				quantity = RandomTestUtil.randomInt();
				requestedDeliveryDate = RandomTestUtil.nextDate();
				shippedQuantity = RandomTestUtil.randomInt();
				shippingAddressId = RandomTestUtil.randomLong();
				sku = StringUtil.toLowerCase(RandomTestUtil.randomString());
				skuExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				skuId = RandomTestUtil.randomLong();
				subscription = RandomTestUtil.randomBoolean();
				unitOfMeasure = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected OrderItem randomIrrelevantOrderItem() throws Exception {
		OrderItem randomIrrelevantOrderItem = randomOrderItem();

		return randomIrrelevantOrderItem;
	}

	protected OrderItem randomPatchOrderItem() throws Exception {
		return randomOrderItem();
	}

	protected OrderItemResource orderItemResource;
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
		LogFactoryUtil.getLog(BaseOrderItemResourceTestCase.class);

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
			OrderItemResource _orderItemResource;

}