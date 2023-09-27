/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.vulcan.openapi.contributor;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.provider.APIApplicationProvider;
import com.liferay.headless.builder.constants.HeadlessBuilderConstants;
import com.liferay.headless.builder.internal.util.OpenAPIUtil;
import com.liferay.headless.builder.internal.util.PathUtil;
import com.liferay.object.rest.dto.v1_0.FileEntry;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
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
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = OpenAPIContributor.class)
public class APIApplicationOpenApiContributor implements OpenAPIContributor {

	@Override
	public void contribute(OpenAPI openAPI, OpenAPIContext openAPIContext)
		throws Exception {

		if (openAPIContext == null) {
			return;
		}

		APIApplication apiApplication = _fetchAPIApplication(openAPIContext);

		if (apiApplication == null) {
			return;
		}

		Components components = openAPI.getComponents();

		if (components == null) {
			components = new Components();

			openAPI.setComponents(components);
		}

		Map<String, Schema> schemas = components.getSchemas();

		if (schemas == null) {
			schemas = new TreeMap<>();

			components.setSchemas(schemas);
		}

		for (APIApplication.Schema schema : apiApplication.getSchemas()) {
			schemas.putAll(_toOpenAPISchemas(schema));
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

		Paths paths = new Paths();

		Paths oldPaths = openAPI.getPaths();

		if ((oldPaths != null) && oldPaths.containsKey("/openapi.{type}")) {
			paths.put("/openapi.{type}", oldPaths.get("/openapi.{type}"));
		}

		Set<String> schemasSet = new HashSet<>();

		for (APIApplication.Endpoint endpoint : apiApplication.getEndpoints()) {
			paths.put(_formatPath(endpoint), _toOpenAPIPathItem(endpoint));

			APIApplication.Schema responseSchema = endpoint.getResponseSchema();

			if (responseSchema != null) {
				if (Objects.equals(
						endpoint.getRetrieveType(),
						APIApplication.Endpoint.RetrieveType.COLLECTION)) {

					schemasSet.add("Page" + responseSchema.getName());
				}
				else {
					schemasSet.add(responseSchema.getName());
				}
			}
		}

		components.setSchemas(_removedUnusedPageSchema(schemas, schemasSet));

		openAPI.setPaths(paths);
	}

	private void _addSchemas(
		Class<?> entityClass, Map<String, Schema> schemas) {

		if (!schemas.containsKey(entityClass.getSimpleName())) {
			schemas.putAll(_openAPIResource.getSchemas(entityClass));
		}
	}

	private APIApplication _fetchAPIApplication(OpenAPIContext openAPIContext)
		throws Exception {

		String path = openAPIContext.getPath();

		if (path.startsWith(HeadlessBuilderConstants.BASE_PATH)) {
			path = path.substring(4);
		}

		if (path.startsWith("/")) {
			path = path.substring(1);
		}

		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}

		return _apiApplicationProvider.fetchAPIApplication(
			path, CompanyThreadLocal.getCompanyId());
	}

	private String _formatPath(APIApplication.Endpoint endpoint) {
		String path = endpoint.getPath();

		if (!path.startsWith(StringPool.SLASH)) {
			path = StringPool.SLASH + path;
		}

		return PathUtil.getPathPrefix(endpoint.getScope()) + path;
	}

	private Map<String, Schema> _removedUnusedPageSchema(
		Map<String, Schema> schemas, Set<String> schemasSet) {

		Map<String, Schema> schemasMap = HashMapBuilder.putAll(
			schemas
		).build();

		for (String pageSchemaName : schemas.keySet()) {
			if (pageSchemaName.startsWith("Page") &&
				!schemasSet.contains(pageSchemaName)) {

				schemasMap.remove(pageSchemaName);
			}
		}

		return schemasMap;
	}

	private PathItem _toOpenAPIPathItem(APIApplication.Endpoint endpoint) {
		Operation operation = new Operation();

		String schemaName = null;

		APIApplication.Schema responseSchema = endpoint.getResponseSchema();

		if (responseSchema != null) {
			schemaName = responseSchema.getName();
		}

		operation.setOperationId(
			OpenAPIUtil.getOperationId(
				endpoint.getMethod(), _formatPath(endpoint),
				endpoint.getRetrieveType(), schemaName));

		if (Objects.equals(endpoint.getMethod(), Http.Method.GET)) {
			List<Parameter> parameters = new ArrayList<>();

			if (Objects.equals(
					endpoint.getScope(), APIApplication.Endpoint.Scope.GROUP)) {

				parameters.add(
					new Parameter() {
						{
							setIn("path");
							setName("scopeKey");
							setRequired(true);
							setSchema(new StringSchema());
						}
					});
			}

			if (Objects.equals(
					endpoint.getRetrieveType(),
					APIApplication.Endpoint.RetrieveType.COLLECTION)) {

				parameters.add(
					new Parameter() {
						{
							setIn("query");
							setName("filter");
							setSchema(new StringSchema());
						}
					});

				parameters.add(
					new Parameter() {
						{
							setIn("query");
							setName("page");
							setSchema(new StringSchema());
						}
					});
				parameters.add(
					new Parameter() {
						{
							setIn("query");
							setName("pageSize");
							setSchema(new StringSchema());
						}
					});

				parameters.add(
					new Parameter() {
						{
							setIn("query");
							setName("sort");
							setSchema(new StringSchema());
						}
					});
			}

			if (Objects.equals(
					endpoint.getRetrieveType(),
					APIApplication.Endpoint.RetrieveType.SINGLE_ELEMENT)) {

				parameters.add(
					new Parameter() {
						{
							setIn("path");
							setName(
								OpenAPIUtil.getPathParameter(
									endpoint.getPath()));
							setRequired(true);
							setSchema(new StringSchema());
						}
					});
			}

			operation.setParameters(parameters);
		}

		if (responseSchema != null) {
			MediaType mediaType = new MediaType() {
				{
					setSchema(
						new Schema() {
							{
								if (Objects.equals(
										endpoint.getRetrieveType(),
										APIApplication.Endpoint.RetrieveType.
											COLLECTION)) {

									set$ref("Page" + responseSchema.getName());
								}
								else {
									set$ref(responseSchema.getName());
								}
							}
						});
				}
			};

			ApiResponse apiResponse = new ApiResponse() {
				{
					setContent(
						new Content() {
							{
								put("application/json", mediaType);
								put("application/xml", mediaType);
							}
						});
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
		else {
			operation.setResponses(
				new ApiResponses() {
					{
						setDefault(
							new ApiResponse() {
								{
									setDescription("default response");
								}
							});
					}
				});
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

		ArraySchema itemsArraySchema = (ArraySchema)pageProperties.get("items");

		itemsArraySchema.setItems(
			new Schema() {
				{
					set$ref(schema.getName());
				}
			});

		schemas.put("Page" + schema.getName(), pageSchema);

		schemas.putAll(pageSchemas);

		return schemas;
	}

	@Reference
	private APIApplicationProvider _apiApplicationProvider;

	@Reference
	private OpenAPIResource _openAPIResource;

}