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

package com.liferay.dynamic.data.mapping.io.internal;

import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializerTracker;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;
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
@Component(immediate = true)
public class DDMFormFieldTypesSerializerTrackerImpl
	implements DDMFormFieldTypesSerializerTracker {

	@Override
	public DDMFormFieldTypesSerializer getDDMFormFieldTypesSerializer(
		String type) {

		return _ddmFormFieldTypesSerializers.get(type);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDDMFormFieldTypesSerializer(
		DDMFormFieldTypesSerializer ddmFormFieldTypesSerializer,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "ddm.form.field.types.serializer.type");

		_ddmFormFieldTypesSerializers.put(type, ddmFormFieldTypesSerializer);
	}

	@Deactivate
	protected void deactivate() {
		_ddmFormFieldTypesSerializers.clear();
	}

	protected void removeDDMFormFieldTypesSerializer(
		DDMFormFieldTypesSerializer ddmFormFieldTypesSerializer,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "ddm.form.field.types.serializer.type");

		_ddmFormFieldTypesSerializers.remove(type);
	}

	private final Map<String, DDMFormFieldTypesSerializer>
		_ddmFormFieldTypesSerializers = new TreeMap<>();

}