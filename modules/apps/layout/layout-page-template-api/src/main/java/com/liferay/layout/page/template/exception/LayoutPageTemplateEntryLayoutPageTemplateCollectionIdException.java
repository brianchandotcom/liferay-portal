/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.layout.page.template.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutPageTemplateEntryLayoutPageTemplateCollectionIdException extends PortalException {

	public LayoutPageTemplateEntryLayoutPageTemplateCollectionIdException() {
	}

	public LayoutPageTemplateEntryLayoutPageTemplateCollectionIdException(String msg) {
		super(msg);
	}

	public LayoutPageTemplateEntryLayoutPageTemplateCollectionIdException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public LayoutPageTemplateEntryLayoutPageTemplateCollectionIdException(Throwable throwable) {
		super(throwable);
	}

}