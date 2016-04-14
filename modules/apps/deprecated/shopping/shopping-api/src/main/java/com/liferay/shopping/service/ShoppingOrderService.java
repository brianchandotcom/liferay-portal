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

import com.liferay.shopping.model.ShoppingOrder;

/**
 * Provides the remote service interface for ShoppingOrder. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderServiceUtil
 * @see com.liferay.shopping.service.base.ShoppingOrderServiceBaseImpl
 * @see com.liferay.shopping.service.impl.ShoppingOrderServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=shopping", "json.web.service.context.path=ShoppingOrder"}, service = ShoppingOrderService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ShoppingOrderService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ShoppingOrderServiceUtil} to access the shopping order remote service. Add custom service methods to {@link com.liferay.shopping.service.impl.ShoppingOrderServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ShoppingOrder getOrder(long groupId, long orderId)
		throws PortalException;

	public ShoppingOrder updateOrder(long groupId, long orderId,
		java.lang.String billingFirstName, java.lang.String billingLastName,
		java.lang.String billingEmailAddress, java.lang.String billingCompany,
		java.lang.String billingStreet, java.lang.String billingCity,
		java.lang.String billingState, java.lang.String billingZip,
		java.lang.String billingCountry, java.lang.String billingPhone,
		boolean shipToBilling, java.lang.String shippingFirstName,
		java.lang.String shippingLastName,
		java.lang.String shippingEmailAddress,
		java.lang.String shippingCompany, java.lang.String shippingStreet,
		java.lang.String shippingCity, java.lang.String shippingState,
		java.lang.String shippingZip, java.lang.String shippingCountry,
		java.lang.String shippingPhone, java.lang.String ccName,
		java.lang.String ccType, java.lang.String ccNumber, int ccExpMonth,
		int ccExpYear, java.lang.String ccVerNumber, java.lang.String comments)
		throws PortalException;

	public ShoppingOrder updateOrder(long groupId, long orderId,
		java.lang.String ppTxnId, java.lang.String ppPaymentStatus,
		double ppPaymentGross, java.lang.String ppReceiverEmail,
		java.lang.String ppPayerEmail) throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public void completeOrder(long groupId, java.lang.String number,
		java.lang.String ppTxnId, java.lang.String ppPaymentStatus,
		double ppPaymentGross, java.lang.String ppReceiverEmail,
		java.lang.String ppPayerEmail, ServiceContext serviceContext)
		throws PortalException;

	public void deleteOrder(long groupId, long orderId)
		throws PortalException;

	public void sendEmail(long groupId, long orderId,
		java.lang.String emailType, ServiceContext serviceContext)
		throws PortalException;
}