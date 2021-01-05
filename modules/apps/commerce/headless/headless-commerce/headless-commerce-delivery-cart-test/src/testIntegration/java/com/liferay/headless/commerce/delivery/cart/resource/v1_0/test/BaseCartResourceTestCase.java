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

package com.liferay.headless.commerce.delivery.cart.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.Cart;
import com.liferay.headless.commerce.delivery.cart.client.http.HttpInvoker;
import com.liferay.headless.commerce.delivery.cart.client.pagination.Page;
import com.liferay.headless.commerce.delivery.cart.client.pagination.Pagination;
import com.liferay.headless.commerce.delivery.cart.client.resource.v1_0.CartResource;
import com.liferay.headless.commerce.delivery.cart.client.serdes.v1_0.CartSerDes;
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
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public abstract class BaseCartResourceTestCase {

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

		_cartResource.setContextCompany(testCompany);

		CartResource.Builder builder = CartResource.builder();

		cartResource = builder.authentication(
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

		Cart cart1 = randomCart();

		String json = objectMapper.writeValueAsString(cart1);

		Cart cart2 = CartSerDes.toDTO(json);

		Assert.assertTrue(equals(cart1, cart2));
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

		Cart cart = randomCart();

		String json1 = objectMapper.writeValueAsString(cart);
		String json2 = CartSerDes.toJSON(cart);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		Cart cart = randomCart();

		cart.setAccount(regex);
		cart.setAuthor(regex);
		cart.setCouponCode(regex);
		cart.setCurrencyCode(regex);
		cart.setOrderUUID(regex);
		cart.setPaymentMethod(regex);
		cart.setPaymentMethodLabel(regex);
		cart.setPaymentStatusLabel(regex);
		cart.setPrintedNote(regex);
		cart.setPurchaseOrderNumber(regex);
		cart.setShippingMethod(regex);
		cart.setShippingOption(regex);
		cart.setStatus(regex);

		String json = CartSerDes.toJSON(cart);

		Assert.assertFalse(json.contains(regex));

		cart = CartSerDes.toDTO(json);

		Assert.assertEquals(regex, cart.getAccount());
		Assert.assertEquals(regex, cart.getAuthor());
		Assert.assertEquals(regex, cart.getCouponCode());
		Assert.assertEquals(regex, cart.getCurrencyCode());
		Assert.assertEquals(regex, cart.getOrderUUID());
		Assert.assertEquals(regex, cart.getPaymentMethod());
		Assert.assertEquals(regex, cart.getPaymentMethodLabel());
		Assert.assertEquals(regex, cart.getPaymentStatusLabel());
		Assert.assertEquals(regex, cart.getPrintedNote());
		Assert.assertEquals(regex, cart.getPurchaseOrderNumber());
		Assert.assertEquals(regex, cart.getShippingMethod());
		Assert.assertEquals(regex, cart.getShippingOption());
		Assert.assertEquals(regex, cart.getStatus());
	}

	@Test
	public void testDeleteCart() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		Cart cart = testDeleteCart_addCart();

		assertHttpResponseStatusCode(
			204, cartResource.deleteCartHttpResponse(cart.getId()));

		assertHttpResponseStatusCode(
			404, cartResource.getCartHttpResponse(cart.getId()));

		assertHttpResponseStatusCode(404, cartResource.getCartHttpResponse(0L));
	}

	protected Cart testDeleteCart_addCart() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteCart() throws Exception {
		Cart cart = testGraphQLCart_addCart();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteCart",
						new HashMap<String, Object>() {
							{
								put("cartId", cart.getId());
							}
						})),
				"JSONObject/data", "Object/deleteCart"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"cart",
						new HashMap<String, Object>() {
							{
								put("cartId", cart.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetCart() throws Exception {
		Cart postCart = testGetCart_addCart();

		Cart getCart = cartResource.getCart(postCart.getId());

		assertEquals(postCart, getCart);
		assertValid(getCart);
	}

	protected Cart testGetCart_addCart() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetCart() throws Exception {
		Cart cart = testGraphQLCart_addCart();

		Assert.assertTrue(
			equals(
				cart,
				CartSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"cart",
								new HashMap<String, Object>() {
									{
										put("cartId", cart.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/cart"))));
	}

	@Test
	public void testGraphQLGetCartNotFound() throws Exception {
		Long irrelevantCartId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"cart",
						new HashMap<String, Object>() {
							{
								put("cartId", irrelevantCartId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchCart() throws Exception {
		Cart postCart = testPatchCart_addCart();

		Cart randomPatchCart = randomPatchCart();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		Cart patchCart = cartResource.patchCart(
			postCart.getId(), randomPatchCart);

		Cart expectedPatchCart = postCart.clone();

		_beanUtilsBean.copyProperties(expectedPatchCart, randomPatchCart);

		Cart getCart = cartResource.getCart(patchCart.getId());

		assertEquals(expectedPatchCart, getCart);
		assertValid(getCart);
	}

	protected Cart testPatchCart_addCart() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutCart() throws Exception {
		Cart postCart = testPutCart_addCart();

		Cart randomCart = randomCart();

		Cart putCart = cartResource.putCart(postCart.getId(), randomCart);

		assertEquals(randomCart, putCart);
		assertValid(putCart);

		Cart getCart = cartResource.getCart(putCart.getId());

		assertEquals(randomCart, getCart);
		assertValid(getCart);
	}

	protected Cart testPutCart_addCart() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostCartCheckout() throws Exception {
		Cart randomCart = randomCart();

		Cart postCart = testPostCartCheckout_addCart(randomCart);

		assertEquals(randomCart, postCart);
		assertValid(postCart);
	}

	protected Cart testPostCartCheckout_addCart(Cart cart) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostCartCouponCode() throws Exception {
		Cart randomCart = randomCart();

		Cart postCart = testPostCartCouponCode_addCart(randomCart);

		assertEquals(randomCart, postCart);
		assertValid(postCart);
	}

	protected Cart testPostCartCouponCode_addCart(Cart cart) throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetChannelCartsPage() throws Exception {
		Page<Cart> page = cartResource.getChannelCartsPage(
			testGetChannelCartsPage_getChannelId(), Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long channelId = testGetChannelCartsPage_getChannelId();
		Long irrelevantChannelId =
			testGetChannelCartsPage_getIrrelevantChannelId();

		if ((irrelevantChannelId != null)) {
			Cart irrelevantCart = testGetChannelCartsPage_addCart(
				irrelevantChannelId, randomIrrelevantCart());

			page = cartResource.getChannelCartsPage(
				irrelevantChannelId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantCart), (List<Cart>)page.getItems());
			assertValid(page);
		}

		Cart cart1 = testGetChannelCartsPage_addCart(channelId, randomCart());

		Cart cart2 = testGetChannelCartsPage_addCart(channelId, randomCart());

		page = cartResource.getChannelCartsPage(channelId, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(cart1, cart2), (List<Cart>)page.getItems());
		assertValid(page);

		cartResource.deleteCart(cart1.getId());

		cartResource.deleteCart(cart2.getId());
	}

	@Test
	public void testGetChannelCartsPageWithPagination() throws Exception {
		Long channelId = testGetChannelCartsPage_getChannelId();

		Cart cart1 = testGetChannelCartsPage_addCart(channelId, randomCart());

		Cart cart2 = testGetChannelCartsPage_addCart(channelId, randomCart());

		Cart cart3 = testGetChannelCartsPage_addCart(channelId, randomCart());

		Page<Cart> page1 = cartResource.getChannelCartsPage(
			channelId, Pagination.of(1, 2));

		List<Cart> carts1 = (List<Cart>)page1.getItems();

		Assert.assertEquals(carts1.toString(), 2, carts1.size());

		Page<Cart> page2 = cartResource.getChannelCartsPage(
			channelId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<Cart> carts2 = (List<Cart>)page2.getItems();

		Assert.assertEquals(carts2.toString(), 1, carts2.size());

		Page<Cart> page3 = cartResource.getChannelCartsPage(
			channelId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(cart1, cart2, cart3), (List<Cart>)page3.getItems());
	}

	protected Cart testGetChannelCartsPage_addCart(Long channelId, Cart cart)
		throws Exception {

		return cartResource.postChannelCart(channelId, cart);
	}

	protected Long testGetChannelCartsPage_getChannelId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetChannelCartsPage_getIrrelevantChannelId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostChannelCart() throws Exception {
		Cart randomCart = randomCart();

		Cart postCart = testPostChannelCart_addCart(randomCart);

		assertEquals(randomCart, postCart);
		assertValid(postCart);
	}

	protected Cart testPostChannelCart_addCart(Cart cart) throws Exception {
		return cartResource.postChannelCart(
			testGetChannelCartsPage_getChannelId(), cart);
	}

	protected Cart testGraphQLCart_addCart() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(Cart cart1, Cart cart2) {
		Assert.assertTrue(
			cart1 + " does not equal " + cart2, equals(cart1, cart2));
	}

	protected void assertEquals(List<Cart> carts1, List<Cart> carts2) {
		Assert.assertEquals(carts1.size(), carts2.size());

		for (int i = 0; i < carts1.size(); i++) {
			Cart cart1 = carts1.get(i);
			Cart cart2 = carts2.get(i);

			assertEquals(cart1, cart2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<Cart> carts1, List<Cart> carts2) {

		Assert.assertEquals(carts1.size(), carts2.size());

		for (Cart cart1 : carts1) {
			boolean contains = false;

			for (Cart cart2 : carts2) {
				if (equals(cart1, cart2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(carts2 + " does not contain " + cart1, contains);
		}
	}

	protected void assertValid(Cart cart) throws Exception {
		boolean valid = true;

		if (cart.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("account", additionalAssertFieldName)) {
				if (cart.getAccount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (cart.getAccountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("author", additionalAssertFieldName)) {
				if (cart.getAuthor() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("billingAddress", additionalAssertFieldName)) {
				if (cart.getBillingAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("billingAddressId", additionalAssertFieldName)) {
				if (cart.getBillingAddressId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("cartItems", additionalAssertFieldName)) {
				if (cart.getCartItems() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("channelId", additionalAssertFieldName)) {
				if (cart.getChannelId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("couponCode", additionalAssertFieldName)) {
				if (cart.getCouponCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (cart.getCreateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("currencyCode", additionalAssertFieldName)) {
				if (cart.getCurrencyCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (cart.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("errorMessages", additionalAssertFieldName)) {
				if (cart.getErrorMessages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"lastPriceUpdateDate", additionalAssertFieldName)) {

				if (cart.getLastPriceUpdateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (cart.getModifiedDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("notes", additionalAssertFieldName)) {
				if (cart.getNotes() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderStatusInfo", additionalAssertFieldName)) {
				if (cart.getOrderStatusInfo() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderUUID", additionalAssertFieldName)) {
				if (cart.getOrderUUID() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("paymentMethod", additionalAssertFieldName)) {
				if (cart.getPaymentMethod() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"paymentMethodLabel", additionalAssertFieldName)) {

				if (cart.getPaymentMethodLabel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("paymentStatus", additionalAssertFieldName)) {
				if (cart.getPaymentStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"paymentStatusInfo", additionalAssertFieldName)) {

				if (cart.getPaymentStatusInfo() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"paymentStatusLabel", additionalAssertFieldName)) {

				if (cart.getPaymentStatusLabel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("printedNote", additionalAssertFieldName)) {
				if (cart.getPrintedNote() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"purchaseOrderNumber", additionalAssertFieldName)) {

				if (cart.getPurchaseOrderNumber() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shippingAddress", additionalAssertFieldName)) {
				if (cart.getShippingAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingAddressId", additionalAssertFieldName)) {

				if (cart.getShippingAddressId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shippingMethod", additionalAssertFieldName)) {
				if (cart.getShippingMethod() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shippingOption", additionalAssertFieldName)) {
				if (cart.getShippingOption() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (cart.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("summary", additionalAssertFieldName)) {
				if (cart.getSummary() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("useAsBilling", additionalAssertFieldName)) {
				if (cart.getUseAsBilling() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("valid", additionalAssertFieldName)) {
				if (cart.getValid() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"workflowStatusInfo", additionalAssertFieldName)) {

				if (cart.getWorkflowStatusInfo() == null) {
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

	protected void assertValid(Page<Cart> page) {
		boolean valid = false;

		java.util.Collection<Cart> carts = page.getItems();

		int size = carts.size();

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
					com.liferay.headless.commerce.delivery.cart.dto.v1_0.Cart.
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

	protected boolean equals(Cart cart1, Cart cart2) {
		if (cart1 == cart2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("account", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getAccount(), cart2.getAccount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getAccountId(), cart2.getAccountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("author", additionalAssertFieldName)) {
				if (!Objects.deepEquals(cart1.getAuthor(), cart2.getAuthor())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("billingAddress", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getBillingAddress(), cart2.getBillingAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("billingAddressId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getBillingAddressId(),
						cart2.getBillingAddressId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("cartItems", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getCartItems(), cart2.getCartItems())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("channelId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getChannelId(), cart2.getChannelId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("couponCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getCouponCode(), cart2.getCouponCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getCreateDate(), cart2.getCreateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("currencyCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getCurrencyCode(), cart2.getCurrencyCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!equals(
						(Map)cart1.getCustomFields(),
						(Map)cart2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("errorMessages", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getErrorMessages(), cart2.getErrorMessages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(cart1.getId(), cart2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals(
					"lastPriceUpdateDate", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						cart1.getLastPriceUpdateDate(),
						cart2.getLastPriceUpdateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getModifiedDate(), cart2.getModifiedDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("notes", additionalAssertFieldName)) {
				if (!Objects.deepEquals(cart1.getNotes(), cart2.getNotes())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("orderStatusInfo", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getOrderStatusInfo(),
						cart2.getOrderStatusInfo())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderUUID", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getOrderUUID(), cart2.getOrderUUID())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("paymentMethod", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getPaymentMethod(), cart2.getPaymentMethod())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"paymentMethodLabel", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						cart1.getPaymentMethodLabel(),
						cart2.getPaymentMethodLabel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("paymentStatus", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getPaymentStatus(), cart2.getPaymentStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"paymentStatusInfo", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						cart1.getPaymentStatusInfo(),
						cart2.getPaymentStatusInfo())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"paymentStatusLabel", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						cart1.getPaymentStatusLabel(),
						cart2.getPaymentStatusLabel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("printedNote", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getPrintedNote(), cart2.getPrintedNote())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"purchaseOrderNumber", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						cart1.getPurchaseOrderNumber(),
						cart2.getPurchaseOrderNumber())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shippingAddress", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getShippingAddress(),
						cart2.getShippingAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"shippingAddressId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						cart1.getShippingAddressId(),
						cart2.getShippingAddressId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shippingMethod", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getShippingMethod(), cart2.getShippingMethod())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shippingOption", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getShippingOption(), cart2.getShippingOption())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(cart1.getStatus(), cart2.getStatus())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("summary", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getSummary(), cart2.getSummary())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("useAsBilling", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cart1.getUseAsBilling(), cart2.getUseAsBilling())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("valid", additionalAssertFieldName)) {
				if (!Objects.deepEquals(cart1.getValid(), cart2.getValid())) {
					return false;
				}

				continue;
			}

			if (Objects.equals(
					"workflowStatusInfo", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						cart1.getWorkflowStatusInfo(),
						cart2.getWorkflowStatusInfo())) {

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

		if (!(_cartResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_cartResource;

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
		EntityField entityField, String operator, Cart cart) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("account")) {
			sb.append("'");
			sb.append(String.valueOf(cart.getAccount()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("accountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("author")) {
			sb.append("'");
			sb.append(String.valueOf(cart.getAuthor()));
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

		if (entityFieldName.equals("cartItems")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("channelId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("couponCode")) {
			sb.append("'");
			sb.append(String.valueOf(cart.getCouponCode()));
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
						DateUtils.addSeconds(cart.getCreateDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(cart.getCreateDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(cart.getCreateDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("currencyCode")) {
			sb.append("'");
			sb.append(String.valueOf(cart.getCurrencyCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("customFields")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("errorMessages")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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
							cart.getLastPriceUpdateDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							cart.getLastPriceUpdateDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(cart.getLastPriceUpdateDate()));
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
						DateUtils.addSeconds(cart.getModifiedDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(cart.getModifiedDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(cart.getModifiedDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("notes")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderStatusInfo")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderUUID")) {
			sb.append("'");
			sb.append(String.valueOf(cart.getOrderUUID()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("paymentMethod")) {
			sb.append("'");
			sb.append(String.valueOf(cart.getPaymentMethod()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("paymentMethodLabel")) {
			sb.append("'");
			sb.append(String.valueOf(cart.getPaymentMethodLabel()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("paymentStatus")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("paymentStatusInfo")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("paymentStatusLabel")) {
			sb.append("'");
			sb.append(String.valueOf(cart.getPaymentStatusLabel()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("printedNote")) {
			sb.append("'");
			sb.append(String.valueOf(cart.getPrintedNote()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("purchaseOrderNumber")) {
			sb.append("'");
			sb.append(String.valueOf(cart.getPurchaseOrderNumber()));
			sb.append("'");

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

		if (entityFieldName.equals("shippingMethod")) {
			sb.append("'");
			sb.append(String.valueOf(cart.getShippingMethod()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("shippingOption")) {
			sb.append("'");
			sb.append(String.valueOf(cart.getShippingOption()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("status")) {
			sb.append("'");
			sb.append(String.valueOf(cart.getStatus()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("summary")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("useAsBilling")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("valid")) {
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

	protected Cart randomCart() throws Exception {
		return new Cart() {
			{
				account = StringUtil.toLowerCase(RandomTestUtil.randomString());
				accountId = RandomTestUtil.randomLong();
				author = StringUtil.toLowerCase(RandomTestUtil.randomString());
				billingAddressId = RandomTestUtil.randomLong();
				channelId = RandomTestUtil.randomLong();
				couponCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				createDate = RandomTestUtil.nextDate();
				currencyCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				lastPriceUpdateDate = RandomTestUtil.nextDate();
				modifiedDate = RandomTestUtil.nextDate();
				orderUUID = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				paymentMethod = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				paymentMethodLabel = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				paymentStatus = RandomTestUtil.randomInt();
				paymentStatusLabel = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				printedNote = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				purchaseOrderNumber = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				shippingAddressId = RandomTestUtil.randomLong();
				shippingMethod = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				shippingOption = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				status = StringUtil.toLowerCase(RandomTestUtil.randomString());
				useAsBilling = RandomTestUtil.randomBoolean();
				valid = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected Cart randomIrrelevantCart() throws Exception {
		Cart randomIrrelevantCart = randomCart();

		return randomIrrelevantCart;
	}

	protected Cart randomPatchCart() throws Exception {
		return randomCart();
	}

	protected CartResource cartResource;
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
		BaseCartResourceTestCase.class);

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
		com.liferay.headless.commerce.delivery.cart.resource.v1_0.CartResource
			_cartResource;

}