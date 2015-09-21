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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.DDMTemplateManager;
import com.liferay.portlet.dynamicdatamapping.DDMTemplateManagerUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
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
		List<TemplateHandler> templateHandlers = new ArrayList<>(
			_instance._templateHandlers.values());

		return Collections.unmodifiableList(templateHandlers);
	}

	private TemplateHandlerRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			TemplateHandler.class,
			new TemplateHandlerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private static final String _PORTLET_DISPLAY_TEMPLATE_CLASS_NAME =
		"com.liferay.portlet.display.template.PortletDisplayTemplate";

	private static final Log _log = LogFactoryUtil.getLog(
		TemplateHandlerRegistryUtil.class);

	private static final TemplateHandlerRegistryUtil _instance =
		new TemplateHandlerRegistryUtil();

	private final ServiceTracker<TemplateHandler, TemplateHandler>
		_serviceTracker;
	private final Map<String, TemplateHandler> _templateHandlers =
		new ConcurrentHashMap<>();

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

			try {
				_addTemplate(templateHandler);
			}
			catch (Exception e) {
				_log.error(
					"Unable to add template for template handler " +
						templateHandler.getClassName(),
					e);
			}

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

		private void _addTemplate(TemplateHandler templateHandler)
			throws Exception {

			long classNameId = PortalUtil.getClassNameId(
				templateHandler.getClassName());

			ServiceContext serviceContext = new ServiceContext();

			long companyId = PortalUtil.getDefaultCompanyId();

			Group group = GroupLocalServiceUtil.getCompanyGroup(companyId);

			serviceContext.setScopeGroupId(group.getGroupId());

			long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

			serviceContext.setUserId(userId);

			List<Element> templateElements =
				templateHandler.getDefaultTemplateElements();

			for (Element templateElement : templateElements) {
				String templateKey = templateElement.elementText(
					"template-key");

				DDMTemplate ddmTemplate = DDMTemplateManagerUtil.fetchTemplate(
					group.getGroupId(), classNameId, templateKey);

				if (ddmTemplate != null) {
					continue;
				}

				Class<?> clazz = templateHandler.getClass();

				ClassLoader classLoader = clazz.getClassLoader();

				Map<Locale, String> nameMap = _getLocalizationMap(
					classLoader, group.getGroupId(),
					templateElement.elementText("name"));
				Map<Locale, String> descriptionMap = _getLocalizationMap(
					classLoader, group.getGroupId(),
					templateElement.elementText("description"));

				String type = templateElement.elementText("type");

				if (type == null) {
					type = DDMTemplateManager.TEMPLATE_TYPE_DISPLAY;
				}

				String language = templateElement.elementText("language");

				String scriptFileName = templateElement.elementText(
					"script-file");

				String script = StringUtil.read(classLoader, scriptFileName);

				boolean cacheable = GetterUtil.getBoolean(
					templateElement.elementText("cacheable"));

				DDMTemplateManagerUtil.addTemplate(
					userId, group.getGroupId(), classNameId, 0,
					PortalUtil.getClassNameId(
						_PORTLET_DISPLAY_TEMPLATE_CLASS_NAME),
					templateKey, nameMap, descriptionMap, type, null, language,
					script, cacheable, false, null, null, serviceContext);
			}
		}

		private Map<Locale, String> _getLocalizationMap(
			ClassLoader classLoader, long groupId, String key) {

			Map<Locale, String> map = new HashMap<>();

			for (Locale locale : LanguageUtil.getAvailableLocales(groupId)) {
				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
					"content.Language", locale, classLoader);

				map.put(locale, LanguageUtil.get(resourceBundle, key));
			}

			return map;
		}

	}

}