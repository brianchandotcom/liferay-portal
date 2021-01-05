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

package com.liferay.headless.commerce.admin.account.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.commerce.admin.account.client.dto.v1_0.AccountAddress;
import com.liferay.headless.commerce.admin.account.client.http.HttpInvoker;
import com.liferay.headless.commerce.admin.account.client.pagination.Page;
import com.liferay.headless.commerce.admin.account.client.pagination.Pagination;
import com.liferay.headless.commerce.admin.account.client.resource.v1_0.AccountAddressResource;
import com.liferay.headless.commerce.admin.account.client.serdes.v1_0.AccountAddressSerDes;
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
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
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
import org.apache.log4j.Level;

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
public abstract class BaseAccountAddressResourceTestCase {

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

		_accountAddressResource.setContextCompany(testCompany);

		AccountAddressResource.Builder builder =
			AccountAddressResource.builder();

		accountAddressResource = builder.authentication(
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

		AccountAddress accountAddress1 = randomAccountAddress();

		String json = objectMapper.writeValueAsString(accountAddress1);

		AccountAddress accountAddress2 = AccountAddressSerDes.toDTO(json);

		Assert.assertTrue(equals(accountAddress1, accountAddress2));
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

		AccountAddress accountAddress = randomAccountAddress();

		String json1 = objectMapper.writeValueAsString(accountAddress);
		String json2 = AccountAddressSerDes.toJSON(accountAddress);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		AccountAddress accountAddress = randomAccountAddress();

		accountAddress.setCity(regex);
		accountAddress.setCountryISOCode(regex);
		accountAddress.setDescription(regex);
		accountAddress.setExternalReferenceCode(regex);
		accountAddress.setName(regex);
		accountAddress.setPhoneNumber(regex);
		accountAddress.setRegionISOCode(regex);
		accountAddress.setStreet1(regex);
		accountAddress.setStreet2(regex);
		accountAddress.setStreet3(regex);
		accountAddress.setZip(regex);

		String json = AccountAddressSerDes.toJSON(accountAddress);

		Assert.assertFalse(json.contains(regex));

		accountAddress = AccountAddressSerDes.toDTO(json);

		Assert.assertEquals(regex, accountAddress.getCity());
		Assert.assertEquals(regex, accountAddress.getCountryISOCode());
		Assert.assertEquals(regex, accountAddress.getDescription());
		Assert.assertEquals(regex, accountAddress.getExternalReferenceCode());
		Assert.assertEquals(regex, accountAddress.getName());
		Assert.assertEquals(regex, accountAddress.getPhoneNumber());
		Assert.assertEquals(regex, accountAddress.getRegionISOCode());
		Assert.assertEquals(regex, accountAddress.getStreet1());
		Assert.assertEquals(regex, accountAddress.getStreet2());
		Assert.assertEquals(regex, accountAddress.getStreet3());
		Assert.assertEquals(regex, accountAddress.getZip());
	}

	@Test
	public void testDeleteAccountAddressByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountAddress accountAddress =
			testDeleteAccountAddressByExternalReferenceCode_addAccountAddress();

		assertHttpResponseStatusCode(
			204,
			accountAddressResource.
				deleteAccountAddressByExternalReferenceCodeHttpResponse(
					accountAddress.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			accountAddressResource.
				getAccountAddressByExternalReferenceCodeHttpResponse(
					accountAddress.getExternalReferenceCode()));

		assertHttpResponseStatusCode(
			404,
			accountAddressResource.
				getAccountAddressByExternalReferenceCodeHttpResponse(
					accountAddress.getExternalReferenceCode()));
	}

	protected AccountAddress
			testDeleteAccountAddressByExternalReferenceCode_addAccountAddress()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetAccountAddressByExternalReferenceCode()
		throws Exception {

		AccountAddress postAccountAddress =
			testGetAccountAddressByExternalReferenceCode_addAccountAddress();

		AccountAddress getAccountAddress =
			accountAddressResource.getAccountAddressByExternalReferenceCode(
				postAccountAddress.getExternalReferenceCode());

		assertEquals(postAccountAddress, getAccountAddress);
		assertValid(getAccountAddress);
	}

	protected AccountAddress
			testGetAccountAddressByExternalReferenceCode_addAccountAddress()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetAccountAddressByExternalReferenceCode()
		throws Exception {

		AccountAddress accountAddress =
			testGraphQLAccountAddress_addAccountAddress();

		Assert.assertTrue(
			equals(
				accountAddress,
				AccountAddressSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"accountAddressByExternalReferenceCode",
								new HashMap<String, Object>() {
									{
										put(
											"externalReferenceCode",
											"\"" +
												accountAddress.
													getExternalReferenceCode() +
														"\"");
									}
								},
								getGraphQLFields())),
						"JSONObject/data",
						"Object/accountAddressByExternalReferenceCode"))));
	}

	@Test
	public void testGraphQLGetAccountAddressByExternalReferenceCodeNotFound()
		throws Exception {

		String irrelevantExternalReferenceCode =
			"\"" + RandomTestUtil.randomString() + "\"";

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"accountAddressByExternalReferenceCode",
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
	public void testPatchAccountAddressByExternalReferenceCode()
		throws Exception {

		Assert.assertTrue(false);
	}

	@Test
	public void testDeleteAccountAddress() throws Exception {
		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountAddress accountAddress =
			testDeleteAccountAddress_addAccountAddress();

		assertHttpResponseStatusCode(
			204,
			accountAddressResource.deleteAccountAddressHttpResponse(
				accountAddress.getId()));

		assertHttpResponseStatusCode(
			404,
			accountAddressResource.getAccountAddressHttpResponse(
				accountAddress.getId()));

		assertHttpResponseStatusCode(
			404,
			accountAddressResource.getAccountAddressHttpResponse(
				accountAddress.getId()));
	}

	protected AccountAddress testDeleteAccountAddress_addAccountAddress()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLDeleteAccountAddress() throws Exception {
		AccountAddress accountAddress =
			testGraphQLAccountAddress_addAccountAddress();

		Assert.assertTrue(
			JSONUtil.getValueAsBoolean(
				invokeGraphQLMutation(
					new GraphQLField(
						"deleteAccountAddress",
						new HashMap<String, Object>() {
							{
								put("id", accountAddress.getId());
							}
						})),
				"JSONObject/data", "Object/deleteAccountAddress"));

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"graphql.execution.SimpleDataFetcherExceptionHandler",
					Level.WARN)) {

			JSONArray errorsJSONArray = JSONUtil.getValueAsJSONArray(
				invokeGraphQLQuery(
					new GraphQLField(
						"accountAddress",
						new HashMap<String, Object>() {
							{
								put("id", accountAddress.getId());
							}
						},
						new GraphQLField("id"))),
				"JSONArray/errors");

			Assert.assertTrue(errorsJSONArray.length() > 0);
		}
	}

	@Test
	public void testGetAccountAddress() throws Exception {
		AccountAddress postAccountAddress =
			testGetAccountAddress_addAccountAddress();

		AccountAddress getAccountAddress =
			accountAddressResource.getAccountAddress(
				postAccountAddress.getId());

		assertEquals(postAccountAddress, getAccountAddress);
		assertValid(getAccountAddress);
	}

	protected AccountAddress testGetAccountAddress_addAccountAddress()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGraphQLGetAccountAddress() throws Exception {
		AccountAddress accountAddress =
			testGraphQLAccountAddress_addAccountAddress();

		Assert.assertTrue(
			equals(
				accountAddress,
				AccountAddressSerDes.toDTO(
					JSONUtil.getValueAsString(
						invokeGraphQLQuery(
							new GraphQLField(
								"accountAddress",
								new HashMap<String, Object>() {
									{
										put("id", accountAddress.getId());
									}
								},
								getGraphQLFields())),
						"JSONObject/data", "Object/accountAddress"))));
	}

	@Test
	public void testGraphQLGetAccountAddressNotFound() throws Exception {
		Long irrelevantId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"Not Found",
			JSONUtil.getValueAsString(
				invokeGraphQLQuery(
					new GraphQLField(
						"accountAddress",
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
	public void testPatchAccountAddress() throws Exception {
		AccountAddress postAccountAddress =
			testPatchAccountAddress_addAccountAddress();

		AccountAddress randomPatchAccountAddress = randomPatchAccountAddress();

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountAddress patchAccountAddress =
			accountAddressResource.patchAccountAddress(
				postAccountAddress.getId(), randomPatchAccountAddress);

		AccountAddress expectedPatchAccountAddress = postAccountAddress.clone();

		_beanUtilsBean.copyProperties(
			expectedPatchAccountAddress, randomPatchAccountAddress);

		AccountAddress getAccountAddress =
			accountAddressResource.getAccountAddress(
				patchAccountAddress.getId());

		assertEquals(expectedPatchAccountAddress, getAccountAddress);
		assertValid(getAccountAddress);
	}

	protected AccountAddress testPatchAccountAddress_addAccountAddress()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPutAccountAddress() throws Exception {
		AccountAddress postAccountAddress =
			testPutAccountAddress_addAccountAddress();

		AccountAddress randomAccountAddress = randomAccountAddress();

		AccountAddress putAccountAddress =
			accountAddressResource.putAccountAddress(
				postAccountAddress.getId(), randomAccountAddress);

		assertEquals(randomAccountAddress, putAccountAddress);
		assertValid(putAccountAddress);

		AccountAddress getAccountAddress =
			accountAddressResource.getAccountAddress(putAccountAddress.getId());

		assertEquals(randomAccountAddress, getAccountAddress);
		assertValid(getAccountAddress);
	}

	protected AccountAddress testPutAccountAddress_addAccountAddress()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetAccountByExternalReferenceCodeAccountAddressesPage()
		throws Exception {

		Page<AccountAddress> page =
			accountAddressResource.
				getAccountByExternalReferenceCodeAccountAddressesPage(
					testGetAccountByExternalReferenceCodeAccountAddressesPage_getExternalReferenceCode(),
					Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		String externalReferenceCode =
			testGetAccountByExternalReferenceCodeAccountAddressesPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetAccountByExternalReferenceCodeAccountAddressesPage_getIrrelevantExternalReferenceCode();

		if ((irrelevantExternalReferenceCode != null)) {
			AccountAddress irrelevantAccountAddress =
				testGetAccountByExternalReferenceCodeAccountAddressesPage_addAccountAddress(
					irrelevantExternalReferenceCode,
					randomIrrelevantAccountAddress());

			page =
				accountAddressResource.
					getAccountByExternalReferenceCodeAccountAddressesPage(
						irrelevantExternalReferenceCode, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccountAddress),
				(List<AccountAddress>)page.getItems());
			assertValid(page);
		}

		AccountAddress accountAddress1 =
			testGetAccountByExternalReferenceCodeAccountAddressesPage_addAccountAddress(
				externalReferenceCode, randomAccountAddress());

		AccountAddress accountAddress2 =
			testGetAccountByExternalReferenceCodeAccountAddressesPage_addAccountAddress(
				externalReferenceCode, randomAccountAddress());

		page =
			accountAddressResource.
				getAccountByExternalReferenceCodeAccountAddressesPage(
					externalReferenceCode, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(accountAddress1, accountAddress2),
			(List<AccountAddress>)page.getItems());
		assertValid(page);

		accountAddressResource.deleteAccountAddress(accountAddress1.getId());

		accountAddressResource.deleteAccountAddress(accountAddress2.getId());
	}

	@Test
	public void testGetAccountByExternalReferenceCodeAccountAddressesPageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetAccountByExternalReferenceCodeAccountAddressesPage_getExternalReferenceCode();

		AccountAddress accountAddress1 =
			testGetAccountByExternalReferenceCodeAccountAddressesPage_addAccountAddress(
				externalReferenceCode, randomAccountAddress());

		AccountAddress accountAddress2 =
			testGetAccountByExternalReferenceCodeAccountAddressesPage_addAccountAddress(
				externalReferenceCode, randomAccountAddress());

		AccountAddress accountAddress3 =
			testGetAccountByExternalReferenceCodeAccountAddressesPage_addAccountAddress(
				externalReferenceCode, randomAccountAddress());

		Page<AccountAddress> page1 =
			accountAddressResource.
				getAccountByExternalReferenceCodeAccountAddressesPage(
					externalReferenceCode, Pagination.of(1, 2));

		List<AccountAddress> accountAddresses1 =
			(List<AccountAddress>)page1.getItems();

		Assert.assertEquals(
			accountAddresses1.toString(), 2, accountAddresses1.size());

		Page<AccountAddress> page2 =
			accountAddressResource.
				getAccountByExternalReferenceCodeAccountAddressesPage(
					externalReferenceCode, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<AccountAddress> accountAddresses2 =
			(List<AccountAddress>)page2.getItems();

		Assert.assertEquals(
			accountAddresses2.toString(), 1, accountAddresses2.size());

		Page<AccountAddress> page3 =
			accountAddressResource.
				getAccountByExternalReferenceCodeAccountAddressesPage(
					externalReferenceCode, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(accountAddress1, accountAddress2, accountAddress3),
			(List<AccountAddress>)page3.getItems());
	}

	protected AccountAddress
			testGetAccountByExternalReferenceCodeAccountAddressesPage_addAccountAddress(
				String externalReferenceCode, AccountAddress accountAddress)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeAccountAddressesPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeAccountAddressesPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAccountByExternalReferenceCodeAccountAddress()
		throws Exception {

		AccountAddress randomAccountAddress = randomAccountAddress();

		AccountAddress postAccountAddress =
			testPostAccountByExternalReferenceCodeAccountAddress_addAccountAddress(
				randomAccountAddress);

		assertEquals(randomAccountAddress, postAccountAddress);
		assertValid(postAccountAddress);

		randomAccountAddress = randomAccountAddress();

		assertHttpResponseStatusCode(
			404,
			accountAddressResource.
				getAccountAddressByExternalReferenceCodeHttpResponse(
					randomAccountAddress.getExternalReferenceCode()));

		testPostAccountByExternalReferenceCodeAccountAddress_addAccountAddress(
			randomAccountAddress);

		assertHttpResponseStatusCode(
			200,
			accountAddressResource.
				getAccountAddressByExternalReferenceCodeHttpResponse(
					randomAccountAddress.getExternalReferenceCode()));
	}

	protected AccountAddress
			testPostAccountByExternalReferenceCodeAccountAddress_addAccountAddress(
				AccountAddress accountAddress)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetAccountIdAccountAddressesPage() throws Exception {
		Page<AccountAddress> page =
			accountAddressResource.getAccountIdAccountAddressesPage(
				testGetAccountIdAccountAddressesPage_getId(),
				Pagination.of(1, 2));

		Assert.assertEquals(0, page.getTotalCount());

		Long id = testGetAccountIdAccountAddressesPage_getId();
		Long irrelevantId =
			testGetAccountIdAccountAddressesPage_getIrrelevantId();

		if ((irrelevantId != null)) {
			AccountAddress irrelevantAccountAddress =
				testGetAccountIdAccountAddressesPage_addAccountAddress(
					irrelevantId, randomIrrelevantAccountAddress());

			page = accountAddressResource.getAccountIdAccountAddressesPage(
				irrelevantId, Pagination.of(1, 2));

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccountAddress),
				(List<AccountAddress>)page.getItems());
			assertValid(page);
		}

		AccountAddress accountAddress1 =
			testGetAccountIdAccountAddressesPage_addAccountAddress(
				id, randomAccountAddress());

		AccountAddress accountAddress2 =
			testGetAccountIdAccountAddressesPage_addAccountAddress(
				id, randomAccountAddress());

		page = accountAddressResource.getAccountIdAccountAddressesPage(
			id, Pagination.of(1, 2));

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(accountAddress1, accountAddress2),
			(List<AccountAddress>)page.getItems());
		assertValid(page);

		accountAddressResource.deleteAccountAddress(accountAddress1.getId());

		accountAddressResource.deleteAccountAddress(accountAddress2.getId());
	}

	@Test
	public void testGetAccountIdAccountAddressesPageWithPagination()
		throws Exception {

		Long id = testGetAccountIdAccountAddressesPage_getId();

		AccountAddress accountAddress1 =
			testGetAccountIdAccountAddressesPage_addAccountAddress(
				id, randomAccountAddress());

		AccountAddress accountAddress2 =
			testGetAccountIdAccountAddressesPage_addAccountAddress(
				id, randomAccountAddress());

		AccountAddress accountAddress3 =
			testGetAccountIdAccountAddressesPage_addAccountAddress(
				id, randomAccountAddress());

		Page<AccountAddress> page1 =
			accountAddressResource.getAccountIdAccountAddressesPage(
				id, Pagination.of(1, 2));

		List<AccountAddress> accountAddresses1 =
			(List<AccountAddress>)page1.getItems();

		Assert.assertEquals(
			accountAddresses1.toString(), 2, accountAddresses1.size());

		Page<AccountAddress> page2 =
			accountAddressResource.getAccountIdAccountAddressesPage(
				id, Pagination.of(2, 2));

		Assert.assertEquals(3, page2.getTotalCount());

		List<AccountAddress> accountAddresses2 =
			(List<AccountAddress>)page2.getItems();

		Assert.assertEquals(
			accountAddresses2.toString(), 1, accountAddresses2.size());

		Page<AccountAddress> page3 =
			accountAddressResource.getAccountIdAccountAddressesPage(
				id, Pagination.of(1, 3));

		assertEqualsIgnoringOrder(
			Arrays.asList(accountAddress1, accountAddress2, accountAddress3),
			(List<AccountAddress>)page3.getItems());
	}

	protected AccountAddress
			testGetAccountIdAccountAddressesPage_addAccountAddress(
				Long id, AccountAddress accountAddress)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountIdAccountAddressesPage_getId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountIdAccountAddressesPage_getIrrelevantId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAccountIdAccountAddress() throws Exception {
		AccountAddress randomAccountAddress = randomAccountAddress();

		AccountAddress postAccountAddress =
			testPostAccountIdAccountAddress_addAccountAddress(
				randomAccountAddress);

		assertEquals(randomAccountAddress, postAccountAddress);
		assertValid(postAccountAddress);

		randomAccountAddress = randomAccountAddress();

		assertHttpResponseStatusCode(
			404,
			accountAddressResource.
				getAccountAddressByExternalReferenceCodeHttpResponse(
					randomAccountAddress.getExternalReferenceCode()));

		testPostAccountIdAccountAddress_addAccountAddress(randomAccountAddress);

		assertHttpResponseStatusCode(
			200,
			accountAddressResource.
				getAccountAddressByExternalReferenceCodeHttpResponse(
					randomAccountAddress.getExternalReferenceCode()));
	}

	protected AccountAddress testPostAccountIdAccountAddress_addAccountAddress(
			AccountAddress accountAddress)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected AccountAddress testGraphQLAccountAddress_addAccountAddress()
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
		AccountAddress accountAddress1, AccountAddress accountAddress2) {

		Assert.assertTrue(
			accountAddress1 + " does not equal " + accountAddress2,
			equals(accountAddress1, accountAddress2));
	}

	protected void assertEquals(
		List<AccountAddress> accountAddresses1,
		List<AccountAddress> accountAddresses2) {

		Assert.assertEquals(accountAddresses1.size(), accountAddresses2.size());

		for (int i = 0; i < accountAddresses1.size(); i++) {
			AccountAddress accountAddress1 = accountAddresses1.get(i);
			AccountAddress accountAddress2 = accountAddresses2.get(i);

			assertEquals(accountAddress1, accountAddress2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<AccountAddress> accountAddresses1,
		List<AccountAddress> accountAddresses2) {

		Assert.assertEquals(accountAddresses1.size(), accountAddresses2.size());

		for (AccountAddress accountAddress1 : accountAddresses1) {
			boolean contains = false;

			for (AccountAddress accountAddress2 : accountAddresses2) {
				if (equals(accountAddress1, accountAddress2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				accountAddresses2 + " does not contain " + accountAddress1,
				contains);
		}
	}

	protected void assertValid(AccountAddress accountAddress) throws Exception {
		boolean valid = true;

		if (accountAddress.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("city", additionalAssertFieldName)) {
				if (accountAddress.getCity() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("countryISOCode", additionalAssertFieldName)) {
				if (accountAddress.getCountryISOCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("defaultBilling", additionalAssertFieldName)) {
				if (accountAddress.getDefaultBilling() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("defaultShipping", additionalAssertFieldName)) {
				if (accountAddress.getDefaultShipping() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (accountAddress.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (accountAddress.getExternalReferenceCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("latitude", additionalAssertFieldName)) {
				if (accountAddress.getLatitude() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("longitude", additionalAssertFieldName)) {
				if (accountAddress.getLongitude() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (accountAddress.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("phoneNumber", additionalAssertFieldName)) {
				if (accountAddress.getPhoneNumber() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("regionISOCode", additionalAssertFieldName)) {
				if (accountAddress.getRegionISOCode() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("street1", additionalAssertFieldName)) {
				if (accountAddress.getStreet1() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("street2", additionalAssertFieldName)) {
				if (accountAddress.getStreet2() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("street3", additionalAssertFieldName)) {
				if (accountAddress.getStreet3() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (accountAddress.getType() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("zip", additionalAssertFieldName)) {
				if (accountAddress.getZip() == null) {
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

	protected void assertValid(Page<AccountAddress> page) {
		boolean valid = false;

		java.util.Collection<AccountAddress> accountAddresses = page.getItems();

		int size = accountAddresses.size();

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
					com.liferay.headless.commerce.admin.account.dto.v1_0.
						AccountAddress.class)) {

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
		AccountAddress accountAddress1, AccountAddress accountAddress2) {

		if (accountAddress1 == accountAddress2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("city", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getCity(), accountAddress2.getCity())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("countryISOCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getCountryISOCode(),
						accountAddress2.getCountryISOCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("defaultBilling", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getDefaultBilling(),
						accountAddress2.getDefaultBilling())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("defaultShipping", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getDefaultShipping(),
						accountAddress2.getDefaultShipping())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getDescription(),
						accountAddress2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"externalReferenceCode", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						accountAddress1.getExternalReferenceCode(),
						accountAddress2.getExternalReferenceCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getId(), accountAddress2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("latitude", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getLatitude(),
						accountAddress2.getLatitude())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("longitude", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getLongitude(),
						accountAddress2.getLongitude())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getName(), accountAddress2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("phoneNumber", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getPhoneNumber(),
						accountAddress2.getPhoneNumber())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("regionISOCode", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getRegionISOCode(),
						accountAddress2.getRegionISOCode())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("street1", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getStreet1(),
						accountAddress2.getStreet1())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("street2", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getStreet2(),
						accountAddress2.getStreet2())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("street3", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getStreet3(),
						accountAddress2.getStreet3())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("type", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getType(), accountAddress2.getType())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("zip", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountAddress1.getZip(), accountAddress2.getZip())) {

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

	protected java.util.Collection<EntityField> getEntityFields()
		throws Exception {

		if (!(_accountAddressResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_accountAddressResource;

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
		AccountAddress accountAddress) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("city")) {
			sb.append("'");
			sb.append(String.valueOf(accountAddress.getCity()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("countryISOCode")) {
			sb.append("'");
			sb.append(String.valueOf(accountAddress.getCountryISOCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("defaultBilling")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("defaultShipping")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(accountAddress.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("externalReferenceCode")) {
			sb.append("'");
			sb.append(
				String.valueOf(accountAddress.getExternalReferenceCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("latitude")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("longitude")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(accountAddress.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("phoneNumber")) {
			sb.append("'");
			sb.append(String.valueOf(accountAddress.getPhoneNumber()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("regionISOCode")) {
			sb.append("'");
			sb.append(String.valueOf(accountAddress.getRegionISOCode()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("street1")) {
			sb.append("'");
			sb.append(String.valueOf(accountAddress.getStreet1()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("street2")) {
			sb.append("'");
			sb.append(String.valueOf(accountAddress.getStreet2()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("street3")) {
			sb.append("'");
			sb.append(String.valueOf(accountAddress.getStreet3()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("type")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("zip")) {
			sb.append("'");
			sb.append(String.valueOf(accountAddress.getZip()));
			sb.append("'");

			return sb.toString();
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

	protected AccountAddress randomAccountAddress() throws Exception {
		return new AccountAddress() {
			{
				city = StringUtil.toLowerCase(RandomTestUtil.randomString());
				countryISOCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				defaultBilling = RandomTestUtil.randomBoolean();
				defaultShipping = RandomTestUtil.randomBoolean();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				latitude = RandomTestUtil.randomDouble();
				longitude = RandomTestUtil.randomDouble();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				phoneNumber = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				regionISOCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				street1 = StringUtil.toLowerCase(RandomTestUtil.randomString());
				street2 = StringUtil.toLowerCase(RandomTestUtil.randomString());
				street3 = StringUtil.toLowerCase(RandomTestUtil.randomString());
				type = RandomTestUtil.randomInt();
				zip = StringUtil.toLowerCase(RandomTestUtil.randomString());
			}
		};
	}

	protected AccountAddress randomIrrelevantAccountAddress() throws Exception {
		AccountAddress randomIrrelevantAccountAddress = randomAccountAddress();

		return randomIrrelevantAccountAddress;
	}

	protected AccountAddress randomPatchAccountAddress() throws Exception {
		return randomAccountAddress();
	}

	protected AccountAddressResource accountAddressResource;
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
		BaseAccountAddressResourceTestCase.class);

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
	private com.liferay.headless.commerce.admin.account.resource.v1_0.
		AccountAddressResource _accountAddressResource;

}