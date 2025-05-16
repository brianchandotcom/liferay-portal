/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Jorge Avalos
 */
public class PreUpgradeVerifyCollation extends PreUpgradeVerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		_checkCollation();
	}

	private void _checkCollation() throws Exception {
			DB _db = DBManagerUtil.getDB();

			if (!_db.isSupportUnicode(connection)) {
				_log.error("UTF-8 support is not enabled for the database. " +
						   "Please check the database character set and " +
						   "collation configuration.");
				throw new Exception();
			}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PreUpgradeVerifyCollation.class);

}