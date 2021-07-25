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

package com.liferay.headless.commerce.admin.inventory.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.inventory.client.dto.v1_0.WarehouseItem;
import com.liferay.headless.commerce.admin.inventory.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.inventory.client.pagination.Page;
import com.liferay.headless.commerce.admin.inventory.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.inventory.client.resource.v1_0.WarehouseItemResource;
import com.liferay.headless.commerce.admin.inventory.client.serdes.v1_0.WarehouseItemSerDes;
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
public abstract class BaseWarehouseItemResourceTestCase {

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

		_warehouseItemResource.setContextCompany(testCompany);

		WarehouseItemResource.Builder builder = WarehouseItemResource.builder();

		warehouseItemResource = builder.authentication(
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

		WarehouseItem warehouseItem1 = randomWarehouseItem();

		String json = objectMapper.writeValueAsString(warehouseItem1);

		WarehouseItem warehouseItem2 = WarehouseItemSerDes.toDTO(json);

		Assert.assertTrue(equals(warehouseItem1, warehouseItem2));
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

		WarehouseItem warehouseItem = randomWarehouseItem();

		String json1 = objectMapper.writeValueAsString(warehouseItem);
		String json2 = WarehouseItemSerDes.toJSON(warehouseItem);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		WarehouseItem warehouseItem = randomWarehouseItem();

		warehouseItem.setExternalReferenceCode(regex);
		warehouseItem.setSku(regex);
		warehouseItem.setWarehouseExternalReferenceCode(regex);

		String json = WarehouseItemSerDes.toJSON(warehouseItem);

		Assert.assertFalse(json.contains(regex));

		warehouseItem = WarehouseItemSerDes.toDTO(json);

		Assert.assertEquals(regex, warehouseItem.getExternalReferenceCode());
		Assert.assertEquals(regex, warehouseItem.getSku());
		Assert.assertEquals(
			regex, warehouseItem.getWarehouseExternalReferenceCode());
	}

	@Test
	public void testDeleteWarehouseItemByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		WarehouseItem warehouseItem =
			testDeleteWarehouseItemByExternalReferenceCode_addWarehouseItem();

		assertHttpResponseStatusCode(
			204,
			warehouseItemResource.
				deleteWarehouseItemByExternalReferenceCodeHttpResponse(
					warehouseItem.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			warehouseItemResource.
				getWarehouseItemByExternalReferenceCodeHttpResponse(
					warehouseItem.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			warehouseItemResource.
				getWarehouseItemByExternalReferenceCodeHttpResponse(
					warehouseItem.getExternalReferenceCode()));
	}

	protected WarehouseItem
			testDeleteWarehouseItemByExternalReferenceCode_addWarehouseItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetWarehouseItemByExternalReferenceCode() throws Exception {
		WarehouseItem postWarehouseItem =
			testGetWarehouseItemByExternalReferenceCode_addWarehouseItem();

		WarehouseItem getWarehouseItem =
			warehouseItemResource.getWarehouseItemByExternalReferenceCode(
				postWarehouseItem.getExternalReferenceCode());

		assertEquals(postWarehouseItem, getWarehouseItem);
		assertValid(getWarehouseItem);
	}

	protected WarehouseItem
			testGetWarehouseItemByExternalReferenceCode_addWarehouseItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetWarehouseItemByExternalReferenceCode()
		throws Exception {

		WarehouseItem warehouseItem =
			testGraphQLWarehouseItem_addWarehouseItem();

		Assert.assertTrue(
			equals(
				warehouseItem,
				WarehouseItemSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"warehouseItemByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												warehouseItem.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/warehouseItemByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetWarehouseItemByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"warehouseItemByExternalReferenceCode",
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
	public void testPatchWarehouseItemByExternalReferenceCode()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testPostWarehouseItemByExternalReferenceCode()
		throws Exception {

		WarehouseItem randomWarehouseItem = randomWarehouseItem();

		WarehouseItem postWarehouseItem =
			testPostWarehouseItemByExternalReferenceCode_addWarehouseItem(
				randomWarehouseItem);

		assertEquals(randomWarehouseItem, postWarehouseItem);
		assertValid(postWarehouseItem);
	}

	protected WarehouseItem
			testPostWarehouseItemByExternalReferenceCode_addWarehouseItem(
				WarehouseItem warehouseItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteWarehouseItem() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		WarehouseItem warehouseItem =
			testDeleteWarehouseItem_addWarehouseItem();

		assertHttpResponseStatusCode(
			204,
			warehouseItemResource.deleteWarehouseItemHttpResponse(
				warehouseItem.getId()));

		assertHttpResponseStatusCode(
			404,
			warehouseItemResource.getWarehouseItemHttpResponse(
				warehouseItem.getId()));

		assertHttpResponseStatusCode(
			404,
			warehouseItemResource.getWarehouseItemHttpResponse(
				warehouseItem.getId()));
	}

	protected WarehouseItem testDeleteWarehouseItem_addWarehouseItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteWarehouseItem() throws Exception {
		WarehouseItem warehouseItem =
			testGraphQLWarehouseItem_addWarehouseItem();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteWarehouseItem",
						new HashMap<String, Object>() {
							{
								put("id", warehouseItem.getId());
							}
						})),
				"JSONObject/data", "Object/deleteWarehouseItem"));

		JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
			invokeGraphQLQuery(
				new GraphQLField(
					"warehouseItem",
					new HashMap<String, Object>() {
						{
							put("id", warehouseItem.getId());
						}
					},
					new GraphQLField("id"))),
			"JSONArray/errors");

		Assert.assertTrue(errorsJSONArray.length() > 0);
	}

	@Test
	public void testGetWarehouseItem() throws Exception {
		WarehouseItem postWarehouseItem =
			testGetWarehouseItem_addWarehouseItem();

		WarehouseItem getWarehouseItem = warehouseItemResource.getWarehouseItem(
			postWarehouseItem.getId());

		assertEquals(postWarehouseItem, getWarehouseItem);
		assertValid(getWarehouseItem);
	}

	protected WarehouseItem testGetWarehouseItem_addWarehouseItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetWarehouseItem() throws Exception {
		WarehouseItem warehouseItem =
			testGraphQLWarehouseItem_addWarehouseItem();

		Assert.assertTrue(
			equals(
				warehouseItem,
				WarehouseItemSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"warehouseItem",
								new HashMap<String, Object>() {
									{
										put("id", warehouseItem.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/warehouseItem"))));
	}

	@Test
	public void testGraphQLGetWarehouseItemNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"warehouseItem",
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
	public void testPatchWarehouseItem() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetWarehousByExternalReferenceCodeWarehouseItemsPage()
		throws Exception {

		Page<WarehouseItem> page =
			warehouseItemResource.
				getWarehousByExternalReferenceCodeWarehouseItemsPage(
					testGetWarehousByExternalReferenceCodeWarehouseItemsPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetWarehousByExternalReferenceCodeWarehouseItemsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetWarehousByExternalReferenceCodeWarehouseItemsPage_getIrrelevantExternalReferenceCode();

		if (irrelevantExternalReferenceCode != null) {
			WarehouseItem irrelevantWarehouseItem =
				testGetWarehousByExternalReferenceCodeWarehouseItemsPage_addWarehouseItem(
					irrelevantExternalReferenceCode,
					randomIrrelevantWarehouseItem());

			page =
				warehouseItemResource.
					getWarehousByExternalReferenceCodeWarehouseItemsPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantWarehouseItem),
				(List<WarehouseItem>)page.getItems());
			assertValid(page);
		}

		WarehouseItem warehouseItem1 =
			testGetWarehousByExternalReferenceCodeWarehouseItemsPage_addWarehouseItem(
				externalReferenceCode, randomWarehouseItem());

		WarehouseItem warehouseItem2 =
			testGetWarehousByExternalReferenceCodeWarehouseItemsPage_addWarehouseItem(
				externalReferenceCode, randomWarehouseItem());

		page =
			warehouseItemResource.
				getWarehousByExternalReferenceCodeWarehouseItemsPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(warehouseItem1, warehouseItem2),
			(List<WarehouseItem>)page.getItems());
		assertValid(page);

		warehouseItemResource.deleteWarehouseItem(warehouseItem1.getId());

		warehouseItemResource.deleteWarehouseItem(warehouseItem2.getId());
	}

	@Test
	public void testGetWarehousByExternalReferenceCodeWarehouseItemsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetWarehousByExternalReferenceCodeWarehouseItemsPage_getExternalReferenceCode();

		WarehouseItem warehouseItem1 =
			testGetWarehousByExternalReferenceCodeWarehouseItemsPage_addWarehouseItem(
				externalReferenceCode, randomWarehouseItem());

		WarehouseItem warehouseItem2 =
			testGetWarehousByExternalReferenceCodeWarehouseItemsPage_addWarehouseItem(
				externalReferenceCode, randomWarehouseItem());

		WarehouseItem warehouseItem3 =
			testGetWarehousByExternalReferenceCodeWarehouseItemsPage_addWarehouseItem(
				externalReferenceCode, randomWarehouseItem());

		Page<WarehouseItem> page1 =
			warehouseItemResource.
				getWarehousByExternalReferenceCodeWarehouseItemsPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<WarehouseItem> warehouseItems1 =
			(List<WarehouseItem>)page1.getItems();

		Assert.assertEquals(
			warehouseItems1.toString(), 2, warehouseItems1.size());

		Page<WarehouseItem> page2 =
			warehouseItemResource.
				getWarehousByExternalReferenceCodeWarehouseItemsPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WarehouseItem> warehouseItems2 =
			(List<WarehouseItem>)page2.getItems();

		Assert.assertEquals(
			warehouseItems2.toString(), 1, warehouseItems2.size());

		Page<WarehouseItem> page3 =
			warehouseItemResource.
				getWarehousByExternalReferenceCodeWarehouseItemsPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(warehouseItem1, warehouseItem2, warehouseItem3),
			(List<WarehouseItem>)page3.getItems());
	}

	protected WarehouseItem
			testGetWarehousByExternalReferenceCodeWarehouseItemsPage_addWarehouseItem(
				String externalReferenceCode, WarehouseItem warehouseItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetWarehousByExternalReferenceCodeWarehouseItemsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetWarehousByExternalReferenceCodeWarehouseItemsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostWarehousByExternalReferenceCodeWarehouseItem()
		throws Exception {

		WarehouseItem randomWarehouseItem = randomWarehouseItem();

		WarehouseItem postWarehouseItem =
			testPostWarehousByExternalReferenceCodeWarehouseItem_addWarehouseItem(
				randomWarehouseItem);

		assertEquals(randomWarehouseItem, postWarehouseItem);
		assertValid(postWarehouseItem);
	}

	protected WarehouseItem
			testPostWarehousByExternalReferenceCodeWarehouseItem_addWarehouseItem(
				WarehouseItem warehouseItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetWarehousIdWarehouseItemsPage() throws Exception {
		Page<WarehouseItem> page =
			warehouseItemResource.getWarehousIdWarehouseItemsPage(
				testGetWarehousIdWarehouseItemsPage_getId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetWarehousIdWarehouseItemsPage_getId();
		Long irrelevantId =
			testGetWarehousIdWarehouseItemsPage_getIrrelevantId();

		if (irrelevantId != null) {
			WarehouseItem irrelevantWarehouseItem =
				testGetWarehousIdWarehouseItemsPage_addWarehouseItem(
					irrelevantId, randomIrrelevantWarehouseItem());

			page = warehouseItemResource.getWarehousIdWarehouseItemsPage(
				irrelevantId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantWarehouseItem),
				(List<WarehouseItem>)page.getItems());
			assertValid(page);
		}

		WarehouseItem warehouseItem1 =
			testGetWarehousIdWarehouseItemsPage_addWarehouseItem(
				id, randomWarehouseItem());

		WarehouseItem warehouseItem2 =
			testGetWarehousIdWarehouseItemsPage_addWarehouseItem(
				id, randomWarehouseItem());

		page = warehouseItemResource.getWarehousIdWarehouseItemsPage(
			id, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(warehouseItem1, warehouseItem2),
			(List<WarehouseItem>)page.getItems());
		assertValid(page);

		warehouseItemResource.deleteWarehouseItem(warehouseItem1.getId());

		warehouseItemResource.deleteWarehouseItem(warehouseItem2.getId());
	}

	@Test
	public void testGetWarehousIdWarehouseItemsPageWithPagination()
		throws Exception {

		Long id = testGetWarehousIdWarehouseItemsPage_getId();

		WarehouseItem warehouseItem1 =
			testGetWarehousIdWarehouseItemsPage_addWarehouseItem(
				id, randomWarehouseItem());

		WarehouseItem warehouseItem2 =
			testGetWarehousIdWarehouseItemsPage_addWarehouseItem(
				id, randomWarehouseItem());

		WarehouseItem warehouseItem3 =
			testGetWarehousIdWarehouseItemsPage_addWarehouseItem(
				id, randomWarehouseItem());

		Page<WarehouseItem> page1 =
			warehouseItemResource.getWarehousIdWarehouseItemsPage(
				id, Pagination.of(1, 2));

		List<WarehouseItem> warehouseItems1 =
			(List<WarehouseItem>)page1.getItems();

		Assert.assertEquals(
			warehouseItems1.toString(), 2, warehouseItems1.size());

		Page<WarehouseItem> page2 =
			warehouseItemResource.getWarehousIdWarehouseItemsPage(
				id, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WarehouseItem> warehouseItems2 =
			(List<WarehouseItem>)page2.getItems();

		Assert.assertEquals(
			warehouseItems2.toString(), 1, warehouseItems2.size());

		Page<WarehouseItem> page3 =
			warehouseItemResource.getWarehousIdWarehouseItemsPage(
				id, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(warehouseItem1, warehouseItem2, warehouseItem3),
			(List<WarehouseItem>)page3.getItems());
	}

	protected WarehouseItem
			testGetWarehousIdWarehouseItemsPage_addWarehouseItem(
				Long id, WarehouseItem warehouseItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetWarehousIdWarehouseItemsPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetWarehousIdWarehouseItemsPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostWarehousIdWarehouseItem() throws Exception {
		WarehouseItem randomWarehouseItem = randomWarehouseItem();

		WarehouseItem postWarehouseItem =
			testPostWarehousIdWarehouseItem_addWarehouseItem(
				randomWarehouseItem);

		assertEquals(randomWarehouseItem, postWarehouseItem);
		assertValid(postWarehouseItem);
	}

	protected WarehouseItem testPostWarehousIdWarehouseItem_addWarehouseItem(
			WarehouseItem warehouseItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetWarehouseItemsUpdatedPage() throws Exception {
		Page<WarehouseItem> page =
			warehouseItemResource.getWarehouseItemsUpdatedPage(
				RandomTestUtil.nextDate(), RandomTestUtil.nextDate(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		WarehouseItem warehouseItem1 =
			testGetWarehouseItemsUpdatedPage_addWarehouseItem(
				randomWarehouseItem());

		WarehouseItem warehouseItem2 =
			testGetWarehouseItemsUpdatedPage_addWarehouseItem(
				randomWarehouseItem());

		page = warehouseItemResource.getWarehouseItemsUpdatedPage(
			null, null, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(warehouseItem1, warehouseItem2),
			(List<WarehouseItem>)page.getItems());
		assertValid(page);

		warehouseItemResource.deleteWarehouseItem(warehouseItem1.getId());

		warehouseItemResource.deleteWarehouseItem(warehouseItem2.getId());
	}

	@Test
	public void testGetWarehouseItemsUpdatedPageWithPagination()
		throws Exception {

		WarehouseItem warehouseItem1 =
			testGetWarehouseItemsUpdatedPage_addWarehouseItem(
				randomWarehouseItem());

		WarehouseItem warehouseItem2 =
			testGetWarehouseItemsUpdatedPage_addWarehouseItem(
				randomWarehouseItem());

		WarehouseItem warehouseItem3 =
			testGetWarehouseItemsUpdatedPage_addWarehouseItem(
				randomWarehouseItem());

		Page<WarehouseItem> page1 =
			warehouseItemResource.getWarehouseItemsUpdatedPage(
				null, null, Pagination.of(1, 2));

		List<WarehouseItem> warehouseItems1 =
			(List<WarehouseItem>)page1.getItems();

		Assert.assertEquals(
			warehouseItems1.toString(), 2, warehouseItems1.size());

		Page<WarehouseItem> page2 =
			warehouseItemResource.getWarehouseItemsUpdatedPage(
				null, null, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<WarehouseItem> warehouseItems2 =
			(List<WarehouseItem>)page2.getItems();

		Assert.assertEquals(
			warehouseItems2.toString(), 1, warehouseItems2.size());

		Page<WarehouseItem> page3 =
			warehouseItemResource.getWarehouseItemsUpdatedPage(
				null, null, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(warehouseItem1, warehouseItem2, warehouseItem3),
			(List<WarehouseItem>)page3.getItems());
	}

	protected WarehouseItem testGetWarehouseItemsUpdatedPage_addWarehouseItem(
			WarehouseItem warehouseItem)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected WarehouseItem testGraphQLWarehouseItem_addWarehouseItem()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		WarehouseItem warehouseItem1, WarehouseItem warehouseItem2) {

		Assert.assertTrue(
			warehouseItem1 + " does not equal " + warehouseItem2,
			equals(warehouseItem1, warehouseItem2));
	}

	protected void assertEquals(
		List<WarehouseItem> warehouseItems1,
		List<WarehouseItem> warehouseItems2) {

		Assert.assertEquals(warehouseItems1.size(), warehouseItems2.size());

		for (int i = 0; i < warehouseItems1.size(); i++) {
			WarehouseItem warehouseItem1 = warehouseItems1.get(i);
			WarehouseItem warehouseItem2 = warehouseItems2.get(i);

			assertEquals(warehouseItem1, warehouseItem2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<WarehouseItem> warehouseItems1,
		List<WarehouseItem> warehouseItems2) {

		Assert.assertEquals(warehouseItems1.size(), warehouseItems2.size());

		for (WarehouseItem warehouseItem1 : warehouseItems1) {
			boolean contains = false;

			for (WarehouseItem warehouseItem2 : warehouseItems2) {
				if (equals(warehouseItem1, warehouseItem2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				warehouseItems2 + " does not contain " + warehouseItem1,
				contains);
		}
	}

	protected void assertValid(WarehouseItem warehouseItem) throws Exception {
		boolean valid = true;

		if (warehouseItem.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (warehouseItem.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (warehouseItem.getModifiedDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("quantity", additionalAssertFieldName)) {
				if (warehouseItem.getQuantity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("reservedQuantity", additionalAssertFieldName)) {
				if (warehouseItem.getReservedQuantity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (warehouseItem.getSku() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"warehouseExternalReferenceCode",
					additionalAssertFieldName)) {

				if (warehouseItem.getWarehouseExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("warehouseId", additionalAssertFieldName)) {
				if (warehouseItem.getWarehouseId() == null) {
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

	protected void assertValid(Page<WarehouseItem> page) {
		boolean valid = false;

		java.util.Collection<WarehouseItem> warehouseItems = page.getItems();

		int size = warehouseItems.size();

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
				getDeclaredFields(
					com.liferay.headless.commerce.admin.inventory.dto.v1_0.
						WarehouseItem.class)) {

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
		WarehouseItem warehouseItem1, WarehouseItem warehouseItem2) {

		if (warehouseItem1 == warehouseItem2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						warehouseItem1.getExternalReferenceCode(),
						warehouseItem2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						warehouseItem1.getId(), warehouseItem2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("modifiedDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						warehouseItem1.getModifiedDate(),
						warehouseItem2.getModifiedDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("quantity", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						warehouseItem1.getQuantity(),
						warehouseItem2.getQuantity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("reservedQuantity", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						warehouseItem1.getReservedQuantity(),
						warehouseItem2.getReservedQuantity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("sku", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						warehouseItem1.getSku(), warehouseItem2.getSku())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"warehouseExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						warehouseItem1.getWarehouseExternalReferenceCode(),
						warehouseItem2.getWarehouseExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("warehouseId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						warehouseItem1.getWarehouseId(),
						warehouseItem2.getWarehouseId())) {

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

	protected Field[] getDeclaredFields(Class clazz) throws Exception {
		Stream<Field> stream = Stream.of(
			ReflectionUtil.getDeclaredFields(clazz));

		return stream.filter(
			field -> !field.isSynthetic()
		).toArray(
			Field[]::new
		);
	}

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_warehouseItemResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_warehouseItemResource;

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
		EntityField entityField, String operator, WarehouseItem warehouseItem) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(String.valueOf(warehouseItem.getExternalReferenceCode()));
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
							warehouseItem.getModifiedDate(), -2)));
				sb.append(" and ");
				sb.append(entityFieldName);
				sb.append(" lt ");
				sb.append(
					_dateFormat.format(
						DateUtils.addSeconds(
							warehouseItem.getModifiedDate(), 2)));
				sb.append(")");
			}
			else {
				sb.append(entityFieldName);

				sb.append(" ");
				sb.append(operator);
				sb.append(" ");

				sb.append(_dateFormat.format(warehouseItem.getModifiedDate()));
			}

			return sb.toString();
		}

		if (entityFieldName.equals("quantity")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("reservedQuantity")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("sku")) {
			sb.append("'");
			sb.append(String.valueOf(warehouseItem.getSku()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("warehouseExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					warehouseItem.getWarehouseExternalReferenceCode()));
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

	protected WarehouseItem randomWarehouseItem() throws Exception {
		return new WarehouseItem() {
			{
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				modifiedDate = RandomTestUtil.nextDate();
				quantity = RandomTestUtil.randomInt();
				reservedQuantity = RandomTestUtil.randomInt();
				sku = StringUtil.toLowerCase(RandomTestUtil.randomString());
				warehouseExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				warehouseId = RandomTestUtil.randomLong();
			}
		};
	}

	protected WarehouseItem randomIrrelevantWarehouseItem() throws Exception {
		WarehouseItem randomIrrelevantWarehouseItem = randomWarehouseItem();

		return randomIrrelevantWarehouseItem;
	}

	protected WarehouseItem randomPatchWarehouseItem() throws Exception {
		return randomWarehouseItem();
	}

	protected WarehouseItemResource warehouseItemResource;
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

	private static final Log _log = LogFactoryUtil.getLog(
		BaseWarehouseItemResourceTestCase.class);

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
	private com.liferay.headless.commerce.admin.inventory.resource.v1_0.
		WarehouseItemResource _warehouseItemResource;

}