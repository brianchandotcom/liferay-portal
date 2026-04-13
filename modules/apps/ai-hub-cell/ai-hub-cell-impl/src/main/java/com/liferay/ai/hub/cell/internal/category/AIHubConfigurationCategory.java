/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.cell.internal.category;

import com.liferay.configuration.admin.category.ConfigurationCategory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(service = ConfigurationCategory.class)
public class AIHubConfigurationCategory implements ConfigurationCategory {

	@Override
	public String getCategoryIcon() {
		return "stars";
	}

	@Override
	public String getCategoryKey() {
		return "ai-hub";
	}

	@Override
	public String getCategorySection() {
		return "platform";
	}

}