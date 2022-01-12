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

import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce price list service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUtil
 * @generated
 */
@ProviderType
public interface CommercePriceListPersistence
	extends BasePersistence<CommercePriceList>,
			CTPersistence<CommercePriceList> {

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
	public java.util.List<CommercePriceList> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce price lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price lists where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns the first commerce price list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns the last commerce price list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns the last commerce price list in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

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
			long commercePriceListId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Removes all the commerce price lists where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce price lists where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price lists
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the commerce price list where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchPriceListException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByUUID_G(String uuid, long groupId)
		throws NoSuchPriceListException;

	/**
	 * Returns the commerce price list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the commerce price list where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the commerce price list where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce price list that was removed
	 */
	public CommercePriceList removeByUUID_G(String uuid, long groupId)
		throws NoSuchPriceListException;

	/**
	 * Returns the number of commerce price lists where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce price lists
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the commerce price lists where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce price lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
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
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price lists where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns the first commerce price list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns the last commerce price list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns the last commerce price list in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

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
			long commercePriceListId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Removes all the commerce price lists where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce price lists where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price lists
	 */
	public int countByUuid_C(String uuid, long companyId);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price lists where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns the first commerce price list in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns the last commerce price list in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns the last commerce price list in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
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
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price lists where commerceCurrencyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param commerceCurrencyId the commerce currency ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByCommerceCurrencyId(
		long commerceCurrencyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator,
		boolean useFinderCache);

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
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
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
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
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
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
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
	 * Returns the commerce price list where parentCommercePriceListId = &#63; or throws a <code>NoSuchPriceListException</code> if it could not be found.
	 *
	 * @param parentCommercePriceListId the parent commerce price list ID
	 * @return the matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByParentCommercePriceListId(
			long parentCommercePriceListId)
		throws NoSuchPriceListException;

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
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByParentCommercePriceListId(
		long parentCommercePriceListId, boolean useFinderCache);

	/**
	 * Removes the commerce price list where parentCommercePriceListId = &#63; from the database.
	 *
	 * @param parentCommercePriceListId the parent commerce price list ID
	 * @return the commerce price list that was removed
	 */
	public CommercePriceList removeByParentCommercePriceListId(
			long parentCommercePriceListId)
		throws NoSuchPriceListException;

	/**
	 * Returns the number of commerce price lists where parentCommercePriceListId = &#63;.
	 *
	 * @param parentCommercePriceListId the parent commerce price list ID
	 * @return the number of matching commerce price lists
	 */
	public int countByParentCommercePriceListId(long parentCommercePriceListId);

	/**
	 * Returns all the commerce price lists where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C(
		long groupId, long companyId);

	/**
	 * Returns a range of all the commerce price lists where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C(
		long groupId, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C(
		long groupId, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price lists where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C(
		long groupId, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByG_C_First(
			long groupId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns the first commerce price list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByG_C_First(
		long groupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns the last commerce price list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByG_C_Last(
			long groupId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns the last commerce price list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByG_C_Last(
		long groupId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns the commerce price lists before and after the current commerce price list in the ordered set where groupId = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceListId the primary key of the current commerce price list
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list
	 * @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	 */
	public CommercePriceList[] findByG_C_PrevAndNext(
			long commercePriceListId, long groupId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns all the commerce price lists that the user has permission to view where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C(
		long groupId, long companyId);

	/**
	 * Returns a range of all the commerce price lists that the user has permission to view where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C(
		long groupId, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists that the user has permissions to view where groupId = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C(
		long groupId, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns the commerce price lists before and after the current commerce price list in the ordered set of commerce price lists that the user has permission to view where groupId = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceListId the primary key of the current commerce price list
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list
	 * @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	 */
	public CommercePriceList[] filterFindByG_C_PrevAndNext(
			long commercePriceListId, long groupId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns all the commerce price lists that the user has permission to view where groupId = any &#63; and companyId = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @return the matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C(
		long[] groupIds, long companyId);

	/**
	 * Returns a range of all the commerce price lists that the user has permission to view where groupId = any &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C(
		long[] groupIds, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists that the user has permission to view where groupId = any &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C(
		long[] groupIds, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns all the commerce price lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @return the matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C(
		long[] groupIds, long companyId);

	/**
	 * Returns a range of all the commerce price lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C(
		long[] groupIds, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C(
		long[] groupIds, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price lists where groupId = &#63; and companyId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C(
		long[] groupIds, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce price lists where groupId = &#63; and companyId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 */
	public void removeByG_C(long groupId, long companyId);

	/**
	 * Returns the number of commerce price lists where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the number of matching commerce price lists
	 */
	public int countByG_C(long groupId, long companyId);

	/**
	 * Returns the number of commerce price lists where groupId = any &#63; and companyId = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @return the number of matching commerce price lists
	 */
	public int countByG_C(long[] groupIds, long companyId);

	/**
	 * Returns the number of commerce price lists that the user has permission to view where groupId = &#63; and companyId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @return the number of matching commerce price lists that the user has permission to view
	 */
	public int filterCountByG_C(long groupId, long companyId);

	/**
	 * Returns the number of commerce price lists that the user has permission to view where groupId = any &#63; and companyId = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @return the number of matching commerce price lists that the user has permission to view
	 */
	public int filterCountByG_C(long[] groupIds, long companyId);

	/**
	 * Returns the commerce price list where groupId = &#63; and catalogBasePriceList = &#63; or throws a <code>NoSuchPriceListException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param catalogBasePriceList the catalog base price list
	 * @return the matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByG_CatalogBasePriceList(
			long groupId, boolean catalogBasePriceList)
		throws NoSuchPriceListException;

	/**
	 * Returns the commerce price list where groupId = &#63; and catalogBasePriceList = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param catalogBasePriceList the catalog base price list
	 * @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByG_CatalogBasePriceList(
		long groupId, boolean catalogBasePriceList);

	/**
	 * Returns the commerce price list where groupId = &#63; and catalogBasePriceList = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param catalogBasePriceList the catalog base price list
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByG_CatalogBasePriceList(
		long groupId, boolean catalogBasePriceList, boolean useFinderCache);

	/**
	 * Removes the commerce price list where groupId = &#63; and catalogBasePriceList = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param catalogBasePriceList the catalog base price list
	 * @return the commerce price list that was removed
	 */
	public CommercePriceList removeByG_CatalogBasePriceList(
			long groupId, boolean catalogBasePriceList)
		throws NoSuchPriceListException;

	/**
	 * Returns the number of commerce price lists where groupId = &#63; and catalogBasePriceList = &#63;.
	 *
	 * @param groupId the group ID
	 * @param catalogBasePriceList the catalog base price list
	 * @return the number of matching commerce price lists
	 */
	public int countByG_CatalogBasePriceList(
		long groupId, boolean catalogBasePriceList);

	/**
	 * Returns all the commerce price lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByLtD_S(
		Date displayDate, int status);

	/**
	 * Returns a range of all the commerce price lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByLtD_S(
		Date displayDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price lists where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByLtD_S_First(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns the first commerce price list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByLtD_S_First(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns the last commerce price list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByLtD_S_Last(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns the last commerce price list in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByLtD_S_Last(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
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
	 * Returns all the commerce price lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_S(
		long groupId, long companyId, int status);

	/**
	 * Returns a range of all the commerce price lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_S(
		long groupId, long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_S(
		long groupId, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_S(
		long groupId, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByG_C_S_First(
			long groupId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns the first commerce price list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByG_C_S_First(
		long groupId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns the last commerce price list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByG_C_S_Last(
			long groupId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns the last commerce price list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByG_C_S_Last(
		long groupId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns the commerce price lists before and after the current commerce price list in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the primary key of the current commerce price list
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list
	 * @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	 */
	public CommercePriceList[] findByG_C_S_PrevAndNext(
			long commercePriceListId, long groupId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns all the commerce price lists that the user has permission to view where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C_S(
		long groupId, long companyId, int status);

	/**
	 * Returns a range of all the commerce price lists that the user has permission to view where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C_S(
		long groupId, long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists that the user has permissions to view where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C_S(
		long groupId, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns the commerce price lists before and after the current commerce price list in the ordered set of commerce price lists that the user has permission to view where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the primary key of the current commerce price list
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list
	 * @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	 */
	public CommercePriceList[] filterFindByG_C_S_PrevAndNext(
			long commercePriceListId, long groupId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns all the commerce price lists that the user has permission to view where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C_S(
		long[] groupIds, long companyId, int status);

	/**
	 * Returns a range of all the commerce price lists that the user has permission to view where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists that the user has permission to view where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns all the commerce price lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_S(
		long[] groupIds, long companyId, int status);

	/**
	 * Returns a range of all the commerce price lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price lists where groupId = &#63; and companyId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce price lists where groupId = &#63; and companyId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 */
	public void removeByG_C_S(long groupId, long companyId, int status);

	/**
	 * Returns the number of commerce price lists where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching commerce price lists
	 */
	public int countByG_C_S(long groupId, long companyId, int status);

	/**
	 * Returns the number of commerce price lists where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching commerce price lists
	 */
	public int countByG_C_S(long[] groupIds, long companyId, int status);

	/**
	 * Returns the number of commerce price lists that the user has permission to view where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching commerce price lists that the user has permission to view
	 */
	public int filterCountByG_C_S(long groupId, long companyId, int status);

	/**
	 * Returns the number of commerce price lists that the user has permission to view where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching commerce price lists that the user has permission to view
	 */
	public int filterCountByG_C_S(long[] groupIds, long companyId, int status);

	/**
	 * Returns all the commerce price lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_NotS(
		long groupId, long companyId, int status);

	/**
	 * Returns a range of all the commerce price lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_NotS(
		long groupId, long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_NotS(
		long groupId, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_NotS(
		long groupId, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByG_C_NotS_First(
			long groupId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns the first commerce price list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByG_C_NotS_First(
		long groupId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns the last commerce price list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByG_C_NotS_Last(
			long groupId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns the last commerce price list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByG_C_NotS_Last(
		long groupId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns the commerce price lists before and after the current commerce price list in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param commercePriceListId the primary key of the current commerce price list
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list
	 * @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	 */
	public CommercePriceList[] findByG_C_NotS_PrevAndNext(
			long commercePriceListId, long groupId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns all the commerce price lists that the user has permission to view where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C_NotS(
		long groupId, long companyId, int status);

	/**
	 * Returns a range of all the commerce price lists that the user has permission to view where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C_NotS(
		long groupId, long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists that the user has permissions to view where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C_NotS(
		long groupId, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns the commerce price lists before and after the current commerce price list in the ordered set of commerce price lists that the user has permission to view where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param commercePriceListId the primary key of the current commerce price list
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list
	 * @throws NoSuchPriceListException if a commerce price list with the primary key could not be found
	 */
	public CommercePriceList[] filterFindByG_C_NotS_PrevAndNext(
			long commercePriceListId, long groupId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws NoSuchPriceListException;

	/**
	 * Returns all the commerce price lists that the user has permission to view where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C_NotS(
		long[] groupIds, long companyId, int status);

	/**
	 * Returns a range of all the commerce price lists that the user has permission to view where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists that the user has permission to view where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price lists that the user has permission to view
	 */
	public java.util.List<CommercePriceList> filterFindByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns all the commerce price lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_NotS(
		long[] groupIds, long companyId, int status);

	/**
	 * Returns a range of all the commerce price lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @return the range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price lists
	 */
	public java.util.List<CommercePriceList> findByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce price lists where groupId = &#63; and companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 */
	public void removeByG_C_NotS(long groupId, long companyId, int status);

	/**
	 * Returns the number of commerce price lists where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching commerce price lists
	 */
	public int countByG_C_NotS(long groupId, long companyId, int status);

	/**
	 * Returns the number of commerce price lists where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching commerce price lists
	 */
	public int countByG_C_NotS(long[] groupIds, long companyId, int status);

	/**
	 * Returns the number of commerce price lists that the user has permission to view where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching commerce price lists that the user has permission to view
	 */
	public int filterCountByG_C_NotS(long groupId, long companyId, int status);

	/**
	 * Returns the number of commerce price lists that the user has permission to view where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching commerce price lists that the user has permission to view
	 */
	public int filterCountByG_C_NotS(
		long[] groupIds, long companyId, int status);

	/**
	 * Returns the commerce price list where groupId = &#63; and catalogBasePriceList = &#63; and type = &#63; or throws a <code>NoSuchPriceListException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param catalogBasePriceList the catalog base price list
	 * @param type the type
	 * @return the matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByG_C_T(
			long groupId, boolean catalogBasePriceList, String type)
		throws NoSuchPriceListException;

	/**
	 * Returns the commerce price list where groupId = &#63; and catalogBasePriceList = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param catalogBasePriceList the catalog base price list
	 * @param type the type
	 * @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByG_C_T(
		long groupId, boolean catalogBasePriceList, String type);

	/**
	 * Returns the commerce price list where groupId = &#63; and catalogBasePriceList = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param catalogBasePriceList the catalog base price list
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByG_C_T(
		long groupId, boolean catalogBasePriceList, String type,
		boolean useFinderCache);

	/**
	 * Removes the commerce price list where groupId = &#63; and catalogBasePriceList = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param catalogBasePriceList the catalog base price list
	 * @param type the type
	 * @return the commerce price list that was removed
	 */
	public CommercePriceList removeByG_C_T(
			long groupId, boolean catalogBasePriceList, String type)
		throws NoSuchPriceListException;

	/**
	 * Returns the number of commerce price lists where groupId = &#63; and catalogBasePriceList = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param catalogBasePriceList the catalog base price list
	 * @param type the type
	 * @return the number of matching commerce price lists
	 */
	public int countByG_C_T(
		long groupId, boolean catalogBasePriceList, String type);

	/**
	 * Returns the commerce price list where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchPriceListException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce price list
	 * @throws NoSuchPriceListException if a matching commerce price list could not be found
	 */
	public CommercePriceList findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchPriceListException;

	/**
	 * Returns the commerce price list where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the commerce price list where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list, or <code>null</code> if a matching commerce price list could not be found
	 */
	public CommercePriceList fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the commerce price list where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce price list that was removed
	 */
	public CommercePriceList removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchPriceListException;

	/**
	 * Returns the number of commerce price lists where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce price lists
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

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
	 * Returns the commerce price list with the primary key or throws a <code>NoSuchPriceListException</code> if it could not be found.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price lists
	 */
	public java.util.List<CommercePriceList> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price lists.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price lists
	 * @param end the upper bound of the range of commerce price lists (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price lists
	 */
	public java.util.List<CommercePriceList> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
			orderByComparator,
		boolean useFinderCache);

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

}