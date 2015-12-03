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

package com.liferay.item.selector;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.ClassUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Roberto Díaz
 */
public abstract class BaseItemSelectorCriterionHandler
	<T extends ItemSelectorCriterion> implements ItemSelectorCriterionHandler {

	@Override
	@SuppressWarnings("unchecked")
	public List<ItemSelectorView<T>>
		getItemSelectorViews(ItemSelectorCriterion itemSelectorCriterion) {

		List<ItemSelectorView<T>> filteredItemSelectedViews = new ArrayList<>();

		for (ItemSelectorView itemSelectorView : _itemSelectorViews) {
			List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
				itemSelectorCriterion.getDesiredItemSelectorReturnTypes();

			for (ItemSelectorReturnType desiredItemSelectorReturnType :
					desiredItemSelectorReturnTypes) {

				if (_isItemSelectorViewSupported(
						itemSelectorView, desiredItemSelectorReturnType)) {

					filteredItemSelectedViews.add(itemSelectorView);

					break;
				}
			}
		}

		return (List)Collections.unmodifiableList(filteredItemSelectedViews);
	}

	protected void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, ItemSelectorView.class,
			new ItemSelectorViewServiceTrackerCustomizer(bundleContext));
	}

	private boolean _isItemSelectorViewSupported(
		ItemSelectorView itemSelectorView,
		ItemSelectorReturnType itemSelectorReturnType) {

		String itemSelectorReturnTypeClassName = ClassUtil.getClassName(
			itemSelectorReturnType);

		List<ItemSelectorReturnType> supportedItemSelectorReturnTypes =
			itemSelectorView.getSupportedItemSelectorReturnTypes();

		for (ItemSelectorReturnType supportedItemSelectorReturnType :
				supportedItemSelectorReturnTypes) {

			String supportedItemSelectorReturnTypeClassName =
				ClassUtil.getClassName(supportedItemSelectorReturnType);

			if (itemSelectorReturnTypeClassName.equals(
					supportedItemSelectorReturnTypeClassName)) {

				return true;
			}
		}

		return false;
	}

	private final List<ItemSelectorView<?>> _itemSelectorViews =
		new CopyOnWriteArrayList<>();
	private ServiceTracker _serviceTracker;

	private class ItemSelectorViewServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ItemSelectorView, ItemSelectorView> {

		public ItemSelectorViewServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public ItemSelectorView addingService(
			ServiceReference<ItemSelectorView> reference) {

			ItemSelectorView service = _bundleContext.getService(reference);

			Class<?> itemSelectorCriterionClass =
				service.getItemSelectorCriterionClass();

			if (!itemSelectorCriterionClass.isAssignableFrom(
					BaseItemSelectorCriterionHandler.this.
						getItemSelectorCriterionClass())) {

				return null;
			}

			_itemSelectorViews.add(service);

			return service;
		}

		@Override
		public void modifiedService(
			ServiceReference<ItemSelectorView> serviceReference,
			ItemSelectorView itemSelectorView) {

			removedService(serviceReference, itemSelectorView);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<ItemSelectorView> serviceReference,
			ItemSelectorView itemSelectorView) {

			_itemSelectorViews.remove(itemSelectorView);

			_bundleContext.ungetService(serviceReference);
		}

		private final BundleContext _bundleContext;

	}

}