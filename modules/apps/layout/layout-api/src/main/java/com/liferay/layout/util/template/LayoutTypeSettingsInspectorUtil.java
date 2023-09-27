/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.template;

import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class LayoutTypeSettingsInspectorUtil {

	public static List<String> getPortletIds(
		UnicodeProperties typeSettingsUnicodeProperties, String columnId) {

		return StringUtil.split(
			typeSettingsUnicodeProperties.getProperty(columnId));
	}

	public static boolean hasNestedPortletsPortlet(
		UnicodeProperties typeSettingsUnicodeProperties) {

		String nestedColumnIds = typeSettingsUnicodeProperties.getProperty(
			LayoutTypePortletConstants.NESTED_COLUMN_IDS);

		if (Validator.isNotNull(nestedColumnIds)) {
			return true;
		}

		String typeSettingsPropertiesString =
			typeSettingsUnicodeProperties.toString();

		if (typeSettingsPropertiesString.contains(
				PortletKeys.NESTED_PORTLETS)) {

			return true;
		}

		return false;
	}

	public static boolean isCustomizableLayout(
		UnicodeProperties typeSettingsUnicodeProperties) {

		boolean customizableLayout = GetterUtil.getBoolean(
			typeSettingsUnicodeProperties.getProperty(
				LayoutConstants.CUSTOMIZABLE_LAYOUT));

		if (customizableLayout) {
			return true;
		}

		return false;
	}

}