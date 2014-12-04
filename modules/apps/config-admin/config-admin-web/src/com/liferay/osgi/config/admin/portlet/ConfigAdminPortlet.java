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

package com.liferay.osgi.config.admin.portlet;

import com.liferay.osgi.config.admin.model.ConfigurationModel;
import com.liferay.osgi.config.admin.util.ConfigurationHelper;
import com.liferay.osgi.config.admin.util.ConfigurationIterator;
import com.liferay.osgi.config.admin.util.FormBuilder;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.freemarker.FreeMarkerPortlet;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.MetaTypeService;

/**
 * @author Kamesh Sampath
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
	public void doView(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = themeDisplay.getLanguageId();

		try {
			ConfigurationHelper modelHelper = new ConfigurationHelper(
				_bundleContext, _configurationAdmin, _metaTypeService,
				languageId);

			List<ConfigurationModel> models = modelHelper.getModels();

			renderRequest.setAttribute(
				"modelIterator", new ConfigurationIterator(models));

			super.doView(renderRequest, renderResponse);
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
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

	protected URL getResourceURL(String path) {
		if (path.indexOf(StringPool.SLASH) != 0) {
			path = StringPool.SLASH.concat(path);
		}

		Bundle bundle = _bundleContext.getBundle();

		return bundle.getEntry("META-INF/resources".concat(path));
	}

	@Override
	protected void include(
			String path, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws IOException, PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = themeDisplay.getLanguageId();

		String factoryPid = ParamUtil.getString(renderRequest, "factoryPid");
		String pid = ParamUtil.getString(renderRequest, "pid");
		String viewType = ParamUtil.getString(renderRequest, "viewType");

		if (Validator.isNull(pid) && Validator.isNotNull(factoryPid)) {
			renderRequest.setAttribute("pid", StringPool.BLANK);
			renderRequest.setAttribute("factoryPid", factoryPid);
		}
		else if (Validator.isNotNull(pid) && Validator.isNotNull(factoryPid)) {
			renderRequest.setAttribute("pid", pid);
			renderRequest.setAttribute("factoryPid", factoryPid);
		}
		else {
			renderRequest.setAttribute("pid", pid);
			renderRequest.setAttribute("factoryPid", StringPool.BLANK);
		}

		ConfigurationHelper modelHelper = new ConfigurationHelper(
			_bundleContext, _configurationAdmin, _metaTypeService, languageId);

		if ("/edit_attributes.ftl".equals(path)) {
			ConfigurationModel model = null;

			if (Validator.isNotNull(factoryPid)) {
				model = modelHelper.getModel(factoryPid);
			}
			else {
				model = modelHelper.getModel(pid);
			}

			if (model != null) {
				Configuration configuration = modelHelper.getConfiguration(pid);

				if (configuration != null) {
					model.setConfiguration(configuration);
				}

				renderRequest.setAttribute("model", model);

				renderRequest.setAttribute(
					"formBuilder", new FormBuilder(model, themeDisplay));
			}
		}
		else if ("factoryInstances".equals(viewType)) {
			ConfigurationModel model = modelHelper.getModel(factoryPid);

			renderRequest.setAttribute("factoryOCD", model);

			List<ConfigurationModel> models = getFactoryInstances(
				modelHelper, languageId, factoryPid);

			renderRequest.setAttribute(
				"modelIterator", new ConfigurationIterator(models));
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

	protected List<ConfigurationModel> getFactoryInstances(
			ConfigurationHelper modelHelper, String languageId,
			String factoryPid)
		throws IOException, PortletException {

		List<ConfigurationModel> models = new ArrayList<ConfigurationModel>();

		StringBundler filter = new StringBundler(5);

		filter.append(StringPool.OPEN_PARENTHESIS);
		filter.append(ConfigurationAdmin.SERVICE_FACTORYPID);
		filter.append(StringPool.EQUAL);
		filter.append(factoryPid);
		filter.append(StringPool.CLOSE_PARENTHESIS);

		Configuration[] configurations = null;

		try {
			configurations = _configurationAdmin.listConfigurations(
				filter.toString());
		}
		catch (InvalidSyntaxException ise) {
			ReflectionUtil.throwException(ise);
		}

		if (configurations == null) {
			return models;
		}

		ConfigurationModel factoryOCD = modelHelper.getModel(factoryPid);

		for (Configuration configuration : configurations) {
			ConfigurationModel model = new ConfigurationModel(
				factoryOCD, configuration.getBundleLocation(), false);

			model.setConfiguration(configuration);

			models.add(model);
		}

		return models;
	}

	private BundleContext _bundleContext;
	private ConfigurationAdmin _configurationAdmin;
	private MetaTypeService _metaTypeService;

}