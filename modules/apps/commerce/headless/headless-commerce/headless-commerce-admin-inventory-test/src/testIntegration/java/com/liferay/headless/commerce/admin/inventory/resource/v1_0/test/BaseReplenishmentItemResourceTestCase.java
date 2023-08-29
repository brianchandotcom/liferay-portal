/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.inventory.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.inventory.client.dto.v1_0.ReplenishmentItem;
import com.liferay.headless.commerce.admin.inventory.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.inventory.client.pagination.Page;
import com.liferay.headless.commerce.admin.inventory.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.inventory.client.resource.v1_0.ReplenishmentItemResource;
import com.liferay.headless.commerce.admin.inventory.client.serdes.v1_0.ReplenishmentItemSerDes;
import com.liferay.petra.function.transform.TransformUtil;
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
 * @author Alessio Antonio Rendina
 * @generated
 */
@Generated("")
public abstract class BaseReplenishmentItemResourceTestCase {

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

		_replenishmentItemResource.setContextCompany(testCompany);

		ReplenishmentItemResource.Builder builder =
			ReplenishmentItemResource.builder();

		replenishmentItemResource = builder.authentication(
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

		ReplenishmentItem replenishmentItem1 = randomReplenishmentItem();

		String json = objectMapper.writeValueAsString(replenishmentItem1);

		ReplenishmentItem replenishmentItem2 = ReplenishmentItemSerDes.toDTO(
			json);

		Assert.assertTrue(equals(replenishmentItem1, replenishmentItem2));
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

		ReplenishmentItem replenishmentItem = randomReplenishmentItem();

		String json1 = objectMapper.writeValueAsString(replenishmentItem);
		String json2 = ReplenishmentItemSerDes.toJSON(replenishmentItem);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		ReplenishmentItem replenishmentItem = randomReplenishmentItem();

		replenishmentItem.setExternalReferenceCode(regex);
		replenishmentItem.setSku(regex);
		replenishmentItem.setUnitOfMeasureKey(regex);

		String json = ReplenishmentItemSerDes.toJSON(replenishmentItem);

		Assert.assertFalse(json.contains(regex));

		replenishmentItem = ReplenishmentItemSerDes.toDTO(json);

		Assert.assertEquals(
			regex, replenishmentItem.getExternalReferenceCode());
		Assert.assertEquals(regex, replenishmentItem.getSku());
		Assert.assertEquals(regex, replenishmentItem.getUnitOfMeasureKey());
	}

	@Test
	public void testDeleteReplenishmentItemByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ReplenishmentItem replenishmentItem =
			testDeleteReplenishmentItemByExternalReferenceCode_addReplenishmentItem();

		assertHttpResponseStatusCode(
			204,
			replenishmentItemResource.
				deleteReplenishmentItemByExternalReferenceCodeHttpResponse(
					replenishmentItem.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			replenishmentItemResource.
				getReplenishmentItemByExternalReferenceCodeHttpResponse(
					replenishmentItem.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			replenishmentItemResource.
				getReplenishmentItemByExternalReferenceCodeHttpResponse(
					replenishmentItem.getExternalReferenceCode()));
	}

	protected ReplenishmentItem
			testDeleteReplenishmentItemByExternalReferenceCode_addReplenishmentItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetReplenishmentItemByExternalReferenceCode()
		throws Exception {

		ReplenishmentItem postReplenishmentItem =
			testGetReplenishmentItemByExternalReferenceCode_addReplenishmentItem();

		ReplenishmentItem getReplenishmentItem =
			replenishmentItemResource.
				getReplenishmentItemByExternalReferenceCode(
					postReplenishmentItem.getExternalReferenceCode());

		assertEquals(postReplenishmentItem, getReplenishmentItem);
		assertValid(getReplenishmentItem);
	}

	protected ReplenishmentItem
			testGetReplenishmentItemByExternalReferenceCode_addReplenishmentItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetReplenishmentItemByExternalReferenceCode()
		throws Exception {

		ReplenishmentItem replenishmentItem =
			testGraphQLGetReplenishmentItemByExternalReferenceCode_addReplenishmentItem();

		Assert.assertTrue(
			equals(
				replenishmentItem,
				ReplenishmentItemSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"replenishmentItemByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												replenishmentItem.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/replenishmentItemByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetReplenishmentItemByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"replenishmentItemByExternalReferenceCode",
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

	protected ReplenishmentItem
			testGraphQLGetReplenishmentItemByExternalReferenceCode_addReplenishmentItem()
		throws Exception {

		return testGraphQLReplenishmentItem_addReplenishmentItem();
	}

	@Test
	public void testPatchReplenishmentItemByExternalReferenceCode()
		throws Exception {

		ReplenishmentItem postReplenishmentItem =
			testPatchReplenishmentItemByExternalReferenceCode_addReplenishmentItem();

		ReplenishmentItem randomPatchReplenishmentItem =
			randomPatchReplenishmentItem();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ReplenishmentItem patchReplenishmentItem =
			replenishmentItemResource.
				patchReplenishmentItemByExternalReferenceCode(
					postReplenishmentItem.getExternalReferenceCode(),
					randomPatchReplenishmentItem);

		ReplenishmentItem expectedPatchReplenishmentItem =
			postReplenishmentItem.clone();

		BeanTestUtil.copyProperties(
			randomPatchReplenishmentItem, expectedPatchReplenishmentItem);

		ReplenishmentItem getReplenishmentItem =
			replenishmentItemResource.
				getReplenishmentItemByExternalReferenceCode(
					patchReplenishmentItem.getExternalReferenceCode());

		assertEquals(expectedPatchReplenishmentItem, getReplenishmentItem);
		assertValid(getReplenishmentItem);
	}

	protected ReplenishmentItem
			testPatchReplenishmentItemByExternalReferenceCode_addReplenishmentItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteReplenishmentItem() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		ReplenishmentItem replenishmentItem =
			testDeleteReplenishmentItem_addReplenishmentItem();

		assertHttpResponseStatusCode(
			204,
			replenishmentItemResource.deleteReplenishmentItemHttpResponse(
				replenishmentItem.getId()));

		assertHttpResponseStatusCode(
			404,
			replenishmentItemResource.getReplenishmentItemHttpResponse(
				replenishmentItem.getId()));

		assertHttpResponseStatusCode(
			404,
			replenishmentItemResource.getReplenishmentItemHttpResponse(0L));
	}

	protected ReplenishmentItem
			testDeleteReplenishmentItem_addReplenishmentItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteReplenishmentItem() throws Exception {
		ReplenishmentItem replenishmentItem =
			testGraphQLDeleteReplenishmentItem_addReplenishmentItem();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteReplenishmentItem",
						new HashMap<String, Object>() {
							{
								put(
									"replenishmentItemId",
									replenishmentItem.getId());
							}
						})),
				"JSONObject/data", "Object/deleteReplenishmentItem"));
		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"replenishmentItem",
					new HashMap<String, Object>() {
						{
							put(
								"replenishmentItemId",
								replenishmentItem.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	protected ReplenishmentItem
			testGraphQLDeleteReplenishmentItem_addReplenishmentItem()
		throws Exception {

		return testGraphQLReplenishmentItem_addReplenishmentItem();
	}

	@Test
	public void testGetReplenishmentItem() throws Exception {
		ReplenishmentItem postReplenishmentItem =
			testGetReplenishmentItem_addReplenishmentItem();

		ReplenishmentItem getReplenishmentItem =
			replenishmentItemResource.getReplenishmentItem(
				postReplenishmentItem.getId());

		assertEquals(postReplenishmentItem, getReplenishmentItem);
		assertValid(getReplenishmentItem);
	}

	protected ReplenishmentItem testGetReplenishmentItem_addReplenishmentItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetReplenishmentItem() throws Exception {
		ReplenishmentItem replenishmentItem =
			testGraphQLGetReplenishmentItem_addReplenishmentItem();

		Assert.assertTrue(
			equals(
				replenishmentItem,
				ReplenishmentItemSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"replenishmentItem",
								new HashMap<String, Object>() {
									{
										put(
											"replenishmentItemId",
											replenishmentItem.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/replenishmentItem"))));
	}

	@Test
	public void testGraphQLGetReplenishmentItemNotFound() throws Exception {
		Long irrelevantReplenishmentItemId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"replenishmentItem",
						new HashMap<String, Object>() {
							{
								put(
									"replenishmentItemId",
									irrelevantReplenishmentItemId);
							}
						},
						getGraphQLFields())),
				"JSONArray/errors", "Object/0", "JSONObject/extensions",
				"Object/code"));
	}

	protected ReplenishmentItem
			testGraphQLGetReplenishmentItem_addReplenishmentItem()
		throws Exception {

		return testGraphQLReplenishmentItem_addReplenishmentItem();
	}

	@Test
	public void testPatchReplenishmentItem() throws Exception {
		ReplenishmentItem postReplenishmentItem =
			testPatchReplenishmentItem_addReplenishmentItem();

		ReplenishmentItem randomPatchReplenishmentItem =
			randomPatchReplenishmentItem();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		ReplenishmentItem patchReplenishmentItem =
			replenishmentItemResource.patchReplenishmentItem(
				postReplenishmentItem.getId(), randomPatchReplenishmentItem);

		ReplenishmentItem expectedPatchReplenishmentItem =
			postReplenishmentItem.clone();

		BeanTestUtil.copyProperties(
			randomPatchReplenishmentItem, expectedPatchReplenishmentItem);

		ReplenishmentItem getReplenishmentItem =
			replenishmentItemResource.getReplenishmentItem(
				patchReplenishmentItem.getId());

		assertEquals(expectedPatchReplenishmentItem, getReplenishmentItem);
		assertValid(getReplenishmentItem);
	}

	protected ReplenishmentItem
			testPatchReplenishmentItem_addReplenishmentItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetReplenishmentItemsPage() throws Exception {
		String sku = testGetReplenishmentItemsPage_getSku();
		String irrelevantSku = testGetReplenishmentItemsPage_getIrrelevantSku();

		Page<ReplenishmentItem> page =
			replenishmentItemResource.getReplenishmentItemsPage(
				sku, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantSku != null) {
			ReplenishmentItem irrelevantReplenishmentItem =
				testGetReplenishmentItemsPage_addReplenishmentItem(
					irrelevantSku, randomIrrelevantReplenishmentItem());

			page = replenishmentItemResource.getReplenishmentItemsPage(
				irrelevantSku, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantReplenishmentItem),
				(List<ReplenishmentItem>)page.getItems());
			assertValid(
				page,
				testGetReplenishmentItemsPage_getExpectedActions(
					irrelevantSku));
		}

		ReplenishmentItem replenishmentItem1 =
			testGetReplenishmentItemsPage_addReplenishmentItem(
				sku, randomReplenishmentItem());

		ReplenishmentItem replenishmentItem2 =
			testGetReplenishmentItemsPage_addReplenishmentItem(
				sku, randomReplenishmentItem());

		page = replenishmentItemResource.getReplenishmentItemsPage(
			sku, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(replenishmentItem1, replenishmentItem2),
			(List<ReplenishmentItem>)page.getItems());
		assertValid(
			page, testGetReplenishmentItemsPage_getExpectedActions(sku));

		replenishmentItemResource.deleteReplenishmentItem(
			replenishmentItem1.getId());

		replenishmentItemResource.deleteReplenishmentItem(
			replenishmentItem2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetReplenishmentItemsPage_getExpectedActions(String sku)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetReplenishmentItemsPageWithPagination() throws Exception {
		String sku = testGetReplenishmentItemsPage_getSku();

		ReplenishmentItem replenishmentItem1 =
			testGetReplenishmentItemsPage_addReplenishmentItem(
				sku, randomReplenishmentItem());

		ReplenishmentItem replenishmentItem2 =
			testGetReplenishmentItemsPage_addReplenishmentItem(
				sku, randomReplenishmentItem());

		ReplenishmentItem replenishmentItem3 =
			testGetReplenishmentItemsPage_addReplenishmentItem(
				sku, randomReplenishmentItem());

		Page<ReplenishmentItem> page1 =
			replenishmentItemResource.getReplenishmentItemsPage(
				sku, Pagination.of(1, 2));

		List<ReplenishmentItem> replenishmentItems1 =
			(List<ReplenishmentItem>)page1.getItems();

		Assert.assertEquals(
			replenishmentItems1.toString(), 2, replenishmentItems1.size());

		Page<ReplenishmentItem> page2 =
			replenishmentItemResource.getReplenishmentItemsPage(
				sku, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ReplenishmentItem> replenishmentItems2 =
			(List<ReplenishmentItem>)page2.getItems();

		Assert.assertEquals(
			replenishmentItems2.toString(), 1, replenishmentItems2.size());

		Page<ReplenishmentItem> page3 =
			replenishmentItemResource.getReplenishmentItemsPage(
				sku, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				replenishmentItem1, replenishmentItem2, replenishmentItem3),
			(List<ReplenishmentItem>)page3.getItems());
	}

	protected ReplenishmentItem
			testGetReplenishmentItemsPage_addReplenishmentItem(
				String sku, ReplenishmentItem replenishmentItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetReplenishmentItemsPage_getSku() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String testGetReplenishmentItemsPage_getIrrelevantSku()
		throws Exception {

		return null;
	}

	@Test
	public void testGraphQLGetReplenishmentItemsPage() throws Exception {
		String sku = testGetReplenishmentItemsPage_getSku();

		GraphQLField graphQLField = new GraphQLField(
			"replenishmentItems",
			new HashMap<String, Object>() {
				{
					put("page", 1);
					put("pageSize", 10);

					put("sku", "\"" + sku + "\"");
				}
			},
			new GraphQLField("items", getGraphQLFields()),
			new GraphQLField("page"), new GraphQLField("totalCount"));

		JSONObject replenishmentItemsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/replenishmentItems");

		Assert.assertEquals(0, replenishmentItemsJSONObject.get("totalCount"));

		ReplenishmentItem replenishmentItem1 =
			testGraphQLGetReplenishmentItemsPage_addReplenishmentItem();
		ReplenishmentItem replenishmentItem2 =
			testGraphQLGetReplenishmentItemsPage_addReplenishmentItem();

		replenishmentItemsJSONObject = JSONUtil.getValueAsJSONObject(
			invokeGraphQLQuery(graphQLField), "JSONObject/data",
			"JSONObject/replenishmentItems");

		Assert.assertEquals(
			2, replenishmentItemsJSONObject.getLong("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(replenishmentItem1, replenishmentItem2),
			Arrays.asList(
				ReplenishmentItemSerDes.toDTOs(
					replenishmentItemsJSONObject.getString("items"))));
	}

	protected ReplenishmentItem
			testGraphQLGetReplenishmentItemsPage_addReplenishmentItem()
		throws Exception {

		return testGraphQLReplenishmentItem_addReplenishmentItem();
	}

	@Test
	public void testGetWarehouseIdReplenishmentItemsPage() throws Exception {
		Long warehouseId =
			testGetWarehouseIdReplenishmentItemsPage_getWarehouseId();
		Long irrelevantWarehouseId =
			testGetWarehouseIdReplenishmentItemsPage_getIrrelevantWarehouseId();

		Page<ReplenishmentItem> page =
			replenishmentItemResource.getWarehouseIdReplenishmentItemsPage(
				warehouseId, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantWarehouseId != null) {
			ReplenishmentItem irrelevantReplenishmentItem =
				testGetWarehouseIdReplenishmentItemsPage_addReplenishmentItem(
					irrelevantWarehouseId, randomIrrelevantReplenishmentItem());

			page =
				replenishmentItemResource.getWarehouseIdReplenishmentItemsPage(
					irrelevantWarehouseId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantReplenishmentItem),
				(List<ReplenishmentItem>)page.getItems());
			assertValid(
				page,
				testGetWarehouseIdReplenishmentItemsPage_getExpectedActions(
					irrelevantWarehouseId));
		}

		ReplenishmentItem replenishmentItem1 =
			testGetWarehouseIdReplenishmentItemsPage_addReplenishmentItem(
				warehouseId, randomReplenishmentItem());

		ReplenishmentItem replenishmentItem2 =
			testGetWarehouseIdReplenishmentItemsPage_addReplenishmentItem(
				warehouseId, randomReplenishmentItem());

		page = replenishmentItemResource.getWarehouseIdReplenishmentItemsPage(
			warehouseId, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(replenishmentItem1, replenishmentItem2),
			(List<ReplenishmentItem>)page.getItems());
		assertValid(
			page,
			testGetWarehouseIdReplenishmentItemsPage_getExpectedActions(
				warehouseId));

		replenishmentItemResource.deleteReplenishmentItem(
			replenishmentItem1.getId());

		replenishmentItemResource.deleteReplenishmentItem(
			replenishmentItem2.getId());
	}

	protected Map<String, Map<String, String>>
			testGetWarehouseIdReplenishmentItemsPage_getExpectedActions(
				Long warehouseId)
		throws Exception {

		Map<String, Map<String, String>> expectedActions = new HashMap<>();

		return expectedActions;
	}

	@Test
	public void testGetWarehouseIdReplenishmentItemsPageWithPagination()
		throws Exception {

		Long warehouseId =
			testGetWarehouseIdReplenishmentItemsPage_getWarehouseId();

		ReplenishmentItem replenishmentItem1 =
			testGetWarehouseIdReplenishmentItemsPage_addReplenishmentItem(
				warehouseId, randomReplenishmentItem());

		ReplenishmentItem replenishmentItem2 =
			testGetWarehouseIdReplenishmentItemsPage_addReplenishmentItem(
				warehouseId, randomReplenishmentItem());

		ReplenishmentItem replenishmentItem3 =
			testGetWarehouseIdReplenishmentItemsPage_addReplenishmentItem(
				warehouseId, randomReplenishmentItem());

		Page<ReplenishmentItem> page1 =
			replenishmentItemResource.getWarehouseIdReplenishmentItemsPage(
				warehouseId, Pagination.of(1, 2));

		List<ReplenishmentItem> replenishmentItems1 =
			(List<ReplenishmentItem>)page1.getItems();

		Assert.assertEquals(
			replenishmentItems1.toString(), 2, replenishmentItems1.size());

		Page<ReplenishmentItem> page2 =
			replenishmentItemResource.getWarehouseIdReplenishmentItemsPage(
				warehouseId, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<ReplenishmentItem> replenishmentItems2 =
			(List<ReplenishmentItem>)page2.getItems();

		Assert.assertEquals(
			replenishmentItems2.toString(), 1, replenishmentItems2.size());

		Page<ReplenishmentItem> page3 =
			replenishmentItemResource.getWarehouseIdReplenishmentItemsPage(
				warehouseId, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				replenishmentItem1, replenishmentItem2, replenishmentItem3),
			(List<ReplenishmentItem>)page3.getItems());
	}

	protected ReplenishmentItem
			testGetWarehouseIdReplenishmentItemsPage_addReplenishmentItem(
				Long warehouseId, ReplenishmentItem replenishmentItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetWarehouseIdReplenishmentItemsPage_getWarehouseId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetWarehouseIdReplenishmentItemsPage_getIrrelevantWarehouseId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostReplenishmentItem() throws Exception {
		ReplenishmentItem randomReplenishmentItem = randomReplenishmentItem();

		ReplenishmentItem postReplenishmentItem =
			testPostReplenishmentItem_addReplenishmentItem(
				randomReplenishmentItem);

		assertEquals(randomReplenishmentItem, postReplenishmentItem);
		assertValid(postReplenishmentItem);
	}

	protected ReplenishmentItem testPostReplenishmentItem_addReplenishmentItem(
			ReplenishmentItem replenishmentItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected ReplenishmentItem
			testGraphQLReplenishmentItem_addReplenishmentItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		ReplenishmentItem replenishmentItem,
		List<ReplenishmentItem> replenishmentItems) {

		boolean contains = false;

		for (ReplenishmentItem item : replenishmentItems) {
			if (equals(replenishmentItem, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			replenishmentItems + " does not contain " + replenishmentItem,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		ReplenishmentItem replenishmentItem1,
		ReplenishmentItem replenishmentItem2) {

		Assert.assertTrue(
			replenishmentItem1 + " does not equal " + replenishmentItem2,
			equals(replenishmentItem1, replenishmentItem2));
	}

	protected void assertEquals(
		List<ReplenishmentItem> replenishmentItems1,
		List<ReplenishmentItem> replenishmentItems2) {

		Assert.assertEquals(
			replenishmentItems1.size(), replenishmentItems2.size());

		for (int i = 0; i < replenishmentItems1.size(); i++) {
			ReplenishmentItem replenishmentItem1 = replenishmentItems1.get(i);
			ReplenishmentItem replenishmentItem2 = replenishmentItems2.get(i);

			assertEquals(replenishmentItem1, replenishmentItem2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<ReplenishmentItem> replenishmentItems1,
		List<ReplenishmentItem> replenishmentItems2) {

		Assert.assertEquals(
			replenishmentItems1.size(), replenishmentItems2.size());

		for (ReplenishmentItem replenishmentItem1 : replenishmentItems1) {
			boolean contains = false;

			for (ReplenishmentItem replenishmentItem2 : replenishmentItems2) {
				if (equals(replenishmentItem1, replenishmentItem2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				replenishmentItems2 + " does not contain " + replenishmentItem1,
				contains);
		}
	}

	protected void assertValid(ReplenishmentItem replenishmentItem)
		throws Exception {

		boolean valid = true;

		if (replenishmentItem.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("availabilityDate", additionalAssertFieldName)) {
				if (replenishmentItem.getAvailabilityDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (replenishmentItem.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("quantity", additionalAssertFieldName)) {
				if (replenishmentItem.getQuantity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (replenishmentItem.getSku() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("unitOfMeasureKey", additionalAssertFieldName)) {
				if (replenishmentItem.getUnitOfMeasureKey() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("warehouseId", additionalAssertFieldName)) {
				if (replenishmentItem.getWarehouseId() == null) {
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

	protected void assertValid(Page<ReplenishmentItem> page) {
		assertValid(page, Collections.emptyMap());
	}

	protected void assertValid(
		Page<ReplenishmentItem> page,
		Map<String, Map<String, String>> expectedActions) {

		boolean valid = false;

		java.util.Collection<ReplenishmentItem> replenishmentItems =
			page.getItems();

		int size = replenishmentItems.size();

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
					com.liferay.headless.commerce.admin.inventory.dto.v1_0.
						ReplenishmentItem.class)) {

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
		ReplenishmentItem replenishmentItem1,
		ReplenishmentItem replenishmentItem2) {

		if (replenishmentItem1 == replenishmentItem2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("availabilityDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						replenishmentItem1.getAvailabilityDate(),
						replenishmentItem2.getAvailabilityDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						replenishmentItem1.getExternalReferenceCode(),
						replenishmentItem2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						replenishmentItem1.getId(),
						replenishmentItem2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("quantity", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						replenishmentItem1.getQuantity(),
						replenishmentItem2.getQuantity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						replenishmentItem1.getSku(),
						replenishmentItem2.getSku())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("unitOfMeasureKey", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						replenishmentItem1.getUnitOfMeasureKey(),
						replenishmentItem2.getUnitOfMeasureKey())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("warehouseId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						replenishmentItem1.getWarehouseId(),
						replenishmentItem2.getWarehouseId())) {

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

		if (!(_replenishmentItemResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_replenishmentItemResource;

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
		ReplenishmentItem replenishmentItem) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("availabilityDate")) {
			if (operator.equals("between")) {
				sb = new StringBundler();

				sb.append("(");
				sb.append(entityFieldName);
				sb.append(" gt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							replenishmentItem.getAvailabilityDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							replenishmentItem.getAvailabilityDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(
					_dateFormat.format(
						replenishmentItem.getAvailabilityDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			Object object = replenishmentItem.getExternalReferenceCode();

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

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("quantity")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sku")) {
			Object object = replenishmentItem.getSku();

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
			Object object = replenishmentItem.getUnitOfMeasureKey();

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

	protected ReplenishmentItem randomReplenishmentItem() throws Exception {
		return new ReplenishmentItem() {
			{
				availabilityDate = RandomTestUtil.nextDate();
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				sku = StringUtil.toLowerCase(RandomTestUtil.randomString());
				unitOfMeasureKey = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				warehouseId = RandomTestUtil.randomLong();
			}
		};
	}

	protected ReplenishmentItem randomIrrelevantReplenishmentItem()
		throws Exception {

		ReplenishmentItem randomIrrelevantReplenishmentItem =
			randomReplenishmentItem();

		return randomIrrelevantReplenishmentItem;
	}

	protected ReplenishmentItem randomPatchReplenishmentItem()
		throws Exception {

		return randomReplenishmentItem();
	}

	protected ReplenishmentItemResource replenishmentItemResource;
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
		LogFactoryUtil.getLog(BaseReplenishmentItemResourceTestCase.class);

	private static DateFormat _dateFormat;

	@Inject
	private com.liferay.headless.commerce.admin.inventory.resource.v1_0.
		ReplenishmentItemResource _replenishmentItemResource;

}