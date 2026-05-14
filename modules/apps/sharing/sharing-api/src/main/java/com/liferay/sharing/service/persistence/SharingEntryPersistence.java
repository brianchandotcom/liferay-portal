/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.service.persistence;

import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.sharing.exception.NoSuchEntryException;
import com.liferay.sharing.model.SharingEntry;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the sharing entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SharingEntryUtil
 * @generated
 */
@ProviderType
public interface SharingEntryPersistence extends BasePersistence<SharingEntry> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SharingEntryUtil} to access the sharing entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns an ordered range of all the sharing entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	public java.util.List<SharingEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sharing entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	public SharingEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sharing entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public SharingEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator);

	/**
	 * Removes all the sharing entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of sharing entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching sharing entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the sharing entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	public SharingEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the sharing entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public SharingEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the sharing entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the sharing entry that was removed
	 */
	public SharingEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of sharing entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching sharing entries
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns an ordered range of all the sharing entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	public java.util.List<SharingEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	public SharingEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public SharingEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator);

	/**
	 * Removes all the sharing entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of sharing entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching sharing entries
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns an ordered range of all the sharing entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	public java.util.List<SharingEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sharing entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	public SharingEntry findByGroupId_First(
			long groupId,
			com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sharing entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public SharingEntry fetchByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator);

	/**
	 * Removes all the sharing entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	public void removeByGroupId(long groupId);

	/**
	 * Returns the number of sharing entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching sharing entries
	 */
	public int countByGroupId(long groupId);

	/**
	 * Returns an ordered range of all the sharing entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	public java.util.List<SharingEntry> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sharing entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	public SharingEntry findByUserId_First(
			long userId,
			com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sharing entry in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public SharingEntry fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator);

	/**
	 * Removes all the sharing entries where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 */
	public void removeByUserId(long userId);

	/**
	 * Returns the number of sharing entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching sharing entries
	 */
	public int countByUserId(long userId);

	/**
	 * Returns an ordered range of all the sharing entries where toTicketId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toTicketId the to ticket ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	public java.util.List<SharingEntry> findByToTicketId(
		long toTicketId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sharing entry in the ordered set where toTicketId = &#63;.
	 *
	 * @param toTicketId the to ticket ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	public SharingEntry findByToTicketId_First(
			long toTicketId,
			com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sharing entry in the ordered set where toTicketId = &#63;.
	 *
	 * @param toTicketId the to ticket ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public SharingEntry fetchByToTicketId_First(
		long toTicketId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator);

	/**
	 * Removes all the sharing entries where toTicketId = &#63; from the database.
	 *
	 * @param toTicketId the to ticket ID
	 */
	public void removeByToTicketId(long toTicketId);

	/**
	 * Returns the number of sharing entries where toTicketId = &#63;.
	 *
	 * @param toTicketId the to ticket ID
	 * @return the number of matching sharing entries
	 */
	public int countByToTicketId(long toTicketId);

	/**
	 * Returns an ordered range of all the sharing entries where toUserGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toUserGroupId the to user group ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	public java.util.List<SharingEntry> findByToUserGroupId(
		long toUserGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sharing entry in the ordered set where toUserGroupId = &#63;.
	 *
	 * @param toUserGroupId the to user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	public SharingEntry findByToUserGroupId_First(
			long toUserGroupId,
			com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sharing entry in the ordered set where toUserGroupId = &#63;.
	 *
	 * @param toUserGroupId the to user group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public SharingEntry fetchByToUserGroupId_First(
		long toUserGroupId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator);

	/**
	 * Removes all the sharing entries where toUserGroupId = &#63; from the database.
	 *
	 * @param toUserGroupId the to user group ID
	 */
	public void removeByToUserGroupId(long toUserGroupId);

	/**
	 * Returns the number of sharing entries where toUserGroupId = &#63;.
	 *
	 * @param toUserGroupId the to user group ID
	 * @return the number of matching sharing entries
	 */
	public int countByToUserGroupId(long toUserGroupId);

	/**
	 * Returns an ordered range of all the sharing entries where toUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	public java.util.List<SharingEntry> findByToUserId(
		long toUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sharing entry in the ordered set where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	public SharingEntry findByToUserId_First(
			long toUserId,
			com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sharing entry in the ordered set where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public SharingEntry fetchByToUserId_First(
		long toUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator);

	/**
	 * Removes all the sharing entries where toUserId = &#63; from the database.
	 *
	 * @param toUserId the to user ID
	 */
	public void removeByToUserId(long toUserId);

	/**
	 * Returns the number of sharing entries where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @return the number of matching sharing entries
	 */
	public int countByToUserId(long toUserId);

	/**
	 * Returns all the sharing entries where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the matching sharing entries
	 */
	public java.util.List<SharingEntry> findByLtExpirationDate(
		Date expirationDate);

	/**
	 * Returns a range of all the sharing entries where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	public java.util.List<SharingEntry> findByLtExpirationDate(
		Date expirationDate, int start, int end);

	/**
	 * Returns an ordered range of all the sharing entries where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	public java.util.List<SharingEntry> findByLtExpirationDate(
		Date expirationDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the sharing entries where expirationDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	public java.util.List<SharingEntry> findByLtExpirationDate(
		Date expirationDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sharing entry in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	public SharingEntry findByLtExpirationDate_First(
			Date expirationDate,
			com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sharing entry in the ordered set where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public SharingEntry fetchByLtExpirationDate_First(
		Date expirationDate,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator);

	/**
	 * Removes all the sharing entries where expirationDate &lt; &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 */
	public void removeByLtExpirationDate(Date expirationDate);

	/**
	 * Returns the number of sharing entries where expirationDate &lt; &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @return the number of matching sharing entries
	 */
	public int countByLtExpirationDate(Date expirationDate);

	/**
	 * Returns an ordered range of all the sharing entries where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	public java.util.List<SharingEntry> findByC_CN(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sharing entry in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	public SharingEntry findByC_CN_First(
			long companyId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sharing entry in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public SharingEntry fetchByC_CN_First(
		long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator);

	/**
	 * Removes all the sharing entries where companyId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 */
	public void removeByC_CN(long companyId, long classNameId);

	/**
	 * Returns the number of sharing entries where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the number of matching sharing entries
	 */
	public int countByC_CN(long companyId, long classNameId);

	/**
	 * Returns an ordered range of all the sharing entries where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	public java.util.List<SharingEntry> findByU_C(
		long userId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sharing entry in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	public SharingEntry findByU_C_First(
			long userId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sharing entry in the ordered set where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public SharingEntry fetchByU_C_First(
		long userId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator);

	/**
	 * Removes all the sharing entries where userId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 */
	public void removeByU_C(long userId, long classNameId);

	/**
	 * Returns the number of sharing entries where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the number of matching sharing entries
	 */
	public int countByU_C(long userId, long classNameId);

	/**
	 * Returns an ordered range of all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	public java.util.List<SharingEntry> findByTU_C(
		long toUserId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	public SharingEntry findByTU_C_First(
			long toUserId, long classNameId,
			com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sharing entry in the ordered set where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public SharingEntry fetchByTU_C_First(
		long toUserId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator);

	/**
	 * Removes all the sharing entries where toUserId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 */
	public void removeByTU_C(long toUserId, long classNameId);

	/**
	 * Returns the number of sharing entries where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @return the number of matching sharing entries
	 */
	public int countByTU_C(long toUserId, long classNameId);

	/**
	 * Returns an ordered range of all the sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching sharing entries
	 */
	public java.util.List<SharingEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	public SharingEntry findByC_C_First(
			long classNameId, long classPK,
			com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
				orderByComparator)
		throws NoSuchEntryException;

	/**
	 * Returns the first sharing entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public SharingEntry fetchByC_C_First(
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator);

	/**
	 * Removes all the sharing entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	public void removeByC_C(long classNameId, long classPK);

	/**
	 * Returns the number of sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching sharing entries
	 */
	public int countByC_C(long classNameId, long classPK);

	/**
	 * Returns the sharing entry where toTicketId = &#63; and toUserGroupId = &#63; and toUserId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param toTicketId the to ticket ID
	 * @param toUserGroupId the to user group ID
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	public SharingEntry findByTT_TUG_TU_C_C(
			long toTicketId, long toUserGroupId, long toUserId,
			long classNameId, long classPK)
		throws NoSuchEntryException;

	/**
	 * Returns the sharing entry where toTicketId = &#63; and toUserGroupId = &#63; and toUserId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param toTicketId the to ticket ID
	 * @param toUserGroupId the to user group ID
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public SharingEntry fetchByTT_TUG_TU_C_C(
		long toTicketId, long toUserGroupId, long toUserId, long classNameId,
		long classPK, boolean useFinderCache);

	/**
	 * Removes the sharing entry where toTicketId = &#63; and toUserGroupId = &#63; and toUserId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param toTicketId the to ticket ID
	 * @param toUserGroupId the to user group ID
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the sharing entry that was removed
	 */
	public SharingEntry removeByTT_TUG_TU_C_C(
			long toTicketId, long toUserGroupId, long toUserId,
			long classNameId, long classPK)
		throws NoSuchEntryException;

	/**
	 * Returns the number of sharing entries where toTicketId = &#63; and toUserGroupId = &#63; and toUserId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param toTicketId the to ticket ID
	 * @param toUserGroupId the to user group ID
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching sharing entries
	 */
	public int countByTT_TUG_TU_C_C(
		long toTicketId, long toUserGroupId, long toUserId, long classNameId,
		long classPK);

	/**
	 * Returns the sharing entry where externalReferenceCode = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching sharing entry
	 * @throws NoSuchEntryException if a matching sharing entry could not be found
	 */
	public SharingEntry findByERC_G(String externalReferenceCode, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the sharing entry where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public SharingEntry fetchByERC_G(
		String externalReferenceCode, long groupId, boolean useFinderCache);

	/**
	 * Removes the sharing entry where externalReferenceCode = &#63; and groupId = &#63; from the database.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the sharing entry that was removed
	 */
	public SharingEntry removeByERC_G(
			String externalReferenceCode, long groupId)
		throws NoSuchEntryException;

	/**
	 * Returns the number of sharing entries where externalReferenceCode = &#63; and groupId = &#63;.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the number of matching sharing entries
	 */
	public int countByERC_G(String externalReferenceCode, long groupId);

	/**
	 * Creates a new sharing entry with the primary key. Does not add the sharing entry to the database.
	 *
	 * @param sharingEntryId the primary key for the new sharing entry
	 * @return the new sharing entry
	 */
	public SharingEntry create(long sharingEntryId);

	/**
	 * Removes the sharing entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sharingEntryId the primary key of the sharing entry
	 * @return the sharing entry that was removed
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	public SharingEntry remove(long sharingEntryId) throws NoSuchEntryException;

	public SharingEntry updateImpl(SharingEntry sharingEntry);

	/**
	 * Returns the sharing entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param sharingEntryId the primary key of the sharing entry
	 * @return the sharing entry
	 * @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	 */
	public SharingEntry findByPrimaryKey(long sharingEntryId)
		throws NoSuchEntryException;

	/**
	 * Returns the sharing entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sharingEntryId the primary key of the sharing entry
	 * @return the sharing entry, or <code>null</code> if a sharing entry with the primary key could not be found
	 */
	public SharingEntry fetchByPrimaryKey(long sharingEntryId);

	/**
	 * Returns the sharing entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public default SharingEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the sharing entry where toTicketId = &#63; and toUserGroupId = &#63; and toUserId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param toTicketId the to ticket ID
	 * @param toUserGroupId the to user group ID
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public default SharingEntry fetchByTT_TUG_TU_C_C(
		long toTicketId, long toUserGroupId, long toUserId, long classNameId,
		long classPK) {

		return fetchByTT_TUG_TU_C_C(
			toTicketId, toUserGroupId, toUserId, classNameId, classPK, true);
	}

	/**
	 * Returns the sharing entry where externalReferenceCode = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param externalReferenceCode the external reference code
	 * @param groupId the group ID
	 * @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	 */
	public default SharingEntry fetchByERC_G(
		String externalReferenceCode, long groupId) {

		return fetchByERC_G(externalReferenceCode, groupId, true);
	}

	/**
	 * Returns all the sharing entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByUuid(String uuid) {
		return findByUuid(
			uuid, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the sharing entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the sharing entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the sharing entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the sharing entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the sharing entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the sharing entries where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByUserId(long userId) {
		return findByUserId(
			userId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the sharing entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByUserId(
		long userId, int start, int end) {

		return findByUserId(userId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator) {

		return findByUserId(userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the sharing entries where toTicketId = &#63;.
	 *
	 * @param toTicketId the to ticket ID
	 * @return the matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByToTicketId(
		long toTicketId) {

		return findByToTicketId(
			toTicketId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the sharing entries where toTicketId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toTicketId the to ticket ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByToTicketId(
		long toTicketId, int start, int end) {

		return findByToTicketId(toTicketId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where toTicketId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toTicketId the to ticket ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByToTicketId(
		long toTicketId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator) {

		return findByToTicketId(
			toTicketId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the sharing entries where toUserGroupId = &#63;.
	 *
	 * @param toUserGroupId the to user group ID
	 * @return the matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByToUserGroupId(
		long toUserGroupId) {

		return findByToUserGroupId(
			toUserGroupId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the sharing entries where toUserGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toUserGroupId the to user group ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByToUserGroupId(
		long toUserGroupId, int start, int end) {

		return findByToUserGroupId(toUserGroupId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where toUserGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toUserGroupId the to user group ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByToUserGroupId(
		long toUserGroupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator) {

		return findByToUserGroupId(
			toUserGroupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the sharing entries where toUserId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @return the matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByToUserId(long toUserId) {
		return findByToUserId(
			toUserId, com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the sharing entries where toUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByToUserId(
		long toUserId, int start, int end) {

		return findByToUserId(toUserId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where toUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByToUserId(
		long toUserId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator) {

		return findByToUserId(toUserId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the sharing entries where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByC_CN(
		long companyId, long classNameId) {

		return findByC_CN(
			companyId, classNameId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the sharing entries where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByC_CN(
		long companyId, long classNameId, int start, int end) {

		return findByC_CN(companyId, classNameId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByC_CN(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator) {

		return findByC_CN(
			companyId, classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the sharing entries where userId = &#63; and classNameId = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @return the matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByU_C(
		long userId, long classNameId) {

		return findByU_C(
			userId, classNameId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the sharing entries where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByU_C(
		long userId, long classNameId, int start, int end) {

		return findByU_C(userId, classNameId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where userId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByU_C(
		long userId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator) {

		return findByU_C(
			userId, classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @return the matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByTU_C(
		long toUserId, long classNameId) {

		return findByTU_C(
			toUserId, classNameId,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByTU_C(
		long toUserId, long classNameId, int start, int end) {

		return findByTU_C(toUserId, classNameId, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where toUserId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param toUserId the to user ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByTU_C(
		long toUserId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator) {

		return findByTU_C(
			toUserId, classNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns all the sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByC_C(
		long classNameId, long classPK) {

		return findByC_C(
			classNameId, classPK,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS,
			com.liferay.portal.kernel.dao.orm.QueryUtil.ALL_POS, null, true);
	}

	/**
	 * Returns a range of all the sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @return the range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null, true);
	}

	/**
	 * Returns an ordered range of all the sharing entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.sharing.model.impl.SharingEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of sharing entries
	 * @param end the upper bound of the range of sharing entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching sharing entries
	 */
	public default java.util.List<SharingEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry>
			orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:1722996421