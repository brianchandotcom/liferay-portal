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

package com.liferay.notifications.admin.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link NotificationsTemplateService}.
 *
 * @author Gabriel Albuquerque
 * @see NotificationsTemplateService
 * @generated
 */
public class NotificationsTemplateServiceWrapper
	implements NotificationsTemplateService,
			   ServiceWrapper<NotificationsTemplateService> {

	public NotificationsTemplateServiceWrapper() {
		this(null);
	}

	public NotificationsTemplateServiceWrapper(
		NotificationsTemplateService notificationsTemplateService) {

		_notificationsTemplateService = notificationsTemplateService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _notificationsTemplateService.getOSGiServiceIdentifier();
	}

	@Override
	public NotificationsTemplateService getWrappedService() {
		return _notificationsTemplateService;
	}

	@Override
	public void setWrappedService(
		NotificationsTemplateService notificationsTemplateService) {

		_notificationsTemplateService = notificationsTemplateService;
	}

	private NotificationsTemplateService _notificationsTemplateService;

}