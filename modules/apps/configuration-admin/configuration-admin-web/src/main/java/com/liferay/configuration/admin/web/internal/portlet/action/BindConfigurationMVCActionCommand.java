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
import com.liferay.configuration.admin.display.ConfigurationFormRenderer;
import com.liferay.configuration.admin.web.internal.display.context.ConfigurationScopeDisplayContext;
import com.liferay.configuration.admin.web.internal.display.context.ConfigurationScopeDisplayContextFactory;
import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.configuration.admin.web.internal.util.ConfigurationFormRendererRetriever;
import com.liferay.configuration.admin.web.internal.util.ConfigurationModelRetriever;
import com.liferay.configuration.admin.web.internal.util.ConfigurationModelToDDMFormConverter;
import com.liferay.configuration.admin.web.internal.util.DDMFormValuesToPropertiesConverter;
import com.liferay.configuration.admin.web.internal.util.ResourceBundleLoaderProvider;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.manager.ClassLoaderResourceManager;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.settings.LocationVariableResolver;
import com.liferay.portal.kernel.settings.SettingsLocatorHelper;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.Serializable;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 */
@Component(
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SITE_SETTINGS,
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.SYSTEM_SETTINGS,
		"mvc.command.name=/configuration_admin/bind_configuration"
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

		ConfigurationModel configurationModel = null;

		ConfigurationScopeDisplayContext configurationScopeDisplayContext =
			ConfigurationScopeDisplayContextFactory.create(actionRequest);

		Map<String, ConfigurationModel> configurationModels =
			_configurationModelRetriever.getConfigurationModels(
				themeDisplay.getLanguageId(),
				configurationScopeDisplayContext.getScope(),
				configurationScopeDisplayContext.getScopePK());

		if (Validator.isNotNull(factoryPid)) {
			configurationModel = configurationModels.get(factoryPid);
		}
		else {
			configurationModel = configurationModels.get(pid);
		}

		configurationModel = _getConfigurationModel(
			_configurationModelRetriever.getConfiguration(
				pid, configurationScopeDisplayContext.getScope(),
				configurationScopeDisplayContext.getScopePK()),
			configurationModel);

		if (configurationModel.isFactory() && pid.equals(factoryPid)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Writing a new factory instance for service " + pid);
			}

			configurationModel = _getConfigurationModel(
				null, configurationModel);
		}

		if (!configurationModel.hasScopeConfiguration(
				configurationScopeDisplayContext.getScope())) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Writing a new scoped instance for service ", pid,
						" at scope ",
						configurationScopeDisplayContext.getScope(),
						" for scope ID ",
						configurationScopeDisplayContext.getScopePK()));
			}

			configurationModel = _getConfigurationModel(
				null, configurationModel);
		}

		Dictionary<String, Object> properties = null;

		Map<String, Object> requestParameters = _getRequestParameters(
			actionRequest, pid);

		if (requestParameters != null) {
			properties = _toDictionary(requestParameters);
		}
		else {
			ResourceBundleLoader resourceBundleLoader =
				_resourceBundleLoaderProvider.getResourceBundleLoader(
					configurationModel.getBundleSymbolicName());

			ResourceBundle resourceBundle =
				resourceBundleLoader.loadResourceBundle(
					themeDisplay.getLocale());

			properties = _getDDMRequestParameters(
				actionRequest, configurationModel, resourceBundle);
		}

		properties.put(Constants.SERVICE_PID, pid);

		if (Validator.isNotNull(factoryPid)) {
			properties.put(ConfigurationAdmin.SERVICE_FACTORYPID, factoryPid);
		}

		try {
			_configureTargetService(
				configurationModel, properties,
				configurationScopeDisplayContext.getScope(),
				configurationScopeDisplayContext.getScopePK());

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (Validator.isNotNull(redirect)) {
				actionResponse.sendRedirect(redirect);
			}
		}
		catch (ConfigurationModelListenerException
					configurationModelListenerException) {

			SessionErrors.add(
				actionRequest, ConfigurationModelListenerException.class,
				configurationModelListenerException);

			actionResponse.setRenderParameter(
				"mvcRenderCommandName",
				"/configuration_admin/edit_configuration");
		}
		catch (IOException ioException) {
			throw new PortletException(ioException);
		}

		return true;
	}

	private void _configureTargetService(
			ConfigurationModel configurationModel,
			Dictionary<String, Object> properties,
			ExtendedObjectClassDefinition.Scope scope, Serializable scopePK)
		throws ConfigurationModelListenerException, PortletException {

		if (_log.isDebugEnabled()) {
			_log.debug("Properties: " + properties);
		}

		try {
			Configuration configuration = configurationModel.getConfiguration();

			boolean scoped = !scope.equals(
				ExtendedObjectClassDefinition.Scope.SYSTEM.getValue());

			if ((configuration == null) ||
				!configurationModel.hasScopeConfiguration(scope)) {

				if (configurationModel.isFactory() || scoped) {
					if (_log.isDebugEnabled()) {
						_log.debug("Creating factory PID");
					}

					String pid = configurationModel.getID();

					if (!configurationModel.isFactory() && scoped) {
						pid = pid + ".scoped";
					}

					configuration =
						_configurationAdmin.createFactoryConfiguration(
							pid, StringPool.QUESTION);
				}
				else {
					if (_log.isDebugEnabled()) {
						_log.debug("Creating instance PID");
					}

					configuration = _configurationAdmin.getConfiguration(
						configurationModel.getID(), StringPool.QUESTION);
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

			Enumeration<String> enumeration = properties.keys();

			while (enumeration.hasMoreElements()) {
				String key = enumeration.nextElement();

				Object value = properties.get(key);

				if (Objects.equals(value, Portal.TEMP_OBFUSCATION_VALUE)) {
					continue;
				}

				configuredProperties.put(key, value);
			}

			if (scoped) {
				configuredProperties.put(scope.getPropertyKey(), scopePK);
			}

			// LPS-69521

			if (configurationModel.isFactory()) {
				configuredProperties.put(
					"configuration.cleaner.ignore", "true");
			}

			configuration.update(configuredProperties);
		}
		catch (ConfigurationModelListenerException
					configurationModelListenerException) {

			throw configurationModelListenerException;
		}
		catch (IOException ioException) {
			throw new PortletException(ioException);
		}
	}

	private ConfigurationModel _getConfigurationModel(
		Configuration configuration, ConfigurationModel configurationModel) {

		return new ConfigurationModel(
			configurationModel.getBundleLocation(),
			configurationModel.getBundleSymbolicName(),
			configurationModel.getClassLoader(), configuration,
			configurationModel.getExtendedObjectClassDefinition(),
			configurationModel.isFactory());
	}

	private DDMFormValues _getDDMFormValues(
		ActionRequest actionRequest, DDMForm ddmForm) {

		return _ddmFormValuesFactory.create(actionRequest, ddmForm);
	}

	private Dictionary<String, Object> _getDDMRequestParameters(
		ActionRequest actionRequest, ConfigurationModel configurationModel,
		ResourceBundle resourceBundle) {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ConfigurationModelToDDMFormConverter
			configurationModelToDDMFormConverter =
				new ConfigurationModelToDDMFormConverter(
					configurationModel, themeDisplay.getLocale(),
					resourceBundle);

		DDMFormValues ddmFormValues = _getDDMFormValues(
			actionRequest, configurationModelToDDMFormConverter.getDDMForm());

		LocationVariableResolver locationVariableResolver =
			new LocationVariableResolver(
				new ClassLoaderResourceManager(
					configurationModel.getClassLoader()),
				_settingsLocatorHelper);

		DDMFormValuesToPropertiesConverter ddmFormValuesToPropertiesConverter =
			new DDMFormValuesToPropertiesConverter(
				configurationModel, ddmFormValues, _jsonFactory,
				themeDisplay.getLocale(), locationVariableResolver);

		return ddmFormValuesToPropertiesConverter.getProperties();
	}

	private Map<String, Object> _getRequestParameters(
		ActionRequest actionRequest, String pid) {

		ConfigurationFormRenderer configurationFormRenderer =
			_configurationFormRendererRetriever.getConfigurationFormRenderer(
				pid);

		return configurationFormRenderer.getRequestParameters(
			_portal.getHttpServletRequest(actionRequest));
	}

	private Dictionary<String, Object> _toDictionary(
		Map<String, Object> requestParameters) {

		Dictionary<String, Object> properties = new Hashtable<>();

		for (Map.Entry<String, Object> entry : requestParameters.entrySet()) {
			properties.put(entry.getKey(), entry.getValue());
		}

		return properties;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BindConfigurationMVCActionCommand.class);

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationFormRendererRetriever
		_configurationFormRendererRetriever;

	@Reference(target = "(!(filter.visibility=*))")
	private ConfigurationModelRetriever _configurationModelRetriever;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceBundleLoaderProvider _resourceBundleLoaderProvider;

	@Reference
	private SettingsLocatorHelper _settingsLocatorHelper;

}