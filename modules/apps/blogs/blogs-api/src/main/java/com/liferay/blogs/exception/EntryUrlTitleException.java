/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class EntryUrlTitleException extends PortalException {

	public EntryUrlTitleException() {
	}

	public EntryUrlTitleException(String msg) {
		super(msg);
	}

	public EntryUrlTitleException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public EntryUrlTitleException(Throwable throwable) {
		super(throwable);
	}

}