/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchMetadataSetException extends NoSuchModelException {

	public NoSuchMetadataSetException() {
	}

	public NoSuchMetadataSetException(String msg) {
		super(msg);
	}

	public NoSuchMetadataSetException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchMetadataSetException(Throwable throwable) {
		super(throwable);
	}

}