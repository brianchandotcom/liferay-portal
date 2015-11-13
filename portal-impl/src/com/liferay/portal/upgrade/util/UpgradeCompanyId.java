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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class UpgradeCompanyId extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		for (String tableName : getTableNames()) {
			if (tableHasColumn(tableName, "companyId")) {
				if (_log.isInfoEnabled()) {
					_log.info("Skipping table " + tableName);
				}

				continue;
			}

			if (_log.isInfoEnabled()) {
				_log.info("Adding column companyId to table " + tableName);
			}

			runSQL("alter table " + tableName + " add companyId LONG");
		}
	}

	protected abstract String[] getTableNames();

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeCompanyId.class);

}