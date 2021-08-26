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

import com.liferay.commerce.discount.exception.NoSuchDiscountAccountRelException;
import com.liferay.commerce.discount.model.CommerceDiscountAccountRel;
import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce discount account rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountAccountRelUtil
 * @generated
 */
@ProviderType
public interface CommerceDiscountAccountRelPersistence
	extends BasePersistence<CommerceDiscountAccountRel> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceDiscountAccountRelUtil} to access the commerce discount account rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce discount account rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce discount account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discount account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discount account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce discount account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException;

	/**
	 * Returns the first commerce discount account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator);

	/**
	 * Returns the last commerce discount account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException;

	/**
	 * Returns the last commerce discount account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator);

	/**
	 * Returns the commerce discount account rels before and after the current commerce discount account rel in the ordered set where uuid = &#63;.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the current commerce discount account rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	public CommerceDiscountAccountRel[] findByUuid_PrevAndNext(
			long commerceDiscountAccountRelId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException;

	/**
	 * Removes all the commerce discount account rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce discount account rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce discount account rels
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns all the commerce discount account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce discount account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discount account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discount account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce discount account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException;

	/**
	 * Returns the first commerce discount account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator);

	/**
	 * Returns the last commerce discount account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException;

	/**
	 * Returns the last commerce discount account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator);

	/**
	 * Returns the commerce discount account rels before and after the current commerce discount account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the current commerce discount account rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	public CommerceDiscountAccountRel[] findByUuid_C_PrevAndNext(
			long commerceDiscountAccountRelId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException;

	/**
	 * Removes all the commerce discount account rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce discount account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce discount account rels
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce discount account rels where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @return the matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByCommerceAccountId(
		long commerceAccountId);

	/**
	 * Returns a range of all the commerce discount account rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByCommerceAccountId(
		long commerceAccountId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discount account rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByCommerceAccountId(
		long commerceAccountId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discount account rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByCommerceAccountId(
		long commerceAccountId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce discount account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel findByCommerceAccountId_First(
			long commerceAccountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException;

	/**
	 * Returns the first commerce discount account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel fetchByCommerceAccountId_First(
		long commerceAccountId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator);

	/**
	 * Returns the last commerce discount account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel findByCommerceAccountId_Last(
			long commerceAccountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException;

	/**
	 * Returns the last commerce discount account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel fetchByCommerceAccountId_Last(
		long commerceAccountId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator);

	/**
	 * Returns the commerce discount account rels before and after the current commerce discount account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the current commerce discount account rel
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	public CommerceDiscountAccountRel[] findByCommerceAccountId_PrevAndNext(
			long commerceDiscountAccountRelId, long commerceAccountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException;

	/**
	 * Removes all the commerce discount account rels where commerceAccountId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 */
	public void removeByCommerceAccountId(long commerceAccountId);

	/**
	 * Returns the number of commerce discount account rels where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @return the number of matching commerce discount account rels
	 */
	public int countByCommerceAccountId(long commerceAccountId);

	/**
	 * Returns all the commerce discount account rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByCommerceDiscountId(
		long commerceDiscountId);

	/**
	 * Returns a range of all the commerce discount account rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce discount account rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discount account rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce discount account rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel findByCommerceDiscountId_First(
			long commerceDiscountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException;

	/**
	 * Returns the first commerce discount account rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel fetchByCommerceDiscountId_First(
		long commerceDiscountId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator);

	/**
	 * Returns the last commerce discount account rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel findByCommerceDiscountId_Last(
			long commerceDiscountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException;

	/**
	 * Returns the last commerce discount account rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel fetchByCommerceDiscountId_Last(
		long commerceDiscountId,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator);

	/**
	 * Returns the commerce discount account rels before and after the current commerce discount account rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the current commerce discount account rel
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	public CommerceDiscountAccountRel[] findByCommerceDiscountId_PrevAndNext(
			long commerceDiscountAccountRelId, long commerceDiscountId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException;

	/**
	 * Removes all the commerce discount account rels where commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 */
	public void removeByCommerceDiscountId(long commerceDiscountId);

	/**
	 * Returns the number of commerce discount account rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount account rels
	 */
	public int countByCommerceDiscountId(long commerceDiscountId);

	/**
	 * Returns the commerce discount account rel where commerceAccountId = &#63; and commerceDiscountId = &#63; or throws a <code>NoSuchDiscountAccountRelException</code> if it could not be found.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel findByCAI_CDI(
			long commerceAccountId, long commerceDiscountId)
		throws NoSuchDiscountAccountRelException;

	/**
	 * Returns the commerce discount account rel where commerceAccountId = &#63; and commerceDiscountId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel fetchByCAI_CDI(
		long commerceAccountId, long commerceDiscountId);

	/**
	 * Returns the commerce discount account rel where commerceAccountId = &#63; and commerceDiscountId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	public CommerceDiscountAccountRel fetchByCAI_CDI(
		long commerceAccountId, long commerceDiscountId,
		boolean useFinderCache);

	/**
	 * Removes the commerce discount account rel where commerceAccountId = &#63; and commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the commerce discount account rel that was removed
	 */
	public CommerceDiscountAccountRel removeByCAI_CDI(
			long commerceAccountId, long commerceDiscountId)
		throws NoSuchDiscountAccountRelException;

	/**
	 * Returns the number of commerce discount account rels where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount account rels
	 */
	public int countByCAI_CDI(long commerceAccountId, long commerceDiscountId);

	/**
	 * Caches the commerce discount account rel in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountAccountRel the commerce discount account rel
	 */
	public void cacheResult(
		CommerceDiscountAccountRel commerceDiscountAccountRel);

	/**
	 * Caches the commerce discount account rels in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountAccountRels the commerce discount account rels
	 */
	public void cacheResult(
		java.util.List<CommerceDiscountAccountRel> commerceDiscountAccountRels);

	/**
	 * Creates a new commerce discount account rel with the primary key. Does not add the commerce discount account rel to the database.
	 *
	 * @param commerceDiscountAccountRelId the primary key for the new commerce discount account rel
	 * @return the new commerce discount account rel
	 */
	public CommerceDiscountAccountRel create(long commerceDiscountAccountRelId);

	/**
	 * Removes the commerce discount account rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the commerce discount account rel
	 * @return the commerce discount account rel that was removed
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	public CommerceDiscountAccountRel remove(long commerceDiscountAccountRelId)
		throws NoSuchDiscountAccountRelException;

	public CommerceDiscountAccountRel updateImpl(
		CommerceDiscountAccountRel commerceDiscountAccountRel);

	/**
	 * Returns the commerce discount account rel with the primary key or throws a <code>NoSuchDiscountAccountRelException</code> if it could not be found.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the commerce discount account rel
	 * @return the commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	public CommerceDiscountAccountRel findByPrimaryKey(
			long commerceDiscountAccountRelId)
		throws NoSuchDiscountAccountRelException;

	/**
	 * Returns the commerce discount account rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the commerce discount account rel
	 * @return the commerce discount account rel, or <code>null</code> if a commerce discount account rel with the primary key could not be found
	 */
	public CommerceDiscountAccountRel fetchByPrimaryKey(
		long commerceDiscountAccountRelId);

	/**
	 * Returns all the commerce discount account rels.
	 *
	 * @return the commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findAll();

	/**
	 * Returns a range of all the commerce discount account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findAll(
		int start, int end);

	/**
	 * Returns an ordered range of all the commerce discount account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator);

	/**
	 * Returns an ordered range of all the commerce discount account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce discount account rels
	 */
	public java.util.List<CommerceDiscountAccountRel> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<CommerceDiscountAccountRel> orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce discount account rels from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce discount account rels.
	 *
	 * @return the number of commerce discount account rels
	 */
	public int countAll();

}