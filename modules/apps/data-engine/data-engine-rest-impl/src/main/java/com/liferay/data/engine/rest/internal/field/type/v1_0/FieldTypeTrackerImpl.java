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

package com.liferay.data.engine.rest.internal.field.type.v1_0;

import com.liferay.data.engine.field.type.FieldType;
import com.liferay.data.engine.field.type.FieldTypeTracker;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = FieldTypeTracker.class)
public class FieldTypeTrackerImpl implements FieldTypeTracker {

	@Override
	public FieldType getFieldType(String name) {
		return _fieldTypes.get(name);
	}

	@Override
	public Map<String, Object> getFieldTypeProperties(String name) {
		return Optional.ofNullable(
			_fieldTypesProperties.get(name)
		).orElse(
			Collections.emptyMap()
		);
	}

	public Collection<FieldType> getFieldTypes() {
		return Collections.unmodifiableCollection(_fieldTypes.values());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addFieldType(
		FieldType fieldType, Map<String, Object> properties) {

		String name = fieldType.getName();

		_fieldTypes.put(name, fieldType);
		_fieldTypesProperties.put(
			name, Collections.unmodifiableMap(properties));
	}

	@Deactivate
	protected void deactivate() {
		_fieldTypes.clear();
		_fieldTypesProperties.clear();
	}

	protected void removeFieldType(
		FieldType fieldType, Map<String, Object> properties) {

		String name = fieldType.getName();

		_fieldTypes.remove(name);
		_fieldTypesProperties.remove(name);
	}

	private final Map<String, FieldType> _fieldTypes = new TreeMap<>();
	private final Map<String, Map<String, Object>> _fieldTypesProperties =
		new TreeMap<>();

}