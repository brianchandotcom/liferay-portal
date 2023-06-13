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
import com.liferay.batch.engine.BatchEngineTaskItemDelegateRegistry;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.ItemClassRegistry;
import com.liferay.batch.engine.configuration.BatchEngineTaskCompanyConfiguration;
import com.liferay.batch.engine.constants.BatchEngineImportTaskConstants;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemDelegateExecutor;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemDelegateExecutorFactory;
import com.liferay.batch.engine.internal.reader.BatchEngineImportTaskItemReader;
import com.liferay.batch.engine.internal.reader.BatchEngineImportTaskItemReaderBuilder;
import com.liferay.batch.engine.internal.reader.BatchEngineImportTaskItemReaderUtil;
import com.liferay.batch.engine.internal.strategy.BatchEngineImportStrategyFactory;
import com.liferay.batch.engine.internal.task.progress.BatchEngineTaskProgress;
import com.liferay.batch.engine.internal.task.progress.BatchEngineTaskProgressFactory;
import com.liferay.batch.engine.internal.util.ItemIndexThreadLocal;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskErrorLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
		SafeCloseable safeCloseable = CompanyThreadLocal.setWithSafeCloseable(
			batchEngineImportTask.getCompanyId());

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
		finally {

			// LPS-167011 Because of call to _updateBatchEngineImportTask when
			// catching a Throwable

			safeCloseable.close();
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_batchEngineTaskItemDelegateExecutorFactory =
			new BatchEngineTaskItemDelegateExecutorFactory(
				_batchEngineTaskItemDelegateRegistry, null, null, null);
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

	private BatchEngineImportTaskItemReader _getBatchEngineImportTaskItemReader(
			BatchEngineImportTask batchEngineImportTask,
			InputStream inputStream, Map<String, Serializable> parameters)
		throws Exception {

		BatchEngineImportTaskItemReaderBuilder
			batchEngineImportTaskItemReaderBuilder =
				new BatchEngineImportTaskItemReaderBuilder();

		Map<String, Serializable> fieldNameMapping =
			batchEngineImportTask.getFieldNameMapping();

		if (fieldNameMapping == null) {
			fieldNameMapping = Collections.emptyMap();
		}

		return batchEngineImportTaskItemReaderBuilder.
			batchEngineTaskContentType(
				BatchEngineTaskContentType.valueOf(
					batchEngineImportTask.getContentType())
			).csvFileColumnDelimiter(
				_getCSVFileColumnDelimiter(batchEngineImportTask.getCompanyId())
			).fieldNames(
				ListUtil.fromCollection(fieldNameMapping.keySet())
			).inputStream(
				inputStream
			).parameters(
				parameters
			).build();
	}

	private String _getCSVFileColumnDelimiter(long companyId) throws Exception {
		BatchEngineTaskCompanyConfiguration
			batchEngineTaskCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					BatchEngineTaskCompanyConfiguration.class, companyId);

		return batchEngineTaskCompanyConfiguration.csvFileColumnDelimiter();
	}

	private Map<String, Serializable> _getParameters(
		BatchEngineImportTask batchEngineImportTask) {

		Map<String, Serializable> parameters =
			batchEngineImportTask.getParameters();

		if (parameters == null) {
			parameters = new HashMap<>();
		}

		parameters.computeIfAbsent(
			"taskItemDelegateName",
			key -> batchEngineImportTask.getTaskItemDelegateName());

		return parameters;
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
				BatchEngineImportTaskConstants.
					IMPORT_STRATEGY_ON_ERROR_CONTINUE) {

			_log.error(exception);
		}
		else if (batchEngineImportTask.getImportStrategy() ==
					BatchEngineImportTaskConstants.
						IMPORT_STRATEGY_ON_ERROR_FAIL) {

			throw exception;
		}
	}

	private void _importItems(BatchEngineImportTask batchEngineImportTask)
		throws Throwable {

		Map<String, Serializable> parameters = _getParameters(
			batchEngineImportTask);

		try (BatchEngineImportTaskItemReader batchEngineImportTaskItemReader =
				_getBatchEngineImportTaskItemReader(
					batchEngineImportTask,
					_batchEngineImportTaskLocalService.openContentInputStream(
						batchEngineImportTask.getBatchEngineImportTaskId()),
					parameters)) {

			BatchEngineTaskItemDelegateExecutor
				batchEngineTaskItemDelegateExecutor =
					_batchEngineTaskItemDelegateExecutorFactory.create(
						batchEngineImportTask.getTaskItemDelegateName(),
						batchEngineImportTask.getClassName(),
						_companyLocalService.getCompany(
							batchEngineImportTask.getCompanyId()),
						parameters,
						_userLocalService.getUser(
							batchEngineImportTask.getUserId()));

			List<Object> items = new ArrayList<>();

			Class<?> itemClass = _itemClassRegistry.getItemClass(
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

		batchEngineImportTask =
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

	@Reference
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	private BatchEngineTaskItemDelegateExecutorFactory
		_batchEngineTaskItemDelegateExecutorFactory;

	@Reference
	private BatchEngineTaskItemDelegateRegistry
		_batchEngineTaskItemDelegateRegistry;

	private final BatchEngineTaskProgressFactory
		_batchEngineTaskProgressFactory = new BatchEngineTaskProgressFactory();

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private ItemClassRegistry _itemClassRegistry;

	@Reference
	private UserLocalService _userLocalService;

}