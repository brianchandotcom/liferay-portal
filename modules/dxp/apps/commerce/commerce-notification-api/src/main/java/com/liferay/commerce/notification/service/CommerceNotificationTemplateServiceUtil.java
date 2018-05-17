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
 * Provides the remote service utility for CommerceNotificationTemplate. This utility wraps
 * {@link com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateService
 * @see com.liferay.commerce.notification.service.base.CommerceNotificationTemplateServiceBaseImpl
 * @see com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateServiceImpl
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.notification.model.CommerceNotificationTemplate addCommerceNotificationTemplate(
		String name, String description, String cc, String ccn, String type,
		boolean enabled, java.util.Map<java.util.Locale, String> subjectMap,
		java.util.Map<java.util.Locale, String> bodyMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceNotificationTemplate(name, description, cc, ccn,
			type, enabled, subjectMap, bodyMap, serviceContext);
	}

	public static void deleteCommerceNotificationTemplate(
		long commerceNotificationTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCommerceNotificationTemplate(commerceNotificationTemplateId);
	}

	public static com.liferay.commerce.notification.model.CommerceNotificationTemplate getCommerceNotificationTemplate(
		long commerceNotificationTemplateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceNotificationTemplate(commerceNotificationTemplateId);
	}

	public static java.util.List<com.liferay.commerce.notification.model.CommerceNotificationTemplate> getCommerceNotificationTemplates(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.notification.model.CommerceNotificationTemplate> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceNotificationTemplates(groupId, start, end,
			orderByComparator);
	}

	public static int getCommerceNotificationTemplatesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceNotificationTemplatesCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.notification.model.CommerceNotificationTemplate updateCommerceNotificationTemplate(
		long commerceNotificationTemplateId, String name, String description,
		String cc, String ccn, String type, boolean enabled,
		java.util.Map<java.util.Locale, String> subjectMap,
		java.util.Map<java.util.Locale, String> bodyMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceNotificationTemplate(commerceNotificationTemplateId,
			name, description, cc, ccn, type, enabled, subjectMap, bodyMap,
			serviceContext);
	}

	public static CommerceNotificationTemplateService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceNotificationTemplateService, CommerceNotificationTemplateService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceNotificationTemplateService.class);

		ServiceTracker<CommerceNotificationTemplateService, CommerceNotificationTemplateService> serviceTracker =
			new ServiceTracker<CommerceNotificationTemplateService, CommerceNotificationTemplateService>(bundle.getBundleContext(),
				CommerceNotificationTemplateService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}