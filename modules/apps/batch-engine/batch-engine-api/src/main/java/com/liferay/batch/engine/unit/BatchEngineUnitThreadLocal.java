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

	public static String getFileName() {
		return _batchEngineUnitThreadLocal.get();
	}

	public static void setFileName(String fileName) {
		_batchEngineUnitThreadLocal.set(fileName);
	}

	private static final ThreadLocal<String> _batchEngineUnitThreadLocal =
		new CentralizedThreadLocal<>(
			BatchEngineUnitThreadLocal.class + "._batchEngineUnitThreadLocal",
			() -> StringPool.BLANK);

}