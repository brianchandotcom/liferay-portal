/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.vulcan.openapi.contributor;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.headless.builder.application.provider.APIApplicationProvider;
import com.liferay.headless.builder.constants.HeadlessBuilderConstants;
import com.liferay.headless.builder.internal.util.OpenAPIUtil;
import com.liferay.object.rest.dto.v1_0.FileEntry;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.CamelCaseUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
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
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

		for (APIApplication.Endpoint endpoint : apiApplication.getEndpoints()) {
			paths.put(
				_formatPath(endpoint.getPath()), _toOpenAPIPathItem(endpoint));
		}

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

	private String _formatPath(String path) {
		if (path.startsWith(StringPool.SLASH)) {
			return path;
		}

		return StringPool.SLASH + path;
	}

	private String _getOperationId(APIApplication.Endpoint endpoint) {
		Http.Method method = endpoint.getMethod();

		return StringUtil.toLowerCase(method.name()) +
			_toCamelCase(endpoint.getPath()) + "Page";
	}

	private String _getOperationId(
		Http.Method httpMethod, APIApplication.Schema schema,
		String operationId, String path, String returnType) {

		if (schema == null) {
			return operationId;
		}

		String schemaName = schema.getName();

		boolean collection = StringUtil.endsWith(operationId, "Page");

		List<String> methodNameSegments = new ArrayList<>();

		methodNameSegments.add(StringUtil.toLowerCase(httpMethod.name()));

		String[] pathSegments = path.split("/");
		String pluralSchemaName = TextFormatter.formatPlural(schemaName);

		for (int i = 0; i < pathSegments.length; i++) {
			String pathSegment = pathSegments[i];

			if (pathSegment.isEmpty()) {
				if (pathSegments.length != 1) {
					continue;
				}

				if (collection) {
					pathSegment = pluralSchemaName;
				}
				else {
					pathSegment = schemaName;
				}
			}

			String pathName = CamelCaseUtil.toCamelCase(
				pathSegment.replaceAll("\\{|-id|}|Id}", ""));

			if (StringUtil.equalsIgnoreCase(pathName, schemaName)) {
				pathName = schemaName;
			}
			else if (StringUtil.equalsIgnoreCase(pathName, pluralSchemaName)) {
				pathName = pluralSchemaName;
			}
			else {
				pathName = StringUtil.upperCaseFirstLetter(pathName);
			}

			if ((i == (pathSegments.length - 1)) && collection) {
				String previousMethodNameSegment = methodNameSegments.get(
					methodNameSegments.size() - 1);

				if (!pathName.endsWith(pluralSchemaName) &&
					previousMethodNameSegment.endsWith(schemaName)) {

					String string = StringUtil.replaceLast(
						previousMethodNameSegment, schemaName,
						pluralSchemaName);

					methodNameSegments.set(
						methodNameSegments.size() - 1, string);
				}

				methodNameSegments.add(pathName + "Page");
			}
			else if (Objects.equals(pathName, schemaName)) {
				methodNameSegments.add(pathName);
			}
			else if ((i != (pathSegments.length - 1)) ||
					 !Objects.equals(returnType, String.class.getName())) {

				String segment = OpenAPIUtil.formatSingular(pathName);

				String s = StringUtil.toLowerCase(segment);

				if (s.endsWith(StringUtil.toLowerCase(schemaName))) {
					char c = segment.charAt(
						segment.length() - schemaName.length());

					if (Character.isUpperCase(c)) {
						String substring = segment.substring(
							0, segment.length() - schemaName.length());

						segment = substring + schemaName;
					}
				}

				methodNameSegments.add(segment);
			}
		}

		return StringUtil.merge(methodNameSegments, "");
	}

	private String _toCamelCase(String path) {
		path = path.replaceAll("/\\{.*\\}", StringPool.BLANK);

		path = path.replaceAll(StringPool.MINUS, StringPool.SLASH);

		return CamelCaseUtil.toCamelCase(path, CharPool.SLASH);
	}

	private PathItem _toOpenAPIPathItem(APIApplication.Endpoint endpoint) {
		String operationId = _getOperationId(endpoint);

		Operation operation = new Operation() {
			{
				setOperationId(operationId);
			}
		};

		operation.setOperationId(
			_getOperationId(
				endpoint.getMethod(), endpoint.getResponseSchema(),
				operation.getOperationId(), endpoint.getPath(), "Page"));

		APIApplication.Schema responseSchema = endpoint.getResponseSchema();

		if (responseSchema != null) {
			if (Objects.equals(endpoint.getMethod(), Http.Method.GET) &&
				operationId.endsWith("Page")) {

				operation.setParameters(
					ListUtil.fromArray(
						new Parameter() {
							{
								setIn("query");
								setName("page");
								setSchema(new StringSchema());
							}
						},
						new Parameter() {
							{
								setIn("query");
								setName("pageSize");
								setSchema(new StringSchema());
							}
						}));
			}

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