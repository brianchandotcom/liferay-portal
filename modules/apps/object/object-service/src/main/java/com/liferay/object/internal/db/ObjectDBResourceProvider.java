/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.db;

import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionLocalizationTable;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionLocalizationTableFactory;
import com.liferay.object.relationship.util.ObjectRelationshipUtil;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.db.DBResourceProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mariano Álvaro Sáiz
 */
@Component(service = DBResourceProvider.class)
public class ObjectDBResourceProvider implements DBResourceProvider {

	@Override
	public Map<String, String[]> getTablesPrimaryKeyColumnNames(long companyId)
		throws PortalException {

		Map<String, String[]> tablesPrimaryKeyColumnNames = new HashMap<>();

		List<ObjectDefinition> objectDefinitions =
			_objectDefinitionLocalService.getObjectDefinitions(
				companyId, WorkflowConstants.STATUS_APPROVED);

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			DynamicObjectDefinitionLocalizationTable
				dynamicObjectDefinitionLocalizationTable =
					DynamicObjectDefinitionLocalizationTableFactory.create(
						objectDefinition, _objectFieldLocalService);

			if (dynamicObjectDefinitionLocalizationTable != null) {
				tablesPrimaryKeyColumnNames.put(
					objectDefinition.getLocalizationDBTableName(),
					dynamicObjectDefinitionLocalizationTable.
						getPrimaryKeyColumnNames());
			}

			tablesPrimaryKeyColumnNames.put(
				objectDefinition.getDBTableName(),
				new String[] {objectDefinition.getPKObjectFieldDBColumnName()});

			tablesPrimaryKeyColumnNames.put(
				objectDefinition.getExtensionDBTableName(),
				new String[] {objectDefinition.getPKObjectFieldDBColumnName()});

			tablesPrimaryKeyColumnNames.putAll(
				_getObjectRelationshipTablesPrimaryKeyColumnNames(
					objectDefinition));
		}

		return tablesPrimaryKeyColumnNames;
	}

	private Map<String, String[]>
			_getObjectRelationshipTablesPrimaryKeyColumnNames(
				ObjectDefinition objectDefinition)
		throws PortalException {

		Map<String, String[]> objectRelationshipTablesPrimaryKeyColumnNames =
			new HashMap<>();

		List<ObjectRelationship> objectRelationships =
			_objectRelationshipLocalService.getAllObjectRelationships(
				objectDefinition.getObjectDefinitionId());

		for (ObjectRelationship objectRelationship : objectRelationships) {
			if (!StringUtil.equalsIgnoreCase(
					objectRelationship.getType(),
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

				continue;
			}

			Map<String, String> pkObjectFieldDBColumnNames =
				ObjectRelationshipUtil.getPKObjectFieldDBColumnNames(
					_objectDefinitionLocalService.getObjectDefinition(
						objectRelationship.getObjectDefinitionId1()),
					_objectDefinitionLocalService.getObjectDefinition(
						objectRelationship.getObjectDefinitionId2()),
					false);

			String pkObjectFieldDBColumnName1 = pkObjectFieldDBColumnNames.get(
				"pkObjectFieldDBColumnName1");
			String pkObjectFieldDBColumnName2 = pkObjectFieldDBColumnNames.get(
				"pkObjectFieldDBColumnName2");

			objectRelationshipTablesPrimaryKeyColumnNames.put(
				objectRelationship.getDBTableName(),
				new String[] {
					pkObjectFieldDBColumnName1, pkObjectFieldDBColumnName2
				});
		}

		return objectRelationshipTablesPrimaryKeyColumnNames;
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}