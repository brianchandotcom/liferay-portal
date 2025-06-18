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
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.verify.PreupgradeVerifyFileSystemStoreStructure;
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
public class PreupgradeVerifyFileSystemStoreStructureTest
	extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_companyId = TestPropsValues.getCompanyId();

		_repositoryId = RandomTestUtil.nextLong();

		_advancedFileSystemStoreRootDir =
			_props.get(PropsKeys.LIFERAY_HOME) +
				"/test/store/advanced_file_system";

		_advancedFileSystemStoreConfiguration =
			_configurationAdmin.getConfiguration(
				"com.liferay.portal.store.file.system.configuration." +
					"AdvancedFileSystemStoreConfiguration",
				StringPool.QUESTION);

		ConfigurationTestUtil.saveConfiguration(
			_advancedFileSystemStoreConfiguration,
			HashMapDictionaryBuilder.<String, Object>put(
				"rootDir", _advancedFileSystemStoreRootDir
			).build());

		_fileSystemStoreRootDir =
			_props.get(PropsKeys.LIFERAY_HOME) + "/test/store/file_system";

		_fileSystemStoreConfiguration = _configurationAdmin.getConfiguration(
			"com.liferay.portal.store.file.system.configuration." +
				"FileSystemStoreConfiguration",
			StringPool.QUESTION);

		ConfigurationTestUtil.saveConfiguration(
			_fileSystemStoreConfiguration,
			HashMapDictionaryBuilder.<String, Object>put(
				"rootDir", _fileSystemStoreRootDir
			).build());

		_originalCacheEnabled = ReflectionTestUtil.getAndSetFieldValue(
			PortalInstancePool.class, "_cacheEnabled", false);

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
	public void testAdvancedFileSystemStoreWithLongDirectoryNameWithoutExtension()
		throws Exception {

		Path directoryWithoutExtension = Paths.get(
			_advancedFileSystemStoreRootDir, String.valueOf(_companyId),
			String.valueOf(_repositoryId), "100");

		String expectedLogEntry =
			"Found directory with name longer than 2 without extension in " +
				"advanced file system pattern directory: " +
					directoryWithoutExtension;

		_assertVerify(
			_ADVANCED_FILE_SYSTEM_STORE, directoryWithoutExtension, null, null,
			1, expectedLogEntry);
	}

	@Test
	public void testAdvancedFileSystemStoreWithValidStructure()
		throws Exception {

		Path validDirectory = Paths.get(
			_advancedFileSystemStoreRootDir, String.valueOf(_companyId),
			String.valueOf(_repositoryId), "10", "100.afsh");

		_assertVerify(
			_ADVANCED_FILE_SYSTEM_STORE, validDirectory, null, null, 0);
	}

	@Test
	public void testFileSystemStoreWithDirectoryContainingExtension()
		throws Exception {

		Path directoryWithExtension = Paths.get(
			_fileSystemStoreRootDir, String.valueOf(_companyId),
			String.valueOf(_repositoryId), "100.txt");

		String expectedLogEntry =
			"Found directory with extension in basic file system pattern (no " +
				"extensions expected): " + directoryWithExtension;

		_assertVerify(
			_FILE_SYSTEM_STORE, directoryWithExtension, null, null, 1,
			expectedLogEntry);
	}

	@Test
	public void testFileSystemStoreWithFileInsteadOfCompanyIdDirectory()
		throws Exception {

		Path fileSystemStoreRootPath = Paths.get(_fileSystemStoreRootDir);

		Path invalidFilePath = fileSystemStoreRootPath.resolve(
			"invalidFile.txt");

		String expectedExceptionMessage =
			invalidFilePath + " is not a directory";

		_assertVerify(
			_FILE_SYSTEM_STORE, null, invalidFilePath, expectedExceptionMessage,
			0);
	}

	@Test
	public void testFileSystemStoreWithFileInsteadOfFolderIdDirectory()
		throws Exception {

		Path companyIdPath = Paths.get(
			_fileSystemStoreRootDir, String.valueOf(_companyId));

		Path invalidFilePath = companyIdPath.resolve("invalidFile.txt");

		String expectedLogEntry =
			"Found file in basic file system pattern directory (only " +
				"directories expected): " + invalidFilePath;

		_assertVerify(
			_FILE_SYSTEM_STORE, null, invalidFilePath, null, 1,
			expectedLogEntry);
	}

	@Test
	public void testFileSystemStoreWithFileInsteadOfNumericFileEntryNameDirectory()
		throws Exception {

		Path folderIdPath = Paths.get(
			_fileSystemStoreRootDir, String.valueOf(_companyId),
			String.valueOf(_repositoryId));

		Path invalidFilePath = folderIdPath.resolve("invalidFile.txt");

		String expectedLogEntry =
			"Found file in basic file system pattern directory (only " +
				"directories expected): " + invalidFilePath;

		_assertVerify(
			_FILE_SYSTEM_STORE, null, invalidFilePath, null, 1,
			expectedLogEntry);
	}

	@Test
	public void testFileSystemStoreWithMissingCompanyFolder() throws Exception {
		Files.deleteIfExists(
			Paths.get(_fileSystemStoreRootDir, String.valueOf(_companyId)));

		String expectedExceptionMessage = StringBundler.concat(
			"Missing folders for companies: [", _companyId,
			"]. Please create the corresponding directories in ",
			Paths.get(_fileSystemStoreRootDir));

		_assertVerify(
			_FILE_SYSTEM_STORE, null, null, expectedExceptionMessage, 0);
	}

	@Test
	public void testFileSystemStoreWithMissingValidVersionFiles()
		throws Exception {

		Path numericFileEntryNamePath = Paths.get(
			_fileSystemStoreRootDir, String.valueOf(_companyId),
			String.valueOf(_repositoryId), "100");

		Path invalidVersionFile = numericFileEntryNamePath.resolve(
			"invalidVersion");

		String expectedLogEntry1 =
			"Found file that does not match version pattern (expected " +
				"\\d+\\.\\d+.*): " + invalidVersionFile;

		String expectedLogEntry2 =
			"Directory does not contain valid version files as expected in " +
				"basic file system pattern: " + numericFileEntryNamePath;

		_assertVerify(
			_FILE_SYSTEM_STORE, null, invalidVersionFile, null, 2,
			expectedLogEntry1, expectedLogEntry2);
	}

	@Test
	public void testFileSystemStoreWithValidStructure() throws Exception {
		Path validDirectory = Paths.get(
			_fileSystemStoreRootDir, String.valueOf(_companyId),
			String.valueOf(_repositoryId), "100", "1.0");

		_assertVerify(_FILE_SYSTEM_STORE, validDirectory, null, null, 0);
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new PreupgradeVerifyFileSystemStoreStructure();
	}

	private void _assertVerify(
			String storeImpl, Path directoryPath, Path filePath,
			String expectedExceptionMessage, int expectedLogEntriesCount,
			String... expectedLogEntries)
		throws Exception {

		String originalDLStoreImpl = ReflectionTestUtil.getAndSetFieldValue(
			PropsValues.class, "DL_STORE_IMPL", storeImpl);

		LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
			PreupgradeVerifyFileSystemStoreStructure.class.getName(),
			LoggerTestUtil.ERROR);

		try {
			if (directoryPath != null) {
				Files.createDirectories(directoryPath);
			}

			if (filePath != null) {
				Files.createDirectories(filePath.getParent());
				Files.createFile(filePath);
			}

			testVerify();

			_validateLogEntries(
				logCapture, expectedLogEntriesCount, expectedLogEntries);

			if (expectedLogEntries.length > 0) {
				Assert.fail();
			}
		}
		catch (Exception exception) {
			if (expectedExceptionMessage == null) {
				String expectedType;
				Path rootDirPath;

				if (_ADVANCED_FILE_SYSTEM_STORE.equals(storeImpl)) {
					expectedType = "AdvancedFileSystemStore";
					rootDirPath = Paths.get(_advancedFileSystemStoreRootDir);
				}
				else {
					expectedType = "FileSystemStore";
					rootDirPath = Paths.get(_fileSystemStoreRootDir);
				}

				expectedExceptionMessage = StringBundler.concat(
					"File system store directory structure mismatch. Expected ",
					expectedType, " structure but found invalid structure in: ",
					rootDirPath);
			}

			Assert.assertEquals(
				expectedExceptionMessage, exception.getMessage());

			_validateLogEntries(
				logCapture, expectedLogEntriesCount, expectedLogEntries);
		}
		finally {
			logCapture.close();

			ReflectionTestUtil.setFieldValue(
				PropsValues.class, "DL_STORE_IMPL", originalDLStoreImpl);
		}
	}

	private void _validateLogEntries(
		LogCapture logCapture, int expectedLogEntriesCount,
		String... expectedLogEntries) {

		List<LogEntry> logEntries = logCapture.getLogEntries();

		Assert.assertEquals(
			logEntries.toString(), expectedLogEntriesCount, logEntries.size());

		if (expectedLogEntriesCount > 0) {
			for (int i = 0; i < expectedLogEntries.length; i++) {
				Assert.assertEquals(
					expectedLogEntries[i],
					logEntries.get(
						i
					).getMessage());
			}
		}
	}

	private static final String _ADVANCED_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.AdvancedFileSystemStore";

	private static final String _FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.FileSystemStore";

	private static Configuration _advancedFileSystemStoreConfiguration;
	private static String _advancedFileSystemStoreRootDir;
	private static long _companyId;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private static Configuration _fileSystemStoreConfiguration;
	private static String _fileSystemStoreRootDir;
	private static boolean _originalCacheEnabled;

	@Inject
	private static Props _props;

	private static long _repositoryId;
	private static SafeCloseable
		_upgradeDatabaseDLStorageCheckDisabledSafeCloseable;

}