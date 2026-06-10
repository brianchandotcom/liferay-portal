/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchAudienceEntryException extends NoSuchModelException {

	public NoSuchAudienceEntryException() {
	}

	public NoSuchAudienceEntryException(String msg) {
		super(msg);
	}

	public NoSuchAudienceEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchAudienceEntryException(Throwable throwable) {
		super(throwable);
	}

}