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

import com.liferay.commerce.model.CommerceOrder;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * Provides the local service interface for CommercePriceCalculation. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceCalculationLocalServiceUtil
 * @see com.liferay.commerce.service.base.CommercePriceCalculationLocalServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommercePriceCalculationLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommercePriceCalculationLocalService extends BaseLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePriceCalculationLocalServiceUtil} to access the commerce price calculation local service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommercePriceCalculationLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public java.lang.String formatPrice(long groupId, double price)
		throws PortalException;

	public java.lang.String formatPriceWithCurrency(long commerceCurrencyId,
		double price) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public double getFinalPrice(long groupId, long userId, long cpInstanceId,
		int quantity) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public double getFinalPrice(long groupId, long commerceCurrencyId,
		long userId, long cpInstanceId, int quantity) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getFormattedFinalPrice(long groupId, long userId,
		long cpInstanceId, int quantity) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getFormattedFinalPrice(long groupId,
		long commerceCurrencyId, long userId, long cpInstanceId, int quantity)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.lang.String getFormattedOrderSubtotal(
		CommerceOrder commerceOrder) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public double getOrderSubtotal(CommerceOrder commerceOrder)
		throws PortalException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public double getUnitPrice(long groupId, long userId, long cpInstanceId,
		int quantity) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public double getUnitPrice(long groupId, long commerceCurrencyId,
		long userId, long cpInstanceId, int quantity) throws PortalException;
}