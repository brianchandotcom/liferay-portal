/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade.datacleanup;

import com.liferay.portal.kernel.upgrade.UpgradeException;

/**
 * @author Luis Ortiz
 */
public class DataCleanupUpgradeException extends UpgradeException {

	public DataCleanupUpgradeException() {
	}

	public DataCleanupUpgradeException(String msg) {
		super(msg);
	}

	public DataCleanupUpgradeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DataCleanupUpgradeException(Throwable throwable) {
		super(throwable);
	}

}