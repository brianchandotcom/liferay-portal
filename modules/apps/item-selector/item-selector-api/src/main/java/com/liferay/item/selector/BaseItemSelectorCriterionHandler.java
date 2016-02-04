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
import com.liferay.portal.kernel.util.ClassUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

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

		List<ItemSelectorView> itemSelectorViews =
			_serviceTrackerMap.getService(itemSelectorCriterion.getClass());

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
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ItemSelectorView.class, null,
			new ItemSelectorViewServiceReferenceMapper(bundleContext),
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator("service.ranking")));
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

			ItemSelectorView service = _bundleContext.getService(
				serviceReference);

			try {
				Class<?> itemSelectorCriterionClass =
					service.getItemSelectorCriterionClass();

				if (itemSelectorCriterionClass.isAssignableFrom(
						BaseItemSelectorCriterionHandler.this.
							getItemSelectorCriterionClass())) {

					emitter.emit(service.getItemSelectorCriterionClass());
				}
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}
		}

		private final BundleContext _bundleContext;

	}

}