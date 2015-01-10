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
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tina Tian
 * @author Raymond Augé
 */
public class TemplateManagerRegistry {

	public void destroy() {
		for (TemplateManager templateManager : _templateManagers.values()) {
			templateManager.destroy();
		}

		_templateManagers.clear();
	}

	public void destroy(ClassLoader classLoader) {
		for (TemplateManager templateManager : _templateManagers.values()) {
			templateManager.destroy(classLoader);
		}
	}

	public TemplateManager getTemplateManager(String templateManagerName)
		throws TemplateException {

		TemplateManager templateManager = _templateManagers.get(
			templateManagerName);

		if (templateManager == null) {
			throw new TemplateException(
				"Unsupported template manager " + templateManagerName);
		}

		return templateManager;
	}

	public Set<String> getTemplateManagerNames() {
		return Collections.unmodifiableSet(_templateManagers.keySet());
	}

	public Map<String, TemplateManager> getTemplateManagers() {
		return Collections.unmodifiableMap(_templateManagers);
	}

	public boolean hasTemplateManager(String templateManagerName) {
		return _templateManagers.containsKey(templateManagerName);
	}

	private TemplateManagerRegistry() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
				TemplateManager.class,
				new TemplateManagerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TemplateManagerRegistry.class);

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

				String name = templateManager.getName();

				try {
					templateManager.init();

					_templateManagers.put(name, templateManager);
				}
				catch (TemplateException e) {
					_log.error(
						"Unable to initialize " + name + " template manager ",
							e);
				}

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