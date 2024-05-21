/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.Bundle;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBResourceUtil {

	public static List<String> getMiscellaneousSQLTemplates() {
		return _miscellaneousSQLTemplates;
	}

	public static String getModuleIndexesSQL(Bundle bundle) {
		return _getSQLTemplateString(bundle, "indexes.sql");
	}

	public static String getModuleSequencesSQL(Bundle bundle) {
		return _getSQLTemplateString(bundle, "sequences.sql");
	}

	public static String getModuleTablesSQL(Bundle bundle) {
		return _getSQLTemplateString(bundle, "tables.sql");
	}

	public static String getPortalIndexesSQL() {
		return StringUtil.read(
			DBResourceUtil.class,
			"/com/liferay/portal/tools/sql/dependencies/indexes.sql");
	}

	public static String getPortalTablesSQL() {
		return StringUtil.read(
			DBResourceUtil.class,
			"/com/liferay/portal/tools/sql/dependencies/portal-tables.sql");
	}

	public static void registerMiscellaneousSQLTemplate(String sqlTemplate) {
		_miscellaneousSQLTemplates.add(sqlTemplate);
	}

	private static String _getSQLTemplateString(
		Bundle bundle, String templateName) {

		URL resource = bundle.getResource("/META-INF/sql/" + templateName);

		if (resource == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to locate SQL template " + templateName);
			}

			return null;
		}

		try (InputStream inputStream = resource.openStream()) {
			return StringUtil.read(inputStream);
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to read SQL template " + templateName, ioException);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(DBResourceUtil.class);

	private static final List<String> _miscellaneousSQLTemplates =
		new ArrayList<>();

}