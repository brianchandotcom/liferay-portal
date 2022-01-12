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

import com.liferay.commerce.price.list.exception.NoSuchPriceListAccountRelException;
import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce price list account rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListAccountRelUtil
 * @generated
 */
@ProviderType
public interface CommercePriceListAccountRelPersistence
	extends BasePersistence<CommercePriceListAccountRel>,
			CTPersistence<CommercePriceListAccountRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceListAccountRelUtil} to access the commerce price list account rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce price list account rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce price list account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @return the range of matching commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListAccountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListAccountRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	public CommercePriceListAccountRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException;

	/**
	 * Returns the first commerce price list account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public CommercePriceListAccountRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListAccountRel> orderByComparator);

	/**
	 * Returns the last commerce price list account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	public CommercePriceListAccountRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException;

	/**
	 * Returns the last commerce price list account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public CommercePriceListAccountRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListAccountRel> orderByComparator);

	/**
	 * Returns the commerce price list account rels before and after the current commerce price list account rel in the ordered set where uuid = &#63;.
	 *
	 * @param commercePriceListAccountRelId the primary key of the current commerce price list account rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	public CommercePriceListAccountRel[] findByUuid_PrevAndNext(
			long commercePriceListAccountRelId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException;

	/**
	 * Removes all the commerce price list account rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce price list account rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list account rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the commerce price list account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce price list account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @return the range of matching commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListAccountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListAccountRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	public CommercePriceListAccountRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException;

	/**
	 * Returns the first commerce price list account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public CommercePriceListAccountRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListAccountRel> orderByComparator);

	/**
	 * Returns the last commerce price list account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	public CommercePriceListAccountRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException;

	/**
	 * Returns the last commerce price list account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public CommercePriceListAccountRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListAccountRel> orderByComparator);

	/**
	 * Returns the commerce price list account rels before and after the current commerce price list account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceListAccountRelId the primary key of the current commerce price list account rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	public CommercePriceListAccountRel[] findByUuid_C_PrevAndNext(
			long commercePriceListAccountRelId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException;

	/**
	 * Removes all the commerce price list account rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce price list account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list account rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce price list account rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel>
		findByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns a range of all the commerce price list account rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @return the range of matching commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel>
		findByCommercePriceListId(long commercePriceListId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list account rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListAccountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list account rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListAccountRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce price list account rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	public CommercePriceListAccountRel findByCommercePriceListId_First(
			long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException;

	/**
	 * Returns the first commerce price list account rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public CommercePriceListAccountRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListAccountRel> orderByComparator);

	/**
	 * Returns the last commerce price list account rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	public CommercePriceListAccountRel findByCommercePriceListId_Last(
			long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException;

	/**
	 * Returns the last commerce price list account rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public CommercePriceListAccountRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListAccountRel> orderByComparator);

	/**
	 * Returns the commerce price list account rels before and after the current commerce price list account rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListAccountRelId the primary key of the current commerce price list account rel
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	public CommercePriceListAccountRel[] findByCommercePriceListId_PrevAndNext(
			long commercePriceListAccountRelId, long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException;

	/**
	 * Removes all the commerce price list account rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	public void removeByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns the number of commerce price list account rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list account rels
	 */
	public int countByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns the commerce price list account rel where commerceAccountId = &#63; and commercePriceListId = &#63; or throws a <code>NoSuchPriceListAccountRelException</code> if it could not be found.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	public CommercePriceListAccountRel findByCAI_CPI(
			long commerceAccountId, long commercePriceListId)
		throws NoSuchPriceListAccountRelException;

	/**
	 * Returns the commerce price list account rel where commerceAccountId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public CommercePriceListAccountRel fetchByCAI_CPI(
		long commerceAccountId, long commercePriceListId);

	/**
	 * Returns the commerce price list account rel where commerceAccountId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commercePriceListId the commerce price list ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	public CommercePriceListAccountRel fetchByCAI_CPI(
		long commerceAccountId, long commercePriceListId,
		boolean useFinderCache);

	/**
	 * Removes the commerce price list account rel where commerceAccountId = &#63; and commercePriceListId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the commerce price list account rel that was removed
	 */
	public CommercePriceListAccountRel removeByCAI_CPI(
			long commerceAccountId, long commercePriceListId)
		throws NoSuchPriceListAccountRelException;

	/**
	 * Returns the number of commerce price list account rels where commerceAccountId = &#63; and commercePriceListId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list account rels
	 */
	public int countByCAI_CPI(long commerceAccountId, long commercePriceListId);

	/**
	 * Caches the commerce price list account rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListAccountRel the commerce price list account rel
	 */
	public void cacheResult(
		CommercePriceListAccountRel commercePriceListAccountRel);

	/**
	 * Caches the commerce price list account rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListAccountRels the commerce price list account rels
	 */
	public void cacheResult(
		java.util.List<CommercePriceListAccountRel>
			commercePriceListAccountRels);

	/**
	 * Creates a new commerce price list account rel with the primary key. Does not add the commerce price list account rel to the database.
	 *
	 * @param commercePriceListAccountRelId the primary key for the new commerce price list account rel
	 * @return the new commerce price list account rel
	 */
	public CommercePriceListAccountRel create(
		long commercePriceListAccountRelId);

	/**
	 * Removes the commerce price list account rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceListAccountRelId the primary key of the commerce price list account rel
	 * @return the commerce price list account rel that was removed
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	public CommercePriceListAccountRel remove(
			long commercePriceListAccountRelId)
		throws NoSuchPriceListAccountRelException;

	public CommercePriceListAccountRel updateImpl(
		CommercePriceListAccountRel commercePriceListAccountRel);

	/**
	 * Returns the commerce price list account rel with the primary key or throws a <code>NoSuchPriceListAccountRelException</code> if it could not be found.
	 *
	 * @param commercePriceListAccountRelId the primary key of the commerce price list account rel
	 * @return the commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	public CommercePriceListAccountRel findByPrimaryKey(
			long commercePriceListAccountRelId)
		throws NoSuchPriceListAccountRelException;

	/**
	 * Returns the commerce price list account rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceListAccountRelId the primary key of the commerce price list account rel
	 * @return the commerce price list account rel, or <code>null</code> if a commerce price list account rel with the primary key could not be found
	 */
	public CommercePriceListAccountRel fetchByPrimaryKey(
		long commercePriceListAccountRelId);

	/**
	 * Returns all the commerce price list account rels.
	 *
	 * @return the commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel> findAll();

	/**
	 * Returns a range of all the commerce price list account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @return the range of commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListAccountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price list account rels
	 */
	public java.util.List<CommercePriceListAccountRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListAccountRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce price list account rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce price list account rels.
	 *
	 * @return the number of commerce price list account rels
	 */
	public int countAll();

}