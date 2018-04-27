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

package com.liferay.commerce.price.list.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.exception.NoSuchPriceListUserSegmentEntryRelException;
import com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce price list user segment entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.price.list.service.persistence.impl.CommercePriceListUserSegmentEntryRelPersistenceImpl
 * @see CommercePriceListUserSegmentEntryRelUtil
 * @generated
 */
@ProviderType
public interface CommercePriceListUserSegmentEntryRelPersistence
	extends BasePersistence<CommercePriceListUserSegmentEntryRel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceListUserSegmentEntryRelUtil} to access the commerce price list user segment entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce price list user segment entry rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findByUuid(
		String uuid);

	/**
	* Returns a range of all the commerce price list user segment entry rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @return the range of matching commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findByUuid(
		String uuid, int start, int end);

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list user segment entry rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException;

	/**
	* Returns the first commerce price list user segment entry rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator);

	/**
	* Returns the last commerce price list user segment entry rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException;

	/**
	* Returns the last commerce price list user segment entry rel in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator);

	/**
	* Returns the commerce price list user segment entry rels before and after the current commerce price list user segment entry rel in the ordered set where uuid = &#63;.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the current commerce price list user segment entry rel
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	*/
	public CommercePriceListUserSegmentEntryRel[] findByUuid_PrevAndNext(
		long commercePriceListUserSegmentEntryRelId, String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException;

	/**
	* Removes all the commerce price list user segment entry rels where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of commerce price list user segment entry rels where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce price list user segment entry rels
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the commerce price list user segment entry rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchPriceListUserSegmentEntryRelException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel findByUUID_G(String uuid,
		long groupId) throws NoSuchPriceListUserSegmentEntryRelException;

	/**
	* Returns the commerce price list user segment entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel fetchByUUID_G(String uuid,
		long groupId);

	/**
	* Returns the commerce price list user segment entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache);

	/**
	* Removes the commerce price list user segment entry rel where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce price list user segment entry rel that was removed
	*/
	public CommercePriceListUserSegmentEntryRel removeByUUID_G(String uuid,
		long groupId) throws NoSuchPriceListUserSegmentEntryRelException;

	/**
	* Returns the number of commerce price list user segment entry rels where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce price list user segment entry rels
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the commerce price list user segment entry rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findByUuid_C(
		String uuid, long companyId);

	/**
	* Returns a range of all the commerce price list user segment entry rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @return the range of matching commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list user segment entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel findByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException;

	/**
	* Returns the first commerce price list user segment entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator);

	/**
	* Returns the last commerce price list user segment entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel findByUuid_C_Last(String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException;

	/**
	* Returns the last commerce price list user segment entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator);

	/**
	* Returns the commerce price list user segment entry rels before and after the current commerce price list user segment entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the current commerce price list user segment entry rel
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	*/
	public CommercePriceListUserSegmentEntryRel[] findByUuid_C_PrevAndNext(
		long commercePriceListUserSegmentEntryRelId, String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException;

	/**
	* Removes all the commerce price list user segment entry rels where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of commerce price list user segment entry rels where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce price list user segment entry rels
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the commerce price list user segment entry rels where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @return the matching commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findByCommercePriceListId(
		long commercePriceListId);

	/**
	* Returns a range of all the commerce price list user segment entry rels where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @return the range of matching commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end);

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels where commercePriceListId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param commercePriceListId the commerce price list ID
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce price list user segment entry rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel findByCommercePriceListId_First(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException;

	/**
	* Returns the first commerce price list user segment entry rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator);

	/**
	* Returns the last commerce price list user segment entry rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel findByCommercePriceListId_Last(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException;

	/**
	* Returns the last commerce price list user segment entry rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator);

	/**
	* Returns the commerce price list user segment entry rels before and after the current commerce price list user segment entry rel in the ordered set where commercePriceListId = &#63;.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the current commerce price list user segment entry rel
	* @param commercePriceListId the commerce price list ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	*/
	public CommercePriceListUserSegmentEntryRel[] findByCommercePriceListId_PrevAndNext(
		long commercePriceListUserSegmentEntryRelId, long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException;

	/**
	* Removes all the commerce price list user segment entry rels where commercePriceListId = &#63; from the database.
	*
	* @param commercePriceListId the commerce price list ID
	*/
	public void removeByCommercePriceListId(long commercePriceListId);

	/**
	* Returns the number of commerce price list user segment entry rels where commercePriceListId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @return the number of matching commerce price list user segment entry rels
	*/
	public int countByCommercePriceListId(long commercePriceListId);

	/**
	* Returns the commerce price list user segment entry rel where commercePriceListId = &#63; and commerceUserSegmentEntryId = &#63; or throws a {@link NoSuchPriceListUserSegmentEntryRelException} if it could not be found.
	*
	* @param commercePriceListId the commerce price list ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel findByC_C(
		long commercePriceListId, long commerceUserSegmentEntryId)
		throws NoSuchPriceListUserSegmentEntryRelException;

	/**
	* Returns the commerce price list user segment entry rel where commercePriceListId = &#63; and commerceUserSegmentEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param commercePriceListId the commerce price list ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel fetchByC_C(
		long commercePriceListId, long commerceUserSegmentEntryId);

	/**
	* Returns the commerce price list user segment entry rel where commercePriceListId = &#63; and commerceUserSegmentEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param commercePriceListId the commerce price list ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	*/
	public CommercePriceListUserSegmentEntryRel fetchByC_C(
		long commercePriceListId, long commerceUserSegmentEntryId,
		boolean retrieveFromCache);

	/**
	* Removes the commerce price list user segment entry rel where commercePriceListId = &#63; and commerceUserSegmentEntryId = &#63; from the database.
	*
	* @param commercePriceListId the commerce price list ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the commerce price list user segment entry rel that was removed
	*/
	public CommercePriceListUserSegmentEntryRel removeByC_C(
		long commercePriceListId, long commerceUserSegmentEntryId)
		throws NoSuchPriceListUserSegmentEntryRelException;

	/**
	* Returns the number of commerce price list user segment entry rels where commercePriceListId = &#63; and commerceUserSegmentEntryId = &#63;.
	*
	* @param commercePriceListId the commerce price list ID
	* @param commerceUserSegmentEntryId the commerce user segment entry ID
	* @return the number of matching commerce price list user segment entry rels
	*/
	public int countByC_C(long commercePriceListId,
		long commerceUserSegmentEntryId);

	/**
	* Caches the commerce price list user segment entry rel in the entity cache if it is enabled.
	*
	* @param commercePriceListUserSegmentEntryRel the commerce price list user segment entry rel
	*/
	public void cacheResult(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel);

	/**
	* Caches the commerce price list user segment entry rels in the entity cache if it is enabled.
	*
	* @param commercePriceListUserSegmentEntryRels the commerce price list user segment entry rels
	*/
	public void cacheResult(
		java.util.List<CommercePriceListUserSegmentEntryRel> commercePriceListUserSegmentEntryRels);

	/**
	* Creates a new commerce price list user segment entry rel with the primary key. Does not add the commerce price list user segment entry rel to the database.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key for the new commerce price list user segment entry rel
	* @return the new commerce price list user segment entry rel
	*/
	public CommercePriceListUserSegmentEntryRel create(
		long commercePriceListUserSegmentEntryRelId);

	/**
	* Removes the commerce price list user segment entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel that was removed
	* @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	*/
	public CommercePriceListUserSegmentEntryRel remove(
		long commercePriceListUserSegmentEntryRelId)
		throws NoSuchPriceListUserSegmentEntryRelException;

	public CommercePriceListUserSegmentEntryRel updateImpl(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel);

	/**
	* Returns the commerce price list user segment entry rel with the primary key or throws a {@link NoSuchPriceListUserSegmentEntryRelException} if it could not be found.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel
	* @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	*/
	public CommercePriceListUserSegmentEntryRel findByPrimaryKey(
		long commercePriceListUserSegmentEntryRelId)
		throws NoSuchPriceListUserSegmentEntryRelException;

	/**
	* Returns the commerce price list user segment entry rel with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commercePriceListUserSegmentEntryRelId the primary key of the commerce price list user segment entry rel
	* @return the commerce price list user segment entry rel, or <code>null</code> if a commerce price list user segment entry rel with the primary key could not be found
	*/
	public CommercePriceListUserSegmentEntryRel fetchByPrimaryKey(
		long commercePriceListUserSegmentEntryRelId);

	@Override
	public java.util.Map<java.io.Serializable, CommercePriceListUserSegmentEntryRel> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce price list user segment entry rels.
	*
	* @return the commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findAll();

	/**
	* Returns a range of all the commerce price list user segment entry rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @return the range of commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator);

	/**
	* Returns an ordered range of all the commerce price list user segment entry rels.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce price list user segment entry rels
	* @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce price list user segment entry rels
	*/
	public java.util.List<CommercePriceListUserSegmentEntryRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce price list user segment entry rels from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce price list user segment entry rels.
	*
	* @return the number of commerce price list user segment entry rels
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}