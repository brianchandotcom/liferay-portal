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

package com.liferay.document.library.opener.google.drive.web.internal.util;

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Adolfo Pérez
 */
public class State implements Serializable {

	public static State get(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();

		return (State)session.getAttribute(
			_SESSION_ATTRIBUTE_NAME_GOOGLE_OAUTH2_STATE);
	}

	public static void save(
		HttpServletRequest httpServletRequest, long userId, String successURL,
		String failureURL, String state) {

		HttpSession session = httpServletRequest.getSession();

		session.setAttribute(
			_SESSION_ATTRIBUTE_NAME_GOOGLE_OAUTH2_STATE,
			new State(userId, successURL, failureURL, state));
	}

	public State(
		long userId, String successURL, String failureURL, String state) {

		_userId = userId;
		_successURL = successURL;
		_failureURL = failureURL;
		_state = state;
	}

	public long getUserId() {
		return _userId;
	}

	public String goToFailurePage(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_cleanUpSession(httpServletRequest);

		httpServletResponse.sendRedirect(_failureURL);

		return null;
	}

	public String goToSuccessPage(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_cleanUpSession(httpServletRequest);

		httpServletResponse.sendRedirect(_successURL);

		return null;
	}

	public boolean isValid(HttpServletRequest httpServletRequest) {
		if (Validator.isNotNull(
				ParamUtil.getString(httpServletRequest, "error"))) {

			return false;
		}

		String state = ParamUtil.getString(httpServletRequest, "state");

		if (!_state.equals(state)) {
			return false;
		}

		return true;
	}

	private void _cleanUpSession(HttpServletRequest httpServletRequest) {
		HttpSession session = httpServletRequest.getSession();

		session.removeAttribute(_SESSION_ATTRIBUTE_NAME_GOOGLE_OAUTH2_STATE);
	}

	private static final String _SESSION_ATTRIBUTE_NAME_GOOGLE_OAUTH2_STATE =
		"google-oauth2-state";

	private static final long serialVersionUID = 1180494919540636879L;

	private final String _failureURL;
	private final String _state;
	private final String _successURL;
	private final long _userId;

}