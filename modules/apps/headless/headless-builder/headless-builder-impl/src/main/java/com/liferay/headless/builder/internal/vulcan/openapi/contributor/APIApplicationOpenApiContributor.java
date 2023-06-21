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

package com.liferay.headless.builder.internal.vulcan.openapi.contributor;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.provider.APIApplicationProvider;
import com.liferay.object.rest.dto.v1_0.FileEntry;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.vulcan.openapi.OpenAPIContext;
import com.liferay.portal.vulcan.openapi.contributor.OpenAPIContributor;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.BooleanSchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.DateSchema;
import io.swagger.v3.oas.models.media.DateTimeSchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.NumberSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = OpenAPIContributor.class)
public class APIApplicationOpenApiContributor implements OpenAPIContributor {

	@Override
	public void contribute(OpenAPI openAPI, OpenAPIContext openAPIContext) {
		if (openAPIContext == null) {
			return;
		}

		APIApplication apiApplication = _fetchApiApplication(openAPIContext);

		if (apiApplication == null) {
			return;
		}

		Components components = openAPI.getComponents();

		if (components == null) {
			components = new Components();

			openAPI.setComponents(components);
		}

		openAPI.setInfo(
			new Info() {
				{
					setDescription(
						"OpenAPI Specification of the " +
							apiApplication.getTitle() + " REST API");
					setLicense(
						new License() {
							{
								setName("Apache 2.0");
								setUrl(
									"http://www.apache.org/licenses" +
										"/LICENSE-2.0.html");
							}
						});
					setTitle(apiApplication.getTitle());
					setVersion(
						GetterUtil.get(apiApplication.getVersion(), "v1.0"));
				}
			});

		Map<String, Schema> schemas = components.getSchemas();

		if (schemas == null) {
			schemas = new TreeMap<>();

			components.setSchemas(schemas);
		}

		for (APIApplication.Schema schema : apiApplication.getSchemas()) {
			schemas.putAll(_toOpenAPISchemas(schema));
		}

		Paths oldPaths = openAPI.getPaths();

		Paths paths = new Paths();

		if ((oldPaths != null) && oldPaths.containsKey("/openapi.{type}")) {
			paths.put("/openapi.{type}", oldPaths.get("/openapi.{type}"));
		}

		for (APIApplication.Endpoint endpoint : apiApplication.getEndpoints()) {
			paths.put(
				_formatPath(endpoint.getPath()), _toOpenAPIPathItem(endpoint));
		}

		openAPI.setPaths(paths);
	}

	private void _addSchemas(Class<?> clazz, Map<String, Schema> schemas) {
		if (!schemas.containsKey(clazz.getSimpleName())) {
			schemas.putAll(_openAPIResource.getSchemas(clazz));
		}
	}

	private APIApplication _fetchApiApplication(OpenAPIContext openAPIContext) {
		String path = openAPIContext.getPath();

		if (path.startsWith("/o")) {
			path = path.substring(2);
		}

		if (path.startsWith("/")) {
			path = path.substring(1);
		}

		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}

		try {
			return _apiApplicationProvider.getAPIApplication(
				path, CompanyThreadLocal.getCompanyId());
		}
		catch (Exception exception) {
			if (!(exception instanceof NoSuchModelException)) {
				_log.error(exception);
			}

			return null;
		}
	}

	private String _formatPath(String path) {
		if (path.startsWith(StringPool.SLASH)) {
			return path;
		}

		return StringPool.SLASH + path;
	}

	private String _getOperationId(APIApplication.Endpoint endpoint) {
		Http.Method method = endpoint.getMethod();

		APIApplication.Schema responseSchema = endpoint.getResponseSchema();

		return StringUtil.toLowerCase(method.name()) +
			TextFormatter.formatPlural(responseSchema.getName()) + "Page";
	}

	private PathItem _toOpenAPIPathItem(APIApplication.Endpoint endpoint) {
		Operation operation = new Operation() {
			{
				setOperationId(_getOperationId(endpoint));
			}
		};

		APIApplication.Schema responseSchema = endpoint.getResponseSchema();

		if (responseSchema != null) {
			MediaType mediaType = new MediaType() {
				{
					setSchema(
						new Schema() {
							{
								set$ref("Page" + responseSchema.getName());
							}
						});
				}
			};

			Content content = new Content() {
				{
					put("application/json", mediaType);
					put("application/xml", mediaType);
				}
			};

			ApiResponse apiResponse = new ApiResponse() {
				{
					setContent(content);
					setDescription("default response");
				}
			};

			operation.setResponses(
				new ApiResponses() {
					{
						setDefault(apiResponse);
					}
				});

			operation.setTags(Arrays.asList(responseSchema.getName()));
		}

		return new PathItem() {
			{
				operation(
					PathItem.HttpMethod.valueOf(
						endpoint.getMethod(
						).name()),
					operation);
			}
		};
	}

	private Map<String, Schema> _toOpenAPISchemas(
		APIApplication.Schema schema) {

		Map<String, Schema> schemas = new TreeMap<>();

		Map<String, Schema> properties = new TreeMap<>();

		for (APIApplication.Property property : schema.getProperties()) {
			Schema propertySchema = null;

			APIApplication.Property.Type type = property.getType();

			if (type == APIApplication.Property.Type.AGGREGATION) {
				propertySchema = new StringSchema();
			}
			else if (type == APIApplication.Property.Type.ATTACHMENT) {
				_addSchemas(FileEntry.class, schemas);

				propertySchema = new Schema() {
					{
						set$ref("FileEntry");
					}
				};
			}
			else if (type == APIApplication.Property.Type.BOOLEAN) {
				propertySchema = new BooleanSchema();
			}
			else if (type == APIApplication.Property.Type.DATE) {
				propertySchema = new DateSchema();
			}
			else if (type == APIApplication.Property.Type.DATE_TIME) {
				propertySchema = new DateTimeSchema();
			}
			else if (type == APIApplication.Property.Type.DECIMAL) {
				propertySchema = new NumberSchema() {
					{
						setFormat("double");
					}
				};
			}
			else if (type == APIApplication.Property.Type.INTEGER) {
				propertySchema = new IntegerSchema();
			}
			else if (type == APIApplication.Property.Type.LONG_INTEGER) {
				propertySchema = new IntegerSchema() {
					{
						setFormat("int64");
					}
				};
			}
			else if (type == APIApplication.Property.Type.LONG_TEXT) {
				propertySchema = new StringSchema();
			}
			else if (type ==
						APIApplication.Property.Type.MULTISELECT_PICKLIST) {

				_addSchemas(ListEntry.class, schemas);

				propertySchema = new ArraySchema() {
					{
						setItems(
							new Schema() {
								{
									set$ref("ListEntry");
								}
							});
					}
				};
			}
			else if (type == APIApplication.Property.Type.PICKLIST) {
				_addSchemas(ListEntry.class, schemas);

				propertySchema = new Schema() {
					{
						set$ref("ListEntry");
					}
				};
			}
			else if (type == APIApplication.Property.Type.PRECISION_DECIMAL) {
				propertySchema = new NumberSchema() {
					{
						setFormat("double");
					}
				};
			}
			else if (type == APIApplication.Property.Type.RICH_TEXT) {
				propertySchema = new StringSchema();
			}
			else if (type == APIApplication.Property.Type.TEXT) {
				propertySchema = new StringSchema();
			}

			if (propertySchema != null) {
				propertySchema.setDescription(property.getDescription());
				propertySchema.setName(property.getName());

				properties.put(property.getName(), propertySchema);
			}
		}

		schemas.put(
			schema.getName(),
			new ObjectSchema() {
				{
					setDescription(schema.getDescription());
					setName(schema.getName());
					setProperties(properties);
				}
			});

		Map<String, Schema> pageSchemas = _openAPIResource.getSchemas(
			Page.class);

		Schema pageSchema = pageSchemas.remove("Page");

		Map<String, Schema> pageProperties = pageSchema.getProperties();

		ArraySchema itemsSchema = (ArraySchema)pageProperties.get("items");

		itemsSchema.setItems(
			new Schema() {
				{
					set$ref(schema.getName());
				}
			});

		schemas.put("Page" + schema.getName(), pageSchema);
		schemas.putAll(pageSchemas);

		return schemas;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		APIApplicationOpenApiContributor.class);

	@Reference
	private APIApplicationProvider _apiApplicationProvider;

	@Reference
	private OpenAPIResource _openAPIResource;

}