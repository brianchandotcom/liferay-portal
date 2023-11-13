/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateSavedContentEntryException extends PortalException {

	public DuplicateSavedContentEntryException() {
	}

	public DuplicateSavedContentEntryException(String msg) {
		super(msg);
	}

	public DuplicateSavedContentEntryException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateSavedContentEntryException(Throwable throwable) {
		super(throwable);
	}

}