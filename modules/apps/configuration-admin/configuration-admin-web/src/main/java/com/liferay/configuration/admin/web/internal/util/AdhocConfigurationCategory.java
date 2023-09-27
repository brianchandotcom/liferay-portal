/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.category.ConfigurationCategory;

/**
 * @author Jorge Ferrer
 */
public class AdhocConfigurationCategory implements ConfigurationCategory {

	public AdhocConfigurationCategory(String key) {
		_key = key;
	}

	@Override
	public String getCategoryKey() {
		return _key;
	}

	@Override
	public String getCategorySection() {
		return "other";
	}

	private final String _key;

}