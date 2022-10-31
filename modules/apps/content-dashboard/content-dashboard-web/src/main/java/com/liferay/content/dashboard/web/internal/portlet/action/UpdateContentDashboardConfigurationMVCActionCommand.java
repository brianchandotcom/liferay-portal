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

package com.liferay.content.dashboard.web.internal.portlet.action;

import com.liferay.content.dashboard.web.internal.constants.ContentDashboardPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Arques
 */
@Component(
	property = {
		"javax.portlet.name=" + ContentDashboardPortletKeys.CONTENT_DASHBOARD_ADMIN,
		"mvc.command.name=/content_dashboard/update_content_dashboard_configuration"
	},
	service = MVCActionCommand.class
)
public class UpdateContentDashboardConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String[] assetVocabularyIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "assetVocabularyIds"));

		if (assetVocabularyIds.length == 0) {
			hideDefaultSuccessMessage(actionRequest);
			SessionMessages.add(actionRequest, "emptyAssetVocabularyIds", true);
		}
		else {
			PortletPreferences portletPreferences =
				actionRequest.getPreferences();

			portletPreferences.setValues(
				"assetVocabularyIds", assetVocabularyIds);

			try {
				portletPreferences.store();
			}
			catch (ValidatorException validatorException) {
				SessionErrors.add(
					actionRequest, ValidatorException.class.getName(),
					validatorException);
			}
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			sendRedirect(actionRequest, actionResponse, redirect);
		}
	}

}