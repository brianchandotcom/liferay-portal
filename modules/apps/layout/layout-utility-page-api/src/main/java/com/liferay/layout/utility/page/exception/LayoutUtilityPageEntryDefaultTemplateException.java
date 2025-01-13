/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.utility.page.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutUtilityPageEntryDefaultTemplateException
	extends PortalException {

	public LayoutUtilityPageEntryDefaultTemplateException() {
	}

	public LayoutUtilityPageEntryDefaultTemplateException(String msg) {
		super(msg);
	}

	public LayoutUtilityPageEntryDefaultTemplateException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public LayoutUtilityPageEntryDefaultTemplateException(Throwable throwable) {
		super(throwable);
	}

}