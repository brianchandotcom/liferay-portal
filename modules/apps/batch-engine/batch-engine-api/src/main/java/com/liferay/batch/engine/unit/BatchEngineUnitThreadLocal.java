/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.unit;

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringPool;

/**
 * @author Gabriel Albuquerque
 */
public class BatchEngineUnitThreadLocal {

	public static String getDataFileName() {
		return _batchEngineUnitDataFileName.get();
	}

	public static String getFileName() {
		return _batchEngineUnitFileName.get();
	}

	public static void setDataFileName(String dataFileName) {
		_batchEngineUnitDataFileName.set(dataFileName);
	}

	public static void setFileName(String fileName) {
		_batchEngineUnitFileName.set(fileName);
	}

	private static final ThreadLocal<String> _batchEngineUnitDataFileName =
		new CentralizedThreadLocal<>(
			BatchEngineUnitThreadLocal.class + "._batchEngineUnitDataFileName",
			() -> StringPool.BLANK);
	private static final ThreadLocal<String> _batchEngineUnitFileName =
		new CentralizedThreadLocal<>(
			BatchEngineUnitThreadLocal.class + "._batchEngineUnitFileName",
			() -> StringPool.BLANK);

}