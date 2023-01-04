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

package com.liferay.portal.vulcan.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.internal.configuration.VulcanCompanyConfiguration;
import com.liferay.portal.vulcan.internal.configuration.VulcanConfiguration;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;

/**
 * @author Carlos Correa
 */
public class BaseConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> dictionary) {
		Collection<ComponentDescriptionDTO> componentDescriptionDTOs =
			_serviceComponentRuntime.getComponentDescriptionDTOs(
				_bundleContext.getBundles());

		for (ComponentDescriptionDTO componentDescriptionDTO :
				componentDescriptionDTOs) {

			Map<String, Object> properties = componentDescriptionDTO.properties;

			Set<Map.Entry<String, Object>> entries = properties.entrySet();

			for (Map.Entry<String, Object> entry : entries) {
				String key = entry.getKey();

				Object value = entry.getValue();

				if (key.equals("osgi.jaxrs.application.base") &&
					value.equals(dictionary.get("path"))) {

					if ((Boolean)dictionary.get("restEnabled")) {
						_serviceComponentRuntime.enableComponent(
							componentDescriptionDTO);
					}
					else {
						_serviceComponentRuntime.disableComponent(
							componentDescriptionDTO);
					}

					break;
				}
			}
		}
	}

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> dictionary)
		throws ConfigurationModelListenerException {

		try {
			String path = (String)dictionary.get("path");

			_validatePathExists(path);

			_validateUniqueConfiguration(path, dictionary);
		}
		catch (Exception exception) {
			throw new ConfigurationModelListenerException(
				exception.getMessage(), BaseConfigurationModelListener.class,
				getClass(), dictionary);
		}
	}

	protected void init(
		BundleContext bundleContext, ConfigurationAdmin configurationAdmin,
		ServiceComponentRuntime serviceComponentRuntime) {

		_bundleContext = bundleContext;
		_configurationAdmin = configurationAdmin;
		_serviceComponentRuntime = serviceComponentRuntime;
	}

	private void _validatePathExists(String path) throws Exception {
		if (Validator.isNotNull(path)) {
			return;
		}

		throw new Exception(
			ResourceBundleUtil.getString(
				ResourceBundleUtil.getBundle(
					"content.Language",
					LocaleThreadLocal.getThemeDisplayLocale(), getClass()),
				"path-cannot-be-empty"));
	}

	private void _validateUniqueConfiguration(
			Dictionary<String, Object> dictionary, String filterString)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (configurations == null) {
			return;
		}

		for (Configuration configuration : configurations) {
			if (configuration == null) {
				continue;
			}

			Dictionary<String, Object> properties =
				configuration.getProperties();

			Object servicePid = properties.get("service.pid");

			if (!servicePid.equals(dictionary.get("service.pid"))) {
				configuration.delete();
			}
		}
	}

	private void _validateUniqueConfiguration(
			String path, Dictionary<String, Object> dictionary)
		throws Exception {

		String filterString = String.format(
			"(&(path=%s)(service.factoryPid=%s))", path,
			VulcanConfiguration.class.getName());

		_validateUniqueConfiguration(dictionary, filterString);

		if (dictionary.get("companyId") == null) {
			return;
		}

		filterString = String.format(
			"(&(path=%s)(service.factoryPid=%s)(%s=%d))", path,
			VulcanCompanyConfiguration.class.getName(),
			ExtendedObjectClassDefinition.Scope.COMPANY.getPropertyKey(),
			GetterUtil.getLong(dictionary.get("companyId")));

		_validateUniqueConfiguration(dictionary, filterString);
	}

	private BundleContext _bundleContext;
	private ConfigurationAdmin _configurationAdmin;
	private ServiceComponentRuntime _serviceComponentRuntime;

}