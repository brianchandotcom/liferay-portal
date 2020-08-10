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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceNotificationTemplateCommerceAccountGroupRel. This utility wraps
 * <code>com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateCommerceAccountGroupRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateCommerceAccountGroupRelService
 * @generated
 */
public class CommerceNotificationTemplateCommerceAccountGroupRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateCommerceAccountGroupRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.notification.model.
		CommerceNotificationTemplateCommerceAccountGroupRel
				addCommerceNotificationTemplateCommerceAccountGroupRel(
					long commerceNotificationTemplateId,
					long commerceAccountGroupId,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().
			addCommerceNotificationTemplateCommerceAccountGroupRel(
				commerceNotificationTemplateId, commerceAccountGroupId,
				serviceContext);
	}

	public static void
			deleteCommerceNotificationTemplateCommerceAccountGroupRel(
				long commerceNotificationTemplateCommerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceNotificationTemplateCommerceAccountGroupRel(
			commerceNotificationTemplateCommerceAccountGroupRelId);
	}

	public static com.liferay.commerce.notification.model.
		CommerceNotificationTemplateCommerceAccountGroupRel
				fetchCommerceNotificationTemplateCommerceAccountGroupRel(
					long commerceNotificationTemplateId,
					long commerceAccountGroupId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().
			fetchCommerceNotificationTemplateCommerceAccountGroupRel(
				commerceNotificationTemplateId, commerceAccountGroupId);
	}

	public static java.util.List
		<com.liferay.commerce.notification.model.
			CommerceNotificationTemplateCommerceAccountGroupRel>
					getCommerceNotificationTemplateCommerceAccountGroupRels(
						long commerceNotificationTemplateId, int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.notification.model.
								CommerceNotificationTemplateCommerceAccountGroupRel>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().
			getCommerceNotificationTemplateCommerceAccountGroupRels(
				commerceNotificationTemplateId, start, end, orderByComparator);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceNotificationTemplateCommerceAccountGroupRelService
		getService() {

		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceNotificationTemplateCommerceAccountGroupRelService,
		 CommerceNotificationTemplateCommerceAccountGroupRelService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceNotificationTemplateCommerceAccountGroupRelService.class);

		ServiceTracker
			<CommerceNotificationTemplateCommerceAccountGroupRelService,
			 CommerceNotificationTemplateCommerceAccountGroupRelService>
				serviceTracker =
					new ServiceTracker
						<CommerceNotificationTemplateCommerceAccountGroupRelService,
						 CommerceNotificationTemplateCommerceAccountGroupRelService>(
							 bundle.getBundleContext(),
							 CommerceNotificationTemplateCommerceAccountGroupRelService.class,
							 null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}