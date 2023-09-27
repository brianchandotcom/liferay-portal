/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.web.internal.configuration.admin.category;

import com.liferay.configuration.admin.category.ConfigurationCategory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Petteri Karttunen
 */
@Component(enabled = false, service = ConfigurationCategory.class)
public class SXPConfigurationCategory implements ConfigurationCategory {

	@Override
	public String getCategoryIcon() {
		return "search-experiences";
	}

	@Override
	public String getCategoryKey() {
		return "search-experiences";
	}

	@Override
	public String getCategorySection() {
		return "platform";
	}

}