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
import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.resource.bundle.AggregateResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ClassResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateHandler;
import com.liferay.portal.kernel.template.TemplateHandlerRegistry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.language.LanguageResources;

import java.util.ArrayList;
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
@Component(service = TemplateHandlerRegistry.class)
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
		return new ArrayList<>(
			_classNameTemplateHandlersServiceTrackerMap.values());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
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

		_ddmTemplateMapsThreadLocal.set(new HashMap<>());

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

		_ddmTemplateMapsThreadLocal.remove();
	}

	@Deactivate
	protected void deactivate() {
		_classNameTemplateHandlersServiceTrackerMap.close();

		_classNameIdTemplateHandlersServiceTrackerMap.close();

		_bundleContext = null;
	}

	@Reference
	protected ResourceBundleLoaderProvider resourceBundleLoaderProvider;

	private static final ThreadLocal<Map<Long, Map<String, DDMTemplate>>>
		_ddmTemplateMapsThreadLocal = new CentralizedThreadLocal<>(
			TemplateHandlerRegistryImpl.class.getName() +
				"._ddmTemplateMapsThreadLocal");

	private BundleContext _bundleContext;
	private ServiceTrackerMap<Long, TemplateHandler>
		_classNameIdTemplateHandlersServiceTrackerMap;
	private ServiceTrackerMap<String, TemplateHandler>
		_classNameTemplateHandlersServiceTrackerMap;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.mapping.model.DDMTemplate)"
	)
	private ModelResourcePermission<DDMTemplate> _modelResourcePermission;

	@Reference
	private Portal _portal;

	private final Map<TemplateHandler, ServiceRegistration<?>>
		_serviceRegistrations = new ConcurrentHashMap<>();

	@Reference
	private UserLocalService _userLocalService;

	private class TemplateHandlerPortalInstanceLifecycleListener
		extends BasePortalInstanceLifecycleListener {

		@Override
		public void portalInstanceRegistered(Company company) throws Exception {
			List<Element> templateElements =
				_templateHandler.getDefaultTemplateElements();

			if (templateElements.isEmpty()) {
				return;
			}

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

			Map<Long, Map<String, DDMTemplate>> ddmTemplateMaps =
				_ddmTemplateMapsThreadLocal.get();

			Map<String, DDMTemplate> ddmTemplateMap = null;

			if (ddmTemplateMaps != null) {
				ddmTemplateMap = ddmTemplateMaps.computeIfAbsent(
					group.getGroupId(),
					groupId -> {
						Map<String, DDMTemplate> ddmTemplates = new HashMap<>();

						for (DDMTemplate ddmTemplate :
								_ddmTemplateLocalService.getTemplatesByGroupId(
									groupId)) {

							ddmTemplates.put(
								StringBundler.concat(
									ddmTemplate.getClassNameId(),
									StringPool.POUND,
									StringUtil.toUpperCase(
										ddmTemplate.getTemplateKey())),
								ddmTemplate);
						}

						return ddmTemplates;
					});
			}

			for (Element templateElement : templateElements) {
				String templateKey = templateElement.elementText(
					"template-key");

				DDMTemplate ddmTemplate = null;

				if (ddmTemplateMap != null) {
					ddmTemplate = ddmTemplateMap.get(
						StringBundler.concat(
							classNameId, StringPool.POUND,
							StringUtil.toUpperCase(templateKey)));
				}

				if (ddmTemplate == null) {
					ddmTemplate = _ddmTemplateLocalService.fetchTemplate(
						group.getGroupId(), classNameId, templateKey);
				}

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
						new ClassResourceBundleLoader(
							"content.Language", clazz.getClassLoader()),
						LanguageResources.PORTAL_RESOURCE_BUNDLE_LOADER);
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
							_CLASS_NAME_PORTLET_DISPLAY_TEMPLATE),
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

			for (Locale locale : _language.getAvailableLocales(groupId)) {
				ResourceBundle resourceBundle =
					resourceBundleLoader.loadResourceBundle(locale);

				map.put(locale, _language.get(resourceBundle, key));
			}

			return map;
		}

		private TemplateHandlerPortalInstanceLifecycleListener(
			TemplateHandler templateHandler) {

			_templateHandler = templateHandler;
		}

		private static final String _CLASS_NAME_PORTLET_DISPLAY_TEMPLATE =
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

			ServiceRegistration<?> serviceRegistration =
				_serviceRegistrations.put(
					templateHandler,
					_bundleContext.registerService(
						PortalInstanceLifecycleListener.class,
						new TemplateHandlerPortalInstanceLifecycleListener(
							templateHandler),
						MapUtil.singletonDictionary(
							Constants.SERVICE_RANKING, serviceRanking)));

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
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

			ServiceRegistration<?> serviceRegistration =
				_serviceRegistrations.remove(templateHandler);

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}

			_bundleContext.ungetService(serviceReference);
		}

	}

}