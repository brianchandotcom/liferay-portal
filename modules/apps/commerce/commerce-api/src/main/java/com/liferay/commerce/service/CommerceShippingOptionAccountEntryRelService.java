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

import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CommerceShippingOptionAccountEntryRel. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingOptionAccountEntryRelServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@OSGiBeanProperties(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceShippingOptionAccountEntryRel"
	},
	service = CommerceShippingOptionAccountEntryRelService.class
)
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommerceShippingOptionAccountEntryRelService
	extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceShippingOptionAccountEntryRelServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce shipping option account entry rel remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommerceShippingOptionAccountEntryRelServiceUtil} if injection and service tracking are not available.
	 */
	public CommerceShippingOptionAccountEntryRel
			addCommerceShippingOptionAccountEntryRel(
				long accountEntryId, long commerceChannelId,
				String commerceShippingMethodKey,
				String commerceShippingOptionKey)
		throws PortalException;

	public void deleteCommerceShippingOptionAccountEntryRel(
			long commerceShippingOptionAccountEntryRelId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommerceShippingOptionAccountEntryRel
			fetchCommerceShippingOptionAccountEntryRel(
				long accountEntryId, long commerceChannelId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public CommerceShippingOptionAccountEntryRel
			updateCommerceShippingOptionAccountEntryRel(
				long commerceShippingOptionAccountEntryRelId,
				String commerceShippingMethodKey,
				String commerceShippingOptionKey)
		throws PortalException;

}