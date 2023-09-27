/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.trash.service;

import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.exception.TrashPermissionException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.model.TrashEntryList;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for TrashEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see TrashEntryServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface TrashEntryService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.trash.service.impl.TrashEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the trash entry remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link TrashEntryServiceUtil} if injection and service tracking are not available.
	 */

	/**
	 * Deletes the trash entries with the matching group ID considering
	 * permissions.
	 *
	 * @param groupId the primary key of the group
	 */
	@Transactional(noRollbackFor = TrashPermissionException.class)
	public void deleteEntries(long groupId) throws PortalException;

	/**
	 * Deletes the trash entries with the primary keys.
	 *
	 * @param entryIds the primary keys of the trash entries
	 */
	@Transactional(noRollbackFor = TrashPermissionException.class)
	public void deleteEntries(long[] entryIds) throws PortalException;

	/**
	 * Deletes the trash entry with the primary key.
	 *
	 * <p>
	 * This method throws a {@link TrashPermissionException} with type {@link
	 * TrashPermissionException#DELETE} if the user did not have permission to
	 * delete the trash entry.
	 * </p>
	 *
	 * @param entryId the primary key of the trash entry
	 */
	public void deleteEntry(long entryId) throws PortalException;

	/**
	 * Deletes the trash entry with the entity class name and class primary key.
	 *
	 * <p>
	 * This method throws a {@link TrashPermissionException} with type {@link
	 * TrashPermissionException#DELETE} if the user did not have permission to
	 * delete the trash entry.
	 * </p>
	 *
	 * @param className the class name of the entity
	 * @param classPK the primary key of the entity
	 */
	public void deleteEntry(String className, long classPK)
		throws PortalException;

	/**
	 * Returns the trash entries with the matching group ID.
	 *
	 * @param groupId the primary key of the group
	 * @return the matching trash entries
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TrashEntryList getEntries(long groupId) throws PrincipalException;

	/**
	 * Returns a range of all the trash entries matching the group ID.
	 *
	 * @param groupId the primary key of the group
	 * @param start the lower bound of the range of trash entries to return
	 * @param end the upper bound of the range of trash entries to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the trash entries
	 (optionally <code>null</code>)
	 * @return the range of matching trash entries ordered by comparator
	 <code>orderByComparator</code>
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TrashEntryList getEntries(
			long groupId, int start, int end,
			OrderByComparator<TrashEntry> orderByComparator)
		throws PrincipalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<TrashEntry> getEntries(long groupId, String className)
		throws PrincipalException;

	/**
	 * Returns a range of all the trash entries matching the group ID.
	 *
	 * @param groupId the primary key of the group
	 * @param className the class name of the entity
	 * @param start the lower bound of the range of trash entries to return
	 * @param end the upper bound of the range of trash entries to return (not
	 inclusive)
	 * @param orderByComparator the comparator to order the trash entries
	 (optionally <code>null</code>)
	 * @return the range of matching trash entries ordered by comparator
	 <code>orderByComparator</code>
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public TrashEntryList getEntries(
			long groupId, String className, int start, int end,
			OrderByComparator<TrashEntry> orderByComparator)
		throws PrincipalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	/**
	 * Moves the trash entry with the entity class name and primary key,
	 * restoring it to a new location identified by the destination container
	 * model ID.
	 *
	 * <p>
	 * This method throws a {@link TrashPermissionException} if the user did not
	 * have the permission to perform one of the necessary operations. The
	 * exception is created with a type specific to the operation:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * {@link TrashPermissionException#MOVE} - if the user did not have
	 * permission to move the trash entry to the new
	 * destination
	 * </li>
	 * <li>
	 * {@link TrashPermissionException#RESTORE} - if the user did not have
	 * permission to restore the trash entry
	 * </li>
	 * </ul>
	 *
	 * @param className the class name of the entity
	 * @param classPK the primary key of the entity
	 * @param destinationContainerModelId the primary key of the new location
	 * @param serviceContext the service context to be applied (optionally
	 <code>null</code>)
	 */
	public void moveEntry(
			String className, long classPK, long destinationContainerModelId,
			ServiceContext serviceContext)
		throws PortalException;

	public TrashEntry restoreEntry(long entryId) throws PortalException;

	/**
	 * Restores the trash entry to its original location. In order to handle a
	 * duplicate trash entry already existing at the original location, either
	 * pass in the primary key of the existing trash entry's entity to overwrite
	 * or pass in a new name to give to the trash entry being restored.
	 *
	 * <p>
	 * This method throws a {@link TrashPermissionException} if the user did not
	 * have the permission to perform one of the necessary operations. The
	 * exception is created with a type specific to the operation:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * {@link TrashPermissionException#RESTORE} - if the user did not have
	 * permission to restore the trash entry
	 * </li>
	 * <li>
	 * {@link TrashPermissionException#RESTORE_OVERWRITE} - if the user did not
	 * have permission to delete the existing trash entry
	 * </li>
	 * <li>
	 * {@link TrashPermissionException#RESTORE_RENAME} - if the user did not
	 * have permission to rename the trash entry
	 * </li>
	 * </ul>
	 *
	 * @param entryId the primary key of the trash entry to restore
	 * @param overrideClassPK the primary key of the entity to overwrite
	 (optionally <code>0</code>)
	 * @param name a new name to give to the trash entry being restored
	 (optionally <code>null</code>)
	 * @return the restored trash entry
	 */
	public TrashEntry restoreEntry(
			long entryId, long overrideClassPK, String name)
		throws PortalException;

	public TrashEntry restoreEntry(String className, long classPK)
		throws PortalException;

	public TrashEntry restoreEntry(
			String className, long classPK, long overrideClassPK, String name)
		throws PortalException;

}