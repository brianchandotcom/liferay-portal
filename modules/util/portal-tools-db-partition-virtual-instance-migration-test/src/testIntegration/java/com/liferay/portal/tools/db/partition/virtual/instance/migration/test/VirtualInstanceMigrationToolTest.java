/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.db.partition.test.util.BaseDBPartitionTestCase;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.common.LiferayInstance;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.extractor.DBPartitionVirtualInstanceMigrationExtractor;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.extractor.util.DatabaseUtil;
import com.liferay.portal.tools.db.partition.virtual.instance.migration.validator.DBPartitionVirtualInstanceMigrationValidator;
import com.liferay.portal.util.PropsValues;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
public class VirtualInstanceMigrationToolTest extends BaseDBPartitionTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		enableDBPartition();
		_defaultCompanyId = TestPropsValues.getCompanyId();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		disableDBPartition();
	}

	@Before
	public void setUp() throws Exception {
		System.setErr(new PrintStream(_errByteArrayOutputStream));
		System.setOut(new PrintStream(_outByteArrayOutputStream));
		System.setSecurityManager(new DisallowExitSecurityManager());
		_company = CompanyTestUtil.addCompany();

		_outputDirectoryFile = new File(
			PropsValues.LIFERAY_HOME, "extractions");

		_outputDirectoryFile.mkdirs();
	}

	@After
	public void tearDown() throws IOException, PortalException {
		_deleteCompany();
		System.setErr(_originalErr);
		System.setOut(_originalOut);
		FileUtils.deleteDirectory(_outputDirectoryFile);
	}

	@Test
	public void testFailure() throws Exception {
		String sourceFilePath = _runExtractorTool(_company.getCompanyId());

		String targetFilePath = _runExtractorTool(_defaultCompanyId);

		File[] files = _outputDirectoryFile.listFiles();

		Assert.assertEquals(Arrays.toString(files), 2, files.length);

		_runValidationTool(
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
						"[WARN] Company name " + _company.getName() +
							" already exists in the target database. Please " +
								"change it during migration."));
				Assert.assertTrue(
					string.contains(
						"[WARN] Virtual host " + _company.getVirtualHostname() +
							" already exists in the target database. Please " +
								"change it during migration."));
				Assert.assertTrue(
					string.contains(
						"[WARN] Web ID " + _company.getWebId() +
							" already exists in the target database. Please " +
								"change it during migration."));
			},
			sourceFilePath, targetFilePath);
	}

	@Test
	public void testSuccess() throws Exception {
		String sourceFilePath = _runExtractorTool(_company.getCompanyId());

		_deleteCompany();

		String targetFilePath = _runExtractorTool(_defaultCompanyId);

		File[] files = _outputDirectoryFile.listFiles();

		Assert.assertEquals(Arrays.toString(files), 2, files.length);

		_runValidationTool(
			() -> {
				Assert.assertTrue(
					_outByteArrayOutputStream.toString(
					).isEmpty());
				Assert.assertTrue(
					_errByteArrayOutputStream.toString(
					).isEmpty());
			},
			runtimeException -> Assert.fail(), sourceFilePath, targetFilePath);
	}

	private void _deleteCompany() throws PortalException {
		if (_company != null) {
			_companyLocalService.deleteCompany(_company);
		}

		_company = null;
	}

	private String _runExtractorTool(long companyId) throws Exception {
		try (SafeCloseable safeCloseable =
				CompanyThreadLocal.setWithSafeCloseable(companyId)) {

			return ReflectionTestUtil.invoke(
				DBPartitionVirtualInstanceMigrationExtractor.class,
				"_writeToFile",
				new Class<?>[] {LiferayInstance.class, String.class},
				DatabaseUtil.exportLiferayInstance(connection),
				_outputDirectoryFile.getAbsolutePath());
		}
	}

	private void _runValidationTool(
			UnsafeRunnable<Exception> afterExecutionValidations,
			UnsafeConsumer<RuntimeException, Exception> catchValidations,
			String sourceFile, String targetFile)
		throws Exception {

		try {
			DBPartitionVirtualInstanceMigrationValidator.main(
				new String[] {"-s", sourceFile, "-t", targetFile});
		}
		catch (RuntimeException runtimeException) {
			catchValidations.accept(runtimeException);
		}

		afterExecutionValidations.run();
	}

	@Inject
	private static CompanyLocalService _companyLocalService;

	private static long _defaultCompanyId;
	private static File _outputDirectoryFile;

	private Company _company;
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