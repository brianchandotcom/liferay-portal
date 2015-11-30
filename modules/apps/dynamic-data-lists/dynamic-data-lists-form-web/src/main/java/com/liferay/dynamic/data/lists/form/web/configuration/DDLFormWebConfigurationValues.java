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

package com.liferay.dynamic.data.lists.form.web.configuration;

import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Rafael Praxedes
 */
public class DDLFormWebConfigurationValues {

	public static final String DEFAULT_DISPLAY_VIEW = GetterUtil.getString(
		DDLFormWebConfigurationUtil.get("default.display.view"));

	public static String[] DISPLAY_VIEWS = DDLFormWebConfigurationUtil.getArray(
		"display.views");

	public static final String EMAIL_FORM_ENTRY_NOTIFICATION_BODY =
		GetterUtil.getString(
			DDLFormWebConfigurationUtil.get(
				"email.form.entry.notification.body"));

}