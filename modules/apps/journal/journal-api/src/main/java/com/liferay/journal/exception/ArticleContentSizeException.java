/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 * @author Vilmos Papp
 */
public class ArticleContentSizeException extends PortalException {

	public ArticleContentSizeException() {
	}

	public ArticleContentSizeException(String msg) {
		super(msg);
	}

	public ArticleContentSizeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ArticleContentSizeException(Throwable throwable) {
		super(throwable);
	}

}