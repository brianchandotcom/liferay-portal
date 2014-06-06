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

package com.liferay.portal.test;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.test.AbstractExecutionTestListener;
import com.liferay.portal.kernel.test.TestContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.log.Log4JLoggerTestUtil;
import com.liferay.portal.search.lucene.LuceneHelperUtil;
import com.liferay.portal.test.jdbc.ResetDatabaseUtil;
import com.liferay.portal.upgrade.util.Table;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.TestPropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
		restoreLuceneStores(false);

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
				backupLuceneStores(true);
			}
			else {
				restoreDLStores(true);
				restoreLuceneStores(true);
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
		backupLuceneStores(false);
	}

	protected void backupDLStores(boolean initialize) {
		String dlFileSystemStoreDirName = null;

		if (initialize) {
			dlFileSystemStoreDirName =
				SystemProperties.get(SystemProperties.TMP_DIR) +
					"/temp-init-dl-file-system-" + System.currentTimeMillis();

			_initDLFileSystemStoreDirName = dlFileSystemStoreDirName;

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

			_initDLJCRStoreDirName = dlJCRStoreDirName;

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

	protected void backupLuceneStores(boolean initialize) {
		for (long companyId : PortalInstances.getCompanyIds()) {
			String fileName = null;

			if (initialize) {
				fileName =
					SystemProperties.get(SystemProperties.TMP_DIR) +
						"/temp-init-lucene-" + companyId + "-" +
							System.currentTimeMillis();

				Runtime runtime = Runtime.getRuntime();

				runtime.addShutdownHook(new DeleteFileShutdownHook(fileName));
			}
			else {
				fileName =
					SystemProperties.get(SystemProperties.TMP_DIR) +
						"/temp-lucene-" + companyId + "-" +
							System.currentTimeMillis();
			}

			try {
				LuceneHelperUtil.dumpIndex(
					TestPropsValues.getCompanyId(),
					new FileOutputStream(fileName));
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			finally {
				if (initialize) {
					_initLuceneFileNames.put(companyId, fileName);
				}
				else {
					_luceneFileNames.put(companyId, fileName);
				}
			}
		}
	}

	protected void restoreDLStores(boolean initialize) {
		FileUtil.deltree(PropsValues.DL_STORE_FILE_SYSTEM_ROOT_DIR);

		String dlFileSystemStoreDirName = _initDLFileSystemStoreDirName;

		if (!initialize) {
			dlFileSystemStoreDirName = _dlFileSystemStoreDirName;

			_dlFileSystemStoreDirName = null;
		}

		FileUtil.move(
			new File(dlFileSystemStoreDirName),
			new File(PropsValues.DL_STORE_FILE_SYSTEM_ROOT_DIR));

		FileUtil.deltree(
			PropsUtil.get(PropsKeys.JCR_JACKRABBIT_REPOSITORY_ROOT));

		String dlJCRStoreDirName = _initDLJCRStoreDirName;

		if (!initialize) {
			dlJCRStoreDirName = _dlJCRStoreDirName;

			_dlJCRStoreDirName = null;
		}

		FileUtil.move(
			new File(dlJCRStoreDirName),
			new File(PropsUtil.get(PropsKeys.JCR_JACKRABBIT_REPOSITORY_ROOT)));
	}

	protected void restoreLuceneStores(boolean initialize) {
		Map<Long, String> luceneFileNames = _luceneFileNames;

		if (initialize) {
			luceneFileNames = _initLuceneFileNames;
		}

		for (Map.Entry<Long, String> entry : luceneFileNames.entrySet()) {
			String fileName = entry.getValue();

			try {
				LuceneHelperUtil.loadIndex(
					entry.getKey(), new FileInputStream(fileName));
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			finally {
				if (!initialize) {
					FileUtil.delete(fileName);
				}
			}
		}

		if (!initialize) {
			luceneFileNames.clear();
		}
	}

	private static String _initDLFileSystemStoreDirName;
	private static String _initDLJCRStoreDirName;
	private static Map<Long, String> _initLuceneFileNames =
		new HashMap<Long, String>();

	private String _dlFileSystemStoreDirName;
	private String _dlJCRStoreDirName;
	private Level _level;
	private Map<Long, String> _luceneFileNames = new HashMap<Long, String>();

	private static class DeleteFileShutdownHook extends Thread {

		public DeleteFileShutdownHook(String fileName) {
			_fileName = fileName;
		}

		@Override
		public void run() {
			File file = new File(_fileName);

			Queue<File> fileQueue = new LinkedList<File>();

			fileQueue.offer(file);

			while ((file = fileQueue.poll()) != null) {
				if (file.isFile()) {
					file.delete();
				}
				else if (file.isDirectory()) {
					File[] files = file.listFiles();

					if (files.length == 0) {
						file.delete();
					}
					else {
						fileQueue.addAll(Arrays.asList(files));
						fileQueue.add(file);
					}
				}
			}
		}

		private final String _fileName;

	}

}