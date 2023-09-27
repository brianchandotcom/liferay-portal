/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;

/**
 * @author Brian Wing Shun Chan
 */
public class ColumnNameException extends PortalException {

	public ColumnNameException() {
	}

	public ColumnNameException(String msg) {
		super(msg);
	}

	public ColumnNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ColumnNameException(Throwable throwable) {
		super(throwable);
	}

	public static class MustValidate extends ColumnNameException {

		public MustValidate() {
			super("Field name must validate with " + Field.class.getName());
		}

	}

}