/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Lourdes Fernández Besada
 */
public class InfoPermissionException extends PortalException {

	public InfoPermissionException(String className, Throwable throwable) {
		super("Unable to check permission for " + className, throwable);

		_className = className;
	}

	public String getClassName() {
		return _className;
	}

	private final String _className;

}