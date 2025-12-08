/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.bulk.selection;

import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionAction;
import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "bulk.selection.action.key=default.permission.object",
	service = BulkSelectionAction.class
)
public class DefaultPermissionObjectBulkSelectionAction
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
						ObjectEntry objectObjectEntry = (ObjectEntry)object;

						Map<String, Serializable> objectObjectEntryValues =
							objectObjectEntry.getValues();

						String roleKey = (String)inputMap.get("roleKey");

						if (Validator.isBlank(roleKey)) {
							objectObjectEntryValues.put(
								"defaultPermissions",
								MapUtil.getString(
									inputMap, "defaultPermissions"));
						}
						else {
							JSONObject existingJSONObject =
								_jsonFactory.createJSONObject(
									GetterUtil.getString(
										objectObjectEntryValues.get(
											"defaultPermissions"),
										"{}"));

							JSONObject newJSONObject =
								_jsonFactory.createJSONObject(
									GetterUtil.getString(
										MapUtil.getString(
											inputMap, "defaultPermissions"),
										"{}"));

							existingJSONObject.put(
								ObjectEntryFolderConstants.
									EXTERNAL_REFERENCE_CODE_CONTENTS,
								_getJSONObject(
									existingJSONObject.getJSONObject(
										ObjectEntryFolderConstants.
											EXTERNAL_REFERENCE_CODE_CONTENTS),
									newJSONObject.getJSONObject(
										ObjectEntryFolderConstants.
											EXTERNAL_REFERENCE_CODE_CONTENTS),
									roleKey)
							).put(
								ObjectEntryFolderConstants.
									EXTERNAL_REFERENCE_CODE_FILES,
								_getJSONObject(
									existingJSONObject.getJSONObject(
										ObjectEntryFolderConstants.
											EXTERNAL_REFERENCE_CODE_FILES),
									newJSONObject.getJSONObject(
										ObjectEntryFolderConstants.
											EXTERNAL_REFERENCE_CODE_FILES),
									roleKey)
							).put(
								"OBJECT_ENTRY_FOLDERS",
								_getJSONObject(
									existingJSONObject.getJSONObject(
										"OBJECT_ENTRY_FOLDERS"),
									newJSONObject.getJSONObject(
										"OBJECT_ENTRY_FOLDERS"),
									roleKey)
							);

							objectObjectEntryValues.put(
								"defaultPermissions",
								existingJSONObject.toString());
						}

						_partialUpdateObjectEntry(
							objectObjectEntry, objectObjectEntryValues);

						numberOfSuccessfulItems.getAndIncrement();
					}
					catch (PortalException portalException) {
						if (_log.isWarnEnabled()) {
							_log.warn(portalException);
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

	private JSONObject _getJSONObject(
		JSONObject jsonObject1, JSONObject jsonObject2, String key) {

		if (jsonObject1 == null) {
			jsonObject1 = _jsonFactory.createJSONObject();
		}

		if ((jsonObject2 == null) || (jsonObject2.get(key) == null)) {
			return jsonObject1;
		}

		jsonObject1.put(key, jsonObject2.get(key));

		return jsonObject1;
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
		DefaultPermissionObjectBulkSelectionAction.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}