/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.web.internal.client;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.rest.manager.AnalyticsSettingsManager;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.NestableRuntimeException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;

import org.apache.http.HttpStatus;

/**
 * @author David Arques
 */
public class AsahFaroBackendClient {

	public AsahFaroBackendClient(
		AnalyticsSettingsManager analyticsSettingsManager, Http http) {

		_analyticsSettingsManager = analyticsSettingsManager;
		_http = http;
	}

	public String doGet(long companyId, String path) {
		try {
			AnalyticsConfiguration analyticsConfiguration =
				_analyticsSettingsManager.getAnalyticsConfiguration(companyId);

			return _getResponse(
				companyId,
				String.format(
					"%s/%s",
					analyticsConfiguration.liferayAnalyticsFaroBackendURL(),
					path));
		}
		catch (Exception exception) {
			throw new NestableRuntimeException(
				"Request to Asah Faro backend failed", exception);
		}
	}

	public boolean isValidConnection(long companyId) throws Exception {
		if (!_analyticsSettingsManager.isAnalyticsEnabled(companyId)) {
			return false;
		}

		try {
			AnalyticsConfiguration analyticsConfiguration =
				_analyticsSettingsManager.getAnalyticsConfiguration(companyId);

			doGet(
				companyId,
				"/api/1.0/data-sources/" +
					analyticsConfiguration.liferayAnalyticsDataSourceId());

			return true;
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalid Asah Faro backend connection", exception);
			}

			return false;
		}
	}

	private String _getResponse(long companyId, String url) throws Exception {
		Http.Options options = new Http.Options();

		AnalyticsConfiguration analyticsConfiguration =
			_analyticsSettingsManager.getAnalyticsConfiguration(companyId);

		options.addHeader("Accept", "application/json");
		options.addHeader(
			"OSB-Asah-Faro-Backend-Security-Signature",
			analyticsConfiguration.
				liferayAnalyticsFaroBackendSecuritySignature());

		String projectId = analyticsConfiguration.liferayAnalyticsProjectId();

		if (projectId != null) {
			options.addHeader("OSB-Asah-Project-ID", projectId);
		}

		options.setLocation(url);

		String response = _http.URLtoString(options);

		Http.Response httpResponse = options.getResponse();

		if (httpResponse.getResponseCode() != HttpStatus.SC_OK) {
			throw new NestableRuntimeException(
				StringBundler.concat(
					"Unexpected response status ",
					httpResponse.getResponseCode(), " with response message: ",
					response));
		}

		return response;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AsahFaroBackendClient.class);

	private final AnalyticsSettingsManager _analyticsSettingsManager;
	private final Http _http;

}