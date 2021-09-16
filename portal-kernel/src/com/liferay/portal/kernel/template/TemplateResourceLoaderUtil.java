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

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;

import java.util.Set;

import org.osgi.framework.BundleContext;

/**
 * @author Tina Tian
 */
public class TemplateResourceLoaderUtil {

	public static void clearCache() {
		for (TemplateResourceLoader templateResourceLoader :
				_templateResourceLoaders.values()) {

			templateResourceLoader.clearCache();
		}
	}

	public static void clearCache(String templateResourceLoaderName)
		throws TemplateException {

		TemplateResourceLoader templateResourceLoader =
			_getTemplateResourceLoader(templateResourceLoaderName);

		templateResourceLoader.clearCache();
	}

	public static void clearCache(
			String templateResourceLoaderName, String templateId)
		throws TemplateException {

		TemplateResourceLoader templateResourceLoader =
			_getTemplateResourceLoader(templateResourceLoaderName);

		templateResourceLoader.clearCache(templateId);
	}

	public static TemplateResource getTemplateResource(
			String templateResourceLoaderName, String templateId)
		throws TemplateException {

		TemplateResourceLoader templateResourceLoader =
			_getTemplateResourceLoader(templateResourceLoaderName);

		return templateResourceLoader.getTemplateResource(templateId);
	}

	public static TemplateResourceLoader getTemplateResourceLoader(
			String templateResourceLoaderName)
		throws TemplateException {

		return _templateResourceLoaderUtil._getTemplateResourceLoader(
			templateResourceLoaderName);
	}

	public static Set<String> getTemplateResourceLoaderNames() {
		return _templateResourceLoaders.keySet();
	}

	public static boolean hasTemplateResource(
			String templateResourceLoaderName, String templateId)
		throws TemplateException {

		TemplateResourceLoader templateResourceLoader =
			_getTemplateResourceLoader(templateResourceLoaderName);

		return templateResourceLoader.hasTemplateResource(templateId);
	}

	public static boolean hasTemplateResourceLoader(
		String templateResourceLoaderName) {

		return _templateResourceLoaders.containsKey(templateResourceLoaderName);
	}

	private static TemplateResourceLoader _getTemplateResourceLoader(
			String templateResourceLoaderName)
		throws TemplateException {

		TemplateResourceLoader templateResourceLoader =
			_templateResourceLoaders.getService(templateResourceLoaderName);

		if (templateResourceLoader == null) {
			throw new TemplateException(
				"Unsupported template resource loader " +
					templateResourceLoaderName);
		}

		return templateResourceLoader;
	}

	private TemplateResourceLoaderUtil() {
	}

	private static final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();

	private static final ServiceTrackerMap<String, TemplateResourceLoader>
		_templateResourceLoaders = ServiceTrackerMapFactory.openSingleValueMap(
			_bundleContext, TemplateResourceLoader.class, null,
			(serviceReference, emitter) -> {
				TemplateResourceLoader templateResourceLoader =
					_bundleContext.getService(serviceReference);

				emitter.emit(templateResourceLoader.getName());

				_bundleContext.ungetService(serviceReference);
			});

	private static final TemplateResourceLoaderUtil
		_templateResourceLoaderUtil = new TemplateResourceLoaderUtil();

}