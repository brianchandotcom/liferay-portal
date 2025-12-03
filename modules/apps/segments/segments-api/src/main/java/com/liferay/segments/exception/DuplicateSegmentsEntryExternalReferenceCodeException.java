/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Eduardo García
 */
public class DuplicateSegmentsEntryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateSegmentsEntryExternalReferenceCodeException() {
	}

	public DuplicateSegmentsEntryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateSegmentsEntryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateSegmentsEntryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}