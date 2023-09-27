/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchStructureException extends NoSuchModelException {

	public NoSuchStructureException() {
	}

	public NoSuchStructureException(String msg) {
		super(msg);
	}

	public NoSuchStructureException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchStructureException(Throwable throwable) {
		super(throwable);
	}

}