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

package com.liferay.site.navigation.taglib.servlet.taglib.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class BreadcrumbEntriesUtil {

	public static List<BreadcrumbEntry> getBreadcrumbEntries(
		HttpServletRequest httpServletRequest, boolean showCurrentGroup,
		boolean showGuestGroup, boolean showLayout, boolean showParentGroups,
		boolean showPortletBreadcrumb) {

		List<BreadcrumbEntry> breadcrumbEntries = Collections.emptyList();

		List<Integer> breadcrumbEntryTypes = new ArrayList<>();

		if (showCurrentGroup) {
			breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_CURRENT_GROUP);
		}

		if (showGuestGroup) {
			breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_GUEST_GROUP);
		}

		if (showLayout) {
			breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_LAYOUT);
		}

		if (showParentGroups) {
			breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_PARENT_GROUP);
		}

		if (showPortletBreadcrumb) {
			breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_PORTLET);
		}

		try {
			breadcrumbEntries = BreadcrumbUtil.getBreadcrumbEntries(
				httpServletRequest, ArrayUtil.toIntArray(breadcrumbEntryTypes));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return breadcrumbEntries;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BreadcrumbEntriesUtil.class);

}