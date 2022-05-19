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

package com.liferay.user.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Christopher Kian
 */
@RunWith(Arquillian.class)
public class UserServiceWhenAddingUserWithDefaultRolesTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testShouldAlwaysReceiveDefaultUserRole() throws Exception {
		_company = CompanyTestUtil.addCompany();

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_company.getUserId(), true);

		portalPreferences.setValue(
			"", PropsKeys.ADMIN_DEFAULT_ROLE_NAMES, StringPool.BLANK);

		PortalPreferencesLocalServiceUtil.updatePreferences(
			_company.getCompanyId(), PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			PortletPreferencesFactoryUtil.toXML(portalPreferences));

		String[] roleNames = PrefsPropsUtil.getStringArray(
			_company.getCompanyId(), PropsKeys.ADMIN_DEFAULT_ROLE_NAMES,
			StringPool.NEW_LINE, PropsValues.ADMIN_DEFAULT_ROLE_NAMES);

		Assert.assertFalse(ArrayUtil.contains(roleNames, RoleConstants.USER));

		_user = UserTestUtil.addUser(_company);

		long[] userRoleIds = _user.getRoleIds();

		Role userRole = RoleLocalServiceUtil.getRole(
			_user.getCompanyId(), RoleConstants.USER);

		Assert.assertTrue(
			ArrayUtil.contains(userRoleIds, userRole.getRoleId()));
	}

	@DeleteAfterTestRun
	private Company _company;

	@DeleteAfterTestRun
	private User _user;

}