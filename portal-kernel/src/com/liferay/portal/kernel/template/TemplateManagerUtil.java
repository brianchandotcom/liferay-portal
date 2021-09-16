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
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Tina Tian
 * @author Raymond Augé
 */
public class TemplateManagerUtil {

	public static void destroy() {
		for (TemplateManager templateManager : _templateManagers.values()) {
			templateManager.destroy();
		}

		_templateManagers.clear();
	}

	public static void destroy(ClassLoader classLoader) {
		for (TemplateManager templateManager : _templateManagers.values()) {
			templateManager.destroy(classLoader);
		}
	}

	public static Set<String> getSupportedLanguageTypes(String propertyKey) {
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

	public static Template getTemplate(
			String templateManagerName, TemplateResource templateResource,
			boolean restricted)
		throws TemplateException {

		TemplateManager templateManager = _getTemplateManagerChecked(
			templateManagerName);

		return templateManager.getTemplate(templateResource, restricted);
	}

	public static TemplateManager getTemplateManager(
		String templateManagerName) {

		return _templateManagers.get(templateManagerName);
	}

	public static Set<String> getTemplateManagerNames() {
		return _templateManagers.keySet();
	}

	public static Map<String, TemplateManager> getTemplateManagers() {
		return Collections.unmodifiableMap(_templateManagers);
	}

	public static boolean hasTemplateManager(String templateManagerName) {
		return _templateManagers.containsKey(templateManagerName);
	}

	private static TemplateManager _getTemplateManagerChecked(
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

	private TemplateManagerUtil() {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TemplateManagerUtil.class);

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private static final ServiceTracker<TemplateManager, TemplateManager>
		_serviceTracker;
	private static final Map<String, Set<String>> _supportedLanguageTypes =
		new ConcurrentHashMap<>();
	private static final Map<String, TemplateManager> _templateManagers =
		new ConcurrentHashMap<>();

	private static class TemplateManagerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<TemplateManager, TemplateManager> {

		@Override
		public TemplateManager addingService(
			ServiceReference<TemplateManager> serviceReference) {

			TemplateManager templateManager = _bundleContext.getService(
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
						"Unable to init template manager " + name,
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

			_bundleContext.ungetService(serviceReference);

			_templateManagers.remove(templateManager.getName());

			templateManager.destroy();
		}

	}

	static {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext,
			SystemBundleUtil.createFilter(
				"(&(language.type=*)(objectClass=" +
					TemplateManager.class.getName() + "))"),
			new TemplateManagerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

}