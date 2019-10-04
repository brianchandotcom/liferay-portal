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

import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tina Tian
 */
public class TemplateResourceLoaderUtil {

	public static void clearCache() {
		_templateResourceLoaderUtil._clearCache();
	}

	public static void clearCache(String templateResourceLoaderName)
		throws TemplateException {

		_templateResourceLoaderUtil._clearCache(templateResourceLoaderName);
	}

	public static void clearCache(
			String templateResourceLoaderName, String templateId)
		throws TemplateException {

		_templateResourceLoaderUtil._clearCache(
			templateResourceLoaderName, templateId);
	}

	public static TemplateResource getTemplateResource(
			String templateResourceLoaderName, String templateId)
		throws TemplateException {

		return _templateResourceLoaderUtil._getTemplateResource(
			templateResourceLoaderName, templateId);
	}

	public static TemplateResourceLoader getTemplateResourceLoader(
			String templateResourceLoaderName)
		throws TemplateException {

		return _templateResourceLoaderUtil._getTemplateResourceLoader(
			templateResourceLoaderName);
	}

	public static Set<String> getTemplateResourceLoaderNames() {
		return _templateResourceLoaderUtil._getTemplateResourceLoaderNames();
	}

	public static boolean hasTemplateResource(
			String templateResourceLoaderName, String templateId)
		throws TemplateException {

		return _templateResourceLoaderUtil._hasTemplateResource(
			templateResourceLoaderName, templateId);
	}

	public static boolean hasTemplateResourceLoader(
		String templateResourceLoaderName) {

		return _templateResourceLoaderUtil._hasTemplateResourceLoader(
			templateResourceLoaderName);
	}

	private TemplateResourceLoaderUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			TemplateResourceLoader.class,
			new TemplateResourceLoaderTrackerCustomizer());

		_serviceTracker.open();
	}

	private void _clearCache() {
		for (TemplateResourceLoader templateResourceLoader :
				_templateResourceLoaders.values()) {

			templateResourceLoader.clearCache();
		}
	}

	private void _clearCache(String templateResourceLoaderName)
		throws TemplateException {

		TemplateResourceLoader templateResourceLoader =
			_getTemplateResourceLoader(templateResourceLoaderName);

		templateResourceLoader.clearCache();
	}

	private void _clearCache(
			String templateResourceLoaderName, String templateId)
		throws TemplateException {

		TemplateResourceLoader templateResourceLoader =
			_getTemplateResourceLoader(templateResourceLoaderName);

		templateResourceLoader.clearCache(templateId);
	}

	private TemplateResource _getTemplateResource(
			String templateResourceLoaderName, String templateId)
		throws TemplateException {

		TemplateResourceLoader templateResourceLoader =
			_getTemplateResourceLoader(templateResourceLoaderName);

		return templateResourceLoader.getTemplateResource(templateId);
	}

	private TemplateResourceLoader _getTemplateResourceLoader(
			String templateResourceLoaderName)
		throws TemplateException {

		TemplateResourceLoader templateResourceLoader =
			_templateResourceLoaders.get(templateResourceLoaderName);

		if (templateResourceLoader == null) {
			throw new TemplateException(
				"Unsupported template resource loader " +
					templateResourceLoaderName);
		}

		return templateResourceLoader;
	}

	private Set<String> _getTemplateResourceLoaderNames() {
		return _templateResourceLoaders.keySet();
	}

	private boolean _hasTemplateResource(
			String templateResourceLoaderName, String templateId)
		throws TemplateException {

		TemplateResourceLoader templateResourceLoader =
			_getTemplateResourceLoader(templateResourceLoaderName);

		return templateResourceLoader.hasTemplateResource(templateId);
	}

	private boolean _hasTemplateResourceLoader(
		String templateResourceLoaderName) {

		return _templateResourceLoaders.containsKey(templateResourceLoaderName);
	}

	private static final TemplateResourceLoaderUtil
		_templateResourceLoaderUtil = new TemplateResourceLoaderUtil();

	private final ServiceTracker<TemplateResourceLoader, TemplateResourceLoader>
		_serviceTracker;
	private final Map<String, TemplateResourceLoader> _templateResourceLoaders =
		new ConcurrentHashMap<>();

	private class TemplateResourceLoaderTrackerCustomizer
		implements ServiceTrackerCustomizer
			<TemplateResourceLoader, TemplateResourceLoader> {

		@Override
		public TemplateResourceLoader addingService(
			ServiceReference<TemplateResourceLoader> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			TemplateResourceLoader templateResourceLoader = registry.getService(
				serviceReference);

			_templateResourceLoaders.put(
				templateResourceLoader.getName(), templateResourceLoader);

			return templateResourceLoader;
		}

		@Override
		public void modifiedService(
			ServiceReference<TemplateResourceLoader> serviceReference,
			TemplateResourceLoader templateResourceLoader) {
		}

		@Override
		public void removedService(
			ServiceReference<TemplateResourceLoader> serviceReference,
			TemplateResourceLoader templateResourceLoader) {

			_templateResourceLoaders.remove(templateResourceLoader.getName());

			templateResourceLoader.clearCache();

			templateResourceLoader.destroy();

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);
		}

	}

}