/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.configuration.category;

import com.liferay.configuration.admin.category.ConfigurationCategory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gianmarco Brunialti Masera
 */
@Component(service = ConfigurationCategory.class)
public class ChannelConfigurationCategory implements ConfigurationCategory {

	@Override
	public String getBundleSymbolicName() {
		return "com.liferay.commerce.service";
	}

	@Override
	public String getCategoryIcon() {
		return "display-content";
	}

	@Override
	public String getCategoryKey() {
		return "channel";
	}

	@Override
	public String getCategorySection() {
		return "commerce";
	}

}