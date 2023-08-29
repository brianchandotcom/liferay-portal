/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Selton Guedes
 */
public class ObjectDefinitionSystemException extends PortalException {

	public ObjectDefinitionSystemException() {
	}

	public ObjectDefinitionSystemException(String msg) {
		super(msg);
	}

	public ObjectDefinitionSystemException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ObjectDefinitionSystemException(Throwable throwable) {
		super(throwable);
	}

}