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

import com.liferay.commerce.model.CommerceAddressRestriction;

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
 * Provides the remote service interface for CommerceAddressRestriction. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressRestrictionServiceUtil
 * @see com.liferay.commerce.service.base.CommerceAddressRestrictionServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceAddressRestrictionServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceAddressRestriction"}, service = CommerceAddressRestrictionService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceAddressRestrictionService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceAddressRestrictionServiceUtil} to access the commerce address restriction remote service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceAddressRestrictionServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceAddressRestriction addCommerceAddressRestriction(
		java.lang.String className, long classPK, long commerceCountryId,
		ServiceContext serviceContext) throws PortalException;

	public void deleteCommerceAddressRestriction(
		long commerceAddressRestrictionId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceAddressRestriction fetchCommerceAddressRestriction(
		java.lang.String className, long classPK, long commerceCountryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAddressRestriction> getCommerceAddressRestrictions(
		java.lang.String className, long classPK, int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceAddressRestrictionsCount(java.lang.String className,
		long classPK);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public java.lang.String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isCommerceAddressRestricted(java.lang.String className,
		long classPK, long commerceCountryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isCommercePaymentMethodRestricted(
		long commercePaymentMethodId, long commerceCountryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isCommerceShippingMethodRestricted(
		long commerceShippingMethodId, long commerceCountryId);
}