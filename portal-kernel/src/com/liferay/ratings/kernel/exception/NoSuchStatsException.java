/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ratings.kernel.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchStatsException extends NoSuchModelException {

	public NoSuchStatsException() {
	}

	public NoSuchStatsException(String msg) {
		super(msg);
	}

	public NoSuchStatsException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchStatsException(Throwable throwable) {
		super(throwable);
	}

}