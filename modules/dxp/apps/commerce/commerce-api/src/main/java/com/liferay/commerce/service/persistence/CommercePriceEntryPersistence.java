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

import com.liferay.commerce.exception.NoSuchPriceEntryException;
import com.liferay.commerce.model.CommercePriceEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce price entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommercePriceEntryPersistenceImpl
 * @see CommercePriceEntryUtil
 * @generated
 */
@ProviderType
public interface CommercePriceEntryPersistence extends BasePersistence<CommercePriceEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceEntryUtil} to access the commerce price entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce price entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the commerce price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the commerce price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price entries where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Returns the first commerce price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns the last commerce price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Returns the last commerce price entry in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns the commerce price entries before and after the current commerce price entry in the ordered set where uuid = &#63;.
	*
	* @param commercePriceEntryId the primary key of the current commerce price entry
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price entry
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public CommercePriceEntry[] findByUuid_PrevAndNext(
		long commercePriceEntryId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Removes all the commerce price entries where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of commerce price entries where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce price entries
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the commerce price entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchPriceEntryException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchPriceEntryException;

	/**
	* Returns the commerce price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the commerce price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByUUID_G(java.lang.String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the commerce price entry where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce price entry that was removed
	*/
	public CommercePriceEntry removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchPriceEntryException;

	/**
	* Returns the number of commerce price entries where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce price entries
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the commerce price entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the commerce price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price entries where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Returns the first commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns the last commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Returns the last commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns the commerce price entries before and after the current commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commercePriceEntryId the primary key of the current commerce price entry
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price entry
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public CommercePriceEntry[] findByUuid_C_PrevAndNext(
		long commercePriceEntryId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Removes all the commerce price entries where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of commerce price entries where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce price entries
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the commerce price entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price entries where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Returns the first commerce price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns the last commerce price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Returns the last commerce price entry in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns the commerce price entries before and after the current commerce price entry in the ordered set where groupId = &#63;.
	*
	* @param commercePriceEntryId the primary key of the current commerce price entry
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price entry
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public CommercePriceEntry[] findByGroupId_PrevAndNext(
		long commercePriceEntryId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Removes all the commerce price entries where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce price entries where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce price entries
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the commerce price entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByCompanyId(long companyId);

	/**
	* Returns a range of all the commerce price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByCompanyId(long companyId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price entries where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByCompanyId(long companyId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry findByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Returns the first commerce price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByCompanyId_First(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns the last commerce price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry findByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Returns the last commerce price entry in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByCompanyId_Last(long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns the commerce price entries before and after the current commerce price entry in the ordered set where companyId = &#63;.
	*
	* @param commercePriceEntryId the primary key of the current commerce price entry
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price entry
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public CommercePriceEntry[] findByCompanyId_PrevAndNext(
		long commercePriceEntryId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Removes all the commerce price entries where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of commerce price entries where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching commerce price entries
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns all the commerce price entries where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByCPInstanceId(
		long CPInstanceId);

	/**
	* Returns a range of all the commerce price entries where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByCPInstanceId(
		long CPInstanceId, int start, int end);

	/**
	* Returns an ordered range of all the commerce price entries where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price entries where CPInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CPInstanceId the cp instance ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByCPInstanceId(
		long CPInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price entry in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry findByCPInstanceId_First(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Returns the first commerce price entry in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByCPInstanceId_First(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns the last commerce price entry in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry findByCPInstanceId_Last(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Returns the last commerce price entry in the ordered set where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByCPInstanceId_Last(long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns the commerce price entries before and after the current commerce price entry in the ordered set where CPInstanceId = &#63;.
	*
	* @param commercePriceEntryId the primary key of the current commerce price entry
	* @param CPInstanceId the cp instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price entry
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public CommercePriceEntry[] findByCPInstanceId_PrevAndNext(
		long commercePriceEntryId, long CPInstanceId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Removes all the commerce price entries where CPInstanceId = &#63; from the database.
	*
	* @param CPInstanceId the cp instance ID
	*/
	public void removeByCPInstanceId(long CPInstanceId);

	/**
	* Returns the number of commerce price entries where CPInstanceId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @return the number of matching commerce price entries
	*/
	public int countByCPInstanceId(long CPInstanceId);

	/**
	* Returns all the commerce price entries where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @return the matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByCommercePriceListId(
		long commercePriceListId);

	/**
	* Returns a range of all the commerce price entries where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByCommercePriceListId(
		long commercePriceListId, int start, int end);

	/**
	* Returns an ordered range of all the commerce price entries where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price entries where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price entry in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry findByCommercePriceListId_First(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Returns the first commerce price entry in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByCommercePriceListId_First(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns the last commerce price entry in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry findByCommercePriceListId_Last(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Returns the last commerce price entry in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByCommercePriceListId_Last(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns the commerce price entries before and after the current commerce price entry in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceEntryId the primary key of the current commerce price entry
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price entry
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public CommercePriceEntry[] findByCommercePriceListId_PrevAndNext(
		long commercePriceEntryId, long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	* Removes all the commerce price entries where commercePriceListId = &#63; from the database.
	*
	* @param commercePriceListId the commerce price list ID
	*/
	public void removeByCommercePriceListId(long commercePriceListId);

	/**
	* Returns the number of commerce price entries where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @return the number of matching commerce price entries
	*/
	public int countByCommercePriceListId(long commercePriceListId);

	/**
	* Returns the commerce price entry where CPInstanceId = &#63; and commercePriceListId = &#63; or throws a {@link NoSuchPriceEntryException} if it could not be found.
	*
	* @param CPInstanceId the cp instance ID
	* @param commercePriceListId the commerce price list ID
	* @return the matching commerce price entry
	* @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry findByC_C(long CPInstanceId,
		long commercePriceListId) throws NoSuchPriceEntryException;

	/**
	* Returns the commerce price entry where CPInstanceId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param CPInstanceId the cp instance ID
	* @param commercePriceListId the commerce price list ID
	* @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByC_C(long CPInstanceId,
		long commercePriceListId);

	/**
	* Returns the commerce price entry where CPInstanceId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param CPInstanceId the cp instance ID
	* @param commercePriceListId the commerce price list ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	*/
	public CommercePriceEntry fetchByC_C(long CPInstanceId,
		long commercePriceListId, boolean retrieveFromCache);

	/**
	* Removes the commerce price entry where CPInstanceId = &#63; and commercePriceListId = &#63; from the database.
	*
	* @param CPInstanceId the cp instance ID
	* @param commercePriceListId the commerce price list ID
	* @return the commerce price entry that was removed
	*/
	public CommercePriceEntry removeByC_C(long CPInstanceId,
		long commercePriceListId) throws NoSuchPriceEntryException;

	/**
	* Returns the number of commerce price entries where CPInstanceId = &#63; and commercePriceListId = &#63;.
	*
	* @param CPInstanceId the cp instance ID
	* @param commercePriceListId the commerce price list ID
	* @return the number of matching commerce price entries
	*/
	public int countByC_C(long CPInstanceId, long commercePriceListId);

	/**
	* Caches the commerce price entry in the entity cache if it is enabled.
	*
	* @param commercePriceEntry the commerce price entry
	*/
	public void cacheResult(CommercePriceEntry commercePriceEntry);

	/**
	* Caches the commerce price entries in the entity cache if it is enabled.
	*
	* @param commercePriceEntries the commerce price entries
	*/
	public void cacheResult(
		java.util.List<CommercePriceEntry> commercePriceEntries);

	/**
	* Creates a new commerce price entry with the primary key. Does not add the commerce price entry to the database.
	*
	* @param commercePriceEntryId the primary key for the new commerce price entry
	* @return the new commerce price entry
	*/
	public CommercePriceEntry create(long commercePriceEntryId);

	/**
	* Removes the commerce price entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceEntryId the primary key of the commerce price entry
	* @return the commerce price entry that was removed
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public CommercePriceEntry remove(long commercePriceEntryId)
		throws NoSuchPriceEntryException;

	public CommercePriceEntry updateImpl(CommercePriceEntry commercePriceEntry);

	/**
	* Returns the commerce price entry with the primary key or throws a {@link NoSuchPriceEntryException} if it could not be found.
	*
	* @param commercePriceEntryId the primary key of the commerce price entry
	* @return the commerce price entry
	* @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	*/
	public CommercePriceEntry findByPrimaryKey(long commercePriceEntryId)
		throws NoSuchPriceEntryException;

	/**
	* Returns the commerce price entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commercePriceEntryId the primary key of the commerce price entry
	* @return the commerce price entry, or <code>null</code> if a commerce price entry with the primary key could not be found
	*/
	public CommercePriceEntry fetchByPrimaryKey(long commercePriceEntryId);

	@Override
	public java.util.Map<java.io.Serializable, CommercePriceEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce price entries.
	*
	* @return the commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findAll();

	/**
	* Returns a range of all the commerce price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @return the range of commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price entries
	* @param end the upper bound of the range of commerce price entries (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce price entries
	*/
	public java.util.List<CommercePriceEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce price entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce price entries.
	*
	* @return the number of commerce price entries
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}