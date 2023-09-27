/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.web.internal.portal.settings.configuration.admin.category;

import com.liferay.configuration.admin.category.ConfigurationCategory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(service = ConfigurationCategory.class)
public class AnalyticsConfigurationCategory implements ConfigurationCategory {

	@Override
	public String getBundleSymbolicName() {
		return "com.liferay.analytics.settings.web";
	}

	@Override
	public String getCategoryIcon() {
		return "liferay-ac";
	}

	@Override
	public String getCategoryKey() {
		return "analytics-cloud";
	}

	@Override
	public String getCategorySection() {
		return "platform";
	}

}