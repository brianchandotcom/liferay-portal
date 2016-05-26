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

package com.liferay.shopping.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import com.liferay.shopping.exception.NoSuchCouponException;
import com.liferay.shopping.model.ShoppingCoupon;

/**
 * The persistence interface for the shopping coupon service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.shopping.service.persistence.impl.ShoppingCouponPersistenceImpl
 * @see ShoppingCouponUtil
 * @generated
 */
@ProviderType
public interface ShoppingCouponPersistence extends BasePersistence<ShoppingCoupon> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingCouponUtil} to access the shopping coupon persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the shopping coupons where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping coupons
	*/
	public java.util.List<ShoppingCoupon> findByGroupId(long groupId);

	/**
	* Returns a range of all the shopping coupons where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCouponModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping coupons
	* @param end the upper bound of the range of shopping coupons (not inclusive)
	* @return the range of matching shopping coupons
	*/
	public java.util.List<ShoppingCoupon> findByGroupId(long groupId,
		int start, int end);

	/**
	* Returns an ordered range of all the shopping coupons where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCouponModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping coupons
	* @param end the upper bound of the range of shopping coupons (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping coupons
	*/
	public java.util.List<ShoppingCoupon> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCoupon> orderByComparator);

	/**
	* Returns an ordered range of all the shopping coupons where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCouponModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping coupons
	* @param end the upper bound of the range of shopping coupons (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching shopping coupons
	*/
	public java.util.List<ShoppingCoupon> findByGroupId(long groupId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCoupon> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first shopping coupon in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping coupon
	* @throws NoSuchCouponException if a matching shopping coupon could not be found
	*/
	public ShoppingCoupon findByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCoupon> orderByComparator)
		throws NoSuchCouponException;

	/**
	* Returns the first shopping coupon in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping coupon, or <code>null</code> if a matching shopping coupon could not be found
	*/
	public ShoppingCoupon fetchByGroupId_First(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCoupon> orderByComparator);

	/**
	* Returns the last shopping coupon in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping coupon
	* @throws NoSuchCouponException if a matching shopping coupon could not be found
	*/
	public ShoppingCoupon findByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCoupon> orderByComparator)
		throws NoSuchCouponException;

	/**
	* Returns the last shopping coupon in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping coupon, or <code>null</code> if a matching shopping coupon could not be found
	*/
	public ShoppingCoupon fetchByGroupId_Last(long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCoupon> orderByComparator);

	/**
	* Returns the shopping coupons before and after the current shopping coupon in the ordered set where groupId = &#63;.
	*
	* @param couponId the primary key of the current shopping coupon
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping coupon
	* @throws NoSuchCouponException if a shopping coupon with the primary key could not be found
	*/
	public ShoppingCoupon[] findByGroupId_PrevAndNext(long couponId,
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCoupon> orderByComparator)
		throws NoSuchCouponException;

	/**
	* Removes all the shopping coupons where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public void removeByGroupId(long groupId);

	/**
	* Returns the number of shopping coupons where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping coupons
	*/
	public int countByGroupId(long groupId);

	/**
	* Returns the shopping coupon where code = &#63; or throws a {@link NoSuchCouponException} if it could not be found.
	*
	* @param code the code
	* @return the matching shopping coupon
	* @throws NoSuchCouponException if a matching shopping coupon could not be found
	*/
	public ShoppingCoupon findByCode(java.lang.String code)
		throws NoSuchCouponException;

	/**
	* Returns the shopping coupon where code = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param code the code
	* @return the matching shopping coupon, or <code>null</code> if a matching shopping coupon could not be found
	*/
	public ShoppingCoupon fetchByCode(java.lang.String code);

	/**
	* Returns the shopping coupon where code = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param code the code
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching shopping coupon, or <code>null</code> if a matching shopping coupon could not be found
	*/
	public ShoppingCoupon fetchByCode(java.lang.String code,
		boolean retrieveFromCache);

	/**
	* Removes the shopping coupon where code = &#63; from the database.
	*
	* @param code the code
	* @return the shopping coupon that was removed
	*/
	public ShoppingCoupon removeByCode(java.lang.String code)
		throws NoSuchCouponException;

	/**
	* Returns the number of shopping coupons where code = &#63;.
	*
	* @param code the code
	* @return the number of matching shopping coupons
	*/
	public int countByCode(java.lang.String code);

	/**
	* Caches the shopping coupon in the entity cache if it is enabled.
	*
	* @param shoppingCoupon the shopping coupon
	*/
	public void cacheResult(ShoppingCoupon shoppingCoupon);

	/**
	* Caches the shopping coupons in the entity cache if it is enabled.
	*
	* @param shoppingCoupons the shopping coupons
	*/
	public void cacheResult(java.util.List<ShoppingCoupon> shoppingCoupons);

	/**
	* Creates a new shopping coupon with the primary key. Does not add the shopping coupon to the database.
	*
	* @param couponId the primary key for the new shopping coupon
	* @return the new shopping coupon
	*/
	public ShoppingCoupon create(long couponId);

	/**
	* Removes the shopping coupon with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param couponId the primary key of the shopping coupon
	* @return the shopping coupon that was removed
	* @throws NoSuchCouponException if a shopping coupon with the primary key could not be found
	*/
	public ShoppingCoupon remove(long couponId) throws NoSuchCouponException;

	public ShoppingCoupon updateImpl(ShoppingCoupon shoppingCoupon);

	/**
	* Returns the shopping coupon with the primary key or throws a {@link NoSuchCouponException} if it could not be found.
	*
	* @param couponId the primary key of the shopping coupon
	* @return the shopping coupon
	* @throws NoSuchCouponException if a shopping coupon with the primary key could not be found
	*/
	public ShoppingCoupon findByPrimaryKey(long couponId)
		throws NoSuchCouponException;

	/**
	* Returns the shopping coupon with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param couponId the primary key of the shopping coupon
	* @return the shopping coupon, or <code>null</code> if a shopping coupon with the primary key could not be found
	*/
	public ShoppingCoupon fetchByPrimaryKey(long couponId);

	@Override
	public java.util.Map<java.io.Serializable, ShoppingCoupon> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the shopping coupons.
	*
	* @return the shopping coupons
	*/
	public java.util.List<ShoppingCoupon> findAll();

	/**
	* Returns a range of all the shopping coupons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCouponModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping coupons
	* @param end the upper bound of the range of shopping coupons (not inclusive)
	* @return the range of shopping coupons
	*/
	public java.util.List<ShoppingCoupon> findAll(int start, int end);

	/**
	* Returns an ordered range of all the shopping coupons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCouponModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping coupons
	* @param end the upper bound of the range of shopping coupons (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shopping coupons
	*/
	public java.util.List<ShoppingCoupon> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCoupon> orderByComparator);

	/**
	* Returns an ordered range of all the shopping coupons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ShoppingCouponModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping coupons
	* @param end the upper bound of the range of shopping coupons (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of shopping coupons
	*/
	public java.util.List<ShoppingCoupon> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ShoppingCoupon> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the shopping coupons from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of shopping coupons.
	*
	* @return the number of shopping coupons
	*/
	public int countAll();

	@Override
	public java.util.Set<java.lang.String> getBadColumnNames();
}