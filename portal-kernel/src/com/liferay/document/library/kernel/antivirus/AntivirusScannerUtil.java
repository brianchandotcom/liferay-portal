/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.antivirus;

import com.liferay.portal.kernel.module.service.Snapshot;

import java.io.File;

/**
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AntivirusScannerUtil {

	public static boolean isActive() {
		AntivirusScanner antivirusScanner = _antivirusScannerSnapshot.get();

		if (antivirusScanner == null) {
			return false;
		}

		return antivirusScanner.isActive();
	}

	public static void scan(byte[] bytes) throws AntivirusScannerException {
		if (isActive()) {
			AntivirusScanner antivirusScanner = _antivirusScannerSnapshot.get();

			antivirusScanner.scan(bytes);
		}
	}

	public static void scan(File file) throws AntivirusScannerException {
		if (isActive()) {
			AntivirusScanner antivirusScanner = _antivirusScannerSnapshot.get();

			antivirusScanner.scan(file);
		}
	}

	private static final Snapshot<AntivirusScanner> _antivirusScannerSnapshot =
		new Snapshot<>(AntivirusScannerUtil.class, AntivirusScanner.class);

}