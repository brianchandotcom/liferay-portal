/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateColumnNameException extends PortalException {

	public DuplicateColumnNameException() {
	}

	public DuplicateColumnNameException(String msg) {
		super(msg);
	}

	public DuplicateColumnNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateColumnNameException(Throwable throwable) {
		super(throwable);
	}

}