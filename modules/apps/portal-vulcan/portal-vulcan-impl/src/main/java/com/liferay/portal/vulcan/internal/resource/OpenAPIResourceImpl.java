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

package com.liferay.portal.vulcan.internal.resource;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.openapi.DTOProperty;
import com.liferay.portal.vulcan.openapi.OpenAPISchemaFilter;
import com.liferay.portal.vulcan.resource.OpenAPIResource;
import com.liferay.portal.vulcan.util.UriInfoUtil;

import io.swagger.v3.core.filter.AbstractSpecFilter;
import io.swagger.v3.core.filter.OpenAPISpecFilter;
import io.swagger.v3.core.filter.SpecFilter;
import io.swagger.v3.core.model.ApiDescription;
import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.integration.GenericOpenApiContext;
import io.swagger.v3.oas.integration.api.OpenAPIConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import io.swagger.v3.oas.integration.api.OpenApiScanner;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.servers.Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier Gamarra
 */
@Component(service = OpenAPIResource.class)
public class OpenAPIResourceImpl implements OpenAPIResource {

	@Override
	public Response getOpenAPI(
			OpenAPISchemaFilter openAPISchemaFilter,
			Set<Class<?>> resourceClasses, String type, UriInfo uriInfo)
		throws Exception {

		JaxrsOpenApiContextBuilder jaxrsOpenApiContextBuilder =
			new JaxrsOpenApiContextBuilder();

		OpenApiContext openApiContext = jaxrsOpenApiContextBuilder.buildContext(
			true);

		GenericOpenApiContext genericOpenApiContext =
			(GenericOpenApiContext)openApiContext;

		genericOpenApiContext.setCacheTTL(0L);
		genericOpenApiContext.setOpenApiScanner(
			new OpenApiScanner() {

				@Override
				public Set<Class<?>> classes() {
					return resourceClasses;
				}

				@Override
				public Map<String, Object> resources() {
					return new HashMap<>();
				}

				@Override
				public void setConfiguration(
					OpenAPIConfiguration openAPIConfiguration) {
				}

			});

		OpenAPI openAPI = openApiContext.read();

		if (openAPISchemaFilter != null) {
			SpecFilter specFilter = new SpecFilter();

			openAPI = specFilter.filter(
				openAPI, _toOpenAPISpecFilter(openAPISchemaFilter),
				uriInfo.getQueryParameters(), null, null);
		}

		if (openAPI == null) {
			return Response.status(
				404
			).build();
		}

		if (uriInfo != null) {
			Server server = new Server();

			server.setUrl(UriInfoUtil.getBasePath(uriInfo));

			openAPI.setServers(Collections.singletonList(server));
		}

		if (StringUtil.equalsIgnoreCase("yaml", type)) {
			return Response.status(
				Response.Status.OK
			).entity(
				Yaml.pretty(openAPI)
			).type(
				"application/yaml"
			).build();
		}

		return Response.status(
			Response.Status.OK
		).entity(
			openAPI
		).type(
			MediaType.APPLICATION_JSON_TYPE
		).build();
	}

	@Override
	public Response getOpenAPI(Set<Class<?>> resourceClasses, String type)
		throws Exception {

		return getOpenAPI(resourceClasses, type, null);
	}

	@Override
	public Response getOpenAPI(
			Set<Class<?>> resourceClasses, String type, UriInfo uriInfo)
		throws Exception {

		return getOpenAPI(null, resourceClasses, type, uriInfo);
	}

	private OpenAPISpecFilter _toOpenAPISpecFilter(
		OpenAPISchemaFilter openAPISchemaFilter) {

		Map<String, String> schemaMappings =
			openAPISchemaFilter.getSchemaMappings();

		return new AbstractSpecFilter() {

			@Override
			public Optional<OpenAPI> filterOpenAPI(
				OpenAPI openAPI, Map<String, List<String>> params,
				Map<String, String> cookies,
				Map<String, List<String>> headers) {

				Components components = openAPI.getComponents();

				Map<String, Schema> schemas = components.getSchemas();

				Paths paths = openAPI.getPaths();

				for (Map.Entry<String, String> entry :
						schemaMappings.entrySet()) {

					String key = entry.getKey();

					Schema schema = schemas.get(key);

					schemas.put(schemaMappings.get(key), schema);

					schemas.remove(key);

					_replaceParameters(key, paths);
				}

				return super.filterOpenAPI(openAPI, params, cookies, headers);
			}

			@Override
			public Optional<Operation> filterOperation(
				Operation operation, ApiDescription api,
				Map<String, List<String>> params, Map<String, String> cookies,
				Map<String, List<String>> headers) {

				List<String> tags = operation.getTags();

				if (tags != null) {
					List<String> newTags = new ArrayList<>(tags);

					for (Map.Entry<String, String> entry :
							schemaMappings.entrySet()) {

						for (int i = 0; i < newTags.size(); i++) {
							String tag = newTags.get(i);

							if (tag.equals(entry.getKey())) {
								newTags.set(i, entry.getValue());
							}
						}
					}

					operation.setTags(newTags);
				}

				operation.setOperationId(null);

				return super.filterOperation(
					operation, api, params, cookies, headers);
			}

			@Override
			public Optional<Parameter> filterParameter(
				Parameter parameter, Operation operation, ApiDescription api,
				Map<String, List<String>> params, Map<String, String> cookies,
				Map<String, List<String>> headers) {

				for (Map.Entry<String, String> entry :
						schemaMappings.entrySet()) {

					String parameterName = parameter.getName();

					String entryName = StringUtil.lowerCaseFirstLetter(
						entry.getKey());

					if (parameterName.contains(entryName)) {
						parameter.setName(
							StringUtil.replace(
								parameterName, entryName,
								StringUtil.lowerCaseFirstLetter(
									entry.getValue())));
					}
				}

				return super.filterParameter(
					parameter, operation, api, params, cookies, headers);
			}

			@Override
			public Optional<RequestBody> filterRequestBody(
				RequestBody requestBody, Operation operation,
				ApiDescription api, Map<String, List<String>> params,
				Map<String, String> cookies,
				Map<String, List<String>> headers) {

				_replaceContentReference(requestBody.getContent());

				return super.filterRequestBody(
					requestBody, operation, api, params, cookies, headers);
			}

			@Override
			public Optional<ApiResponse> filterResponse(
				ApiResponse response, Operation operation, ApiDescription api,
				Map<String, List<String>> params, Map<String, String> cookies,
				Map<String, List<String>> headers) {

				_replaceContentReference(response.getContent());

				return super.filterResponse(
					response, operation, api, params, cookies, headers);
			}

			@Override
			public Optional<Schema> filterSchema(
				Schema schema, Map<String, List<String>> params,
				Map<String, String> cookies,
				Map<String, List<String>> headers) {

				DTOProperty dtoProperty = openAPISchemaFilter.getDTOProperty();

				if (Objects.equals(dtoProperty.getName(), schema.getName())) {
					for (DTOProperty childDTOProperty :
							dtoProperty.getDTOProperties()) {

						schema.addProperties(
							childDTOProperty.getName(),
							_addSchema(childDTOProperty));
					}

					return Optional.of(schema);
				}

				return super.filterSchema(schema, params, cookies, headers);
			}

			@Override
			public Optional<Schema> filterSchemaProperty(
				Schema property, Schema schema, String propName,
				Map<String, List<String>> params, Map<String, String> cookies,
				Map<String, List<String>> headers) {

				for (Map.Entry<String, String> entry :
						schemaMappings.entrySet()) {

					_replaceReference(entry, property);

					if (property instanceof ArraySchema) {
						ArraySchema arraySchema = (ArraySchema)property;

						_replaceReference(entry, arraySchema.getItems());
					}
				}

				return super.filterSchemaProperty(
					property, schema, propName, params, cookies, headers);
			}

			private Schema<Object> _addSchema(DTOProperty dtoProperty) {
				Schema<Object> schema = new Schema<>();

				schema.setName(dtoProperty.getName());

				String type = dtoProperty.getType();

				if (type.equals("Boolean")) {
					schema.setType("boolean");
				}
				else if (type.equals("Date")) {
					schema.setFormat("date");
					schema.setType("string");
				}
				else if (type.equals("Double")) {
					schema.setFormat("double");
					schema.setType("number");
				}
				else if (type.equals("Integer")) {
					schema.setFormat("int32");
					schema.setType("integer");
				}
				else if (type.equals("Long")) {
					schema.setFormat("int64");
					schema.setType("integer");
				}
				else if (type.equals("String")) {
					schema.setType("string");
				}
				else {
					schema.setType("object");
				}

				for (DTOProperty childDTOProperty :
						dtoProperty.getDTOProperties()) {

					schema.addProperties(
						childDTOProperty.getName(),
						_addSchema(childDTOProperty));
				}

				return schema;
			}

			private void _replaceContentReference(Content content) {
				if (content != null) {
					for (io.swagger.v3.oas.models.media.MediaType mediaType :
							content.values()) {

						for (Map.Entry<String, String> entry :
								schemaMappings.entrySet()) {

							if (mediaType.getSchema() == null) {
								continue;
							}

							_replaceReference(entry, mediaType.getSchema());
						}
					}
				}
			}

			private void _replaceParameters(String key, Paths paths) {
				String parameterName = StringUtil.lowerCaseFirstLetter(key);

				for (String path : new ArrayList<>(paths.keySet())) {
					if (path.contains(parameterName)) {
						PathItem pathItem = paths.get(path);

						paths.put(
							path.replace(
								parameterName,
								StringUtil.lowerCaseFirstLetter(
									schemaMappings.get(key))),
							pathItem);

						paths.remove(path);
					}
				}
			}

			private void _replaceReference(
				Map.Entry<String, String> entry, Schema schema) {

				String ref = schema.get$ref();

				if ((ref != null) && ref.contains(entry.getKey())) {
					schema.set$ref(
						StringUtil.replace(
							ref, entry.getKey(), entry.getValue()));
				}
			}

		};
	}

}