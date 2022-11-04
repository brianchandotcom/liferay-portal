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

	public static String getPortalIndexesSQL() throws IOException {
		return _getPortalResource(
			"/com/liferay/portal/tools/sql/dependencies/indexes.sql");
	}

	public static String getPortalTablesSQL() throws IOException {
		return _getPortalResource(
			"/com/liferay/portal/tools/sql/dependencies/portal-tables.sql");
	}

	private static String _getPortalResource(String name) throws IOException {
		try (InputStream inputStream = DBResourceUtil.class.getResourceAsStream(
				name)) {

			return StringUtil.read(inputStream);
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