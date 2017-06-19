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

import com.liferay.commerce.cart.exception.NoSuchCCartException;
import com.liferay.commerce.cart.model.CCart;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the c cart service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see com.liferay.commerce.cart.service.persistence.impl.CCartPersistenceImpl
 * @see CCartUtil
 * @generated
 */
@ProviderType
public interface CCartPersistence extends BasePersistence<CCart> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CCartUtil} to access the c cart persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the c carts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching c carts
	*/
	public java.util.List<CCart> findByUuid(java.lang.String uuid);

	/**
	* Returns a range of all the c carts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @return the range of matching c carts
	*/
	public java.util.List<CCart> findByUuid(java.lang.String uuid, int start,
		int end);

	/**
	* Returns an ordered range of all the c carts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c carts
	*/
	public java.util.List<CCart> findByUuid(java.lang.String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator);

	/**
	* Returns an ordered range of all the c carts where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c carts
	*/
	public java.util.List<CCart> findByUuid(java.lang.String uuid, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first c cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart
	* @throws NoSuchCCartException if a matching c cart could not be found
	*/
	public CCart findByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator)
		throws NoSuchCCartException;

	/**
	* Returns the first c cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public CCart fetchByUuid_First(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator);

	/**
	* Returns the last c cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart
	* @throws NoSuchCCartException if a matching c cart could not be found
	*/
	public CCart findByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator)
		throws NoSuchCCartException;

	/**
	* Returns the last c cart in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public CCart fetchByUuid_Last(java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator);

	/**
	* Returns the c carts before and after the current c cart in the ordered set where uuid = &#63;.
	*
	* @param CCartId the primary key of the current c cart
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c cart
	* @throws NoSuchCCartException if a c cart with the primary key could not be found
	*/
	public CCart[] findByUuid_PrevAndNext(long CCartId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator)
		throws NoSuchCCartException;

	/**
	* Removes all the c carts where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of c carts where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching c carts
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns the c cart where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCCartException} if it could not be found.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching c cart
	* @throws NoSuchCCartException if a matching c cart could not be found
	*/
	public CCart findByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCCartException;

	/**
	* Returns the c cart where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public CCart fetchByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns the c cart where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public CCart fetchByUUID_G(java.lang.String uuid, long groupId,
		boolean retrieveFromCache);

	/**
	* Removes the c cart where uuid = &#63; and groupId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the c cart that was removed
	*/
	public CCart removeByUUID_G(java.lang.String uuid, long groupId)
		throws NoSuchCCartException;

	/**
	* Returns the number of c carts where uuid = &#63; and groupId = &#63;.
	*
	* @param uuid the uuid
	* @param groupId the group ID
	* @return the number of matching c carts
	*/
	public int countByUUID_G(java.lang.String uuid, long groupId);

	/**
	* Returns all the c carts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching c carts
	*/
	public java.util.List<CCart> findByUuid_C(java.lang.String uuid,
		long companyId);

	/**
	* Returns a range of all the c carts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @return the range of matching c carts
	*/
	public java.util.List<CCart> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the c carts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c carts
	*/
	public java.util.List<CCart> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator);

	/**
	* Returns an ordered range of all the c carts where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c carts
	*/
	public java.util.List<CCart> findByUuid_C(java.lang.String uuid,
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first c cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart
	* @throws NoSuchCCartException if a matching c cart could not be found
	*/
	public CCart findByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator)
		throws NoSuchCCartException;

	/**
	* Returns the first c cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public CCart fetchByUuid_C_First(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator);

	/**
	* Returns the last c cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart
	* @throws NoSuchCCartException if a matching c cart could not be found
	*/
	public CCart findByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator)
		throws NoSuchCCartException;

	/**
	* Returns the last c cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public CCart fetchByUuid_C_Last(java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator);

	/**
	* Returns the c carts before and after the current c cart in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param CCartId the primary key of the current c cart
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c cart
	* @throws NoSuchCCartException if a c cart with the primary key could not be found
	*/
	public CCart[] findByUuid_C_PrevAndNext(long CCartId,
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator)
		throws NoSuchCCartException;

	/**
	* Removes all the c carts where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of c carts where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching c carts
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the c carts where type = &#63;.
	*
	* @param type the type
	* @return the matching c carts
	*/
	public java.util.List<CCart> findByType(int type);

	/**
	* Returns a range of all the c carts where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @return the range of matching c carts
	*/
	public java.util.List<CCart> findByType(int type, int start, int end);

	/**
	* Returns an ordered range of all the c carts where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching c carts
	*/
	public java.util.List<CCart> findByType(int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator);

	/**
	* Returns an ordered range of all the c carts where type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param type the type
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching c carts
	*/
	public java.util.List<CCart> findByType(int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first c cart in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart
	* @throws NoSuchCCartException if a matching c cart could not be found
	*/
	public CCart findByType_First(int type,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator)
		throws NoSuchCCartException;

	/**
	* Returns the first c cart in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public CCart fetchByType_First(int type,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator);

	/**
	* Returns the last c cart in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart
	* @throws NoSuchCCartException if a matching c cart could not be found
	*/
	public CCart findByType_Last(int type,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator)
		throws NoSuchCCartException;

	/**
	* Returns the last c cart in the ordered set where type = &#63;.
	*
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching c cart, or <code>null</code> if a matching c cart could not be found
	*/
	public CCart fetchByType_Last(int type,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator);

	/**
	* Returns the c carts before and after the current c cart in the ordered set where type = &#63;.
	*
	* @param CCartId the primary key of the current c cart
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next c cart
	* @throws NoSuchCCartException if a c cart with the primary key could not be found
	*/
	public CCart[] findByType_PrevAndNext(long CCartId, int type,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator)
		throws NoSuchCCartException;

	/**
	* Removes all the c carts where type = &#63; from the database.
	*
	* @param type the type
	*/
	public void removeByType(int type);

	/**
	* Returns the number of c carts where type = &#63;.
	*
	* @param type the type
	* @return the number of matching c carts
	*/
	public int countByType(int type);

	/**
	* Caches the c cart in the entity cache if it is enabled.
	*
	* @param cCart the c cart
	*/
	public void cacheResult(CCart cCart);

	/**
	* Caches the c carts in the entity cache if it is enabled.
	*
	* @param cCarts the c carts
	*/
	public void cacheResult(java.util.List<CCart> cCarts);

	/**
	* Creates a new c cart with the primary key. Does not add the c cart to the database.
	*
	* @param CCartId the primary key for the new c cart
	* @return the new c cart
	*/
	public CCart create(long CCartId);

	/**
	* Removes the c cart with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param CCartId the primary key of the c cart
	* @return the c cart that was removed
	* @throws NoSuchCCartException if a c cart with the primary key could not be found
	*/
	public CCart remove(long CCartId) throws NoSuchCCartException;

	public CCart updateImpl(CCart cCart);

	/**
	* Returns the c cart with the primary key or throws a {@link NoSuchCCartException} if it could not be found.
	*
	* @param CCartId the primary key of the c cart
	* @return the c cart
	* @throws NoSuchCCartException if a c cart with the primary key could not be found
	*/
	public CCart findByPrimaryKey(long CCartId) throws NoSuchCCartException;

	/**
	* Returns the c cart with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param CCartId the primary key of the c cart
	* @return the c cart, or <code>null</code> if a c cart with the primary key could not be found
	*/
	public CCart fetchByPrimaryKey(long CCartId);

	@Override
	public java.util.Map<java.io.Serializable, CCart> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the c carts.
	*
	* @return the c carts
	*/
	public java.util.List<CCart> findAll();

	/**
	* Returns a range of all the c carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @return the range of c carts
	*/
	public java.util.List<CCart> findAll(int start, int end);

	/**
	* Returns an ordered range of all the c carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of c carts
	*/
	public java.util.List<CCart> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator);

	/**
	* Returns an ordered range of all the c carts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CCartModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of c carts
	* @param end the upper bound of the range of c carts (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of c carts
	*/
	public java.util.List<CCart> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CCart> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the c carts from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of c carts.
	*
	* @return the number of c carts
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}