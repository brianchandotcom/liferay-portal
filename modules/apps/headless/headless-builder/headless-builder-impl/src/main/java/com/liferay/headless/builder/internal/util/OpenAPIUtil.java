/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.util;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.object.rest.dto.v1_0.FileEntry;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.DateSchema;
import io.swagger.v3.oas.models.media.DateTimeSchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @author Sergio Jiménez del Coso
 */
public class OpenAPIUtil {

	public static String getOperationId(
		Http.Method method, String path,
		APIApplication.Endpoint.RetrieveType retrieveType, String schemaName) {

		List<String> methodNameParts = new ArrayList<>();

		methodNameParts.add(StringUtil.toLowerCase(method.name()));

		String pluralSchemaName = TextFormatter.formatPlural(schemaName);

		String[] pathParts = path.split("/");

		for (int i = 0; i < pathParts.length; i++) {
			String pathPart = pathParts[i];

			String pathName = _toCamelCase(
				pathPart.replaceAll("\\{|-id|}|Id}", ""));

			if (StringUtil.equalsIgnoreCase(pathName, pluralSchemaName)) {
				pathName = pluralSchemaName;
			}
			else {
				pathName = StringUtil.upperCaseFirstLetter(pathName);
			}

			if ((i == (pathParts.length - 1)) &&
				Objects.equals(
					retrieveType,
					APIApplication.Endpoint.RetrieveType.COLLECTION)) {

				String previousMethodNamePart = methodNameParts.get(
					methodNameParts.size() - 1);

				if ((schemaName != null) &&
					!pathName.endsWith(pluralSchemaName) &&
					previousMethodNamePart.endsWith(schemaName)) {

					String methodNamePart = StringUtil.replaceLast(
						previousMethodNamePart, schemaName, pluralSchemaName);

					methodNameParts.set(
						methodNameParts.size() - 1, methodNamePart);
				}

				methodNameParts.add(pathName + "Page");
			}
			else if (pathPart.contains("{") && (schemaName != null)) {
				String previousMethodNameSegment = methodNameParts.get(
					methodNameParts.size() - 1);

				if (!previousMethodNameSegment.endsWith(pathName) &&
					!previousMethodNameSegment.endsWith(schemaName)) {

					methodNameParts.add(pathName);
				}
			}
			else {
				String methodNamePart = _formatSingular(pathName);

				String methodNamePartLowerCase = StringUtil.toLowerCase(
					methodNamePart);

				if ((schemaName != null) &&
					methodNamePartLowerCase.endsWith(
						StringUtil.toLowerCase(schemaName))) {

					char c = methodNamePart.charAt(
						methodNamePart.length() - schemaName.length());

					if (Character.isUpperCase(c)) {
						String substring = methodNamePart.substring(
							0, methodNamePart.length() - schemaName.length());

						methodNamePart = substring + schemaName;
					}
				}

				methodNameParts.add(methodNamePart);
			}
		}

		return StringUtil.merge(methodNameParts, "");
	}

	public static String getPathParameter(String path) {
		String pathParameter = path.substring(path.lastIndexOf("/") + 1);

		pathParameter = pathParameter.replaceAll("\\{", StringPool.BLANK);

		return pathParameter.replaceAll("\\}", StringPool.BLANK);
	}

	public static Map<String, Schema> toOpenAPISchemas(
		OpenAPIResource openAPIResource, APIApplication.Schema schema) {

		Map<String, Schema> schemas = new TreeMap<>();

		Map<String, Schema> properties = new TreeMap<>();

		for (APIApplication.Property property : schema.getProperties()) {
			properties.put(
				property.getName(),
				_getPropertySchema(openAPIResource, property, schemas));
		}

		schemas.put(
			schema.getName(),
			new ObjectSchema() {
				{
					setDescription(schema.getDescription());
					setName(schema.getName());
					setProperties(properties);
				}
			});

		Map<String, Schema> pageSchemas = openAPIResource.getSchemas(
			Page.class);

		Schema pageSchema = pageSchemas.remove("Page");

		Map<String, Schema> pageProperties = pageSchema.getProperties();

		ArraySchema itemsArraySchema = (ArraySchema)pageProperties.get("items");

		itemsArraySchema.setItems(
			new Schema() {
				{
					set$ref(schema.getName());
				}
			});

		schemas.put("Page" + schema.getName(), pageSchema);

		schemas.putAll(pageSchemas);

		return schemas;
	}

	private static void _addSchemas(
		Class<?> entityClass, OpenAPIResource openAPIResource,
		Map<String, Schema> schemas) {

		if (!schemas.containsKey(entityClass.getSimpleName())) {
			schemas.putAll(openAPIResource.getSchemas(entityClass));
		}
	}

	private static String _formatSingular(String s) {
		if (s.endsWith("ases")) {

			// bases to base

			s = s.substring(0, s.length() - 1);
		}
		else if (s.endsWith("auses")) {

			// clauses to clause

			s = s.substring(0, s.length() - 1);
		}
		else if (s.endsWith("ses") || s.endsWith("xes")) {
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

	private static Schema _getPropertySchema(
		OpenAPIResource openAPIResource, APIApplication.Property property,
		Map<String, Schema> schemas) {

		APIApplication.Property.Type type = property.getType();

		Schema schema = null;

		if (type == APIApplication.Property.Type.AGGREGATION) {
			schema = new StringSchema();
		}
		else if (type == APIApplication.Property.Type.ARRAY_CONTAINER) {
			schema = new ArraySchema();
		}
		else if (type == APIApplication.Property.Type.ATTACHMENT) {
			_addSchemas(FileEntry.class, openAPIResource, schemas);

			schema = new Schema() {
				{
					set$ref("FileEntry");
				}
			};
		}
		else if (type == APIApplication.Property.Type.BOOLEAN) {
			schema = new BooleanSchema();
		}
		else if (type == APIApplication.Property.Type.DATE) {
			schema = new DateSchema();
		}
		else if (type == APIApplication.Property.Type.DATE_TIME) {
			schema = new DateTimeSchema();
		}
		else if (type == APIApplication.Property.Type.DECIMAL) {
			schema = new NumberSchema() {
				{
					setFormat("double");
				}
			};
		}
		else if (type == APIApplication.Property.Type.INTEGER) {
			schema = new IntegerSchema();
		}
		else if (type == APIApplication.Property.Type.LONG_INTEGER) {
			schema = new IntegerSchema() {
				{
					setFormat("int64");
				}
			};
		}
		else if (type == APIApplication.Property.Type.LONG_TEXT) {
			schema = new StringSchema();
		}
		else if (type == APIApplication.Property.Type.MULTISELECT_PICKLIST) {
			_addSchemas(ListEntry.class, openAPIResource, schemas);

			schema = new ArraySchema() {
				{
					setItems(
						new Schema() {
							{
								set$ref("ListEntry");
							}
						});
				}
			};
		}
		else if (type == APIApplication.Property.Type.SINGLE_CONTAINER) {
			schema = new ObjectSchema();
		}
		else if (type == APIApplication.Property.Type.PICKLIST) {
			_addSchemas(ListEntry.class, openAPIResource, schemas);

			schema = new Schema() {
				{
					set$ref("ListEntry");
				}
			};
		}
		else if (type == APIApplication.Property.Type.PRECISION_DECIMAL) {
			schema = new NumberSchema() {
				{
					setFormat("double");
				}
			};
		}
		else if (type == APIApplication.Property.Type.RICH_TEXT) {
			schema = new StringSchema();
		}
		else if (type == APIApplication.Property.Type.TEXT) {
			schema = new StringSchema();
		}

		schema.setDescription(property.getDescription());
		schema.setName(property.getName());

		for (APIApplication.Property childProperty : property.getProperties()) {
			schema.setProperties(
				HashMapBuilder.put(
					childProperty.getName(),
					_getPropertySchema(openAPIResource, childProperty, schemas)
				).putAll(
					schema.getProperties()
				).build());
		}

		return schema;
	}

	private static String _toCamelCase(String path) {
		path = path.replaceAll("[{}]", StringPool.BLANK);

		return CamelCaseUtil.toCamelCase(
			path.replaceAll(StringPool.MINUS, StringPool.SLASH),
			CharPool.SLASH);
	}

}