/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class AudienceEntryNameException extends PortalException {

	public AudienceEntryNameException() {
	}

	public AudienceEntryNameException(String msg) {
		super(msg);
	}

	public AudienceEntryNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public AudienceEntryNameException(Throwable throwable) {
		super(throwable);
	}

}