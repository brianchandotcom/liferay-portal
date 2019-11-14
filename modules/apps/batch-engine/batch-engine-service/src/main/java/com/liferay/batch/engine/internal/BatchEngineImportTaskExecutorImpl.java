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
import com.liferay.batch.engine.configuration.BatchEngineImportTaskConfiguration;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemResourceDelegate;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemResourceDelegateFactory;
import com.liferay.batch.engine.internal.reader.BatchEngineImportTaskItemReader;
import com.liferay.batch.engine.internal.reader.BatchEngineImportTaskItemReaderFactory;
import com.liferay.batch.engine.internal.reader.BatchEngineImportTaskItemReaderUtil;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.ArrayList;
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
@Component(
	configurationPid = "com.liferay.batch.engine.configuration.BatchEngineImportTaskConfiguration",
	service = BatchEngineImportTaskExecutor.class
)
public class BatchEngineImportTaskExecutorImpl
	implements BatchEngineImportTaskExecutor {

	@Override
	public void execute(BatchEngineImportTask batchEngineImportTask) {
		try {
			batchEngineImportTask.setExecuteStatus(
				BatchEngineTaskExecuteStatus.STARTED.toString());
			batchEngineImportTask.setStartTime(new Date());

			_batchEngineImportTaskLocalService.updateBatchEngineImportTask(
				batchEngineImportTask);

			BatchEngineTaskExecutorUtil.execute(
				() -> _importItems(batchEngineImportTask),
				_userLocalService.getUser(batchEngineImportTask.getUserId()));

			_updateBatchEngineImportTask(
				BatchEngineTaskExecuteStatus.COMPLETED, batchEngineImportTask,
				null);
		}
		catch (Throwable t) {
			_log.error(
				"Unable to update batch engine import task " +
					batchEngineImportTask,
				t);

			_updateBatchEngineImportTask(
				BatchEngineTaskExecuteStatus.FAILED, batchEngineImportTask,
				t.getMessage());
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		BatchEngineImportTaskConfiguration batchEngineImportTaskConfiguration =
			ConfigurableUtil.createConfigurable(
				BatchEngineImportTaskConfiguration.class, properties);

		_batchEngineImportTaskItemReaderFactory =
			new BatchEngineImportTaskItemReaderFactory(
				GetterUtil.getString(
					batchEngineImportTaskConfiguration.csvFileColumnDelimiter(),
					StringPool.COMMA));

		_batchEngineTaskItemResourceDelegateFactory =
			new BatchEngineTaskItemResourceDelegateFactory(
				_batchEngineTaskMethodRegistry, _companyLocalService,
				_userLocalService);
	}

	private void _commitItems(
			BatchEngineImportTask batchEngineImportTask,
			BatchEngineTaskItemResourceDelegate
				batchEngineTaskItemResourceDelegate,
			List<Object> items)
		throws Throwable {

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				batchEngineTaskItemResourceDelegate.addItems(items);

				_batchEngineImportTaskLocalService.updateBatchEngineImportTask(
					batchEngineImportTask);

				return null;
			});
	}

	private void _importItems(BatchEngineImportTask batchEngineImportTask)
		throws Throwable {

		try (BatchEngineImportTaskItemReader batchEngineImportTaskItemReader =
				_batchEngineImportTaskItemReaderFactory.create(
					BatchEngineTaskContentType.valueOf(
						batchEngineImportTask.getContentType()),
					_batchEngineImportTaskLocalService.openContentInputStream(
						batchEngineImportTask.getBatchEngineImportTaskId()));
			BatchEngineTaskItemResourceDelegate
				batchEngineTaskItemResourceDelegate =
					_batchEngineTaskItemResourceDelegateFactory.create(
						BatchEngineTaskOperation.valueOf(
							batchEngineImportTask.getOperation()),
						batchEngineImportTask.getClassName(),
						batchEngineImportTask.getCompanyId(),
						batchEngineImportTask.getParameters(),
						batchEngineImportTask.getUserId(),
						batchEngineImportTask.getVersion())) {

			List<Object> items = new ArrayList<>();

			Class<?> itemClass = _batchEngineTaskMethodRegistry.getItemClass(
				batchEngineImportTask.getClassName());

			Map<String, Object> fieldNameValueMap = null;

			while ((fieldNameValueMap =
						batchEngineImportTaskItemReader.read()) != null) {

				if (Thread.interrupted()) {
					throw new InterruptedException();
				}

				items.add(
					BatchEngineImportTaskItemReaderUtil.convertValue(
						itemClass,
						BatchEngineImportTaskItemReaderUtil.mapFieldNames(
							batchEngineImportTask.getFieldNameMapping(),
							fieldNameValueMap)));

				if (items.size() == batchEngineImportTask.getBatchSize()) {
					_commitItems(
						batchEngineImportTask,
						batchEngineTaskItemResourceDelegate, items);

					items.clear();
				}
			}

			if (!items.isEmpty()) {
				_commitItems(
					batchEngineImportTask, batchEngineTaskItemResourceDelegate,
					items);
			}
		}
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

	private BatchEngineImportTaskItemReaderFactory
		_batchEngineImportTaskItemReaderFactory;

	@Reference
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	private BatchEngineTaskItemResourceDelegateFactory
		_batchEngineTaskItemResourceDelegateFactory;

	@Reference
	private BatchEngineTaskMethodRegistry _batchEngineTaskMethodRegistry;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private UserLocalService _userLocalService;

}