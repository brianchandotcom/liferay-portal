/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.oauth2.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuch2TokenEntryException extends NoSuchModelException {

	public NoSuch2TokenEntryException() {
	}

	public NoSuch2TokenEntryException(String msg) {
		super(msg);
	}

	public NoSuch2TokenEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuch2TokenEntryException(Throwable throwable) {
		super(throwable);
	}

}