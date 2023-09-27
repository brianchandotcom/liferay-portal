/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.dao.orm;

/**
 * @author Brian Wing Shun Chan
 */
public class ORMException extends RuntimeException {

	public ORMException() {
	}

	public ORMException(String msg) {
		super(msg);
	}

	public ORMException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ORMException(Throwable throwable) {
		super(throwable);
	}

}