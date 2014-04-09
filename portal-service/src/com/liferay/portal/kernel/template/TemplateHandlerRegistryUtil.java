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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.util.PortalUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.StringServiceRegistrationMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Juan Fernández
 */
@ProviderType
public class TemplateHandlerRegistryUtil {

	public static long[] getClassNameIds() {
		long[] classNameIds = new long[_instance._templateHandlers.size()];

		int i = 0;

		for (Map.Entry<String, TemplateHandler> entry :
				_instance._templateHandlers.entrySet()) {

			TemplateHandler templateHandler = entry.getValue();

			classNameIds[i++] = PortalUtil.getClassNameId(
				templateHandler.getClassName());
		}

		return classNameIds;
	}

	public static TemplateHandler getTemplateHandler(long classNameId) {
		String className = PortalUtil.getClassName(classNameId);

		return _instance._templateHandlers.get(className);
	}

	public static TemplateHandler getTemplateHandler(String className) {
		return _instance._templateHandlers.get(className);
	}

	public static List<TemplateHandler> getTemplateHandlers() {
		List<TemplateHandler> templateHandlers = new ArrayList<TemplateHandler>(
			_instance._templateHandlers.values());

		return Collections.unmodifiableList(templateHandlers);
	}

	public static void register(TemplateHandler templateHandler) {
		_instance._register(templateHandler);
	}

	public static void unregister(TemplateHandler templateHandler) {
		_instance._unregister(templateHandler);
	}

	private TemplateHandlerRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			TemplateHandler.class,
			new TemplateHandlerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private void _register(TemplateHandler templateHandler) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<TemplateHandler> serviceRegistration =
			registry.registerService(TemplateHandler.class, templateHandler);

		_serviceRegistrations.put(
			templateHandler.getClassName(), serviceRegistration);
	}

	private void _unregister(TemplateHandler templateHandler) {
		ServiceRegistration<TemplateHandler> serviceRegistration =
			_serviceRegistrations.remove(templateHandler.getClassName());

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static TemplateHandlerRegistryUtil _instance =
		new TemplateHandlerRegistryUtil();

	private StringServiceRegistrationMap<TemplateHandler>
		_serviceRegistrations =
			new StringServiceRegistrationMap<TemplateHandler>();
	private ServiceTracker<TemplateHandler, TemplateHandler> _serviceTracker;
	private Map<String, TemplateHandler> _templateHandlers =
		new ConcurrentHashMap<String, TemplateHandler>();

	private class TemplateHandlerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<TemplateHandler, TemplateHandler> {

		@Override
		public TemplateHandler addingService(
			ServiceReference<TemplateHandler> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			TemplateHandler templateHandler = registry.getService(
				serviceReference);

			_templateHandlers.put(
				templateHandler.getClassName(), templateHandler);

			return templateHandler;
		}

		@Override
		public void modifiedService(
			ServiceReference<TemplateHandler> serviceReference,
			TemplateHandler templateHandler) {
		}

		@Override
		public void removedService(
			ServiceReference<TemplateHandler> serviceReference,
			TemplateHandler templateHandler) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_templateHandlers.remove(templateHandler.getClassName());
		}

	}

}