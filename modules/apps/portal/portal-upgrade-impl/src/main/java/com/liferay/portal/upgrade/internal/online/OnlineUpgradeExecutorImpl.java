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

package com.liferay.portal.upgrade.internal.online;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.upgrade.online.OnlineUpgradeExecutor;
import com.liferay.portal.upgrade.online.OnlineUpgradeStep;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;

/**
 * @author Kevin Lee
 */
@Component(service = OnlineUpgradeExecutor.class)
public class OnlineUpgradeExecutorImpl implements OnlineUpgradeExecutor {

	@Override
	public void upgrade(
			String tableName, OnlineUpgradeStep... onlineUpgradeSteps)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			String tempTableName = _getTempTableName(tableName);

			DB db = DBManagerUtil.getDB();

			db.copyTableStructure(connection, tableName, tempTableName);

			for (OnlineUpgradeStep onlineUpgradeStep : onlineUpgradeSteps) {
				onlineUpgradeStep.upgrade(tempTableName);
			}

			db.copyTableRows(connection, tableName, tempTableName);
		}
	}

	private String _getTempTableName(String tableName) {
		return _UPGRADE_ONLINE_TABLE_NAME_PREFIX.concat(tableName);
	}

	private static final String _UPGRADE_ONLINE_TABLE_NAME_PREFIX =
		GetterUtil.get(
			PropsUtil.get("upgrade.online.table.name.prefix"), "tmp_");

}