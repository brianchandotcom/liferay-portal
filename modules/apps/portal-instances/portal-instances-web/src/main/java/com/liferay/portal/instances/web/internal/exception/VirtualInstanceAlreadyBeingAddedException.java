/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.web.internal.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Luis Ortiz
 */
public class VirtualInstanceAlreadyBeingAddedException extends PortalException {

	public VirtualInstanceAlreadyBeingAddedException(String msg) {
		super(msg);
	}

}