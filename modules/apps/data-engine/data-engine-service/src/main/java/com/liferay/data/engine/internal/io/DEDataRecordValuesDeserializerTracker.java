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

package com.liferay.data.engine.internal.io;

import com.liferay.data.engine.io.DEDataRecordValuesDeserializer;
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
@Component(
	immediate = true, service = DEDataRecordValuesDeserializerTracker.class
)
public class DEDataRecordValuesDeserializerTracker {

	public DEDataRecordValuesDeserializer getDEDataRecordDeserializer(
		String type) {

		return _deDataRecordDeserializers.get(type);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDEDataRecordDeserializer(
		DEDataRecordValuesDeserializer dataRecordDeserializer,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "de.data.record.values.deserializer.type");

		_deDataRecordDeserializers.put(type, dataRecordDeserializer);
	}

	@Deactivate
	protected void deactivate() {
		_deDataRecordDeserializers.clear();
	}

	protected void removeDEDataRecordDeserializer(
		DEDataRecordValuesDeserializer dataRecordDeserializer,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "de.data.record.values.deserializer.type");

		_deDataRecordDeserializers.remove(type);
	}

	private final Map<String, DEDataRecordValuesDeserializer>
		_deDataRecordDeserializers = new TreeMap<>();

}