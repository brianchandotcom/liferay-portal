/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Rubén Pulido
 */
public class GroupNameException extends PortalException {

	public GroupNameException() {
	}

	public GroupNameException(String msg) {
		super(msg);
	}

	public GroupNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public GroupNameException(Throwable throwable) {
		super(throwable);
	}

}