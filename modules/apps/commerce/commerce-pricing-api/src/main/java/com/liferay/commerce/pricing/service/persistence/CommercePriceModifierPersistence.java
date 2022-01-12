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

package com.liferay.commerce.pricing.service.persistence;

import com.liferay.commerce.pricing.exception.NoSuchPriceModifierException;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the commerce price modifier service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Riccardo Alberti
 * @see CommercePriceModifierUtil
 * @generated
 */
@ProviderType
public interface CommercePriceModifierPersistence
	extends BasePersistence<CommercePriceModifier>,
			CTPersistence<CommercePriceModifier> {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceModifierUtil} to access the commerce price modifier persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns all the commerce price modifiers where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByUuid(String uuid);

	/**
	 * Returns a range of all the commerce price modifiers where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @return the range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByUuid(
		String uuid, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifiers where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifiers where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByUuid(
		String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price modifier in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByUuid_First(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the first commerce price modifier in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByUuid_First(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the last commerce price modifier in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByUuid_Last(
			String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the last commerce price modifier in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByUuid_Last(
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the commerce price modifiers before and after the current commerce price modifier in the ordered set where uuid = &#63;.
	 *
	 * @param commercePriceModifierId the primary key of the current commerce price modifier
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier
	 * @throws NoSuchPriceModifierException if a commerce price modifier with the primary key could not be found
	 */
	public CommercePriceModifier[] findByUuid_PrevAndNext(
			long commercePriceModifierId, String uuid,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Removes all the commerce price modifiers where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	public void removeByUuid(String uuid);

	/**
	 * Returns the number of commerce price modifiers where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price modifiers
	 */
	public int countByUuid(String uuid);

	/**
	 * Returns the commerce price modifier where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchPriceModifierException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByUUID_G(String uuid, long groupId)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the commerce price modifier where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByUUID_G(String uuid, long groupId);

	/**
	 * Returns the commerce price modifier where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache);

	/**
	 * Removes the commerce price modifier where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce price modifier that was removed
	 */
	public CommercePriceModifier removeByUUID_G(String uuid, long groupId)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the number of commerce price modifiers where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce price modifiers
	 */
	public int countByUUID_G(String uuid, long groupId);

	/**
	 * Returns all the commerce price modifiers where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByUuid_C(
		String uuid, long companyId);

	/**
	 * Returns a range of all the commerce price modifiers where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @return the range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByUuid_C(
		String uuid, long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifiers where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifiers where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByUuid_C(
		String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price modifier in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByUuid_C_First(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the first commerce price modifier in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByUuid_C_First(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the last commerce price modifier in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByUuid_C_Last(
			String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the last commerce price modifier in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByUuid_C_Last(
		String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the commerce price modifiers before and after the current commerce price modifier in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceModifierId the primary key of the current commerce price modifier
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier
	 * @throws NoSuchPriceModifierException if a commerce price modifier with the primary key could not be found
	 */
	public CommercePriceModifier[] findByUuid_C_PrevAndNext(
			long commercePriceModifierId, String uuid, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Removes all the commerce price modifiers where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	public void removeByUuid_C(String uuid, long companyId);

	/**
	 * Returns the number of commerce price modifiers where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price modifiers
	 */
	public int countByUuid_C(String uuid, long companyId);

	/**
	 * Returns all the commerce price modifiers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByCompanyId(
		long companyId);

	/**
	 * Returns a range of all the commerce price modifiers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @return the range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByCompanyId(
		long companyId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifiers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifiers where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price modifier in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByCompanyId_First(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the first commerce price modifier in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the last commerce price modifier in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByCompanyId_Last(
			long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the last commerce price modifier in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the commerce price modifiers before and after the current commerce price modifier in the ordered set where companyId = &#63;.
	 *
	 * @param commercePriceModifierId the primary key of the current commerce price modifier
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier
	 * @throws NoSuchPriceModifierException if a commerce price modifier with the primary key could not be found
	 */
	public CommercePriceModifier[] findByCompanyId_PrevAndNext(
			long commercePriceModifierId, long companyId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Removes all the commerce price modifiers where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	public void removeByCompanyId(long companyId);

	/**
	 * Returns the number of commerce price modifiers where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce price modifiers
	 */
	public int countByCompanyId(long companyId);

	/**
	 * Returns all the commerce price modifiers where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByCommercePriceListId(
		long commercePriceListId);

	/**
	 * Returns a range of all the commerce price modifiers where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @return the range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByCommercePriceListId(
		long commercePriceListId, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifiers where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifiers where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price modifier in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByCommercePriceListId_First(
			long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the first commerce price modifier in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByCommercePriceListId_First(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the last commerce price modifier in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByCommercePriceListId_Last(
			long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the last commerce price modifier in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByCommercePriceListId_Last(
		long commercePriceListId,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the commerce price modifiers before and after the current commerce price modifier in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceModifierId the primary key of the current commerce price modifier
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier
	 * @throws NoSuchPriceModifierException if a commerce price modifier with the primary key could not be found
	 */
	public CommercePriceModifier[] findByCommercePriceListId_PrevAndNext(
			long commercePriceModifierId, long commercePriceListId,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Removes all the commerce price modifiers where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	public void removeByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns the number of commerce price modifiers where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price modifiers
	 */
	public int countByCommercePriceListId(long commercePriceListId);

	/**
	 * Returns all the commerce price modifiers where companyId = &#63; and target = &#63;.
	 *
	 * @param companyId the company ID
	 * @param target the target
	 * @return the matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByC_T(
		long companyId, String target);

	/**
	 * Returns a range of all the commerce price modifiers where companyId = &#63; and target = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param target the target
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @return the range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByC_T(
		long companyId, String target, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifiers where companyId = &#63; and target = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param target the target
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByC_T(
		long companyId, String target, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifiers where companyId = &#63; and target = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param target the target
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByC_T(
		long companyId, String target, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price modifier in the ordered set where companyId = &#63; and target = &#63;.
	 *
	 * @param companyId the company ID
	 * @param target the target
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByC_T_First(
			long companyId, String target,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the first commerce price modifier in the ordered set where companyId = &#63; and target = &#63;.
	 *
	 * @param companyId the company ID
	 * @param target the target
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByC_T_First(
		long companyId, String target,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the last commerce price modifier in the ordered set where companyId = &#63; and target = &#63;.
	 *
	 * @param companyId the company ID
	 * @param target the target
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByC_T_Last(
			long companyId, String target,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the last commerce price modifier in the ordered set where companyId = &#63; and target = &#63;.
	 *
	 * @param companyId the company ID
	 * @param target the target
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByC_T_Last(
		long companyId, String target,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the commerce price modifiers before and after the current commerce price modifier in the ordered set where companyId = &#63; and target = &#63;.
	 *
	 * @param commercePriceModifierId the primary key of the current commerce price modifier
	 * @param companyId the company ID
	 * @param target the target
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier
	 * @throws NoSuchPriceModifierException if a commerce price modifier with the primary key could not be found
	 */
	public CommercePriceModifier[] findByC_T_PrevAndNext(
			long commercePriceModifierId, long companyId, String target,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Removes all the commerce price modifiers where companyId = &#63; and target = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param target the target
	 */
	public void removeByC_T(long companyId, String target);

	/**
	 * Returns the number of commerce price modifiers where companyId = &#63; and target = &#63;.
	 *
	 * @param companyId the company ID
	 * @param target the target
	 * @return the number of matching commerce price modifiers
	 */
	public int countByC_T(long companyId, String target);

	/**
	 * Returns all the commerce price modifiers where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByLtD_S(
		Date displayDate, int status);

	/**
	 * Returns a range of all the commerce price modifiers where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @return the range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByLtD_S(
		Date displayDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifiers where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifiers where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByLtD_S(
		Date displayDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price modifier in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByLtD_S_First(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the first commerce price modifier in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByLtD_S_First(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the last commerce price modifier in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByLtD_S_Last(
			Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the last commerce price modifier in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByLtD_S_Last(
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the commerce price modifiers before and after the current commerce price modifier in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commercePriceModifierId the primary key of the current commerce price modifier
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier
	 * @throws NoSuchPriceModifierException if a commerce price modifier with the primary key could not be found
	 */
	public CommercePriceModifier[] findByLtD_S_PrevAndNext(
			long commercePriceModifierId, Date displayDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Removes all the commerce price modifiers where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	public void removeByLtD_S(Date displayDate, int status);

	/**
	 * Returns the number of commerce price modifiers where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce price modifiers
	 */
	public int countByLtD_S(Date displayDate, int status);

	/**
	 * Returns all the commerce price modifiers where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByLtE_S(
		Date expirationDate, int status);

	/**
	 * Returns a range of all the commerce price modifiers where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @return the range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByLtE_S(
		Date expirationDate, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifiers where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifiers where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price modifier in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByLtE_S_First(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the first commerce price modifier in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByLtE_S_First(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the last commerce price modifier in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByLtE_S_Last(
			Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the last commerce price modifier in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByLtE_S_Last(
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the commerce price modifiers before and after the current commerce price modifier in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commercePriceModifierId the primary key of the current commerce price modifier
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier
	 * @throws NoSuchPriceModifierException if a commerce price modifier with the primary key could not be found
	 */
	public CommercePriceModifier[] findByLtE_S_PrevAndNext(
			long commercePriceModifierId, Date expirationDate, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Removes all the commerce price modifiers where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	public void removeByLtE_S(Date expirationDate, int status);

	/**
	 * Returns the number of commerce price modifiers where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce price modifiers
	 */
	public int countByLtE_S(Date expirationDate, int status);

	/**
	 * Returns all the commerce price modifiers where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_S(
		long groupId, long companyId, int status);

	/**
	 * Returns a range of all the commerce price modifiers where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @return the range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_S(
		long groupId, long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifiers where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_S(
		long groupId, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifiers where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_S(
		long groupId, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price modifier in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByG_C_S_First(
			long groupId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the first commerce price modifier in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByG_C_S_First(
		long groupId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the last commerce price modifier in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByG_C_S_Last(
			long groupId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the last commerce price modifier in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByG_C_S_Last(
		long groupId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the commerce price modifiers before and after the current commerce price modifier in the ordered set where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param commercePriceModifierId the primary key of the current commerce price modifier
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier
	 * @throws NoSuchPriceModifierException if a commerce price modifier with the primary key could not be found
	 */
	public CommercePriceModifier[] findByG_C_S_PrevAndNext(
			long commercePriceModifierId, long groupId, long companyId,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns all the commerce price modifiers where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_S(
		long[] groupIds, long companyId, int status);

	/**
	 * Returns a range of all the commerce price modifiers where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @return the range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifiers where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifiers where groupId = &#63; and companyId = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_S(
		long[] groupIds, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce price modifiers where groupId = &#63; and companyId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 */
	public void removeByG_C_S(long groupId, long companyId, int status);

	/**
	 * Returns the number of commerce price modifiers where groupId = &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching commerce price modifiers
	 */
	public int countByG_C_S(long groupId, long companyId, int status);

	/**
	 * Returns the number of commerce price modifiers where groupId = any &#63; and companyId = &#63; and status = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching commerce price modifiers
	 */
	public int countByG_C_S(long[] groupIds, long companyId, int status);

	/**
	 * Returns all the commerce price modifiers where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_NotS(
		long groupId, long companyId, int status);

	/**
	 * Returns a range of all the commerce price modifiers where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @return the range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_NotS(
		long groupId, long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifiers where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_NotS(
		long groupId, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifiers where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_NotS(
		long groupId, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Returns the first commerce price modifier in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByG_C_NotS_First(
			long groupId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the first commerce price modifier in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByG_C_NotS_First(
		long groupId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the last commerce price modifier in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByG_C_NotS_Last(
			long groupId, long companyId, int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the last commerce price modifier in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByG_C_NotS_Last(
		long groupId, long companyId, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns the commerce price modifiers before and after the current commerce price modifier in the ordered set where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param commercePriceModifierId the primary key of the current commerce price modifier
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price modifier
	 * @throws NoSuchPriceModifierException if a commerce price modifier with the primary key could not be found
	 */
	public CommercePriceModifier[] findByG_C_NotS_PrevAndNext(
			long commercePriceModifierId, long groupId, long companyId,
			int status,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommercePriceModifier> orderByComparator)
		throws NoSuchPriceModifierException;

	/**
	 * Returns all the commerce price modifiers where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_NotS(
		long[] groupIds, long companyId, int status);

	/**
	 * Returns a range of all the commerce price modifiers where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @return the range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifiers where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifiers where groupId = &#63; and companyId = &#63; and status &ne; &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findByG_C_NotS(
		long[] groupIds, long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce price modifiers where groupId = &#63; and companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 */
	public void removeByG_C_NotS(long groupId, long companyId, int status);

	/**
	 * Returns the number of commerce price modifiers where groupId = &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching commerce price modifiers
	 */
	public int countByG_C_NotS(long groupId, long companyId, int status);

	/**
	 * Returns the number of commerce price modifiers where groupId = any &#63; and companyId = &#63; and status &ne; &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching commerce price modifiers
	 */
	public int countByG_C_NotS(long[] groupIds, long companyId, int status);

	/**
	 * Returns the commerce price modifier where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchPriceModifierException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce price modifier
	 * @throws NoSuchPriceModifierException if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the commerce price modifier where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByC_ERC(
		long companyId, String externalReferenceCode);

	/**
	 * Returns the commerce price modifier where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price modifier, or <code>null</code> if a matching commerce price modifier could not be found
	 */
	public CommercePriceModifier fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache);

	/**
	 * Removes the commerce price modifier where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce price modifier that was removed
	 */
	public CommercePriceModifier removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the number of commerce price modifiers where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce price modifiers
	 */
	public int countByC_ERC(long companyId, String externalReferenceCode);

	/**
	 * Caches the commerce price modifier in the entity cache if it is enabled.
	 *
	 * @param commercePriceModifier the commerce price modifier
	 */
	public void cacheResult(CommercePriceModifier commercePriceModifier);

	/**
	 * Caches the commerce price modifiers in the entity cache if it is enabled.
	 *
	 * @param commercePriceModifiers the commerce price modifiers
	 */
	public void cacheResult(
		java.util.List<CommercePriceModifier> commercePriceModifiers);

	/**
	 * Creates a new commerce price modifier with the primary key. Does not add the commerce price modifier to the database.
	 *
	 * @param commercePriceModifierId the primary key for the new commerce price modifier
	 * @return the new commerce price modifier
	 */
	public CommercePriceModifier create(long commercePriceModifierId);

	/**
	 * Removes the commerce price modifier with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceModifierId the primary key of the commerce price modifier
	 * @return the commerce price modifier that was removed
	 * @throws NoSuchPriceModifierException if a commerce price modifier with the primary key could not be found
	 */
	public CommercePriceModifier remove(long commercePriceModifierId)
		throws NoSuchPriceModifierException;

	public CommercePriceModifier updateImpl(
		CommercePriceModifier commercePriceModifier);

	/**
	 * Returns the commerce price modifier with the primary key or throws a <code>NoSuchPriceModifierException</code> if it could not be found.
	 *
	 * @param commercePriceModifierId the primary key of the commerce price modifier
	 * @return the commerce price modifier
	 * @throws NoSuchPriceModifierException if a commerce price modifier with the primary key could not be found
	 */
	public CommercePriceModifier findByPrimaryKey(long commercePriceModifierId)
		throws NoSuchPriceModifierException;

	/**
	 * Returns the commerce price modifier with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceModifierId the primary key of the commerce price modifier
	 * @return the commerce price modifier, or <code>null</code> if a commerce price modifier with the primary key could not be found
	 */
	public CommercePriceModifier fetchByPrimaryKey(
		long commercePriceModifierId);

	/**
	 * Returns all the commerce price modifiers.
	 *
	 * @return the commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findAll();

	/**
	 * Returns a range of all the commerce price modifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @return the range of commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the commerce price modifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator);

	/**
	 * Returns an ordered range of all the commerce price modifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceModifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price modifiers
	 * @param end the upper bound of the range of commerce price modifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price modifiers
	 */
	public java.util.List<CommercePriceModifier> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommercePriceModifier>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the commerce price modifiers from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of commerce price modifiers.
	 *
	 * @return the number of commerce price modifiers
	 */
	public int countAll();

}