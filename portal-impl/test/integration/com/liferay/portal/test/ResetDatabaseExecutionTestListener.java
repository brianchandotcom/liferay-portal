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

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;

/**
 * @author Shuyang Zhou
 */
public class ResetDatabaseExecutionTestListener
	extends AbstractExecutionTestListener {

	@Override
	public void runAfterTest(TestContext testContext) {
		restoreDLStores();
		restoreLuceneStores();

		ResetDatabaseUtil.resetModifiedTables();

		Log4JLoggerTestUtil.setLoggerLevel(Table.class.getName(), _level);

		CacheRegistryUtil.clear();
		SingleVMPoolUtil.clear();
		MultiVMPoolUtil.clear();

		ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);
	}

	@Override
	public void runBeforeTest(TestContext testContext) {
		_level = Log4JLoggerTestUtil.setLoggerLevel(
			Table.class.getName(), Level.WARN);

		ResetDatabaseUtil.startRecording();

		backupDLStores();
		backupLuceneStores();
	}

	protected void backupDLStores() {
		_dlFileSystemStoreDirName =
			SystemProperties.get(SystemProperties.TMP_DIR) +
				"/temp-dl-file-system-" + System.currentTimeMillis();

		try {
			FileUtil.copyDirectory(
				new File(PropsValues.DL_STORE_FILE_SYSTEM_ROOT_DIR),
				new File(_dlFileSystemStoreDirName));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		_dlJCRStoreDirName =
			SystemProperties.get(SystemProperties.TMP_DIR) +
				"/temp-dl-jcr-" + System.currentTimeMillis();

		try {
			FileUtil.copyDirectory(
				new File(
					PropsUtil.get(PropsKeys.JCR_JACKRABBIT_REPOSITORY_ROOT)),
				new File(_dlJCRStoreDirName));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	protected void backupLuceneStores() {
		for (long companyId : PortalInstances.getCompanyIds()) {
			String fileName =
				SystemProperties.get(SystemProperties.TMP_DIR) +
					"/temp-lucene-" + companyId + "-" +
						System.currentTimeMillis();

			try {
				LuceneHelperUtil.dumpIndex(
					TestPropsValues.getCompanyId(),
					new FileOutputStream(fileName));
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			finally {
				_luceneFileNames.put(companyId, fileName);
			}
		}
	}

	protected void restoreDLStores() {
		FileUtil.deltree(PropsValues.DL_STORE_FILE_SYSTEM_ROOT_DIR);

		FileUtil.move(
			new File(_dlFileSystemStoreDirName),
			new File(PropsValues.DL_STORE_FILE_SYSTEM_ROOT_DIR));

		_dlFileSystemStoreDirName = null;

		FileUtil.deltree(
			PropsUtil.get(PropsKeys.JCR_JACKRABBIT_REPOSITORY_ROOT));

		FileUtil.move(
			new File(_dlJCRStoreDirName),
			new File(PropsUtil.get(PropsKeys.JCR_JACKRABBIT_REPOSITORY_ROOT)));

		_dlJCRStoreDirName = null;
	}

	protected void restoreLuceneStores() {
		for (Map.Entry<Long, String> entry : _luceneFileNames.entrySet()) {
			String fileName = entry.getValue();

			try {
				LuceneHelperUtil.loadIndex(
					entry.getKey(), new FileInputStream(fileName));
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			finally {
				FileUtil.delete(fileName);
			}
		}

		_luceneFileNames.clear();
	}

	private String _dlFileSystemStoreDirName;
	private String _dlJCRStoreDirName;
	private Level _level;
	private Map<Long, String> _luceneFileNames = new HashMap<Long, String>();

}