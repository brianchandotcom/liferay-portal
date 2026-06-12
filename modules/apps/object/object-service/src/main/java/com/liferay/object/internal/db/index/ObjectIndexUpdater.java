/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.db.index;

import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectRelationshipConstants;
import com.liferay.object.internal.dao.db.ObjectDBManagerUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.petra.sql.dsl.DynamicObjectRelationshipMappingTable;
import com.liferay.object.petra.sql.dsl.DynamicObjectRelationshipMappingTableFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.db.index.IndexUpdater;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yuri Monteiro
 */
@Component(service = IndexUpdater.class)
public class ObjectIndexUpdater implements IndexUpdater {

	@Override
	public void updateIndexes() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				try (Connection connection = DataAccess.getConnection()) {
					for (ObjectDefinition objectDefinition :
							_objectDefinitionLocalService.getObjectDefinitions(
								companyId, true,
								WorkflowConstants.STATUS_APPROVED)) {

						try {
							_updateIndexes(connection, objectDefinition);
						}
						catch (Exception exception) {
							_log.error(
								"Unable to update indexes for object " +
									"definition " + objectDefinition.getName(),
								exception);
						}
					}
				}
				catch (Exception exception) {
					_log.error(
						"Unable to update indexes for company " + companyId,
						exception);
				}
			});
	}

	private void _updateIndexes(
			Connection connection, ObjectDefinition objectDefinition)
		throws Exception {

		for (ObjectRelationship objectRelationship :
				_objectRelationshipLocalService.getObjectRelationships(
					objectDefinition.getObjectDefinitionId(), false,
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			DynamicObjectRelationshipMappingTable
				dynamicObjectRelationshipMappingTable =
					DynamicObjectRelationshipMappingTableFactory.create(
						objectRelationship);

			String dbTableName = objectRelationship.getDBTableName();

			Column<DynamicObjectRelationshipMappingTable, Long>
				primaryKeyColumn1 =
					dynamicObjectRelationshipMappingTable.
						getPrimaryKeyColumn1();

			ObjectDBManagerUtil.createIndexMetadata(
				connection, dbTableName, false, primaryKeyColumn1.getName());

			Column<DynamicObjectRelationshipMappingTable, Long>
				primaryKeyColumn2 =
					dynamicObjectRelationshipMappingTable.
						getPrimaryKeyColumn2();

			ObjectDBManagerUtil.createIndexMetadata(
				connection, dbTableName, false, primaryKeyColumn2.getName());
		}

		if (objectDefinition.isUnmodifiableSystemObject()) {
			return;
		}

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFieldsByBusinessType(
					objectDefinition.getObjectDefinitionId(),
					ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

			ObjectDBManagerUtil.createIndexMetadata(
				connection, objectField.getDBTableName(), false,
				objectField.getDBColumnName());
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectIndexUpdater.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}