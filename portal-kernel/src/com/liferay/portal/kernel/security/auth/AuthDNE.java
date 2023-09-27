/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import java.util.Map;

/**
 * @author Nikoletta Buza
 */
public interface AuthDNE {

	public void onDoesNotExist(
			long companyId, String authType, String login,
			Map<String, String[]> headerMap, Map<String, String[]> parameterMap)
		throws AuthException;

}