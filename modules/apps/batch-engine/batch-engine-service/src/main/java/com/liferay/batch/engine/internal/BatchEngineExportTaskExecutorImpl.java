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

import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.configuration.BatchEngineExportTaskConfiguration;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemResourceDelegate;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemResourceDelegateFactory;
import com.liferay.batch.engine.internal.writer.BatchEngineExportTaskItemWriter;
import com.liferay.batch.engine.internal.writer.BatchEngineExportTaskItemWriterFactory;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.pagination.Page;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	configurationPid = "com.liferay.batch.engine.configuration.BatchEngineExportTaskConfiguration",
	service = BatchEngineExportTaskExecutor.class
)
public class BatchEngineExportTaskExecutorImpl
	implements BatchEngineExportTaskExecutor {

	@Override
	public void execute(BatchEngineExportTask batchEngineExportTask) {
		try {
			batchEngineExportTask.setExecuteStatus(
				BatchEngineTaskExecuteStatus.STARTED.toString());
			batchEngineExportTask.setStartTime(new Date());

			_batchEngineExportTaskLocalService.updateBatchEngineExportTask(
				batchEngineExportTask);

			BatchEngineTaskExecutorUtil.execute(
				() -> _exportItems(batchEngineExportTask),
				_userLocalService.getUser(batchEngineExportTask.getUserId()));

			_updateBatchEngineExportTask(
				BatchEngineTaskExecuteStatus.COMPLETED, batchEngineExportTask,
				null);
		}
		catch (Throwable t) {
			_log.error(
				"Unable to update batch engine export task " +
					batchEngineExportTask,
				t);

			_updateBatchEngineExportTask(
				BatchEngineTaskExecuteStatus.FAILED, batchEngineExportTask,
				t.getMessage());
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		BatchEngineExportTaskConfiguration batchEngineExportTaskConfiguration =
			ConfigurableUtil.createConfigurable(
				BatchEngineExportTaskConfiguration.class, properties);

		_batchSize = batchEngineExportTaskConfiguration.batchSize();

		_batchEngineExportTaskItemWriterFactory =
			new BatchEngineExportTaskItemWriterFactory(
				GetterUtil.getString(
					batchEngineExportTaskConfiguration.csvFileColumnDelimiter(),
					StringPool.COMMA));

		_batchEngineTaskItemResourceDelegateFactory =
			new BatchEngineTaskItemResourceDelegateFactory(
				_batchEngineTaskMethodRegistry, _companyLocalService,
				_userLocalService);
	}

	private void _exportItems(BatchEngineExportTask batchEngineExportTask)
		throws Exception {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (BatchEngineTaskItemResourceDelegate
				batchEngineTaskItemResourceDelegate =
					_batchEngineTaskItemResourceDelegateFactory.create(
						BatchEngineTaskOperation.READ,
						batchEngineExportTask.getClassName(),
						batchEngineExportTask.getCompanyId(),
						batchEngineExportTask.getParameters(),
						batchEngineExportTask.getUserId(),
						batchEngineExportTask.getVersion());
			BatchEngineExportTaskItemWriter batchEngineExportTaskItemWriter =
				_batchEngineExportTaskItemWriterFactory.create(
					BatchEngineTaskContentType.valueOf(
						batchEngineExportTask.getContentType()),
					batchEngineExportTask.getFieldNamesList(),
					_batchEngineTaskMethodRegistry.getItemClass(
						batchEngineExportTask.getClassName()),
					unsyncByteArrayOutputStream)) {

			Page<?> page = null;
			int pageIndex = 1;

			do {
				if (Thread.interrupted()) {
					throw new InterruptedException();
				}

				page = batchEngineTaskItemResourceDelegate.getItems(
					pageIndex++, _batchSize);

				Collection<?> items = page.getItems();

				if (items.isEmpty()) {
					break;
				}

				batchEngineExportTaskItemWriter.write(items);

				_batchEngineExportTaskLocalService.updateBatchEngineExportTask(
					batchEngineExportTask);
			}
			while ((page.getPage() * page.getPageSize()) <
						page.getTotalCount());
		}

		byte[] content = unsyncByteArrayOutputStream.toByteArray();

		batchEngineExportTask.setContent(
			new OutputBlob(
				new UnsyncByteArrayInputStream(content), content.length));

		_batchEngineExportTaskLocalService.updateBatchEngineExportTask(
			batchEngineExportTask);
	}

	private void _updateBatchEngineExportTask(
		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus,
		BatchEngineExportTask batchEngineExportTask, String errorMessage) {

		batchEngineExportTask.setEndTime(new Date());
		batchEngineExportTask.setErrorMessage(errorMessage);
		batchEngineExportTask.setExecuteStatus(
			batchEngineTaskExecuteStatus.toString());

		_batchEngineExportTaskLocalService.updateBatchEngineExportTask(
			batchEngineExportTask);

		BatchEngineTaskCallbackUtil.sendCallback(
			batchEngineExportTask.getCallbackURL(),
			batchEngineExportTask.getExecuteStatus(),
			batchEngineExportTask.getBatchEngineExportTaskId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineExportTaskExecutorImpl.class);

	private BatchEngineExportTaskItemWriterFactory
		_batchEngineExportTaskItemWriterFactory;

	@Reference
	private BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;

	private BatchEngineTaskItemResourceDelegateFactory
		_batchEngineTaskItemResourceDelegateFactory;

	@Reference
	private BatchEngineTaskMethodRegistry _batchEngineTaskMethodRegistry;

	private int _batchSize;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private UserLocalService _userLocalService;

}