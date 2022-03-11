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
import com.liferay.batch.engine.configuration.BatchEngineTaskConfiguration;
import com.liferay.batch.engine.constants.BatchEnginePortletKeys;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemDelegateExecutor;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemDelegateExecutorFactory;
import com.liferay.batch.engine.internal.notification.BatchEngineNotificationSender;
import com.liferay.batch.engine.internal.reader.BatchEngineImportTaskItemReader;
import com.liferay.batch.engine.internal.reader.BatchEngineImportTaskItemReaderFactory;
import com.liferay.batch.engine.internal.reader.BatchEngineImportTaskItemReaderUtil;
import com.liferay.batch.engine.internal.strategy.BatchEngineImportStrategyFactory;
import com.liferay.batch.engine.internal.task.progress.BatchEngineTaskProgress;
import com.liferay.batch.engine.internal.task.progress.BatchEngineTaskProgressFactory;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineImportTaskErrorLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
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
@Component(
	configurationPid = "com.liferay.batch.engine.configuration.BatchEngineTaskConfiguration",
	service = BatchEngineImportTaskExecutor.class
)
public class BatchEngineImportTaskExecutorImpl
	extends BatchEngineNotificationSender
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
				() -> _importItems(batchEngineImportTask),
				_userLocalService.getUser(batchEngineImportTask.getUserId()));

			_updateBatchEngineImportTask(
				BatchEngineTaskExecuteStatus.COMPLETED, batchEngineImportTask,
				null);

			sendUserNotificationEvents(
				batchEngineImportTask.getUserId(),
				BatchEnginePortletKeys.BATCH_ENGINE,
				UserNotificationDeliveryConstants.TYPE_WEBSITE,
				getNotificationEventJSONObject(
					BatchEngineTaskExecuteStatus.COMPLETED,
					batchEngineImportTask.getClassName()));
		}
		catch (Throwable throwable) {
			_log.error(
				"Unable to update batch engine import task " +
					batchEngineImportTask,
				throwable);

			_updateBatchEngineImportTask(
				BatchEngineTaskExecuteStatus.FAILED, batchEngineImportTask,
				throwable.getMessage());

			sendUserNotificationEvents(
				batchEngineImportTask.getUserId(),
				BatchEnginePortletKeys.BATCH_ENGINE,
				UserNotificationDeliveryConstants.TYPE_WEBSITE,
				getNotificationEventJSONObject(
					BatchEngineTaskExecuteStatus.FAILED,
					batchEngineImportTask.getClassName()));
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		BatchEngineTaskConfiguration batchEngineTaskConfiguration =
			ConfigurableUtil.createConfigurable(
				BatchEngineTaskConfiguration.class, properties);

		_batchEngineImportTaskItemReaderFactory =
			new BatchEngineImportTaskItemReaderFactory(
				GetterUtil.getString(
					batchEngineTaskConfiguration.csvFileColumnDelimiter(),
					StringPool.COMMA));

		_batchEngineTaskProgressFactory = new BatchEngineTaskProgressFactory();

		_batchEngineTaskItemDelegateExecutorFactory =
			new BatchEngineTaskItemDelegateExecutorFactory(
				_batchEngineTaskMethodRegistry, null, null, null);

		_batchEngineImportStrategyFactory = new BatchEngineImportStrategyFactory();

		setUserNotificationEventLocalService(
			_userNotificationEventLocalService);
	}

	@Override
	protected String getTaskType() {
		return "import";
	}

	protected JSONObject populateNotificationEventJSONObject(String className) {
		JSONObject notificationEventJSONObject =
			JSONFactoryUtil.createJSONObject();

		return notificationEventJSONObject.put(
			"batchEngineTaskType", "import"
		).put(
			"className", className
		);
	}

	private void _commitItems(
			BatchEngineImportTask batchEngineImportTask,
			BatchEngineTaskItemDelegateExecutor
				batchEngineTaskItemDelegateExecutor,
			List<Object> items)
		throws Throwable {

		TransactionInvokerUtil.invoke(
			_transactionConfig,
			() -> {
				batchEngineTaskItemDelegateExecutor.saveItems(
					BatchEngineTaskOperation.valueOf(
						batchEngineImportTask.getOperation()),
					_batchEngineImportStrategyFactory.create(batchEngineImportTask),
					items);

				batchEngineImportTask.setProcessedItemsCount(
					batchEngineImportTask.getProcessedItemsCount() +
						items.size());

				_batchEngineImportTaskLocalService.updateBatchEngineImportTask(
					batchEngineImportTask);

				return null;
			});
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
						batchEngineTaskItemDelegateExecutor, items);

					items.clear();
				}
			}

			if (!items.isEmpty()) {
				_commitItems(
					batchEngineImportTask, batchEngineTaskItemDelegateExecutor,
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

	@Reference
	private BatchEngineImportTaskErrorLocalService
		_batchEngineImportTaskErrorLocalService;

	private BatchEngineImportTaskItemReaderFactory
		_batchEngineImportTaskItemReaderFactory;

	@Reference
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	private BatchEngineTaskItemDelegateExecutorFactory
		_batchEngineTaskItemDelegateExecutorFactory;

	@Reference
	private BatchEngineTaskMethodRegistry _batchEngineTaskMethodRegistry;

	private BatchEngineTaskProgressFactory _batchEngineTaskProgressFactory;

	@Reference
	private CompanyLocalService _companyLocalService;

	private BatchEngineImportStrategyFactory _batchEngineImportStrategyFactory;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}