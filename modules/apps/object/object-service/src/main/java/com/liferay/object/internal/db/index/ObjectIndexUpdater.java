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
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSyncUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.Connection;

import java.util.List;

import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.runtime.ServiceComponentRuntime;

/**
 * @author Yuri Monteiro
 */
@Component(service = {})
public class ObjectIndexUpdater {

	@Activate
	protected void activate() {
		if (!PropsValues.DATABASE_INDEXES_UPDATE_ON_STARTUP ||
			StartupHelperUtil.isDBNew()) {

			return;
		}

		DependencyManagerSyncUtil.registerSyncCallable(
			() -> {
				try {
					updateIndexes();
				}
				catch (Exception exception) {
					_log.error(
						"Unable to update object definition indexes",
						exception);
				}

				_serviceComponentRuntime.disableComponent(
					_serviceComponentRuntime.getComponentDescriptionDTO(
						FrameworkUtil.getBundle(ObjectIndexUpdater.class),
						ObjectIndexUpdater.class.getName()));

				return null;
			});
	}

	protected void updateIndexes() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				try (Connection connection = DataAccess.getConnection()) {
					List<ObjectDefinition> objectDefinitions =
						_objectDefinitionLocalService.getObjectDefinitions(
							companyId, WorkflowConstants.STATUS_APPROVED);

					List<Long> objectDefinitionIds = TransformUtil.transform(
						objectDefinitions,
						ObjectDefinition::getObjectDefinitionId);

					for (ObjectDefinition objectDefinition :
							objectDefinitions) {

						try {
							_updateIndexes(
								connection, objectDefinition,
								objectDefinitionIds);
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
			Connection connection, ObjectDefinition objectDefinition,
			List<Long> objectDefinitionIds)
		throws Exception {

		for (ObjectRelationship objectRelationship :
				_objectRelationshipLocalService.getObjectRelationships(
					objectDefinition.getObjectDefinitionId(), false,
					ObjectRelationshipConstants.TYPE_MANY_TO_MANY)) {

			if (!objectDefinitionIds.contains(
					objectRelationship.getObjectDefinitionId2())) {

				continue;
			}

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

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.object.service)(release.schema.version>=1.0.0))"
	)
	private Release _release;

	@Reference
	private ServiceComponentRuntime _serviceComponentRuntime;

}