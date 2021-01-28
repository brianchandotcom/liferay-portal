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
public class TemplateManagerUtil {

	public static void destroy() {
		_templateManagerUtil._destroy();
	}

	public static void destroy(ClassLoader classLoader) {
		_templateManagerUtil._destroy(classLoader);
	}

	public static Set<String> getSupportedLanguageTypes(String propertyKey) {
		return _templateManagerUtil._getSupportedLanguageTypes(propertyKey);
	}

	public static Template getTemplate(
			String templateManagerName, TemplateResource templateResource,
			boolean restricted)
		throws TemplateException {

		return _templateManagerUtil._getTemplate(
			templateManagerName, templateResource, restricted);
	}

	public static TemplateManager getTemplateManager(
		String templateManagerName) {

		return _templateManagerUtil._getTemplateManager(templateManagerName);
	}

	public static Set<String> getTemplateManagerNames() {
		return _templateManagerUtil._getTemplateManagerNames();
	}

	public static Map<String, TemplateManager> getTemplateManagers() {
		return _templateManagerUtil._getTemplateManagers();
	}

	public static boolean hasTemplateManager(String templateManagerName) {
		return _templateManagerUtil._hasTemplateManager(templateManagerName);
	}

	private TemplateManagerUtil() {
		Registry registry = RegistryUtil.getRegistry();

		com.liferay.registry.Filter filter = registry.getFilter(
			"(&(language.type=*)(objectClass=" +
				TemplateManager.class.getName() + "))");

		_serviceTracker = registry.trackServices(
			filter, new TemplateManagerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private void _destroy() {
		for (TemplateManager templateManager : _templateManagers.values()) {
			templateManager.destroy();
		}

		_templateManagers.clear();
	}

	private void _destroy(ClassLoader classLoader) {
		for (TemplateManager templateManager : _templateManagers.values()) {
			templateManager.destroy(classLoader);
		}
	}

	private Set<String> _getSupportedLanguageTypes(String propertyKey) {
		Set<String> supportedLanguageTypes = _supportedLanguageTypes.get(
			propertyKey);

		if (supportedLanguageTypes != null) {
			return supportedLanguageTypes;
		}

		supportedLanguageTypes = new HashSet<>();

		for (String templateManagerName : _templateManagers.keySet()) {
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

	private Template _getTemplate(
			String templateManagerName, TemplateResource templateResource,
			boolean restricted)
		throws TemplateException {

		TemplateManager templateManager = _getTemplateManagerChecked(
			templateManagerName);

		return templateManager.getTemplate(templateResource, restricted);
	}

	private TemplateManager _getTemplateManager(String templateManagerName) {
		return _templateManagers.get(templateManagerName);
	}

	private TemplateManager _getTemplateManagerChecked(
			String templateManagerName)
		throws TemplateException {

		TemplateManager templateManager = _templateManagers.get(
			templateManagerName);

		if (templateManager == null) {
			throw new TemplateException(
				"Unsupported template manager " + templateManagerName);
		}

		return templateManager;
	}

	private Set<String> _getTemplateManagerNames() {
		return _templateManagers.keySet();
	}

	private Map<String, TemplateManager> _getTemplateManagers() {
		return Collections.unmodifiableMap(_templateManagers);
	}

	private boolean _hasTemplateManager(String templateManagerName) {
		return _templateManagers.containsKey(templateManagerName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TemplateManagerUtil.class);

	private static final TemplateManagerUtil _templateManagerUtil =
		new TemplateManagerUtil();

	private final ServiceTracker<TemplateManager, TemplateManager>
		_serviceTracker;
	private final Map<String, Set<String>> _supportedLanguageTypes =
		new ConcurrentHashMap<>();
	private final Map<String, TemplateManager> _templateManagers =
		new ConcurrentHashMap<>();

	private class TemplateManagerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<TemplateManager, TemplateManager> {

		@Override
		public TemplateManager addingService(
			ServiceReference<TemplateManager> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			TemplateManager templateManager = registry.getService(
				serviceReference);

			try {
				templateManager.init();

				_templateManagers.put(
					templateManager.getName(), templateManager);
			}
			catch (TemplateException templateException) {
				if (_log.isWarnEnabled()) {
					String name = templateManager.getName();

					_log.warn(
						"unable to init " + name + " Template Manager ",
						templateException);
				}
			}

			return templateManager;
		}

		@Override
		public void modifiedService(
			ServiceReference<TemplateManager> serviceReference,
			TemplateManager templateManager) {

			_templateManagers.compute(
				templateManager.getName(),
				(key, value) -> {
					templateManager.destroy();

					try {
						templateManager.init();
					}
					catch (TemplateException templateException) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"unable to init " + templateManager.getName() +
									" Template Manager ",
								templateException);
						}
					}

					return templateManager;
				});
		}

		@Override
		public void removedService(
			ServiceReference<TemplateManager> serviceReference,
			TemplateManager templateManager) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			_templateManagers.remove(templateManager.getName());

			templateManager.destroy();
		}

	}

}