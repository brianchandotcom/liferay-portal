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

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;

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
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for CommerceShippingFixedOption. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionServiceUtil
 * @see com.liferay.commerce.shipping.engine.fixed.service.base.CommerceShippingFixedOptionServiceBaseImpl
 * @see com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceShippingFixedOption"}, service = CommerceShippingFixedOptionService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceShippingFixedOptionService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceShippingFixedOptionServiceUtil} to access the commerce shipping fixed option remote service. Add custom service methods to {@link com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceShippingFixedOption addCommerceShippingFixedOption(
		long commerceShippingMethodId, Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, BigDecimal amount,
		double priority, ServiceContext serviceContext)
		throws PortalException;

	public void deleteCommerceShippingFixedOption(
		long commerceShippingFixedOptionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShippingFixedOption fetchCommerceShippingFixedOption(
		long commerceShippingFixedOptionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShippingFixedOptionsCount(
		long commerceShippingMethodId);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	public CommerceShippingFixedOption updateCommerceShippingFixedOption(
		long commerceShippingFixedOptionId,
		Map<Locale, java.lang.String> nameMap,
		Map<Locale, java.lang.String> descriptionMap, BigDecimal amount,
		double priority) throws PortalException;
}