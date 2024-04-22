/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.migration.validator;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;

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
public class DBPartitionMigrationValidatorValidationTest {

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
	public void testTargetNondefaultPartition() throws Exception {
		_validate(
			"sourceSuccess.json", "targetNotDefault.json",
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
			() -> {
			});
	}

	@Test
	public void testValidationErrors() throws Exception {
		_validate(
			"sourceFailure.json", "targetFailure.json",
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
						StringBundler.concat(
							"[WARN] Company name Liferay DXP already exists ",
							"in the target database. You must set a different ",
							"value in ",
							"DBPartitionInsertVirtualInstanceConfiguration.",
							"config.")));
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
						StringBundler.concat(
							"[WARN] Virtual host localhost already exists in ",
							"the target database. You must set a different ",
							"value in ",
							"DBPartitionInsertVirtualInstanceConfiguration.",
							"config.")));
				Assert.assertTrue(
					string.contains(
						StringBundler.concat(
							"[WARN] Web ID liferay.com already exists in the ",
							"target database. You must set a different value ",
							"in ",
							"DBPartitionInsertVirtualInstanceConfiguration.",
							"config.")));
			},
			() -> {
			});
	}

	@Test
	public void testValidationSuccess() throws Exception {
		_validate(
			"sourceSuccess.json", "targetSuccess.json",
			runtimeException -> Assert.fail(),
			() -> {
				Assert.assertTrue(
					_errByteArrayOutputStream.toString(
					).isEmpty());
				Assert.assertTrue(
					_outByteArrayOutputStream.toString(
					).isEmpty());
			});
	}

	private String _getResourceFilePath(String fileName)
		throws URISyntaxException {

		URL resource =
			DBPartitionMigrationValidatorValidationTest.class.getResource(
				"dependencies/" + fileName);

		return String.valueOf(Paths.get(resource.toURI()));
	}

	private void _validate(
			String sourceFile, String targetFile,
			UnsafeConsumer<RuntimeException, Exception> catchValidations,
			UnsafeRunnable<Exception> validations)
		throws Exception {

		try {
			DBPartitionMigrationValidator.main(
				new String[] {
					"--validate", "--source-file",
					_getResourceFilePath(sourceFile), "--target-file",
					_getResourceFilePath(targetFile)
				});
		}
		catch (RuntimeException runtimeException) {
			catchValidations.accept(runtimeException);
		}

		validations.run();
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

			if (status != 0) {
				throw new RuntimeException(String.valueOf(status));
			}
		}

		@Override
		public void checkPermission(Permission perm) {
		}

	}

}