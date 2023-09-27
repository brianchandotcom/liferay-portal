/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchLayoutUtilityPageEntryException
	extends NoSuchModelException {

	public NoSuchLayoutUtilityPageEntryException() {
	}

	public NoSuchLayoutUtilityPageEntryException(String msg) {
		super(msg);
	}

	public NoSuchLayoutUtilityPageEntryException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchLayoutUtilityPageEntryException(Throwable throwable) {
		super(throwable);
	}

}