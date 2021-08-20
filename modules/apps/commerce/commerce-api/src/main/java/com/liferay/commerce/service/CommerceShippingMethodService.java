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

import com.liferay.commerce.model.CommerceAddressRestriction;
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
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.File;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CommerceShippingMethod. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingMethodServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceShippingMethod"
	},
	service = CommerceShippingMethodService.class
)
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceShippingMethodService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceShippingMethodServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce shipping method remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceShippingMethodServiceUtil} if injection and service tracking are not available.
	 */
	public CommerceAddressRestriction addCommerceAddressRestriction(
			long groupId, long commerceShippingMethodId, long countryId)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public CommerceAddressRestriction addCommerceAddressRestriction(
			long commerceShippingMethodId, long countryId,
			ServiceContext serviceContext)
		throws PortalException;

	public CommerceShippingMethod addCommerceShippingMethod(
			long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, File imageFile,
			String engineKey, double priority, boolean active)
		throws PortalException;

	public CommerceShippingMethod createCommerceShippingMethod(
			long commerceShippingMethodId)
		throws PortalException;

	public void deleteCommerceAddressRestriction(
			long commerceAddressRestrictionId)
		throws PortalException;

	public void deleteCommerceAddressRestrictions(long commerceShippingMethodId)
		throws PortalException;

	public void deleteCommerceShippingMethod(long commerceShippingMethodId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShippingMethod fetchCommerceShippingMethod(
			long groupId, String engineKey)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAddressRestriction> getCommerceAddressRestrictions(
			long commerceShippingMethodId, int start, int end,
			OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceAddressRestrictionsCount(
			long commerceShippingMethodId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShippingMethod getCommerceShippingMethod(
			long commerceShippingMethodId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShippingMethod> getCommerceShippingMethods(long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShippingMethod> getCommerceShippingMethods(
			long groupId, boolean active)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceShippingMethod> getCommerceShippingMethods(
			long groupId, long countryId, boolean active)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceShippingMethodsCount(long groupId, boolean active)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public CommerceShippingMethod setActive(
			long commerceShippingMethodId, boolean active)
		throws PortalException;

	public CommerceShippingMethod updateCommerceShippingMethod(
			long commerceShippingMethodId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, File imageFile, double priority,
			boolean active)
		throws PortalException;

}