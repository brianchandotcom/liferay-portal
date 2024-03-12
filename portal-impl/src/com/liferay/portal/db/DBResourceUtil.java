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

import org.osgi.framework.Bundle;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBResourceUtil {

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
		try {
			return StringUtil.read(
				DBResourceUtil.class,
				"/com/liferay/portal/tools/sql/dependencies/indexes.sql");
		}
		catch (Exception exception) {
			_log.error("Unable to read portal indexes SQL", exception);

			return null;
		}
	}

	public static String getPortalTablesSQL() {
		try {
			return StringUtil.read(
				DBResourceUtil.class,
				"/com/liferay/portal/tools/sql/dependencies/portal-tables.sql");
		}
		catch (Exception exception) {
			_log.error("Unable to read portal tables SQL", exception);

			return null;
		}
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

}