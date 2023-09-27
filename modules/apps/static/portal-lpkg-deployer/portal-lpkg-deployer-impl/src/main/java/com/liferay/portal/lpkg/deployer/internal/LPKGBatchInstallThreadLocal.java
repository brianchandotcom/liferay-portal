/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lpkg.deployer.internal;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.lang.SafeCloseable;

/**
 * @author Matthew Tambara
 */
public class LPKGBatchInstallThreadLocal {

	public static boolean isBatchInstallInProcess() {
		return _batchInstallInProcess.get();
	}

	public static SafeCloseable setBatchInstallInProcess(
		boolean batchInstallInProcess) {

		return _batchInstallInProcess.setWithSafeCloseable(
			batchInstallInProcess);
	}

	private static final CentralizedThreadLocal<Boolean>
		_batchInstallInProcess = new CentralizedThreadLocal<>(
			LPKGBatchInstallThreadLocal.class + "._batchInstallInProcess",
			() -> Boolean.FALSE);

}