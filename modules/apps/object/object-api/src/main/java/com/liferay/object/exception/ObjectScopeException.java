/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class ObjectScopeException extends PortalException {

	public ObjectScopeException() {
	}

	public ObjectScopeException(String msg) {
		super(msg);
	}

	public ObjectScopeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ObjectScopeException(Throwable throwable) {
		super(throwable);
	}

}