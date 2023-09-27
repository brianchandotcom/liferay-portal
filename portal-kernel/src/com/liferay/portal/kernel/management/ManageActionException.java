/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.management;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Shuyang Zhou
 */
public class ManageActionException extends PortalException {

	public ManageActionException() {
	}

	public ManageActionException(String msg) {
		super(msg);
	}

	public ManageActionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ManageActionException(Throwable throwable) {
		super(throwable);
	}

}