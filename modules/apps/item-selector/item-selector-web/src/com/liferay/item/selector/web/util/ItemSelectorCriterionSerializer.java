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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author Iván Zaera
 */
public class ItemSelectorCriterionSerializer<T extends ItemSelectorCriterion> {

	public static final String JSON = "json";

	public ItemSelectorCriterionSerializer(
		T itemSelectorCriterion, String prefix) {

		_itemSelectorCriterion = itemSelectorCriterion;
		_prefix = prefix;

		_initExternalPropertyKeys();
	}

	public Map<String, String[]> getProperties() {
		Map<String, String[]> properties = new HashMap<>();

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		String[] serializableFields = ArrayUtil.append(
			_externalPropertyKeys, "desiredItemSelectorReturnTypes");

		jsonSerializer.include(serializableFields);

		properties.put(
			_prefix + JSON,
			new String[] {jsonSerializer.serialize(_itemSelectorCriterion)});

		return properties;
	}

	public void setProperties(Map<String, String[]> parameters) {
		try {
			String json = parameters.get(_prefix + JSON)[0];

			JSONDeserializer<Map<String, ?>> jsonDeserializer =
				JSONFactoryUtil.createJSONDeserializer();

			Map<String, ?> map = jsonDeserializer.deserialize(json);

			for (String externalPropertyKey : _externalPropertyKeys) {
				Class<?> serializableFieldClass = PropertyUtils.getPropertyType(
					_itemSelectorCriterion, externalPropertyKey);

				Object value = map.get(externalPropertyKey);

				if (serializableFieldClass.isArray() &&
					List.class.isInstance(value)) {

					List<?> list = (List<?>)value;

					value = list.toArray(
						(Object[])Array.newInstance(
							serializableFieldClass.getComponentType(),
							list.size()));
				}

				PropertyUtils.setProperty(
					_itemSelectorCriterion, externalPropertyKey, value);
			}

			_setDesiredReturnTypes(map);
		}
		catch (IllegalAccessException | InvocationTargetException |
			NoSuchMethodException e) {

			throw new SystemException(e);
		}
	}

	private void _initExternalPropertyKeys() {
		List<String> list = new ArrayList<>();

		try {
			Map<String, Object> map = PropertyUtils.describe(
				_itemSelectorCriterion);

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

		_externalPropertyKeys = list.toArray(new String[list.size()]);
	}

	private boolean _isInternalProperty(String name) {
		if (name.equals("class") ||
			name.equals("availableItemSelectorReturnTypes") ||
			name.equals("desiredItemSelectorReturnTypes")) {

			return true;
		}

		return false;
	}

	private void _setDesiredReturnTypes(Map<String, ?> map) {
		Set<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new LinkedHashSet<>();

		List<String> desiredItemSelectorReturnTypeNames = (List<String>)map.get(
			"desiredItemSelectorReturnTypes");

		for (String desiredItemSelectorReturnTypeName :
				desiredItemSelectorReturnTypeNames) {

			Set<ItemSelectorReturnType> availableItemSelectorReturnTypes =
				_itemSelectorCriterion.getAvailableItemSelectorReturnTypes();

			for (ItemSelectorReturnType availableItemSelectorReturnType :
					availableItemSelectorReturnTypes) {

				String availableItemSelectorReturnTypeName =
					availableItemSelectorReturnType.getName();

				if (availableItemSelectorReturnTypeName.equals(
						desiredItemSelectorReturnTypeName)) {

					desiredItemSelectorReturnTypes.add(
						availableItemSelectorReturnType);

					break;
				}
			}
		}

		if (desiredItemSelectorReturnTypes.isEmpty()) {
			if (_log.isWarnEnabled()) {
				_log.warn("No valid item selector return types found");
			}
		}

		_itemSelectorCriterion.setItemSelectorDesiredReturnTypes(
			desiredItemSelectorReturnTypes);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ItemSelectorCriterionSerializer.class);

	private String[] _externalPropertyKeys;
	private final T _itemSelectorCriterion;
	private final String _prefix;

}