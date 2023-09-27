/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.field.type;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldValueValidationException extends PortalException {

	public DDMFormFieldValueValidationException() {
	}

	public DDMFormFieldValueValidationException(String msg) {
		super(msg);
	}

	public DDMFormFieldValueValidationException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DDMFormFieldValueValidationException(Throwable throwable) {
		super(throwable);
	}

}