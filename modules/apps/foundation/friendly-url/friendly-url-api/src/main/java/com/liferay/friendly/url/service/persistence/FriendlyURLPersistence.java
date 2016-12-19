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

import com.liferay.friendly.url.exception.NoSuchFriendlyURLException;
import com.liferay.friendly.url.model.FriendlyURL;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the friendly url service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.friendly.url.service.persistence.impl.FriendlyURLPersistenceImpl
 * @see FriendlyURLUtil
 * @generated
 */
@ProviderType
public interface FriendlyURLPersistence extends BasePersistence<FriendlyURL> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FriendlyURLUtil} to access the friendly url persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the friendly urls where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the friendly urls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @return the range of matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the friendly urls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the friendly urls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public FriendlyURL findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the first friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator);

	/**
	* Returns the last friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public FriendlyURL findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the last friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator);

	/**
	* Returns the friendly urls before and after the current friendly url in the ordered set where uuid = &#63;.
	*
	* @param friendlyURLId the primary key of the current friendly url
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next friendly url
	* @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	*/
	public FriendlyURL[] findByUuid_PrevAndNext(long friendlyURLId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	* Removes all the friendly urls where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of friendly urls where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching friendly urls
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the friendly url where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public FriendlyURL findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the friendly url where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the friendly url that was removed
	*/
	public FriendlyURL removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the number of friendly urls where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching friendly urls
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @return the range of matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public FriendlyURL findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the first friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator);

	/**
	* Returns the last friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public FriendlyURL findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the last friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator);

	/**
	* Returns the friendly urls before and after the current friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param friendlyURLId the primary key of the current friendly url
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next friendly url
	* @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	*/
	public FriendlyURL[] findByUuid_C_PrevAndNext(long friendlyURLId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	* Removes all the friendly urls where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching friendly urls
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the friendly urls where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByG_C(long groupId, long classNameId);

	/**
	* Returns a range of all the friendly urls where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @return the range of matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByG_C(long groupId,
		long classNameId, int start, int end);

	/**
	* Returns an ordered range of all the friendly urls where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the friendly urls where groupId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByG_C(long groupId,
		long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first friendly url in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public FriendlyURL findByG_C_First(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the first friendly url in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByG_C_First(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator);

	/**
	* Returns the last friendly url in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public FriendlyURL findByG_C_Last(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the last friendly url in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByG_C_Last(long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator);

	/**
	* Returns the friendly urls before and after the current friendly url in the ordered set where groupId = &#63; and classNameId = &#63;.
	*
	* @param friendlyURLId the primary key of the current friendly url
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next friendly url
	* @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	*/
	public FriendlyURL[] findByG_C_PrevAndNext(long friendlyURLId,
		long groupId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	* Removes all the friendly urls where groupId = &#63; and classNameId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	*/
	public void removeByG_C(long groupId, long classNameId);

	/**
	* Returns the number of friendly urls where groupId = &#63; and classNameId = &#63;.
	*
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @return the number of matching friendly urls
	*/
	public int countByG_C(long groupId, long classNameId);

	/**
	* Returns all the friendly urls where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByC_G_C_C(long companyId,
		long groupId, long classNameId, long classPK);

	/**
	* Returns a range of all the friendly urls where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @return the range of matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByC_G_C_C(long companyId,
		long groupId, long classNameId, long classPK, int start, int end);

	/**
	* Returns an ordered range of all the friendly urls where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByC_G_C_C(long companyId,
		long groupId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the friendly urls where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching friendly urls
	*/
	public java.util.List<FriendlyURL> findByC_G_C_C(long companyId,
		long groupId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first friendly url in the ordered set where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public FriendlyURL findByC_G_C_C_First(long companyId, long groupId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the first friendly url in the ordered set where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByC_G_C_C_First(long companyId, long groupId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator);

	/**
	* Returns the last friendly url in the ordered set where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public FriendlyURL findByC_G_C_C_Last(long companyId, long groupId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the last friendly url in the ordered set where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByC_G_C_C_Last(long companyId, long groupId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator);

	/**
	* Returns the friendly urls before and after the current friendly url in the ordered set where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param friendlyURLId the primary key of the current friendly url
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next friendly url
	* @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	*/
	public FriendlyURL[] findByC_G_C_C_PrevAndNext(long friendlyURLId,
		long companyId, long groupId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator)
		throws NoSuchFriendlyURLException;

	/**
	* Removes all the friendly urls where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public void removeByC_G_C_C(long companyId, long groupId, long classNameId,
		long classPK);

	/**
	* Returns the number of friendly urls where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching friendly urls
	*/
	public int countByC_G_C_C(long companyId, long groupId, long classNameId,
		long classPK);

	/**
	* Returns the friendly url where companyId = &#63; and groupId = &#63; and classNameId = &#63; and urlTitle = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @return the matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public FriendlyURL findByC_G_C_U(long companyId, long groupId,
		long classNameId, java.lang.String urlTitle)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the friendly url where companyId = &#63; and groupId = &#63; and classNameId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByC_G_C_U(long companyId, long groupId,
		long classNameId, java.lang.String urlTitle);

	/**
	* Returns the friendly url where companyId = &#63; and groupId = &#63; and classNameId = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByC_G_C_U(long companyId, long groupId,
		long classNameId, java.lang.String urlTitle, boolean retrieveFromCache);

	/**
	* Removes the friendly url where companyId = &#63; and groupId = &#63; and classNameId = &#63; and urlTitle = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @return the friendly url that was removed
	*/
	public FriendlyURL removeByC_G_C_U(long companyId, long groupId,
		long classNameId, java.lang.String urlTitle)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the number of friendly urls where companyId = &#63; and groupId = &#63; and classNameId = &#63; and urlTitle = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param urlTitle the url title
	* @return the number of matching friendly urls
	*/
	public int countByC_G_C_U(long companyId, long groupId, long classNameId,
		java.lang.String urlTitle);

	/**
	* Returns the friendly url where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param urlTitle the url title
	* @return the matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public FriendlyURL findByC_G_C_C_U(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String urlTitle)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the friendly url where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param urlTitle the url title
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByC_G_C_C_U(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String urlTitle);

	/**
	* Returns the friendly url where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param urlTitle the url title
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByC_G_C_C_U(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String urlTitle,
		boolean retrieveFromCache);

	/**
	* Removes the friendly url where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param urlTitle the url title
	* @return the friendly url that was removed
	*/
	public FriendlyURL removeByC_G_C_C_U(long companyId, long groupId,
		long classNameId, long classPK, java.lang.String urlTitle)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the number of friendly urls where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63; and urlTitle = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param urlTitle the url title
	* @return the number of matching friendly urls
	*/
	public int countByC_G_C_C_U(long companyId, long groupId, long classNameId,
		long classPK, java.lang.String urlTitle);

	/**
	* Returns the friendly url where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @return the matching friendly url
	* @throws NoSuchFriendlyURLException if a matching friendly url could not be found
	*/
	public FriendlyURL findByC_G_C_C_M(long companyId, long groupId,
		long classNameId, long classPK, boolean main)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the friendly url where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByC_G_C_C_M(long companyId, long groupId,
		long classNameId, long classPK, boolean main);

	/**
	* Returns the friendly url where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching friendly url, or <code>null</code> if a matching friendly url could not be found
	*/
	public FriendlyURL fetchByC_G_C_C_M(long companyId, long groupId,
		long classNameId, long classPK, boolean main, boolean retrieveFromCache);

	/**
	* Removes the friendly url where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @return the friendly url that was removed
	*/
	public FriendlyURL removeByC_G_C_C_M(long companyId, long groupId,
		long classNameId, long classPK, boolean main)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the number of friendly urls where companyId = &#63; and groupId = &#63; and classNameId = &#63; and classPK = &#63; and main = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param main the main
	* @return the number of matching friendly urls
	*/
	public int countByC_G_C_C_M(long companyId, long groupId, long classNameId,
		long classPK, boolean main);

	/**
	* Caches the friendly url in the entity cache if it is enabled.
	*
	* @param friendlyURL the friendly url
	*/
	public void cacheResult(FriendlyURL friendlyURL);

	/**
	* Caches the friendly urls in the entity cache if it is enabled.
	*
	* @param friendlyURLs the friendly urls
	*/
	public void cacheResult(java.util.List<FriendlyURL> friendlyURLs);

	/**
	* Creates a new friendly url with the primary key. Does not add the friendly url to the database.
	*
	* @param friendlyURLId the primary key for the new friendly url
	* @return the new friendly url
	*/
	public FriendlyURL create(long friendlyURLId);

	/**
	* Removes the friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param friendlyURLId the primary key of the friendly url
	* @return the friendly url that was removed
	* @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	*/
	public FriendlyURL remove(long friendlyURLId)
		throws NoSuchFriendlyURLException;

	public FriendlyURL updateImpl(FriendlyURL friendlyURL);

	/**
	* Returns the friendly url with the primary key or throws a {@link NoSuchFriendlyURLException} if it could not be found.
	*
	* @param friendlyURLId the primary key of the friendly url
	* @return the friendly url
	* @throws NoSuchFriendlyURLException if a friendly url with the primary key could not be found
	*/
	public FriendlyURL findByPrimaryKey(long friendlyURLId)
		throws NoSuchFriendlyURLException;

	/**
	* Returns the friendly url with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param friendlyURLId the primary key of the friendly url
	* @return the friendly url, or <code>null</code> if a friendly url with the primary key could not be found
	*/
	public FriendlyURL fetchByPrimaryKey(long friendlyURLId);

	@Override
	public java.util.Map<java.io.Serializable, FriendlyURL> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the friendly urls.
	*
	* @return the friendly urls
	*/
	public java.util.List<FriendlyURL> findAll();

	/**
	* Returns a range of all the friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @return the range of friendly urls
	*/
	public java.util.List<FriendlyURL> findAll(int start, int end);

	/**
	* Returns an ordered range of all the friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of friendly urls
	*/
	public java.util.List<FriendlyURL> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of friendly urls
	* @param end the upper bound of the range of friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of friendly urls
	*/
	public java.util.List<FriendlyURL> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<FriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the friendly urls from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of friendly urls.
	*
	* @return the number of friendly urls
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}