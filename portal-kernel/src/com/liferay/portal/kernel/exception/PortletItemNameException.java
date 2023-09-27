/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletItemNameException extends PortalException {

	public PortletItemNameException() {
	}

	public PortletItemNameException(String msg) {
		super(msg);
	}

	public PortletItemNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public PortletItemNameException(Throwable throwable) {
		super(throwable);
	}

}