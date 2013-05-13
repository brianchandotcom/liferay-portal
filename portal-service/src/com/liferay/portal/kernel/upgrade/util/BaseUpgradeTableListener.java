/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.model.ServiceComponent;

import java.io.IOException;

import java.sql.SQLException;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseUpgradeTableListener implements UpgradeTableListener {

	@Override
	public void onAfterUpdateTable(
			ServiceComponent previousServiceComponent,
			UpgradeTable upgradeTable)
		throws Exception {
	}

	@Override
	public void onBeforeUpdateTable(
			ServiceComponent previousServiceComponent,
			UpgradeTable upgradeTable)
		throws Exception {
	}

	protected void runSQL(String template) throws IOException, SQLException {
		DB db = DBFactoryUtil.getDB();

		db.runSQL(template);
	}

}