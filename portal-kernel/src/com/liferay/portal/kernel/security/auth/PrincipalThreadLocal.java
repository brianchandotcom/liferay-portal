/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Brian Wing Shun Chan
 */
public class PrincipalThreadLocal {

	public static long getCompanyId() {
		return _companyId.get();
	}

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

	public static void setName(long name, boolean resetCTCollectionId) {
		setName(String.valueOf(name), resetCTCollectionId);
	}

	public static void setName(String name) {
		setName(name, true);
	}

	public static void setName(String name, boolean resetCTCollectionId) {
		if (Objects.equals(_name.get(), name)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Skip setName " + name);
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("setName " + name);
		}

		_companyId.set(_getCompanyId(name));
		_name.set(name);

		if (resetCTCollectionId) {
			CTCollectionThreadLocal.removeCTCollectionId();
		}
	}

	public static void setPassword(String password) {
		_password.set(password);
	}

	private static Long _getCompanyId(String name) {
		if ((name == null) || (GetterUtil.getLong(name) == 0L)) {
			return CompanyConstants.SYSTEM;
		}

		Map<String, Long> namesCompanyId = _namesCompanyId.get();

		return namesCompanyId.computeIfAbsent(
			name,
			key -> {
				User user = UserLocalServiceUtil.fetchUser(
					GetterUtil.getLong(key));

				if (user != null) {
					return user.getCompanyId();
				}

				return null;
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PrincipalThreadLocal.class);

	private static final CentralizedThreadLocal<Long> _companyId =
		new CentralizedThreadLocal<>(
			PrincipalThreadLocal.class + "._companyId",
			() -> CompanyConstants.SYSTEM);
	private static final ThreadLocal<String> _name =
		new CentralizedThreadLocal<>(PrincipalThreadLocal.class + "._name");
	private static final CentralizedThreadLocal<Map<String, Long>>
		_namesCompanyId = new CentralizedThreadLocal<>(
			PrincipalThreadLocal.class + "._namesCompanyId",
			() -> new HashMap<>());
	private static final ThreadLocal<String> _password =
		new CentralizedThreadLocal<>(PrincipalThreadLocal.class + "._password");

}