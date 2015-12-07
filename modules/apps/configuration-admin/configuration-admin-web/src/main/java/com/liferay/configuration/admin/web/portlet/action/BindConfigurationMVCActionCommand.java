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
import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.configuration.admin.web.util.ConfigurationHelper;
import com.liferay.configuration.admin.web.util.ConfigurationModelToDDMFormConverter;
import com.liferay.configuration.admin.web.util.DDMFormValuesToPropertiesConverter;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.IOException;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.CONFIGURATION_ADMIN,
		"mvc.command.name=bindConfiguration"
	},
	service = MVCActionCommand.class
)
public class BindConfigurationMVCActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String factoryPid = ParamUtil.getString(actionRequest, "factoryPid");

		String pid = ParamUtil.getString(actionRequest, "pid", factoryPid);

		if (_log.isDebugEnabled()) {
			_log.debug("Binding attributes for service " + pid);
		}

		ConfigurationHelper configurationHelper = new ConfigurationHelper(
			_bundleContext, _configurationAdmin, _extendedMetaTypeService,
			themeDisplay.getLanguageId());

		ConfigurationModel configurationModel = null;

		if (Validator.isNotNull(factoryPid)) {
			configurationModel = configurationHelper.getConfigurationModel(
				factoryPid);
		}
		else {
			configurationModel = configurationHelper.getConfigurationModel(pid);
		}

		Configuration configuration = configurationHelper.getConfiguration(pid);

		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter =
				new ConfigurationModelToDDMFormConverter(
					configurationModel, themeDisplay.getLocale());

		DDMForm ddmForm = configurationModelToDDMFormConverter.getDDMForm();

		DDMFormValues ddmFormValues = getDDMFormValues(actionRequest, ddmForm);

		DDMFormValuesToPropertiesConverter ddmFormValuesToPropertiesConverter =
			new DDMFormValuesToPropertiesConverter(
				configurationModel, ddmFormValues, _jsonFactory,
				themeDisplay.getLocale());

		Dictionary<String, Object> properties =
			ddmFormValuesToPropertiesConverter.getProperties();

		properties.put(Constants.SERVICE_PID, pid);

		if (Validator.isNotNull(factoryPid)) {
			properties.put(ConfigurationAdmin.SERVICE_FACTORYPID, factoryPid);
		}

		configureTargetService(configurationModel, configuration, properties);

		return true;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	protected void configureTargetService(
			ConfigurationModel configurationModel, Configuration configuration,
			Dictionary<String, Object> properties)
		throws PortletException {

		if (_log.isDebugEnabled()) {
			_log.debug("Properties: " + properties);
		}

		try {
			if (configuration == null) {
				if (configurationModel.isFactory()) {
					if (_log.isDebugEnabled()) {
						_log.debug("Creating factory PID");
					}

					configuration =
						_configurationAdmin.createFactoryConfiguration(
							configurationModel.getID(),
							configurationModel.getBundleLocation());
				}
				else {
					if (_log.isDebugEnabled()) {
						_log.debug("Creating instance PID");
					}

					configuration = _configurationAdmin.getConfiguration(
						configurationModel.getID(),
						configurationModel.getBundleLocation());
				}
			}

			Dictionary<String, Object> configuredProperties =
				configuration.getProperties();

			if (configuredProperties == null) {
				configuredProperties = new Hashtable<>();
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Configuration properties: " +
						configuration.getProperties());
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
		ActionRequest actionRequest, DDMForm ddmForm) {

		return _ddmFormValuesFactory.create(actionRequest, ddmForm);
	}

	@Reference(unbind = "-")
	protected void setConfigAdminService(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesFactory(
		DDMFormValuesFactory ddmFormValuesFactory) {

		_ddmFormValuesFactory = ddmFormValuesFactory;
	}

	@Reference(unbind = "-")
	protected void setExtendedMetaTypeService(
		ExtendedMetaTypeService extendedMetaTypeService) {

		_extendedMetaTypeService = extendedMetaTypeService;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BindConfigurationMVCActionCommand.class);

	private BundleContext _bundleContext;
	private volatile ConfigurationAdmin _configurationAdmin;
	private volatile DDMFormValuesFactory _ddmFormValuesFactory;
	private volatile ExtendedMetaTypeService _extendedMetaTypeService;
	private volatile JSONFactory _jsonFactory;

}