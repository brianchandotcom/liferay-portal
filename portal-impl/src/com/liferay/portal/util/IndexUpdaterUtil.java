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

package com.liferay.portal.util;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.Validator;

import java.sql.Connection;

import org.osgi.framework.Bundle;

/**
 * @author Ricardo Couso
 */
public class IndexUpdaterUtil {

	public static void updateIndexes(Bundle bundle) throws Exception {
		String indexesSQL = BundleUtil.getSQLTemplateString(
			bundle, "indexes.sql");
		String tablesSQL = BundleUtil.getSQLTemplateString(
			bundle, "tables.sql");

		if ((indexesSQL == null) || (tablesSQL == null)) {
			return;
		}

		DB db = DBManagerUtil.getDB();

		db.process(
			companyId -> {
				String message = new String(
					"Updating database indexes for " +
						bundle.getSymbolicName());

				if (Validator.isNotNull(companyId) && _log.isInfoEnabled()) {
					message += " and company " + companyId;
				}

				try (Connection connection = DataAccess.getConnection();
					LoggingTimer loggingTimer = new LoggingTimer(message)) {

					db.updateIndexes(connection, tablesSQL, indexesSQL, true);
				}
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexUpdaterUtil.class);

}