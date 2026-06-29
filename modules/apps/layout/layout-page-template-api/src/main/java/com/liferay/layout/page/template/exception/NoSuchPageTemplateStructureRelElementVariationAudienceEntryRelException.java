/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class
	NoSuchPageTemplateStructureRelElementVariationAudienceEntryRelException
		extends NoSuchModelException {

	public NoSuchPageTemplateStructureRelElementVariationAudienceEntryRelException() {
	}

	public NoSuchPageTemplateStructureRelElementVariationAudienceEntryRelException(
		String msg) {

		super(msg);
	}

	public NoSuchPageTemplateStructureRelElementVariationAudienceEntryRelException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchPageTemplateStructureRelElementVariationAudienceEntryRelException(
		Throwable throwable) {

		super(throwable);
	}

}