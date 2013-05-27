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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryTypeServiceBaseImpl;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryTypePermission;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service for accessing, adding, deleting, and updating
 * file and folder file entry types. Its methods include permission checks.
 *
 * @author Alexander Chow
 */
public class DLFileEntryTypeServiceImpl extends DLFileEntryTypeServiceBaseImpl {

	@Override
	public DLFileEntryType addFileEntryType(
			long groupId, String fileEntryTypeKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long[] ddmStructureIds,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLPermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_DOCUMENT_TYPE);

		return dlFileEntryTypeLocalService.addFileEntryType(
			getUserId(), groupId, fileEntryTypeKey, nameMap, descriptionMap,
			ddmStructureIds, serviceContext);
	}

	@Override
	public DLFileEntryType addFileEntryType(
			long groupId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.getDefault(), name);

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(LocaleUtil.getDefault(), description);

		return addFileEntryType(
			groupId, null, nameMap, descriptionMap, ddmStructureIds,
			serviceContext);
	}

	@Override
	public void deleteFileEntryType(long fileEntryTypeId)
		throws PortalException, SystemException {

		DLFileEntryTypePermission.check(
			getPermissionChecker(), fileEntryTypeId, ActionKeys.DELETE);

		dlFileEntryTypeLocalService.deleteFileEntryType(fileEntryTypeId);
	}

	@Override
	public DLFileEntryType getFileEntryType(long fileEntryTypeId)
		throws PortalException, SystemException {

		DLFileEntryTypePermission.check(
			getPermissionChecker(), fileEntryTypeId, ActionKeys.VIEW);

		return dlFileEntryTypeLocalService.getFileEntryType(fileEntryTypeId);
	}

	@Override
	public List<DLFileEntryType> getFileEntryTypes(long[] groupIds)
		throws SystemException {

		return dlFileEntryTypePersistence.filterFindByGroupId(groupIds);
	}

	@Override
	public int getFileEntryTypesCount(long[] groupIds) throws SystemException {
		return dlFileEntryTypePersistence.filterCountByGroupId(groupIds);
	}

	@Override
	public List<DLFileEntryType> getFolderFileEntryTypes(
			long[] groupIds, long folderId, boolean inherited)
		throws PortalException, SystemException {

		return filterFileEntryTypes(
			dlFileEntryTypeLocalService.getFolderFileEntryTypes(
				groupIds, folderId, inherited));
	}

	@Override
	public List<DLFileEntryType> search(
			long companyId, long[] groupIds, String keywords,
			boolean includeBasicFileEntryType, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return dlFileEntryTypeFinder.filterFindByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType, start,
			end, orderByComparator);
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, String keywords,
			boolean includeBasicFileEntryType)
		throws SystemException {

		return dlFileEntryTypeFinder.filterCountByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType);
	}

	@Override
	public void updateFileEntryType(
			long fileEntryTypeId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long[] ddmStructureIds,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryTypePermission.check(
			getPermissionChecker(), fileEntryTypeId, ActionKeys.UPDATE);

		dlFileEntryTypeLocalService.updateFileEntryType(
			getUserId(), fileEntryTypeId, nameMap, descriptionMap,
			ddmStructureIds, serviceContext);
	}

	public void updateFileEntryType(
			long fileEntryTypeId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.getDefault(), name);

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(LocaleUtil.getDefault(), description);

		updateFileEntryType(
			fileEntryTypeId, nameMap, descriptionMap, ddmStructureIds,
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