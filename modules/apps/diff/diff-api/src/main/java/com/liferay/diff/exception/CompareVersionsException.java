/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.diff.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eudaldo Alonso
 */
public class CompareVersionsException extends PortalException {

	public CompareVersionsException(double version) {
		_version = version;
	}

	public CompareVersionsException(String msg) {
		super(msg);

		_version = 0;
	}

	public CompareVersionsException(String msg, Throwable throwable) {
		super(msg, throwable);

		_version = 0;
	}

	public CompareVersionsException(Throwable throwable) {
		super(throwable);

		_version = 0;
	}

	public double getVersion() {
		return _version;
	}

	private final double _version;

}