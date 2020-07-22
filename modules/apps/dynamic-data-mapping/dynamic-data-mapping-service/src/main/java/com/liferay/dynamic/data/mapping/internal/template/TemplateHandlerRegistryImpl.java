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

package com.liferay.dynamic.data.mapping.internal.template;

import com.liferay.dynamic.data.mapping.internal.util.ResourceBundleLoaderProvider;
import com.liferay.dynamic.data.mapping.kernel.DDMTemplateManager;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistry;
import com.liferay.portal.kernel.util.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.language.LanguageResources;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = TemplateHandlerRegistry.class)
public class TemplateHandlerRegistryImpl implements TemplateHandlerRegistry {

	@Override
	public long[] getClassNameIds() {
		return ArrayUtil.toLongArray(
			_classNameIdTemplateHandlersServiceTrackerMap.keySet());
	}

	@Override
	public TemplateHandler getTemplateHandler(long classNameId) {
		return _classNameIdTemplateHandlersServiceTrackerMap.getService(
			classNameId);
	}

	@Override
	public TemplateHandler getTemplateHandler(String className) {
		return _classNameTemplateHandlersServiceTrackerMap.getService(
			className);
	}

	@Override
	public List<TemplateHandler> getTemplateHandlers() {
		List<TemplateHandler> templateHandlers = new ArrayList<>();

		for (String className :
				_classNameTemplateHandlersServiceTrackerMap.keySet()) {

			templateHandlers.add(
				_classNameTemplateHandlersServiceTrackerMap.getService(
					className));
		}

		return templateHandlers;
	}

	@Activate
	protected synchronized void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_classNameIdTemplateHandlersServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, TemplateHandler.class, null,
				(serviceReference, emitter) -> {
					TemplateHandler templateHandler = bundleContext.getService(
						serviceReference);

					emitter.emit(
						_portal.getClassNameId(templateHandler.getClassName()));

					bundleContext.ungetService(serviceReference);
				});

		_classNameTemplateHandlersServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, TemplateHandler.class, null,
				(serviceReference, emitter) -> {
					TemplateHandler templateHandler = bundleContext.getService(
						serviceReference);

					emitter.emit(templateHandler.getClassName());

					bundleContext.ungetService(serviceReference);
				},
				new TemplateHandlerServiceTrackerCustomizer());
	}

	@Deactivate
	protected synchronized void deactivate() {
		_bundleContext = null;
	}

	protected void registerPortalInstanceLifecycleListener(
		TemplateHandler templateHandler, int serviceRanking) {

		ServiceRegistration<?> serviceRegistration = _serviceRegistrations.get(
			templateHandler);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}

		PortalInstanceLifecycleListener portalInstanceLifecycleListener =
			new TemplateHandlerPortalInstanceLifecycleListener(templateHandler);

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(Constants.SERVICE_RANKING, serviceRanking);

		serviceRegistration = _bundleContext.registerService(
			PortalInstanceLifecycleListener.class,
			portalInstanceLifecycleListener, properties);

		_serviceRegistrations.put(templateHandler, serviceRegistration);
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMTemplate)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<DDMTemplate> modelResourcePermission) {
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		_portal = portal;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference
	protected ResourceBundleLoaderProvider resourceBundleLoaderProvider;

	private BundleContext _bundleContext;
	private ServiceTrackerMap<Long, TemplateHandler>
		_classNameIdTemplateHandlersServiceTrackerMap;
	private ServiceTrackerMap<String, TemplateHandler>
		_classNameTemplateHandlersServiceTrackerMap;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	private GroupLocalService _groupLocalService;
	private Portal _portal;
	private final Map<TemplateHandler, ServiceRegistration<?>>
		_serviceRegistrations = new ConcurrentHashMap<>();
	private UserLocalService _userLocalService;

	private class TemplateHandlerPortalInstanceLifecycleListener
		extends BasePortalInstanceLifecycleListener {

		@Override
		public void portalInstanceRegistered(Company company) throws Exception {
			long classNameId = _portal.getClassNameId(
				_templateHandler.getClassName());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGuestPermissions(true);

			Group group = _groupLocalService.getCompanyGroup(
				company.getCompanyId());

			serviceContext.setScopeGroupId(group.getGroupId());

			long userId = _userLocalService.getDefaultUserId(
				company.getCompanyId());

			serviceContext.setUserId(userId);

			List<Element> templateElements =
				_templateHandler.getDefaultTemplateElements();

			for (Element templateElement : templateElements) {
				String templateKey = templateElement.elementText(
					"template-key");

				DDMTemplate ddmTemplate =
					_ddmTemplateLocalService.fetchTemplate(
						group.getGroupId(), classNameId, templateKey);

				if ((ddmTemplate != null) &&
					((ddmTemplate.getUserId() != userId) ||
					 (ddmTemplate.getVersionUserId() != userId))) {

					continue;
				}

				Class<?> clazz = _templateHandler.getClass();

				String scriptFileName = templateElement.elementText(
					"script-file");

				String script = StringUtil.read(
					clazz.getClassLoader(), scriptFileName);

				if ((ddmTemplate != null) &&
					StringUtil.equals(script, ddmTemplate.getScript())) {

					continue;
				}

				ResourceBundleLoader resourceBundleLoader = null;

				Bundle bundle = FrameworkUtil.getBundle(clazz);

				if (bundle != null) {
					resourceBundleLoader =
						resourceBundleLoaderProvider.getResourceBundleLoader(
							bundle.getSymbolicName());
				}
				else {
					resourceBundleLoader = new AggregateResourceBundleLoader(
						ResourceBundleUtil.getResourceBundleLoader(
							"content.Language", clazz.getClassLoader()),
						LanguageResources.RESOURCE_BUNDLE_LOADER);
				}

				Map<Locale, String> nameMap = getLocalizationMap(
					resourceBundleLoader, group.getGroupId(),
					templateElement.elementText("name"));
				Map<Locale, String> descriptionMap = getLocalizationMap(
					resourceBundleLoader, group.getGroupId(),
					templateElement.elementText("description"));

				String type = templateElement.elementText("type");

				if (type == null) {
					type = DDMTemplateManager.TEMPLATE_TYPE_DISPLAY;
				}

				String language = templateElement.elementText("language");

				boolean cacheable = GetterUtil.getBoolean(
					templateElement.elementText("cacheable"));

				if (ddmTemplate == null) {
					_ddmTemplateLocalService.addTemplate(
						userId, group.getGroupId(), classNameId, 0,
						_portal.getClassNameId(
							_PORTLET_DISPLAY_TEMPLATE_CLASS_NAME),
						templateKey, nameMap, descriptionMap, type, null,
						language, script, cacheable, false, null, null,
						serviceContext);
				}
				else {
					_ddmTemplateLocalService.updateTemplate(
						userId, ddmTemplate.getTemplateId(), 0, nameMap,
						descriptionMap, type, null, language, script, cacheable,
						false, null, null, serviceContext);
				}
			}
		}

		@Override
		public void portalInstanceUnregistered(Company company)
			throws Exception {
		}

		protected Map<Locale, String> getLocalizationMap(
			ResourceBundleLoader resourceBundleLoader, long groupId,
			String key) {

			Map<Locale, String> map = new HashMap<>();

			for (Locale locale : LanguageUtil.getAvailableLocales(groupId)) {
				ResourceBundle resourceBundle =
					resourceBundleLoader.loadResourceBundle(locale);

				map.put(locale, LanguageUtil.get(resourceBundle, key));
			}

			return map;
		}

		private TemplateHandlerPortalInstanceLifecycleListener(
			TemplateHandler templateHandler) {

			_templateHandler = templateHandler;
		}

		private static final String _PORTLET_DISPLAY_TEMPLATE_CLASS_NAME =
			"com.liferay.portlet.display.template.PortletDisplayTemplate";

		private final TemplateHandler _templateHandler;

	}

	private class TemplateHandlerServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<TemplateHandler, TemplateHandler> {

		@Override
		public TemplateHandler addingService(
			ServiceReference<TemplateHandler> serviceReference) {

			TemplateHandler templateHandler = _bundleContext.getService(
				serviceReference);

			int serviceRanking = GetterUtil.getInteger(
				serviceReference.getProperty(Constants.SERVICE_RANKING));

			registerPortalInstanceLifecycleListener(
				templateHandler, serviceRanking);

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

			ServiceRegistration<?> serviceRegistration =
				_serviceRegistrations.remove(templateHandler);

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}

	}

}