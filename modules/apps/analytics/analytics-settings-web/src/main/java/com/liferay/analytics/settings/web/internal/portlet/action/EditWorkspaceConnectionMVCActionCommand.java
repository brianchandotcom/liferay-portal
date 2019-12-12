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

package com.liferay.analytics.settings.web.internal.portlet.action;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.Objects;

import javax.portlet.ActionRequest;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/analytics/edit_workspace_connection"
	},
	service = MVCActionCommand.class
)
public class EditWorkspaceConnectionMVCActionCommand
	extends BaseAnalyticsMVCActionCommand {

	@Override
	protected void updateConfigurationProperties(
			ActionRequest actionRequest,
			Dictionary<String, Object> configurationProperties)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Objects.equals(cmd, "disconnect")) {
			_disconnect(actionRequest, configurationProperties);
		}
		else {
			_connect(actionRequest, configurationProperties);
		}
	}

	private void _connect(
			ActionRequest actionRequest,
			Dictionary<String, Object> configurationProperties)
		throws Exception {

		JSONObject tokenJSONObject = _createTokenJSONObject(actionRequest);

		String dataSourceConnectionJSON = _connectDataSource(
			actionRequest, tokenJSONObject);

		_updateCompanyPreferences(actionRequest, dataSourceConnectionJSON);

		_updateConfigurationProperties(
			actionRequest, configurationProperties, tokenJSONObject);
	}

	private String _connectDataSource(
			ActionRequest actionRequest, JSONObject tokenJSONObject)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			HttpPost httpPost = new HttpPost(tokenJSONObject.getString("url"));

			httpPost.setEntity(
				new UrlEncodedFormEntity(
					Arrays.asList(
						new BasicNameValuePair(
							"portalURL", _portal.getPortalURL(themeDisplay)),
						new BasicNameValuePair(
							"token", tokenJSONObject.getString("token")))));

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				throw new PortalException("Invalid token");
			}

			return EntityUtils.toString(closeableHttpResponse.getEntity());
		}
	}

	private JSONObject _createTokenJSONObject(ActionRequest actionRequest)
		throws Exception {

		String token = ParamUtil.getString(actionRequest, "token");

		try {
			return JSONFactoryUtil.createJSONObject(
				new String(Base64.decode(token)));
		}
		catch (Exception e) {
			throw new PortalException("Invalid token", e);
		}
	}

	private void _disconnect(
			ActionRequest actionRequest,
			Dictionary<String, Object> configurationProperties)
		throws Exception {

		_disconnectDataSource(actionRequest);
		_removeCompanyPreferences(actionRequest);
		_removeConfigurationProperties(actionRequest, configurationProperties);
	}

	private void _disconnectDataSource(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String liferayAnalyticsDataSourceId = PrefsPropsUtil.getString(
			themeDisplay.getCompanyId(), "liferayAnalyticsDataSourceId");

		String liferayAnalyticsFaroBackendSecuritySignature =
			PrefsPropsUtil.getString(
				themeDisplay.getCompanyId(),
				"liferayAnalyticsFaroBackendSecuritySignature");

		String liferayAnalyticsFaroBackendURL = PrefsPropsUtil.getString(
			themeDisplay.getCompanyId(), "liferayAnalyticsFaroBackendURL");

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			HttpPost httpPost = new HttpPost(
				String.format(
					"%s/api/1.0/data-sources/%s/disconnect",
					liferayAnalyticsFaroBackendURL,
					liferayAnalyticsDataSourceId));

			httpPost.setHeader(
				"OSB-Asah-Faro-Backend-Security-Signature",
				liferayAnalyticsFaroBackendSecuritySignature);

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				throw new PortalException("Failed to disconnected data source");
			}
		}
	}

	private void _removeCompanyPreferences(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_companyService.removePreferences(
			themeDisplay.getCompanyId(),
			new String[] {
				"liferayAnalyticsDataSourceId", "liferayAnalyticsEndpointURL",
				"liferayAnalyticsFaroBackendSecuritySignature",
				"liferayAnalyticsFaroBackendURL", "liferayAnalyticsGroupIds",
				"liferayAnalyticsURL"
			});
	}

	private void _removeConfigurationProperties(
			ActionRequest actionRequest,
			Dictionary<String, Object> configurationProperties)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		configurationProperties.remove("token");

		configurationProvider.deleteCompanyConfiguration(
			AnalyticsConfiguration.class, themeDisplay.getCompanyId());
	}

	private void _updateCompanyPreferences(
			ActionRequest actionRequest, String dataSourceConnectionJSON)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		UnicodeProperties unicodeProperties = new UnicodeProperties(true);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			dataSourceConnectionJSON);

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			unicodeProperties.setProperty(key, jsonObject.getString(key));
		}

		_companyService.updatePreferences(
			themeDisplay.getCompanyId(), unicodeProperties);
	}

	private void _updateConfigurationProperties(
		ActionRequest actionRequest,
		Dictionary<String, Object> configurationProperties,
		JSONObject tokenJSONObject) {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		configurationProperties.put("companyId", themeDisplay.getCompanyId());

		Iterator<String> keys = tokenJSONObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			configurationProperties.put(key, tokenJSONObject.getString(key));
		}

		configurationProperties.put(
			"token", ParamUtil.getString(actionRequest, "token"));
	}

	@Reference
	private CompanyService _companyService;

	@Reference
	private Portal _portal;

}