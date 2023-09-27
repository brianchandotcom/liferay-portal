/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Eduardo García
 */
public class NoSuchExperienceException extends NoSuchModelException {

	public NoSuchExperienceException() {
	}

	public NoSuchExperienceException(String msg) {
		super(msg);
	}

	public NoSuchExperienceException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchExperienceException(Throwable throwable) {
		super(throwable);
	}

}