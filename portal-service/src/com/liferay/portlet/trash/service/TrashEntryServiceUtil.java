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

package com.liferay.portlet.trash.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the trash entry remote service. This utility wraps {@link com.liferay.portlet.trash.service.impl.TrashEntryServiceImpl} and is the primary access point for service operations in application layer code running on a remote server.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntryService
 * @see com.liferay.portlet.trash.service.base.TrashEntryServiceBaseImpl
 * @see com.liferay.portlet.trash.service.impl.TrashEntryServiceImpl
 * @generated
 */
public class TrashEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.trash.service.impl.TrashEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
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

	/**
	* Deletes the trash entries with the matching group ID considering
	* permissions.
	*
	* @param groupId the primary key of the group
	* @throws PrincipalException if a principal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteEntries(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.security.auth.PrincipalException {
		getService().deleteEntries(groupId);
	}

	/**
	* Deletes the trash entries with the primary keys.
	*
	* @param entryIds the primary keys of the trash entries
	* @throws PortalException if the user didn't have permission to delete one
	or more entries
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteEntries(long[] entryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntries(entryIds);
	}

	/**
	* Deletes the trash entry with the primary key.
	*
	* <p>
	* This method throws a PrincipalException if the user didn't have the
	* permissions to perform the necessary operation. The exception is created
	* with different messages for different operations:
	* </p>
	*
	* <ul>
	* <li>
	* trash.delete.error - if the permission to delete the item was missing
	* </li>
	* </ul>
	*
	* @param entryId the primary key of the trash entry
	* @throws PortalException if the user didn't have permission to delete the
	entry
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(entryId);
	}

	/**
	* Deletes the trash entry with the entity class name and primary key.
	*
	* <p>
	* This method throws a PrincipalException if the user didn't have the
	* permissions to perform the necessary operation. The exception is created
	* with different messages for different operations:
	* </p>
	*
	* <ul>
	* <li>
	* trash.delete.error - if the permission to delete the item was missing
	* </li>
	* </ul>
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @throws PortalException if the user didn't have permission to delete the
	entry
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteEntry(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(className, classPK);
	}

	/**
	* Returns the trash entries with the matching group ID.
	*
	* @param groupId the primary key of the group
	* @return the matching trash entries
	* @throws PrincipalException if a principal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntryList getEntries(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.security.auth.PrincipalException {
		return getService().getEntries(groupId);
	}

	/**
	* Returns a range of all the trash entries matching the group ID.
	*
	* @param groupId the primary key of the group
	* @param start the lower bound of the range of trash entries to return
	* @param end the upper bound of the range of trash entries to return (not
	inclusive)
	* @param obc the comparator to order the trash entries (optionally
	<code>null</code>)
	* @return the range of matching trash entries ordered by comparator
	<code>obc</code>
	* @throws PrincipalException if a system exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntryList getEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.security.auth.PrincipalException {
		return getService().getEntries(groupId, start, end, obc);
	}

	/**
	* Restores the trash entry with the primary key by moving it to a new
	* location identified by destination container model ID.
	*
	* <p>
	* This method throws a PrincipalException if the user didn't have the
	* permissions to perform one of the necessary operations. The exception is
	* created with different messages for different operations:
	* </p>
	*
	* <ul>
	* <li>
	* trash.move.error - if the permission to add the item to the new
	* destination was missing
	* </li>
	* <li>
	* trash.restore.error - if the permission to restore the item from trash
	* was missing
	* </li>
	* </ul>
	*
	* @param className the class name of the entity
	* @param classPK the primary key of the entity
	* @param destinationContainerModelId the primary key of the new location
	* @param serviceContext the service context (optionally <code>null</code>)
	* @throws PortalException if the user didn't have permission to add the
	entry to its new location or to restore it from the trash in
	general
	* @throws SystemException if a system exception occurred
	*/
	public static void moveEntry(java.lang.String className, long classPK,
		long destinationContainerModelId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.moveEntry(className, classPK, destinationContainerModelId,
			serviceContext);
	}

	public static com.liferay.portlet.trash.model.TrashEntry restoreEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().restoreEntry(entryId);
	}

	/**
	* Restores to trash entry to its original location. In case of duplication,
	* on of the optional parameters are set indicating either to overwrite the
	* existing item or to rename the entry before restore.
	*
	* <p>
	* This method throws a PrincipalException if the user didn't have the
	* permissions to perform one of the necessary operations. The exception is
	* created with different messages for different operations:
	* </p>
	*
	* <ul>
	* <li>
	* trash.restore.error - if the permission to restore the item from trash
	* was missing
	* </li>
	* <li>
	* trash.restore.overwrite.error - if the permission to delete the existing
	* item was missing
	* </li>
	* <li>
	* trash.restore.rename.error - if the permission to rename the entry was
	* missing
	* </li>
	* </ul>
	*
	* @param entryId the primary key of the trash entry
	* @param overrideClassPK the primary key of the item to overwrite
	* @param name the new name of the entry (optionally <code>null</code>)
	* @return the trash entry that was restored
	* @throws PortalException if the user didn't have permission to overwrite
	an existing item, to rename the entry or to restore the entry
	from the trash in general
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.trash.model.TrashEntry restoreEntry(
		long entryId, long overrideClassPK, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().restoreEntry(entryId, overrideClassPK, name);
	}

	public static TrashEntryService getService() {
		if (_service == null) {
			_service = (TrashEntryService)PortalBeanLocatorUtil.locate(TrashEntryService.class.getName());

			ReferenceRegistry.registerReference(TrashEntryServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(TrashEntryService service) {
	}

	private static TrashEntryService _service;
}