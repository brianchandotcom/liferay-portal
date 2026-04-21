/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class RequiredFragmentEntryVersionException extends PortalException {

	public RequiredFragmentEntryVersionException() {
	}

	public RequiredFragmentEntryVersionException(String msg) {
		super(msg);
	}

	public RequiredFragmentEntryVersionException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public RequiredFragmentEntryVersionException(Throwable throwable) {
		super(throwable);
	}

}