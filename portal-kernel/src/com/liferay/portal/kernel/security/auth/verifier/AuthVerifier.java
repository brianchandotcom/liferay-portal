/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth.verifier;

import com.liferay.portal.kernel.security.auth.AccessControlContext;
import com.liferay.portal.kernel.security.auth.AuthException;

import java.util.Properties;

/**
 * @author Tomas Polesovsky
 */
public interface AuthVerifier {

	public String getAuthType();

	public AuthVerifierResult verify(
			AccessControlContext accessControlContext, Properties properties)
		throws AuthException;

}