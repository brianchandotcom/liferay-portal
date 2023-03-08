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

package com.liferay.object.rest.internal.util;

import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectField;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author Luis Miguel Barcos
 */
public class ObjectFieldTestUtil {

	public static List<ObjectField> createDefaultObjectFieldList(
		String objectFieldName) {

		return Collections.singletonList(
			ObjectFieldUtil.createObjectField(
				"Text", "String", true, true, null,
				RandomTestUtil.randomString(), objectFieldName, false));
	}

}