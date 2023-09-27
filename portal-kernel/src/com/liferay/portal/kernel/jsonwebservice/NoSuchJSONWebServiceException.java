/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.jsonwebservice;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Igor Spasic
 */
public class NoSuchJSONWebServiceException extends PortalException {

	public NoSuchJSONWebServiceException() {
	}

	public NoSuchJSONWebServiceException(String msg) {
		super(msg);
	}

	public NoSuchJSONWebServiceException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchJSONWebServiceException(Throwable throwable) {
		super(throwable);
	}

}