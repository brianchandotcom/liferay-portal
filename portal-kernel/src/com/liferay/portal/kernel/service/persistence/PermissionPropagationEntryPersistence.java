/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence;

import com.liferay.portal.kernel.exception.NoSuchPermissionPropagationEntryException;
import com.liferay.portal.kernel.model.PermissionPropagationEntry;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the permission propagation entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PermissionPropagationEntryUtil
 * @generated
 */
@ProviderType
public interface PermissionPropagationEntryPersistence
	extends BasePersistence<PermissionPropagationEntry>,
			CTPersistence<PermissionPropagationEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PermissionPropagationEntryUtil} to access the permission propagation entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the permission propagation entry where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchPermissionPropagationEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching permission propagation entry
	 * @throws NoSuchPermissionPropagationEntryException if a matching permission propagation entry could not be found
	 */
	public PermissionPropagationEntry findByG_C_C_C(
			long groupId, long companyId, long classNameId, long classPK)
		throws NoSuchPermissionPropagationEntryException;

	/**
	 * Returns the permission propagation entry where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching permission propagation entry, or <code>null</code> if a matching permission propagation entry could not be found
	 */
	public PermissionPropagationEntry fetchByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK);

	/**
	 * Returns the permission propagation entry where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching permission propagation entry, or <code>null</code> if a matching permission propagation entry could not be found
	 */
	public PermissionPropagationEntry fetchByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK,
		boolean useFinderCache);

	/**
	 * Removes the permission propagation entry where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the permission propagation entry that was removed
	 */
	public PermissionPropagationEntry removeByG_C_C_C(
			long groupId, long companyId, long classNameId, long classPK)
		throws NoSuchPermissionPropagationEntryException;

	/**
	 * Returns the number of permission propagation entries where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching permission propagation entries
	 */
	public int countByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK);

	/**
	 * Caches the permission propagation entry in the entity cache if it is enabled.
	 *
	 * @param permissionPropagationEntry the permission propagation entry
	 */
	public void cacheResult(
		PermissionPropagationEntry permissionPropagationEntry);

	/**
	 * Caches the permission propagation entries in the entity cache if it is enabled.
	 *
	 * @param permissionPropagationEntries the permission propagation entries
	 */
	public void cacheResult(
		java.util.List<PermissionPropagationEntry>
			permissionPropagationEntries);

	/**
	 * Creates a new permission propagation entry with the primary key. Does not add the permission propagation entry to the database.
	 *
	 * @param permissionPropagationEntryId the primary key for the new permission propagation entry
	 * @return the new permission propagation entry
	 */
	public PermissionPropagationEntry create(long permissionPropagationEntryId);

	/**
	 * Removes the permission propagation entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param permissionPropagationEntryId the primary key of the permission propagation entry
	 * @return the permission propagation entry that was removed
	 * @throws NoSuchPermissionPropagationEntryException if a permission propagation entry with the primary key could not be found
	 */
	public PermissionPropagationEntry remove(long permissionPropagationEntryId)
		throws NoSuchPermissionPropagationEntryException;

	public PermissionPropagationEntry updateImpl(
		PermissionPropagationEntry permissionPropagationEntry);

	/**
	 * Returns the permission propagation entry with the primary key or throws a <code>NoSuchPermissionPropagationEntryException</code> if it could not be found.
	 *
	 * @param permissionPropagationEntryId the primary key of the permission propagation entry
	 * @return the permission propagation entry
	 * @throws NoSuchPermissionPropagationEntryException if a permission propagation entry with the primary key could not be found
	 */
	public PermissionPropagationEntry findByPrimaryKey(
			long permissionPropagationEntryId)
		throws NoSuchPermissionPropagationEntryException;

	/**
	 * Returns the permission propagation entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param permissionPropagationEntryId the primary key of the permission propagation entry
	 * @return the permission propagation entry, or <code>null</code> if a permission propagation entry with the primary key could not be found
	 */
	public PermissionPropagationEntry fetchByPrimaryKey(
		long permissionPropagationEntryId);

	/**
	 * Returns all the permission propagation entries.
	 *
	 * @return the permission propagation entries
	 */
	public java.util.List<PermissionPropagationEntry> findAll();

	/**
	 * Returns a range of all the permission propagation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagation entries
	 * @param end the upper bound of the range of permission propagation entries (not inclusive)
	 * @return the range of permission propagation entries
	 */
	public java.util.List<PermissionPropagationEntry> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the permission propagation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagation entries
	 * @param end the upper bound of the range of permission propagation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of permission propagation entries
	 */
	public java.util.List<PermissionPropagationEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<PermissionPropagationEntry> orderByComparator);

	/**
	 * Returns an ordered range of all the permission propagation entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagation entries
	 * @param end the upper bound of the range of permission propagation entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of permission propagation entries
	 */
	public java.util.List<PermissionPropagationEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<PermissionPropagationEntry> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the permission propagation entries from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of permission propagation entries.
	 *
	 * @return the number of permission propagation entries
	 */
	public int countAll();

}