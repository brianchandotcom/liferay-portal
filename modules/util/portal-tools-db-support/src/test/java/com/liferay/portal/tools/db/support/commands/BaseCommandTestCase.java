/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.support.commands;

import com.liferay.portal.tools.db.support.util.FileTestUtil;

import java.io.File;

import java.net.URL;

import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Andrea Di Giorgi
 */
@RunWith(Parameterized.class)
public abstract class BaseCommandTestCase {

	@Parameterized.Parameters(name = "{0}")
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
		catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	private final String _mode;

}