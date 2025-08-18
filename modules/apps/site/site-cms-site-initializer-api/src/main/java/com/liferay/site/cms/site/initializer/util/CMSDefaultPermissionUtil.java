/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.util;

import com.liferay.object.constants.ObjectEntryFolderConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import java.util.Map;

/**
 * @author Stefano Motta
 */
public class CMSDefaultPermissionUtil {

	public static ObjectEntry addOrUpdateCMSDefaultsPermission(
			String externalReferenceCode, long groupId, long userId,
			String classExternalReferenceCode, String className,
			JSONObject permissionsJSONObject)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.
				getObjectDefinitionByExternalReferenceCode(
					"L_DEFAULT_PERMISSION", group.getCompanyId());

		return ObjectEntryLocalServiceUtil.addOrUpdateObjectEntry(
			externalReferenceCode, groupId, userId,
			objectDefinition.getObjectDefinitionId(),
			ObjectEntryFolderConstants.PARENT_OBJECT_ENTRY_FOLDER_ID_DEFAULT,
			HashMapBuilder.<String, Serializable>put(
				"classExternalReferenceCode", classExternalReferenceCode
			).put(
				"className", className
			).put(
				"permissions", permissionsJSONObject.toString()
			).build(),
			new ServiceContext());
	}

	public static JSONObject getCMSDefaultPermissionPermissions(
			String externalReferenceCode, long groupId)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		ObjectDefinition objectDefinition =
			ObjectDefinitionLocalServiceUtil.
				getObjectDefinitionByExternalReferenceCode(
					"L_DEFAULT_PERMISSION", group.getCompanyId());

		ObjectEntry objectEntry = ObjectEntryLocalServiceUtil.fetchObjectEntry(
			externalReferenceCode, group.getGroupId(),
			objectDefinition.getObjectDefinitionId());

		if (objectEntry == null) {
			return JSONFactoryUtil.createJSONObject();
		}

		Map<String, Serializable> values = objectEntry.getValues();

		return JSONFactoryUtil.createJSONObject(
			String.valueOf(values.getOrDefault("permissions", "{}")));
	}

}