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

package com.liferay.portal.upgrade.util;

import com.liferay.petra.executor.PortalExecutorManager;
import com.liferay.portal.kernel.dao.db.DBProcess;
import com.liferay.portal.kernel.upgrade.BaseUpgradeCallable;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Miguel Pastor
 */
public class ParallelUpgradeSchemaUtil {

	public static void execute(DBProcess dbProcess, String... sqlFileNames)
		throws Exception {

		ExecutorService executorService =
			_portalExecutorManager.getPortalExecutor(
				ParallelUpgradeSchemaUtil.class.getName());

		List<Future<Void>> futures = new ArrayList<>(sqlFileNames.length);

		try {
			for (String sqlFileName : sqlFileNames) {
				futures.add(
					executorService.submit(
						new SQLExecutorUpgradeCallable(
							dbProcess, sqlFileName)));
			}

			for (Future<Void> future : futures) {
				future.get();
			}
		}
		finally {
			executorService.shutdown();
		}
	}

	private static volatile PortalExecutorManager _portalExecutorManager =
		ServiceProxyFactory.newServiceTrackedInstance(
			PortalExecutorManager.class, ParallelUpgradeSchemaUtil.class,
			"_portalExecutorManager", true);

	private static class SQLExecutorUpgradeCallable
		extends BaseUpgradeCallable<Void> {

		@Override
		protected Void doCall() throws Exception {
			try (LoggingTimer loggingTimer = new LoggingTimer(_sqlFileName)) {
				_dbProcess.runSQLTemplate(_sqlFileName, false);
			}

			return null;
		}

		private SQLExecutorUpgradeCallable(
			DBProcess dbProcess, String sqlFileName) {

			_dbProcess = dbProcess;
			_sqlFileName = sqlFileName;
		}

		private final DBProcess _dbProcess;
		private final String _sqlFileName;

	}

}