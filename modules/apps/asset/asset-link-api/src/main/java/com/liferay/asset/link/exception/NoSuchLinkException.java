/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.link.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchLinkException extends NoSuchModelException {

	public NoSuchLinkException() {
	}

	public NoSuchLinkException(String msg) {
		super(msg);
	}

	public NoSuchLinkException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchLinkException(Throwable throwable) {
		super(throwable);
	}

}