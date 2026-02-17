/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetCategoryParentCategoryIdException extends PortalException {

	public AssetCategoryParentCategoryIdException() {
	}

	public AssetCategoryParentCategoryIdException(String msg) {
		super(msg);
	}

	public AssetCategoryParentCategoryIdException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public AssetCategoryParentCategoryIdException(Throwable throwable) {
		super(throwable);
	}

}