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
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Dictionary;

import javax.portlet.ActionRequest;

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
		throws PortalException {

		String siteReportingGrouping = ParamUtil.getString(
			actionRequest, "siteReportingGrouping");
		String[] syncedGroupIds = ParamUtil.getStringValues(
			actionRequest, "rowIds");

		configurationProperties.put(
			"siteReportingGrouping", siteReportingGrouping);
		configurationProperties.put("syncedGroupIds", syncedGroupIds);

		Long companyId = (Long)actionRequest.getAttribute(WebKeys.COMPANY_ID);

		_updateCompanyPreferences(companyId, syncedGroupIds);
	}

	private void _updateCompanyPreferences(
			long companyId, String[] syncedGroupIds)
		throws PortalException {

		UnicodeProperties unicodeProperties = new UnicodeProperties(true);

		unicodeProperties.setProperty(
			"liferayAnalyticsGroupIds", StringUtil.merge(syncedGroupIds, ","));

		_companyService.updatePreferences(companyId, unicodeProperties);
	}

	@Reference
	private CompanyService _companyService;

}