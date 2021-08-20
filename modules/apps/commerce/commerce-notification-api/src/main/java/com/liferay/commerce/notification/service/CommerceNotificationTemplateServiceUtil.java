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

import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for CommerceNotificationTemplate. This utility wraps
 * <code>com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateService
 * @generated
 */
public class CommerceNotificationTemplateServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.notification.service.impl.CommerceNotificationTemplateServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceNotificationTemplate addCommerceNotificationTemplate(
			long groupId, String name, String description, String from,
			Map<java.util.Locale, String> fromNameMap, String to, String cc,
			String bcc, String type, boolean enabled,
			Map<java.util.Locale, String> subjectMap,
			Map<java.util.Locale, String> bodyMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceNotificationTemplate(
			groupId, name, description, from, fromNameMap, to, cc, bcc, type,
			enabled, subjectMap, bodyMap, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static CommerceNotificationTemplate addCommerceNotificationTemplate(
			String name, String description, String from,
			Map<java.util.Locale, String> fromNameMap, String to, String cc,
			String bcc, String type, boolean enabled,
			Map<java.util.Locale, String> subjectMap,
			Map<java.util.Locale, String> bodyMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceNotificationTemplate(
			name, description, from, fromNameMap, to, cc, bcc, type, enabled,
			subjectMap, bodyMap, serviceContext);
	}

	public static void deleteCommerceNotificationTemplate(
			long commerceNotificationTemplateId)
		throws PortalException {

		getService().deleteCommerceNotificationTemplate(
			commerceNotificationTemplateId);
	}

	public static CommerceNotificationTemplate getCommerceNotificationTemplate(
			long commerceNotificationTemplateId)
		throws PortalException {

		return getService().getCommerceNotificationTemplate(
			commerceNotificationTemplateId);
	}

	public static List<CommerceNotificationTemplate>
			getCommerceNotificationTemplates(
				long groupId, boolean enabled, int start, int end,
				OrderByComparator<CommerceNotificationTemplate>
					orderByComparator)
		throws PortalException {

		return getService().getCommerceNotificationTemplates(
			groupId, enabled, start, end, orderByComparator);
	}

	public static List<CommerceNotificationTemplate>
			getCommerceNotificationTemplates(
				long groupId, int start, int end,
				OrderByComparator<CommerceNotificationTemplate>
					orderByComparator)
		throws PortalException {

		return getService().getCommerceNotificationTemplates(
			groupId, start, end, orderByComparator);
	}

	public static int getCommerceNotificationTemplatesCount(long groupId)
		throws PortalException {

		return getService().getCommerceNotificationTemplatesCount(groupId);
	}

	public static int getCommerceNotificationTemplatesCount(
			long groupId, boolean enabled)
		throws PortalException {

		return getService().getCommerceNotificationTemplatesCount(
			groupId, enabled);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceNotificationTemplate
			updateCommerceNotificationTemplate(
				long commerceNotificationTemplateId, String name,
				String description, String from,
				Map<java.util.Locale, String> fromNameMap, String to, String cc,
				String bcc, String type, boolean enabled,
				Map<java.util.Locale, String> subjectMap,
				Map<java.util.Locale, String> bodyMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateCommerceNotificationTemplate(
			commerceNotificationTemplateId, name, description, from,
			fromNameMap, to, cc, bcc, type, enabled, subjectMap, bodyMap,
			serviceContext);
	}

	public static CommerceNotificationTemplateService getService() {
		return _service;
	}

	private static volatile CommerceNotificationTemplateService _service;

}