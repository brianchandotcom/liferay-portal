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

package com.liferay.gradle.util;

import java.io.File;

/**
 * @author Brian Wing Shun Chan
 */
public class OSDetector {

	public static String getBitmode() {
		if (_bitMode != null) {
			return _bitMode;
		}

		_bitMode = System.getProperty("sun.arch.data.model");

		if ((_bitMode == null) || _bitMode.equals("null")) {
			_bitMode = System.getProperty("com.ibm.vm.bitmode");
		}

		if ((_bitMode == null) || _bitMode.equals("null")) {
			String arch = System.getProperty("os.arch");

			arch = arch.toLowerCase();

			if (arch.equals("amd64") || arch.equals("x86_64")) {
				_bitMode = "64";
			}
			else if (arch.equals("i386") || arch.equals("i686") ||
					 arch.equals("x86")) {

				_bitMode = "32";
			}
		}

		return _bitMode;
	}

	public static boolean isApple() {
		if (_apple != null) {
			return _apple.booleanValue();
		}

		String osName = System.getProperty("os.name");

		osName = osName.toLowerCase();

		if (osName.contains("darwin") || osName.contains("mac")) {
			_apple = Boolean.TRUE;
		}
		else {
			_apple = Boolean.FALSE;
		}

		return _apple.booleanValue();
	}

	public static boolean isWindows() {
		if (_windows != null) {
			return _windows.booleanValue();
		}

		if (File.pathSeparator.equals(";")) {
			_windows = Boolean.TRUE;
		}
		else {
			_windows = Boolean.FALSE;
		}

		return _windows.booleanValue();
	}

	private static Boolean _apple;
	private static String _bitMode;
	private static Boolean _windows;

}