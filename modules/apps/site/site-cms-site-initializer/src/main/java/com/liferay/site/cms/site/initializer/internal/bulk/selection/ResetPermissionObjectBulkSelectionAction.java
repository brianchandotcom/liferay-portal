/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.bulk.selection;

import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionAction;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryFolderLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.site.cms.site.initializer.util.ResetAssetPermissionUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Balazs Breier
 */
@Component(
	property = "bulk.selection.action.key=reset.permission.object",
	service = BulkSelectionAction.class
)
public class ResetPermissionObjectBulkSelectionAction
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
						if (object instanceof ObjectEntry) {
							ObjectEntry objectObjectEntry = (ObjectEntry)object;

							ResetAssetPermissionUtil.
								executeResetAssetPermission(
									objectObjectEntry.getModelClassName(),
									objectObjectEntry.getObjectEntryId(),
									_filterFactory, _groupLocalService,
									_objectDefinitionLocalService,
									_objectEntryFolderLocalService,
									_objectEntryFolderModelResourcePermission,
									_objectEntryLocalService,
									_resourcePermissionLocalService,
									_roleLocalService);
						}
						else if (object instanceof ObjectEntryFolder) {
							ObjectEntryFolder objectObjectEntryFolder =
								(ObjectEntryFolder)object;

							ResetAssetPermissionUtil.
								executeResetAssetPermission(
									objectObjectEntryFolder.getModelClassName(),
									objectObjectEntryFolder.
										getObjectEntryFolderId(),
									_filterFactory, _groupLocalService,
									_objectDefinitionLocalService,
									_objectEntryFolderLocalService,
									_objectEntryFolderModelResourcePermission,
									_objectEntryLocalService,
									_resourcePermissionLocalService,
									_roleLocalService);
						}
						else {
							throw new IllegalArgumentException(
								"Unsupported object " + object);
						}

						numberOfSuccessfulItems.getAndIncrement();
					}
					catch (Exception exception) {
						if (_log.isWarnEnabled()) {
							_log.warn(exception);
						}

						numberOfFailedItems.getAndIncrement();
						status = "failed";
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

	private ObjectEntry _partialUpdateObjectEntry(
			ObjectEntry objectEntry, Map<String, Serializable> values)
		throws PortalException {

		return _objectEntryLocalService.partialUpdateObjectEntry(
			objectEntry.getUserId(), objectEntry.getObjectEntryId(),
			objectEntry.getObjectEntryFolderId(), values, new ServiceContext());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResetPermissionObjectBulkSelectionAction.class);

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryFolderLocalService _objectEntryFolderLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectEntryFolder)"
	)
	private ModelResourcePermission<ObjectEntryFolder>
		_objectEntryFolderModelResourcePermission;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}