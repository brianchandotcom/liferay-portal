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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.messageboards.model.MBMailingList;

/**
 * The persistence interface for the message boards mailing list service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBMailingListPersistenceImpl
 * @see MBMailingListUtil
 * @generated
 */
public interface MBMailingListPersistence extends BasePersistence<MBMailingList> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBMailingListUtil} to access the message boards mailing list persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the message boards mailing list in the entity cache if it is enabled.
	*
	* @param mbMailingList the message boards mailing list
	*/
	public void cacheResult(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList);

	/**
	* Caches the message boards mailing lists in the entity cache if it is enabled.
	*
	* @param mbMailingLists the message boards mailing lists
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> mbMailingLists);

	/**
	* Creates a new message boards mailing list with the primary key. Does not add the message boards mailing list to the database.
	*
	* @param mailingListId the primary key for the new message boards mailing list
	* @return the new message boards mailing list
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList create(
		long mailingListId);

	/**
	* Removes the message boards mailing list with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param mailingListId the primary key of the message boards mailing list
	* @return the message boards mailing list that was removed
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a message boards mailing list with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList remove(
		long mailingListId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	public com.liferay.portlet.messageboards.model.MBMailingList updateImpl(
		com.liferay.portlet.messageboards.model.MBMailingList mbMailingList,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message boards mailing list with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchMailingListException} if it could not be found.
	*
	* @param mailingListId the primary key of the message boards mailing list
	* @return the message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a message boards mailing list with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList findByPrimaryKey(
		long mailingListId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	/**
	* Returns the message boards mailing list with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param mailingListId the primary key of the message boards mailing list
	* @return the message boards mailing list, or <code>null</code> if a message boards mailing list with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList fetchByPrimaryKey(
		long mailingListId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the message boards mailing lists where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByUuid(
		java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards mailing lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards mailing lists
	* @param end the upper bound of the range of message boards mailing lists (not inclusive)
	* @return the range of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByUuid(
		java.lang.String uuid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards mailing lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards mailing lists
	* @param end the upper bound of the range of message boards mailing lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first message boards mailing list in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	/**
	* Returns the last message boards mailing list in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	/**
	* Returns the message boards mailing lists before and after the current message boards mailing list in the ordered set where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param mailingListId the primary key of the current message boards mailing list
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a message boards mailing list with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList[] findByUuid_PrevAndNext(
		long mailingListId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	/**
	* Returns the message boards mailing list where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchMailingListException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList findByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	/**
	* Returns the message boards mailing list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList fetchByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message boards mailing list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList fetchByUUID_G(
		java.lang.String uuid, long groupId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the message boards mailing lists where active = &#63;.
	*
	* @param active the active
	* @return the matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByActive(
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards mailing lists where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of message boards mailing lists
	* @param end the upper bound of the range of message boards mailing lists (not inclusive)
	* @return the range of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByActive(
		boolean active, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards mailing lists where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param active the active
	* @param start the lower bound of the range of message boards mailing lists
	* @param end the upper bound of the range of message boards mailing lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findByActive(
		boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first message boards mailing list in the ordered set where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList findByActive_First(
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	/**
	* Returns the last message boards mailing list in the ordered set where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList findByActive_Last(
		boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	/**
	* Returns the message boards mailing lists before and after the current message boards mailing list in the ordered set where active = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param mailingListId the primary key of the current message boards mailing list
	* @param active the active
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a message boards mailing list with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList[] findByActive_PrevAndNext(
		long mailingListId, boolean active,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	/**
	* Returns the message boards mailing list where groupId = &#63; and categoryId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchMailingListException} if it could not be found.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the matching message boards mailing list
	* @throws com.liferay.portlet.messageboards.NoSuchMailingListException if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList findByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	/**
	* Returns the message boards mailing list where groupId = &#63; and categoryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList fetchByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message boards mailing list where groupId = &#63; and categoryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message boards mailing list, or <code>null</code> if a matching message boards mailing list could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList fetchByG_C(
		long groupId, long categoryId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the message boards mailing lists.
	*
	* @return the message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards mailing lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards mailing lists
	* @param end the upper bound of the range of message boards mailing lists (not inclusive)
	* @return the range of message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards mailing lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards mailing lists
	* @param end the upper bound of the range of message boards mailing lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBMailingList> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards mailing lists where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the message boards mailing list where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the message boards mailing list that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList removeByUUID_G(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	/**
	* Removes all the message boards mailing lists where active = &#63; from the database.
	*
	* @param active the active
	* @throws SystemException if a system exception occurred
	*/
	public void removeByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the message boards mailing list where groupId = &#63; and categoryId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the message boards mailing list that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMailingList removeByG_C(
		long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchMailingListException;

	/**
	* Removes all the message boards mailing lists from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards mailing lists where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public int countByUuid(java.lang.String uuid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards mailing lists where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards mailing lists where active = &#63;.
	*
	* @param active the active
	* @return the number of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public int countByActive(boolean active)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards mailing lists where groupId = &#63; and categoryId = &#63;.
	*
	* @param groupId the group ID
	* @param categoryId the category ID
	* @return the number of matching message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public int countByG_C(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards mailing lists.
	*
	* @return the number of message boards mailing lists
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}