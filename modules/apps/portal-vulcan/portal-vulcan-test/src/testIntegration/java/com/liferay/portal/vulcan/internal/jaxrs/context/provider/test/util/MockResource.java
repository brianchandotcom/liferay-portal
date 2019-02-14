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

package com.liferay.portal.vulcan.internal.jaxrs.context.provider.test.util;

import com.liferay.portal.odata.entity.EntityField;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.odata.entity.StringEntityField;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author Cristina González
 */
public class MockResource implements EntityModelResource {

	public static final String METHOD_NAME = "mockJaxRSMethod";

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _ENTITY_MODEL;
	}

	@GET
	public String mockJaxRSMethod(String param) {
		return "";
	}

	private static final EntityModel _ENTITY_MODEL = new EntityModel() {

		@Override
		public Map<String, EntityField> getEntityFieldsMap() {
			return Collections.singletonMap(
				"title",
				new StringEntityField("title", locale -> "internalTitle"));
		}

		@Override
		public String getName() {
			return "Example";
		}

	};

}