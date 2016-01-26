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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Brian Wing Shun Chan
 */
public class PrincipalThreadLocal {

	public static String getName() {
		String name = _name.get();

		if (_log.isDebugEnabled()) {
			_log.debug("getName " + name);
		}

		return name;
	}

	public static String getPassword() {
		return _password.get();
	}

	public static String getPasswordUnencrypted() {
		return _passwordUnencrypted.get();
	}

	public static long getUserId() {
		return GetterUtil.getLong(getName());
	}

	public static boolean isPasswordModified() {
		return _passwordModified.get();
	}

	public static void setName(long name) {
		setName(String.valueOf(name));
	}

	public static void setName(String name) {
		if (_log.isDebugEnabled()) {
			_log.debug("setName " + name);
		}

		_name.set(name);
	}

	public static void setPassword(String password) {
		_password.set(password);
	}

	public static void setPasswordModified(boolean passwordModified) {
		_passwordModified.set(passwordModified);
	}

	public static void setPasswordUnencrypted(String passwordUnencrypted) {
		_passwordUnencrypted.set(passwordUnencrypted);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PrincipalThreadLocal.class);

	private static final ThreadLocal<String> _name = new AutoResetThreadLocal<>(
		PrincipalThreadLocal.class + "._name");
	private static final ThreadLocal<String> _password =
		new AutoResetThreadLocal<>(PrincipalThreadLocal.class + "._password");
	private static final ThreadLocal<Boolean> _passwordModified =
		new AutoResetThreadLocal<>(
			PrincipalThreadLocal.class + "._passwordModified", false);
	private static final ThreadLocal<String> _passwordUnencrypted =
		new AutoResetThreadLocal<>(
			PrincipalThreadLocal.class + "._passwordUnencrypted",
			StringPool.BLANK);

}