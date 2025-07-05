/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.instance.PortalInstancePool;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.PropsValuesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.verify.PreupgradeVerifyStoreFileSystemStructure;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.test.util.BaseVerifyProcessTestCase;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author István András Dézsi
 */
@RunWith(Arquillian.class)
public class PreupgradeVerifyStoreFileSystemStructureTest
	extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_companyId = TestPropsValues.getCompanyId();

		_advancedFileSystemStoreConfiguration =
			_configurationAdmin.getConfiguration(
				"com.liferay.portal.store.file.system.configuration." +
					"AdvancedFileSystemStoreConfiguration",
				StringPool.QUESTION);
		_advancedFileSystemStoreRootDir =
			PropsUtil.get(PropsKeys.LIFERAY_HOME) +
				"/test/store/advanced_file_system";

		ConfigurationTestUtil.saveConfiguration(
			_advancedFileSystemStoreConfiguration,
			HashMapDictionaryBuilder.<String, Object>put(
				"rootDir", _advancedFileSystemStoreRootDir
			).build());

		_fileSystemStoreConfiguration = _configurationAdmin.getConfiguration(
			"com.liferay.portal.store.file.system.configuration." +
				"FileSystemStoreConfiguration",
			StringPool.QUESTION);
		_fileSystemStoreRootDir =
			PropsUtil.get(PropsKeys.LIFERAY_HOME) + "/test/store/file_system";

		ConfigurationTestUtil.saveConfiguration(
			_fileSystemStoreConfiguration,
			HashMapDictionaryBuilder.<String, Object>put(
				"rootDir", _fileSystemStoreRootDir
			).build());

		_originalCacheEnabled = ReflectionTestUtil.getAndSetFieldValue(
			PortalInstancePool.class, "_cacheEnabled", false);

		_repositoryId = RandomTestUtil.nextLong();

		_upgradeDatabaseDLStorageCheckDisabledSafeCloseable =
			PropsValuesTestUtil.swapWithSafeCloseable(
				"UPGRADE_DATABASE_DL_STORAGE_CHECK_DISABLED", false);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(
			_advancedFileSystemStoreConfiguration);
		ConfigurationTestUtil.deleteConfiguration(
			_fileSystemStoreConfiguration);

		FileUtil.deltree(_advancedFileSystemStoreRootDir);
		FileUtil.deltree(_fileSystemStoreRootDir);

		ReflectionTestUtil.setFieldValue(
			PortalInstancePool.class, "_cacheEnabled", _originalCacheEnabled);

		_upgradeDatabaseDLStorageCheckDisabledSafeCloseable.close();
	}

	@Before
	@Override
	public void setUp() throws Exception {
		CompanyLocalServiceUtil.forEachCompanyId(
			companyId -> {
				Files.createDirectories(
					Paths.get(
						_advancedFileSystemStoreRootDir,
						String.valueOf(companyId)));
				Files.createDirectories(
					Paths.get(
						_fileSystemStoreRootDir, String.valueOf(companyId)));
			},
			PortalInstancePool.getCompanyIds());
	}

	@After
	public void tearDown() {
		FileUtil.deltree(_advancedFileSystemStoreRootDir);
		FileUtil.deltree(_fileSystemStoreRootDir);
	}

	@Test
	public void testVerifyAdvancedFileSystemStoreLongFileNameDirectoryWithoutExtension()
		throws Exception {

		Path fileNameDirectoryWithoutExtensionPath = Paths.get(
			_advancedFileSystemStoreRootDir, String.valueOf(_companyId),
			String.valueOf(_repositoryId), "100");

		String expectedLogEntry = StringBundler.concat(
			"File ", fileNameDirectoryWithoutExtensionPath,
			" name has more than 2 characters and no extension in advanced ",
			"file system structure");

		Files.createDirectories(fileNameDirectoryWithoutExtensionPath);

		_assertVerifyLogEntry(_DL_STORE_IMPL_ADVANCED_FILE_SYSTEM_STORE, expectedLogEntry);
	}

	@Test
	public void testVerifyAdvancedFileSystemStoreValidDirectory()
		throws Exception {

		Path validDirectoryPath = Paths.get(
			_advancedFileSystemStoreRootDir, String.valueOf(_companyId),
			String.valueOf(_repositoryId), "10", "100.afsh");

		Files.createDirectories(validDirectoryPath);

		_assertVerifyValidDirectory(_DL_STORE_IMPL_ADVANCED_FILE_SYSTEM_STORE);
	}

	@Test
	public void testVerifyFileSystemStoreFileInsteadOfCompanyIdDirectory()
		throws Exception {

		Path fileSystemStoreRootPath = Paths.get(_fileSystemStoreRootDir);

		long companyId = PortalInstancePool.getCompanyIds()[0];

		Path companyIdPath = fileSystemStoreRootPath.resolve(
			String.valueOf(companyId));
		Path companyIdBackupPath = fileSystemStoreRootPath.resolve(
			String.valueOf(companyId) + "_backup");

		try {
			Files.move(companyIdPath, companyIdBackupPath);
			Files.createFile(companyIdPath);

			String expectedExceptionMessage =
				companyIdPath + " is not a directory";

			_assertVerifyExceptionMessage(
				_DL_STORE_IMPL_FILE_SYSTEM_STORE, expectedExceptionMessage);
		}
		finally {
			Files.delete(companyIdPath);
			Files.move(companyIdBackupPath, companyIdPath);
		}
	}

	@Test
	public void testVerifyFileSystemStoreFileInsteadOfFileNameDirectory()
		throws Exception {

		Path repositoryIdPath = Paths.get(
			_fileSystemStoreRootDir, String.valueOf(_companyId),
			String.valueOf(_repositoryId));

		Path invalidFilePath = repositoryIdPath.resolve("invalidFile.txt");

		String expectedLogEntry =
			"Unexpected file " + invalidFilePath + " in file system structure";

		Files.createDirectories(invalidFilePath.getParent());
		Files.createFile(invalidFilePath);

		_assertVerifyLogEntry(_DL_STORE_IMPL_FILE_SYSTEM_STORE, expectedLogEntry);
	}

	@Test
	public void testVerifyFileSystemStoreFileInsteadOfRepositoryIdDirectory()
		throws Exception {

		Path companyIdPath = Paths.get(
			_fileSystemStoreRootDir, String.valueOf(_companyId));

		Path invalidFilePath = companyIdPath.resolve("invalidFile.txt");

		String expectedLogEntry =
			"Unexpected file " + invalidFilePath + " in file system structure";

		Files.createDirectories(invalidFilePath.getParent());
		Files.createFile(invalidFilePath);

		_assertVerifyLogEntry(_DL_STORE_IMPL_FILE_SYSTEM_STORE, expectedLogEntry);
	}

	@Test
	public void testVerifyFileSystemStoreFileNameDirectoryContainingExtension()
		throws Exception {

		Path fileNameDirectoryWithExtensionPath = Paths.get(
			_fileSystemStoreRootDir, String.valueOf(_companyId),
			String.valueOf(_repositoryId), "100.txt");

		String expectedLogEntry = StringBundler.concat(
			"Unexpected file name directory ",
			fileNameDirectoryWithExtensionPath,
			" with extension in file system structure");

		Files.createDirectories(fileNameDirectoryWithExtensionPath);

		_assertVerifyLogEntry(_DL_STORE_IMPL_FILE_SYSTEM_STORE, expectedLogEntry);
	}

	@Test
	public void testVerifyFileSystemStoreInvalidVersionLabelFile()
		throws Exception {

		Path fileNamePath = Paths.get(
			_fileSystemStoreRootDir, String.valueOf(_companyId),
			String.valueOf(_repositoryId), "100");

		Path invalidVersionLabelPath = fileNamePath.resolve(
			"invalidVersionLabel");

		String expectedLogEntry =
			"Unexpected file " + invalidVersionLabelPath +
				" not matching version label pattern";

		Files.createDirectories(invalidVersionLabelPath.getParent());
		Files.createFile(invalidVersionLabelPath);

		_assertVerifyLogEntry(_DL_STORE_IMPL_FILE_SYSTEM_STORE, expectedLogEntry);
	}

	@Test
	public void testVerifyFileSystemStoreMissingCompanyIdDirectory()
		throws Exception {

		Files.deleteIfExists(
			Paths.get(_fileSystemStoreRootDir, String.valueOf(_companyId)));

		String expectedExceptionMessage = StringBundler.concat(
			"Missing directories in ", Paths.get(_fileSystemStoreRootDir),
			" for companies: [", _companyId, "]");

		_assertVerifyExceptionMessage(
			_DL_STORE_IMPL_FILE_SYSTEM_STORE, expectedExceptionMessage);
	}

	@Test
	public void testVerifyFileSystemStoreValidDirectory() throws Exception {
		Path validDirectoryPath = Paths.get(
			_fileSystemStoreRootDir, String.valueOf(_companyId),
			String.valueOf(_repositoryId), "100", "1.0");

		Files.createDirectories(validDirectoryPath);

		_assertVerifyValidDirectory(_DL_STORE_IMPL_FILE_SYSTEM_STORE);
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new PreupgradeVerifyStoreFileSystemStructure();
	}

	private void _assertVerify(
			String dlStoreImpl, String expectedExceptionMessage,
			String expectedLogEntry)
		throws Exception {

		String originalDLStoreImpl = ReflectionTestUtil.getAndSetFieldValue(
			PropsValues.class, "DL_STORE_IMPL", dlStoreImpl);

		LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
			PreupgradeVerifyStoreFileSystemStructure.class.getName(),
			LoggerTestUtil.ERROR);

		try {
			testVerify();

			_validateLogEntry(logCapture, expectedLogEntry);

			if ((expectedExceptionMessage != null) ||
				(expectedLogEntry != null)) {

				Assert.fail();
			}
		}
		catch (Exception exception) {
			if (expectedExceptionMessage == null) {
				boolean advancedFileSystemStore = StringUtil.equals(
					dlStoreImpl, _DL_STORE_IMPL_ADVANCED_FILE_SYSTEM_STORE);

				Path rootDirPath;

				if (advancedFileSystemStore) {
					rootDirPath = Paths.get(_advancedFileSystemStoreRootDir);
				}
				else {
					rootDirPath = Paths.get(_fileSystemStoreRootDir);
				}

				expectedExceptionMessage = StringBundler.concat(
					advancedFileSystemStore ? "Advanced file" : "File",
					" system store directory structure ", rootDirPath,
					" is invalid");
			}

			Assert.assertEquals(
				expectedExceptionMessage, exception.getMessage());

			_validateLogEntry(logCapture, expectedLogEntry);
		}
		finally {
			logCapture.close();

			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "DL_STORE_IMPL", originalDLStoreImpl);
		}
	}

	private void _assertVerifyExceptionMessage(
			String dlStoreImpl, String expectedExceptionMessage)
		throws Exception {

		_assertVerify(dlStoreImpl, expectedExceptionMessage, null);
	}

	private void _assertVerifyLogEntry(
			String dlStoreImpl, String expectedLogEntry)
		throws Exception {

		_assertVerify(dlStoreImpl, null, expectedLogEntry);
	}

	private void _assertVerifyValidDirectory(String dlStoreImpl)
		throws Exception {

		_assertVerify(dlStoreImpl, null, null);
	}

	private void _validateLogEntry(
		LogCapture logCapture, String expectedLogEntry) {

		List<LogEntry> logEntries = logCapture.getLogEntries();

		if (expectedLogEntry == null) {
			Assert.assertEquals(logEntries.toString(), 0, logEntries.size());

			return;
		}

		Assert.assertEquals(logEntries.toString(), 1, logEntries.size());
		Assert.assertEquals(
			expectedLogEntry,
			logEntries.get(
				0
			).getMessage());
	}

	private static final String _DL_STORE_IMPL_ADVANCED_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.AdvancedFileSystemStore";

	private static final String _DL_STORE_IMPL_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.FileSystemStore";

	private static Configuration _advancedFileSystemStoreConfiguration;
	private static String _advancedFileSystemStoreRootDir;
	private static long _companyId;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private static Configuration _fileSystemStoreConfiguration;
	private static String _fileSystemStoreRootDir;
	private static boolean _originalCacheEnabled;
	private static long _repositoryId;
	private static SafeCloseable
		_upgradeDatabaseDLStorageCheckDisabledSafeCloseable;

}