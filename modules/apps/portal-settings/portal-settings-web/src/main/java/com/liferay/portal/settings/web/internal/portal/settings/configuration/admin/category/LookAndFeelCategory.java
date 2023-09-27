/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.settings.web.internal.portal.settings.configuration.admin.category;

import com.liferay.configuration.admin.category.ConfigurationCategory;

import org.osgi.service.component.annotations.Component;

/**
 * @author João Victor Torres
 */
@Component(service = ConfigurationCategory.class)
public class LookAndFeelCategory implements ConfigurationCategory {

	@Override
	public String getCategoryIcon() {
		return "format";
	}

	@Override
	public String getCategoryKey() {
		return "look-and-feel";
	}

	@Override
	public String getCategorySection() {
		return "platform";
	}

}