/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.blueprint.exception;

/**
 * @author Petteri Karttunen
 */
public class PrivateIPAddressException extends RuntimeException {

	public PrivateIPAddressException() {
	}

	public PrivateIPAddressException(String msg) {
		super(msg);
	}

	public PrivateIPAddressException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public PrivateIPAddressException(Throwable throwable) {
		super(throwable);
	}

}