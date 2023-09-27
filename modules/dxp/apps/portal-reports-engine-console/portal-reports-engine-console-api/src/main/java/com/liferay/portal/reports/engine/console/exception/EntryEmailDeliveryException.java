/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class EntryEmailDeliveryException extends PortalException {

	public EntryEmailDeliveryException() {
	}

	public EntryEmailDeliveryException(String msg) {
		super(msg);
	}

	public EntryEmailDeliveryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public EntryEmailDeliveryException(Throwable throwable) {
		super(throwable);
	}

}