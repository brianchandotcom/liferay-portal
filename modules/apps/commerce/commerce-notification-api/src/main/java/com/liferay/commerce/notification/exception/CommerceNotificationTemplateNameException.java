/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceNotificationTemplateNameException extends PortalException {

	public CommerceNotificationTemplateNameException() {
	}

	public CommerceNotificationTemplateNameException(String msg) {
		super(msg);
	}

	public CommerceNotificationTemplateNameException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CommerceNotificationTemplateNameException(Throwable throwable) {
		super(throwable);
	}

}