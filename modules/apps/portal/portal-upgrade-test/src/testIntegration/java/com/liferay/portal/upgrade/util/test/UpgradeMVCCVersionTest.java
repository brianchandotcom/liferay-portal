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

package com.liferay.portal.upgrade.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.test.util.DBAssertionUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.upgrade.PortalMVCCVersionUpgradeProcess;

import java.io.InputStream;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class UpgradeMVCCVersionTest extends PortalMVCCVersionUpgradeProcess {

	@Before
	public void setUp() throws Exception {
		connection = DataAccess.getConnection();

		_createTable(_HIBERNATE_MAPPING_TABLE_NAME);
		_createTable(_TABLE_NAME);
	}

	@After
	public void tearDown() throws Exception {
		_dropTable(_HIBERNATE_MAPPING_TABLE_NAME);
		_dropTable(_TABLE_NAME);

		connection.close();
	}

	@Test
	public void testUpgradeModuleMVCCVersionByHibernateMapping()
		throws Exception {

		doUpgrade();

		DBAssertionUtil.assertColumns(
			_HIBERNATE_MAPPING_TABLE_NAME, "id", "userId", "mvccVersion");
	}

	@Test
	public void testUpgradePortalMVCCVersionByTableName() throws Exception {
		_excludedTableNames = new String[] {_HIBERNATE_MAPPING_TABLE_NAME};

		_moduleTableNames = new String[] {_TABLE_NAME};

		doUpgrade();

		DBAssertionUtil.assertColumns(
			_TABLE_NAME, "id", "userId", "mvccversion");
	}

	@Override
	protected List<Element> getClassElements() throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"META-INF/test-portal-hbm.xml");

		Document document = UnsecureSAXReaderUtil.read(inputStream);

		Element rootElement = document.getRootElement();

		return rootElement.elements("class");
	}

	@Override
	protected String[] getExcludedTableNames() {
		return _excludedTableNames;
	}

	@Override
	protected String[] getModuleTableNames() {
		return _moduleTableNames;
	}

	private void _createTable(String tableName) throws Exception {
		runSQL(
			StringBundler.concat(
				"create table ", tableName, "(id LONG not null primary key, ",
				"userId LONG)"));
	}

	private void _dropTable(String tableName) throws Exception {
		runSQL("drop table " + tableName);
	}

	private static final String _HIBERNATE_MAPPING_TABLE_NAME =
		"UpgradeMVCCVersionHBMTest";

	private static final String _TABLE_NAME = "UpgradeMVCCVersionTest";

	private String[] _excludedTableNames = new String[0];
	private String[] _moduleTableNames = new String[0];

}