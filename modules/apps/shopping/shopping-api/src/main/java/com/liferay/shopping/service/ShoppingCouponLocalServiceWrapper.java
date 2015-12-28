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

package com.liferay.shopping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ShoppingCouponLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCouponLocalService
 * @generated
 */
@ProviderType
public class ShoppingCouponLocalServiceWrapper
	implements ShoppingCouponLocalService,
		ServiceWrapper<ShoppingCouponLocalService> {
	public ShoppingCouponLocalServiceWrapper(
		ShoppingCouponLocalService shoppingCouponLocalService) {
		_shoppingCouponLocalService = shoppingCouponLocalService;
	}

	@Override
	public com.liferay.shopping.model.ShoppingCoupon addCoupon(long userId,
		java.lang.String code, boolean autoCode, java.lang.String name,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
		int endDateMinute, boolean neverExpire, boolean active,
		java.lang.String limitCategories, java.lang.String limitSkus,
		double minOrder, double discount, java.lang.String discountType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCouponLocalService.addCoupon(userId, code, autoCode,
			name, description, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, endDateMonth, endDateDay,
			endDateYear, endDateHour, endDateMinute, neverExpire, active,
			limitCategories, limitSkus, minOrder, discount, discountType,
			serviceContext);
	}

	/**
	* Adds the shopping coupon to the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingCoupon the shopping coupon
	* @return the shopping coupon that was added
	*/
	@Override
	public com.liferay.shopping.model.ShoppingCoupon addShoppingCoupon(
		com.liferay.shopping.model.ShoppingCoupon shoppingCoupon) {
		return _shoppingCouponLocalService.addShoppingCoupon(shoppingCoupon);
	}

	/**
	* Creates a new shopping coupon with the primary key. Does not add the shopping coupon to the database.
	*
	* @param couponId the primary key for the new shopping coupon
	* @return the new shopping coupon
	*/
	@Override
	public com.liferay.shopping.model.ShoppingCoupon createShoppingCoupon(
		long couponId) {
		return _shoppingCouponLocalService.createShoppingCoupon(couponId);
	}

	@Override
	public void deleteCoupon(com.liferay.shopping.model.ShoppingCoupon coupon) {
		_shoppingCouponLocalService.deleteCoupon(coupon);
	}

	@Override
	public void deleteCoupon(long couponId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingCouponLocalService.deleteCoupon(couponId);
	}

	@Override
	public void deleteCoupons(long groupId) {
		_shoppingCouponLocalService.deleteCoupons(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCouponLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the shopping coupon with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param couponId the primary key of the shopping coupon
	* @return the shopping coupon that was removed
	* @throws PortalException if a shopping coupon with the primary key could not be found
	*/
	@Override
	public com.liferay.shopping.model.ShoppingCoupon deleteShoppingCoupon(
		long couponId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCouponLocalService.deleteShoppingCoupon(couponId);
	}

	/**
	* Deletes the shopping coupon from the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingCoupon the shopping coupon
	* @return the shopping coupon that was removed
	*/
	@Override
	public com.liferay.shopping.model.ShoppingCoupon deleteShoppingCoupon(
		com.liferay.shopping.model.ShoppingCoupon shoppingCoupon) {
		return _shoppingCouponLocalService.deleteShoppingCoupon(shoppingCoupon);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _shoppingCouponLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _shoppingCouponLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingCouponModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _shoppingCouponLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingCouponModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _shoppingCouponLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _shoppingCouponLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _shoppingCouponLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.shopping.model.ShoppingCoupon fetchShoppingCoupon(
		long couponId) {
		return _shoppingCouponLocalService.fetchShoppingCoupon(couponId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _shoppingCouponLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.shopping.model.ShoppingCoupon getCoupon(
		java.lang.String code)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCouponLocalService.getCoupon(code);
	}

	@Override
	public com.liferay.shopping.model.ShoppingCoupon getCoupon(long couponId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCouponLocalService.getCoupon(couponId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _shoppingCouponLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _shoppingCouponLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCouponLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the shopping coupon with the primary key.
	*
	* @param couponId the primary key of the shopping coupon
	* @return the shopping coupon
	* @throws PortalException if a shopping coupon with the primary key could not be found
	*/
	@Override
	public com.liferay.shopping.model.ShoppingCoupon getShoppingCoupon(
		long couponId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCouponLocalService.getShoppingCoupon(couponId);
	}

	/**
	* Returns a range of all the shopping coupons.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingCouponModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping coupons
	* @param end the upper bound of the range of shopping coupons (not inclusive)
	* @return the range of shopping coupons
	*/
	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingCoupon> getShoppingCoupons(
		int start, int end) {
		return _shoppingCouponLocalService.getShoppingCoupons(start, end);
	}

	/**
	* Returns the number of shopping coupons.
	*
	* @return the number of shopping coupons
	*/
	@Override
	public int getShoppingCouponsCount() {
		return _shoppingCouponLocalService.getShoppingCouponsCount();
	}

	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingCoupon> search(
		long groupId, long companyId, java.lang.String code, boolean active,
		java.lang.String discountType, boolean andOperator, int start, int end) {
		return _shoppingCouponLocalService.search(groupId, companyId, code,
			active, discountType, andOperator, start, end);
	}

	@Override
	public int searchCount(long groupId, long companyId, java.lang.String code,
		boolean active, java.lang.String discountType, boolean andOperator) {
		return _shoppingCouponLocalService.searchCount(groupId, companyId,
			code, active, discountType, andOperator);
	}

	@Override
	public com.liferay.shopping.model.ShoppingCoupon updateCoupon(long userId,
		long couponId, java.lang.String name, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
		boolean neverExpire, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, double minOrder, double discount,
		java.lang.String discountType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingCouponLocalService.updateCoupon(userId, couponId, name,
			description, startDateMonth, startDateDay, startDateYear,
			startDateHour, startDateMinute, endDateMonth, endDateDay,
			endDateYear, endDateHour, endDateMinute, neverExpire, active,
			limitCategories, limitSkus, minOrder, discount, discountType,
			serviceContext);
	}

	/**
	* Updates the shopping coupon in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param shoppingCoupon the shopping coupon
	* @return the shopping coupon that was updated
	*/
	@Override
	public com.liferay.shopping.model.ShoppingCoupon updateShoppingCoupon(
		com.liferay.shopping.model.ShoppingCoupon shoppingCoupon) {
		return _shoppingCouponLocalService.updateShoppingCoupon(shoppingCoupon);
	}

	@Override
	public ShoppingCouponLocalService getWrappedService() {
		return _shoppingCouponLocalService;
	}

	@Override
	public void setWrappedService(
		ShoppingCouponLocalService shoppingCouponLocalService) {
		_shoppingCouponLocalService = shoppingCouponLocalService;
	}

	private ShoppingCouponLocalService _shoppingCouponLocalService;
}