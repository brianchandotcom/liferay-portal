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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.security.ac.AccessControlled;
import com.liferay.portal.service.BaseService;

/**
 * The interface for the trash entry remote service.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntryServiceUtil
 * @see com.liferay.portlet.trash.service.base.TrashEntryServiceBaseImpl
 * @see com.liferay.portlet.trash.service.impl.TrashEntryServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface TrashEntryService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link TrashEntryServiceUtil} to access the trash entry remote service. Add custom service methods to {@link com.liferay.portlet.trash.service.impl.TrashEntryServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	/**
	* Deletes the trash entries with the matching group ID considering
	* permissions.
	*
	* @param groupId the primary key of the group
	* @throws PrincipalException if a principal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	public void deleteEntries(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.security.auth.PrincipalException;

	/**
	* Deletes the trash entries with the primary keys.
	*
	* @param entryIds the primary keys of the trash entries
	* @throws PortalException if the user didn't have permission to delete one
	or more entries
	* @throws SystemException if a system exception occurred
	*/
	public void deleteEntries(long[] entryIds)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

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
	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

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
	public void deleteEntry(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the trash entries with the matching group ID.
	*
	* @param groupId the primary key of the group
	* @return the matching trash entries
	* @throws PrincipalException if a principal exception occurred
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.trash.model.TrashEntryList getEntries(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.security.auth.PrincipalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.trash.model.TrashEntryList getEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portal.security.auth.PrincipalException;

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
	public void moveEntry(java.lang.String className, long classPK,
		long destinationContainerModelId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.trash.model.TrashEntry restoreEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

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
	public com.liferay.portlet.trash.model.TrashEntry restoreEntry(
		long entryId, long overrideClassPK, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}