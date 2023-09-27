/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alexander Chow
 */
public class StagnantRowException extends PortalException {

	public StagnantRowException(String msg) {
		super(msg);
	}

	public StagnantRowException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

}