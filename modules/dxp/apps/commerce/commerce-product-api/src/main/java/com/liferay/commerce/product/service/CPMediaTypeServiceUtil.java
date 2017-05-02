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
 * Provides the remote service utility for CPMediaType. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPMediaTypeServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPMediaTypeService
 * @see com.liferay.commerce.product.service.base.CPMediaTypeServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPMediaTypeServiceImpl
 * @generated
 */
@ProviderType
public class CPMediaTypeServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPMediaTypeServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPMediaType addCPMediaType(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPMediaType(titleMap, descriptionMap, priority,
			serviceContext);
	}

	public static com.liferay.commerce.product.model.CPMediaType deleteCPMediaType(
		com.liferay.commerce.product.model.CPMediaType cpMediaType)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPMediaType(cpMediaType);
	}

	public static com.liferay.commerce.product.model.CPMediaType deleteCPMediaType(
		long cpMediaTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPMediaType(cpMediaTypeId);
	}

	public static com.liferay.commerce.product.model.CPMediaType fetchCPMediaType(
		long cpMediaTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchCPMediaType(cpMediaTypeId);
	}

	public static com.liferay.commerce.product.model.CPMediaType getCPMediaType(
		long cpMediaTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPMediaType(cpMediaTypeId);
	}

	public static com.liferay.commerce.product.model.CPMediaType updateCPMediaType(
		long cpMediaTypeId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPMediaType(cpMediaTypeId, titleMap, descriptionMap,
			priority, serviceContext);
	}

	public static int getCPMediaTypesCount(long groupId) {
		return getService().getCPMediaTypesCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.product.model.CPMediaType> getCPMediaTypes(
		long groupId, int start, int end) {
		return getService().getCPMediaTypes(groupId, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPMediaType> getCPMediaTypes(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPMediaType> orderByComparator) {
		return getService()
				   .getCPMediaTypes(groupId, start, end, orderByComparator);
	}

	public static CPMediaTypeService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPMediaTypeService, CPMediaTypeService> _serviceTracker =
		ServiceTrackerFactory.open(CPMediaTypeService.class);
}