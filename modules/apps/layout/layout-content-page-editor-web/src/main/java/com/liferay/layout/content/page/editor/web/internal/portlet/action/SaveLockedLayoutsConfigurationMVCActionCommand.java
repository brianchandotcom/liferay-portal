/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.layout.content.page.editor.web.internal.configuration.LockedLayoutsConfiguration;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/instance_settings/save_locked_layouts_configuration"
	},
	service = MVCActionCommand.class
)
public class SaveLockedLayoutsConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_configurationProvider.saveCompanyConfiguration(
			LockedLayoutsConfiguration.class, themeDisplay.getCompanyId(),
			HashMapDictionaryBuilder.<String, Object>put(
				"allowAutomaticUnlockingProcess",
				ParamUtil.getBoolean(
					actionRequest, "allowAutomaticUnlockingProcess")
			).put(
				"lockReviewFrequency",
				ParamUtil.getInteger(actionRequest, "lockReviewFrequency")
			).put(
				"timeWithoutAutosave",
				ParamUtil.getInteger(actionRequest, "timeWithoutAutosave")
			).build());

		SessionMessages.add(actionRequest, "requestProcessed");

		sendRedirect(actionRequest, actionResponse);
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

}