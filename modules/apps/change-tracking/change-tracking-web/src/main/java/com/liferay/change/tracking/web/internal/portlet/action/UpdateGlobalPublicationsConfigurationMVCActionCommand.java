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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.configuration.CTSettingsConfiguration;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.exception.CTStagingEnabledException;
import com.liferay.change.tracking.web.internal.configuration.helper.CTSettingsConfigurationHelper;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/update_global_publications_configuration"
	},
	service = MVCActionCommand.class
)
public class UpdateGlobalPublicationsConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL redirectURL = PortletURLFactoryUtil.create(
			actionRequest, CTPortletKeys.PUBLICATIONS,
			PortletRequest.RENDER_PHASE);

		long companyId = themeDisplay.getCompanyId();

		CTSettingsConfiguration ctSettingsConfiguration =
			_ctSettingsConfigurationHelper.getCTSettingsConfiguration(
				companyId);

		if (ctSettingsConfiguration.enabled()) {
			redirectURL.setParameter(
				"mvcRenderCommandName", "/change_tracking/view_settings");
		}

		boolean enablePublications = ParamUtil.getBoolean(
			actionRequest, "enablePublications",
			ctSettingsConfiguration.enabled());
		boolean enableSandboxOnly = ParamUtil.getBoolean(
			actionRequest, "enableSandboxOnly",
			ctSettingsConfiguration.sandboxEnabled());

		try {
			_portletPermission.check(
				themeDisplay.getPermissionChecker(), CTPortletKeys.PUBLICATIONS,
				ActionKeys.CONFIGURATION);

			_ctSettingsConfigurationHelper.save(
				companyId, enablePublications, enableSandboxOnly);
		}
		catch (ConfigurationException configurationException) {
			Throwable throwable = configurationException.getCause();

			if (throwable.getCause() instanceof CTStagingEnabledException) {
				SessionErrors.add(actionRequest, "stagingEnabled");
			}
			else {
				SessionErrors.add(actionRequest, throwable.getClass());
			}

			redirectURL.setParameter(
				"mvcRenderCommandName", "/change_tracking/view_settings");

			sendRedirect(actionRequest, actionResponse, redirectURL.toString());

			return;
		}

		hideDefaultSuccessMessage(actionRequest);

		SessionMessages.add(
			_portal.getHttpServletRequest(actionRequest), "requestProcessed",
			_language.get(
				themeDisplay.getLocale(), "the-configuration-has-been-saved"));

		sendRedirect(actionRequest, actionResponse, redirectURL.toString());
	}

	@Reference
	private CTSettingsConfigurationHelper _ctSettingsConfigurationHelper;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPermission _portletPermission;

}