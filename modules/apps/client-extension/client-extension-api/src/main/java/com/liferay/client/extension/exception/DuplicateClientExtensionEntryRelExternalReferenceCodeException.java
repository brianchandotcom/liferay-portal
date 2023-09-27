/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateClientExtensionEntryRelExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateClientExtensionEntryRelExternalReferenceCodeException() {
	}

	public DuplicateClientExtensionEntryRelExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateClientExtensionEntryRelExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateClientExtensionEntryRelExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}