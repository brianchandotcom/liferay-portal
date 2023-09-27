/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.importer;

import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public interface PortletPreferencesPortletConfigurationImporter {

	public void importPortletConfiguration(
			long plid, String portletId,
			Map<String, Object> portletConfiguration)
		throws Exception;

}