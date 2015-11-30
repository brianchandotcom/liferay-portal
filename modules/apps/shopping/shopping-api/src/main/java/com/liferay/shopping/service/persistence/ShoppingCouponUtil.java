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

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import com.liferay.shopping.model.ShoppingCoupon;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the shopping coupon service. This utility wraps {@link com.liferay.shopping.service.persistence.impl.ShoppingCouponPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCouponPersistence
 * @see com.liferay.shopping.service.persistence.impl.ShoppingCouponPersistenceImpl
 * @generated
 */
@ProviderType
public class ShoppingCouponUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(ShoppingCoupon shoppingCoupon) {
		getPersistence().clearCache(shoppingCoupon);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ShoppingCoupon> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ShoppingCoupon> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ShoppingCoupon> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ShoppingCoupon> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static ShoppingCoupon update(ShoppingCoupon shoppingCoupon) {
		return getPersistence().update(shoppingCoupon);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static ShoppingCoupon update(ShoppingCoupon shoppingCoupon,
		ServiceContext serviceContext) {
		return getPersistence().update(shoppingCoupon, serviceContext);
	}

	/**
	* Returns all the shopping coupons where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the matching shopping coupons
	*/
	public static List<ShoppingCoupon> findByGroupId(long groupId) {
		return getPersistence().findByGroupId(groupId);
	}

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
	public static List<ShoppingCoupon> findByGroupId(long groupId, int start,
		int end) {
		return getPersistence().findByGroupId(groupId, start, end);
	}

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
	public static List<ShoppingCoupon> findByGroupId(long groupId, int start,
		int end, OrderByComparator<ShoppingCoupon> orderByComparator) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator);
	}

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
	public static List<ShoppingCoupon> findByGroupId(long groupId, int start,
		int end, OrderByComparator<ShoppingCoupon> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByGroupId(groupId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first shopping coupon in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping coupon
	* @throws com.liferay.shopping.NoSuchCouponException if a matching shopping coupon could not be found
	*/
	public static ShoppingCoupon findByGroupId_First(long groupId,
		OrderByComparator<ShoppingCoupon> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchCouponException {
		return getPersistence().findByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the first shopping coupon in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching shopping coupon, or <code>null</code> if a matching shopping coupon could not be found
	*/
	public static ShoppingCoupon fetchByGroupId_First(long groupId,
		OrderByComparator<ShoppingCoupon> orderByComparator) {
		return getPersistence().fetchByGroupId_First(groupId, orderByComparator);
	}

	/**
	* Returns the last shopping coupon in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping coupon
	* @throws com.liferay.shopping.NoSuchCouponException if a matching shopping coupon could not be found
	*/
	public static ShoppingCoupon findByGroupId_Last(long groupId,
		OrderByComparator<ShoppingCoupon> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchCouponException {
		return getPersistence().findByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the last shopping coupon in the ordered set where groupId = &#63;.
	*
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching shopping coupon, or <code>null</code> if a matching shopping coupon could not be found
	*/
	public static ShoppingCoupon fetchByGroupId_Last(long groupId,
		OrderByComparator<ShoppingCoupon> orderByComparator) {
		return getPersistence().fetchByGroupId_Last(groupId, orderByComparator);
	}

	/**
	* Returns the shopping coupons before and after the current shopping coupon in the ordered set where groupId = &#63;.
	*
	* @param couponId the primary key of the current shopping coupon
	* @param groupId the group ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next shopping coupon
	* @throws com.liferay.shopping.NoSuchCouponException if a shopping coupon with the primary key could not be found
	*/
	public static ShoppingCoupon[] findByGroupId_PrevAndNext(long couponId,
		long groupId, OrderByComparator<ShoppingCoupon> orderByComparator)
		throws com.liferay.shopping.exception.NoSuchCouponException {
		return getPersistence()
				   .findByGroupId_PrevAndNext(couponId, groupId,
			orderByComparator);
	}

	/**
	* Removes all the shopping coupons where groupId = &#63; from the database.
	*
	* @param groupId the group ID
	*/
	public static void removeByGroupId(long groupId) {
		getPersistence().removeByGroupId(groupId);
	}

	/**
	* Returns the number of shopping coupons where groupId = &#63;.
	*
	* @param groupId the group ID
	* @return the number of matching shopping coupons
	*/
	public static int countByGroupId(long groupId) {
		return getPersistence().countByGroupId(groupId);
	}

	/**
	* Returns the shopping coupon where code = &#63; or throws a {@link com.liferay.shopping.NoSuchCouponException} if it could not be found.
	*
	* @param code the code
	* @return the matching shopping coupon
	* @throws com.liferay.shopping.NoSuchCouponException if a matching shopping coupon could not be found
	*/
	public static ShoppingCoupon findByCode(java.lang.String code)
		throws com.liferay.shopping.exception.NoSuchCouponException {
		return getPersistence().findByCode(code);
	}

	/**
	* Returns the shopping coupon where code = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param code the code
	* @return the matching shopping coupon, or <code>null</code> if a matching shopping coupon could not be found
	*/
	public static ShoppingCoupon fetchByCode(java.lang.String code) {
		return getPersistence().fetchByCode(code);
	}

	/**
	* Returns the shopping coupon where code = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param code the code
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching shopping coupon, or <code>null</code> if a matching shopping coupon could not be found
	*/
	public static ShoppingCoupon fetchByCode(java.lang.String code,
		boolean retrieveFromCache) {
		return getPersistence().fetchByCode(code, retrieveFromCache);
	}

	/**
	* Removes the shopping coupon where code = &#63; from the database.
	*
	* @param code the code
	* @return the shopping coupon that was removed
	*/
	public static ShoppingCoupon removeByCode(java.lang.String code)
		throws com.liferay.shopping.exception.NoSuchCouponException {
		return getPersistence().removeByCode(code);
	}

	/**
	* Returns the number of shopping coupons where code = &#63;.
	*
	* @param code the code
	* @return the number of matching shopping coupons
	*/
	public static int countByCode(java.lang.String code) {
		return getPersistence().countByCode(code);
	}

	/**
	* Caches the shopping coupon in the entity cache if it is enabled.
	*
	* @param shoppingCoupon the shopping coupon
	*/
	public static void cacheResult(ShoppingCoupon shoppingCoupon) {
		getPersistence().cacheResult(shoppingCoupon);
	}

	/**
	* Caches the shopping coupons in the entity cache if it is enabled.
	*
	* @param shoppingCoupons the shopping coupons
	*/
	public static void cacheResult(List<ShoppingCoupon> shoppingCoupons) {
		getPersistence().cacheResult(shoppingCoupons);
	}

	/**
	* Creates a new shopping coupon with the primary key. Does not add the shopping coupon to the database.
	*
	* @param couponId the primary key for the new shopping coupon
	* @return the new shopping coupon
	*/
	public static ShoppingCoupon create(long couponId) {
		return getPersistence().create(couponId);
	}

	/**
	* Removes the shopping coupon with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param couponId the primary key of the shopping coupon
	* @return the shopping coupon that was removed
	* @throws com.liferay.shopping.NoSuchCouponException if a shopping coupon with the primary key could not be found
	*/
	public static ShoppingCoupon remove(long couponId)
		throws com.liferay.shopping.exception.NoSuchCouponException {
		return getPersistence().remove(couponId);
	}

	public static ShoppingCoupon updateImpl(ShoppingCoupon shoppingCoupon) {
		return getPersistence().updateImpl(shoppingCoupon);
	}

	/**
	* Returns the shopping coupon with the primary key or throws a {@link com.liferay.shopping.NoSuchCouponException} if it could not be found.
	*
	* @param couponId the primary key of the shopping coupon
	* @return the shopping coupon
	* @throws com.liferay.shopping.NoSuchCouponException if a shopping coupon with the primary key could not be found
	*/
	public static ShoppingCoupon findByPrimaryKey(long couponId)
		throws com.liferay.shopping.exception.NoSuchCouponException {
		return getPersistence().findByPrimaryKey(couponId);
	}

	/**
	* Returns the shopping coupon with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param couponId the primary key of the shopping coupon
	* @return the shopping coupon, or <code>null</code> if a shopping coupon with the primary key could not be found
	*/
	public static ShoppingCoupon fetchByPrimaryKey(long couponId) {
		return getPersistence().fetchByPrimaryKey(couponId);
	}

	public static java.util.Map<java.io.Serializable, ShoppingCoupon> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the shopping coupons.
	*
	* @return the shopping coupons
	*/
	public static List<ShoppingCoupon> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<ShoppingCoupon> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<ShoppingCoupon> findAll(int start, int end,
		OrderByComparator<ShoppingCoupon> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<ShoppingCoupon> findAll(int start, int end,
		OrderByComparator<ShoppingCoupon> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the shopping coupons from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of shopping coupons.
	*
	* @return the number of shopping coupons
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<java.lang.String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static ShoppingCouponPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(ShoppingCouponPersistence persistence) {
	}

	private static ServiceTracker<ShoppingCouponPersistence, ShoppingCouponPersistence> _serviceTracker =
		ServiceTrackerFactory.open(ShoppingCouponPersistence.class);
}