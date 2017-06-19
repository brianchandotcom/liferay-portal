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

package com.liferay.commerce.cart.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.cart.exception.NoSuchCCartItemException;
import com.liferay.commerce.cart.model.CCartItem;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the c cart item service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.cart.service.persistence.impl.CCartItemPersistenceImpl
 * @see CCartItemUtil
 * @generated
 */
@ProviderType
public interface CCartItemPersistence extends BasePersistence<CCartItem> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CCartItemUtil} to access the c cart item persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the c cart items where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching c cart items
	*/
	public java.util.List<CCartItem> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the c cart items where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @return the range of matching c cart items
	*/
	public java.util.List<CCartItem> findByUuid(java.lang.String uuid,
		int start, int end);

	/**
	* Returns an ordered range of all the c cart items where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c cart items
	*/
	public java.util.List<CCartItem> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator);

	/**
	* Returns an ordered range of all the c cart items where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c cart items
	*/
	public java.util.List<CCartItem> findByUuid(java.lang.String uuid,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first c cart item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart item
	* @throws NoSuchCCartItemException if a matching c cart item could not be found
	*/
	public CCartItem findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException;

	/**
	* Returns the first c cart item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public CCartItem fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator);

	/**
	* Returns the last c cart item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart item
	* @throws NoSuchCCartItemException if a matching c cart item could not be found
	*/
	public CCartItem findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException;

	/**
	* Returns the last c cart item in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public CCartItem fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator);

	/**
	* Returns the c cart items before and after the current c cart item in the ordered set where uuid = &#63;.
	*
	* @param CCartItemId the primary key of the current c cart item
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c cart item
	* @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	*/
	public CCartItem[] findByUuid_PrevAndNext(long CCartItemId,
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException;

	/**
	* Removes all the c cart items where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of c cart items where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching c cart items
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the c cart item where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCCartItemException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching c cart item
	* @throws NoSuchCCartItemException if a matching c cart item could not be found
	*/
	public CCartItem findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCCartItemException;

	/**
	* Returns the c cart item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public CCartItem fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the c cart item where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public CCartItem fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the c cart item where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the c cart item that was removed
	*/
	public CCartItem removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCCartItemException;

	/**
	* Returns the number of c cart items where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching c cart items
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the c cart items where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching c cart items
	*/
	public java.util.List<CCartItem> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the c cart items where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @return the range of matching c cart items
	*/
	public java.util.List<CCartItem> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the c cart items where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c cart items
	*/
	public java.util.List<CCartItem> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator);

	/**
	* Returns an ordered range of all the c cart items where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c cart items
	*/
	public java.util.List<CCartItem> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first c cart item in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart item
	* @throws NoSuchCCartItemException if a matching c cart item could not be found
	*/
	public CCartItem findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException;

	/**
	* Returns the first c cart item in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public CCartItem fetchByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator);

	/**
	* Returns the last c cart item in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart item
	* @throws NoSuchCCartItemException if a matching c cart item could not be found
	*/
	public CCartItem findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException;

	/**
	* Returns the last c cart item in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public CCartItem fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator);

	/**
	* Returns the c cart items before and after the current c cart item in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CCartItemId the primary key of the current c cart item
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c cart item
	* @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	*/
	public CCartItem[] findByUuid_C_PrevAndNext(long CCartItemId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException;

	/**
	* Removes all the c cart items where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of c cart items where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching c cart items
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the c cart items where CCartId = &#63;.
	*
	* @param CCartId the c cart ID
	* @return the matching c cart items
	*/
	public java.util.List<CCartItem> findByCCartId(long CCartId);

	/**
	* Returns a range of all the c cart items where CCartId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CCartId the c cart ID
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @return the range of matching c cart items
	*/
	public java.util.List<CCartItem> findByCCartId(long CCartId, int start,
		int end);

	/**
	* Returns an ordered range of all the c cart items where CCartId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CCartId the c cart ID
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c cart items
	*/
	public java.util.List<CCartItem> findByCCartId(long CCartId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator);

	/**
	* Returns an ordered range of all the c cart items where CCartId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param CCartId the c cart ID
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c cart items
	*/
	public java.util.List<CCartItem> findByCCartId(long CCartId, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first c cart item in the ordered set where CCartId = &#63;.
	*
	* @param CCartId the c cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart item
	* @throws NoSuchCCartItemException if a matching c cart item could not be found
	*/
	public CCartItem findByCCartId_First(long CCartId,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException;

	/**
	* Returns the first c cart item in the ordered set where CCartId = &#63;.
	*
	* @param CCartId the c cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public CCartItem fetchByCCartId_First(long CCartId,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator);

	/**
	* Returns the last c cart item in the ordered set where CCartId = &#63;.
	*
	* @param CCartId the c cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart item
	* @throws NoSuchCCartItemException if a matching c cart item could not be found
	*/
	public CCartItem findByCCartId_Last(long CCartId,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException;

	/**
	* Returns the last c cart item in the ordered set where CCartId = &#63;.
	*
	* @param CCartId the c cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart item, or <code>null</code> if a matching c cart item could not be found
	*/
	public CCartItem fetchByCCartId_Last(long CCartId,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator);

	/**
	* Returns the c cart items before and after the current c cart item in the ordered set where CCartId = &#63;.
	*
	* @param CCartItemId the primary key of the current c cart item
	* @param CCartId the c cart ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c cart item
	* @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	*/
	public CCartItem[] findByCCartId_PrevAndNext(long CCartItemId,
		long CCartId,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator)
		throws NoSuchCCartItemException;

	/**
	* Removes all the c cart items where CCartId = &#63; from the database.
	*
	* @param CCartId the c cart ID
	*/
	public void removeByCCartId(long CCartId);

	/**
	* Returns the number of c cart items where CCartId = &#63;.
	*
	* @param CCartId the c cart ID
	* @return the number of matching c cart items
	*/
	public int countByCCartId(long CCartId);

	/**
	* Caches the c cart item in the entity cache if it is enabled.
	*
	* @param cCartItem the c cart item
	*/
	public void cacheResult(CCartItem cCartItem);

	/**
	* Caches the c cart items in the entity cache if it is enabled.
	*
	* @param cCartItems the c cart items
	*/
	public void cacheResult(java.util.List<CCartItem> cCartItems);

	/**
	* Creates a new c cart item with the primary key. Does not add the c cart item to the database.
	*
	* @param CCartItemId the primary key for the new c cart item
	* @return the new c cart item
	*/
	public CCartItem create(long CCartItemId);

	/**
	* Removes the c cart item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CCartItemId the primary key of the c cart item
	* @return the c cart item that was removed
	* @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	*/
	public CCartItem remove(long CCartItemId) throws NoSuchCCartItemException;

	public CCartItem updateImpl(CCartItem cCartItem);

	/**
	* Returns the c cart item with the primary key or throws a {@link NoSuchCCartItemException} if it could not be found.
	*
	* @param CCartItemId the primary key of the c cart item
	* @return the c cart item
	* @throws NoSuchCCartItemException if a c cart item with the primary key could not be found
	*/
	public CCartItem findByPrimaryKey(long CCartItemId)
		throws NoSuchCCartItemException;

	/**
	* Returns the c cart item with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CCartItemId the primary key of the c cart item
	* @return the c cart item, or <code>null</code> if a c cart item with the primary key could not be found
	*/
	public CCartItem fetchByPrimaryKey(long CCartItemId);

	@Override
	public java.util.Map<java.io.Serializable, CCartItem> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the c cart items.
	*
	* @return the c cart items
	*/
	public java.util.List<CCartItem> findAll();

	/**
	* Returns a range of all the c cart items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @return the range of c cart items
	*/
	public java.util.List<CCartItem> findAll(int start, int end);

	/**
	* Returns an ordered range of all the c cart items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of c cart items
	*/
	public java.util.List<CCartItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator);

	/**
	* Returns an ordered range of all the c cart items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartItemModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c cart items
	* @param end the upper bound of the range of c cart items (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of c cart items
	*/
	public java.util.List<CCartItem> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCartItem> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the c cart items from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of c cart items.
	*
	* @return the number of c cart items
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}