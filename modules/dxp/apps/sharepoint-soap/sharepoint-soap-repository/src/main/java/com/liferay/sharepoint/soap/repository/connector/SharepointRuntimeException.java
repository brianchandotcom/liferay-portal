/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector;

/**
 * @author Iván Zaera
 */
public class SharepointRuntimeException extends RuntimeException {

	public SharepointRuntimeException(String message) {
		super(message);
	}

	public SharepointRuntimeException(String message, Throwable throwable) {
		super(message, throwable);
	}

}