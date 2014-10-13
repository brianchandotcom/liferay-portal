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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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

	public static void destroy() {
		for (TemplateResourceLoader templateResourceLoader :
				_templateResourceLoaders.values()) {

			templateResourceLoader.destroy();
		}

		_templateResourceLoaders.clear();
	}

	public static TemplateResource getTemplateResource(
			String templateResourceLoaderName, String templateId)
		throws TemplateException {

		TemplateResourceLoader templateResourceLoader =
			_getTemplateResourceLoader(templateResourceLoaderName);

		return templateResourceLoader.getTemplateResource(templateId);
	}

	public static TemplateResourceLoader getTemplateResourceLoader(
		String templateResourceLoaderName) {

		return _templateResourceLoaders.get(templateResourceLoaderName);
	}

	public static Set<String> getTemplateResourceLoaderNames() {
		return _templateResourceLoaders.keySet();
	}

	public static Map<String, TemplateResourceLoader>
		getTemplateResourceLoaders() {

		return Collections.unmodifiableMap(_templateResourceLoaders);
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

	public static void registerTemplateResourceLoader(
		TemplateResourceLoader templateResourceLoader) {

		_templateResourceLoaders.put(
			templateResourceLoader.getName(), templateResourceLoader);
	}

	public static void unregisterTemplateResourceLoader(
		String templateResourceLoaderName) {

		TemplateResourceLoader templateResourceLoader =
			_templateResourceLoaders.remove(templateResourceLoaderName);

		if (templateResourceLoader != null) {
			templateResourceLoader.destroy();
		}
	}

	public void setTemplateResourceLoaders(
		List<TemplateResourceLoader> templateResourceLoaders) {

		for (TemplateResourceLoader templateResourceLoader :
				templateResourceLoaders) {

			_templateResourceLoaders.put(
				templateResourceLoader.getName(), templateResourceLoader);
		}
	}

	private static TemplateResourceLoader _getTemplateResourceLoader(
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

	private static final Map<String, TemplateResourceLoader>
		_templateResourceLoaders =
			new ConcurrentHashMap<String, TemplateResourceLoader>();

}