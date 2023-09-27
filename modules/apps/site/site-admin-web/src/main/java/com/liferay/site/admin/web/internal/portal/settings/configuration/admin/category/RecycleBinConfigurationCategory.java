/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.admin.web.internal.portal.settings.configuration.admin.category;

import com.liferay.configuration.admin.category.ConfigurationCategory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ConfigurationCategory.class)
public class RecycleBinConfigurationCategory implements ConfigurationCategory {

	@Override
	public String getBundleSymbolicName() {
		return "com.liferay.site.admin.web";
	}

	@Override
	public String getCategoryIcon() {
		return "trash";
	}

	@Override
	public String getCategoryKey() {
		return "recycle-bin";
	}

	@Override
	public String getCategorySection() {
		return "content-and-data";
	}

}