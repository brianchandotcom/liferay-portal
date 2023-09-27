/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.google.merchant.internal.configuration.category;

import com.liferay.configuration.admin.category.ConfigurationCategory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eric Chin
 */
@Component(service = ConfigurationCategory.class)
public class GoogleMerchantConfigurationCategory
	implements ConfigurationCategory {

	@Override
	public String getCategoryKey() {
		return "google-merchant";
	}

	@Override
	public String getCategorySection() {
		return "commerce";
	}

}