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

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.DBType;

import java.io.IOException;

/**
 * @author Tomas Polesovsky
 */
public class SybaseDB extends BaseDB {

	public SybaseDB(int majorVersion, int minorVersion) {
		super(DBType.SYBASE, majorVersion, minorVersion);
	}

	@Override
	public String buildSQL(String template) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSupportsClob() {
		return _SUPPORTS_CLOB;
	}

	@Override
	protected String buildCreateFileContent(
			String sqlDir, String databaseName, int population)
		throws IOException {

		throw new UnsupportedOperationException();
	}

	@Override
	protected String getServerName() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String[] getTemplate() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String reword(String data) throws IOException {
		throw new UnsupportedOperationException();
	}

	private static final boolean _SUPPORTS_CLOB = false;

}