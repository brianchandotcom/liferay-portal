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

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tina Tian
 * @author Raymond Augé
 */
public class TemplateManagerRegistry {

	public static void destroy() {
		Map<String, TemplateManager> templateManagers = _getTemplateManagers();

		for (TemplateManager templateManager : templateManagers.values()) {
			templateManager.destroy();
		}

		templateManagers.clear();
	}

	public static void destroy(ClassLoader classLoader) {
		Map<String, TemplateManager> templateManagers = _getTemplateManagers();

		for (TemplateManager templateManager : templateManagers.values()) {
			templateManager.destroy(classLoader);
		}
	}

	public static Set<String> getSupportedLanguageTypes(String propertyKey) {
		Set<String> supportedLanguageTypes = _supportedLanguageTypes.get(
			propertyKey);

		if (supportedLanguageTypes != null) {
			return supportedLanguageTypes;
		}

		Map<String, TemplateManager> templateManagers = _getTemplateManagers();

		supportedLanguageTypes = new HashSet<String>();

		for (String templateManagerName : templateManagers.keySet()) {
			String content = PropsUtil.get(
				propertyKey, new Filter(templateManagerName));

			if (Validator.isNotNull(content)) {
				supportedLanguageTypes.add(templateManagerName);
			}
		}

		supportedLanguageTypes = Collections.unmodifiableSet(
			supportedLanguageTypes);

		_supportedLanguageTypes.put(propertyKey, supportedLanguageTypes);

		return supportedLanguageTypes;
	}
	
	public static Set<String> getRegisteredTemplateNames() {
		Map<String, TemplateManager> templateManagers = _getTemplateManagers();

		return templateManagers.keySet();
	}

	public static Template getTemplate(
			String templateManagerName, TemplateResource templateResource,
			boolean restricted)
		throws TemplateException {

		TemplateManager templateManager = _getTemplateManager(
			templateManagerName);

		return templateManager.getTemplate(templateResource, restricted);
	}

	public static Template getTemplate(
			String templateManagerName, TemplateResource templateResource,
			TemplateResource errorTemplateResource, boolean restricted)
		throws TemplateException {

		TemplateManager templateManager = _getTemplateManager(
			templateManagerName);

		return templateManager.getTemplate(
			templateResource, errorTemplateResource, restricted);
	}

	public static TemplateManager getTemplateManager(
		String templateManagerName) {

		Map<String, TemplateManager> templateManagers = _getTemplateManagers();

		return templateManagers.get(templateManagerName);
	}

	public static Set<String> getTemplateManagerNames() {
		Map<String, TemplateManager> templateManagers = _getTemplateManagers();

		return templateManagers.keySet();
	}

	public static Map<String, TemplateManager> getTemplateManagers() {
		return Collections.unmodifiableMap(_getTemplateManagers());
	}

	public static boolean hasTemplateManager(String templateManagerName) {
		Map<String, TemplateManager> templateManagers = _getTemplateManagers();

		return templateManagers.containsKey(templateManagerName);
	}

	private static TemplateManager _getTemplateManager(
			String templateManagerName)
		throws TemplateException {

		Map<String, TemplateManager> templateManagers = _getTemplateManagers();

		TemplateManager templateManager = templateManagers.get(
			templateManagerName);

		if (templateManager == null) {
			throw new TemplateException(
				"Unsupported template manager " + templateManagerName);
		}

		return templateManager;
	}

	private static Map<String, TemplateManager> _getTemplateManagers() {
		PortalRuntimePermission.checkGetBeanProperty(TemplateManagerRegistry.class);

		return _templateManagers;
	}

	private static final Map<String, Set<String>> _supportedLanguageTypes =
		new ConcurrentHashMap<String, Set<String>>();
	private static final Map<String, TemplateManager> _templateManagers =
		new ConcurrentHashMap<String, TemplateManager>();
	
	private static Log _log = LogFactoryUtil.getLog(
			TemplateManagerRegistry.class);
	
	private ServiceTracker<TemplateManager, TemplateManager> _serviceTracker;
	
	private TemplateManagerRegistry() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			TemplateManager.class,
			new TemplateManagerServiceTrackerCustomizer());

		_serviceTracker.open();

	}

	private class TemplateManagerServiceTrackerCustomizer
	implements ServiceTrackerCustomizer
		<TemplateManager, TemplateManager> {

		@Override
		public TemplateManager addingService(
			ServiceReference<TemplateManager> serviceReference) {
	
			Registry registry = RegistryUtil.getRegistry();
	
			TemplateManager templateManager = registry.getService(
				serviceReference);
	
			String name = templateManager.getName();
			
			try{
				templateManager.init();
				_templateManagers.put(name, templateManager);
			}catch(TemplateException e){
				_log.warn("unable to init " + name + " Template Manager ", e);
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