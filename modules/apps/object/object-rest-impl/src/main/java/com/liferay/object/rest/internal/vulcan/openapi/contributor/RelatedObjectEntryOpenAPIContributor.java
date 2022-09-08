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
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResource;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.openapi.contributor.OpenAPIContributor;
import com.liferay.portal.vulcan.util.TransformUtil;

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

import java.net.URI;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.core.Response;
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

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				systemObjectRelationship.getObjectDefinitionId2());

		openAPI.schema(
			objectDefinition.getShortName(),
			_getObjectDefinitionSchema(objectDefinition));

		Paths paths = openAPI.getPaths();

		paths.addPathItem(
			StringBundler.concat(
				StringPool.SLASH, _getJaxRsVersion(uriInfo), StringPool.SLASH,
				_getSystemObjectBasePath(systemObjectDefinitionMetadata),
				StringPool.SLASH,
				_getSystemObjectDefinitionPathName(
					systemObjectDefinitionMetadata),
				StringPool.SLASH, systemObjectRelationship.getName()),
			new PathItem() {
				{
					get(
						_getOperation(
							systemObjectRelationship,
							systemObjectDefinitionMetadata));
				}
			});

		paths.addPathItem(
			StringBundler.concat(
				StringPool.SLASH, _getJaxRsVersion(uriInfo), StringPool.SLASH,
				_getSystemObjectBasePath(systemObjectDefinitionMetadata),
				StringPool.SLASH,
				_getSystemObjectDefinitionPathName(
					systemObjectDefinitionMetadata),
				StringPool.SLASH, systemObjectRelationship.getName(),
				StringPool.SLASH,
				_getObjectDefinitionPathName(objectDefinition)),
			new PathItem() {
				{
					put(
						_getPutOperation(
							objectDefinition, systemObjectRelationship,
							systemObjectDefinitionMetadata));
				}
			});
	}

	private Content _getContent(ObjectRelationship objectRelationship)
		throws Exception {

		Content content = new Content();

		MediaType mediaType = new MediaType();

		Schema schema = new Schema();

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId2());

		schema.set$ref(objectDefinition.getShortName());

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

	private String _getObjectDefinitionPathName(
		ObjectDefinition objectDefinition) {

		return _getIdParameterTemplate(objectDefinition.getShortName());
	}

	private Schema _getObjectDefinitionSchema(ObjectDefinition objectDefinition)
		throws Exception {

		Response response = _objectEntryOpenAPIResource.getOpenAPI(
			objectDefinition.getObjectDefinitionId(), "json", null);

		OpenAPI openAPI = (OpenAPI)response.getEntity();

		Components components = openAPI.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		return schemas.get(objectDefinition.getShortName());
	}

	private Operation _getOperation(
			ObjectRelationship objectRelationship,
			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata)
		throws Exception {

		String lowerCaseFirstLetterContentType =
			StringUtil.lowerCaseFirstLetter(
				_getContentType(systemObjectDefinitionMetadata));

		String parameterName = lowerCaseFirstLetterContentType + "Id";

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
											_getContent(objectRelationship));
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

	private Operation _getPutOperation(
		ObjectDefinition objectDefinition,
		ObjectRelationship objectRelationship,
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		return new Operation() {
			{
				String upperCaseFirstLetterObjectRelationShipName =
					StringUtil.upperCaseFirstLetter(
						objectRelationship.getName());

				operationId("put" + upperCaseFirstLetterObjectRelationShipName);

				parameters(
					Arrays.asList(
						new Parameter() {
							{
								in("path");

								DTOConverter<?, ?> dtoConverter =
									_dtoConverterRegistry.getDTOConverter(
										systemObjectDefinitionMetadata.
											getModelClassName());

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
							setDefault(new ApiResponse());
						}
					});
				tags(
					Collections.singletonList(
						_getContentType(systemObjectDefinitionMetadata)));
			}
		};
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

	private String _getSystemObjectDefinitionPathName(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		return _getIdParameterTemplate(
			_getContentType(systemObjectDefinitionMetadata));
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