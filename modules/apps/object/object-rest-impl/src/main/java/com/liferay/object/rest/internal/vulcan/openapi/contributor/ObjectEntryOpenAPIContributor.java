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

package com.liferay.object.rest.internal.vulcan.openapi.contributor;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.openapi.contributor.OpenAPIContributor;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.core.UriInfo;

/**
 * @author Alejandro Tardín
 */
public class ObjectEntryOpenAPIContributor implements OpenAPIContributor {

	public ObjectEntryOpenAPIContributor(
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
	}

	@Override
	public void contribute(OpenAPI openAPI, UriInfo uriInfo) {
		Map<ObjectRelationship, ObjectDefinition> relatedObjectDefinitionsMap =
			_getRelatedObjectDefinitionsMap();

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
	}

	private Operation _createOperation(
		String httpMethod, ObjectRelationship objectRelationship,
		Operation operation, ObjectDefinition relatedObjectDefinition) {

		return new Operation() {
			{
				operationId(
					StringBundler.concat(
						httpMethod, _objectDefinition.getShortName(),
						StringUtil.upperCaseFirstLetter(
							objectRelationship.getName()),
						relatedObjectDefinition.getShortName()));
				parameters(_getParameters(operation, relatedObjectDefinition));
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
							"get", objectRelationship, pathItem.getGet(),
							relatedObjectDefinition));
				}
			};
		}

		operation = operations.get(PathItem.HttpMethod.PUT);

		if (operation != null) {
			return new PathItem() {
				{
					put(
						_createOperation(
							"put", objectRelationship, pathItem.getPut(),
							relatedObjectDefinition));
				}
			};
		}

		return new PathItem();
	}

	private List<Parameter> _getParameters(
		Operation operation, ObjectDefinition relatedObjectDefinition) {

		List<Parameter> parameters = new ArrayList<>();

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

			parameters.add(
				new Parameter() {
					{
						in(parameter.getIn());
						name(finalParameterName);
						required(parameter.getRequired());
						schema(parameter.getSchema());
					}
				});
		}

		return parameters;
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

	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;

}