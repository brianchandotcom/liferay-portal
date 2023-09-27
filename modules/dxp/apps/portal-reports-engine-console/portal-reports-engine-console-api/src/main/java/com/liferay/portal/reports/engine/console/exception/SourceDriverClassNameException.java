/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class SourceDriverClassNameException extends PortalException {

	public SourceDriverClassNameException() {
	}

	public SourceDriverClassNameException(String msg) {
		super(msg);
	}

	public SourceDriverClassNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public SourceDriverClassNameException(Throwable throwable) {
		super(throwable);
	}

}