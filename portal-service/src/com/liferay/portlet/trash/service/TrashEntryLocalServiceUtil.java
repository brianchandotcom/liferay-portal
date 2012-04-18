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

package com.liferay.portlet.trash.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the trash entry local service. This utility wraps {@link com.liferay.portlet.trash.service.impl.TrashEntryLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntryLocalService
 * @see com.liferay.portlet.trash.service.base.TrashEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.trash.service.impl.TrashEntryLocalServiceImpl
 * @generated
 */
public class TrashEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.trash.service.impl.TrashEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the trash entry to the database. Also notifies the appropriate model listeners.
	*
	* @param trashEntry the trash entry
	* @return the trash entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntry addTrashEntry(
		com.liferay.portlet.trash.model.TrashEntry trashEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addTrashEntry(trashEntry);
	}

	/**
	* Creates a new trash entry with the primary key. Does not add the trash entry to the database.
	*
	* @param entryId the primary key for the new trash entry
	* @return the new trash entry
	*/
	public static com.liferay.portlet.trash.model.TrashEntry createTrashEntry(
		long entryId) {
		return getService().createTrashEntry(entryId);
	}

	/**
	* Deletes the trash entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the trash entry
	* @return the trash entry that was removed
	* @throws PortalException if a trash entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntry deleteTrashEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteTrashEntry(entryId);
	}

	/**
	* Deletes the trash entry from the database. Also notifies the appropriate model listeners.
	*
	* @param trashEntry the trash entry
	* @return the trash entry that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntry deleteTrashEntry(
		com.liferay.portlet.trash.model.TrashEntry trashEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteTrashEntry(trashEntry);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
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

	public static com.liferay.portlet.trash.model.TrashEntry fetchTrashEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchTrashEntry(entryId);
	}

	/**
	* Returns the trash entry with the primary key.
	*
	* @param entryId the primary key of the trash entry
	* @return the trash entry
	* @throws PortalException if a trash entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntry getTrashEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTrashEntry(entryId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the trash entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of trash entries
	* @param end the upper bound of the range of trash entries (not inclusive)
	* @return the range of trash entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.trash.model.TrashEntry> getTrashEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTrashEntries(start, end);
	}

	/**
	* Returns the number of trash entries.
	*
	* @return the number of trash entries
	* @throws SystemException if a system exception occurred
	*/
	public static int getTrashEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTrashEntriesCount();
	}

	/**
	* Updates the trash entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param trashEntry the trash entry
	* @return the trash entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntry updateTrashEntry(
		com.liferay.portlet.trash.model.TrashEntry trashEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTrashEntry(trashEntry);
	}

	/**
	* Updates the trash entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param trashEntry the trash entry
	* @param merge whether to merge the trash entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the trash entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntry updateTrashEntry(
		com.liferay.portlet.trash.model.TrashEntry trashEntry, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTrashEntry(trashEntry, merge);
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

	/**
	* Moves an entry to trash.
	*
	* @param companyId the primary key of the entry's company
	* @param groupId the primary key of the entry's group
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @param status the status of the entityy prior to being moved to trash
	* @param typeSettingsProperties the type settings properties
	* @return the trashEntry
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntry addTrashEntry(
		long companyId, long groupId, java.lang.String className, long classPK,
		int status,
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addTrashEntry(companyId, groupId, className, classPK,
			status, typeSettingsProperties);
	}

	/**
	* Deletes the trash entry with the entity class name and primary key.
	*
	* @param className the class name of entity
	* @param classPK the primary key of the entry
	* @throws PortalException if the user did not have permission to delete the entry
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteEntry(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(className, classPK);
	}

	/**
	* Returns the trash entry with the primary key.
	*
	* @param entryId the primary key of the entry
	* @return the trash entry with the primary key
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntry fetchEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchEntry(entryId);
	}

	/**
	* Returns the trash entry with the entity class name and primary key.
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @return the trash entry with the entity class name and primary key
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntry fetchEntry(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchEntry(className, classPK);
	}

	/**
	* Returns the trash entries with the matching group ID.
	*
	* @param groupId the primary key of the group
	* @return the trash entries with the group ID
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.trash.model.TrashEntry> getEntries(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(groupId);
	}

	/**
	* Returns a range of all the trash entries matching the group ID.
	*
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of trash entries to return
	* @param end the upper bound of the range of trash entries to return (not
	inclusive)
	* @return the range of matching trash entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.trash.model.TrashEntry> getEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(groupId, start, end);
	}

	/**
	* Returns the number of trash entries with the group ID.
	*
	* @param groupId the primary key of the group
	* @return the number of matching trash entries
	* @throws SystemException if a system exception occurred
	*/
	public static int getEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntriesCount(groupId);
	}

	/**
	* Returns the trash entry with the primary key.
	*
	* @param entryId the primary key of the trash entry
	* @return the trash entry with the primary key
	* @throws PortalException if a portal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(entryId);
	}

	/**
	* Returns the entry with the entity class name and primary key.
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @return the trash entry with the entity class name and primary key
	* @throws PortalException if a trash entry with the primary key could not
	be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntry getEntry(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(className, classPK);
	}

	public static TrashEntryLocalService getService() {
		if (_service == null) {
			_service = (TrashEntryLocalService)PortalBeanLocatorUtil.locate(TrashEntryLocalService.class.getName());

			ReferenceRegistry.registerReference(TrashEntryLocalServiceUtil.class,
				"_service");
			MethodCache.remove(TrashEntryLocalService.class);
		}

		return _service;
	}

	public void setService(TrashEntryLocalService service) {
		MethodCache.remove(TrashEntryLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(TrashEntryLocalServiceUtil.class,
			"_service");
		MethodCache.remove(TrashEntryLocalService.class);
	}

	private static TrashEntryLocalService _service;
}