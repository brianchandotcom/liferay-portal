/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryTypeServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryTypePermission;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;

import java.util.Iterator;
import java.util.List;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * file and folder file entry types. Its methods include permission checks.
 *
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

	public List<DLFileEntryType> getFileEntryTypes(long[] groupIds)
		throws SystemException {

		return dlFileEntryTypePersistence.filterFindByGroupId(groupIds);
	}

	public int getFileEntryTypesCount(long[] groupIds) throws SystemException {
		return dlFileEntryTypePersistence.filterCountByGroupId(groupIds);
	}

	public List<DLFileEntryType> getFolderFileEntryTypes(
			long[] groupIds, long folderId, boolean inherited)
		throws PortalException, SystemException {

		return filterFileEntryTypes(
			dlFileEntryTypeLocalService.getFolderFileEntryTypes(
				groupIds, folderId, inherited));
	}

	public List<DLFileEntryType> search(
			long companyId, long[] groupIds, String keywords,
			boolean includeBasicFileEntryType, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return dlFileEntryTypeFinder.filterFindByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType, start,
			end, orderByComparator);
	}

	public int searchCount(
			long companyId, long[] groupIds, String keywords,
			boolean includeBasicFileEntryType)
		throws SystemException {

		return dlFileEntryTypeFinder.filterCountByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType);
	}

	public void updateFileEntryType(
			long fileEntryTypeId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryTypePermission.check(
			getPermissionChecker(), fileEntryTypeId, ActionKeys.UPDATE);

		dlFileEntryTypeLocalService.updateFileEntryType(
			getUserId(), fileEntryTypeId, name, description, ddmStructureIds,
			serviceContext);
	}

	protected List<DLFileEntryType> filterFileEntryTypes(
			List<DLFileEntryType> fileEntryTypes)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		fileEntryTypes = ListUtil.copy(fileEntryTypes);

		Iterator<DLFileEntryType> itr = fileEntryTypes.iterator();

		while (itr.hasNext()) {
			DLFileEntryType fileEntryType = itr.next();

			if ((fileEntryType.getFileEntryTypeId() > 0) &&
				!DLFileEntryTypePermission.contains(
					permissionChecker, fileEntryType, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return fileEntryTypes;
	}

}