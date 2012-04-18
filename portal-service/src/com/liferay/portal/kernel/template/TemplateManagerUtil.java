/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tina Tian
 */
public class TemplateManagerUtil {

	public static void clearCache(String templateManagerName)
		throws TemplateException {

		_getTemplateManager(templateManagerName).clearCache();
	}

	public static void clearCache(String templateManager, String templateId)
		throws TemplateException {

		_getTemplateManager(templateManager).clearCache(templateId);
	}

	public static void destroy() throws TemplateException {
		Iterator<TemplateManager> iterator =
			_templateManagers.values().iterator();

		while (iterator.hasNext()) {
			TemplateManager templateManager = iterator.next();

			iterator.remove();

			templateManager.destroy();
		}
	}

	public static Template getTemplate(
			String templateManagerName, String templateId,
			String templateContent, String errorTemplateId,
			String errorTemplateContent,
			TemplateContextType templateContextType)
		throws TemplateException {

		return _getTemplateManager(templateManagerName).getTemplate(
			templateId, templateContent, errorTemplateId, errorTemplateContent,
			templateContextType);
	}

	public static Template getTemplate(
			String templateManagerName, String templateId,
			String templateContent, String errorTemplateId,
			TemplateContextType templateContextType)
		throws TemplateException {

		return _getTemplateManager(templateManagerName).getTemplate(
			templateId, templateContent, errorTemplateId, templateContextType);
	}

	public static Template getTemplate(
			String templateManagerName, String templateId,
			String templateContent, TemplateContextType templateContextType)
		throws TemplateException {

		return _getTemplateManager(templateManagerName).getTemplate(
			templateId, templateContent, templateContextType);
	}

	public static Template getTemplate(
			String templateManagerName, String templateId,
			TemplateContextType templateContextType)
		throws TemplateException {

		return _getTemplateManager(templateManagerName).getTemplate(
			templateId, templateContextType);
	}

	public static TemplateManager getTemplateManager(
		String templateManagerName) {

		return _templateManagers.get(templateManagerName);
	}

	public static Set<String> getTemplateManagerNames(
		String templateManagerName) {

		return _templateManagers.keySet();
	}

	public static Map<String, TemplateManager> getTemplateManagers() {

		return Collections.unmodifiableMap(_templateManagers);
	}

	public static boolean hasTemplate(
			String templateManagerName, String templateId)
		throws TemplateException {

		return _getTemplateManager(templateManagerName).hasTemplate(templateId);
	}

	public static boolean hasTemplateManager(String templateManagerName) {
		return _templateManagers.containsKey(templateManagerName);
	}

	public static void init() throws TemplateException {
		for (TemplateManager templateManager : _templateManagers.values()) {
			templateManager.init();
		}
	}

	public static void registerTemplateManager(TemplateManager templateManager)
		throws TemplateException {

		templateManager.init();

		_templateManagers.put(
			templateManager.getManagerName(), templateManager);
	}

	public static void unregisterTemplateManager(String templateManagerName)
		throws TemplateException {

		TemplateManager templateManager = _templateManagers.remove(
			templateManagerName);

		if (templateManager != null) {
			templateManager.destroy();
		}
	}

	public void setTemplateManagers(List<TemplateManager> templateManagers) {
		for (TemplateManager templateManager : templateManagers) {
			_templateManagers.put(
				templateManager.getManagerName(), templateManager);
		}
	}

	private static TemplateManager _getTemplateManager(
			String templateManagerName)
		throws TemplateException {

		TemplateManager templateManager = _templateManagers.get(
			templateManagerName);

		if (templateManager == null) {
			throw new TemplateException(
				"Current system does not support template manager " +
					templateManagerName);
		}

		return templateManager;
	}

	private static Map<String, TemplateManager> _templateManagers =
		new ConcurrentHashMap<String, TemplateManager>();

}