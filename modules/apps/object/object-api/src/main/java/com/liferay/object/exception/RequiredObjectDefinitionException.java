/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class RequiredObjectDefinitionException extends PortalException {

	public RequiredObjectDefinitionException() {
	}

	public RequiredObjectDefinitionException(String msg) {
		super(msg);
	}

	public RequiredObjectDefinitionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RequiredObjectDefinitionException(Throwable throwable) {
		super(throwable);
	}

}