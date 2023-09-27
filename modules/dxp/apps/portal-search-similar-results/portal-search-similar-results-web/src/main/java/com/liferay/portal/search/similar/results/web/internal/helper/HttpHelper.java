/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.similar.results.web.internal.helper;

/**
 * @author André de Oliveira
 */
public interface HttpHelper {

	public String[] getFriendlyURLParameters(String urlString);

	public String getPortletIdParameter(String urlString, String parameterName);

	public String getPortletIdParameter(
		String urlString, String parameterName, String portletId);

}