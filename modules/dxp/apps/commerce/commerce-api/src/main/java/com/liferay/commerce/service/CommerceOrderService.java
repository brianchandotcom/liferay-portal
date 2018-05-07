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

import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.model.CommerceOrder;

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

import java.math.BigDecimal;

import java.util.List;

/**
 * Provides the remote service interface for CommerceOrder. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceOrderServiceUtil
 * @see com.liferay.commerce.service.base.CommerceOrderServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceOrderServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceOrder"}, service = CommerceOrderService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceOrderService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceOrderServiceUtil} to access the commerce order remote service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceOrderServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceOrder addOrganizationCommerceOrder(long groupId,
		long siteGroupId, long orderOrganizationId, long shippingAddressId,
		String purchaseOrderNumber) throws PortalException;

	public CommerceOrder addUserCommerceOrder(long groupId)
		throws PortalException;

	public CommerceOrder addUserCommerceOrder(long groupId, long orderUserId)
		throws PortalException;

	public CommerceOrder approveCommerceOrder(long commerceOrderId)
		throws PortalException;

	public CommerceOrder cancelCommerceOrderPayment(long commerceOrderId,
		ServiceContext serviceContext) throws PortalException;

	public CommerceOrder checkoutCommerceOrder(long commerceOrderId,
		CommerceContext commerceContext, ServiceContext serviceContext)
		throws PortalException;

	public CommerceOrder completeCommerceOrderPayment(long commerceOrderId,
		ServiceContext serviceContext) throws PortalException;

	public void deleteCommerceOrder(long commerceOrderId)
		throws PortalException;

	public CommerceOrder executeWorkflowTransition(long commerceOrderId,
		long workflowTaskId, String transitionName, String comment)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder fetchCommerceOrder(long commerceOrderId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder fetchCommerceOrder(long groupId, int orderStatus)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder fetchCommerceOrder(String uuid, long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int[] getAvailableOrderStatuses(long commerceOrderId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder getCommerceOrder(long commerceOrderId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceOrder getCommerceOrderByUuidAndGroupId(String uuid,
		long groupId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceOrder> getCommerceOrders(long groupId,
		long orderUserId, int start, int end,
		OrderByComparator<CommerceOrder> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceOrdersCount(long groupId, long orderUserId)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public void mergeGuestCommerceOrder(long guestCommerceOrderId,
		long userCommerceOrderId, CommerceContext commerceContext,
		ServiceContext serviceContext) throws PortalException;

	public CommerceOrder reorderCommerceOrder(long commerceOrderId,
		CommerceContext commerceContext) throws PortalException;

	public String startCommerceOrderPayment(long commerceOrderId,
		ServiceContext serviceContext) throws PortalException;

	public CommerceOrder submitCommerceOrder(long commerceOrderId)
		throws PortalException;

	public CommerceOrder updateBillingAddress(long commerceOrderId,
		String name, String description, String street1, String street2,
		String street3, String city, String zip, long commerceRegionId,
		long commerceCountryId, String phoneNumber,
		ServiceContext serviceContext) throws PortalException;

	public CommerceOrder updateCommerceOrder(long commerceOrderId,
		long billingAddressId, long shippingAddressId,
		long commercePaymentMethodId, long commerceShippingMethodId,
		String shippingOptionName, String purchaseOrderNumber,
		BigDecimal subtotal, BigDecimal shippingPrice, BigDecimal total,
		String advanceStatus) throws PortalException;

	public CommerceOrder updateOrderStatus(long commerceOrderId, int orderStatus)
		throws PortalException;

	public CommerceOrder updatePaymentStatus(long commerceOrderId,
		int paymentStatus, ServiceContext serviceContext)
		throws PortalException;

	public CommerceOrder updatePurchaseOrderNumber(long commerceOrderId,
		String purchaseOrderNumber) throws PortalException;

	public CommerceOrder updateShippingAddress(long commerceOrderId,
		String name, String description, String street1, String street2,
		String street3, String city, String zip, long commerceRegionId,
		long commerceCountryId, String phoneNumber,
		ServiceContext serviceContext) throws PortalException;

	public CommerceOrder updateUser(long commerceOrderId, long userId)
		throws PortalException;
}