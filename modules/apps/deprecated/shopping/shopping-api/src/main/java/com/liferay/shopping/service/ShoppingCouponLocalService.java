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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.shopping.model.ShoppingCoupon;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for ShoppingCoupon. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCouponLocalServiceUtil
 * @see com.liferay.shopping.service.base.ShoppingCouponLocalServiceBaseImpl
 * @see com.liferay.shopping.service.impl.ShoppingCouponLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ShoppingCouponLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingCouponLocalServiceUtil} to access the shopping coupon local service. Add custom service methods to {@link com.liferay.shopping.service.impl.ShoppingCouponLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	public DynamicQuery dynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	public ShoppingCoupon addCoupon(long userId, java.lang.String code,
		boolean autoCode, java.lang.String name, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
		boolean neverExpire, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, double minOrder, double discount,
		java.lang.String discountType, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Adds the shopping coupon to the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingCoupon the shopping coupon
	* @return the shopping coupon that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ShoppingCoupon addShoppingCoupon(ShoppingCoupon shoppingCoupon);

	/**
	* Creates a new shopping coupon with the primary key. Does not add the shopping coupon to the database.
	*
	* @param couponId the primary key for the new shopping coupon
	* @return the new shopping coupon
	*/
	public ShoppingCoupon createShoppingCoupon(long couponId);

	/**
	* Deletes the shopping coupon from the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingCoupon the shopping coupon
	* @return the shopping coupon that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public ShoppingCoupon deleteShoppingCoupon(ShoppingCoupon shoppingCoupon);

	/**
	* Deletes the shopping coupon with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param couponId the primary key of the shopping coupon
	* @return the shopping coupon that was removed
	* @throws PortalException if a shopping coupon with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public ShoppingCoupon deleteShoppingCoupon(long couponId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingCoupon fetchShoppingCoupon(long couponId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingCoupon getCoupon(java.lang.String code)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingCoupon getCoupon(long couponId) throws PortalException;

	/**
	* Returns the shopping coupon with the primary key.
	*
	* @param couponId the primary key of the shopping coupon
	* @return the shopping coupon
	* @throws PortalException if a shopping coupon with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingCoupon getShoppingCoupon(long couponId)
		throws PortalException;

	public ShoppingCoupon updateCoupon(long userId, long couponId,
		java.lang.String name, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
		boolean neverExpire, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, double minOrder, double discount,
		java.lang.String discountType, ServiceContext serviceContext)
		throws PortalException;

	/**
	* Updates the shopping coupon in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param shoppingCoupon the shopping coupon
	* @return the shopping coupon that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ShoppingCoupon updateShoppingCoupon(ShoppingCoupon shoppingCoupon);

	/**
	* Returns the number of shopping coupons.
	*
	* @return the number of shopping coupons
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getShoppingCouponsCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long groupId, long companyId, java.lang.String code,
		boolean active, java.lang.String discountType, boolean andOperator);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

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
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingCoupon> getShoppingCoupons(int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingCoupon> search(long groupId, long companyId,
		java.lang.String code, boolean active, java.lang.String discountType,
		boolean andOperator, int start, int end);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	public void deleteCoupon(ShoppingCoupon coupon);

	public void deleteCoupon(long couponId) throws PortalException;

	public void deleteCoupons(long groupId);
}