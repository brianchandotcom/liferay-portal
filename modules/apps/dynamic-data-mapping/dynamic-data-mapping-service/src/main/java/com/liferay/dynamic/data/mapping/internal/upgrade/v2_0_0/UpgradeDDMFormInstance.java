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
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceConstants;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Leonardo Barros
 */
public class UpgradeDDMFormInstance extends UpgradeProcess {

	public UpgradeDDMFormInstance(
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

	protected void collectNewActionIds(
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

	protected void deleteDDLRecordSet(long ddmStructureId, long recordSetId)
		throws SQLException {

		deleteStructureStructureLinks(ddmStructureId);

		try (PreparedStatement ps = connection.prepareStatement(
				"delete from DDLRecordSet where recordSetId = ?")) {

			ps.setLong(1, recordSetId);

			ps.executeUpdate();
		}
	}

	protected void deleteStructureStructureLinks(long ddmStructureId)
		throws SQLException {

		try (PreparedStatement ps = connection.prepareStatement(
				"delete from DDMStructureLink where structureId = ?")) {

			ps.setLong(1, ddmStructureId);

			ps.executeUpdate();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		readAndCheckResourceActions();

		duplicateResourcePermission(
			_CLASS_NAME_RECORD_SET, _CLASS_NAME_FORM_INSTANCE);

		upgradeRootModelResourceResourcePermission(
			"com.liferay.dynamic.data.lists",
			"com.liferay.dynamic.data.mapping");

		StringBundler sb1 = new StringBundler(7);

		sb1.append("select DDLRecordSet.*, TEMP_TABLE.structureVersionId ");
		sb1.append("from DDLRecordSet inner join (select structureId, ");
		sb1.append("max(structureVersionId) as structureVersionId from ");
		sb1.append("DDMStructureVersion group by ");
		sb1.append("DDMStructureVersion.structureId) TEMP_TABLE on ");
		sb1.append("DDLRecordSet.DDMStructureId = TEMP_TABLE.structureId ");
		sb1.append("where scope = 2");

		StringBundler sb2 = new StringBundler(5);

		sb2.append("insert into DDMFormInstance(uuid_, formInstanceId, ");
		sb2.append("groupId, companyId, userId, userName, versionUserId, ");
		sb2.append("versionUserName, createDate, modifiedDate, structureId, ");
		sb2.append("version, name, description, settings_, lastPublishDate) ");
		sb2.append("values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps1 = connection.prepareStatement(
				sb1.toString());
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb2.toString())) {

			while (rs.next()) {
				long recordSetId = rs.getLong("recordSetId");
				long structureId = rs.getLong("DDMStructureId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String settings = rs.getString("settings_");
				Timestamp lastPublishDate = rs.getTimestamp("lastPublishDate");
				long structureVersionId = rs.getLong("structureVersionId");

				ps2.setString(1, PortalUUIDUtil.generate());
				ps2.setLong(2, recordSetId);
				ps2.setLong(3, groupId);
				ps2.setLong(4, companyId);
				ps2.setLong(5, userId);
				ps2.setString(6, userName);
				ps2.setLong(7, userId);
				ps2.setString(8, userName);
				ps2.setTimestamp(9, createDate);
				ps2.setTimestamp(10, rs.getTimestamp("modifiedDate"));
				ps2.setLong(11, structureId);
				ps2.setString(12, DDMFormInstanceConstants.VERSION_DEFAULT);
				ps2.setString(13, name);
				ps2.setString(14, description);
				ps2.setString(15, settings);
				ps2.setTimestamp(16, lastPublishDate);

				updateDDMStructure(structureId);
				updateDDMStructureLink(structureId);

				upgradeDDMFormInstanceVersion(
					groupId, companyId, userId, userName, createDate,
					recordSetId, structureVersionId, name, description,
					settings, lastPublishDate);

				upgradeResourcePermission(
					recordSetId, _CLASS_NAME_RECORD_SET,
					_CLASS_NAME_FORM_INSTANCE);

				deleteDDLRecordSet(structureId, recordSetId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected void duplicateResourcePermission(String oldName, String newName)
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

	protected long getNewActionIds(
		String oldName, String newName, long currentActionIds,
		long oldActionIds) {

		Set<String> actionsIdsList = new HashSet<>();

		collectNewActionIds(
			actionsIdsList,
			_resourceActionLocalService.getResourceActions(oldName),
			oldActionIds);

		List<ResourceAction> newResourceActions =
			_resourceActionLocalService.getResourceActions(newName);

		collectNewActionIds(
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

	protected void readAndCheckResourceActions() throws Exception {
		Class<?> clazz = getClass();

		_resourceActions.readAndCheck(
			null, clazz.getClassLoader(),
			"/META-INF/resource-actions/default.xml");
	}

	protected void updateDDMStructure(long ddmStructureId) throws Exception {
		StringBundler sb = new StringBundler(2);

		sb.append("update DDMStructure set classNameId = ? where structureId ");
		sb.append("= ?");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			ps.setLong(
				1,
				_classNameLocalService.getClassNameId(
					DDMFormInstance.class.getName()));
			ps.setLong(2, ddmStructureId);

			ps.executeUpdate();
		}
	}

	protected void updateDDMStructureLink(long ddmStructureId)
		throws Exception {

		StringBundler sb = new StringBundler(2);

		sb.append("update DDMStructureLink set classNameId = ? where ");
		sb.append("structureId = ?");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			ps.setLong(
				1,
				_classNameLocalService.getClassNameId(
					DDMFormInstance.class.getName()));
			ps.setLong(2, ddmStructureId);

			ps.executeUpdate();
		}
	}

	protected void upgradeDDMFormInstanceVersion(
			long groupId, long companyId, long userId, String userName,
			Timestamp createDate, long formInstanceId, long structureVersionId,
			String name, String description, String settings,
			Timestamp statusDate)
		throws SQLException {

		StringBundler sb = new StringBundler(6);

		sb.append("insert into DDMFormInstanceVersion(formInstanceVersionId, ");
		sb.append("groupId, companyId, userId, userName, createDate, ");
		sb.append("formInstanceId, structureVersionId, name, description, ");
		sb.append("settings_, version, status, statusByUserId, ");
		sb.append("statusByUserName, statusDate) values(?, ?, ?, ?, ?, ?, ?, ");
		sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb.toString())) {

			ps2.setLong(1, _counterLocalService.increment());
			ps2.setLong(2, groupId);
			ps2.setLong(3, companyId);
			ps2.setLong(4, userId);
			ps2.setString(5, userName);
			ps2.setTimestamp(6, createDate);
			ps2.setLong(7, formInstanceId);
			ps2.setLong(8, structureVersionId);
			ps2.setString(9, name);
			ps2.setString(10, description);
			ps2.setString(11, settings);
			ps2.setString(12, DDMFormInstanceConstants.VERSION_DEFAULT);
			ps2.setInt(13, WorkflowConstants.STATUS_APPROVED);
			ps2.setLong(14, userId);
			ps2.setString(15, userName);
			ps2.setTimestamp(16, statusDate);

			ps2.execute();
		}
	}

	protected void upgradeResourcePermission(
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

	protected void upgradeRootModelResourceResourcePermission(
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
		new ConcurrentHashMap<String, String>() {
			{
				put("ADD_DATA_PROVIDER_INSTANCE", "ADD_DATA_PROVIDER_INSTANCE");
				put("ADD_RECORD", "ADD_FORM_INSTANCE_RECORD");
				put("ADD_RECORD_SET", "ADD_FORM_INSTANCE");
				put("ADD_STRUCTURE", "ADD_STRUCTURE");
				put("DELETE", "DELETE");
				put("PERMISSIONS", "PERMISSIONS");
				put("UPDATE", "UPDATE");
				put("VIEW", "VIEW");
			}
		};

	private final ClassNameLocalService _classNameLocalService;
	private final CounterLocalService _counterLocalService;
	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceActions _resourceActions;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}