/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.opener.oauth;

import java.io.Serializable;

/**
 * @author Adolfo Pérez
 * @author Alicia Garcia Garcia
 */
public class OAuth2State implements Serializable {

	public OAuth2State(
		long userId, String successURL, String failureURL, String state) {

		_userId = userId;
		_successURL = successURL;
		_failureURL = failureURL;
		_state = state;
	}

	public String getFailureURL() {
		return _failureURL;
	}

	public String getSuccessURL() {
		return _successURL;
	}

	public long getUserId() {
		return _userId;
	}

	public boolean isValid(String state) {
		if (!_state.equals(state)) {
			return false;
		}

		return true;
	}

	private static final long serialVersionUID = 1180494919540636880L;

	private final String _failureURL;
	private final String _state;
	private final String _successURL;
	private final long _userId;

}