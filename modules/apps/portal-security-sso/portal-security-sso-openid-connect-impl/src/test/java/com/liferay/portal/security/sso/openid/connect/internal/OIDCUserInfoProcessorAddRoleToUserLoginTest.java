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
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.language.LanguageImpl;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.AdditionalMatchers;
import org.mockito.Mockito;

/**
 * @author Álvaro Saugar
 */
public class OIDCUserInfoProcessorAddRoleToUserLoginTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		_oidcUserInfoProcessor = new OIDCUserInfoProcessor();

		ReflectionTestUtil.setFieldValue(
			_oidcUserInfoProcessor, "_props", _props);
		ReflectionTestUtil.setFieldValue(
			_oidcUserInfoProcessor, "_roleLocalService", _roleLocalServiceMock);

		ReflectionTestUtil.setFieldValue(
			_oidcUserInfoProcessor, "_jsonFactory", _jsonFactoryMock);

		ReflectionTestUtil.setFieldValue(
			_oidcUserInfoProcessor, "_userLocalService", _userLocalServiceMock);

		ReflectionTestUtil.setFieldValue(
			_oidcUserInfoProcessor, "_companyLocalService",
			_companyLocalServiceMock);
	}

	@Test
	public void testBothPropertiesDefinedAndCorrectRole() throws Exception {
		String roleName = _CORRECT_ROLE_NAME;

		_setUpEnvironment(roleName, true, RoleConstants.TYPE_REGULAR, true);

		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, _ISSUER, null, _userInfoJSON,
				_userInfoMapperJSON));
	}

	@Test
	public void testBothPropertiesDefinedAndFakeRole() throws Exception {
		String roleName = "Fake Role";

		_setUpEnvironment(roleName, false, 0, false);

		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, _ISSUER, null, _userInfoJSON,
				_userInfoMapperJSON));
	}

	@Test
	public void testBothPropertiesDefinedAndRoleCorrectAndUserAlreadyExists()
		throws Exception {

		String roleName = "Power User";

		_setUpEnvironment(roleName, true, RoleConstants.TYPE_REGULAR, true);

		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, _ISSUER, null, _userInfoJSON,
				_userInfoMapperJSON));
	}

	@Test
	public void testBothPropertiesDefinedAndRoleExistsButDifferentIssuers()
		throws Exception {

		String roleName = _CORRECT_ROLE_NAME;

		String issuerProvider = RandomTestUtil.randomString();

		_setUpEnvironment(roleName, true, RoleConstants.TYPE_REGULAR, false);
		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, issuerProvider, null, _userInfoJSON,
				_userInfoMapperJSON));
	}

	@Test
	public void testBothPropertiesDefinedAndRoleNotRegularType()
		throws Exception {

		String roleName = _CORRECT_ROLE_NAME;

		_setUpEnvironment(roleName, true, RoleConstants.TYPE_SITE, false);

		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, _ISSUER, null, _userInfoJSON,
				_userInfoMapperJSON));
	}

	@Test
	public void testBothPropertiesDefinedButRoleNameWrong1() throws Exception {
		String roleName = "Power User=Publications Owner";

		_setUpEnvironment(roleName, false, 0, false);

		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, _ISSUER, null, _userInfoJSON,
				_userInfoMapperJSON));
	}

	@Test
	public void testBothPropertiesDefinedButRoleNameWrong2() throws Exception {
		String roleName = "Power%20User";

		_setUpEnvironment(roleName, false, 0, false);

		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, _ISSUER, null, _userInfoJSON,
				_userInfoMapperJSON));
	}

	@Test
	public void testIssuerPropertyDefinedSoRoleNotExists() throws Exception {
		String roleName = null;

		_setUpEnvironment(roleName, false, 0, false);

		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, _ISSUER, null, _userInfoJSON,
				_userInfoMapperJSON));
	}

	@Test
	public void testRoleNamePropertyDefinedAndRoleExists() throws Exception {
		String issuer = null;
		String roleName = _CORRECT_ROLE_NAME;

		String issuerProvider = RandomTestUtil.randomString();

		_setUpEnvironment(roleName, true, RoleConstants.TYPE_REGULAR, false);

		setUpPropsUtil(issuer, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, issuerProvider, null, _userInfoJSON,
				_userInfoMapperJSON));
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

	private void _jsonBuilding() {
		String randomString = RandomTestUtil.randomString();

		_userInfoJSON = JSONUtil.put(
			"email", _emailUSer
		).put(
			"family_name", randomString
		).put(
			"given_name", randomString
		).put(
			"jobTitle", randomString
		).put(
			"middle_name", randomString
		).put(
			"screen_name", randomString
		).toString();

		JSONObject userJSONObject = JSONUtil.put(
			"emailAddress", "email"
		).put(
			"firstName", "given_name"
		).put(
			"jobTitle", "jobTitle"
		).put(
			"lastName", "family_name"
		).put(
			"middleName", "middle_name"
		).put(
			"screenName", "screen_name"
		);

		JSONObject contactJSONObject = JSONUtil.put("birthdate", "birthdate");
		JSONObject addressJSONObject = JSONUtil.put("street", "street");

		_userInfoMapperJSON = JSONUtil.put(
			"address", addressJSONObject
		).put(
			"contact", contactJSONObject
		).put(
			"user", userJSONObject
		).toString();
	}

	private void _mockLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(new LanguageImpl());
	}

	private void _setUpEnvironment(
			String roleName, Boolean existRole, int roleType,
			Boolean hasRoleIds)
		throws Exception {

		_setUpLocaleUtil();

		String domain = StringUtil.toLowerCase(RandomTestUtil.randomString());

		String mx = domain + ".com";

		_emailUSer =
			StringUtil.toLowerCase(RandomTestUtil.randomString()) +
				StringPool.AT + mx;

		_jsonBuilding();

		Role role = new RoleImpl();

		long[] roleIds = null;

		if (existRole) {
			role.setRoleId(_ROLE_ID);
			role.setCompanyId(_COMPANY_ID);
			role.setName(roleName);
			role.setType(roleType);

			if (hasRoleIds) {
				roleIds = new long[] {role.getRoleId()};
			}
		}

		Mockito.when(
			_roleLocalServiceMock.fetchRole(
				Mockito.eq(_COMPANY_ID),
				AdditionalMatchers.not(Mockito.eq(_CORRECT_ROLE_NAME)))
		).thenReturn(
			null
		);
		Mockito.when(
			_roleLocalServiceMock.fetchRole(
				Mockito.eq(_COMPANY_ID), Mockito.eq(_CORRECT_ROLE_NAME))
		).thenReturn(
			role
		);

		JSONObject userInfoMapperJSONObject = JSONFactoryUtil.createJSONObject(
			_userInfoMapperJSON);

		JSONObject userInfoJSONObject = JSONFactoryUtil.createJSONObject(
			_userInfoJSON);

		Mockito.when(
			_jsonFactoryMock.createJSONObject(_userInfoMapperJSON)
		).thenReturn(
			userInfoMapperJSONObject
		);
		Mockito.when(
			_jsonFactoryMock.createJSONObject(_userInfoJSON)
		).thenReturn(
			userInfoJSONObject
		);

		Mockito.when(
			_companyLocalServiceMock.getCompany(Mockito.any(long.class))
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
			_companyMock.getLocale()
		).thenReturn(
			LocaleUtil.ENGLISH
		);

		User user = new UserImpl();

		user.setUserId(_CORRECT_USER_ID);
		user.setDigest(_DIGEST_USER);
		user.setScreenName(StringPool.BLANK);
		user.setEmailAddress(_emailUSer);
		user.setLanguageId(LocaleUtil.US.getLanguage());

		User errorUser = new UserImpl();

		errorUser.setUserId(_INCORRECT_USER_ID);
		errorUser.setDigest(_DIGEST_USER);
		errorUser.setScreenName(StringPool.BLANK);
		errorUser.setEmailAddress(_emailUSer);
		errorUser.setLanguageId(LocaleUtil.US.getLanguage());

		Mockito.when(
			_userLocalServiceMock.fetchUserByEmailAddress(
				_COMPANY_ID, _emailUSer)
		).thenReturn(
			user
		);

		Mockito.when(
			_userLocalServiceMock.addUser(
				Mockito.any(long.class), Mockito.eq(_COMPANY_ID),
				Mockito.any(boolean.class), Mockito.nullable(String.class),
				Mockito.nullable(String.class), Mockito.any(boolean.class),
				Mockito.anyString(), Mockito.anyString(),
				Mockito.any(Locale.class), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(),
				Mockito.any(long.class), Mockito.any(long.class),
				Mockito.any(boolean.class), Mockito.any(int.class),
				Mockito.any(int.class), Mockito.any(int.class),
				Mockito.anyString(), Mockito.nullable(long[].class),
				Mockito.nullable(long[].class), Mockito.eq(roleIds),
				Mockito.nullable(long[].class), Mockito.any(boolean.class),
				Mockito.nullable(ServiceContext.class))
		).thenReturn(
			user
		);

		Mockito.when(
			_userLocalServiceMock.addUser(
				Mockito.any(long.class), Mockito.eq(_COMPANY_ID),
				Mockito.any(boolean.class), Mockito.nullable(String.class),
				Mockito.nullable(String.class), Mockito.any(boolean.class),
				Mockito.anyString(), Mockito.anyString(),
				Mockito.any(Locale.class), Mockito.anyString(),
				Mockito.anyString(), Mockito.anyString(),
				Mockito.any(long.class), Mockito.any(long.class),
				Mockito.any(boolean.class), Mockito.any(int.class),
				Mockito.any(int.class), Mockito.any(int.class),
				Mockito.anyString(), Mockito.nullable(long[].class),
				Mockito.nullable(long[].class),
				AdditionalMatchers.not(Mockito.eq(roleIds)),
				Mockito.nullable(long[].class), Mockito.any(boolean.class),
				Mockito.nullable(ServiceContext.class))
		).thenReturn(
			errorUser
		);

		Mockito.when(
			_userLocalServiceMock.updatePasswordReset(
				Mockito.eq(_CORRECT_USER_ID), Mockito.eq(false))
		).thenReturn(
			user
		);
		Mockito.when(
			_userLocalServiceMock.updatePasswordReset(
				Mockito.eq(_INCORRECT_USER_ID), Mockito.eq(false))
		).thenReturn(
			errorUser
		);
	}

	private void _setUpLocaleUtil() {
		LocaleUtil localeUtil = ReflectionTestUtil.getFieldValue(
			LocaleUtil.class, "_localeUtil");

		Map<String, Locale> locales = ReflectionTestUtil.getFieldValue(
			localeUtil, "_locales");

		locales.clear();

		locales.put(LocaleUtil.US.getLanguage(), LocaleUtil.US);

		_mockLanguageUtil();
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _CORRECT_ROLE_NAME =
		RandomTestUtil.randomString();

	private static final long _CORRECT_USER_ID = 1;

	private static final String _DIGEST_USER = RandomTestUtil.randomString();

	private static final long _INCORRECT_USER_ID = 0;

	private static final String _ISSUER = RandomTestUtil.randomString();

	private static final long _ROLE_ID = RandomTestUtil.randomLong();

	private static String _emailUSer;
	private static OIDCUserInfoProcessor _oidcUserInfoProcessor;
	private static String _userInfoJSON;
	private static String _userInfoMapperJSON;

	private final CompanyLocalService _companyLocalServiceMock = Mockito.mock(
		CompanyLocalService.class);
	private final Company _companyMock = Mockito.mock(Company.class);
	private final JSONFactory _jsonFactoryMock = Mockito.mock(
		JSONFactory.class);
	private final Props _props = Mockito.mock(Props.class);
	private final RoleLocalService _roleLocalServiceMock = Mockito.mock(
		RoleLocalService.class);
	private final UserLocalService _userLocalServiceMock = Mockito.mock(
		UserLocalService.class);

}