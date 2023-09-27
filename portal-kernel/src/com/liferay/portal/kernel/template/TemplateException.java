/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Tina Tian
 */
public class TemplateException extends PortalException {

	public TemplateException() {
	}

	public TemplateException(String msg) {
		super(msg);
	}

	public TemplateException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public TemplateException(Throwable throwable) {
		super(throwable);
	}

}