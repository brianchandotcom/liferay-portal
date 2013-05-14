/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl.servlet;

import com.liferay.portal.kernel.servlet.PluginContextListener;

import java.io.IOException;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author Raymond Augé
 */
public class PACLRequestDispatcherWrapper implements RequestDispatcher {

	public PACLRequestDispatcherWrapper(
		ServletContext servletContext, RequestDispatcher requestDispatcher) {

		_servletContext = servletContext;
		_requestDispatcher = requestDispatcher;
	}

	@Override
	public void forward(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		doDispatch(servletRequest, servletResponse, false);
	}

	@Override
	public void include(
			ServletRequest servletRequest, ServletResponse servletResponse)
		throws IOException, ServletException {

		doDispatch(servletRequest, servletResponse, true);
	}

	protected void doDispatch(
			ServletRequest servletRequest, ServletResponse servletResponse,
			boolean include)
		throws IOException, ServletException {

		ClassLoader pluginClassLoader =
			(ClassLoader)_servletContext.getAttribute(
				PluginContextListener.PLUGIN_CLASS_LOADER);

		DispatchPrivilegedExceptionAction dispatchPrivilegedExceptionAction =
			new DispatchPrivilegedExceptionAction(
				_requestDispatcher, servletRequest, servletResponse, include);

		try {
			if (pluginClassLoader == null) {
				AccessController.doPrivileged(
					dispatchPrivilegedExceptionAction);
			}
			else {
				dispatchPrivilegedExceptionAction.run();
			}
		}
		catch (PrivilegedActionException pae) {
			Exception e = pae.getException();

			if (e instanceof IOException) {
				throw (IOException)e;
			}

			throw (ServletException)pae.getException();
		}
	}

	private RequestDispatcher _requestDispatcher;
	private ServletContext _servletContext;

	private class DispatchPrivilegedExceptionAction
		implements PrivilegedExceptionAction<Void> {

		public DispatchPrivilegedExceptionAction(
			RequestDispatcher requestDispatcher, ServletRequest servletRequest,
			ServletResponse servletResponse, boolean include) {

			_requestDispatcher = requestDispatcher;
			_servletRequest = servletRequest;
			_servletResponse = servletResponse;
			_include = include;
		}

		public Void run() throws IOException, ServletException {
			if (_include) {
				_requestDispatcher.include(_servletRequest, _servletResponse);
			}
			else {
				_requestDispatcher.forward(_servletRequest, _servletResponse);
			}

			return null;
		}

		private boolean _include;
		private RequestDispatcher _requestDispatcher;
		private ServletRequest _servletRequest;
		private ServletResponse _servletResponse;

	}

}