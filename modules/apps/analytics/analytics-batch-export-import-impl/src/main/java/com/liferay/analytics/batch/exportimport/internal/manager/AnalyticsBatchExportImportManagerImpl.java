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

package com.liferay.analytics.batch.exportimport.internal.manager;

import com.liferay.analytics.batch.exportimport.manager.AnalyticsBatchExportImportManager;
import com.liferay.analytics.message.storage.service.AnalyticsMessageLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationTracker;
import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.BatchEngineTaskExecuteStatus;
import com.liferay.batch.engine.BatchEngineTaskOperation;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.model.BatchEngineImportTask;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskLocalService;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import java.net.HttpURLConnection;

import java.nio.file.Files;

import java.text.Format;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = AnalyticsBatchExportImportManager.class)
public class AnalyticsBatchExportImportManagerImpl
	implements AnalyticsBatchExportImportManager {

	@Override
	public void exportToAnalyticsCloud(
			String batchEngineExportTaskItemDelegateName, long companyId,
			List<String> fieldNamesList,
			UnsafeConsumer<String, Exception> notificationUnsafeConsumer,
			Date resourceLastModifiedDate, String resourceName, long userId)
		throws Exception {

		_notify(
			"Exporting resource " + resourceName, notificationUnsafeConsumer);

		Map<String, Serializable> parameters = new HashMap<>();

		if (resourceLastModifiedDate != null) {
			parameters.put(
				"filter",
				StringBundler.concat(
					Field.getSortableFieldName(Field.MODIFIED_DATE), " ge ",
					resourceLastModifiedDate.getTime()));
		}

		BatchEngineExportTask batchEngineExportTask =
			_batchEngineExportTaskLocalService.addBatchEngineExportTask(
				companyId, userId, null, resourceName,
				BatchEngineTaskContentType.JSONL.name(),
				BatchEngineTaskExecuteStatus.INITIAL.name(), fieldNamesList,
				parameters, batchEngineExportTaskItemDelegateName);

		_batchEngineExportTaskExecutor.execute(batchEngineExportTask);

		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus =
			BatchEngineTaskExecuteStatus.valueOf(
				batchEngineExportTask.getExecuteStatus());

		if (batchEngineTaskExecuteStatus.equals(
				BatchEngineTaskExecuteStatus.COMPLETED)) {

			_notify(
				StringBundler.concat(
					"Exported ", batchEngineExportTask.getTotalItemsCount(),
					" items for resource ", resourceName),
				notificationUnsafeConsumer);

			if (batchEngineExportTask.getTotalItemsCount() == 0) {
				_notify(
					"There are no items to upload", notificationUnsafeConsumer);

				return;
			}

			_notify(
				"Uploading resource " + resourceName,
				notificationUnsafeConsumer);

			InputStream contentInputStream =
				_batchEngineExportTaskLocalService.openContentInputStream(
					batchEngineExportTask.getBatchEngineExportTaskId());

			_upload(
				companyId, contentInputStream, resourceLastModifiedDate,
				resourceName);

			contentInputStream.close();

			_batchEngineExportTaskLocalService.deleteBatchEngineExportTask(
				batchEngineExportTask);

			_notify(
				"Completed uploading resource " + resourceName,
				notificationUnsafeConsumer);
		}
		else {
			throw new PortalException(
				"Unable to export resource " + resourceName);
		}
	}

	@Override
	public void importFromAnalyticsCloud(
			String batchEngineImportTaskItemDelegateName, long companyId,
			Map<String, String> fieldMapping,
			UnsafeConsumer<String, Exception> notificationUnsafeConsumer,
			Date resourceLastModifiedDate, String resourceName, long userId)
		throws Exception {

		_notify(
			"Checking modifications for resource " + resourceName,
			notificationUnsafeConsumer);

		File resourceFile = _download(
			companyId, resourceLastModifiedDate, resourceName);

		if (resourceFile == null) {
			_notify(
				"There are no modifications for resource " + resourceName,
				notificationUnsafeConsumer);

			return;
		}

		_notify(
			"Importing resource " + resourceName, notificationUnsafeConsumer);

		BatchEngineImportTask batchEngineImportTask =
			_batchEngineImportTaskLocalService.addBatchEngineImportTask(
				companyId, userId, 50, null, resourceName,
				Files.readAllBytes(resourceFile.toPath()),
				BatchEngineTaskContentType.JSONL.name(),
				BatchEngineTaskExecuteStatus.INITIAL.name(), fieldMapping,
				BatchEngineTaskOperation.CREATE.name(), null,
				batchEngineImportTaskItemDelegateName);

		_batchEngineImportTaskExecutor.execute(batchEngineImportTask);

		BatchEngineTaskExecuteStatus batchEngineTaskExecuteStatus =
			BatchEngineTaskExecuteStatus.valueOf(
				batchEngineImportTask.getExecuteStatus());

		if (batchEngineTaskExecuteStatus.equals(
				BatchEngineTaskExecuteStatus.COMPLETED)) {

			_notify(
				StringBundler.concat(
					"Imported ", batchEngineImportTask.getTotalItemsCount(),
					" items for resource ", resourceName),
				notificationUnsafeConsumer);

			_batchEngineImportTaskLocalService.deleteBatchEngineImportTask(
				batchEngineImportTask);
		}
		else {
			throw new PortalException(
				"Unable to import resource " + resourceName);
		}
	}

	@Reference
	protected BatchEngineExportTaskExecutor batchEngineExportTaskExecutor;

	private void _checkCompany(long companyId) {
		if (_analyticsConfigurationTracker.isActive()) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Analytics configuration tracker is inactive");
		}

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		if (Validator.isNotNull(
				analyticsConfiguration.liferayAnalyticsEndpointURL())) {

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Analytics configuration endpoint URL is null");
		}

		throw new IllegalStateException(
			"Analytics batch export/import is disabled");
	}

	private File _download(
		long companyId, Date resourceLastModifiedDate, String resourceName) {

		_checkCompany(companyId);

		Http.Options options = _getOptions(companyId);

		if (resourceLastModifiedDate != null) {
			options.addHeader(
				"If-Modified-Since", _format.format(resourceLastModifiedDate));
		}

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		options.setLocation(
			_http.addParameter(
				analyticsConfiguration.liferayAnalyticsEndpointURL() +
					"/dxp-batch-entities",
				"resourceName", resourceName));

		try {
			InputStream inputStream = _http.URLtoInputStream(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() ==
					HttpURLConnection.HTTP_FORBIDDEN) {

				JSONObject responseJSONObject =
					JSONFactoryUtil.createJSONObject(
						StringUtil.read(inputStream));

				_processInvalidTokenMessage(
					companyId, responseJSONObject.getString("message"));
			}

			if (inputStream != null) {
				return FileUtil.createTempFile(inputStream);
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		return null;
	}

	private Http.Options _getOptions(long companyId) {
		AnalyticsConfiguration analyticsConfiguration =
			_analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		Http.Options options = new Http.Options();

		options.addHeader(
			"OSB-Asah-Data-Source-ID",
			analyticsConfiguration.liferayAnalyticsDataSourceId());
		options.addHeader(
			"OSB-Asah-Faro-Backend-Security-Signature",
			analyticsConfiguration.
				liferayAnalyticsFaroBackendSecuritySignature());
		options.addHeader(
			"OSB-Asah-Project-ID",
			analyticsConfiguration.liferayAnalyticsProjectId());

		return options;
	}

	private void _notify(
			String message,
			UnsafeConsumer<String, Exception> notificationUnsafeConsumer)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug(message);
		}

		if (notificationUnsafeConsumer == null) {
			return;
		}

		notificationUnsafeConsumer.accept(message);
	}

	private void _processInvalidTokenMessage(long companyId, String message) {
		if (!Objects.equals(message, "INVALID_TOKEN")) {
			return;
		}

		if (_log.isWarnEnabled()) {
			_log.warn(
				StringBundler.concat(
					"Disconnecting data source for company ", companyId, ": ",
					message));
		}

		try {
			_companyLocalService.updatePreferences(
				companyId,
				UnicodePropertiesBuilder.create(
					true
				).put(
					"liferayAnalyticsConnectionType", ""
				).put(
					"liferayAnalyticsDataSourceId", ""
				).put(
					"liferayAnalyticsEndpointURL", ""
				).put(
					"liferayAnalyticsFaroBackendSecuritySignature", ""
				).put(
					"liferayAnalyticsFaroBackendURL", ""
				).put(
					"liferayAnalyticsGroupIds", ""
				).put(
					"liferayAnalyticsProjectId", ""
				).put(
					"liferayAnalyticsURL", ""
				).build());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to remove analytics preferences for company " +
						companyId,
					exception);
			}
		}

		try {
			_configurationProvider.deleteCompanyConfiguration(
				AnalyticsConfiguration.class, companyId);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to remove analytics configuration for company " +
						companyId,
					exception);
			}
		}

		_analyticsMessageLocalService.deleteAnalyticsMessages(companyId);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Deleted all analytics messages for company " + companyId);
		}
	}

	private void _upload(
		long companyId, InputStream resourceInputStream,
		Date resourceLastModifiedDate, String resourceName) {

		_checkCompany(companyId);

		Http.Options options = _getOptions(companyId);

		options.addHeader(
			HttpHeaders.CONTENT_TYPE,
			ContentTypes.MULTIPART_FORM_DATA +
				"; boundary=__MULTIPART_BOUNDARY__");
		options.addInputStreamPart(
			"file", resourceName, resourceInputStream,
			ContentTypes.MULTIPART_FORM_DATA);
		options.addPart(
			"uploadType",
			(resourceLastModifiedDate != null) ? "INCREMENTAL" : "FULL");

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		options.setLocation(
			analyticsConfiguration.liferayAnalyticsEndpointURL() +
				"/dxp-batch-entities");

		options.setPost(true);

		try {
			InputStream inputStream = _http.URLtoInputStream(options);

			Http.Response response = options.getResponse();

			if (response.getResponseCode() ==
					HttpURLConnection.HTTP_FORBIDDEN) {

				JSONObject responseJSONObject =
					JSONFactoryUtil.createJSONObject(
						StringUtil.read(inputStream));

				_processInvalidTokenMessage(
					companyId, responseJSONObject.getString("message"));
			}

			if ((response.getResponseCode() < 200) ||
				(response.getResponseCode() >= 300)) {

				throw new Exception(
					"Upload failed with HTTP response code: " +
						response.getResponseCode());
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Upload completed successfully");
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsBatchExportImportManagerImpl.class);

	private static final Format _format =
		FastDateFormatFactoryUtil.getSimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss zzz");

	@Reference
	private AnalyticsConfigurationTracker _analyticsConfigurationTracker;

	@Reference
	private AnalyticsMessageLocalService _analyticsMessageLocalService;

	@Reference
	private BatchEngineExportTaskExecutor _batchEngineExportTaskExecutor;

	@Reference
	private BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;

	@Reference
	private BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;

	@Reference
	private BatchEngineImportTaskLocalService
		_batchEngineImportTaskLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

}