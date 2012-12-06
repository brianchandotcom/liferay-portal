/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zsolt Berentey
 */
public class TableRegistryUtil {

	public static List<String> getTableNames() {
		return _allTableNames;
	}

	public static void init() throws SystemException {
		registerPortalTables();
	}

	public static void registerPluginTables(
		String servletContextName, String tablesSQL) {

		List<String> tableNames = _pluginTableNames.get(servletContextName);

		if (tableNames == null) {
			tableNames = new ArrayList<String>();

			_pluginTableNames.put(servletContextName, tableNames);
		}

		if (_log.isInfoEnabled()) {
			_log.info("Registering tables for " + servletContextName);
		}

		registerTables(tablesSQL, tableNames);
	}

	public static void unregisterPluginTables(String servletContextName) {
		List<String> tableNames = _pluginTableNames.remove(servletContextName);

		if (tableNames != null) {
			if (_log.isInfoEnabled()) {
				_log.info("Unregistering tables for " + servletContextName);
			}

			_allTableNames.removeAll(tableNames);
		}
	}

	protected static void registerPortalTables() throws SystemException {
		try {
			String tablesSQL =
				StringUtil.read(
					PortalClassLoaderUtil.getClassLoader(),
					"com/liferay/portal/tools/sql/dependencies/" +
						"portal-tables.sql");

			if (_log.isInfoEnabled()) {
				_log.info("Registering tables for the root context");
			}

			registerTables(tablesSQL, null);
		}
		catch (IOException ie) {
			throw new SystemException(ie);
		}
	}

	protected static void registerTables(
		String tablesSQL, List<String> tableNames) {

		Pattern pattern = Pattern.compile("create table ([^ ]+) \\(");

		Matcher m = pattern.matcher(tablesSQL);

		while (m.find()) {
			String tableName = m.group(1);

			tableName = tableName.toLowerCase();

			if (tableNames != null) {
				tableNames.add(tableName);
			}

			_allTableNames.add(tableName);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(TableRegistryUtil.class);

	private static List<String> _allTableNames = new ArrayList<String>();
	private static Map<String, List<String>> _pluginTableNames =
		new HashMap<String, List<String>>();

}