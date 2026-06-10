/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.version.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Lourdes Fernández Besada
 */
public class UnsupportedLayoutLayoutContentVersionException
	extends PortalException {

	public UnsupportedLayoutLayoutContentVersionException() {
	}

	public UnsupportedLayoutLayoutContentVersionException(String msg) {
		super(msg);
	}

	public UnsupportedLayoutLayoutContentVersionException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public UnsupportedLayoutLayoutContentVersionException(Throwable throwable) {
		super(throwable);
	}

}