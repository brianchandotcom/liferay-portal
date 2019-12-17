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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Dictionary;

import javax.portlet.ActionRequest;

import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/analytics/edit_synced_contacts"
	},
	service = MVCActionCommand.class
)
public class EditSyncedContactsMVCActionCommand
	extends BaseAnalyticsMVCActionCommand {

	@Override
	protected void updateConfigurationProperties(
			ActionRequest actionRequest,
			Dictionary<String, Object> configurationProperties)
		throws Exception {

		boolean syncAllContacts = ParamUtil.getBoolean(
			actionRequest, "syncAllContacts");
		String[] syncedOrganizationIds = ParamUtil.getStringValues(
			actionRequest, "syncedOrganizationIds");
		String[] syncedUserGroupIds = ParamUtil.getStringValues(
			actionRequest, "syncedUserGroupIds");

		configurationProperties.put(
			"syncAllContacts", String.valueOf(syncAllContacts));

		if (!syncAllContacts) {
			configurationProperties.put(
				"syncedOrganizationIds", syncedOrganizationIds);
			configurationProperties.put(
				"syncedUserGroupIds", syncedUserGroupIds);
		}

		_notifyAnalyticsCloud(
			actionRequest, syncAllContacts, syncedOrganizationIds,
			syncedUserGroupIds);
	}

	private void _notifyAnalyticsCloud(
			ActionRequest actionRequest, boolean syncAllContacts,
			String[] syncedOrganizationIds, String[] syncedUserGroupIds)
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

		if (Validator.isNull(liferayAnalyticsDataSourceId) ||
			Validator.isNull(liferayAnalyticsFaroBackendSecuritySignature) ||
			Validator.isNull(liferayAnalyticsFaroBackendURL)) {

			return;
		}

		boolean contactsSelected = true;

		if (!syncAllContacts && ArrayUtil.isEmpty(syncedOrganizationIds) &&
			ArrayUtil.isEmpty(syncedUserGroupIds)) {

			contactsSelected = false;
		}

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient closeableHttpClient =
				httpClientBuilder.build()) {

			HttpPut httpPut = new HttpPut(
				String.format(
					"%s/api/1.0/data-sources/%s/details",
					liferayAnalyticsFaroBackendURL,
					liferayAnalyticsDataSourceId));

			JSONObject bodyJSONObject = JSONUtil.put(
				"contactsSelected", contactsSelected);

			httpPut.setEntity(new StringEntity(bodyJSONObject.toString()));

			httpPut.setHeader("Content-type", "application/json");
			httpPut.setHeader(
				"OSB-Asah-Faro-Backend-Security-Signature",
				liferayAnalyticsFaroBackendSecuritySignature);

			CloseableHttpResponse closeableHttpResponse =
				closeableHttpClient.execute(httpPut);

			StatusLine statusLine = closeableHttpResponse.getStatusLine();

			if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
				throw new PortalException("Invalid token");
			}
		}
	}

}