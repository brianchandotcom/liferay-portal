/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.NavigationSettings;
import com.liferay.layout.admin.kernel.model.LayoutTypePortletConstants;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Objects;

/**
 * @author Jürgen Kappler
 * @author Javier de Arcos
 */
public class PageSettingsUtil {

	public static NavigationSettings toNavigationSettings(
		UnicodeProperties unicodeProperties) {

		String targetPropertyValue = unicodeProperties.getProperty(
			LayoutTypePortletConstants.TARGET);
		String targetTypePropertyValue = unicodeProperties.getProperty(
			"targetType");

		if ((targetPropertyValue == null) &&
			(targetTypePropertyValue == null)) {

			return null;
		}

		return new NavigationSettings() {
			{
				setTarget(() -> targetPropertyValue);
				setTargetType(
					() -> {
						if (targetTypePropertyValue == null) {
							return null;
						}

						if (Objects.equals(
								targetTypePropertyValue, "useNewTab")) {

							return TargetType.NEW_TAB;
						}

						return TargetType.SPECIFIC_FRAME;
					});
			}
		};
	}

}