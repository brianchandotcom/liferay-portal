/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Eduardo García
 */
public class DuplicateSegmentsExperienceExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateSegmentsExperienceExternalReferenceCodeException() {
	}

	public DuplicateSegmentsExperienceExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateSegmentsExperienceExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateSegmentsExperienceExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}