/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateLayoutPageTemplateEntryExternalReferenceCodeException
	extends DuplicateExternalReferenceCodeException {

	public DuplicateLayoutPageTemplateEntryExternalReferenceCodeException() {
	}

	public DuplicateLayoutPageTemplateEntryExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateLayoutPageTemplateEntryExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateLayoutPageTemplateEntryExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}