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

package com.liferay.object.rest.internal.resource.v1_0;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.openapi.OpenAPISchemaFilter;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Javier Gamarra
 */
@OpenAPIDefinition(info = @Info(title = "Object", version = "v1.0"))
public class OpenAPIResourceImpl {

	public OpenAPIResourceImpl(
		ObjectDefinition currentObjectDefinition,
		Map<ObjectRelationship, ObjectDefinition>
			objectRelationshipRelatedObjectDefinitionMap,
		OpenAPIResource openAPIResource,
		OpenAPISchemaFilter openAPISchemaFilter,
		Set<Class<?>> resourceClasses) {

		_currentObjectDefinition = currentObjectDefinition;
		_objectRelationshipRelatedObjectDefinitionMap =
			objectRelationshipRelatedObjectDefinitionMap;
		_openAPIResource = openAPIResource;
		_openAPISchemaFilter = openAPISchemaFilter;
		_resourceClasses = resourceClasses;
	}

	@GET
	@Path("/openapi.{type:json|yaml}")
	@Produces({MediaType.APPLICATION_JSON, "application/yaml"})
	public Response getOpenAPI(@PathParam("type") String type)
		throws Exception {

		Response openAPI = _openAPIResource.getOpenAPI(
			_openAPISchemaFilter, _resourceClasses, type, _uriInfo);

		OpenAPI entity = (OpenAPI)openAPI.getEntity();

		List<String> relationshipsEndpoints = _getRelationshipsEndpoints(
			entity.getPaths());

		relationshipsEndpoints.forEach(
			endpoint -> {
				_objectRelationshipRelatedObjectDefinitionMap.forEach(
					(objectRelationship, objectDefinition) -> {
						_createCustomRelationshipEndpointToOpenAPI(
							entity, endpoint, objectRelationship,
							objectDefinition);

						Schema<Object> relationshipSchema = new Schema<>();

						relationshipSchema.setDescription(
							"Information about the relationship " +
								objectRelationship.getName() +
									". Can be embedded with nestedFields");

						entity.getComponents(
						).getSchemas(
						).get(
							_currentObjectDefinition.getShortName()
						).getProperties(
						).put(
							objectRelationship.getName(), relationshipSchema
						);
					});

				Paths entityPaths = entity.getPaths();

				entityPaths.remove(endpoint);
			});

		return openAPI;
	}

	private void _createCustomRelationshipEndpointToOpenAPI(
		OpenAPI entity, String endpoint, ObjectRelationship objectRelationship,
		ObjectDefinition relatedObjectDefinition) {

		Paths entityPaths = entity.getPaths();

		PathItem customRelationshipPathItem = _createCustomRelationshipPathItem(
			entityPaths.get(endpoint), objectRelationship,
			relatedObjectDefinition);

		String currentObjectName = StringUtil.lowerCaseFirstLetter(
			_currentObjectDefinition.getShortName());

		String relatedObjectName = StringUtil.lowerCaseFirstLetter(
			relatedObjectDefinition.getShortName());

		String customRelationshipEndpoint = StringUtil.replace(
			endpoint,
			new String[] {
				_CURRENT_OBJECT_ID, _RELATIONSHIP_NAME_IN_ENDPOINT,
				_RELATED_OBJECT_ID
			},
			new String[] {
				currentObjectName + "Id", objectRelationship.getName(),
				relatedObjectName + "Id"
			});

		entityPaths.addPathItem(
			customRelationshipEndpoint, customRelationshipPathItem);
	}

	private PathItem _createCustomRelationshipPathItem(
		PathItem relationshipPathItem, ObjectRelationship objectRelationship,
		ObjectDefinition relatedObjectDefinition) {

		Operation relationshipPutOperation = relationshipPathItem.getPut();

		List<Parameter> relationshipPutOperationParameters =
			relationshipPutOperation.getParameters();

		Stream<Parameter> relationshipPutOperationParametersStream =
			relationshipPutOperationParameters.stream();

		Map<String, Parameter> customRelationshipPutOperationParameters =
			relationshipPutOperationParametersStream.map(
				parameter -> {
					Parameter customParameter = new Parameter();

					customParameter.name(parameter.getName());
					customParameter.in(parameter.getIn());
					customParameter.required(parameter.getRequired());
					customParameter.schema(parameter.getSchema());

					return customParameter;
				}
			).collect(
				Collectors.toMap(Parameter::getName, parameter -> parameter)
			);

		String currentObjectName = StringUtil.lowerCaseFirstLetter(
			_currentObjectDefinition.getShortName());

		_customizeParameter(
			customRelationshipPutOperationParameters, _CURRENT_OBJECT_ID,
			currentObjectName + "Id", false);

		String relatedObjectName = StringUtil.lowerCaseFirstLetter(
			relatedObjectDefinition.getShortName());

		_customizeParameter(
			customRelationshipPutOperationParameters, _RELATED_OBJECT_ID,
			relatedObjectName + "Id", false);

		_customizeParameter(
			customRelationshipPutOperationParameters, _RELATIONSHIP_NAME,
			objectRelationship.getName(), true);

		Operation customRelationshipPutOperation = new Operation();

		customRelationshipPutOperation.tags(relationshipPutOperation.getTags());
		customRelationshipPutOperation.operationId(
			StringBundler.concat(
				"put", StringUtil.upperCaseFirstLetter(currentObjectName),
				StringUtil.upperCaseFirstLetter(objectRelationship.getName()),
				StringUtil.upperCaseFirstLetter(relatedObjectName)));
		customRelationshipPutOperation.parameters(
			new ArrayList<>(customRelationshipPutOperationParameters.values()));
		customRelationshipPutOperation.responses(
			relationshipPutOperation.getResponses());

		return new PathItem().put(customRelationshipPutOperation);
	}

	private void _customizeParameter(
		Map<String, Parameter> parameters, String parameterName,
		String newParameterName, boolean shouldBeDeleted) {

		Parameter parameter = parameters.get(parameterName);

		if (shouldBeDeleted) {
			parameters.remove(parameterName);
		}

		parameter.setName(newParameterName);
	}

	private List<String> _getRelationshipsEndpoints(Paths paths) {
		Set<String> keySet = paths.keySet();

		Stream<String> keySetStream = keySet.stream();

		return keySetStream.filter(
			pathItem -> pathItem.contains(_RELATIONSHIP_NAME)
		).collect(
			Collectors.toList()
		);
	}

	private static final String _CURRENT_OBJECT_ID = "currentObjectEntryId";

	private static final String _RELATED_OBJECT_ID = "relatedObjectEntryId";

	private static final String _RELATIONSHIP_NAME = "objectRelationshipName";

	private static final String _RELATIONSHIP_NAME_IN_ENDPOINT =
		"{objectRelationshipName}";

	private final ObjectDefinition _currentObjectDefinition;
	private final Map<ObjectRelationship, ObjectDefinition>
		_objectRelationshipRelatedObjectDefinitionMap;
	private final OpenAPIResource _openAPIResource;
	private final OpenAPISchemaFilter _openAPISchemaFilter;
	private final Set<Class<?>> _resourceClasses;

	@Context
	private UriInfo _uriInfo;

}