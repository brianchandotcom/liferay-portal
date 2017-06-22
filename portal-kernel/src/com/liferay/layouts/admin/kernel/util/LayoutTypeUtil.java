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

package com.liferay.layouts.admin.kernel.util;

import com.liferay.portal.kernel.model.LayoutTypePortletConstants;

/**
 * @author Jürgen Kappler
 */
public class LayoutTypeUtil {

	public static boolean isDivId(String id) {
		if (id.equals(
				LayoutTypePortletConstants.
					DEFAULT_ASSET_PUBLISHER_PORTLET_ID) ||
			id.equals(LayoutTypePortletConstants.LAYOUT_TEMPLATE_ID) ||
			id.equals(LayoutTypePortletConstants.MODE_ABOUT) ||
			id.equals(LayoutTypePortletConstants.MODE_CONFIG) ||
			id.equals(LayoutTypePortletConstants.MODE_EDIT) ||
			id.equals(LayoutTypePortletConstants.MODE_EDIT_DEFAULTS) ||
			id.equals(LayoutTypePortletConstants.MODE_EDIT_GUEST) ||
			id.equals(LayoutTypePortletConstants.MODE_HELP) ||
			id.equals(LayoutTypePortletConstants.MODE_PREVIEW) ||
			id.equals(LayoutTypePortletConstants.MODE_PRINT) ||
			id.equals(LayoutTypePortletConstants.NESTED_COLUMN_IDS) ||
			id.equals(LayoutTypePortletConstants.STATE_MAX) ||
			id.equals(LayoutTypePortletConstants.STATE_MIN) ||
			id.equals(
				LayoutTypePortletConstants.
					STATIC_PORTLET_ORGANIZATION_SELECTOR) ||
			id.equals(
				LayoutTypePortletConstants.
					STATIC_PORTLET_REGULAR_SITE_SELECTOR) ||
			id.equals(
				LayoutTypePortletConstants.STATIC_PORTLET_USER_SELECTOR)) {

			return false;
		}

		return true;
	}

}