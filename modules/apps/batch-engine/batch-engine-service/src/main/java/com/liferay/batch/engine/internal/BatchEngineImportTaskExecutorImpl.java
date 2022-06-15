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

package com.liferay.batch.engine.internal;

import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.configuration.BatchEngineTaskCompanyConfiguration;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemDelegateExecutor;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemDelegateExecutorFactory;
import com.liferay.batch.engine.internal.reader.BatchEngineImportTaskItemReader;
import com.liferay.batch.engine.internal.reader.BatchEngineImportTaskItemReaderFactory;
import com.liferay.batch.engine.internal.reader.BatchEngineImportTaskItemReaderUtil;
import com.liferay.batch.engine.internal.strategy.BatchEngineImportStrategyFactory;
import com.liferay.batch.engine.internal.task.progress.BatchEngineTaskProgress;
import com.liferay.batch.engine.internal.task.progress.BatchEngineTaskProgressFactory;
import com.liferay.batch.engine.internal.util.ItemIndexThreadLocal;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskErrorLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(service = BatchEngineImportTaskExecutor.class)
public class BatchEngineImportTaskExecutorImpl
	implements BatchEngineImportTaskExecutor {

	@Override
	public void execute(BatchEngineImportTask batchEngineImportTask) {
		try {
			batchEngineImportTask.setExecuteStatus(
				BatchEngineTaskExecuteStatus.STARTED.toString());
			batchEngineImportTask.setStartTime(new Date());

			BatchEngineTaskProgress batchEngineTaskProgress =
				_batchEngineTaskProgressFactory.create(
					BatchEngineTaskContentType.valueOf(
						batchEngineImportTask.getContentType()));

			batchEngineImportTask.setTotalItemsCount(
				batchEngineTaskProgress.getTotalItemsCount(
					_batchEngineImportTaskLocalService.openContentInputStream(
						batchEngineImportTask.getBatchEngineImportTaskId())));

			_batchEngineImportTaskLocalService.updateBatchEngineImportTask(
				batchEngineImportTask);

			BatchEngineTaskExecutorUtil.execute(
				batchEngineImportTask.getCompanyId(),
				() -> _importItems(batchEngineImportTask),
				_userLocalService.getUser(batchEngineImportTask.getUserId()));

			_updateBatchEngineImportTask(
				BatchEngineTaskExecuteStatus.COMPLETED, batchEngineImportTask,
				null);
		}
		catch (Throwable throwable) {
			_log.error(
				"Unable to update batch engine import task " +
					batchEngineImportTask,
				throwable);

			_updateBatchEngineImportTask(
				BatchEngineTaskExecuteStatus.FAILED, batchEngineImportTask,
				throwable.toString());
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_batchEngineTaskItemDelegateExecutorFactory =
			new BatchEngineTaskItemDelegateExecutorFactory(
				_batchEngineTaskMethodRegistry, null, null, null);
	}

	private void _commitItems(
			BatchEngineImportTask batchEngineImportTask,
			BatchEngineTaskItemDelegateExecutor
				batchEngineTaskItemDelegateExecutor,
			List<Object> items, int processedItemsCount)
		throws Throwable {

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				batchEngineTaskItemDelegateExecutor.saveItems(
					_batchEngineImportStrategyFactory.create(
						batchEngineImportTask),
					BatchEngineTaskOperation.valueOf(
						batchEngineImportTask.getOperation()),
					items);

				batchEngineImportTask.setProcessedItemsCount(
					processedItemsCount);

				_batchEngineImportTaskLocalService.updateBatchEngineImportTask(
					batchEngineImportTask);

				return null;
			});
	}

	private String _getCSVFileColumnDelimiter(long companyId)
		throws ConfigurationException {

		BatchEngineTaskCompanyConfiguration
			batchEngineTaskCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					BatchEngineTaskCompanyConfiguration.class, companyId);

		return batchEngineTaskCompanyConfiguration.csvFileColumnDelimiter();
	}

	private void _handleException(
			BatchEngineImportTask batchEngineImportTask, Exception exception,
			int processedItemsCount)
		throws Exception {

		_batchEngineImportTaskErrorLocalService.addBatchEngineImportTaskError(
			batchEngineImportTask.getCompanyId(),
			batchEngineImportTask.getUserId(),
			batchEngineImportTask.getBatchEngineImportTaskId(), null,
			processedItemsCount, exception.toString());

		if (batchEngineImportTask.getImportStrategy() ==
				BatchEngineImportTaskConstants.IMPORT_STRATEGY_ON_ERROR_FAIL) {

			throw exception;
		}
	}

	private void _importItems(BatchEngineImportTask batchEngineImportTask)
		throws Throwable {

		Map<String, Serializable> parameters =
			batchEngineImportTask.getParameters();

		if (parameters == null) {
			parameters = Collections.emptyMap();
		}

		try (BatchEngineImportTaskItemReader batchEngineImportTaskItemReader =
				_batchEngineImportTaskItemReaderFactory.create(
					BatchEngineTaskContentType.valueOf(
						batchEngineImportTask.getContentType()),
					GetterUtil.getString(
						_getCSVFileColumnDelimiter(
							batchEngineImportTask.getCompanyId()),
						StringPool.COMMA),
					_batchEngineImportTaskLocalService.openContentInputStream(
						batchEngineImportTask.getBatchEngineImportTaskId()),
					parameters);
			BatchEngineTaskItemDelegateExecutor
				batchEngineTaskItemDelegateExecutor =
					_batchEngineTaskItemDelegateExecutorFactory.create(
						batchEngineImportTask.getTaskItemDelegateName(),
						batchEngineImportTask.getClassName(),
						_companyLocalService.getCompany(
							batchEngineImportTask.getCompanyId()),
						parameters,
						_userLocalService.getUser(
							batchEngineImportTask.getUserId()))) {

			List<Object> items = new ArrayList<>();

			Class<?> itemClass = _batchEngineTaskMethodRegistry.getItemClass(
				batchEngineImportTask.getClassName());

			int processedItemsCount = 0;

			while (true) {
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}

				try {
					Object item = _readItem(
						batchEngineImportTaskItemReader,
						batchEngineImportTask.getFieldNameMapping(), itemClass);

					if (item == null) {
						break;
					}

					items.add(item);

					processedItemsCount++;

					ItemIndexThreadLocal.put(item, processedItemsCount);
				}
				catch (Exception exception) {
					processedItemsCount++;

					_handleException(
						batchEngineImportTask, exception, processedItemsCount);
				}

				if (items.size() == batchEngineImportTask.getBatchSize()) {
					_commitItems(
						batchEngineImportTask,
						batchEngineTaskItemDelegateExecutor, items,
						processedItemsCount);

					items.clear();

					ItemIndexThreadLocal.remove();
				}
			}

			if (!items.isEmpty()) {
				_commitItems(
					batchEngineImportTask, batchEngineTaskItemDelegateExecutor,
					items, processedItemsCount);
			}
		}
	}

	private Object _readItem(
			BatchEngineImportTaskItemReader batchEngineImportTaskItemReader,
			Map<String, Serializable> fieldNameMapping, Class<?> itemClass)
		throws Exception {

		Map<String, Object> fieldNameValueMap =
			batchEngineImportTaskItemReader.read();

		if (fieldNameValueMap == null) {
			return null;
		}

		return BatchEngineImportTaskItemReaderUtil.convertValue(
			itemClass,
			BatchEngineImportTaskItemReaderUtil.mapFieldNames(
				fieldNameMapping, fieldNameValueMap));
	}

	private void _updateBatchEngineImportTask(
		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus,
		BatchEngineImportTask batchEngineImportTask, String errorMessage) {

		batchEngineImportTask.setEndTime(new Date());
		batchEngineImportTask.setErrorMessage(errorMessage);
		batchEngineImportTask.setExecuteStatus(
			batchEngineTaskExecuteStatus.toString());

		_batchEngineImportTaskLocalService.updateBatchEngineImportTask(
			batchEngineImportTask);

		BatchEngineTaskCallbackUtil.sendCallback(
			batchEngineImportTask.getCallbackURL(),
			batchEngineImportTask.getExecuteStatus(),
			batchEngineImportTask.getBatchEngineImportTaskId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineImportTaskExecutorImpl.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRES_NEW, new Class<?>[] {Exception.class});

	private final BatchEngineImportStrategyFactory
		_batchEngineImportStrategyFactory =
			new BatchEngineImportStrategyFactory();

	@Reference
	private BatchEngineImportTaskErrorLocalService
		_batchEngineImportTaskErrorLocalService;

	private final BatchEngineImportTaskItemReaderFactory
		_batchEngineImportTaskItemReaderFactory =
			new BatchEngineImportTaskItemReaderFactory();

	@Reference
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	private BatchEngineTaskItemDelegateExecutorFactory
		_batchEngineTaskItemDelegateExecutorFactory;

	@Reference
	private BatchEngineTaskMethodRegistry _batchEngineTaskMethodRegistry;

	private final BatchEngineTaskProgressFactory
		_batchEngineTaskProgressFactory = new BatchEngineTaskProgressFactory();

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private UserLocalService _userLocalService;

}