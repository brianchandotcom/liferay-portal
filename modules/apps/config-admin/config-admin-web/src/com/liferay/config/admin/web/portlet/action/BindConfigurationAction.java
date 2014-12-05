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

package com.liferay.config.admin.web.portlet.action;

import com.liferay.config.admin.web.model.ConfigurationModel;
import com.liferay.config.admin.web.util.ConfigurationConverter;
import com.liferay.config.admin.web.util.ConfigurationHelper;
import com.liferay.config.admin.web.util.ConfigurationProperties;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.ActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormValuesJSONDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormFieldValue;
import com.liferay.portlet.dynamicdatamapping.storage.DDMFormValues;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.MetaTypeService;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 */
@Component(
	immediate = true, service = ActionCommand.class,
	property = {
		"action.command.name=bindConfiguration",
		"javax.portlet.name=com_liferay_config_admin_web_portlet_" +
			"ConfigAdminPortlet"
	}
)
public class BindConfigurationAction implements ActionCommand {

	@Override
	public boolean processCommand(
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String languageId = themeDisplay.getLanguageId();
		Locale locale = themeDisplay.getLocale();

		String factoryPid = ParamUtil.getString(portletRequest, "factoryPid");
		String pid = ParamUtil.getString(portletRequest, "pid", factoryPid);

		if (_log.isDebugEnabled()) {
			_log.debug("Binding attributes for service: " + pid);
		}

		ConfigurationHelper configurationHelper = new ConfigurationHelper(
			_bundleContext, _configurationAdmin, _metaTypeService,
			languageId);

		ConfigurationModel model = configurationHelper.getModel(pid);

		Configuration configuration = configurationHelper.getConfiguration(pid);

		DDMFormValues ddmFormValues = getDDMFormValues(
			portletRequest, model, configuration, pid, locale);

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		Dictionary<String, Object> properties =
			ConfigurationProperties.load(
				model, ddmFormFieldValuesMap, locale);

		properties.put(Constants.SERVICE_PID, pid);

		if (Validator.isNotNull(factoryPid)) {
			properties.put(
				ConfigurationAdmin.SERVICE_FACTORYPID, factoryPid);
		}

		configureTargetService(model, configuration, properties);

		return true;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	protected void configureTargetService(
			ConfigurationModel model, Configuration configuration,
			Dictionary<String, Object> properties)
		throws PortletException {

		if (_log.isDebugEnabled()) {
			_log.debug("Props to be bound: " + properties);
		}

		try {
			if (configuration == null) {
				if (model.isFactory()) {
					if (_log.isDebugEnabled()) {
						_log.debug("Creating factory pid");
					}

					configuration =
						_configurationAdmin.createFactoryConfiguration(
							model.getID(), model.getBundleLocation());
				}
				else {
					if (_log.isDebugEnabled()) {
						_log.debug("Creating instance pid");
					}

					configuration = _configurationAdmin.getConfiguration(
						model.getID(), model.getBundleLocation());
				}
			}

			Dictionary<String, Object> configuredProperties =
				configuration.getProperties();

			if (configuredProperties == null) {
				configuredProperties = new Hashtable<>();
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Config Properties: " + configuration.getProperties());
			}

			Enumeration<String> keys = properties.keys();

			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				Object value = properties.get(key);

				configuredProperties.put(key, value);
			}

			configuration.update(configuredProperties);
		}
		catch (IOException e) {
			throw new PortletException(e);
		}
	}

	protected DDMFormValues getDDMFormValues(
		PortletRequest portletRequest,
		ObjectClassDefinition objectClassDefinition,
		Configuration configuration, String pid, Locale locale) {

		DDMForm ddmForm = ConfigurationConverter.convert(
			objectClassDefinition, configuration, locale);

		String serializedDDMFormValues = ParamUtil.getString(
			portletRequest, "configFields");

		try {
			return DDMFormValuesJSONDeserializerUtil.deserialize(
				ddmForm, serializedDDMFormValues);
		}
		catch (PortalException pe) {
			return ReflectionUtil.throwException(pe);
		}
	}

	@Reference(unbind = "-")
	protected void setConfigAdminService(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Reference(unbind = "-")
	protected void setMetaTypeService(MetaTypeService metaTypeService) {
		_metaTypeService = metaTypeService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BindConfigurationAction.class);

	private BundleContext _bundleContext;
	private ConfigurationAdmin _configurationAdmin;
	private MetaTypeService _metaTypeService;

}