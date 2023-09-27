/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Shuyang Zhou
 */
public class DuplicateBatchEngineImportTaskExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateBatchEngineImportTaskExternalReferenceCodeException() {
	}

	public DuplicateBatchEngineImportTaskExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateBatchEngineImportTaskExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateBatchEngineImportTaskExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}