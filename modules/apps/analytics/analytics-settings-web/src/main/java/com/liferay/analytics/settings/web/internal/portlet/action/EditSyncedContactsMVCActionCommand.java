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
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Dictionary;

import javax.portlet.ActionRequest;

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
		Dictionary<String, Object> configurationProperties) {

		String syncAllContactsString = ParamUtil.getString(
			actionRequest, "syncAllContacts");
		String[] syncedUserGroupIds = ParamUtil.getStringValues(
			actionRequest, "userGroupIds");
		String[] syncedOrganizationIds = ParamUtil.getStringValues(
			actionRequest, "organizationIds");

		boolean syncAllContacts = false;

		if (Validator.isNotNull(syncAllContactsString)) {
			syncAllContacts = true;
		}

		configurationProperties.put(
			"syncAllContacts", String.valueOf(syncAllContacts));

		if (!syncAllContacts) {
			configurationProperties.put(
				"syncedOrganizationIds", syncedOrganizationIds);
			configurationProperties.put(
				"syncedUserGroupIds", syncedUserGroupIds);
		}
	}

}