/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DuplicateLayoutPageTemplateCollectionException
	extends PortalException {

	public DuplicateLayoutPageTemplateCollectionException() {
	}

	public DuplicateLayoutPageTemplateCollectionException(String msg) {
		super(msg);
	}

	public DuplicateLayoutPageTemplateCollectionException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateLayoutPageTemplateCollectionException(Throwable throwable) {
		super(throwable);
	}

}