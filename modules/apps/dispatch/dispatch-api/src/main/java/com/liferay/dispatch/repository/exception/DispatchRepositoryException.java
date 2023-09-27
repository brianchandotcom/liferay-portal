/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.repository.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Igor Beslic
 */
public class DispatchRepositoryException extends PortalException {

	public DispatchRepositoryException() {
	}

	public DispatchRepositoryException(String msg) {
		super(msg);
	}

	public DispatchRepositoryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DispatchRepositoryException(Throwable throwable) {
		super(throwable);
	}

}