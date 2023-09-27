/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Gabriel Albuquerque
 */
public class NoSuchListTypeDefinitionException extends NoSuchModelException {

	public NoSuchListTypeDefinitionException() {
	}

	public NoSuchListTypeDefinitionException(String msg) {
		super(msg);
	}

	public NoSuchListTypeDefinitionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchListTypeDefinitionException(Throwable throwable) {
		super(throwable);
	}

}