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

import com.liferay.headless.commerce.delivery.order.client.dto.v1_0.PlacedOrderItem;
import com.liferay.headless.commerce.delivery.order.client.http.HttpInvoker;
import com.liferay.headless.commerce.delivery.order.client.pagination.Page;
import com.liferay.headless.commerce.delivery.order.client.pagination.Pagination;
import com.liferay.headless.commerce.delivery.order.client.resource.v1_0.PlacedOrderItemResource;
import com.liferay.headless.commerce.delivery.order.client.serdes.v1_0.PlacedOrderItemSerDes;
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
public abstract class BasePlacedOrderItemResourceTestCase {

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

		_placedOrderItemResource.setContextCompany(testCompany);

		PlacedOrderItemResource.Builder builder =
			PlacedOrderItemResource.builder();

		placedOrderItemResource = builder.authentication(
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

		PlacedOrderItem placedOrderItem1 = randomPlacedOrderItem();

		String json = objectMapper.writeValueAsString(placedOrderItem1);

		PlacedOrderItem placedOrderItem2 = PlacedOrderItemSerDes.toDTO(json);

		Assert.assertTrue(equals(placedOrderItem1, placedOrderItem2));
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

		PlacedOrderItem placedOrderItem = randomPlacedOrderItem();

		String json1 = objectMapper.writeValueAsString(placedOrderItem);
		String json2 = PlacedOrderItemSerDes.toJSON(placedOrderItem);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		PlacedOrderItem placedOrderItem = randomPlacedOrderItem();

		placedOrderItem.setAdaptiveMediaImageHTMLTag(regex);
		placedOrderItem.setName(regex);
		placedOrderItem.setOptions(regex);
		placedOrderItem.setReplacedSku(regex);
		placedOrderItem.setSku(regex);
		placedOrderItem.setThumbnail(regex);
		placedOrderItem.setUnitOfMeasureKey(regex);

		String json = PlacedOrderItemSerDes.toJSON(placedOrderItem);

		Assert.assertFalse(json.contains(regex));

		placedOrderItem = PlacedOrderItemSerDes.toDTO(json);

		Assert.assertEquals(
			regex, placedOrderItem.getAdaptiveMediaImageHTMLTag());
		Assert.assertEquals(regex, placedOrderItem.getName());
		Assert.assertEquals(regex, placedOrderItem.getOptions());
		Assert.assertEquals(regex, placedOrderItem.getReplacedSku());
		Assert.assertEquals(regex, placedOrderItem.getSku());
		Assert.assertEquals(regex, placedOrderItem.getThumbnail());
		Assert.assertEquals(regex, placedOrderItem.getUnitOfMeasureKey());
	}

	@Test
	public void testGetPlacedOrderItem() throws Exception {
		PlacedOrderItem postPlacedOrderItem =
			testGetPlacedOrderItem_addPlacedOrderItem();

		PlacedOrderItem getPlacedOrderItem =
			placedOrderItemResource.getPlacedOrderItem(
				postPlacedOrderItem.getId());

		assertEquals(postPlacedOrderItem, getPlacedOrderItem);
		assertValid(getPlacedOrderItem);
	}

	protected PlacedOrderItem testGetPlacedOrderItem_addPlacedOrderItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetPlacedOrderItem() throws Exception {
		PlacedOrderItem placedOrderItem =
			testGraphQLGetPlacedOrderItem_addPlacedOrderItem();

		Assert.assertTrue(
			equals(
				placedOrderItem,
				PlacedOrderItemSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"placedOrderItem",
								new HashMap<String, Object>() {
									{
										put(
											"placedOrderItemId",
											placedOrderItem.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/placedOrderItem"))));
	}

	@Test
	public void testGraphQLGetPlacedOrderItemNotFound() throws Exception {
		Long irrelevantPlacedOrderItemId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"placedOrderItem",
						new HashMap<String, Object>() {
							{
								put(
									"placedOrderItemId",
									irrelevantPlacedOrderItemId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected PlacedOrderItem testGraphQLGetPlacedOrderItem_addPlacedOrderItem()
		throws Exception {

		return testGraphQLPlacedOrderItem_addPlacedOrderItem();
	}

	@Test
	public void testGetPlacedOrderPlacedOrderItemsPage() throws Exception {
		Long placedOrderId =
			testGetPlacedOrderPlacedOrderItemsPage_getPlacedOrderId();
		Long irrelevantPlacedOrderId =
			testGetPlacedOrderPlacedOrderItemsPage_getIrrelevantPlacedOrderId();

		Page<PlacedOrderItem> page =
			placedOrderItemResource.getPlacedOrderPlacedOrderItemsPage(
				placedOrderId, null, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantPlacedOrderId != null) {
			PlacedOrderItem irrelevantPlacedOrderItem =
				testGetPlacedOrderPlacedOrderItemsPage_addPlacedOrderItem(
					irrelevantPlacedOrderId, randomIrrelevantPlacedOrderItem());

			page = placedOrderItemResource.getPlacedOrderPlacedOrderItemsPage(
				irrelevantPlacedOrderId, null, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPlacedOrderItem),
				(List<PlacedOrderItem>)page.getItems());
			assertValid(
				page,
				testGetPlacedOrderPlacedOrderItemsPage_getExpectedActions(
					irrelevantPlacedOrderId));
		}

		PlacedOrderItem placedOrderItem1 =
			testGetPlacedOrderPlacedOrderItemsPage_addPlacedOrderItem(
				placedOrderId, randomPlacedOrderItem());

		PlacedOrderItem placedOrderItem2 =
			testGetPlacedOrderPlacedOrderItemsPage_addPlacedOrderItem(
				placedOrderId, randomPlacedOrderItem());

		page = placedOrderItemResource.getPlacedOrderPlacedOrderItemsPage(
			placedOrderId, null, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(placedOrderItem1, placedOrderItem2),
			(List<PlacedOrderItem>)page.getItems());
		assertValid(
			page,
			testGetPlacedOrderPlacedOrderItemsPage_getExpectedActions(
				placedOrderId));
	}

	protected Map<String, Map<String, String>>
			testGetPlacedOrderPlacedOrderItemsPage_getExpectedActions(
				Long placedOrderId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetPlacedOrderPlacedOrderItemsPageWithPagination()
		throws Exception {

		Long placedOrderId =
			testGetPlacedOrderPlacedOrderItemsPage_getPlacedOrderId();

		PlacedOrderItem placedOrderItem1 =
			testGetPlacedOrderPlacedOrderItemsPage_addPlacedOrderItem(
				placedOrderId, randomPlacedOrderItem());

		PlacedOrderItem placedOrderItem2 =
			testGetPlacedOrderPlacedOrderItemsPage_addPlacedOrderItem(
				placedOrderId, randomPlacedOrderItem());

		PlacedOrderItem placedOrderItem3 =
			testGetPlacedOrderPlacedOrderItemsPage_addPlacedOrderItem(
				placedOrderId, randomPlacedOrderItem());

		Page<PlacedOrderItem> page1 =
			placedOrderItemResource.getPlacedOrderPlacedOrderItemsPage(
				placedOrderId, null, Pagination.of(1, 2));

		List<PlacedOrderItem> placedOrderItems1 =
			(List<PlacedOrderItem>)page1.getItems();

		Assert.assertEquals(
			placedOrderItems1.toString(), 2, placedOrderItems1.size());

		Page<PlacedOrderItem> page2 =
			placedOrderItemResource.getPlacedOrderPlacedOrderItemsPage(
				placedOrderId, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<PlacedOrderItem> placedOrderItems2 =
			(List<PlacedOrderItem>)page2.getItems();

		Assert.assertEquals(
			placedOrderItems2.toString(), 1, placedOrderItems2.size());

		Page<PlacedOrderItem> page3 =
			placedOrderItemResource.getPlacedOrderPlacedOrderItemsPage(
				placedOrderId, null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(placedOrderItem1, placedOrderItem2, placedOrderItem3),
			(List<PlacedOrderItem>)page3.getItems());
	}

	protected PlacedOrderItem
			testGetPlacedOrderPlacedOrderItemsPage_addPlacedOrderItem(
				Long placedOrderId, PlacedOrderItem placedOrderItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPlacedOrderPlacedOrderItemsPage_getPlacedOrderId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetPlacedOrderPlacedOrderItemsPage_getIrrelevantPlacedOrderId()
		throws Exception {

		return null;
	}

	protected PlacedOrderItem testGraphQLPlacedOrderItem_addPlacedOrderItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		PlacedOrderItem placedOrderItem,
		List<PlacedOrderItem> placedOrderItems) {

		boolean contains = false;

		for (PlacedOrderItem item : placedOrderItems) {
			if (equals(placedOrderItem, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			placedOrderItems + " does not contain " + placedOrderItem,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		PlacedOrderItem placedOrderItem1, PlacedOrderItem placedOrderItem2) {

		Assert.assertTrue(
			placedOrderItem1 + " does not equal " + placedOrderItem2,
			equals(placedOrderItem1, placedOrderItem2));
	}

	protected void assertEquals(
		List<PlacedOrderItem> placedOrderItems1,
		List<PlacedOrderItem> placedOrderItems2) {

		Assert.assertEquals(placedOrderItems1.size(), placedOrderItems2.size());

		for (int i = 0; i < placedOrderItems1.size(); i++) {
			PlacedOrderItem placedOrderItem1 = placedOrderItems1.get(i);
			PlacedOrderItem placedOrderItem2 = placedOrderItems2.get(i);

			assertEquals(placedOrderItem1, placedOrderItem2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<PlacedOrderItem> placedOrderItems1,
		List<PlacedOrderItem> placedOrderItems2) {

		Assert.assertEquals(placedOrderItems1.size(), placedOrderItems2.size());

		for (PlacedOrderItem placedOrderItem1 : placedOrderItems1) {
			boolean contains = false;

			for (PlacedOrderItem placedOrderItem2 : placedOrderItems2) {
				if (equals(placedOrderItem1, placedOrderItem2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				placedOrderItems2 + " does not contain " + placedOrderItem1,
				contains);
		}
	}

	protected void assertValid(PlacedOrderItem placedOrderItem)
		throws Exception {

		boolean valid = true;

		if (placedOrderItem.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"adaptiveMediaImageHTMLTag", additionalAssertFieldName)) {

				if (placedOrderItem.getAdaptiveMediaImageHTMLTag() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (placedOrderItem.getCustomFields() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("errorMessages", additionalAssertFieldName)) {
				if (placedOrderItem.getErrorMessages() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (placedOrderItem.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("options", additionalAssertFieldName)) {
				if (placedOrderItem.getOptions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"parentOrderItemId", additionalAssertFieldName)) {

				if (placedOrderItem.getParentOrderItemId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"placedOrderItemShipments", additionalAssertFieldName)) {

				if (placedOrderItem.getPlacedOrderItemShipments() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("placedOrderItems", additionalAssertFieldName)) {
				if (placedOrderItem.getPlacedOrderItems() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("price", additionalAssertFieldName)) {
				if (placedOrderItem.getPrice() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("productId", additionalAssertFieldName)) {
				if (placedOrderItem.getProductId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("productURLs", additionalAssertFieldName)) {
				if (placedOrderItem.getProductURLs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("quantity", additionalAssertFieldName)) {
				if (placedOrderItem.getQuantity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("replacedSku", additionalAssertFieldName)) {
				if (placedOrderItem.getReplacedSku() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("settings", additionalAssertFieldName)) {
				if (placedOrderItem.getSettings() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (placedOrderItem.getSku() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("skuId", additionalAssertFieldName)) {
				if (placedOrderItem.getSkuId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("subscription", additionalAssertFieldName)) {
				if (placedOrderItem.getSubscription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("thumbnail", additionalAssertFieldName)) {
				if (placedOrderItem.getThumbnail() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("unitOfMeasureKey", additionalAssertFieldName)) {
				if (placedOrderItem.getUnitOfMeasureKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("valid", additionalAssertFieldName)) {
				if (placedOrderItem.getValid() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("virtualItemURLs", additionalAssertFieldName)) {
				if (placedOrderItem.getVirtualItemURLs() == null) {
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

	protected void assertValid(Page<PlacedOrderItem> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<PlacedOrderItem> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<PlacedOrderItem> placedOrderItems =
			page.getItems();

		int size = placedOrderItems.size();

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
						PlacedOrderItem.class)) {

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
		PlacedOrderItem placedOrderItem1, PlacedOrderItem placedOrderItem2) {

		if (placedOrderItem1 == placedOrderItem2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"adaptiveMediaImageHTMLTag", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						placedOrderItem1.getAdaptiveMediaImageHTMLTag(),
						placedOrderItem2.getAdaptiveMediaImageHTMLTag())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("customFields", additionalAssertFieldName)) {
				if (!equals(
						(Map)placedOrderItem1.getCustomFields(),
						(Map)placedOrderItem2.getCustomFields())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("errorMessages", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getErrorMessages(),
						placedOrderItem2.getErrorMessages())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getId(), placedOrderItem2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getName(),
						placedOrderItem2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("options", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getOptions(),
						placedOrderItem2.getOptions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"parentOrderItemId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						placedOrderItem1.getParentOrderItemId(),
						placedOrderItem2.getParentOrderItemId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"placedOrderItemShipments", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						placedOrderItem1.getPlacedOrderItemShipments(),
						placedOrderItem2.getPlacedOrderItemShipments())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("placedOrderItems", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getPlacedOrderItems(),
						placedOrderItem2.getPlacedOrderItems())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("price", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getPrice(),
						placedOrderItem2.getPrice())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getProductId(),
						placedOrderItem2.getProductId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("productURLs", additionalAssertFieldName)) {
				if (!equals(
						(Map)placedOrderItem1.getProductURLs(),
						(Map)placedOrderItem2.getProductURLs())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("quantity", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getQuantity(),
						placedOrderItem2.getQuantity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("replacedSku", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getReplacedSku(),
						placedOrderItem2.getReplacedSku())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("settings", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getSettings(),
						placedOrderItem2.getSettings())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getSku(), placedOrderItem2.getSku())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("skuId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getSkuId(),
						placedOrderItem2.getSkuId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("subscription", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getSubscription(),
						placedOrderItem2.getSubscription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("thumbnail", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getThumbnail(),
						placedOrderItem2.getThumbnail())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("unitOfMeasureKey", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getUnitOfMeasureKey(),
						placedOrderItem2.getUnitOfMeasureKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("valid", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getValid(),
						placedOrderItem2.getValid())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("virtualItemURLs", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						placedOrderItem1.getVirtualItemURLs(),
						placedOrderItem2.getVirtualItemURLs())) {

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

		if (!(_placedOrderItemResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_placedOrderItemResource;

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
		EntityField entityField, String operator,
		PlacedOrderItem placedOrderItem) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("adaptiveMediaImageHTMLTag")) {
			Object object = placedOrderItem.getAdaptiveMediaImageHTMLTag();

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

		if (entityFieldName.equals("name")) {
			Object object = placedOrderItem.getName();

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

		if (entityFieldName.equals("options")) {
			Object object = placedOrderItem.getOptions();

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

		if (entityFieldName.equals("parentOrderItemId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("placedOrderItemShipments")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("placedOrderItems")) {
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

		if (entityFieldName.equals("replacedSku")) {
			Object object = placedOrderItem.getReplacedSku();

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

		if (entityFieldName.equals("settings")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sku")) {
			Object object = placedOrderItem.getSku();

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

		if (entityFieldName.equals("skuId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("subscription")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("thumbnail")) {
			Object object = placedOrderItem.getThumbnail();

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

		if (entityFieldName.equals("unitOfMeasureKey")) {
			Object object = placedOrderItem.getUnitOfMeasureKey();

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

		if (entityFieldName.equals("valid")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("virtualItemURLs")) {
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

	protected PlacedOrderItem randomPlacedOrderItem() throws Exception {
		return new PlacedOrderItem() {
			{
				adaptiveMediaImageHTMLTag = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				options = StringUtil.toLowerCase(RandomTestUtil.randomString());
				parentOrderItemId = RandomTestUtil.randomLong();
				productId = RandomTestUtil.randomLong();
				replacedSku = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				sku = StringUtil.toLowerCase(RandomTestUtil.randomString());
				skuId = RandomTestUtil.randomLong();
				subscription = RandomTestUtil.randomBoolean();
				thumbnail = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				unitOfMeasureKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				valid = RandomTestUtil.randomBoolean();
			}
		};
	}

	protected PlacedOrderItem randomIrrelevantPlacedOrderItem()
		throws Exception {

		PlacedOrderItem randomIrrelevantPlacedOrderItem =
			randomPlacedOrderItem();

		return randomIrrelevantPlacedOrderItem;
	}

	protected PlacedOrderItem randomPatchPlacedOrderItem() throws Exception {
		return randomPlacedOrderItem();
	}

	protected PlacedOrderItemResource placedOrderItemResource;
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
		LogFactoryUtil.getLog(BasePlacedOrderItemResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.commerce.delivery.order.resource.v1_0.
		PlacedOrderItemResource _placedOrderItemResource;

}