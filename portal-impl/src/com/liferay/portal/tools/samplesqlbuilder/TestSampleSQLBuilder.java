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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.tools.DBLoader;
import com.liferay.portal.util.InitUtil;

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

	public static void main(String[] args) {
		InitUtil.initWithSpring();

		Reader reader = null;

		try {
			Properties properties = new SortedProperties();

			reader = new FileReader(args[0]);

			properties.load(reader);

			DataFactory dataFactory = new DataFactory(properties);

			new SampleSQLBuilder(properties, dataFactory);

			String sqlDir = properties.getProperty("sql.dir");
			String outputDir = properties.getProperty("sample.sql.output.dir");

			loadHypersonic(sqlDir, outputDir);
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

	protected static void loadHypersonic(String sqlDir, String outputDir)
		throws Exception {

		Class.forName("org.hsqldb.jdbcDriver");

		Connection connection = null;
		Statement statement = null;

		try {
			connection = DriverManager.getConnection(
				"jdbc:hsqldb:mem:testSampleSQLBuilderDB;shutdown=true", "sa",
				"");

			DBLoader.loadHypersonic(
				connection, sqlDir + "/portal/portal-hypersonic.sql");
			DBLoader.loadHypersonic(
				connection, sqlDir + "/indexes/indexes-hypersonic.sql");
			DBLoader.loadHypersonic(
				connection, outputDir + "/sample-hypersonic.sql");

			statement = connection.createStatement();

			statement.execute("SHUTDOWN COMPACT");
		}
		finally {
			DataAccess.cleanUp(connection, statement);
		}
	}

}