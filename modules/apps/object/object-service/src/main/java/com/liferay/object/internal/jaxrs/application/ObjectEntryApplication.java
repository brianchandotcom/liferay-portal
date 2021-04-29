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

package com.liferay.object.internal.jaxrs.application;

import com.liferay.object.internal.resource.OpenAPIResourceImpl;
import com.liferay.object.resource.ObjectEntryResource;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * @author Javier de Arcos
 */
public class ObjectEntryApplication extends Application {

	public ObjectEntryApplication(
		ObjectEntryResource objectEntryResource,
		OpenAPIResourceImpl openAPIResourceImpl) {

		_objectEntryResource = objectEntryResource;
		_openAPIResourceImpl = openAPIResourceImpl;
	}

	@Override
	public Set<Object> getSingletons() {
		HashSet<Object> objects = new HashSet<>();

		objects.add(_objectEntryResource);
		objects.add(_openAPIResourceImpl);

		return objects;
	}

	private final ObjectEntryResource _objectEntryResource;
	private final OpenAPIResourceImpl _openAPIResourceImpl;

}