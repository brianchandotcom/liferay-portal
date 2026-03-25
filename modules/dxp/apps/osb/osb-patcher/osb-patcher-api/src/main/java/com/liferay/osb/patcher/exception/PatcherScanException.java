/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Davi Santos
 */
public class PatcherScanException extends PortalException {

	public PatcherScanException(String msg, Object[] arguments) {
		super(msg);

		_arguments = arguments;
	}

	public Object[] getArguments() {
		return _arguments;
	}

	private final Object[] _arguments;

}