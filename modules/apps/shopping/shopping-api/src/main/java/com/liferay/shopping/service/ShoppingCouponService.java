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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import com.liferay.shopping.model.ShoppingCoupon;

import java.util.List;

/**
 * Provides the remote service interface for ShoppingCoupon. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingCouponServiceUtil
 * @see com.liferay.shopping.service.base.ShoppingCouponServiceBaseImpl
 * @see com.liferay.shopping.service.impl.ShoppingCouponServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=shopping", "json.web.service.context.path=ShoppingCoupon"}, service = ShoppingCouponService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ShoppingCouponService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingCouponServiceUtil} to access the shopping coupon remote service. Add custom service methods to {@link com.liferay.shopping.service.impl.ShoppingCouponServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public ShoppingCoupon addCoupon(java.lang.String code, boolean autoCode,
		java.lang.String name, java.lang.String description,
		int startDateMonth, int startDateDay, int startDateYear,
		int startDateHour, int startDateMinute, int endDateMonth,
		int endDateDay, int endDateYear, int endDateHour, int endDateMinute,
		boolean neverExpire, boolean active, java.lang.String limitCategories,
		java.lang.String limitSkus, double minOrder, double discount,
		java.lang.String discountType, ServiceContext serviceContext)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingCoupon getCoupon(long groupId, long couponId)
		throws PortalException;

	public ShoppingCoupon updateCoupon(long couponId, java.lang.String name,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int endDateHour,
		int endDateMinute, boolean neverExpire, boolean active,
		java.lang.String limitCategories, java.lang.String limitSkus,
		double minOrder, double discount, java.lang.String discountType,
		ServiceContext serviceContext) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ShoppingCoupon> search(long groupId, long companyId,
		java.lang.String code, boolean active, java.lang.String discountType,
		boolean andOperator, int start, int end) throws PortalException;

	public void deleteCoupon(long groupId, long couponId)
		throws PortalException;
}