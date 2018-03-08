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

package com.liferay.configuration.admin.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.internal.display.ConfigurationCategoryMenuDisplay;
import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.configuration.admin.web.internal.util.ConfigurationModelRetriever;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
		"mvc.command.name=/view_category",
		"service.ranking:Integer=" + Integer.MAX_VALUE
	},
	service = MVCRenderCommand.class
)
public class ViewCategoryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String configurationCategory = ParamUtil.getString(
			renderRequest, "configurationCategory");

		try {
			ConfigurationCategoryMenuDisplay configurationCategoryMenuDisplay =
				_configurationModelRetriever.
					getConfigurationCategoryMenuDisplay(
						configurationCategory, themeDisplay.getLanguageId());

			PortletURL redirectURL = renderResponse.createRenderURL();

			PortletURL backURL = renderResponse.createRenderURL();

			redirectURL.setParameter("redirect", backURL.toString());

			if (!configurationCategoryMenuDisplay.isEmpty()) {
				ConfigurationModel configurationModel =
					configurationCategoryMenuDisplay.
						getFirstConfigurationModel();

				if (configurationModel.isFactory() &&
					!configurationModel.isCompanyFactory()) {

					redirectURL.setParameter(
						"mvcRenderCommandName", "/view_factory_instances");
					redirectURL.setParameter(
						"factoryPid", configurationModel.getFactoryPid());
				}
				else {
					redirectURL.setParameter(
						"mvcRenderCommandName", "/edit_configuration");
					redirectURL.setParameter(
						"factoryPid", configurationModel.getFactoryPid());
					redirectURL.setParameter("pid", configurationModel.getID());
				}
			}

			HttpServletResponse response = _portal.getHttpServletResponse(
				renderResponse);

			response.sendRedirect(redirectURL.toString());
		}
		catch (IOException ioe) {
			throw new PortletException(ioe);
		}

		return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
	}

	@Reference
	private ConfigurationModelRetriever _configurationModelRetriever;

	@Reference
	private Portal _portal;

}