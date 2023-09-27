/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Objects;

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

	public static long getUserId() {
		return GetterUtil.getLong(getName());
	}

	public static void setName(long name) {
		setName(String.valueOf(name));
	}

	public static void setName(String name) {
		if (Objects.equals(_name.get(), name)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Skip setName " + name);
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("setName " + name);
		}

		_name.set(name);

		CTCollectionThreadLocal.removeCTCollectionId();
	}

	public static void setPassword(String password) {
		_password.set(password);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PrincipalThreadLocal.class);

	private static final ThreadLocal<String> _name =
		new CentralizedThreadLocal<>(PrincipalThreadLocal.class + "._name");
	private static final ThreadLocal<String> _password =
		new CentralizedThreadLocal<>(PrincipalThreadLocal.class + "._password");

}