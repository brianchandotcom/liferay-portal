/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.runtime.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Mika Koivisto
 */
public class SubjectException extends PortalException {

	public SubjectException() {
	}

	public SubjectException(String msg) {
		super(msg);
	}

	public SubjectException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public SubjectException(Throwable throwable) {
		super(throwable);
	}

}