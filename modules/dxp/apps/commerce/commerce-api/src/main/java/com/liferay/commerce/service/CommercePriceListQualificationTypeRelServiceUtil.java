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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommercePriceListQualificationTypeRel. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommercePriceListQualificationTypeRelServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListQualificationTypeRelService
 * @see com.liferay.commerce.service.base.CommercePriceListQualificationTypeRelServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommercePriceListQualificationTypeRelServiceImpl
 * @generated
 */
@ProviderType
public class CommercePriceListQualificationTypeRelServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommercePriceListQualificationTypeRelServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel addCommercePriceListQualificationTypeRel(
		long commercePriceListId,
		java.lang.String commercePriceListQualificationType, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommercePriceListQualificationTypeRel(commercePriceListId,
			commercePriceListQualificationType, order, serviceContext);
	}

	public static void deleteCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId);
	}

	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel fetchCommercePriceListQualificationTypeRel(
		java.lang.String commercePriceListQualificationType,
		long commercePriceListId) {
		return getService()
				   .fetchCommercePriceListQualificationTypeRel(commercePriceListQualificationType,
			commercePriceListId);
	}

	public static java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRels(
		long commercePriceListId) {
		return getService()
				   .getCommercePriceListQualificationTypeRels(commercePriceListId);
	}

	public static java.util.List<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> getCommercePriceListQualificationTypeRels(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceListQualificationTypeRel> orderByComparator) {
		return getService()
				   .getCommercePriceListQualificationTypeRels(commercePriceListId,
			start, end, orderByComparator);
	}

	public static int getCommercePriceListQualificationTypeRelsCount(
		long commercePriceListId) {
		return getService()
				   .getCommercePriceListQualificationTypeRelsCount(commercePriceListId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommercePriceListQualificationTypeRel updateCommercePriceListQualificationTypeRel(
		long commercePriceListQualificationTypeRelId, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommercePriceListQualificationTypeRel(commercePriceListQualificationTypeRelId,
			order, serviceContext);
	}

	public static CommercePriceListQualificationTypeRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePriceListQualificationTypeRelService, CommercePriceListQualificationTypeRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommercePriceListQualificationTypeRelService.class);

		ServiceTracker<CommercePriceListQualificationTypeRelService, CommercePriceListQualificationTypeRelService> serviceTracker =
			new ServiceTracker<CommercePriceListQualificationTypeRelService, CommercePriceListQualificationTypeRelService>(bundle.getBundleContext(),
				CommercePriceListQualificationTypeRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}