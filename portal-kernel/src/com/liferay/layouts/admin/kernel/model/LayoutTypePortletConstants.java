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

package com.liferay.layouts.admin.kernel.model;

/**
 * @author Michael Bowerman
 */
public class LayoutTypePortletConstants
	extends com.liferay.portal.kernel.model.LayoutTypePortletConstants {

	public static boolean isDivId(String id) {
		if (id.equals(DEFAULT_ASSET_PUBLISHER_PORTLET_ID) ||
			id.equals(LAYOUT_TEMPLATE_ID) || id.equals(MODE_ABOUT) ||
			id.equals(MODE_CONFIG) || id.equals(MODE_EDIT) ||
			id.equals(MODE_EDIT_DEFAULTS) || id.equals(MODE_EDIT_GUEST) ||
			id.equals(MODE_HELP) || id.equals(MODE_PREVIEW) ||
			id.equals(MODE_PRINT) || id.equals(NESTED_COLUMN_IDS) ||
			id.equals(STATE_MAX) || id.equals(STATE_MIN) ||
			id.equals(STATIC_PORTLET_ORGANIZATION_SELECTOR) ||
			id.equals(STATIC_PORTLET_REGULAR_SITE_SELECTOR) ||
			id.equals(STATIC_PORTLET_USER_SELECTOR)) {

			return false;
		}

		return true;
	}

}