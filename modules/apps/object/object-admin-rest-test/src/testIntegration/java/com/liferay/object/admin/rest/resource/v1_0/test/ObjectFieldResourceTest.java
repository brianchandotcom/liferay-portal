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

package com.liferay.object.admin.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.admin.rest.client.dto.v1_0.ObjectField;
import com.liferay.portal.kernel.util.HashMapBuilder;

import org.junit.Ignore;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@Ignore
@RunWith(Arquillian.class)
public class ObjectFieldResourceTest extends BaseObjectFieldResourceTestCase {

	@Override
	protected ObjectField randomObjectField() throws Exception {
		ObjectField objectField = super.randomObjectField();

		objectField.setLabel(
			HashMapBuilder.put(
				"en_US", "A" + objectField.getName()
			).build());

		return objectField;
	}

}