/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.version.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Lourdes Fernández Besada
 */
public class NoSuchLayoutContentVersionException extends NoSuchModelException {

	public NoSuchLayoutContentVersionException() {
	}

	public NoSuchLayoutContentVersionException(String msg) {
		super(msg);
	}

	public NoSuchLayoutContentVersionException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchLayoutContentVersionException(Throwable throwable) {
		super(throwable);
	}

}