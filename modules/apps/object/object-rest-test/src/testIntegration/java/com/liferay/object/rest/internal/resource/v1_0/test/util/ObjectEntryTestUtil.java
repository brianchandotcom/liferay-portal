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

package com.liferay.object.rest.internal.resource.v1_0.test.util;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

/**
 * @author Luis Miguel Barcos
 */
public class ObjectEntryTestUtil {

	public static ObjectEntry addObjectEntry(
			ObjectDefinition objectDefinition, String objectFieldName,
			String objectFieldValue)
		throws Exception {

		return ObjectEntryLocalServiceUtil.addObjectEntry(
			TestPropsValues.getUserId(), 0,
			objectDefinition.getObjectDefinitionId(),
			HashMapBuilder.<String, Serializable>put(
				objectFieldName, objectFieldValue
			).build(),
			ServiceContextTestUtil.getServiceContext());
	}

}