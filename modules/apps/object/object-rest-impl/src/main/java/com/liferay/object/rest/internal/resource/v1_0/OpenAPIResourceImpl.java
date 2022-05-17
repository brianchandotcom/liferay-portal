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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
		OpenAPIResource openAPIResource,
		OpenAPISchemaFilter openAPISchemaFilter,
		Map<ObjectRelationship, ObjectDefinition> relatedObjectDefinitionsMap,
		Set<Class<?>> resourceClasses) {

		_currentObjectDefinition = currentObjectDefinition;
		_openAPIResource = openAPIResource;
		_openAPISchemaFilter = openAPISchemaFilter;
		_relatedObjectDefinitionsMap = relatedObjectDefinitionsMap;
		_resourceClasses = resourceClasses;
	}

	@GET
	@Path("/openapi.{type:json|yaml}")
	@Produces({MediaType.APPLICATION_JSON, "application/yaml"})
	public Response getOpenAPI(@PathParam("type") String type)
		throws Exception {

		Response response = _openAPIResource.getOpenAPI(
			_openAPISchemaFilter, _resourceClasses, type, _uriInfo);

		OpenAPI openAPI = (OpenAPI)response.getEntity();

		Paths paths = openAPI.getPaths();

		for (String key : new ArrayList<>(paths.keySet())) {
			if (!key.contains("objectRelationshipName")) {
				continue;
			}

			for (Map.Entry<ObjectRelationship, ObjectDefinition> entry :
					_relatedObjectDefinitionsMap.entrySet()) {

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
								_currentObjectDefinition.getShortName()),
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
					_currentObjectDefinition.getShortName()
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

	private PathItem _createPathItem(
		ObjectRelationship objectRelationship, PathItem pathItem,
		ObjectDefinition relatedObjectDefinition) {

		Map<String, Parameter> parameters = new HashMap<>();

		Operation operation = pathItem.getPut();

		for (Parameter parameter : operation.getParameters()) {
			String parameterName = parameter.getName();

			if (Objects.equals(parameterName, "objectRelationshipName")) {
				continue;
			}

			if (Objects.equals(parameterName, "currentObjectEntryId")) {
				parameterName = StringUtil.replace(
					parameterName, "currentObjectEntry",
					StringUtil.lowerCaseFirstLetter(
						_currentObjectDefinition.getShortName()));
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

		return new PathItem() {
			{
				put(
					new Operation() {
						{
							operationId(
								StringBundler.concat(
									"put",
									_currentObjectDefinition.getShortName(),
									StringUtil.upperCaseFirstLetter(
										objectRelationship.getName()),
									relatedObjectDefinition.getShortName()));
							parameters(new ArrayList<>(parameters.values()));
							responses(operation.getResponses());
							tags(operation.getTags());
						}
					});
			}
		};
	}

	private final ObjectDefinition _currentObjectDefinition;
	private final OpenAPIResource _openAPIResource;
	private final OpenAPISchemaFilter _openAPISchemaFilter;
	private final Map<ObjectRelationship, ObjectDefinition>
		_relatedObjectDefinitionsMap;
	private final Set<Class<?>> _resourceClasses;

	@Context
	private UriInfo _uriInfo;

}