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

package com.liferay.journal.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.journal.exception.NoSuchFeedException;
import com.liferay.journal.model.JournalFeed;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the journal feed service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.journal.service.persistence.impl.JournalFeedPersistenceImpl
 * @see JournalFeedUtil
 * @generated
 */
@ProviderType
public interface JournalFeedPersistence extends BasePersistence<JournalFeed> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link JournalFeedUtil} to access the journal feed persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the journal feeds where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching journal feeds
	*/
	public java.util.List<JournalFeed> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the journal feeds where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @return the range of matching journal feeds
	*/
	public java.util.List<JournalFeed> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the journal feeds where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal feeds
	*/
	public java.util.List<JournalFeed> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator);

	/**
	* Returns an ordered range of all the journal feeds where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal feeds
	*/
	public java.util.List<JournalFeed> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal feed in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal feed
	* @throws NoSuchFeedException if a matching journal feed could not be found
	*/
	public JournalFeed findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException;

	/**
	* Returns the first journal feed in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal feed, or <code>null</code> if a matching journal feed could not be found
	*/
	public JournalFeed fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator);

	/**
	* Returns the last journal feed in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal feed
	* @throws NoSuchFeedException if a matching journal feed could not be found
	*/
	public JournalFeed findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException;

	/**
	* Returns the last journal feed in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal feed, or <code>null</code> if a matching journal feed could not be found
	*/
	public JournalFeed fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator);

	/**
	* Returns the journal feeds before and after the current journal feed in the ordered set where uuid = &#63;.
	*
	* @param id the primary key of the current journal feed
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal feed
	* @throws NoSuchFeedException if a journal feed with the primary key could not be found
	*/
	public JournalFeed[] findByUuid_PrevAndNext(long id, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException;

	/**
	* Removes all the journal feeds where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of journal feeds where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching journal feeds
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the journal feed where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFeedException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal feed
	* @throws NoSuchFeedException if a matching journal feed could not be found
	*/
	public JournalFeed findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchFeedException;

	/**
	* Returns the journal feed where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching journal feed, or <code>null</code> if a matching journal feed could not be found
	*/
	public JournalFeed fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the journal feed where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching journal feed, or <code>null</code> if a matching journal feed could not be found
	*/
	public JournalFeed fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the journal feed where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the journal feed that was removed
	*/
	public JournalFeed removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchFeedException;

	/**
	* Returns the number of journal feeds where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching journal feeds
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the journal feeds where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching journal feeds
	*/
	public java.util.List<JournalFeed> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the journal feeds where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @return the range of matching journal feeds
	*/
	public java.util.List<JournalFeed> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the journal feeds where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal feeds
	*/
	public java.util.List<JournalFeed> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator);

	/**
	* Returns an ordered range of all the journal feeds where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal feeds
	*/
	public java.util.List<JournalFeed> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal feed in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal feed
	* @throws NoSuchFeedException if a matching journal feed could not be found
	*/
	public JournalFeed findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException;

	/**
	* Returns the first journal feed in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal feed, or <code>null</code> if a matching journal feed could not be found
	*/
	public JournalFeed fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator);

	/**
	* Returns the last journal feed in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal feed
	* @throws NoSuchFeedException if a matching journal feed could not be found
	*/
	public JournalFeed findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException;

	/**
	* Returns the last journal feed in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal feed, or <code>null</code> if a matching journal feed could not be found
	*/
	public JournalFeed fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator);

	/**
	* Returns the journal feeds before and after the current journal feed in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param id the primary key of the current journal feed
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal feed
	* @throws NoSuchFeedException if a journal feed with the primary key could not be found
	*/
	public JournalFeed[] findByUuid_C_PrevAndNext(long id,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException;

	/**
	* Removes all the journal feeds where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of journal feeds where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching journal feeds
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the journal feeds where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal feeds
	*/
	public java.util.List<JournalFeed> findByGroupId(long groupId);

	/**
	* Returns a range of all the journal feeds where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @return the range of matching journal feeds
	*/
	public java.util.List<JournalFeed> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the journal feeds where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal feeds
	*/
	public java.util.List<JournalFeed> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator);

	/**
	* Returns an ordered range of all the journal feeds where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching journal feeds
	*/
	public java.util.List<JournalFeed> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first journal feed in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal feed
	* @throws NoSuchFeedException if a matching journal feed could not be found
	*/
	public JournalFeed findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException;

	/**
	* Returns the first journal feed in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching journal feed, or <code>null</code> if a matching journal feed could not be found
	*/
	public JournalFeed fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator);

	/**
	* Returns the last journal feed in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal feed
	* @throws NoSuchFeedException if a matching journal feed could not be found
	*/
	public JournalFeed findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException;

	/**
	* Returns the last journal feed in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching journal feed, or <code>null</code> if a matching journal feed could not be found
	*/
	public JournalFeed fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator);

	/**
	* Returns the journal feeds before and after the current journal feed in the ordered set where groupId = &#63;.
	*
	* @param id the primary key of the current journal feed
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal feed
	* @throws NoSuchFeedException if a journal feed with the primary key could not be found
	*/
	public JournalFeed[] findByGroupId_PrevAndNext(long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException;

	/**
	* Returns all the journal feeds that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching journal feeds that the user has permission to view
	*/
	public java.util.List<JournalFeed> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the journal feeds that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @return the range of matching journal feeds that the user has permission to view
	*/
	public java.util.List<JournalFeed> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the journal feeds that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching journal feeds that the user has permission to view
	*/
	public java.util.List<JournalFeed> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator);

	/**
	* Returns the journal feeds before and after the current journal feed in the ordered set of journal feeds that the user has permission to view where groupId = &#63;.
	*
	* @param id the primary key of the current journal feed
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next journal feed
	* @throws NoSuchFeedException if a journal feed with the primary key could not be found
	*/
	public JournalFeed[] filterFindByGroupId_PrevAndNext(long id, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator)
		throws NoSuchFeedException;

	/**
	* Removes all the journal feeds where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of journal feeds where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal feeds
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of journal feeds that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching journal feeds that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns the journal feed where groupId = &#63; and feedId = &#63; or throws a {@link NoSuchFeedException} if it could not be found.
	*
	* @param groupId the group ID
	* @param feedId the feed ID
	* @return the matching journal feed
	* @throws NoSuchFeedException if a matching journal feed could not be found
	*/
	public JournalFeed findByG_F(long groupId, java.lang.String feedId)
		throws NoSuchFeedException;

	/**
	* Returns the journal feed where groupId = &#63; and feedId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param feedId the feed ID
	* @return the matching journal feed, or <code>null</code> if a matching journal feed could not be found
	*/
	public JournalFeed fetchByG_F(long groupId, java.lang.String feedId);

	/**
	* Returns the journal feed where groupId = &#63; and feedId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param feedId the feed ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching journal feed, or <code>null</code> if a matching journal feed could not be found
	*/
	public JournalFeed fetchByG_F(long groupId, java.lang.String feedId,
		boolean retrieveFromCache);

	/**
	* Removes the journal feed where groupId = &#63; and feedId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param feedId the feed ID
	* @return the journal feed that was removed
	*/
	public JournalFeed removeByG_F(long groupId, java.lang.String feedId)
		throws NoSuchFeedException;

	/**
	* Returns the number of journal feeds where groupId = &#63; and feedId = &#63;.
	*
	* @param groupId the group ID
	* @param feedId the feed ID
	* @return the number of matching journal feeds
	*/
	public int countByG_F(long groupId, java.lang.String feedId);

	/**
	* Caches the journal feed in the entity cache if it is enabled.
	*
	* @param journalFeed the journal feed
	*/
	public void cacheResult(JournalFeed journalFeed);

	/**
	* Caches the journal feeds in the entity cache if it is enabled.
	*
	* @param journalFeeds the journal feeds
	*/
	public void cacheResult(java.util.List<JournalFeed> journalFeeds);

	/**
	* Creates a new journal feed with the primary key. Does not add the journal feed to the database.
	*
	* @param id the primary key for the new journal feed
	* @return the new journal feed
	*/
	public JournalFeed create(long id);

	/**
	* Removes the journal feed with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the journal feed
	* @return the journal feed that was removed
	* @throws NoSuchFeedException if a journal feed with the primary key could not be found
	*/
	public JournalFeed remove(long id) throws NoSuchFeedException;

	public JournalFeed updateImpl(JournalFeed journalFeed);

	/**
	* Returns the journal feed with the primary key or throws a {@link NoSuchFeedException} if it could not be found.
	*
	* @param id the primary key of the journal feed
	* @return the journal feed
	* @throws NoSuchFeedException if a journal feed with the primary key could not be found
	*/
	public JournalFeed findByPrimaryKey(long id) throws NoSuchFeedException;

	/**
	* Returns the journal feed with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the journal feed
	* @return the journal feed, or <code>null</code> if a journal feed with the primary key could not be found
	*/
	public JournalFeed fetchByPrimaryKey(long id);

	@Override
	public java.util.Map<java.io.Serializable, JournalFeed> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the journal feeds.
	*
	* @return the journal feeds
	*/
	public java.util.List<JournalFeed> findAll();

	/**
	* Returns a range of all the journal feeds.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @return the range of journal feeds
	*/
	public java.util.List<JournalFeed> findAll(int start, int end);

	/**
	* Returns an ordered range of all the journal feeds.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of journal feeds
	*/
	public java.util.List<JournalFeed> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator);

	/**
	* Returns an ordered range of all the journal feeds.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link JournalFeedModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of journal feeds
	* @param end the upper bound of the range of journal feeds (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of journal feeds
	*/
	public java.util.List<JournalFeed> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<JournalFeed> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the journal feeds from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of journal feeds.
	*
	* @return the number of journal feeds
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}