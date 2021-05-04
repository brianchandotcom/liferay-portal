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

package com.liferay.portal.vulcan.internal.graphql.servlet;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.SetUtil;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Javier de Arcos
 */
public class ObjectDefinitionGraphQLDTOContributor implements GraphQLDTOContributor {

	public static ObjectDefinitionGraphQLDTOContributor of(
		ObjectDefinition objectDefinition, List<ObjectField> objectFields) {

		List<GraphQLDTOProperty> properties = new ArrayList<>();
		properties.add(GraphQLDTOProperty.of(
			objectDefinition.getPrimaryKeyColumnName(), Long.class));

		for (ObjectField objectField: objectFields) {
			properties.add(GraphQLDTOProperty.of(
				objectField.getName(), _mappedTypeToClass.getOrDefault(objectField.getType(), Object.class));
		}

		return new ObjectDefinitionGraphQLDTOContributor(
			objectDefinition.getName(), properties);
	}

	private ObjectDefinitionGraphQLDTOContributor(
		String name, List<GraphQLDTOProperty> properties) {

		_name = name;
		_properties = properties;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public List<GraphQLDTOProperty> getProperties() {
		return _properties;
	}

	private final String _name;
	private final List<GraphQLDTOProperty> _properties;

	private static Map<String, Class<?>> _mappedTypeToClass =
		HashMapBuilder
			.<String, Class<?>>put("BigDecimal", BigDecimal.class)
			.put("Blob", Blob.class)
			.put("Boolean", Boolean.class)
			.put("Date", Date.class)
			.put("Double", Double.class)
			.put("Integer", Integer.class)
			.put("Long", Long.class)
			.put("String", String.class)
			.build();
}
