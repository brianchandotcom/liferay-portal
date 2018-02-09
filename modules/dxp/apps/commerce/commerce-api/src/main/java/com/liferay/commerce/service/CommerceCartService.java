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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceCart;

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
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service interface for CommerceCart. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCartServiceUtil
 * @see com.liferay.commerce.service.base.CommerceCartServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceCartServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceCart"}, service = CommerceCartService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceCartService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceCartServiceUtil} to access the commerce cart remote service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceCartServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceCart addCommerceCart(java.lang.String name,
		boolean defaultCart, ServiceContext serviceContext)
		throws PortalException;

	public void deleteCommerceCart(long commerceCartId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceCart fetchCommerceCart(long commerceCartId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceCart fetchCommerceCart(java.lang.String uuid, long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceCart fetchDefaultCommerceCart(long groupId, long userId,
		boolean defaultCart);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceCart getCommerceCart(long commerceCartId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceCart> getCommerceCarts(long groupId, int start,
		int end, OrderByComparator<CommerceCart> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceCartsCount(long groupId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public void mergeGuestCommerceCart(long guestCommerceCartId,
		long userCommerceCartId, ServiceContext serviceContext)
		throws PortalException;

	public CommerceCart updateCommerceCart(long commerceCartId,
		long billingAddressId, long shippingAddressId,
		long commercePaymentMethodId, long commerceShippingMethodId,
		java.lang.String shippingOptionName, double shippingPrice)
		throws PortalException;

	public CommerceCart updateCommerceCart(long commerceCartId,
		java.lang.String name, boolean defaultCart) throws PortalException;

	public CommerceCart updateUser(long commerceCartId, long userId)
		throws PortalException;
}