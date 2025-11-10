/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Feliphe Marinho
 */
public class NoSuchNodeSettingException extends NoSuchModelException {

	public NoSuchNodeSettingException() {
	}

	public NoSuchNodeSettingException(String msg) {
		super(msg);
	}

	public NoSuchNodeSettingException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchNodeSettingException(Throwable throwable) {
		super(throwable);
	}

}