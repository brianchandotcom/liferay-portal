/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchEntryCustomMetaTagException extends NoSuchModelException {

	public NoSuchEntryCustomMetaTagException() {
	}

	public NoSuchEntryCustomMetaTagException(String msg) {
		super(msg);
	}

	public NoSuchEntryCustomMetaTagException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchEntryCustomMetaTagException(Throwable throwable) {
		super(throwable);
	}

}