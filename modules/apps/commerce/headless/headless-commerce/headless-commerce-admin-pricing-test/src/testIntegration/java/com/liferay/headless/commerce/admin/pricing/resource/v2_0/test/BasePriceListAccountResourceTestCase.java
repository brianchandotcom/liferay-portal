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

import com.liferay.headless.commerce.admin.pricing.client.dto.v2_0.PriceListAccount;
import com.liferay.headless.commerce.admin.pricing.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Page;
import com.liferay.headless.commerce.admin.pricing.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.pricing.client.resource.v2_0.PriceListAccountResource;
import com.liferay.headless.commerce.admin.pricing.client.serdes.v2_0.PriceListAccountSerDes;
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
public abstract class BasePriceListAccountResourceTestCase {

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

		_priceListAccountResource.setContextCompany(testCompany);

		PriceListAccountResource.Builder builder =
			PriceListAccountResource.builder();

		priceListAccountResource = builder.authentication(
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

		PriceListAccount priceListAccount1 = randomPriceListAccount();

		String json = objectMapper.writeValueAsString(priceListAccount1);

		PriceListAccount priceListAccount2 = PriceListAccountSerDes.toDTO(json);

		Assert.assertTrue(equals(priceListAccount1, priceListAccount2));
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

		PriceListAccount priceListAccount = randomPriceListAccount();

		String json1 = objectMapper.writeValueAsString(priceListAccount);
		String json2 = PriceListAccountSerDes.toJSON(priceListAccount);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		PriceListAccount priceListAccount = randomPriceListAccount();

		priceListAccount.setAccountExternalReferenceCode(regex);
		priceListAccount.setPriceListExternalReferenceCode(regex);

		String json = PriceListAccountSerDes.toJSON(priceListAccount);

		Assert.assertFalse(json.contains(regex));

		priceListAccount = PriceListAccountSerDes.toDTO(json);

		Assert.assertEquals(
			regex, priceListAccount.getAccountExternalReferenceCode());
		Assert.assertEquals(
			regex, priceListAccount.getPriceListExternalReferenceCode());
	}

	@Test
	public void testDeletePriceListAccount() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceListAccount priceListAccount =
			testDeletePriceListAccount_addPriceListAccount();

		assertHttpResponseStatusCode(
			204,
			priceListAccountResource.deletePriceListAccountHttpResponse(
				priceListAccount.getId()));
	}

	protected PriceListAccount testDeletePriceListAccount_addPriceListAccount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeletePriceListAccount() throws Exception {
		PriceListAccount priceListAccount =
			testGraphQLPriceListAccount_addPriceListAccount();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deletePriceListAccount",
						new HashMap<String, Object>() {
							{
								put("id", priceListAccount.getId());
							}
						})),
				"JSONObject/data", "Object/deletePriceListAccount"));
	}

	@Test
	public void testGetPriceListByExternalReferenceCodePriceListAccountsPage()
		throws Exception {

		Page<PriceListAccount> page =
			priceListAccountResource.
				getPriceListByExternalReferenceCodePriceListAccountsPage(
					testGetPriceListByExternalReferenceCodePriceListAccountsPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceListAccountsPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceListAccountsPage_getIrrelevantExternalReferenceCode();

		if ((irrelevantExternalReferenceCode != null)) {
			PriceListAccount irrelevantPriceListAccount =
				testGetPriceListByExternalReferenceCodePriceListAccountsPage_addPriceListAccount(
					irrelevantExternalReferenceCode,
					randomIrrelevantPriceListAccount());

			page =
				priceListAccountResource.
					getPriceListByExternalReferenceCodePriceListAccountsPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceListAccount),
				(List<PriceListAccount>)page.getItems());
			assertValid(page);
		}

		PriceListAccount priceListAccount1 =
			testGetPriceListByExternalReferenceCodePriceListAccountsPage_addPriceListAccount(
				externalReferenceCode, randomPriceListAccount());

		PriceListAccount priceListAccount2 =
			testGetPriceListByExternalReferenceCodePriceListAccountsPage_addPriceListAccount(
				externalReferenceCode, randomPriceListAccount());

		page =
			priceListAccountResource.
				getPriceListByExternalReferenceCodePriceListAccountsPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceListAccount1, priceListAccount2),
			(List<PriceListAccount>)page.getItems());
		assertValid(page);

		priceListAccountResource.deletePriceListAccount(
			priceListAccount1.getId());

		priceListAccountResource.deletePriceListAccount(
			priceListAccount2.getId());
	}

	@Test
	public void testGetPriceListByExternalReferenceCodePriceListAccountsPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetPriceListByExternalReferenceCodePriceListAccountsPage_getExternalReferenceCode();

		PriceListAccount priceListAccount1 =
			testGetPriceListByExternalReferenceCodePriceListAccountsPage_addPriceListAccount(
				externalReferenceCode, randomPriceListAccount());

		PriceListAccount priceListAccount2 =
			testGetPriceListByExternalReferenceCodePriceListAccountsPage_addPriceListAccount(
				externalReferenceCode, randomPriceListAccount());

		PriceListAccount priceListAccount3 =
			testGetPriceListByExternalReferenceCodePriceListAccountsPage_addPriceListAccount(
				externalReferenceCode, randomPriceListAccount());

		Page<PriceListAccount> page1 =
			priceListAccountResource.
				getPriceListByExternalReferenceCodePriceListAccountsPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<PriceListAccount> priceListAccounts1 =
			(List<PriceListAccount>)page1.getItems();

		Assert.assertEquals(
			priceListAccounts1.toString(), 2, priceListAccounts1.size());

		Page<PriceListAccount> page2 =
			priceListAccountResource.
				getPriceListByExternalReferenceCodePriceListAccountsPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceListAccount> priceListAccounts2 =
			(List<PriceListAccount>)page2.getItems();

		Assert.assertEquals(
			priceListAccounts2.toString(), 1, priceListAccounts2.size());

		Page<PriceListAccount> page3 =
			priceListAccountResource.
				getPriceListByExternalReferenceCodePriceListAccountsPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(
				priceListAccount1, priceListAccount2, priceListAccount3),
			(List<PriceListAccount>)page3.getItems());
	}

	protected PriceListAccount
			testGetPriceListByExternalReferenceCodePriceListAccountsPage_addPriceListAccount(
				String externalReferenceCode, PriceListAccount priceListAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceListByExternalReferenceCodePriceListAccountsPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetPriceListByExternalReferenceCodePriceListAccountsPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceListByExternalReferenceCodePriceListAccount()
		throws Exception {

		PriceListAccount randomPriceListAccount = randomPriceListAccount();

		PriceListAccount postPriceListAccount =
			testPostPriceListByExternalReferenceCodePriceListAccount_addPriceListAccount(
				randomPriceListAccount);

		assertEquals(randomPriceListAccount, postPriceListAccount);
		assertValid(postPriceListAccount);
	}

	protected PriceListAccount
			testPostPriceListByExternalReferenceCodePriceListAccount_addPriceListAccount(
				PriceListAccount priceListAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetPriceListIdPriceListAccountsPage() throws Exception {
		Page<PriceListAccount> page =
			priceListAccountResource.getPriceListIdPriceListAccountsPage(
				testGetPriceListIdPriceListAccountsPage_getId(),
				RandomTestUtil.randomString(), null, Pagination.of(1, 2), null);

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetPriceListIdPriceListAccountsPage_getId();
		Long irrelevantId =
			testGetPriceListIdPriceListAccountsPage_getIrrelevantId();

		if ((irrelevantId != null)) {
			PriceListAccount irrelevantPriceListAccount =
				testGetPriceListIdPriceListAccountsPage_addPriceListAccount(
					irrelevantId, randomIrrelevantPriceListAccount());

			page = priceListAccountResource.getPriceListIdPriceListAccountsPage(
				irrelevantId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantPriceListAccount),
				(List<PriceListAccount>)page.getItems());
			assertValid(page);
		}

		PriceListAccount priceListAccount1 =
			testGetPriceListIdPriceListAccountsPage_addPriceListAccount(
				id, randomPriceListAccount());

		PriceListAccount priceListAccount2 =
			testGetPriceListIdPriceListAccountsPage_addPriceListAccount(
				id, randomPriceListAccount());

		page = priceListAccountResource.getPriceListIdPriceListAccountsPage(
			id, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(priceListAccount1, priceListAccount2),
			(List<PriceListAccount>)page.getItems());
		assertValid(page);

		priceListAccountResource.deletePriceListAccount(
			priceListAccount1.getId());

		priceListAccountResource.deletePriceListAccount(
			priceListAccount2.getId());
	}

	@Test
	public void testGetPriceListIdPriceListAccountsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceListIdPriceListAccountsPage_getId();

		PriceListAccount priceListAccount1 = randomPriceListAccount();

		priceListAccount1 =
			testGetPriceListIdPriceListAccountsPage_addPriceListAccount(
				id, priceListAccount1);

		for (EntityField entityField : entityFields) {
			Page<PriceListAccount> page =
				priceListAccountResource.getPriceListIdPriceListAccountsPage(
					id, null,
					getFilterString(entityField, "between", priceListAccount1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceListAccount1),
				(List<PriceListAccount>)page.getItems());
		}
	}

	@Test
	public void testGetPriceListIdPriceListAccountsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceListIdPriceListAccountsPage_getId();

		PriceListAccount priceListAccount1 =
			testGetPriceListIdPriceListAccountsPage_addPriceListAccount(
				id, randomPriceListAccount());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		PriceListAccount priceListAccount2 =
			testGetPriceListIdPriceListAccountsPage_addPriceListAccount(
				id, randomPriceListAccount());

		for (EntityField entityField : entityFields) {
			Page<PriceListAccount> page =
				priceListAccountResource.getPriceListIdPriceListAccountsPage(
					id, null,
					getFilterString(entityField, "eq", priceListAccount1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(priceListAccount1),
				(List<PriceListAccount>)page.getItems());
		}
	}

	@Test
	public void testGetPriceListIdPriceListAccountsPageWithPagination()
		throws Exception {

		Long id = testGetPriceListIdPriceListAccountsPage_getId();

		PriceListAccount priceListAccount1 =
			testGetPriceListIdPriceListAccountsPage_addPriceListAccount(
				id, randomPriceListAccount());

		PriceListAccount priceListAccount2 =
			testGetPriceListIdPriceListAccountsPage_addPriceListAccount(
				id, randomPriceListAccount());

		PriceListAccount priceListAccount3 =
			testGetPriceListIdPriceListAccountsPage_addPriceListAccount(
				id, randomPriceListAccount());

		Page<PriceListAccount> page1 =
			priceListAccountResource.getPriceListIdPriceListAccountsPage(
				id, null, null, Pagination.of(1, 2), null);

		List<PriceListAccount> priceListAccounts1 =
			(List<PriceListAccount>)page1.getItems();

		Assert.assertEquals(
			priceListAccounts1.toString(), 2, priceListAccounts1.size());

		Page<PriceListAccount> page2 =
			priceListAccountResource.getPriceListIdPriceListAccountsPage(
				id, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<PriceListAccount> priceListAccounts2 =
			(List<PriceListAccount>)page2.getItems();

		Assert.assertEquals(
			priceListAccounts2.toString(), 1, priceListAccounts2.size());

		Page<PriceListAccount> page3 =
			priceListAccountResource.getPriceListIdPriceListAccountsPage(
				id, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(
				priceListAccount1, priceListAccount2, priceListAccount3),
			(List<PriceListAccount>)page3.getItems());
	}

	@Test
	public void testGetPriceListIdPriceListAccountsPageWithSortDateTime()
		throws Exception {

		testGetPriceListIdPriceListAccountsPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, priceListAccount1, priceListAccount2) -> {
				BeanUtils.setProperty(
					priceListAccount1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetPriceListIdPriceListAccountsPageWithSortInteger()
		throws Exception {

		testGetPriceListIdPriceListAccountsPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, priceListAccount1, priceListAccount2) -> {
				BeanUtils.setProperty(
					priceListAccount1, entityField.getName(), 0);
				BeanUtils.setProperty(
					priceListAccount2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetPriceListIdPriceListAccountsPageWithSortString()
		throws Exception {

		testGetPriceListIdPriceListAccountsPageWithSort(
			EntityField.Type.STRING,
			(entityField, priceListAccount1, priceListAccount2) -> {
				Class<?> clazz = priceListAccount1.getClass();

				String entityFieldName = entityField.getName();

				Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						priceListAccount1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						priceListAccount2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						priceListAccount1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						priceListAccount2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						priceListAccount1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						priceListAccount2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetPriceListIdPriceListAccountsPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer
				<EntityField, PriceListAccount, PriceListAccount, Exception>
					unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long id = testGetPriceListIdPriceListAccountsPage_getId();

		PriceListAccount priceListAccount1 = randomPriceListAccount();
		PriceListAccount priceListAccount2 = randomPriceListAccount();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(
				entityField, priceListAccount1, priceListAccount2);
		}

		priceListAccount1 =
			testGetPriceListIdPriceListAccountsPage_addPriceListAccount(
				id, priceListAccount1);

		priceListAccount2 =
			testGetPriceListIdPriceListAccountsPage_addPriceListAccount(
				id, priceListAccount2);

		for (EntityField entityField : entityFields) {
			Page<PriceListAccount> ascPage =
				priceListAccountResource.getPriceListIdPriceListAccountsPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(priceListAccount1, priceListAccount2),
				(List<PriceListAccount>)ascPage.getItems());

			Page<PriceListAccount> descPage =
				priceListAccountResource.getPriceListIdPriceListAccountsPage(
					id, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(priceListAccount2, priceListAccount1),
				(List<PriceListAccount>)descPage.getItems());
		}
	}

	protected PriceListAccount
			testGetPriceListIdPriceListAccountsPage_addPriceListAccount(
				Long id, PriceListAccount priceListAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPriceListIdPriceListAccountsPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetPriceListIdPriceListAccountsPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostPriceListIdPriceListAccount() throws Exception {
		PriceListAccount randomPriceListAccount = randomPriceListAccount();

		PriceListAccount postPriceListAccount =
			testPostPriceListIdPriceListAccount_addPriceListAccount(
				randomPriceListAccount);

		assertEquals(randomPriceListAccount, postPriceListAccount);
		assertValid(postPriceListAccount);
	}

	protected PriceListAccount
			testPostPriceListIdPriceListAccount_addPriceListAccount(
				PriceListAccount priceListAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected PriceListAccount testGraphQLPriceListAccount_addPriceListAccount()
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
		PriceListAccount priceListAccount1,
		PriceListAccount priceListAccount2) {

		Assert.assertTrue(
			priceListAccount1 + " does not equal " + priceListAccount2,
			equals(priceListAccount1, priceListAccount2));
	}

	protected void assertEquals(
		List<PriceListAccount> priceListAccounts1,
		List<PriceListAccount> priceListAccounts2) {

		Assert.assertEquals(
			priceListAccounts1.size(), priceListAccounts2.size());

		for (int i = 0; i < priceListAccounts1.size(); i++) {
			PriceListAccount priceListAccount1 = priceListAccounts1.get(i);
			PriceListAccount priceListAccount2 = priceListAccounts2.get(i);

			assertEquals(priceListAccount1, priceListAccount2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<PriceListAccount> priceListAccounts1,
		List<PriceListAccount> priceListAccounts2) {

		Assert.assertEquals(
			priceListAccounts1.size(), priceListAccounts2.size());

		for (PriceListAccount priceListAccount1 : priceListAccounts1) {
			boolean contains = false;

			for (PriceListAccount priceListAccount2 : priceListAccounts2) {
				if (equals(priceListAccount1, priceListAccount2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				priceListAccounts2 + " does not contain " + priceListAccount1,
				contains);
		}
	}

	protected void assertValid(PriceListAccount priceListAccount)
		throws Exception {

		boolean valid = true;

		if (priceListAccount.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("account", additionalAssertFieldName)) {
				if (priceListAccount.getAccount() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"accountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (priceListAccount.getAccountExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (priceListAccount.getAccountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (priceListAccount.getActions() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("order", additionalAssertFieldName)) {
				if (priceListAccount.getOrder() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListExternalReferenceCode",
					additionalAssertFieldName)) {

				if (priceListAccount.getPriceListExternalReferenceCode() ==
						null) {

					valid = false;
				}

				continue;
			}

			if (Objects.equals("priceListId", additionalAssertFieldName)) {
				if (priceListAccount.getPriceListId() == null) {
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

	protected void assertValid(Page<PriceListAccount> page) {
		boolean valid = false;

		java.util.Collection<PriceListAccount> priceListAccounts =
			page.getItems();

		int size = priceListAccounts.size();

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
						PriceListAccount.class)) {

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
		PriceListAccount priceListAccount1,
		PriceListAccount priceListAccount2) {

		if (priceListAccount1 == priceListAccount2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("account", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListAccount1.getAccount(),
						priceListAccount2.getAccount())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"accountExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceListAccount1.getAccountExternalReferenceCode(),
						priceListAccount2.getAccountExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListAccount1.getAccountId(),
						priceListAccount2.getAccountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("actions", additionalAssertFieldName)) {
				if (!equals(
						(Map)priceListAccount1.getActions(),
						(Map)priceListAccount2.getActions())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListAccount1.getId(), priceListAccount2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("order", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListAccount1.getOrder(),
						priceListAccount2.getOrder())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"priceListExternalReferenceCode",
					additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						priceListAccount1.getPriceListExternalReferenceCode(),
						priceListAccount2.
							getPriceListExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("priceListId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						priceListAccount1.getPriceListId(),
						priceListAccount2.getPriceListId())) {

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

		if (!(_priceListAccountResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_priceListAccountResource;

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
		PriceListAccount priceListAccount) {

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
					priceListAccount.getAccountExternalReferenceCode()));
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

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("order")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("priceListExternalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(
					priceListAccount.getPriceListExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("priceListId")) {
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

	protected PriceListAccount randomPriceListAccount() throws Exception {
		return new PriceListAccount() {
			{
				accountExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				accountId = RandomTestUtil.randomLong();
				id = RandomTestUtil.randomLong();
				order = RandomTestUtil.randomInt();
				priceListExternalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				priceListId = RandomTestUtil.randomLong();
			}
		};
	}

	protected PriceListAccount randomIrrelevantPriceListAccount()
		throws Exception {

		PriceListAccount randomIrrelevantPriceListAccount =
			randomPriceListAccount();

		return randomIrrelevantPriceListAccount;
	}

	protected PriceListAccount randomPatchPriceListAccount() throws Exception {
		return randomPriceListAccount();
	}

	protected PriceListAccountResource priceListAccountResource;
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
		BasePriceListAccountResourceTestCase.class);

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
		PriceListAccountResource _priceListAccountResource;

}