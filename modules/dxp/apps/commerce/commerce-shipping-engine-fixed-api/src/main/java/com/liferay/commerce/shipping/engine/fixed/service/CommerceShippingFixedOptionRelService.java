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

package com.liferay.commerce.shipping.engine.fixed.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;

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
 * Provides the remote service interface for CommerceShippingFixedOptionRel. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRelServiceUtil
 * @see com.liferay.commerce.shipping.engine.fixed.service.base.CommerceShippingFixedOptionRelServiceBaseImpl
 * @see com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionRelServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceShippingFixedOptionRel"}, service = CommerceShippingFixedOptionRelService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceShippingFixedOptionRelService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceShippingFixedOptionRelServiceUtil} to access the commerce shipping fixed option rel remote service. Add custom service methods to {@link com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionRelServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel(
		long commerceShippingMethodId, long commerceShippingFixedOptionId,
		long commerceWarehouseId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double weightFrom,
		double weightTo, BigDecimal fixedPrice, BigDecimal rateUnitWeightPrice,
		double ratePercentage, ServiceContext serviceContext)
		throws PortalException;

	public void deleteCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionRelId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShippingFixedOptionRel fetchCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionRelId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShippingFixedOptionRel fetchCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double weight);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShippingFixedOptionRel> getCommerceShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShippingFixedOptionRel> getCommerceShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShippingFixedOptionRelsCount(
		long commerceShippingFixedOptionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOptionRel> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShippingMethodFixedOptionRelsCount(
		long commerceShippingMethodId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public CommerceShippingFixedOptionRel updateCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionRelId, long commerceWarehouseId,
		long commerceCountryId, long commerceRegionId, java.lang.String zip,
		double weightFrom, double weightTo, BigDecimal fixedPrice,
		BigDecimal rateUnitWeightPrice, double ratePercentage)
		throws PortalException;
}