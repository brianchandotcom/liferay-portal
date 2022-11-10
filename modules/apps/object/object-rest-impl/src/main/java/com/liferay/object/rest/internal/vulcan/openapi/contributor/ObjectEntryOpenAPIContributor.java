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
import com.liferay.object.rest.internal.helper.ObjectHelper;
import com.liferay.object.rest.internal.vulcan.openapi.contributor.util.OpenAPIContributorUtil;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResource;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.openapi.contributor.OpenAPIContributor;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.core.UriInfo;

import org.osgi.framework.BundleContext;

/**
 * @author Alejandro Tardín
 */
public class ObjectEntryOpenAPIContributor implements OpenAPIContributor {

	public ObjectEntryOpenAPIContributor(
		BundleContext bundleContext, ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryOpenAPIResource objectEntryOpenAPIResource,
		ObjectHelper objectHelper,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		OpenAPIResource openAPIResource) {

		_bundleContext = bundleContext;
		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryOpenAPIResource = objectEntryOpenAPIResource;
		_objectHelper = objectHelper;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_openAPIResource = openAPIResource;
	}

	@Override
	public void contribute(OpenAPI openAPI, UriInfo uriInfo) throws Exception {
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

				if (!relatedObjectDefinition.isSystem() ||
					GetterUtil.getBoolean(
						PropsUtil.get("feature.flag.LPS-162966"))) {

					String relatedSchemaName = _objectHelper.getSchemaName(
						relatedObjectDefinition);

					if (uriInfo != null) {
						_addSchema(
							relatedObjectDefinition, openAPI,
							relatedSchemaName);
					}

					_addPathItem(
						key, objectRelationship, paths, relatedSchemaName);
				}

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

	private void _addPathItem(
		String key, ObjectRelationship objectRelationship, Paths paths,
		String schemaName) {

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
					StringUtil.lowerCaseFirstLetter(schemaName)
				}),
			_createPathItem(objectRelationship, paths.get(key), schemaName));
	}

	private void _addSchema(
			ObjectDefinition objectDefinition, OpenAPI openAPI,
			String schemaName)
		throws Exception {

		Components components = openAPI.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		if (schemas.containsKey(schemaName)) {
			return;
		}

		OpenAPI sourceOpenAPI;

		if (objectDefinition.isSystem()) {
			sourceOpenAPI = OpenAPIContributorUtil.getSystemObjectOpenAPI(
				_bundleContext,
				_objectHelper.getExternalDTOClassName(objectDefinition),
				_openAPIResource);
		}
		else {
			sourceOpenAPI = OpenAPIContributorUtil.getObjectEntryOpenAPI(
				objectDefinition, _objectEntryOpenAPIResource);
		}

		OpenAPIContributorUtil.copySchemas(
			schemaName, sourceOpenAPI, objectDefinition.isSystem(), openAPI);
	}

	private PathItem _createPathItem(
		ObjectRelationship objectRelationship, PathItem pathItem,
		String schemaName) {

		Map<PathItem.HttpMethod, Operation> operations =
			pathItem.readOperationsMap();

		Operation operation = operations.get(PathItem.HttpMethod.GET);

		if (operation != null) {
			return new PathItem() {
				{
					get(
						_getGetOperation(
							objectRelationship, pathItem.getGet(), schemaName));
				}
			};
		}

		operation = operations.get(PathItem.HttpMethod.PUT);

		if (operation != null) {
			return new PathItem() {
				{
					put(
						_getPutOperation(
							objectRelationship, pathItem.getPut(), schemaName));
				}
			};
		}

		return new PathItem();
	}

	private ApiResponses _getApiResponses(
		Operation operation, String schemaName) {

		ApiResponses apiResponses = new ApiResponses();

		ApiResponses operationApiResponses = operation.getResponses();

		for (Map.Entry<String, ApiResponse> entry :
				operationApiResponses.entrySet()) {

			ApiResponse apiResponse = entry.getValue();

			apiResponses.put(
				entry.getKey(),
				new ApiResponse() {
					{
						setContent(
							_getContent(apiResponse.getContent(), schemaName));
						setDescription(apiResponse.getDescription());
					}
				});
		}

		return apiResponses;
	}

	private Content _getContent(Content originalContent, String schemaName) {
		Content content = new Content();

		Schema schema = new Schema();

		schema.set$ref(schemaName);

		for (String key : originalContent.keySet()) {
			content.addMediaType(
				key,
				new MediaType() {
					{
						setSchema(schema);
					}
				});
		}

		return content;
	}

	private Operation _getGetOperation(
		ObjectRelationship objectRelationship, Operation operation,
		String schemaName) {

		return new Operation() {
			{
				operationId(
					StringBundler.concat(
						"get", _objectDefinition.getShortName(),
						StringUtil.upperCaseFirstLetter(
							objectRelationship.getName()),
						schemaName, "Page"));
				parameters(_getParameters(operation, schemaName));
				responses(
					_getApiResponses(
						operation,
						OpenAPIContributorUtil.getPageSchemaName(schemaName)));
				tags(operation.getTags());
			}
		};
	}

	private List<Parameter> _getParameters(
		Operation operation, String schemaName) {

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
					StringUtil.lowerCaseFirstLetter(schemaName));
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

	private Operation _getPutOperation(
		ObjectRelationship objectRelationship, Operation operation,
		String schemaName) {

		return new Operation() {
			{
				operationId(
					StringBundler.concat(
						"put", _objectDefinition.getShortName(),
						StringUtil.upperCaseFirstLetter(
							objectRelationship.getName()),
						schemaName));
				parameters(_getParameters(operation, schemaName));
				responses(_getApiResponses(operation, schemaName));
				tags(operation.getTags());
			}
		};
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

	private final BundleContext _bundleContext;
	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryOpenAPIResource _objectEntryOpenAPIResource;
	private final ObjectHelper _objectHelper;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final OpenAPIResource _openAPIResource;

}