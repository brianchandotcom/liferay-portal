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

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(service = ItemSelectorReturnTypeResolverHandler.class)
public class ItemSelectorReturnTypeResolverHandler {

	public ItemSelectorReturnTypeResolver getItemSelectorReturnTypeResolver(
		Class<? extends ItemSelectorReturnType> itemSelectorReturnTypeClass,
		Class<?> modelClass) {

		return _serviceTrackerMap.getService(
			_getKey(itemSelectorReturnTypeClass, modelClass));
	}

	public ItemSelectorReturnTypeResolver getItemSelectorReturnTypeResolver(
		ItemSelectorCriterion itemSelectorCriterion,
		ItemSelectorView itemSelectorView, Class<?> modelClass) {

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			itemSelectorCriterion.getDesiredItemSelectorReturnTypes();

		ItemSelectorReturnType itemSelectorReturnType =
			getFirstAvailableItemSelectorReturnType(
				desiredItemSelectorReturnTypes,
				itemSelectorView.getSupportedItemSelectorReturnTypes());

		return getItemSelectorReturnTypeResolver(
			itemSelectorReturnType.getClass(), modelClass);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ItemSelectorReturnTypeResolver.class, null,
			new ItemSelectorReturnTypeResolverServiceReferenceMapper(
				bundleContext));
	}

	protected ItemSelectorReturnType getFirstAvailableItemSelectorReturnType(
		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes,
		List<ItemSelectorReturnType> supportedItemSelectorReturnTypes) {

		List<String> supportedItemSelectorReturnTypeNames = ListUtil.toList(
			supportedItemSelectorReturnTypes, ClassUtil::getClassName);

		for (ItemSelectorReturnType itemSelectorReturnType :
				desiredItemSelectorReturnTypes) {

			String className = ClassUtil.getClassName(itemSelectorReturnType);

			if (supportedItemSelectorReturnTypeNames.contains(className)) {
				return itemSelectorReturnType;
			}
		}

		return null;
	}

	private String _getKey(
		Class itemSelectorReturnTypeClass, Class modelClass) {

		String itemSelectorResolverReturnTypeName =
			itemSelectorReturnTypeClass.getName();

		String itemSelectorResolverModelName = modelClass.getName();

		return itemSelectorResolverReturnTypeName + StringPool.UNDERLINE +
			itemSelectorResolverModelName;
	}

	private ServiceTrackerMap<String, ItemSelectorReturnTypeResolver>
		_serviceTrackerMap;

	private class ItemSelectorReturnTypeResolverServiceReferenceMapper
		implements ServiceReferenceMapper
			<String, ItemSelectorReturnTypeResolver> {

		public ItemSelectorReturnTypeResolverServiceReferenceMapper(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public void map(
			ServiceReference<ItemSelectorReturnTypeResolver> serviceReference,
			Emitter<String> emitter) {

			ItemSelectorReturnTypeResolver itemSelectorReturnTypeResolver =
				_bundleContext.getService(serviceReference);

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