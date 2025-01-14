/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet.configuration.icon;

import com.liferay.petra.string.StringPool;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public interface EditModePortletConfigurationIcon {

	public default String getIcon() {
		return StringPool.BLANK;
	}

	public String getTitle(HttpServletRequest httpServletRequest);

	public String getURL(
		HttpServletRequest httpServletRequest, String portletResource);

	public boolean isShow(
		HttpServletRequest httpServletRequest, String portletResource);

}