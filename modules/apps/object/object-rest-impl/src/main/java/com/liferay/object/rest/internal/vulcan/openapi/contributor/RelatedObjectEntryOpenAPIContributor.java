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
import com.liferay.object.rest.internal.vulcan.openapi.contributor.util.OpenAPIContributorUtil;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResource;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataTracker;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.openapi.contributor.OpenAPIContributor;

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

import java.net.URI;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = OpenAPIContributor.class)
public class RelatedObjectEntryOpenAPIContributor
	implements OpenAPIContributor {

	@Override
	public void contribute(OpenAPI openAPI, UriInfo uriInfo) throws Exception {
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-153324")) ||
			(uriInfo == null)) {

			return;
		}

		List<SystemObjectDefinitionMetadata> systemObjectDefinitionMetadatas =
			TransformUtil.transform(
				_objectDefinitionLocalService.getSystemObjectDefinitions(),
				objectDefinition -> {
					SystemObjectDefinitionMetadata
						systemObjectDefinitionMetadata =
							_systemObjectDefinitionMetadataTracker.
								getSystemObjectDefinitionMetadata(
									objectDefinition.getName());

					URI uri = uriInfo.getBaseUri();

					String path = uri.getPath();

					if (path.contains(
							_getSystemObjectBasePath(
								systemObjectDefinitionMetadata.
									getRESTContextPath()))) {

						return systemObjectDefinitionMetadata;
					}

					return null;
				});

		for (SystemObjectDefinitionMetadata systemObjectDefinitionMetadata :
				systemObjectDefinitionMetadatas) {

			List<ObjectRelationship> systemObjectRelationships =
				_getSystemObjectRelationships(systemObjectDefinitionMetadata);

			for (ObjectRelationship systemObjectRelationship :
					systemObjectRelationships) {

				_contribute(
					openAPI, systemObjectDefinitionMetadata,
					systemObjectRelationship, uriInfo);
			}
		}
	}

	private void _contribute(
			OpenAPI openAPI,
			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata,
			ObjectRelationship systemObjectRelationship, UriInfo uriInfo)
		throws Exception {

		ObjectDefinition objectDefinition = _getRelatedObjectDefinition(
			systemObjectDefinitionMetadata, systemObjectRelationship);

		OpenAPI objectEntryOpenAPI =
			OpenAPIContributorUtil.getObjectEntryOpenAPI(
				objectDefinition, _objectEntryOpenAPIResource);

		OpenAPIContributorUtil.copySchemas(
			objectDefinition, objectEntryOpenAPI, openAPI);

		Paths paths = openAPI.getPaths();

		String name = StringBundler.concat(
			StringPool.SLASH, _getJaxRsVersion(uriInfo), StringPool.SLASH,
			_getSystemObjectBasePath(systemObjectDefinitionMetadata),
			StringPool.SLASH,
			_getIdParameterTemplate(
				_getContentType(systemObjectDefinitionMetadata)),
			StringPool.SLASH, systemObjectRelationship.getName());

		paths.addPathItem(
			name,
			new PathItem() {
				{
					get(
						_getGetOperation(
							objectDefinition, systemObjectRelationship,
							systemObjectDefinitionMetadata));
				}
			});
		paths.addPathItem(
			StringBundler.concat(
				name, StringPool.SLASH,
				_getIdParameterTemplate(objectDefinition.getShortName())),
			new PathItem() {
				{
					put(
						_getPutOperation(
							objectDefinition, systemObjectRelationship,
							systemObjectDefinitionMetadata));
				}
			});
	}

	private Content _getContent(String schemaName) {
		Content content = new Content();

		MediaType mediaType = new MediaType();

		Schema schema = new Schema();

		schema.set$ref(schemaName);

		mediaType.setSchema(schema);

		content.addMediaType("application/json", mediaType);
		content.addMediaType("application/xml", mediaType);

		return content;
	}

	private String _getContentType(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		DTOConverter<?, ?> dtoConverter = _dtoConverterRegistry.getDTOConverter(
			systemObjectDefinitionMetadata.getModelClassName());

		return dtoConverter.getContentType();
	}

	private Operation _getGetOperation(
		ObjectDefinition objectDefinition,
		ObjectRelationship objectRelationship,
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		String parameterName = _getIdParameterName(
			_getContentType(systemObjectDefinitionMetadata));

		return new Operation() {
			{
				operationId(
					StringBundler.concat(
						"get", systemObjectDefinitionMetadata.getName(),
						StringUtil.upperCaseFirstLetter(
							objectRelationship.getName())));
				parameters(
					Collections.singletonList(
						new Parameter() {
							{
								in("path");
								name(parameterName);
								required(true);
							}
						}));
				responses(
					new ApiResponses() {
						{
							setDefault(
								new ApiResponse() {
									{
										setContent(
											_getContent(
												OpenAPIContributorUtil.
													getPageSchemaName(
														objectDefinition)));
									}
								});
						}
					});
				tags(
					Collections.singletonList(
						_getContentType(systemObjectDefinitionMetadata)));
			}
		};
	}

	private String _getIdParameterName(String name) {
		return StringUtil.lowerCaseFirstLetter(name) + "Id";
	}

	private String _getIdParameterTemplate(String name) {
		return StringPool.OPEN_CURLY_BRACE + _getIdParameterName(name) +
			StringPool.CLOSE_CURLY_BRACE;
	}

	private String _getJaxRsVersion(UriInfo uriInfo) {
		String path = uriInfo.getPath();

		return path.split(StringPool.SLASH)[0];
	}

	private Operation _getPutOperation(
		ObjectDefinition objectDefinition,
		ObjectRelationship objectRelationship,
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		String upperCaseFirstLetterObjectRelationshipName =
			StringUtil.upperCaseFirstLetter(objectRelationship.getName());

		DTOConverter<?, ?> dtoConverter = _dtoConverterRegistry.getDTOConverter(
			systemObjectDefinitionMetadata.getModelClassName());

		return new Operation() {
			{
				operationId("put" + upperCaseFirstLetterObjectRelationshipName);
				parameters(
					Arrays.asList(
						new Parameter() {
							{
								in("path");
								name(
									_getIdParameterName(
										dtoConverter.getContentType()));
								required(true);
							}
						},
						new Parameter() {
							{
								in("path");
								name(
									_getIdParameterName(
										objectDefinition.getShortName()));
								required(true);
							}
						}));
				responses(
					new ApiResponses() {
						{
							setDefault(
								new ApiResponse() {
									{
										setContent(
											_getContent(
												OpenAPIContributorUtil.
													getSchemaName(
														objectDefinition)));
									}
								});
						}
					});
				tags(
					Collections.singletonList(
						_getContentType(systemObjectDefinitionMetadata)));
			}
		};
	}

	private ObjectDefinition _getRelatedObjectDefinition(
			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata,
			ObjectRelationship objectRelationship)
		throws Exception {

		ObjectDefinition systemObjectDefinition = _getSystemObjectDefinition(
			systemObjectDefinitionMetadata);

		long objectDefinitionId1 = objectRelationship.getObjectDefinitionId1();

		if (objectDefinitionId1 !=
				systemObjectDefinition.getObjectDefinitionId()) {

			return _objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());
		}

		return _objectDefinitionLocalService.getObjectDefinition(
			objectRelationship.getObjectDefinitionId2());
	}

	private String _getSystemObjectBasePath(
		String systemObjectRESTContextPath) {

		return systemObjectRESTContextPath.split(StringPool.SLASH)[0];
	}

	private String _getSystemObjectBasePath(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		String restContextPath =
			systemObjectDefinitionMetadata.getRESTContextPath();

		String[] restContextPathParts = restContextPath.split(StringPool.SLASH);

		return StringUtil.lowerCaseFirstLetter(
			restContextPathParts[restContextPathParts.length - 1]);
	}

	private ObjectDefinition _getSystemObjectDefinition(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		List<ObjectDefinition> systemObjectDefinitions =
			_objectDefinitionLocalService.getSystemObjectDefinitions();

		for (ObjectDefinition systemObjectDefinition :
				systemObjectDefinitions) {

			if (Objects.equals(
					systemObjectDefinition.getName(),
					systemObjectDefinitionMetadata.getName())) {

				return systemObjectDefinition;
			}
		}

		return null;
	}

	private List<ObjectRelationship> _getSystemObjectRelationships(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		ObjectDefinition systemObjectDefinition = _getSystemObjectDefinition(
			systemObjectDefinitionMetadata);

		if (systemObjectDefinition != null) {
			return _objectRelationshipLocalService.getObjectRelationships(
				systemObjectDefinition.getObjectDefinitionId());
		}

		return Collections.emptyList();
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryOpenAPIResource _objectEntryOpenAPIResource;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private SystemObjectDefinitionMetadataTracker
		_systemObjectDefinitionMetadataTracker;

}