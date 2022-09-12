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

package com.liferay.analytics.settings.rest.internal.helper.v1_0;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.net.HttpURLConnection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = AnalyticsCloudClientHelper.class)
public class AnalyticsCloudClientHelper {

	public JSONObject connectDataSource(long companyId, String connectionToken)
		throws Exception {

		JSONObject jsonObject = _createTokenJSONObject(connectionToken);

		Company company = _companyLocalService.getCompany(companyId);

		Http.Options options = new Http.Options();

		String url = HttpComponentsUtil.addParameter(
			jsonObject.getString("url"), "name", company.getName());

		url = HttpComponentsUtil.addParameter(
			url, "portalURL", company.getPortalURL(0));
		url = HttpComponentsUtil.addParameter(
			url, "token", jsonObject.getString("token"));

		options.setLocation(url);

		options.setPost(true);

		InputStream inputStream = _http.URLtoInputStream(options);

		Http.Response response = options.getResponse();

		JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(
			StringUtil.read(inputStream));

		if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Received response code %s",
						response.getResponseCode()));
			}

			throw new PortalException("Invalid token");
		}

		return responseJSONObject;
	}

	public JSONObject disconnectDataSource(long companyId) throws Exception {
		AnalyticsConfiguration analyticsConfiguration =
			_configurationProvider.getCompanyConfiguration(
				AnalyticsConfiguration.class, companyId);

		try {
			Http.Options options = new Http.Options();

			options.addHeader(HttpHeaders.CONTENT_LENGTH, "0");
			options.addHeader(
				"OSB-Asah-Faro-Backend-Security-Signature",
				analyticsConfiguration.
					liferayAnalyticsFaroBackendSecuritySignature());
			options.addHeader(
				"OSB-Asah-Project-ID",
				analyticsConfiguration.liferayAnalyticsProjectId());
			options.setLocation(
				String.format(
					"%s/api/1.0/data-sources/%s/disconnect",
					analyticsConfiguration.liferayAnalyticsFaroBackendURL(),
					analyticsConfiguration.liferayAnalyticsDataSourceId()));
			options.setPost(true);

			InputStream inputStream = _http.URLtoInputStream(options);

			Http.Response response = options.getResponse();

			JSONObject responseJSONObject = JSONFactoryUtil.createJSONObject(
				StringUtil.read(inputStream));

			if (response.getResponseCode() == HttpURLConnection.HTTP_OK) {
				return responseJSONObject;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					String.format(
						"Received response code %s",
						response.getResponseCode()));
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return null;
	}

	private JSONObject _createTokenJSONObject(String connectionToken)
		throws Exception {

		try {
			if (Validator.isBlank(connectionToken)) {
				throw new IllegalArgumentException();
			}

			return JSONFactoryUtil.createJSONObject(
				new String(Base64.decode(connectionToken)));
		}
		catch (Exception exception) {
			_log.error("Invalid token", exception);

			throw new PortalException("Invalid token", exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsCloudClientHelper.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private Http _http;

	@Reference
	private SettingsFactory _settingsFactory;

}