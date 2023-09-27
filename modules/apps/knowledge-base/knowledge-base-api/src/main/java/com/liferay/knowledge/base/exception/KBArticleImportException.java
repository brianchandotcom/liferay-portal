/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.exception;

import com.liferay.asset.kernel.exception.AssetCategoryException;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class KBArticleImportException extends PortalException {

	public KBArticleImportException() {
	}

	public KBArticleImportException(String msg) {
		super(msg);
	}

	public KBArticleImportException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public KBArticleImportException(Throwable throwable) {
		super(throwable);
	}

	public static class MustHaveACategory extends KBArticleImportException {

		public MustHaveACategory(
			AssetCategoryException assetCategoryException) {

			super(assetCategoryException);
		}

	}

}