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

package com.liferay.object.rest.internal.openapi.v1_0;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.FileEntry;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryResourceImpl;
import com.liferay.object.rest.internal.resource.v1_0.OpenAPIResourceImpl;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResource;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.batch.engine.Field;
import com.liferay.portal.vulcan.openapi.DTOProperty;
import com.liferay.portal.vulcan.openapi.OpenAPISchemaFilter;
import com.liferay.portal.vulcan.resource.OpenAPIResource;
import com.liferay.portal.vulcan.util.TransformUtil;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = ObjectEntryOpenAPIResource.class)
public class ObjectEntryOpenAPIResourceImpl
	implements ObjectEntryOpenAPIResource {

	@Override
	public Map<String, Field> getFields(
			long objectDefinitionId, UriInfo uriInfo)
		throws Exception {

		Response response = getOpenAPI(objectDefinitionId, "json", uriInfo);

		OpenAPI openAPI = (OpenAPI)response.getEntity();

		Components components = openAPI.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		Schema schema = schemas.get(_objectDefinition.getShortName());

		if (schema == null) {
			return Collections.emptyMap();
		}

		Map<String, Field> fields = new HashMap<>();

		List<String> requiredPropertySchemaNames =
			_getRequiredPropertySchemaNames(schema);

		Map<String, Schema> properties = schema.getProperties();

		for (Map.Entry<String, Schema> schemaEntry : properties.entrySet()) {
			String propertyName = schemaEntry.getKey();
			Schema propertySchema = schemaEntry.getValue();

			if (Optional.ofNullable(
					propertySchema.getReadOnly()
				).orElse(
					false
				) ||
				Optional.ofNullable(
					propertySchema.getWriteOnly()
				).orElse(
					false
				) || propertyName.startsWith("x-")) {

				continue;
			}

			fields.put(
				propertyName,
				Field.of(
					propertySchema.getDescription(), propertyName,
					Optional.ofNullable(
						propertySchema.getReadOnly()
					).orElse(
						false
					),
					requiredPropertySchemaNames.contains(propertyName),
					propertySchema.getType(),
					Optional.ofNullable(
						propertySchema.getWriteOnly()
					).orElse(
						false
					)));
		}

		return fields;
	}

	@Override
	public Response getOpenAPI(
			long objectDefinitionId, String type, UriInfo uriInfo)
		throws Exception {

		_objectDefinition = _objectDefinitionLocalService.getObjectDefinition(
			objectDefinitionId);

		Map<ObjectRelationship, ObjectDefinition> relatedObjectDefinitionsMap =
			_getRelatedObjectDefinitionsMap();

		Response response = _openAPIResource.getOpenAPI(
			_getOpenAPISchemaFilter(_objectDefinition.getRESTContextPath()),
			new HashSet<Class<?>>() {
				{
					add(ObjectEntryResourceImpl.class);
					add(OpenAPIResourceImpl.class);
				}
			},
			type, uriInfo);

		OpenAPI openAPI = (OpenAPI)response.getEntity();

		Paths paths = openAPI.getPaths();

		for (String key : new ArrayList<>(paths.keySet())) {
			if (!key.contains("objectRelationshipName")) {
				continue;
			}

			for (Map.Entry<ObjectRelationship, ObjectDefinition> entry :
					relatedObjectDefinitionsMap.entrySet()) {

				ObjectRelationship objectRelationship = entry.getKey();
				ObjectDefinition relatedObjectDefinition = entry.getValue();

				paths.addPathItem(
					StringUtil.replace(
						key,
						new String[] {
							"currentObjectEntry", "{objectRelationshipName}",
							"relatedObjectEntry"
						},
						new String[] {
							StringUtil.lowerCaseFirstLetter(
								_objectDefinition.getShortName()),
							objectRelationship.getName(),
							StringUtil.lowerCaseFirstLetter(
								relatedObjectDefinition.getShortName())
						}),
					_createPathItem(
						objectRelationship, paths.get(key),
						relatedObjectDefinition));

				openAPI.getComponents(
				).getSchemas(
				).get(
					_objectDefinition.getShortName()
				).getProperties(
				).put(
					objectRelationship.getName(),
					new Schema<Object>() {
						{
							setDescription(
								StringBundler.concat(
									"Information about the relationship ",
									objectRelationship.getName(),
									" can be embedded with \"nestedFields\"."));
						}
					}
				);
			}

			paths.remove(key);
		}

		return response;
	}

	private Operation _createOperation(
		String httpMethod, ObjectRelationship objectRelationship,
		ObjectDefinition relatedObjectDefinition, Operation operation) {

		Map<String, Parameter> parameters = new HashMap<>();

		for (Parameter parameter : operation.getParameters()) {
			String parameterName = parameter.getName();

			if (Objects.equals(parameterName, "objectRelationshipName")) {
				continue;
			}

			if (Objects.equals(parameterName, "currentObjectEntryId")) {
				parameterName = StringUtil.replace(
					parameterName, "currentObjectEntry",
					StringUtil.lowerCaseFirstLetter(
						_objectDefinition.getShortName()));
			}
			else if (Objects.equals(parameterName, "relatedObjectEntryId")) {
				parameterName = StringUtil.replace(
					parameterName, "relatedObjectEntry",
					StringUtil.lowerCaseFirstLetter(
						relatedObjectDefinition.getShortName()));
			}

			String finalParameterName = parameterName;

			parameters.put(
				parameter.getName(),
				new Parameter() {
					{
						name(finalParameterName);
						in(parameter.getIn());
						required(parameter.getRequired());
						schema(parameter.getSchema());
					}
				});
		}

		return new Operation() {
			{
				operationId(
					StringBundler.concat(
						httpMethod, _objectDefinition.getShortName(),
						StringUtil.upperCaseFirstLetter(
							objectRelationship.getName()),
						relatedObjectDefinition.getShortName()));
				parameters(new ArrayList<>(parameters.values()));
				responses(operation.getResponses());
				tags(operation.getTags());
			}
		};
	}

	private PathItem _createPathItem(
		ObjectRelationship objectRelationship, PathItem pathItem,
		ObjectDefinition relatedObjectDefinition) {

		Map<PathItem.HttpMethod, Operation> operations =
			pathItem.readOperationsMap();

		Operation operation = operations.get(PathItem.HttpMethod.GET);

		if (operation != null) {
			return new PathItem() {
				{
					get(
						_createOperation(
							"get", objectRelationship, relatedObjectDefinition,
							pathItem.getGet()));
				}
			};
		}

		operation = operations.get(PathItem.HttpMethod.PUT);

		if (operation != null) {
			return new PathItem() {
				{
					put(
						_createOperation(
							"put", objectRelationship, relatedObjectDefinition,
							pathItem.getPut()));
				}
			};
		}

		return new PathItem();
	}

	private DTOProperty _getDTOProperty(ObjectField objectField) {
		if (Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			DTOProperty dtoProperty = new DTOProperty(
				Collections.singletonMap("x-parent-map", "properties"),
				objectField.getName(), FileEntry.class.getSimpleName());

			dtoProperty.setDTOProperties(
				Arrays.asList(
					new DTOProperty(
						Collections.singletonMap("x-parent-map", "properties"),
						"id", Long.class.getSimpleName()),
					new DTOProperty(
						Collections.singletonMap("x-parent-map", "properties"),
						"name", String.class.getSimpleName())));
			dtoProperty.setRequired(objectField.isRequired());

			return dtoProperty;
		}

		if (objectField.getListTypeDefinitionId() != 0) {
			DTOProperty dtoProperty = new DTOProperty(
				Collections.singletonMap("x-parent-map", "properties"),
				objectField.getName(), ListEntry.class.getSimpleName());

			dtoProperty.setDTOProperties(
				Arrays.asList(
					new DTOProperty(
						Collections.singletonMap("x-parent-map", "properties"),
						"key", String.class.getSimpleName()),
					new DTOProperty(
						Collections.singletonMap("x-parent-map", "properties"),
						"name", String.class.getSimpleName())));
			dtoProperty.setRequired(objectField.isRequired());

			return dtoProperty;
		}

		return new DTOProperty(
			Collections.singletonMap("x-parent-map", "properties"),
			objectField.getName(), objectField.getDBType()) {

			{
				setRequired(objectField.isRequired());
			}
		};
	}

	private OpenAPISchemaFilter _getOpenAPISchemaFilter(
		String applicationPath) {

		OpenAPISchemaFilter openAPISchemaFilter = new OpenAPISchemaFilter();

		openAPISchemaFilter.setApplicationPath(applicationPath);

		DTOProperty dtoProperty = new DTOProperty(
			new HashMap<>(), "ObjectEntry", "object");

		dtoProperty.setDTOProperties(
			TransformUtil.transform(
				_objectFieldLocalService.getObjectFields(
					_objectDefinition.getObjectDefinitionId()),
				this::_getDTOProperty));

		openAPISchemaFilter.setDTOProperties(Arrays.asList(dtoProperty));

		openAPISchemaFilter.setSchemaMappings(
			HashMapBuilder.put(
				"ObjectEntry", _objectDefinition.getShortName()
			).put(
				"PageObjectEntry", "Page" + _objectDefinition.getShortName()
			).build());

		return openAPISchemaFilter;
	}

	private Map<ObjectRelationship, ObjectDefinition>
		_getRelatedObjectDefinitionsMap() {

		Map<ObjectRelationship, ObjectDefinition> relatedObjectDefinitionsMap =
			new HashMap<>();

		List<ObjectRelationship> objectRelationships =
			_objectRelationshipLocalService.getObjectRelationships(
				_objectDefinition.getObjectDefinitionId());

		for (ObjectRelationship objectRelationship : objectRelationships) {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					objectRelationship.getObjectDefinitionId2());

			relatedObjectDefinitionsMap.put(
				objectRelationship, objectDefinition);
		}

		return relatedObjectDefinitionsMap;
	}

	private List<String> _getRequiredPropertySchemaNames(Schema schema) {
		List<String> requiredPropertySchemaNames = schema.getRequired();

		if (requiredPropertySchemaNames == null) {
			requiredPropertySchemaNames = Collections.emptyList();
		}

		return requiredPropertySchemaNames;
	}

	private ObjectDefinition _objectDefinition;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private OpenAPIResource _openAPIResource;

}