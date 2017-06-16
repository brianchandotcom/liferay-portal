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

package com.liferay.friendly.url.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryException;
import com.liferay.friendly.url.model.FriendlyURLEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the friendly url entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.friendly.url.service.persistence.impl.FriendlyURLEntryPersistenceImpl
 * @see FriendlyURLEntryUtil
 * @generated
 */
@ProviderType
public interface FriendlyURLEntryPersistence extends BasePersistence<FriendlyURLEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FriendlyURLEntryUtil} to access the friendly url entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the friendly url entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the friendly url entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @return the range of matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the friendly url entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator);

	/**
	* Returns an ordered range of all the friendly url entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first friendly url entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the first friendly url entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator);

	/**
	* Returns the last friendly url entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the last friendly url entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator);

	/**
	* Returns the friendly url entries before and after the current friendly url entry in the ordered set where uuid = &#63;.
	*
	* @param friendlyURLEntryId the primary key of the current friendly url entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a friendly url entry with the primary key could not be found
	*/
	public FriendlyURLEntry[] findByUuid_PrevAndNext(long friendlyURLEntryId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Removes all the friendly url entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of friendly url entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching friendly url entries
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the friendly url entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFriendlyURLEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the friendly url entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the friendly url entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the friendly url entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the friendly url entry that was removed
	*/
	public FriendlyURLEntry removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the number of friendly url entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching friendly url entries
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the friendly url entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the friendly url entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @return the range of matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the friendly url entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator);

	/**
	* Returns an ordered range of all the friendly url entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the first friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator);

	/**
	* Returns the last friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the last friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator);

	/**
	* Returns the friendly url entries before and after the current friendly url entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param friendlyURLEntryId the primary key of the current friendly url entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a friendly url entry with the primary key could not be found
	*/
	public FriendlyURLEntry[] findByUuid_C_PrevAndNext(
		long friendlyURLEntryId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Removes all the friendly url entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of friendly url entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching friendly url entries
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the friendly url entries where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByG_C(long groupId,
		long classNameId);

	/**
	* Returns a range of all the friendly url entries where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @return the range of matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByG_C(long groupId,
		long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the friendly url entries where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator);

	/**
	* Returns an ordered range of all the friendly url entries where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry findByG_C_First(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the first friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByG_C_First(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator);

	/**
	* Returns the last friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry findByG_C_Last(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the last friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByG_C_Last(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator);

	/**
	* Returns the friendly url entries before and after the current friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param friendlyURLEntryId the primary key of the current friendly url entry
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a friendly url entry with the primary key could not be found
	*/
	public FriendlyURLEntry[] findByG_C_PrevAndNext(long friendlyURLEntryId,
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Removes all the friendly url entries where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	*/
	public void removeByG_C(long groupId, long classNameId);

	/**
	* Returns the number of friendly url entries where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching friendly url entries
	*/
	public int countByG_C(long groupId, long classNameId);

	/**
	* Returns all the friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByG_C_C(long groupId,
		long classNameId, long classPK);

	/**
	* Returns a range of all the friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @return the range of matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end);

	/**
	* Returns an ordered range of all the friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator);

	/**
	* Returns an ordered range of all the friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findByG_C_C(long groupId,
		long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry findByG_C_C_First(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the first friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByG_C_C_First(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator);

	/**
	* Returns the last friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry findByG_C_C_Last(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the last friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByG_C_C_Last(long groupId, long classNameId,
		long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator);

	/**
	* Returns the friendly url entries before and after the current friendly url entry in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param friendlyURLEntryId the primary key of the current friendly url entry
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a friendly url entry with the primary key could not be found
	*/
	public FriendlyURLEntry[] findByG_C_C_PrevAndNext(long friendlyURLEntryId,
		long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Removes all the friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public void removeByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns the number of friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching friendly url entries
	*/
	public int countByG_C_C(long groupId, long classNameId, long classPK);

	/**
	* Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and urlTitle = &#63; or throws a {@link NoSuchFriendlyURLEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @return the matching friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry findByG_C_U(long groupId, long classNameId,
		java.lang.String urlTitle) throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByG_C_U(long groupId, long classNameId,
		java.lang.String urlTitle);

	/**
	* Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByG_C_U(long groupId, long classNameId,
		java.lang.String urlTitle, boolean retrieveFromCache);

	/**
	* Removes the friendly url entry where groupId = &#63; and classNameId = &#63; and urlTitle = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @return the friendly url entry that was removed
	*/
	public FriendlyURLEntry removeByG_C_U(long groupId, long classNameId,
		java.lang.String urlTitle) throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the number of friendly url entries where groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @return the number of matching friendly url entries
	*/
	public int countByG_C_U(long groupId, long classNameId,
		java.lang.String urlTitle);

	/**
	* Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; or throws a {@link NoSuchFriendlyURLEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param urlTitle the url title
	* @return the matching friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry findByG_C_C_U(long groupId, long classNameId,
		long classPK, java.lang.String urlTitle)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param urlTitle the url title
	* @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByG_C_C_U(long groupId, long classNameId,
		long classPK, java.lang.String urlTitle);

	/**
	* Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param urlTitle the url title
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByG_C_C_U(long groupId, long classNameId,
		long classPK, java.lang.String urlTitle, boolean retrieveFromCache);

	/**
	* Removes the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param urlTitle the url title
	* @return the friendly url entry that was removed
	*/
	public FriendlyURLEntry removeByG_C_C_U(long groupId, long classNameId,
		long classPK, java.lang.String urlTitle)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the number of friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param urlTitle the url title
	* @return the number of matching friendly url entries
	*/
	public int countByG_C_C_U(long groupId, long classNameId, long classPK,
		java.lang.String urlTitle);

	/**
	* Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; or throws a {@link NoSuchFriendlyURLEntryException} if it could not be found.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @return the matching friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry findByG_C_C_M(long groupId, long classNameId,
		long classPK, boolean main) throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByG_C_C_M(long groupId, long classNameId,
		long classPK, boolean main);

	/**
	* Returns the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url entry, or <code>null</code> if a matching friendly url entry could not be found
	*/
	public FriendlyURLEntry fetchByG_C_C_M(long groupId, long classNameId,
		long classPK, boolean main, boolean retrieveFromCache);

	/**
	* Removes the friendly url entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @return the friendly url entry that was removed
	*/
	public FriendlyURLEntry removeByG_C_C_M(long groupId, long classNameId,
		long classPK, boolean main) throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the number of friendly url entries where groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @return the number of matching friendly url entries
	*/
	public int countByG_C_C_M(long groupId, long classNameId, long classPK,
		boolean main);

	/**
	* Caches the friendly url entry in the entity cache if it is enabled.
	*
	* @param friendlyURLEntry the friendly url entry
	*/
	public void cacheResult(FriendlyURLEntry friendlyURLEntry);

	/**
	* Caches the friendly url entries in the entity cache if it is enabled.
	*
	* @param friendlyURLEntries the friendly url entries
	*/
	public void cacheResult(java.util.List<FriendlyURLEntry> friendlyURLEntries);

	/**
	* Creates a new friendly url entry with the primary key. Does not add the friendly url entry to the database.
	*
	* @param friendlyURLEntryId the primary key for the new friendly url entry
	* @return the new friendly url entry
	*/
	public FriendlyURLEntry create(long friendlyURLEntryId);

	/**
	* Removes the friendly url entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLEntryId the primary key of the friendly url entry
	* @return the friendly url entry that was removed
	* @throws NoSuchFriendlyURLEntryException if a friendly url entry with the primary key could not be found
	*/
	public FriendlyURLEntry remove(long friendlyURLEntryId)
		throws NoSuchFriendlyURLEntryException;

	public FriendlyURLEntry updateImpl(FriendlyURLEntry friendlyURLEntry);

	/**
	* Returns the friendly url entry with the primary key or throws a {@link NoSuchFriendlyURLEntryException} if it could not be found.
	*
	* @param friendlyURLEntryId the primary key of the friendly url entry
	* @return the friendly url entry
	* @throws NoSuchFriendlyURLEntryException if a friendly url entry with the primary key could not be found
	*/
	public FriendlyURLEntry findByPrimaryKey(long friendlyURLEntryId)
		throws NoSuchFriendlyURLEntryException;

	/**
	* Returns the friendly url entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param friendlyURLEntryId the primary key of the friendly url entry
	* @return the friendly url entry, or <code>null</code> if a friendly url entry with the primary key could not be found
	*/
	public FriendlyURLEntry fetchByPrimaryKey(long friendlyURLEntryId);

	@Override
	public java.util.Map<java.io.Serializable, FriendlyURLEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the friendly url entries.
	*
	* @return the friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findAll();

	/**
	* Returns a range of all the friendly url entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @return the range of friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the friendly url entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator);

	/**
	* Returns an ordered range of all the friendly url entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly url entries
	* @param end the upper bound of the range of friendly url entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of friendly url entries
	*/
	public java.util.List<FriendlyURLEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURLEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the friendly url entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of friendly url entries.
	*
	* @return the number of friendly url entries
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}