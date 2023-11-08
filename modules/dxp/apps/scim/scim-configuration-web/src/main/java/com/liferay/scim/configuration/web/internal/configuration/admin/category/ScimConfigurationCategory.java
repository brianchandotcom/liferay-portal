/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.configuration.web.internal.configuration.admin.category;

import com.liferay.configuration.admin.category.ConfigurationCategory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alvaro Saugar
 */
@Component(service = ConfigurationCategory.class)
public class ScimConfigurationCategory implements ConfigurationCategory {

	@Override
	public String getCategoryIcon() {
		return "lock";
	}

	@Override
	public String getCategoryKey() {
		return "scim-name";
	}

	@Override
	public String getCategorySection() {
		return "security";
	}

}