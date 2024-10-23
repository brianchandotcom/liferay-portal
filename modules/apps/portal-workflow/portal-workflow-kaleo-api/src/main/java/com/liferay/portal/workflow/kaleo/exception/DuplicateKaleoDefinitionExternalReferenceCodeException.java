/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateKaleoDefinitionExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateKaleoDefinitionExternalReferenceCodeException() {
	}

	public DuplicateKaleoDefinitionExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateKaleoDefinitionExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateKaleoDefinitionExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}