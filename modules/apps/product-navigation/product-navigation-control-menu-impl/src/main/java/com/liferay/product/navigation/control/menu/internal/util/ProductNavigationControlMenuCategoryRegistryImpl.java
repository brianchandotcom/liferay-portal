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

package com.liferay.product.navigation.control.menu.internal.util;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuCategory;
import com.liferay.product.navigation.control.menu.ProductNavigationControlMenuEntry;
import com.liferay.product.navigation.control.menu.util.ProductNavigationControlMenuCategoryRegistry;
import com.liferay.product.navigation.control.menu.util.ProductNavigationControlMenuCategoryServiceReferenceMapper;
import com.liferay.product.navigation.control.menu.util.ProductNavigationControlMenuEntryRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Julio Camarero
 */
@Component(
	immediate = true,
	service = ProductNavigationControlMenuCategoryRegistry.class
)
public class ProductNavigationControlMenuCategoryRegistryImpl
	implements ProductNavigationControlMenuCategoryRegistry {

	@Override
	public List<ProductNavigationControlMenuCategory>
		getProductNavigationControlMenuCategories(
			String productNavigationControlMenuCategoryKey) {

		List<ProductNavigationControlMenuCategory>
			productNavigationControlMenuCategories =
				_productNavigationControlMenuCategoryServiceTrackerMap.
					getService(productNavigationControlMenuCategoryKey);

		if (productNavigationControlMenuCategories == null) {
			return Collections.emptyList();
		}

		return new ArrayList<>(productNavigationControlMenuCategories);
	}

	@Override
	public List<ProductNavigationControlMenuCategory>
		getProductNavigationControlMenuCategories(
			String productNavigationControlMenuCategoryKey,
			final HttpServletRequest httpServletRequest) {

		List<ProductNavigationControlMenuCategory>
			productNavigationControlMenuCategories =
				getProductNavigationControlMenuCategories(
					productNavigationControlMenuCategoryKey);

		if (productNavigationControlMenuCategories.isEmpty()) {
			return productNavigationControlMenuCategories;
		}

		return ListUtil.filter(
			productNavigationControlMenuCategories,
			productNavigationControlMenuCategory -> {
				try {
					if (!productNavigationControlMenuCategory.
							hasAccessPermission(httpServletRequest)) {

						return false;
					}

					List<ProductNavigationControlMenuEntry>
						productNavigationControlMenuEntries =
							_productNavigationControlMenuEntryRegistry.
								getProductNavigationControlMenuEntries(
									productNavigationControlMenuCategory,
									httpServletRequest);

					if (productNavigationControlMenuEntries.isEmpty()) {
						return false;
					}

					return true;
				}
				catch (PortalException pe) {
					_log.error(pe, pe);
				}

				return false;
			});
	}

	@Activate
	protected void activate(final BundleContext bundleContext) {
		_productNavigationControlMenuCategoryServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, ProductNavigationControlMenuCategory.class,
				"(product.navigation.control.menu.category.key=*)",
				new ProductNavigationControlMenuCategoryServiceReferenceMapper(),
				Collections.reverseOrder(
					new PropertyServiceReferenceComparator(
						"product.navigation.control.menu.category.order")));
	}

	@Deactivate
	protected void deactivate() {
		_productNavigationControlMenuCategoryServiceTrackerMap.close();
	}

	@Reference(unbind = "-")
	protected void setProductNavigationControlMenuEntryRegistry(
		ProductNavigationControlMenuEntryRegistry
			productNavigationControlMenuEntryRegistry) {

		_productNavigationControlMenuEntryRegistry =
			productNavigationControlMenuEntryRegistry;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductNavigationControlMenuCategoryRegistryImpl.class);

	private ServiceTrackerMap
		<String, List<ProductNavigationControlMenuCategory>>
			_productNavigationControlMenuCategoryServiceTrackerMap;
	private ProductNavigationControlMenuEntryRegistry
		_productNavigationControlMenuEntryRegistry;

}