/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchTagGroupRelException extends NoSuchModelException {

	public NoSuchTagGroupRelException() {
	}

	public NoSuchTagGroupRelException(String msg) {
		super(msg);
	}

	public NoSuchTagGroupRelException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchTagGroupRelException(Throwable throwable) {
		super(throwable);
	}

}