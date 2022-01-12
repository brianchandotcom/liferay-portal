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

import com.liferay.commerce.price.list.exception.NoSuchPriceListDiscountRelException;
import com.liferay.commerce.price.list.model.CommercePriceListDiscountRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce price list discount rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListDiscountRelUtil
 * @generated
 */
@ProviderType
public interface CommercePriceListDiscountRelPersistence
	extends BasePersistence<CommercePriceListDiscountRel>,
			CTPersistence<CommercePriceListDiscountRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceListDiscountRelUtil} to access the commerce price list discount rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce price list discount rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce price list discount rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @return the range of matching commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list discount rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListDiscountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list discount rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListDiscountRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list discount rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a matching commerce price list discount rel could not be found
	 */
	public CommercePriceListDiscountRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException;

	/**
	 * Returns the first commerce price list discount rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	public CommercePriceListDiscountRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListDiscountRel> orderByComparator);

	/**
	 * Returns the last commerce price list discount rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a matching commerce price list discount rel could not be found
	 */
	public CommercePriceListDiscountRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException;

	/**
	 * Returns the last commerce price list discount rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	public CommercePriceListDiscountRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListDiscountRel> orderByComparator);

	/**
	 * Returns the commerce price list discount rels before and after the current commerce price list discount rel in the ordered set where uuid = &#63;.
	 *
	 * @param commercePriceListDiscountRelId the primary key of the current commerce price list discount rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a commerce price list discount rel with the primary key could not be found
	 */
	public CommercePriceListDiscountRel[] findByUuid_PrevAndNext(
			long commercePriceListDiscountRelId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException;

	/**
	 * Removes all the commerce price list discount rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce price list discount rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list discount rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the commerce price list discount rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce price list discount rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @return the range of matching commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list discount rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListDiscountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list discount rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListDiscountRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list discount rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a matching commerce price list discount rel could not be found
	 */
	public CommercePriceListDiscountRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException;

	/**
	 * Returns the first commerce price list discount rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	public CommercePriceListDiscountRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListDiscountRel> orderByComparator);

	/**
	 * Returns the last commerce price list discount rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a matching commerce price list discount rel could not be found
	 */
	public CommercePriceListDiscountRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException;

	/**
	 * Returns the last commerce price list discount rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	public CommercePriceListDiscountRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListDiscountRel> orderByComparator);

	/**
	 * Returns the commerce price list discount rels before and after the current commerce price list discount rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceListDiscountRelId the primary key of the current commerce price list discount rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a commerce price list discount rel with the primary key could not be found
	 */
	public CommercePriceListDiscountRel[] findByUuid_C_PrevAndNext(
			long commercePriceListDiscountRelId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException;

	/**
	 * Removes all the commerce price list discount rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce price list discount rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list discount rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce price list discount rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel>
		findByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns a range of all the commerce price list discount rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @return the range of matching commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel>
		findByCommercePriceListId(long commercePriceListId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list discount rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListDiscountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list discount rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListDiscountRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce price list discount rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a matching commerce price list discount rel could not be found
	 */
	public CommercePriceListDiscountRel findByCommercePriceListId_First(
			long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException;

	/**
	 * Returns the first commerce price list discount rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	public CommercePriceListDiscountRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListDiscountRel> orderByComparator);

	/**
	 * Returns the last commerce price list discount rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a matching commerce price list discount rel could not be found
	 */
	public CommercePriceListDiscountRel findByCommercePriceListId_Last(
			long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException;

	/**
	 * Returns the last commerce price list discount rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	public CommercePriceListDiscountRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListDiscountRel> orderByComparator);

	/**
	 * Returns the commerce price list discount rels before and after the current commerce price list discount rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListDiscountRelId the primary key of the current commerce price list discount rel
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a commerce price list discount rel with the primary key could not be found
	 */
	public CommercePriceListDiscountRel[] findByCommercePriceListId_PrevAndNext(
			long commercePriceListDiscountRelId, long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListDiscountRel> orderByComparator)
		throws NoSuchPriceListDiscountRelException;

	/**
	 * Removes all the commerce price list discount rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	public void removeByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns the number of commerce price list discount rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list discount rels
	 */
	public int countByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns the commerce price list discount rel where commerceDiscountId = &#63; and commercePriceListId = &#63; or throws a <code>NoSuchPriceListDiscountRelException</code> if it could not be found.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a matching commerce price list discount rel could not be found
	 */
	public CommercePriceListDiscountRel findByCDI_CPI(
			long commerceDiscountId, long commercePriceListId)
		throws NoSuchPriceListDiscountRelException;

	/**
	 * Returns the commerce price list discount rel where commerceDiscountId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	public CommercePriceListDiscountRel fetchByCDI_CPI(
		long commerceDiscountId, long commercePriceListId);

	/**
	 * Returns the commerce price list discount rel where commerceDiscountId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commercePriceListId the commerce price list ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list discount rel, or <code>null</code> if a matching commerce price list discount rel could not be found
	 */
	public CommercePriceListDiscountRel fetchByCDI_CPI(
		long commerceDiscountId, long commercePriceListId,
		boolean useFinderCache);

	/**
	 * Removes the commerce price list discount rel where commerceDiscountId = &#63; and commercePriceListId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the commerce price list discount rel that was removed
	 */
	public CommercePriceListDiscountRel removeByCDI_CPI(
			long commerceDiscountId, long commercePriceListId)
		throws NoSuchPriceListDiscountRelException;

	/**
	 * Returns the number of commerce price list discount rels where commerceDiscountId = &#63; and commercePriceListId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list discount rels
	 */
	public int countByCDI_CPI(
		long commerceDiscountId, long commercePriceListId);

	/**
	 * Caches the commerce price list discount rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListDiscountRel the commerce price list discount rel
	 */
	public void cacheResult(
		CommercePriceListDiscountRel commercePriceListDiscountRel);

	/**
	 * Caches the commerce price list discount rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListDiscountRels the commerce price list discount rels
	 */
	public void cacheResult(
		java.util.List<CommercePriceListDiscountRel>
			commercePriceListDiscountRels);

	/**
	 * Creates a new commerce price list discount rel with the primary key. Does not add the commerce price list discount rel to the database.
	 *
	 * @param commercePriceListDiscountRelId the primary key for the new commerce price list discount rel
	 * @return the new commerce price list discount rel
	 */
	public CommercePriceListDiscountRel create(
		long commercePriceListDiscountRelId);

	/**
	 * Removes the commerce price list discount rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceListDiscountRelId the primary key of the commerce price list discount rel
	 * @return the commerce price list discount rel that was removed
	 * @throws NoSuchPriceListDiscountRelException if a commerce price list discount rel with the primary key could not be found
	 */
	public CommercePriceListDiscountRel remove(
			long commercePriceListDiscountRelId)
		throws NoSuchPriceListDiscountRelException;

	public CommercePriceListDiscountRel updateImpl(
		CommercePriceListDiscountRel commercePriceListDiscountRel);

	/**
	 * Returns the commerce price list discount rel with the primary key or throws a <code>NoSuchPriceListDiscountRelException</code> if it could not be found.
	 *
	 * @param commercePriceListDiscountRelId the primary key of the commerce price list discount rel
	 * @return the commerce price list discount rel
	 * @throws NoSuchPriceListDiscountRelException if a commerce price list discount rel with the primary key could not be found
	 */
	public CommercePriceListDiscountRel findByPrimaryKey(
			long commercePriceListDiscountRelId)
		throws NoSuchPriceListDiscountRelException;

	/**
	 * Returns the commerce price list discount rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceListDiscountRelId the primary key of the commerce price list discount rel
	 * @return the commerce price list discount rel, or <code>null</code> if a commerce price list discount rel with the primary key could not be found
	 */
	public CommercePriceListDiscountRel fetchByPrimaryKey(
		long commercePriceListDiscountRelId);

	/**
	 * Returns all the commerce price list discount rels.
	 *
	 * @return the commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel> findAll();

	/**
	 * Returns a range of all the commerce price list discount rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @return the range of commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list discount rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListDiscountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list discount rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListDiscountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list discount rels
	 * @param end the upper bound of the range of commerce price list discount rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price list discount rels
	 */
	public java.util.List<CommercePriceListDiscountRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListDiscountRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce price list discount rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce price list discount rels.
	 *
	 * @return the number of commerce price list discount rels
	 */
	public int countAll();

}