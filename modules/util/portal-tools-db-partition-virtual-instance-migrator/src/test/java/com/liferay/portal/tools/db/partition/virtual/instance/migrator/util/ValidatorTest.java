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

import com.liferay.petra.function.UnsafeRunnable;
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
		System.setOut(new PrintStream(_byteArrayOutputStream));

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
	public void testValidatePartitionedTables() throws Exception {
		_testValidatePartitionedTables(
			new ArrayList<>(
				Arrays.asList("table1", "table2", "table3", "table5")),
			new ArrayList<>(
				Arrays.asList("table1", "table3", "table4", "table5")),
			() -> _validateAndAssert(
				false, true,
				Arrays.asList(
					"[WARN] Table table2 is not present in the target database",
					"[WARN] Table table4 is not present in the source " +
						"database")));

		_testValidatePartitionedTables(
			new ArrayList<>(Arrays.asList("table1", "table3", "table4")),
			new ArrayList<>(
				Arrays.asList(
					"table1", "table2", "table3", "table4", "table5")),
			() -> _validateAndAssert(
				false, true,
				Arrays.asList(
					"[WARN] Table table2 is not present in the source database",
					"[WARN] Table table5 is not present in the source " +
						"database")));

		_testValidatePartitionedTables(
			new ArrayList<>(
				Arrays.asList(
					"table1", "table2", "table3", "table4", "table5")),
			new ArrayList<>(Arrays.asList("table1", "table3", "table4")),
			() -> _validateAndAssert(
				false, true,
				Arrays.asList(
					"[WARN] Table table2 is not present in the target database",
					"[WARN] Table table5 is not present in the target " +
						"database")));
	}

	@Test
	public void testValidateReleaseMissingSourceModules() throws Exception {
		List<Release> releases = _createReleases();

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleases(_sourceConnection)
		).thenReturn(
			releases
		);

		Map<String, Release> releasesMap = new HashMap<>();

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleasesMap(_targetConnection)
		).thenReturn(
			releasesMap
		);

		for (Release release : releases) {
			if (!release.getServletContextName(
				).equals(
					"module1"
				)) {

				releasesMap.put(release.getServletContextName(), release);
			}
		}

		_validateAndAssert(
			false, true,
			Arrays.asList(
				"[WARN] Module module1 is not present in the target database"));
	}

	@Test
	public void testValidateReleaseMissingTargetModules() throws Exception {
		_testValidateReleaseMissingTargetModule(
			"module1",
			() -> _validateAndAssert(
				false, true,
				Arrays.asList(
					"[WARN] Module module1 is not present in the source " +
						"database")));

		_testValidateReleaseMissingTargetModule(
			"module2.service",
			() -> _validateAndAssert(
				true, false,
				Arrays.asList(
					"[ERROR] Module module2.service needs to be installed in " +
						"the source database before the migration")));
	}

	@Test
	public void testValidateReleaseState() throws Exception {
		List<String> failedServletContextNames = Arrays.asList(
			"module1", "module2");

		_testValidateReleaseState(
			failedServletContextNames, new ArrayList<>(),
			() -> _validateAndAssert(
				true, false,
				Arrays.asList(
					"[ERROR] Module module1 has a failed release state in " +
						"the source database",
					"[ERROR] Module module2 has a failed release state in " +
						"the source database")));

		_testValidateReleaseState(
			new ArrayList<>(), failedServletContextNames,
			() -> _validateAndAssert(
				true, false,
				Arrays.asList(
					"[ERROR] Module module1 has a failed release state in " +
						"the target database",
					"[ERROR] Module module2 has a failed release state in " +
						"the target database")));
	}

	@Test
	public void testValidateReleaseUnverifiedModules() throws Exception {
		_testValidateReleaseUnverifiedModule(
			"module2.service", true,
			() -> _validateAndAssert(
				true, false,
				Arrays.asList(
					"[ERROR] Module module2.service needs to be verified in " +
						"the source database before the migration")));

		_testValidateReleaseUnverifiedModule(
			"module2", false,
			() -> _validateAndAssert(
				true, false,
				Arrays.asList(
					"[ERROR] Module module2 needs to be verified in the " +
						"target database before the migration")));
	}

	@Test
	public void testValidateReleaseVersionModules() throws Exception {
		_testValidateReleaseVersionModule(
			"1.0.0", "module2.service",
			() -> _validateAndAssert(
				true, false,
				Arrays.asList(
					"[ERROR] Module module2.service needs to be upgraded in " +
						"the target database before the migration")));

		_testValidateReleaseVersionModule(
			"10.0.0", "module1",
			() -> _validateAndAssert(
				true, false,
				Arrays.asList(
					"[ERROR] Module module1 needs to be upgraded in the " +
						"source database before the migration")));
	}

	@Test
	public void testValidateWebId() throws Exception {
		_testValidateWebId(
			false,
			() -> _validateAndAssert(
				true, false,
				Arrays.asList(
					"[ERROR] Web ID " + _TEST_WEB_ID +
						" already exists in the target database")));

		_testValidateWebId(true, () -> _validateAndAssert(false, false, null));
	}

	private List<Release> _createReleases() {
		return Arrays.asList(
			new Release(Version.parseVersion("3.5.1"), "module1.service", true),
			new Release(
				Version.parseVersion("5.0.0"), "module2.service", false),
			new Release(Version.parseVersion("2.3.2"), "module1", true),
			new Release(Version.parseVersion("5.1.0"), "module2", true));
	}

	private void _testValidatePartitionedTables(
			List<String> sourceTableNames, List<String> targetTableNames,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

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

		unsafeRunnable.run();
	}

	private void _testValidateReleaseMissingTargetModule(
			String servletContextName, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		List<Release> missingTargetModuleReleases = new ArrayList<>();

		Map<String, Release> releasesMap = new HashMap<>();

		for (Release release : _createReleases()) {
			releasesMap.put(release.getServletContextName(), release);

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
			releasesMap
		);

		unsafeRunnable.run();
	}

	private void _testValidateReleaseState(
			List<String> sourceFailedServletContextNames,
			List<String> targetFailedServletContextNames,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

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

		unsafeRunnable.run();
	}

	private void _testValidateReleaseUnverifiedModule(
			String servletContextName, boolean verified,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		List<Release> releases = _createReleases();

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleases(_sourceConnection)
		).thenReturn(
			releases
		);

		Map<String, Release> releaseMap = new HashMap<>();

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleasesMap(_targetConnection)
		).thenReturn(
			releaseMap
		);

		for (Release release : releases) {
			if (servletContextName.equals(release.getServletContextName())) {
				release = new Release(
					release.getSchemaVersion(), servletContextName, verified);
			}

			releaseMap.put(release.getServletContextName(), release);
		}

		unsafeRunnable.run();
	}

	private void _testValidateReleaseVersionModule(
			String schemaVersion, String servletContextName,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		List<Release> releases = _createReleases();

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleases(_sourceConnection)
		).thenReturn(
			releases
		);

		Map<String, Release> releasesMap = new HashMap<>();

		_databaseMockedStatic.when(
			() -> DatabaseUtil.getReleasesMap(_targetConnection)
		).thenReturn(
			releasesMap
		);

		for (Release release : releases) {
			if (servletContextName.equals(release.getServletContextName())) {
				release = new Release(
					Version.parseVersion(schemaVersion), servletContextName,
					release.getVerified());
			}

			releasesMap.put(release.getServletContextName(), release);
		}

		unsafeRunnable.run();
	}

	private void _testValidateWebId(
			boolean valid, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

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

		unsafeRunnable.run();
	}

	private void _validateAndAssert(
			boolean hasErrors, boolean hasWarnings, List<String> messages)
		throws Exception {

		try {
			Recorder recorder = Validator.validateDatabases(
				_sourceConnection, _targetConnection);

			Assert.assertEquals(hasErrors, recorder.hasErrors());
			Assert.assertEquals(hasWarnings, recorder.hasWarnings());

			recorder.printMessages();

			String string = _byteArrayOutputStream.toString();

			if (messages == null) {
				Assert.assertTrue(string.isEmpty());
			}
			else {
				for (String message : messages) {
					Assert.assertTrue(string.contains(message));
				}
			}
		}
		finally {
			_byteArrayOutputStream.reset();
		}
	}

	private static final String _TEST_WEB_ID = "test.com";

	private final ByteArrayOutputStream _byteArrayOutputStream =
		new ByteArrayOutputStream();
	private MockedStatic<DatabaseUtil> _databaseMockedStatic;
	private final PrintStream _originalOut = System.out;
	private Connection _sourceConnection;
	private Connection _targetConnection;

}