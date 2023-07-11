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

package com.liferay.object.service.impl;

import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.model.ObjectFolder;
import com.liferay.object.service.base.ObjectFolderServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Murilo Stodolni
 */
@Component(
	property = {
		"json.web.service.context.name=object",
		"json.web.service.context.path=ObjectFolder"
	},
	service = AopService.class
)
public class ObjectFolderServiceImpl extends ObjectFolderServiceBaseImpl {

	@Override
	public ObjectFolder addObjectFolder(
			String externalReferenceCode, Map<Locale, String> labelMap,
			String name)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), null, ObjectActionKeys.ADD_OBJECT_FOLDER);

		return objectFolderLocalService.addObjectFolder(
			externalReferenceCode, getUserId(), labelMap, name);
	}

	@Override
	public ObjectFolder deleteObjectFolder(long objectFolderId)
		throws PortalException {

		_objectFolderModelResourcePermission.check(
			getPermissionChecker(), objectFolderId, ActionKeys.DELETE);

		return objectFolderLocalService.deleteObjectFolder(objectFolderId);
	}

	@Override
	public ObjectFolder getObjectFolder(long objectFolderId)
		throws PortalException {

		_objectFolderModelResourcePermission.check(
			getPermissionChecker(), objectFolderId, ActionKeys.VIEW);

		return objectFolderLocalService.getObjectFolder(objectFolderId);
	}

	@Override
	public ObjectFolder updateObjectFolder(
			String externalReferenceCode, long objectFolderId,
			Map<Locale, String> labelMap)
		throws PortalException {

		_objectFolderModelResourcePermission.check(
			getPermissionChecker(), objectFolderId, ActionKeys.UPDATE);

		return objectFolderLocalService.updateObjectFolder(
			externalReferenceCode, objectFolderId, labelMap);
	}

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectFolder)"
	)
	private ModelResourcePermission<ObjectFolder>
		_objectFolderModelResourcePermission;

	@Reference(target = "(resource.name=" + ObjectConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}