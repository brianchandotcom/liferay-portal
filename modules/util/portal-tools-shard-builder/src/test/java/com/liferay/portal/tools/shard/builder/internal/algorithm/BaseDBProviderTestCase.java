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

		DBManagerTestUtil.execute(
			dbProvider.getDataSource(), getCreateTableStatement());
	}

	@After
	public void tearDown() throws Exception {
		DBManagerTestUtil.execute(
			dbProvider.getDataSource(), getDropTableStatement());
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

	protected String getCreateTableStatement() {
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

	protected String getDropTableStatement() {
		return "drop table foo";
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