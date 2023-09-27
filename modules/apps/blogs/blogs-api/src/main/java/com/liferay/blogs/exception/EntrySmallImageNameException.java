/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class EntrySmallImageNameException extends PortalException {

	public EntrySmallImageNameException() {
	}

	public EntrySmallImageNameException(String msg) {
		super(msg);
	}

	public EntrySmallImageNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public EntrySmallImageNameException(Throwable throwable) {
		super(throwable);
	}

}