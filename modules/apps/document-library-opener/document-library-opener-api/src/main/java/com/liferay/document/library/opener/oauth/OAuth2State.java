/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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