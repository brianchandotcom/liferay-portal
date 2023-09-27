/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringPool;

/**
 * @author Edward Han
 * @author Vilmos Papp
 */
public class UserImportTransactionThreadLocal {

	public static String getOriginalEmailAddress() {
		return _originalEmailAddress.get();
	}

	public static boolean isOriginatesFromImport() {
		return _originatesFromImport.get();
	}

	public static void setOriginalEmailAddress(String originalEmailAddress) {
		_originalEmailAddress.set(originalEmailAddress);
	}

	public static void setOriginatesFromImport(boolean originatesFromImport) {
		_originatesFromImport.set(originatesFromImport);
	}

	private static final ThreadLocal<String> _originalEmailAddress =
		new CentralizedThreadLocal<>(
			UserImportTransactionThreadLocal.class + "._originalEmailAddress",
			() -> StringPool.BLANK, false);
	private static final ThreadLocal<Boolean> _originatesFromImport =
		new CentralizedThreadLocal<>(
			UserImportTransactionThreadLocal.class + "._originatesFromImport",
			() -> Boolean.FALSE, false);

}