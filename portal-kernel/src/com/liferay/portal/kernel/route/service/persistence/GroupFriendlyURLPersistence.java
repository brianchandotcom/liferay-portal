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

package com.liferay.portal.kernel.route.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.route.exception.NoSuchGroupFriendlyURLException;
import com.liferay.portal.kernel.route.model.GroupFriendlyURL;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the group friendly url service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.route.service.persistence.impl.GroupFriendlyURLPersistenceImpl
 * @see GroupFriendlyURLUtil
 * @generated
 */
@ProviderType
public interface GroupFriendlyURLPersistence extends BasePersistence<GroupFriendlyURL> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link GroupFriendlyURLUtil} to access the group friendly url persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the group friendly urls where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the group friendly urls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @return the range of matching group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the group friendly urls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the group friendly urls where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first group friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException;

	/**
	* Returns the first group friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator);

	/**
	* Returns the last group friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException;

	/**
	* Returns the last group friendly url in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator);

	/**
	* Returns the group friendly urls before and after the current group friendly url in the ordered set where uuid = &#63;.
	*
	* @param groupFriendlyURLId the primary key of the current group friendly url
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group friendly url
	* @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	*/
	public GroupFriendlyURL[] findByUuid_PrevAndNext(long groupFriendlyURLId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException;

	/**
	* Removes all the group friendly urls where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of group friendly urls where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching group friendly urls
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the group friendly url where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchGroupFriendlyURLException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchGroupFriendlyURLException;

	/**
	* Returns the group friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the group friendly url where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the group friendly url where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the group friendly url that was removed
	*/
	public GroupFriendlyURL removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchGroupFriendlyURLException;

	/**
	* Returns the number of group friendly urls where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching group friendly urls
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the group friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the group friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @return the range of matching group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the group friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the group friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first group friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException;

	/**
	* Returns the first group friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator);

	/**
	* Returns the last group friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException;

	/**
	* Returns the last group friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator);

	/**
	* Returns the group friendly urls before and after the current group friendly url in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param groupFriendlyURLId the primary key of the current group friendly url
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group friendly url
	* @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	*/
	public GroupFriendlyURL[] findByUuid_C_PrevAndNext(
		long groupFriendlyURLId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException;

	/**
	* Removes all the group friendly urls where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of group friendly urls where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching group friendly urls
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the group friendly urls where companyId = &#63; and groupId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @return the matching group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findByC_G(long companyId,
		long groupId);

	/**
	* Returns a range of all the group friendly urls where companyId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @return the range of matching group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findByC_G(long companyId,
		long groupId, int start, int end);

	/**
	* Returns an ordered range of all the group friendly urls where companyId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findByC_G(long companyId,
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the group friendly urls where companyId = &#63; and groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findByC_G(long companyId,
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first group friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL findByC_G_First(long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException;

	/**
	* Returns the first group friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL fetchByC_G_First(long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator);

	/**
	* Returns the last group friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL findByC_G_Last(long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException;

	/**
	* Returns the last group friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL fetchByC_G_Last(long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator);

	/**
	* Returns the group friendly urls before and after the current group friendly url in the ordered set where companyId = &#63; and groupId = &#63;.
	*
	* @param groupFriendlyURLId the primary key of the current group friendly url
	* @param companyId the company ID
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next group friendly url
	* @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	*/
	public GroupFriendlyURL[] findByC_G_PrevAndNext(long groupFriendlyURLId,
		long companyId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator)
		throws NoSuchGroupFriendlyURLException;

	/**
	* Removes all the group friendly urls where companyId = &#63; and groupId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	*/
	public void removeByC_G(long companyId, long groupId);

	/**
	* Returns the number of group friendly urls where companyId = &#63; and groupId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @return the number of matching group friendly urls
	*/
	public int countByC_G(long companyId, long groupId);

	/**
	* Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; or throws a {@link NoSuchGroupFriendlyURLException} if it could not be found.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @return the matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL findByC_F(long companyId,
		java.lang.String friendlyURL) throws NoSuchGroupFriendlyURLException;

	/**
	* Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL fetchByC_F(long companyId,
		java.lang.String friendlyURL);

	/**
	* Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL fetchByC_F(long companyId,
		java.lang.String friendlyURL, boolean retrieveFromCache);

	/**
	* Removes the group friendly url where companyId = &#63; and friendlyURL = &#63; from the database.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @return the group friendly url that was removed
	*/
	public GroupFriendlyURL removeByC_F(long companyId,
		java.lang.String friendlyURL) throws NoSuchGroupFriendlyURLException;

	/**
	* Returns the number of group friendly urls where companyId = &#63; and friendlyURL = &#63;.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @return the number of matching group friendly urls
	*/
	public int countByC_F(long companyId, java.lang.String friendlyURL);

	/**
	* Returns the group friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; or throws a {@link NoSuchGroupFriendlyURLException} if it could not be found.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param languageId the language ID
	* @return the matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL findByC_G_L(long companyId, long groupId,
		java.lang.String languageId) throws NoSuchGroupFriendlyURLException;

	/**
	* Returns the group friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param languageId the language ID
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL fetchByC_G_L(long companyId, long groupId,
		java.lang.String languageId);

	/**
	* Returns the group friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL fetchByC_G_L(long companyId, long groupId,
		java.lang.String languageId, boolean retrieveFromCache);

	/**
	* Removes the group friendly url where companyId = &#63; and groupId = &#63; and languageId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param languageId the language ID
	* @return the group friendly url that was removed
	*/
	public GroupFriendlyURL removeByC_G_L(long companyId, long groupId,
		java.lang.String languageId) throws NoSuchGroupFriendlyURLException;

	/**
	* Returns the number of group friendly urls where companyId = &#63; and groupId = &#63; and languageId = &#63;.
	*
	* @param companyId the company ID
	* @param groupId the group ID
	* @param languageId the language ID
	* @return the number of matching group friendly urls
	*/
	public int countByC_G_L(long companyId, long groupId,
		java.lang.String languageId);

	/**
	* Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or throws a {@link NoSuchGroupFriendlyURLException} if it could not be found.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @param languageId the language ID
	* @return the matching group friendly url
	* @throws NoSuchGroupFriendlyURLException if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL findByC_F_L(long companyId,
		java.lang.String friendlyURL, java.lang.String languageId)
		throws NoSuchGroupFriendlyURLException;

	/**
	* Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @param languageId the language ID
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL fetchByC_F_L(long companyId,
		java.lang.String friendlyURL, java.lang.String languageId);

	/**
	* Returns the group friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @param languageId the language ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching group friendly url, or <code>null</code> if a matching group friendly url could not be found
	*/
	public GroupFriendlyURL fetchByC_F_L(long companyId,
		java.lang.String friendlyURL, java.lang.String languageId,
		boolean retrieveFromCache);

	/**
	* Removes the group friendly url where companyId = &#63; and friendlyURL = &#63; and languageId = &#63; from the database.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @param languageId the language ID
	* @return the group friendly url that was removed
	*/
	public GroupFriendlyURL removeByC_F_L(long companyId,
		java.lang.String friendlyURL, java.lang.String languageId)
		throws NoSuchGroupFriendlyURLException;

	/**
	* Returns the number of group friendly urls where companyId = &#63; and friendlyURL = &#63; and languageId = &#63;.
	*
	* @param companyId the company ID
	* @param friendlyURL the friendly url
	* @param languageId the language ID
	* @return the number of matching group friendly urls
	*/
	public int countByC_F_L(long companyId, java.lang.String friendlyURL,
		java.lang.String languageId);

	/**
	* Caches the group friendly url in the entity cache if it is enabled.
	*
	* @param groupFriendlyURL the group friendly url
	*/
	public void cacheResult(GroupFriendlyURL groupFriendlyURL);

	/**
	* Caches the group friendly urls in the entity cache if it is enabled.
	*
	* @param groupFriendlyURLs the group friendly urls
	*/
	public void cacheResult(java.util.List<GroupFriendlyURL> groupFriendlyURLs);

	/**
	* Creates a new group friendly url with the primary key. Does not add the group friendly url to the database.
	*
	* @param groupFriendlyURLId the primary key for the new group friendly url
	* @return the new group friendly url
	*/
	public GroupFriendlyURL create(long groupFriendlyURLId);

	/**
	* Removes the group friendly url with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param groupFriendlyURLId the primary key of the group friendly url
	* @return the group friendly url that was removed
	* @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	*/
	public GroupFriendlyURL remove(long groupFriendlyURLId)
		throws NoSuchGroupFriendlyURLException;

	public GroupFriendlyURL updateImpl(GroupFriendlyURL groupFriendlyURL);

	/**
	* Returns the group friendly url with the primary key or throws a {@link NoSuchGroupFriendlyURLException} if it could not be found.
	*
	* @param groupFriendlyURLId the primary key of the group friendly url
	* @return the group friendly url
	* @throws NoSuchGroupFriendlyURLException if a group friendly url with the primary key could not be found
	*/
	public GroupFriendlyURL findByPrimaryKey(long groupFriendlyURLId)
		throws NoSuchGroupFriendlyURLException;

	/**
	* Returns the group friendly url with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param groupFriendlyURLId the primary key of the group friendly url
	* @return the group friendly url, or <code>null</code> if a group friendly url with the primary key could not be found
	*/
	public GroupFriendlyURL fetchByPrimaryKey(long groupFriendlyURLId);

	@Override
	public java.util.Map<java.io.Serializable, GroupFriendlyURL> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the group friendly urls.
	*
	* @return the group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findAll();

	/**
	* Returns a range of all the group friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @return the range of group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findAll(int start, int end);

	/**
	* Returns an ordered range of all the group friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator);

	/**
	* Returns an ordered range of all the group friendly urls.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link GroupFriendlyURLModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of group friendly urls
	* @param end the upper bound of the range of group friendly urls (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of group friendly urls
	*/
	public java.util.List<GroupFriendlyURL> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<GroupFriendlyURL> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the group friendly urls from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of group friendly urls.
	*
	* @return the number of group friendly urls
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}