/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.events;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class ActionException extends PortalException {

	public ActionException() {
	}

	public ActionException(String msg) {
		super(msg);
	}

	public ActionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ActionException(Throwable throwable) {
		super(throwable);
	}

}