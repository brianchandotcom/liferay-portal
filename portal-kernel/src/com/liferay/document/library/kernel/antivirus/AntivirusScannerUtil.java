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

package com.liferay.document.library.kernel.antivirus;

import java.io.File;
import java.io.InputStream;

/**
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AntivirusScannerUtil {

	public static AntivirusScanner getAntivirusScanner() {
		return _antivirusScanner;
	}

	public static boolean isActive() {
		AntivirusScanner antivirusScanner = getAntivirusScanner();

		if (antivirusScanner == null) {
			return false;
		}

		return antivirusScanner.isActive();
	}

	public static void scan(byte[] bytes) throws AntivirusScannerException {
		if (isActive()) {
			getAntivirusScanner().scan(bytes);
		}
	}

	public static void scan(File file) throws AntivirusScannerException {
		if (isActive()) {
			getAntivirusScanner().scan(file);
		}
	}

	public static void scan(InputStream inputStream)
		throws AntivirusScannerException {

		if (isActive()) {
			getAntivirusScanner().scan(inputStream);
		}
	}

	public void setAntivirusScanner(AntivirusScanner antivirusScanner) {
		_antivirusScanner = antivirusScanner;
	}

	private static AntivirusScanner _antivirusScanner;

}