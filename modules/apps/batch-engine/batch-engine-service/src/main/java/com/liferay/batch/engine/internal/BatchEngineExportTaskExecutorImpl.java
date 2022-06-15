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
import com.liferay.batch.engine.configuration.BatchEngineTaskCompanyConfiguration;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemDelegateExecutor;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemDelegateExecutorFactory;
import com.liferay.batch.engine.internal.writer.BatchEngineExportTaskItemWriter;
import com.liferay.batch.engine.internal.writer.BatchEngineExportTaskItemWriterFactory;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortParserProvider;

import java.io.IOException;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(service = BatchEngineExportTaskExecutor.class)
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
				batchEngineExportTask.getCompanyId(),
				() -> _exportItems(batchEngineExportTask),
				_userLocalService.getUser(batchEngineExportTask.getUserId()));

			_updateBatchEngineExportTask(
				BatchEngineTaskExecuteStatus.COMPLETED, batchEngineExportTask,
				null);
		}
		catch (Throwable throwable) {
			_log.error(
				"Unable to update batch engine export task " +
					batchEngineExportTask,
				throwable);

			try {
				BatchEngineExportTask currentBatchEngineExportTask =
					_batchEngineExportTaskLocalService.getBatchEngineExportTask(
						batchEngineExportTask.getPrimaryKey());

				_updateBatchEngineExportTask(
					BatchEngineTaskExecuteStatus.FAILED,
					currentBatchEngineExportTask, throwable.getMessage());
			}
			catch (PortalException portalException) {
				_log.error(
					"Unable to update batch engine export task",
					portalException);
			}
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_batchEngineTaskItemDelegateExecutorFactory =
			new BatchEngineTaskItemDelegateExecutorFactory(
				_batchEngineTaskMethodRegistry, _expressionConvert,
				_filterParserProvider, _sortParserProvider);
	}

	private void _exportItems(BatchEngineExportTask batchEngineExportTask)
		throws Exception {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (BatchEngineTaskItemDelegateExecutor
				batchEngineTaskItemDelegateExecutor =
					_batchEngineTaskItemDelegateExecutorFactory.create(
						batchEngineExportTask.getTaskItemDelegateName(),
						batchEngineExportTask.getClassName(),
						_companyLocalService.getCompany(
							batchEngineExportTask.getCompanyId()),
						batchEngineExportTask.getParameters(),
						_userLocalService.getUser(
							batchEngineExportTask.getUserId()));
			ZipOutputStream zipOutputStream = _getZipOutputStream(
				batchEngineExportTask.getContentType(),
				unsyncByteArrayOutputStream);
			BatchEngineExportTaskItemWriter batchEngineExportTaskItemWriter =
				_batchEngineExportTaskItemWriterFactory.create(
					BatchEngineTaskContentType.valueOf(
						batchEngineExportTask.getContentType()),
					GetterUtil.getString(
						_getCSVFileColumnDelimiter(
							batchEngineExportTask.getCompanyId()),
						StringPool.COMMA),
					batchEngineExportTask.getFieldNamesList(),
					_batchEngineTaskMethodRegistry.getItemClass(
						batchEngineExportTask.getClassName()),
					zipOutputStream, batchEngineExportTask.getParameters())) {

			int exportBatchSize = _getExportBatchSize(
				batchEngineExportTask.getCompanyId());

			Page<?> page = batchEngineTaskItemDelegateExecutor.getItems(
				1, exportBatchSize);

			batchEngineExportTask.setTotalItemsCount(
				Math.toIntExact(page.getTotalCount()));

			Collection<?> items = page.getItems();

			while (!items.isEmpty()) {
				batchEngineExportTaskItemWriter.write(items);

				batchEngineExportTask.setProcessedItemsCount(
					batchEngineExportTask.getProcessedItemsCount() +
						items.size());

				batchEngineExportTask =
					_batchEngineExportTaskLocalService.
						updateBatchEngineExportTask(batchEngineExportTask);

				if (Thread.interrupted()) {
					throw new InterruptedException();
				}

				if (!page.hasNext()) {
					break;
				}

				page = batchEngineTaskItemDelegateExecutor.getItems(
					(int)page.getPage() + 1, exportBatchSize);

				items = page.getItems();
			}
		}

		byte[] content = unsyncByteArrayOutputStream.toByteArray();

		batchEngineExportTask.setContent(
			new OutputBlob(
				new UnsyncByteArrayInputStream(content), content.length));

		_batchEngineExportTaskLocalService.updateBatchEngineExportTask(
			batchEngineExportTask);
	}

	private String _getCSVFileColumnDelimiter(long companyId)
		throws ConfigurationException {

		BatchEngineTaskCompanyConfiguration
			batchEngineTaskCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					BatchEngineTaskCompanyConfiguration.class, companyId);

		return batchEngineTaskCompanyConfiguration.csvFileColumnDelimiter();
	}

	private int _getExportBatchSize(long companyId)
		throws ConfigurationException {

		BatchEngineTaskCompanyConfiguration
			batchEngineTaskCompanyConfiguration =
				_configurationProvider.getCompanyConfiguration(
					BatchEngineTaskCompanyConfiguration.class, companyId);

		return batchEngineTaskCompanyConfiguration.exportBatchSize();
	}

	private ZipOutputStream _getZipOutputStream(
			String contentType,
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream)
		throws IOException {

		ZipOutputStream zipOutputStream = new ZipOutputStream(
			unsyncByteArrayOutputStream);

		ZipEntry zipEntry = new ZipEntry(
			"export." + StringUtil.toLowerCase(contentType));

		zipOutputStream.putNextEntry(zipEntry);

		return zipOutputStream;
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

	private final BatchEngineExportTaskItemWriterFactory
		_batchEngineExportTaskItemWriterFactory =
			new BatchEngineExportTaskItemWriterFactory();

	@Reference
	private BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;

	private BatchEngineTaskItemDelegateExecutorFactory
		_batchEngineTaskItemDelegateExecutorFactory;

	@Reference
	private BatchEngineTaskMethodRegistry _batchEngineTaskMethodRegistry;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference(
		target = "(result.class.name=com.liferay.portal.kernel.search.filter.Filter)"
	)
	private ExpressionConvert<Filter> _expressionConvert;

	@Reference
	private FilterParserProvider _filterParserProvider;

	@Reference
	private SortParserProvider _sortParserProvider;

	@Reference
	private UserLocalService _userLocalService;

}