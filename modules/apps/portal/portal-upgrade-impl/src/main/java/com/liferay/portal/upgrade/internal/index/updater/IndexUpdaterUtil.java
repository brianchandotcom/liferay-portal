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

package com.liferay.portal.upgrade.internal.index.updater;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Ricardo Couso
 */
public class IndexUpdaterUtil {

	public static Bundle getBundle(BundleContext bundleContext, long bundleId) {
		Bundle bundle = bundleContext.getBundle(bundleId);

		if (bundle == null) {
			throw new IllegalArgumentException(
				"Module with id " + bundleId + " does not exist");
		}

		return bundle;
	}

	public static Bundle getBundle(
		BundleContext bundleContext, String bundleSymbolicName) {

		for (Bundle bundle : bundleContext.getBundles()) {
			if (bundleSymbolicName.equals(bundle.getSymbolicName())) {
				return bundle;
			}
		}

		throw new IllegalArgumentException(
			"Module with symbolic name " + bundleSymbolicName +
				" does not exist");
	}

	public static boolean isLiferayServiceBundle(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		return GetterUtil.getBoolean(headers.get("Liferay-Service"));
	}

	public static void updateIndexes(Bundle bundle) {
		String indexesSQL = _getSQLTemplateString(bundle, "indexes.sql");
		String tablesSQL = _getSQLTemplateString(bundle, "tables.sql");

		if ((indexesSQL == null) || (tablesSQL == null)) {
			return;
		}

		DB db = DBManagerUtil.getDB();

		try (Connection connection = DataAccess.getConnection()) {
			_executeUpdateIndexes(
				bundle, db, connection, tablesSQL, indexesSQL);
		}
		catch (SQLException sqle) {
			_log.error(sqle, sqle);
		}
	}

	public static void updateIndexesAll(BundleContext bundleContext) {
		DB db = DBManagerUtil.getDB();

		try (Connection connection = DataAccess.getConnection()) {
			for (Bundle bundle : bundleContext.getBundles()) {
				if (!isLiferayServiceBundle(bundle)) {
					continue;
				}

				String indexesSQL = _getSQLTemplateString(
					bundle, "indexes.sql");

				if (indexesSQL == null) {
					continue;
				}

				String tablesSQL = _getSQLTemplateString(bundle, "tables.sql");

				if (tablesSQL == null) {
					continue;
				}

				_executeUpdateIndexes(
					bundle, db, connection, tablesSQL, indexesSQL);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static void _executeUpdateIndexes(
		Bundle bundle, DB db, Connection connection, String tablesSQL,
		String indexesSQL) {

		String loggingTimerName =
			"update of database indexes for " + bundle.getSymbolicName();

		try (LoggingTimer loggingTimer = new LoggingTimer(loggingTimerName)) {
			db.updateIndexes(connection, tablesSQL, indexesSQL, true);
		}
		catch (IOException | SQLException e) {
			_log.error(e, e);
		}
	}

	private static String _getSQLTemplateString(
		Bundle bundle, String templateName) {

		URL resource = bundle.getResource("/META-INF/sql/" + templateName);

		if (resource == null) {
			return null;
		}

		try (InputStream inputStream = resource.openStream()) {
			return StringUtil.read(inputStream);
		}
		catch (IOException ioe) {
			_log.error("Unable to read SQL template " + templateName, ioe);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexUpdaterUtil.class);

}