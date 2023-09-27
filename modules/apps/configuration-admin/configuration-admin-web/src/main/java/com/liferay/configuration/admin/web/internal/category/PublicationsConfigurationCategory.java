/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.category;

import com.liferay.configuration.admin.category.ConfigurationCategory;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Truong
 */
@Component(service = ConfigurationCategory.class)
public class PublicationsConfigurationCategory
	implements ConfigurationCategory {

	@Override
	public String getBundleSymbolicName() {
		return "com.liferay.change.tracking.web";
	}

	@Override
	public String getCategoryIcon() {
		return "radio-button";
	}

	@Override
	public String getCategoryKey() {
		return "publications";
	}

	@Override
	public String getCategorySection() {
		return "platform";
	}

}