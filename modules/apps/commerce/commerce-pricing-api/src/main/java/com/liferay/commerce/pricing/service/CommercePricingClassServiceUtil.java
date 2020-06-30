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

package com.liferay.commerce.pricing.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommercePricingClass. This utility wraps
 * <code>com.liferay.commerce.pricing.service.impl.CommercePricingClassServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Riccardo Alberti
 * @see CommercePricingClassService
 * @generated
 */
public class CommercePricingClassServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.pricing.service.impl.CommercePricingClassServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.pricing.model.CommercePricingClass
			addCommercePricingClass(
				long userId, long groupId, String title, String description,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePricingClass(
			userId, groupId, title, description, serviceContext);
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClass
			deleteCommercePricingClass(long commercePricingClassId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommercePricingClass(commercePricingClassId);
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClass
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClass
			fetchCommercePricingClass(long commercePricingClassId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommercePricingClass(commercePricingClassId);
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClass
			getCommercePricingClass(long commercePricingClassId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePricingClass(commercePricingClassId);
	}

	public static java.util.List
		<com.liferay.commerce.pricing.model.CommercePricingClass>
				getCommercePricingClasses(
					long companyId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.pricing.model.
							CommercePricingClass> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePricingClasses(
			companyId, start, end, orderByComparator);
	}

	public static int getCommercePricingClassesCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePricingClassesCount(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClass
			updateCommercePricingClass(
				long commercePricingClassId, long userId, long groupId,
				String title, String description,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommercePricingClass(
			commercePricingClassId, userId, groupId, title, description,
			serviceContext);
	}

	public static com.liferay.commerce.pricing.model.CommercePricingClass
			upsertCommercePricingClass(
				long commercePricingClassId, long userId, long groupId,
				String title, String description, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommercePricingClass(
			commercePricingClassId, userId, groupId, title, description,
			externalReferenceCode, serviceContext);
	}

	public static CommercePricingClassService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommercePricingClassService, CommercePricingClassService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommercePricingClassService.class);

		ServiceTracker<CommercePricingClassService, CommercePricingClassService>
			serviceTracker =
				new ServiceTracker
					<CommercePricingClassService, CommercePricingClassService>(
						bundle.getBundleContext(),
						CommercePricingClassService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}