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

package com.liferay.config.admin.web.util;

import com.liferay.config.admin.web.model.ConfigurationModel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormJSONSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormFieldRenderingContext;
import com.liferay.portlet.dynamicdatamapping.render.DDMFormRendererUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.metatype.MetaTypeInformation;
import org.osgi.service.metatype.MetaTypeService;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 */
public class ConfigurationHelper {

	public ConfigurationHelper(
		BundleContext bundleContext, ConfigurationAdmin configurationAdmin,
		MetaTypeService metaTypeService, String languageId) {

		_bundleContext = bundleContext;
		_configurationAdmin = configurationAdmin;
		_metaTypeService = metaTypeService;

		_configurationModels = _getConfigurationModels(languageId);
	}

	public Configuration getConfiguration(String pid) {
		try {
			String pidFilter = _getPidFilterString(pid, false);

			Configuration[] configurations =
				_configurationAdmin.listConfigurations(pidFilter);

			if (configurations != null) {
				return configurations[0];
			}
		}
		catch (InvalidSyntaxException | IOException ise) {
			ReflectionUtil.throwException(ise);
		}

		return null;
	}

	public List<ConfigurationModel> getFactoryInstances(
			String languageId, String factoryPid)
		throws IOException {

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

		ConfigurationModel configurationModel = getConfigurationModel(
			factoryPid);

		for (Configuration configuration : configurations) {
			ConfigurationModel model = new ConfigurationModel(
				configurationModel, configuration,
				configuration.getBundleLocation(), false);

			models.add(model);
		}

		return models;
	}

	public ConfigurationModel getConfigurationModel(String pid) {
		return _configurationModels.get(pid);
	}

	public List<ConfigurationModel> getConfigurationModels() {
		return new ArrayList<>(_configurationModels.values());
	}

	public String render(
			PortletRequest portletRequest, PortletResponse portletResponse,
			ConfigurationModel model)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DDMForm ddmForm = ConfigurationConverter.convert(
			model, model.getConfiguration(), themeDisplay.getLocale());

		DDMFormFieldRenderingContext ddmFormFieldRenderingContext =
			new DDMFormFieldRenderingContext();

		ddmFormFieldRenderingContext.setHttpServletRequest(
			PortalUtil.getHttpServletRequest(portletRequest));
		ddmFormFieldRenderingContext.setHttpServletResponse(
			PortalUtil.getHttpServletResponse(portletResponse));
		ddmFormFieldRenderingContext.setLocale(themeDisplay.getLocale());
		ddmFormFieldRenderingContext.setPortletNamespace(
			portletResponse.getNamespace());
		ddmFormFieldRenderingContext.setReadOnly(false);

		String configFieldJSON = DDMFormJSONSerializerUtil.serialize(ddmForm);

		if (_log.isDebugEnabled()) {
			_log.debug("DDMForm: " + configFieldJSON);
		}

		portletRequest.setAttribute("configFieldJSON", configFieldJSON);
		portletRequest.setAttribute(
			"scopeGroupId", themeDisplay.getScopeGroupId());
		portletRequest.setAttribute("plId", themeDisplay.getPlid());

		return DDMFormRendererUtil.render(
			ddmForm, ddmFormFieldRenderingContext);
	}

	private void _collectConfigurationModels(
		Bundle bundle, Map<String, ConfigurationModel> modelMap, String locale,
		boolean factory) {

		MetaTypeInformation metaTypeInformation =
			_metaTypeService.getMetaTypeInformation(bundle);

		if (metaTypeInformation == null) {
			return;
		}

		String[] pids = null;

		if (factory) {
			pids = metaTypeInformation.getFactoryPids();
		}
		else {
			pids = metaTypeInformation.getPids();
		}

		for (String pid : pids) {
			ConfigurationModel configurationModel = _getConfigurationModel(
				bundle, pid, factory, locale);

			if (configurationModel == null) {
				continue;
			}

			modelMap.put(pid, configurationModel);
		}
	}

	private ConfigurationModel _getConfigurationModel(
		Bundle bundle, String pid, boolean factory, String locale) {

		MetaTypeInformation metaTypeInformation =
			_metaTypeService.getMetaTypeInformation(bundle);

		if (metaTypeInformation == null) {
			return null;
		}

		return new ConfigurationModel(
			metaTypeInformation.getObjectClassDefinition(pid, locale),
			getConfiguration(pid), bundle.getLocation(), factory);
	}

	private Map<String, ConfigurationModel> _getConfigurationModels(
		String locale) {

		Map<String, ConfigurationModel> configurationModelMap =
			new HashMap<String, ConfigurationModel>();

		Bundle[] bundles = _bundleContext.getBundles();

		for (Bundle bundle : bundles) {
			_collectConfigurationModels(
				bundle, configurationModelMap, locale, false);
			_collectConfigurationModels(
				bundle, configurationModelMap, locale, true);
		}

		return configurationModelMap;
	}

	private String _getPidFilterString(String pid, boolean factory) {
		StringBundler filter = new StringBundler(5);

		filter.append(StringPool.OPEN_PARENTHESIS);

		if (factory) {
			filter.append(ConfigurationAdmin.SERVICE_FACTORYPID);
		}
		else {
			filter.append(Constants.SERVICE_PID);
		}

		filter.append(StringPool.EQUAL);
		filter.append(pid);
		filter.append(StringPool.CLOSE_PARENTHESIS);

		return filter.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ConfigurationHelper.class);

	private final BundleContext _bundleContext;
	private final ConfigurationAdmin _configurationAdmin;
	private final MetaTypeService _metaTypeService;
	private final Map<String, ConfigurationModel> _configurationModels;

}