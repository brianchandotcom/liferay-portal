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

package com.liferay.portal.tools.data.partitioning.sql.builder.mysql.exporter;

import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.InsertSQLBuilder;

import java.sql.Date;
import java.sql.Timestamp;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class MySQLInsertSQLBuilderTest {

	@Test
	public void testSerializeTableFieldDate() throws Exception {
		String serializeTableField = _insertSQLBuilder.serializeTableField(
			new Date(0L));

		Assert.assertEquals("'1970-01-01 00:00:00'", serializeTableField);
	}

	@Test
	public void testSerializeTableFieldDouble() throws Exception {
		String serializeTableField = _insertSQLBuilder.serializeTableField(
			Double.valueOf(99.99));

		Assert.assertEquals("'99.99'", serializeTableField);
	}

	@Test
	public void testSerializeTableFieldFloat() throws Exception {
		String serializeTableField = _insertSQLBuilder.serializeTableField(
			Float.valueOf(1));

		Assert.assertEquals("'1.0'", serializeTableField);
	}

	@Test
	public void testSerializeTableFieldInteger() throws Exception {
		String serializeTableField = _insertSQLBuilder.serializeTableField(
			Integer.valueOf(1));

		Assert.assertEquals("'1'", serializeTableField);
	}

	@Test
	public void testSerializeTableFieldString() throws Exception {
		String serializeTableField = _insertSQLBuilder.serializeTableField(
			new String("1"));

		Assert.assertEquals("'1'", serializeTableField);
	}

	@Test
	public void testSerializeTableFieldStringShouldWithQuotes()
		throws Exception {

		String serializeTableField = _insertSQLBuilder.serializeTableField(
			new String("'1'"));

		Assert.assertEquals("'''1'''", serializeTableField);
	}

	@Test
	public void testSerializeTableFieldTimestamp() throws Exception {
		String serializeTableField = _insertSQLBuilder.serializeTableField(
			new Timestamp(0L));

		Assert.assertEquals("'1970-01-01 00:00:00'", serializeTableField);
	}

	private final InsertSQLBuilder _insertSQLBuilder =
		new MySQLInsertSQLBuilder();

}