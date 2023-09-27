/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.order.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.delivery.order.client.dto.v1_0.PlacedOrder;
import com.liferay.headless.commerce.delivery.order.client.http.HttpInvoker;
import com.liferay.headless.commerce.delivery.order.client.pagination.Page;
import com.liferay.headless.commerce.delivery.order.client.pagination.Pagination;
import com.liferay.headless.commerce.delivery.order.client.resource.v1_0.PlacedOrderResource;
import com.liferay.headless.commerce.delivery.order.client.serdes.v1_0.PlacedOrderSerDes;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
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

import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.lang.time.DateUtils;

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
public abstract class BasePlacedOrderResourceTestCase {

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

		_placedOrderResource.setContextCompany(testCompany);

		PlacedOrderResource.Builder builder = PlacedOrderResource.builder();

		placedOrderResource = builder.authentication(
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

		PlacedOrder placedOrder1 = randomPlacedOrder();

		String json = objectMapper.writeValueAsString(placedOrder1);

		PlacedOrder placedOrder2 = PlacedOrderSerDes.toDTO(json);

		Assert.assertTrue(equals(placedOrder1, placedOrder2));
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

		PlacedOrder placedOrder = randomPlacedOrder();

		String json1 = objectMapper.writeValueAsString(placedOrder);
		String json2 = PlacedOrderSerDes.toJSON(placedOrder);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		PlacedOrder placedOrder = randomPlacedOrder();

		placedOrder.setAccount(regex);
		placedOrder.setAuthor(regex);
		placedOrder.setCouponCode(regex);
		placedOrder.setCurrencyCode(regex);
		placedOrder.setOrderTypeExternalReferenceCode(regex);
		placedOrder.setOrderUUID(regex);
		placedOrder.setPaymentMethod(regex);
		placedOrder.setPaymentMethodLabel(regex);
		placedOrder.setPaymentStatusLabel(regex);
		placedOrder.setPrintedNote(regex);
		placedOrder.setPurchaseOrderNumber(regex);
		placedOrder.setShippingMethod(regex);
		placedOrder.setShippingOption(regex);
		placedOrder.setStatus(regex);

		String json = PlacedOrderSerDes.toJSON(placedOrder);

		Assert.assertFalse(json.contains(regex));

		placedOrder = PlacedOrderSerDes.toDTO(json);

		Assert.assertEquals(regex, placedOrder.getAccount());
		Assert.assertEquals(regex, placedOrder.getAuthor());
		Assert.assertEquals(regex, placedOrder.getCouponCode());
		Assert.assertEquals(regex, placedOrder.getCurrencyCode());
		Assert.assertEquals(
			regex, placedOrder.getOrderTypeExternalReferenceCode());
		Assert.assertEquals(regex, placedOrder.getOrderUUID());
		Assert.assertEquals(regex, placedOrder.getPaymentMethod());
		Assert.assertEquals(regex, placedOrder.getPaymentMethodLabel());
		Assert.assertEquals(regex, placedOrder.getPaymentStatusLabel());
		Assert.assertEquals(regex, placedOrder.getPrintedNote());
		Assert.assertEquals(regex, placedOrder.getPurchaseOrderNumber());
		Assert.assertEquals(regex, placedOrder.getShippingMethod());
		Assert.assertEquals(regex, placedOrder.getShippingOption());
		Assert.assertEquals(regex, placedOrder.getStatus());
	}

	@Test
	public void testGetChannelAccountPlacedOrdersPage() throws Exception {
		Long accountId = testGetChannelAccountPlacedOrdersPage_getAccountId();
		Long irrelevantAccountId =
			testGetChannelAccountPlacedOrdersPage_getIrrelevantAccountId();
		Long channelId = testGetChannelAccountPlacedOrdersPage_getChannelId();
		Long irrelevantChannelId =
			testGetChannelAccountPlacedOrdersPage_getIrrelevantChannelId();

		Page<PlacedOrder> page =
			placedOrderResource.getChannelAccountPlacedOrdersPage(
				accountId, channelId, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if ((irrelevantAccountId != null) && (irrelevantChannelId != null)) {
			PlacedOrder irrelevantPlacedOrder =
				testGetChannelAccountPlacedOrdersPage_addPlacedOrder(
					irrelevantAccountId, irrelevantChannelId,
					randomIrrelevantPlacedOrder());

			page = placedOrderResource.getChannelAccountPlacedOrdersPage(
				irrelevantAccountId, irrelevantChannelId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPlacedOrder),
				(List<PlacedOrder>)page.getItems());
			assertValid(
				page,
				testGetChannelAccountPlacedOrdersPage_getExpectedActions(
					irrelevantAccountId, irrelevantChannelId));
		}

		PlacedOrder placedOrder1 =
			testGetChannelAccountPlacedOrdersPage_addPlacedOrder(
				accountId, channelId, randomPlacedOrder());

		PlacedOrder placedOrder2 =
			testGetChannelAccountPlacedOrdersPage_addPlacedOrder(
				accountId, channelId, randomPlacedOrder());

		page = placedOrderResource.getChannelAccountPlacedOrdersPage(
			accountId, channelId, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(placedOrder1, placedOrder2),
			(List<PlacedOrder>)page.getItems());
		assertValid(
			page,
			testGetChannelAccountPlacedOrdersPage_getExpectedActions(
				accountId, channelId));
	}

	protected Map<String, Map<String, String>>
			testGetChannelAccountPlacedOrdersPage_getExpectedActions(
				Long accountId, Long channelId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetChannelAccountPlacedOrdersPageWithPagination()
		throws Exception {

		Long accountId = testGetChannelAccountPlacedOrdersPage_getAccountId();
		Long channelId = testGetChannelAccountPlacedOrdersPage_getChannelId();

		PlacedOrder placedOrder1 =
			testGetChannelAccountPlacedOrdersPage_addPlacedOrder(
				accountId, channelId, randomPlacedOrder());

		PlacedOrder placedOrder2 =
			testGetChannelAccountPlacedOrdersPage_addPlacedOrder(
				accountId, channelId, randomPlacedOrder());

		PlacedOrder placedOrder3 =
			testGetChannelAccountPlacedOrdersPage_addPlacedOrder(
				accountId, channelId, randomPlacedOrder());

		Page<PlacedOrder> page1 =
			placedOrderResource.getChannelAccountPlacedOrdersPage(
				accountId, channelId, Pagination.of(1, 2));

		List<PlacedOrder> placedOrders1 = (List<PlacedOrder>)page1.getItems();

		Assert.assertEquals(placedOrders1.toString(), 2, placedOrders1.size());

		Page<PlacedOrder> page2 =
			placedOrderResource.getChannelAccountPlacedOrdersPage(
				accountId, channelId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<PlacedOrder> placedOrders2 = (List<PlacedOrder>)page2.getItems();

		Assert.assertEquals(placedOrders2.toString(), 1, placedOrders2.size());

		Page<PlacedOrder> page3 =
			placedOrderResource.getChannelAccountPlacedOrdersPage(
				accountId, channelId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(placedOrder1, placedOrder2, placedOrder3),
			(List<PlacedOrder>)page3.getItems());
	}

	protected PlacedOrder testGetChannelAccountPlacedOrdersPage_addPlacedOrder(
			Long accountId, Long channelId, PlacedOrder placedOrder)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetChannelAccountPlacedOrdersPage_getAccountId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetChannelAccountPlacedOrdersPage_getIrrelevantAccountId()
		throws Exception {

		return null;
	}

	protected Long testGetChannelAccountPlacedOrdersPage_getChannelId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetChannelAccountPlacedOrdersPage_getIrrelevantChannelId()
		throws Exception {

		return null;
	}

	@Test
	public void testGetPlacedOrder() throws Exception {
		PlacedOrder postPlacedOrder = testGetPlacedOrder_addPlacedOrder();

		PlacedOrder getPlacedOrder = placedOrderResource.getPlacedOrder(
			postPlacedOrder.getId());

		assertEquals(postPlacedOrder, getPlacedOrder);
		assertValid(getPlacedOrder);
	}

	protected PlacedOrder testGetPlacedOrder_addPlacedOrder() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetPlacedOrder() throws Exception {
		PlacedOrder placedOrder = testGraphQLGetPlacedOrder_addPlacedOrder();

		Assert.assertTrue(
			equals(
				placedOrder,
				PlacedOrderSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"placedOrder",
								new HashMap<String, Object>() {
									{
										put(
											"placedOrderId",
											placedOrder.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/placedOrder"))));
	}

	@Test
	public void testGraphQLGetPlacedOrderNotFound() throws Exception {
		Long irrelevantPlacedOrderId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"placedOrder",
						new HashMap<String, Object>() {
							{
								put("placedOrderId", irrelevantPlacedOrderId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected PlacedOrder testGraphQLGetPlacedOrder_addPlacedOrder()
		throws Exception {

		return testGraphQLPlacedOrder_addPlacedOrder();
	}

	@Test
	public void testGetPlacedOrderPaymentURL() throws Exception {
		Assert.assertTrue(false);
	}

	protected PlacedOrder testGraphQLPlacedOrder_addPlacedOrder()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		PlacedOrder placedOrder, List<PlacedOrder> placedOrders) {

		boolean contains = false;

		for (PlacedOrder item : placedOrders) {
			if (equals(placedOrder, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			placedOrders + " does not contain " + placedOrder, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		PlacedOrder placedOrder1, PlacedOrder placedOrder2) {

		Assert.assertTrue(
			placedOrder1 + " does not equal " + placedOrder2,
			equals(placedOrder1, placedOrder2));
	}

	protected void assertEquals(
		List<PlacedOrder> placedOrders1, List<PlacedOrder> placedOrders2) {

		Assert.assertEquals(placedOrders1.size(), placedOrders2.size());

		for (int i = 0; i < placedOrders1.size(); i++) {
			PlacedOrder placedOrder1 = placedOrders1.get(i);
			PlacedOrder placedOrder2 = placedOrders2.get(i);

			assertEquals(placedOrder1, placedOrder2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<PlacedOrder> placedOrders1, List<PlacedOrder> placedOrders2) {

		Assert.assertEquals(placedOrders1.size(), placedOrders2.size());

		for (PlacedOrder placedOrder1 : placedOrders1) {
			boolean contains = false;

			for (PlacedOrder placedOrder2 : placedOrders2) {
				if (equals(placedOrder1, placedOrder2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				placedOrders2 + " does not contain " + placedOrder1, contains);
		}
	}

	protected void assertValid(PlacedOrder placedOrder) throws Exception {
		boolean valid = true;

		if (placedOrder.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("account", additionalAssertFieldName)) {
				if (placedOrder.getAccount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (placedOrder.getAccountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("author", additionalAssertFieldName)) {
				if (placedOrder.getAuthor() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("channelId", additionalAssertFieldName)) {
				if (placedOrder.getChannelId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("couponCode", additionalAssertFieldName)) {
				if (placedOrder.getCouponCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (placedOrder.getCreateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("currencyCode", additionalAssertFieldName)) {
				if (placedOrder.getCurrencyCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (placedOrder.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("errorMessages", additionalAssertFieldName)) {
				if (placedOrder.getErrorMessages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"lastPriceUpdateDate", additionalAssertFieldName)) {

				if (placedOrder.getLastPriceUpdateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (placedOrder.getModifiedDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderStatusInfo", additionalAssertFieldName)) {
				if (placedOrder.getOrderStatusInfo() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"orderTypeExternalReferenceCode",
					additionalAssertFieldName)) {

				if (placedOrder.getOrderTypeExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderTypeId", additionalAssertFieldName)) {
				if (placedOrder.getOrderTypeId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderUUID", additionalAssertFieldName)) {
				if (placedOrder.getOrderUUID() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("paymentMethod", additionalAssertFieldName)) {
				if (placedOrder.getPaymentMethod() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"paymentMethodLabel", additionalAssertFieldName)) {

				if (placedOrder.getPaymentMethodLabel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("paymentStatus", additionalAssertFieldName)) {
				if (placedOrder.getPaymentStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"paymentStatusInfo", additionalAssertFieldName)) {

				if (placedOrder.getPaymentStatusInfo() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"paymentStatusLabel", additionalAssertFieldName)) {

				if (placedOrder.getPaymentStatusLabel() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"placedOrderBillingAddress", additionalAssertFieldName)) {

				if (placedOrder.getPlacedOrderBillingAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"placedOrderBillingAddressId", additionalAssertFieldName)) {

				if (placedOrder.getPlacedOrderBillingAddressId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"placedOrderComments", additionalAssertFieldName)) {

				if (placedOrder.getPlacedOrderComments() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("placedOrderItems", additionalAssertFieldName)) {
				if (placedOrder.getPlacedOrderItems() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"placedOrderShippingAddress", additionalAssertFieldName)) {

				if (placedOrder.getPlacedOrderShippingAddress() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"placedOrderShippingAddressId",
					additionalAssertFieldName)) {

				if (placedOrder.getPlacedOrderShippingAddressId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("printedNote", additionalAssertFieldName)) {
				if (placedOrder.getPrintedNote() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"purchaseOrderNumber", additionalAssertFieldName)) {

				if (placedOrder.getPurchaseOrderNumber() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shippingMethod", additionalAssertFieldName)) {
				if (placedOrder.getShippingMethod() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shippingOption", additionalAssertFieldName)) {
				if (placedOrder.getShippingOption() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (placedOrder.getStatus() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("summary", additionalAssertFieldName)) {
				if (placedOrder.getSummary() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("useAsBilling", additionalAssertFieldName)) {
				if (placedOrder.getUseAsBilling() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("valid", additionalAssertFieldName)) {
				if (placedOrder.getValid() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"workflowStatusInfo", additionalAssertFieldName)) {

				if (placedOrder.getWorkflowStatusInfo() == null) {
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

	protected void assertValid(Page<PlacedOrder> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<PlacedOrder> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<PlacedOrder> placedOrders = page.getItems();

		int size = placedOrders.size();

		if ((page.getLastPage() > 0) && (page.getPage() > 0) &&
			(page.getPageSize() > 0) && (page.getTotalCount() > 0) &&
			(size > 0)) {

			valid = true;
		}

		Assert.assertTrue(valid);

		assertValid(page.getActions(), expectedActions);
	}

	protected void assertValid(
		Map<String, Map<String, String>> actions1,
		Map<String, Map<String, String>> actions2) {

		for (String key : actions2.keySet()) {
			Map action = actions1.get(key);

			Assert.assertNotNull(key + " does not contain an action", action);

			Map<String, String> expectedAction = actions2.get(key);

			Assert.assertEquals(
				expectedAction.get("method"), action.get("method"));
			Assert.assertEquals(expectedAction.get("href"), action.get("href"));
		}
	}

	protected String[] getAdditionalAssertFieldNames() {
		return new String[0];
	}

	protected List<GraphQLField> getGraphQLFields() throws Exception {
		List<GraphQLField> graphQLFields = new ArrayList<>();

		for (java.lang.reflect.Field field :
				getDeclaredFields(
					com.liferay.headless.commerce.delivery.order.dto.v1_0.
						PlacedOrder.class)) {

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

	protected boolean equals(
		PlacedOrder placedOrder1, PlacedOrder placedOrder2) {

		if (placedOrder1 == placedOrder2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("account", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getAccount(), placedOrder2.getAccount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getAccountId(),
						placedOrder2.getAccountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("author", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getAuthor(), placedOrder2.getAuthor())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("channelId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getChannelId(),
						placedOrder2.getChannelId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("couponCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getCouponCode(),
						placedOrder2.getCouponCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getCreateDate(),
						placedOrder2.getCreateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("currencyCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getCurrencyCode(),
						placedOrder2.getCurrencyCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!equals(
						(Map)placedOrder1.getCustomFields(),
						(Map)placedOrder2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("errorMessages", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getErrorMessages(),
						placedOrder2.getErrorMessages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getId(), placedOrder2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"lastPriceUpdateDate", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						placedOrder1.getLastPriceUpdateDate(),
						placedOrder2.getLastPriceUpdateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getModifiedDate(),
						placedOrder2.getModifiedDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderStatusInfo", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getOrderStatusInfo(),
						placedOrder2.getOrderStatusInfo())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"orderTypeExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						placedOrder1.getOrderTypeExternalReferenceCode(),
						placedOrder2.getOrderTypeExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderTypeId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getOrderTypeId(),
						placedOrder2.getOrderTypeId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderUUID", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getOrderUUID(),
						placedOrder2.getOrderUUID())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("paymentMethod", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getPaymentMethod(),
						placedOrder2.getPaymentMethod())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"paymentMethodLabel", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						placedOrder1.getPaymentMethodLabel(),
						placedOrder2.getPaymentMethodLabel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("paymentStatus", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getPaymentStatus(),
						placedOrder2.getPaymentStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"paymentStatusInfo", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						placedOrder1.getPaymentStatusInfo(),
						placedOrder2.getPaymentStatusInfo())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"paymentStatusLabel", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						placedOrder1.getPaymentStatusLabel(),
						placedOrder2.getPaymentStatusLabel())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"placedOrderBillingAddress", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						placedOrder1.getPlacedOrderBillingAddress(),
						placedOrder2.getPlacedOrderBillingAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"placedOrderBillingAddressId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						placedOrder1.getPlacedOrderBillingAddressId(),
						placedOrder2.getPlacedOrderBillingAddressId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"placedOrderComments", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						placedOrder1.getPlacedOrderComments(),
						placedOrder2.getPlacedOrderComments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("placedOrderItems", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getPlacedOrderItems(),
						placedOrder2.getPlacedOrderItems())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"placedOrderShippingAddress", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						placedOrder1.getPlacedOrderShippingAddress(),
						placedOrder2.getPlacedOrderShippingAddress())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"placedOrderShippingAddressId",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						placedOrder1.getPlacedOrderShippingAddressId(),
						placedOrder2.getPlacedOrderShippingAddressId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("printedNote", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getPrintedNote(),
						placedOrder2.getPrintedNote())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"purchaseOrderNumber", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						placedOrder1.getPurchaseOrderNumber(),
						placedOrder2.getPurchaseOrderNumber())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shippingMethod", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getShippingMethod(),
						placedOrder2.getShippingMethod())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shippingOption", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getShippingOption(),
						placedOrder2.getShippingOption())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("status", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getStatus(), placedOrder2.getStatus())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("summary", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getSummary(), placedOrder2.getSummary())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("useAsBilling", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getUseAsBilling(),
						placedOrder2.getUseAsBilling())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("valid", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrder1.getValid(), placedOrder2.getValid())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"workflowStatusInfo", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						placedOrder1.getWorkflowStatusInfo(),
						placedOrder2.getWorkflowStatusInfo())) {

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

		return TransformUtil.transform(
			ReflectionUtil.getDeclaredFields(clazz),
			field -> {
				if (field.isSynthetic()) {
					return null;
				}

				return field;
			},
			java.lang.reflect.Field.class);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_placedOrderResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_placedOrderResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		if (entityModel == null) {
			return Collections.emptyList();
		}

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		return TransformUtil.transform(
			getEntityFields(),
			entityField -> {
				if (!Objects.equals(entityField.getType(), type) ||
					ArrayUtil.contains(
						getIgnoredEntityFieldNames(), entityField.getName())) {

					return null;
				}

				return entityField;
			});
	}

	protected String getFilterString(
		EntityField entityField, String operator, PlacedOrder placedOrder) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("account")) {
			Object object = placedOrder.getAccount();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("accountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("author")) {
			Object object = placedOrder.getAuthor();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("channelId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("couponCode")) {
			Object object = placedOrder.getCouponCode();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

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
						DateUtils.addSeconds(placedOrder.getCreateDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(placedOrder.getCreateDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(placedOrder.getCreateDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("currencyCode")) {
			Object object = placedOrder.getCurrencyCode();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

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
							placedOrder.getLastPriceUpdateDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							placedOrder.getLastPriceUpdateDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(placedOrder.getLastPriceUpdateDate()));
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
						DateUtils.addSeconds(
							placedOrder.getModifiedDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							placedOrder.getModifiedDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(placedOrder.getModifiedDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("orderStatusInfo")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderTypeExternalReferenceCode")) {
			Object object = placedOrder.getOrderTypeExternalReferenceCode();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("orderTypeId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderUUID")) {
			Object object = placedOrder.getOrderUUID();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("paymentMethod")) {
			Object object = placedOrder.getPaymentMethod();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("paymentMethodLabel")) {
			Object object = placedOrder.getPaymentMethodLabel();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("paymentStatus")) {
			sb.append(String.valueOf(placedOrder.getPaymentStatus()));

			return sb.toString();
		}

		if (entityFieldName.equals("paymentStatusInfo")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("paymentStatusLabel")) {
			Object object = placedOrder.getPaymentStatusLabel();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("placedOrderBillingAddress")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("placedOrderBillingAddressId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("placedOrderComments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("placedOrderItems")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("placedOrderShippingAddress")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("placedOrderShippingAddressId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("printedNote")) {
			Object object = placedOrder.getPrintedNote();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("purchaseOrderNumber")) {
			Object object = placedOrder.getPurchaseOrderNumber();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("shippingMethod")) {
			Object object = placedOrder.getShippingMethod();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("shippingOption")) {
			Object object = placedOrder.getShippingOption();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

			return sb.toString();
		}

		if (entityFieldName.equals("status")) {
			Object object = placedOrder.getStatus();

			String value = String.valueOf(object);

			if (operator.equals("contains")) {
				sb = new StringBundler();

				sb.append("contains(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 2)) {
					sb.append(value.substring(1, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else if (operator.equals("startswith")) {
				sb = new StringBundler();

				sb.append("startswith(");
				sb.append(entityFieldName);
				sb.append(",'");

				if ((object != null) && (value.length() > 1)) {
					sb.append(value.substring(0, value.length() - 1));
				}
				else {
					sb.append(value);
				}

				sb.append("')");
			}
			else {
				sb.append("'");
				sb.append(value);
				sb.append("'");
			}

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

	protected PlacedOrder randomPlacedOrder() throws Exception {
		return new PlacedOrder() {
			{
				account = StringUtil.toLowerCase(RandomTestUtil.randomString());
				accountId = RandomTestUtil.randomLong();
				author = StringUtil.toLowerCase(RandomTestUtil.randomString());
				channelId = RandomTestUtil.randomLong();
				couponCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				createDate = RandomTestUtil.nextDate();
				currencyCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				lastPriceUpdateDate = RandomTestUtil.nextDate();
				modifiedDate = RandomTestUtil.nextDate();
				orderTypeExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				orderTypeId = RandomTestUtil.randomLong();
				orderUUID = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				paymentMethod = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				paymentMethodLabel = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				paymentStatus = RandomTestUtil.randomInt();
				paymentStatusLabel = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				placedOrderBillingAddressId = RandomTestUtil.randomLong();
				placedOrderShippingAddressId = RandomTestUtil.randomLong();
				printedNote = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				purchaseOrderNumber = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
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

	protected PlacedOrder randomIrrelevantPlacedOrder() throws Exception {
		PlacedOrder randomIrrelevantPlacedOrder = randomPlacedOrder();

		return randomIrrelevantPlacedOrder;
	}

	protected PlacedOrder randomPatchPlacedOrder() throws Exception {
		return randomPlacedOrder();
	}

	protected PlacedOrderResource placedOrderResource;
	protected Group irrelevantGroup;
	protected Company testCompany;
	protected Group testGroup;

	protected static class BeanTestUtil {

		public static void copyProperties(Object source, Object target)
			throws Exception {

			Class<?> sourceClass = _getSuperClass(source.getClass());

			Class<?> targetClass = target.getClass();

			for (java.lang.reflect.Field field :
					sourceClass.getDeclaredFields()) {

				if (field.isSynthetic()) {
					continue;
				}

				Method getMethod = _getMethod(
					sourceClass, field.getName(), "get");

				Method setMethod = _getMethod(
					targetClass, field.getName(), "set",
					getMethod.getReturnType());

				setMethod.invoke(target, getMethod.invoke(source));
			}
		}

		public static boolean hasProperty(Object bean, String name) {
			Method setMethod = _getMethod(
				bean.getClass(), "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod != null) {
				return true;
			}

			return false;
		}

		public static void setProperty(Object bean, String name, Object value)
			throws Exception {

			Class<?> clazz = bean.getClass();

			Method setMethod = _getMethod(
				clazz, "set" + StringUtil.upperCaseFirstLetter(name));

			if (setMethod == null) {
				throw new NoSuchMethodException();
			}

			Class<?>[] parameterTypes = setMethod.getParameterTypes();

			setMethod.invoke(bean, _translateValue(parameterTypes[0], value));
		}

		private static Method _getMethod(Class<?> clazz, String name) {
			for (Method method : clazz.getMethods()) {
				if (name.equals(method.getName()) &&
					(method.getParameterCount() == 1) &&
					_parameterTypes.contains(method.getParameterTypes()[0])) {

					return method;
				}
			}

			return null;
		}

		private static Method _getMethod(
				Class<?> clazz, String fieldName, String prefix,
				Class<?>... parameterTypes)
			throws Exception {

			return clazz.getMethod(
				prefix + StringUtil.upperCaseFirstLetter(fieldName),
				parameterTypes);
		}

		private static Class<?> _getSuperClass(Class<?> clazz) {
			Class<?> superClass = clazz.getSuperclass();

			if ((superClass == null) || (superClass == Object.class)) {
				return clazz;
			}

			return superClass;
		}

		private static Object _translateValue(
			Class<?> parameterType, Object value) {

			if ((value instanceof Integer) &&
				parameterType.equals(Long.class)) {

				Integer intValue = (Integer)value;

				return intValue.longValue();
			}

			return value;
		}

		private static final Set<Class<?>> _parameterTypes = new HashSet<>(
			Arrays.asList(
				Boolean.class, Date.class, Double.class, Integer.class,
				Long.class, Map.class, String.class));

	}

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
		LogFactoryUtil.getLog(BasePlacedOrderResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.commerce.delivery.order.resource.v1_0.
		PlacedOrderResource _placedOrderResource;

}