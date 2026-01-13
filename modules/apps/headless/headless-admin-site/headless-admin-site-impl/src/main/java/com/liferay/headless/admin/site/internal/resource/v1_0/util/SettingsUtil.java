/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.ContentPageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.PageSpecification;
import com.liferay.headless.admin.site.dto.v1_0.Settings;
import com.liferay.headless.admin.site.dto.v1_0.WidgetPageSpecification;

/**
 * @author Mikel Lorza Pascual
 */
public class SettingsUtil {

	public static Settings getSettings(PageSpecification pageSpecification) {
		if (pageSpecification == null) {
			return null;
		}

		if (pageSpecification instanceof ContentPageSpecification) {
			ContentPageSpecification contentPageSpecification =
				(ContentPageSpecification)pageSpecification;

			return contentPageSpecification.getSettings();
		}

		if (pageSpecification instanceof WidgetPageSpecification) {
			WidgetPageSpecification widgetPageSpecification =
				(WidgetPageSpecification)pageSpecification;

			return widgetPageSpecification.getSettings();
		}

		return null;
	}

}