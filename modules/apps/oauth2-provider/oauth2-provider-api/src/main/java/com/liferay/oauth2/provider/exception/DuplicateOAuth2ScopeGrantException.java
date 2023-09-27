/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateOAuth2ScopeGrantException extends PortalException {

	public DuplicateOAuth2ScopeGrantException() {
	}

	public DuplicateOAuth2ScopeGrantException(String msg) {
		super(msg);
	}

	public DuplicateOAuth2ScopeGrantException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateOAuth2ScopeGrantException(Throwable throwable) {
		super(throwable);
	}

}