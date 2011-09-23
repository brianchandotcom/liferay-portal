/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryTypeServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryTypePermission;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class DLFileEntryTypeServiceImpl extends DLFileEntryTypeServiceBaseImpl {

	public DLFileEntryType addFileEntryType(
			long groupId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_DOCUMENT_TYPE);

		return dlFileEntryTypeLocalService.addFileEntryType(
			getUserId(), groupId, name, description, ddmStructureIds,
			serviceContext);
	}

	public void deleteFileEntryType(long fileEntryTypeId)
		throws PortalException, SystemException {

		DLFileEntryTypePermission.check(
			getPermissionChecker(), fileEntryTypeId, ActionKeys.DELETE);

		dlFileEntryTypeLocalService.deleteFileEntryType(fileEntryTypeId);
	}

	public DLFileEntryType getFileEntryType(long fileEntryTypeId)
		throws PortalException, SystemException {

		DLFileEntryTypePermission.check(
			getPermissionChecker(), fileEntryTypeId, ActionKeys.VIEW);

		return dlFileEntryTypeLocalService.getFileEntryType(fileEntryTypeId);
	}

	public List<DLFileEntryType> getFileEntryTypes(
			long[] groupIds, int start, int end)
		throws SystemException {

		return dlFileEntryTypePersistence.filterFindByGroupId(
			groupIds, start, end);
	}

	public int getFileEntryTypesCount(long[] groupIds) throws SystemException {

		return dlFileEntryTypePersistence.filterCountByGroupId(groupIds);
	}

	public void updateFileEntryType(
			long fileEntryTypeId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryTypePermission.check(
			getPermissionChecker(), fileEntryTypeId, ActionKeys.UPDATE);

		dlFileEntryTypeLocalService.updateFileEntryType(
			fileEntryTypeId, name, description, ddmStructureIds,
			serviceContext);
	}

}