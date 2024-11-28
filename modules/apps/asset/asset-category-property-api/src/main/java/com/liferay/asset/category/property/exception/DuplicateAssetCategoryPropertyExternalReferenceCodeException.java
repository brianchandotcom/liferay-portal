/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.category.property.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateAssetCategoryPropertyExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateAssetCategoryPropertyExternalReferenceCodeException() {
	}

	public DuplicateAssetCategoryPropertyExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateAssetCategoryPropertyExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateAssetCategoryPropertyExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}