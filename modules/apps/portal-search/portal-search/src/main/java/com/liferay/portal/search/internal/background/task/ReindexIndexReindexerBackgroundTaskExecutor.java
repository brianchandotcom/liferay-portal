/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.background.task;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.spi.reindexer.IndexReindexer;
import com.liferay.portal.search.spi.reindexer.IndexReindexerRegistry;

import java.io.Serializable;

import java.util.Collection;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gustavo Lima
 */
@Component(
	property = "background.task.executor.class.name=com.liferay.portal.search.internal.background.task.ReindexIndexReindexerBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class ReindexIndexReindexerBackgroundTaskExecutor
	extends BaseReindexBackgroundTaskExecutor {

	public ReindexIndexReindexerBackgroundTaskExecutor() {
		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_TASK_NAME);
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public String generateLockKey(BackgroundTask backgroundTask) {
		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		String className = (String)taskContextMap.get("className");

		if (Validator.isNotNull(className)) {
			return className;
		}

		return super.generateLockKey(backgroundTask);
	}

	@Override
	protected void reindex(
			String className, long[] companyIds, String executionMode)
		throws Exception {

		if (Validator.isBlank(className)) {
			Collection<IndexReindexer> indexReindexers =
				_indexReindexerRegistry.getIndexReindexers();

			for (IndexReindexer indexReindexer : indexReindexers) {
				_reindex(className, companyIds, executionMode, indexReindexer);
			}
		}
		else {
			IndexReindexer indexReindexer =
				_indexReindexerRegistry.getIndexReindexer(className);

			if (indexReindexer == null) {
				return;
			}

			_reindex(className, companyIds, executionMode, indexReindexer);
		}
	}

	private void _reindex(
			String className, long[] companyIds, String executionMode,
			IndexReindexer indexReindexer)
		throws Exception {

		for (long companyId : companyIds) {
			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Start reindexing company ", companyId,
						" for class name ", className, " with execution mode ",
						executionMode));
			}

			indexReindexer.reindex(new long[] {companyId}, executionMode);

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Finished reindexing company ", companyId,
						" for class name ", className, " with execution mode ",
						executionMode));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ReindexIndexReindexerBackgroundTaskExecutor.class);

	@Reference
	private IndexReindexerRegistry _indexReindexerRegistry;

}