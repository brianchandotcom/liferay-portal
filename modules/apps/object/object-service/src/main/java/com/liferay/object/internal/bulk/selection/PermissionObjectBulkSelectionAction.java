/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.bulk.selection;

import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionAction;
import com.liferay.depot.model.DepotEntry;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.internal.entry.folder.util.ObjectEntryFolderUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.PermissionService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.permission.ModelPermissionsUtil;
import com.liferay.portal.vulcan.permission.Permission;

import java.io.Serializable;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "bulk.selection.action.key=permission.object",
	service = BulkSelectionAction.class
)
public class PermissionObjectBulkSelectionAction
	implements BulkSelectionAction<Object> {

	@Override
	public void execute(
			User user, BulkSelection<Object> bulkSelection,
			Map<String, Serializable> inputMap)
		throws Exception {

		long bulkActionTaskId = GetterUtil.getLong(
			inputMap.get("bulkActionTaskId"));

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			bulkActionTaskId);

		Map<String, Serializable> values = objectEntry.getValues();

		values.put("numberOfItems", bulkSelection.getSize());

		String executionStatus = "completed";
		AtomicInteger numberOfFailedItems = new AtomicInteger(0);
		AtomicInteger numberOfSuccessfulItems = new AtomicInteger(0);

		try {
			values.put("executionStatus", "started");

			objectEntry = _partialUpdateObjectEntry(objectEntry, values);

			values = objectEntry.getValues();

			long companyId = objectEntry.getCompanyId();

			bulkSelection.forEach(
				object -> {
					long objectDefinitionId = _getObjectDefinitionId(companyId);
					String status = "completed";

					try {
						String className = null;
						long groupId = 0L;
						long resourceId = 0L;
						String resourceName = null;

						if (object instanceof DepotEntry) {
							DepotEntry depotEntry = (DepotEntry)object;

							className = depotEntry.getModelClassName();
							groupId = depotEntry.getGroupId();
							resourceId = depotEntry.getDepotEntryId();
							resourceName = depotEntry.getModelClassName();
						}
						else if (object instanceof ObjectEntry) {
							ObjectEntry objectObjectEntry = (ObjectEntry)object;

							long rootObjectEntryFolderId =
								ObjectEntryFolderUtil.
									getRootObjectEntryFolderId(
										objectObjectEntry.
											getObjectEntryFolderId());

							if (rootObjectEntryFolderId == 0) {
								return;
							}

							ObjectEntryFolder objectEntryFolder =
								_objectEntryFolderLocalService.
									getObjectEntryFolder(
										rootObjectEntryFolderId);

							className =
								objectEntryFolder.getExternalReferenceCode();

							groupId = objectObjectEntry.getGroupId();
							resourceId = objectObjectEntry.getObjectEntryId();
							resourceName =
								objectObjectEntry.getModelClassName();
						}
						else {
							ObjectEntryFolder objectEntryFolder =
								(ObjectEntryFolder)object;

							className = objectEntryFolder.getModelClassName();
							groupId = objectEntryFolder.getGroupId();
							resourceId =
								objectEntryFolder.getObjectEntryFolderId();
							resourceName =
								objectEntryFolder.getModelClassName();
						}

						_permissionService.checkPermission(
							groupId, resourceName, resourceId);

						ModelPermissions modelPermissions =
							ModelPermissionsUtil.toModelPermissions(
								companyId,
								_getPermissions(
									(Map<String, Serializable>)inputMap.get(
										"permissions"),
									className),
								resourceId, resourceName,
								_resourceActionLocalService,
								_resourcePermissionLocalService,
								_roleLocalService);

						Collection<String> roleNames =
							modelPermissions.getRoleNames();

						for (ResourcePermission resourcePermission :
								_resourcePermissionLocalService.
									getResourcePermissions(
										companyId, resourceName,
										ResourceConstants.SCOPE_INDIVIDUAL,
										String.valueOf(resourceId))) {

							Role role = _roleLocalService.fetchRole(
								resourcePermission.getRoleId());

							if ((role == null) ||
								roleNames.contains(role.getName())) {

								continue;
							}

							for (ResourceAction resourceAction :
									_resourceActionLocalService.
										getResourceActions(resourceName)) {

								_resourcePermissionLocalService.
									removeResourcePermission(
										companyId, resourceName,
										ResourceConstants.SCOPE_INDIVIDUAL,
										String.valueOf(resourceId),
										role.getRoleId(),
										resourceAction.getActionId());
							}
						}

						_resourcePermissionLocalService.
							updateResourcePermissions(
								companyId, groupId, resourceName,
								String.valueOf(resourceId), modelPermissions);

						numberOfSuccessfulItems.getAndIncrement();
					}
					catch (PortalException portalException) {
						if (_log.isWarnEnabled()) {
							_log.warn(portalException);
						}

						numberOfFailedItems.getAndIncrement();
					}
					finally {
						_objectEntryLocalService.addObjectEntry(
							0, user.getUserId(), objectDefinitionId,
							ObjectEntryFolderConstants.
								PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
							null,
							HashMapBuilder.<String, Serializable>put(
								"bulkActionTaskId", bulkActionTaskId
							).put(
								"executionStatus", status
							).put(
								"r_cmsBATaskToCMSBATaskItems_c_cmsBulkActionT" +
									"askId",
								bulkActionTaskId
							).put(
								"type", "ObjectEntryFolder"
							).build(),
							new ServiceContext());
					}
				});
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			executionStatus = "failed";
		}
		finally {
			values.put("completionDate", new Date());
			values.put("executionStatus", executionStatus);
			values.put("numberOfFailedItems", numberOfFailedItems.get());
			values.put(
				"numberOfSuccessfulItems", numberOfSuccessfulItems.get());

			_partialUpdateObjectEntry(objectEntry, values);
		}
	}

	private long _getObjectDefinitionId(long companyId) throws PortalException {
		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMS_BULK_ACTION_TASK_ITEM", companyId);

		return objectDefinition.getObjectDefinitionId();
	}

	private Permission[] _getPermissions(
		Map<String, Serializable> map, String key) {

		return (Permission[])map.getOrDefault(key, new Permission[0]);
	}

	private ObjectEntry _partialUpdateObjectEntry(
			ObjectEntry objectEntry, Map<String, Serializable> values)
		throws PortalException {

		return _objectEntryLocalService.partialUpdateObjectEntry(
			objectEntry.getUserId(), objectEntry.getObjectEntryId(),
			objectEntry.getObjectEntryFolderId(), values, new ServiceContext());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PermissionObjectBulkSelectionAction.class);

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private PermissionService _permissionService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}