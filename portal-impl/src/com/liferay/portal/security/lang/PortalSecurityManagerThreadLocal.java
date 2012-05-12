/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.lang;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.security.pacl.PACLPolicy;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PortalSecurityManagerThreadLocal {

	public static PACLPolicy getPACLPolicy() {
		return _paclPolicy.get();
	}

	public static boolean isCheckCreateClassLoader() {
		return _checkCreateClassLoader.get();
	}

	public static boolean isCheckGetClassLoader() {
		return _checkGetClassLoader.get();
	}

	public static boolean isCheckReadFile() {
		return _checkReadFile.get();
	}

	public static boolean isCheckReadFileDescriptor() {
		return _checkReadFileDescriptor.get();
	}

	public static boolean isCheckSQL() {
		return _checkSQL.get();
	}

	public static boolean isCheckWriteFileDescriptor() {
		return _checkWriteFileDescriptor.get();
	}

	public static boolean isEnabled() {
		return _enabled.get();
	}

	public static void setCheckCreateClassLoader(
		boolean checkCreateClassLoader) {

		_checkCreateClassLoader.set(checkCreateClassLoader);
	}

	public static void setCheckGetClassLoader(boolean checkGetClassLoader) {
		_checkGetClassLoader.set(checkGetClassLoader);
	}

	public static void setCheckReadFile(boolean checkReadFile) {
		_checkReadFile.set(checkReadFile);
	}

	public static void setCheckReadFileDescriptor(
		boolean checkReadFileDescriptor) {

		_checkReadFileDescriptor.set(checkReadFileDescriptor);
	}

	public static void setCheckSQL(boolean checkSQL) {
		_checkSQL.set(checkSQL);
	}

	public static void setCheckWriteFileDescriptor(
		boolean checkWriteFileDescriptor) {

		_checkWriteFileDescriptor.set(checkWriteFileDescriptor);
	}

	public static void setEnabled(boolean enabled) {
		_enabled.set(enabled);
	}

	public static void setPACLPolicy(PACLPolicy paclPolicy) {
		_paclPolicy.set(paclPolicy);
	}

	private static ThreadLocal<Boolean> _checkCreateClassLoader =
		new AutoResetThreadLocal<Boolean>(
			PortalSecurityManagerThreadLocal.class + "._checkCreateClassLoader",
			true);
	private static ThreadLocal<Boolean> _checkGetClassLoader =
		new AutoResetThreadLocal<Boolean>(
			PortalSecurityManagerThreadLocal.class + "._checkGetClassLoader",
			true);
	private static ThreadLocal<Boolean> _checkReadFile =
		new AutoResetThreadLocal<Boolean>(
			PortalSecurityManagerThreadLocal.class + "._checkReadFile", true);
	private static ThreadLocal<Boolean> _checkReadFileDescriptor =
		new AutoResetThreadLocal<Boolean>(
			PortalSecurityManagerThreadLocal.class +
				"._checkReadFileDescriptor",
			true);
	private static ThreadLocal<Boolean> _checkSQL =
		new AutoResetThreadLocal<Boolean>(
			PortalSecurityManagerThreadLocal.class + "._checkSQL", true);
	private static ThreadLocal<Boolean> _checkWriteFileDescriptor =
		new AutoResetThreadLocal<Boolean>(
			PortalSecurityManagerThreadLocal.class +
				"._checkWriteFileDescriptor",
			true);
	private static ThreadLocal<Boolean> _enabled =
		new AutoResetThreadLocal<Boolean>(
			PortalSecurityManagerThreadLocal.class + "._enabled", true);
	private static ThreadLocal<PACLPolicy> _paclPolicy =
		new AutoResetThreadLocal<PACLPolicy>(
			PortalSecurityManagerThreadLocal.class + "._paclPolicy");

}