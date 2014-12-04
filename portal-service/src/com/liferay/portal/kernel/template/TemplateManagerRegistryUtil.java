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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.StringServiceRegistrationMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Marcellus Tavares
 */
public class TemplateManagerRegistryUtil {

	public static TemplateManager getTemplateManager(String templateManagerName)
		throws TemplateException {

		TemplateManager templateManager = _instance._templateManagers.get(
			templateManagerName);

		if (templateManager == null) {
			throw new TemplateException(
				"Unsupported template manager " + templateManagerName);
		}

		return templateManager;
	}

	public static Map<String, TemplateManager> getTemplateManagers() {
		return Collections.unmodifiableMap(_instance._templateManagers);
	}

	public static void register(TemplateManager templateManager)
		throws TemplateException {

		templateManager.init();

		_instance._register(templateManager);
	}

	public static void unregister(String templateManagerName) {
		TemplateManager templateManager = _instance._templateManagers.get(
			templateManagerName);

		templateManager.destroy();

		_instance._unregister(templateManager);
	}

	public void setTemplateManagers(List<TemplateManager> templateManagers) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		for (TemplateManager templateManager : templateManagers) {
			_instance._register(templateManager);
		}
	}

	private TemplateManagerRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			TemplateManager.class,
			new TemplateManagerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private void _register(TemplateManager templateManager) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<TemplateManager> serviceRegistration =
			registry.registerService(TemplateManager.class, templateManager);

		_serviceRegistrations.put(
			templateManager.getName(), serviceRegistration);
	}

	private void _unregister(TemplateManager templateManager) {
		ServiceRegistration<TemplateManager> serviceRegistration =
			_serviceRegistrations.remove(templateManager.getName());

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final TemplateManagerRegistryUtil _instance =
		new TemplateManagerRegistryUtil();

	private final StringServiceRegistrationMap<TemplateManager>
		_serviceRegistrations =
			new StringServiceRegistrationMap<TemplateManager>();
	private final ServiceTracker<TemplateManager, TemplateManager>
		_serviceTracker;
	private final Map<String, TemplateManager> _templateManagers =
		new ConcurrentHashMap<String, TemplateManager>();

	private class TemplateManagerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<TemplateManager, TemplateManager> {

		@Override
		public TemplateManager addingService(
			ServiceReference<TemplateManager> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			TemplateManager templateManager = registry.getService(
				serviceReference);

			_templateManagers.put(templateManager.getName(), templateManager);

			return templateManager;
		}

		@Override
		public void modifiedService(
			ServiceReference<TemplateManager> serviceReference,
			TemplateManager templateManager) {
		}

		@Override
		public void removedService(
			ServiceReference<TemplateManager> serviceReference,
			TemplateManager templateManager) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_templateManagers.remove(templateManager.getName());
		}

	}

}