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

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.internal.configuration.VulcanConfiguration;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;

/**
 * @author Javier Gamarra
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.vulcan.internal.configuration.VulcanConfiguration",
	service = ConfigurationModelListener.class
)
public class VulcanConfigurationModelListener
	implements ConfigurationModelListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

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
		catch (Exception e) {
			throw new ConfigurationModelListenerException(
				e.getMessage(), VulcanConfigurationModelListener.class,
				getClass(), dictionary);
		}
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
			String path, Dictionary<String, Object> dictionary)
		throws Exception {

		String filterString = String.format(
			"(&(path=%s)(service.factoryPid=%s))", path,
			VulcanConfiguration.class.getName());

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (configurations == null) {
			return;
		}

		Configuration configuration = configurations[0];

		if (configuration != null) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			Object servicePid = properties.get("service.pid");

			if (!servicePid.equals(dictionary.get("service.pid"))) {
				configuration.delete();
			}
		}
	}

	private BundleContext _bundleContext;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ServiceComponentRuntime _serviceComponentRuntime;

}