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
 * Provides the remote service utility for CPDefinitionMedia. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPDefinitionMediaServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPDefinitionMediaService
 * @see com.liferay.commerce.product.service.base.CPDefinitionMediaServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPDefinitionMediaServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionMediaServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPDefinitionMediaServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPDefinitionMedia addCPDefinitionMedia(
		long cpDefinitionId, long fileEntryId, java.lang.String ddmContent,
		int priority, long cpMediaTypeId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCPDefinitionMedia(cpDefinitionId, fileEntryId,
			ddmContent, priority, cpMediaTypeId, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPDefinitionMedia updateCPDefinitionMedia(
		long cpDefinitionMediaId, java.lang.String ddmContent, int priority,
		long cpMediaTypeId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionMedia(cpDefinitionMediaId, ddmContent,
			priority, cpMediaTypeId, serviceContext);
	}

	public static com.liferay.commerce.product.model.CPMediaType deleteCPMediaType(
		long cpMediaTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCPMediaType(cpMediaTypeId);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinitionMedia> searchCPDefinitionMedias(
		long companyId, long groupId, long cpDefinitionId,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCPDefinitionMedias(companyId, groupId,
			cpDefinitionId, keywords, start, end, sort);
	}

	public static com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return getService().search(searchContext);
	}

	public static int getDefinitionMediasCount(long cpDefinitionId) {
		return getService().getDefinitionMediasCount(cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionMedia> getDefinitionMedias(
		long cpDefinitionId, int start, int end) {
		return getService().getDefinitionMedias(cpDefinitionId, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPDefinitionMedia> getDefinitionMedias(
		long cpDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionMedia> orderByComparator) {
		return getService()
				   .getDefinitionMedias(cpDefinitionId, start, end,
			orderByComparator);
	}

	public static CPDefinitionMediaService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionMediaService, CPDefinitionMediaService> _serviceTracker =
		ServiceTrackerFactory.open(CPDefinitionMediaService.class);
}