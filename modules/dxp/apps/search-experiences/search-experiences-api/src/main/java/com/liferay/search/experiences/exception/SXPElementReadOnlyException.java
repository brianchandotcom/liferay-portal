/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class SXPElementReadOnlyException extends PortalException {

	public SXPElementReadOnlyException() {
	}

	public SXPElementReadOnlyException(String msg) {
		super(msg);
	}

	public SXPElementReadOnlyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public SXPElementReadOnlyException(Throwable throwable) {
		super(throwable);
	}

}