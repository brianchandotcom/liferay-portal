/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static boolean isAppleARM() {
		if (_appleARM != null) {
			return _appleARM.booleanValue();
		}

		String arch = System.getProperty("os.arch");

		arch = arch.toLowerCase();

		if (arch.contains("aarch")) {
			_appleARM = Boolean.TRUE;
		}
		else {
			_appleARM = Boolean.FALSE;
		}

		return _appleARM.booleanValue();
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
	private static Boolean _appleARM;
	private static String _bitMode;
	private static Boolean _windows;

}