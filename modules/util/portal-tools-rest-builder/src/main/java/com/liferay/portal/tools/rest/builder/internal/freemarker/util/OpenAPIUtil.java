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

package com.liferay.portal.tools.rest.builder.internal.freemarker.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.rest.builder.internal.freemarker.tool.java.parser.util.OpenAPIParserUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Components;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Info;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Items;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Schema;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class OpenAPIUtil {

	public static String escapeVersion(OpenAPIYAML openAPIYAML) {
		Info info = openAPIYAML.getInfo();

		String version = info.getVersion();

		if (Validator.isNull(version)) {
			return null;
		}

		Matcher matcher = _nondigitPattern.matcher(version);

		String escapedVersion = matcher.replaceAll("_");

		matcher = _leadingUnderscorePattern.matcher(escapedVersion);

		return "v" + matcher.replaceFirst("");
	}

	public static String formatSingular(String s) {
		if (s.endsWith("ases")) {
			s = s.substring(0, s.length() - 1);
		}
		else if (s.endsWith("ses")) {
			s = s.substring(0, s.length() - 2);
		}
		else if (s.endsWith("ies")) {
			s = s.substring(0, s.length() - 3) + "y";
		}
		else if (s.endsWith("s")) {
			s = s.substring(0, s.length() - 1);
		}

		return s;
	}

	public static Map<String, Schema> getAllExternalSchemas(
			OpenAPIYAML openAPIYAML)
		throws Exception {

		Map<String, Schema> allExternalSchemas = new HashMap<>();

		Map<String, Schema> externalSchemas =
			OpenAPIParserUtil.getExternalSchemas(openAPIYAML);

		List<String> externalReferences =
			OpenAPIParserUtil.getExternalReferences(openAPIYAML);

		for (String externalReference : externalReferences) {
			String referenceName = OpenAPIParserUtil.getReferenceName(
				externalReference);

			allExternalSchemas.put(
				referenceName, externalSchemas.get(referenceName));
		}

		Queue<Map<String, Schema>> queue = new LinkedList<>();

		queue.add(allExternalSchemas);

		Map<String, Schema> map = null;

		while ((map = queue.poll()) != null) {
			for (Map.Entry<String, Schema> entry : map.entrySet()) {
				Schema schema = entry.getValue();

				Map<String, Schema> propertySchemas = null;

				Items items = schema.getItems();

				if (items != null) {
					if (items.getReference() != null) {
						_addExternalReference(
							allExternalSchemas, externalSchemas, queue,
							items.getReference());
					}
					else {
						propertySchemas = items.getPropertySchemas();
					}
				}
				else if (schema.getAllOfSchemas() != null) {
					List<Schema> allOfSchemas = schema.getAllOfSchemas();

					queue.add(
						Collections.singletonMap(
							entry.getKey(), allOfSchemas.get(0)));
				}
				else if (schema.getReference() != null) {
					_addExternalReference(
						allExternalSchemas, externalSchemas, queue,
						schema.getReference());
				}
				else {
					propertySchemas = schema.getPropertySchemas();
				}

				if (propertySchemas == null) {
					continue;
				}

				String schemaName = StringUtil.upperCaseFirstLetter(
					entry.getKey());

				if (items != null) {
					schemaName = formatSingular(schemaName);
				}

				allExternalSchemas.put(schemaName, schema);

				queue.add(propertySchemas);
			}
		}

		return allExternalSchemas;
	}

	public static Map<String, Schema> getAllSchemas(OpenAPIYAML openAPIYAML) {
		Map<String, Schema> allSchemas = new TreeMap<>();

		Components components = openAPIYAML.getComponents();

		if (components == null) {
			return allSchemas;
		}

		Queue<Map<String, Schema>> queue = new LinkedList<>();

		queue.add(components.getSchemas());

		Map<String, Schema> map = null;

		while ((map = queue.poll()) != null) {
			for (Map.Entry<String, Schema> entry : map.entrySet()) {
				Schema schema = entry.getValue();

				Map<String, Schema> propertySchemas = null;

				Items items = schema.getItems();

				if (items != null) {
					propertySchemas = items.getPropertySchemas();
				}
				else if (schema.getAllOfSchemas() != null) {
					propertySchemas = OpenAPIParserUtil.getAllOfPropertySchemas(
						schema);
				}
				else {
					propertySchemas = schema.getPropertySchemas();
				}

				if (propertySchemas == null) {
					continue;
				}

				String schemaName = StringUtil.upperCaseFirstLetter(
					entry.getKey());

				if (items != null) {
					schemaName = formatSingular(schemaName);
				}

				allSchemas.put(schemaName, schema);

				if (schema.getOneOfSchemas() != null) {
					for (Schema oneOfSchema : schema.getOneOfSchemas()) {
						Map<String, Schema> schemas =
							oneOfSchema.getPropertySchemas();

						Set<String> keys = schemas.keySet();

						Iterator<String> iterator = keys.iterator();

						String schemaKey = StringUtil.upperCaseFirstLetter(
							iterator.next());

						if (!allSchemas.containsKey(schemaKey)) {
							allSchemas.put(schemaKey, oneOfSchema);

							queue.add(schemas);
						}
					}
				}

				queue.add(propertySchemas);
			}
		}

		return allSchemas;
	}

	public static Map<String, Schema> getGlobalEnumSchemas(
		OpenAPIYAML openAPIYAML) {

		Map<String, Schema> globalEnumSchemas = new TreeMap<>();

		Components components = openAPIYAML.getComponents();

		if (components == null) {
			return globalEnumSchemas;
		}

		Map<String, Schema> schemas = components.getSchemas();

		for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
			Schema schema = entry.getValue();

			Map<String, Schema> propertySchemas = null;

			Items items = schema.getItems();

			if (items != null) {
				propertySchemas = items.getPropertySchemas();
			}
			else if (schema.getAllOfSchemas() != null) {
				propertySchemas = OpenAPIParserUtil.getAllOfPropertySchemas(
					schema);
			}
			else {
				propertySchemas = schema.getPropertySchemas();
			}

			if ((propertySchemas == null) && (schema.getEnumValues() != null)) {
				String schemaName = StringUtil.upperCaseFirstLetter(
					entry.getKey());

				if (items != null) {
					schemaName = formatSingular(schemaName);
				}

				globalEnumSchemas.put(schemaName, schema);
			}
		}

		return globalEnumSchemas;
	}

	private static void _addExternalReference(
		Map<String, Schema> allExternalSchemas,
		Map<String, Schema> externalSchemas, Queue<Map<String, Schema>> queue,
		String reference) {

		String referenceName = OpenAPIParserUtil.getReferenceName(reference);

		if (allExternalSchemas.get(referenceName) == null) {
			Schema externalSchema = externalSchemas.get(referenceName);

			Map<String, Schema> externalSchemaMap = Collections.singletonMap(
				referenceName, externalSchema);

			allExternalSchemas.putAll(externalSchemaMap);
			queue.add(externalSchemaMap);
		}
	}

	private static final Pattern _leadingUnderscorePattern = Pattern.compile(
		"^_+");
	private static final Pattern _nondigitPattern = Pattern.compile("\\D");

}