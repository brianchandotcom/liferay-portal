/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateWikiNodeExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateWikiNodeExternalReferenceCodeException() {
	}

	public DuplicateWikiNodeExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateWikiNodeExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateWikiNodeExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}