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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

/**
 * The persistence interface for the commerce discount service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.discount.service.persistence.impl.CommerceDiscountPersistenceImpl
 * @see CommerceDiscountUtil
 * @generated
 */
@ProviderType
public interface CommerceDiscountPersistence extends BasePersistence<CommerceDiscount> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceDiscountUtil} to access the commerce discount persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce discounts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByUuid(String uuid);

	/**
	* Returns a range of all the commerce discounts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @return the range of matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByUuid(String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the commerce discounts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns an ordered range of all the commerce discounts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByUuid(String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce discount in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public CommerceDiscount findByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Returns the first commerce discount in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public CommerceDiscount fetchByUuid_First(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns the last commerce discount in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public CommerceDiscount findByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Returns the last commerce discount in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public CommerceDiscount fetchByUuid_Last(String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns the commerce discounts before and after the current commerce discount in the ordered set where uuid = &#63;.
	*
	* @param commerceDiscountId the primary key of the current commerce discount
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public CommerceDiscount[] findByUuid_PrevAndNext(long commerceDiscountId,
		String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Removes all the commerce discounts where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(String uuid);

	/**
	* Returns the number of commerce discounts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce discounts
	*/
	public int countByUuid(String uuid);

	/**
	* Returns the commerce discount where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchDiscountException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public CommerceDiscount findByUUID_G(String uuid, long groupId)
		throws NoSuchDiscountException;

	/**
	* Returns the commerce discount where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public CommerceDiscount fetchByUUID_G(String uuid, long groupId);

	/**
	* Returns the commerce discount where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public CommerceDiscount fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the commerce discount where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce discount that was removed
	*/
	public CommerceDiscount removeByUUID_G(String uuid, long groupId)
		throws NoSuchDiscountException;

	/**
	* Returns the number of commerce discounts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce discounts
	*/
	public int countByUUID_G(String uuid, long groupId);

	/**
	* Returns all the commerce discounts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByUuid_C(String uuid,
		long companyId);

	/**
	* Returns a range of all the commerce discounts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @return the range of matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByUuid_C(String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce discounts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns an ordered range of all the commerce discounts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByUuid_C(String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public CommerceDiscount findByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Returns the first commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public CommerceDiscount fetchByUuid_C_First(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns the last commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public CommerceDiscount findByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Returns the last commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public CommerceDiscount fetchByUuid_C_Last(String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns the commerce discounts before and after the current commerce discount in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceDiscountId the primary key of the current commerce discount
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public CommerceDiscount[] findByUuid_C_PrevAndNext(
		long commerceDiscountId, String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Removes all the commerce discounts where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(String uuid, long companyId);

	/**
	* Returns the number of commerce discounts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce discounts
	*/
	public int countByUuid_C(String uuid, long companyId);

	/**
	* Returns all the commerce discounts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce discounts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @return the range of matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce discounts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns an ordered range of all the commerce discounts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce discount in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public CommerceDiscount findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Returns the first commerce discount in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public CommerceDiscount fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns the last commerce discount in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public CommerceDiscount findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Returns the last commerce discount in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public CommerceDiscount fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns the commerce discounts before and after the current commerce discount in the ordered set where groupId = &#63;.
	*
	* @param commerceDiscountId the primary key of the current commerce discount
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public CommerceDiscount[] findByGroupId_PrevAndNext(
		long commerceDiscountId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Returns all the commerce discounts that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce discounts that the user has permission to view
	*/
	public java.util.List<CommerceDiscount> filterFindByGroupId(long groupId);

	/**
	* Returns a range of all the commerce discounts that the user has permission to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @return the range of matching commerce discounts that the user has permission to view
	*/
	public java.util.List<CommerceDiscount> filterFindByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce discounts that the user has permissions to view where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discounts that the user has permission to view
	*/
	public java.util.List<CommerceDiscount> filterFindByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns the commerce discounts before and after the current commerce discount in the ordered set of commerce discounts that the user has permission to view where groupId = &#63;.
	*
	* @param commerceDiscountId the primary key of the current commerce discount
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public CommerceDiscount[] filterFindByGroupId_PrevAndNext(
		long commerceDiscountId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Removes all the commerce discounts where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce discounts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce discounts
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the number of commerce discounts that the user has permission to view where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce discounts that the user has permission to view
	*/
	public int filterCountByGroupId(long groupId);

	/**
	* Returns all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByLtD_S(Date displayDate,
		int status);

	/**
	* Returns a range of all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @return the range of matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByLtD_S(Date displayDate,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByLtD_S(Date displayDate,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns an ordered range of all the commerce discounts where displayDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param displayDate the display date
	* @param status the status
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByLtD_S(Date displayDate,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public CommerceDiscount findByLtD_S_First(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Returns the first commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public CommerceDiscount fetchByLtD_S_First(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns the last commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public CommerceDiscount findByLtD_S_Last(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Returns the last commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public CommerceDiscount fetchByLtD_S_Last(Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns the commerce discounts before and after the current commerce discount in the ordered set where displayDate &lt; &#63; and status = &#63;.
	*
	* @param commerceDiscountId the primary key of the current commerce discount
	* @param displayDate the display date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public CommerceDiscount[] findByLtD_S_PrevAndNext(long commerceDiscountId,
		Date displayDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Removes all the commerce discounts where displayDate &lt; &#63; and status = &#63; from the database.
	*
	* @param displayDate the display date
	* @param status the status
	*/
	public void removeByLtD_S(Date displayDate, int status);

	/**
	* Returns the number of commerce discounts where displayDate &lt; &#63; and status = &#63;.
	*
	* @param displayDate the display date
	* @param status the status
	* @return the number of matching commerce discounts
	*/
	public int countByLtD_S(Date displayDate, int status);

	/**
	* Returns all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @return the matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByLtE_S(Date expirationDate,
		int status);

	/**
	* Returns a range of all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @return the range of matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByLtE_S(Date expirationDate,
		int status, int start, int end);

	/**
	* Returns an ordered range of all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByLtE_S(Date expirationDate,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns an ordered range of all the commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce discounts
	*/
	public java.util.List<CommerceDiscount> findByLtE_S(Date expirationDate,
		int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public CommerceDiscount findByLtE_S_First(Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Returns the first commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public CommerceDiscount fetchByLtE_S_First(Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns the last commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount
	* @throws NoSuchDiscountException if a matching commerce discount could not be found
	*/
	public CommerceDiscount findByLtE_S_Last(Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Returns the last commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce discount, or <code>null</code> if a matching commerce discount could not be found
	*/
	public CommerceDiscount fetchByLtE_S_Last(Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns the commerce discounts before and after the current commerce discount in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	*
	* @param commerceDiscountId the primary key of the current commerce discount
	* @param expirationDate the expiration date
	* @param status the status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce discount
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public CommerceDiscount[] findByLtE_S_PrevAndNext(long commerceDiscountId,
		Date expirationDate, int status,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator)
		throws NoSuchDiscountException;

	/**
	* Removes all the commerce discounts where expirationDate &lt; &#63; and status = &#63; from the database.
	*
	* @param expirationDate the expiration date
	* @param status the status
	*/
	public void removeByLtE_S(Date expirationDate, int status);

	/**
	* Returns the number of commerce discounts where expirationDate &lt; &#63; and status = &#63;.
	*
	* @param expirationDate the expiration date
	* @param status the status
	* @return the number of matching commerce discounts
	*/
	public int countByLtE_S(Date expirationDate, int status);

	/**
	* Caches the commerce discount in the entity cache if it is enabled.
	*
	* @param commerceDiscount the commerce discount
	*/
	public void cacheResult(CommerceDiscount commerceDiscount);

	/**
	* Caches the commerce discounts in the entity cache if it is enabled.
	*
	* @param commerceDiscounts the commerce discounts
	*/
	public void cacheResult(java.util.List<CommerceDiscount> commerceDiscounts);

	/**
	* Creates a new commerce discount with the primary key. Does not add the commerce discount to the database.
	*
	* @param commerceDiscountId the primary key for the new commerce discount
	* @return the new commerce discount
	*/
	public CommerceDiscount create(long commerceDiscountId);

	/**
	* Removes the commerce discount with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceDiscountId the primary key of the commerce discount
	* @return the commerce discount that was removed
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public CommerceDiscount remove(long commerceDiscountId)
		throws NoSuchDiscountException;

	public CommerceDiscount updateImpl(CommerceDiscount commerceDiscount);

	/**
	* Returns the commerce discount with the primary key or throws a {@link NoSuchDiscountException} if it could not be found.
	*
	* @param commerceDiscountId the primary key of the commerce discount
	* @return the commerce discount
	* @throws NoSuchDiscountException if a commerce discount with the primary key could not be found
	*/
	public CommerceDiscount findByPrimaryKey(long commerceDiscountId)
		throws NoSuchDiscountException;

	/**
	* Returns the commerce discount with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceDiscountId the primary key of the commerce discount
	* @return the commerce discount, or <code>null</code> if a commerce discount with the primary key could not be found
	*/
	public CommerceDiscount fetchByPrimaryKey(long commerceDiscountId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceDiscount> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce discounts.
	*
	* @return the commerce discounts
	*/
	public java.util.List<CommerceDiscount> findAll();

	/**
	* Returns a range of all the commerce discounts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @return the range of commerce discounts
	*/
	public java.util.List<CommerceDiscount> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce discounts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce discounts
	*/
	public java.util.List<CommerceDiscount> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator);

	/**
	* Returns an ordered range of all the commerce discounts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce discounts
	* @param end the upper bound of the range of commerce discounts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce discounts
	*/
	public java.util.List<CommerceDiscount> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceDiscount> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce discounts from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce discounts.
	*
	* @return the number of commerce discounts
	*/
	public int countAll();

	@Override
	public java.util.Set<String> getBadColumnNames();
}