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

import com.liferay.data.engine.io.DataDefinitionFieldsSerializer;
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
	immediate = true, service = DataDefinitionFieldsSerializerTracker.class
)
public class DataDefinitionFieldsSerializerTracker {

	public DataDefinitionFieldsSerializer getDataDefinitionFieldsSerializer(
		String type) {

		return _dataDefinitionFieldsSerializers.get(type);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDataDefinitionFieldsSerializer(
		DataDefinitionFieldsSerializer dataDefinitionFieldsSerializer,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "data.definition.serializer.type");

		_dataDefinitionFieldsSerializers.put(
			type, dataDefinitionFieldsSerializer);
	}

	@Deactivate
	protected void deactivate() {
		_dataDefinitionFieldsSerializers.clear();
	}

	protected void removeDataDefinitionFieldsSerializer(
		DataDefinitionFieldsSerializer dataDefinitionFieldsSerializer,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "data.definition.serializer.type");

		_dataDefinitionFieldsSerializers.remove(type);
	}

	private final Map<String, DataDefinitionFieldsSerializer>
		_dataDefinitionFieldsSerializers = new TreeMap<>();

}