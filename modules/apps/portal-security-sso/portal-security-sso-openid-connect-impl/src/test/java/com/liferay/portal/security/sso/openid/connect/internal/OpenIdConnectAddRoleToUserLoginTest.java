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

package com.liferay.portal.security.sso.openid.connect.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.nimbusds.openid.connect.sdk.claims.UserInfo;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.AdditionalMatchers;
import org.mockito.Mockito;

/**
 * @author Álvaro Saugar
 */
public class OpenIdConnectAddRoleToUserLoginTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_openIdConnectUserInfoProcessorImpl =
			new OpenIdConnectUserInfoProcessorImpl();

		ReflectionTestUtil.setFieldValue(
			_openIdConnectUserInfoProcessorImpl, "_companyLocalService",
			_companyLocalServiceMock);
		ReflectionTestUtil.setFieldValue(
			_openIdConnectUserInfoProcessorImpl, "_props", _props);
		ReflectionTestUtil.setFieldValue(
			_openIdConnectUserInfoProcessorImpl, "_roleLocalService",
			_roleLocalServiceMock);
		ReflectionTestUtil.setFieldValue(
			_openIdConnectUserInfoProcessorImpl, "_userLocalService",
			_userLocalServiceMock);
	}

	@Test
	public void testBothPropertiesDefinedAndCorrectRole() throws Exception {
		String issuer = "issuer url";
		String roleName = _ROLE_NAME_EXISTS;

		Role role = new RoleImpl();
		long roleId = 3333;

		role.setRoleId(roleId);

		role.setName(roleName);
		role.setType(RoleConstants.TYPE_REGULAR);

		long[] roleIds = {role.getRoleId()};

		_setUpEnvironment(role, roleIds, null);

		setUpPropsUtil(issuer, roleName);

		Assert.assertEquals(
			_USEROK_ID,
			_openIdConnectUserInfoProcessorImpl.processUserInfo(
				_userInfo, _COMPANY_ID, issuer, _mainPath, _portalURL));
	}

	@Test
	public void testBothPropertiesDefinedAndFakeRole() throws Exception {
		String issuer = "issuer url";
		String roleName = "Fake Role";

		Role role = null;

		long[] roleIds = null;

		_setUpEnvironment(role, roleIds, null);

		setUpPropsUtil(issuer, roleName);

		Assert.assertEquals(
			_USEROK_ID,
			_openIdConnectUserInfoProcessorImpl.processUserInfo(
				_userInfo, _COMPANY_ID, issuer, _mainPath, _portalURL));
	}

	@Test
	public void testBothPropertiesDefinedAndRoleCorrectAndUserAlreadyExists()
		throws Exception {

		String issuer = "issuer url";
		String roleName = "Power User";

		Role role = new RoleImpl();
		long roleId = 3333;

		role.setRoleId(roleId);

		role.setCompanyId(_COMPANY_ID);
		role.setName(roleName);
		role.setType(RoleConstants.TYPE_REGULAR);

		long[] roleIds = {role.getRoleId()};

		User userExists = new UserImpl();

		userExists.setUserId(_USEROK_ID);
		userExists.setDigest("digest");
		userExists.setScreenName(StringPool.BLANK);
		userExists.setEmailAddress(StringPool.BLANK);

		_setUpEnvironment(role, roleIds, userExists);

		setUpPropsUtil(issuer, roleName);

		Assert.assertEquals(
			_USEROK_ID,
			_openIdConnectUserInfoProcessorImpl.processUserInfo(
				_userInfo, _COMPANY_ID, issuer, _mainPath, _portalURL));
	}

	@Test
	public void testBothPropertiesDefinedAndRoleExistsButDifferentIssuers()
		throws Exception {

		String issuer = "issuer url";
		String roleName = _ROLE_NAME_EXISTS;

		Role role = new RoleImpl();
		long roleId = 3333;

		role.setRoleId(roleId);

		role.setCompanyId(_COMPANY_ID);
		role.setName(roleName);
		role.setType(RoleConstants.TYPE_REGULAR);

		long[] roleIds = null;
		String issuerProvider = "issuer Provicer";

		_setUpEnvironment(role, roleIds, null);
		setUpPropsUtil(issuer, roleName);

		Assert.assertEquals(
			_USEROK_ID,
			_openIdConnectUserInfoProcessorImpl.processUserInfo(
				_userInfo, _COMPANY_ID, issuerProvider, _mainPath, _portalURL));
	}

	@Test
	public void testBothPropertiesDefinedAndRoleNotRegularType()
		throws Exception {

		String issuer = "issuer url";
		String roleName = _ROLE_NAME_EXISTS;

		Role role = new RoleImpl();
		long roleId = 3333;

		role.setRoleId(roleId);

		role.setCompanyId(_COMPANY_ID);
		role.setName(roleName);
		role.setType(RoleConstants.TYPE_SITE);

		long[] roleIds = null;

		_setUpEnvironment(role, roleIds, null);

		setUpPropsUtil(issuer, roleName);

		Assert.assertEquals(
			_USEROK_ID,
			_openIdConnectUserInfoProcessorImpl.processUserInfo(
				_userInfo, _COMPANY_ID, issuer, _mainPath, _portalURL));
	}

	@Test
	public void testBothPropertiesDefinedButRoleNameWrong1() throws Exception {
		String issuer = "issuer url";
		String roleName = "Power User=Publications Owner";

		Role role = null;

		long[] roleIds = null;

		_setUpEnvironment(role, roleIds, null);

		setUpPropsUtil(issuer, roleName);

		Assert.assertEquals(
			_USEROK_ID,
			_openIdConnectUserInfoProcessorImpl.processUserInfo(
				_userInfo, _COMPANY_ID, issuer, _mainPath, _portalURL));
	}

	@Test
	public void testBothPropertiesDefinedButRoleNameWrong2() throws Exception {
		String issuer = "issuer url";
		String roleName = "Power%20User";

		Role role = null;

		long[] roleIds = null;

		_setUpEnvironment(role, roleIds, null);

		setUpPropsUtil(issuer, roleName);

		Assert.assertEquals(
			_USEROK_ID,
			_openIdConnectUserInfoProcessorImpl.processUserInfo(
				_userInfo, _COMPANY_ID, issuer, _mainPath, _portalURL));
	}

	@Test
	public void testIssuerPropertyDefinedSoRoleNotExists() throws Exception {
		String issuer = "issuer url";
		String roleName = null;

		Role role = null;

		long[] roleIds = null;

		_setUpEnvironment(role, roleIds, null);

		setUpPropsUtil(issuer, roleName);

		Assert.assertEquals(
			_USEROK_ID,
			_openIdConnectUserInfoProcessorImpl.processUserInfo(
				_userInfo, _COMPANY_ID, issuer, _mainPath, _portalURL));
	}

	@Test
	public void testRoleNamePropertyDefinedAndRoleExists() throws Exception {
		String issuer = null;
		String roleName = _ROLE_NAME_EXISTS;

		Role role = new RoleImpl();
		long roleId = 3333;

		role.setRoleId(roleId);

		role.setCompanyId(_COMPANY_ID);
		role.setName(roleName);
		role.setType(RoleConstants.TYPE_REGULAR);

		long[] roleIds = null;
		String issuerProvider = "issuer Provicer";

		_setUpEnvironment(role, roleIds, null);
		setUpPropsUtil(issuer, roleName);

		Assert.assertEquals(
			_USEROK_ID,
			_openIdConnectUserInfoProcessorImpl.processUserInfo(
				_userInfo, _COMPANY_ID, issuerProvider, _mainPath, _portalURL));
	}

	protected void setUpPropsUtil(String issuer, String roleName) {
		PropsUtil.setProps(_props);

		Mockito.when(
			_props.get("open.id.connect.user.info.processor.impl.issuer")
		).thenReturn(
			issuer
		);

		Mockito.when(
			_props.get("open.id.connect.user.info.processor.impl.regular.role")
		).thenReturn(
			roleName
		);
	}

	private void _setUpEnvironment(Role role, long[] roleIds, User userExists)
		throws Exception {

		_setUpLocaleUtil();

		String emailAddress = "email@liferay.com";

		_mainPath = "/c";
		_portalURL = "http://localhost:8080";

		Mockito.when(
			_companyLocalServiceMock.getCompany(_COMPANY_ID)
		).thenReturn(
			_companyMock
		);
		Mockito.when(
			_companyMock.isStrangers()
		).thenReturn(
			true
		);
		Mockito.when(
			_companyMock.isStrangersWithMx()
		).thenReturn(
			true
		);
		Mockito.when(
			_userInfo.getEmailAddress()
		).thenReturn(
			emailAddress
		);
		Mockito.when(
			_userInfo.getFamilyName()
		).thenReturn(
			emailAddress
		);
		Mockito.when(
			_userInfo.getGivenName()
		).thenReturn(
			emailAddress
		);
		Mockito.when(
			_userInfo.getMiddleName()
		).thenReturn(
			emailAddress
		);

		User user = new UserImpl();

		user.setUserId(_USEROK_ID);
		user.setDigest("digest");
		user.setScreenName(StringPool.BLANK);
		user.setEmailAddress(emailAddress);
		user.setLanguageId("en");

		User userError = new UserImpl();

		userError.setUserId(_USERERROR_ID);
		userError.setDigest("digest");
		userError.setScreenName(StringPool.BLANK);
		userError.setEmailAddress(emailAddress);
		userError.setLanguageId("en");

		Mockito.when(
			_userLocalServiceMock.fetchUserByEmailAddress(
				_COMPANY_ID, emailAddress)
		).thenReturn(
			userExists
		);
		Mockito.when(
			_companyMock.getLocale()
		).thenReturn(
			LocaleUtil.ENGLISH
		);

		Mockito.when(
			_roleLocalServiceMock.fetchRole(
				Mockito.eq(_COMPANY_ID), Mockito.eq(_ROLE_NAME_EXISTS))
		).thenReturn(
			role
		);

		Mockito.when(
			_roleLocalServiceMock.fetchRole(
				Mockito.eq(_COMPANY_ID),
				AdditionalMatchers.not(Mockito.eq(_ROLE_NAME_EXISTS)))
		).thenReturn(
			null
		);

		Mockito.when(
			_userLocalServiceMock.addUser(
				Mockito.anyLong(), Mockito.eq(_COMPANY_ID),
				Mockito.eq(true), Mockito.eq(null), Mockito.eq(null),
				Mockito.eq(true), Mockito.eq(StringPool.BLANK),
				Mockito.eq(emailAddress), Mockito.any(Locale.class),
				Mockito.eq(emailAddress), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong(),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.anyString(), Mockito.eq(null),
				Mockito.any(), Mockito.eq(roleIds), Mockito.eq(null),
				Mockito.anyBoolean(), Mockito.any(ServiceContext.class))
		).thenReturn(
			user
		);
		Mockito.when(
			_userLocalServiceMock.addUser(
				Mockito.anyLong(), Mockito.eq(_COMPANY_ID),
				Mockito.eq(true), Mockito.eq(null), Mockito.eq(null),
				Mockito.eq(true), Mockito.eq(StringPool.BLANK),
				Mockito.eq(emailAddress), Mockito.any(Locale.class),
				Mockito.eq(emailAddress), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong(),
				Mockito.anyBoolean(), Mockito.anyInt(), Mockito.anyInt(),
				Mockito.anyInt(), Mockito.anyString(), Mockito.eq(null),
				Mockito.any(long[].class),
				AdditionalMatchers.not(Mockito.eq(roleIds)),
				Mockito.any(long[].class), Mockito.anyBoolean(),
				Mockito.any(ServiceContext.class))
		).thenReturn(
			userError
		);

		Mockito.when(
			_userLocalServiceMock.updatePasswordReset(
				Mockito.eq(user.getUserId()), Mockito.eq(false))
		).thenReturn(
			user
		);

		Mockito.when(
			_userLocalServiceMock.updatePasswordReset(
				Mockito.eq(userError.getUserId()), Mockito.eq(false))
		).thenReturn(
			userError
		);
	}

	private void _setUpLocaleUtil() {
		LocaleUtil localeUtil = ReflectionTestUtil.getFieldValue(
			LocaleUtil.class, "_localeUtil");

		Map<String, Locale> locales = ReflectionTestUtil.getFieldValue(
			localeUtil, "_locales");

		locales.clear();

		locales.put("en", LocaleUtil.ENGLISH);
	}

	private static final long _COMPANY_ID = 444444;

	private static final String _ROLE_NAME_EXISTS = "Role exist";

	private static final long _USERERROR_ID = 0;

	private static final long _USEROK_ID = 1;

	private static OpenIdConnectUserInfoProcessorImpl
		_openIdConnectUserInfoProcessorImpl;

	private final CompanyLocalService _companyLocalServiceMock = Mockito.mock(
		CompanyLocalService.class);
	private final Company _companyMock = Mockito.mock(Company.class);
	private String _mainPath;
	private String _portalURL;
	private final Props _props = Mockito.mock(Props.class);
	private final RoleLocalService _roleLocalServiceMock = Mockito.mock(
		RoleLocalService.class);
	private final UserInfo _userInfo = Mockito.mock(UserInfo.class);
	private final UserLocalService _userLocalServiceMock = Mockito.mock(
		UserLocalService.class);

}