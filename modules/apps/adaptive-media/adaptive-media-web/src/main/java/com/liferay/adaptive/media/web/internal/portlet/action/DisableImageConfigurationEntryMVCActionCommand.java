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

package com.liferay.adaptive.media.web.internal.portlet.action;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationEntry;
import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.web.constants.AMPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AMPortletKeys.ADAPTIVE_MEDIA,
		"mvc.command.name=/adaptive_media/disable_image_configuration_entry"
	},
	service = MVCActionCommand.class
)
public class DisableImageConfigurationEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doPermissionCheckedProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String amImageConfigurationEntryUuid = ParamUtil.getString(
			actionRequest, "amImageConfigurationEntryUuid");

		_amImageConfigurationHelper.disableAMImageConfigurationEntry(
			themeDisplay.getCompanyId(), amImageConfigurationEntryUuid);

		Optional<AMImageConfigurationEntry> amImageConfigurationEntryOptional =
			_amImageConfigurationHelper.getAMImageConfigurationEntry(
				themeDisplay.getCompanyId(), amImageConfigurationEntryUuid);

		amImageConfigurationEntryOptional.ifPresent(
			amImageConfigurationEntry -> SessionMessages.add(
				actionRequest, "configurationEntryDisabled",
				amImageConfigurationEntry));
	}

	@Reference
	private AMImageConfigurationHelper _amImageConfigurationHelper;

}