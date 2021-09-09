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

import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.internal.jaxrs.container.request.filter.ObjectDefinitionIdContainerRequestFilter;
import com.liferay.object.rest.internal.resource.v1_0.ObjectEntryResourceImpl;
import com.liferay.object.rest.internal.resource.v1_0.OpenAPIResourceImpl;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.openapi.DTOProperty;
import com.liferay.portal.vulcan.openapi.OpenAPISchemaFilter;
import com.liferay.portal.vulcan.resource.OpenAPIResource;

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
				_applicationName, _objectDefinitionId));
		objects.add(
			new OpenAPIResourceImpl(
				_openAPIResource, _getOpenAPISchemaFilter(_applicationPath),
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

	private OpenAPISchemaFilter _getOpenAPISchemaFilter(
		String applicationPath) {

		OpenAPISchemaFilter openAPISchemaFilter = new OpenAPISchemaFilter();

		openAPISchemaFilter.setApplicationPath(applicationPath);

		DTOProperty dtoProperty = new DTOProperty("ObjectEntry", "object");

		Stream<ObjectField> stream = _objectFields.stream();

		dtoProperty.setDTOProperties(
			stream.map(
				objectField -> new DTOProperty(
					objectField.getName(), objectField.getType())
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
	private String _objectDefinitionName;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	private List<ObjectField> _objectFields;

	@Reference
	private OpenAPIResource _openAPIResource;

}