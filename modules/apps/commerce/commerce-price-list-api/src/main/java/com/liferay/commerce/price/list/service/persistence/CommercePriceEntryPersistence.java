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

import com.liferay.commerce.price.list.exception.NoSuchPriceEntryException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce price entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntryUtil
 * @generated
 */
@ProviderType
public interface CommercePriceEntryPersistence
	extends BasePersistence<CommercePriceEntry>,
			CTPersistence<CommercePriceEntry> {

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
	public java.util.List<CommercePriceEntry> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the first commerce price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns the last commerce price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the last commerce price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

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
			long commercePriceEntryId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Removes all the commerce price entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce price entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price entries
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the commerce price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
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
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the first commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns the last commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the last commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

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
			long commercePriceEntryId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Removes all the commerce price entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price entries
	 */
	public int countByUuid_C(String uuid, long companyId);

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the first commerce price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns the last commerce price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the last commerce price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
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
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator,
		boolean useFinderCache);

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
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
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
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
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
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

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
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
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
	 * Returns all the commerce price entries where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByCPInstanceUuid(
		String CPInstanceUuid);

	/**
	 * Returns a range of all the commerce price entries where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price entries where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price entries where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price entry in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByCPInstanceUuid_First(
			String CPInstanceUuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the first commerce price entry in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByCPInstanceUuid_First(
		String CPInstanceUuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns the last commerce price entry in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByCPInstanceUuid_Last(
			String CPInstanceUuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the last commerce price entry in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByCPInstanceUuid_Last(
		String CPInstanceUuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	public CommercePriceEntry[] findByCPInstanceUuid_PrevAndNext(
			long commercePriceEntryId, String CPInstanceUuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Removes all the commerce price entries where CPInstanceUuid = &#63; from the database.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 */
	public void removeByCPInstanceUuid(String CPInstanceUuid);

	/**
	 * Returns the number of commerce price entries where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the number of matching commerce price entries
	 */
	public int countByCPInstanceUuid(String CPInstanceUuid);

	/**
	 * Returns all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByC_C(
		long commercePriceListId, String CPInstanceUuid);

	/**
	 * Returns a range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByC_C(
		long commercePriceListId, String CPInstanceUuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByC_C(
		long commercePriceListId, String CPInstanceUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByC_C(
		long commercePriceListId, String CPInstanceUuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByC_C_First(
			long commercePriceListId, String CPInstanceUuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the first commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByC_C_First(
		long commercePriceListId, String CPInstanceUuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns the last commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByC_C_Last(
			long commercePriceListId, String CPInstanceUuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the last commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByC_C_Last(
		long commercePriceListId, String CPInstanceUuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	public CommercePriceEntry[] findByC_C_PrevAndNext(
			long commercePriceEntryId, long commercePriceListId,
			String CPInstanceUuid,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Removes all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 */
	public void removeByC_C(long commercePriceListId, String CPInstanceUuid);

	/**
	 * Returns the number of commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the number of matching commerce price entries
	 */
	public int countByC_C(long commercePriceListId, String CPInstanceUuid);

	/**
	 * Returns all the commerce price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByLtD_S(
		Date displayDate, int status);

	/**
	 * Returns a range of all the commerce price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByLtD_S(
		Date displayDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByLtD_S_First(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the first commerce price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByLtD_S_First(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns the last commerce price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByLtD_S_Last(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the last commerce price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByLtD_S_Last(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	public CommercePriceEntry[] findByLtD_S_PrevAndNext(
			long commercePriceEntryId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Removes all the commerce price entries where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public void removeByLtD_S(Date displayDate, int status);

	/**
	 * Returns the number of commerce price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce price entries
	 */
	public int countByLtD_S(Date displayDate, int status);

	/**
	 * Returns all the commerce price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByLtE_S(
		Date expirationDate, int status);

	/**
	 * Returns a range of all the commerce price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByLtE_S_First(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the first commerce price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByLtE_S_First(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns the last commerce price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByLtE_S_Last(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the last commerce price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByLtE_S_Last(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	public CommercePriceEntry[] findByLtE_S_PrevAndNext(
			long commercePriceEntryId, Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Removes all the commerce price entries where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	public void removeByLtE_S(Date expirationDate, int status);

	/**
	 * Returns the number of commerce price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce price entries
	 */
	public int countByLtE_S(Date expirationDate, int status);

	/**
	 * Returns all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @return the matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status);

	/**
	 * Returns a range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status, int start,
		int end);

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByC_C_S_First(
			long commercePriceListId, String CPInstanceUuid, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the first commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByC_C_S_First(
		long commercePriceListId, String CPInstanceUuid, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns the last commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByC_C_S_Last(
			long commercePriceListId, String CPInstanceUuid, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the last commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByC_C_S_Last(
		long commercePriceListId, String CPInstanceUuid, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	public CommercePriceEntry[] findByC_C_S_PrevAndNext(
			long commercePriceEntryId, long commercePriceListId,
			String CPInstanceUuid, int status,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws NoSuchPriceEntryException;

	/**
	 * Removes all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 */
	public void removeByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status);

	/**
	 * Returns the number of commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @return the number of matching commerce price entries
	 */
	public int countByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status);

	/**
	 * Returns the commerce price entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchPriceEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the commerce price entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the commerce price entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	public CommercePriceEntry fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the commerce price entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce price entry that was removed
	 */
	public CommercePriceEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchPriceEntryException;

	/**
	 * Returns the number of commerce price entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce price entries
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

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
	 * Returns the commerce price entry with the primary key or throws a <code>NoSuchPriceEntryException</code> if it could not be found.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price entries
	 */
	public java.util.List<CommercePriceEntry> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
			orderByComparator,
		boolean useFinderCache);

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

}