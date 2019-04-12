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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.headless.admin.user.dto.v1_0.ContactInformation;
import com.liferay.headless.admin.user.dto.v1_0.OrganizationBrief;
import com.liferay.headless.admin.user.dto.v1_0.RoleBrief;
import com.liferay.headless.admin.user.dto.v1_0.SiteBrief;
import com.liferay.headless.admin.user.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.resource.v1_0.UserAccountResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.lang.reflect.InvocationTargetException;

import java.net.URL;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

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
public abstract class BaseUserAccountResourceTestCase {

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
		testLocale = LocaleUtil.getDefault();

		_resourceURL = new URL(
			"http://localhost:8080/o/headless-admin-user/v1.0");
	}

	@After
	public void tearDown() throws Exception {
		GroupTestUtil.deleteGroup(irrelevantGroup);
		GroupTestUtil.deleteGroup(testGroup);
	}

	@Test
	public void testGetMyUserAccount() throws Exception {
		UserAccount postUserAccount = testGetMyUserAccount_addUserAccount();

		UserAccount getUserAccount = invokeGetMyUserAccount();

		assertEquals(postUserAccount, getUserAccount);
		assertValid(getUserAccount);
	}

	protected UserAccount testGetMyUserAccount_addUserAccount()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected UserAccount invokeGetMyUserAccount() throws Exception {
		Http.Options options = _createHttpOptions();

		String location = _resourceURL + "/my-user-account";

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, UserAccount.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetMyUserAccountResponse() throws Exception {
		Http.Options options = _createHttpOptions();

		String location = _resourceURL + "/my-user-account";

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetOrganizationUserAccountsPage() throws Exception {
		Long organizationId =
			testGetOrganizationUserAccountsPage_getOrganizationId();
		Long irrelevantOrganizationId =
			testGetOrganizationUserAccountsPage_getIrrelevantOrganizationId();

		if ((irrelevantOrganizationId != null)) {
			UserAccount irrelevantUserAccount =
				testGetOrganizationUserAccountsPage_addUserAccount(
					irrelevantOrganizationId, randomIrrelevantUserAccount());

			Page<UserAccount> page = invokeGetOrganizationUserAccountsPage(
				irrelevantOrganizationId, null, null, Pagination.of(1, 2),
				null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantUserAccount),
				(List<UserAccount>)page.getItems());
			assertValid(page);
		}

		UserAccount userAccount1 =
			testGetOrganizationUserAccountsPage_addUserAccount(
				organizationId, randomUserAccount());

		UserAccount userAccount2 =
			testGetOrganizationUserAccountsPage_addUserAccount(
				organizationId, randomUserAccount());

		Page<UserAccount> page = invokeGetOrganizationUserAccountsPage(
			organizationId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2),
			(List<UserAccount>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetOrganizationUserAccountsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long organizationId =
			testGetOrganizationUserAccountsPage_getOrganizationId();

		UserAccount userAccount1 = randomUserAccount();
		UserAccount userAccount2 = randomUserAccount();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				userAccount1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		userAccount1 = testGetOrganizationUserAccountsPage_addUserAccount(
			organizationId, userAccount1);

		Thread.sleep(1000);

		userAccount2 = testGetOrganizationUserAccountsPage_addUserAccount(
			organizationId, userAccount2);

		for (EntityField entityField : entityFields) {
			Page<UserAccount> page = invokeGetOrganizationUserAccountsPage(
				organizationId, null,
				getFilterString(entityField, "eq", userAccount1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(userAccount1),
				(List<UserAccount>)page.getItems());
		}
	}

	@Test
	public void testGetOrganizationUserAccountsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long organizationId =
			testGetOrganizationUserAccountsPage_getOrganizationId();

		UserAccount userAccount1 =
			testGetOrganizationUserAccountsPage_addUserAccount(
				organizationId, randomUserAccount());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		UserAccount userAccount2 =
			testGetOrganizationUserAccountsPage_addUserAccount(
				organizationId, randomUserAccount());

		for (EntityField entityField : entityFields) {
			Page<UserAccount> page = invokeGetOrganizationUserAccountsPage(
				organizationId, null,
				getFilterString(entityField, "eq", userAccount1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(userAccount1),
				(List<UserAccount>)page.getItems());
		}
	}

	@Test
	public void testGetOrganizationUserAccountsPageWithPagination()
		throws Exception {

		Long organizationId =
			testGetOrganizationUserAccountsPage_getOrganizationId();

		UserAccount userAccount1 =
			testGetOrganizationUserAccountsPage_addUserAccount(
				organizationId, randomUserAccount());

		UserAccount userAccount2 =
			testGetOrganizationUserAccountsPage_addUserAccount(
				organizationId, randomUserAccount());

		UserAccount userAccount3 =
			testGetOrganizationUserAccountsPage_addUserAccount(
				organizationId, randomUserAccount());

		Page<UserAccount> page1 = invokeGetOrganizationUserAccountsPage(
			organizationId, null, null, Pagination.of(1, 2), null);

		List<UserAccount> userAccounts1 = (List<UserAccount>)page1.getItems();

		Assert.assertEquals(userAccounts1.toString(), 2, userAccounts1.size());

		Page<UserAccount> page2 = invokeGetOrganizationUserAccountsPage(
			organizationId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<UserAccount> userAccounts2 = (List<UserAccount>)page2.getItems();

		Assert.assertEquals(userAccounts2.toString(), 1, userAccounts2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2, userAccount3),
			new ArrayList<UserAccount>() {
				{
					addAll(userAccounts1);
					addAll(userAccounts2);
				}
			});
	}

	@Test
	public void testGetOrganizationUserAccountsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long organizationId =
			testGetOrganizationUserAccountsPage_getOrganizationId();

		UserAccount userAccount1 = randomUserAccount();
		UserAccount userAccount2 = randomUserAccount();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				userAccount1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		userAccount1 = testGetOrganizationUserAccountsPage_addUserAccount(
			organizationId, userAccount1);

		Thread.sleep(1000);

		userAccount2 = testGetOrganizationUserAccountsPage_addUserAccount(
			organizationId, userAccount2);

		for (EntityField entityField : entityFields) {
			Page<UserAccount> ascPage = invokeGetOrganizationUserAccountsPage(
				organizationId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(userAccount1, userAccount2),
				(List<UserAccount>)ascPage.getItems());

			Page<UserAccount> descPage = invokeGetOrganizationUserAccountsPage(
				organizationId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(userAccount2, userAccount1),
				(List<UserAccount>)descPage.getItems());
		}
	}

	@Test
	public void testGetOrganizationUserAccountsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long organizationId =
			testGetOrganizationUserAccountsPage_getOrganizationId();

		UserAccount userAccount1 = randomUserAccount();
		UserAccount userAccount2 = randomUserAccount();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(userAccount1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(userAccount2, entityField.getName(), "Bbb");
		}

		userAccount1 = testGetOrganizationUserAccountsPage_addUserAccount(
			organizationId, userAccount1);

		userAccount2 = testGetOrganizationUserAccountsPage_addUserAccount(
			organizationId, userAccount2);

		for (EntityField entityField : entityFields) {
			Page<UserAccount> ascPage = invokeGetOrganizationUserAccountsPage(
				organizationId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(userAccount1, userAccount2),
				(List<UserAccount>)ascPage.getItems());

			Page<UserAccount> descPage = invokeGetOrganizationUserAccountsPage(
				organizationId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(userAccount2, userAccount1),
				(List<UserAccount>)descPage.getItems());
		}
	}

	protected UserAccount testGetOrganizationUserAccountsPage_addUserAccount(
			Long organizationId, UserAccount userAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetOrganizationUserAccountsPage_getOrganizationId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long
			testGetOrganizationUserAccountsPage_getIrrelevantOrganizationId()
		throws Exception {

		return null;
	}

	protected Page<UserAccount> invokeGetOrganizationUserAccountsPage(
			Long organizationId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/organizations/{organizationId}/user-accounts",
					organizationId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<UserAccount>>() {
			});
	}

	protected Http.Response invokeGetOrganizationUserAccountsPageResponse(
			Long organizationId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath(
					"/organizations/{organizationId}/user-accounts",
					organizationId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetUserAccountsPage() throws Exception {
		UserAccount userAccount1 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());

		UserAccount userAccount2 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());

		Page<UserAccount> page = invokeGetUserAccountsPage(
			null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2),
			(List<UserAccount>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetUserAccountsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		UserAccount userAccount1 = randomUserAccount();
		UserAccount userAccount2 = randomUserAccount();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				userAccount1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		userAccount1 = testGetUserAccountsPage_addUserAccount(userAccount1);

		Thread.sleep(1000);

		userAccount2 = testGetUserAccountsPage_addUserAccount(userAccount2);

		for (EntityField entityField : entityFields) {
			Page<UserAccount> page = invokeGetUserAccountsPage(
				null, getFilterString(entityField, "eq", userAccount1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(userAccount1),
				(List<UserAccount>)page.getItems());
		}
	}

	@Test
	public void testGetUserAccountsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		UserAccount userAccount1 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		UserAccount userAccount2 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());

		for (EntityField entityField : entityFields) {
			Page<UserAccount> page = invokeGetUserAccountsPage(
				null, getFilterString(entityField, "eq", userAccount1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(userAccount1),
				(List<UserAccount>)page.getItems());
		}
	}

	@Test
	public void testGetUserAccountsPageWithPagination() throws Exception {
		UserAccount userAccount1 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());

		UserAccount userAccount2 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());

		UserAccount userAccount3 = testGetUserAccountsPage_addUserAccount(
			randomUserAccount());

		Page<UserAccount> page1 = invokeGetUserAccountsPage(
			null, null, Pagination.of(1, 2), null);

		List<UserAccount> userAccounts1 = (List<UserAccount>)page1.getItems();

		Assert.assertEquals(userAccounts1.toString(), 2, userAccounts1.size());

		Page<UserAccount> page2 = invokeGetUserAccountsPage(
			null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<UserAccount> userAccounts2 = (List<UserAccount>)page2.getItems();

		Assert.assertEquals(userAccounts2.toString(), 1, userAccounts2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2, userAccount3),
			new ArrayList<UserAccount>() {
				{
					addAll(userAccounts1);
					addAll(userAccounts2);
				}
			});
	}

	@Test
	public void testGetUserAccountsPageWithSortDateTime() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		UserAccount userAccount1 = randomUserAccount();
		UserAccount userAccount2 = randomUserAccount();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				userAccount1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		userAccount1 = testGetUserAccountsPage_addUserAccount(userAccount1);

		Thread.sleep(1000);

		userAccount2 = testGetUserAccountsPage_addUserAccount(userAccount2);

		for (EntityField entityField : entityFields) {
			Page<UserAccount> ascPage = invokeGetUserAccountsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(userAccount1, userAccount2),
				(List<UserAccount>)ascPage.getItems());

			Page<UserAccount> descPage = invokeGetUserAccountsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(userAccount2, userAccount1),
				(List<UserAccount>)descPage.getItems());
		}
	}

	@Test
	public void testGetUserAccountsPageWithSortString() throws Exception {
		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		UserAccount userAccount1 = randomUserAccount();
		UserAccount userAccount2 = randomUserAccount();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(userAccount1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(userAccount2, entityField.getName(), "Bbb");
		}

		userAccount1 = testGetUserAccountsPage_addUserAccount(userAccount1);

		userAccount2 = testGetUserAccountsPage_addUserAccount(userAccount2);

		for (EntityField entityField : entityFields) {
			Page<UserAccount> ascPage = invokeGetUserAccountsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(userAccount1, userAccount2),
				(List<UserAccount>)ascPage.getItems());

			Page<UserAccount> descPage = invokeGetUserAccountsPage(
				null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(userAccount2, userAccount1),
				(List<UserAccount>)descPage.getItems());
		}
	}

	protected UserAccount testGetUserAccountsPage_addUserAccount(
			UserAccount userAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Page<UserAccount> invokeGetUserAccountsPage(
			String search, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/user-accounts");

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<UserAccount>>() {
			});
	}

	protected Http.Response invokeGetUserAccountsPageResponse(
			String search, String filterString, Pagination pagination,
			String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location = _resourceURL + _toPath("/user-accounts");

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetUserAccount() throws Exception {
		UserAccount postUserAccount = testGetUserAccount_addUserAccount();

		UserAccount getUserAccount = invokeGetUserAccount(
			postUserAccount.getId());

		assertEquals(postUserAccount, getUserAccount);
		assertValid(getUserAccount);
	}

	protected UserAccount testGetUserAccount_addUserAccount() throws Exception {
		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected UserAccount invokeGetUserAccount(Long userAccountId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/user-accounts/{userAccountId}", userAccountId);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		try {
			return outputObjectMapper.readValue(string, UserAccount.class);
		}
		catch (Exception e) {
			_log.error("Unable to process HTTP response: " + string, e);

			throw e;
		}
	}

	protected Http.Response invokeGetUserAccountResponse(Long userAccountId)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/user-accounts/{userAccountId}", userAccountId);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	@Test
	public void testGetWebSiteUserAccountsPage() throws Exception {
		Long webSiteId = testGetWebSiteUserAccountsPage_getWebSiteId();
		Long irrelevantWebSiteId =
			testGetWebSiteUserAccountsPage_getIrrelevantWebSiteId();

		if ((irrelevantWebSiteId != null)) {
			UserAccount irrelevantUserAccount =
				testGetWebSiteUserAccountsPage_addUserAccount(
					irrelevantWebSiteId, randomIrrelevantUserAccount());

			Page<UserAccount> page = invokeGetWebSiteUserAccountsPage(
				irrelevantWebSiteId, null, null, Pagination.of(1, 2), null);

			Assert.assertEquals(1, page.getTotalCount());

			assertEquals(
				Arrays.asList(irrelevantUserAccount),
				(List<UserAccount>)page.getItems());
			assertValid(page);
		}

		UserAccount userAccount1 =
			testGetWebSiteUserAccountsPage_addUserAccount(
				webSiteId, randomUserAccount());

		UserAccount userAccount2 =
			testGetWebSiteUserAccountsPage_addUserAccount(
				webSiteId, randomUserAccount());

		Page<UserAccount> page = invokeGetWebSiteUserAccountsPage(
			webSiteId, null, null, Pagination.of(1, 2), null);

		Assert.assertEquals(2, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2),
			(List<UserAccount>)page.getItems());
		assertValid(page);
	}

	@Test
	public void testGetWebSiteUserAccountsPageWithFilterDateTimeEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long webSiteId = testGetWebSiteUserAccountsPage_getWebSiteId();

		UserAccount userAccount1 = randomUserAccount();
		UserAccount userAccount2 = randomUserAccount();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				userAccount1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		userAccount1 = testGetWebSiteUserAccountsPage_addUserAccount(
			webSiteId, userAccount1);

		Thread.sleep(1000);

		userAccount2 = testGetWebSiteUserAccountsPage_addUserAccount(
			webSiteId, userAccount2);

		for (EntityField entityField : entityFields) {
			Page<UserAccount> page = invokeGetWebSiteUserAccountsPage(
				webSiteId, null,
				getFilterString(entityField, "eq", userAccount1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(userAccount1),
				(List<UserAccount>)page.getItems());
		}
	}

	@Test
	public void testGetWebSiteUserAccountsPageWithFilterStringEquals()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long webSiteId = testGetWebSiteUserAccountsPage_getWebSiteId();

		UserAccount userAccount1 =
			testGetWebSiteUserAccountsPage_addUserAccount(
				webSiteId, randomUserAccount());

		@SuppressWarnings("PMD.UnusedLocalVariable")
		UserAccount userAccount2 =
			testGetWebSiteUserAccountsPage_addUserAccount(
				webSiteId, randomUserAccount());

		for (EntityField entityField : entityFields) {
			Page<UserAccount> page = invokeGetWebSiteUserAccountsPage(
				webSiteId, null,
				getFilterString(entityField, "eq", userAccount1),
				Pagination.of(1, 2), null);

			assertEquals(
				Collections.singletonList(userAccount1),
				(List<UserAccount>)page.getItems());
		}
	}

	@Test
	public void testGetWebSiteUserAccountsPageWithPagination()
		throws Exception {

		Long webSiteId = testGetWebSiteUserAccountsPage_getWebSiteId();

		UserAccount userAccount1 =
			testGetWebSiteUserAccountsPage_addUserAccount(
				webSiteId, randomUserAccount());

		UserAccount userAccount2 =
			testGetWebSiteUserAccountsPage_addUserAccount(
				webSiteId, randomUserAccount());

		UserAccount userAccount3 =
			testGetWebSiteUserAccountsPage_addUserAccount(
				webSiteId, randomUserAccount());

		Page<UserAccount> page1 = invokeGetWebSiteUserAccountsPage(
			webSiteId, null, null, Pagination.of(1, 2), null);

		List<UserAccount> userAccounts1 = (List<UserAccount>)page1.getItems();

		Assert.assertEquals(userAccounts1.toString(), 2, userAccounts1.size());

		Page<UserAccount> page2 = invokeGetWebSiteUserAccountsPage(
			webSiteId, null, null, Pagination.of(2, 2), null);

		Assert.assertEquals(3, page2.getTotalCount());

		List<UserAccount> userAccounts2 = (List<UserAccount>)page2.getItems();

		Assert.assertEquals(userAccounts2.toString(), 1, userAccounts2.size());

		assertEqualsIgnoringOrder(
			Arrays.asList(userAccount1, userAccount2, userAccount3),
			new ArrayList<UserAccount>() {
				{
					addAll(userAccounts1);
					addAll(userAccounts2);
				}
			});
	}

	@Test
	public void testGetWebSiteUserAccountsPageWithSortDateTime()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.DATE_TIME);

		if (entityFields.isEmpty()) {
			return;
		}

		Long webSiteId = testGetWebSiteUserAccountsPage_getWebSiteId();

		UserAccount userAccount1 = randomUserAccount();
		UserAccount userAccount2 = randomUserAccount();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(
				userAccount1, entityField.getName(),
				DateUtils.addMinutes(new Date(), -2));
		}

		userAccount1 = testGetWebSiteUserAccountsPage_addUserAccount(
			webSiteId, userAccount1);

		Thread.sleep(1000);

		userAccount2 = testGetWebSiteUserAccountsPage_addUserAccount(
			webSiteId, userAccount2);

		for (EntityField entityField : entityFields) {
			Page<UserAccount> ascPage = invokeGetWebSiteUserAccountsPage(
				webSiteId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(userAccount1, userAccount2),
				(List<UserAccount>)ascPage.getItems());

			Page<UserAccount> descPage = invokeGetWebSiteUserAccountsPage(
				webSiteId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(userAccount2, userAccount1),
				(List<UserAccount>)descPage.getItems());
		}
	}

	@Test
	public void testGetWebSiteUserAccountsPageWithSortString()
		throws Exception {

		List<EntityField> entityFields = getEntityFields(
			EntityField.Type.STRING);

		if (entityFields.isEmpty()) {
			return;
		}

		Long webSiteId = testGetWebSiteUserAccountsPage_getWebSiteId();

		UserAccount userAccount1 = randomUserAccount();
		UserAccount userAccount2 = randomUserAccount();

		for (EntityField entityField : entityFields) {
			BeanUtils.setProperty(userAccount1, entityField.getName(), "Aaa");
			BeanUtils.setProperty(userAccount2, entityField.getName(), "Bbb");
		}

		userAccount1 = testGetWebSiteUserAccountsPage_addUserAccount(
			webSiteId, userAccount1);

		userAccount2 = testGetWebSiteUserAccountsPage_addUserAccount(
			webSiteId, userAccount2);

		for (EntityField entityField : entityFields) {
			Page<UserAccount> ascPage = invokeGetWebSiteUserAccountsPage(
				webSiteId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":asc");

			assertEquals(
				Arrays.asList(userAccount1, userAccount2),
				(List<UserAccount>)ascPage.getItems());

			Page<UserAccount> descPage = invokeGetWebSiteUserAccountsPage(
				webSiteId, null, null, Pagination.of(1, 2),
				entityField.getName() + ":desc");

			assertEquals(
				Arrays.asList(userAccount2, userAccount1),
				(List<UserAccount>)descPage.getItems());
		}
	}

	protected UserAccount testGetWebSiteUserAccountsPage_addUserAccount(
			Long webSiteId, UserAccount userAccount)
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetWebSiteUserAccountsPage_getWebSiteId()
		throws Exception {

		throw new UnsupportedOperationException(
			"This method needs to be implemented");
	}

	protected Long testGetWebSiteUserAccountsPage_getIrrelevantWebSiteId()
		throws Exception {

		return null;
	}

	protected Page<UserAccount> invokeGetWebSiteUserAccountsPage(
			Long webSiteId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/web-sites/{webSiteId}/user-accounts", webSiteId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		String string = HttpUtil.URLtoString(options);

		if (_log.isDebugEnabled()) {
			_log.debug("HTTP response: " + string);
		}

		return outputObjectMapper.readValue(
			string,
			new TypeReference<Page<UserAccount>>() {
			});
	}

	protected Http.Response invokeGetWebSiteUserAccountsPageResponse(
			Long webSiteId, String search, String filterString,
			Pagination pagination, String sortString)
		throws Exception {

		Http.Options options = _createHttpOptions();

		String location =
			_resourceURL +
				_toPath("/web-sites/{webSiteId}/user-accounts", webSiteId);

		location = HttpUtil.addParameter(location, "filter", filterString);

		location = HttpUtil.addParameter(
			location, "page", pagination.getPage());
		location = HttpUtil.addParameter(
			location, "pageSize", pagination.getPageSize());

		location = HttpUtil.addParameter(location, "sort", sortString);

		options.setLocation(location);

		HttpUtil.URLtoByteArray(options);

		return options.getResponse();
	}

	protected void assertResponseCode(
		int expectedResponseCode, Http.Response actualResponse) {

		Assert.assertEquals(
			expectedResponseCode, actualResponse.getResponseCode());
	}

	protected void assertEquals(
		UserAccount userAccount1, UserAccount userAccount2) {

		Assert.assertTrue(
			userAccount1 + " does not equal " + userAccount2,
			equals(userAccount1, userAccount2));
	}

	protected void assertEquals(
		List<UserAccount> userAccounts1, List<UserAccount> userAccounts2) {

		Assert.assertEquals(userAccounts1.size(), userAccounts2.size());

		for (int i = 0; i < userAccounts1.size(); i++) {
			UserAccount userAccount1 = userAccounts1.get(i);
			UserAccount userAccount2 = userAccounts2.get(i);

			assertEquals(userAccount1, userAccount2);
		}
	}

	protected void assertEqualsIgnoringOrder(
		List<UserAccount> userAccounts1, List<UserAccount> userAccounts2) {

		Assert.assertEquals(userAccounts1.size(), userAccounts2.size());

		for (UserAccount userAccount1 : userAccounts1) {
			boolean contains = false;

			for (UserAccount userAccount2 : userAccounts2) {
				if (equals(userAccount1, userAccount2)) {
					contains = true;

					break;
				}
			}

			Assert.assertTrue(
				userAccounts2 + " does not contain " + userAccount1, contains);
		}
	}

	protected void assertValid(UserAccount userAccount) {
		boolean valid = true;

		if (userAccount.getDateCreated() == null) {
			valid = false;
		}

		if (userAccount.getDateModified() == null) {
			valid = false;
		}

		if (userAccount.getId() == null) {
			valid = false;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("additionalName", additionalAssertFieldName)) {
				if (userAccount.getAdditionalName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("alternateName", additionalAssertFieldName)) {
				if (userAccount.getAlternateName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("birthDate", additionalAssertFieldName)) {
				if (userAccount.getBirthDate() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"contactInformation", additionalAssertFieldName)) {

				if (userAccount.getContactInformation() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("dashboardURL", additionalAssertFieldName)) {
				if (userAccount.getDashboardURL() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("email", additionalAssertFieldName)) {
				if (userAccount.getEmail() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("familyName", additionalAssertFieldName)) {
				if (userAccount.getFamilyName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("givenName", additionalAssertFieldName)) {
				if (userAccount.getGivenName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("honorificPrefix", additionalAssertFieldName)) {
				if (userAccount.getHonorificPrefix() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("honorificSuffix", additionalAssertFieldName)) {
				if (userAccount.getHonorificSuffix() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (userAccount.getImage() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("jobTitle", additionalAssertFieldName)) {
				if (userAccount.getJobTitle() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (userAccount.getKeywords() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (userAccount.getName() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals(
					"organizationBriefs", additionalAssertFieldName)) {

				if (userAccount.getOrganizationBriefs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("profileURL", additionalAssertFieldName)) {
				if (userAccount.getProfileURL() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("roleBriefs", additionalAssertFieldName)) {
				if (userAccount.getRoleBriefs() == null) {
					valid = false;
				}

				continue;
			}

			if (Objects.equals("siteBriefs", additionalAssertFieldName)) {
				if (userAccount.getSiteBriefs() == null) {
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

	protected void assertValid(Page<UserAccount> page) {
		boolean valid = false;

		Collection<UserAccount> userAccounts = page.getItems();

		int size = userAccounts.size();

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

	protected boolean equals(
		UserAccount userAccount1, UserAccount userAccount2) {

		if (userAccount1 == userAccount2) {
			return true;
		}

		for (String additionalAssertFieldName :
				getAdditionalAssertFieldNames()) {

			if (Objects.equals("additionalName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getAdditionalName(),
						userAccount2.getAdditionalName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("alternateName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getAlternateName(),
						userAccount2.getAlternateName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("birthDate", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getBirthDate(),
						userAccount2.getBirthDate())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"contactInformation", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						userAccount1.getContactInformation(),
						userAccount2.getContactInformation())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dashboardURL", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getDashboardURL(),
						userAccount2.getDashboardURL())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateCreated", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getDateCreated(),
						userAccount2.getDateCreated())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("dateModified", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getDateModified(),
						userAccount2.getDateModified())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("email", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getEmail(), userAccount2.getEmail())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("familyName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getFamilyName(),
						userAccount2.getFamilyName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("givenName", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getGivenName(),
						userAccount2.getGivenName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("honorificPrefix", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getHonorificPrefix(),
						userAccount2.getHonorificPrefix())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("honorificSuffix", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getHonorificSuffix(),
						userAccount2.getHonorificSuffix())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("id", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getId(), userAccount2.getId())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("image", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getImage(), userAccount2.getImage())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("jobTitle", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getJobTitle(),
						userAccount2.getJobTitle())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("keywords", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getKeywords(),
						userAccount2.getKeywords())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("name", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getName(), userAccount2.getName())) {

					return false;
				}

				continue;
			}

			if (Objects.equals(
					"organizationBriefs", additionalAssertFieldName)) {

				if (!Objects.deepEquals(
						userAccount1.getOrganizationBriefs(),
						userAccount2.getOrganizationBriefs())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("profileURL", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getProfileURL(),
						userAccount2.getProfileURL())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("roleBriefs", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getRoleBriefs(),
						userAccount2.getRoleBriefs())) {

					return false;
				}

				continue;
			}

			if (Objects.equals("siteBriefs", additionalAssertFieldName)) {
				if (!Objects.deepEquals(
						userAccount1.getSiteBriefs(),
						userAccount2.getSiteBriefs())) {

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

	protected Collection<EntityField> getEntityFields() throws Exception {
		if (!(_userAccountResource instanceof EntityModelResource)) {
			throw new UnsupportedOperationException(
				"Resource is not an instance of EntityModelResource");
		}

		EntityModelResource entityModelResource =
			(EntityModelResource)_userAccountResource;

		EntityModel entityModel = entityModelResource.getEntityModel(
			new MultivaluedHashMap());

		Map<String, EntityField> entityFieldsMap =
			entityModel.getEntityFieldsMap();

		return entityFieldsMap.values();
	}

	protected List<EntityField> getEntityFields(EntityField.Type type)
		throws Exception {

		Collection<EntityField> entityFields = getEntityFields();

		Stream<EntityField> stream = entityFields.stream();

		return stream.filter(
			entityField -> Objects.equals(entityField.getType(), type)
		).collect(
			Collectors.toList()
		);
	}

	protected String getFilterString(
		EntityField entityField, String operator, UserAccount userAccount) {

		StringBundler sb = new StringBundler();

		String entityFieldName = entityField.getName();

		sb.append(entityFieldName);

		sb.append(" ");
		sb.append(operator);
		sb.append(" ");

		if (entityFieldName.equals("additionalName")) {
			sb.append("'");
			sb.append(String.valueOf(userAccount.getAdditionalName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("alternateName")) {
			sb.append("'");
			sb.append(String.valueOf(userAccount.getAlternateName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("birthDate")) {
			sb.append(_dateFormat.format(userAccount.getBirthDate()));

			return sb.toString();
		}

		if (entityFieldName.equals("contactInformation")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("dashboardURL")) {
			sb.append("'");
			sb.append(String.valueOf(userAccount.getDashboardURL()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("dateCreated")) {
			sb.append(_dateFormat.format(userAccount.getDateCreated()));

			return sb.toString();
		}

		if (entityFieldName.equals("dateModified")) {
			sb.append(_dateFormat.format(userAccount.getDateModified()));

			return sb.toString();
		}

		if (entityFieldName.equals("email")) {
			sb.append("'");
			sb.append(String.valueOf(userAccount.getEmail()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("familyName")) {
			sb.append("'");
			sb.append(String.valueOf(userAccount.getFamilyName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("givenName")) {
			sb.append("'");
			sb.append(String.valueOf(userAccount.getGivenName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("honorificPrefix")) {
			sb.append("'");
			sb.append(String.valueOf(userAccount.getHonorificPrefix()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("honorificSuffix")) {
			sb.append("'");
			sb.append(String.valueOf(userAccount.getHonorificSuffix()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("id")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("image")) {
			sb.append("'");
			sb.append(String.valueOf(userAccount.getImage()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("jobTitle")) {
			sb.append("'");
			sb.append(String.valueOf(userAccount.getJobTitle()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("keywords")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("name")) {
			sb.append("'");
			sb.append(String.valueOf(userAccount.getName()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("organizationBriefs")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("profileURL")) {
			sb.append("'");
			sb.append(String.valueOf(userAccount.getProfileURL()));
			sb.append("'");

			return sb.toString();
		}

		if (entityFieldName.equals("roleBriefs")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		if (entityFieldName.equals("siteBriefs")) {
			throw new IllegalArgumentException(
				"Invalid entity field " + entityFieldName);
		}

		throw new IllegalArgumentException(
			"Invalid entity field " + entityFieldName);
	}

	protected UserAccount randomUserAccount() {
		return new UserAccount() {
			{
				additionalName = RandomTestUtil.randomString();
				alternateName = RandomTestUtil.randomString();
				birthDate = RandomTestUtil.nextDate();
				dashboardURL = RandomTestUtil.randomString();
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				email = RandomTestUtil.randomString();
				familyName = RandomTestUtil.randomString();
				givenName = RandomTestUtil.randomString();
				honorificPrefix = RandomTestUtil.randomString();
				honorificSuffix = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				image = RandomTestUtil.randomString();
				jobTitle = RandomTestUtil.randomString();
				name = RandomTestUtil.randomString();
				profileURL = RandomTestUtil.randomString();
			}
		};
	}

	protected UserAccount randomIrrelevantUserAccount() {
		UserAccount randomIrrelevantUserAccount = randomUserAccount();

		return randomIrrelevantUserAccount;
	}

	protected UserAccount randomPatchUserAccount() {
		return randomUserAccount();
	}

	protected static final ObjectMapper inputObjectMapper = new ObjectMapper() {
		{
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	protected static final ObjectMapper outputObjectMapper =
		new ObjectMapper() {
			{
				addMixIn(UserAccount.class, UserAccountMixin.class);
				setFilterProvider(
					new SimpleFilterProvider() {
						{
							addFilter(
								"Liferay.Vulcan",
								SimpleBeanPropertyFilter.serializeAll());
						}
					});
			}
		};

	protected Group irrelevantGroup;
	protected String testContentType = "application/json";
	protected Group testGroup;
	protected Locale testLocale;
	protected String testUserNameAndPassword = "test@liferay.com:test";

	protected static class UserAccountMixin {

		@JsonProperty
		String additionalName;

		@JsonProperty
		String alternateName;

		@JsonProperty
		Date birthDate;

		@JsonProperty
		ContactInformation contactInformation;

		@JsonProperty
		String dashboardURL;

		@JsonProperty
		Date dateCreated;

		@JsonProperty
		Date dateModified;

		@JsonProperty
		String email;

		@JsonProperty
		String familyName;

		@JsonProperty
		String givenName;

		@JsonProperty
		String honorificPrefix;

		@JsonProperty
		String honorificSuffix;

		@JsonProperty
		Long id;

		@JsonProperty
		String image;

		@JsonProperty
		String jobTitle;

		@JsonProperty
		String[] keywords;

		@JsonProperty
		String name;

		@JsonProperty
		OrganizationBrief[] organizationBriefs;

		@JsonProperty
		String profileURL;

		@JsonProperty
		RoleBrief[] roleBriefs;

		@JsonProperty
		SiteBrief[] siteBriefs;

	}

	protected static class Page<T> {

		public Collection<T> getItems() {
			return new ArrayList<>(items);
		}

		public long getLastPage() {
			return lastPage;
		}

		public long getPage() {
			return page;
		}

		public long getPageSize() {
			return pageSize;
		}

		public long getTotalCount() {
			return totalCount;
		}

		@JsonProperty
		protected Collection<T> items;

		@JsonProperty
		protected long lastPage;

		@JsonProperty
		protected long page;

		@JsonProperty
		protected long pageSize;

		@JsonProperty
		protected long totalCount;

	}

	private Http.Options _createHttpOptions() {
		Http.Options options = new Http.Options();

		options.addHeader("Accept", "application/json");
		options.addHeader(
			"Accept-Language", LocaleUtil.toW3cLanguageId(testLocale));

		String encodedTestUserNameAndPassword = Base64.encode(
			testUserNameAndPassword.getBytes());

		options.addHeader(
			"Authorization", "Basic " + encodedTestUserNameAndPassword);

		options.addHeader("Content-Type", testContentType);

		return options;
	}

	private String _toPath(String template, Object... values) {
		if (ArrayUtil.isEmpty(values)) {
			return template;
		}

		for (int i = 0; i < values.length; i++) {
			template = template.replaceFirst(
				"\\{.*?\\}", String.valueOf(values[i]));
		}

		return template;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUserAccountResourceTestCase.class);

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
	private UserAccountResource _userAccountResource;

	private URL _resourceURL;

}