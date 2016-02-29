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

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Miguel Pastor
 */
public class ParallelSchemaUpgraderExecutor {

	public ParallelSchemaUpgraderExecutor(String... sqlFiles) {
		_sqlFiles = sqlFiles;
	}

	public void execute() throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		List<Future<Void>> futures = new ArrayList<>();

		try {
			for (int i = 0; i < _sqlFiles.length; i++) {
				futures.add(
					executorService.submit(
						new CallableSQLExecutor(_sqlFiles[i])));
			}
		}
		finally {
			executorService.shutdown();
		}

		for (Future<Void> future : futures) {
			future.get();
		}
	}

	private final String[] _sqlFiles;

	private class CallableSQLExecutor implements Callable<Void> {

		public CallableSQLExecutor(String sqlFile) {
			_sqlFile = sqlFile;
		}

		@Override
		public Void call() throws Exception {
			DB db = DBManagerUtil.getDB();

			try(LoggingTimer loggingTimer = new LoggingTimer(
				"runSQLTemplate(" + _sqlFile + ")")) {

				db.runSQLTemplate(_sqlFile, false);
			}

			return null;
		}

		private final String _sqlFile;

	}

}