/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.samplesqlbuilder;

import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.tools.DBLoader;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.Properties;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
public class TestSampleSQLBuilder {

	public static void main(String[] args) throws Exception {
		SampleSQLBuilder.main(args);

		Reader reader = null;

		try {
			reader = new FileReader(args[0]);

			Properties properties = new SortedProperties();

			properties.load(reader);

			new TestSampleSQLBuilder(properties);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}

	public TestSampleSQLBuilder(Properties properties) throws Exception {
		new SampleSQLBuilder(properties);

		String sqlDir = properties.getProperty("sql.dir");
		String outputDir = properties.getProperty("sample.sql.output.dir");

		_loadHypersonic(sqlDir, outputDir);
	}

	private void _loadHypersonic(String sqlDir, String outputDir)
		throws Exception {

		Class.forName("org.hsqldb.jdbcDriver");

		Connection con = DriverManager.getConnection(
			"jdbc:hsqldb:mem:testSampleSQLBuilderDB;shutdown=true", "sa", "");

		DBLoader.loadHypersonic(
			con, sqlDir + "/portal-minimal/portal-minimal-hypersonic.sql");
		DBLoader.loadHypersonic(
			con, sqlDir + "/indexes/indexes-hypersonic.sql");
		DBLoader.loadHypersonic(con, outputDir + "/sample-hypersonic.sql");

		Statement statement = con.createStatement();

		statement.execute("SHUTDOWN COMPACT");

		statement.close();

		con.close();
	}

}