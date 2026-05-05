/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.provider;

import jakarta.portlet.PortletPreferences;
import jakarta.portlet.RenderRequest;

/**
 * @author Amos Fong
 */
public interface LayoutSEOMetaRobotsProvider {

	public String getContent(RenderRequest renderRequest);

	public boolean providesContent(PortletPreferences portletPreferences);

}