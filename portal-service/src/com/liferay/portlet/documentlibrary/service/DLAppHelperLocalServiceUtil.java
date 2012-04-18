/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d l app helper local service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.impl.DLAppHelperLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLAppHelperLocalService
 * @see com.liferay.portlet.documentlibrary.service.base.DLAppHelperLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLAppHelperLocalServiceImpl
 * @generated
 */
public class DLAppHelperLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLAppHelperLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static void addFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addFileEntry(userId, fileEntry, fileVersion, serviceContext);
	}

	public static void addFolder(
		com.liferay.portal.kernel.repository.model.Folder folder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().addFolder(folder, serviceContext);
	}

	public static void checkAssetEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().checkAssetEntry(userId, fileEntry, fileVersion);
	}

	public static void deleteFileEntry(
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileEntry(fileEntry);
	}

	public static void deleteFolder(
		com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFolder(folder);
	}

	public static void getFileAsStream(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		boolean incrementCounter)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().getFileAsStream(userId, fileEntry, incrementCounter);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileShortcuts(groupId, folderId, status);
	}

	public static int getFileShortcutsCount(long groupId, long folderId,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileShortcutsCount(groupId, folderId, status);
	}

	public static java.util.List<com.liferay.portal.kernel.repository.model.FileEntry> getNoAssetFileEntries() {
		return getService().getNoAssetFileEntries();
	}

	public static void moveFileEntry(
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().moveFileEntry(fileEntry);
	}

	public static com.liferay.portal.kernel.repository.model.FileEntry moveFileEntryToTrash(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().moveFileEntryToTrash(userId, fileEntry);
	}

	public static void moveFolder(
		com.liferay.portal.kernel.repository.model.Folder folder)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().moveFolder(folder);
	}

	public static void restoreFileEntryFromTrash(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().restoreFileEntryFromTrash(userId, fileEntry);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateAsset(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long assetClassPk)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateAsset(userId, fileEntry, fileVersion, assetClassPk);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateAsset(
		long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long[] assetCategoryIds, java.lang.String[] assetTagNames,
		long[] assetLinkEntryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateAsset(userId, fileEntry, fileVersion,
			assetCategoryIds, assetTagNames, assetLinkEntryIds);
	}

	public static void updateFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		long assetClassPk)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateFileEntry(userId, fileEntry, fileVersion, assetClassPk);
	}

	public static void updateFileEntry(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion fileVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateFileEntry(userId, fileEntry, fileVersion, serviceContext);
	}

	public static void updateFolder(
		com.liferay.portal.kernel.repository.model.Folder folder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateFolder(folder, serviceContext);
	}

	public static void updateStatus(long userId,
		com.liferay.portal.kernel.repository.model.FileEntry fileEntry,
		com.liferay.portal.kernel.repository.model.FileVersion latestFileVersion,
		int oldStatus, int newStatus,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateStatus(userId, fileEntry, latestFileVersion, oldStatus,
			newStatus, workflowContext);
	}

	public static DLAppHelperLocalService getService() {
		if (_service == null) {
			_service = (DLAppHelperLocalService)PortalBeanLocatorUtil.locate(DLAppHelperLocalService.class.getName());

			ReferenceRegistry.registerReference(DLAppHelperLocalServiceUtil.class,
				"_service");
			MethodCache.remove(DLAppHelperLocalService.class);
		}

		return _service;
	}

	public void setService(DLAppHelperLocalService service) {
		MethodCache.remove(DLAppHelperLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(DLAppHelperLocalServiceUtil.class,
			"_service");
		MethodCache.remove(DLAppHelperLocalService.class);
	}

	private static DLAppHelperLocalService _service;
}