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

package com.liferay.dynamic.data.mapping.internal.upgrade.v2_0_0;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMFormInstanceConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ConcurrentHashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 */
public class DDMFormInstanceUpgradeProcess extends UpgradeProcess {

	public DDMFormInstanceUpgradeProcess(
		ClassNameLocalService classNameLocalService,
		CounterLocalService counterLocalService,
		ResourceActions resourceActions,
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_classNameLocalService = classNameLocalService;
		_counterLocalService = counterLocalService;
		_resourceActions = resourceActions;
		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_readAndCheckResourceActions();

		_duplicateResourcePermission(
			_CLASS_NAME_RECORD_SET, _CLASS_NAME_FORM_INSTANCE);

		_upgradeRootModelResourceResourcePermission(
			"com.liferay.dynamic.data.lists",
			"com.liferay.dynamic.data.mapping");

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select DDLRecordSet.*, TEMP_TABLE.structureVersionId ",
					"from DDLRecordSet inner join (select structureId, ",
					"max(structureVersionId) as structureVersionId from ",
					"DDMStructureVersion group by ",
					"DDMStructureVersion.structureId) TEMP_TABLE on ",
					"DDLRecordSet.DDMStructureId = TEMP_TABLE.structureId ",
					"where scope = 2"));
			ResultSet resultSet = preparedStatement1.executeQuery();
			PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into DDMFormInstance(uuid_, formInstanceId, ",
						"groupId, companyId, userId, userName, versionUserId, ",
						"versionUserName, createDate, modifiedDate, ",
						"structureId, version, name, description, settings_, ",
						"lastPublishDate) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ",
						"?, ?, ?, ?, ?, ?, ?)"))) {

			while (resultSet.next()) {
				long recordSetId = resultSet.getLong("recordSetId");
				long structureId = resultSet.getLong("DDMStructureId");
				long groupId = resultSet.getLong("groupId");
				long companyId = resultSet.getLong("companyId");
				long userId = resultSet.getLong("userId");
				String userName = resultSet.getString("userName");
				Timestamp createDate = resultSet.getTimestamp("createDate");
				String name = resultSet.getString("name");
				String description = resultSet.getString("description");
				String settings = resultSet.getString("settings_");
				Timestamp lastPublishDate = resultSet.getTimestamp(
					"lastPublishDate");
				long structureVersionId = resultSet.getLong(
					"structureVersionId");

				preparedStatement2.setString(1, PortalUUIDUtil.generate());
				preparedStatement2.setLong(2, recordSetId);
				preparedStatement2.setLong(3, groupId);
				preparedStatement2.setLong(4, companyId);
				preparedStatement2.setLong(5, userId);
				preparedStatement2.setString(6, userName);
				preparedStatement2.setLong(7, userId);
				preparedStatement2.setString(8, userName);
				preparedStatement2.setTimestamp(9, createDate);
				preparedStatement2.setTimestamp(
					10, resultSet.getTimestamp("modifiedDate"));
				preparedStatement2.setLong(11, structureId);
				preparedStatement2.setString(
					12, DDMFormInstanceConstants.VERSION_DEFAULT);
				preparedStatement2.setString(13, name);
				preparedStatement2.setString(14, description);
				preparedStatement2.setString(15, settings);
				preparedStatement2.setTimestamp(16, lastPublishDate);

				_updateDDMStructure(structureId);
				_updateDDMStructureLink(structureId);

				_upgradeDDMFormInstanceVersion(
					groupId, companyId, userId, userName, createDate,
					recordSetId, structureVersionId, name, description,
					settings, lastPublishDate);

				_upgradeResourcePermission(
					recordSetId, _CLASS_NAME_RECORD_SET,
					_CLASS_NAME_FORM_INSTANCE);

				_deleteDDLRecordSet(structureId, recordSetId);

				preparedStatement2.addBatch();
			}

			preparedStatement2.executeBatch();

			_updateWorkflowDefinitionLink();
		}
	}

	protected long getNewActionIds(
		String oldName, String newName, long currentActionIds,
		long oldActionIds) {

		Set<String> actionsIdsList = new HashSet<>();

		_collectNewActionIds(
			actionsIdsList,
			_resourceActionLocalService.getResourceActions(oldName),
			oldActionIds);

		List<ResourceAction> newResourceActions =
			_resourceActionLocalService.getResourceActions(newName);

		_collectNewActionIds(
			actionsIdsList, newResourceActions, currentActionIds);

		Stream<ResourceAction> resourceActionStream =
			newResourceActions.stream();

		Map<String, Long> map = resourceActionStream.collect(
			Collectors.toMap(
				resourceAction -> resourceAction.getActionId(),
				resourceAction -> resourceAction.getBitwiseValue()));

		Stream<String> actionsIdsStream = actionsIdsList.stream();

		return actionsIdsStream.mapToLong(
			actionId -> MapUtil.getLong(map, actionId)
		).sum();
	}

	private void _collectNewActionIds(
		Set<String> actionsIdsSet, List<ResourceAction> resourceActionList,
		long oldActionIds) {

		for (ResourceAction resourceAction : resourceActionList) {
			long bitwiseValue = resourceAction.getBitwiseValue();

			if ((oldActionIds & bitwiseValue) == bitwiseValue) {
				actionsIdsSet.add(
					MapUtil.getString(
						_resourceActionIdsMap, resourceAction.getActionId()));
			}
		}
	}

	private void _deleteDDLRecordSet(long ddmStructureId, long recordSetId)
		throws SQLException {

		_deleteStructureStructureLinks(ddmStructureId);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from DDLRecordSet where recordSetId = ?")) {

			preparedStatement.setLong(1, recordSetId);

			preparedStatement.executeUpdate();
		}
	}

	private void _deleteStructureStructureLinks(long ddmStructureId)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from DDMStructureLink where structureId = ?")) {

			preparedStatement.setLong(1, ddmStructureId);

			preparedStatement.executeUpdate();
		}
	}

	private void _duplicateResourcePermission(String oldName, String newName)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			_resourcePermissionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property nameProperty = PropertyFactoryUtil.forName("name");

				dynamicQuery.add(nameProperty.eq(oldName));

				Property primKeyProperty = PropertyFactoryUtil.forName("scope");

				dynamicQuery.add(
					primKeyProperty.ne(ResourceConstants.SCOPE_INDIVIDUAL));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<ResourcePermission>)
				resourcePermission -> {
					resourcePermission.setName(newName);

					if (Objects.equals(
							resourcePermission.getPrimKey(), oldName)) {

						resourcePermission.setPrimKey(newName);
					}

					resourcePermission.setActionIds(
						getNewActionIds(
							oldName, newName, 0,
							resourcePermission.getActionIds()));
					resourcePermission.setResourcePermissionId(
						_counterLocalService.increment());

					_resourcePermissionLocalService.addResourcePermission(
						resourcePermission);
				});

		actionableDynamicQuery.performActions();
	}

	private void _readAndCheckResourceActions() throws Exception {
		Class<?> clazz = getClass();

		_resourceActions.populateModelResources(
			clazz.getClassLoader(), "/resource-actions/default.xml");
	}

	private void _updateDDMStructure(long ddmStructureId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update DDMStructure set classNameId = ? where structureId = " +
					"?")) {

			preparedStatement.setLong(
				1,
				_classNameLocalService.getClassNameId(
					DDMFormInstance.class.getName()));
			preparedStatement.setLong(2, ddmStructureId);

			preparedStatement.executeUpdate();
		}
	}

	private void _updateDDMStructureLink(long ddmStructureId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update DDMStructureLink set classNameId = ? where " +
					"structureId = ?")) {

			preparedStatement.setLong(
				1,
				_classNameLocalService.getClassNameId(
					DDMFormInstance.class.getName()));
			preparedStatement.setLong(2, ddmStructureId);

			preparedStatement.executeUpdate();
		}
	}

	private void _updateWorkflowDefinitionLink() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update WorkflowDefinitionLink set classNameId = ? where " +
					"classNameId = ?")) {

			preparedStatement.setLong(
				1,
				_classNameLocalService.getClassNameId(
					_CLASS_NAME_FORM_INSTANCE));

			preparedStatement.setLong(
				2,
				_classNameLocalService.getClassNameId(_CLASS_NAME_RECORD_SET));

			preparedStatement.execute();
		}
	}

	private void _upgradeDDMFormInstanceVersion(
			long groupId, long companyId, long userId, String userName,
			Timestamp createDate, long formInstanceId, long structureVersionId,
			String name, String description, String settings,
			Timestamp statusDate)
		throws SQLException {

		try (PreparedStatement preparedStatement2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into DDMFormInstanceVersion(",
						"formInstanceVersionId, groupId, companyId, userId, ",
						"userName, createDate, formInstanceId, ",
						"structureVersionId, name, description, settings_, ",
						"version, status, statusByUserId, statusByUserName, ",
						"statusDate) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ",
						"?, ?, ?, ?, ?)"))) {

			preparedStatement2.setLong(1, _counterLocalService.increment());
			preparedStatement2.setLong(2, groupId);
			preparedStatement2.setLong(3, companyId);
			preparedStatement2.setLong(4, userId);
			preparedStatement2.setString(5, userName);
			preparedStatement2.setTimestamp(6, createDate);
			preparedStatement2.setLong(7, formInstanceId);
			preparedStatement2.setLong(8, structureVersionId);
			preparedStatement2.setString(9, name);
			preparedStatement2.setString(10, description);
			preparedStatement2.setString(11, settings);
			preparedStatement2.setString(
				12, DDMFormInstanceConstants.VERSION_DEFAULT);
			preparedStatement2.setInt(13, WorkflowConstants.STATUS_APPROVED);
			preparedStatement2.setLong(14, userId);
			preparedStatement2.setString(15, userName);
			preparedStatement2.setTimestamp(16, statusDate);

			preparedStatement2.execute();
		}
	}

	private void _upgradeResourcePermission(
			long primKeyId, String oldName, String newName)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			_resourcePermissionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property nameProperty = PropertyFactoryUtil.forName("name");

				dynamicQuery.add(nameProperty.eq(oldName));

				Property primKeyProperty = PropertyFactoryUtil.forName(
					"primKey");

				dynamicQuery.add(primKeyProperty.eq(String.valueOf(primKeyId)));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<ResourcePermission>)
				resourcePermission -> {
					resourcePermission.setName(newName);
					resourcePermission.setActionIds(
						getNewActionIds(
							oldName, newName, 0,
							resourcePermission.getActionIds()));

					_resourcePermissionLocalService.updateResourcePermission(
						resourcePermission);
				});

		actionableDynamicQuery.performActions();
	}

	private void _upgradeRootModelResourceResourcePermission(
			String oldRootModelResourceName, String newRootModelResourceName)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			_resourcePermissionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property nameProperty = PropertyFactoryUtil.forName("name");

				dynamicQuery.add(nameProperty.eq(oldRootModelResourceName));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<ResourcePermission>)
				resourcePermission -> {
					resourcePermission.setName(newRootModelResourceName);

					if (Objects.equals(
							resourcePermission.getPrimKey(),
							oldRootModelResourceName)) {

						resourcePermission.setPrimKey(newRootModelResourceName);
					}

					ResourcePermission existingResourcePermission =
						_resourcePermissionLocalService.fetchResourcePermission(
							resourcePermission.getCompanyId(),
							resourcePermission.getName(),
							resourcePermission.getScope(),
							resourcePermission.getPrimKey(),
							resourcePermission.getRoleId());

					long currentActionIds = 0;

					if (existingResourcePermission != null) {
						currentActionIds =
							existingResourcePermission.getActionIds();

						resourcePermission = existingResourcePermission;
					}
					else {
						resourcePermission.setResourcePermissionId(
							_counterLocalService.increment());
					}

					resourcePermission.setActionIds(
						getNewActionIds(
							oldRootModelResourceName, newRootModelResourceName,
							currentActionIds,
							resourcePermission.getActionIds()));

					_resourcePermissionLocalService.updateResourcePermission(
						resourcePermission);
				});

		actionableDynamicQuery.performActions();
	}

	private static final String _CLASS_NAME_FORM_INSTANCE =
		"com.liferay.dynamic.data.mapping.model.DDMFormInstance";

	private static final String _CLASS_NAME_RECORD_SET =
		"com.liferay.dynamic.data.lists.model.DDLRecordSet";

	private static final Map<String, String> _resourceActionIdsMap =
		ConcurrentHashMapBuilder.put(
			"ADD_DATA_PROVIDER_INSTANCE", "ADD_DATA_PROVIDER_INSTANCE"
		).put(
			"ADD_RECORD", "ADD_FORM_INSTANCE_RECORD"
		).put(
			"ADD_RECORD_SET", "ADD_FORM_INSTANCE"
		).put(
			"ADD_STRUCTURE", "ADD_STRUCTURE"
		).put(
			"DELETE", "DELETE"
		).put(
			"PERMISSIONS", "PERMISSIONS"
		).put(
			"UPDATE", "UPDATE"
		).put(
			"VIEW", "VIEW"
		).build();

	private final ClassNameLocalService _classNameLocalService;
	private final CounterLocalService _counterLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceActions _resourceActions;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}