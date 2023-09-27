/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.encryptor;

/**
 * @author Brian Wing Shun Chan
 * @see    com.liferay.util.EncryptorException
 */
public class EncryptorException extends Exception {

	public EncryptorException() {
	}

	public EncryptorException(String msg) {
		super(msg);
	}

	public EncryptorException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public EncryptorException(Throwable throwable) {
		super(throwable);
	}

}