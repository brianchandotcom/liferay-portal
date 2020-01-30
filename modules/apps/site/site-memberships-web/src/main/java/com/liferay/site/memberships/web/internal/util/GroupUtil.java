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

package com.liferay.site.memberships.web.internal.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
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

		if (group.getType() == GroupConstants.TYPE_DEPOT) {
			groupTypeLabel = "repository";
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

		resourceBundle = new AggregateResourceBundle(
			resourceBundle, PortalUtil.getResourceBundle(locale));

		return resourceBundle;
	}

}