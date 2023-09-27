/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector;

/**
 * @author Iván Zaera
 */
public class SharepointResultException extends SharepointException {

	public SharepointResultException(String errorCode, String errorText) {
		super(errorCode + ": " + errorText);

		_errorCode = errorCode;
		_errorText = errorText;
	}

	public String getErrorCode() {
		return _errorCode;
	}

	public String getErrorText() {
		return _errorText;
	}

	private final String _errorCode;
	private final String _errorText;

}