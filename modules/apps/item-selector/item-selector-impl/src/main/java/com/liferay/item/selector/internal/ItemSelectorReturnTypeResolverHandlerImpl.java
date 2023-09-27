/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.item.selector.internal;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewReturnTypeProviderHandler;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = ItemSelectorReturnTypeResolverHandler.class)
public class ItemSelectorReturnTypeResolverHandlerImpl
	implements ItemSelectorReturnTypeResolverHandler {

	@Override
	public ItemSelectorReturnTypeResolver<?, ?>
		getItemSelectorReturnTypeResolver(
			Class<? extends ItemSelectorReturnType> itemSelectorReturnTypeClass,
			Class<?> modelClass) {

		return _serviceTrackerMap.getService(
			_getKey(itemSelectorReturnTypeClass, modelClass));
	}

	@Override
	public ItemSelectorReturnTypeResolver<?, ?>
		getItemSelectorReturnTypeResolver(
			ItemSelectorCriterion itemSelectorCriterion,
			ItemSelectorView<?> itemSelectorView, Class<?> modelClass) {

		ItemSelectorReturnType itemSelectorReturnType =
			_getFirstAvailableItemSelectorReturnType(
				itemSelectorCriterion.getDesiredItemSelectorReturnTypes(),
				_itemSelectorViewReturnTypeProviderHandler.
					getSupportedItemSelectorReturnTypes(itemSelectorView));

		return getItemSelectorReturnTypeResolver(
			itemSelectorReturnType.getClass(), modelClass);
	}

	@Override
	public ItemSelectorReturnTypeResolver<?, ?>
		getItemSelectorReturnTypeResolver(
			String itemSelectorReturnTypeClassName, String modelClassName) {

		return _serviceTrackerMap.getService(
			_getKey(itemSelectorReturnTypeClassName, modelClassName));
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext,
			(Class<ItemSelectorReturnTypeResolver<?, ?>>)
				(Class<?>)ItemSelectorReturnTypeResolver.class,
			null,
			new ItemSelectorReturnTypeResolverServiceReferenceMapper(
				bundleContext));
	}

	private ItemSelectorReturnType _getFirstAvailableItemSelectorReturnType(
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes,
		List<ItemSelectorReturnType> supportedItemSelectorReturnTypes) {

		List<String> supportedItemSelectorReturnTypeNames = ListUtil.toList(
			supportedItemSelectorReturnTypes, ClassUtil::getClassName);

		for (ItemSelectorReturnType itemSelectorReturnType :
				desiredItemSelectorReturnTypes) {

			if (supportedItemSelectorReturnTypeNames.contains(
					ClassUtil.getClassName(itemSelectorReturnType))) {

				return itemSelectorReturnType;
			}
		}

		return null;
	}

	private String _getKey(
		Class<?> itemSelectorReturnTypeClass, Class<?> modelClass) {

		String itemSelectorResolverReturnTypeClassName =
			itemSelectorReturnTypeClass.getName();

		String itemSelectorResolverModelClassName = modelClass.getName();

		return _getKey(
			itemSelectorResolverReturnTypeClassName,
			itemSelectorResolverModelClassName);
	}

	private String _getKey(
		String itemSelectorResolverReturnTypeClassName,
		String itemSelectorResolverModelClassName) {

		return itemSelectorResolverReturnTypeClassName + StringPool.UNDERLINE +
			itemSelectorResolverModelClassName;
	}

	@Reference
	private ItemSelectorViewReturnTypeProviderHandler
		_itemSelectorViewReturnTypeProviderHandler;

	private ServiceTrackerMap<String, ItemSelectorReturnTypeResolver<?, ?>>
		_serviceTrackerMap;

	private class ItemSelectorReturnTypeResolverServiceReferenceMapper
		implements ServiceReferenceMapper
			<String, ItemSelectorReturnTypeResolver<?, ?>> {

		public ItemSelectorReturnTypeResolverServiceReferenceMapper(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public void map(
			ServiceReference<ItemSelectorReturnTypeResolver<?, ?>>
				serviceReference,
			Emitter<String> emitter) {

			ItemSelectorReturnTypeResolver<?, ?>
				itemSelectorReturnTypeResolver = _bundleContext.getService(
					serviceReference);

			try {
				emitter.emit(
					_getKey(
						itemSelectorReturnTypeResolver.
							getItemSelectorReturnTypeClass(),
						itemSelectorReturnTypeResolver.getModelClass()));
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}
		}

		private final BundleContext _bundleContext;

	}

}