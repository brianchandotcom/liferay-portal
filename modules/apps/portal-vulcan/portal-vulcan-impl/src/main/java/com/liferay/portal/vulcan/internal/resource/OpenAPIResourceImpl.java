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

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.vulcan.extension.ExtensionProviderRegistry;
import com.liferay.portal.vulcan.extension.PropertyDefinition;
import com.liferay.portal.vulcan.internal.configuration.util.ConfigurationUtil;
import com.liferay.portal.vulcan.internal.extension.EntityExtensionHandler;
import com.liferay.portal.vulcan.internal.extension.util.ExtensionUtil;
import com.liferay.portal.vulcan.jaxrs.JaxRsResourceRegistry;
import com.liferay.portal.vulcan.openapi.DTOProperty;
import com.liferay.portal.vulcan.openapi.OpenAPISchemaFilter;
import com.liferay.portal.vulcan.openapi.contributor.OpenAPIContributor;
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
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.servers.Server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(service = OpenAPIResource.class)
public class OpenAPIResourceImpl implements OpenAPIResource {

	@Override
	public Response getOpenAPI(
			HttpServletRequest httpServletRequest,
			Set<Class<?>> resourceClasses, String type, UriInfo uriInfo)
		throws Exception {

		return _getOpenAPI(
			httpServletRequest, null, null, resourceClasses, type, uriInfo);
	}

	@Override
	public Response getOpenAPI(
			OpenAPIContributor openAPIContributor,
			OpenAPISchemaFilter openAPISchemaFilter,
			Set<Class<?>> resourceClasses, String type, UriInfo uriInfo)
		throws Exception {

		return _getOpenAPI(
			null, openAPIContributor, openAPISchemaFilter, resourceClasses,
			type, uriInfo);
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

		return getOpenAPI(null, null, resourceClasses, type, uriInfo);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_trackedOpenAPIContributors = ServiceTrackerListFactory.open(
			bundleContext, OpenAPIContributor.class);
	}

	@Deactivate
	protected void deactivate() {
		_trackedOpenAPIContributors.close();
	}

	private String _getBasePath(
		HttpServletRequest httpServletRequest, UriInfo uriInfo) {

		if (uriInfo == null) {
			return null;
		}

		if (httpServletRequest == null) {
			return UriInfoUtil.getBasePath(uriInfo);
		}

		String scheme = Http.HTTP;

		if (_portal.isSecure(httpServletRequest)) {
			scheme = Http.HTTPS;
		}

		UriBuilder uriBuilder = UriInfoUtil.getBaseUriBuilder(uriInfo);

		uriBuilder.host(
			_portal.getForwardedHost(httpServletRequest)
		).scheme(
			scheme
		);

		return String.valueOf(uriBuilder.build());
	}

	private Set<String> _getDTOClassNames(Set<Class<?>> resourceClasses) {
		Set<String> classNames = new HashSet<>();

		for (Class<?> resourceClass : resourceClasses) {
			String className = resourceClass.getName();

			Object propertyValue = _jaxRsResourceRegistry.getPropertyValue(
				className, "entity.class.name");

			if (propertyValue != null) {
				classNames.add((String)propertyValue);
			}
		}

		return classNames;
	}

	private DTOProperty _getDTOProperty(PropertyDefinition propertyDefinition) {
		PropertyDefinition.PropertyType propertyType =
			propertyDefinition.getPropertyType();

		DTOProperty dtoProperty;

		String type = null;

		if ((propertyType ==
				PropertyDefinition.PropertyType.MULTIPLE_ELEMENT) ||
			(propertyType == PropertyDefinition.PropertyType.SINGLE_ELEMENT)) {

			if (propertyType ==
					PropertyDefinition.PropertyType.MULTIPLE_ELEMENT) {

				type = "Array";
			}
			else {
				type = "Object";
			}

			dtoProperty = new DTOProperty(
				null, propertyDefinition.getPropertyName(), type);

			DTOProperty objectDTOProperty = new DTOProperty(
				null, propertyDefinition.getPropertyClassName(), "Object");

			dtoProperty.setDTOProperties(Arrays.asList(objectDTOProperty));

			if (ListUtil.isNotEmpty(
					propertyDefinition.getPropertyDefinitions())) {

				List<DTOProperty> dtoProperties = new ArrayList<>();

				for (PropertyDefinition definition :
						propertyDefinition.getPropertyDefinitions()) {

					dtoProperties.add(_getDTOProperty(definition));
				}

				objectDTOProperty.setDTOProperties(dtoProperties);
			}
		}
		else {
			if (propertyType == PropertyDefinition.PropertyType.BIG_DECIMAL) {
				type = "Double";
			}
			else if (propertyType == PropertyDefinition.PropertyType.BOOLEAN) {
				type = "Boolean";
			}
			else if (propertyType ==
						PropertyDefinition.PropertyType.DATE_TIME) {

				type = "Date";
			}
			else if (propertyType == PropertyDefinition.PropertyType.DECIMAL) {
				type = "Float";
			}
			else if (propertyType == PropertyDefinition.PropertyType.DOUBLE) {
				type = "Double";
			}
			else if (propertyType == PropertyDefinition.PropertyType.INTEGER) {
				type = "Integer";
			}
			else if (propertyType == PropertyDefinition.PropertyType.LONG) {
				type = "Long";
			}
			else if (propertyType == PropertyDefinition.PropertyType.TEXT) {
				type = "String";
			}
			else {
				type = "Object";
			}

			dtoProperty = new DTOProperty(
				null, propertyDefinition.getPropertyName(), type);
		}

		dtoProperty.setDescription(propertyDefinition.getPropertyDescription());

		return dtoProperty;
	}

	private List<PropertyDefinition> _getExtendedPropertyDefinitions(
			String className, long companyId,
			ExtensionProviderRegistry extensionProviderRegistry)
		throws Exception {

		List<PropertyDefinition> propertyDefinitions = null;

		EntityExtensionHandler entityExtensionHandler =
			ExtensionUtil.getEntityExtensionHandler(
				className, companyId, extensionProviderRegistry);

		if (entityExtensionHandler != null) {
			Map<String, PropertyDefinition> extendedPropertyDefinitions =
				entityExtensionHandler.getExtendedPropertyDefinitions(
					companyId, className);

			propertyDefinitions = new ArrayList<>(
				extendedPropertyDefinitions.values());
		}

		return propertyDefinitions;
	}

	private Response _getOpenAPI(
			HttpServletRequest httpServletRequest,
			OpenAPIContributor openAPIContributor,
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

		OpenAPISchemaFilter mergedOpenAPISchemaFilter =
			_mergeOpenAPISchemaFilters(
				openAPISchemaFilter,
				_getOpenAPISchemaFilter(
					_getBasePath(null, uriInfo), _extensionProviderRegistry,
					resourceClasses));

		if (mergedOpenAPISchemaFilter != null) {
			SpecFilter specFilter = new SpecFilter();

			Map<String, List<String>> queryParameters = null;

			if (uriInfo != null) {
				queryParameters = uriInfo.getQueryParameters();
			}

			openAPI = specFilter.filter(
				openAPI, _toOpenAPISpecFilter(mergedOpenAPISchemaFilter),
				queryParameters, null, null);
		}

		if (openAPI == null) {
			return Response.status(
				404
			).build();
		}

		if (uriInfo != null) {
			Server server = new Server();

			server.setUrl(_getBasePath(httpServletRequest, uriInfo));

			openAPI.setServers(Collections.singletonList(server));
		}

		if (openAPIContributor != null) {
			openAPIContributor.contribute(openAPI, uriInfo);
		}

		for (OpenAPIContributor trackedOpenAPIContributor :
				_trackedOpenAPIContributors) {

			trackedOpenAPIContributor.contribute(openAPI, uriInfo);
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

	private OpenAPISchemaFilter _getOpenAPISchemaFilter(
			String basePath,
			ExtensionProviderRegistry extensionProviderRegistry,
			Set<Class<?>> resourceClasses)
		throws Exception {

		Set<String> classNames = _getDTOClassNames(resourceClasses);

		if (SetUtil.isEmpty(classNames)) {
			return null;
		}

		long companyId = CompanyThreadLocal.getCompanyId();

		Map<String, List<PropertyDefinition>> propertyDefinitionsMap =
			new HashMap<>();

		for (String className : classNames) {
			List<PropertyDefinition> propertyDefinitions =
				_getExtendedPropertyDefinitions(
					className, companyId, extensionProviderRegistry);

			if (ListUtil.isNotEmpty(propertyDefinitions)) {
				propertyDefinitionsMap.put(className, propertyDefinitions);
			}
		}

		if (MapUtil.isNotEmpty(propertyDefinitionsMap)) {
			return _getOpenAPISchemaFilter(basePath, propertyDefinitionsMap);
		}

		return null;
	}

	private OpenAPISchemaFilter _getOpenAPISchemaFilter(
		String applicationPath,
		Map<String, List<PropertyDefinition>> propertyDefinitions) {

		List<DTOProperty> dtoProperties = new ArrayList<>();

		for (Map.Entry<String, List<PropertyDefinition>> entry :
				propertyDefinitions.entrySet()) {

			String name = entry.getKey();

			DTOProperty dtoProperty = new DTOProperty(
				null, StringUtil.extractLast(name, "."), "object");

			dtoProperty.setDTOProperties(
				TransformUtil.transform(
					entry.getValue(), this::_getDTOProperty));

			dtoProperties.add(dtoProperty);
		}

		return new OpenAPISchemaFilter() {
			{
				setApplicationPath(applicationPath);
				setDTOProperties(dtoProperties);
			}
		};
	}

	private OpenAPISchemaFilter _mergeOpenAPISchemaFilters(
		OpenAPISchemaFilter openAPISchemaFilter1,
		OpenAPISchemaFilter openAPISchemaFilter2) {

		if (openAPISchemaFilter1 == null) {
			return openAPISchemaFilter2;
		}

		if (openAPISchemaFilter2 == null) {
			return openAPISchemaFilter1;
		}

		OpenAPISchemaFilter mergedOpenAPISchemaFilter =
			new OpenAPISchemaFilter();

		mergedOpenAPISchemaFilter.setApplicationPath(
			openAPISchemaFilter1.getApplicationPath());

		List<DTOProperty> dtoProperties =
			mergedOpenAPISchemaFilter.getDTOProperties();

		dtoProperties.addAll(openAPISchemaFilter1.getDTOProperties());
		dtoProperties.addAll(openAPISchemaFilter2.getDTOProperties());

		Map<String, String> schemaMappings =
			mergedOpenAPISchemaFilter.getSchemaMappings();

		schemaMappings.putAll(openAPISchemaFilter1.getSchemaMappings());
		schemaMappings.putAll(openAPISchemaFilter2.getSchemaMappings());

		return mergedOpenAPISchemaFilter;
	}

	private OpenAPISpecFilter _toOpenAPISpecFilter(
		OpenAPISchemaFilter openAPISchemaFilter) {

		List<DTOProperty> dtoProperties =
			openAPISchemaFilter.getDTOProperties();

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

				for (Map.Entry<String, String> entry :
						schemaMappings.entrySet()) {

					String key = entry.getKey();

					Schema schema = schemas.get(key);

					schemas.put(schemaMappings.get(key), schema);

					schemas.remove(key);

					_replaceParameters(key, openAPI.getPaths());
				}

				for (DTOProperty dtoProperty : dtoProperties) {
					Map<DTOProperty, Schema> newSchemas = _getNewSchemas(
						dtoProperty);

					if (MapUtil.isEmpty(newSchemas)) {
						continue;
					}

					_fixNewSchemaNames(newSchemas, schemas);

					for (Schema schema : newSchemas.values()) {
						openAPI.schema(schema.getName(), schema);
					}
				}

				return super.filterOpenAPI(openAPI, params, cookies, headers);
			}

			@Override
			public Optional<Operation> filterOperation(
				Operation operation, ApiDescription apiDescription,
				Map<String, List<String>> params, Map<String, String> cookies,
				Map<String, List<String>> headers) {

				String operationId = operation.getOperationId();

				Set<String> excludedOperationIds =
					ConfigurationUtil.getExcludedOperationIds(
						_configurationAdmin,
						openAPISchemaFilter.getApplicationPath());

				if (excludedOperationIds.contains(operationId)) {
					return Optional.empty();
				}

				for (Map.Entry<String, String> entry :
						schemaMappings.entrySet()) {

					operationId = StringUtil.replace(
						operationId, TextFormatter.formatPlural(entry.getKey()),
						TextFormatter.formatPlural(entry.getValue()));

					operationId = StringUtil.replace(
						operationId, entry.getKey(), entry.getValue());
				}

				operation.setOperationId(operationId);

				List<String> tags = operation.getTags();

				if (tags != null) {
					List<String> newTags = new ArrayList<>(tags);

					for (Map.Entry<String, String> entry :
							schemaMappings.entrySet()) {

						for (int i = 0; i < newTags.size(); i++) {
							if (Objects.equals(
									entry.getKey(), newTags.get(i))) {

								newTags.set(i, entry.getValue());
							}
						}
					}

					operation.setTags(newTags);
				}

				return super.filterOperation(
					operation, apiDescription, params, cookies, headers);
			}

			@Override
			public Optional<Parameter> filterParameter(
				Parameter parameter, Operation operation,
				ApiDescription apiDescription, Map<String, List<String>> params,
				Map<String, String> cookies,
				Map<String, List<String>> headers) {

				for (Map.Entry<String, String> entry :
						schemaMappings.entrySet()) {

					String parameterName = parameter.getName();

					String schemaName = StringUtil.lowerCaseFirstLetter(
						entry.getKey());

					if (parameterName.contains(schemaName)) {
						parameter.setName(
							StringUtil.replace(
								parameterName, schemaName,
								StringUtil.lowerCaseFirstLetter(
									entry.getValue())));
					}
				}

				return super.filterParameter(
					parameter, operation, apiDescription, params, cookies,
					headers);
			}

			@Override
			public Optional<RequestBody> filterRequestBody(
				RequestBody requestBody, Operation operation,
				ApiDescription apiDescription, Map<String, List<String>> params,
				Map<String, String> cookies,
				Map<String, List<String>> headers) {

				_replaceContentReference(requestBody.getContent());

				return super.filterRequestBody(
					requestBody, operation, apiDescription, params, cookies,
					headers);
			}

			@Override
			public Optional<ApiResponse> filterResponse(
				ApiResponse response, Operation operation,
				ApiDescription apiDescription, Map<String, List<String>> params,
				Map<String, String> cookies,
				Map<String, List<String>> headers) {

				_replaceContentReference(response.getContent());

				return super.filterResponse(
					response, operation, apiDescription, params, cookies,
					headers);
			}

			@Override
			public Optional<Schema> filterSchema(
				Schema schema, Map<String, List<String>> params,
				Map<String, String> cookies,
				Map<String, List<String>> headers) {

				if (schemaMappings.containsKey(schema.getName())) {
					StringSchema stringSchema = new StringSchema();

					stringSchema.readOnly(true);
					stringSchema.setDefault(
						schemaMappings.get(schema.getName()));

					schema.addProperties("x-schema-name", stringSchema);
				}

				for (DTOProperty dtoProperty : dtoProperties) {
					if (!StringUtil.equals(
							dtoProperty.getName(), schema.getName())) {

						continue;
					}

					for (DTOProperty childDTOProperty :
							dtoProperty.getDTOProperties()) {

						schema.addProperties(
							childDTOProperty.getName(),
							_addSchema(childDTOProperty));

						if (childDTOProperty.isRequired()) {
							schema.addRequiredItem(childDTOProperty.getName());
						}
					}

					return Optional.of(schema);
				}

				return super.filterSchema(schema, params, cookies, headers);
			}

			@Override
			public Optional<Schema> filterSchemaProperty(
				Schema propertySchema, Schema schema, String propName,
				Map<String, List<String>> params, Map<String, String> cookies,
				Map<String, List<String>> headers) {

				for (Map.Entry<String, String> entry :
						schemaMappings.entrySet()) {

					_replaceReference(entry, propertySchema);

					if (propertySchema instanceof ArraySchema) {
						ArraySchema arraySchema = (ArraySchema)propertySchema;

						_replaceReference(entry, arraySchema.getItems());
					}
				}

				return super.filterSchemaProperty(
					propertySchema, schema, propName, params, cookies, headers);
			}

			private Schema<Object> _addSchema(DTOProperty dtoProperty) {
				String type = dtoProperty.getType();

				if (type.equals("Array")) {
					ArraySchema arraySchema = new ArraySchema();

					arraySchema.setDescription(dtoProperty.getDescription());
					arraySchema.setExtensions(dtoProperty.getExtensions());
					arraySchema.setName(dtoProperty.getName());
					arraySchema.setType("array");

					List<DTOProperty> dtoProperties =
						dtoProperty.getDTOProperties();

					if (ListUtil.isNotEmpty(dtoProperties)) {
						DTOProperty childDTOProperty = dtoProperties.get(0);

						String childType = childDTOProperty.getType();

						if (childType.equals("Object")) {
							arraySchema.setItems(
								new Schema() {
									{
										set$ref(
											"#/components/schemas/" +
												childDTOProperty.getName());
									}
								});
						}
						else {
							arraySchema.setItems(_addSchema(childDTOProperty));
						}
					}

					return arraySchema;
				}

				Schema<Object> schema = new Schema<>();

				schema.setDescription(dtoProperty.getDescription());
				schema.setExtensions(dtoProperty.getExtensions());
				schema.setName(dtoProperty.getName());

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
				else if (type.equals("Float")) {
					schema.setFormat("float");
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
				else if (type.equals("Object")) {
					schema.setType("object");

					for (DTOProperty childDTOProperty :
							dtoProperty.getDTOProperties()) {

						if (StringUtil.equals(
								childDTOProperty.getType(), "Object")) {

							schema.set$ref(
								"#/components/schemas/" +
									childDTOProperty.getName());
						}
						else {
							schema.addProperties(
								childDTOProperty.getName(),
								_addSchema(childDTOProperty));

							if (childDTOProperty.isRequired()) {
								schema.addRequiredItem(
									childDTOProperty.getName());
							}
						}
					}
				}
				else if (type.equals("String")) {
					schema.setType("string");
				}
				else {
					schema.setType("object");
				}

				return schema;
			}

			private void _fixNewSchemaNames(
				Map<DTOProperty, Schema> newSchemas,
				Map<String, Schema> schemas) {

				for (Map.Entry<DTOProperty, Schema> entry :
						newSchemas.entrySet()) {

					Schema schema = entry.getValue();

					if (schemas.containsKey(schema.getName())) {
						String newName = "C." + schema.getName();

						schema.setName(newName);

						DTOProperty dtoProperty = entry.getKey();

						dtoProperty.setName(newName);
					}
				}
			}

			private Map<DTOProperty, Schema> _getNewSchemas(
				DTOProperty dtoProperty) {

				Map<DTOProperty, Schema> schemas = new HashMap<>();

				if (StringUtil.equals(dtoProperty.getType(), "Array") ||
					StringUtil.equals(dtoProperty.getType(), "Object")) {

					for (DTOProperty childDTOProperty1 :
							dtoProperty.getDTOProperties()) {

						if (StringUtil.equals(
								childDTOProperty1.getType(), "Object")) {

							Schema schema = new Schema();

							schema.setDescription(
								childDTOProperty1.getDescription());
							schema.setExtensions(
								childDTOProperty1.getExtensions());
							schema.setName(childDTOProperty1.getName());
							schema.setType("object");

							for (DTOProperty childDTOProperty2 :
									childDTOProperty1.getDTOProperties()) {

								schema.addProperties(
									childDTOProperty2.getName(),
									_addSchema(childDTOProperty2));

								if (childDTOProperty2.isRequired()) {
									schema.addRequiredItem(
										childDTOProperty2.getName());
								}

								schemas.putAll(
									_getNewSchemas(childDTOProperty2));
							}

							schemas.put(childDTOProperty1, schema);
						}
					}
				}
				else {
					for (DTOProperty childDTOProperty :
							dtoProperty.getDTOProperties()) {

						schemas.putAll(_getNewSchemas(childDTOProperty));
					}
				}

				return schemas;
			}

			private void _replaceContentReference(Content content) {
				if (content == null) {
					return;
				}

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

			private void _replaceParameters(String key, Paths paths) {
				String parameterName = StringUtil.lowerCaseFirstLetter(key);

				for (String path : new ArrayList<>(paths.keySet())) {
					if (!path.contains(parameterName)) {
						continue;
					}

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

			private void _replaceReference(
				Map.Entry<String, String> entry, Schema schema) {

				String ref = schema.get$ref();

				if ((ref == null) || !ref.contains(entry.getKey())) {
					return;
				}

				schema.set$ref(
					StringUtil.replace(ref, entry.getKey(), entry.getValue()));
			}

		};
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ExtensionProviderRegistry _extensionProviderRegistry;

	@Reference
	private JaxRsResourceRegistry _jaxRsResourceRegistry;

	@Reference
	private Portal _portal;

	private ServiceTrackerList<OpenAPIContributor> _trackedOpenAPIContributors;

}