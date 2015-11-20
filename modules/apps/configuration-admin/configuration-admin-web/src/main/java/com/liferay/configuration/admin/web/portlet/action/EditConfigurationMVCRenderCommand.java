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

import com.liferay.configuration.admin.ExtendedMetaTypeService;
import com.liferay.configuration.admin.web.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.constants.ConfigurationAdminWebKeys;
import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.configuration.admin.web.util.ConfigurationHelper;
import com.liferay.configuration.admin.web.util.DDMFormRendererHelper;
import com.liferay.dynamic.data.mapping.constants.DDMWebKeys;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.CONFIGURATION_ADMIN,
		"mvc.command.name=/edit-configuration"
	},
	service = MVCRenderCommand.class
)
public class EditConfigurationMVCRenderCommand implements MVCRenderCommand {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	public void deactivate() {
		_bundleContext = null;
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ConfigurationHelper configurationHelper = new ConfigurationHelper(
			_bundleContext, _configurationAdmin, _extendedMetaTypeService,
			themeDisplay.getLanguageId());

		String factoryPid = ParamUtil.getString(renderRequest, "factoryPid");
		String pid = ParamUtil.getString(renderRequest, "pid", factoryPid);

		ConfigurationModel configurationModel =
			configurationHelper.getConfigurationModel(pid);

		if ((configurationModel == null) && Validator.isNotNull(factoryPid)) {
			configurationModel = configurationHelper.getConfigurationModel(
				factoryPid);
		}

		if (configurationModel != null) {
			configurationModel = new ConfigurationModel(
				configurationModel.getExtendedObjectClassDefinition(),
				configurationHelper.getConfiguration(pid),
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
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Reference(unbind = "-")
	protected void setDDMFormRenderer(DDMFormRenderer ddmFormRenderer) {
		_ddmFormRenderer = ddmFormRenderer;
	}

	@Reference(unbind = "-")
	protected void setExtendedMetaTypeService(
		ExtendedMetaTypeService extendedMetaTypeService) {

		_extendedMetaTypeService = extendedMetaTypeService;
	}

	private BundleContext _bundleContext;
	private ConfigurationAdmin _configurationAdmin;
	private DDMFormRenderer _ddmFormRenderer;
	private ExtendedMetaTypeService _extendedMetaTypeService;

}