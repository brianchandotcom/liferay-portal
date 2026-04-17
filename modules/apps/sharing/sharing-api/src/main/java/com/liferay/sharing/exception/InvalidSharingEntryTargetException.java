/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class InvalidSharingEntryTargetException extends PortalException {

	public InvalidSharingEntryTargetException() {
	}

	public InvalidSharingEntryTargetException(String msg) {
		super(msg);
	}

	public InvalidSharingEntryTargetException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public InvalidSharingEntryTargetException(Throwable throwable) {
		super(throwable);
	}

}