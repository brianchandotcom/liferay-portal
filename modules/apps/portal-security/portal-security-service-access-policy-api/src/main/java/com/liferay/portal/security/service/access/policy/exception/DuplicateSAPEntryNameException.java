/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateSAPEntryNameException extends PortalException {

	public DuplicateSAPEntryNameException() {
	}

	public DuplicateSAPEntryNameException(String msg) {
		super(msg);
	}

	public DuplicateSAPEntryNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateSAPEntryNameException(Throwable throwable) {
		super(throwable);
	}

}