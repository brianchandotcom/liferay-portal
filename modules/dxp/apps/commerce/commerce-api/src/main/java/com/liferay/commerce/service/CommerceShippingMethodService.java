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

import com.liferay.commerce.model.CommerceShippingMethod;

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
 * Provides the remote service interface for CommerceShippingMethod. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingMethodServiceUtil
 * @see com.liferay.commerce.service.base.CommerceShippingMethodServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceShippingMethodServiceImpl
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(property =  {
	"json.web.service.context.name=commerce", "json.web.service.context.path=CommerceShippingMethod"}, service = CommerceShippingMethodService.class)
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CommerceShippingMethodService extends BaseService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CommerceShippingMethodServiceUtil} to access the commerce shipping method remote service. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceShippingMethodServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public CommerceShippingMethod addCommerceShippingMethod(
		Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
		File imageFile, String engineKey, double priority, boolean active,
		ServiceContext serviceContext) throws PortalException;

	public CommerceShippingMethod createCommerceShippingMethod(
		long commerceShippingMethodId) throws PortalException;

	public void deleteCommerceShippingMethod(long commerceShippingMethodId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShippingMethod fetchCommerceShippingMethod(long groupId,
		String engineKey);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShippingMethod getCommerceShippingMethod(
		long commerceShippingMethodId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShippingMethod> getCommerceShippingMethods(long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShippingMethod> getCommerceShippingMethods(
		long groupId, boolean active);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShippingMethodsCount(long groupId, boolean active);

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	public CommerceShippingMethod setActive(long commerceShippingMethodId,
		boolean active) throws PortalException;

	public CommerceShippingMethod updateCommerceShippingMethod(
		long commerceShippingMethodId, Map<Locale, String> nameMap,
		Map<Locale, String> descriptionMap, File imageFile, double priority,
		boolean active) throws PortalException;
}