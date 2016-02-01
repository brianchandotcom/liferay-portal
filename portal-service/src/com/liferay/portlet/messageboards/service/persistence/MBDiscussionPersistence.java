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

package com.liferay.portlet.messageboards.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.messageboards.exception.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBDiscussion;

/**
 * The persistence interface for the message boards discussion service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.messageboards.service.persistence.impl.MBDiscussionPersistenceImpl
 * @see MBDiscussionUtil
 * @generated
 */
@ProviderType
public interface MBDiscussionPersistence extends BasePersistence<MBDiscussion> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBDiscussionUtil} to access the message boards discussion persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the message boards discussions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching message boards discussions
	*/
	public java.util.List<MBDiscussion> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the message boards discussions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBDiscussionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @return the range of matching message boards discussions
	*/
	public java.util.List<MBDiscussion> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the message boards discussions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBDiscussionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards discussions
	*/
	public java.util.List<MBDiscussion> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator);

	/**
	* Returns an ordered range of all the message boards discussions where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBDiscussionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message boards discussions
	*/
	public java.util.List<MBDiscussion> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first message boards discussion in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards discussion
	* @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	*/
	public MBDiscussion findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException;

	/**
	* Returns the first message boards discussion in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	*/
	public MBDiscussion fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator);

	/**
	* Returns the last message boards discussion in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards discussion
	* @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	*/
	public MBDiscussion findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException;

	/**
	* Returns the last message boards discussion in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	*/
	public MBDiscussion fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator);

	/**
	* Returns the message boards discussions before and after the current message boards discussion in the ordered set where uuid = &#63;.
	*
	* @param discussionId the primary key of the current message boards discussion
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards discussion
	* @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	*/
	public MBDiscussion[] findByUuid_PrevAndNext(long discussionId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException;

	/**
	* Removes all the message boards discussions where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of message boards discussions where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching message boards discussions
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the message boards discussion where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchDiscussionException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards discussion
	* @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	*/
	public MBDiscussion findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchDiscussionException;

	/**
	* Returns the message boards discussion where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	*/
	public MBDiscussion fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the message boards discussion where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	*/
	public MBDiscussion fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the message boards discussion where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the message boards discussion that was removed
	*/
	public MBDiscussion removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchDiscussionException;

	/**
	* Returns the number of message boards discussions where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching message boards discussions
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the message boards discussions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching message boards discussions
	*/
	public java.util.List<MBDiscussion> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the message boards discussions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBDiscussionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @return the range of matching message boards discussions
	*/
	public java.util.List<MBDiscussion> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the message boards discussions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBDiscussionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards discussions
	*/
	public java.util.List<MBDiscussion> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator);

	/**
	* Returns an ordered range of all the message boards discussions where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBDiscussionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message boards discussions
	*/
	public java.util.List<MBDiscussion> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first message boards discussion in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards discussion
	* @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	*/
	public MBDiscussion findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException;

	/**
	* Returns the first message boards discussion in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	*/
	public MBDiscussion fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator);

	/**
	* Returns the last message boards discussion in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards discussion
	* @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	*/
	public MBDiscussion findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException;

	/**
	* Returns the last message boards discussion in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	*/
	public MBDiscussion fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator);

	/**
	* Returns the message boards discussions before and after the current message boards discussion in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param discussionId the primary key of the current message boards discussion
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards discussion
	* @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	*/
	public MBDiscussion[] findByUuid_C_PrevAndNext(long discussionId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException;

	/**
	* Removes all the message boards discussions where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of message boards discussions where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching message boards discussions
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the message boards discussions where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the matching message boards discussions
	*/
	public java.util.List<MBDiscussion> findByClassNameId(long classNameId);

	/**
	* Returns a range of all the message boards discussions where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBDiscussionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @return the range of matching message boards discussions
	*/
	public java.util.List<MBDiscussion> findByClassNameId(long classNameId,
		int start, int end);

	/**
	* Returns an ordered range of all the message boards discussions where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBDiscussionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards discussions
	*/
	public java.util.List<MBDiscussion> findByClassNameId(long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator);

	/**
	* Returns an ordered range of all the message boards discussions where classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBDiscussionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message boards discussions
	*/
	public java.util.List<MBDiscussion> findByClassNameId(long classNameId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first message boards discussion in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards discussion
	* @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	*/
	public MBDiscussion findByClassNameId_First(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException;

	/**
	* Returns the first message boards discussion in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	*/
	public MBDiscussion fetchByClassNameId_First(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator);

	/**
	* Returns the last message boards discussion in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards discussion
	* @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	*/
	public MBDiscussion findByClassNameId_Last(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException;

	/**
	* Returns the last message boards discussion in the ordered set where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	*/
	public MBDiscussion fetchByClassNameId_Last(long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator);

	/**
	* Returns the message boards discussions before and after the current message boards discussion in the ordered set where classNameId = &#63;.
	*
	* @param discussionId the primary key of the current message boards discussion
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards discussion
	* @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	*/
	public MBDiscussion[] findByClassNameId_PrevAndNext(long discussionId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator)
		throws NoSuchDiscussionException;

	/**
	* Removes all the message boards discussions where classNameId = &#63; from the database.
	*
	* @param classNameId the class name ID
	*/
	public void removeByClassNameId(long classNameId);

	/**
	* Returns the number of message boards discussions where classNameId = &#63;.
	*
	* @param classNameId the class name ID
	* @return the number of matching message boards discussions
	*/
	public int countByClassNameId(long classNameId);

	/**
	* Returns the message boards discussion where threadId = &#63; or throws a {@link NoSuchDiscussionException} if it could not be found.
	*
	* @param threadId the thread ID
	* @return the matching message boards discussion
	* @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	*/
	public MBDiscussion findByThreadId(long threadId)
		throws NoSuchDiscussionException;

	/**
	* Returns the message boards discussion where threadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param threadId the thread ID
	* @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	*/
	public MBDiscussion fetchByThreadId(long threadId);

	/**
	* Returns the message boards discussion where threadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param threadId the thread ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	*/
	public MBDiscussion fetchByThreadId(long threadId, boolean retrieveFromCache);

	/**
	* Removes the message boards discussion where threadId = &#63; from the database.
	*
	* @param threadId the thread ID
	* @return the message boards discussion that was removed
	*/
	public MBDiscussion removeByThreadId(long threadId)
		throws NoSuchDiscussionException;

	/**
	* Returns the number of message boards discussions where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @return the number of matching message boards discussions
	*/
	public int countByThreadId(long threadId);

	/**
	* Returns the message boards discussion where classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchDiscussionException} if it could not be found.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching message boards discussion
	* @throws NoSuchDiscussionException if a matching message boards discussion could not be found
	*/
	public MBDiscussion findByC_C(long classNameId, long classPK)
		throws NoSuchDiscussionException;

	/**
	* Returns the message boards discussion where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	*/
	public MBDiscussion fetchByC_C(long classNameId, long classPK);

	/**
	* Returns the message boards discussion where classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching message boards discussion, or <code>null</code> if a matching message boards discussion could not be found
	*/
	public MBDiscussion fetchByC_C(long classNameId, long classPK,
		boolean retrieveFromCache);

	/**
	* Removes the message boards discussion where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the message boards discussion that was removed
	*/
	public MBDiscussion removeByC_C(long classNameId, long classPK)
		throws NoSuchDiscussionException;

	/**
	* Returns the number of message boards discussions where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class p k
	* @return the number of matching message boards discussions
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Caches the message boards discussion in the entity cache if it is enabled.
	*
	* @param mbDiscussion the message boards discussion
	*/
	public void cacheResult(MBDiscussion mbDiscussion);

	/**
	* Caches the message boards discussions in the entity cache if it is enabled.
	*
	* @param mbDiscussions the message boards discussions
	*/
	public void cacheResult(java.util.List<MBDiscussion> mbDiscussions);

	/**
	* Creates a new message boards discussion with the primary key. Does not add the message boards discussion to the database.
	*
	* @param discussionId the primary key for the new message boards discussion
	* @return the new message boards discussion
	*/
	public MBDiscussion create(long discussionId);

	/**
	* Removes the message boards discussion with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param discussionId the primary key of the message boards discussion
	* @return the message boards discussion that was removed
	* @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	*/
	public MBDiscussion remove(long discussionId)
		throws NoSuchDiscussionException;

	public MBDiscussion updateImpl(MBDiscussion mbDiscussion);

	/**
	* Returns the message boards discussion with the primary key or throws a {@link NoSuchDiscussionException} if it could not be found.
	*
	* @param discussionId the primary key of the message boards discussion
	* @return the message boards discussion
	* @throws NoSuchDiscussionException if a message boards discussion with the primary key could not be found
	*/
	public MBDiscussion findByPrimaryKey(long discussionId)
		throws NoSuchDiscussionException;

	/**
	* Returns the message boards discussion with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param discussionId the primary key of the message boards discussion
	* @return the message boards discussion, or <code>null</code> if a message boards discussion with the primary key could not be found
	*/
	public MBDiscussion fetchByPrimaryKey(long discussionId);

	@Override
	public java.util.Map<java.io.Serializable, MBDiscussion> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the message boards discussions.
	*
	* @return the message boards discussions
	*/
	public java.util.List<MBDiscussion> findAll();

	/**
	* Returns a range of all the message boards discussions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBDiscussionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @return the range of message boards discussions
	*/
	public java.util.List<MBDiscussion> findAll(int start, int end);

	/**
	* Returns an ordered range of all the message boards discussions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBDiscussionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of message boards discussions
	*/
	public java.util.List<MBDiscussion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator);

	/**
	* Returns an ordered range of all the message boards discussions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBDiscussionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards discussions
	* @param end the upper bound of the range of message boards discussions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of message boards discussions
	*/
	public java.util.List<MBDiscussion> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBDiscussion> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the message boards discussions from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of message boards discussions.
	*
	* @return the number of message boards discussions
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}