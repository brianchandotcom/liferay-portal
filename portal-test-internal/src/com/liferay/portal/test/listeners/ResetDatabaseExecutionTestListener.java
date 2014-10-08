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

package com.liferay.portal.test.listeners;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.test.AbstractExecutionTestListener;
import com.liferay.portal.kernel.test.TestContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.jdbc.ResetDatabaseUtil;
import com.liferay.portal.upgrade.util.Table;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Level;

/**
 * @author Shuyang Zhou
 */
public class ResetDatabaseExecutionTestListener
	extends AbstractExecutionTestListener {

	@Override
	public void runAfterTest(TestContext testContext) {
		restoreDLStores(false);
		restoreSearchIndices(false);

		ResetDatabaseUtil.resetModifiedTables();

		Log4JLoggerTestUtil.setLoggerLevel(Table.class.getName(), _level);

		CacheRegistryUtil.clear();
		SingleVMPoolUtil.clear();
		MultiVMPoolUtil.clear();

		ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);
	}

	@Override
	public void runBeforeClass(TestContext testContext) {
		_level = Log4JLoggerTestUtil.setLoggerLevel(
			Table.class.getName(), Level.WARN);

		try {
			if (ResetDatabaseUtil.initialize()) {
				backupDLStores(true);
				backupSearchIndices(true);
			}
			else {
				restoreDLStores(true);
				restoreSearchIndices(true);
			}
		}
		finally {
			Log4JLoggerTestUtil.setLoggerLevel(Table.class.getName(), _level);
		}
	}

	@Override
	public void runBeforeTest(TestContext testContext) {
		_level = Log4JLoggerTestUtil.setLoggerLevel(
			Table.class.getName(), Level.WARN);

		ResetDatabaseUtil.startRecording();

		backupDLStores(false);
		backupSearchIndices(false);
	}

	protected void backupDLStores(boolean initialize) {
		String dlFileSystemStoreDirName = null;

		if (initialize) {
			dlFileSystemStoreDirName =
				SystemProperties.get(SystemProperties.TMP_DIR) +
					"/temp-init-dl-file-system-" + System.currentTimeMillis();

			_initializedDLFileSystemStoreDirName = dlFileSystemStoreDirName;

			Runtime runtime = Runtime.getRuntime();

			runtime.addShutdownHook(
				new DeleteFileShutdownHook(dlFileSystemStoreDirName));
		}
		else {
			dlFileSystemStoreDirName =
				SystemProperties.get(SystemProperties.TMP_DIR) +
					"/temp-dl-file-system-" + System.currentTimeMillis();

			_dlFileSystemStoreDirName = dlFileSystemStoreDirName;
		}

		try {
			FileUtil.copyDirectory(
				new File(PropsValues.DL_STORE_FILE_SYSTEM_ROOT_DIR),
				new File(dlFileSystemStoreDirName));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		String dlJCRStoreDirName = null;

		if (initialize) {
			dlJCRStoreDirName =
				SystemProperties.get(SystemProperties.TMP_DIR) +
					"/temp-init-dl-jcr-" + System.currentTimeMillis();

			_initializedDLJCRStoreDirName = dlJCRStoreDirName;

			Runtime runtime = Runtime.getRuntime();

			runtime.addShutdownHook(
				new DeleteFileShutdownHook(dlJCRStoreDirName));
		}
		else {
			dlJCRStoreDirName =
				SystemProperties.get(SystemProperties.TMP_DIR) +
					"/temp-dl-jcr-" + System.currentTimeMillis();

			_dlJCRStoreDirName = dlJCRStoreDirName;
		}

		try {
			FileUtil.copyDirectory(
				new File(
					PropsUtil.get(PropsKeys.JCR_JACKRABBIT_REPOSITORY_ROOT)),
				new File(dlJCRStoreDirName));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	protected void backupSearchIndices(boolean initialize) {
		for (long companyId : PortalInstances.getCompanyIds()) {
			String backupName = null;

			if (initialize) {
				backupName =
					"temp-init-search-" + companyId + "-" +
						System.currentTimeMillis();
			}
			else {
				backupName =
					"temp-search-" + companyId + "-" +
						System.currentTimeMillis();
			}

			try {
				String backupFileName = SearchEngineUtil.backup(
					companyId, SearchEngineUtil.SYSTEM_ENGINE_ID, backupName);

				if (initialize) {
					Runtime runtime = Runtime.getRuntime();

					runtime.addShutdownHook(
						new DeleteFileShutdownHook(backupFileName));
				}
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			finally {
				if (initialize) {
					_initializedIndexNames.put(companyId, backupName);
				}
				else {
					_indexNames.put(companyId, backupName);
				}
			}
		}
	}

	protected void restoreDLStores(boolean initialize) {
		FileUtil.deltree(PropsValues.DL_STORE_FILE_SYSTEM_ROOT_DIR);

		String dlFileSystemStoreDirName = _initializedDLFileSystemStoreDirName;

		if (!initialize) {
			dlFileSystemStoreDirName = _dlFileSystemStoreDirName;

			_dlFileSystemStoreDirName = null;
		}

		FileUtil.move(
			new File(dlFileSystemStoreDirName),
			new File(PropsValues.DL_STORE_FILE_SYSTEM_ROOT_DIR));

		FileUtil.deltree(
			PropsUtil.get(PropsKeys.JCR_JACKRABBIT_REPOSITORY_ROOT));

		String dlJCRStoreDirName = _initializedDLJCRStoreDirName;

		if (!initialize) {
			dlJCRStoreDirName = _dlJCRStoreDirName;

			_dlJCRStoreDirName = null;
		}

		FileUtil.move(
			new File(dlJCRStoreDirName),
			new File(PropsUtil.get(PropsKeys.JCR_JACKRABBIT_REPOSITORY_ROOT)));
	}

	protected void restoreSearchIndices(boolean initialize) {
		Map<Long, String> backupFileNames = _indexNames;

		if (initialize) {
			backupFileNames = _initializedIndexNames;
		}

		for (Map.Entry<Long, String> entry : backupFileNames.entrySet()) {
			String backupFileName = entry.getValue();

			try {
				SearchEngineUtil.restore(entry.getKey(), backupFileName);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			finally {
				if (!initialize) {
					try {
						SearchEngineUtil.removeBackup(
							entry.getKey(), backupFileName);
					}
					catch (SearchException e) {
						if (_log.isInfoEnabled()) {
							_log.info("Unable to remove backup", e);
						}
					}
				}
			}
		}

		if (!initialize) {
			backupFileNames.clear();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResetDatabaseExecutionTestListener.class);

	private static String _initializedDLFileSystemStoreDirName;
	private static String _initializedDLJCRStoreDirName;
	private static final Map<Long, String> _initializedIndexNames =
		new HashMap<Long, String>();

	private String _dlFileSystemStoreDirName;
	private String _dlJCRStoreDirName;
	private final Map<Long, String> _indexNames = new HashMap<Long, String>();
	private Level _level;

	private class DeleteFileShutdownHook extends Thread {

		public DeleteFileShutdownHook(String fileName) {
			_fileName = fileName;
		}

		@Override
		public void run() {
			File file = new File(_fileName);

			Queue<File> queue = new LinkedList<File>();

			queue.offer(file);

			while ((file = queue.poll()) != null) {
				if (file.isFile()) {
					file.delete();
				}
				else if (file.isDirectory()) {
					File[] files = file.listFiles();

					if (files.length == 0) {
						file.delete();
					}
					else {
						queue.addAll(Arrays.asList(files));
						queue.add(file);
					}
				}
			}
		}

		private final String _fileName;

	}

}