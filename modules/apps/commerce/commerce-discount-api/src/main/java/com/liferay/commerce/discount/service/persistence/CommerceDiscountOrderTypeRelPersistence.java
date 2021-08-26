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

package com.liferay.commerce.discount.service.persistence;

import com.liferay.commerce.discount.exception.NoSuchDiscountOrderTypeRelException;
import com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce discount order type rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountOrderTypeRelUtil
 * @generated
 */
@ProviderType
public interface CommerceDiscountOrderTypeRelPersistence
	extends BasePersistence<CommerceDiscountOrderTypeRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceDiscountOrderTypeRelUtil} to access the commerce discount order type rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce discount order type rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce discount order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discount order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountOrderTypeRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discount order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountOrderTypeRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce discount order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException;

	/**
	 * Returns the first commerce discount order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountOrderTypeRel> orderByComparator);

	/**
	 * Returns the last commerce discount order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException;

	/**
	 * Returns the last commerce discount order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountOrderTypeRel> orderByComparator);

	/**
	 * Returns the commerce discount order type rels before and after the current commerce discount order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the current commerce discount order type rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	public CommerceDiscountOrderTypeRel[] findByUuid_PrevAndNext(
			long commerceDiscountOrderTypeRelId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException;

	/**
	 * Removes all the commerce discount order type rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce discount order type rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce discount order type rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the commerce discount order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce discount order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discount order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountOrderTypeRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discount order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountOrderTypeRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce discount order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException;

	/**
	 * Returns the first commerce discount order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountOrderTypeRel> orderByComparator);

	/**
	 * Returns the last commerce discount order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException;

	/**
	 * Returns the last commerce discount order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountOrderTypeRel> orderByComparator);

	/**
	 * Returns the commerce discount order type rels before and after the current commerce discount order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the current commerce discount order type rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	public CommerceDiscountOrderTypeRel[] findByUuid_C_PrevAndNext(
			long commerceDiscountOrderTypeRelId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException;

	/**
	 * Removes all the commerce discount order type rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce discount order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce discount order type rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce discount order type rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel>
		findByCommerceDiscountId(long commerceDiscountId);

	/**
	 * Returns a range of all the commerce discount order type rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel>
		findByCommerceDiscountId(long commerceDiscountId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discount order type rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel>
		findByCommerceDiscountId(
			long commerceDiscountId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discount order type rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel>
		findByCommerceDiscountId(
			long commerceDiscountId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce discount order type rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel findByCommerceDiscountId_First(
			long commerceDiscountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException;

	/**
	 * Returns the first commerce discount order type rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel fetchByCommerceDiscountId_First(
		long commerceDiscountId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountOrderTypeRel> orderByComparator);

	/**
	 * Returns the last commerce discount order type rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel findByCommerceDiscountId_Last(
			long commerceDiscountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException;

	/**
	 * Returns the last commerce discount order type rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel fetchByCommerceDiscountId_Last(
		long commerceDiscountId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountOrderTypeRel> orderByComparator);

	/**
	 * Returns the commerce discount order type rels before and after the current commerce discount order type rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the current commerce discount order type rel
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	public CommerceDiscountOrderTypeRel[] findByCommerceDiscountId_PrevAndNext(
			long commerceDiscountOrderTypeRelId, long commerceDiscountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException;

	/**
	 * Removes all the commerce discount order type rels where commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 */
	public void removeByCommerceDiscountId(long commerceDiscountId);

	/**
	 * Returns the number of commerce discount order type rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount order type rels
	 */
	public int countByCommerceDiscountId(long commerceDiscountId);

	/**
	 * Returns all the commerce discount order type rels where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel>
		findByCommerceOrderTypeId(long commerceOrderTypeId);

	/**
	 * Returns a range of all the commerce discount order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel>
		findByCommerceOrderTypeId(long commerceOrderTypeId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discount order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel>
		findByCommerceOrderTypeId(
			long commerceOrderTypeId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discount order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel>
		findByCommerceOrderTypeId(
			long commerceOrderTypeId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator,
			boolean useFinderCache);

	/**
	 * Returns the first commerce discount order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel findByCommerceOrderTypeId_First(
			long commerceOrderTypeId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException;

	/**
	 * Returns the first commerce discount order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel fetchByCommerceOrderTypeId_First(
		long commerceOrderTypeId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountOrderTypeRel> orderByComparator);

	/**
	 * Returns the last commerce discount order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel findByCommerceOrderTypeId_Last(
			long commerceOrderTypeId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException;

	/**
	 * Returns the last commerce discount order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel fetchByCommerceOrderTypeId_Last(
		long commerceOrderTypeId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountOrderTypeRel> orderByComparator);

	/**
	 * Returns the commerce discount order type rels before and after the current commerce discount order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the current commerce discount order type rel
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	public CommerceDiscountOrderTypeRel[] findByCommerceOrderTypeId_PrevAndNext(
			long commerceDiscountOrderTypeRelId, long commerceOrderTypeId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException;

	/**
	 * Removes all the commerce discount order type rels where commerceOrderTypeId = &#63; from the database.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 */
	public void removeByCommerceOrderTypeId(long commerceOrderTypeId);

	/**
	 * Returns the number of commerce discount order type rels where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce discount order type rels
	 */
	public int countByCommerceOrderTypeId(long commerceOrderTypeId);

	/**
	 * Returns the commerce discount order type rel where commerceDiscountId = &#63; and commerceOrderTypeId = &#63; or throws a <code>NoSuchDiscountOrderTypeRelException</code> if it could not be found.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel findByCDI_COTI(
			long commerceDiscountId, long commerceOrderTypeId)
		throws NoSuchDiscountOrderTypeRelException;

	/**
	 * Returns the commerce discount order type rel where commerceDiscountId = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel fetchByCDI_COTI(
		long commerceDiscountId, long commerceOrderTypeId);

	/**
	 * Returns the commerce discount order type rel where commerceDiscountId = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	public CommerceDiscountOrderTypeRel fetchByCDI_COTI(
		long commerceDiscountId, long commerceOrderTypeId,
		boolean useFinderCache);

	/**
	 * Removes the commerce discount order type rel where commerceDiscountId = &#63; and commerceOrderTypeId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the commerce discount order type rel that was removed
	 */
	public CommerceDiscountOrderTypeRel removeByCDI_COTI(
			long commerceDiscountId, long commerceOrderTypeId)
		throws NoSuchDiscountOrderTypeRelException;

	/**
	 * Returns the number of commerce discount order type rels where commerceDiscountId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce discount order type rels
	 */
	public int countByCDI_COTI(
		long commerceDiscountId, long commerceOrderTypeId);

	/**
	 * Caches the commerce discount order type rel in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountOrderTypeRel the commerce discount order type rel
	 */
	public void cacheResult(
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel);

	/**
	 * Caches the commerce discount order type rels in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountOrderTypeRels the commerce discount order type rels
	 */
	public void cacheResult(
		java.util.List<CommerceDiscountOrderTypeRel>
			commerceDiscountOrderTypeRels);

	/**
	 * Creates a new commerce discount order type rel with the primary key. Does not add the commerce discount order type rel to the database.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key for the new commerce discount order type rel
	 * @return the new commerce discount order type rel
	 */
	public CommerceDiscountOrderTypeRel create(
		long commerceDiscountOrderTypeRelId);

	/**
	 * Removes the commerce discount order type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the commerce discount order type rel
	 * @return the commerce discount order type rel that was removed
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	public CommerceDiscountOrderTypeRel remove(
			long commerceDiscountOrderTypeRelId)
		throws NoSuchDiscountOrderTypeRelException;

	public CommerceDiscountOrderTypeRel updateImpl(
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel);

	/**
	 * Returns the commerce discount order type rel with the primary key or throws a <code>NoSuchDiscountOrderTypeRelException</code> if it could not be found.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the commerce discount order type rel
	 * @return the commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	public CommerceDiscountOrderTypeRel findByPrimaryKey(
			long commerceDiscountOrderTypeRelId)
		throws NoSuchDiscountOrderTypeRelException;

	/**
	 * Returns the commerce discount order type rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the commerce discount order type rel
	 * @return the commerce discount order type rel, or <code>null</code> if a commerce discount order type rel with the primary key could not be found
	 */
	public CommerceDiscountOrderTypeRel fetchByPrimaryKey(
		long commerceDiscountOrderTypeRelId);

	/**
	 * Returns all the commerce discount order type rels.
	 *
	 * @return the commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel> findAll();

	/**
	 * Returns a range of all the commerce discount order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce discount order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountOrderTypeRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discount order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce discount order type rels
	 */
	public java.util.List<CommerceDiscountOrderTypeRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountOrderTypeRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce discount order type rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce discount order type rels.
	 *
	 * @return the number of commerce discount order type rels
	 */
	public int countAll();

}