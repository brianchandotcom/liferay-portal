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

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Ricardo Couso
 */
public class IndexUpdaterUtil {

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

	public static String getSQLTemplateString(
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

	public static boolean isLiferayServiceBundle(Bundle bundle) {
		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		return GetterUtil.getBoolean(headers.get("Liferay-Service"));
	}

	public static void updateIndexes(Bundle bundle) throws Exception {
		String indexesSQL = getSQLTemplateString(bundle, "indexes.sql");
		String tablesSQL = getSQLTemplateString(bundle, "tables.sql");

		if ((indexesSQL == null) || (tablesSQL == null)) {
			return;
		}

		DB db = DBManagerUtil.getDB();

		String loggingTimerName =
			"Updating database indexes for " + bundle.getSymbolicName();

		try (Connection connection = DataAccess.getConnection();
			LoggingTimer loggingTimer = new LoggingTimer(loggingTimerName)) {

			db.updateIndexes(connection, tablesSQL, indexesSQL, true);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndexUpdaterUtil.class);

}