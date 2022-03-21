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

package com.liferay.headless.admin.user.resource.v1_0.test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.admin.user.client.dto.v1_0.AccountRole;
import com.liferay.headless.admin.user.client.http.HttpInvoker;
import com.liferay.headless.admin.user.client.pagination.Page;
import com.liferay.headless.admin.user.client.pagination.Pagination;
import com.liferay.headless.admin.user.client.resource.v1_0.AccountRoleResource;
import com.liferay.headless.admin.user.client.serdes.v1_0.AccountRoleSerDes;
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
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public abstract class BaseAccountRoleResourceTestCase {

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

		_accountRoleResource.setContextCompany(testCompany);

		AccountRoleResource.Builder builder = AccountRoleResource.builder();

		accountRoleResource = builder.authentication(
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

		AccountRole accountRole1 = randomAccountRole();

		String json = objectMapper.writeValueAsString(accountRole1);

		AccountRole accountRole2 = AccountRoleSerDes.toDTO(json);

		Assert.assertTrue(equals(accountRole1, accountRole2));
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

		AccountRole accountRole = randomAccountRole();

		String json1 = objectMapper.writeValueAsString(accountRole);
		String json2 = AccountRoleSerDes.toJSON(accountRole);

		Assert.assertEquals(
			objectMapper.readTree(json1), objectMapper.readTree(json2));
	}

	@Test
	public void testEscapeRegexInStringFields() throws Exception {
		String regex = "^[0-9]+(\\.[0-9]{1,2})\"?";

		AccountRole accountRole = randomAccountRole();

		accountRole.setDescription(regex);
		accountRole.setDisplayName(regex);
		accountRole.setName(regex);

		String json = AccountRoleSerDes.toJSON(accountRole);

		Assert.assertFalse(json.contains(regex));

		accountRole = AccountRoleSerDes.toDTO(json);

		Assert.assertEquals(regex, accountRole.getDescription());
		Assert.assertEquals(regex, accountRole.getDisplayName());
		Assert.assertEquals(regex, accountRole.getName());
	}

	@Test
	public void testDeleteAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountRole accountRole =
			testDeleteAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode_addAccountRole();

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.
				deleteAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCodeHttpResponse(
					null, accountRole.getId(), null));
	}

	protected AccountRole
			testDeleteAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode_addAccountRole()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountRole accountRole =
			testPostAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode_addAccountRole();

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.
				postAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCodeHttpResponse(
					null, accountRole.getId(), null));

		assertHttpResponseStatusCode(
			404,
			accountRoleResource.
				postAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCodeHttpResponse(
					null, 0L, null));
	}

	protected AccountRole
			testPostAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode_addAccountRole()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage()
		throws Exception {

		String accountExternalReferenceCode =
			testGetAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage_getAccountExternalReferenceCode();
		String irrelevantAccountExternalReferenceCode =
			testGetAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage_getIrrelevantAccountExternalReferenceCode();
		String userAccountExternalReferenceCode =
			testGetAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage_getUserAccountExternalReferenceCode();
		String irrelevantUserAccountExternalReferenceCode =
			testGetAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage_getIrrelevantUserAccountExternalReferenceCode();

		Page<AccountRole> page =
			accountRoleResource.
				getAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage(
					accountExternalReferenceCode,
					userAccountExternalReferenceCode);

		Assert.assertEquals(0, page.getTotalCount());

		if ((irrelevantAccountExternalReferenceCode != null) &&
			(irrelevantUserAccountExternalReferenceCode != null)) {

			AccountRole irrelevantAccountRole =
				testGetAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage_addAccountRole(
					irrelevantAccountExternalReferenceCode,
					irrelevantUserAccountExternalReferenceCode,
					randomIrrelevantAccountRole());

			page =
				accountRoleResource.
					getAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage(
						irrelevantAccountExternalReferenceCode,
						irrelevantUserAccountExternalReferenceCode);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccountRole),
				(List<AccountRole>)page.getItems());
			assertValid(page);
		}

		AccountRole accountRole1 =
			testGetAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage_addAccountRole(
				accountExternalReferenceCode, userAccountExternalReferenceCode,
				randomAccountRole());

		AccountRole accountRole2 =
			testGetAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage_addAccountRole(
				accountExternalReferenceCode, userAccountExternalReferenceCode,
				randomAccountRole());

		page =
			accountRoleResource.
				getAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage(
					accountExternalReferenceCode,
					userAccountExternalReferenceCode);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(accountRole1, accountRole2),
			(List<AccountRole>)page.getItems());
		assertValid(page);
	}

	protected AccountRole
			testGetAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage_addAccountRole(
				String accountExternalReferenceCode,
				String userAccountExternalReferenceCode,
				AccountRole accountRole)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage_getAccountExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage_getIrrelevantAccountExternalReferenceCode()
		throws Exception {

		return null;
	}

	protected String
			testGetAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage_getUserAccountExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage_getIrrelevantUserAccountExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testGetAccountAccountRolesByExternalReferenceCodePage()
		throws Exception {

		String externalReferenceCode =
			testGetAccountAccountRolesByExternalReferenceCodePage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetAccountAccountRolesByExternalReferenceCodePage_getIrrelevantExternalReferenceCode();

		Page<AccountRole> page =
			accountRoleResource.
				getAccountAccountRolesByExternalReferenceCodePage(
					externalReferenceCode, RandomTestUtil.randomString(), null,
					Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantExternalReferenceCode != null) {
			AccountRole irrelevantAccountRole =
				testGetAccountAccountRolesByExternalReferenceCodePage_addAccountRole(
					irrelevantExternalReferenceCode,
					randomIrrelevantAccountRole());

			page =
				accountRoleResource.
					getAccountAccountRolesByExternalReferenceCodePage(
						irrelevantExternalReferenceCode, null, null,
						Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccountRole),
				(List<AccountRole>)page.getItems());
			assertValid(page);
		}

		AccountRole accountRole1 =
			testGetAccountAccountRolesByExternalReferenceCodePage_addAccountRole(
				externalReferenceCode, randomAccountRole());

		AccountRole accountRole2 =
			testGetAccountAccountRolesByExternalReferenceCodePage_addAccountRole(
				externalReferenceCode, randomAccountRole());

		page =
			accountRoleResource.
				getAccountAccountRolesByExternalReferenceCodePage(
					externalReferenceCode, null, null, Pagination.of(1, 10),
					null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(accountRole1, accountRole2),
			(List<AccountRole>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountAccountRolesByExternalReferenceCodePageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		String externalReferenceCode =
			testGetAccountAccountRolesByExternalReferenceCodePage_getExternalReferenceCode();

		AccountRole accountRole1 = randomAccountRole();

		accountRole1 =
			testGetAccountAccountRolesByExternalReferenceCodePage_addAccountRole(
				externalReferenceCode, accountRole1);

		for (EntityField entityField : entityFields) {
			Page<AccountRole> page =
				accountRoleResource.
					getAccountAccountRolesByExternalReferenceCodePage(
						externalReferenceCode, null,
						getFilterString(entityField, "between", accountRole1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(accountRole1),
				(List<AccountRole>)page.getItems());
		}
	}

	@Test
	public void testGetAccountAccountRolesByExternalReferenceCodePageWithFilterDoubleEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DOUBLE);

		if (entityFields.isEmpty()) {
			return;
		}

		String externalReferenceCode =
			testGetAccountAccountRolesByExternalReferenceCodePage_getExternalReferenceCode();

		AccountRole accountRole1 =
			testGetAccountAccountRolesByExternalReferenceCodePage_addAccountRole(
				externalReferenceCode, randomAccountRole());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountRole accountRole2 =
			testGetAccountAccountRolesByExternalReferenceCodePage_addAccountRole(
				externalReferenceCode, randomAccountRole());

		for (EntityField entityField : entityFields) {
			Page<AccountRole> page =
				accountRoleResource.
					getAccountAccountRolesByExternalReferenceCodePage(
						externalReferenceCode, null,
						getFilterString(entityField, "eq", accountRole1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(accountRole1),
				(List<AccountRole>)page.getItems());
		}
	}

	@Test
	public void testGetAccountAccountRolesByExternalReferenceCodePageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		String externalReferenceCode =
			testGetAccountAccountRolesByExternalReferenceCodePage_getExternalReferenceCode();

		AccountRole accountRole1 =
			testGetAccountAccountRolesByExternalReferenceCodePage_addAccountRole(
				externalReferenceCode, randomAccountRole());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountRole accountRole2 =
			testGetAccountAccountRolesByExternalReferenceCodePage_addAccountRole(
				externalReferenceCode, randomAccountRole());

		for (EntityField entityField : entityFields) {
			Page<AccountRole> page =
				accountRoleResource.
					getAccountAccountRolesByExternalReferenceCodePage(
						externalReferenceCode, null,
						getFilterString(entityField, "eq", accountRole1),
						Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(accountRole1),
				(List<AccountRole>)page.getItems());
		}
	}

	@Test
	public void testGetAccountAccountRolesByExternalReferenceCodePageWithPagination()
		throws Exception {

		String externalReferenceCode =
			testGetAccountAccountRolesByExternalReferenceCodePage_getExternalReferenceCode();

		AccountRole accountRole1 =
			testGetAccountAccountRolesByExternalReferenceCodePage_addAccountRole(
				externalReferenceCode, randomAccountRole());

		AccountRole accountRole2 =
			testGetAccountAccountRolesByExternalReferenceCodePage_addAccountRole(
				externalReferenceCode, randomAccountRole());

		AccountRole accountRole3 =
			testGetAccountAccountRolesByExternalReferenceCodePage_addAccountRole(
				externalReferenceCode, randomAccountRole());

		Page<AccountRole> page1 =
			accountRoleResource.
				getAccountAccountRolesByExternalReferenceCodePage(
					externalReferenceCode, null, null, Pagination.of(1, 2),
					null);

		List<AccountRole> accountRoles1 = (List<AccountRole>)page1.getItems();

		Assert.assertEquals(accountRoles1.toString(), 2, accountRoles1.size());

		Page<AccountRole> page2 =
			accountRoleResource.
				getAccountAccountRolesByExternalReferenceCodePage(
					externalReferenceCode, null, null, Pagination.of(2, 2),
					null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<AccountRole> accountRoles2 = (List<AccountRole>)page2.getItems();

		Assert.assertEquals(accountRoles2.toString(), 1, accountRoles2.size());

		Page<AccountRole> page3 =
			accountRoleResource.
				getAccountAccountRolesByExternalReferenceCodePage(
					externalReferenceCode, null, null, Pagination.of(1, 3),
					null);

		assertEqualsIgnoringOrder(
			Arrays.asList(accountRole1, accountRole2, accountRole3),
			(List<AccountRole>)page3.getItems());
	}

	@Test
	public void testGetAccountAccountRolesByExternalReferenceCodePageWithSortDateTime()
		throws Exception {

		testGetAccountAccountRolesByExternalReferenceCodePageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, accountRole1, accountRole2) -> {
				BeanUtils.setProperty(
					accountRole1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAccountAccountRolesByExternalReferenceCodePageWithSortDouble()
		throws Exception {

		testGetAccountAccountRolesByExternalReferenceCodePageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, accountRole1, accountRole2) -> {
				BeanUtils.setProperty(accountRole1, entityField.getName(), 0.1);
				BeanUtils.setProperty(accountRole2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetAccountAccountRolesByExternalReferenceCodePageWithSortInteger()
		throws Exception {

		testGetAccountAccountRolesByExternalReferenceCodePageWithSort(
			EntityField.Type.INTEGER,
			(entityField, accountRole1, accountRole2) -> {
				BeanUtils.setProperty(accountRole1, entityField.getName(), 0);
				BeanUtils.setProperty(accountRole2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAccountAccountRolesByExternalReferenceCodePageWithSortString()
		throws Exception {

		testGetAccountAccountRolesByExternalReferenceCodePageWithSort(
			EntityField.Type.STRING,
			(entityField, accountRole1, accountRole2) -> {
				Class<?> clazz = accountRole1.getClass();

				String entityFieldName = entityField.getName();

				java.lang.reflect.Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						accountRole1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						accountRole2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						accountRole1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						accountRole2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						accountRole1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						accountRole2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void
			testGetAccountAccountRolesByExternalReferenceCodePageWithSort(
				EntityField.Type type,
				UnsafeTriConsumer
					<EntityField, AccountRole, AccountRole, Exception>
						unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		String externalReferenceCode =
			testGetAccountAccountRolesByExternalReferenceCodePage_getExternalReferenceCode();

		AccountRole accountRole1 = randomAccountRole();
		AccountRole accountRole2 = randomAccountRole();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, accountRole1, accountRole2);
		}

		accountRole1 =
			testGetAccountAccountRolesByExternalReferenceCodePage_addAccountRole(
				externalReferenceCode, accountRole1);

		accountRole2 =
			testGetAccountAccountRolesByExternalReferenceCodePage_addAccountRole(
				externalReferenceCode, accountRole2);

		for (EntityField entityField : entityFields) {
			Page<AccountRole> ascPage =
				accountRoleResource.
					getAccountAccountRolesByExternalReferenceCodePage(
						externalReferenceCode, null, null, Pagination.of(1, 2),
						entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(accountRole1, accountRole2),
				(List<AccountRole>)ascPage.getItems());

			Page<AccountRole> descPage =
				accountRoleResource.
					getAccountAccountRolesByExternalReferenceCodePage(
						externalReferenceCode, null, null, Pagination.of(1, 2),
						entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(accountRole2, accountRole1),
				(List<AccountRole>)descPage.getItems());
		}
	}

	protected AccountRole
			testGetAccountAccountRolesByExternalReferenceCodePage_addAccountRole(
				String externalReferenceCode, AccountRole accountRole)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountAccountRolesByExternalReferenceCodePage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountAccountRolesByExternalReferenceCodePage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAccountAccountRoleByExternalReferenceCode()
		throws Exception {

		AccountRole randomAccountRole = randomAccountRole();

		AccountRole postAccountRole =
			testPostAccountAccountRoleByExternalReferenceCode_addAccountRole(
				randomAccountRole);

		assertEquals(randomAccountRole, postAccountRole);
		assertValid(postAccountRole);
	}

	protected AccountRole
			testPostAccountAccountRoleByExternalReferenceCode_addAccountRole(
				AccountRole accountRole)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testDeleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountRole accountRole =
			testDeleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress_addAccountRole();

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.
				deleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddressHttpResponse(
					null, accountRole.getId(), null));
	}

	protected AccountRole
			testDeleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress_addAccountRole()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountRole accountRole =
			testPostAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress_addAccountRole();

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.
				postAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddressHttpResponse(
					null, accountRole.getId(), null));

		assertHttpResponseStatusCode(
			404,
			accountRoleResource.
				postAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddressHttpResponse(
					null, 0L, null));
	}

	protected AccountRole
			testPostAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress_addAccountRole()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testGetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage()
		throws Exception {

		String externalReferenceCode =
			testGetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage_getExternalReferenceCode();
		String irrelevantExternalReferenceCode =
			testGetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage_getIrrelevantExternalReferenceCode();
		String emailAddress =
			testGetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage_getEmailAddress();
		String irrelevantEmailAddress =
			testGetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage_getIrrelevantEmailAddress();

		Page<AccountRole> page =
			accountRoleResource.
				getAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage(
					externalReferenceCode, emailAddress);

		Assert.assertEquals(0, page.getTotalCount());

		if ((irrelevantExternalReferenceCode != null) &&
			(irrelevantEmailAddress != null)) {

			AccountRole irrelevantAccountRole =
				testGetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage_addAccountRole(
					irrelevantExternalReferenceCode, irrelevantEmailAddress,
					randomIrrelevantAccountRole());

			page =
				accountRoleResource.
					getAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage(
						irrelevantExternalReferenceCode,
						irrelevantEmailAddress);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccountRole),
				(List<AccountRole>)page.getItems());
			assertValid(page);
		}

		AccountRole accountRole1 =
			testGetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage_addAccountRole(
				externalReferenceCode, emailAddress, randomAccountRole());

		AccountRole accountRole2 =
			testGetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage_addAccountRole(
				externalReferenceCode, emailAddress, randomAccountRole());

		page =
			accountRoleResource.
				getAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage(
					externalReferenceCode, emailAddress);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(accountRole1, accountRole2),
			(List<AccountRole>)page.getItems());
		assertValid(page);
	}

	protected AccountRole
			testGetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage_addAccountRole(
				String externalReferenceCode, String emailAddress,
				AccountRole accountRole)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage_getExternalReferenceCode()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage_getIrrelevantExternalReferenceCode()
		throws Exception {

		return null;
	}

	protected String
			testGetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage_getEmailAddress()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected String
			testGetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage_getIrrelevantEmailAddress()
		throws Exception {

		return null;
	}

	@Test
	public void testGetAccountAccountRolesPage() throws Exception {
		Long accountId = testGetAccountAccountRolesPage_getAccountId();
		Long irrelevantAccountId =
			testGetAccountAccountRolesPage_getIrrelevantAccountId();

		Page<AccountRole> page = accountRoleResource.getAccountAccountRolesPage(
			accountId, RandomTestUtil.randomString(), null,
			Pagination.of(1, 10), null);

		Assert.assertEquals(0, page.getTotalCount());

		if (irrelevantAccountId != null) {
			AccountRole irrelevantAccountRole =
				testGetAccountAccountRolesPage_addAccountRole(
					irrelevantAccountId, randomIrrelevantAccountRole());

			page = accountRoleResource.getAccountAccountRolesPage(
				irrelevantAccountId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantAccountRole),
				(List<AccountRole>)page.getItems());
			assertValid(page);
		}

		AccountRole accountRole1 =
			testGetAccountAccountRolesPage_addAccountRole(
				accountId, randomAccountRole());

		AccountRole accountRole2 =
			testGetAccountAccountRolesPage_addAccountRole(
				accountId, randomAccountRole());

		page = accountRoleResource.getAccountAccountRolesPage(
			accountId, null, null, Pagination.of(1, 10), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(accountRole1, accountRole2),
			(List<AccountRole>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetAccountAccountRolesPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long accountId = testGetAccountAccountRolesPage_getAccountId();

		AccountRole accountRole1 = randomAccountRole();

		accountRole1 = testGetAccountAccountRolesPage_addAccountRole(
			accountId, accountRole1);

		for (EntityField entityField : entityFields) {
			Page<AccountRole> page =
				accountRoleResource.getAccountAccountRolesPage(
					accountId, null,
					getFilterString(entityField, "between", accountRole1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(accountRole1),
				(List<AccountRole>)page.getItems());
		}
	}

	@Test
	public void testGetAccountAccountRolesPageWithFilterDoubleEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DOUBLE);

		if (entityFields.isEmpty()) {
			return;
		}

		Long accountId = testGetAccountAccountRolesPage_getAccountId();

		AccountRole accountRole1 =
			testGetAccountAccountRolesPage_addAccountRole(
				accountId, randomAccountRole());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountRole accountRole2 =
			testGetAccountAccountRolesPage_addAccountRole(
				accountId, randomAccountRole());

		for (EntityField entityField : entityFields) {
			Page<AccountRole> page =
				accountRoleResource.getAccountAccountRolesPage(
					accountId, null,
					getFilterString(entityField, "eq", accountRole1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(accountRole1),
				(List<AccountRole>)page.getItems());
		}
	}

	@Test
	public void testGetAccountAccountRolesPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long accountId = testGetAccountAccountRolesPage_getAccountId();

		AccountRole accountRole1 =
			testGetAccountAccountRolesPage_addAccountRole(
				accountId, randomAccountRole());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountRole accountRole2 =
			testGetAccountAccountRolesPage_addAccountRole(
				accountId, randomAccountRole());

		for (EntityField entityField : entityFields) {
			Page<AccountRole> page =
				accountRoleResource.getAccountAccountRolesPage(
					accountId, null,
					getFilterString(entityField, "eq", accountRole1),
					Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(accountRole1),
				(List<AccountRole>)page.getItems());
		}
	}

	@Test
	public void testGetAccountAccountRolesPageWithPagination()
		throws Exception {

		Long accountId = testGetAccountAccountRolesPage_getAccountId();

		AccountRole accountRole1 =
			testGetAccountAccountRolesPage_addAccountRole(
				accountId, randomAccountRole());

		AccountRole accountRole2 =
			testGetAccountAccountRolesPage_addAccountRole(
				accountId, randomAccountRole());

		AccountRole accountRole3 =
			testGetAccountAccountRolesPage_addAccountRole(
				accountId, randomAccountRole());

		Page<AccountRole> page1 =
			accountRoleResource.getAccountAccountRolesPage(
				accountId, null, null, Pagination.of(1, 2), null);

		List<AccountRole> accountRoles1 = (List<AccountRole>)page1.getItems();

		Assert.assertEquals(accountRoles1.toString(), 2, accountRoles1.size());

		Page<AccountRole> page2 =
			accountRoleResource.getAccountAccountRolesPage(
				accountId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<AccountRole> accountRoles2 = (List<AccountRole>)page2.getItems();

		Assert.assertEquals(accountRoles2.toString(), 1, accountRoles2.size());

		Page<AccountRole> page3 =
			accountRoleResource.getAccountAccountRolesPage(
				accountId, null, null, Pagination.of(1, 3), null);

		assertEqualsIgnoringOrder(
			Arrays.asList(accountRole1, accountRole2, accountRole3),
			(List<AccountRole>)page3.getItems());
	}

	@Test
	public void testGetAccountAccountRolesPageWithSortDateTime()
		throws Exception {

		testGetAccountAccountRolesPageWithSort(
			EntityField.Type.DATE_TIME,
			(entityField, accountRole1, accountRole2) -> {
				BeanUtils.setProperty(
					accountRole1, entityField.getName(),
					DateUtils.addMinutes(new Date(), -2));
			});
	}

	@Test
	public void testGetAccountAccountRolesPageWithSortDouble()
		throws Exception {

		testGetAccountAccountRolesPageWithSort(
			EntityField.Type.DOUBLE,
			(entityField, accountRole1, accountRole2) -> {
				BeanUtils.setProperty(accountRole1, entityField.getName(), 0.1);
				BeanUtils.setProperty(accountRole2, entityField.getName(), 0.5);
			});
	}

	@Test
	public void testGetAccountAccountRolesPageWithSortInteger()
		throws Exception {

		testGetAccountAccountRolesPageWithSort(
			EntityField.Type.INTEGER,
			(entityField, accountRole1, accountRole2) -> {
				BeanUtils.setProperty(accountRole1, entityField.getName(), 0);
				BeanUtils.setProperty(accountRole2, entityField.getName(), 1);
			});
	}

	@Test
	public void testGetAccountAccountRolesPageWithSortString()
		throws Exception {

		testGetAccountAccountRolesPageWithSort(
			EntityField.Type.STRING,
			(entityField, accountRole1, accountRole2) -> {
				Class<?> clazz = accountRole1.getClass();

				String entityFieldName = entityField.getName();

				java.lang.reflect.Method method = clazz.getMethod(
					"get" + StringUtil.upperCaseFirstLetter(entityFieldName));

				Class<?> returnType = method.getReturnType();

				if (returnType.isAssignableFrom(Map.class)) {
					BeanUtils.setProperty(
						accountRole1, entityFieldName,
						Collections.singletonMap("Aaa", "Aaa"));
					BeanUtils.setProperty(
						accountRole2, entityFieldName,
						Collections.singletonMap("Bbb", "Bbb"));
				}
				else if (entityFieldName.contains("email")) {
					BeanUtils.setProperty(
						accountRole1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
					BeanUtils.setProperty(
						accountRole2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()) +
									"@liferay.com");
				}
				else {
					BeanUtils.setProperty(
						accountRole1, entityFieldName,
						"aaa" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
					BeanUtils.setProperty(
						accountRole2, entityFieldName,
						"bbb" +
							StringUtil.toLowerCase(
								RandomTestUtil.randomString()));
				}
			});
	}

	protected void testGetAccountAccountRolesPageWithSort(
			EntityField.Type type,
			UnsafeTriConsumer<EntityField, AccountRole, AccountRole, Exception>
				unsafeTriConsumer)
		throws Exception {

		List<EntityField> entityFields = getEntityFields(type);

		if (entityFields.isEmpty()) {
			return;
		}

		Long accountId = testGetAccountAccountRolesPage_getAccountId();

		AccountRole accountRole1 = randomAccountRole();
		AccountRole accountRole2 = randomAccountRole();

		for (EntityField entityField : entityFields) {
			unsafeTriConsumer.accept(entityField, accountRole1, accountRole2);
		}

		accountRole1 = testGetAccountAccountRolesPage_addAccountRole(
			accountId, accountRole1);

		accountRole2 = testGetAccountAccountRolesPage_addAccountRole(
			accountId, accountRole2);

		for (EntityField entityField : entityFields) {
			Page<AccountRole> ascPage =
				accountRoleResource.getAccountAccountRolesPage(
					accountId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(accountRole1, accountRole2),
				(List<AccountRole>)ascPage.getItems());

			Page<AccountRole> descPage =
				accountRoleResource.getAccountAccountRolesPage(
					accountId, null, null, Pagination.of(1, 2),
					entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(accountRole2, accountRole1),
				(List<AccountRole>)descPage.getItems());
		}
	}

	protected AccountRole testGetAccountAccountRolesPage_addAccountRole(
			Long accountId, AccountRole accountRole)
		throws Exception {

		return accountRoleResource.postAccountAccountRole(
			accountId, accountRole);
	}

	protected Long testGetAccountAccountRolesPage_getAccountId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetAccountAccountRolesPage_getIrrelevantAccountId()
		throws Exception {

		return null;
	}

	@Test
	public void testPostAccountAccountRole() throws Exception {
		AccountRole randomAccountRole = randomAccountRole();

		AccountRole postAccountRole = testPostAccountAccountRole_addAccountRole(
			randomAccountRole);

		assertEquals(randomAccountRole, postAccountRole);
		assertValid(postAccountRole);
	}

	protected AccountRole testPostAccountAccountRole_addAccountRole(
			AccountRole accountRole)
		throws Exception {

		return accountRoleResource.postAccountAccountRole(
			testGetAccountAccountRolesPage_getAccountId(), accountRole);
	}

	@Test
	public void testDeleteAccountAccountRoleUserAccountAssociation()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountRole accountRole =
			testDeleteAccountAccountRoleUserAccountAssociation_addAccountRole();

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.
				deleteAccountAccountRoleUserAccountAssociationHttpResponse(
					accountRole.getAccountId(), accountRole.getId(), null));
	}

	protected AccountRole
			testDeleteAccountAccountRoleUserAccountAssociation_addAccountRole()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Test
	public void testPostAccountAccountRoleUserAccountAssociation()
		throws Exception {

		@SuppressWarnings("PMD.UnusedLocalVariable")
		AccountRole accountRole =
			testPostAccountAccountRoleUserAccountAssociation_addAccountRole();

		assertHttpResponseStatusCode(
			204,
			accountRoleResource.
				postAccountAccountRoleUserAccountAssociationHttpResponse(
					accountRole.getAccountId(), accountRole.getId(), null));

		assertHttpResponseStatusCode(
			404,
			accountRoleResource.
				postAccountAccountRoleUserAccountAssociationHttpResponse(
					accountRole.getAccountId(), 0L, null));
	}

	protected AccountRole
			testPostAccountAccountRoleUserAccountAssociation_addAccountRole()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected AccountRole testGraphQLAccountRole_addAccountRole()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected void assertContains(
		AccountRole accountRole, List<AccountRole> accountRoles) {

		boolean contains = false;

		for (AccountRole item : accountRoles) {
			if (equals(accountRole, item)) {
				contains = true;

				break;
			}
		}

		Assert.assertTrue(
			accountRoles + " does not contain " + accountRole, contains);
	}

	protected void assertHttpResponseStatusCode(
		int expectedHttpResponseStatusCode,
		HttpInvoker.HttpResponse actualHttpResponse) {

		Assert.assertEquals(
			expectedHttpResponseStatusCode, actualHttpResponse.getStatusCode());
	}

	protected void assertEquals(
		AccountRole accountRole1, AccountRole accountRole2) {

		Assert.assertTrue(
			accountRole1 + " does not equal " + accountRole2,
			equals(accountRole1, accountRole2));
	}

	protected void assertEquals(
		List<AccountRole> accountRoles1, List<AccountRole> accountRoles2) {

		Assert.assertEquals(accountRoles1.size(), accountRoles2.size());

		for (int i = 0; i < accountRoles1.size(); i++) {
			AccountRole accountRole1 = accountRoles1.get(i);
			AccountRole accountRole2 = accountRoles2.get(i);

			assertEquals(accountRole1, accountRole2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<AccountRole> accountRoles1, List<AccountRole> accountRoles2) {

		Assert.assertEquals(accountRoles1.size(), accountRoles2.size());

		for (AccountRole accountRole1 : accountRoles1) {
			boolean contains = false;

			for (AccountRole accountRole2 : accountRoles2) {
				if (equals(accountRole1, accountRole2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				accountRoles2 + " does not contain " + accountRole1, contains);
		}
	}

	protected void assertValid(AccountRole accountRole) throws Exception {
		boolean valid = true;

		if (accountRole.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (accountRole.getAccountId() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (accountRole.getDescription() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("displayName", additionalAssertFieldName)) {
				if (accountRole.getDisplayName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (accountRole.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("roleId", additionalAssertFieldName)) {
				if (accountRole.getRoleId() == null) {
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

	protected void assertValid(Page<AccountRole> page) {
		boolean valid = false;

		java.util.Collection<AccountRole> accountRoles = page.getItems();

		int size = accountRoles.size();

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
					com.liferay.headless.admin.user.dto.v1_0.AccountRole.
						class)) {

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
		AccountRole accountRole1, AccountRole accountRole2) {

		if (accountRole1 == accountRole2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("accountId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountRole1.getAccountId(),
						accountRole2.getAccountId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("description", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountRole1.getDescription(),
						accountRole2.getDescription())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("displayName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountRole1.getDisplayName(),
						accountRole2.getDisplayName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountRole1.getId(), accountRole2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountRole1.getName(), accountRole2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("roleId", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						accountRole1.getRoleId(), accountRole2.getRoleId())) {

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

		if (!(_accountRoleResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_accountRoleResource;

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
		EntityField entityField, String operator, AccountRole accountRole) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("accountId")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("description")) {
			sb.append("'");
			sb.append(String.valueOf(accountRole.getDescription()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("displayName")) {
			sb.append("'");
			sb.append(String.valueOf(accountRole.getDisplayName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(accountRole.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("roleId")) {
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

	protected AccountRole randomAccountRole() throws Exception {
		return new AccountRole() {
			{
				accountId = RandomTestUtil.randomLong();
				description = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				displayName = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				name = StringUtil.toLowerCase(RandomTestUtil.randomString());
				roleId = RandomTestUtil.randomLong();
			}
		};
	}

	protected AccountRole randomIrrelevantAccountRole() throws Exception {
		AccountRole randomIrrelevantAccountRole = randomAccountRole();

		return randomIrrelevantAccountRole;
	}

	protected AccountRole randomPatchAccountRole() throws Exception {
		return randomAccountRole();
	}

	protected AccountRoleResource accountRoleResource;
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
		LogFactoryUtil.getLog(BaseAccountRoleResourceTestCase.class);

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
	private com.liferay.headless.admin.user.resource.v1_0.AccountRoleResource
		_accountRoleResource;

}