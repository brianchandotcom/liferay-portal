/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.migration.validator.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.tools.db.partition.migration.validator.DBPartitionMigrationValidator;
import com.liferay.portal.tools.db.partition.migration.validator.LiferayDatabase;
import com.liferay.portal.tools.db.partition.migration.validator.util.DatabaseUtil;
import com.liferay.portal.util.PropsValues;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import java.security.Permission;

import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class DBPartitionMigrationValidatorTest extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		BaseDBPartitionTestCase.setUpClass();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_deleteCompany();
	}

	@Before
	public void setUp() throws Exception {
		System.setErr(new PrintStream(_errByteArrayOutputStream));
		System.setOut(new PrintStream(_outByteArrayOutputStream));
		System.setSecurityManager(new DisallowExitSecurityManager());

		if (_company == null) {
			_company = CompanyTestUtil.addCompany();
		}

		_outputDirectoryFile = new File(PropsValues.LIFERAY_HOME, "exports");

		_outputDirectoryFile.mkdirs();
	}

	@After
	public void tearDown() throws Exception {
		FileUtils.deleteDirectory(_outputDirectoryFile);
		System.setErr(_originalErrPrintStream);
		System.setOut(_originalOutPrintStream);
	}

	@Test
	public void testValidateFailure() throws Exception {
		String sourceFileName = _testExport(_company.getCompanyId());

		String targetFilePath = _testExport(TestPropsValues.getCompanyId());

		File[] files = _outputDirectoryFile.listFiles();

		Assert.assertEquals(Arrays.toString(files), 2, files.length);

		_testValidate(
			() -> {
			},
			runtimeException -> {
				String string = _outByteArrayOutputStream.toString();

				Assert.assertTrue(
					string.contains(
						"[ERROR] Company ID " + _company.getCompanyId() +
							" already exists in the target database"));
				Assert.assertTrue(
					string.contains(
						StringBundler.concat(
							"[WARN] Company name ", _company.getName(),
							" already exists in the target database. You must ",
							"set a different value in ",
							"DBPartitionInsertVirtualInstanceConfiguration.",
							"config.")));
				Assert.assertTrue(
					string.contains(
						StringBundler.concat(
							"[WARN] Virtual host ",
							_company.getVirtualHostname(),
							" already exists in the target database. You must ",
							"set a different value in ",
							"DBPartitionInsertVirtualInstanceConfiguration.",
							"config.")));
				Assert.assertTrue(
					string.contains(
						StringBundler.concat(
							"[WARN] Web ID ", _company.getWebId(),
							" already exists in the target database. You must ",
							"set a different value in ",
							"DBPartitionInsertVirtualInstanceConfiguration.",
							"config.")));
			},
			sourceFileName, targetFilePath);
	}

	@Test
	public void testValidateSuccess() throws Exception {
		String sourceFileName = _testExport(_company.getCompanyId());

		_deleteCompany();

		String targetFileName = _testExport(TestPropsValues.getCompanyId());

		File[] files = _outputDirectoryFile.listFiles();

		Assert.assertEquals(Arrays.toString(files), 2, files.length);

		_testValidate(
			() -> {
				Assert.assertTrue(
					_outByteArrayOutputStream.toString(
					).isEmpty());
				Assert.assertTrue(
					_errByteArrayOutputStream.toString(
					).isEmpty());
			},
			runtimeException -> Assert.assertEquals(
				"0", runtimeException.getMessage()),
			sourceFileName, targetFileName);
	}

	private static void _deleteCompany() throws Exception {
		if (_company != null) {
			_companyLocalService.deleteCompany(_company);
		}

		_company = null;
	}

	private String _testExport(long companyId) throws Exception {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(companyId)) {

			return ReflectionTestUtil.invoke(
				DBPartitionMigrationValidator.class, "_write",
				new Class<?>[] {LiferayDatabase.class, String.class},
				DatabaseUtil.exportLiferayDatabase(connection),
				_outputDirectoryFile.getAbsolutePath());
		}
	}

	private void _testValidate(
			UnsafeRunnable<Exception> unsafeRunnable,
			UnsafeConsumer<RuntimeException, Exception> unsafeConsumer,
			String sourceFileName, String targetFileName)
		throws Exception {

		try {
			DBPartitionMigrationValidator.main(
				new String[] {
					"--validate", "--source-file", sourceFileName,
					"--target-file", targetFileName
				});
		}
		catch (RuntimeException runtimeException) {
			unsafeConsumer.accept(runtimeException);
		}

		unsafeRunnable.run();
	}

	private static Company _company;

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static File _outputDirectoryFile;

	private final ByteArrayOutputStream _errByteArrayOutputStream =
		new ByteArrayOutputStream();
	private final PrintStream _originalErrPrintStream = System.err;
	private final PrintStream _originalOutPrintStream = System.out;
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