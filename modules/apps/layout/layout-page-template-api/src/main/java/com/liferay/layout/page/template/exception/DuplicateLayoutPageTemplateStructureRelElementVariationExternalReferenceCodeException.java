/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class
	DuplicateLayoutPageTemplateStructureRelElementVariationExternalReferenceCodeException
		extends DuplicateExternalReferenceCodeException {

	public DuplicateLayoutPageTemplateStructureRelElementVariationExternalReferenceCodeException() {
	}

	public DuplicateLayoutPageTemplateStructureRelElementVariationExternalReferenceCodeException(
		String msg) {

		super(msg);
	}

	public DuplicateLayoutPageTemplateStructureRelElementVariationExternalReferenceCodeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateLayoutPageTemplateStructureRelElementVariationExternalReferenceCodeException(
		Throwable throwable) {

		super(throwable);
	}

}