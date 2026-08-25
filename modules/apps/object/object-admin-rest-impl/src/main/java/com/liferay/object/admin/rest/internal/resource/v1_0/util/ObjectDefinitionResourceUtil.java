/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.admin.rest.internal.resource.v1_0.util;

import com.liferay.object.admin.rest.dto.v1_0.ObjectDefinition;
import com.liferay.object.admin.rest.dto.v1_0.ObjectField;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public class ObjectDefinitionResourceUtil {

	public static ObjectField[] patchObjectFields(
		ObjectDefinition existingObjectDefinition,
		ObjectDefinition objectDefinition) {

		Map<String, ObjectField> existingObjectFields = new HashMap<>();

		for (ObjectField existingObjectField :
				existingObjectDefinition.getObjectFields()) {

			existingObjectFields.put(
				existingObjectField.getExternalReferenceCode(),
				existingObjectField);
		}

		return TransformUtil.transformToArray(
			ListUtil.fromArray(objectDefinition.getObjectFields()),
			objectField -> ObjectFieldResourceUtil.patchObjectField(
				existingObjectFields.get(
					objectField.getExternalReferenceCode()),
				objectField),
			ObjectField.class);
	}

}