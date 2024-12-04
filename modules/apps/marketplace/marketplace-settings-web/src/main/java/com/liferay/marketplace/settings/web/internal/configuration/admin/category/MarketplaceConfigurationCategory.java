/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.settings.web.internal.configuration.admin.category;

import com.liferay.configuration.admin.category.ConfigurationCategory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo Diniz
 * @author Keven Leone
 */
@Component(service = ConfigurationCategory.class)
public class MarketplaceConfigurationCategory implements ConfigurationCategory {

	@Override
	public String getBundleSymbolicName() {
		return "com.liferay.marketplace.settings.web";
	}

	@Override
	public String getCategoryIcon() {
		return "marketplace";
	}

	@Override
	public String getCategoryKey() {
		return "marketplace";
	}

	@Override
	public String getCategorySection() {
		return "platform";
	}

}