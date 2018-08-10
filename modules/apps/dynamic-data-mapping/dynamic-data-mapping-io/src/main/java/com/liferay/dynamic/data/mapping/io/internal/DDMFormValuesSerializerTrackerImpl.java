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

import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerTracker;
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
public class DDMFormValuesSerializerTrackerImpl
	implements DDMFormValuesSerializerTracker {

	@Override
	public DDMFormValuesSerializer getDDMFormValuesSerializer(String type) {
		return _ddmFormValuesSerializers.get(type);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDDMFormValuesSerializer(
		DDMFormValuesSerializer ddmFormValuesSerializer,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "ddm.form.values.serializer.type");

		_ddmFormValuesSerializers.put(type, ddmFormValuesSerializer);
	}

	@Deactivate
	protected void deactivate() {
		_ddmFormValuesSerializers.clear();
	}

	protected void removeDDMFormValuesSerializer(
		DDMFormValuesSerializer ddmFormValuesSerializer,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "ddm.form.values.serializer.type");

		_ddmFormValuesSerializers.remove(type);
	}

	private final Map<String, DDMFormValuesSerializer>
		_ddmFormValuesSerializers = new TreeMap<>();

}