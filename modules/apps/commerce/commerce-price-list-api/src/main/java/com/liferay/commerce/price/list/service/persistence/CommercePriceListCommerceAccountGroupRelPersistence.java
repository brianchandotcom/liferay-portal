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

import com.liferay.commerce.price.list.exception.NoSuchPriceListCommerceAccountGroupRelException;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce price list commerce account group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListCommerceAccountGroupRelUtil
 * @generated
 */
@ProviderType
public interface CommercePriceListCommerceAccountGroupRelPersistence
	extends BasePersistence<CommercePriceListCommerceAccountGroupRel>,
			CTPersistence<CommercePriceListCommerceAccountGroupRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceListCommerceAccountGroupRelUtil} to access the commerce price list commerce account group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce price list commerce account group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel> findByUuid(
		String uuid);

	/**
	 * Returns a range of all the commerce price list commerce account group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @return the range of matching commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListCommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListCommerceAccountGroupRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list commerce account group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListCommerceAccountGroupRel> orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException;

	/**
	 * Returns the first commerce price list commerce account group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListCommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns the last commerce price list commerce account group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListCommerceAccountGroupRel> orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException;

	/**
	 * Returns the last commerce price list commerce account group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListCommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns the commerce price list commerce account group rels before and after the current commerce price list commerce account group rel in the ordered set where uuid = &#63;.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key of the current commerce price list commerce account group rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a commerce price list commerce account group rel with the primary key could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel[] findByUuid_PrevAndNext(
			long commercePriceListCommerceAccountGroupRelId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListCommerceAccountGroupRel> orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException;

	/**
	 * Removes all the commerce price list commerce account group rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce price list commerce account group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list commerce account group rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the commerce price list commerce account group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel>
		findByUuid_C(String uuid, long companyId);

	/**
	 * Returns a range of all the commerce price list commerce account group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @return the range of matching commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel>
		findByUuid_C(String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel>
		findByUuid_C(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListCommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel>
		findByUuid_C(
			String uuid, long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListCommerceAccountGroupRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce price list commerce account group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListCommerceAccountGroupRel> orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException;

	/**
	 * Returns the first commerce price list commerce account group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListCommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns the last commerce price list commerce account group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListCommerceAccountGroupRel> orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException;

	/**
	 * Returns the last commerce price list commerce account group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListCommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns the commerce price list commerce account group rels before and after the current commerce price list commerce account group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key of the current commerce price list commerce account group rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a commerce price list commerce account group rel with the primary key could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel[] findByUuid_C_PrevAndNext(
			long commercePriceListCommerceAccountGroupRelId, String uuid,
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListCommerceAccountGroupRel> orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException;

	/**
	 * Removes all the commerce price list commerce account group rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce price list commerce account group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list commerce account group rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce price list commerce account group rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel>
		findByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns a range of all the commerce price list commerce account group rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @return the range of matching commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel>
		findByCommercePriceListId(long commercePriceListId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListCommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListCommerceAccountGroupRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce price list commerce account group rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel
			findByCommercePriceListId_First(
				long commercePriceListId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommercePriceListCommerceAccountGroupRel>
						orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException;

	/**
	 * Returns the first commerce price list commerce account group rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel
		fetchByCommercePriceListId_First(
			long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListCommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns the last commerce price list commerce account group rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel
			findByCommercePriceListId_Last(
				long commercePriceListId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommercePriceListCommerceAccountGroupRel>
						orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException;

	/**
	 * Returns the last commerce price list commerce account group rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel
		fetchByCommercePriceListId_Last(
			long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListCommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns the commerce price list commerce account group rels before and after the current commerce price list commerce account group rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key of the current commerce price list commerce account group rel
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a commerce price list commerce account group rel with the primary key could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel[]
			findByCommercePriceListId_PrevAndNext(
				long commercePriceListCommerceAccountGroupRelId,
				long commercePriceListId,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommercePriceListCommerceAccountGroupRel>
						orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException;

	/**
	 * Removes all the commerce price list commerce account group rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	public void removeByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns the number of commerce price list commerce account group rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list commerce account group rels
	 */
	public int countByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns the commerce price list commerce account group rel where commercePriceListId = &#63; and commerceAccountGroupId = &#63; or throws a <code>NoSuchPriceListCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel findByCAGI_CPI(
			long commercePriceListId, long commerceAccountGroupId)
		throws NoSuchPriceListCommerceAccountGroupRelException;

	/**
	 * Returns the commerce price list commerce account group rel where commercePriceListId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel fetchByCAGI_CPI(
		long commercePriceListId, long commerceAccountGroupId);

	/**
	 * Returns the commerce price list commerce account group rel where commercePriceListId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel fetchByCAGI_CPI(
		long commercePriceListId, long commerceAccountGroupId,
		boolean useFinderCache);

	/**
	 * Removes the commerce price list commerce account group rel where commercePriceListId = &#63; and commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the commerce price list commerce account group rel that was removed
	 */
	public CommercePriceListCommerceAccountGroupRel removeByCAGI_CPI(
			long commercePriceListId, long commerceAccountGroupId)
		throws NoSuchPriceListCommerceAccountGroupRelException;

	/**
	 * Returns the number of commerce price list commerce account group rels where commercePriceListId = &#63; and commerceAccountGroupId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce price list commerce account group rels
	 */
	public int countByCAGI_CPI(
		long commercePriceListId, long commerceAccountGroupId);

	/**
	 * Caches the commerce price list commerce account group rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListCommerceAccountGroupRel the commerce price list commerce account group rel
	 */
	public void cacheResult(
		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel);

	/**
	 * Caches the commerce price list commerce account group rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListCommerceAccountGroupRels the commerce price list commerce account group rels
	 */
	public void cacheResult(
		java.util.List<CommercePriceListCommerceAccountGroupRel>
			commercePriceListCommerceAccountGroupRels);

	/**
	 * Creates a new commerce price list commerce account group rel with the primary key. Does not add the commerce price list commerce account group rel to the database.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key for the new commerce price list commerce account group rel
	 * @return the new commerce price list commerce account group rel
	 */
	public CommercePriceListCommerceAccountGroupRel create(
		long commercePriceListCommerceAccountGroupRelId);

	/**
	 * Removes the commerce price list commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key of the commerce price list commerce account group rel
	 * @return the commerce price list commerce account group rel that was removed
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a commerce price list commerce account group rel with the primary key could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel remove(
			long commercePriceListCommerceAccountGroupRelId)
		throws NoSuchPriceListCommerceAccountGroupRelException;

	public CommercePriceListCommerceAccountGroupRel updateImpl(
		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel);

	/**
	 * Returns the commerce price list commerce account group rel with the primary key or throws a <code>NoSuchPriceListCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key of the commerce price list commerce account group rel
	 * @return the commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a commerce price list commerce account group rel with the primary key could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel findByPrimaryKey(
			long commercePriceListCommerceAccountGroupRelId)
		throws NoSuchPriceListCommerceAccountGroupRelException;

	/**
	 * Returns the commerce price list commerce account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key of the commerce price list commerce account group rel
	 * @return the commerce price list commerce account group rel, or <code>null</code> if a commerce price list commerce account group rel with the primary key could not be found
	 */
	public CommercePriceListCommerceAccountGroupRel fetchByPrimaryKey(
		long commercePriceListCommerceAccountGroupRelId);

	/**
	 * Returns all the commerce price list commerce account group rels.
	 *
	 * @return the commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel> findAll();

	/**
	 * Returns a range of all the commerce price list commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @return the range of commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListCommerceAccountGroupRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price list commerce account group rels
	 */
	public java.util.List<CommercePriceListCommerceAccountGroupRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListCommerceAccountGroupRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce price list commerce account group rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce price list commerce account group rels.
	 *
	 * @return the number of commerce price list commerce account group rels
	 */
	public int countAll();

}