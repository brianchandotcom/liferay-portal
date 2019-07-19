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

package com.liferay.layout.util.template;

import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class LayoutTypeSettingsInspectorUtil {

	public static List<String> getPortletIds(
		UnicodeProperties typeSettingsProperties, String columnId) {

		return StringUtil.split(typeSettingsProperties.getProperty(columnId));
	}

	public static boolean hasNestedPortletsPortlet(
		UnicodeProperties typeSettingsProperties) {

		String nestedColumnIds = typeSettingsProperties.getProperty(
			LayoutTypePortletConstants.NESTED_COLUMN_IDS);

		if (Validator.isNotNull(nestedColumnIds)) {
			return true;
		}

		return false;
	}

	public static boolean isCustomizableLayout(
		UnicodeProperties typeSettingsProperties) {

		boolean customizableLayout = GetterUtil.getBoolean(
			typeSettingsProperties.getProperty(
				LayoutConstants.CUSTOMIZABLE_LAYOUT));

		if (customizableLayout) {
			return true;
		}

		return false;
	}

}