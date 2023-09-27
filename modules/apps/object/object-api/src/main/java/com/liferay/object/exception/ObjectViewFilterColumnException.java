/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class ObjectViewFilterColumnException extends PortalException {

	public ObjectViewFilterColumnException() {
	}

	public ObjectViewFilterColumnException(String msg) {
		super(msg);
	}

	public ObjectViewFilterColumnException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ObjectViewFilterColumnException(Throwable throwable) {
		super(throwable);
	}

}