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

package com.liferay.object.rest.internal.openapi.v1_0;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.FileEntry;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryRelatedObjectsResourceImpl;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryResourceImpl;
import com.liferay.object.rest.internal.resource.v1_0.OpenAPIResourceImpl;
import com.liferay.object.rest.internal.vulcan.openapi.contributor.ObjectEntryOpenAPIContributor;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResource;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionMetadataRegistry;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.vulcan.batch.engine.Field;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.openapi.DTOProperty;
import com.liferay.portal.vulcan.openapi.OpenAPISchemaFilter;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(service = ObjectEntryOpenAPIResource.class)
public class ObjectEntryOpenAPIResourceImpl
	implements ObjectEntryOpenAPIResource {

	@Override
	public Map<String, Field> getFields(
			ObjectDefinition objectDefinition, UriInfo uriInfo)
		throws Exception {

		Response response = getOpenAPI(objectDefinition, "json", uriInfo);

		OpenAPI openAPI = (OpenAPI)response.getEntity();

		Components components = openAPI.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		Schema schema = schemas.get(_objectDefinition.getShortName());

		if (schema == null) {
			return Collections.emptyMap();
		}

		Map<String, Field> fields = new HashMap<>();

		List<String> requiredPropertySchemaNames =
			_getRequiredPropertySchemaNames(schema);

		Map<String, Schema> properties = schema.getProperties();

		for (Map.Entry<String, Schema> schemaEntry : properties.entrySet()) {
			String propertyName = schemaEntry.getKey();
			Schema propertySchema = schemaEntry.getValue();

			if (Optional.ofNullable(
					propertySchema.getReadOnly()
				).orElse(
					false
				) ||
				Optional.ofNullable(
					propertySchema.getWriteOnly()
				).orElse(
					false
				) || propertyName.startsWith("x-")) {

				continue;
			}

			fields.put(
				propertyName,
				Field.of(
					propertySchema.getDescription(), propertyName,
					Optional.ofNullable(
						propertySchema.getReadOnly()
					).orElse(
						false
					),
					requiredPropertySchemaNames.contains(propertyName),
					propertySchema.getType(),
					Optional.ofNullable(
						propertySchema.getWriteOnly()
					).orElse(
						false
					)));
		}

		return fields;
	}

	@Override
	public Response getOpenAPI(
			ObjectDefinition objectDefinition, String type, UriInfo uriInfo)
		throws Exception {

		_objectDefinition = objectDefinition;

		return _openAPIResource.getOpenAPI(
			new ObjectEntryOpenAPIContributor(
				_bundleContext, _dtoConverterRegistry,
				_objectActionLocalService, _objectDefinition,
				_objectDefinitionLocalService, this,
				_objectRelationshipLocalService, _openAPIResource,
				_systemObjectDefinitionMetadataRegistry),
			_getOpenAPISchemaFilter(_objectDefinition.getRESTContextPath()),
			new HashSet<Class<?>>() {
				{
					add(ObjectEntryRelatedObjectsResourceImpl.class);
					add(ObjectEntryResourceImpl.class);
					add(OpenAPIResourceImpl.class);
				}
			},
			type, uriInfo);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	private DTOProperty _getDTOProperty(ObjectField objectField) {
		if (Objects.equals(
				objectField.getBusinessType(),
				ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT)) {

			DTOProperty dtoProperty = new DTOProperty(
				Collections.singletonMap("x-parent-map", "properties"),
				objectField.getName(), FileEntry.class.getSimpleName());

			dtoProperty.setDTOProperties(
				Arrays.asList(
					new DTOProperty(
						Collections.singletonMap("x-parent-map", "properties"),
						"id", Long.class.getSimpleName()),
					new DTOProperty(
						Collections.singletonMap("x-parent-map", "properties"),
						"name", String.class.getSimpleName())));
			dtoProperty.setRequired(objectField.isRequired());

			return dtoProperty;
		}

		if (objectField.getListTypeDefinitionId() != 0) {
			DTOProperty dtoProperty = new DTOProperty(
				Collections.singletonMap("x-parent-map", "properties"),
				objectField.getName(), ListEntry.class.getSimpleName());

			dtoProperty.setDTOProperties(
				Arrays.asList(
					new DTOProperty(
						Collections.singletonMap("x-parent-map", "properties"),
						"key", String.class.getSimpleName()),
					new DTOProperty(
						Collections.singletonMap("x-parent-map", "properties"),
						"name", String.class.getSimpleName())));
			dtoProperty.setRequired(objectField.isRequired());

			return dtoProperty;
		}

		return new DTOProperty(
			Collections.singletonMap("x-parent-map", "properties"),
			objectField.getName(), objectField.getDBType()) {

			{
				setRequired(objectField.isRequired());
			}
		};
	}

	private OpenAPISchemaFilter _getOpenAPISchemaFilter(
		String applicationPath) {

		OpenAPISchemaFilter openAPISchemaFilter = new OpenAPISchemaFilter();

		openAPISchemaFilter.setApplicationPath(applicationPath);

		DTOProperty dtoProperty = new DTOProperty(
			new HashMap<>(), "ObjectEntry", "Object");

		List<DTOProperty> dtoProperties = new ArrayList<>();

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(
					_objectDefinition.getObjectDefinitionId())) {

			dtoProperties.add(_getDTOProperty(objectField));

			if (Objects.equals(
					objectField.getRelationshipType(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

				dtoProperties.add(
					new DTOProperty(
						Collections.singletonMap("x-parent-map", "properties"),
						ObjectFieldSettingUtil.getValue(
							ObjectFieldSettingConstants.
								NAME_OBJECT_RELATIONSHIP_ERC_OBJECT_FIELD_NAME,
							objectField),
						String.class.getSimpleName()) {

						{
							setRequired(objectField.isRequired());
						}
					});
			}
		}

		dtoProperty.setDTOProperties(dtoProperties);

		DTOProperty pageDTOProperty = new DTOProperty(
			new HashMap<>(), "PageObject", "Object");

		pageDTOProperty.setDTOProperties(
			Arrays.asList(
				new DTOProperty(new HashMap<>(), "items", "Array") {
					{
						setDTOProperties(
							Arrays.asList(
								new DTOProperty(
									new HashMap<>(), "ObjectEntry", "Object")));
					}
				}));

		openAPISchemaFilter.setDTOProperties(
			Arrays.asList(dtoProperty, pageDTOProperty));

		openAPISchemaFilter.setSchemaMappings(
			TreeMapBuilder.<String, String>create(
				Collections.reverseOrder()
			).put(
				"ObjectEntry", _objectDefinition.getShortName()
			).put(
				"PageObject", "Page" + _objectDefinition.getShortName()
			).put(
				"PageObjectEntry", "Page" + _objectDefinition.getShortName()
			).build());

		return openAPISchemaFilter;
	}

	private List<String> _getRequiredPropertySchemaNames(Schema schema) {
		List<String> requiredPropertySchemaNames = schema.getRequired();

		if (requiredPropertySchemaNames == null) {
			requiredPropertySchemaNames = Collections.emptyList();
		}

		return requiredPropertySchemaNames;
	}

	private BundleContext _bundleContext;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ObjectActionLocalService _objectActionLocalService;

	private ObjectDefinition _objectDefinition;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private OpenAPIResource _openAPIResource;

	@Reference
	private SystemObjectDefinitionMetadataRegistry
		_systemObjectDefinitionMetadataRegistry;

}