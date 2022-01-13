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

package com.liferay.site.navigation.menu.item.display.page.internal.type.provider;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.capability.InfoItemCapability;
import com.liferay.info.item.provider.InfoItemCapabilitiesProvider;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.display.page.LayoutDisplayPageMultiSelectionProviderTracker;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.page.template.info.item.capability.DisplayPageInfoItemCapability;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.reflect.GenericUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.site.navigation.menu.item.display.page.internal.type.DisplayPageTypeContext;
import com.liferay.site.navigation.menu.item.display.page.internal.type.DisplayPageTypeSiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

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
@Component(
	immediate = true,
	service = DisplayPageSiteNavigationMenuItemTypeProviderTrackerImpl.class
)
public class DisplayPageSiteNavigationMenuItemTypeProviderTrackerImpl {

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

		for (ServiceRegistration<SiteNavigationMenuItemType>
				serviceRegistration : _serviceRegistrations.values()) {

			serviceRegistration.unregister();
		}

		_serviceRegistrations.clear();
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private DisplayPageInfoItemCapability _displayPageInfoItemCapability;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private LayoutDisplayPageMultiSelectionProviderTracker
		_layoutDisplayPageMultiSelectionProviderTracker;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private Portal _portal;

	private final Map<String, ServiceRegistration<SiteNavigationMenuItemType>>
		_serviceRegistrations = new ConcurrentHashMap<>();
	private ServiceTracker
		<InfoItemCapabilitiesProvider<?>, InfoItemCapabilitiesProvider<?>>
			_serviceTracker;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.menu.item.display.page)",
		unbind = "-"
	)
	private ServletContext _servletContext;

	private class InfoItemCapabilitiesProviderServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<InfoItemCapabilitiesProvider<?>, InfoItemCapabilitiesProvider<?>> {

		public InfoItemCapabilitiesProviderServiceTrackerCustomizer(
			BundleContext bundleContext,
			Map<String, ServiceRegistration<SiteNavigationMenuItemType>>
				serviceRegistrations) {

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

			if (!infoItemCapabilities.contains(
					_displayPageInfoItemCapability)) {

				return infoItemCapabilitiesProvider;
			}

			try {
				_serviceRegistrations.put(
					className,
					_bundleContext.registerService(
						SiteNavigationMenuItemType.class,
						new DisplayPageTypeSiteNavigationMenuItemType(
							_assetDisplayPageFriendlyURLProvider,
							new DisplayPageTypeContext(
								className, _infoItemServiceTracker,
								_layoutDisplayPageMultiSelectionProviderTracker,
								_layoutDisplayPageProviderTracker),
							_itemSelector, _jspRenderer, _portal,
							_servletContext),
						HashMapDictionaryBuilder.<String, Object>put(
							"service.ranking:Integer", "300"
						).put(
							"site.navigation.menu.item.type", className
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

			ServiceRegistration<SiteNavigationMenuItemType>
				serviceRegistration = _serviceRegistrations.remove(className);

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}

		private final BundleContext _bundleContext;
		private final Map
			<String, ServiceRegistration<SiteNavigationMenuItemType>>
				_serviceRegistrations;

	}

}