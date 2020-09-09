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

package com.liferay.headless.commerce.admin.pricing.resource.v2_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.DiscountAccount;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.DiscountAccountResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.DiscountAccountSerDes;
import com.liferay.petra.function.UnsafeTriConsumer;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
 * @author Zoltán Takács
 * @generated
 */
@Generated("")
public abstract class BaseDiscountAccountResourceTestCase {

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

		_discountAccountResource.setContextCompany(testCompany);

		DiscountAccountResource.Builder builder =
			DiscountAccountResource.builder();

		discountAccountResource = builder.authentication(
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

		DiscountAccount discountAccount1 = randomDiscountAccount();

		String json = objectMapper.writeValueAsString(discountAccount1);

		DiscountAccount discountAccount2 = DiscountAccountSerDes.toDTO(json);

		Assert.assertTrue(equals(discountAccount1, discountAccount2));
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

		DiscountAccount discountAccount = randomDiscountAccount();

		String json1 = objectMapper.writeValueAsString(discountAccount);
		String json2 = DiscountAccountSerDes.toJSON(discountAccount);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		DiscountAccount discountAccount = randomDiscountAccount();

		discountAccount.setAccountExternalReferenceCode(regex);
		discountAccount.setDiscountExternalReferenceCode(regex);

		String json = DiscountAccountSerDes.toJSON(discountAccount);

		Assert.assertFalse(json.contains(regex));

		discountAccount = DiscountAccountSerDes.toDTO(json);

		Assert.assertEquals(
			regex, discountAccount.getAccountExternalReferenceCode());
		Assert.assertEquals(
			regex, discountAccount.getDiscountExternalReferenceCode());
	}

	@Test
	public void testDeleteDiscountAccount() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscountAccount discountAccount =
			testDeleteDiscountAccount_addDiscountAccount();

		assertHttpResponseStatusCode(
			204,
			discountAccountResource.deleteDiscountAccountHttpResponse(
				discountAccount.getId()));
	}

	protected DiscountAccount testDeleteDiscountAccount_addDiscountAccount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteDiscountAccount() throws Exception {
		DiscountAccount discountAccount =
			testGraphQLDiscountAccount_addDiscountAccount();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteDiscountAccount",
						new HashMap<String, Object>() {
							{
								put("id", discountAccount.getId());
							}
						})),
				"JSONObject/data", "Object/deleteDiscountAccount"));
	}

	@Test
	public void testGetDiscountByExternalReferenceCodeDiscountAccountsPage()
		throws Exception {

		Page<DiscountAccount> page =
			discountAccountResource.
				getDiscountByExternalReferenceCodeDiscountAccountsPage(
					testGetDiscountByExternalReferenceCodeDiscountAccountsPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountAccountsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountAccountsPage_getIrrelevantExternalReferenceCode();

		if ((irrelevantExternalReferenceCode != null)) {
			DiscountAccount irrelevantDiscountAccount =
				testGetDiscountByExternalReferenceCodeDiscountAccountsPage_addDiscountAccount(
					irrelevantExternalReferenceCode,
					randomIrrelevantDiscountAccount());

			page =
				discountAccountResource.
					getDiscountByExternalReferenceCodeDiscountAccountsPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscountAccount),
				(List<DiscountAccount>)page.getItems());
			assertValid(page);
		}

		DiscountAccount discountAccount1 =
			testGetDiscountByExternalReferenceCodeDiscountAccountsPage_addDiscountAccount(
				externalReferenceCode, randomDiscountAccount());

		DiscountAccount discountAccount2 =
			testGetDiscountByExternalReferenceCodeDiscountAccountsPage_addDiscountAccount(
				externalReferenceCode, randomDiscountAccount());

		page =
			discountAccountResource.
				getDiscountByExternalReferenceCodeDiscountAccountsPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountAccount1, discountAccount2),
			(List<DiscountAccount>)page.getItems());
		assertValid(page);

		discountAccountResource.deleteDiscountAccount(discountAccount1.getId());

		discountAccountResource.deleteDiscountAccount(discountAccount2.getId());
	}

	@Test
	public void testGetDiscountByExternalReferenceCodeDiscountAccountsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetDiscountByExternalReferenceCodeDiscountAccountsPage_getExternalReferenceCode();

		DiscountAccount discountAccount1 =
			testGetDiscountByExternalReferenceCodeDiscountAccountsPage_addDiscountAccount(
				externalReferenceCode, randomDiscountAccount());

		DiscountAccount discountAccount2 =
			testGetDiscountByExternalReferenceCodeDiscountAccountsPage_addDiscountAccount(
				externalReferenceCode, randomDiscountAccount());

		DiscountAccount discountAccount3 =
			testGetDiscountByExternalReferenceCodeDiscountAccountsPage_addDiscountAccount(
				externalReferenceCode, randomDiscountAccount());

		Page<DiscountAccount> page1 =
			discountAccountResource.
				getDiscountByExternalReferenceCodeDiscountAccountsPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<DiscountAccount> discountAccounts1 =
			(List<DiscountAccount>)page1.getItems();

		Assert.assertEquals(
			discountAccounts1.toString(), 2, discountAccounts1.size());

		Page<DiscountAccount> page2 =
			discountAccountResource.
				getDiscountByExternalReferenceCodeDiscountAccountsPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscountAccount> discountAccounts2 =
			(List<DiscountAccount>)page2.getItems();

		Assert.assertEquals(
			discountAccounts2.toString(), 1, discountAccounts2.size());

		Page<DiscountAccount> page3 =
			discountAccountResource.
				getDiscountByExternalReferenceCodeDiscountAccountsPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(discountAccount1, discountAccount2, discountAccount3),
			(List<DiscountAccount>)page3.getItems());
	}

	protected DiscountAccount
			testGetDiscountByExternalReferenceCodeDiscountAccountsPage_addDiscountAccount(
				String externalReferenceCode, DiscountAccount discountAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDiscountByExternalReferenceCodeDiscountAccountsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetDiscountByExternalReferenceCodeDiscountAccountsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDiscountByExternalReferenceCodeDiscountAccount()
		throws Exception {

		DiscountAccount randomDiscountAccount = randomDiscountAccount();

		DiscountAccount postDiscountAccount =
			testPostDiscountByExternalReferenceCodeDiscountAccount_addDiscountAccount(
				randomDiscountAccount);

		assertEquals(randomDiscountAccount, postDiscountAccount);
		assertValid(postDiscountAccount);
	}

	protected DiscountAccount
			testPostDiscountByExternalReferenceCodeDiscountAccount_addDiscountAccount(
				DiscountAccount discountAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetDiscountIdDiscountAccountsPage() throws Exception {
		Page<DiscountAccount> page =
			discountAccountResource.getDiscountIdDiscountAccountsPage(
				testGetDiscountIdDiscountAccountsPage_getId(),
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetDiscountIdDiscountAccountsPage_getId();
		Long irrelevantId =
			testGetDiscountIdDiscountAccountsPage_getIrrelevantId();

		if ((irrelevantId != null)) {
			DiscountAccount irrelevantDiscountAccount =
				testGetDiscountIdDiscountAccountsPage_addDiscountAccount(
					irrelevantId, randomIrrelevantDiscountAccount());

			page = discountAccountResource.getDiscountIdDiscountAccountsPage(
				irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantDiscountAccount),
				(List<DiscountAccount>)page.getItems());
			assertValid(page);
		}

		DiscountAccount discountAccount1 =
			testGetDiscountIdDiscountAccountsPage_addDiscountAccount(
				id, randomDiscountAccount());

		DiscountAccount discountAccount2 =
			testGetDiscountIdDiscountAccountsPage_addDiscountAccount(
				id, randomDiscountAccount());

		page = discountAccountResource.getDiscountIdDiscountAccountsPage(
			id, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(discountAccount1, discountAccount2),
			(List<DiscountAccount>)page.getItems());
		assertValid(page);

		discountAccountResource.deleteDiscountAccount(discountAccount1.getId());

		discountAccountResource.deleteDiscountAccount(discountAccount2.getId());
	}

	@Test
	public void testGetDiscountIdDiscountAccountsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountAccountsPage_getId();

		DiscountAccount discountAccount1 = randomDiscountAccount();

		discountAccount1 =
			testGetDiscountIdDiscountAccountsPage_addDiscountAccount(
				id, discountAccount1);

		for (EntityField entityField : entityFields) {
			Page<DiscountAccount> page =
				discountAccountResource.getDiscountIdDiscountAccountsPage(
					id, null,
					getFilterString(entityField, "between", discountAccount1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discountAccount1),
				(List<DiscountAccount>)page.getItems());
		}
	}

	@Test
	public void testGetDiscountIdDiscountAccountsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountAccountsPage_getId();

		DiscountAccount discountAccount1 =
			testGetDiscountIdDiscountAccountsPage_addDiscountAccount(
				id, randomDiscountAccount());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		DiscountAccount discountAccount2 =
			testGetDiscountIdDiscountAccountsPage_addDiscountAccount(
				id, randomDiscountAccount());

		for (EntityField entityField : entityFields) {
			Page<DiscountAccount> page =
				discountAccountResource.getDiscountIdDiscountAccountsPage(
					id, null,
					getFilterString(entityField, "eq", discountAccount1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(discountAccount1),
				(List<DiscountAccount>)page.getItems());
		}
	}

	@Test
	public void testGetDiscountIdDiscountAccountsPageWithPagination()
		throws Exception {

		Long id = testGetDiscountIdDiscountAccountsPage_getId();

		DiscountAccount discountAccount1 =
			testGetDiscountIdDiscountAccountsPage_addDiscountAccount(
				id, randomDiscountAccount());

		DiscountAccount discountAccount2 =
			testGetDiscountIdDiscountAccountsPage_addDiscountAccount(
				id, randomDiscountAccount());

		DiscountAccount discountAccount3 =
			testGetDiscountIdDiscountAccountsPage_addDiscountAccount(
				id, randomDiscountAccount());

		Page<DiscountAccount> page1 =
			discountAccountResource.getDiscountIdDiscountAccountsPage(
				id, null, null, Pagination.of(1, 2), null);

		List<DiscountAccount> discountAccounts1 =
			(List<DiscountAccount>)page1.getItems();

		Assert.assertEquals(
			discountAccounts1.toString(), 2, discountAccounts1.size());

		Page<DiscountAccount> page2 =
			discountAccountResource.getDiscountIdDiscountAccountsPage(
				id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<DiscountAccount> discountAccounts2 =
			(List<DiscountAccount>)page2.getItems();

		Assert.assertEquals(
			discountAccounts2.toString(), 1, discountAccounts2.size());

		Page<DiscountAccount> page3 =
			discountAccountResource.getDiscountIdDiscountAccountsPage(
				id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(discountAccount1, discountAccount2, discountAccount3),
			(List<DiscountAccount>)page3.getItems());
	}

	@Test
	public void testGetDiscountIdDiscountAccountsPageWithSortDateTime()
		throws Exception {

		testGetDiscountIdDiscountAccountsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, discountAccount1, discountAccount2) -> {
				BeanUtils.setProperty(
					discountAccount1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetDiscountIdDiscountAccountsPageWithSortInteger()
		throws Exception {

		testGetDiscountIdDiscountAccountsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, discountAccount1, discountAccount2) -> {
				BeanUtils.setProperty(
					discountAccount1, entityField.getName(), 0);
				BeanUtils.setProperty(
					discountAccount2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetDiscountIdDiscountAccountsPageWithSortString()
		throws Exception {

		testGetDiscountIdDiscountAccountsPageWithSort(
			EntityField.Type.STRING,
			(entityField, discountAccount1, discountAccount2) -> {
				Class<?> clazz = discountAccount1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						discountAccount1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						discountAccount2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						discountAccount1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						discountAccount2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						discountAccount1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						discountAccount2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetDiscountIdDiscountAccountsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, DiscountAccount, DiscountAccount, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetDiscountIdDiscountAccountsPage_getId();

		DiscountAccount discountAccount1 = randomDiscountAccount();
		DiscountAccount discountAccount2 = randomDiscountAccount();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, discountAccount1, discountAccount2);
		}

		discountAccount1 =
			testGetDiscountIdDiscountAccountsPage_addDiscountAccount(
				id, discountAccount1);

		discountAccount2 =
			testGetDiscountIdDiscountAccountsPage_addDiscountAccount(
				id, discountAccount2);

		for (EntityField entityField : entityFields) {
			Page<DiscountAccount> ascPage =
				discountAccountResource.getDiscountIdDiscountAccountsPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(discountAccount1, discountAccount2),
				(List<DiscountAccount>)ascPage.getItems());

			Page<DiscountAccount> descPage =
				discountAccountResource.getDiscountIdDiscountAccountsPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(discountAccount2, discountAccount1),
				(List<DiscountAccount>)descPage.getItems());
		}
	}

	protected DiscountAccount
			testGetDiscountIdDiscountAccountsPage_addDiscountAccount(
				Long id, DiscountAccount discountAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDiscountIdDiscountAccountsPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetDiscountIdDiscountAccountsPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostDiscountIdDiscountAccount() throws Exception {
		DiscountAccount randomDiscountAccount = randomDiscountAccount();

		DiscountAccount postDiscountAccount =
			testPostDiscountIdDiscountAccount_addDiscountAccount(
				randomDiscountAccount);

		assertEquals(randomDiscountAccount, postDiscountAccount);
		assertValid(postDiscountAccount);
	}

	protected DiscountAccount
			testPostDiscountIdDiscountAccount_addDiscountAccount(
				DiscountAccount discountAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected DiscountAccount testGraphQLDiscountAccount_addDiscountAccount()
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
		DiscountAccount discountAccount1, DiscountAccount discountAccount2) {

		Assert.assertTrue(
			discountAccount1 + " does not equal " + discountAccount2,
			equals(discountAccount1, discountAccount2));
	}

	protected void assertEquals(
		List<DiscountAccount> discountAccounts1,
		List<DiscountAccount> discountAccounts2) {

		Assert.assertEquals(discountAccounts1.size(), discountAccounts2.size());

		for (int i = 0; i < discountAccounts1.size(); i++) {
			DiscountAccount discountAccount1 = discountAccounts1.get(i);
			DiscountAccount discountAccount2 = discountAccounts2.get(i);

			assertEquals(discountAccount1, discountAccount2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<DiscountAccount> discountAccounts1,
		List<DiscountAccount> discountAccounts2) {

		Assert.assertEquals(discountAccounts1.size(), discountAccounts2.size());

		for (DiscountAccount discountAccount1 : discountAccounts1) {
			boolean contains = false;

			for (DiscountAccount discountAccount2 : discountAccounts2) {
				if (equals(discountAccount1, discountAccount2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				discountAccounts2 + " does not contain " + discountAccount1,
				contains);
		}
	}

	protected void assertValid(DiscountAccount discountAccount)
		throws Exception {

		boolean valid = true;

		if (discountAccount.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("account", additionalAssertFieldName)) {
				if (discountAccount.getAccount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"accountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (discountAccount.getAccountExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (discountAccount.getAccountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (discountAccount.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"discountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (discountAccount.getDiscountExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (discountAccount.getDiscountId() == null) {
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

	protected void assertValid(Page<DiscountAccount> page) {
		boolean valid = false;

		java.util.Collection<DiscountAccount> discountAccounts =
			page.getItems();

		int size = discountAccounts.size();

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
					com.liferay.headless.commerce.admin.pricing.dto.v2_0.
						DiscountAccount.class)) {

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

	protected boolean equals(
		DiscountAccount discountAccount1, DiscountAccount discountAccount2) {

		if (discountAccount1 == discountAccount2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("account", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountAccount1.getAccount(),
						discountAccount2.getAccount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"accountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discountAccount1.getAccountExternalReferenceCode(),
						discountAccount2.getAccountExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountAccount1.getAccountId(),
						discountAccount2.getAccountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)discountAccount1.getActions(),
						(Map)discountAccount2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"discountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						discountAccount1.getDiscountExternalReferenceCode(),
						discountAccount2.getDiscountExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("discountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountAccount1.getDiscountId(),
						discountAccount2.getDiscountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						discountAccount1.getId(), discountAccount2.getId())) {

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

		if (!(_discountAccountResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_discountAccountResource;

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
		DiscountAccount discountAccount) {

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
					discountAccount.getAccountExternalReferenceCode()));
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

		if (entityFieldName.equals("discountExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					discountAccount.getDiscountExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("discountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("id")) {
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

	protected DiscountAccount randomDiscountAccount() throws Exception {
		return new DiscountAccount() {
			{
				accountExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				accountId = RandomTestUtil.randomLong();
				discountExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				discountId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
			}
		};
	}

	protected DiscountAccount randomIrrelevantDiscountAccount()
		throws Exception {

		DiscountAccount randomIrrelevantDiscountAccount =
			randomDiscountAccount();

		return randomIrrelevantDiscountAccount;
	}

	protected DiscountAccount randomPatchDiscountAccount() throws Exception {
		return randomDiscountAccount();
	}

	protected DiscountAccountResource discountAccountResource;
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
		BaseDiscountAccountResourceTestCase.class);

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
	private com.liferay.headless.commerce.admin.pricing.resource.v2_0.
		DiscountAccountResource _discountAccountResource;

}