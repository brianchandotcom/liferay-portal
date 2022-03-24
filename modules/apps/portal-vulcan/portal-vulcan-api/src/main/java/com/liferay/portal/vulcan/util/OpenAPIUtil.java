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

package com.liferay.portal.vulcan.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.batch.engine.Field;
import com.liferay.portal.vulcan.yaml.openapi.Components;
import com.liferay.portal.vulcan.yaml.openapi.Get;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Operation;
import com.liferay.portal.vulcan.yaml.openapi.Parameter;
import com.liferay.portal.vulcan.yaml.openapi.PathItem;
import com.liferay.portal.vulcan.yaml.openapi.Post;
import com.liferay.portal.vulcan.yaml.openapi.Response;
import com.liferay.portal.vulcan.yaml.openapi.ResponseCode;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Javier de Arcos
 */
public class OpenAPIUtil {

	public static List<String> getCreateEntityScopes(
		String entityName, OpenAPIYAML openAPIYAML) {

		Map<String, PathItem> pathItemsMap = openAPIYAML.getPathItems();

		List<String> scopes = new ArrayList<>();

		for (PathItem pathItem : pathItemsMap.values()) {
			Post post = pathItem.getPost();

			if ((post == null) ||
				!_hasOKResponseContentSchemaReferenceLike(entityName, post)) {

				continue;
			}

			scopes.add(_getOperationScope(post));
		}

		return scopes;
	}

	public static Map<String, Field> getDTOEntityFields(
		String entityName, OpenAPIYAML openAPIYAML) {

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		Schema schema = schemas.get(entityName);

		if (schema == null) {
			return Collections.emptyMap();
		}

		List<String> requiredPropertySchemaNames =
			schema.getRequiredPropertySchemaNames();

		Map<String, Schema> propertySchemas = schema.getPropertySchemas();

		Map<String, Field> fields = new HashMap<>();

		for (Map.Entry<String, Schema> schemaEntry :
				propertySchemas.entrySet()) {

			String propertyName = schemaEntry.getKey();
			Schema propertySchema = schemaEntry.getValue();

			fields.put(
				propertyName,
				Field.of(
					propertySchema.getDescription(), propertyName,
					propertySchema.isReadOnly(),
					requiredPropertySchemaNames.contains(propertyName),
					propertySchema.getType(), propertySchema.isWriteOnly()));
		}

		return fields;
	}

	public static List<String> getReadEntityScopes(
		String entityName, OpenAPIYAML openAPIYAML) {

		Map<String, PathItem> pathItemsMap = openAPIYAML.getPathItems();

		List<String> scopes = new ArrayList<>();

		for (PathItem pathItem : pathItemsMap.values()) {
			Get get = pathItem.getGet();

			if ((get == null) ||
				!_hasOKResponseContentSchemaReferenceLike(
					"Page" + entityName, get)) {

				continue;
			}

			scopes.add(_getOperationScope(get));
		}

		return scopes;
	}

	private static String _getOperationScope(Operation operation) {
		List<Parameter> parameters = operation.getParameters();

		Stream<Parameter> parametersStream = parameters.stream();

		return parametersStream.filter(
			parameter -> StringUtil.equals(parameter.getIn(), "path")
		).map(
			parameter -> {
				String name = parameter.getName();

				if (name.endsWith("Id")) {
					name = StringUtil.removeLast(name, "Id");
				}

				return name;
			}
		).collect(
			Collectors.joining(",")
		);
	}

	private static boolean _hasOKResponseContentSchemaReferenceLike(
		String name, Operation operation) {

		Map<ResponseCode, Response> responses = operation.getResponses();

		if (responses == null) {
			return false;
		}

		Set<Map.Entry<ResponseCode, Response>> entries = responses.entrySet();

		Stream<Map.Entry<ResponseCode, Response>> stream = entries.stream();

		return stream.filter(
			entry -> _isOKResponseCode(entry.getKey())
		).map(
			Map.Entry::getValue
		).map(
			Response::getContent
		).map(
			Map::entrySet
		).flatMap(
			Set::stream
		).map(
			Map.Entry::getValue
		).anyMatch(
			content -> Optional.ofNullable(
				content.getSchema()
			).map(
				Schema::getReference
			).map(
				reference -> StringUtil.equals(
					name, reference.substring(reference.lastIndexOf('/') + 1))
			).orElse(
				false
			)
		);
	}

	private static boolean _isOKResponseCode(ResponseCode responseCode) {
		if (responseCode.isDefaultResponse() ||
			((responseCode.getHttpCode() / 100) == 2)) {

			return true;
		}

		return false;
	}

}