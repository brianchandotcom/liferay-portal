/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sites.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Raymond Augé
 * @author Ryan Park
 * @author Zsolt Berentey
 */
public class SitesUtil {

	public static void applyLayoutPrototype(
			LayoutPrototype layoutPrototype, Layout targetLayout,
			boolean linkEnabled)
		throws Exception {

		_sites.applyLayoutPrototype(layoutPrototype, targetLayout, linkEnabled);
	}

	public static Sites getSites() {
		return _sites;
	}

	public static boolean isLayoutSetMergeable(Group group, LayoutSet layoutSet)
		throws PortalException {

		return _sites.isLayoutSetMergeable(group, layoutSet);
	}

	public static boolean isUserGroupLayoutSetViewable(
			PermissionChecker permissionChecker, Group userGroupGroup)
		throws PortalException {

		return _sites.isUserGroupLayoutSetViewable(
			permissionChecker, userGroupGroup);
	}

	public static void mergeLayoutPrototypeLayout(Group group, Layout layout)
		throws Exception {

		_sites.mergeLayoutPrototypeLayout(group, layout);
	}

	public static void mergeLayoutSetPrototypeLayouts(
			Group group, LayoutSet layoutSet)
		throws Exception {

		_sites.mergeLayoutSetPrototypeLayouts(group, layoutSet);
	}

	public void setSites(Sites sites) {
		_sites = sites;
	}

	private static Sites _sites;

}