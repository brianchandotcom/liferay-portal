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

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRuleAccountGroup;
import com.liferay.headless.commerce.admin.order.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.headless.commerce.admin.order.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderRuleAccountGroupResource;
import com.liferay.headless.commerce.admin.order.client.serdes.v1_0.OrderRuleAccountGroupSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
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
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

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
public abstract class BaseOrderRuleAccountGroupResourceTestCase {

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

		_orderRuleAccountGroupResource.setContextCompany(testCompany);

		OrderRuleAccountGroupResource.Builder builder =
			OrderRuleAccountGroupResource.builder();

		orderRuleAccountGroupResource = builder.authentication(
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

		OrderRuleAccountGroup orderRuleAccountGroup1 =
			randomOrderRuleAccountGroup();

		String json = objectMapper.writeValueAsString(orderRuleAccountGroup1);

		OrderRuleAccountGroup orderRuleAccountGroup2 =
			OrderRuleAccountGroupSerDes.toDTO(json);

		Assert.assertTrue(
			equals(orderRuleAccountGroup1, orderRuleAccountGroup2));
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

		OrderRuleAccountGroup orderRuleAccountGroup =
			randomOrderRuleAccountGroup();

		String json1 = objectMapper.writeValueAsString(orderRuleAccountGroup);
		String json2 = OrderRuleAccountGroupSerDes.toJSON(
			orderRuleAccountGroup);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		OrderRuleAccountGroup orderRuleAccountGroup =
			randomOrderRuleAccountGroup();

		orderRuleAccountGroup.setAccountGroupExternalReferenceCode(regex);
		orderRuleAccountGroup.setOrderRuleExternalReferenceCode(regex);

		String json = OrderRuleAccountGroupSerDes.toJSON(orderRuleAccountGroup);

		Assert.assertFalse(json.contains(regex));

		orderRuleAccountGroup = OrderRuleAccountGroupSerDes.toDTO(json);

		Assert.assertEquals(
			regex,
			orderRuleAccountGroup.getAccountGroupExternalReferenceCode());
		Assert.assertEquals(
			regex, orderRuleAccountGroup.getOrderRuleExternalReferenceCode());
	}

	@Test
	public void testDeleteOrderRuleAccountGroup() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLDeleteOrderRuleAccountGroup() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage()
		throws Exception {

		String externalReferenceCode =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage_getIrrelevantExternalReferenceCode();

		Page<OrderRuleAccountGroup> page =
			orderRuleAccountGroupResource.
				getOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage(
					externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantExternalReferenceCode != null) {
			OrderRuleAccountGroup irrelevantOrderRuleAccountGroup =
				testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
					irrelevantExternalReferenceCode,
					randomIrrelevantOrderRuleAccountGroup());

			page =
				orderRuleAccountGroupResource.
					getOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantOrderRuleAccountGroup),
				(List<OrderRuleAccountGroup>)page.getItems());
			assertValid(page);
		}

		OrderRuleAccountGroup orderRuleAccountGroup1 =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				externalReferenceCode, randomOrderRuleAccountGroup());

		OrderRuleAccountGroup orderRuleAccountGroup2 =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				externalReferenceCode, randomOrderRuleAccountGroup());

		page =
			orderRuleAccountGroupResource.
				getOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage(
					externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(orderRuleAccountGroup1, orderRuleAccountGroup2),
			(List<OrderRuleAccountGroup>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage_getExternalReferenceCode();

		OrderRuleAccountGroup orderRuleAccountGroup1 =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				externalReferenceCode, randomOrderRuleAccountGroup());

		OrderRuleAccountGroup orderRuleAccountGroup2 =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				externalReferenceCode, randomOrderRuleAccountGroup());

		OrderRuleAccountGroup orderRuleAccountGroup3 =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				externalReferenceCode, randomOrderRuleAccountGroup());

		Page<OrderRuleAccountGroup> page1 =
			orderRuleAccountGroupResource.
				getOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<OrderRuleAccountGroup> orderRuleAccountGroups1 =
			(List<OrderRuleAccountGroup>)page1.getItems();

		Assert.assertEquals(
			orderRuleAccountGroups1.toString(), 2,
			orderRuleAccountGroups1.size());

		Page<OrderRuleAccountGroup> page2 =
			orderRuleAccountGroupResource.
				getOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<OrderRuleAccountGroup> orderRuleAccountGroups2 =
			(List<OrderRuleAccountGroup>)page2.getItems();

		Assert.assertEquals(
			orderRuleAccountGroups2.toString(), 1,
			orderRuleAccountGroups2.size());

		Page<OrderRuleAccountGroup> page3 =
			orderRuleAccountGroupResource.
				getOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				orderRuleAccountGroup1, orderRuleAccountGroup2,
				orderRuleAccountGroup3),
			(List<OrderRuleAccountGroup>)page3.getItems());
	}

	protected OrderRuleAccountGroup
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				String externalReferenceCode,
				OrderRuleAccountGroup orderRuleAccountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostOrderRuleByExternalReferenceCodeOrderRuleAccountGroup()
		throws Exception {

		OrderRuleAccountGroup randomOrderRuleAccountGroup =
			randomOrderRuleAccountGroup();

		OrderRuleAccountGroup postOrderRuleAccountGroup =
			testPostOrderRuleByExternalReferenceCodeOrderRuleAccountGroup_addOrderRuleAccountGroup(
				randomOrderRuleAccountGroup);

		assertEquals(randomOrderRuleAccountGroup, postOrderRuleAccountGroup);
		assertValid(postOrderRuleAccountGroup);
	}

	protected OrderRuleAccountGroup
			testPostOrderRuleByExternalReferenceCodeOrderRuleAccountGroup_addOrderRuleAccountGroup(
				OrderRuleAccountGroup orderRuleAccountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetOrderRuleIdOrderRuleAccountGroupsPage()
		throws Exception {

		Long id = testGetOrderRuleIdOrderRuleAccountGroupsPage_getId();
		Long irrelevantId =
			testGetOrderRuleIdOrderRuleAccountGroupsPage_getIrrelevantId();

		Page<OrderRuleAccountGroup> page =
			orderRuleAccountGroupResource.
				getOrderRuleIdOrderRuleAccountGroupsPage(
					id, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantId != null) {
			OrderRuleAccountGroup irrelevantOrderRuleAccountGroup =
				testGetOrderRuleIdOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
					irrelevantId, randomIrrelevantOrderRuleAccountGroup());

			page =
				orderRuleAccountGroupResource.
					getOrderRuleIdOrderRuleAccountGroupsPage(
						irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantOrderRuleAccountGroup),
				(List<OrderRuleAccountGroup>)page.getItems());
			assertValid(page);
		}

		OrderRuleAccountGroup orderRuleAccountGroup1 =
			testGetOrderRuleIdOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				id, randomOrderRuleAccountGroup());

		OrderRuleAccountGroup orderRuleAccountGroup2 =
			testGetOrderRuleIdOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				id, randomOrderRuleAccountGroup());

		page =
			orderRuleAccountGroupResource.
				getOrderRuleIdOrderRuleAccountGroupsPage(
					id, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(orderRuleAccountGroup1, orderRuleAccountGroup2),
			(List<OrderRuleAccountGroup>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetOrderRuleIdOrderRuleAccountGroupsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetOrderRuleIdOrderRuleAccountGroupsPage_getId();

		OrderRuleAccountGroup orderRuleAccountGroup1 =
			randomOrderRuleAccountGroup();

		orderRuleAccountGroup1 =
			testGetOrderRuleIdOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				id, orderRuleAccountGroup1);

		for (EntityField entityField : entityFields) {
			Page<OrderRuleAccountGroup> page =
				orderRuleAccountGroupResource.
					getOrderRuleIdOrderRuleAccountGroupsPage(
						id, null,
						getFilterString(
							entityField, "between", orderRuleAccountGroup1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(orderRuleAccountGroup1),
				(List<OrderRuleAccountGroup>)page.getItems());
		}
	}

	@Test
	public void testGetOrderRuleIdOrderRuleAccountGroupsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetOrderRuleIdOrderRuleAccountGroupsPage_getId();

		OrderRuleAccountGroup orderRuleAccountGroup1 =
			testGetOrderRuleIdOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				id, randomOrderRuleAccountGroup());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		OrderRuleAccountGroup orderRuleAccountGroup2 =
			testGetOrderRuleIdOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				id, randomOrderRuleAccountGroup());

		for (EntityField entityField : entityFields) {
			Page<OrderRuleAccountGroup> page =
				orderRuleAccountGroupResource.
					getOrderRuleIdOrderRuleAccountGroupsPage(
						id, null,
						getFilterString(
							entityField, "eq", orderRuleAccountGroup1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(orderRuleAccountGroup1),
				(List<OrderRuleAccountGroup>)page.getItems());
		}
	}

	@Test
	public void testGetOrderRuleIdOrderRuleAccountGroupsPageWithPagination()
		throws Exception {

		Long id = testGetOrderRuleIdOrderRuleAccountGroupsPage_getId();

		OrderRuleAccountGroup orderRuleAccountGroup1 =
			testGetOrderRuleIdOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				id, randomOrderRuleAccountGroup());

		OrderRuleAccountGroup orderRuleAccountGroup2 =
			testGetOrderRuleIdOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				id, randomOrderRuleAccountGroup());

		OrderRuleAccountGroup orderRuleAccountGroup3 =
			testGetOrderRuleIdOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				id, randomOrderRuleAccountGroup());

		Page<OrderRuleAccountGroup> page1 =
			orderRuleAccountGroupResource.
				getOrderRuleIdOrderRuleAccountGroupsPage(
					id, null, null, Pagination.of(1, 2), null);

		List<OrderRuleAccountGroup> orderRuleAccountGroups1 =
			(List<OrderRuleAccountGroup>)page1.getItems();

		Assert.assertEquals(
			orderRuleAccountGroups1.toString(), 2,
			orderRuleAccountGroups1.size());

		Page<OrderRuleAccountGroup> page2 =
			orderRuleAccountGroupResource.
				getOrderRuleIdOrderRuleAccountGroupsPage(
					id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<OrderRuleAccountGroup> orderRuleAccountGroups2 =
			(List<OrderRuleAccountGroup>)page2.getItems();

		Assert.assertEquals(
			orderRuleAccountGroups2.toString(), 1,
			orderRuleAccountGroups2.size());

		Page<OrderRuleAccountGroup> page3 =
			orderRuleAccountGroupResource.
				getOrderRuleIdOrderRuleAccountGroupsPage(
					id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				orderRuleAccountGroup1, orderRuleAccountGroup2,
				orderRuleAccountGroup3),
			(List<OrderRuleAccountGroup>)page3.getItems());
	}

	@Test
	public void testGetOrderRuleIdOrderRuleAccountGroupsPageWithSortDateTime()
		throws Exception {

		testGetOrderRuleIdOrderRuleAccountGroupsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, orderRuleAccountGroup1, orderRuleAccountGroup2) -> {
				BeanUtils.setProperty(
					orderRuleAccountGroup1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetOrderRuleIdOrderRuleAccountGroupsPageWithSortInteger()
		throws Exception {

		testGetOrderRuleIdOrderRuleAccountGroupsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, orderRuleAccountGroup1, orderRuleAccountGroup2) -> {
				BeanUtils.setProperty(
					orderRuleAccountGroup1, entityField.getName(), 0);
				BeanUtils.setProperty(
					orderRuleAccountGroup2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetOrderRuleIdOrderRuleAccountGroupsPageWithSortString()
		throws Exception {

		testGetOrderRuleIdOrderRuleAccountGroupsPageWithSort(
			EntityField.Type.STRING,
			(entityField, orderRuleAccountGroup1, orderRuleAccountGroup2) -> {
				Class<?> clazz = orderRuleAccountGroup1.getClass();

				String entityFieldName = entityField.getName();

				java.lang.reflect.Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						orderRuleAccountGroup1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						orderRuleAccountGroup2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						orderRuleAccountGroup1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						orderRuleAccountGroup2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						orderRuleAccountGroup1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						orderRuleAccountGroup2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetOrderRuleIdOrderRuleAccountGroupsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, OrderRuleAccountGroup, OrderRuleAccountGroup,
				 Exception> unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetOrderRuleIdOrderRuleAccountGroupsPage_getId();

		OrderRuleAccountGroup orderRuleAccountGroup1 =
			randomOrderRuleAccountGroup();
		OrderRuleAccountGroup orderRuleAccountGroup2 =
			randomOrderRuleAccountGroup();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, orderRuleAccountGroup1, orderRuleAccountGroup2);
		}

		orderRuleAccountGroup1 =
			testGetOrderRuleIdOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				id, orderRuleAccountGroup1);

		orderRuleAccountGroup2 =
			testGetOrderRuleIdOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				id, orderRuleAccountGroup2);

		for (EntityField entityField : entityFields) {
			Page<OrderRuleAccountGroup> ascPage =
				orderRuleAccountGroupResource.
					getOrderRuleIdOrderRuleAccountGroupsPage(
						id, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(orderRuleAccountGroup1, orderRuleAccountGroup2),
				(List<OrderRuleAccountGroup>)ascPage.getItems());

			Page<OrderRuleAccountGroup> descPage =
				orderRuleAccountGroupResource.
					getOrderRuleIdOrderRuleAccountGroupsPage(
						id, null, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(orderRuleAccountGroup2, orderRuleAccountGroup1),
				(List<OrderRuleAccountGroup>)descPage.getItems());
		}
	}

	protected OrderRuleAccountGroup
			testGetOrderRuleIdOrderRuleAccountGroupsPage_addOrderRuleAccountGroup(
				Long id, OrderRuleAccountGroup orderRuleAccountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrderRuleIdOrderRuleAccountGroupsPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetOrderRuleIdOrderRuleAccountGroupsPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostOrderRuleIdOrderRuleAccountGroup() throws Exception {
		OrderRuleAccountGroup randomOrderRuleAccountGroup =
			randomOrderRuleAccountGroup();

		OrderRuleAccountGroup postOrderRuleAccountGroup =
			testPostOrderRuleIdOrderRuleAccountGroup_addOrderRuleAccountGroup(
				randomOrderRuleAccountGroup);

		assertEquals(randomOrderRuleAccountGroup, postOrderRuleAccountGroup);
		assertValid(postOrderRuleAccountGroup);
	}

	protected OrderRuleAccountGroup
			testPostOrderRuleIdOrderRuleAccountGroup_addOrderRuleAccountGroup(
				OrderRuleAccountGroup orderRuleAccountGroup)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertContains(
		OrderRuleAccountGroup orderRuleAccountGroup,
		List<OrderRuleAccountGroup> orderRuleAccountGroups) {

		boolean contains = false;

		for (OrderRuleAccountGroup item : orderRuleAccountGroups) {
			if (equals(orderRuleAccountGroup, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			orderRuleAccountGroups + " does not contain " +
				orderRuleAccountGroup,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		OrderRuleAccountGroup orderRuleAccountGroup1,
		OrderRuleAccountGroup orderRuleAccountGroup2) {

		Assert.assertTrue(
			orderRuleAccountGroup1 + " does not equal " +
				orderRuleAccountGroup2,
			equals(orderRuleAccountGroup1, orderRuleAccountGroup2));
	}

	protected void assertEquals(
		List<OrderRuleAccountGroup> orderRuleAccountGroups1,
		List<OrderRuleAccountGroup> orderRuleAccountGroups2) {

		Assert.assertEquals(
			orderRuleAccountGroups1.size(), orderRuleAccountGroups2.size());

		for (int i = 0; i < orderRuleAccountGroups1.size(); i++) {
			OrderRuleAccountGroup orderRuleAccountGroup1 =
				orderRuleAccountGroups1.get(i);
			OrderRuleAccountGroup orderRuleAccountGroup2 =
				orderRuleAccountGroups2.get(i);

			assertEquals(orderRuleAccountGroup1, orderRuleAccountGroup2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<OrderRuleAccountGroup> orderRuleAccountGroups1,
		List<OrderRuleAccountGroup> orderRuleAccountGroups2) {

		Assert.assertEquals(
			orderRuleAccountGroups1.size(), orderRuleAccountGroups2.size());

		for (OrderRuleAccountGroup orderRuleAccountGroup1 :
				orderRuleAccountGroups1) {

			boolean contains = false;

			for (OrderRuleAccountGroup orderRuleAccountGroup2 :
					orderRuleAccountGroups2) {

				if (equals(orderRuleAccountGroup1, orderRuleAccountGroup2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				orderRuleAccountGroups2 + " does not contain " +
					orderRuleAccountGroup1,
				contains);
		}
	}

	protected void assertValid(OrderRuleAccountGroup orderRuleAccountGroup)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountGroup", additionalAssertFieldName)) {
				if (orderRuleAccountGroup.getAccountGroup() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"accountGroupExternalReferenceCode",
					additionalAssertFieldName)) {

				if (orderRuleAccountGroup.
						getAccountGroupExternalReferenceCode() == null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("accountGroupId", additionalAssertFieldName)) {
				if (orderRuleAccountGroup.getAccountGroupId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (orderRuleAccountGroup.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleAccountGroupId", additionalAssertFieldName)) {

				if (orderRuleAccountGroup.getOrderRuleAccountGroupId() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleExternalReferenceCode",
					additionalAssertFieldName)) {

				if (orderRuleAccountGroup.getOrderRuleExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderRuleId", additionalAssertFieldName)) {
				if (orderRuleAccountGroup.getOrderRuleId() == null) {
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

	protected void assertValid(Page<OrderRuleAccountGroup> page) {
		boolean valid = false;

		java.util.Collection<OrderRuleAccountGroup> orderRuleAccountGroups =
			page.getItems();

		int size = orderRuleAccountGroups.size();

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
						OrderRuleAccountGroup.class)) {

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
		OrderRuleAccountGroup orderRuleAccountGroup1,
		OrderRuleAccountGroup orderRuleAccountGroup2) {

		if (orderRuleAccountGroup1 == orderRuleAccountGroup2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountGroup", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRuleAccountGroup1.getAccountGroup(),
						orderRuleAccountGroup2.getAccountGroup())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"accountGroupExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderRuleAccountGroup1.
							getAccountGroupExternalReferenceCode(),
						orderRuleAccountGroup2.
							getAccountGroupExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("accountGroupId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRuleAccountGroup1.getAccountGroupId(),
						orderRuleAccountGroup2.getAccountGroupId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)orderRuleAccountGroup1.getActions(),
						(Map)orderRuleAccountGroup2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleAccountGroupId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderRuleAccountGroup1.getOrderRuleAccountGroupId(),
						orderRuleAccountGroup2.getOrderRuleAccountGroupId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderRuleAccountGroup1.
							getOrderRuleExternalReferenceCode(),
						orderRuleAccountGroup2.
							getOrderRuleExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderRuleId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRuleAccountGroup1.getOrderRuleId(),
						orderRuleAccountGroup2.getOrderRuleId())) {

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

		if (!(_orderRuleAccountGroupResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_orderRuleAccountGroupResource;

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
		EntityField entityField, String operator,
		OrderRuleAccountGroup orderRuleAccountGroup) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("accountGroup")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("accountGroupExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					orderRuleAccountGroup.
						getAccountGroupExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("accountGroupId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderRuleAccountGroupId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderRuleExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					orderRuleAccountGroup.getOrderRuleExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("orderRuleId")) {
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

	protected OrderRuleAccountGroup randomOrderRuleAccountGroup()
		throws Exception {

		return new OrderRuleAccountGroup() {
			{
				accountGroupExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				accountGroupId = RandomTestUtil.randomLong();
				orderRuleAccountGroupId = RandomTestUtil.randomLong();
				orderRuleExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				orderRuleId = RandomTestUtil.randomLong();
			}
		};
	}

	protected OrderRuleAccountGroup randomIrrelevantOrderRuleAccountGroup()
		throws Exception {

		OrderRuleAccountGroup randomIrrelevantOrderRuleAccountGroup =
			randomOrderRuleAccountGroup();

		return randomIrrelevantOrderRuleAccountGroup;
	}

	protected OrderRuleAccountGroup randomPatchOrderRuleAccountGroup()
		throws Exception {

		return randomOrderRuleAccountGroup();
	}

	protected OrderRuleAccountGroupResource orderRuleAccountGroupResource;
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
		LogFactoryUtil.getLog(BaseOrderRuleAccountGroupResourceTestCase.class);

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
	private com.liferay.headless.commerce.admin.order.resource.v1_0.
		OrderRuleAccountGroupResource _orderRuleAccountGroupResource;

}