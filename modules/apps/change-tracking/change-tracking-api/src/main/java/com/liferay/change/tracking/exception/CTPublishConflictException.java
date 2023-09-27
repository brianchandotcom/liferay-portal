/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class CTPublishConflictException extends PortalException {

	public CTPublishConflictException() {
	}

	public CTPublishConflictException(String msg) {
		super(msg);
	}

	public CTPublishConflictException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CTPublishConflictException(Throwable throwable) {
		super(throwable);
	}

}