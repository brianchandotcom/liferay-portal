/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	<T extends ItemSelectorCriterion>
		implements ItemSelectorCriterionHandler {

	@Override
	@SuppressWarnings("unchecked")
	public List<ItemSelectorView<T>> getItemSelectorViews(
		ItemSelectorCriterion itemSelectorCriterion) {

		List<ItemSelectorView<T>> itemSelectorViews =
			_serviceTrackerMap.getService(itemSelectorCriterion.getClass());

		if (itemSelectorViews == null) {
			return Collections.emptyList();
		}

		List<ItemSelectorView<T>> filteredItemSelectedViews = new ArrayList<>();

		for (ItemSelectorView<T> itemSelectorView : itemSelectorViews) {
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
			bundleContext,
			(Class<ItemSelectorView<T>>)(Class<?>)ItemSelectorView.class, null,
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
		ItemSelectorView<T> itemSelectorView,
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
	private ServiceTrackerMap<Class<?>, List<ItemSelectorView<T>>>
		_serviceTrackerMap;

	private class ItemSelectorViewServiceReferenceMapper
		implements ServiceReferenceMapper<Class<?>, ItemSelectorView<T>> {

		public ItemSelectorViewServiceReferenceMapper(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public void map(
			ServiceReference<ItemSelectorView<T>> serviceReference,
			Emitter<Class<?>> emitter) {

			ItemSelectorView<T> itemSelectorView = _bundleContext.getService(
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