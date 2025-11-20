/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.petra.sql.dsl;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.relationship.util.ObjectRelationshipUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DynamicObjectRelationshipMappingTableFactory {

	public static DynamicObjectRelationshipMappingTable create(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2, String dbTableName)
		throws PortalException {

		return create(objectDefinition1, objectDefinition2, dbTableName, false);
	}

	public static DynamicObjectRelationshipMappingTable create(
			ObjectDefinition objectDefinition1,
			ObjectDefinition objectDefinition2, String dbTableName,
			boolean reverse)
		throws PortalException {

		Map<String, String> pkObjectFieldDBColumnNames =
			ObjectRelationshipUtil.getPKObjectFieldDBColumnNames(
				objectDefinition1, objectDefinition2, reverse);

		String pkObjectFieldDBColumnName1 = pkObjectFieldDBColumnNames.get(
			"pkObjectFieldDBColumnName1");
		String pkObjectFieldDBColumnName2 = pkObjectFieldDBColumnNames.get(
			"pkObjectFieldDBColumnName2");

		return new DynamicObjectRelationshipMappingTable(
			pkObjectFieldDBColumnName1, pkObjectFieldDBColumnName2,
			dbTableName);
	}

	public static DynamicObjectRelationshipMappingTable create(
			ObjectRelationship objectRelationship)
		throws PortalException {

		ObjectDefinitionLocalService objectDefinitionLocalService =
			ObjectDefinitionLocalServiceUtil.getService();

		ObjectDefinition objectDefinition1 =
			objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId1());
		ObjectDefinition objectDefinition2 =
			objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId2());

		return create(
			objectDefinition1, objectDefinition2,
			objectRelationship.getDBTableName(), false);
	}

}