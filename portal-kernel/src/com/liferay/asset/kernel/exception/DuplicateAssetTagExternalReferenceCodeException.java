/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateAssetTagExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateAssetTagExternalReferenceCodeException() {
	}

	public DuplicateAssetTagExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateAssetTagExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateAssetTagExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}