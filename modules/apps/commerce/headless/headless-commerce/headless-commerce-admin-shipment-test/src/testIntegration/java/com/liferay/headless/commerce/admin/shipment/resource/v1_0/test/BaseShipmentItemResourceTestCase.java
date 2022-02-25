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

package com.liferay.headless.commerce.admin.shipment.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.shipment.client.dto.v1_0.ShipmentItem;
import com.liferay.headless.commerce.admin.shipment.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.shipment.client.pagination.Page;
import com.liferay.headless.commerce.admin.shipment.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.shipment.client.resource.v1_0.ShipmentItemResource;
import com.liferay.headless.commerce.admin.shipment.client.serdes.v1_0.ShipmentItemSerDes;
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
 * @author Andrea Sbarra
 * @generated
 */
@Generated("")
public abstract class BaseShipmentItemResourceTestCase {

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

		_shipmentItemResource.setContextCompany(testCompany);

		ShipmentItemResource.Builder builder = ShipmentItemResource.builder();

		shipmentItemResource = builder.authentication(
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

		ShipmentItem shipmentItem1 = randomShipmentItem();

		String json = objectMapper.writeValueAsString(shipmentItem1);

		ShipmentItem shipmentItem2 = ShipmentItemSerDes.toDTO(json);

		Assert.assertTrue(equals(shipmentItem1, shipmentItem2));
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

		ShipmentItem shipmentItem = randomShipmentItem();

		String json1 = objectMapper.writeValueAsString(shipmentItem);
		String json2 = ShipmentItemSerDes.toJSON(shipmentItem);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ShipmentItem shipmentItem = randomShipmentItem();

		shipmentItem.setExternalReferenceCode(regex);
		shipmentItem.setShipmentExternalReferenceCode(regex);
		shipmentItem.setUserName(regex);

		String json = ShipmentItemSerDes.toJSON(shipmentItem);

		Assert.assertFalse(json.contains(regex));

		shipmentItem = ShipmentItemSerDes.toDTO(json);

		Assert.assertEquals(regex, shipmentItem.getExternalReferenceCode());
		Assert.assertEquals(
			regex, shipmentItem.getShipmentExternalReferenceCode());
		Assert.assertEquals(regex, shipmentItem.getUserName());
	}

	@Test
	public void testDeleteShipmentItemByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ShipmentItem shipmentItem =
			testDeleteShipmentItemByExternalReferenceCode_addShipmentItem();

		assertHttpResponseStatusCode(
			204,
			shipmentItemResource.
				deleteShipmentItemByExternalReferenceCodeHttpResponse(
					shipmentItem.getExternalReferenceCode()));
	}

	protected ShipmentItem
			testDeleteShipmentItemByExternalReferenceCode_addShipmentItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetShipmentByExternalReferenceCodeItem() throws Exception {
		ShipmentItem postShipmentItem =
			testGetShipmentByExternalReferenceCodeItem_addShipmentItem();

		ShipmentItem getShipmentItem =
			shipmentItemResource.getShipmentByExternalReferenceCodeItem(
				postShipmentItem.getExternalReferenceCode());

		assertEquals(postShipmentItem, getShipmentItem);
		assertValid(getShipmentItem);
	}

	protected ShipmentItem
			testGetShipmentByExternalReferenceCodeItem_addShipmentItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetShipmentByExternalReferenceCodeItem()
		throws Exception {

		ShipmentItem shipmentItem = testGraphQLShipmentItem_addShipmentItem();

		Assert.assertTrue(
			equals(
				shipmentItem,
				ShipmentItemSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"shipmentByExternalReferenceCodeItem",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												shipmentItem.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/shipmentByExternalReferenceCodeItem"))));
	}

	@Test
	public void testGraphQLGetShipmentByExternalReferenceCodeItemNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"shipmentByExternalReferenceCodeItem",
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
	public void testPatchShipmentItemByExternalReferenceCode()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteShipmentItem() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		ShipmentItem shipmentItem = testDeleteShipmentItem_addShipmentItem();

		assertHttpResponseStatusCode(
			204,
			shipmentItemResource.deleteShipmentItemHttpResponse(
				shipmentItem.getId()));

		assertHttpResponseStatusCode(
			404,
			shipmentItemResource.getShipmentItemHttpResponse(
				shipmentItem.getId()));

		assertHttpResponseStatusCode(
			404, shipmentItemResource.getShipmentItemHttpResponse(0L));
	}

	protected ShipmentItem testDeleteShipmentItem_addShipmentItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteShipmentItem() throws Exception {
		ShipmentItem shipmentItem = testGraphQLShipmentItem_addShipmentItem();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteShipmentItem",
						new HashMap<String, Object>() {
							{
								put("shipmentItemId", shipmentItem.getId());
							}
						})),
				"JSONObject/data", "Object/deleteShipmentItem"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"shipmentItem",
					new HashMap<String, Object>() {
						{
							put("shipmentItemId", shipmentItem.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetShipmentItem() throws Exception {
		ShipmentItem postShipmentItem = testGetShipmentItem_addShipmentItem();

		ShipmentItem getShipmentItem = shipmentItemResource.getShipmentItem(
			postShipmentItem.getId());

		assertEquals(postShipmentItem, getShipmentItem);
		assertValid(getShipmentItem);
	}

	protected ShipmentItem testGetShipmentItem_addShipmentItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetShipmentItem() throws Exception {
		ShipmentItem shipmentItem = testGraphQLShipmentItem_addShipmentItem();

		Assert.assertTrue(
			equals(
				shipmentItem,
				ShipmentItemSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"shipmentItem",
								new HashMap<String, Object>() {
									{
										put(
											"shipmentItemId",
											shipmentItem.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/shipmentItem"))));
	}

	@Test
	public void testGraphQLGetShipmentItemNotFound() throws Exception {
		Long irrelevantShipmentItemId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"shipmentItem",
						new HashMap<String, Object>() {
							{
								put("shipmentItemId", irrelevantShipmentItemId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	@Test
	public void testPatchShipmentItem() throws Exception {
		ShipmentItem postShipmentItem = testPatchShipmentItem_addShipmentItem();

		ShipmentItem randomPatchShipmentItem = randomPatchShipmentItem();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ShipmentItem patchShipmentItem = shipmentItemResource.patchShipmentItem(
			postShipmentItem.getId(), randomPatchShipmentItem);

		ShipmentItem expectedPatchShipmentItem = postShipmentItem.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchShipmentItem, randomPatchShipmentItem);

		ShipmentItem getShipmentItem = shipmentItemResource.getShipmentItem(
			patchShipmentItem.getId());

		assertEquals(expectedPatchShipmentItem, getShipmentItem);
		assertValid(getShipmentItem);
	}

	protected ShipmentItem testPatchShipmentItem_addShipmentItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetShipmentByExternalReferenceCodeItemsPage()
		throws Exception {

		String externalReferenceCode =
			testGetShipmentByExternalReferenceCodeItemsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetShipmentByExternalReferenceCodeItemsPage_getIrrelevantExternalReferenceCode();

		Page<ShipmentItem> page =
			shipmentItemResource.getShipmentByExternalReferenceCodeItemsPage(
				externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantExternalReferenceCode != null) {
			ShipmentItem irrelevantShipmentItem =
				testGetShipmentByExternalReferenceCodeItemsPage_addShipmentItem(
					irrelevantExternalReferenceCode,
					randomIrrelevantShipmentItem());

			page =
				shipmentItemResource.
					getShipmentByExternalReferenceCodeItemsPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantShipmentItem),
				(List<ShipmentItem>)page.getItems());
			assertValid(page);
		}

		ShipmentItem shipmentItem1 =
			testGetShipmentByExternalReferenceCodeItemsPage_addShipmentItem(
				externalReferenceCode, randomShipmentItem());

		ShipmentItem shipmentItem2 =
			testGetShipmentByExternalReferenceCodeItemsPage_addShipmentItem(
				externalReferenceCode, randomShipmentItem());

		page = shipmentItemResource.getShipmentByExternalReferenceCodeItemsPage(
			externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(shipmentItem1, shipmentItem2),
			(List<ShipmentItem>)page.getItems());
		assertValid(page);

		shipmentItemResource.deleteShipmentItem(shipmentItem1.getId());

		shipmentItemResource.deleteShipmentItem(shipmentItem2.getId());
	}

	@Test
	public void testGetShipmentByExternalReferenceCodeItemsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetShipmentByExternalReferenceCodeItemsPage_getExternalReferenceCode();

		ShipmentItem shipmentItem1 =
			testGetShipmentByExternalReferenceCodeItemsPage_addShipmentItem(
				externalReferenceCode, randomShipmentItem());

		ShipmentItem shipmentItem2 =
			testGetShipmentByExternalReferenceCodeItemsPage_addShipmentItem(
				externalReferenceCode, randomShipmentItem());

		ShipmentItem shipmentItem3 =
			testGetShipmentByExternalReferenceCodeItemsPage_addShipmentItem(
				externalReferenceCode, randomShipmentItem());

		Page<ShipmentItem> page1 =
			shipmentItemResource.getShipmentByExternalReferenceCodeItemsPage(
				externalReferenceCode, Pagination.of(1, 2));

		List<ShipmentItem> shipmentItems1 =
			(List<ShipmentItem>)page1.getItems();

		Assert.assertEquals(
			shipmentItems1.toString(), 2, shipmentItems1.size());

		Page<ShipmentItem> page2 =
			shipmentItemResource.getShipmentByExternalReferenceCodeItemsPage(
				externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ShipmentItem> shipmentItems2 =
			(List<ShipmentItem>)page2.getItems();

		Assert.assertEquals(
			shipmentItems2.toString(), 1, shipmentItems2.size());

		Page<ShipmentItem> page3 =
			shipmentItemResource.getShipmentByExternalReferenceCodeItemsPage(
				externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(shipmentItem1, shipmentItem2, shipmentItem3),
			(List<ShipmentItem>)page3.getItems());
	}

	protected ShipmentItem
			testGetShipmentByExternalReferenceCodeItemsPage_addShipmentItem(
				String externalReferenceCode, ShipmentItem shipmentItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetShipmentByExternalReferenceCodeItemsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetShipmentByExternalReferenceCodeItemsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPutShipmentByExternalReferenceCodeItem() throws Exception {
		ShipmentItem postShipmentItem =
			testPutShipmentByExternalReferenceCodeItem_addShipmentItem();

		ShipmentItem randomShipmentItem = randomShipmentItem();

		ShipmentItem putShipmentItem =
			shipmentItemResource.putShipmentByExternalReferenceCodeItem(
				postShipmentItem.getExternalReferenceCode(),
				randomShipmentItem);

		assertEquals(randomShipmentItem, putShipmentItem);
		assertValid(putShipmentItem);

		ShipmentItem getShipmentItem =
			shipmentItemResource.getShipmentByExternalReferenceCodeItem(
				putShipmentItem.getExternalReferenceCode());

		assertEquals(randomShipmentItem, getShipmentItem);
		assertValid(getShipmentItem);
	}

	protected ShipmentItem
			testPutShipmentByExternalReferenceCodeItem_addShipmentItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetShipmentItemsPage() throws Exception {
		Long shipmentId = testGetShipmentItemsPage_getShipmentId();
		Long irrelevantShipmentId =
			testGetShipmentItemsPage_getIrrelevantShipmentId();

		Page<ShipmentItem> page = shipmentItemResource.getShipmentItemsPage(
			shipmentId, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantShipmentId != null) {
			ShipmentItem irrelevantShipmentItem =
				testGetShipmentItemsPage_addShipmentItem(
					irrelevantShipmentId, randomIrrelevantShipmentItem());

			page = shipmentItemResource.getShipmentItemsPage(
				irrelevantShipmentId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantShipmentItem),
				(List<ShipmentItem>)page.getItems());
			assertValid(page);
		}

		ShipmentItem shipmentItem1 = testGetShipmentItemsPage_addShipmentItem(
			shipmentId, randomShipmentItem());

		ShipmentItem shipmentItem2 = testGetShipmentItemsPage_addShipmentItem(
			shipmentId, randomShipmentItem());

		page = shipmentItemResource.getShipmentItemsPage(
			shipmentId, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(shipmentItem1, shipmentItem2),
			(List<ShipmentItem>)page.getItems());
		assertValid(page);

		shipmentItemResource.deleteShipmentItem(shipmentItem1.getId());

		shipmentItemResource.deleteShipmentItem(shipmentItem2.getId());
	}

	@Test
	public void testGetShipmentItemsPageWithPagination() throws Exception {
		Long shipmentId = testGetShipmentItemsPage_getShipmentId();

		ShipmentItem shipmentItem1 = testGetShipmentItemsPage_addShipmentItem(
			shipmentId, randomShipmentItem());

		ShipmentItem shipmentItem2 = testGetShipmentItemsPage_addShipmentItem(
			shipmentId, randomShipmentItem());

		ShipmentItem shipmentItem3 = testGetShipmentItemsPage_addShipmentItem(
			shipmentId, randomShipmentItem());

		Page<ShipmentItem> page1 = shipmentItemResource.getShipmentItemsPage(
			shipmentId, Pagination.of(1, 2));

		List<ShipmentItem> shipmentItems1 =
			(List<ShipmentItem>)page1.getItems();

		Assert.assertEquals(
			shipmentItems1.toString(), 2, shipmentItems1.size());

		Page<ShipmentItem> page2 = shipmentItemResource.getShipmentItemsPage(
			shipmentId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ShipmentItem> shipmentItems2 =
			(List<ShipmentItem>)page2.getItems();

		Assert.assertEquals(
			shipmentItems2.toString(), 1, shipmentItems2.size());

		Page<ShipmentItem> page3 = shipmentItemResource.getShipmentItemsPage(
			shipmentId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(shipmentItem1, shipmentItem2, shipmentItem3),
			(List<ShipmentItem>)page3.getItems());
	}

	protected ShipmentItem testGetShipmentItemsPage_addShipmentItem(
			Long shipmentId, ShipmentItem shipmentItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetShipmentItemsPage_getShipmentId() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetShipmentItemsPage_getIrrelevantShipmentId()
		throws Exception {

		return null;
	}

	@Test
	public void testGraphQLGetShipmentItemsPage() throws Exception {
		Long shipmentId = testGetShipmentItemsPage_getShipmentId();

		GraphQLField graphQLField = new GraphQLField(
			"shipmentItems",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);

					put("shipmentId", shipmentId);
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject shipmentItemsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/shipmentItems");

		Assert.assertEquals(0, shipmentItemsJSONObject.get("totalCount"));

		ShipmentItem shipmentItem1 = testGraphQLShipmentItem_addShipmentItem();
		ShipmentItem shipmentItem2 = testGraphQLShipmentItem_addShipmentItem();

		shipmentItemsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/shipmentItems");

		Assert.assertEquals(2, shipmentItemsJSONObject.getLong("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(shipmentItem1, shipmentItem2),
			Arrays.asList(
				ShipmentItemSerDes.toDTOs(
					shipmentItemsJSONObject.getString("items"))));
	}

	@Test
	public void testPostShipmentItem() throws Exception {
		ShipmentItem randomShipmentItem = randomShipmentItem();

		ShipmentItem postShipmentItem = testPostShipmentItem_addShipmentItem(
			randomShipmentItem);

		assertEquals(randomShipmentItem, postShipmentItem);
		assertValid(postShipmentItem);
	}

	protected ShipmentItem testPostShipmentItem_addShipmentItem(
			ShipmentItem shipmentItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected ShipmentItem testGraphQLShipmentItem_addShipmentItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		ShipmentItem shipmentItem, List<ShipmentItem> shipmentItems) {

		boolean contains = false;

		for (ShipmentItem item : shipmentItems) {
			if (equals(shipmentItem, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			shipmentItems + " does not contain " + shipmentItem, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		ShipmentItem shipmentItem1, ShipmentItem shipmentItem2) {

		Assert.assertTrue(
			shipmentItem1 + " does not equal " + shipmentItem2,
			equals(shipmentItem1, shipmentItem2));
	}

	protected void assertEquals(
		List<ShipmentItem> shipmentItems1, List<ShipmentItem> shipmentItems2) {

		Assert.assertEquals(shipmentItems1.size(), shipmentItems2.size());

		for (int i = 0; i < shipmentItems1.size(); i++) {
			ShipmentItem shipmentItem1 = shipmentItems1.get(i);
			ShipmentItem shipmentItem2 = shipmentItems2.get(i);

			assertEquals(shipmentItem1, shipmentItem2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ShipmentItem> shipmentItems1, List<ShipmentItem> shipmentItems2) {

		Assert.assertEquals(shipmentItems1.size(), shipmentItems2.size());

		for (ShipmentItem shipmentItem1 : shipmentItems1) {
			boolean contains = false;

			for (ShipmentItem shipmentItem2 : shipmentItems2) {
				if (equals(shipmentItem1, shipmentItem2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				shipmentItems2 + " does not contain " + shipmentItem1,
				contains);
		}
	}

	protected void assertValid(ShipmentItem shipmentItem) throws Exception {
		boolean valid = true;

		if (shipmentItem.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (shipmentItem.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (shipmentItem.getCreateDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (shipmentItem.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (shipmentItem.getModifiedDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderItemId", additionalAssertFieldName)) {
				if (shipmentItem.getOrderItemId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("quantity", additionalAssertFieldName)) {
				if (shipmentItem.getQuantity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"shipmentExternalReferenceCode",
					additionalAssertFieldName)) {

				if (shipmentItem.getShipmentExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("shipmentId", additionalAssertFieldName)) {
				if (shipmentItem.getShipmentId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (shipmentItem.getUserName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("warehouseId", additionalAssertFieldName)) {
				if (shipmentItem.getWarehouseId() == null) {
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

	protected void assertValid(Page<ShipmentItem> page) {
		boolean valid = false;

		java.util.Collection<ShipmentItem> shipmentItems = page.getItems();

		int size = shipmentItems.size();

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
					com.liferay.headless.commerce.admin.shipment.dto.v1_0.
						ShipmentItem.class)) {

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
		ShipmentItem shipmentItem1, ShipmentItem shipmentItem2) {

		if (shipmentItem1 == shipmentItem2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)shipmentItem1.getActions(),
						(Map)shipmentItem2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("createDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipmentItem1.getCreateDate(),
						shipmentItem2.getCreateDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						shipmentItem1.getExternalReferenceCode(),
						shipmentItem2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipmentItem1.getId(), shipmentItem2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipmentItem1.getModifiedDate(),
						shipmentItem2.getModifiedDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderItemId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipmentItem1.getOrderItemId(),
						shipmentItem2.getOrderItemId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("quantity", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipmentItem1.getQuantity(),
						shipmentItem2.getQuantity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"shipmentExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						shipmentItem1.getShipmentExternalReferenceCode(),
						shipmentItem2.getShipmentExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("shipmentId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipmentItem1.getShipmentId(),
						shipmentItem2.getShipmentId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("userName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipmentItem1.getUserName(),
						shipmentItem2.getUserName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("warehouseId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						shipmentItem1.getWarehouseId(),
						shipmentItem2.getWarehouseId())) {

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

		if (!(_shipmentItemResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_shipmentItemResource;

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
		EntityField entityField, String operator, ShipmentItem shipmentItem) {

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

		if (entityFieldName.equals("createDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							shipmentItem.getCreateDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(shipmentItem.getCreateDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(shipmentItem.getCreateDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(shipmentItem.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
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
							shipmentItem.getModifiedDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							shipmentItem.getModifiedDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(shipmentItem.getModifiedDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("orderItemId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("quantity")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("shipmentExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					shipmentItem.getShipmentExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("shipmentId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("userName")) {
			sb.append("'");
			sb.append(String.valueOf(shipmentItem.getUserName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("warehouseId")) {
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

	protected ShipmentItem randomShipmentItem() throws Exception {
		return new ShipmentItem() {
			{
				createDate = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				modifiedDate = RandomTestUtil.nextDate();
				orderItemId = RandomTestUtil.randomLong();
				quantity = RandomTestUtil.randomInt();
				shipmentExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				shipmentId = RandomTestUtil.randomLong();
				userName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				warehouseId = RandomTestUtil.randomLong();
			}
		};
	}

	protected ShipmentItem randomIrrelevantShipmentItem() throws Exception {
		ShipmentItem randomIrrelevantShipmentItem = randomShipmentItem();

		return randomIrrelevantShipmentItem;
	}

	protected ShipmentItem randomPatchShipmentItem() throws Exception {
		return randomShipmentItem();
	}

	protected ShipmentItemResource shipmentItemResource;
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
		LogFactoryUtil.getLog(BaseShipmentItemResourceTestCase.class);

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
	private com.liferay.headless.commerce.admin.shipment.resource.v1_0.
		ShipmentItemResource _shipmentItemResource;

}