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

package com.liferay.portal.vulcan.internal.jaxrs.extension;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author Javier de Arcos
 */
@JsonFilter("Liferay.Vulcan")
public class ExtendedEntity {

	public static ExtendedEntity extend(
		Object entity, Map<String, Object> extendedProperties,
		Set<String> filteredPropertyKeys) {

		return new ExtendedEntity(
			entity,
			Optional.ofNullable(
				extendedProperties
			).orElse(
				Collections.emptyMap()
			),
			Optional.ofNullable(
				filteredPropertyKeys
			).orElse(
				Collections.emptySet()
			));
	}

	@JsonUnwrapped
	public Object getEntity() {
		return _entity;
	}

	@JsonAnyGetter
	public Map<String, Object> getExtendedProperties() {
		return _extendedProperties;
	}

	private ExtendedEntity(
		Object entity, Map<String, Object> extendedProperties,
		Set<String> filteredPropertyKeys) {

		_entity = entity;

		_extendedProperties = new HashMap<>(extendedProperties);

		Set<String> keySet = _extendedProperties.keySet();

		keySet.removeIf(Objects::isNull);

		for (String key : filteredPropertyKeys) {
			_extendedProperties.put(key, null);
		}

		for (Field field : _getAllFields()) {
			if (_extendedProperties.containsKey(field.getName())) {
				try {
					field.setAccessible(true);
					field.set(_entity, null);
				}
				catch (Exception exception) {
					if (_log.isDebugEnabled()) {
						_log.debug(exception, exception);
					}
				}
			}
		}
	}

	private Field[] _getAllFields() {
		List<Field> fields = new ArrayList<>();

		Class<?> clazz = _entity.getClass();

		while (clazz != Object.class) {
			Collections.addAll(fields, clazz.getDeclaredFields());
			clazz = clazz.getSuperclass();
		}

		return fields.toArray(new Field[0]);
	}

	private static final Log _log = LogFactoryUtil.getLog(ExtendedEntity.class);

	private final Object _entity;
	private final Map<String, Object> _extendedProperties;

}