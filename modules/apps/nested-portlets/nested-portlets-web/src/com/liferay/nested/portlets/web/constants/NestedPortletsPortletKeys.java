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

package com.liferay.nested.portlets.web.constants;

import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Eudaldo Alonso
 */
public class NestedPortletsPortletKeys {

	public static final String NESTED_PORTLETS =
		"com_liferay_nested_portlets_web_portlet_NestedPortletsPortlet";

	public static final String TEMPLATE_CONTENT = "TEMPLATE_CONTENT";

	public static final String TEMPLATE_ID = "TEMPLATE_ID";

	public static String getTemplateContentKey(String portletId) {
		return TEMPLATE_CONTENT + StringPool.POUND + portletId;
	}

	public static String getTemplateIdKey(String portletId) {
		return TEMPLATE_ID + StringPool.POUND + portletId;
	}

}