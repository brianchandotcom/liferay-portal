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

package com.liferay.site.navigation.menu.item.display.page.internal.type;

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.item.selector.ItemSelector;
import com.liferay.layout.display.page.LayoutDisplayPageProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.page.template.info.item.capability.DisplayPageInfoItemCapability;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.site.navigation.menu.item.display.page.internal.configuration.FFDisplayPageSiteNavigationMenuItemConfigurationUtil;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(immediate = true, service = {})
public class DisplayPageSiteNavigationMenuItemTypeRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistrations = new HashMap<>();

		if (!FFDisplayPageSiteNavigationMenuItemConfigurationUtil.
				displayPageTypesEnabled()) {

			return;
		}

		int ranking = 300;

		for (InfoItemClassDetails infoItemClassDetails :
				_infoItemServiceTracker.getInfoItemClassDetails(
					DisplayPageInfoItemCapability.KEY)) {

			LayoutDisplayPageProvider<?> layoutDisplayPageProvider =
				_layoutDisplayPageProviderTracker.
					getLayoutDisplayPageProviderByClassName(
						infoItemClassDetails.getClassName());

			if (layoutDisplayPageProvider == null) {
				continue;
			}

			_serviceRegistrations.put(
				infoItemClassDetails.getClassName(),
				bundleContext.registerService(
					SiteNavigationMenuItemType.class,
					new DisplayPageTypeSiteNavigationMenuItemType(
						_assetDisplayPageFriendlyURLProvider,
						new DisplayPageTypeContext(
							infoItemClassDetails,
							_infoItemServiceTracker.getFirstInfoItemService(
								InfoItemFormVariationsProvider.class,
								infoItemClassDetails.getClassName()),
							layoutDisplayPageProvider),
						_itemSelector, _jspRenderer, _portal, _servletContext),
					HashMapDictionaryBuilder.<String, Object>put(
						"service.ranking:Integer", ranking
					).put(
						"site.navigation.menu.item.type",
						infoItemClassDetails.getClassName()
					).build()));

			ranking += 10;
		}
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<?> serviceRegistration :
				_serviceRegistrations.values()) {

			serviceRegistration.unregister();
		}
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

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

	private Map<String, ServiceRegistration<?>> _serviceRegistrations;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.menu.item.display.page)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}