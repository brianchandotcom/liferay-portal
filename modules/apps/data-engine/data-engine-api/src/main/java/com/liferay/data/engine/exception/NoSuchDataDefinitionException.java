/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchDataDefinitionException extends NoSuchModelException {

	public NoSuchDataDefinitionException() {
	}

	public NoSuchDataDefinitionException(String msg) {
		super(msg);
	}

	public NoSuchDataDefinitionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchDataDefinitionException(Throwable throwable) {
		super(throwable);
	}

}