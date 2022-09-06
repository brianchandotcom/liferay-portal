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
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataTracker;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.openapi.contributor.OpenAPIContributor;
import com.liferay.portal.vulcan.util.TransformUtil;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public void contribute(OpenAPI openAPI, UriInfo uriInfo) {
		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-153324"))) {
			return;
		}

		List<SystemObjectDefinitionMetadata>
			systemObjectDefinitionMetadataList =
				_getSystemObjectDefinitionsMetadata(
					_objectDefinitionLocalService.getSystemObjectDefinitions(),
					uriInfo);

		for (SystemObjectDefinitionMetadata systemObjectDefinitionMetadata :
				systemObjectDefinitionMetadataList) {

			_populatePathItems(
				openAPI.getPaths(), systemObjectDefinitionMetadata, uriInfo);
		}
	}

	private PathItem _createPathItem(
		ObjectRelationship objectRelationship,
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		return new PathItem() {
			{
				get(
					_getOperation(
						objectRelationship, systemObjectDefinitionMetadata));
			}
		};
	}

	private String _getBaseUriPath(UriInfo uriInfo) {
		URI baseURI = uriInfo.getBaseUri();

		return baseURI.getPath();
	}

	private String _getExternalType(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		DTOConverter<?, ?> dtoConverter = _dtoConverterRegistry.getDTOConverter(
			systemObjectDefinitionMetadata.getModelClassName());

		return dtoConverter.getContentType();
	}

	private String _getJaxRsVersion(UriInfo uriInfo) {
		String path = uriInfo.getPath();

		return path.split(StringPool.SLASH)[0];
	}

	private Operation _getOperation(
		ObjectRelationship objectRelationship,
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		String systemObjectDefinitionExternalType =
			StringUtil.lowerCaseFirstLetter(
				_getExternalType(systemObjectDefinitionMetadata));

		String parameterName = systemObjectDefinitionExternalType + "Id";

		Map<String, Parameter> parameters = new HashMap<String, Parameter>() {
			{
				put(
					parameterName,
					new Parameter() {
						{
							name(parameterName);
							in("path");
							required(true);
						}
					});
			}
		};

		ApiResponses apiResponses = new ApiResponses();

		ApiResponse defaultResponse = new ApiResponse();

		Content content = new Content();

		MediaType mediaType = new MediaType();

		Schema<Object> schema = new Schema<>();

		schema.set$ref("");

		mediaType.setSchema(schema);

		content.addMediaType("application/json", mediaType);

		defaultResponse.setContent(content);

		apiResponses.setDefault(defaultResponse);

		return new Operation() {
			{
				operationId(
					StringBundler.concat(
						"get", systemObjectDefinitionMetadata.getName(),
						StringUtil.upperCaseFirstLetter(
							objectRelationship.getName())));
				parameters(new ArrayList<>(parameters.values()));
				responses(apiResponses);
				tags(
					Collections.singletonList(
						_getExternalType(systemObjectDefinitionMetadata)));
			}
		};
	}

	private String _getPath(
		ObjectRelationship objectRelationship,
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata,
		UriInfo uriInfo) {

		return StringBundler.concat(
			StringPool.SLASH, _getJaxRsVersion(uriInfo), StringPool.SLASH,
			_getSystemObjectBasePath(systemObjectDefinitionMetadata),
			StringPool.SLASH,
			_getSystemObjectDefinitionPathName(systemObjectDefinitionMetadata),
			StringPool.SLASH, objectRelationship.getName());
	}

	private String _getSystemObjectBasePath(
		String systemObjectRESTContextPath) {

		return systemObjectRESTContextPath.split(StringPool.SLASH)[0];
	}

	private String _getSystemObjectBasePath(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		String systemObjectRestContextPath =
			systemObjectDefinitionMetadata.getRESTContextPath();

		String[] systemObjectRestContextPathSplit =
			systemObjectRestContextPath.split(StringPool.SLASH);

		return StringUtil.lowerCaseFirstLetter(
			systemObjectRestContextPathSplit
				[systemObjectRestContextPathSplit.length - 1]);
	}

	private String _getSystemObjectDefinitionPathName(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		return StringBundler.concat(
			StringPool.OPEN_CURLY_BRACE,
			StringUtil.lowerCaseFirstLetter(
				_getExternalType(systemObjectDefinitionMetadata)),
			"Id}");
	}

	private List<SystemObjectDefinitionMetadata>
		_getSystemObjectDefinitionsMetadata(
			List<ObjectDefinition> systemObjectDefinitions, UriInfo uriInfo) {

		return ListUtil.filter(
			TransformUtil.transform(
				systemObjectDefinitions,
				this::_getSystemObjectDefinitionsMetadata),
			systemObjectDefinitionMetadata ->
				_shouldGenerateSystemObjectEndpoints(
					systemObjectDefinitionMetadata, uriInfo));
	}

	private SystemObjectDefinitionMetadata _getSystemObjectDefinitionsMetadata(
		ObjectDefinition objectDefinition) {

		return _systemObjectDefinitionMetadataTracker.
			getSystemObjectDefinitionMetadata(objectDefinition.getName());
	}

	private void _populatePathItems(
		Paths paths,
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata,
		UriInfo uriInfo) {

		List<ObjectRelationship> systemObjectRelationships =
			systemObjectDefinitionMetadata.getSystemObjectRelationships();

		for (ObjectRelationship systemObjectRelationship :
				systemObjectRelationships) {

			paths.addPathItem(
				_getPath(
					systemObjectRelationship, systemObjectDefinitionMetadata,
					uriInfo),
				_createPathItem(
					systemObjectRelationship, systemObjectDefinitionMetadata));
		}
	}

	private boolean _shouldGenerateSystemObjectEndpoints(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata,
		UriInfo uriInfo) {

		String baseURIPath = _getBaseUriPath(uriInfo);

		return baseURIPath.contains(
			_getSystemObjectBasePath(
				systemObjectDefinitionMetadata.getRESTContextPath()));
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private SystemObjectDefinitionMetadataTracker
		_systemObjectDefinitionMetadataTracker;

}