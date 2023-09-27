/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateOAuth2ApplicationClientIdException
	extends PortalException {

	public DuplicateOAuth2ApplicationClientIdException() {
	}

	public DuplicateOAuth2ApplicationClientIdException(String msg) {
		super(msg);
	}

	public DuplicateOAuth2ApplicationClientIdException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateOAuth2ApplicationClientIdException(Throwable throwable) {
		super(throwable);
	}

}