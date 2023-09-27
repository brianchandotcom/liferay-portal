/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Drew Brokke
 * @see    com.liferay.portal.kernel.portlet.LiferayPortlet#isSessionErrorException(
 *         Throwable)
 */
public class DuplicateExternalReferenceCodeException extends SystemException {

	public DuplicateExternalReferenceCodeException() {
	}

	public DuplicateExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateExternalReferenceCodeException(Throwable throwable) {
		super(throwable);
	}

}