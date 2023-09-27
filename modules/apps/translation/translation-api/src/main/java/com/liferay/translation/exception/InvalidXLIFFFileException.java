/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.translation.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), replaced by {@link XLIFFFileException}
 */
@Deprecated
public class InvalidXLIFFFileException extends PortalException {

	public InvalidXLIFFFileException() {
	}

	public InvalidXLIFFFileException(String msg) {
		super(msg);
	}

	public InvalidXLIFFFileException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public InvalidXLIFFFileException(Throwable throwable) {
		super(throwable);
	}

}