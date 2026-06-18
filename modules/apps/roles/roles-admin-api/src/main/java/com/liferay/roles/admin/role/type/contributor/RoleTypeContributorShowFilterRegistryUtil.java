/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.role.type.contributor;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Adolfo Pérez
 */
public class RoleTypeContributorShowFilterRegistryUtil {

	public static boolean isShow(
		PermissionChecker permissionChecker,
		RoleTypeContributor roleTypeContributor) {

		for (RoleTypeContributorShowFilter roleTypeContributorShowFilter :
				_serviceTrackerList) {

			try {
				if (!roleTypeContributorShowFilter.isShow(
						permissionChecker, roleTypeContributor)) {

					return false;
				}
			}
			catch (PortalException portalException) {
				_log.error(portalException);

				return false;
			}
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RoleTypeContributorShowFilterRegistryUtil.class);

	private static final ServiceTrackerList<RoleTypeContributorShowFilter>
		_serviceTrackerList;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			RoleTypeContributorShowFilterRegistryUtil.class);

		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundle.getBundleContext(), RoleTypeContributorShowFilter.class);
	}

}