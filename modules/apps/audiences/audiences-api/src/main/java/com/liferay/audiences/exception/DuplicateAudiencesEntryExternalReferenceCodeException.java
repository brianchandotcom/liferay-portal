/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateAudiencesEntryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateAudiencesEntryExternalReferenceCodeException() {
	}

	public DuplicateAudiencesEntryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateAudiencesEntryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateAudiencesEntryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}