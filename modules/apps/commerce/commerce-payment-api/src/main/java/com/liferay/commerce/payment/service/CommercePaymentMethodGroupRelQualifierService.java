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

import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CommercePaymentMethodGroupRelQualifier. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Luca Pellizzon
 * @see CommercePaymentMethodGroupRelQualifierServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommercePaymentMethodGroupRelQualifier"
	},
	service = CommercePaymentMethodGroupRelQualifierService.class
)
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommercePaymentMethodGroupRelQualifierService
	extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.payment.service.impl.CommercePaymentMethodGroupRelQualifierServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce payment method group rel qualifier remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommercePaymentMethodGroupRelQualifierServiceUtil} if injection and service tracking are not available.
	 */
	public CommercePaymentMethodGroupRelQualifier
			addCommercePaymentMethodGroupRelQualifier(
				String className, long classPK,
				long commercePaymentMethodGroupRelId)
		throws PortalException;

	public void deleteCommercePaymentMethodGroupRelQualifier(
			long commercePaymentMethodGroupRelQualifierId)
		throws PortalException;

	public void deleteCommercePaymentMethodGroupRelQualifiers(
			String className, long commercePaymentMethodGroupRelId)
		throws PortalException;

	public void
			deleteCommercePaymentMethodGroupRelQualifiersByCommercePaymentMethodGroupRelId(
				long commercePaymentMethodGroupRelId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePaymentMethodGroupRelQualifier
			fetchCommercePaymentMethodGroupRelQualifier(
				String className, long classPK,
				long commercePaymentMethodGroupRelId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethodGroupRelQualifier>
			getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiers(
				long commercePaymentMethodGroupRelId, String keywords,
				int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceOrderTypeCommercePaymentMethodGroupRelQualifiersCount(
			long commercePaymentMethodGroupRelId, String keywords)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePaymentMethodGroupRelQualifier
			getCommercePaymentMethodGroupRelQualifier(
				long commercePaymentMethodGroupRelQualifierId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethodGroupRelQualifier>
			getCommercePaymentMethodGroupRelQualifiers(
				long commercePaymentMethodGroupRelId, int start, int end,
				OrderByComparator<CommercePaymentMethodGroupRelQualifier>
					orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethodGroupRelQualifier>
			getCommercePaymentMethodGroupRelQualifiers(
				String className, long commercePaymentMethodGroupRelId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePaymentMethodGroupRelQualifiersCount(
			long commercePaymentMethodGroupRelId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePaymentMethodGroupRelQualifier>
			getCommerceTermEntryCommercePaymentMethodGroupRelQualifiers(
				long commercePaymentMethodGroupRelId, String keywords,
				int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommerceTermEntryCommercePaymentMethodGroupRelQualifiersCount(
			long commercePaymentMethodGroupRelId, String keywords)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

}