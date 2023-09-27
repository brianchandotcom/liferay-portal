/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.memberships.web.internal.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Alejandro Tardín
 */
public class GroupUtil {

	public static String getGroupTypeLabel(Group group, Locale locale) {
		String groupTypeLabel = "site";

		if (group.isDepot()) {
			groupTypeLabel = "asset-library";
		}

		return LanguageUtil.get(_getResourceBundle(locale), groupTypeLabel);
	}

	public static String getGroupTypeLabel(long groupId, Locale locale) {
		return getGroupTypeLabel(
			GroupLocalServiceUtil.fetchGroup(groupId), locale);
	}

	private static ResourceBundle _getResourceBundle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, GroupUtil.class);

		return new AggregateResourceBundle(
			resourceBundle, PortalUtil.getResourceBundle(locale));
	}

}