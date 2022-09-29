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

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.impl.RoleImpl;
import com.liferay.portal.security.sso.openid.connect.internal.user.info.handler.spi.UserModelOIDCUserInfoMapper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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
public class OIDCUserInfoProcessorAddRoleToUserLoginTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_oidcUserInfoProcessor = new OIDCUserInfoProcessor();

		ReflectionTestUtil.setFieldValue(
			_oidcUserInfoProcessor, "_userModelOIDCUserInfoMapper",
			_userModelOIDCUserInfoMapperMock);
		ReflectionTestUtil.setFieldValue(
			_oidcUserInfoProcessor, "_props", _props);
		ReflectionTestUtil.setFieldValue(
			_oidcUserInfoProcessor, "_roleLocalService", _roleLocalServiceMock);
	}

	@Test
	public void testBothPropertiesDefinedAndCorrectRole() throws Exception {
		String roleName = _CORRECT_ROLE_NAME;

		_setUpEnvironment(
			roleName, true, RoleConstants.TYPE_REGULAR, true,
			_INCORRECT_USER_ID);

		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, _ISSUER, null, _USER_INFO_JSON,
				_USER_INFO_MAPPER_JSON));
	}

	@Test
	public void testBothPropertiesDefinedAndFakeRole() throws Exception {
		String roleName = "Fake Role";

		_setUpEnvironment(roleName, false, 0, false, _INCORRECT_USER_ID);

		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, _ISSUER, null, _USER_INFO_JSON,
				_USER_INFO_MAPPER_JSON));
	}

	@Test
	public void testBothPropertiesDefinedAndRoleCorrectAndUserAlreadyExists()
		throws Exception {

		String roleName = "Power User";

		_setUpEnvironment(
			roleName, true, RoleConstants.TYPE_REGULAR, true, _CORRECT_USER_ID);

		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, _ISSUER, null, _USER_INFO_JSON,
				_USER_INFO_MAPPER_JSON));
	}

	@Test
	public void testBothPropertiesDefinedAndRoleExistsButDifferentIssuers()
		throws Exception {

		String roleName = _CORRECT_ROLE_NAME;

		String issuerProvider = RandomTestUtil.randomString();

		_setUpEnvironment(
			roleName, true, RoleConstants.TYPE_REGULAR, false,
			_INCORRECT_USER_ID);
		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, issuerProvider, null, _USER_INFO_JSON,
				_USER_INFO_MAPPER_JSON));
	}

	@Test
	public void testBothPropertiesDefinedAndRoleNotRegularType()
		throws Exception {

		String roleName = _CORRECT_ROLE_NAME;

		_setUpEnvironment(
			roleName, true, RoleConstants.TYPE_SITE, false, _INCORRECT_USER_ID);

		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, _ISSUER, null, _USER_INFO_JSON,
				_USER_INFO_MAPPER_JSON));
	}

	@Test
	public void testBothPropertiesDefinedButRoleNameWrong1() throws Exception {
		String roleName = "Power User=Publications Owner";

		_setUpEnvironment(roleName, false, 0, false, _INCORRECT_USER_ID);

		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, _ISSUER, null, _USER_INFO_JSON,
				_USER_INFO_MAPPER_JSON));
	}

	@Test
	public void testBothPropertiesDefinedButRoleNameWrong2() throws Exception {
		String roleName = "Power%20User";

		_setUpEnvironment(roleName, false, 0, false, _INCORRECT_USER_ID);

		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, _ISSUER, null, _USER_INFO_JSON,
				_USER_INFO_MAPPER_JSON));
	}

	@Test
	public void testIssuerPropertyDefinedSoRoleNotExists() throws Exception {
		String roleName = null;

		_setUpEnvironment(roleName, false, 0, false, _INCORRECT_USER_ID);

		setUpPropsUtil(_ISSUER, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, _ISSUER, null, _USER_INFO_JSON,
				_USER_INFO_MAPPER_JSON));
	}

	@Test
	public void testRoleNamePropertyDefinedAndRoleExists() throws Exception {
		String issuer = null;
		String roleName = _CORRECT_ROLE_NAME;

		String issuerProvider = RandomTestUtil.randomString();

		_setUpEnvironment(
			roleName, true, RoleConstants.TYPE_REGULAR, false,
			_INCORRECT_USER_ID);

		setUpPropsUtil(issuer, roleName);

		Assert.assertEquals(
			_CORRECT_USER_ID,
			_oidcUserInfoProcessor.processUserInfo(
				_COMPANY_ID, issuerProvider, null, _USER_INFO_JSON,
				_USER_INFO_MAPPER_JSON));
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

	private void _setUpEnvironment(
			String roleName, Boolean existRole, int roleType,
			Boolean hasRoleIds, long userId)
		throws Exception {

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

		Mockito.when(
			_userModelOIDCUserInfoMapperMock.getUserIdByEmailAddress(
				Mockito.eq(_COMPANY_ID), Mockito.anyString(),
				Mockito.anyString())
		).thenReturn(
			userId
		);

		Mockito.when(
			_userModelOIDCUserInfoMapperMock.generateUser(
				Mockito.eq(_COMPANY_ID), Mockito.eq(roleIds), Mockito.eq(null),
				Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			_CORRECT_USER_ID
		);
		Mockito.when(
			_userModelOIDCUserInfoMapperMock.generateUser(
				Mockito.eq(_COMPANY_ID),
				AdditionalMatchers.not(Mockito.eq(roleIds)), Mockito.eq(null),
				Mockito.anyString(), Mockito.anyString())
		).thenReturn(
			_INCORRECT_USER_ID
		);
	}

	private static final long _COMPANY_ID = RandomTestUtil.randomLong();

	private static final String _CORRECT_ROLE_NAME =
		RandomTestUtil.randomString();

	private static final long _CORRECT_USER_ID = 1;

	private static final long _INCORRECT_USER_ID = 0;

	private static final String _ISSUER = RandomTestUtil.randomString();

	private static final long _ROLE_ID = RandomTestUtil.randomLong();

	private static final String _USER_INFO_JSON = RandomTestUtil.randomString();

	private static final String _USER_INFO_MAPPER_JSON =
		RandomTestUtil.randomString();

	private static OIDCUserInfoProcessor _oidcUserInfoProcessor;

	private final Props _props = Mockito.mock(Props.class);
	private final RoleLocalService _roleLocalServiceMock = Mockito.mock(
		RoleLocalService.class);
	private final UserModelOIDCUserInfoMapper _userModelOIDCUserInfoMapperMock =
		Mockito.mock(UserModelOIDCUserInfoMapper.class);

}