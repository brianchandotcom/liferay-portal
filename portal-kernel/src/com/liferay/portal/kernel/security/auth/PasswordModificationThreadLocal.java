/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Michael C. Han
 */
public class PasswordModificationThreadLocal {

	public static String getPasswordUnencrypted() {
		return _passwordUnencrypted.get();
	}

	public static boolean isPasswordModified() {
		return _passwordModified.get();
	}

	public static void setPasswordModified(boolean passwordModified) {
		_passwordModified.set(passwordModified);
	}

	public static void setPasswordUnencrypted(String passwordUnencrypted) {
		_passwordUnencrypted.set(passwordUnencrypted);
	}

	private static final ThreadLocal<Boolean> _passwordModified =
		new CentralizedThreadLocal<>(
			PrincipalThreadLocal.class + "._passwordModified",
			() -> Boolean.FALSE);
	private static final ThreadLocal<String> _passwordUnencrypted =
		new CentralizedThreadLocal<>(
			PrincipalThreadLocal.class + "._passwordUnencrypted");

}