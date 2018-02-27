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