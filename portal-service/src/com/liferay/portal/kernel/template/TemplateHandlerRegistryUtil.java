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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplate;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.StringServiceRegistrationMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

	public static void register(TemplateHandler templateHandler) {
		_instance._register(templateHandler);
	}

	public static void unregister(TemplateHandler templateHandler) {
		_instance._unregister(templateHandler);
	}

	private TemplateHandlerRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			TemplateHandler.class,
			new TemplateHandlerServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private void _register(TemplateHandler templateHandler) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<TemplateHandler> serviceRegistration =
			registry.registerService(TemplateHandler.class, templateHandler);

		_serviceRegistrations.put(
			templateHandler.getClassName(), serviceRegistration);
	}

	private void _unregister(TemplateHandler templateHandler) {
		ServiceRegistration<TemplateHandler> serviceRegistration =
			_serviceRegistrations.remove(templateHandler.getClassName());

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TemplateHandlerRegistryUtil.class);

	private static final TemplateHandlerRegistryUtil _instance =
		new TemplateHandlerRegistryUtil();

	private final StringServiceRegistrationMap<TemplateHandler>
		_serviceRegistrations = new StringServiceRegistrationMap<>();
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
				addTemplate(templateHandler);
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

		protected void addTemplate(TemplateHandler templateHandler)
			throws Exception {

			long companyId = PortalUtil.getDefaultCompanyId();

			ServiceContext serviceContext = new ServiceContext();

			Group group = GroupLocalServiceUtil.getCompanyGroup(companyId);

			long groupId = group.getGroupId();

			serviceContext.setScopeGroupId(group.getGroupId());

			long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

			serviceContext.setUserId(userId);

			long classNameId = PortalUtil.getClassNameId(
				templateHandler.getClassName());

			List<Element> templateElements =
				templateHandler.getDefaultTemplateElements();

			for (Element templateElement : templateElements) {
				String templateKey = templateElement.elementText(
					"template-key");

				DDMTemplate ddmTemplate =
					DDMTemplateLocalServiceUtil.fetchTemplate(
						groupId, classNameId, templateKey);

				if (ddmTemplate != null) {
					continue;
				}

				Map<Locale, String> nameMap = getLocalizationMap(
					groupId, templateElement.elementText("name"));
				Map<Locale, String> descriptionMap = getLocalizationMap(
					groupId, templateElement.elementText("description"));
				String language = templateElement.elementText("language");
				String scriptFileName = templateElement.elementText(
					"script-file");

				Class<?> clazz = templateHandler.getClass();

				ClassLoader classLoader = clazz.getClassLoader();

				String script = StringUtil.read(classLoader, scriptFileName);

				boolean cacheable = GetterUtil.getBoolean(
					templateElement.elementText("cacheable"));

				DDMTemplateLocalServiceUtil.addTemplate(
					userId, groupId, classNameId, 0,
					PortalUtil.getClassNameId(PortletDisplayTemplate.class),
					templateKey, nameMap, descriptionMap,
					DDMTemplateConstants.TEMPLATE_TYPE_DISPLAY, null, language,
					script, cacheable, false, null, null, serviceContext);
			}
		}

		protected Map<Locale, String> getLocalizationMap(
			long groupId, String key) {

			Map<Locale, String> map = new HashMap<>();

			Locale[] locales = LanguageUtil.getAvailableLocales(groupId);

			for (Locale locale : locales) {
				map.put(locale, LanguageUtil.get(locale, key));
			}

			return map;
		}

	}

}