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

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.collections.StringServiceRegistrationMap;

import java.util.List;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class TemplateManagerRegistry {

	public void destroy() {
		Set<String> keySet = _serviceRegistrations.keySet();

		for (String key : keySet) {
			unregister(key, null);
		}
	}

	public void destroy(ClassLoader classLoader) {
		Set<String> keySet = _serviceRegistrations.keySet();

		for (String key : keySet) {
			unregister(key, classLoader);
		}
	}

	public Set<String> getRegisteredTemplateNames() {
		return _templateManagers.keySet();
	}

	public TemplateManager getTemplateManager(String templateManagerName)
		throws TemplateException {

		TemplateManager templateManager = _templateManagers.getService(
			templateManagerName);

		if (templateManager == null) {
			throw new TemplateException(
				"Unsupported template manager " + templateManagerName);
		}

		return templateManager;
	}

	public void register(TemplateManager templateManager)
		throws TemplateException {

		templateManager.init();

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<TemplateManager> serviceRegistration =
			registry.registerService(TemplateManager.class, templateManager);

		_serviceRegistrations.put(
			templateManager.getName(), serviceRegistration);
	}

	public void setTemplateManagers(List<TemplateManager> templateManagers) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		for (TemplateManager templateManager : templateManagers) {
			try {
				register(templateManager);
			} catch (TemplateException te) {
				_log.error(
					"Unable to register template manager " +
						templateManager.getName(), te);
			}
		}
	}

	public void unregister(
		String templateManagerName, ClassLoader classLoader) {

		TemplateManager templateManager = _templateManagers.getService(
			templateManagerName);

		if (templateManager == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"No template manager is registered under the name " +
						templateManagerName);
			}

			return;
		}

		if (classLoader != null) {
			templateManager.destroy(classLoader);
		}
		else {
			templateManager.destroy();
		}

		ServiceRegistration<TemplateManager> serviceRegistration =
			_serviceRegistrations.remove(templateManager.getName());

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private TemplateManagerRegistry() {
		_templateManagers.open();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TemplateManagerRegistry.class);

	private final StringServiceRegistrationMap<TemplateManager>
		_serviceRegistrations = new StringServiceRegistrationMap<>();
	private final ServiceTrackerMap<String, TemplateManager> _templateManagers =
		ServiceTrackerCollections.singleValueMap(
			TemplateManager.class, null,
			new TemplateManagerServiceReferenceMapper());

	private static class TemplateManagerServiceReferenceMapper
		implements ServiceReferenceMapper<String, TemplateManager> {

		@Override
		public void map(
			ServiceReference<TemplateManager> serviceReference,
			Emitter<String> emitter) {

			Registry registry = RegistryUtil.getRegistry();

			TemplateManager templateManager = registry.getService(
				serviceReference);

			emitter.emit(templateManager.getName());
		}

	}

}