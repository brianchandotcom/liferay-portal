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

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(immediate = true, service = ItemSelectorCriterionSerializer.class)
public class ItemSelectorCriterionSerializer<T extends ItemSelectorCriterion> {

	public static final String JSON = "json";

	public Map<String, String[]> getProperties(
		ItemSelectorCriterion itemSelectorCriterion, String prefix) {

		Map<String, String[]> properties = new HashMap<>();

		JSONSerializer jsonSerializer = JSONFactoryUtil.createJSONSerializer();

		String[] externalPropertyKeys = getExternalPropertyKeys(
			itemSelectorCriterion);

		String[] serializableFields = ArrayUtil.append(
			externalPropertyKeys, "desiredItemSelectorReturnTypes");

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

		Set<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new LinkedHashSet<>();

		List<String> desiredItemSelectorReturnTypeNames = (List<String>)map.get(
			"desiredItemSelectorReturnTypes");

		for (String desiredItemSelectorReturnTypeName :
				desiredItemSelectorReturnTypeNames) {

			Set<ItemSelectorReturnType> availableItemSelectorReturnTypes =
				itemSelectorCriterion.getAvailableItemSelectorReturnTypes();

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

}