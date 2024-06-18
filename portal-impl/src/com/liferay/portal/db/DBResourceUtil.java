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
		return _read(bundle, "/META-INF/sql/indexes.sql");
	}

	public static String getModuleSequencesSQL(Bundle bundle) {
		return _read(bundle, "/META-INF/sql/sequences.sql");
	}

	public static String getModuleTablesSQL(Bundle bundle) {
		return _read(bundle, "/META-INF/sql/tables.sql");
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

	private static String _read(Bundle bundle, String path) {
		URL resource = bundle.getResource(path);

		if (resource == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to locate SQL file " + path);
			}

			return null;
		}

		try (InputStream inputStream = resource.openStream()) {
			return StringUtil.read(inputStream);
		}
		catch (IOException ioException) {
			_log.error("Unable to read SQL file " + path, ioException);

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(DBResourceUtil.class);

}