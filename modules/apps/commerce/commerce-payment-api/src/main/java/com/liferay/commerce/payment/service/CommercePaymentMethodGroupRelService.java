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

package com.liferay.commerce.payment.service;

import com.liferay.commerce.model.CommerceAddressRestriction;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
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
 * Provides the remote service interface for CommercePaymentMethodGroupRel. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRelServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommercePaymentMethodGroupRel"
	},
	service = CommercePaymentMethodGroupRelService.class
)
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommercePaymentMethodGroupRelService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.payment.service.impl.CommercePaymentMethodGroupRelServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce payment method group rel remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommercePaymentMethodGroupRelServiceUtil} if injection and service tracking are not available.
	 */
	public CommerceAddressRestriction addCommerceAddressRestriction(
			long groupId, long classPK, long countryId)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public CommerceAddressRestriction addCommerceAddressRestriction(
			long classPK, long countryId, ServiceContext serviceContext)
		throws PortalException;

	public CommercePaymentMethodGroupRel addCommercePaymentMethodGroupRel(
			long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, File imageFile,
			String engineKey, double priority, boolean active)
		throws PortalException;

	public void deleteCommerceAddressRestriction(
			long commerceAddressRestrictionId)
		throws PortalException;

	public void deleteCommerceAddressRestrictions(
			long commercePaymentMethodGroupRelId)
		throws PortalException;

	public void deleteCommercePaymentMethodGroupRel(
			long commercePaymentMethodGroupRelId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePaymentMethodGroupRel fetchCommercePaymentMethodGroupRel(
			long groupId, String engineKey)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommerceAddressRestriction> getCommerceAddressRestrictions(
			long classPK, int start, int end,
			OrderByComparator<CommerceAddressRestriction> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceAddressRestrictionsCount(long classPK)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePaymentMethodGroupRel getCommercePaymentMethodGroupRel(
			long commercePaymentMethodGroupRelId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePaymentMethodGroupRel getCommercePaymentMethodGroupRel(
			long groupId, String engineKey)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethodGroupRel>
			getCommercePaymentMethodGroupRels(long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethodGroupRel>
			getCommercePaymentMethodGroupRels(long groupId, boolean active)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethodGroupRel>
			getCommercePaymentMethodGroupRels(
				long groupId, boolean active, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethodGroupRel>
			getCommercePaymentMethodGroupRels(
				long groupId, boolean active, int start, int end,
				OrderByComparator<CommercePaymentMethodGroupRel>
					orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethodGroupRel>
			getCommercePaymentMethodGroupRels(
				long groupId, int start, int end,
				OrderByComparator<CommercePaymentMethodGroupRel>
					orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethodGroupRel>
			getCommercePaymentMethodGroupRels(
				long groupId, long countryId, boolean active)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePaymentMethodGroupRelsCount(long groupId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePaymentMethodGroupRelsCount(
			long groupId, boolean active)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public CommercePaymentMethodGroupRel setActive(
			long commercePaymentMethodGroupRelId, boolean active)
		throws PortalException;

	public CommercePaymentMethodGroupRel updateCommercePaymentMethodGroupRel(
			long commercePaymentMethodGroupRelId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, File imageFile, double priority,
			boolean active)
		throws PortalException;

}