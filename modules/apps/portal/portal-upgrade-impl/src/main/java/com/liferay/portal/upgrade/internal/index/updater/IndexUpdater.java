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
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Ricardo Couso
 */
@Component(immediate = true, service = IndexUpdater.class)
public class IndexUpdater {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	public boolean hasIndexes(Bundle bundle) {
		if (bundle == null) {
			return false;
		}

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		return GetterUtil.getBoolean(headers.get("Liferay-Service"));
	}

	public boolean hasIndexes(long bundleId) {
		return hasIndexes(_getBundle(bundleId));
	}

	public boolean hasIndexes(String bundleSymbolicName) {
		return hasIndexes(_getBundle(bundleSymbolicName));
	}

	public void updateIndexes(Bundle bundle) {
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

	public void updateIndexes(long bundleId) {
		Bundle bundle = _getBundle(bundleId);

		updateIndexes(bundle);
	}

	public void updateIndexes(String bundleSymbolicName) {
		Bundle bundle = _getBundle(bundleSymbolicName);

		updateIndexes(bundle);
	}

	public void updateIndexesAll() {
		DB db = DBManagerUtil.getDB();

		try (Connection connection = DataAccess.getConnection()) {
			for (Bundle bundle : _bundleContext.getBundles()) {
				if (hasIndexes(bundle)) {
					String indexesSQL = _getSQLTemplateString(
						bundle, "indexes.sql");
					String tablesSQL = _getSQLTemplateString(
						bundle, "tables.sql");

					if ((indexesSQL != null) && (tablesSQL != null)) {
						_executeUpdateIndexes(
							bundle, db, connection, tablesSQL, indexesSQL);
					}
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private void _executeUpdateIndexes(
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

	private Bundle _getBundle(long bundleId) {
		Bundle bundle = _bundleContext.getBundle(bundleId);

		if (bundle == null) {
			throw new IllegalArgumentException(
				"Module with id " + bundleId + " does not exist");
		}

		return bundle;
	}

	private Bundle _getBundle(String bundleSymbolicName) {
		for (Bundle bundle : _bundleContext.getBundles()) {
			if (bundleSymbolicName.equals(bundle.getSymbolicName())) {
				return bundle;
			}
		}

		throw new IllegalArgumentException(
			"Module with symbolic name " + bundleSymbolicName +
				" does not exist");
	}

	private String _getSQLTemplateString(Bundle bundle, String templateName) {
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

	private static final Log _log = LogFactoryUtil.getLog(IndexUpdater.class);

	private BundleContext _bundleContext;

}