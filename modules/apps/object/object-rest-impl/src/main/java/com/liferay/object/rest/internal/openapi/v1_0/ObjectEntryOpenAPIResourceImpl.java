/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.openapi.v1_0;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.FileEntry;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryRelatedObjectsResourceImpl;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryResourceImpl;
import com.liferay.object.rest.internal.resource.v1_0.OpenAPIResourceImpl;
import com.liferay.object.rest.internal.vulcan.openapi.contributor.ObjectEntryOpenAPIContributor;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResource;
import com.liferay.object.rest.openapi.v1_0.ObjectEntryOpenAPIResourceProvider;
import com.liferay.object.service.ObjectActionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.vulcan.batch.engine.Field;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.openapi.DTOProperty;
import com.liferay.portal.vulcan.openapi.OpenAPISchemaFilter;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.osgi.framework.BundleContext;

/**
 * @author Luis Miguel Barcos
 */
public class ObjectEntryOpenAPIResourceImpl
	implements ObjectEntryOpenAPIResource {

	public ObjectEntryOpenAPIResourceImpl(
		BundleContext bundleContext, DTOConverterRegistry dtoConverterRegistry,
		ObjectActionLocalService objectActionLocalService,
		ObjectDefinition objectDefinition,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryOpenAPIResourceProvider objectEntryOpenAPIResourceProvider,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		OpenAPIResource openAPIResource,
		SystemObjectDefinitionManagerRegistry
			systemObjectDefinitionManagerRegistry) {

		_bundleContext = bundleContext;
		_dtoConverterRegistry = dtoConverterRegistry;
		_objectActionLocalService = objectActionLocalService;
		_objectDefinition = objectDefinition;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryOpenAPIResourceProvider =
			objectEntryOpenAPIResourceProvider;
		_objectFieldLocalService = objectFieldLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_openAPIResource = openAPIResource;
		_systemObjectDefinitionManagerRegistry =
			systemObjectDefinitionManagerRegistry;
	}

	@Override
	public Map<String, Field> getFields(UriInfo uriInfo) throws Exception {
		Response response = getOpenAPI(null, "json", uriInfo);

		Schema schema = _getObjectDefinitionSchema(
			(OpenAPI)response.getEntity());

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

			fields.put(
				propertyName,
				Field.of(
					propertySchema.getDescription(), propertyName,
					GetterUtil.getBoolean(propertySchema.getReadOnly()),
					_getRef(propertySchema),
					requiredPropertySchemaNames.contains(propertyName),
					propertySchema.getType(),
					GetterUtil.getBoolean(propertySchema.getWriteOnly())));
		}

		return fields;
	}

	@Override
	public Response getOpenAPI(
			HttpServletRequest httpServletRequest, String type, UriInfo uriInfo)
		throws Exception {

		return _getOpenAPI(true, type, uriInfo);
	}

	@Override
	public Map<String, Schema> getSchemas() throws Exception {
		Response response = _getOpenAPI(false, "json", null);

		OpenAPI openAPI = (OpenAPI)response.getEntity();

		Components components = openAPI.getComponents();

		return components.getSchemas();
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
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_DATE) &&
				 _fieldNameMappings.containsKey(objectField.getName())) {

			return new DTOProperty(
				null, _fieldNameMappings.get(objectField.getName()),
				ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME) {

				{
					setRequired(objectField.isRequired());
				}
			};
		}
		else if (Objects.equals(
					objectField.getBusinessType(),
					ObjectFieldConstants.BUSINESS_TYPE_DATE_TIME)) {

			return new DTOProperty(
				HashMapBuilder.<String, Object>put(
					"x-parent-map", "properties"
				).put(
					"x-timeStorage",
					ObjectFieldSettingUtil.getValue(
						ObjectFieldSettingConstants.NAME_TIME_STORAGE,
						objectField)
				).build(),
				objectField.getName(), objectField.getDBType()) {

				{
					setRequired(objectField.isRequired());
				}
			};
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

	private Schema _getObjectDefinitionSchema(OpenAPI openAPI) {
		Components components = openAPI.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		return schemas.get(_objectDefinition.getShortName());
	}

	private Response _getOpenAPI(
			boolean addRelatedSchemas, String type, UriInfo uriInfo)
		throws Exception {

		return _setReadOnly(
			_openAPIResource.getOpenAPI(
				new ObjectEntryOpenAPIContributor(
					addRelatedSchemas, _bundleContext, _dtoConverterRegistry,
					_objectActionLocalService, _objectDefinition,
					_objectDefinitionLocalService, this,
					_objectEntryOpenAPIResourceProvider,
					_objectRelationshipLocalService, _openAPIResource,
					_systemObjectDefinitionManagerRegistry),
				_getOpenAPISchemaFilter(_objectDefinition),
				new HashSet<Class<?>>() {
					{
						add(ObjectEntryRelatedObjectsResourceImpl.class);
						add(ObjectEntryResourceImpl.class);
						add(OpenAPIResourceImpl.class);
					}
				},
				type, uriInfo));
	}

	private OpenAPISchemaFilter _getOpenAPISchemaFilter(
		ObjectDefinition objectDefinition) {

		OpenAPISchemaFilter openAPISchemaFilter = new OpenAPISchemaFilter();

		openAPISchemaFilter.setApplicationPath(
			objectDefinition.getRESTContextPath());

		DTOProperty dtoProperty = new DTOProperty(
			new HashMap<>(), "ObjectEntry", "Object");

		List<DTOProperty> dtoProperties = new ArrayList<>();

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(
					objectDefinition.getObjectDefinitionId())) {

			dtoProperties.add(_getDTOProperty(objectField));

			if (objectField.isLocalized() &&
				FeatureFlagManagerUtil.isEnabled("LPS-172017")) {

				dtoProperties.add(
					new DTOProperty(
						Collections.singletonMap("x-parent-map", "properties"),
						objectField.getI18nObjectFieldName(),
						Map.class.getSimpleName()) {

						{
							setRequired(objectField.isRequired());
						}
					});
			}

			if (Objects.equals(
					objectField.getRelationshipType(),
					ObjectRelationshipConstants.TYPE_ONE_TO_MANY)) {

				ObjectRelationship objectRelationship =
					_objectRelationshipLocalService.
						fetchObjectRelationshipByObjectFieldId2(
							objectField.getObjectFieldId());

				dtoProperties.add(
					new DTOProperty(
						Collections.singletonMap("x-parent-map", "properties"),
						objectRelationship.getName(),
						String.class.getSimpleName()) {

						{
							setRequired(objectField.isRequired());
						}
					});

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
				"ObjectEntry", objectDefinition.getShortName()
			).put(
				"PageObject", "Page" + objectDefinition.getShortName()
			).put(
				"PageObjectEntry", "Page" + objectDefinition.getShortName()
			).build());

		return openAPISchemaFilter;
	}

	private String _getRef(Schema schema) {
		if (schema instanceof ArraySchema) {
			Schema itemsSchema = ((ArraySchema)schema).getItems();

			return itemsSchema.get$ref();
		}

		return schema.get$ref();
	}

	private List<String> _getRequiredPropertySchemaNames(Schema schema) {
		List<String> requiredPropertySchemaNames = schema.getRequired();

		if (requiredPropertySchemaNames == null) {
			requiredPropertySchemaNames = Collections.emptyList();
		}

		return requiredPropertySchemaNames;
	}

	private Response _setReadOnly(Response response) {
		Map<String, ObjectField> objectFields =
			ObjectFieldUtil.toObjectFieldsMap(
				_objectFieldLocalService.getObjectFields(
					_objectDefinition.getObjectDefinitionId()));

		Schema schema = _getObjectDefinitionSchema(
			(OpenAPI)response.getEntity());

		Map<String, Schema> properties = schema.getProperties();

		for (Map.Entry<String, Schema> entry : properties.entrySet()) {
			String key = entry.getKey();

			schema = entry.getValue();

			if (_readOnlyFieldNames.contains(key)) {
				schema.readOnly(true);

				continue;
			}

			ObjectField objectField = objectFields.get(key);

			if (objectField == null) {
				continue;
			}

			if (Objects.equals(
					objectField.getReadOnly(),
					ObjectFieldConstants.READ_ONLY_CONDITIONAL) ||
				Objects.equals(
					objectField.getReadOnly(),
					ObjectFieldConstants.READ_ONLY_FALSE)) {

				schema.readOnly(false);

				continue;
			}

			schema.readOnly(true);
		}

		return response;
	}

	private final BundleContext _bundleContext;
	private final DTOConverterRegistry _dtoConverterRegistry;
	private final Map<String, String> _fieldNameMappings = HashMapBuilder.put(
		"createDate", "dateCreated"
	).put(
		"modifiedDate", "dateModified"
	).build();
	private final ObjectActionLocalService _objectActionLocalService;
	private final ObjectDefinition _objectDefinition;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectEntryOpenAPIResourceProvider
		_objectEntryOpenAPIResourceProvider;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final OpenAPIResource _openAPIResource;
	private final Set<String> _readOnlyFieldNames = SetUtil.fromArray(
		"dateCreated", "dateModified", "id");
	private final SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

}