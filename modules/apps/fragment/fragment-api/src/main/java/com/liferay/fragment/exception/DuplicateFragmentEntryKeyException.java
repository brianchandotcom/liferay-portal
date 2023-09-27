/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateFragmentEntryKeyException extends PortalException {

	public DuplicateFragmentEntryKeyException() {
	}

	public DuplicateFragmentEntryKeyException(String msg) {
		super(msg);
	}

	public DuplicateFragmentEntryKeyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateFragmentEntryKeyException(Throwable throwable) {
		super(throwable);
	}

}