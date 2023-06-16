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

package com.liferay.portal.tools.db.partition.virtual.instance.migrator.util;

import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.Recorder;
import com.liferay.portal.tools.db.partition.virtual.instance.migrator.Release;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.sql.Connection;

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
		_targetConnection = Mockito.mock(Connection.class);
	}

	@After
	public void tearDown() {
		System.setOut(_originalOut);
		_databaseMockedStatic.close();
	}

	@Test
	public void testCheckWebIdNotValid() throws Exception {
		_mockWebIds(false);

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"[ERROR] WebId " + _TEST_WEB_ID +
					" already exists in target database"));
	}

	@Test
	public void testCheckWebIdValid() throws Exception {
		_mockWebIds(true);

		_executeAndAssert(false, false, null);
	}

	@Test
	public void testHigherVersionModule() throws Exception {
		_mockReleaseSchemaVersion("module2.service", "1.0.0");

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"[ERROR] Module module2.service needs to be upgraded in " +
					"target database before the migration"));
	}

	@Test
	public void testInvalidSourceReleaseState() throws Exception {
		List<String> failedServletContextNames = Arrays.asList(
			"module1", "module2");

		_mockFailedServletContextNames(
			failedServletContextNames, new ArrayList<>());

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"[ERROR] Module module1 has a failed Release state in the " +
					"source database",
				"[ERROR] Module module2 has a failed Release state in the " +
					"source database"));
	}

	@Test
	public void testInvalidTargetReleaseState() throws Exception {
		List<String> failedServletContextNames = Arrays.asList(
			"module1", "module2");

		_mockFailedServletContextNames(
			new ArrayList<>(), failedServletContextNames);

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"[ERROR] Module module1 has a failed Release state in the " +
					"target database",
				"[ERROR] Module module2 has a failed Release state in the " +
					"target database"));
	}

	@Test
	public void testLowerVersionModule() throws Exception {
		_mockReleaseSchemaVersion("module1", "10.0.0");

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"[ERROR] Module module1 needs to be upgraded in source " +
					"database before the migration"));
	}

	@Test
	public void testMissingSourceModule() throws Exception {
		_mockMissingSourceModule("module1");

		_executeAndAssert(
			false, true,
			Arrays.asList(
				"[WARN] Module module1 is not present in the target database"));
	}

	@Test
	public void testMissingTablesInBothDatabases() throws Exception {
		_mockMissingTables(
			new ArrayList<>(
				Arrays.asList("table1", "table2", "table3", "table5")),
			new ArrayList<>(
				Arrays.asList("table1", "table3", "table4", "table5")));

		_executeAndAssert(
			false, true,
			Arrays.asList(
				"[WARN] Table table4 is not present in source database",
				"[WARN] Table table2 is not present in target database"));
	}

	@Test
	public void testMissingTablesInSource() throws Exception {
		_mockMissingTables(
			new ArrayList<>(Arrays.asList("table1", "table3", "table4")),
			new ArrayList<>(
				Arrays.asList(
					"table1", "table2", "table3", "table4", "table5")));

		_executeAndAssert(
			false, true,
			Arrays.asList(
				"[WARN] Table table2 is not present in source database",
				"[WARN] Table table5 is not present in source database"));
	}

	@Test
	public void testMissingTablesInTarget() throws Exception {
		_mockMissingTables(
			new ArrayList<>(
				Arrays.asList(
					"table1", "table2", "table3", "table4", "table5")),
			new ArrayList<>(Arrays.asList("table1", "table3", "table4")));

		_executeAndAssert(
			false, true,
			Arrays.asList(
				"[WARN] Table table2 is not present in target database",
				"[WARN] Table table5 is not present in target database"));
	}

	@Test
	public void testMissingTargetNotServiceModule() throws Exception {
		_mockMissingTargetModule("module1");

		_executeAndAssert(
			false, true,
			Arrays.asList(
				"[WARN] Module module1 is not present in the source database"));
	}

	@Test
	public void testMissingTargetServiceModule() throws Exception {
		_mockMissingTargetModule("module2.service");

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"[ERROR] Module module2.service needs to be installed in the " +
					"source database before the migration"));
	}

	@Test
	public void testUnverifiedSourceModule() throws Exception {
		_mockupReleaseVerified("module2.service", true);

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"[ERROR] Module module2.service needs to be verified in the " +
					"source database before the migration"));
	}

	@Test
	public void testUnverifiedTargetModule() throws Exception {
		_mockupReleaseVerified("module2", false);

		_executeAndAssert(
			true, false,
			Arrays.asList(
				"[ERROR] Module module2 needs to be verified in the target " +
					"database before the migration"));
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
		throws Exception {

		Recorder recorder = Validator.validateDatabases(
			_sourceConnection, _targetConnection);

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
		List<String> targetFailedServletContextNames) {

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getFailedServletContextNames(_sourceConnection)
		).thenReturn(
			sourceFailedServletContextNames
		);

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getFailedServletContextNames(_targetConnection)
		).thenReturn(
			targetFailedServletContextNames
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
			() -> DatabaseUtil.getReleasesMap(_targetConnection)
		).thenReturn(
			releaseMap
		);
	}

	private void _mockMissingTables(
		List<String> sourceTableNames, List<String> targetTableNames) {

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getPartitionedTableNames(_sourceConnection)
		).thenReturn(
			sourceTableNames
		);

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getPartitionedTableNames(_targetConnection)
		).thenReturn(
			targetTableNames
		);
	}

	private void _mockMissingTargetModule(String servletContextName) {
		List<Release> releases = _createReleaseElements();

		Map<String, Release> releaseMap = new HashMap<>();

		List<Release> missingTargetModuleReleases = new ArrayList<>();

		for (Release release : releases) {
			releaseMap.put(release.getServletContextName(), release);

			if (!servletContextName.equals(release.getServletContextName())) {
				missingTargetModuleReleases.add(release);
			}
		}

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleases(_sourceConnection)
		).thenReturn(
			missingTargetModuleReleases
		);

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleasesMap(_targetConnection)
		).thenReturn(
			releaseMap
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
			() -> DatabaseUtil.getReleasesMap(_targetConnection)
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
			() -> DatabaseUtil.getReleasesMap(_targetConnection)
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
			() -> DatabaseUtil.hasWebId(_targetConnection, _TEST_WEB_ID)
		).thenReturn(
			!valid
		);
	}

	private static final String _TEST_WEB_ID = "www.able.com";

	private MockedStatic<DatabaseUtil> _databaseMockedStatic;
	private final PrintStream _originalOut = System.out;
	private Connection _sourceConnection;
	private Connection _targetConnection;
	private final ByteArrayOutputStream _testOutByteArrayOutputStream =
		new ByteArrayOutputStream();

}