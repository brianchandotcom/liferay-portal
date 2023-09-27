/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.contacts.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Shinn Lok
 */
public class NoSuchContactsLayoutTemplateException
	extends NoSuchModelException {

	public NoSuchContactsLayoutTemplateException() {
	}

	public NoSuchContactsLayoutTemplateException(String msg) {
		super(msg);
	}

	public NoSuchContactsLayoutTemplateException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchContactsLayoutTemplateException(Throwable throwable) {
		super(throwable);
	}

}