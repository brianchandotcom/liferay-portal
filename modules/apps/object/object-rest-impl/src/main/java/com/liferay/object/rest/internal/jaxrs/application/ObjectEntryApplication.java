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

import com.liferay.object.rest.internal.jaxrs.container.request.filter.ObjectDefinitionIdContainerRequestFilter;
import com.liferay.object.rest.internal.jaxrs.context.provider.ObjectDefinitionContextProvider;
import com.liferay.object.rest.internal.resource.v1_0.OpenAPIResourceImpl;
import com.liferay.object.rest.resource.v1_0.ObjectEntryResource;
import com.liferay.object.service.ObjectDefinitionLocalService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

		objects.add(_objectEntryResource);
		objects.add(_openAPIResourceImpl);
		objects.add(
			new ObjectDefinitionContextProvider(
				_objectDefinitionLocalService.fetchObjectDefinition(
					_objectDefinitionId)));
		objects.add(
			new ObjectDefinitionIdContainerRequestFilter(_objectDefinitionId));

		return objects;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_objectDefinitionId = (Long)properties.get("objectDefinitionId");
	}

	private Long _objectDefinitionId;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryResource _objectEntryResource;

	@Reference
	private OpenAPIResourceImpl _openAPIResourceImpl;

}