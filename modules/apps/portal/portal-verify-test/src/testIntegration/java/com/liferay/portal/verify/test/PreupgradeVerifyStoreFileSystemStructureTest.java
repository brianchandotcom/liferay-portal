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

import org.junit.AfterClass;
import org.junit.Assert;
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
		_advancedFileSystemStoreConfiguration =
			_configurationAdmin.getConfiguration(
				"com.liferay.portal.store.file.system.configuration." +
					"AdvancedFileSystemStoreConfiguration",
				StringPool.QUESTION);

		ConfigurationTestUtil.saveConfiguration(
			_advancedFileSystemStoreConfiguration,
			HashMapDictionaryBuilder.<String, Object>put(
				"rootDir", _ROOT_DIR_ADVANCED_FILE_SYSTEM_STORE
			).build());

		_cacheEnabled = ReflectionTestUtil.getAndSetFieldValue(
			PortalInstancePool.class, "_cacheEnabled", false);
		_companyId = TestPropsValues.getCompanyId();

		_fileSystemStoreConfiguration = _configurationAdmin.getConfiguration(
			"com.liferay.portal.store.file.system.configuration." +
				"FileSystemStoreConfiguration",
			StringPool.QUESTION);

		ConfigurationTestUtil.saveConfiguration(
			_fileSystemStoreConfiguration,
			HashMapDictionaryBuilder.<String, Object>put(
				"rootDir", _ROOT_DIR_FILE_SYSTEM_STORE
			).build());

		_upgradeDatabaseDLStorageCheckDisabledSafeCloseable =
			PropsValuesTestUtil.swapWithSafeCloseable(
				"UPGRADE_DATABASE_DL_STORAGE_CHECK_DISABLED", false);
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		ConfigurationTestUtil.deleteConfiguration(
			_advancedFileSystemStoreConfiguration);

		FileUtil.deltree(_ROOT_DIR_ADVANCED_FILE_SYSTEM_STORE);

		ConfigurationTestUtil.deleteConfiguration(
			_fileSystemStoreConfiguration);

		FileUtil.deltree(_ROOT_DIR_FILE_SYSTEM_STORE);

		ReflectionTestUtil.setFieldValue(
			PortalInstancePool.class, "_cacheEnabled", _cacheEnabled);

		_upgradeDatabaseDLStorageCheckDisabledSafeCloseable.close();
	}

	@Test
	public void testVerifyAdvancedFileSystem() throws Exception {
		Path path = Paths.get(
			_ROOT_DIR_ADVANCED_FILE_SYSTEM_STORE, String.valueOf(_companyId),
			String.valueOf(_REPOSITORY_ID), "100");

		Files.createDirectories(path);

		_testVerify(
			true, null,
			StringBundler.concat(
				"File ", path, " name has more than 2 characters and no ",
				"extension in advanced file system structure"));

		Files.createDirectories(
			Paths.get(
				_ROOT_DIR_ADVANCED_FILE_SYSTEM_STORE,
				String.valueOf(_companyId), String.valueOf(_REPOSITORY_ID),
				"10", "100.afsh"));

		_testVerify(true, null, null);
	}

	@Test
	public void testVerifyFileSystem() throws Exception {
		Files.deleteIfExists(
			Paths.get(_ROOT_DIR_FILE_SYSTEM_STORE, String.valueOf(_companyId)));

		_testVerify(
			false,
			StringBundler.concat(
				"Missing directories in ",
				Paths.get(_ROOT_DIR_FILE_SYSTEM_STORE), " for companies: [",
				_companyId, "]"),
			null);

		Path path = Paths.get(
			_ROOT_DIR_FILE_SYSTEM_STORE, String.valueOf(_companyId),
			String.valueOf(_REPOSITORY_ID), "invalidFile.txt");

		Files.createDirectories(path.getParent());
		Files.createFile(path);

		_testVerify(
			false, null,
			"Unexpected file " + path + " in file system structure");






		path = Paths.get(
			_ROOT_DIR_FILE_SYSTEM_STORE, String.valueOf(_companyId),
			"invalidFile.txt");

		Files.createDirectories(path.getParent());
		Files.createFile(path);

		_testVerify(
			false, null,
			"Unexpected file " + path + " in file system structure");


		

		path = Paths.get(
			_ROOT_DIR_FILE_SYSTEM_STORE, String.valueOf(_companyId),
			String.valueOf(_REPOSITORY_ID), "100", "invalidVersionLabel");

		Files.createDirectories(path.getParent());
		Files.createFile(path);

		_testVerify(
			false, null,
			"Unexpected file " + path + " not matching version label pattern");



		path = Paths.get(
			_ROOT_DIR_FILE_SYSTEM_STORE, String.valueOf(_companyId),
			String.valueOf(_REPOSITORY_ID), "100.txt");

		Files.createDirectories(path);

		_testVerify(
			false, null,
			StringBundler.concat(
				"Unexpected file name directory ", path,
				" with extension in file system structure"));






		Files.createDirectories(
			Paths.get(
				_ROOT_DIR_FILE_SYSTEM_STORE, String.valueOf(_companyId),
				String.valueOf(_REPOSITORY_ID), "100", "1.0"));

		_testVerify(false, null, null);
	}

	@Test
	public void testVerifyFileSystemStoreFileInsteadOfCompanyIdDirectory()
		throws Exception {

		long companyId = PortalInstancePool.getCompanyIds()[0];

		Path path = Paths.get(_ROOT_DIR_FILE_SYSTEM_STORE);

		Path companyIdPath = path.resolve(String.valueOf(companyId));

		Files.createFile(companyIdPath);

		_assertVerifyExceptionMessage(
			_DL_STORE_IMPL_FILE_SYSTEM_STORE,
			companyIdPath + " is not a directory");
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
					rootDirPath = Paths.get(
						_ROOT_DIR_ADVANCED_FILE_SYSTEM_STORE);
				}
				else {
					rootDirPath = Paths.get(_ROOT_DIR_FILE_SYSTEM_STORE);
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

	private void _testVerify(
			boolean advancedFileSystemStore, String expectedExceptionMessage,
			String expectedLogEntry)
		throws Exception {

		CompanyLocalServiceUtil.forEachCompanyId(
			companyId -> {
				Files.createDirectories(
					Paths.get(
						_ROOT_DIR_ADVANCED_FILE_SYSTEM_STORE,
						String.valueOf(companyId)));
				Files.createDirectories(
					Paths.get(
						_ROOT_DIR_FILE_SYSTEM_STORE,
						String.valueOf(companyId)));
			},
			PortalInstancePool.getCompanyIds());

		try {
			System.out.println("x");
		}
		finally {
			FileUtil.deltree(_ROOT_DIR_ADVANCED_FILE_SYSTEM_STORE);
			FileUtil.deltree(_ROOT_DIR_FILE_SYSTEM_STORE);
		}
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

	private static final long _REPOSITORY_ID = RandomTestUtil.nextLong();

	private static final String _ROOT_DIR_ADVANCED_FILE_SYSTEM_STORE =
		PropsUtil.get(PropsKeys.LIFERAY_HOME) +
			"/test/store/advanced_file_system";

	private static final String _ROOT_DIR_FILE_SYSTEM_STORE =
		PropsUtil.get(PropsKeys.LIFERAY_HOME) + "/test/store/file_system";

	private static Configuration _advancedFileSystemStoreConfiguration;
	private static boolean _cacheEnabled;
	private static long _companyId;

	@Inject
	private static ConfigurationAdmin _configurationAdmin;

	private static Configuration _fileSystemStoreConfiguration;
	private static SafeCloseable
		_upgradeDatabaseDLStorageCheckDisabledSafeCloseable;

}