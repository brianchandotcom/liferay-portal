/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Alvaro Saugar
 */
public class PwdEncryptorAlgorithmException extends PwdEncryptorException {

	public PwdEncryptorAlgorithmException() {
	}

	public PwdEncryptorAlgorithmException(String msg) {
		super(msg);
	}

	public PwdEncryptorAlgorithmException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public PwdEncryptorAlgorithmException(Throwable throwable) {
		super(throwable);
	}

}