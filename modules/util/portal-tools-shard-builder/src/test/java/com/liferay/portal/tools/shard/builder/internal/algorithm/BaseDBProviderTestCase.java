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

package com.liferay.portal.tools.shard.builder.internal.algorithm;

import com.liferay.portal.tools.shard.builder.exporter.ShardExporter;
import com.liferay.portal.tools.shard.builder.exporter.ShardExporterFactory;
import com.liferay.portal.tools.shard.builder.internal.DBProvider;
import com.liferay.portal.tools.shard.builder.test.util.DBManagerTestUtil;
import com.liferay.portal.tools.shard.builder.test.util.DBProviderTestUtil;

import java.sql.Date;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public abstract class BaseDBProviderTestCase {

	@Before
	public void setUp() throws Exception {
		dbProperties = DBProviderTestUtil.readProperties(
			getTestPropertiesFileName());

		ShardExporter exporter = ShardExporterFactory.getShardExporter(
			dbProperties);

		dbProvider = (DBProvider)exporter;

		DBManagerTestUtil.execute(dbProvider.getDataSource(), getCreateTable());
	}

	@After
	public void tearDown() throws Exception {
		DBManagerTestUtil.execute(dbProvider.getDataSource(), "drop table foo");
	}

	@Test
	public void testGetDescribeTable() throws Exception {
		boolean ping = DBManagerTestUtil.ping(
			dbProvider.getDataSource(), getDescribeTable("foo"));

		Assert.assertTrue(ping);
	}

	@Test
	public void testGetTableName() {
		Assert.assertEquals("table_name", dbProvider.getTableNameFieldName());
	}

	@Test
	public void testPingReturnsFalse() throws Exception {
		boolean ping = DBManagerTestUtil.ping(
			dbProvider.getDataSource(), "select * from foo");

		Assert.assertFalse(ping);
	}

	@Test
	public void testPingReturnsTrue() throws Exception {
		Calendar calendar = Calendar.getInstance();

		Timestamp expectedTimestamp = new Timestamp(calendar.getTimeInMillis());

		Object[] defaultArgs = getDefaultArgs(0, expectedTimestamp);

		insertIntoFoo(defaultArgs);

		boolean ping = DBManagerTestUtil.ping(
			dbProvider.getDataSource(), "select * from foo");

		Assert.assertTrue(ping);
	}

	@Test
	public void testSerializeDateFieldShouldAddQuotes() throws Exception {
		String serializeTableField = dbProvider.serializeTableField(
			new Date(0L));

		Assert.assertEquals("'1970-01-01 00:01:00'", serializeTableField);
	}

	@Test
	public void testSerializeTimestampFieldShouldAddQuotes() throws Exception {
		String serializeTableField = dbProvider.serializeTableField(
			new Timestamp(0L));

		Assert.assertEquals("'1970-01-01 00:01:00'", serializeTableField);
	}

	protected String getCreateTable() {
		return "create table foo (i INT, f FLOAT, s VARCHAR(75), d DATETIME)";
	}

	protected Object[] getDefaultArgs(
			int expectedInteger, Timestamp expectedTimestamp)
		throws Exception {

		float expectedFloat = 99.99f;
		String expectedString = "expectedString";

		return new Object[] {
			expectedInteger, expectedFloat, expectedString, expectedTimestamp
		};
	}

	protected String getDescribeTable(String tableName) {
		return
			"select * from information_schema.columns where table_name = '" +
				tableName + "'";
	}

	protected abstract String getTestPropertiesFileName();

	protected void insertIntoFoo(Object[] args) throws Exception {
		DBManagerTestUtil.execute(
			dbProvider.getDataSource(), "insert into foo values(?, ?, ?, ?)",
			args);
	}

	protected static Properties dbProperties;
	protected static DBProvider dbProvider;

}