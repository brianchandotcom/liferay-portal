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
 * Provides the remote service utility for CPGroup. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPGroupServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPGroupService
 * @see com.liferay.commerce.product.service.base.CPGroupServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPGroupServiceImpl
 * @generated
 */
@ProviderType
public class CPGroupServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPGroupServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPGroup addCPGroup(
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addCPGroup(serviceContext);
	}

	public static com.liferay.commerce.product.model.CPGroup deleteCPGroupByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPGroupByGroupId(groupId);
	}

	public static com.liferay.commerce.product.model.CPGroup fetchCPGroupByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPGroupByGroupId(groupId);
	}

	public static com.liferay.commerce.product.model.CPGroup getCPGroupByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPGroupByGroupId(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPGroupService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPGroupService, CPGroupService> _serviceTracker =
		ServiceTrackerFactory.open(CPGroupService.class);
}