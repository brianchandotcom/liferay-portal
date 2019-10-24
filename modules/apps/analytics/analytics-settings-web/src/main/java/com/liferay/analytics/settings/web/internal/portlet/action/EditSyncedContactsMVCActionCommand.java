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
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

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
	protected void storeSettings(
			ActionRequest actionRequest, ModifiableSettings modifiableSettings)
		throws Exception {

		String syncAllContactsString = ParamUtil.getString(
			actionRequest, "syncAllContacts");

		boolean syncAllContacts = false;

		if (Validator.isNotNull(syncAllContactsString)) {
			syncAllContacts = true;
		}

		modifiableSettings.setValue(
			"syncAllContacts", String.valueOf(syncAllContacts));

		modifiableSettings.store();
	}

}