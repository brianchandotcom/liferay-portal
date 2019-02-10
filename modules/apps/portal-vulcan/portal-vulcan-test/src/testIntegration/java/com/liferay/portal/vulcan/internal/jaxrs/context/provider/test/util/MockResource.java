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

import java.util.Collections;
import java.util.Map;

/**
 * @author Cristina González
 */
public class MockResource {

	public static final EntityModel ENTITY_MODEL = new EntityModel() {

		@Override
		public Map<String, EntityField> getEntityFieldsMap() {
			return Collections.singletonMap(
				"title",
				new StringEntityField("title", locale -> "internalTitle"));
		}

		@Override
		public String getName() {
			return ODATA_ENTITY_MODEL_NAME;
		}

	};

	public static final String METHOD_NAME = "exampleJaxRSMethod";

	public static final String ODATA_ENTITY_MODEL_NAME =
		"ExampleResourceEntityModel";

	public String exampleJaxRSMethod(String param) {
		return "";
	}

}