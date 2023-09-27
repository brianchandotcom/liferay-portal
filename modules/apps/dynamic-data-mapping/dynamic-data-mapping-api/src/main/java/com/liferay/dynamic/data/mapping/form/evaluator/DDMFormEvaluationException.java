/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marcellus Tavares
 */
public class DDMFormEvaluationException extends PortalException {

	public DDMFormEvaluationException() {
	}

	public DDMFormEvaluationException(String msg) {
		super(msg);
	}

	public DDMFormEvaluationException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DDMFormEvaluationException(Throwable throwable) {
		super(throwable);
	}

	private static final long serialVersionUID = 1L;

}