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

package com.liferay.portal.tools.db.partition.virtual.instance.migration.internal.validation;

import com.liferay.portal.tools.db.partition.virtual.instance.migration.internal.Release;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.internal.util.DatabaseUtil;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.internal.version.Version;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Luis Ortiz
 */
public class ValidatorTest {

	@Before
	public void setUp() {
		System.setOut(new PrintStream(_testOutByteArrayOutputStream));
		_databaseMockedStatic = Mockito.mockStatic(DatabaseUtil.class);

		_sourceConnection = Mockito.mock(Connection.class);
		_destinationConnection = Mockito.mock(Connection.class);
	}

	@After
	public void tearDown() {
		System.setOut(_originalOut);
		_databaseMockedStatic.close();
	}

	@Test
	public void testCheckWebIdNotValid() throws SQLException {
		_mockWebIds(false);

		_executeAndAssert(
			false, true,
			Arrays.asList(
				"WARNING: webId " + _TEST_WEB_ID +
					" already exists in destination database"));
	}

	@Test
	public void testCheckWebIdValid() throws SQLException {
		_mockWebIds(true);

		_executeAndAssert(false, false, null);
	}

	@Test
	public void testHigherVersionModule() throws SQLException {
		_mockModuleVersion("module.2.service", "1.0.0");

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"ERROR: Module module.2.service is in a lower version in " +
					"destination database"));
	}

	@Test
	public void testInvalidDestinationReleaseState() throws SQLException {
		List<String> failedServletContextNames = Arrays.asList(
			"module1", "module2");

		_mockFailedServletContextNames(
			new ArrayList<>(), failedServletContextNames);

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"ERROR: Destination Release_ table has the following servlet " +
					"context names with a failed state: module1, module2"));
	}

	@Test
	public void testInvalidSourceReleaseState() throws SQLException {
		List<String> failedServletContextNames = Arrays.asList(
			"module1", "module2");

		_mockFailedServletContextNames(
			failedServletContextNames, new ArrayList<>());

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"ERROR: Source Release_ table has the following servlet " +
					"context names with a failed state: module1, module2"));
	}

	@Test
	public void testLowerVersionModule() throws SQLException {
		_mockModuleVersion("module.1", "10.0.0");

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"ERROR: Module module.1 needs to be upgraded in source " +
					"database before the migration"));
	}

	@Test
	public void testMissingNotServiceModule() throws SQLException {
		_mockMissingModules("module.1");

		_executeAndAssert(
			false, true,
			Arrays.asList(
				"WARNING: Module module.1 will not be available in the " +
					"destination"));
	}

	@Test
	public void testMissingServiceModule() throws SQLException {
		_mockMissingModules("module.2.service");

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"ERROR: Module module.2.service will not be available in the " +
					"destination"));
	}

	@Test
	public void testMissingTablesInBothDatabases() throws SQLException {
		_mockMissingTables(
			new ArrayList<>(
				Arrays.asList("table1", "table2", "table3", "table5")),
			new ArrayList<>(
				Arrays.asList("table1", "table3", "table4", "table5")));

		_executeAndAssert(
			false, true,
			Arrays.asList(
				"WARNING: Table table4 is not present in source database",
				"WARNING: Table table2 is not present in destination " +
					"database"));
	}

	@Test
	public void testMissingTablesInDestination() throws SQLException {
		_mockMissingTables(
			new ArrayList<>(
				Arrays.asList(
					"table1", "table2", "table3", "table4", "table5")),
			new ArrayList<>(Arrays.asList("table1", "table3", "table4")));

		_executeAndAssert(
			false, true,
			Arrays.asList(
				"WARNING: Table table2 is not present in destination database",
				"WARNING: Table table5 is not present in destination " +
					"database"));
	}

	@Test
	public void testMissingTablesInSource() throws SQLException {
		_mockMissingTables(
			new ArrayList<>(Arrays.asList("table1", "table3", "table4")),
			new ArrayList<>(
				Arrays.asList(
					"table1", "table2", "table3", "table4", "table5")));

		_executeAndAssert(
			false, true,
			Arrays.asList(
				"WARNING: Table table2 is not present in source database",
				"WARNING: Table table5 is not present in source database"));
	}

	@Test
	public void testUnverifiedDestinationModule() throws SQLException {
		_mockupUnverifiedModule("module.2", false);

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"ERROR: Module module.2 needs to be verified in the " +
					"destination before the migration"));
	}

	@Test
	public void testUnverifiedSourceModule() throws SQLException {
		_mockupUnverifiedModule("module.2.service", true);

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"ERROR: Module module.2.service needs to be verified in the " +
					"source before the migration"));
	}

	private List<Release> _createReleaseElements() {
		return Arrays.asList(
			new Release(
				"module.1.service", Version.parseVersion("3.5.1"), true),
			new Release(
				"module.2.service", Version.parseVersion("5.0.0"), false),
			new Release("module.1", Version.parseVersion("2.3.2"), true),
			new Release("module.2", Version.parseVersion("5.1.0"), true));
	}

	private void _executeAndAssert(
			boolean hasErrors, boolean hasWarnings, List<String> messages)
		throws SQLException {

		ValidatorRecorder recorder = Validator.validateDatabases(
			_sourceConnection, _destinationConnection);

		Assert.assertEquals(hasErrors, recorder.hasErrors());
		Assert.assertEquals(hasWarnings, recorder.hasWarnings());

		recorder.printMessages();

		String output = _testOutByteArrayOutputStream.toString();

		if (messages == null) {
			Assert.assertTrue(output.isEmpty());
		}
		else {
			for (String message : messages) {
				Assert.assertTrue(output.contains(message));
			}
		}
	}

	private void _mockFailedServletContextNames(
		List<String> sourceFailedServletContextNames,
		List<String> destinationFailedServletContextNames) {

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getFailedServletContextNames(_sourceConnection)
		).thenReturn(
			sourceFailedServletContextNames
		);

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getFailedServletContextNames(
				_destinationConnection)
		).thenReturn(
			destinationFailedServletContextNames
		);
	}

	private void _mockMissingModules(String module) {
		List<Release> releases = _createReleaseElements();

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleaseEntries(_sourceConnection)
		).thenReturn(
			releases
		);

		for (Release release : releases) {
			String servletContextName = release.getServletContextName();

			if (!servletContextName.equals(module)) {
				_databaseMockedStatic.when(
					() -> DatabaseUtil.getReleaseEntry(
						_destinationConnection, servletContextName)
				).thenReturn(
					release
				);
			}
		}
	}

	private void _mockMissingTables(
		List<String> sourceTables, List<String> destinationTables) {

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getTables(_sourceConnection)
		).thenReturn(
			sourceTables
		);

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getTables(_destinationConnection)
		).thenReturn(
			destinationTables
		);
	}

	private void _mockModuleVersion(String module, String version) {
		List<Release> releases = _createReleaseElements();

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleaseEntries(_sourceConnection)
		).thenReturn(
			releases
		);

		for (Release release : releases) {
			String servletContextName = release.getServletContextName();

			if (servletContextName.equals(module)) {
				release = new Release(
					servletContextName, Version.parseVersion(version),
					release.getVerified());
			}

			_databaseMockedStatic.when(
				() -> DatabaseUtil.getReleaseEntry(
					_destinationConnection, servletContextName)
			).thenReturn(
				release
			);
		}
	}

	private void _mockupUnverifiedModule(String module, boolean verified) {
		List<Release> releases = _createReleaseElements();

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleaseEntries(_sourceConnection)
		).thenReturn(
			releases
		);

		for (Release release : releases) {
			String servletContextName = release.getServletContextName();

			if (servletContextName.equals(module)) {
				release = new Release(
					servletContextName, release.getSchemaVersion(), verified);
			}

			_databaseMockedStatic.when(
				() -> DatabaseUtil.getReleaseEntry(
					_destinationConnection, servletContextName)
			).thenReturn(
				release
			);
		}
	}

	private void _mockWebIds(boolean valid) {
		_databaseMockedStatic.when(
			() -> DatabaseUtil.getWebId(_sourceConnection)
		).thenReturn(
			_TEST_WEB_ID
		);

		_databaseMockedStatic.when(
			() -> DatabaseUtil.hasWebId(_destinationConnection, _TEST_WEB_ID)
		).thenReturn(
			!valid
		);
	}

	private static final String _TEST_WEB_ID = "www.able.com";

	private MockedStatic<DatabaseUtil> _databaseMockedStatic;
	private Connection _destinationConnection;
	private final PrintStream _originalOut = System.out;
	private Connection _sourceConnection;
	private final ByteArrayOutputStream _testOutByteArrayOutputStream =
		new ByteArrayOutputStream();

}