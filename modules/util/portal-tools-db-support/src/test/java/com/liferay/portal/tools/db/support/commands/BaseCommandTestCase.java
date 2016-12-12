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

package com.liferay.portal.tools.db.support.commands;

import com.liferay.portal.tools.db.support.util.FileTestUtil;

import java.io.File;

import java.net.URL;

import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Andrea Di Giorgi
 */
@RunWith(Parameterized.class)
public abstract class BaseCommandTestCase {

	@Parameters(name = "{0}")
	public static String[] getModes() {
		return new String[] {
			"DB2", "Derby", "HSQLDB", "MSSQLServer", "MySQL", "Oracle",
			"PostgreSQL"
		};
	}

	public BaseCommandTestCase(String mode) {
		_mode = mode;
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	protected String getUrl(String... initSqlFileNames) {
		StringBuilder sb = new StringBuilder();

		sb.append("jdbc:h2:");

		File dbFile = new File(temporaryFolder.getRoot(), "db");

		sb.append(FileTestUtil.getAbsolutePath(dbFile));

		if (initSqlFileNames.length > 0) {
			sb.append(";INIT=");

			for (int i = 0; i < initSqlFileNames.length; i++) {
				if (i > 0) {
					sb.append("\\;");
				}

				sb.append("runscript from '");

				File initSqlFile = new File(
					dependenciesDir, initSqlFileNames[i]);

				sb.append(FileTestUtil.getAbsolutePath(initSqlFile));

				sb.append('\'');
			}
		}

		sb.append(";MODE=");
		sb.append(_mode);

		return sb.toString();
	}

	protected static final File dependenciesDir;

	static {
		try {
			URL url = BaseCommandTestCase.class.getResource("dependencies");

			dependenciesDir = new File(url.toURI());
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private final String _mode;

}