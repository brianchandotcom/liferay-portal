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

import java.io.File;
import java.io.InputStream;

/**
 * @author Michael C. Han
 */
public class AntivirusScannerUtil {

	public static AntivirusScanner getAntivirusScanner() {
		return _antivirusScanner;
	}

	public static boolean isActive() {
		if (_antivirusScanner == null) {
			return false;
		}
		else {
			return _antivirusScanner.isActive();
		}
	}

	public static void scan(byte[] bytes)
		throws AntivirusScannerException, SystemException {

		if (isActive()) {
			_antivirusScanner.scan(bytes);
		}
	}

	public static void scan(File file)
		throws AntivirusScannerException, SystemException {

		if (isActive()) {
			_antivirusScanner.scan(file);
		}
	}

	public static void scan(InputStream inputStream)
		throws AntivirusScannerException, SystemException {

		if (isActive()) {
			_antivirusScanner.scan(inputStream);
		}
	}

	public void setAntivirusScanner(AntivirusScanner antiVirusScanner) {
		_antivirusScanner = antiVirusScanner;
	}

	private static AntivirusScanner _antivirusScanner;

}