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

package com.liferay.configuration.admin.web.portlet.action;

import com.liferay.configuration.admin.web.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.constants.ConfigurationAdminWebKeys;
import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.configuration.admin.web.util.ConfigurationModelRetriever;
import com.liferay.configuration.admin.web.util.DDMFormRendererHelper;
import com.liferay.dynamic.data.mapping.constants.DDMWebKeys;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.CONFIGURATION_ADMIN,
		"mvc.command.name=/edit_configuration"
	},
	service = MVCRenderCommand.class
)
public class EditConfigurationMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String factoryPid = ParamUtil.getString(renderRequest, "factoryPid");

		String pid = ParamUtil.getString(renderRequest, "pid", factoryPid);

		Map<String, ConfigurationModel> configurationModels =
			_configurationModelRetriever.getConfigurationModels(
				themeDisplay.getLanguageId());

		ConfigurationModel configurationModel = configurationModels.get(pid);

		if ((configurationModel == null) && Validator.isNotNull(factoryPid)) {
			configurationModel = configurationModels.get(factoryPid);
		}

		if (configurationModel != null) {
			configurationModel = new ConfigurationModel(
				configurationModel.getExtendedObjectClassDefinition(),
				_configurationModelRetriever.getConfiguration(pid),
				configurationModel.getBundleLocation(),
				configurationModel.isFactory());
		}

		renderRequest.setAttribute(
			ConfigurationAdminWebKeys.CONFIGURATION_MODEL, configurationModel);

		DDMFormRendererHelper ddmFormRendererHelper = new DDMFormRendererHelper(
			renderRequest, renderResponse, configurationModel,
			_ddmFormRenderer);

		renderRequest.setAttribute(
			DDMWebKeys.DYNAMIC_DATA_MAPPING_FORM_HTML,
			ddmFormRendererHelper.getDDMFormHTML());

		return "/edit_configuration.jsp";
	}

	@Reference(unbind = "-")
	protected void setConfigurationModelRetriever(
		ConfigurationModelRetriever configurationModelRetriever) {

		_configurationModelRetriever = configurationModelRetriever;
	}

	@Reference(unbind = "-")
	protected void setDDMFormRenderer(DDMFormRenderer ddmFormRenderer) {
		_ddmFormRenderer = ddmFormRenderer;
	}

	private ConfigurationModelRetriever _configurationModelRetriever;
	private DDMFormRenderer _ddmFormRenderer;

}