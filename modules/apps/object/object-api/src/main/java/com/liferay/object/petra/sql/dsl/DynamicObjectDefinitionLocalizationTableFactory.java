/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.petra.sql.dsl;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author Feliphe Marinho
 */
public class DynamicObjectDefinitionLocalizationTableFactory {

	public static DynamicObjectDefinitionLocalizationTable create(
		ObjectDefinition objectDefinition, List<ObjectField> objectFields) {

		return _create(objectDefinition, objectFields);
	}

	public static DynamicObjectDefinitionLocalizationTable create(
		ObjectDefinition objectDefinition,
		ObjectFieldLocalService objectFieldLocalService) {

		return _create(
			objectDefinition,
			objectFieldLocalService.getLocalizedObjectFields(
				objectDefinition.getObjectDefinitionId()));
	}

	private static DynamicObjectDefinitionLocalizationTable _create(
		ObjectDefinition objectDefinition, List<ObjectField> objectFields) {

		List<ObjectField> localizedObjectFields = ListUtil.filter(
			objectFields, ObjectField::isLocalized);

		if (objectDefinition.isUnmodifiableSystemObject()) {
			localizedObjectFields = ListUtil.filter(
				objectFields, Predicate.not(ObjectField::isSystem));
		}

		if (localizedObjectFields.isEmpty()) {
			return null;
		}

		return new DynamicObjectDefinitionLocalizationTable(
			objectDefinition, localizedObjectFields);
	}

}