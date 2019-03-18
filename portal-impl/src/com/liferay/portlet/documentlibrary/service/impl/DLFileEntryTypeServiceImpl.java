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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portlet.documentlibrary.constants.DLConstants;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryTypeServiceBaseImpl;

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
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId, ActionKeys.ADD_DOCUMENT_TYPE);

		return dlFileEntryTypeLocalService.addFileEntryType(
			getUserId(), groupId, fileEntryTypeKey, nameMap, descriptionMap,
			ddmStructureIds, serviceContext);
	}

	@Override
	public DLFileEntryType addFileEntryType(
			long groupId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), name);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.getSiteDefault(), description);

		return addFileEntryType(
			groupId, null, nameMap, descriptionMap, ddmStructureIds,
			serviceContext);
	}

	@Override
	public void deleteFileEntryType(long fileEntryTypeId)
		throws PortalException {

		_dlFileEntryTypeModelResourcePermission.check(
			getPermissionChecker(), fileEntryTypeId, ActionKeys.DELETE);

		dlFileEntryTypeLocalService.deleteFileEntryType(fileEntryTypeId);
	}

	@Override
	public DLFileEntryType getFileEntryType(long fileEntryTypeId)
		throws PortalException {

		_dlFileEntryTypeModelResourcePermission.check(
			getPermissionChecker(), fileEntryTypeId, ActionKeys.VIEW);

		return dlFileEntryTypeLocalService.getFileEntryType(fileEntryTypeId);
	}

	@Override
	public List<DLFileEntryType> getFileEntryTypes(long[] groupIds) {
		return dlFileEntryTypePersistence.filterFindByGroupId(groupIds);
	}

	@Override
	public List<DLFileEntryType> getFileEntryTypes(
		long[] groupIds, int start, int end) {

		return dlFileEntryTypePersistence.filterFindByGroupId(
			groupIds, start, end);
	}

	@Override
	public int getFileEntryTypesCount(long[] groupIds) {
		return dlFileEntryTypePersistence.filterCountByGroupId(groupIds);
	}

	@Override
	public List<DLFileEntryType> getFolderFileEntryTypes(
			long[] groupIds, long folderId, boolean inherited)
		throws PortalException {

		return filterFileEntryTypes(
			dlFileEntryTypeLocalService.getFolderFileEntryTypes(
				groupIds, folderId, inherited));
	}

	@Override
	public List<DLFileEntryType> search(
			long companyId, long folderId, long[] groupIds, String keywords,
			boolean includeBasicFileEntryType, boolean inherited, int start,
			int end)
		throws PortalException {

		return dlFileEntryTypeFinder.filterFindByKeywords(
			companyId, folderId, groupIds, keywords, includeBasicFileEntryType,
			inherited, start, end);
	}

	@Override
	public List<DLFileEntryType> search(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {

		return dlFileEntryTypeFinder.filterFindByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType, start,
			end, orderByComparator);
	}

	@Override
	public int searchCount(
		long companyId, long folderId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, boolean inherited) {

		return dlFileEntryTypeFinder.filterCountByKeywords(
			companyId, folderId, groupIds, keywords, includeBasicFileEntryType,
			inherited);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType) {

		return dlFileEntryTypeFinder.filterCountByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType);
	}

	@Override
	public void updateFileEntryType(
			long fileEntryTypeId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long[] ddmStructureIds,
			ServiceContext serviceContext)
		throws PortalException {

		_dlFileEntryTypeModelResourcePermission.check(
			getPermissionChecker(), fileEntryTypeId, ActionKeys.UPDATE);

		dlFileEntryTypeLocalService.updateFileEntryType(
			getUserId(), fileEntryTypeId, nameMap, descriptionMap,
			ddmStructureIds, serviceContext);
	}

	@Override
	public void updateFileEntryType(
			long fileEntryTypeId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), name);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.getSiteDefault(), description);

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
				!_dlFileEntryTypeModelResourcePermission.contains(
					permissionChecker, fileEntryType, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return fileEntryTypes;
	}

	private static volatile ModelResourcePermission<DLFileEntryType>
		_dlFileEntryTypeModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				DLFileEntryTypeServiceImpl.class,
				"_dlFileEntryTypeModelResourcePermission",
				DLFileEntryType.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				DLFileEntryTypeServiceImpl.class, "_portletResourcePermission",
				DLConstants.RESOURCE_NAME);

}