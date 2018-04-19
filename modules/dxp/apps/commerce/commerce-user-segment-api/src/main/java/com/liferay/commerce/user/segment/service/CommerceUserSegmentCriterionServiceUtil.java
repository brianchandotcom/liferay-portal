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

package com.liferay.commerce.user.segment.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceUserSegmentCriterion. This utility wraps
 * {@link com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentCriterionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceUserSegmentCriterionService
 * @see com.liferay.commerce.user.segment.service.base.CommerceUserSegmentCriterionServiceBaseImpl
 * @see com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentCriterionServiceImpl
 * @generated
 */
@ProviderType
public class CommerceUserSegmentCriterionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.user.segment.service.impl.CommerceUserSegmentCriterionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion addCommerceUserSegmentCriterion(
		long commerceUserSegmentEntryId, java.lang.String type,
		java.lang.String typeSettings, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceUserSegmentCriterion(commerceUserSegmentEntryId,
			type, typeSettings, priority, serviceContext);
	}

	public static void deleteCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCommerceUserSegmentCriterion(commerceUserSegmentCriterionId);
	}

	public static java.util.List<com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion> getCommerceUserSegmentCriteria(
		long commerceUserSegmentEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceUserSegmentCriteria(commerceUserSegmentEntryId,
			start, end, orderByComparator);
	}

	public static int getCommerceUserSegmentCriteriaCount(
		long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceUserSegmentCriteriaCount(commerceUserSegmentEntryId);
	}

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion getCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceUserSegmentCriterion(commerceUserSegmentCriterionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion updateCommerceUserSegmentCriterion(
		long commerceUserSegmentCriterionId, java.lang.String type,
		java.lang.String typeSettings, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceUserSegmentCriterion(commerceUserSegmentCriterionId,
			type, typeSettings, priority, serviceContext);
	}

	public static CommerceUserSegmentCriterionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceUserSegmentCriterionService, CommerceUserSegmentCriterionService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceUserSegmentCriterionService.class);

		ServiceTracker<CommerceUserSegmentCriterionService, CommerceUserSegmentCriterionService> serviceTracker =
			new ServiceTracker<CommerceUserSegmentCriterionService, CommerceUserSegmentCriterionService>(bundle.getBundleContext(),
				CommerceUserSegmentCriterionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}