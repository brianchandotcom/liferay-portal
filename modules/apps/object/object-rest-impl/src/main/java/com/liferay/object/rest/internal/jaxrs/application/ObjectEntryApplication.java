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

package com.liferay.object.rest.internal.jaxrs.application;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.internal.jaxrs.container.request.filter.ObjectDefinitionIdContainerRequestFilter;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryResourceImpl;
import com.liferay.object.rest.internal.resource.v1_0.OpenAPIResourceImpl;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.openapi.DTOProperty;
import com.liferay.portal.vulcan.openapi.OpenAPISchemaFilter;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.Application;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(
	factory = "com.liferay.object.internal.jaxrs.application.ObjectEntryApplication",
	service = Application.class
)
public class ObjectEntryApplication extends Application {

	@Override
	public Set<Object> getSingletons() {
		Set<Object> objects = new HashSet<>();

		objects.add(
			new ObjectDefinitionIdContainerRequestFilter(
				_objectDefinitionId, _objectDefinitionName));
		objects.add(
			new OpenAPIResourceImpl(
				_objectDefinitionLocalService.fetchObjectDefinition(
					_objectDefinitionId),
				_getObjectRelationshipsRelatedObjects(), _openAPIResource,
				_getOpenAPISchemaFilter(_applicationPath),
				new HashSet<Class<?>>() {
					{
						add(ObjectEntryResourceImpl.class);
						add(OpenAPIResourceImpl.class);
					}
				}));

		return objects;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_applicationName = (String)properties.get("osgi.jaxrs.name");
		_applicationPath = (String)properties.get(
			"osgi.jaxrs.application.base");
		_objectDefinitionName = (String)properties.get(
			"liferay.object.definition.name");

		_objectDefinitionId = (Long)properties.get(
			"liferay.object.definition.id");

		_objectFields = _objectFieldLocalService.getObjectFields(
			_objectDefinitionId);
	}

	private DTOProperty _getDTOProperty(ObjectField objectField) {
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

			return dtoProperty;
		}

		return new DTOProperty(
			Collections.singletonMap("x-parent-map", "properties"),
			objectField.getName(), objectField.getDBType());
	}

	private Map<ObjectRelationship, ObjectDefinition>
		_getObjectRelationshipsRelatedObjects() {

		Map<ObjectRelationship, ObjectDefinition>
			objectRelationshipObjectDefinitionMap = new HashMap<>();
		List<ObjectRelationship> objectRelationships =
			_objectRelationshipLocalService.getObjectRelationships(
				_objectDefinitionId);

		for (ObjectRelationship objectRelationship : objectRelationships) {
			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinition(
					objectRelationship.getObjectDefinitionId2());

			if (objectDefinition != null) {
				objectRelationshipObjectDefinitionMap.put(
					objectRelationship, objectDefinition);
			}
		}

		return objectRelationshipObjectDefinitionMap;
	}

	private OpenAPISchemaFilter _getOpenAPISchemaFilter(
		String applicationPath) {

		OpenAPISchemaFilter openAPISchemaFilter = new OpenAPISchemaFilter();

		openAPISchemaFilter.setApplicationPath(applicationPath);

		DTOProperty dtoProperty = new DTOProperty(
			new HashMap<>(), "ObjectEntry", "object");

		Stream<ObjectField> stream = _objectFields.stream();

		dtoProperty.setDTOProperties(
			stream.map(
				objectField -> _getDTOProperty(objectField)
			).collect(
				Collectors.toList()
			));

		openAPISchemaFilter.setDTOProperty(dtoProperty);
		openAPISchemaFilter.setSchemaMappings(
			HashMapBuilder.put(
				"ObjectEntry", _objectDefinitionName
			).put(
				"PageObjectEntry", "Page" + _objectDefinitionName
			).build());

		return openAPISchemaFilter;
	}

	private String _applicationName;
	private String _applicationPath;
	private Long _objectDefinitionId;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private String _objectDefinitionName;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	private List<ObjectField> _objectFields;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private OpenAPIResource _openAPIResource;

}