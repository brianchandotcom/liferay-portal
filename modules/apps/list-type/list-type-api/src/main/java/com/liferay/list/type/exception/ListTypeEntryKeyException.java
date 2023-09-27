/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.list.type.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Gabriel Albuquerque
 */
public class ListTypeEntryKeyException extends PortalException {

	public ListTypeEntryKeyException() {
	}

	public ListTypeEntryKeyException(String msg) {
		super(msg);
	}

	public ListTypeEntryKeyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ListTypeEntryKeyException(Throwable throwable) {
		super(throwable);
	}

}