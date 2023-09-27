/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Marco Leo
 */
public class NoSuchObjectDefinitionException extends NoSuchModelException {

	public NoSuchObjectDefinitionException() {
	}

	public NoSuchObjectDefinitionException(String msg) {
		super(msg);
	}

	public NoSuchObjectDefinitionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchObjectDefinitionException(Throwable throwable) {
		super(throwable);
	}

}