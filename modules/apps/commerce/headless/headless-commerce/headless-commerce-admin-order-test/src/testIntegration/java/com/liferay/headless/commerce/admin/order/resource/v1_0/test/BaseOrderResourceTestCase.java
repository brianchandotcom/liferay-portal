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

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.Order;
import com.liferay.headless.commerce.admin.order.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderResource;
import com.liferay.headless.commerce.admin.order.client.serdes.v1_0.OrderSerDes;
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
import org.apache.log4j.Level;

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
public abstract class BaseOrderResourceTestCase {

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

		_orderResource.setContextCompany(testCompany);

		OrderResource.Builder builder = OrderResource.builder();

		orderResource = builder.locale(
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

		Order order1 = randomOrder();

		String json = objectMapper.writeValueAsString(order1);

		Order order2 = OrderSerDes.toDTO(json);

		Assert.assertTrue(equals(order1, order2));
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

		Order order = randomOrder();

		String json1 = objectMapper.writeValueAsString(order);
		String json2 = OrderSerDes.toJSON(order);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Order order = randomOrder();

		order.setAccountExternalReferenceCode(regex);
		order.setAdvanceStatus(regex);
		order.setCouponCode(regex);
		order.setCurrencyCode(regex);
		order.setExternalReferenceCode(regex);
		order.setPaymentMethod(regex);
		order.setPrintedNote(regex);
		order.setPurchaseOrderNumber(regex);
		order.setShippingAmountFormatted(regex);
		order.setShippingDiscountAmountFormatted(regex);
		order.setShippingMethod(regex);
		order.setShippingOption(regex);
		order.setSubtotalDiscountAmountFormatted(regex);
		order.setSubtotalFormatted(regex);
		order.setTaxAmountFormatted(regex);
		order.setTotalDiscountAmountFormatted(regex);
		order.setTotalFormatted(regex);
		order.setTransactionId(regex);

		String json = OrderSerDes.toJSON(order);

		Assert.assertFalse(json.contains(regex));

		order = OrderSerDes.toDTO(json);

		Assert.assertEquals(regex, order.getAccountExternalReferenceCode());
		Assert.assertEquals(regex, order.getAdvanceStatus());
		Assert.assertEquals(regex, order.getCouponCode());
		Assert.assertEquals(regex, order.getCurrencyCode());
		Assert.assertEquals(regex, order.getExternalReferenceCode());
		Assert.assertEquals(regex, order.getPaymentMethod());
		Assert.assertEquals(regex, order.getPrintedNote());
		Assert.assertEquals(regex, order.getPurchaseOrderNumber());
		Assert.assertEquals(regex, order.getShippingAmountFormatted());
		Assert.assertEquals(regex, order.getShippingDiscountAmountFormatted());
		Assert.assertEquals(regex, order.getShippingMethod());
		Assert.assertEquals(regex, order.getShippingOption());
		Assert.assertEquals(regex, order.getSubtotalDiscountAmountFormatted());
		Assert.assertEquals(regex, order.getSubtotalFormatted());
		Assert.assertEquals(regex, order.getTaxAmountFormatted());
		Assert.assertEquals(regex, order.getTotalDiscountAmountFormatted());
		Assert.assertEquals(regex, order.getTotalFormatted());
		Assert.assertEquals(regex, order.getTransactionId());
	}

	@Test
	public void testGetOrdersPage() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLGetOrdersPage() throws Exception {
		GraphQLField graphQLField = new GraphQLField(
			"orders",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 2);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject ordersJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/orders");

		Assert.assertEquals(0, ordersJSONObject.get("totalCount"));

		Order order1 = testGraphQLOrder_addOrder();
		Order order2 = testGraphQLOrder_addOrder();

		ordersJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/orders");

		Assert.assertEquals(2, ordersJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(order1, order2),
			Arrays.asList(
				OrderSerDes.toDTOs(ordersJSONObject.getString("items"))));
	}

	@Test
	public void testPostOrder() throws Exception {
		Order randomOrder = randomOrder();

		Order postOrder = testPostOrder_addOrder(randomOrder);

		assertEquals(randomOrder, postOrder);
		assertValid(postOrder);
	}

	protected Order testPostOrder_addOrder(Order order) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteOrderByExternalReferenceCode() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Order order = testDeleteOrderByExternalReferenceCode_addOrder();

		assertHttpResponseStatusCode(
			204,
			orderResource.deleteOrderByExternalReferenceCodeHttpResponse(
				order.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			orderResource.getOrderByExternalReferenceCodeHttpResponse(
				order.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			orderResource.getOrderByExternalReferenceCodeHttpResponse(
				order.getExternalReferenceCode()));
	}

	protected Order testDeleteOrderByExternalReferenceCode_addOrder()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetOrderByExternalReferenceCode() throws Exception {
		Order postOrder = testGetOrderByExternalReferenceCode_addOrder();

		Order getOrder = orderResource.getOrderByExternalReferenceCode(
			postOrder.getExternalReferenceCode());

		assertEquals(postOrder, getOrder);
		assertValid(getOrder);
	}

	protected Order testGetOrderByExternalReferenceCode_addOrder()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetOrderByExternalReferenceCode() throws Exception {
		Order order = testGraphQLOrder_addOrder();

		Assert.assertTrue(
			equals(
				order,
				OrderSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"orderByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												order.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/orderByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetOrderByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"orderByExternalReferenceCode",
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
	public void testPatchOrderByExternalReferenceCode() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteOrder() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Order order = testDeleteOrder_addOrder();

		assertHttpResponseStatusCode(
			204, orderResource.deleteOrderHttpResponse(order.getId()));

		assertHttpResponseStatusCode(
			404, orderResource.getOrderHttpResponse(order.getId()));

		assertHttpResponseStatusCode(
			404, orderResource.getOrderHttpResponse(order.getId()));
	}

	protected Order testDeleteOrder_addOrder() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteOrder() throws Exception {
		Order order = testGraphQLOrder_addOrder();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteOrder",
						new HashMap<String, Object>() {
							{
								put("orderId", order.getId());
							}
						})),
				"JSONObject/data", "Object/deleteOrder"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"order",
						new HashMap<String, Object>() {
							{
								put("orderId", order.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetOrder() throws Exception {
		Order postOrder = testGetOrder_addOrder();

		Order getOrder = orderResource.getOrder(postOrder.getId());

		assertEquals(postOrder, getOrder);
		assertValid(getOrder);
	}

	protected Order testGetOrder_addOrder() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetOrder() throws Exception {
		Order order = testGraphQLOrder_addOrder();

		Assert.assertTrue(
			equals(
				order,
				OrderSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"order",
								new HashMap<String, Object>() {
									{
										put("id", order.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/order"))));
	}

	@Test
	public void testGraphQLGetOrderNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"order",
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
	public void testPatchOrder() throws Exception {
		Assert.assertTrue(false);
	}

	protected Order testGraphQLOrder_addOrder() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Order order1, Order order2) {
		Assert.assertTrue(
			order1 + " does not equal " + order2, equals(order1, order2));
	}

	protected void assertEquals(List<Order> orders1, List<Order> orders2) {
		Assert.assertEquals(orders1.size(), orders2.size());

		for (int i = 0; i < orders1.size(); i++) {
			Order order1 = orders1.get(i);
			Order order2 = orders2.get(i);

			assertEquals(order1, order2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Order> orders1, List<Order> orders2) {

		Assert.assertEquals(orders1.size(), orders2.size());

		for (Order order1 : orders1) {
			boolean contains = false;

			for (Order order2 : orders2) {
				if (equals(order1, order2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				orders2 + " does not contain " + order1, contains);
		}
	}

	protected void assertValid(Order order) {
		boolean valid = true;

		if (order.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"accountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (order.getAccountExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (order.getAccountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("advanceStatus", additionalAssertFieldName)) {
				if (order.getAdvanceStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("billingAddress", additionalAssertFieldName)) {
				if (order.getBillingAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("billingAddressId", additionalAssertFieldName)) {
				if (order.getBillingAddressId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("channelId", additionalAssertFieldName)) {
				if (order.getChannelId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("couponCode", additionalAssertFieldName)) {
				if (order.getCouponCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (order.getCreateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("currencyCode", additionalAssertFieldName)) {
				if (order.getCurrencyCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (order.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (order.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"lastPriceUpdateDate", additionalAssertFieldName)) {

				if (order.getLastPriceUpdateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (order.getModifiedDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderDate", additionalAssertFieldName)) {
				if (order.getOrderDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderItems", additionalAssertFieldName)) {
				if (order.getOrderItems() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderStatus", additionalAssertFieldName)) {
				if (order.getOrderStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("paymentMethod", additionalAssertFieldName)) {
				if (order.getPaymentMethod() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("paymentStatus", additionalAssertFieldName)) {
				if (order.getPaymentStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("printedNote", additionalAssertFieldName)) {
				if (order.getPrintedNote() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"purchaseOrderNumber", additionalAssertFieldName)) {

				if (order.getPurchaseOrderNumber() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"requestedDeliveryDate", additionalAssertFieldName)) {

				if (order.getRequestedDeliveryDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shippingAddress", additionalAssertFieldName)) {
				if (order.getShippingAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingAddressId", additionalAssertFieldName)) {

				if (order.getShippingAddressId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shippingAmount", additionalAssertFieldName)) {
				if (order.getShippingAmount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingAmountFormatted", additionalAssertFieldName)) {

				if (order.getShippingAmountFormatted() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingAmountValue", additionalAssertFieldName)) {

				if (order.getShippingAmountValue() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingDiscountAmount", additionalAssertFieldName)) {

				if (order.getShippingDiscountAmount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingDiscountAmountFormatted",
					additionalAssertFieldName)) {

				if (order.getShippingDiscountAmountFormatted() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingDiscountPercentageLevel1",
					additionalAssertFieldName)) {

				if (order.getShippingDiscountPercentageLevel1() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingDiscountPercentageLevel2",
					additionalAssertFieldName)) {

				if (order.getShippingDiscountPercentageLevel2() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingDiscountPercentageLevel3",
					additionalAssertFieldName)) {

				if (order.getShippingDiscountPercentageLevel3() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingDiscountPercentageLevel4",
					additionalAssertFieldName)) {

				if (order.getShippingDiscountPercentageLevel4() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shippingMethod", additionalAssertFieldName)) {
				if (order.getShippingMethod() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shippingOption", additionalAssertFieldName)) {
				if (order.getShippingOption() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("subtotal", additionalAssertFieldName)) {
				if (order.getSubtotal() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("subtotalAmount", additionalAssertFieldName)) {
				if (order.getSubtotalAmount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"subtotalDiscountAmount", additionalAssertFieldName)) {

				if (order.getSubtotalDiscountAmount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"subtotalDiscountAmountFormatted",
					additionalAssertFieldName)) {

				if (order.getSubtotalDiscountAmountFormatted() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"subtotalDiscountPercentageLevel1",
					additionalAssertFieldName)) {

				if (order.getSubtotalDiscountPercentageLevel1() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"subtotalDiscountPercentageLevel2",
					additionalAssertFieldName)) {

				if (order.getSubtotalDiscountPercentageLevel2() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"subtotalDiscountPercentageLevel3",
					additionalAssertFieldName)) {

				if (order.getSubtotalDiscountPercentageLevel3() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"subtotalDiscountPercentageLevel4",
					additionalAssertFieldName)) {

				if (order.getSubtotalDiscountPercentageLevel4() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"subtotalFormatted", additionalAssertFieldName)) {

				if (order.getSubtotalFormatted() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("taxAmount", additionalAssertFieldName)) {
				if (order.getTaxAmount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"taxAmountFormatted", additionalAssertFieldName)) {

				if (order.getTaxAmountFormatted() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("total", additionalAssertFieldName)) {
				if (order.getTotal() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("totalAmount", additionalAssertFieldName)) {
				if (order.getTotalAmount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"totalDiscountAmount", additionalAssertFieldName)) {

				if (order.getTotalDiscountAmount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"totalDiscountAmountFormatted",
					additionalAssertFieldName)) {

				if (order.getTotalDiscountAmountFormatted() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"totalDiscountPercentageLevel1",
					additionalAssertFieldName)) {

				if (order.getTotalDiscountPercentageLevel1() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"totalDiscountPercentageLevel2",
					additionalAssertFieldName)) {

				if (order.getTotalDiscountPercentageLevel2() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"totalDiscountPercentageLevel3",
					additionalAssertFieldName)) {

				if (order.getTotalDiscountPercentageLevel3() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"totalDiscountPercentageLevel4",
					additionalAssertFieldName)) {

				if (order.getTotalDiscountPercentageLevel4() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("totalFormatted", additionalAssertFieldName)) {
				if (order.getTotalFormatted() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("transactionId", additionalAssertFieldName)) {
				if (order.getTransactionId() == null) {
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

	protected void assertValid(Page<Order> page) {
		boolean valid = false;

		java.util.Collection<Order> orders = page.getItems();

		int size = orders.size();

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
					com.liferay.headless.commerce.admin.order.dto.v1_0.Order.
						class)) {

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

	protected boolean equals(Order order1, Order order2) {
		if (order1 == order2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"accountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getAccountExternalReferenceCode(),
						order2.getAccountExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getAccountId(), order2.getAccountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("advanceStatus", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getAdvanceStatus(), order2.getAdvanceStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("billingAddress", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getBillingAddress(),
						order2.getBillingAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("billingAddressId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getBillingAddressId(),
						order2.getBillingAddressId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("channelId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getChannelId(), order2.getChannelId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("couponCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getCouponCode(), order2.getCouponCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getCreateDate(), order2.getCreateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("currencyCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getCurrencyCode(), order2.getCurrencyCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!equals(
						(Map)order1.getCustomFields(),
						(Map)order2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getExternalReferenceCode(),
						order2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(order1.getId(), order2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals(
					"lastPriceUpdateDate", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getLastPriceUpdateDate(),
						order2.getLastPriceUpdateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getModifiedDate(), order2.getModifiedDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getOrderDate(), order2.getOrderDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderItems", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getOrderItems(), order2.getOrderItems())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderStatus", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getOrderStatus(), order2.getOrderStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("paymentMethod", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getPaymentMethod(), order2.getPaymentMethod())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("paymentStatus", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getPaymentStatus(), order2.getPaymentStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("printedNote", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getPrintedNote(), order2.getPrintedNote())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"purchaseOrderNumber", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getPurchaseOrderNumber(),
						order2.getPurchaseOrderNumber())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"requestedDeliveryDate", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getRequestedDeliveryDate(),
						order2.getRequestedDeliveryDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shippingAddress", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getShippingAddress(),
						order2.getShippingAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingAddressId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getShippingAddressId(),
						order2.getShippingAddressId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shippingAmount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getShippingAmount(),
						order2.getShippingAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingAmountFormatted", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getShippingAmountFormatted(),
						order2.getShippingAmountFormatted())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingAmountValue", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getShippingAmountValue(),
						order2.getShippingAmountValue())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingDiscountAmount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getShippingDiscountAmount(),
						order2.getShippingDiscountAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingDiscountAmountFormatted",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getShippingDiscountAmountFormatted(),
						order2.getShippingDiscountAmountFormatted())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingDiscountPercentageLevel1",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getShippingDiscountPercentageLevel1(),
						order2.getShippingDiscountPercentageLevel1())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingDiscountPercentageLevel2",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getShippingDiscountPercentageLevel2(),
						order2.getShippingDiscountPercentageLevel2())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingDiscountPercentageLevel3",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getShippingDiscountPercentageLevel3(),
						order2.getShippingDiscountPercentageLevel3())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingDiscountPercentageLevel4",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getShippingDiscountPercentageLevel4(),
						order2.getShippingDiscountPercentageLevel4())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shippingMethod", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getShippingMethod(),
						order2.getShippingMethod())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shippingOption", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getShippingOption(),
						order2.getShippingOption())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subtotal", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getSubtotal(), order2.getSubtotal())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subtotalAmount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getSubtotalAmount(),
						order2.getSubtotalAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"subtotalDiscountAmount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getSubtotalDiscountAmount(),
						order2.getSubtotalDiscountAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"subtotalDiscountAmountFormatted",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getSubtotalDiscountAmountFormatted(),
						order2.getSubtotalDiscountAmountFormatted())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"subtotalDiscountPercentageLevel1",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getSubtotalDiscountPercentageLevel1(),
						order2.getSubtotalDiscountPercentageLevel1())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"subtotalDiscountPercentageLevel2",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getSubtotalDiscountPercentageLevel2(),
						order2.getSubtotalDiscountPercentageLevel2())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"subtotalDiscountPercentageLevel3",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getSubtotalDiscountPercentageLevel3(),
						order2.getSubtotalDiscountPercentageLevel3())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"subtotalDiscountPercentageLevel4",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getSubtotalDiscountPercentageLevel4(),
						order2.getSubtotalDiscountPercentageLevel4())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"subtotalFormatted", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getSubtotalFormatted(),
						order2.getSubtotalFormatted())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("taxAmount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getTaxAmount(), order2.getTaxAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"taxAmountFormatted", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getTaxAmountFormatted(),
						order2.getTaxAmountFormatted())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("total", additionalAssertFieldName)) {
				if (!Objects.deepEquals(order1.getTotal(), order2.getTotal())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("totalAmount", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getTotalAmount(), order2.getTotalAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"totalDiscountAmount", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getTotalDiscountAmount(),
						order2.getTotalDiscountAmount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"totalDiscountAmountFormatted",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getTotalDiscountAmountFormatted(),
						order2.getTotalDiscountAmountFormatted())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"totalDiscountPercentageLevel1",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getTotalDiscountPercentageLevel1(),
						order2.getTotalDiscountPercentageLevel1())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"totalDiscountPercentageLevel2",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getTotalDiscountPercentageLevel2(),
						order2.getTotalDiscountPercentageLevel2())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"totalDiscountPercentageLevel3",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getTotalDiscountPercentageLevel3(),
						order2.getTotalDiscountPercentageLevel3())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"totalDiscountPercentageLevel4",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						order1.getTotalDiscountPercentageLevel4(),
						order2.getTotalDiscountPercentageLevel4())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("totalFormatted", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getTotalFormatted(),
						order2.getTotalFormatted())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("transactionId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						order1.getTransactionId(), order2.getTransactionId())) {

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

		if (!(_orderResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_orderResource;

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
		EntityField entityField, String operator, Order order) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("accountExternalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(order.getAccountExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("accountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("advanceStatus")) {
			sb.append("'");
			sb.append(String.valueOf(order.getAdvanceStatus()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("billingAddress")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("billingAddressId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("channelId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("couponCode")) {
			sb.append("'");
			sb.append(String.valueOf(order.getCouponCode()));
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
						DateUtils.addSeconds(order.getCreateDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(order.getCreateDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(order.getCreateDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("currencyCode")) {
			sb.append("'");
			sb.append(String.valueOf(order.getCurrencyCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("customFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(order.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("lastPriceUpdateDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							order.getLastPriceUpdateDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							order.getLastPriceUpdateDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(order.getLastPriceUpdateDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("modifiedDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(order.getModifiedDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(order.getModifiedDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(order.getModifiedDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("orderDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(order.getOrderDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(order.getOrderDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(order.getOrderDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("orderItems")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderStatus")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("paymentMethod")) {
			sb.append("'");
			sb.append(String.valueOf(order.getPaymentMethod()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("paymentStatus")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("printedNote")) {
			sb.append("'");
			sb.append(String.valueOf(order.getPrintedNote()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("purchaseOrderNumber")) {
			sb.append("'");
			sb.append(String.valueOf(order.getPurchaseOrderNumber()));
			sb.append("'");

			return sb.toString();
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
							order.getRequestedDeliveryDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							order.getRequestedDeliveryDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(order.getRequestedDeliveryDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("shippingAddress")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingAddressId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingAmountFormatted")) {
			sb.append("'");
			sb.append(String.valueOf(order.getShippingAmountFormatted()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("shippingAmountValue")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingDiscountAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingDiscountAmountFormatted")) {
			sb.append("'");
			sb.append(
				String.valueOf(order.getShippingDiscountAmountFormatted()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("shippingDiscountPercentageLevel1")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingDiscountPercentageLevel2")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingDiscountPercentageLevel3")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingDiscountPercentageLevel4")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shippingMethod")) {
			sb.append("'");
			sb.append(String.valueOf(order.getShippingMethod()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("shippingOption")) {
			sb.append("'");
			sb.append(String.valueOf(order.getShippingOption()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("subtotal")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subtotalAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subtotalDiscountAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subtotalDiscountAmountFormatted")) {
			sb.append("'");
			sb.append(
				String.valueOf(order.getSubtotalDiscountAmountFormatted()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("subtotalDiscountPercentageLevel1")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subtotalDiscountPercentageLevel2")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subtotalDiscountPercentageLevel3")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subtotalDiscountPercentageLevel4")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subtotalFormatted")) {
			sb.append("'");
			sb.append(String.valueOf(order.getSubtotalFormatted()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("taxAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("taxAmountFormatted")) {
			sb.append("'");
			sb.append(String.valueOf(order.getTaxAmountFormatted()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("total")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("totalAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("totalDiscountAmount")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("totalDiscountAmountFormatted")) {
			sb.append("'");
			sb.append(String.valueOf(order.getTotalDiscountAmountFormatted()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("totalDiscountPercentageLevel1")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("totalDiscountPercentageLevel2")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("totalDiscountPercentageLevel3")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("totalDiscountPercentageLevel4")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("totalFormatted")) {
			sb.append("'");
			sb.append(String.valueOf(order.getTotalFormatted()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("transactionId")) {
			sb.append("'");
			sb.append(String.valueOf(order.getTransactionId()));
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

	protected Order randomOrder() throws Exception {
		return new Order() {
			{
				accountExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				accountId = RandomTestUtil.randomLong();
				advanceStatus = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				billingAddressId = RandomTestUtil.randomLong();
				channelId = RandomTestUtil.randomLong();
				couponCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				createDate = RandomTestUtil.nextDate();
				currencyCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				lastPriceUpdateDate = RandomTestUtil.nextDate();
				modifiedDate = RandomTestUtil.nextDate();
				orderDate = RandomTestUtil.nextDate();
				orderStatus = RandomTestUtil.randomInt();
				paymentMethod = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				paymentStatus = RandomTestUtil.randomInt();
				printedNote = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				purchaseOrderNumber = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				requestedDeliveryDate = RandomTestUtil.nextDate();
				shippingAddressId = RandomTestUtil.randomLong();
				shippingAmountFormatted = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				shippingAmountValue = RandomTestUtil.randomDouble();
				shippingDiscountAmount = RandomTestUtil.randomDouble();
				shippingDiscountAmountFormatted = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				shippingDiscountPercentageLevel1 =
					RandomTestUtil.randomDouble();
				shippingDiscountPercentageLevel2 =
					RandomTestUtil.randomDouble();
				shippingDiscountPercentageLevel3 =
					RandomTestUtil.randomDouble();
				shippingDiscountPercentageLevel4 =
					RandomTestUtil.randomDouble();
				shippingMethod = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				shippingOption = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				subtotalAmount = RandomTestUtil.randomDouble();
				subtotalDiscountAmount = RandomTestUtil.randomDouble();
				subtotalDiscountAmountFormatted = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				subtotalDiscountPercentageLevel1 =
					RandomTestUtil.randomDouble();
				subtotalDiscountPercentageLevel2 =
					RandomTestUtil.randomDouble();
				subtotalDiscountPercentageLevel3 =
					RandomTestUtil.randomDouble();
				subtotalDiscountPercentageLevel4 =
					RandomTestUtil.randomDouble();
				subtotalFormatted = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				taxAmount = RandomTestUtil.randomDouble();
				taxAmountFormatted = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				totalAmount = RandomTestUtil.randomDouble();
				totalDiscountAmount = RandomTestUtil.randomDouble();
				totalDiscountAmountFormatted = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				totalDiscountPercentageLevel1 = RandomTestUtil.randomDouble();
				totalDiscountPercentageLevel2 = RandomTestUtil.randomDouble();
				totalDiscountPercentageLevel3 = RandomTestUtil.randomDouble();
				totalDiscountPercentageLevel4 = RandomTestUtil.randomDouble();
				totalFormatted = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				transactionId = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
			}
		};
	}

	protected Order randomIrrelevantOrder() throws Exception {
		Order randomIrrelevantOrder = randomOrder();

		return randomIrrelevantOrder;
	}

	protected Order randomPatchOrder() throws Exception {
		return randomOrder();
	}

	protected OrderResource orderResource;
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
		BaseOrderResourceTestCase.class);

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
		com.liferay.headless.commerce.admin.order.resource.v1_0.OrderResource
			_orderResource;

}