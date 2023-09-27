/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.category;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jorge Ferrer
 */
@ProviderType
public interface ConfigurationCategory {

	public default String getBundleSymbolicName() {
		return "com.liferay.configuration.admin.web";
	}

	public default String getCategoryIcon() {
		return "cog";
	}

	public String getCategoryKey();

	public String getCategorySection();

}