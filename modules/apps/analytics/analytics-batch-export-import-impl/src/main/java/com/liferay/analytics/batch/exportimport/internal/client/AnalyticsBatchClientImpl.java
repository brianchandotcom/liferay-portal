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

package com.liferay.analytics.batch.exportimport.internal.client;

import com.liferay.analytics.batch.exportimport.client.AnalyticsBatchClient;
import com.liferay.analytics.batch.exportimport.client.UploadType;
import com.liferay.analytics.message.storage.service.AnalyticsMessageLocalService;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationTracker;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.io.File;
import java.io.InputStream;

import java.net.HttpURLConnection;

import java.text.Format;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = AnalyticsBatchClient.class)
public class AnalyticsBatchClientImpl implements AnalyticsBatchClient {

	@Override
	public File downloadResource(
		long companyId, Date resourceLastModifiedDate, String resourceName) {

		if (!_isEnabled(companyId)) {
			throw new IllegalStateException(
				"Analytics batch client is not enabled");
		}

		Http.Options options = _getOptions(companyId);

		if (resourceLastModifiedDate != null) {
			options.addHeader(
				"If-Modified-Since",
				_modifiedSinceHeaderDateFormatter.format(
					resourceLastModifiedDate));
		}

		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

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

	@Override
	public void uploadResource(
		long companyId, InputStream resourceInputStream, String resourceName,
		UploadType uploadType) {

		if (!_isEnabled(companyId)) {
			throw new IllegalStateException(
				"Analytics batch client is not enabled");
		}

		Http.Options options = _getOptions(companyId);

		options.addHeader(
			HttpHeaders.CONTENT_TYPE,
			ContentTypes.MULTIPART_FORM_DATA +
				"; boundary=__MULTIPART_BOUNDARY__");

		options.addPart("uploadType", uploadType.name());

		options.addInputStreamPart(
			"file", resourceName, resourceInputStream,
			ContentTypes.MULTIPART_FORM_DATA);

		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

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
					String.format(
						"HTTP %s error during resource upload. %s",
						response.getResponseCode(),
						StringUtil.read(inputStream)));
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Upload completed successfully");
			}
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Reference
	protected AnalyticsConfigurationTracker analyticsConfigurationTracker;

	@Reference
	protected AnalyticsMessageLocalService analyticsMessageLocalService;

	@Reference
	protected CompanyLocalService companyLocalService;

	@Reference
	protected ConfigurationProvider configurationProvider;

	private void _disconnectDataSource(long companyId) {
		try {
			companyLocalService.updatePreferences(
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
			configurationProvider.deleteCompanyConfiguration(
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
	}

	private Http.Options _getOptions(long companyId) {
		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

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

	private boolean _isEnabled(long companyId) {
		if (!analyticsConfigurationTracker.isActive()) {
			if (_log.isDebugEnabled()) {
				_log.debug("Analytics configuration tracker not active");
			}

			return false;
		}

		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(companyId);

		if (analyticsConfiguration.liferayAnalyticsEndpointURL() == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Analytics endpoint URL null");
			}

			return false;
		}

		return true;
	}

	private void _processInvalidTokenMessage(long companyId, String message) {
		if (message.equals("INVALID_TOKEN")) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Disconnecting data source for company ", companyId,
						". Cause: ", message));
			}

			_disconnectDataSource(companyId);

			analyticsMessageLocalService.deleteAnalyticsMessages(companyId);

			if (_log.isInfoEnabled()) {
				_log.info(
					"Deleted all analytics messages for company " + companyId);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsBatchClientImpl.class);

	private static final Format _modifiedSinceHeaderDateFormatter =
		FastDateFormatFactoryUtil.getSimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss zzz");

	@Reference
	private Http _http;

}