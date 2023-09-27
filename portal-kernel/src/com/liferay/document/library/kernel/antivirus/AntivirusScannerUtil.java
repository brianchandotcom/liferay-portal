/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.antivirus;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.io.File;

/**
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AntivirusScannerUtil {

	public static boolean isActive() {
		return _antivirusScanner.isActive();
	}

	public static void scan(byte[] bytes) throws AntivirusScannerException {
		if (isActive()) {
			_antivirusScanner.scan(bytes);
		}
	}

	public static void scan(File file) throws AntivirusScannerException {
		if (isActive()) {
			_antivirusScanner.scan(file);
		}
	}

	private static volatile AntivirusScanner _antivirusScanner =
		ServiceProxyFactory.newServiceTrackedInstance(
			AntivirusScanner.class, AntivirusScannerUtil.class,
			"_antivirusScanner", false);

}