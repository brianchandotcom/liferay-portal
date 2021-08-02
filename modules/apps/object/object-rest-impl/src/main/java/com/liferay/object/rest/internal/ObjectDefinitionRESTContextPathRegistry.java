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

package com.liferay.object.rest.internal;

import com.liferay.object.model.ObjectDefinition;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier de Arcos
 */
@Component(service = ObjectDefinitionRESTContextPathRegistry.class)
public class ObjectDefinitionRESTContextPathRegistry {

	public ObjectDefinition getObjectDefinition(
		long companyId, String restContextPath) {

		Map<Long, ObjectDefinition> objectDefinitionCompanyMap =
			_objectDefinitionMap.get(restContextPath);

		if (objectDefinitionCompanyMap != null) {
			return objectDefinitionCompanyMap.get(companyId);
		}

		return null;
	}

	public boolean hasRESTContextPath(String restContextPath) {
		return _objectDefinitionMap.containsKey(restContextPath);
	}

	public void register(ObjectDefinition objectDefinition) {
		_objectDefinitionMap.putIfAbsent(
			objectDefinition.getRESTContextPath(), new HashMap<>());

		Map<Long, ObjectDefinition> objectDefinitionCompanyMap =
			_objectDefinitionMap.get(objectDefinition.getRESTContextPath());

		objectDefinitionCompanyMap.put(
			objectDefinition.getCompanyId(), objectDefinition);
	}

	public void unregister(ObjectDefinition objectDefinition) {
		Map<Long, ObjectDefinition> objectDefinitionCompanyMap =
			_objectDefinitionMap.get(objectDefinition.getRESTContextPath());

		objectDefinitionCompanyMap.remove(objectDefinition.getCompanyId());

		if (objectDefinitionCompanyMap.isEmpty()) {
			_objectDefinitionMap.remove(objectDefinition.getRESTContextPath());
		}
	}

	private final Map<String, Map<Long, ObjectDefinition>>
		_objectDefinitionMap = new HashMap<>();

}