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

package com.liferay.template.internal.info.item.provider;

import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.item.provider.InfoItemCapabilitiesProvider;
import com.liferay.info.item.renderer.InfoItemRenderer;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.template.info.item.capability.TemplateInfoItemCapability;
import com.liferay.template.internal.info.item.renderer.TemplateInfoItemTemplatedRenderer;
import com.liferay.template.internal.transformer.TemplateNodeFactory;
import com.liferay.template.service.TemplateEntryLocalService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Lourdes Fernández Besada
 */
@Component(immediate = true, service = {})
public class TemplateInfoItemCapabilitiesProviderTracker {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext,
			(Class<InfoItemCapabilitiesProvider<?>>)
				(Class<?>)InfoItemCapabilitiesProvider.class,
			new InfoItemCapabilitiesProviderServiceTrackerCustomizer(
				bundleContext, _serviceRegistrations));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations.values()) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	private final Map<String, ServiceRegistration<?>> _serviceRegistrations =
		new ConcurrentHashMap<>();
	private ServiceTracker
		<InfoItemCapabilitiesProvider<?>, InfoItemCapabilitiesProvider<?>>
			_serviceTracker;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

	@Reference
	private TemplateEntryLocalService _templateEntryLocalService;

	@Reference
	private TemplateInfoItemCapability _templateInfoItemCapability;

	@Reference
	private TemplateNodeFactory _templateNodeFactory;

	private class InfoItemCapabilitiesProviderServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<InfoItemCapabilitiesProvider<?>, InfoItemCapabilitiesProvider<?>> {

		public InfoItemCapabilitiesProviderServiceTrackerCustomizer(
			BundleContext bundleContext,
			Map<String, ServiceRegistration<?>> serviceRegistrations) {

			_bundleContext = bundleContext;
			_serviceRegistrations = serviceRegistrations;
		}

		@Override
		public InfoItemCapabilitiesProvider<?> addingService(
			ServiceReference<InfoItemCapabilitiesProvider<?>>
				serviceReference) {

			InfoItemCapabilitiesProvider<?> infoItemCapabilitiesProvider =
				_bundleContext.getService(serviceReference);

			String className = GetterUtil.getString(
				serviceReference.getProperty("item.class.name"));

			if (Validator.isNull(className)) {
				className = GenericUtil.getGenericClassName(
					infoItemCapabilitiesProvider);
			}

			if (Validator.isNull(className) ||
				_serviceRegistrations.containsKey(className)) {

				return infoItemCapabilitiesProvider;
			}

			List<InfoItemCapability> infoItemCapabilities =
				infoItemCapabilitiesProvider.getInfoItemCapabilities();

			if (!infoItemCapabilities.contains(_templateInfoItemCapability)) {
				return infoItemCapabilitiesProvider;
			}

			try {
				_serviceRegistrations.put(
					className,
					_bundleContext.registerService(
						(Class<InfoItemRenderer<?>>)
							(Class<?>)InfoItemRenderer.class,
						new TemplateInfoItemTemplatedRenderer<>(
							className, _ddmTemplateLocalService,
							_infoItemServiceTracker, _stagingGroupHelper,
							_templateEntryLocalService, _templateNodeFactory),
						HashMapDictionaryBuilder.<String, Object>put(
							"item.class.name", className
						).put(
							"service.ranking:Integer", "200"
						).build()));
			}
			catch (Throwable throwable) {
				_bundleContext.ungetService(serviceReference);

				throw throwable;
			}

			return infoItemCapabilitiesProvider;
		}

		@Override
		public void modifiedService(
			ServiceReference<InfoItemCapabilitiesProvider<?>> serviceReference,
			InfoItemCapabilitiesProvider<?> infoItemCapabilitiesProvider) {

			removedService(serviceReference, infoItemCapabilitiesProvider);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<InfoItemCapabilitiesProvider<?>> serviceReference,
			InfoItemCapabilitiesProvider<?> infoItemCapabilitiesProvider) {

			String className = GenericUtil.getGenericClassName(
				infoItemCapabilitiesProvider);

			if (Validator.isNull(className)) {
				return;
			}

			ServiceRegistration<?> serviceRegistration =
				_serviceRegistrations.remove(className);

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}

		private final BundleContext _bundleContext;
		private final Map<String, ServiceRegistration<?>> _serviceRegistrations;

	}

}