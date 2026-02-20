/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.dsr.site.initializer.test.util;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.List;
import java.util.Objects;

import org.junit.Assert;

/**
 * @author Stefano Motta
 */
public class DSRLayoutTestUtil {

	public static void assertLayouts(
			long groupId, String[] names, boolean privateLayout)
		throws Exception {

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout);

		Assert.assertTrue(
			ArrayUtil.containsAll(
				TransformUtil.transformToArray(
					layouts,
					layout -> layout.getName(LocaleUtil.getSiteDefault()),
					String.class),
				names));

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), "Guest");

		for (Layout layout : layouts) {
			boolean hasResourcePermission =
				ResourcePermissionLocalServiceUtil.hasResourcePermission(
					layout.getCompanyId(), Layout.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(layout.getPlid()), role.getRoleId(),
					ActionKeys.VIEW);

			if (Objects.equals(
					layout.getName(LocaleUtil.getSiteDefault()), "Documents") ||
				Objects.equals(
					layout.getName(LocaleUtil.getSiteDefault()),
					"Onboarding")) {

				Assert.assertFalse(hasResourcePermission);
			}
			else {
				Assert.assertTrue(hasResourcePermission);
			}
		}
	}

}