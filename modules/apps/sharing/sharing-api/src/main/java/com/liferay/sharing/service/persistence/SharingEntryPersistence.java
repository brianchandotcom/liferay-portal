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

package com.liferay.sharing.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.sharing.exception.NoSuchEntryException;
import com.liferay.sharing.model.SharingEntry;

/**
 * The persistence interface for the sharing entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.sharing.service.persistence.impl.SharingEntryPersistenceImpl
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
	* Returns all the sharing entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching sharing entries
	*/
	public java.util.List<SharingEntry> findByUuid(String uuid);

	/**
	* Returns a range of all the sharing entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByUuid(String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the sharing entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns an ordered range of all the sharing entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first sharing entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public SharingEntry findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first sharing entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns the last sharing entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public SharingEntry findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last sharing entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns the sharing entries before and after the current sharing entry in the ordered set where uuid = &#63;.
	*
	* @param sharingEntryId the primary key of the current sharing entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sharing entry
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public SharingEntry[] findByUuid_PrevAndNext(long sharingEntryId,
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

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
	* Returns the sharing entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public SharingEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException;

	/**
	* Returns the sharing entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the sharing entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

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
	* Returns all the sharing entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching sharing entries
	*/
	public java.util.List<SharingEntry> findByUuid_C(String uuid, long companyId);

	/**
	* Returns a range of all the sharing entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByUuid_C(String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the sharing entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns an ordered range of all the sharing entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public SharingEntry findByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns the last sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public SharingEntry findByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns the sharing entries before and after the current sharing entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param sharingEntryId the primary key of the current sharing entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sharing entry
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public SharingEntry[] findByUuid_C_PrevAndNext(long sharingEntryId,
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

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
	* Returns all the sharing entries where fromUserId = &#63;.
	*
	* @param fromUserId the from user ID
	* @return the matching sharing entries
	*/
	public java.util.List<SharingEntry> findByFromUserId(long fromUserId);

	/**
	* Returns a range of all the sharing entries where fromUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fromUserId the from user ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByFromUserId(long fromUserId,
		int start, int end);

	/**
	* Returns an ordered range of all the sharing entries where fromUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fromUserId the from user ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByFromUserId(long fromUserId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns an ordered range of all the sharing entries where fromUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param fromUserId the from user ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByFromUserId(long fromUserId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first sharing entry in the ordered set where fromUserId = &#63;.
	*
	* @param fromUserId the from user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public SharingEntry findByFromUserId_First(long fromUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first sharing entry in the ordered set where fromUserId = &#63;.
	*
	* @param fromUserId the from user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByFromUserId_First(long fromUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns the last sharing entry in the ordered set where fromUserId = &#63;.
	*
	* @param fromUserId the from user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public SharingEntry findByFromUserId_Last(long fromUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last sharing entry in the ordered set where fromUserId = &#63;.
	*
	* @param fromUserId the from user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByFromUserId_Last(long fromUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns the sharing entries before and after the current sharing entry in the ordered set where fromUserId = &#63;.
	*
	* @param sharingEntryId the primary key of the current sharing entry
	* @param fromUserId the from user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sharing entry
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public SharingEntry[] findByFromUserId_PrevAndNext(long sharingEntryId,
		long fromUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the sharing entries where fromUserId = &#63; from the database.
	*
	* @param fromUserId the from user ID
	*/
	public void removeByFromUserId(long fromUserId);

	/**
	* Returns the number of sharing entries where fromUserId = &#63;.
	*
	* @param fromUserId the from user ID
	* @return the number of matching sharing entries
	*/
	public int countByFromUserId(long fromUserId);

	/**
	* Returns all the sharing entries where toUserId = &#63;.
	*
	* @param toUserId the to user ID
	* @return the matching sharing entries
	*/
	public java.util.List<SharingEntry> findByToUserId(long toUserId);

	/**
	* Returns a range of all the sharing entries where toUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toUserId the to user ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByToUserId(long toUserId,
		int start, int end);

	/**
	* Returns an ordered range of all the sharing entries where toUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toUserId the to user ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByToUserId(long toUserId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns an ordered range of all the sharing entries where toUserId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toUserId the to user ID
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByToUserId(long toUserId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first sharing entry in the ordered set where toUserId = &#63;.
	*
	* @param toUserId the to user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public SharingEntry findByToUserId_First(long toUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first sharing entry in the ordered set where toUserId = &#63;.
	*
	* @param toUserId the to user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByToUserId_First(long toUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns the last sharing entry in the ordered set where toUserId = &#63;.
	*
	* @param toUserId the to user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public SharingEntry findByToUserId_Last(long toUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last sharing entry in the ordered set where toUserId = &#63;.
	*
	* @param toUserId the to user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByToUserId_Last(long toUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns the sharing entries before and after the current sharing entry in the ordered set where toUserId = &#63;.
	*
	* @param sharingEntryId the primary key of the current sharing entry
	* @param toUserId the to user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sharing entry
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public SharingEntry[] findByToUserId_PrevAndNext(long sharingEntryId,
		long toUserId,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

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
	* Returns all the sharing entries where toUserId = &#63; and className = &#63;.
	*
	* @param toUserId the to user ID
	* @param className the class name
	* @return the matching sharing entries
	*/
	public java.util.List<SharingEntry> findByTU_CN(long toUserId,
		String className);

	/**
	* Returns a range of all the sharing entries where toUserId = &#63; and className = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toUserId the to user ID
	* @param className the class name
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByTU_CN(long toUserId,
		String className, int start, int end);

	/**
	* Returns an ordered range of all the sharing entries where toUserId = &#63; and className = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toUserId the to user ID
	* @param className the class name
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByTU_CN(long toUserId,
		String className, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns an ordered range of all the sharing entries where toUserId = &#63; and className = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param toUserId the to user ID
	* @param className the class name
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByTU_CN(long toUserId,
		String className, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first sharing entry in the ordered set where toUserId = &#63; and className = &#63;.
	*
	* @param toUserId the to user ID
	* @param className the class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public SharingEntry findByTU_CN_First(long toUserId, String className,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first sharing entry in the ordered set where toUserId = &#63; and className = &#63;.
	*
	* @param toUserId the to user ID
	* @param className the class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByTU_CN_First(long toUserId, String className,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns the last sharing entry in the ordered set where toUserId = &#63; and className = &#63;.
	*
	* @param toUserId the to user ID
	* @param className the class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public SharingEntry findByTU_CN_Last(long toUserId, String className,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last sharing entry in the ordered set where toUserId = &#63; and className = &#63;.
	*
	* @param toUserId the to user ID
	* @param className the class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByTU_CN_Last(long toUserId, String className,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns the sharing entries before and after the current sharing entry in the ordered set where toUserId = &#63; and className = &#63;.
	*
	* @param sharingEntryId the primary key of the current sharing entry
	* @param toUserId the to user ID
	* @param className the class name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sharing entry
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public SharingEntry[] findByTU_CN_PrevAndNext(long sharingEntryId,
		long toUserId, String className,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the sharing entries where toUserId = &#63; and className = &#63; from the database.
	*
	* @param toUserId the to user ID
	* @param className the class name
	*/
	public void removeByTU_CN(long toUserId, String className);

	/**
	* Returns the number of sharing entries where toUserId = &#63; and className = &#63;.
	*
	* @param toUserId the to user ID
	* @param className the class name
	* @return the number of matching sharing entries
	*/
	public int countByTU_CN(long toUserId, String className);

	/**
	* Returns all the sharing entries where className = &#63; and classPK = &#63;.
	*
	* @param className the class name
	* @param classPK the class pk
	* @return the matching sharing entries
	*/
	public java.util.List<SharingEntry> findByCN_PK(String className,
		long classPK);

	/**
	* Returns a range of all the sharing entries where className = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param className the class name
	* @param classPK the class pk
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByCN_PK(String className,
		long classPK, int start, int end);

	/**
	* Returns an ordered range of all the sharing entries where className = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param className the class name
	* @param classPK the class pk
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByCN_PK(String className,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns an ordered range of all the sharing entries where className = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param className the class name
	* @param classPK the class pk
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching sharing entries
	*/
	public java.util.List<SharingEntry> findByCN_PK(String className,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first sharing entry in the ordered set where className = &#63; and classPK = &#63;.
	*
	* @param className the class name
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public SharingEntry findByCN_PK_First(String className, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the first sharing entry in the ordered set where className = &#63; and classPK = &#63;.
	*
	* @param className the class name
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByCN_PK_First(String className, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns the last sharing entry in the ordered set where className = &#63; and classPK = &#63;.
	*
	* @param className the class name
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public SharingEntry findByCN_PK_Last(String className, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Returns the last sharing entry in the ordered set where className = &#63; and classPK = &#63;.
	*
	* @param className the class name
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByCN_PK_Last(String className, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns the sharing entries before and after the current sharing entry in the ordered set where className = &#63; and classPK = &#63;.
	*
	* @param sharingEntryId the primary key of the current sharing entry
	* @param className the class name
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next sharing entry
	* @throws NoSuchEntryException if a sharing entry with the primary key could not be found
	*/
	public SharingEntry[] findByCN_PK_PrevAndNext(long sharingEntryId,
		String className, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator)
		throws NoSuchEntryException;

	/**
	* Removes all the sharing entries where className = &#63; and classPK = &#63; from the database.
	*
	* @param className the class name
	* @param classPK the class pk
	*/
	public void removeByCN_PK(String className, long classPK);

	/**
	* Returns the number of sharing entries where className = &#63; and classPK = &#63;.
	*
	* @param className the class name
	* @param classPK the class pk
	* @return the number of matching sharing entries
	*/
	public int countByCN_PK(String className, long classPK);

	/**
	* Returns the sharing entry where toUserId = &#63; and className = &#63; and classPK = &#63; or throws a {@link NoSuchEntryException} if it could not be found.
	*
	* @param toUserId the to user ID
	* @param className the class name
	* @param classPK the class pk
	* @return the matching sharing entry
	* @throws NoSuchEntryException if a matching sharing entry could not be found
	*/
	public SharingEntry findByTU_CN_PK(long toUserId, String className,
		long classPK) throws NoSuchEntryException;

	/**
	* Returns the sharing entry where toUserId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param toUserId the to user ID
	* @param className the class name
	* @param classPK the class pk
	* @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByTU_CN_PK(long toUserId, String className,
		long classPK);

	/**
	* Returns the sharing entry where toUserId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param toUserId the to user ID
	* @param className the class name
	* @param classPK the class pk
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching sharing entry, or <code>null</code> if a matching sharing entry could not be found
	*/
	public SharingEntry fetchByTU_CN_PK(long toUserId, String className,
		long classPK, boolean retrieveFromCache);

	/**
	* Removes the sharing entry where toUserId = &#63; and className = &#63; and classPK = &#63; from the database.
	*
	* @param toUserId the to user ID
	* @param className the class name
	* @param classPK the class pk
	* @return the sharing entry that was removed
	*/
	public SharingEntry removeByTU_CN_PK(long toUserId, String className,
		long classPK) throws NoSuchEntryException;

	/**
	* Returns the number of sharing entries where toUserId = &#63; and className = &#63; and classPK = &#63;.
	*
	* @param toUserId the to user ID
	* @param className the class name
	* @param classPK the class pk
	* @return the number of matching sharing entries
	*/
	public int countByTU_CN_PK(long toUserId, String className, long classPK);

	/**
	* Caches the sharing entry in the entity cache if it is enabled.
	*
	* @param sharingEntry the sharing entry
	*/
	public void cacheResult(SharingEntry sharingEntry);

	/**
	* Caches the sharing entries in the entity cache if it is enabled.
	*
	* @param sharingEntries the sharing entries
	*/
	public void cacheResult(java.util.List<SharingEntry> sharingEntries);

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
	* Returns the sharing entry with the primary key or throws a {@link NoSuchEntryException} if it could not be found.
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

	@Override
	public java.util.Map<java.io.Serializable, SharingEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the sharing entries.
	*
	* @return the sharing entries
	*/
	public java.util.List<SharingEntry> findAll();

	/**
	* Returns a range of all the sharing entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @return the range of sharing entries
	*/
	public java.util.List<SharingEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the sharing entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of sharing entries
	*/
	public java.util.List<SharingEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator);

	/**
	* Returns an ordered range of all the sharing entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SharingEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of sharing entries
	* @param end the upper bound of the range of sharing entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of sharing entries
	*/
	public java.util.List<SharingEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<SharingEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the sharing entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of sharing entries.
	*
	* @return the number of sharing entries
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}