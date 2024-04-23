/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateAssetListEntryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateAssetListEntryExternalReferenceCodeException() {
	}

	public DuplicateAssetListEntryExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateAssetListEntryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateAssetListEntryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}