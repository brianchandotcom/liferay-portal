/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * Thrown when the system identifies a violation of the Record Set Name required
 * property.
 *
 * @author Brian Wing Shun Chan
 */
public class RecordSetNameException extends PortalException {

	public RecordSetNameException() {
	}

	public RecordSetNameException(String msg) {
		super(msg);
	}

	public RecordSetNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RecordSetNameException(Throwable throwable) {
		super(throwable);
	}

}