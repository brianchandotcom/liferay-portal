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

package com.liferay.configuration.admin.web.portlet;

import com.liferay.configuration.admin.ExtendedMetaTypeService;
import com.liferay.configuration.admin.web.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.configuration.admin.web.util.ConfigurationHelper;
import com.liferay.configuration.admin.web.util.ConfigurationModelIterator;
import com.liferay.configuration.admin.web.util.DDMFormRendererHelper;
import com.liferay.dynamic.data.mapping.constants.DDMWebKeys;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.freemarker.FreeMarkerPortlet;

import java.io.IOException;

import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-configuration-admin",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.display-name=Configuration Admin",
		"javax.portlet.info.keywords=osgi,configuration,admin",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.ftl",
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.CONFIGURATION_ADMIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ConfigurationAdminPortlet extends FreeMarkerPortlet {

	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	public void deactivate() {
		_bundleContext = null;
	}

	@Override
	public void destroy() {
		PortletContext portletContext = getPortletContext();

		ServletContextPool.remove(portletContext.getPortletContextName());

		super.destroy();
	}

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		super.init(portletConfig);

		LiferayPortletConfig liferayPortletConfig =
			(LiferayPortletConfig)portletConfig;

		com.liferay.portal.model.Portlet portlet =
			liferayPortletConfig.getPortlet();

		PortletApp portletApp = portlet.getPortletApp();

		ServletContextPool.put(
			portletApp.getServletContextName(), portletApp.getServletContext());
	}

	@Override
	protected void include(
			String path, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String factoryPid = ParamUtil.getString(renderRequest, "factoryPid");

		ConfigurationHelper configurationHelper = new ConfigurationHelper(
			_bundleContext, _configurationAdmin, _extendedMetaTypeService,
			themeDisplay.getLanguageId());

		if (path.equals("/edit_configuration.ftl")) {
			String pid = ParamUtil.getString(renderRequest, "pid", factoryPid);

			ConfigurationModel configurationModel =
				configurationHelper.getConfigurationModel(pid);

			if ((configurationModel == null) &&
				Validator.isNotNull(factoryPid)) {

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
				"configurationModel", configurationModel);
			renderRequest.setAttribute("factoryPid", factoryPid);
			renderRequest.setAttribute("pid", pid);

			DDMFormRendererHelper ddmFormRendererHelper =
				new DDMFormRendererHelper(
					renderRequest, renderResponse, configurationModel,
					_ddmFormRenderer);

			renderRequest.setAttribute(
				DDMWebKeys.DYNAMIC_DATA_MAPPING_FORM_HTML,
				ddmFormRendererHelper.getDDMFormHTML());
		}
		else {
			String viewType = ParamUtil.getString(renderRequest, "viewType");

			if (viewType.equals("factoryInstances")) {
				List<ConfigurationModel> configurationModels =
					configurationHelper.getFactoryInstances(
						themeDisplay.getLanguageId(), factoryPid);

				renderRequest.setAttribute(
					"configurationModelIterator",
					new ConfigurationModelIterator(configurationModels));

				ConfigurationModel factoryConfigurationModel =
					configurationHelper.getConfigurationModel(factoryPid);

				renderRequest.setAttribute(
					"factoryConfigurationModel", factoryConfigurationModel);
			}
			else {
				List<String> configurationCategories =
					configurationHelper.getConfigurationCategories();

				renderRequest.setAttribute(
					"configurationCategories", configurationCategories);

				String curConfigurationCategory = ParamUtil.getString(
					renderRequest, "curConfigurationCategory");

				if (Validator.isNull(curConfigurationCategory)) {
					curConfigurationCategory = configurationCategories.get(0);
				}

				List<ConfigurationModel> configurationModels =
					configurationHelper.getConfigurationModels(
						curConfigurationCategory);

				renderRequest.setAttribute(
					"configurationModelIterator",
					new ConfigurationModelIterator(configurationModels));
			}
		}

		include(
			path, renderRequest, renderResponse, PortletRequest.RENDER_PHASE);
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