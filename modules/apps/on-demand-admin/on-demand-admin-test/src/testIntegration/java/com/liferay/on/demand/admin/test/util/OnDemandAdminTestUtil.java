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

package com.liferay.on.demand.admin.test.util;

import com.liferay.on.demand.admin.constants.OnDemandAdminConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;

/**
 * @author Pei-Jung Lan
 */
public class OnDemandAdminTestUtil {

	public static User addOnDemandAdminUser(Company company) throws Exception {
		String screenName = _getScreenName();

		User user = UserTestUtil.addUser(
			company.getCompanyId(), TestPropsValues.getUserId(), null,
			screenName + StringPool.AT + company.getMx(), screenName,
			company.getLocale(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null,
			ServiceContextTestUtil.getServiceContext());

		Role role = RoleLocalServiceUtil.getRole(
			company.getCompanyId(), RoleConstants.ADMINISTRATOR);

		UserLocalServiceUtil.addRoleUser(role.getRoleId(), user);

		return user;
	}

	private static String _getScreenName() {
		return StringBundler.concat(
			OnDemandAdminConstants.SCREEN_NAME_PREFIX_ON_DEMAND_ADMIN,
			StringPool.UNDERLINE, RandomTestUtil.randomString());
	}

}