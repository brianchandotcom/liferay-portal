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

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;

import javax.portlet.ActionRequest;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
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

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String token = ParamUtil.getString(actionRequest, "token");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			new String(Base64.decode(token)));

		_connectAnalytics(
			themeDisplay, jsonObject.getString("token"),
			jsonObject.getString("url"));

		configurationProperties.put("companyId", themeDisplay.getCompanyId());

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			configurationProperties.put(key, jsonObject.getString(key));
		}

		configurationProperties.put("token", token);
	}

	private void _connectAnalytics(
			ThemeDisplay themeDisplay, String token, String url)
		throws Exception {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			HttpPost httpPost = new HttpPost(url);

			List<NameValuePair> parameters = new ArrayList<>();

			parameters.add(new BasicNameValuePair("token", token));
			parameters.add(
				new BasicNameValuePair(
					"portalURL", _portal.getPortalURL(themeDisplay)));

			httpPost.setEntity(new UrlEncodedFormEntity(parameters));

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPost);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				throw new PortalException("Unable to connect this token");
			}

			_savePreferences(
				themeDisplay.getCompanyId(),
				EntityUtils.toString(closeableHttpResponse.getEntity()));
		}
	}

	private void _savePreferences(long companyId, String json)
		throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		UnicodeProperties unicodeProperties = new UnicodeProperties(true);

		Iterator<String> keys = jsonObject.keys();

		while (keys.hasNext()) {
			String key = keys.next();

			unicodeProperties.setProperty(key, jsonObject.getString(key));
		}

		_companyService.updatePreferences(companyId, unicodeProperties);
	}

	@Reference
	private CompanyService _companyService;

	@Reference
	private Portal _portal;

}