/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.entry.rel.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchEntryAssetCategoryRelException extends NoSuchModelException {

	public NoSuchEntryAssetCategoryRelException() {
	}

	public NoSuchEntryAssetCategoryRelException(String msg) {
		super(msg);
	}

	public NoSuchEntryAssetCategoryRelException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchEntryAssetCategoryRelException(Throwable throwable) {
		super(throwable);
	}

}