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

import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.internal.vulcan.openapi.contributor.util.OpenAPIContributorUtil;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResource;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionMetadataRegistry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
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
public class ObjectEntryOpenAPIContributor extends BaseOpenAPIContributor {

	public ObjectEntryOpenAPIContributor(
		BundleContext bundleContext, DTOConverterRegistry dtoConverterRegistry,
		ObjectActionLocalService objectActionLocalService,
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryOpenAPIResource objectEntryOpenAPIResource,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		OpenAPIResource openAPIResource,
		SystemObjectDefinitionMetadataRegistry
			systemObjectDefinitionMetadataRegistry) {

		_bundleContext = bundleContext;
		_objectActionLocalService = objectActionLocalService;
		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryOpenAPIResource = objectEntryOpenAPIResource;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_openAPIResource = openAPIResource;

		init(dtoConverterRegistry, systemObjectDefinitionMetadataRegistry);
	}

	@Override
	public void contribute(OpenAPI openAPI, UriInfo uriInfo) throws Exception {
		List<ObjectAction> objectActions =
			_objectActionLocalService.getObjectActions(
				_objectDefinition.getObjectDefinitionId(),
				ObjectActionTriggerConstants.KEY_STANDALONE);
		Map<ObjectRelationship, ObjectDefinition> relatedObjectDefinitionsMap =
			_getRelatedObjectDefinitionsMap();

		Paths paths = openAPI.getPaths();

		for (String key : new ArrayList<>(paths.keySet())) {
			if (!key.contains("objectActionName") &&
				!key.contains("objectRelationshipName")) {

				continue;
			}

			if (key.contains("objectActionName")) {
				if (GetterUtil.getBoolean(
						PropsUtil.get("feature.flag.LPS-166918"))) {

					ListUtil.isNotEmptyForEach(
						objectActions,
						objectAction -> _addObjectActionPathItem(
							key, objectAction, paths));
				}
			}
			else if (key.contains("objectRelationshipName")) {
				for (Map.Entry<ObjectRelationship, ObjectDefinition> entry :
						relatedObjectDefinitionsMap.entrySet()) {

					ObjectRelationship objectRelationship = entry.getKey();

					ObjectDefinition relatedObjectDefinition = entry.getValue();

					if (!relatedObjectDefinition.isSystem() ||
						GetterUtil.getBoolean(
							PropsUtil.get("feature.flag.LPS-162966"))) {

						String relatedSchemaName = getSchemaName(
							relatedObjectDefinition);

						if (uriInfo != null) {
							_addObjectRelationshipSchema(
								relatedObjectDefinition, openAPI,
								relatedSchemaName);
						}

						_addObjectRelationshipPathItem(
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
										" can be embedded with ",
										"\"nestedFields\"."));
							}
						}
					);
				}
			}

			paths.remove(key);
		}
	}

	private void _addObjectActionPathItem(
		String key, ObjectAction objectAction, Paths paths) {

		paths.addPathItem(
			StringUtil.replace(
				key, new String[] {"objectEntry", "{objectActionName}"},
				new String[] {
					StringUtil.lowerCaseFirstLetter(
						_objectDefinition.getShortName()),
					objectAction.getName()
				}),
			_createObjectActionPathItem(objectAction, paths.get(key)));
	}

	private void _addObjectRelationshipPathItem(
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
			_createObjectRelationshipPathItem(
				paths.get(key), objectRelationship, schemaName));
	}

	private void _addObjectRelationshipSchema(
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
				_bundleContext, getExternalDTOClassName(objectDefinition),
				_openAPIResource);
		}
		else {
			sourceOpenAPI = OpenAPIContributorUtil.getObjectEntryOpenAPI(
				objectDefinition, _objectEntryOpenAPIResource);
		}

		OpenAPIContributorUtil.copySchemas(
			schemaName, sourceOpenAPI, objectDefinition.isSystem(), openAPI);
	}

	private PathItem _createObjectActionPathItem(
		ObjectAction objectAction, PathItem pathItem) {

		Map<PathItem.HttpMethod, Operation> operations =
			pathItem.readOperationsMap();

		Operation operation = operations.get(PathItem.HttpMethod.PUT);

		if (operation == null) {
			return new PathItem();
		}

		return new PathItem() {
			{
				put(
					new Operation() {
						{
							operationId(
								StringBundler.concat(
									"put", _objectDefinition.getShortName(),
									StringUtil.upperCaseFirstLetter(
										objectAction.getName())));
							parameters(_getParameters(operation, null));
							responses(operation.getResponses());
							tags(operation.getTags());
						}
					});
			}
		};
	}

	private PathItem _createObjectRelationshipPathItem(
		PathItem existingPathItem, ObjectRelationship objectRelationship,
		String schemaName) {

		PathItem pathItem = new PathItem();

		Map<PathItem.HttpMethod, Operation> operations =
			existingPathItem.readOperationsMap();

		if (operations.containsKey(PathItem.HttpMethod.DELETE)) {
			pathItem.delete(
				_getObjectRelationshipDeleteOperation(
					objectRelationship, existingPathItem.getDelete(),
					schemaName));
		}

		if (operations.containsKey(PathItem.HttpMethod.GET)) {
			pathItem.get(
				_getObjectRelationshipGetOperation(
					objectRelationship, existingPathItem.getGet(), schemaName));
		}

		if (operations.containsKey(PathItem.HttpMethod.PUT)) {
			pathItem.put(
				_getObjectRelationshipPutOperation(
					objectRelationship, existingPathItem.getPut(), schemaName));
		}

		return pathItem;
	}

	private Content _getContent(Content originalContent, String schemaName) {
		Content content = new Content();

		Schema schema = null;

		if (schemaName != null) {
			schema = new Schema();

			schema.set$ref(schemaName);
		}

		for (String key : originalContent.keySet()) {
			Schema finalSchema = schema;

			content.addMediaType(
				key,
				new MediaType() {
					{
						setSchema(finalSchema);
					}
				});
		}

		return content;
	}

	private ApiResponses _getObjectRelationshipApiResponses(
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

	private Operation _getObjectRelationshipDeleteOperation(
		ObjectRelationship objectRelationship, Operation operation,
		String schemaName) {

		return new Operation() {
			{
				operationId(
					StringBundler.concat(
						"delete", _objectDefinition.getShortName(),
						StringUtil.upperCaseFirstLetter(
							objectRelationship.getName()),
						schemaName));
				parameters(_getParameters(operation, schemaName));
				responses(_getObjectRelationshipApiResponses(operation, null));
				tags(operation.getTags());
			}
		};
	}

	private Operation _getObjectRelationshipGetOperation(
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
					_getObjectRelationshipApiResponses(
						operation,
						OpenAPIContributorUtil.getPageSchemaName(schemaName)));
				tags(operation.getTags());
			}
		};
	}

	private Operation _getObjectRelationshipPutOperation(
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
				responses(
					_getObjectRelationshipApiResponses(operation, schemaName));
				tags(operation.getTags());
			}
		};
	}

	private List<Parameter> _getParameters(
		Operation operation, String schemaName) {

		List<Parameter> parameters = new ArrayList<>();

		for (Parameter parameter : operation.getParameters()) {
			String parameterName = parameter.getName();

			if (Objects.equals(parameterName, "objectActionName") ||
				Objects.equals(parameterName, "objectRelationshipName")) {

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
	private final ObjectActionLocalService _objectActionLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryOpenAPIResource _objectEntryOpenAPIResource;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final OpenAPIResource _openAPIResource;

}