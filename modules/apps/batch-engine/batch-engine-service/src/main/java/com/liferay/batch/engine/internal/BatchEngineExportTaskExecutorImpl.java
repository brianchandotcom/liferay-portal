/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal;

import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskItemDelegateRegistry;
import com.liferay.batch.engine.ItemClassRegistry;
import com.liferay.batch.engine.configuration.BatchEngineTaskCompanyConfiguration;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemDelegateExecutor;
import com.liferay.batch.engine.internal.item.BatchEngineTaskItemDelegateExecutorFactory;
import com.liferay.batch.engine.internal.writer.BatchEngineExportTaskItemWriter;
import com.liferay.batch.engine.internal.writer.BatchEngineExportTaskItemWriterBuilder;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.pagination.Page;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.odata.filter.ExpressionConvert;
import com.liferay.portal.odata.filter.FilterParserProvider;
import com.liferay.portal.odata.sort.SortParserProvider;

import java.io.Serializable;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
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
		SafeCloseable safeCloseable = CompanyThreadLocal.setWithSafeCloseable(
			batchEngineExportTask.getCompanyId());

		try {
			batchEngineExportTask.setExecuteStatus(
				BatchEngineTaskExecuteStatus.STARTED.toString());
			batchEngineExportTask.setStartTime(new Date());

			_batchEngineExportTaskLocalService.updateBatchEngineExportTask(
				batchEngineExportTask);

			BatchEngineTaskExecutorUtil.execute(
				true, () -> _exportItems(batchEngineExportTask),
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
				_batchEngineTaskItemDelegateRegistry, _expressionConvert,
				_filterParserProvider, _sortParserProvider);
	}

	private void _exportItems(BatchEngineExportTask batchEngineExportTask)
		throws Exception {

		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		Map<String, Serializable> parameters = _getParameters(
			batchEngineExportTask);

		try (BatchEngineExportTaskItemWriter batchEngineExportTaskItemWriter =
				_getBatchEngineExportTaskItemWriter(
					batchEngineExportTask, parameters,
					unsyncByteArrayOutputStream)) {

			int exportBatchSize = _getExportBatchSize(
				batchEngineExportTask.getCompanyId());

			BatchEngineTaskItemDelegateExecutor
				batchEngineTaskItemDelegateExecutor =
					_batchEngineTaskItemDelegateExecutorFactory.create(
						batchEngineExportTask.getTaskItemDelegateName(),
						batchEngineExportTask.getClassName(),
						_companyLocalService.getCompany(
							batchEngineExportTask.getCompanyId()),
						parameters,
						_userLocalService.getUser(
							batchEngineExportTask.getUserId()));

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

	private BatchEngineExportTaskItemWriter _getBatchEngineExportTaskItemWriter(
			BatchEngineExportTask batchEngineExportTask,
			Map<String, Serializable> parameters,
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream)
		throws Exception {

		BatchEngineExportTaskItemWriterBuilder
			batchEngineExportTaskItemWriterBuilder =
				new BatchEngineExportTaskItemWriterBuilder();

		BatchEngineTaskContentType batchEngineTaskContentType =
			BatchEngineTaskContentType.valueOf(
				batchEngineExportTask.getContentType());

		return batchEngineExportTaskItemWriterBuilder.
			batchEngineTaskContentType(
				batchEngineTaskContentType
			).companyId(
				batchEngineExportTask.getCompanyId()
			).csvFileColumnDelimiter(
				GetterUtil.getString(
					_getCSVFileColumnDelimiter(
						batchEngineExportTask.getCompanyId()),
					StringPool.COMMA)
			).fieldNames(
				batchEngineExportTask.getFieldNamesList()
			).itemClass(
				_itemClassRegistry.getItemClass(
					batchEngineExportTask.getClassName())
			).outputStream(
				_getZipOutputStream(
					batchEngineTaskContentType, unsyncByteArrayOutputStream)
			).parameters(
				parameters
			).userId(
				batchEngineExportTask.getUserId()
			).build();
	}

	private String _getCSVFileColumnDelimiter(long companyId) throws Exception {
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

	private Map<String, Serializable> _getParameters(
		BatchEngineExportTask batchEngineExportTask) {

		Map<String, Serializable> parameters =
			batchEngineExportTask.getParameters();

		if (parameters == null) {
			parameters = new HashMap<>();
		}

		parameters.computeIfAbsent(
			"taskItemDelegateName",
			key -> batchEngineExportTask.getTaskItemDelegateName());

		return parameters;
	}

	private ZipOutputStream _getZipOutputStream(
			BatchEngineTaskContentType batchEngineTaskContentType,
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream)
		throws Exception {

		ZipOutputStream zipOutputStream = new ZipOutputStream(
			unsyncByteArrayOutputStream);

		ZipEntry zipEntry = new ZipEntry(
			"export." + batchEngineTaskContentType.getFileExtension());

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

		batchEngineExportTask =
			_batchEngineExportTaskLocalService.updateBatchEngineExportTask(
				batchEngineExportTask);

		BatchEngineTaskCallbackUtil.sendCallback(
			batchEngineExportTask.getCallbackURL(),
			batchEngineExportTask.getExecuteStatus(),
			batchEngineExportTask.getBatchEngineExportTaskId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineExportTaskExecutorImpl.class);

	@Reference
	private BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;

	private BatchEngineTaskItemDelegateExecutorFactory
		_batchEngineTaskItemDelegateExecutorFactory;

	@Reference
	private BatchEngineTaskItemDelegateRegistry
		_batchEngineTaskItemDelegateRegistry;

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
	private ItemClassRegistry _itemClassRegistry;

	@Reference
	private SortParserProvider _sortParserProvider;

	@Reference
	private UserLocalService _userLocalService;

}