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

import com.liferay.analytics.settings.web.internal.util.AnalyticsSettingsUtil;
import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Dictionary;

import javax.portlet.ActionRequest;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/analytics/edit_synced_sites"
	},
	service = MVCActionCommand.class
)
public class EditSyncedSitesMVCActionCommand
	extends BaseAnalyticsMVCActionCommand {

	@Override
	protected void updateConfigurationProperties(
			ActionRequest actionRequest,
			Dictionary<String, Object> configurationProperties)
		throws Exception {

		String siteReportingGrouping = ParamUtil.getString(
			actionRequest, "siteReportingGrouping");
		String[] syncedGroupIds = ParamUtil.getStringValues(
			actionRequest, "rowIds");

		configurationProperties.put(
			"siteReportingGrouping", siteReportingGrouping);
		configurationProperties.put("syncedGroupIds", syncedGroupIds);

		_updateCompanyPreferences(actionRequest, syncedGroupIds);

		_notifyAnalyticsCloud(actionRequest, syncedGroupIds);
	}

	private void _notifyAnalyticsCloud(
			ActionRequest actionRequest, String[] syncedGroupIds)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!AnalyticsSettingsUtil.isAnalyticsEnabled(
				themeDisplay.getCompanyId())) {

			return;
		}

		boolean sitesSelected = true;

		if (ArrayUtil.isEmpty(syncedGroupIds)) {
			sitesSelected = false;
		}

		HttpResponse httpResponse = AnalyticsSettingsUtil.doPut(
			JSONUtil.put("sitesSelected", sitesSelected),
			themeDisplay.getCompanyId(),
			String.format(
				"api/1.0/data-sources/%s/details",
				AnalyticsSettingsUtil.getAsahFaroBackendDataSourceId(
					themeDisplay.getCompanyId())));

		StatusLine statusLine = httpResponse.getStatusLine();

		if (statusLine.getStatusCode() == HttpStatus.SC_FORBIDDEN) {
			disconnectDataSource(themeDisplay.getCompanyId(), httpResponse);

			return;
		}

		if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
			throw new PortalException("Invalid token");
		}
	}

	private void _updateCompanyPreferences(
			ActionRequest actionRequest, String[] syncedGroupIds)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		UnicodeProperties unicodeProperties = new UnicodeProperties(true);

		unicodeProperties.setProperty(
			"liferayAnalyticsGroupIds",
			StringUtil.merge(syncedGroupIds, StringPool.COMMA));

		_companyService.updatePreferences(
			themeDisplay.getCompanyId(), unicodeProperties);
	}

	@Reference
	private CompanyService _companyService;

}