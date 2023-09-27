/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Pedro Tavares
 */
public class NotificationQueueEntryStatusException extends PortalException {

	public NotificationQueueEntryStatusException() {
	}

	public NotificationQueueEntryStatusException(String msg) {
		super(msg);
	}

	public NotificationQueueEntryStatusException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NotificationQueueEntryStatusException(Throwable throwable) {
		super(throwable);
	}

}