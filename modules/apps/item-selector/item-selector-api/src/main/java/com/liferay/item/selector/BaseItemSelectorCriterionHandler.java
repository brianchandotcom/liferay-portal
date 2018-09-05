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

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.ClassUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Roberto Díaz
 */
public abstract class BaseItemSelectorCriterionHandler
	<T extends ItemSelectorCriterion> implements ItemSelectorCriterionHandler {

	@Override
	@SuppressWarnings("unchecked")
	public List<ItemSelectorView<T>> getItemSelectorViews(
		ItemSelectorCriterion itemSelectorCriterion) {

		List<ItemSelectorView<T>> filteredItemSelectedViews = new ArrayList<>();

		List<ItemSelectorView> itemSelectorViews =
			_serviceTrackerMap.getService(itemSelectorCriterion.getClass());

		if (itemSelectorViews == null) {
			return Collections.emptyList();
		}

		for (ItemSelectorView itemSelectorView : itemSelectorViews) {
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
			bundleContext, ItemSelectorViewReturnTypeProviderHandler.class,
			null);

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ItemSelectorView.class, null,
			new ItemSelectorViewServiceReferenceMapper(bundleContext),
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator(
					"item.selector.view.order")));
	}

	protected void deactivate() {
		_serviceTracker.close();

		_serviceTrackerMap.close();
	}

	private boolean _isItemSelectorViewSupported(
		ItemSelectorView itemSelectorView,
		ItemSelectorReturnType itemSelectorReturnType) {

		String itemSelectorReturnTypeClassName = ClassUtil.getClassName(
			itemSelectorReturnType);

		List<ItemSelectorReturnType> supportedItemSelectorReturnTypes = null;

		ItemSelectorViewReturnTypeProviderHandler
			itemSelectorViewReturnTypeProviderHandler =
				_serviceTracker.getService();

		if (itemSelectorViewReturnTypeProviderHandler != null) {
			supportedItemSelectorReturnTypes =
				itemSelectorViewReturnTypeProviderHandler.
					getSupportedItemSelectorReturnTypes(itemSelectorView);
		}
		else {
			supportedItemSelectorReturnTypes =
				itemSelectorView.getSupportedItemSelectorReturnTypes();
		}

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

	private ServiceTracker
		<ItemSelectorViewReturnTypeProviderHandler,
		 ItemSelectorViewReturnTypeProviderHandler> _serviceTracker;
	private ServiceTrackerMap<Class, List<ItemSelectorView>> _serviceTrackerMap;

	private class ItemSelectorViewServiceReferenceMapper
		implements ServiceReferenceMapper<Class, ItemSelectorView> {

		public ItemSelectorViewServiceReferenceMapper(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public void map(
			ServiceReference<ItemSelectorView> serviceReference,
			Emitter<Class> emitter) {

			ItemSelectorView itemSelectorView = _bundleContext.getService(
				serviceReference);

			try {
				Class<?> itemSelectorCriterionClass =
					itemSelectorView.getItemSelectorCriterionClass();

				if (itemSelectorCriterionClass.isAssignableFrom(
						BaseItemSelectorCriterionHandler.this.
							getItemSelectorCriterionClass())) {

					emitter.emit(
						itemSelectorView.getItemSelectorCriterionClass());
				}
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}
		}

		private final BundleContext _bundleContext;

	}

}