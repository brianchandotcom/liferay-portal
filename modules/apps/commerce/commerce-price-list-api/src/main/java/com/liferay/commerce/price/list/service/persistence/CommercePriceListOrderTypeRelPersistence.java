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

import com.liferay.commerce.price.list.exception.NoSuchPriceListOrderTypeRelException;
import com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce price list order type rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListOrderTypeRelUtil
 * @generated
 */
@ProviderType
public interface CommercePriceListOrderTypeRelPersistence
	extends BasePersistence<CommercePriceListOrderTypeRel>,
			CTPersistence<CommercePriceListOrderTypeRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceListOrderTypeRelUtil} to access the commerce price list order type rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce price list order type rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel> findByUuid(
		String uuid);

	/**
	 * Returns a range of all the commerce price list order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @return the range of matching commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListOrderTypeRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListOrderTypeRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	public CommercePriceListOrderTypeRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException;

	/**
	 * Returns the first commerce price list order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public CommercePriceListOrderTypeRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListOrderTypeRel> orderByComparator);

	/**
	 * Returns the last commerce price list order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	public CommercePriceListOrderTypeRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException;

	/**
	 * Returns the last commerce price list order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public CommercePriceListOrderTypeRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListOrderTypeRel> orderByComparator);

	/**
	 * Returns the commerce price list order type rels before and after the current commerce price list order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the current commerce price list order type rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	public CommercePriceListOrderTypeRel[] findByUuid_PrevAndNext(
			long commercePriceListOrderTypeRelId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException;

	/**
	 * Removes all the commerce price list order type rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce price list order type rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list order type rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the commerce price list order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce price list order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @return the range of matching commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListOrderTypeRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListOrderTypeRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	public CommercePriceListOrderTypeRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException;

	/**
	 * Returns the first commerce price list order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public CommercePriceListOrderTypeRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListOrderTypeRel> orderByComparator);

	/**
	 * Returns the last commerce price list order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	public CommercePriceListOrderTypeRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException;

	/**
	 * Returns the last commerce price list order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public CommercePriceListOrderTypeRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListOrderTypeRel> orderByComparator);

	/**
	 * Returns the commerce price list order type rels before and after the current commerce price list order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the current commerce price list order type rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	public CommercePriceListOrderTypeRel[] findByUuid_C_PrevAndNext(
			long commercePriceListOrderTypeRelId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException;

	/**
	 * Removes all the commerce price list order type rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce price list order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list order type rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce price list order type rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel>
		findByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns a range of all the commerce price list order type rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @return the range of matching commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel>
		findByCommercePriceListId(long commercePriceListId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list order type rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListOrderTypeRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list order type rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListOrderTypeRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce price list order type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	public CommercePriceListOrderTypeRel findByCommercePriceListId_First(
			long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException;

	/**
	 * Returns the first commerce price list order type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public CommercePriceListOrderTypeRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListOrderTypeRel> orderByComparator);

	/**
	 * Returns the last commerce price list order type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	public CommercePriceListOrderTypeRel findByCommercePriceListId_Last(
			long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException;

	/**
	 * Returns the last commerce price list order type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public CommercePriceListOrderTypeRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListOrderTypeRel> orderByComparator);

	/**
	 * Returns the commerce price list order type rels before and after the current commerce price list order type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the current commerce price list order type rel
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	public CommercePriceListOrderTypeRel[]
			findByCommercePriceListId_PrevAndNext(
				long commercePriceListOrderTypeRelId, long commercePriceListId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException;

	/**
	 * Removes all the commerce price list order type rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	public void removeByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns the number of commerce price list order type rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list order type rels
	 */
	public int countByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns the commerce price list order type rel where commercePriceListId = &#63; and commerceOrderTypeId = &#63; or throws a <code>NoSuchPriceListOrderTypeRelException</code> if it could not be found.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	public CommercePriceListOrderTypeRel findByCPI_COTI(
			long commercePriceListId, long commerceOrderTypeId)
		throws NoSuchPriceListOrderTypeRelException;

	/**
	 * Returns the commerce price list order type rel where commercePriceListId = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public CommercePriceListOrderTypeRel fetchByCPI_COTI(
		long commercePriceListId, long commerceOrderTypeId);

	/**
	 * Returns the commerce price list order type rel where commercePriceListId = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	public CommercePriceListOrderTypeRel fetchByCPI_COTI(
		long commercePriceListId, long commerceOrderTypeId,
		boolean useFinderCache);

	/**
	 * Removes the commerce price list order type rel where commercePriceListId = &#63; and commerceOrderTypeId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the commerce price list order type rel that was removed
	 */
	public CommercePriceListOrderTypeRel removeByCPI_COTI(
			long commercePriceListId, long commerceOrderTypeId)
		throws NoSuchPriceListOrderTypeRelException;

	/**
	 * Returns the number of commerce price list order type rels where commercePriceListId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce price list order type rels
	 */
	public int countByCPI_COTI(
		long commercePriceListId, long commerceOrderTypeId);

	/**
	 * Caches the commerce price list order type rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListOrderTypeRel the commerce price list order type rel
	 */
	public void cacheResult(
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel);

	/**
	 * Caches the commerce price list order type rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListOrderTypeRels the commerce price list order type rels
	 */
	public void cacheResult(
		java.util.List<CommercePriceListOrderTypeRel>
			commercePriceListOrderTypeRels);

	/**
	 * Creates a new commerce price list order type rel with the primary key. Does not add the commerce price list order type rel to the database.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key for the new commerce price list order type rel
	 * @return the new commerce price list order type rel
	 */
	public CommercePriceListOrderTypeRel create(
		long commercePriceListOrderTypeRelId);

	/**
	 * Removes the commerce price list order type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the commerce price list order type rel
	 * @return the commerce price list order type rel that was removed
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	public CommercePriceListOrderTypeRel remove(
			long commercePriceListOrderTypeRelId)
		throws NoSuchPriceListOrderTypeRelException;

	public CommercePriceListOrderTypeRel updateImpl(
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel);

	/**
	 * Returns the commerce price list order type rel with the primary key or throws a <code>NoSuchPriceListOrderTypeRelException</code> if it could not be found.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the commerce price list order type rel
	 * @return the commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	public CommercePriceListOrderTypeRel findByPrimaryKey(
			long commercePriceListOrderTypeRelId)
		throws NoSuchPriceListOrderTypeRelException;

	/**
	 * Returns the commerce price list order type rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the commerce price list order type rel
	 * @return the commerce price list order type rel, or <code>null</code> if a commerce price list order type rel with the primary key could not be found
	 */
	public CommercePriceListOrderTypeRel fetchByPrimaryKey(
		long commercePriceListOrderTypeRelId);

	/**
	 * Returns all the commerce price list order type rels.
	 *
	 * @return the commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel> findAll();

	/**
	 * Returns a range of all the commerce price list order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @return the range of commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListOrderTypeRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price list order type rels
	 */
	public java.util.List<CommercePriceListOrderTypeRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListOrderTypeRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce price list order type rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce price list order type rels.
	 *
	 * @return the number of commerce price list order type rels
	 */
	public int countAll();

}