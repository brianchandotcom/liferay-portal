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

import com.liferay.portal.tools.DBLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.Properties;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
public class TestSampleSQLBuilder {

	public static void main(String[] args) {
		try {
			SampleSQLBuilder sampleSQLBuilder = new SampleSQLBuilder(args);

			Properties properties = sampleSQLBuilder.getProperties(args);

			String sqlDir = properties.getProperty("sql.dir");
			String outputDir = properties.getProperty("sample.sql.output.dir");

			String tableFile =
				sqlDir + "/portal-minimal/portal-minimal-hypersonic.sql";
			String indexFile = sqlDir + "/indexes/indexes-hypersonic.sql";
			String sampleFile = outputDir + "/sample-hypersonic.sql";

			loadHypersonic(tableFile, indexFile, sampleFile);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected static void loadHypersonic(
			String tableFile, String indexFile, String sampleFile)
		throws Exception {

		Class.forName("org.hsqldb.jdbcDriver");

		Connection connection = null;
		Statement statement = null;

		try {
			connection = DriverManager.getConnection(
				"jdbc:hsqldb:mem:testSampleSQLBuilderDB;shutdown=true", "sa",
				"");

			DBLoader.loadHypersonic(connection, tableFile);
			DBLoader.loadHypersonic(connection, indexFile);
			DBLoader.loadHypersonic(connection, sampleFile);

			statement = connection.createStatement();

			statement.execute("SHUTDOWN COMPACT");
		}
		finally {
			if (statement != null) {
				statement.close();
			}

			if (connection != null) {
				connection.close();
			}
		}

	}

}