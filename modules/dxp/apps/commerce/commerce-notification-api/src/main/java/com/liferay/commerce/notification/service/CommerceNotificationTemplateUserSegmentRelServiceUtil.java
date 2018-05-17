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

package com.liferay.commerce.notification.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceNotificationTemplateUserSegmentRel. This utility wraps
 * {@link com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateUserSegmentRelServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateUserSegmentRelService
 * @see com.liferay.commerce.notification.service.base.CommerceNotificationTemplateUserSegmentRelServiceBaseImpl
 * @see com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateUserSegmentRelServiceImpl
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateUserSegmentRelServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateUserSegmentRelServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel addCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateId,
			commerceUserSegmentEntryId, serviceContext);
	}

	public static void deleteCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateUserSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateUserSegmentRelId);
	}

	public static com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel fetchCommerceNotificationTemplateUserSegmentRel(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .fetchCommerceNotificationTemplateUserSegmentRel(commerceNotificationTemplateId,
			commerceUserSegmentEntryId);
	}

	public static java.util.List<com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel> getCommerceNotificationTemplateUserSegmentRels(
		long commerceNotificationTemplateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceNotificationTemplateUserSegmentRels(commerceNotificationTemplateId,
			start, end, orderByComparator);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceNotificationTemplateUserSegmentRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceNotificationTemplateUserSegmentRelService, CommerceNotificationTemplateUserSegmentRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceNotificationTemplateUserSegmentRelService.class);

		ServiceTracker<CommerceNotificationTemplateUserSegmentRelService, CommerceNotificationTemplateUserSegmentRelService> serviceTracker =
			new ServiceTracker<CommerceNotificationTemplateUserSegmentRelService, CommerceNotificationTemplateUserSegmentRelService>(bundle.getBundleContext(),
				CommerceNotificationTemplateUserSegmentRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}