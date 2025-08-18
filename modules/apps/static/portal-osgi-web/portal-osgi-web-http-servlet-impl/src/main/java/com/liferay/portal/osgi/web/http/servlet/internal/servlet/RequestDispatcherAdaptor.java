/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import com.liferay.petra.string.StringBundler;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.eclipse.equinox.http.servlet.internal.context.DispatchTargets;

/**
 * @author Dante Wang
 */
public class RequestDispatcherAdaptor implements RequestDispatcher {

	public RequestDispatcherAdaptor(
		DispatchTargets dispatchTargets, String path) {

		_dispatchTargets = dispatchTargets;
		_path = path;
	}

	@Override
	public void forward(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		_dispatchTargets.doDispatch(
			(HttpServletRequest)servletRequest,
			(HttpServletResponse)servletResponse, _path,
			DispatcherType.FORWARD);
	}

	@Override
	public void include(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		_dispatchTargets.doDispatch(
			(HttpServletRequest)servletRequest,
			(HttpServletResponse)servletResponse, _path,
			DispatcherType.INCLUDE);
	}

	@Override
	public String toString() {
		String value = _string;

		if (value == null) {
			value = StringBundler.concat(
				_SIMPLE_NAME, '[', _path, ", ", _dispatchTargets, ']');

			_string = value;
		}

		return value;
	}

	private static final String _SIMPLE_NAME =
		org.eclipse.equinox.http.servlet.internal.servlet.
			RequestDispatcherAdaptor.class.getSimpleName();

	private final DispatchTargets _dispatchTargets;
	private final String _path;
	private String _string;

}