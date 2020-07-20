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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Javier de Arcos
 */
@JsonFilter("ExtendedEntityPropertyFilter")
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

	@JsonIgnore
	public Set<String> getFilteredPropertyKeys() {
		return _filteredPropertyKeys;
	}

	private ExtendedEntity(
		Object entity, Map<String, Object> extendedProperties,
		Set<String> filteredPropertyKeys) {

		_entity = entity;
		_extendedProperties = new HashMap<>(extendedProperties);
		_filteredPropertyKeys = new HashSet<>(filteredPropertyKeys);
	}

	private final Object _entity;
	private final Map<String, Object> _extendedProperties;
	private final Set<String> _filteredPropertyKeys;

}