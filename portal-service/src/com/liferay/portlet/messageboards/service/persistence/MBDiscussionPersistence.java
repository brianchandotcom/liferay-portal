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

import com.liferay.portlet.messageboards.model.MBDiscussion;

/**
 * The persistence interface for the message boards discussion service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBDiscussionPersistenceImpl
 * @see MBDiscussionUtil
 * @generated
 */
public interface MBDiscussionPersistence extends BasePersistence<MBDiscussion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBDiscussionUtil} to access the message boards discussion persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the message boards discussion in the entity cache if it is enabled.
	*
	* @param mbDiscussion the message boards discussion
	*/
	public void cacheResult(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion);

	/**
	* Caches the message boards discussions in the entity cache if it is enabled.
	*
	* @param mbDiscussions the message boards discussions
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> mbDiscussions);

	/**
	* Creates a new message boards discussion with the primary key. Does not add the message boards discussion to the database.
	*
	* @param discussionId the primary key for the new message boards discussion
	* @return the new message boards discussion
	*/
	public com.liferay.portlet.messageboards.model.MBDiscussion create(
		long discussionId);

	/**
	* Removes the message boards discussion with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param discussionId the primary key of the message boards discussion
	* @return the message boards discussion that was removed
	* @throws com.liferay.portlet.messageboards.NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBDiscussion remove(
		long discussionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	public com.liferay.portlet.messageboards.model.MBDiscussion updateImpl(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message boards discussion with the primary key or throws a {@link com.liferay.portlet.messageboards.NoSuchDiscussionException} if it could not be found.
	*
	* @param discussionId the primary key of the message boards discussion
	* @return the message boards discussion
	* @throws com.liferay.portlet.messageboards.NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBDiscussion findByPrimaryKey(
		long discussionId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	/**
	* Returns the message boards discussion with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param discussionId the primary key of the message boards discussion
	* @return the message boards discussion, or <code>null</code> if a message boards discussion with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBDiscussion fetchByPrimaryKey(
		long discussionId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the message boards discussions where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching message boards discussions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findByClassNameId(
		long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards discussions where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @return the range of matching message boards discussions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findByClassNameId(
		long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards discussions where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards discussions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findByClassNameId(
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first message boards discussion in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards discussion
	* @throws com.liferay.portlet.messageboards.NoSuchDiscussionException if a matching message boards discussion could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBDiscussion findByClassNameId_First(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	/**
	* Returns the last message boards discussion in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards discussion
	* @throws com.liferay.portlet.messageboards.NoSuchDiscussionException if a matching message boards discussion could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBDiscussion findByClassNameId_Last(
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	/**
	* Returns the message boards discussions before and after the current message boards discussion in the ordered set where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param discussionId the primary key of the current message boards discussion
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards discussion
	* @throws com.liferay.portlet.messageboards.NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBDiscussion[] findByClassNameId_PrevAndNext(
		long discussionId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	/**
	* Returns the message boards discussion where threadId = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchDiscussionException} if it could not be found.
	*
	* @param threadId the thread ID
	* @return the matching message boards discussion
	* @throws com.liferay.portlet.messageboards.NoSuchDiscussionException if a matching message boards discussion could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBDiscussion findByThreadId(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	/**
	* Returns the message boards discussion where threadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param threadId the thread ID
	* @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBDiscussion fetchByThreadId(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message boards discussion where threadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param threadId the thread ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBDiscussion fetchByThreadId(
		long threadId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message boards discussion where classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.messageboards.NoSuchDiscussionException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching message boards discussion
	* @throws com.liferay.portlet.messageboards.NoSuchDiscussionException if a matching message boards discussion could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBDiscussion findByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	/**
	* Returns the message boards discussion where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBDiscussion fetchByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message boards discussion where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBDiscussion fetchByC_C(
		long classNameId, long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the message boards discussions.
	*
	* @return the message boards discussions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the message boards discussions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @return the range of message boards discussions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the message boards discussions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of message boards discussions
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the message boards discussions where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the message boards discussion where threadId = &#63; from the database.
	*
	* @param threadId the thread ID
	* @return the message boards discussion that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBDiscussion removeByThreadId(
		long threadId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	/**
	* Removes the message boards discussion where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the message boards discussion that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBDiscussion removeByC_C(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.messageboards.NoSuchDiscussionException;

	/**
	* Removes all the message boards discussions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards discussions where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching message boards discussions
	* @throws SystemException if a system exception occurred
	*/
	public int countByClassNameId(long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards discussions where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @return the number of matching message boards discussions
	* @throws SystemException if a system exception occurred
	*/
	public int countByThreadId(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards discussions where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching message boards discussions
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C(long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of message boards discussions.
	*
	* @return the number of message boards discussions
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}