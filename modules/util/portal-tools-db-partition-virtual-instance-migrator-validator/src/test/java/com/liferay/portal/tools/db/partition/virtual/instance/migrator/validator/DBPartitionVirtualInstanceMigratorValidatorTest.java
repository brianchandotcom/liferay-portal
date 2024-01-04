/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migrator.validator;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.net.URISyntaxException;
import java.net.URL;

import java.nio.file.Paths;

import java.security.Permission;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Luis Ortiz
 */
public class DBPartitionVirtualInstanceMigratorValidatorTest {

	@Before
	public void setUp() {
		System.setErr(new PrintStream(_errByteArrayOutputStream));
		System.setOut(new PrintStream(_outByteArrayOutputStream));
		System.setSecurityManager(new DisallowExitSecurityManager());
	}

	@After
	public void tearDown() {
		System.setErr(_originalErr);
		System.setOut(_originalOut);
	}

	@Test
	public void testSourceMultipleCompanies() throws Exception {
		_testAndAssert(
			() -> {
			},
			runtimeException -> {
				Assert.assertEquals("1", runtimeException.getMessage());
				Assert.assertTrue(
					_errByteArrayOutputStream.toString(
					).contains(
						"Source has more than one company"
					));
				Assert.assertTrue(
					_outByteArrayOutputStream.toString(
					).isEmpty());
			},
			"sourceMultipleCompanies.data", "targetSuccess.data");
	}

	@Test
	public void testTargetNondefaultPartition() throws Exception {
		_testAndAssert(
			() -> {
			},
			runtimeException -> {
				Assert.assertEquals("1", runtimeException.getMessage());
				Assert.assertTrue(
					_errByteArrayOutputStream.toString(
					).contains(
						"Target is not the default partition"
					));
				Assert.assertTrue(
					_outByteArrayOutputStream.toString(
					).isEmpty());
			},
			"sourceSuccess.data", "targetNotDefault.data");
	}

	@Test
	public void testValidationErrors() throws Exception {
		_testAndAssert(
			() -> {
			},
			runtimeException -> {
				Assert.assertEquals("1", runtimeException.getMessage());

				String string = _outByteArrayOutputStream.toString();

				Assert.assertTrue(
					string.contains(
						"[ERROR] Company ID 3007447931789165977 already " +
							"exists in the target database"));
				Assert.assertTrue(
					string.contains(
						"[ERROR] Module com.liferay.address.impl needs to be " +
							"verified in the source database before the " +
								"migration"));
				Assert.assertTrue(
					string.contains(
						"[ERROR] Module com.liferay.comment.page.comments." +
							"web has a failed release state in the source " +
								"database"));
				Assert.assertTrue(
					string.contains(
						"[ERROR] Module com.liferay.exportimport.service " +
							"needs to be installed in the source database " +
								"before the migration"));
				Assert.assertTrue(
					string.contains(
						"[ERROR] Module com.liferay.knowledge.base.web needs " +
							"to be upgraded in the target database before " +
								"the migration"));
				Assert.assertTrue(
					string.contains(
						"[ERROR] Module com.liferay.organizations.service " +
							"has a failed release state in the target " +
								"database"));
				Assert.assertTrue(
					string.contains(
						"[ERROR] Module com.liferay.organizations.service " +
							"needs to be verified in the target database " +
								"before the migration"));
				Assert.assertTrue(
					string.contains(
						"[ERROR] Module com.liferay.wiki.web needs to be " +
							"upgraded in the source database before the " +
								"migration"));
				Assert.assertTrue(
					string.contains(
						"[WARN] Company name Liferay DXP already exists in " +
							"the target database. Please change it during " +
								"migration."));
				Assert.assertTrue(
					string.contains(
						"[WARN] Module com.liferay.asset.publisher.web is " +
							"not present in the source database"));
				Assert.assertTrue(
					string.contains(
						"[WARN] Module com.liferay.license.manager.web is " +
							"not present in the target database"));
				Assert.assertTrue(
					string.contains(
						"[WARN] Table CommercePriceList is not present in " +
							"the source database"));
				Assert.assertTrue(
					string.contains(
						"[WARN] Table DDMTemplate is not present in the " +
							"target database"));
				Assert.assertTrue(
					string.contains(
						"[WARN] Virtual host localhost already exists in the " +
							"target database. Please change it during " +
								"migration."));
				Assert.assertTrue(
					string.contains(
						"[WARN] Web ID liferay.com already exists in the " +
							"target database. Please change it during " +
								"migration."));
			},
			"sourceFailure.data", "targetFailure.data");
	}

	@Test
	public void testValidationSuccess() throws Exception {
		_testAndAssert(
			() -> {
				Assert.assertTrue(
					_errByteArrayOutputStream.toString(
					).isEmpty());
				Assert.assertTrue(
					_outByteArrayOutputStream.toString(
					).isEmpty());
			},
			runtimeException -> Assert.fail(), "sourceSuccess.data",
			"targetSuccess.data");
	}

	private String _getResourceFilePath(String fileName)
		throws URISyntaxException {

		URL resource =
			DBPartitionVirtualInstanceMigratorValidatorTest.class.getResource(
				"dependencies/" + fileName);

		return String.valueOf(Paths.get(resource.toURI()));
	}

	private void _testAndAssert(
			UnsafeRunnable<Exception> afterExecutionValidations,
			UnsafeConsumer<RuntimeException, Exception> catchValidations,
			String sourceFile, String targetFile)
		throws Exception {

		try {
			DBPartitionVirtualInstanceMigratorValidator.main(
				new String[] {
					"-s", _getResourceFilePath(sourceFile), "-t",
					_getResourceFilePath(targetFile)
				});
		}
		catch (RuntimeException runtimeException) {
			catchValidations.accept(runtimeException);
		}

		afterExecutionValidations.run();
	}

	private final ByteArrayOutputStream _errByteArrayOutputStream =
		new ByteArrayOutputStream();
	private final PrintStream _originalErr = System.err;
	private final PrintStream _originalOut = System.out;
	private final ByteArrayOutputStream _outByteArrayOutputStream =
		new ByteArrayOutputStream();

	private class DisallowExitSecurityManager extends SecurityManager {

		@Override
		public void checkExit(int status) {
			super.checkExit(status);

			throw new RuntimeException(String.valueOf(status));
		}

		@Override
		public void checkPermission(Permission perm) {
		}

	}

}