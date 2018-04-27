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

import com.liferay.commerce.model.CommercePaymentMethod;

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

import java.io.File;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the remote service interface for CommercePaymentMethod. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePaymentMethodServiceUtil
 * @see com.liferay.commerce.service.base.CommercePaymentMethodServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommercePaymentMethodServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommercePaymentMethod"}, service = CommercePaymentMethodService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommercePaymentMethodService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommercePaymentMethodServiceUtil} to access the commerce payment method remote service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommercePaymentMethodServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommercePaymentMethod addCommercePaymentMethod(
		Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
		File imageFile, String engineKey,
		Map<String, String> engineParameterMap, double priority,
		boolean active, ServiceContext serviceContext)
		throws PortalException;

	public CommercePaymentMethod createCommercePaymentMethod(
		long commercePaymentMethodId) throws PortalException;

	public void deleteCommercePaymentMethod(long commercePaymentMethodId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePaymentMethod getCommercePaymentMethod(
		long commercePaymentMethodId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethod> getCommercePaymentMethods(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethod> getCommercePaymentMethods(long groupId,
		boolean active);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethod> getCommercePaymentMethods(long groupId,
		long commerceCountryId, boolean active);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePaymentMethodsCount(long groupId, boolean active);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CommercePaymentMethod setActive(long commercePaymentMethodId,
		boolean active) throws PortalException;

	public CommercePaymentMethod updateCommercePaymentMethod(
		long commercePaymentMethodId, Map<Locale, String> nameMap,
		Map<Locale, String> descriptionMap, File imageFile,
		Map<String, String> engineParameterMap, double priority,
		boolean active, ServiceContext serviceContext)
		throws PortalException;
}