/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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