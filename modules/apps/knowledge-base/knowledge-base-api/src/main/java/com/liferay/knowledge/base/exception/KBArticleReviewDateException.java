/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alicia García
 */
public class KBArticleReviewDateException extends PortalException {

	public KBArticleReviewDateException() {
	}

	public KBArticleReviewDateException(String msg) {
		super(msg);
	}

	public KBArticleReviewDateException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public KBArticleReviewDateException(Throwable throwable) {
		super(throwable);
	}

}