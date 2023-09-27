/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.service;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DLFileEntryTypeService}.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryTypeService
 * @generated
 */
public class DLFileEntryTypeServiceWrapper
	implements DLFileEntryTypeService, ServiceWrapper<DLFileEntryTypeService> {

	public DLFileEntryTypeServiceWrapper() {
		this(null);
	}

	public DLFileEntryTypeServiceWrapper(
		DLFileEntryTypeService dlFileEntryTypeService) {

		_dlFileEntryTypeService = dlFileEntryTypeService;
	}

	@Override
	public DLFileEntryType addFileEntryType(
			long groupId, long dataDefinitionId, String fileEntryTypeKey,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryTypeService.addFileEntryType(
			groupId, dataDefinitionId, fileEntryTypeKey, nameMap,
			descriptionMap, serviceContext);
	}

	@Override
	public void deleteFileEntryType(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_dlFileEntryTypeService.deleteFileEntryType(fileEntryTypeId);
	}

	@Override
	public DLFileEntryType getFileEntryType(long fileEntryTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryTypeService.getFileEntryType(fileEntryTypeId);
	}

	@Override
	public java.util.List<DLFileEntryType> getFileEntryTypes(long[] groupIds) {
		return _dlFileEntryTypeService.getFileEntryTypes(groupIds);
	}

	@Override
	public java.util.List<DLFileEntryType> getFileEntryTypes(
		long[] groupIds, int start, int end) {

		return _dlFileEntryTypeService.getFileEntryTypes(groupIds, start, end);
	}

	@Override
	public int getFileEntryTypesCount(long[] groupIds) {
		return _dlFileEntryTypeService.getFileEntryTypesCount(groupIds);
	}

	@Override
	public java.util.List<DLFileEntryType> getFolderFileEntryTypes(
			long[] groupIds, long folderId, boolean inherited)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryTypeService.getFolderFileEntryTypes(
			groupIds, folderId, inherited);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _dlFileEntryTypeService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<DLFileEntryType> search(
			long companyId, long folderId, long[] groupIds, String keywords,
			boolean includeBasicFileEntryType, boolean inherited, int start,
			int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryTypeService.search(
			companyId, folderId, groupIds, keywords, includeBasicFileEntryType,
			inherited, start, end);
	}

	@Override
	public java.util.List<DLFileEntryType> search(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int scope, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryType>
			orderByComparator) {

		return _dlFileEntryTypeService.search(
			companyId, groupIds, keywords, includeBasicFileEntryType, scope,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<DLFileEntryType> search(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<DLFileEntryType>
			orderByComparator) {

		return _dlFileEntryTypeService.search(
			companyId, groupIds, keywords, includeBasicFileEntryType, start,
			end, orderByComparator);
	}

	@Override
	public int searchCount(
		long companyId, long folderId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, boolean inherited) {

		return _dlFileEntryTypeService.searchCount(
			companyId, folderId, groupIds, keywords, includeBasicFileEntryType,
			inherited);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType) {

		return _dlFileEntryTypeService.searchCount(
			companyId, groupIds, keywords, includeBasicFileEntryType);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int scope) {

		return _dlFileEntryTypeService.searchCount(
			companyId, groupIds, keywords, includeBasicFileEntryType, scope);
	}

	@Override
	public DLFileEntryType updateFileEntryType(
			long fileEntryTypeId,
			java.util.Map<java.util.Locale, String> nameMap,
			java.util.Map<java.util.Locale, String> descriptionMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _dlFileEntryTypeService.updateFileEntryType(
			fileEntryTypeId, nameMap, descriptionMap);
	}

	@Override
	public DLFileEntryTypeService getWrappedService() {
		return _dlFileEntryTypeService;
	}

	@Override
	public void setWrappedService(
		DLFileEntryTypeService dlFileEntryTypeService) {

		_dlFileEntryTypeService = dlFileEntryTypeService;
	}

	private DLFileEntryTypeService _dlFileEntryTypeService;

}