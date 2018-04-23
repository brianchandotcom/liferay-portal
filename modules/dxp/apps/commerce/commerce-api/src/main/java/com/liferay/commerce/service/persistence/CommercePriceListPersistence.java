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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchPriceListException;
import com.liferay.commerce.model.CommercePriceList;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

/**
 * The persistence interface for the commerce price list service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommercePriceListPersistenceImpl
 * @see CommercePriceListUtil
 * @generated
 */
@ProviderType
public interface CommercePriceListPersistence extends BasePersistence<CommercePriceList> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceListUtil} to access the commerce price list persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce price lists where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the commerce price lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce price lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price lists where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the first commerce price list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the last commerce price list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the last commerce price list in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where uuid = &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public CommercePriceList[] findByUuid_PrevAndNext(
		long commercePriceListId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Removes all the commerce price lists where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of commerce price lists where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce price lists
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the commerce price list where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchPriceListException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchPriceListException;

	/**
	* Returns the commerce price list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the commerce price list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the commerce price list where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce price list that was removed
	*/
	public CommercePriceList removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchPriceListException;

	/**
	* Returns the number of commerce price lists where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce price lists
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the commerce price lists where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the commerce price lists where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce price lists where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price lists where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the first commerce price list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the last commerce price list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the last commerce price list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public CommercePriceList[] findByUuid_C_PrevAndNext(
		long commercePriceListId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Removes all the commerce price lists where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of commerce price lists where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce price lists
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the commerce price lists where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce price lists where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce price lists where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price lists where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the first commerce price list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the last commerce price list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the last commerce price list in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where groupId = &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public CommercePriceList[] findByGroupId_PrevAndNext(
		long commercePriceListId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Removes all the commerce price lists where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce price lists where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce price lists
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the commerce price lists where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByCompanyId(long companyId);

	/**
	* Returns a range of all the commerce price lists where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce price lists where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price lists where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the first commerce price list in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the last commerce price list in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the last commerce price list in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where companyId = &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public CommercePriceList[] findByCompanyId_PrevAndNext(
		long commercePriceListId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Removes all the commerce price lists where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of commerce price lists where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce price lists
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns the commerce price list where parentCommercePriceListId = &#63; or throws a {@link NoSuchPriceListException} if it could not be found.
	*
	* @param parentCommercePriceListId the parent commerce price list ID
	* @return the matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByParentCommercePriceListId(
		long parentCommercePriceListId) throws NoSuchPriceListException;

	/**
	* Returns the commerce price list where parentCommercePriceListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param parentCommercePriceListId the parent commerce price list ID
	* @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByParentCommercePriceListId(
		long parentCommercePriceListId);

	/**
	* Returns the commerce price list where parentCommercePriceListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param parentCommercePriceListId the parent commerce price list ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByParentCommercePriceListId(
		long parentCommercePriceListId, boolean retrieveFromCache);

	/**
	* Removes the commerce price list where parentCommercePriceListId = &#63; from the database.
	*
	* @param parentCommercePriceListId the parent commerce price list ID
	* @return the commerce price list that was removed
	*/
	public CommercePriceList removeByParentCommercePriceListId(
		long parentCommercePriceListId) throws NoSuchPriceListException;

	/**
	* Returns the number of commerce price lists where parentCommercePriceListId = &#63;.
	*
	* @param parentCommercePriceListId the parent commerce price list ID
	* @return the number of matching commerce price lists
	*/
	public int countByParentCommercePriceListId(long parentCommercePriceListId);

	/**
	* Returns all the commerce price lists where commerceCurrencyId = &#63;.
	*
	* @param commerceCurrencyId the commerce currency ID
	* @return the matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByCommerceCurrencyId(
		long commerceCurrencyId);

	/**
	* Returns a range of all the commerce price lists where commerceCurrencyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCurrencyId the commerce currency ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByCommerceCurrencyId(
		long commerceCurrencyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce price lists where commerceCurrencyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCurrencyId the commerce currency ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByCommerceCurrencyId(
		long commerceCurrencyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price lists where commerceCurrencyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commerceCurrencyId the commerce currency ID
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByCommerceCurrencyId(
		long commerceCurrencyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list in the ordered set where commerceCurrencyId = &#63;.
	*
	* @param commerceCurrencyId the commerce currency ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByCommerceCurrencyId_First(
		long commerceCurrencyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the first commerce price list in the ordered set where commerceCurrencyId = &#63;.
	*
	* @param commerceCurrencyId the commerce currency ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByCommerceCurrencyId_First(
		long commerceCurrencyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the last commerce price list in the ordered set where commerceCurrencyId = &#63;.
	*
	* @param commerceCurrencyId the commerce currency ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByCommerceCurrencyId_Last(
		long commerceCurrencyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the last commerce price list in the ordered set where commerceCurrencyId = &#63;.
	*
	* @param commerceCurrencyId the commerce currency ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByCommerceCurrencyId_Last(
		long commerceCurrencyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where commerceCurrencyId = &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param commerceCurrencyId the commerce currency ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public CommercePriceList[] findByCommerceCurrencyId_PrevAndNext(
		long commercePriceListId, long commerceCurrencyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Removes all the commerce price lists where commerceCurrencyId = &#63; from the database.
	*
	* @param commerceCurrencyId the commerce currency ID
	*/
	public void removeByCommerceCurrencyId(long commerceCurrencyId);

	/**
	* Returns the number of commerce price lists where commerceCurrencyId = &#63;.
	*
	* @param commerceCurrencyId the commerce currency ID
	* @return the number of matching commerce price lists
	*/
	public int countByCommerceCurrencyId(long commerceCurrencyId);

	/**
	* Returns all the commerce price lists where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByG_S(long groupId, int status);

	/**
	* Returns a range of all the commerce price lists where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByG_S(long groupId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the commerce price lists where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByG_S(long groupId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price lists where groupId = &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByG_S(long groupId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByG_S_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the first commerce price list in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByG_S_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the last commerce price list in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByG_S_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the last commerce price list in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByG_S_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where groupId = &#63; and status = &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public CommercePriceList[] findByG_S_PrevAndNext(long commercePriceListId,
		long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Removes all the commerce price lists where groupId = &#63; and status = &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public void removeByG_S(long groupId, int status);

	/**
	* Returns the number of commerce price lists where groupId = &#63; and status = &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching commerce price lists
	*/
	public int countByG_S(long groupId, int status);

	/**
	* Returns all the commerce price lists where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByG_NotS(long groupId,
		int status);

	/**
	* Returns a range of all the commerce price lists where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByG_NotS(long groupId,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the commerce price lists where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByG_NotS(long groupId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price lists where groupId = &#63; and status &ne; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByG_NotS(long groupId,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByG_NotS_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the first commerce price list in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByG_NotS_First(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the last commerce price list in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByG_NotS_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the last commerce price list in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByG_NotS_Last(long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where groupId = &#63; and status &ne; &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param groupId the group ID
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public CommercePriceList[] findByG_NotS_PrevAndNext(
		long commercePriceListId, long groupId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Removes all the commerce price lists where groupId = &#63; and status &ne; &#63; from the database.
	*
	* @param groupId the group ID
	* @param status the status
	*/
	public void removeByG_NotS(long groupId, int status);

	/**
	* Returns the number of commerce price lists where groupId = &#63; and status &ne; &#63;.
	*
	* @param groupId the group ID
	* @param status the status
	* @return the number of matching commerce price lists
	*/
	public int countByG_NotS(long groupId, int status);

	/**
	* Returns all the commerce price lists where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByLtD_S(Date displayDate,
		int status);

	/**
	* Returns a range of all the commerce price lists where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByLtD_S(Date displayDate,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the commerce price lists where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByLtD_S(Date displayDate,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price lists where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price lists
	*/
	public java.util.List<CommercePriceList> findByLtD_S(Date displayDate,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByLtD_S_First(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the first commerce price list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByLtD_S_First(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the last commerce price list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list
	* @throws NoSuchPriceListException if a matching commerce price list could not be found
	*/
	public CommercePriceList findByLtD_S_Last(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Returns the last commerce price list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	*/
	public CommercePriceList fetchByLtD_S_Last(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns the commerce price lists before and after the current commerce price list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param commercePriceListId the primary key of the current commerce price list
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public CommercePriceList[] findByLtD_S_PrevAndNext(
		long commercePriceListId, Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator)
		throws NoSuchPriceListException;

	/**
	* Removes all the commerce price lists where displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param displayDate the display date
	* @param status the status
	*/
	public void removeByLtD_S(Date displayDate, int status);

	/**
	* Returns the number of commerce price lists where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching commerce price lists
	*/
	public int countByLtD_S(Date displayDate, int status);

	/**
	* Caches the commerce price list in the entity cache if it is enabled.
	*
	* @param commercePriceList the commerce price list
	*/
	public void cacheResult(CommercePriceList commercePriceList);

	/**
	* Caches the commerce price lists in the entity cache if it is enabled.
	*
	* @param commercePriceLists the commerce price lists
	*/
	public void cacheResult(
		java.util.List<CommercePriceList> commercePriceLists);

	/**
	* Creates a new commerce price list with the primary key. Does not add the commerce price list to the database.
	*
	* @param commercePriceListId the primary key for the new commerce price list
	* @return the new commerce price list
	*/
	public CommercePriceList create(long commercePriceListId);

	/**
	* Removes the commerce price list with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListId the primary key of the commerce price list
	* @return the commerce price list that was removed
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public CommercePriceList remove(long commercePriceListId)
		throws NoSuchPriceListException;

	public CommercePriceList updateImpl(CommercePriceList commercePriceList);

	/**
	* Returns the commerce price list with the primary key or throws a {@link NoSuchPriceListException} if it could not be found.
	*
	* @param commercePriceListId the primary key of the commerce price list
	* @return the commerce price list
	* @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	*/
	public CommercePriceList findByPrimaryKey(long commercePriceListId)
		throws NoSuchPriceListException;

	/**
	* Returns the commerce price list with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commercePriceListId the primary key of the commerce price list
	* @return the commerce price list, or <code>null</code> if a commerce price list with the primary key could not be found
	*/
	public CommercePriceList fetchByPrimaryKey(long commercePriceListId);

	@Override
	public java.util.Map<java.io.Serializable, CommercePriceList> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce price lists.
	*
	* @return the commerce price lists
	*/
	public java.util.List<CommercePriceList> findAll();

	/**
	* Returns a range of all the commerce price lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @return the range of commerce price lists
	*/
	public java.util.List<CommercePriceList> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce price lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce price lists
	*/
	public java.util.List<CommercePriceList> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price lists
	* @param end the upper bound of the range of commerce price lists (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce price lists
	*/
	public java.util.List<CommercePriceList> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce price lists from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce price lists.
	*
	* @return the number of commerce price lists
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}