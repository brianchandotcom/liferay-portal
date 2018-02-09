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

package com.liferay.commerce.wish.list.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.wish.list.exception.NoSuchWishListException;
import com.liferay.commerce.wish.list.model.CommerceWishList;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce wish list service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Andrea Di Giorgi
 * @see com.liferay.commerce.wish.list.service.persistence.impl.CommerceWishListPersistenceImpl
 * @see CommerceWishListUtil
 * @generated
 */
@ProviderType
public interface CommerceWishListPersistence extends BasePersistence<CommerceWishList> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceWishListUtil} to access the commerce wish list persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce wish lists where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the commerce wish lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @return the range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce wish lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce wish lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce wish list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list
	* @throws NoSuchWishListException if a matching commerce wish list could not be found
	*/
	public CommerceWishList findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Returns the first commerce wish list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	public CommerceWishList fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns the last commerce wish list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list
	* @throws NoSuchWishListException if a matching commerce wish list could not be found
	*/
	public CommerceWishList findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Returns the last commerce wish list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	public CommerceWishList fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns the commerce wish lists before and after the current commerce wish list in the ordered set where uuid = &#63;.
	*
	* @param commerceWishListId the primary key of the current commerce wish list
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce wish list
	* @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	*/
	public CommerceWishList[] findByUuid_PrevAndNext(long commerceWishListId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Removes all the commerce wish lists where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of commerce wish lists where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce wish lists
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the commerce wish list where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchWishListException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce wish list
	* @throws NoSuchWishListException if a matching commerce wish list could not be found
	*/
	public CommerceWishList findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchWishListException;

	/**
	* Returns the commerce wish list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	public CommerceWishList fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the commerce wish list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	public CommerceWishList fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the commerce wish list where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce wish list that was removed
	*/
	public CommerceWishList removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchWishListException;

	/**
	* Returns the number of commerce wish lists where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce wish lists
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the commerce wish lists where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the commerce wish lists where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @return the range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce wish lists where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce wish lists where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce wish list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list
	* @throws NoSuchWishListException if a matching commerce wish list could not be found
	*/
	public CommerceWishList findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Returns the first commerce wish list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	public CommerceWishList fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns the last commerce wish list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list
	* @throws NoSuchWishListException if a matching commerce wish list could not be found
	*/
	public CommerceWishList findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Returns the last commerce wish list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	public CommerceWishList fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns the commerce wish lists before and after the current commerce wish list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceWishListId the primary key of the current commerce wish list
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce wish list
	* @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	*/
	public CommerceWishList[] findByUuid_C_PrevAndNext(
		long commerceWishListId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Removes all the commerce wish lists where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of commerce wish lists where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce wish lists
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the commerce wish lists where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce wish lists where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @return the range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce wish lists where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce wish lists where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce wish list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list
	* @throws NoSuchWishListException if a matching commerce wish list could not be found
	*/
	public CommerceWishList findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Returns the first commerce wish list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	public CommerceWishList fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns the last commerce wish list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list
	* @throws NoSuchWishListException if a matching commerce wish list could not be found
	*/
	public CommerceWishList findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Returns the last commerce wish list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	public CommerceWishList fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns the commerce wish lists before and after the current commerce wish list in the ordered set where groupId = &#63;.
	*
	* @param commerceWishListId the primary key of the current commerce wish list
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce wish list
	* @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	*/
	public CommerceWishList[] findByGroupId_PrevAndNext(
		long commerceWishListId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Removes all the commerce wish lists where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce wish lists where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce wish lists
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the commerce wish lists where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByUserId(long userId);

	/**
	* Returns a range of all the commerce wish lists where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @return the range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByUserId(long userId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce wish lists where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByUserId(long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce wish lists where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByUserId(long userId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce wish list in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list
	* @throws NoSuchWishListException if a matching commerce wish list could not be found
	*/
	public CommerceWishList findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Returns the first commerce wish list in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	public CommerceWishList fetchByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns the last commerce wish list in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list
	* @throws NoSuchWishListException if a matching commerce wish list could not be found
	*/
	public CommerceWishList findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Returns the last commerce wish list in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	public CommerceWishList fetchByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns the commerce wish lists before and after the current commerce wish list in the ordered set where userId = &#63;.
	*
	* @param commerceWishListId the primary key of the current commerce wish list
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce wish list
	* @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	*/
	public CommerceWishList[] findByUserId_PrevAndNext(
		long commerceWishListId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Removes all the commerce wish lists where userId = &#63; from the database.
	*
	* @param userId the user ID
	*/
	public void removeByUserId(long userId);

	/**
	* Returns the number of commerce wish lists where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching commerce wish lists
	*/
	public int countByUserId(long userId);

	/**
	* Returns all the commerce wish lists where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByG_U(long groupId, long userId);

	/**
	* Returns a range of all the commerce wish lists where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @return the range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByG_U(long groupId,
		long userId, int start, int end);

	/**
	* Returns an ordered range of all the commerce wish lists where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByG_U(long groupId,
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce wish lists where groupId = &#63; and userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByG_U(long groupId,
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce wish list in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list
	* @throws NoSuchWishListException if a matching commerce wish list could not be found
	*/
	public CommerceWishList findByG_U_First(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Returns the first commerce wish list in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	public CommerceWishList fetchByG_U_First(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns the last commerce wish list in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list
	* @throws NoSuchWishListException if a matching commerce wish list could not be found
	*/
	public CommerceWishList findByG_U_Last(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Returns the last commerce wish list in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	public CommerceWishList fetchByG_U_Last(long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns the commerce wish lists before and after the current commerce wish list in the ordered set where groupId = &#63; and userId = &#63;.
	*
	* @param commerceWishListId the primary key of the current commerce wish list
	* @param groupId the group ID
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce wish list
	* @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	*/
	public CommerceWishList[] findByG_U_PrevAndNext(long commerceWishListId,
		long groupId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Removes all the commerce wish lists where groupId = &#63; and userId = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	*/
	public void removeByG_U(long groupId, long userId);

	/**
	* Returns the number of commerce wish lists where groupId = &#63; and userId = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @return the number of matching commerce wish lists
	*/
	public int countByG_U(long groupId, long userId);

	/**
	* Returns all the commerce wish lists where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultWishList the default wish list
	* @return the matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByG_U_D(long groupId,
		long userId, boolean defaultWishList);

	/**
	* Returns a range of all the commerce wish lists where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultWishList the default wish list
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @return the range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByG_U_D(long groupId,
		long userId, boolean defaultWishList, int start, int end);

	/**
	* Returns an ordered range of all the commerce wish lists where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultWishList the default wish list
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByG_U_D(long groupId,
		long userId, boolean defaultWishList, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce wish lists where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultWishList the default wish list
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce wish lists
	*/
	public java.util.List<CommerceWishList> findByG_U_D(long groupId,
		long userId, boolean defaultWishList, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce wish list in the ordered set where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultWishList the default wish list
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list
	* @throws NoSuchWishListException if a matching commerce wish list could not be found
	*/
	public CommerceWishList findByG_U_D_First(long groupId, long userId,
		boolean defaultWishList,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Returns the first commerce wish list in the ordered set where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultWishList the default wish list
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	public CommerceWishList fetchByG_U_D_First(long groupId, long userId,
		boolean defaultWishList,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns the last commerce wish list in the ordered set where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultWishList the default wish list
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list
	* @throws NoSuchWishListException if a matching commerce wish list could not be found
	*/
	public CommerceWishList findByG_U_D_Last(long groupId, long userId,
		boolean defaultWishList,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Returns the last commerce wish list in the ordered set where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultWishList the default wish list
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce wish list, or <code>null</code> if a matching commerce wish list could not be found
	*/
	public CommerceWishList fetchByG_U_D_Last(long groupId, long userId,
		boolean defaultWishList,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns the commerce wish lists before and after the current commerce wish list in the ordered set where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	*
	* @param commerceWishListId the primary key of the current commerce wish list
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultWishList the default wish list
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce wish list
	* @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	*/
	public CommerceWishList[] findByG_U_D_PrevAndNext(long commerceWishListId,
		long groupId, long userId, boolean defaultWishList,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator)
		throws NoSuchWishListException;

	/**
	* Removes all the commerce wish lists where groupId = &#63; and userId = &#63; and defaultWishList = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultWishList the default wish list
	*/
	public void removeByG_U_D(long groupId, long userId, boolean defaultWishList);

	/**
	* Returns the number of commerce wish lists where groupId = &#63; and userId = &#63; and defaultWishList = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultWishList the default wish list
	* @return the number of matching commerce wish lists
	*/
	public int countByG_U_D(long groupId, long userId, boolean defaultWishList);

	/**
	* Caches the commerce wish list in the entity cache if it is enabled.
	*
	* @param commerceWishList the commerce wish list
	*/
	public void cacheResult(CommerceWishList commerceWishList);

	/**
	* Caches the commerce wish lists in the entity cache if it is enabled.
	*
	* @param commerceWishLists the commerce wish lists
	*/
	public void cacheResult(java.util.List<CommerceWishList> commerceWishLists);

	/**
	* Creates a new commerce wish list with the primary key. Does not add the commerce wish list to the database.
	*
	* @param commerceWishListId the primary key for the new commerce wish list
	* @return the new commerce wish list
	*/
	public CommerceWishList create(long commerceWishListId);

	/**
	* Removes the commerce wish list with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceWishListId the primary key of the commerce wish list
	* @return the commerce wish list that was removed
	* @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	*/
	public CommerceWishList remove(long commerceWishListId)
		throws NoSuchWishListException;

	public CommerceWishList updateImpl(CommerceWishList commerceWishList);

	/**
	* Returns the commerce wish list with the primary key or throws a {@link NoSuchWishListException} if it could not be found.
	*
	* @param commerceWishListId the primary key of the commerce wish list
	* @return the commerce wish list
	* @throws NoSuchWishListException if a commerce wish list with the primary key could not be found
	*/
	public CommerceWishList findByPrimaryKey(long commerceWishListId)
		throws NoSuchWishListException;

	/**
	* Returns the commerce wish list with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceWishListId the primary key of the commerce wish list
	* @return the commerce wish list, or <code>null</code> if a commerce wish list with the primary key could not be found
	*/
	public CommerceWishList fetchByPrimaryKey(long commerceWishListId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceWishList> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce wish lists.
	*
	* @return the commerce wish lists
	*/
	public java.util.List<CommerceWishList> findAll();

	/**
	* Returns a range of all the commerce wish lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @return the range of commerce wish lists
	*/
	public java.util.List<CommerceWishList> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce wish lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce wish lists
	*/
	public java.util.List<CommerceWishList> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce wish lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceWishListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce wish lists
	* @param end the upper bound of the range of commerce wish lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce wish lists
	*/
	public java.util.List<CommerceWishList> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceWishList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce wish lists from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce wish lists.
	*
	* @return the number of commerce wish lists
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}