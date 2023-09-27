/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.layoutconfiguration.util.velocity;

import com.liferay.portal.kernel.portlet.PortletProvider;

import java.util.Map;

/**
 * @author Raymond Augé
 * @author Oliver Teichmann
 */
public interface ColumnProcessor {

	public String processColumn(String columnId) throws Exception;

	public String processColumn(String columnId, String classNames)
		throws Exception;

	public String processDynamicColumn(String columnId, String classNames)
		throws Exception;

	public String processMax() throws Exception;

	public String processPortlet(String portletId) throws Exception;

	public String processPortlet(
			String portletId, Map<String, ?> defaultSettingsMap)
		throws Exception;

	public String processPortlet(
			String portletProviderClassName,
			PortletProvider.Action portletProviderAction)
		throws Exception;

}