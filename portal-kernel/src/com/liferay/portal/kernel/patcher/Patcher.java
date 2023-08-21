/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.patcher;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Zsolt Balogh
 * @author Brian Wing Shun Chan
 * @author Zoltán Takács
 */
@ProviderType
public interface Patcher {

	public static final String PATCHER_PROPERTIES = "patcher.properties";

	public static final String PATCHER_SERVICE_PROPERTIES =
		"patcher-service.properties";

	public static final String PROPERTY_INSTALLED_PATCHES = "installed.patches";

	public String[] getInstalledPatches();

}