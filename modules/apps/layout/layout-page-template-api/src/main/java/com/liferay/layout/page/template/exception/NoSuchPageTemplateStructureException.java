/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchPageTemplateStructureException extends NoSuchModelException {

	public NoSuchPageTemplateStructureException() {
	}

	public NoSuchPageTemplateStructureException(String msg) {
		super(msg);
	}

	public NoSuchPageTemplateStructureException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchPageTemplateStructureException(Throwable throwable) {
		super(throwable);
	}

}