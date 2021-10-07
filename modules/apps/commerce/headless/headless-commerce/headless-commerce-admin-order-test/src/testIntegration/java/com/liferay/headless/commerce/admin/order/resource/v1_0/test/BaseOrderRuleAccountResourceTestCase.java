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

import com.liferay.headless.commerce.admin.order.client.dto.v1_0.OrderRuleAccount;
import com.liferay.headless.commerce.admin.order.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.order.client.pagination.Page;
import com.liferay.headless.commerce.admin.order.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.order.client.resource.v1_0.OrderRuleAccountResource;
import com.liferay.headless.commerce.admin.order.client.serdes.v1_0.OrderRuleAccountSerDes;
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
public abstract class BaseOrderRuleAccountResourceTestCase {

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

		_orderRuleAccountResource.setContextCompany(testCompany);

		OrderRuleAccountResource.Builder builder =
			OrderRuleAccountResource.builder();

		orderRuleAccountResource = builder.authentication(
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

		OrderRuleAccount orderRuleAccount1 = randomOrderRuleAccount();

		String json = objectMapper.writeValueAsString(orderRuleAccount1);

		OrderRuleAccount orderRuleAccount2 = OrderRuleAccountSerDes.toDTO(json);

		Assert.assertTrue(equals(orderRuleAccount1, orderRuleAccount2));
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

		OrderRuleAccount orderRuleAccount = randomOrderRuleAccount();

		String json1 = objectMapper.writeValueAsString(orderRuleAccount);
		String json2 = OrderRuleAccountSerDes.toJSON(orderRuleAccount);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		OrderRuleAccount orderRuleAccount = randomOrderRuleAccount();

		orderRuleAccount.setAccountExternalReferenceCode(regex);
		orderRuleAccount.setOrderRuleExternalReferenceCode(regex);

		String json = OrderRuleAccountSerDes.toJSON(orderRuleAccount);

		Assert.assertFalse(json.contains(regex));

		orderRuleAccount = OrderRuleAccountSerDes.toDTO(json);

		Assert.assertEquals(
			regex, orderRuleAccount.getAccountExternalReferenceCode());
		Assert.assertEquals(
			regex, orderRuleAccount.getOrderRuleExternalReferenceCode());
	}

	@Test
	public void testDeleteOrderRuleAccount() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGraphQLDeleteOrderRuleAccount() throws Exception {
		Assert.assertTrue(false);
	}

	@Test
	public void testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage()
		throws Exception {

		String externalReferenceCode =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage_getIrrelevantExternalReferenceCode();

		Page<OrderRuleAccount> page =
			orderRuleAccountResource.
				getOrderRuleByExternalReferenceCodeOrderRuleAccountsPage(
					externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantExternalReferenceCode != null) {
			OrderRuleAccount irrelevantOrderRuleAccount =
				testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage_addOrderRuleAccount(
					irrelevantExternalReferenceCode,
					randomIrrelevantOrderRuleAccount());

			page =
				orderRuleAccountResource.
					getOrderRuleByExternalReferenceCodeOrderRuleAccountsPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantOrderRuleAccount),
				(List<OrderRuleAccount>)page.getItems());
			assertValid(page);
		}

		OrderRuleAccount orderRuleAccount1 =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage_addOrderRuleAccount(
				externalReferenceCode, randomOrderRuleAccount());

		OrderRuleAccount orderRuleAccount2 =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage_addOrderRuleAccount(
				externalReferenceCode, randomOrderRuleAccount());

		page =
			orderRuleAccountResource.
				getOrderRuleByExternalReferenceCodeOrderRuleAccountsPage(
					externalReferenceCode, Pagination.of(1, 10));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(orderRuleAccount1, orderRuleAccount2),
			(List<OrderRuleAccount>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage_getExternalReferenceCode();

		OrderRuleAccount orderRuleAccount1 =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage_addOrderRuleAccount(
				externalReferenceCode, randomOrderRuleAccount());

		OrderRuleAccount orderRuleAccount2 =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage_addOrderRuleAccount(
				externalReferenceCode, randomOrderRuleAccount());

		OrderRuleAccount orderRuleAccount3 =
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage_addOrderRuleAccount(
				externalReferenceCode, randomOrderRuleAccount());

		Page<OrderRuleAccount> page1 =
			orderRuleAccountResource.
				getOrderRuleByExternalReferenceCodeOrderRuleAccountsPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<OrderRuleAccount> orderRuleAccounts1 =
			(List<OrderRuleAccount>)page1.getItems();

		Assert.assertEquals(
			orderRuleAccounts1.toString(), 2, orderRuleAccounts1.size());

		Page<OrderRuleAccount> page2 =
			orderRuleAccountResource.
				getOrderRuleByExternalReferenceCodeOrderRuleAccountsPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<OrderRuleAccount> orderRuleAccounts2 =
			(List<OrderRuleAccount>)page2.getItems();

		Assert.assertEquals(
			orderRuleAccounts2.toString(), 1, orderRuleAccounts2.size());

		Page<OrderRuleAccount> page3 =
			orderRuleAccountResource.
				getOrderRuleByExternalReferenceCodeOrderRuleAccountsPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				orderRuleAccount1, orderRuleAccount2, orderRuleAccount3),
			(List<OrderRuleAccount>)page3.getItems());
	}

	protected OrderRuleAccount
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage_addOrderRuleAccount(
				String externalReferenceCode, OrderRuleAccount orderRuleAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostOrderRuleByExternalReferenceCodeOrderRuleAccount()
		throws Exception {

		OrderRuleAccount randomOrderRuleAccount = randomOrderRuleAccount();

		OrderRuleAccount postOrderRuleAccount =
			testPostOrderRuleByExternalReferenceCodeOrderRuleAccount_addOrderRuleAccount(
				randomOrderRuleAccount);

		assertEquals(randomOrderRuleAccount, postOrderRuleAccount);
		assertValid(postOrderRuleAccount);
	}

	protected OrderRuleAccount
			testPostOrderRuleByExternalReferenceCodeOrderRuleAccount_addOrderRuleAccount(
				OrderRuleAccount orderRuleAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetOrderRuleIdOrderRuleAccountsPage() throws Exception {
		Long id = testGetOrderRuleIdOrderRuleAccountsPage_getId();
		Long irrelevantId =
			testGetOrderRuleIdOrderRuleAccountsPage_getIrrelevantId();

		Page<OrderRuleAccount> page =
			orderRuleAccountResource.getOrderRuleIdOrderRuleAccountsPage(
				id, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantId != null) {
			OrderRuleAccount irrelevantOrderRuleAccount =
				testGetOrderRuleIdOrderRuleAccountsPage_addOrderRuleAccount(
					irrelevantId, randomIrrelevantOrderRuleAccount());

			page = orderRuleAccountResource.getOrderRuleIdOrderRuleAccountsPage(
				irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantOrderRuleAccount),
				(List<OrderRuleAccount>)page.getItems());
			assertValid(page);
		}

		OrderRuleAccount orderRuleAccount1 =
			testGetOrderRuleIdOrderRuleAccountsPage_addOrderRuleAccount(
				id, randomOrderRuleAccount());

		OrderRuleAccount orderRuleAccount2 =
			testGetOrderRuleIdOrderRuleAccountsPage_addOrderRuleAccount(
				id, randomOrderRuleAccount());

		page = orderRuleAccountResource.getOrderRuleIdOrderRuleAccountsPage(
			id, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(orderRuleAccount1, orderRuleAccount2),
			(List<OrderRuleAccount>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetOrderRuleIdOrderRuleAccountsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetOrderRuleIdOrderRuleAccountsPage_getId();

		OrderRuleAccount orderRuleAccount1 = randomOrderRuleAccount();

		orderRuleAccount1 =
			testGetOrderRuleIdOrderRuleAccountsPage_addOrderRuleAccount(
				id, orderRuleAccount1);

		for (EntityField entityField : entityFields) {
			Page<OrderRuleAccount> page =
				orderRuleAccountResource.getOrderRuleIdOrderRuleAccountsPage(
					id, null,
					getFilterString(entityField, "between", orderRuleAccount1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(orderRuleAccount1),
				(List<OrderRuleAccount>)page.getItems());
		}
	}

	@Test
	public void testGetOrderRuleIdOrderRuleAccountsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetOrderRuleIdOrderRuleAccountsPage_getId();

		OrderRuleAccount orderRuleAccount1 =
			testGetOrderRuleIdOrderRuleAccountsPage_addOrderRuleAccount(
				id, randomOrderRuleAccount());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		OrderRuleAccount orderRuleAccount2 =
			testGetOrderRuleIdOrderRuleAccountsPage_addOrderRuleAccount(
				id, randomOrderRuleAccount());

		for (EntityField entityField : entityFields) {
			Page<OrderRuleAccount> page =
				orderRuleAccountResource.getOrderRuleIdOrderRuleAccountsPage(
					id, null,
					getFilterString(entityField, "eq", orderRuleAccount1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(orderRuleAccount1),
				(List<OrderRuleAccount>)page.getItems());
		}
	}

	@Test
	public void testGetOrderRuleIdOrderRuleAccountsPageWithPagination()
		throws Exception {

		Long id = testGetOrderRuleIdOrderRuleAccountsPage_getId();

		OrderRuleAccount orderRuleAccount1 =
			testGetOrderRuleIdOrderRuleAccountsPage_addOrderRuleAccount(
				id, randomOrderRuleAccount());

		OrderRuleAccount orderRuleAccount2 =
			testGetOrderRuleIdOrderRuleAccountsPage_addOrderRuleAccount(
				id, randomOrderRuleAccount());

		OrderRuleAccount orderRuleAccount3 =
			testGetOrderRuleIdOrderRuleAccountsPage_addOrderRuleAccount(
				id, randomOrderRuleAccount());

		Page<OrderRuleAccount> page1 =
			orderRuleAccountResource.getOrderRuleIdOrderRuleAccountsPage(
				id, null, null, Pagination.of(1, 2), null);

		List<OrderRuleAccount> orderRuleAccounts1 =
			(List<OrderRuleAccount>)page1.getItems();

		Assert.assertEquals(
			orderRuleAccounts1.toString(), 2, orderRuleAccounts1.size());

		Page<OrderRuleAccount> page2 =
			orderRuleAccountResource.getOrderRuleIdOrderRuleAccountsPage(
				id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<OrderRuleAccount> orderRuleAccounts2 =
			(List<OrderRuleAccount>)page2.getItems();

		Assert.assertEquals(
			orderRuleAccounts2.toString(), 1, orderRuleAccounts2.size());

		Page<OrderRuleAccount> page3 =
			orderRuleAccountResource.getOrderRuleIdOrderRuleAccountsPage(
				id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				orderRuleAccount1, orderRuleAccount2, orderRuleAccount3),
			(List<OrderRuleAccount>)page3.getItems());
	}

	@Test
	public void testGetOrderRuleIdOrderRuleAccountsPageWithSortDateTime()
		throws Exception {

		testGetOrderRuleIdOrderRuleAccountsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, orderRuleAccount1, orderRuleAccount2) -> {
				BeanUtils.setProperty(
					orderRuleAccount1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetOrderRuleIdOrderRuleAccountsPageWithSortInteger()
		throws Exception {

		testGetOrderRuleIdOrderRuleAccountsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, orderRuleAccount1, orderRuleAccount2) -> {
				BeanUtils.setProperty(
					orderRuleAccount1, entityField.getName(), 0);
				BeanUtils.setProperty(
					orderRuleAccount2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetOrderRuleIdOrderRuleAccountsPageWithSortString()
		throws Exception {

		testGetOrderRuleIdOrderRuleAccountsPageWithSort(
			EntityField.Type.STRING,
			(entityField, orderRuleAccount1, orderRuleAccount2) -> {
				Class<?> clazz = orderRuleAccount1.getClass();

				String entityFieldName = entityField.getName();

				java.lang.reflect.Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						orderRuleAccount1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						orderRuleAccount2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						orderRuleAccount1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						orderRuleAccount2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						orderRuleAccount1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						orderRuleAccount2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetOrderRuleIdOrderRuleAccountsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, OrderRuleAccount, OrderRuleAccount, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetOrderRuleIdOrderRuleAccountsPage_getId();

		OrderRuleAccount orderRuleAccount1 = randomOrderRuleAccount();
		OrderRuleAccount orderRuleAccount2 = randomOrderRuleAccount();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, orderRuleAccount1, orderRuleAccount2);
		}

		orderRuleAccount1 =
			testGetOrderRuleIdOrderRuleAccountsPage_addOrderRuleAccount(
				id, orderRuleAccount1);

		orderRuleAccount2 =
			testGetOrderRuleIdOrderRuleAccountsPage_addOrderRuleAccount(
				id, orderRuleAccount2);

		for (EntityField entityField : entityFields) {
			Page<OrderRuleAccount> ascPage =
				orderRuleAccountResource.getOrderRuleIdOrderRuleAccountsPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(orderRuleAccount1, orderRuleAccount2),
				(List<OrderRuleAccount>)ascPage.getItems());

			Page<OrderRuleAccount> descPage =
				orderRuleAccountResource.getOrderRuleIdOrderRuleAccountsPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(orderRuleAccount2, orderRuleAccount1),
				(List<OrderRuleAccount>)descPage.getItems());
		}
	}

	protected OrderRuleAccount
			testGetOrderRuleIdOrderRuleAccountsPage_addOrderRuleAccount(
				Long id, OrderRuleAccount orderRuleAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrderRuleIdOrderRuleAccountsPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrderRuleIdOrderRuleAccountsPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostOrderRuleIdOrderRuleAccount() throws Exception {
		OrderRuleAccount randomOrderRuleAccount = randomOrderRuleAccount();

		OrderRuleAccount postOrderRuleAccount =
			testPostOrderRuleIdOrderRuleAccount_addOrderRuleAccount(
				randomOrderRuleAccount);

		assertEquals(randomOrderRuleAccount, postOrderRuleAccount);
		assertValid(postOrderRuleAccount);
	}

	protected OrderRuleAccount
			testPostOrderRuleIdOrderRuleAccount_addOrderRuleAccount(
				OrderRuleAccount orderRuleAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertContains(
		OrderRuleAccount orderRuleAccount,
		List<OrderRuleAccount> orderRuleAccounts) {

		boolean contains = false;

		for (OrderRuleAccount item : orderRuleAccounts) {
			if (equals(orderRuleAccount, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			orderRuleAccounts + " does not contain " + orderRuleAccount,
			contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		OrderRuleAccount orderRuleAccount1,
		OrderRuleAccount orderRuleAccount2) {

		Assert.assertTrue(
			orderRuleAccount1 + " does not equal " + orderRuleAccount2,
			equals(orderRuleAccount1, orderRuleAccount2));
	}

	protected void assertEquals(
		List<OrderRuleAccount> orderRuleAccounts1,
		List<OrderRuleAccount> orderRuleAccounts2) {

		Assert.assertEquals(
			orderRuleAccounts1.size(), orderRuleAccounts2.size());

		for (int i = 0; i < orderRuleAccounts1.size(); i++) {
			OrderRuleAccount orderRuleAccount1 = orderRuleAccounts1.get(i);
			OrderRuleAccount orderRuleAccount2 = orderRuleAccounts2.get(i);

			assertEquals(orderRuleAccount1, orderRuleAccount2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<OrderRuleAccount> orderRuleAccounts1,
		List<OrderRuleAccount> orderRuleAccounts2) {

		Assert.assertEquals(
			orderRuleAccounts1.size(), orderRuleAccounts2.size());

		for (OrderRuleAccount orderRuleAccount1 : orderRuleAccounts1) {
			boolean contains = false;

			for (OrderRuleAccount orderRuleAccount2 : orderRuleAccounts2) {
				if (equals(orderRuleAccount1, orderRuleAccount2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				orderRuleAccounts2 + " does not contain " + orderRuleAccount1,
				contains);
		}
	}

	protected void assertValid(OrderRuleAccount orderRuleAccount)
		throws Exception {

		boolean valid = true;

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("account", additionalAssertFieldName)) {
				if (orderRuleAccount.getAccount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"accountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (orderRuleAccount.getAccountExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (orderRuleAccount.getAccountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (orderRuleAccount.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleAccountId", additionalAssertFieldName)) {

				if (orderRuleAccount.getOrderRuleAccountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleExternalReferenceCode",
					additionalAssertFieldName)) {

				if (orderRuleAccount.getOrderRuleExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("orderRuleId", additionalAssertFieldName)) {
				if (orderRuleAccount.getOrderRuleId() == null) {
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

	protected void assertValid(Page<OrderRuleAccount> page) {
		boolean valid = false;

		java.util.Collection<OrderRuleAccount> orderRuleAccounts =
			page.getItems();

		int size = orderRuleAccounts.size();

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
						OrderRuleAccount.class)) {

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
		OrderRuleAccount orderRuleAccount1,
		OrderRuleAccount orderRuleAccount2) {

		if (orderRuleAccount1 == orderRuleAccount2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("account", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRuleAccount1.getAccount(),
						orderRuleAccount2.getAccount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"accountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderRuleAccount1.getAccountExternalReferenceCode(),
						orderRuleAccount2.getAccountExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRuleAccount1.getAccountId(),
						orderRuleAccount2.getAccountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)orderRuleAccount1.getActions(),
						(Map)orderRuleAccount2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleAccountId", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderRuleAccount1.getOrderRuleAccountId(),
						orderRuleAccount2.getOrderRuleAccountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"orderRuleExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						orderRuleAccount1.getOrderRuleExternalReferenceCode(),
						orderRuleAccount2.
							getOrderRuleExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("orderRuleId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						orderRuleAccount1.getOrderRuleId(),
						orderRuleAccount2.getOrderRuleId())) {

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

		if (!(_orderRuleAccountResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_orderRuleAccountResource;

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
		OrderRuleAccount orderRuleAccount) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("account")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("accountExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					orderRuleAccount.getAccountExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("accountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("actions")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderRuleAccountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("orderRuleExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					orderRuleAccount.getOrderRuleExternalReferenceCode()));
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

	protected OrderRuleAccount randomOrderRuleAccount() throws Exception {
		return new OrderRuleAccount() {
			{
				accountExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				accountId = RandomTestUtil.randomLong();
				orderRuleAccountId = RandomTestUtil.randomLong();
				orderRuleExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				orderRuleId = RandomTestUtil.randomLong();
			}
		};
	}

	protected OrderRuleAccount randomIrrelevantOrderRuleAccount()
		throws Exception {

		OrderRuleAccount randomIrrelevantOrderRuleAccount =
			randomOrderRuleAccount();

		return randomIrrelevantOrderRuleAccount;
	}

	protected OrderRuleAccount randomPatchOrderRuleAccount() throws Exception {
		return randomOrderRuleAccount();
	}

	protected OrderRuleAccountResource orderRuleAccountResource;
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
		LogFactoryUtil.getLog(BaseOrderRuleAccountResourceTestCase.class);

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
		OrderRuleAccountResource _orderRuleAccountResource;

}