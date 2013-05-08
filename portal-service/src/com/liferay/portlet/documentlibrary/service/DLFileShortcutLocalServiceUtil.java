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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for DLFileShortcut. This utility wraps
 * {@link com.liferay.portlet.documentlibrary.service.impl.DLFileShortcutLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcutLocalService
 * @see com.liferay.portlet.documentlibrary.service.base.DLFileShortcutLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileShortcutLocalServiceImpl
 * @generated
 */
public class DLFileShortcutLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFileShortcutLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the document library file shortcut to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the document library file shortcut
	* @return the document library file shortcut that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut addDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDLFileShortcut(dlFileShortcut);
	}

	/**
	* Creates a new document library file shortcut with the primary key. Does not add the document library file shortcut to the database.
	*
	* @param fileShortcutId the primary key for the new document library file shortcut
	* @return the new document library file shortcut
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut createDLFileShortcut(
		long fileShortcutId) {
		return getService().createDLFileShortcut(fileShortcutId);
	}

	/**
	* Deletes the document library file shortcut with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut that was removed
	* @throws PortalException if a document library file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut deleteDLFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDLFileShortcut(fileShortcutId);
	}

	/**
	* Deletes the document library file shortcut from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the document library file shortcut
	* @return the document library file shortcut that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut deleteDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteDLFileShortcut(dlFileShortcut);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut fetchDLFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchDLFileShortcut(fileShortcutId);
	}

	/**
	* Returns the document library file shortcut with the primary key.
	*
	* @param fileShortcutId the primary key of the document library file shortcut
	* @return the document library file shortcut
	* @throws PortalException if a document library file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut getDLFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileShortcut(fileShortcutId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the document library file shortcut matching the UUID and group.
	*
	* @param uuid the document library file shortcut's UUID
	* @param groupId the primary key of the group
	* @return the matching document library file shortcut
	* @throws PortalException if a matching document library file shortcut could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut getDLFileShortcutByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileShortcutByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Returns a range of all the document library file shortcuts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of document library file shortcuts
	* @param end the upper bound of the range of document library file shortcuts (not inclusive)
	* @return the range of document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getDLFileShortcuts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileShortcuts(start, end);
	}

	/**
	* Returns the number of document library file shortcuts.
	*
	* @return the number of document library file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public static int getDLFileShortcutsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileShortcutsCount();
	}

	/**
	* Updates the document library file shortcut in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the document library file shortcut
	* @return the document library file shortcut that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut updateDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDLFileShortcut(dlFileShortcut);
	}

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

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		long userId, long groupId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addFileShortcut(userId, groupId, folderId, toFileEntryId,
			serviceContext);
	}

	public static void addFileShortcutResources(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addFileShortcutResources(fileShortcut, addGroupPermissions,
			addGuestPermissions);
	}

	public static void addFileShortcutResources(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addFileShortcutResources(fileShortcut, groupPermissions,
			guestPermissions);
	}

	public static void addFileShortcutResources(long fileShortcutId,
		boolean addGroupPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addFileShortcutResources(fileShortcutId, addGroupPermissions,
			addGuestPermissions);
	}

	public static void addFileShortcutResources(long fileShortcutId,
		java.lang.String[] groupPermissions, java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addFileShortcutResources(fileShortcutId, groupPermissions,
			guestPermissions);
	}

	public static void deleteFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileShortcut(fileShortcut);
	}

	public static void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileShortcut(fileShortcutId);
	}

	public static void deleteFileShortcuts(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileShortcuts(toFileEntryId);
	}

	public static void deleteFileShortcuts(long groupId, long folderId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteFileShortcuts(groupId, folderId);
	}

	public static void deleteFileShortcuts(long groupId, long folderId,
		boolean includeTrashedEntries)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteFileShortcuts(groupId, folderId, includeTrashedEntries);
	}

	public static void disableFileShortcuts(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().disableFileShortcuts(toFileEntryId);
	}

	public static void enableFileShortcuts(long toFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().enableFileShortcuts(toFileEntryId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileShortcut(fileShortcutId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, boolean active, int status, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileShortcuts(groupId, folderId, active, status, start,
			end);
	}

	public static int getFileShortcutsCount(long groupId, long folderId,
		boolean active, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getFileShortcutsCount(groupId, folderId, active, status);
	}

	public static void updateAsset(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateAsset(userId, fileShortcut, assetCategoryIds, assetTagNames);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		long userId, long fileShortcutId, long folderId, long toFileEntryId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateFileShortcut(userId, fileShortcutId, folderId,
			toFileEntryId, serviceContext);
	}

	public static void updateFileShortcuts(long oldToFileEntryId,
		long newToFileEntryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().updateFileShortcuts(oldToFileEntryId, newToFileEntryId);
	}

	public static void updateStatus(long userId, long fileShortcutId,
		int status, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateStatus(userId, fileShortcutId, status, serviceContext);
	}

	public static DLFileShortcutLocalService getService() {
		if (_service == null) {
			_service = (DLFileShortcutLocalService)PortalBeanLocatorUtil.locate(DLFileShortcutLocalService.class.getName());

			ReferenceRegistry.registerReference(DLFileShortcutLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(DLFileShortcutLocalService service) {
	}

	private static DLFileShortcutLocalService _service;
}