/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateFriendlyURLEntryException extends PortalException {

	public DuplicateFriendlyURLEntryException() {
	}

	public DuplicateFriendlyURLEntryException(String msg) {
		super(msg);
	}

	public DuplicateFriendlyURLEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateFriendlyURLEntryException(Throwable throwable) {
		super(throwable);
	}

}