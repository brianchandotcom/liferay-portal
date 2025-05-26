/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;

/**
 * @author Jorge Avalos
 */
public class PreupgradeVerifyCharacterSet extends PreupgradeVerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		_checkCharacterSet();
	}

	private void _checkCharacterSet() throws Exception {
		DB db = DBManagerUtil.getDB();

		if (!db.isSupportsCharacterSet(connection)) {
			throw new Exception(
				"Unsupported database character set: " +
					db.getCharacterSet(connection) +
						". Please verify your database configuration.");
		}
	}

}