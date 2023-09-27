/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.notification;

import com.liferay.portal.kernel.workflow.WorkflowException;

/**
 * @author Michael C. Han
 */
public class NotificationMessageSenderException extends WorkflowException {

	public NotificationMessageSenderException() {
	}

	public NotificationMessageSenderException(String msg) {
		super(msg);
	}

	public NotificationMessageSenderException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NotificationMessageSenderException(Throwable throwable) {
		super(throwable);
	}

}