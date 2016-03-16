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

package com.liferay.portal.tools.data.partitioning.sql.builder.exporter;

import com.liferay.portal.tools.data.partitioning.sql.builder.db.postgresql.PostgreSQLProvider;
import com.liferay.portal.tools.data.partitioning.sql.builder.internal.util.PropsReader;

import java.net.URL;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class ShardExporterFactoryTest {

	@Test
	public void testGetShardExporterReturnsPostgreSQLProvider()
		throws Exception {

		Class<?> clazz = getClass();

		URL url = clazz.getResource("/postgresql.properties");

		Assume.assumeNotNull(url);

		Properties properties = PropsReader.read(url.getPath());

		ShardExporter shardExporter = ShardExporterFactory.getShardExporter(
			properties);

		Class<PostgreSQLProvider> providerClass = PostgreSQLProvider.class;

		Assert.assertTrue(providerClass.isInstance(shardExporter));
	}

}