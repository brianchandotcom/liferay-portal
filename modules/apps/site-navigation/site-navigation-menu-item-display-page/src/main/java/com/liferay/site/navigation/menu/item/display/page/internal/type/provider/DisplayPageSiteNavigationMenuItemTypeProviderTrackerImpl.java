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
import com.liferay.info.exception.CapabilityVerificationException;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.page.template.info.item.capability.DisplayPageInfoItemCapability;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.navigation.menu.item.display.page.internal.configuration.FFDisplayPageSiteNavigationMenuItemConfigurationUtil;
import com.liferay.site.navigation.menu.item.display.page.internal.type.DisplayPageTypeContext;
import com.liferay.site.navigation.menu.item.display.page.internal.type.DisplayPageTypeSiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;

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
			(Class<InfoItemDetailsProvider<?>>)
				(Class<?>)InfoItemDetailsProvider.class,
			new InfoItemDetailsProviderServiceTrackerCustomizer(
				bundleContext, _serviceRegistrations));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		for (ServiceRegistration<SiteNavigationMenuItemType>
				serviceRegistration : _serviceRegistrations.values()) {

			try {
				serviceRegistration.unregister();
			}
			catch (IllegalStateException illegalStateException) {
				_log.error(illegalStateException, illegalStateException);
			}
		}

		_serviceRegistrations.clear();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DisplayPageSiteNavigationMenuItemTypeProviderTrackerImpl.class);

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
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private Portal _portal;

	private final Map<String, ServiceRegistration<SiteNavigationMenuItemType>>
		_serviceRegistrations = new ConcurrentHashMap<>();
	private ServiceTracker
		<InfoItemDetailsProvider<?>, InfoItemDetailsProvider<?>>
			_serviceTracker;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.menu.item.display.page)",
		unbind = "-"
	)
	private ServletContext _servletContext;

	private class InfoItemDetailsProviderServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<InfoItemDetailsProvider<?>, InfoItemDetailsProvider<?>> {

		public InfoItemDetailsProviderServiceTrackerCustomizer(
			BundleContext bundleContext,
			Map<String, ServiceRegistration<SiteNavigationMenuItemType>>
				serviceRegistrations) {

			_bundleContext = bundleContext;

			_serviceRegistrations = serviceRegistrations;
		}

		@Override
		public InfoItemDetailsProvider<?> addingService(
			ServiceReference<InfoItemDetailsProvider<?>> serviceReference) {

			InfoItemDetailsProvider<?> infoItemDetailsProvider =
				_bundleContext.getService(serviceReference);

			if (!FFDisplayPageSiteNavigationMenuItemConfigurationUtil.
					displayPageTypesEnabled()) {

				return infoItemDetailsProvider;
			}

			InfoItemClassDetails infoItemClassDetails =
				infoItemDetailsProvider.getInfoItemClassDetails();

			if (!_hasDisplayPageInfoItemCapability(infoItemClassDetails)) {
				return infoItemDetailsProvider;
			}

			LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
				_layoutDisplayPageProviderTracker.
					getLayoutDisplayPageProviderByClassName(
						infoItemClassDetails.getClassName());

			if (layoutDisplayPageProvider == null) {
				return infoItemDetailsProvider;
			}

			try {
				_serviceRegistrations.put(
					infoItemClassDetails.getClassName(),
					_bundleContext.registerService(
						SiteNavigationMenuItemType.class,
						new DisplayPageTypeSiteNavigationMenuItemType(
							_assetDisplayPageFriendlyURLProvider,
							new DisplayPageTypeContext(
								infoItemClassDetails,
								_infoItemServiceTracker.getFirstInfoItemService(
									InfoItemFormVariationsProvider.class,
									infoItemClassDetails.getClassName()),
								layoutDisplayPageProvider),
							_itemSelector, _jspRenderer, _portal,
							_servletContext),
						HashMapDictionaryBuilder.<String, Object>put(
							"service.ranking:Integer", "300"
						).put(
							"site.navigation.menu.item.type",
							infoItemClassDetails.getClassName()
						).build()));
			}
			catch (Throwable throwable) {
				_bundleContext.ungetService(serviceReference);

				throw throwable;
			}

			return infoItemDetailsProvider;
		}

		@Override
		public void modifiedService(
			ServiceReference<InfoItemDetailsProvider<?>> serviceReference,
			InfoItemDetailsProvider<?> infoItemDetailsProvider) {

			removedService(serviceReference, infoItemDetailsProvider);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<InfoItemDetailsProvider<?>> serviceReference,
			InfoItemDetailsProvider<?> infoItemDetailsProvider) {

			InfoItemClassDetails infoItemClassDetails =
				infoItemDetailsProvider.getInfoItemClassDetails();

			ServiceRegistration<SiteNavigationMenuItemType>
				serviceRegistration = _serviceRegistrations.remove(
					infoItemClassDetails.getClassName());

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}

		private boolean _hasDisplayPageInfoItemCapability(
			InfoItemClassDetails infoItemClassDetails) {

			try {
				_displayPageInfoItemCapability.verify(
					infoItemClassDetails.getClassName());

				return true;
			}
			catch (CapabilityVerificationException
						capabilityVerificationException) {

				return false;
			}
		}

		private final BundleContext _bundleContext;
		private final Map
			<String, ServiceRegistration<SiteNavigationMenuItemType>>
				_serviceRegistrations;

	}

}