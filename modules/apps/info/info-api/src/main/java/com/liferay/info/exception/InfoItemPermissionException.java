/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jorge Ferrer
 */
public class InfoItemPermissionException extends PortalException {

	public InfoItemPermissionException(long classPK, Throwable throwable) {
		super("Unable to check permission for " + classPK, throwable);

		_classPK = classPK;
	}

	public long getClassPK() {
		return _classPK;
	}

	private final long _classPK;

}