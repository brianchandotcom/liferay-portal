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

package com.liferay.commerce.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchCartException;
import com.liferay.commerce.model.CommerceCart;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the commerce cart service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.service.persistence.impl.CommerceCartPersistenceImpl
 * @see CommerceCartUtil
 * @generated
 */
@ProviderType
public interface CommerceCartPersistence extends BasePersistence<CommerceCart> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceCartUtil} to access the commerce cart persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the commerce carts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching commerce carts
	*/
	public java.util.List<CommerceCart> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the commerce carts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the commerce carts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns an ordered range of all the commerce carts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the first commerce cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the last commerce cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the last commerce cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where uuid = &#63;.
	*
	* @param commerceCartId the primary key of the current commerce cart
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public CommerceCart[] findByUuid_PrevAndNext(long commerceCartId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Removes all the commerce carts where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of commerce carts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching commerce carts
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the commerce cart where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCartException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCartException;

	/**
	* Returns the commerce cart where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the commerce cart where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the commerce cart where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the commerce cart that was removed
	*/
	public CommerceCart removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCartException;

	/**
	* Returns the number of commerce carts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching commerce carts
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the commerce carts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching commerce carts
	*/
	public java.util.List<CommerceCart> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the commerce carts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the commerce carts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns an ordered range of all the commerce carts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the first commerce cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByUuid_C_First(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the last commerce cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the last commerce cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByUuid_C_Last(java.lang.String uuid,
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param commerceCartId the primary key of the current commerce cart
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public CommerceCart[] findByUuid_C_PrevAndNext(long commerceCartId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Removes all the commerce carts where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of commerce carts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching commerce carts
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the commerce carts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching commerce carts
	*/
	public java.util.List<CommerceCart> findByGroupId(long groupId);

	/**
	* Returns a range of all the commerce carts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByGroupId(long groupId, int start,
		int end);

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByGroupId(long groupId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where groupId = &#63;.
	*
	* @param commerceCartId the primary key of the current commerce cart
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public CommerceCart[] findByGroupId_PrevAndNext(long commerceCartId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Removes all the commerce carts where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of commerce carts where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching commerce carts
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns all the commerce carts where billingAddressId = &#63;.
	*
	* @param billingAddressId the billing address ID
	* @return the matching commerce carts
	*/
	public java.util.List<CommerceCart> findByBillingAddressId(
		long billingAddressId);

	/**
	* Returns a range of all the commerce carts where billingAddressId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param billingAddressId the billing address ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByBillingAddressId(
		long billingAddressId, int start, int end);

	/**
	* Returns an ordered range of all the commerce carts where billingAddressId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param billingAddressId the billing address ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByBillingAddressId(
		long billingAddressId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns an ordered range of all the commerce carts where billingAddressId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param billingAddressId the billing address ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByBillingAddressId(
		long billingAddressId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce cart in the ordered set where billingAddressId = &#63;.
	*
	* @param billingAddressId the billing address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByBillingAddressId_First(long billingAddressId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the first commerce cart in the ordered set where billingAddressId = &#63;.
	*
	* @param billingAddressId the billing address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByBillingAddressId_First(long billingAddressId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the last commerce cart in the ordered set where billingAddressId = &#63;.
	*
	* @param billingAddressId the billing address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByBillingAddressId_Last(long billingAddressId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the last commerce cart in the ordered set where billingAddressId = &#63;.
	*
	* @param billingAddressId the billing address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByBillingAddressId_Last(long billingAddressId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where billingAddressId = &#63;.
	*
	* @param commerceCartId the primary key of the current commerce cart
	* @param billingAddressId the billing address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public CommerceCart[] findByBillingAddressId_PrevAndNext(
		long commerceCartId, long billingAddressId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Removes all the commerce carts where billingAddressId = &#63; from the database.
	*
	* @param billingAddressId the billing address ID
	*/
	public void removeByBillingAddressId(long billingAddressId);

	/**
	* Returns the number of commerce carts where billingAddressId = &#63;.
	*
	* @param billingAddressId the billing address ID
	* @return the number of matching commerce carts
	*/
	public int countByBillingAddressId(long billingAddressId);

	/**
	* Returns all the commerce carts where shippingAddressId = &#63;.
	*
	* @param shippingAddressId the shipping address ID
	* @return the matching commerce carts
	*/
	public java.util.List<CommerceCart> findByShippingAddressId(
		long shippingAddressId);

	/**
	* Returns a range of all the commerce carts where shippingAddressId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param shippingAddressId the shipping address ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByShippingAddressId(
		long shippingAddressId, int start, int end);

	/**
	* Returns an ordered range of all the commerce carts where shippingAddressId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param shippingAddressId the shipping address ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByShippingAddressId(
		long shippingAddressId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns an ordered range of all the commerce carts where shippingAddressId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param shippingAddressId the shipping address ID
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByShippingAddressId(
		long shippingAddressId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce cart in the ordered set where shippingAddressId = &#63;.
	*
	* @param shippingAddressId the shipping address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByShippingAddressId_First(long shippingAddressId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the first commerce cart in the ordered set where shippingAddressId = &#63;.
	*
	* @param shippingAddressId the shipping address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByShippingAddressId_First(long shippingAddressId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the last commerce cart in the ordered set where shippingAddressId = &#63;.
	*
	* @param shippingAddressId the shipping address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByShippingAddressId_Last(long shippingAddressId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the last commerce cart in the ordered set where shippingAddressId = &#63;.
	*
	* @param shippingAddressId the shipping address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByShippingAddressId_Last(long shippingAddressId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where shippingAddressId = &#63;.
	*
	* @param commerceCartId the primary key of the current commerce cart
	* @param shippingAddressId the shipping address ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public CommerceCart[] findByShippingAddressId_PrevAndNext(
		long commerceCartId, long shippingAddressId,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Removes all the commerce carts where shippingAddressId = &#63; from the database.
	*
	* @param shippingAddressId the shipping address ID
	*/
	public void removeByShippingAddressId(long shippingAddressId);

	/**
	* Returns the number of commerce carts where shippingAddressId = &#63;.
	*
	* @param shippingAddressId the shipping address ID
	* @return the number of matching commerce carts
	*/
	public int countByShippingAddressId(long shippingAddressId);

	/**
	* Returns all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @return the matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_N(long groupId, long userId,
		java.lang.String name);

	/**
	* Returns a range of all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_N(long groupId, long userId,
		java.lang.String name, int start, int end);

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_N(long groupId, long userId,
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_N(long groupId, long userId,
		java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByG_U_N_First(long groupId, long userId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByG_U_N_First(long groupId, long userId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByG_U_N_Last(long groupId, long userId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByG_U_N_Last(long groupId, long userId,
		java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* @param commerceCartId the primary key of the current commerce cart
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public CommerceCart[] findByG_U_N_PrevAndNext(long commerceCartId,
		long groupId, long userId, java.lang.String name,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Removes all the commerce carts where groupId = &#63; and userId = &#63; and name = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	*/
	public void removeByG_U_N(long groupId, long userId, java.lang.String name);

	/**
	* Returns the number of commerce carts where groupId = &#63; and userId = &#63; and name = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param name the name
	* @return the number of matching commerce carts
	*/
	public int countByG_U_N(long groupId, long userId, java.lang.String name);

	/**
	* Returns all the commerce carts where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @return the matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_D(long groupId, long userId,
		boolean defaultCart);

	/**
	* Returns a range of all the commerce carts where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_D(long groupId, long userId,
		boolean defaultCart, int start, int end);

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_D(long groupId, long userId,
		boolean defaultCart, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns an ordered range of all the commerce carts where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching commerce carts
	*/
	public java.util.List<CommerceCart> findByG_U_D(long groupId, long userId,
		boolean defaultCart, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByG_U_D_First(long groupId, long userId,
		boolean defaultCart,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the first commerce cart in the ordered set where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByG_U_D_First(long groupId, long userId,
		boolean defaultCart,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart
	* @throws NoSuchCartException if a matching commerce cart could not be found
	*/
	public CommerceCart findByG_U_D_Last(long groupId, long userId,
		boolean defaultCart,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Returns the last commerce cart in the ordered set where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching commerce cart, or <code>null</code> if a matching commerce cart could not be found
	*/
	public CommerceCart fetchByG_U_D_Last(long groupId, long userId,
		boolean defaultCart,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns the commerce carts before and after the current commerce cart in the ordered set where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* @param commerceCartId the primary key of the current commerce cart
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public CommerceCart[] findByG_U_D_PrevAndNext(long commerceCartId,
		long groupId, long userId, boolean defaultCart,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator)
		throws NoSuchCartException;

	/**
	* Removes all the commerce carts where groupId = &#63; and userId = &#63; and defaultCart = &#63; from the database.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	*/
	public void removeByG_U_D(long groupId, long userId, boolean defaultCart);

	/**
	* Returns the number of commerce carts where groupId = &#63; and userId = &#63; and defaultCart = &#63;.
	*
	* @param groupId the group ID
	* @param userId the user ID
	* @param defaultCart the default cart
	* @return the number of matching commerce carts
	*/
	public int countByG_U_D(long groupId, long userId, boolean defaultCart);

	/**
	* Caches the commerce cart in the entity cache if it is enabled.
	*
	* @param commerceCart the commerce cart
	*/
	public void cacheResult(CommerceCart commerceCart);

	/**
	* Caches the commerce carts in the entity cache if it is enabled.
	*
	* @param commerceCarts the commerce carts
	*/
	public void cacheResult(java.util.List<CommerceCart> commerceCarts);

	/**
	* Creates a new commerce cart with the primary key. Does not add the commerce cart to the database.
	*
	* @param commerceCartId the primary key for the new commerce cart
	* @return the new commerce cart
	*/
	public CommerceCart create(long commerceCartId);

	/**
	* Removes the commerce cart with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param commerceCartId the primary key of the commerce cart
	* @return the commerce cart that was removed
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public CommerceCart remove(long commerceCartId) throws NoSuchCartException;

	public CommerceCart updateImpl(CommerceCart commerceCart);

	/**
	* Returns the commerce cart with the primary key or throws a {@link NoSuchCartException} if it could not be found.
	*
	* @param commerceCartId the primary key of the commerce cart
	* @return the commerce cart
	* @throws NoSuchCartException if a commerce cart with the primary key could not be found
	*/
	public CommerceCart findByPrimaryKey(long commerceCartId)
		throws NoSuchCartException;

	/**
	* Returns the commerce cart with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param commerceCartId the primary key of the commerce cart
	* @return the commerce cart, or <code>null</code> if a commerce cart with the primary key could not be found
	*/
	public CommerceCart fetchByPrimaryKey(long commerceCartId);

	@Override
	public java.util.Map<java.io.Serializable, CommerceCart> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the commerce carts.
	*
	* @return the commerce carts
	*/
	public java.util.List<CommerceCart> findAll();

	/**
	* Returns a range of all the commerce carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @return the range of commerce carts
	*/
	public java.util.List<CommerceCart> findAll(int start, int end);

	/**
	* Returns an ordered range of all the commerce carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of commerce carts
	*/
	public java.util.List<CommerceCart> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator);

	/**
	* Returns an ordered range of all the commerce carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of commerce carts
	* @param end the upper bound of the range of commerce carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of commerce carts
	*/
	public java.util.List<CommerceCart> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CommerceCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the commerce carts from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of commerce carts.
	*
	* @return the number of commerce carts
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}