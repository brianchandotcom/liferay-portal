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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		_mockReleaseSchemaVersion("module2.service", "1.0.0");

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"ERROR: Module module2.service needs to be upgraded in " +
					"destination database before the migration"));
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
		_mockReleaseSchemaVersion("module1", "10.0.0");

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"ERROR: Module module1 needs to be upgraded in source " +
					"database before the migration"));
	}

	@Test
	public void testMissingDestinationNotServiceModule() throws SQLException {
		_mockMissingDestinationModule("module1");

		_executeAndAssert(
			false, true,
			Arrays.asList(
				"WARNING: Module module1 is not present in the source " +
					"database"));
	}

	@Test
	public void testMissingDestinationServiceModule() throws SQLException {
		_mockMissingDestinationModule("module2.service");

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"ERROR: Module module2.service needs to be installed in the " +
					"source database before the migration"));
	}

	@Test
	public void testMissingSourceModule() throws SQLException {
		_mockMissingSourceModule("module1");

		_executeAndAssert(
			false, true,
			Arrays.asList(
				"WARNING: Module module1 is not present in the destination " +
					"database"));
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
		_mockupReleaseVerified("module2", false);

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"ERROR: Module module2 needs to be verified in the " +
					"destination database before the migration"));
	}

	@Test
	public void testUnverifiedSourceModule() throws SQLException {
		_mockupReleaseVerified("module2.service", true);

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"ERROR: Module module2.service needs to be verified in the " +
					"source database before the migration"));
	}

	private List<Release> _createReleaseElements() {
		return Arrays.asList(
			new Release("module1.service", Version.parseVersion("3.5.1"), true),
			new Release(
				"module2.service", Version.parseVersion("5.0.0"), false),
			new Release("module1", Version.parseVersion("2.3.2"), true),
			new Release("module2", Version.parseVersion("5.1.0"), true));
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

	private void _mockMissingDestinationModule(String servletContextName) {
		List<Release> releases = _createReleaseElements();

		Map<String, Release> releaseMap = new HashMap<>();

		List<Release> missingDestinationModuleReleases = new ArrayList<>();

		for (Release release : releases) {
			releaseMap.put(release.getServletContextName(), release);

			if (!servletContextName.equals(release.getServletContextName())) {
				missingDestinationModuleReleases.add(release);
			}
		}

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleases(_sourceConnection)
		).thenReturn(
			missingDestinationModuleReleases
		);

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleaseMap(_destinationConnection)
		).thenReturn(
			releaseMap
		);
	}

	private void _mockMissingSourceModule(String servletContextName) {
		List<Release> releases = _createReleaseElements();

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleases(_sourceConnection)
		).thenReturn(
			releases
		);

		Map<String, Release> releaseMap = new HashMap<>();

		for (Release release : releases) {
			if (!servletContextName.equals(release.getServletContextName())) {
				releaseMap.put(release.getServletContextName(), release);
			}
		}

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleaseMap(_destinationConnection)
		).thenReturn(
			releaseMap
		);
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

	private void _mockReleaseSchemaVersion(
		String servletContextName, String schemaVersion) {

		List<Release> releases = _createReleaseElements();

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleases(_sourceConnection)
		).thenReturn(
			releases
		);

		Map<String, Release> releaseMap = new HashMap<>();

		for (Release release : releases) {
			if (servletContextName.equals(release.getServletContextName())) {
				release = new Release(
					servletContextName, Version.parseVersion(schemaVersion),
					release.getVerified());
			}

			releaseMap.put(release.getServletContextName(), release);
		}

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleaseMap(_destinationConnection)
		).thenReturn(
			releaseMap
		);
	}

	private void _mockupReleaseVerified(
		String servletContextName, boolean verified) {

		List<Release> releases = _createReleaseElements();

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleases(_sourceConnection)
		).thenReturn(
			releases
		);

		Map<String, Release> releaseMap = new HashMap<>();

		for (Release release : releases) {
			if (servletContextName.equals(release.getServletContextName())) {
				release = new Release(
					servletContextName, release.getSchemaVersion(), verified);
			}

			releaseMap.put(release.getServletContextName(), release);
		}

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleaseMap(_destinationConnection)
		).thenReturn(
			releaseMap
		);
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