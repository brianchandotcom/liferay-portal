/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.impl;

import com.liferay.object.model.ObjectEntryFolder;
import com.liferay.object.service.base.ObjectEntryFolderServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionUtil;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"json.web.service.context.name=object",
		"json.web.service.context.path=ObjectEntryFolder"
	},
	service = AopService.class
)
public class ObjectEntryFolderServiceImpl
	extends ObjectEntryFolderServiceBaseImpl {

	@Override
	public ObjectEntryFolder addObjectEntryFolder(
			String externalReferenceCode, long groupId,
			long parentObjectEntryFolderId, Map<Locale, String> labelMap,
			String name, ServiceContext serviceContext)
		throws PortalException {

		ModelResourcePermissionUtil.check(
			_objectEntryFolderModelResourcePermission, getPermissionChecker(),
			groupId, parentObjectEntryFolderId, ActionKeys.ADD_FOLDER);

		return objectEntryFolderLocalService.addObjectEntryFolder(
			externalReferenceCode, getUserId(), groupId,
			parentObjectEntryFolderId, labelMap, name, serviceContext);
	}

	@Override
	public ObjectEntryFolder deleteObjectEntryFolder(long objectEntryFolderId)
		throws PortalException {

		_objectEntryFolderModelResourcePermission.check(
			getPermissionChecker(), objectEntryFolderId, ActionKeys.DELETE);

		return objectEntryFolderLocalService.deleteObjectEntryFolder(
			objectEntryFolderId);
	}

	@Override
	public ObjectEntryFolder getObjectEntryFolder(long objectEntryFolderId)
		throws PortalException {

		ObjectEntryFolder objectEntryFolder =
			objectEntryFolderLocalService.getObjectEntryFolder(
				objectEntryFolderId);

		_objectEntryFolderModelResourcePermission.check(
			getPermissionChecker(), objectEntryFolder, ActionKeys.VIEW);

		return objectEntryFolder;
	}

	@Override
	public ObjectEntryFolder updateObjectEntryFolder(
			long objectEntryFolderId, long parentObjectEntryFolderId,
			Map<Locale, String> labelMap, String name)
		throws PortalException {

		_objectEntryFolderModelResourcePermission.check(
			getPermissionChecker(), objectEntryFolderId, ActionKeys.UPDATE);

		return objectEntryFolderLocalService.updateObjectEntryFolder(
			getUserId(), objectEntryFolderId, parentObjectEntryFolderId,
			labelMap, name);
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.object.model.ObjectEntryFolder)"
	)
	private volatile ModelResourcePermission<ObjectEntryFolder>
		_objectEntryFolderModelResourcePermission;

}