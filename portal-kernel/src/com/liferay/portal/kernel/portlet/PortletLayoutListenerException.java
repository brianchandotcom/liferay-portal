/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.portlet;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletLayoutListenerException extends RuntimeException {

	public PortletLayoutListenerException() {
	}

	public PortletLayoutListenerException(String msg) {
		super(msg);
	}

	public PortletLayoutListenerException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public PortletLayoutListenerException(Throwable throwable) {
		super(throwable);
	}

}