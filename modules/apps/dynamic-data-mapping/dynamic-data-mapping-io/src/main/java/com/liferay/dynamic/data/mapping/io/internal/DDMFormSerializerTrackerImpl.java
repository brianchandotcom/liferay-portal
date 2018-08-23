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

import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerTracker;
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
public class DDMFormSerializerTrackerImpl implements DDMFormSerializerTracker {

	@Override
	public DDMFormSerializer getDDMFormSerializer(String type) {
		return _ddmFormSerializers.get(type);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDDMFormSerializer(
		DDMFormSerializer ddmFormSerializer, Map<String, Object> properties) {

		String type = MapUtil.getString(properties, "ddm.form.serializer.type");

		_ddmFormSerializers.put(type, ddmFormSerializer);
	}

	@Deactivate
	protected void deactivate() {
		_ddmFormSerializers.clear();
	}

	protected void removeDDMFormSerializer(
		DDMFormSerializer ddmFormSerializer, Map<String, Object> properties) {

		String type = MapUtil.getString(properties, "ddm.form.serializer.type");

		_ddmFormSerializers.remove(type);
	}

	private final Map<String, DDMFormSerializer> _ddmFormSerializers =
		new TreeMap<>();

}