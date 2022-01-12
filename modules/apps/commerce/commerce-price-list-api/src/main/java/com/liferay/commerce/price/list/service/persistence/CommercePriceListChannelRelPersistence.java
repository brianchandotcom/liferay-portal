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

import com.liferay.commerce.price.list.exception.NoSuchPriceListChannelRelException;
import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce price list channel rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListChannelRelUtil
 * @generated
 */
@ProviderType
public interface CommercePriceListChannelRelPersistence
	extends BasePersistence<CommercePriceListChannelRel>,
			CTPersistence<CommercePriceListChannelRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceListChannelRelUtil} to access the commerce price list channel rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce price list channel rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce price list channel rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @return the range of matching commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list channel rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListChannelRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list channel rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListChannelRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list channel rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	public CommercePriceListChannelRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException;

	/**
	 * Returns the first commerce price list channel rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public CommercePriceListChannelRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListChannelRel> orderByComparator);

	/**
	 * Returns the last commerce price list channel rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	public CommercePriceListChannelRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException;

	/**
	 * Returns the last commerce price list channel rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public CommercePriceListChannelRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListChannelRel> orderByComparator);

	/**
	 * Returns the commerce price list channel rels before and after the current commerce price list channel rel in the ordered set where uuid = &#63;.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the current commerce price list channel rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	public CommercePriceListChannelRel[] findByUuid_PrevAndNext(
			long CommercePriceListChannelRelId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException;

	/**
	 * Removes all the commerce price list channel rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce price list channel rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list channel rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the commerce price list channel rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce price list channel rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @return the range of matching commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list channel rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListChannelRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list channel rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListChannelRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price list channel rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	public CommercePriceListChannelRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException;

	/**
	 * Returns the first commerce price list channel rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public CommercePriceListChannelRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListChannelRel> orderByComparator);

	/**
	 * Returns the last commerce price list channel rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	public CommercePriceListChannelRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException;

	/**
	 * Returns the last commerce price list channel rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public CommercePriceListChannelRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListChannelRel> orderByComparator);

	/**
	 * Returns the commerce price list channel rels before and after the current commerce price list channel rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the current commerce price list channel rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	public CommercePriceListChannelRel[] findByUuid_C_PrevAndNext(
			long CommercePriceListChannelRelId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException;

	/**
	 * Removes all the commerce price list channel rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce price list channel rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list channel rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce price list channel rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel>
		findByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns a range of all the commerce price list channel rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @return the range of matching commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel>
		findByCommercePriceListId(long commercePriceListId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list channel rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListChannelRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list channel rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListChannelRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce price list channel rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	public CommercePriceListChannelRel findByCommercePriceListId_First(
			long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException;

	/**
	 * Returns the first commerce price list channel rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public CommercePriceListChannelRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListChannelRel> orderByComparator);

	/**
	 * Returns the last commerce price list channel rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	public CommercePriceListChannelRel findByCommercePriceListId_Last(
			long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException;

	/**
	 * Returns the last commerce price list channel rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public CommercePriceListChannelRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListChannelRel> orderByComparator);

	/**
	 * Returns the commerce price list channel rels before and after the current commerce price list channel rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the current commerce price list channel rel
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	public CommercePriceListChannelRel[] findByCommercePriceListId_PrevAndNext(
			long CommercePriceListChannelRelId, long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException;

	/**
	 * Removes all the commerce price list channel rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	public void removeByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns the number of commerce price list channel rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list channel rels
	 */
	public int countByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns the commerce price list channel rel where commerceChannelId = &#63; and commercePriceListId = &#63; or throws a <code>NoSuchPriceListChannelRelException</code> if it could not be found.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	public CommercePriceListChannelRel findByCCI_CPI(
			long commerceChannelId, long commercePriceListId)
		throws NoSuchPriceListChannelRelException;

	/**
	 * Returns the commerce price list channel rel where commerceChannelId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public CommercePriceListChannelRel fetchByCCI_CPI(
		long commerceChannelId, long commercePriceListId);

	/**
	 * Returns the commerce price list channel rel where commerceChannelId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param commercePriceListId the commerce price list ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	public CommercePriceListChannelRel fetchByCCI_CPI(
		long commerceChannelId, long commercePriceListId,
		boolean useFinderCache);

	/**
	 * Removes the commerce price list channel rel where commerceChannelId = &#63; and commercePriceListId = &#63; from the database.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the commerce price list channel rel that was removed
	 */
	public CommercePriceListChannelRel removeByCCI_CPI(
			long commerceChannelId, long commercePriceListId)
		throws NoSuchPriceListChannelRelException;

	/**
	 * Returns the number of commerce price list channel rels where commerceChannelId = &#63; and commercePriceListId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list channel rels
	 */
	public int countByCCI_CPI(long commerceChannelId, long commercePriceListId);

	/**
	 * Caches the commerce price list channel rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListChannelRel the commerce price list channel rel
	 */
	public void cacheResult(
		CommercePriceListChannelRel commercePriceListChannelRel);

	/**
	 * Caches the commerce price list channel rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListChannelRels the commerce price list channel rels
	 */
	public void cacheResult(
		java.util.List<CommercePriceListChannelRel>
			commercePriceListChannelRels);

	/**
	 * Creates a new commerce price list channel rel with the primary key. Does not add the commerce price list channel rel to the database.
	 *
	 * @param CommercePriceListChannelRelId the primary key for the new commerce price list channel rel
	 * @return the new commerce price list channel rel
	 */
	public CommercePriceListChannelRel create(
		long CommercePriceListChannelRelId);

	/**
	 * Removes the commerce price list channel rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the commerce price list channel rel
	 * @return the commerce price list channel rel that was removed
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	public CommercePriceListChannelRel remove(
			long CommercePriceListChannelRelId)
		throws NoSuchPriceListChannelRelException;

	public CommercePriceListChannelRel updateImpl(
		CommercePriceListChannelRel commercePriceListChannelRel);

	/**
	 * Returns the commerce price list channel rel with the primary key or throws a <code>NoSuchPriceListChannelRelException</code> if it could not be found.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the commerce price list channel rel
	 * @return the commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	public CommercePriceListChannelRel findByPrimaryKey(
			long CommercePriceListChannelRelId)
		throws NoSuchPriceListChannelRelException;

	/**
	 * Returns the commerce price list channel rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the commerce price list channel rel
	 * @return the commerce price list channel rel, or <code>null</code> if a commerce price list channel rel with the primary key could not be found
	 */
	public CommercePriceListChannelRel fetchByPrimaryKey(
		long CommercePriceListChannelRelId);

	/**
	 * Returns all the commerce price list channel rels.
	 *
	 * @return the commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel> findAll();

	/**
	 * Returns a range of all the commerce price list channel rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @return the range of commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce price list channel rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListChannelRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price list channel rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price list channel rels
	 */
	public java.util.List<CommercePriceListChannelRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommercePriceListChannelRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce price list channel rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce price list channel rels.
	 *
	 * @return the number of commerce price list channel rels
	 */
	public int countAll();

}