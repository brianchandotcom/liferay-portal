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
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

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
		_templateManagerRegistry.unregisterAll();
	}

	public static void destroy(ClassLoader classLoader) {
		Map<String, TemplateManager> templateManagers =
			_templateManagerRegistry.getTemplateManagers();

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

		Map<String, TemplateManager> templateManagers =
			_templateManagerRegistry.getTemplateManagers();

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

	public static Template getTemplate(
			String templateManagerName, TemplateResource templateResource,
			boolean restricted)
		throws TemplateException {

		TemplateManager templateManager =
			_templateManagerRegistry.getTemplateManager(templateManagerName);

		return templateManager.getTemplate(templateResource, restricted);
	}

	public static Template getTemplate(
			String templateManagerName, TemplateResource templateResource,
			TemplateResource errorTemplateResource, boolean restricted)
		throws TemplateException {

		TemplateManager templateManager =
			_templateManagerRegistry.getTemplateManager(templateManagerName);

		return templateManager.getTemplate(
			templateResource, errorTemplateResource, restricted);
	}

	public static TemplateManager getTemplateManager(
		String templateManagerName) {

		Map<String, TemplateManager> templateManagers =
			_templateManagerRegistry.getTemplateManagers();

		return templateManagers.get(templateManagerName);
	}

	public static Set<String> getTemplateManagerNames() {
		Map<String, TemplateManager> templateManagers =
			_templateManagerRegistry.getTemplateManagers();

		return templateManagers.keySet();
	}

	public static Map<String, TemplateManager> getTemplateManagers() {
		return _templateManagerRegistry.getTemplateManagers();
	}

	public static boolean hasTemplateManager(String templateManagerName) {
		Map<String, TemplateManager> templateManagers =
			_templateManagerRegistry.getTemplateManagers();

		return templateManagers.containsKey(templateManagerName);
	}

	public void setTemplateManagerRegistry(
		TemplateManagerRegistry templateManagerRegistry) {

		_templateManagerRegistry = templateManagerRegistry;
	}

	private static final Map<String, Set<String>> _supportedLanguageTypes =
		new ConcurrentHashMap<String, Set<String>>();
	private static TemplateManagerRegistry _templateManagerRegistry;

}