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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.InitialThreadLocal;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Edward Han
 * @author Vilmos Papp
 */
public class LDAPUserTransactionThreadLocal {

	public static String getOriginalEmailAddress() {
		return _originalEmailAddress.get();
	}

	public static boolean isOriginatesFromLDAP() {
		return _originatesFromLDAP.get();
	}

	public static void setOriginalEmailAddress(String originalEmailAddress) {
		_originalEmailAddress.set(originalEmailAddress);
	}

	public static void setOriginatesFromLDAP(boolean originatesFromLDAP) {
		_originatesFromLDAP.set(originatesFromLDAP);
	}

	private static final ThreadLocal<String> _originalEmailAddress =
		new AutoResetThreadLocal<String>(
			LDAPUserTransactionThreadLocal.class + "._originalEmailAddress",
			StringPool.BLANK);
	private static final ThreadLocal<Boolean> _originatesFromLDAP =
		new InitialThreadLocal<Boolean>(
			LDAPUserTransactionThreadLocal.class + "._originatesFromLDAP",
			false);

}