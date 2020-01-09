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

package com.liferay.portal.lpkg.deployer.internal;

import com.liferay.petra.lang.CentralizedThreadLocal;

/**
 * @author Matthew Tambara
 */
public class LPKGBatchInstallThreadLocal {

	public static boolean isBatchInstallInProcess() {
		return _batchInstallInProcess.get();
	}

	public static void setBatchInstallInProcess(boolean batchInstallInProcess) {
		_batchInstallInProcess.set(batchInstallInProcess);
	}

	private static final ThreadLocal<Boolean> _batchInstallInProcess =
		new CentralizedThreadLocal<>(
			LPKGBatchInstallThreadLocal.class + "._batchInstallInProcess",
			() -> Boolean.FALSE);

}