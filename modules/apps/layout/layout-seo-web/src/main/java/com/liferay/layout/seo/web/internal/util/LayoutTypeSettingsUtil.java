/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.web.internal.util;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

/**
 * @author Alejandro Tardín
 */
public class LayoutTypeSettingsUtil {

	public static Layout updateTypeSettings(
			Layout layout, LayoutService layoutService,
			UnicodeProperties typeSettingsUnicodeProperties)
		throws Exception {

		UnicodeProperties layoutTypeSettingsUnicodeProperties =
			layout.getTypeSettingsProperties();

		String type = layout.getType();

		if (type.equals(LayoutConstants.TYPE_PORTLET)) {
			layoutTypeSettingsUnicodeProperties.putAll(
				typeSettingsUnicodeProperties);

			boolean layoutCustomizable = GetterUtil.getBoolean(
				layoutTypeSettingsUnicodeProperties.get(
					LayoutConstants.CUSTOMIZABLE_LAYOUT));

			if (!layoutCustomizable) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				layoutTypePortlet.removeCustomization(
					layoutTypeSettingsUnicodeProperties);
			}

			return layoutService.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(),
				layoutTypeSettingsUnicodeProperties.toString());
		}

		layoutTypeSettingsUnicodeProperties.putAll(
			typeSettingsUnicodeProperties);

		layoutTypeSettingsUnicodeProperties.putAll(
			layout.getTypeSettingsProperties());

		return layoutService.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layoutTypeSettingsUnicodeProperties.toString());
	}

}