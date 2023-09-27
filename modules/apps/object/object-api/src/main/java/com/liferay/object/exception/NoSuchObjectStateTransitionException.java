/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Marco Leo
 */
public class NoSuchObjectStateTransitionException extends NoSuchModelException {

	public NoSuchObjectStateTransitionException() {
	}

	public NoSuchObjectStateTransitionException(String msg) {
		super(msg);
	}

	public NoSuchObjectStateTransitionException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchObjectStateTransitionException(Throwable throwable) {
		super(throwable);
	}

}