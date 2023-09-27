/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Shuyang Zhou
 */
public class NoSuchExportTaskException extends NoSuchModelException {

	public NoSuchExportTaskException() {
	}

	public NoSuchExportTaskException(String msg) {
		super(msg);
	}

	public NoSuchExportTaskException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchExportTaskException(Throwable throwable) {
		super(throwable);
	}

}