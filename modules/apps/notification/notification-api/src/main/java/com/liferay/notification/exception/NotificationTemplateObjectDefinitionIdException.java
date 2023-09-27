/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Gabriel Albuquerque
 */
public class NotificationTemplateObjectDefinitionIdException
	extends PortalException {

	public NotificationTemplateObjectDefinitionIdException() {
	}

	public NotificationTemplateObjectDefinitionIdException(String msg) {
		super(msg);
	}

	public NotificationTemplateObjectDefinitionIdException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NotificationTemplateObjectDefinitionIdException(
		Throwable throwable) {

		super(throwable);
	}

}