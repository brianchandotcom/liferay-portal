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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link NotificationTemplateRecipientService}.
 *
 * @author Gabriel Albuquerque
 * @see NotificationTemplateRecipientService
 * @generated
 */
public class NotificationTemplateRecipientServiceWrapper
	implements NotificationTemplateRecipientService,
			   ServiceWrapper<NotificationTemplateRecipientService> {

	public NotificationTemplateRecipientServiceWrapper() {
		this(null);
	}

	public NotificationTemplateRecipientServiceWrapper(
		NotificationTemplateRecipientService
			notificationTemplateRecipientService) {

		_notificationTemplateRecipientService =
			notificationTemplateRecipientService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationTemplateRecipientService.getOSGiServiceIdentifier();
	}

	@Override
	public NotificationTemplateRecipientService getWrappedService() {
		return _notificationTemplateRecipientService;
	}

	@Override
	public void setWrappedService(
		NotificationTemplateRecipientService
			notificationTemplateRecipientService) {

		_notificationTemplateRecipientService =
			notificationTemplateRecipientService;
	}

	private NotificationTemplateRecipientService
		_notificationTemplateRecipientService;

}