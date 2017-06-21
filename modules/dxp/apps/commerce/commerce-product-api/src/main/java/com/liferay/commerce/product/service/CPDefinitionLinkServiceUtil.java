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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPDefinitionLink. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPDefinitionLinkServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionLinkService
 * @see com.liferay.commerce.product.service.base.CPDefinitionLinkServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionLinkServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionLinkServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionLinkServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPDefinitionLink addCPDefinitionLink(
		long cpDefinitionId1, long cpDefinitionId2, int displayOrder, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionLink(cpDefinitionId1, cpDefinitionId2,
			displayOrder, type, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinitionLink deleteCPDefinitionLink(
		com.liferay.commerce.product.model.CPDefinitionLink cpDefinitionLink)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPDefinitionLink(cpDefinitionLink);
	}

	public static com.liferay.commerce.product.model.CPDefinitionLink deleteCPDefinitionLink(
		long cpDefinitionLinkId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPDefinitionLink(cpDefinitionLinkId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPDefinitionLinkService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionLinkService, CPDefinitionLinkService> _serviceTracker =
		ServiceTrackerFactory.open(CPDefinitionLinkService.class);
}