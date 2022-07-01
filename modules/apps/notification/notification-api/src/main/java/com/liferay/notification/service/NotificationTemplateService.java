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

package com.liferay.notification.service;

import com.liferay.notification.model.NotificationTemplate;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for NotificationTemplate. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface NotificationTemplateService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.notification.service.impl.NotificationTemplateServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the notification template remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link NotificationTemplateServiceUtil} if injection and service tracking are not available.
	 */
	public NotificationTemplate addNotificationTemplate(
			long userId, long objectDefinitionId, String bcc,
			Map<Locale, String> bodyMap, String cc, String description,
			String from, Map<Locale, String> fromNameMap, String name,
			Map<Locale, String> subjectMap, Map<Locale, String> toMap,
			List<Long> attachmentObjectFieldIds)
		throws PortalException;

	public NotificationTemplate deleteNotificationTemplate(
			long notificationTemplateId)
		throws PortalException;

	public NotificationTemplate deleteNotificationTemplate(
			NotificationTemplate notificationTemplate)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public NotificationTemplate getNotificationTemplate(
			long notificationTemplateId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public NotificationTemplate updateNotificationTemplate(
			long notificationTemplateId, long objectDefinitionId, String bcc,
			Map<Locale, String> bodyMap, String cc, String description,
			String from, Map<Locale, String> fromNameMap, String name,
			Map<Locale, String> subjectMap, Map<Locale, String> toMap,
			List<Long> attachmentObjectFieldIds)
		throws PortalException;

}