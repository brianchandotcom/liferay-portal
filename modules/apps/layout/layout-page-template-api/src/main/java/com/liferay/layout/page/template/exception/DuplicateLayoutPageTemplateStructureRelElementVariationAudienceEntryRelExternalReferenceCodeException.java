/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.layout.page.template.exception;

import com.liferay.portal.kernel.exception.DuplicateExternalReferenceCodeException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateLayoutPageTemplateStructureRelElementVariationAudienceEntryRelExternalReferenceCodeException extends DuplicateExternalReferenceCodeException {

	public DuplicateLayoutPageTemplateStructureRelElementVariationAudienceEntryRelExternalReferenceCodeException() {
	}

	public DuplicateLayoutPageTemplateStructureRelElementVariationAudienceEntryRelExternalReferenceCodeException(String msg) {
		super(msg);
	}

	public DuplicateLayoutPageTemplateStructureRelElementVariationAudienceEntryRelExternalReferenceCodeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateLayoutPageTemplateStructureRelElementVariationAudienceEntryRelExternalReferenceCodeException(Throwable throwable) {
		super(throwable);
	}

}