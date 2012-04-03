/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.liferay.portlet.shopping.model.ShoppingCoupon;

/**
 * The persistence interface for the shopping coupon service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCouponPersistenceImpl
 * @see ShoppingCouponUtil
 * @generated
 */
public interface ShoppingCouponPersistence extends BasePersistence<ShoppingCoupon> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingCouponUtil} to access the shopping coupon persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the shopping coupon in the entity cache if it is enabled.
	*
	* @param shoppingCoupon the shopping coupon
	*/
	public void cacheResult(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon);

	/**
	* Caches the shopping coupons in the entity cache if it is enabled.
	*
	* @param shoppingCoupons the shopping coupons
	*/
	public void cacheResult(
		java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> shoppingCoupons);

	/**
	* Creates a new shopping coupon with the primary key. Does not add the shopping coupon to the database.
	*
	* @param couponId the primary key for the new shopping coupon
	* @return the new shopping coupon
	*/
	public com.liferay.portlet.shopping.model.ShoppingCoupon create(
		long couponId);

	/**
	* Removes the shopping coupon with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param couponId the primary key of the shopping coupon
	* @return the shopping coupon that was removed
	* @throws com.liferay.portlet.shopping.NoSuchCouponException if a shopping coupon with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCoupon remove(
		long couponId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCouponException;

	public com.liferay.portlet.shopping.model.ShoppingCoupon updateImpl(
		com.liferay.portlet.shopping.model.ShoppingCoupon shoppingCoupon,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the shopping coupon with the primary key or throws a {@link com.liferay.portlet.shopping.NoSuchCouponException} if it could not be found.
	*
	* @param couponId the primary key of the shopping coupon
	* @return the shopping coupon
	* @throws com.liferay.portlet.shopping.NoSuchCouponException if a shopping coupon with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCoupon findByPrimaryKey(
		long couponId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCouponException;

	/**
	* Returns the shopping coupon with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param couponId the primary key of the shopping coupon
	* @return the shopping coupon, or <code>null</code> if a shopping coupon with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCoupon fetchByPrimaryKey(
		long couponId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the shopping coupons where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping coupons
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> findByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the shopping coupons where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping coupons
	* @param end the upper bound of the range of shopping coupons (not inclusive)
	* @return the range of matching shopping coupons
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> findByGroupId(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the shopping coupons where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param start the lower bound of the range of shopping coupons
	* @param end the upper bound of the range of shopping coupons (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching shopping coupons
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> findByGroupId(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first shopping coupon in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping coupon
	* @throws com.liferay.portlet.shopping.NoSuchCouponException if a matching shopping coupon could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCoupon findByGroupId_First(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCouponException;

	/**
	* Returns the last shopping coupon in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping coupon
	* @throws com.liferay.portlet.shopping.NoSuchCouponException if a matching shopping coupon could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCoupon findByGroupId_Last(
		long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCouponException;

	/**
	* Returns the shopping coupons before and after the current shopping coupon in the ordered set where groupId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param couponId the primary key of the current shopping coupon
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping coupon
	* @throws com.liferay.portlet.shopping.NoSuchCouponException if a shopping coupon with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCoupon[] findByGroupId_PrevAndNext(
		long couponId, long groupId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCouponException;

	/**
	* Returns the shopping coupon where code = &#63; or throws a {@link com.liferay.portlet.shopping.NoSuchCouponException} if it could not be found.
	*
	* @param code the code
	* @return the matching shopping coupon
	* @throws com.liferay.portlet.shopping.NoSuchCouponException if a matching shopping coupon could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCoupon findByCode(
		java.lang.String code)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCouponException;

	/**
	* Returns the shopping coupon where code = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param code the code
	* @return the matching shopping coupon, or <code>null</code> if a matching shopping coupon could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCoupon fetchByCode(
		java.lang.String code)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the shopping coupon where code = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param code the code
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching shopping coupon, or <code>null</code> if a matching shopping coupon could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCoupon fetchByCode(
		java.lang.String code, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the shopping coupons.
	*
	* @return the shopping coupons
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the shopping coupons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of shopping coupons
	* @param end the upper bound of the range of shopping coupons (not inclusive)
	* @return the range of shopping coupons
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the shopping coupons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of shopping coupons
	* @param end the upper bound of the range of shopping coupons (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of shopping coupons
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.shopping.model.ShoppingCoupon> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the shopping coupons where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the shopping coupon where code = &#63; from the database.
	*
	* @param code the code
	* @return the shopping coupon that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.shopping.model.ShoppingCoupon removeByCode(
		java.lang.String code)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.shopping.NoSuchCouponException;

	/**
	* Removes all the shopping coupons from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of shopping coupons where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping coupons
	* @throws SystemException if a system exception occurred
	*/
	public int countByGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of shopping coupons where code = &#63;.
	*
	* @param code the code
	* @return the number of matching shopping coupons
	* @throws SystemException if a system exception occurred
	*/
	public int countByCode(java.lang.String code)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of shopping coupons.
	*
	* @return the number of shopping coupons
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}