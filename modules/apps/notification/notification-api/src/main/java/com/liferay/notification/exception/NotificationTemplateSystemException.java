/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Selton Guedes
 */
public class NotificationTemplateSystemException extends PortalException {

	public NotificationTemplateSystemException() {
	}

	public NotificationTemplateSystemException(String msg) {
		super(msg);
	}

	public NotificationTemplateSystemException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NotificationTemplateSystemException(Throwable throwable) {
		super(throwable);
	}

}