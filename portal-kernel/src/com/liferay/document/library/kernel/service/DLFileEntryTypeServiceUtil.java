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

package com.liferay.document.library.kernel.service;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for DLFileEntryType. This utility wraps
 * <code>com.liferay.portlet.documentlibrary.service.impl.DLFileEntryTypeServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryTypeService
 * @generated
 */
public class DLFileEntryTypeServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.documentlibrary.service.impl.DLFileEntryTypeServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static DLFileEntryType addFileEntryType(
			long groupId, long dataDefinitionId, String fileEntryTypeKey,
			Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFileEntryType(
			groupId, dataDefinitionId, fileEntryTypeKey, nameMap,
			descriptionMap, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addFileEntryType(long, String, Map, Map, long,
	 ServiceContext)}
	 */
	@Deprecated
	public static DLFileEntryType addFileEntryType(
			long groupId, String fileEntryTypeKey,
			Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap,
			long[] ddmStructureIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFileEntryType(
			groupId, fileEntryTypeKey, nameMap, descriptionMap, ddmStructureIds,
			serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #addFileEntryType(long, String, Map, Map, long,
	 ServiceContext)}
	 */
	@Deprecated
	public static DLFileEntryType addFileEntryType(
			long groupId, String name, String description,
			long[] ddmStructureIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFileEntryType(
			groupId, name, description, ddmStructureIds, serviceContext);
	}

	public static void deleteFileEntryType(long fileEntryTypeId)
		throws PortalException {

		getService().deleteFileEntryType(fileEntryTypeId);
	}

	public static DLFileEntryType getFileEntryType(long fileEntryTypeId)
		throws PortalException {

		return getService().getFileEntryType(fileEntryTypeId);
	}

	public static List<DLFileEntryType> getFileEntryTypes(long[] groupIds) {
		return getService().getFileEntryTypes(groupIds);
	}

	public static List<DLFileEntryType> getFileEntryTypes(
		long[] groupIds, int start, int end) {

		return getService().getFileEntryTypes(groupIds, start, end);
	}

	public static int getFileEntryTypesCount(long[] groupIds) {
		return getService().getFileEntryTypesCount(groupIds);
	}

	public static List<DLFileEntryType> getFolderFileEntryTypes(
			long[] groupIds, long folderId, boolean inherited)
		throws PortalException {

		return getService().getFolderFileEntryTypes(
			groupIds, folderId, inherited);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<DLFileEntryType> search(
			long companyId, long folderId, long[] groupIds, String keywords,
			boolean includeBasicFileEntryType, boolean inherited, int start,
			int end)
		throws PortalException {

		return getService().search(
			companyId, folderId, groupIds, keywords, includeBasicFileEntryType,
			inherited, start, end);
	}

	public static List<DLFileEntryType> search(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int scope, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {

		return getService().search(
			companyId, groupIds, keywords, includeBasicFileEntryType, scope,
			start, end, orderByComparator);
	}

	public static List<DLFileEntryType> search(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {

		return getService().search(
			companyId, groupIds, keywords, includeBasicFileEntryType, start,
			end, orderByComparator);
	}

	public static int searchCount(
		long companyId, long folderId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, boolean inherited) {

		return getService().searchCount(
			companyId, folderId, groupIds, keywords, includeBasicFileEntryType,
			inherited);
	}

	public static int searchCount(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType) {

		return getService().searchCount(
			companyId, groupIds, keywords, includeBasicFileEntryType);
	}

	public static int searchCount(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int scope) {

		return getService().searchCount(
			companyId, groupIds, keywords, includeBasicFileEntryType, scope);
	}

	public static DLFileEntryType updateFileEntryType(
			long fileEntryTypeId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap)
		throws PortalException {

		return getService().updateFileEntryType(
			fileEntryTypeId, nameMap, descriptionMap);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateFileEntryType(long, Map, Map)}
	 */
	@Deprecated
	public static void updateFileEntryType(
			long fileEntryTypeId, Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap,
			long[] ddmStructureIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().updateFileEntryType(
			fileEntryTypeId, nameMap, descriptionMap, ddmStructureIds,
			serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 #updateFileEntryType(long, Map, Map)}
	 */
	@Deprecated
	public static void updateFileEntryType(
			long fileEntryTypeId, String name, String description,
			long[] ddmStructureIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().updateFileEntryType(
			fileEntryTypeId, name, description, ddmStructureIds,
			serviceContext);
	}

	public static DLFileEntryTypeService getService() {
		return _service;
	}

	public static void setService(DLFileEntryTypeService service) {
		_service = service;
	}

	private static volatile DLFileEntryTypeService _service;

}