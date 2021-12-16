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

package com.liferay.remote.app.web.internal.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.TypedSettings;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.remote.app.service.RemoteAppEntryLocalService;
import com.liferay.remote.app.web.internal.constants.RemoteAppAdminPortletKeys;
import com.liferay.remote.app.web.internal.constants.RemoteAppAdminWebKeys;
import com.liferay.remote.app.web.internal.display.context.RemoteAppAdminDisplayContext;

import java.util.Collections;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + RemoteAppAdminPortletKeys.REMOTE_APP_ADMIN,
		"mvc.command.name=/"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long companyId = themeDisplay.getCompanyId();

			Settings settings = _settingsFactory.getSettings(
				new CompanyServiceSettingsLocator(
					companyId, RemoteAppAdminPortletKeys.REMOTE_APP_ADMIN));

			TypedSettings typedSettings = new TypedSettings(settings);

			if (typedSettings.getBooleanValue(
					"createSampleCustomElement", true)) {

				_remoteAppEntryLocalService.addCustomElementRemoteAppEntry(
					_userLocalService.getDefaultUserId(
						themeDisplay.getCompanyId()),
					StringPool.BLANK, "vanilla-counter",
					"https://liferay.github.io/liferay-frontend-projects" +
						"/vanilla-counter/index.js",
					"See how a vanilla counter works as a remote app.",
					"vanilla_counter", false,
					Collections.singletonMap(
						LocaleUtil.getDefault(), "Vanilla Counter"),
					"category.remote-apps",
					"friendly-url-mapping=vanilla_counter",
					"https://liferay.github.io/liferay-frontend-projects",
					WorkflowConstants.STATUS_APPROVED);

				typedSettings.setBooleanValue(
					"createSampleCustomElement", false);

				ModifiableSettings modifiableSettings =
					settings.getModifiableSettings();

				modifiableSettings.store();
			}
		}
		catch (Exception exception) {
			throw new PortletException(exception);
		}

		renderRequest.setAttribute(
			RemoteAppAdminWebKeys.REMOTE_APP_ADMIN_DISPLAY_CONTEXT,
			new RemoteAppAdminDisplayContext(renderRequest, renderResponse));

		return "/admin/view.jsp";
	}

	@Reference
	private RemoteAppEntryLocalService _remoteAppEntryLocalService;

	@Reference
	private SettingsFactory _settingsFactory;

	@Reference
	private UserLocalService _userLocalService;

}