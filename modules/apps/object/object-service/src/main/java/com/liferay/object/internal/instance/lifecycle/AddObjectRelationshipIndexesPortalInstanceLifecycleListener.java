/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.instance.lifecycle;

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
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.EveryNodeEveryStartup;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Connection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yuri Monteiro
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddObjectRelationshipIndexesPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener
	implements EveryNodeEveryStartup {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (!PropsValues.DATABASE_INDEXES_UPDATE_ON_STARTUP) {
			return;
		}

		for (ObjectDefinition objectDefinition :
				_objectDefinitionLocalService.getObjectDefinitions(
					company.getCompanyId(), true,
					WorkflowConstants.STATUS_APPROVED)) {

			if (objectDefinition.isUnmodifiableSystemObject()) {
				continue;
			}

			try {
				_addObjectRelationshipIndexes(objectDefinition);
			}
			catch (Exception exception) {
				_log.error(
					"Unable to add relationship indexes for " +
						objectDefinition.getName(),
					exception);
			}
		}
	}

	private void _addObjectRelationshipIndexes(
			ObjectDefinition objectDefinition)
		throws Exception {

		try (Connection connection = DataAccess.getConnection()) {
			for (ObjectField objectField :
					_objectFieldLocalService.getObjectFieldsByBusinessType(
						objectDefinition.getObjectDefinitionId(),
						ObjectFieldConstants.BUSINESS_TYPE_RELATIONSHIP)) {

				ObjectDBManagerUtil.createIndexMetadata(
					connection, objectField.getDBTableName(), false,
					objectField.getDBColumnName());
			}

			for (ObjectRelationship objectRelationship :
					_objectRelationshipLocalService.getObjectRelationships(
						objectDefinition.getObjectDefinitionId(), false,
						ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

				DynamicObjectRelationshipMappingTable
					dynamicObjectRelationshipMappingTable =
						DynamicObjectRelationshipMappingTableFactory.create(
							objectRelationship);

				Column<DynamicObjectRelationshipMappingTable, Long>
					primaryKeyColumn1 =
						dynamicObjectRelationshipMappingTable.
							getPrimaryKeyColumn1();

				ObjectDBManagerUtil.createIndexMetadata(
					connection, objectRelationship.getDBTableName(), false,
					primaryKeyColumn1.getName());

				Column<DynamicObjectRelationshipMappingTable, Long>
					primaryKeyColumn2 =
						dynamicObjectRelationshipMappingTable.
							getPrimaryKeyColumn2();

				ObjectDBManagerUtil.createIndexMetadata(
					connection, objectRelationship.getDBTableName(), false,
					primaryKeyColumn2.getName());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AddObjectRelationshipIndexesPortalInstanceLifecycleListener.class);

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

}