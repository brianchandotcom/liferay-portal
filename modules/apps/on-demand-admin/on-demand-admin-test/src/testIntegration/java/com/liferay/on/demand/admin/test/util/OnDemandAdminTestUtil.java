/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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