/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchPermissionPropagationEntryException
	extends NoSuchModelException {

	public NoSuchPermissionPropagationEntryException() {
	}

	public NoSuchPermissionPropagationEntryException(String msg) {
		super(msg);
	}

	public NoSuchPermissionPropagationEntryException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchPermissionPropagationEntryException(Throwable throwable) {
		super(throwable);
	}

}