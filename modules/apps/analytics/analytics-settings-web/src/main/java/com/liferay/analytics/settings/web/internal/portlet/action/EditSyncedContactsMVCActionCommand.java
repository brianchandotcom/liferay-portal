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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Dictionary;
import java.util.Objects;

import javax.portlet.ActionRequest;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;

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
			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

			if (Objects.equals(cmd, "update_synced_groups")) {
				configurationProperties.put(
					"syncedUserGroupIds", syncedUserGroupIds);

				syncedOrganizationIds = GetterUtil.getStringValues(
					configurationProperties.get("syncedOrganizationIds"));
			}
			else if (Objects.equals(cmd, "update_synced_organizations")) {
				configurationProperties.put(
					"syncedOrganizationIds", syncedOrganizationIds);

				syncedUserGroupIds = GetterUtil.getStringValues(
					configurationProperties.get("syncedUserGroupIds"));
			}
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

		if (!AnalyticsSettingsUtil.isAnalyticsEnabled(
				themeDisplay.getCompanyId())) {

			return;
		}

		boolean contactsSelected = true;

		if (!syncAllContacts && ArrayUtil.isEmpty(syncedOrganizationIds) &&
			ArrayUtil.isEmpty(syncedUserGroupIds)) {

			contactsSelected = false;
		}

		HttpResponse httpResponse = AnalyticsSettingsUtil.doPut(
			JSONUtil.put("contactsSelected", contactsSelected),
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

}