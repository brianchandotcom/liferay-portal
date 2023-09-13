/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.test.util;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.List;

/**
 * @author Luis Miguel Barcos
 */
public class ObjectDefinitionTestUtil {

	public static ObjectDefinition addCustomObjectDefinition(
			List<ObjectField> objectFields)
		throws Exception {

		return ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
			TestPropsValues.getUserId(), 0, false, false, false,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			"A" + RandomTestUtil.randomString(), null, null,
			LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
			true, ObjectDefinitionConstants.SCOPE_COMPANY,
			ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT, objectFields);
	}

	public static ObjectDefinition publishObjectDefinition(
			List<ObjectField> objectFields)
		throws Exception {

		return publishObjectDefinition(
			objectFields, ObjectDefinitionConstants.SCOPE_COMPANY);
	}

	public static ObjectDefinition publishObjectDefinition(
			List<ObjectField> objectFields, String scope)
		throws Exception {

		return publishObjectDefinition(
			objectFields, scope, TestPropsValues.getUserId());
	}

	public static ObjectDefinition publishObjectDefinition(
			List<ObjectField> objectFields, String scope, long userId)
		throws Exception {

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.addCustomObjectDefinition(
				userId, 0, false, false, false,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				"A" + RandomTestUtil.randomString(), null, null,
				LocalizedMapUtil.getLocalizedMap(RandomTestUtil.randomString()),
				true, scope, ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT,
				objectFields);

		return ObjectDefinitionLocalServiceUtil.publishCustomObjectDefinition(
			userId, objectDefinition.getObjectDefinitionId());
	}

}