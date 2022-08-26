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

package com.liferay.object.rest.internal.resource.v1_0;

import com.liferay.object.rest.internal.helper.ObjectRelationshipsResourceHelper;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luis Miguel Barcos
 */
@Component(
	factory = "com.liferay.object.rest.internal.resource.v1_0.ObjectRelationshipsResource",
	property = {"api.version=v1.0", "osgi.jaxrs.resource=true"},
	service = ObjectRelationshipsResourceImpl.class
)
public class ObjectRelationshipsResourceImpl
	extends BaseObjectRelationshipsResourceImpl {

	@Override
	public Page<Object> getObjectRelatedObjectsPage(
			String previousPath, Long objectEntryId,
			String objectRelationshipName, Pagination pagination)
		throws Exception {

		return _objectRelationshipsResourceHelper.getObjectRelatedObjectsPage(
			objectEntryId, objectRelationshipName, pagination, _uriInfo);
	}

	@Reference
	private ObjectRelationshipsResourceHelper
		_objectRelationshipsResourceHelper;

	@Context
	private UriInfo _uriInfo;

}