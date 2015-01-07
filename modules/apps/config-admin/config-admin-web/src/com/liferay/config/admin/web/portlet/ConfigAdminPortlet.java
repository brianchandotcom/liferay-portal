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

package com.liferay.config.admin.web.portlet;

import com.liferay.config.admin.web.model.ConfigurationModel;
import com.liferay.config.admin.web.util.ConfigurationHelper;
import com.liferay.config.admin.web.util.ConfigurationModelIterator;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.ParamUtil;
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
import org.osgi.service.metatype.MetaTypeService;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.css-class-wrapper=portlet-config-admin",
		"com.liferay.portlet.control-panel-entry-category=configuration",
		"com.liferay.portlet.control-panel-entry-weight=11",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.instanceable=false",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.ftl",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ConfigAdminPortlet extends FreeMarkerPortlet {

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
		String pid = ParamUtil.getString(renderRequest, "pid", factoryPid);
		String viewType = ParamUtil.getString(renderRequest, "viewType");

		ConfigurationHelper configurationHelper = new ConfigurationHelper(
			_bundleContext, _configurationAdmin, _metaTypeService,
			themeDisplay.getLanguageId());

		if (path.equals("/edit_configuration.ftl")) {
			ConfigurationModel configurationModel =
				configurationHelper.getConfigurationModel(pid);

			if (configurationModel != null) {
				configurationModel = new ConfigurationModel(
					configurationModel.getObjectClassDefinition(),
					configurationHelper.getConfiguration(pid),
					configurationModel.getBundleLocation(),
					configurationModel.isFactory());
			}

			renderRequest.setAttribute(
				"configurationHelper", configurationHelper);
			renderRequest.setAttribute("factoryPid", factoryPid);
			renderRequest.setAttribute("model", configurationModel);
			renderRequest.setAttribute("pid", pid);
		}
		else if (viewType.equals("factoryInstances")) {
			ConfigurationModel factoryModel =
				configurationHelper.getConfigurationModel(factoryPid);

			renderRequest.setAttribute("factoryModel", factoryModel);

			List<ConfigurationModel> configurationModels =
				configurationHelper.getFactoryInstances(
					themeDisplay.getLanguageId(), factoryPid);

			renderRequest.setAttribute(
				"modelIterator",
				new ConfigurationModelIterator(configurationModels));
		}
		else {
			List<ConfigurationModel> configurationModels =
				configurationHelper.getConfigurationModels();

			renderRequest.setAttribute(
				"modelIterator",
				new ConfigurationModelIterator(configurationModels));
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
	protected void setMetaTypeService(MetaTypeService metaTypeService) {
		_metaTypeService = metaTypeService;
	}

	private BundleContext _bundleContext;
	private ConfigurationAdmin _configurationAdmin;
	private MetaTypeService _metaTypeService;

}