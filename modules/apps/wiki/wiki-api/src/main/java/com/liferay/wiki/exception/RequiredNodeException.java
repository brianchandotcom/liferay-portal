/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 * @author Levente Hudák
 */
public class RequiredNodeException extends PortalException {

	public RequiredNodeException() {
	}

	public RequiredNodeException(String msg) {
		super(msg);
	}

	public RequiredNodeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RequiredNodeException(Throwable throwable) {
		super(throwable);
	}

}