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

package com.liferay.item.selector.web.util;

import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONContext;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.json.JSONTransformer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Accessor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.beanutils.PropertyUtils;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Iván Zaera
 */
@Component(immediate = true, service = ItemSelectorCriterionSerializer.class)
public class ItemSelectorCriterionSerializer<T extends ItemSelectorCriterion> {

	public static final String JSON = "json";

	public void addItemSelectorReturnType(
		ItemSelectorReturnType itemSelectorReturnType) {

		Class<? extends ItemSelectorReturnType> itemSelectorReturnTypeClass =
			itemSelectorReturnType.getClass();

		_itemSelectorReturnTypes.putIfAbsent(
			itemSelectorReturnTypeClass.getName(),
			new CopyOnWriteArrayList<ItemSelectorReturnType>());

		List<ItemSelectorReturnType> itemSelectorReturnTypes =
			_itemSelectorReturnTypes.get(itemSelectorReturnTypeClass.getName());

		itemSelectorReturnTypes.add(itemSelectorReturnType);
	}

	public Map<String, String[]> getProperties(
		ItemSelectorCriterion itemSelectorCriterion, String prefix) {

		Map<String, String[]> properties = new HashMap<>();

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		String[] externalPropertyKeys = getExternalPropertyKeys(
			itemSelectorCriterion);

		String[] serializableFields = ArrayUtil.append(
			externalPropertyKeys, "desiredItemSelectorReturnTypes");

		jsonSerializer.transform(
			new DesiredItemSelectorReturnTypesJSONTransformer(),
			"desiredItemSelectorReturnTypes");

		jsonSerializer.include(serializableFields);

		properties.put(
			prefix + JSON,
			new String[] {jsonSerializer.serialize(itemSelectorCriterion)});

		return properties;
	}

	public void setProperties(
		ItemSelectorCriterion itemSelectorCriterion, String prefix,
		Map<String, String[]> parameters) {

		try {
			String json = parameters.get(prefix + JSON)[0];

			JSONDeserializer<Map<String, ?>> jsonDeserializer =
				JSONFactoryUtil.createJSONDeserializer();

			Map<String, ?> map = jsonDeserializer.deserialize(json);

			String[] externalPropertyKeys = getExternalPropertyKeys(
				itemSelectorCriterion);

			for (String externalPropertyKey : externalPropertyKeys) {
				Class<?> serializableFieldClass = PropertyUtils.getPropertyType(
					itemSelectorCriterion, externalPropertyKey);

				Object value = map.get(externalPropertyKey);

				if (serializableFieldClass.isArray() &&
					List.class.isInstance(value)) {

					List<?> list = (List<?>)value;

					value = list.toArray(
						(Object[])Array.newInstance(
							serializableFieldClass.getComponentType(),
							list.size()));
				}
				else if (((serializableFieldClass == Long.class) ||
						  (serializableFieldClass == Long.TYPE)) &&
						 (value instanceof String)) {

					value = Long.valueOf((String)value);
				}

				PropertyUtils.setProperty(
					itemSelectorCriterion, externalPropertyKey, value);
			}

			_setDesiredItemSelectorReturnTypes(itemSelectorCriterion, map);
		}
		catch (IllegalAccessException | InvocationTargetException |
			   NoSuchMethodException e) {

			throw new SystemException(e);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, ItemSelectorView.class,
			new ItemSelectorReturnTypeServiceTrackerCustomizer());
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private boolean _isInternalProperty(String name) {
		if (name.equals("availableItemSelectorReturnTypes") ||
			name.equals("class") ||
			name.equals("desiredItemSelectorReturnTypes")) {

			return true;
		}

		return false;
	}

	private void _setDesiredItemSelectorReturnTypes(
		ItemSelectorCriterion itemSelectorCriterion, Map<String, ?> map) {

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		String[] desiredItemSelectorReturnTypeNames = StringUtil.split(
			(String)map.get("desiredItemSelectorReturnTypes"));

		for (String desiredItemSelectorReturnTypeName :
				desiredItemSelectorReturnTypeNames) {

			List<ItemSelectorReturnType> itemSelectorReturnTypes =
				_itemSelectorReturnTypes.get(desiredItemSelectorReturnTypeName);

			if (ListUtil.isEmpty(itemSelectorReturnTypes)) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No return types are registered for " +
							desiredItemSelectorReturnTypeName);
				}

				continue;
			}

			Iterator<ItemSelectorReturnType> iterator =
				itemSelectorReturnTypes.iterator();

			if (iterator.hasNext()) {
				desiredItemSelectorReturnTypes.add(iterator.next());
			}
		}

		if (desiredItemSelectorReturnTypes.isEmpty()) {
			if (_log.isWarnEnabled()) {
				_log.warn("No valid desired item selector return types found");
			}
		}

		itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredItemSelectorReturnTypes);
	}

	private String[] getExternalPropertyKeys(
		ItemSelectorCriterion itemSelectorCriterion) {

		List<String> list = new ArrayList<>();

		try {
			Map<String, Object> map = PropertyUtils.describe(
				itemSelectorCriterion);

			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();

				if (_isInternalProperty(key)) {
					continue;
				}

				list.add(key);
			}
		}
		catch (IllegalAccessException | InvocationTargetException |
			   NoSuchMethodException e) {

			throw new SystemException(e);
		}

		return list.toArray(new String[list.size()]);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ItemSelectorCriterionSerializer.class);

	private BundleContext _bundleContext;
	private final ConcurrentMap<String, List<ItemSelectorReturnType>>
		_itemSelectorReturnTypes = new ConcurrentHashMap<>();
	private ServiceTracker<ItemSelectorView, ItemSelectorView> _serviceTracker;

	private static class DesiredItemSelectorReturnTypesJSONTransformer
		implements JSONTransformer {

		@Override
		public void transform(JSONContext jsonContext, Object object) {
			List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
				(List<ItemSelectorReturnType>)object;

			String desiredItemSelectorReturnTypesString = ListUtil.toString(
				desiredItemSelectorReturnTypes,
				new Accessor<ItemSelectorReturnType, String>() {

					@Override
					public String get(
						ItemSelectorReturnType itemSelectorReturnType) {

						Class<? extends ItemSelectorReturnType>
							itemSelectorReturnTypeClass =
								itemSelectorReturnType.getClass();

						return itemSelectorReturnTypeClass.getName();
					}

					@Override
					public Class<String> getAttributeClass() {
						return String.class;
					}

					@Override
					public Class<ItemSelectorReturnType> getTypeClass() {
						return ItemSelectorReturnType.class;
					}

				}
			);

			jsonContext.write(
				StringUtil.quote(
					desiredItemSelectorReturnTypesString, StringPool.QUOTE));
		}

	}

	private class ItemSelectorReturnTypeServiceTrackerCustomizer
		implements
			ServiceTrackerCustomizer<ItemSelectorView, ItemSelectorView> {

		@Override
		public ItemSelectorView addingService(
			ServiceReference<ItemSelectorView> serviceReference) {

			ItemSelectorView itemSelectorView = _bundleContext.getService(
				serviceReference);

			List<ItemSelectorReturnType> supportedItemSelectorReturnTypes =
				itemSelectorView.getSupportedItemSelectorReturnTypes();

			for (ItemSelectorReturnType supportedItemSelectorReturnType :
					supportedItemSelectorReturnTypes) {

				Class<? extends ItemSelectorReturnType>
					supportedItemSelectorReturnTypeClass =
						supportedItemSelectorReturnType.getClass();

				_itemSelectorReturnTypes.putIfAbsent(
					supportedItemSelectorReturnTypeClass.getName(),
					new CopyOnWriteArrayList<ItemSelectorReturnType>());

				List<ItemSelectorReturnType> itemSelectorReturnTypes =
					_itemSelectorReturnTypes.get(
						supportedItemSelectorReturnTypeClass.getName());

				itemSelectorReturnTypes.add(supportedItemSelectorReturnType);
			}

			return itemSelectorView;
		}

		@Override
		public void modifiedService(
			ServiceReference<ItemSelectorView> serviceReference,
			ItemSelectorView ItemSelectorView) {
		}

		@Override
		public void removedService(
			ServiceReference<ItemSelectorView> serviceReference,
			ItemSelectorView itemSelectorView) {

			try {
				List<ItemSelectorReturnType> supportedItemSelectorReturnTypes =
					itemSelectorView.getSupportedItemSelectorReturnTypes();

				for (ItemSelectorReturnType supportedItemSelectorReturnType :
						supportedItemSelectorReturnTypes) {

					Class<? extends ItemSelectorReturnType>
						supportedItemSelectorReturnTypeClass =
							supportedItemSelectorReturnType.getClass();

					List<ItemSelectorReturnType> itemSelectorReturnTypes =
						_itemSelectorReturnTypes.get(
							supportedItemSelectorReturnTypeClass.getName());

					itemSelectorReturnTypes.remove(0);
				}
			}
			finally {
				_bundleContext.ungetService(serviceReference);
			}
		}

	}

}