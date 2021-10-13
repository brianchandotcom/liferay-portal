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

import com.liferay.headless.commerce.delivery.cart.client.dto.v1_0.CartItem;
import com.liferay.headless.commerce.delivery.cart.client.http.HttpInvoker;
import com.liferay.headless.commerce.delivery.cart.client.pagination.Page;
import com.liferay.headless.commerce.delivery.cart.client.pagination.Pagination;
import com.liferay.headless.commerce.delivery.cart.client.resource.v1_0.CartItemResource;
import com.liferay.headless.commerce.delivery.cart.client.serdes.v1_0.CartItemSerDes;
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
public abstract class BaseCartItemResourceTestCase {

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

		_cartItemResource.setContextCompany(testCompany);

		CartItemResource.Builder builder = CartItemResource.builder();

		cartItemResource = builder.authentication(
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

		CartItem cartItem1 = randomCartItem();

		String json = objectMapper.writeValueAsString(cartItem1);

		CartItem cartItem2 = CartItemSerDes.toDTO(json);

		Assert.assertTrue(equals(cartItem1, cartItem2));
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

		CartItem cartItem = randomCartItem();

		String json1 = objectMapper.writeValueAsString(cartItem);
		String json2 = CartItemSerDes.toJSON(cartItem);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		CartItem cartItem = randomCartItem();

		cartItem.setAdaptiveMediaImageHTMLTag(regex);
		cartItem.setName(regex);
		cartItem.setOptions(regex);
		cartItem.setSku(regex);
		cartItem.setThumbnail(regex);

		String json = CartItemSerDes.toJSON(cartItem);

		Assert.assertFalse(json.contains(regex));

		cartItem = CartItemSerDes.toDTO(json);

		Assert.assertEquals(regex, cartItem.getAdaptiveMediaImageHTMLTag());
		Assert.assertEquals(regex, cartItem.getName());
		Assert.assertEquals(regex, cartItem.getOptions());
		Assert.assertEquals(regex, cartItem.getSku());
		Assert.assertEquals(regex, cartItem.getThumbnail());
	}

	@Test
	public void testDeleteCartItem() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		CartItem cartItem = testDeleteCartItem_addCartItem();

		assertHttpResponseStatusCode(
			204, cartItemResource.deleteCartItemHttpResponse(cartItem.getId()));

		assertHttpResponseStatusCode(
			404, cartItemResource.getCartItemHttpResponse(cartItem.getId()));

		assertHttpResponseStatusCode(
			404, cartItemResource.getCartItemHttpResponse(0L));
	}

	protected CartItem testDeleteCartItem_addCartItem() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteCartItem() throws Exception {
		CartItem cartItem = testGraphQLCartItem_addCartItem();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteCartItem",
						new HashMap<String, Object>() {
							{
								put("cartItemId", cartItem.getId());
							}
						})),
				"JSONObject/data", "Object/deleteCartItem"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"cartItem",
					new HashMap<String, Object>() {
						{
							put("cartItemId", cartItem.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetCartItem() throws Exception {
		CartItem postCartItem = testGetCartItem_addCartItem();

		CartItem getCartItem = cartItemResource.getCartItem(
			postCartItem.getId());

		assertEquals(postCartItem, getCartItem);
		assertValid(getCartItem);
	}

	protected CartItem testGetCartItem_addCartItem() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetCartItem() throws Exception {
		CartItem cartItem = testGraphQLCartItem_addCartItem();

		Assert.assertTrue(
			equals(
				cartItem,
				CartItemSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"cartItem",
								new HashMap<String, Object>() {
									{
										put("cartItemId", cartItem.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/cartItem"))));
	}

	@Test
	public void testGraphQLGetCartItemNotFound() throws Exception {
		Long irrelevantCartItemId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"cartItem",
						new HashMap<String, Object>() {
							{
								put("cartItemId", irrelevantCartItemId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchCartItem() throws Exception {
		CartItem postCartItem = testPatchCartItem_addCartItem();

		CartItem randomPatchCartItem = randomPatchCartItem();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		CartItem patchCartItem = cartItemResource.patchCartItem(
			postCartItem.getId(), randomPatchCartItem);

		CartItem expectedPatchCartItem = postCartItem.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchCartItem, randomPatchCartItem);

		CartItem getCartItem = cartItemResource.getCartItem(
			patchCartItem.getId());

		assertEquals(expectedPatchCartItem, getCartItem);
		assertValid(getCartItem);
	}

	protected CartItem testPatchCartItem_addCartItem() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutCartItem() throws Exception {
		CartItem postCartItem = testPutCartItem_addCartItem();

		CartItem randomCartItem = randomCartItem();

		CartItem putCartItem = cartItemResource.putCartItem(
			postCartItem.getId(), randomCartItem);

		assertEquals(randomCartItem, putCartItem);
		assertValid(putCartItem);

		CartItem getCartItem = cartItemResource.getCartItem(
			putCartItem.getId());

		assertEquals(randomCartItem, getCartItem);
		assertValid(getCartItem);
	}

	protected CartItem testPutCartItem_addCartItem() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetCartItemsPage() throws Exception {
		Long cartId = testGetCartItemsPage_getCartId();
		Long irrelevantCartId = testGetCartItemsPage_getIrrelevantCartId();

		Page<CartItem> page = cartItemResource.getCartItemsPage(
			cartId, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantCartId != null) {
			CartItem irrelevantCartItem = testGetCartItemsPage_addCartItem(
				irrelevantCartId, randomIrrelevantCartItem());

			page = cartItemResource.getCartItemsPage(
				irrelevantCartId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantCartItem),
				(List<CartItem>)page.getItems());
			assertValid(page);
		}

		CartItem cartItem1 = testGetCartItemsPage_addCartItem(
			cartId, randomCartItem());

		CartItem cartItem2 = testGetCartItemsPage_addCartItem(
			cartId, randomCartItem());

		page = cartItemResource.getCartItemsPage(cartId, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(cartItem1, cartItem2),
			(List<CartItem>)page.getItems());
		assertValid(page);

		cartItemResource.deleteCartItem(cartItem1.getId());

		cartItemResource.deleteCartItem(cartItem2.getId());
	}

	@Test
	public void testGetCartItemsPageWithPagination() throws Exception {
		Long cartId = testGetCartItemsPage_getCartId();

		CartItem cartItem1 = testGetCartItemsPage_addCartItem(
			cartId, randomCartItem());

		CartItem cartItem2 = testGetCartItemsPage_addCartItem(
			cartId, randomCartItem());

		CartItem cartItem3 = testGetCartItemsPage_addCartItem(
			cartId, randomCartItem());

		Page<CartItem> page1 = cartItemResource.getCartItemsPage(
			cartId, Pagination.of(1, 2));

		List<CartItem> cartItems1 = (List<CartItem>)page1.getItems();

		Assert.assertEquals(cartItems1.toString(), 2, cartItems1.size());

		Page<CartItem> page2 = cartItemResource.getCartItemsPage(
			cartId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<CartItem> cartItems2 = (List<CartItem>)page2.getItems();

		Assert.assertEquals(cartItems2.toString(), 1, cartItems2.size());

		Page<CartItem> page3 = cartItemResource.getCartItemsPage(
			cartId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(cartItem1, cartItem2, cartItem3),
			(List<CartItem>)page3.getItems());
	}

	protected CartItem testGetCartItemsPage_addCartItem(
			Long cartId, CartItem cartItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetCartItemsPage_getCartId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetCartItemsPage_getIrrelevantCartId() throws Exception {
		return null;
	}

	@Test
	public void testGraphQLGetCartItemsPage() throws Exception {
		Long cartId = testGetCartItemsPage_getCartId();

		GraphQLField graphQLField = new GraphQLField(
			"cartItems",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);

					put("cartId", cartId);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject cartItemsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/cartItems");

		Assert.assertEquals(0, cartItemsJSONObject.get("totalCount"));

		CartItem cartItem1 = testGraphQLCartItem_addCartItem();
		CartItem cartItem2 = testGraphQLCartItem_addCartItem();

		cartItemsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/cartItems");

		Assert.assertEquals(2, cartItemsJSONObject.getLong("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(cartItem1, cartItem2),
			Arrays.asList(
				CartItemSerDes.toDTOs(cartItemsJSONObject.getString("items"))));
	}

	@Test
	public void testPostCartItem() throws Exception {
		CartItem randomCartItem = randomCartItem();

		CartItem postCartItem = testPostCartItem_addCartItem(randomCartItem);

		assertEquals(randomCartItem, postCartItem);
		assertValid(postCartItem);
	}

	protected CartItem testPostCartItem_addCartItem(CartItem cartItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected CartItem testGraphQLCartItem_addCartItem() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(CartItem cartItem, List<CartItem> cartItems) {
		boolean contains = false;

		for (CartItem item : cartItems) {
			if (equals(cartItem, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			cartItems + " does not contain " + cartItem, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(CartItem cartItem1, CartItem cartItem2) {
		Assert.assertTrue(
			cartItem1 + " does not equal " + cartItem2,
			equals(cartItem1, cartItem2));
	}

	protected void assertEquals(
		List<CartItem> cartItems1, List<CartItem> cartItems2) {

		Assert.assertEquals(cartItems1.size(), cartItems2.size());

		for (int i = 0; i < cartItems1.size(); i++) {
			CartItem cartItem1 = cartItems1.get(i);
			CartItem cartItem2 = cartItems2.get(i);

			assertEquals(cartItem1, cartItem2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<CartItem> cartItems1, List<CartItem> cartItems2) {

		Assert.assertEquals(cartItems1.size(), cartItems2.size());

		for (CartItem cartItem1 : cartItems1) {
			boolean contains = false;

			for (CartItem cartItem2 : cartItems2) {
				if (equals(cartItem1, cartItem2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				cartItems2 + " does not contain " + cartItem1, contains);
		}
	}

	protected void assertValid(CartItem cartItem) throws Exception {
		boolean valid = true;

		if (cartItem.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"adaptiveMediaImageHTMLTag", additionalAssertFieldName)) {

				if (cartItem.getAdaptiveMediaImageHTMLTag() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("cartItems", additionalAssertFieldName)) {
				if (cartItem.getCartItems() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (cartItem.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("errorMessages", additionalAssertFieldName)) {
				if (cartItem.getErrorMessages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (cartItem.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("options", additionalAssertFieldName)) {
				if (cartItem.getOptions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("parentCartItemId", additionalAssertFieldName)) {
				if (cartItem.getParentCartItemId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("price", additionalAssertFieldName)) {
				if (cartItem.getPrice() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("productId", additionalAssertFieldName)) {
				if (cartItem.getProductId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("productURLs", additionalAssertFieldName)) {
				if (cartItem.getProductURLs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("quantity", additionalAssertFieldName)) {
				if (cartItem.getQuantity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("settings", additionalAssertFieldName)) {
				if (cartItem.getSettings() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (cartItem.getSku() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("skuId", additionalAssertFieldName)) {
				if (cartItem.getSkuId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("subscription", additionalAssertFieldName)) {
				if (cartItem.getSubscription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("thumbnail", additionalAssertFieldName)) {
				if (cartItem.getThumbnail() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("valid", additionalAssertFieldName)) {
				if (cartItem.getValid() == null) {
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

	protected void assertValid(Page<CartItem> page) {
		boolean valid = false;

		java.util.Collection<CartItem> cartItems = page.getItems();

		int size = cartItems.size();

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
					com.liferay.headless.commerce.delivery.cart.dto.v1_0.
						CartItem.class)) {

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

	protected boolean equals(CartItem cartItem1, CartItem cartItem2) {
		if (cartItem1 == cartItem2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"adaptiveMediaImageHTMLTag", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						cartItem1.getAdaptiveMediaImageHTMLTag(),
						cartItem2.getAdaptiveMediaImageHTMLTag())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("cartItems", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartItem1.getCartItems(), cartItem2.getCartItems())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!equals(
						(Map)cartItem1.getCustomFields(),
						(Map)cartItem2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("errorMessages", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartItem1.getErrorMessages(),
						cartItem2.getErrorMessages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(cartItem1.getId(), cartItem2.getId())) {
					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartItem1.getName(), cartItem2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("options", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartItem1.getOptions(), cartItem2.getOptions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("parentCartItemId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartItem1.getParentCartItemId(),
						cartItem2.getParentCartItemId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("price", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartItem1.getPrice(), cartItem2.getPrice())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartItem1.getProductId(), cartItem2.getProductId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productURLs", additionalAssertFieldName)) {
				if (!equals(
						(Map)cartItem1.getProductURLs(),
						(Map)cartItem2.getProductURLs())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("quantity", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartItem1.getQuantity(), cartItem2.getQuantity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("settings", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartItem1.getSettings(), cartItem2.getSettings())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartItem1.getSku(), cartItem2.getSku())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("skuId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartItem1.getSkuId(), cartItem2.getSkuId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscription", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartItem1.getSubscription(),
						cartItem2.getSubscription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("thumbnail", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartItem1.getThumbnail(), cartItem2.getThumbnail())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("valid", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						cartItem1.getValid(), cartItem2.getValid())) {

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

		if (!(_cartItemResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_cartItemResource;

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
		EntityField entityField, String operator, CartItem cartItem) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("adaptiveMediaImageHTMLTag")) {
			sb.append("'");
			sb.append(String.valueOf(cartItem.getAdaptiveMediaImageHTMLTag()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("cartItems")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(cartItem.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("options")) {
			sb.append("'");
			sb.append(String.valueOf(cartItem.getOptions()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("parentCartItemId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("price")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("productURLs")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("quantity")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("settings")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sku")) {
			sb.append("'");
			sb.append(String.valueOf(cartItem.getSku()));
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

		if (entityFieldName.equals("thumbnail")) {
			sb.append("'");
			sb.append(String.valueOf(cartItem.getThumbnail()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("valid")) {
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

	protected CartItem randomCartItem() throws Exception {
		return new CartItem() {
			{
				adaptiveMediaImageHTMLTag = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				options = StringUtil.toLowerCase(RandomTestUtil.randomString());
				parentCartItemId = RandomTestUtil.randomLong();
				productId = RandomTestUtil.randomLong();
				quantity = RandomTestUtil.randomInt();
				sku = StringUtil.toLowerCase(RandomTestUtil.randomString());
				skuId = RandomTestUtil.randomLong();
				subscription = RandomTestUtil.randomBoolean();
				thumbnail = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				valid = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected CartItem randomIrrelevantCartItem() throws Exception {
		CartItem randomIrrelevantCartItem = randomCartItem();

		return randomIrrelevantCartItem;
	}

	protected CartItem randomPatchCartItem() throws Exception {
		return randomCartItem();
	}

	protected CartItemResource cartItemResource;
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
		LogFactoryUtil.getLog(BaseCartItemResourceTestCase.class);

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
		com.liferay.headless.commerce.delivery.cart.resource.v1_0.
			CartItemResource _cartItemResource;

}