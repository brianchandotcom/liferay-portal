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
import com.liferay.configuration.admin.web.util.ConfigurationModelIterator;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.List;

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
		"mvc.command.name=/"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

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
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ConfigurationHelper configurationHelper = new ConfigurationHelper(
			_bundleContext, _configurationAdmin, _extendedMetaTypeService,
			themeDisplay.getLanguageId());

		List<String> configurationCategories =
			configurationHelper.getConfigurationCategories();

		renderRequest.setAttribute(
			ConfigurationAdminWebKeys.CONFIGURATION_CATEGORIES,
			configurationCategories);

		String configurationCategory = ParamUtil.getString(
			renderRequest, "configurationCategory");

		if (Validator.isNull(configurationCategory)) {
			configurationCategory = configurationCategories.get(0);
		}

		renderRequest.setAttribute(
			ConfigurationAdminWebKeys.CONFIGURATION_CATEGORY,
			configurationCategory);

		List<ConfigurationModel> configurationModels =
			configurationHelper.getConfigurationModels(configurationCategory);

		renderRequest.setAttribute(
			ConfigurationAdminWebKeys.CONFIGURATION_MODEL_ITERATOR,
			new ConfigurationModelIterator(configurationModels));

		return "/view.jsp";
	}

	@Reference(unbind = "-")
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Reference(unbind = "-")
	protected void setExtendedMetaTypeService(
		ExtendedMetaTypeService extendedMetaTypeService) {

		_extendedMetaTypeService = extendedMetaTypeService;
	}

	private BundleContext _bundleContext;
	private ConfigurationAdmin _configurationAdmin;
	private ExtendedMetaTypeService _extendedMetaTypeService;

}