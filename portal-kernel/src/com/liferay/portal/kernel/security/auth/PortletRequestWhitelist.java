/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import java.util.Set;

/**
 * @author Tomas Polesovsky
 * @author Raymond Augé
 * @author Péter Borkuti
 */
public interface PortletRequestWhitelist {

	public Set<String> getPortletInvocationWhitelist();

	public Set<String> getPortletInvocationWhitelistActions();

	public boolean isPortletInvocationWhitelisted(
		long companyId, String portletId, String strutsAction);

	public Set<String> resetPortletInvocationWhitelist();

	public Set<String> resetPortletInvocationWhitelistActions();

}