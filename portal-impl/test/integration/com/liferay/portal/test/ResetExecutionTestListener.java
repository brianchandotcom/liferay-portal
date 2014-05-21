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
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.log.test.Log4JLoggerTestUtil;
import com.liferay.portal.search.lucene.LuceneHelperUtil;
import com.liferay.portal.test.jdbc.ResetDatabaseUtil;
import com.liferay.portal.upgrade.util.Table;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.test.TestPropsValues;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;

/**
 * @author Shuyang Zhou
 */
public class ResetExecutionTestListener extends AbstractExecutionTestListener {

	@Override
	public void runAfterTest(TestContext testContext) {
		reloadLuceneStores();

		ResetDatabaseUtil.resetTables();

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

		ResetDatabaseUtil.startRecordTables();

		dumpLuceneStores();
	}

	protected void dumpLuceneStores() {
		for (long companyId : PortalInstances.getCompanyIds()) {
			String luceneDumpFileName =
				SystemProperties.get(SystemProperties.TMP_DIR) +
					"/temp-lucene-" + companyId + "-" +
						System.currentTimeMillis();

			try {
				LuceneHelperUtil.dumpIndex(
					TestPropsValues.getCompanyId(),
					new FileOutputStream(luceneDumpFileName));
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			finally {
				_luceneDumpFileNames.put(companyId, luceneDumpFileName);
			}
		}
	}

	protected void reloadLuceneStores() {
		for (Map.Entry<Long, String> entry : _luceneDumpFileNames.entrySet()) {
			String luceneDumpFileName = entry.getValue();

			try {
				LuceneHelperUtil.loadIndex(
					entry.getKey(), new FileInputStream(luceneDumpFileName));
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
			finally {
				FileUtil.delete(luceneDumpFileName);
			}
		}
	}

	private Level _level;
	private Map<Long, String> _luceneDumpFileNames =
		new HashMap<Long, String>();

}