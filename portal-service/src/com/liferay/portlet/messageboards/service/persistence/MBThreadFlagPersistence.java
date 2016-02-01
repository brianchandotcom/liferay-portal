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

import com.liferay.portlet.messageboards.exception.NoSuchThreadFlagException;
import com.liferay.portlet.messageboards.model.MBThreadFlag;

/**
 * The persistence interface for the message boards thread flag service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.messageboards.service.persistence.impl.MBThreadFlagPersistenceImpl
 * @see MBThreadFlagUtil
 * @generated
 */
@ProviderType
public interface MBThreadFlagPersistence extends BasePersistence<MBThreadFlag> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MBThreadFlagUtil} to access the message boards thread flag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the message boards thread flags where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the message boards thread flags where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @return the range of matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the message boards thread flags where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator);

	/**
	* Returns an ordered range of all the message boards thread flags where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first message boards thread flag in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread flag
	* @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException;

	/**
	* Returns the first message boards thread flag in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator);

	/**
	* Returns the last message boards thread flag in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread flag
	* @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException;

	/**
	* Returns the last message boards thread flag in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator);

	/**
	* Returns the message boards thread flags before and after the current message boards thread flag in the ordered set where uuid = &#63;.
	*
	* @param threadFlagId the primary key of the current message boards thread flag
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread flag
	* @throws NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	*/
	public MBThreadFlag[] findByUuid_PrevAndNext(long threadFlagId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException;

	/**
	* Removes all the message boards thread flags where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of message boards thread flags where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching message boards thread flags
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the message boards thread flag where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchThreadFlagException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards thread flag
	* @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchThreadFlagException;

	/**
	* Returns the message boards thread flag where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the message boards thread flag where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the message boards thread flag where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the message boards thread flag that was removed
	*/
	public MBThreadFlag removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchThreadFlagException;

	/**
	* Returns the number of message boards thread flags where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching message boards thread flags
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the message boards thread flags where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the message boards thread flags where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @return the range of matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the message boards thread flags where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator);

	/**
	* Returns an ordered range of all the message boards thread flags where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first message boards thread flag in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread flag
	* @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException;

	/**
	* Returns the first message boards thread flag in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator);

	/**
	* Returns the last message boards thread flag in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread flag
	* @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException;

	/**
	* Returns the last message boards thread flag in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator);

	/**
	* Returns the message boards thread flags before and after the current message boards thread flag in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param threadFlagId the primary key of the current message boards thread flag
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread flag
	* @throws NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	*/
	public MBThreadFlag[] findByUuid_C_PrevAndNext(long threadFlagId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException;

	/**
	* Removes all the message boards thread flags where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of message boards thread flags where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching message boards thread flags
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the message boards thread flags where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByUserId(long userId);

	/**
	* Returns a range of all the message boards thread flags where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @return the range of matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByUserId(long userId, int start,
		int end);

	/**
	* Returns an ordered range of all the message boards thread flags where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByUserId(long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator);

	/**
	* Returns an ordered range of all the message boards thread flags where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByUserId(long userId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first message boards thread flag in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread flag
	* @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException;

	/**
	* Returns the first message boards thread flag in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator);

	/**
	* Returns the last message boards thread flag in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread flag
	* @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException;

	/**
	* Returns the last message boards thread flag in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator);

	/**
	* Returns the message boards thread flags before and after the current message boards thread flag in the ordered set where userId = &#63;.
	*
	* @param threadFlagId the primary key of the current message boards thread flag
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread flag
	* @throws NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	*/
	public MBThreadFlag[] findByUserId_PrevAndNext(long threadFlagId,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException;

	/**
	* Removes all the message boards thread flags where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of message boards thread flags where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching message boards thread flags
	*/
	public int countByUserId(long userId);

	/**
	* Returns all the message boards thread flags where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @return the matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByThreadId(long threadId);

	/**
	* Returns a range of all the message boards thread flags where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @return the range of matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByThreadId(long threadId,
		int start, int end);

	/**
	* Returns an ordered range of all the message boards thread flags where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByThreadId(long threadId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator);

	/**
	* Returns an ordered range of all the message boards thread flags where threadId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param threadId the thread ID
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findByThreadId(long threadId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first message boards thread flag in the ordered set where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread flag
	* @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag findByThreadId_First(long threadId,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException;

	/**
	* Returns the first message boards thread flag in the ordered set where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag fetchByThreadId_First(long threadId,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator);

	/**
	* Returns the last message boards thread flag in the ordered set where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread flag
	* @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag findByThreadId_Last(long threadId,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException;

	/**
	* Returns the last message boards thread flag in the ordered set where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag fetchByThreadId_Last(long threadId,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator);

	/**
	* Returns the message boards thread flags before and after the current message boards thread flag in the ordered set where threadId = &#63;.
	*
	* @param threadFlagId the primary key of the current message boards thread flag
	* @param threadId the thread ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next message boards thread flag
	* @throws NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	*/
	public MBThreadFlag[] findByThreadId_PrevAndNext(long threadFlagId,
		long threadId,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator)
		throws NoSuchThreadFlagException;

	/**
	* Removes all the message boards thread flags where threadId = &#63; from the database.
	*
	* @param threadId the thread ID
	*/
	public void removeByThreadId(long threadId);

	/**
	* Returns the number of message boards thread flags where threadId = &#63;.
	*
	* @param threadId the thread ID
	* @return the number of matching message boards thread flags
	*/
	public int countByThreadId(long threadId);

	/**
	* Returns the message boards thread flag where userId = &#63; and threadId = &#63; or throws a {@link NoSuchThreadFlagException} if it could not be found.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @return the matching message boards thread flag
	* @throws NoSuchThreadFlagException if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag findByU_T(long userId, long threadId)
		throws NoSuchThreadFlagException;

	/**
	* Returns the message boards thread flag where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag fetchByU_T(long userId, long threadId);

	/**
	* Returns the message boards thread flag where userId = &#63; and threadId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching message boards thread flag, or <code>null</code> if a matching message boards thread flag could not be found
	*/
	public MBThreadFlag fetchByU_T(long userId, long threadId,
		boolean retrieveFromCache);

	/**
	* Removes the message boards thread flag where userId = &#63; and threadId = &#63; from the database.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @return the message boards thread flag that was removed
	*/
	public MBThreadFlag removeByU_T(long userId, long threadId)
		throws NoSuchThreadFlagException;

	/**
	* Returns the number of message boards thread flags where userId = &#63; and threadId = &#63;.
	*
	* @param userId the user ID
	* @param threadId the thread ID
	* @return the number of matching message boards thread flags
	*/
	public int countByU_T(long userId, long threadId);

	/**
	* Caches the message boards thread flag in the entity cache if it is enabled.
	*
	* @param mbThreadFlag the message boards thread flag
	*/
	public void cacheResult(MBThreadFlag mbThreadFlag);

	/**
	* Caches the message boards thread flags in the entity cache if it is enabled.
	*
	* @param mbThreadFlags the message boards thread flags
	*/
	public void cacheResult(java.util.List<MBThreadFlag> mbThreadFlags);

	/**
	* Creates a new message boards thread flag with the primary key. Does not add the message boards thread flag to the database.
	*
	* @param threadFlagId the primary key for the new message boards thread flag
	* @return the new message boards thread flag
	*/
	public MBThreadFlag create(long threadFlagId);

	/**
	* Removes the message boards thread flag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param threadFlagId the primary key of the message boards thread flag
	* @return the message boards thread flag that was removed
	* @throws NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	*/
	public MBThreadFlag remove(long threadFlagId)
		throws NoSuchThreadFlagException;

	public MBThreadFlag updateImpl(MBThreadFlag mbThreadFlag);

	/**
	* Returns the message boards thread flag with the primary key or throws a {@link NoSuchThreadFlagException} if it could not be found.
	*
	* @param threadFlagId the primary key of the message boards thread flag
	* @return the message boards thread flag
	* @throws NoSuchThreadFlagException if a message boards thread flag with the primary key could not be found
	*/
	public MBThreadFlag findByPrimaryKey(long threadFlagId)
		throws NoSuchThreadFlagException;

	/**
	* Returns the message boards thread flag with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param threadFlagId the primary key of the message boards thread flag
	* @return the message boards thread flag, or <code>null</code> if a message boards thread flag with the primary key could not be found
	*/
	public MBThreadFlag fetchByPrimaryKey(long threadFlagId);

	@Override
	public java.util.Map<java.io.Serializable, MBThreadFlag> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the message boards thread flags.
	*
	* @return the message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findAll();

	/**
	* Returns a range of all the message boards thread flags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @return the range of message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findAll(int start, int end);

	/**
	* Returns an ordered range of all the message boards thread flags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator);

	/**
	* Returns an ordered range of all the message boards thread flags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MBThreadFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of message boards thread flags
	* @param end the upper bound of the range of message boards thread flags (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of message boards thread flags
	*/
	public java.util.List<MBThreadFlag> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<MBThreadFlag> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the message boards thread flags from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of message boards thread flags.
	*
	* @return the number of message boards thread flags
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}