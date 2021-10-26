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

package com.liferay.portal.security.permission;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.contributor.RoleContributor;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionCheckerUtil {

	public static void setThreadValues(User user) {
		if (user == null) {
			PrincipalThreadLocal.setName(null);
			PermissionThreadLocal.setPermissionChecker(null);

			return;
		}

		PrincipalThreadLocal.setName(String.valueOf(user.getUserId()));

		try {
			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (permissionChecker == null) {
				Class<?> clazz = Class.forName(PropsValues.PERMISSIONS_CHECKER);

				permissionChecker = (PermissionChecker)clazz.newInstance();
			}

			List<RoleContributor> roleContributors = new ArrayList<>();

			_roleContributors.forEach(roleContributors::add);

			permissionChecker.init(
				user, roleContributors.toArray(new RoleContributor[0]));

			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PermissionCheckerUtil.class);

	private static final ServiceTrackerList<RoleContributor> _roleContributors =
		ServiceTrackerListFactory.open(
			SystemBundleUtil.getBundleContext(), RoleContributor.class);

}