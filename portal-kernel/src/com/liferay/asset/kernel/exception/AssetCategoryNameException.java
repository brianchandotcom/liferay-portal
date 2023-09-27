/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetCategoryNameException extends PortalException {

	public AssetCategoryNameException() {
	}

	public AssetCategoryNameException(String msg) {
		super(msg);
	}

	public AssetCategoryNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public AssetCategoryNameException(Throwable throwable) {
		super(throwable);
	}

}