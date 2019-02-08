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

import com.liferay.data.engine.io.DEDataLayoutDeserializer;
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
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = DEDataLayoutDeserializerTracker.class)
public class DEDataLayoutDeserializerTracker {

	public DEDataLayoutDeserializer getDEDataLayoutSerializer(String type) {
		return _deDataLayoutDeserializers.get(type);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDEDataLayoutDeserializer(
		DEDataLayoutDeserializer deDataLayoutDeserializer,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "data.layout.deserializer.type");

		_deDataLayoutDeserializers.put(type, deDataLayoutDeserializer);
	}

	@Deactivate
	protected void deactivate() {
		_deDataLayoutDeserializers.clear();
	}

	protected void removeDEDataLayoutDeserializer(
		DEDataLayoutDeserializer deDataLayoutDeserializer,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "data.layout.deserializer.type");

		_deDataLayoutDeserializers.remove(type);
	}

	private final Map<String, DEDataLayoutDeserializer>
		_deDataLayoutDeserializers = new TreeMap<>();

}