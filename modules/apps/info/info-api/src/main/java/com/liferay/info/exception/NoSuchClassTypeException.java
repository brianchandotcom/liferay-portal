/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jorge Ferrer
 */
public class NoSuchClassTypeException extends PortalException {

	public NoSuchClassTypeException(long classTypeId, Throwable throwable) {
		super(
			"Unable to get class type found with class type ID " + classTypeId,
			throwable);

		_classTypeId = classTypeId;
	}

	public long getClassTypeId() {
		return _classTypeId;
	}

	private final long _classTypeId;

}