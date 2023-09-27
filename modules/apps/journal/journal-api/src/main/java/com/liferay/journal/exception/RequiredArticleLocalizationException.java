/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author José Ángel Jiménez Campoy
 */
public class RequiredArticleLocalizationException extends PortalException {

	public RequiredArticleLocalizationException() {
	}

	public RequiredArticleLocalizationException(String msg) {
		super(msg);
	}

	public RequiredArticleLocalizationException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public RequiredArticleLocalizationException(Throwable throwable) {
		super(throwable);
	}

}