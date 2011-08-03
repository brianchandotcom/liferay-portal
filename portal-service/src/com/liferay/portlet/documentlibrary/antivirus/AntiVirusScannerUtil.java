/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.antivirus;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

import java.io.File;
import java.io.InputStream;

/**
 * @author Michael C. Han
 */
public class AntiVirusScannerUtil {

	public static boolean isActive() {
		if (!_enabled || (_antiVirusScanner == null)) {
			return false;
		}

		return true;
	}

	public static void scan(byte[] bytes)
		throws AntiVirusScannerException, SystemException {

		if (isActive()) {
			_antiVirusScanner.scan(bytes);
		}
	}

	public static void scan(InputStream inputStream)
		throws AntiVirusScannerException, SystemException {

		if (isActive()) {
			_antiVirusScanner.scan(inputStream);
		}
	}

	public static void scan(File file)
		throws AntiVirusScannerException, SystemException {

		if (isActive()) {
			_antiVirusScanner.scan(file);
		}
	}

	public void setAntiVirusScanner(AntiVirusScanner antiVirusScanner) {
		_antiVirusScanner = antiVirusScanner;
	}

	private static AntiVirusScanner _antiVirusScanner;
	private static boolean _enabled = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.DL_STORE_ANTIVIRUS_ENABLED), false);

}
