/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.petra.sql.dsl;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;

import java.util.List;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DynamicObjectDefinitionTableFactory {

	public static DynamicObjectDefinitionTable create(
		ObjectDefinition objectDefinition,
		ObjectFieldLocalService objectFieldLocalService) {

		return _create(
			objectDefinition.getDBTableName(), objectDefinition,
			objectFieldLocalService);
	}

	public static DynamicObjectDefinitionTable createExtension(
		ObjectDefinition objectDefinition,
		ObjectFieldLocalService objectFieldLocalService) {

		return _create(
			objectDefinition.getExtensionDBTableName(), objectDefinition,
			objectFieldLocalService);
	}

	private static DynamicObjectDefinitionTable _create(
		String dbTableName, ObjectDefinition objectDefinition,
		ObjectFieldLocalService objectFieldLocalService) {

		List<ObjectField> objectFields =
			objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId(), dbTableName);

		return new DynamicObjectDefinitionTable(
			objectDefinition, objectFields, dbTableName);
	}

}