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

package com.liferay.portal.dao.db;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.util.PropsImpl;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Manuel de la Peña
 */
@PrepareForTest({DBFactoryUtil.class, PropsUtil.class})
@RunWith(PowerMockRunner.class)
public class DBFactoryImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		spy(DBFactoryUtil.class);
		spy(PropsUtil.class);

		when(
			DBFactoryUtil.getDBFactory()
		).thenReturn(
			new DBFactoryImpl()
		);

		when(
			PropsUtil.getProps()
		).thenReturn(
			new PropsImpl()
		);
	}

	@Test
	public void testGetDatabaseNameDb2() throws Exception {
		when(
			PropsUtil.get(PropsKeys.JDBC_DEFAULT_URL)
		).thenReturn(
			"jdbc:db2://localhost:50000/" + _DATABASE_NAME +
				":deferPrepares=false;fullyMaterializeInputStreams=true;" +
				"fullyMaterializeLobData=true;progresssiveLocators=2;" +
				"progressiveStreaming=2;"
		);

		testGetDatabaseName(DB.TYPE_DB2);
	}

	@Test
	public void testGetDatabaseNameDerby() throws Exception {
		when(
			PropsUtil.get(PropsKeys.JDBC_DEFAULT_URL)
		).thenReturn(
			"jdbc:derby:" + _DATABASE_NAME
		);

		testGetDatabaseName(DB.TYPE_DERBY);
	}

	@Test
	public void testGetDatabaseNameHypersonic() throws Exception {
		when(
			PropsUtil.get(PropsKeys.JDBC_DEFAULT_URL)
		).thenReturn(
			"jdbc:hsqldb:${liferay.home}/data/hsql/" + _DATABASE_NAME
		);

		testGetDatabaseName(DB.TYPE_HYPERSONIC);
	}

	@Test
	public void testGetDatabaseNameIngress() throws Exception {
		when(
			PropsUtil.get(PropsKeys.JDBC_DEFAULT_URL)
		).thenReturn(
			"jdbc:ingres://localhost:II7/" + _DATABASE_NAME
		);

		testGetDatabaseName(DB.TYPE_INGRES);
	}

	@Test
	public void testGetDatabaseNameMySQL() throws Exception {
		when(
			PropsUtil.get(PropsKeys.JDBC_DEFAULT_URL)
		).thenReturn(
			"jdbc:mysql://localhost/" + _DATABASE_NAME + "?useUnicode=true&" +
				"characterEncoding=UTF-8&useFastDateParsing=false"
		);

		testGetDatabaseName(DB.TYPE_MYSQL);
	}

	@Test
	public void testGetDatabaseNameOracle() throws Exception {
		when(
			PropsUtil.get(PropsKeys.JDBC_DEFAULT_USERNAME)
		).thenReturn(
			_DATABASE_NAME
		);

		testGetDatabaseName(DB.TYPE_ORACLE);
	}

	@Test
	public void testGetDatabaseNamePostgreSQL() throws Exception {
		when(
			PropsUtil.get(PropsKeys.JDBC_DEFAULT_URL)
		).thenReturn(
			"jdbc:postgresql://localhost:5432/" + _DATABASE_NAME
		);

		testGetDatabaseName(DB.TYPE_POSTGRESQL);
	}

	@Test
	public void testGetDatabaseNameSQLServer() throws Exception {
		when(
			PropsUtil.get(PropsKeys.JDBC_DEFAULT_URL)
		).thenReturn(
			"jdbc:jtds:sqlserver://localhost/" + _DATABASE_NAME
		);

		testGetDatabaseName(DB.TYPE_SQLSERVER);
	}

	@Test
	public void testGetDatabaseNameSybase() throws Exception {
		when(
			PropsUtil.get(PropsKeys.JDBC_DEFAULT_URL)
		).thenReturn(
			"jdbc:jtds:sybase://localhost:5000/" + _DATABASE_NAME
		);

		testGetDatabaseName(DB.TYPE_SYBASE);
	}

	protected void testGetDatabaseName(String type) throws Exception {
		Field field = ReflectionUtil.getDeclaredField(
			DBFactoryImpl.class, "_db");

		field.set(DBFactoryUtil.getDB(), null);

		DBFactoryUtil.setDB(type);

		if (type.equals(DB.TYPE_DB2)) {
			when(
				DBFactoryUtil.getDB()
			).thenReturn(
				new DB2DB()
			);
		}
		else if (type.equals(DB.TYPE_DERBY)) {
			when(
				DBFactoryUtil.getDB()
			).thenReturn(
				new DerbyDB()
			);
		}
		else if (type.equals(DB.TYPE_HYPERSONIC)) {
			when(
				DBFactoryUtil.getDB()
			).thenReturn(
				new HypersonicDB()
			);
		}
		else if (type.equals(DB.TYPE_INGRES)) {
			when(
				DBFactoryUtil.getDB()
			).thenReturn(
				new IngresDB()
			);
		}
		else if (type.equals(DB.TYPE_MYSQL)) {
			when(
				DBFactoryUtil.getDB()
			).thenReturn(
				new MySQLDB()
			);
		}
		else if (type.equals(DB.TYPE_ORACLE)) {
			when(
				DBFactoryUtil.getDB()
			).thenReturn(
				new OracleDB()
			);
		}
		else if (type.equals(DB.TYPE_POSTGRESQL)) {
			when(
				DBFactoryUtil.getDB()
			).thenReturn(
				new PostgreSQLDB()
			);
		}
		else if (type.equals(DB.TYPE_SQLSERVER)) {
			when(
				DBFactoryUtil.getDB()
			).thenReturn(
				new SQLServerDB()
			);
		}
		else if (type.equals(DB.TYPE_SYBASE)) {
			when(
				DBFactoryUtil.getDB()
			).thenReturn(
				new SybaseDB()
			);
		}

		String databaseName = DBFactoryUtil.getDatabaseName();

		verifyStatic();

		Assert.assertEquals(_DATABASE_NAME, databaseName);
	}

	private static final String _DATABASE_NAME = "lportal_test";

}