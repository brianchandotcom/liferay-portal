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

package com.liferay.object.rest.openapi.v1_0;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.vulcan.batch.engine.Field;

import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * @author Luis Miguel Barcos
 */
public interface ObjectEntryOpenAPIResource {

	public Map<String, Field> getFields(
			ObjectDefinition objectDefinition, UriInfo uriInfo)
		throws Exception;

	public Response getOpenAPI(
			ObjectDefinition objectDefinition, String type, UriInfo uriInfo)
		throws Exception;

}