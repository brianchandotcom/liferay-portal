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

package com.liferay.object.service.test.util;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.util.LocalizedMapUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;

import java.util.List;

/**
 * @author Guilherme Camacho
 */
public class ObjectDefinitionTestUtil {

	public static ObjectDefinition addObjectDefinition(
			ObjectDefinitionLocalService objectDefinitionLocalService)
		throws Exception {

		return addObjectDefinition(objectDefinitionLocalService, null);
	}

	public static ObjectDefinition addObjectDefinition(
			ObjectDefinitionLocalService objectDefinitionLocalService,
			List<ObjectField> objectFields)
		throws Exception {

		return objectDefinitionLocalService.addCustomObjectDefinition(
			TestPropsValues.getUserId(),
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"A" + RandomTestUtil.randomString(), null, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			ObjectDefinitionConstants.SCOPE_COMPANY,
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT, objectFields);
	}

}