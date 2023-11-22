/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Gustavo Lima
 */
public class SXPElementNoDefaultLocaleTitleException extends PortalException {

	public SXPElementNoDefaultLocaleTitleException() {
	}

	public SXPElementNoDefaultLocaleTitleException(String msg) {
		super(msg);
	}

	public SXPElementNoDefaultLocaleTitleException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public SXPElementNoDefaultLocaleTitleException(Throwable throwable) {
		super(throwable);
	}

}