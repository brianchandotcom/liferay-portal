/*******************************************************************************
 * Copyright (c) 2014, 2015 Raymond Augé and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Raymond Augé <raymond.auge@liferay.com> - Bug 436698
 ******************************************************************************/

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import com.liferay.portal.osgi.web.http.servlet.internal.context.ContextController;
import com.liferay.portal.osgi.web.http.servlet.internal.context.DispatchTargets;
import com.liferay.portal.osgi.web.http.servlet.internal.registration.EndpointRegistration;
import com.liferay.portal.osgi.web.http.servlet.internal.registration.FilterRegistration;
import com.liferay.portal.osgi.web.http.servlet.internal.util.EventListeners;

import java.io.IOException;

import java.util.Collections;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Raymond Augé
 */
public class ResponseStateHandler {

	public ResponseStateHandler(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		DispatchTargets dispatchTargets) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_dispatchTargets = dispatchTargets;
	}

	public void processRequest() throws IOException, ServletException {
		List<ServletRequestListener> servletRequestListeners =
			_getServletRequestListener();
		EndpointRegistration<?> endpointRegistration =
			_dispatchTargets.getServletRegistration();
		List<FilterRegistration> filters =
			_dispatchTargets.getMatchingFilterRegistrations();

		endpointRegistration.addReference();

		for (FilterRegistration filterRegistration : filters) {
			filterRegistration.addReference();
		}

		ServletRequestEvent servletRequestEvent = null;

		try {
			if ((_dispatchTargets.getDispatcherType() ==
					DispatcherType.REQUEST) &&
				!servletRequestListeners.isEmpty()) {

				servletRequestEvent = new ServletRequestEvent(
					endpointRegistration.getServletContext(),
					_httpServletRequest);

				for (ServletRequestListener servletRequestListener :
						servletRequestListeners) {

					servletRequestListener.requestInitialized(
						servletRequestEvent);
				}
			}

			ServletContextHelper servletContextHelper =
				endpointRegistration.getServletContextHelper();

			if (servletContextHelper.handleSecurity(
					_httpServletRequest, _httpServletResponse)) {

				if (filters.isEmpty()) {
					endpointRegistration.service(
						_httpServletRequest, _httpServletResponse);
				}
				else {
					Collections.sort(filters);

					FilterChain filterChain = new FilterChainImpl(
						filters, endpointRegistration,
						_dispatchTargets.getDispatcherType());

					filterChain.doFilter(
						_httpServletRequest, _httpServletResponse);
				}
			}
		}
		catch (Exception exception) {
			if (!(exception instanceof IOException) &&
				!(exception instanceof RuntimeException) &&
				!(exception instanceof ServletException)) {

				exception = new ServletException(exception);
			}

			setException(exception);

			if (_dispatchTargets.getDispatcherType() !=
					DispatcherType.REQUEST) {

				_throwException(exception);
			}
		}
		finally {
			endpointRegistration.removeReference();

			for (FilterRegistration filterRegistration : filters) {
				filterRegistration.removeReference();
			}

			if (_dispatchTargets.getDispatcherType() ==
					DispatcherType.REQUEST) {

				_handleErrors();

				for (ServletRequestListener servletRequestListener :
						servletRequestListeners) {

					servletRequestListener.requestDestroyed(
						servletRequestEvent);
				}
			}
		}
	}

	public void setException(Exception exception) {
		_exception = exception;
	}

	private List<ServletRequestListener> _getServletRequestListener() {
		ContextController contextController =
			_dispatchTargets.getContextController();

		EventListeners eventListeners = contextController.getEventListeners();

		return eventListeners.get(ServletRequestListener.class);
	}

	private void _handleErrors() throws IOException, ServletException {
		if (_exception != null) {
			_handleException();
		}
		else {
			_handleResponseCode();
		}
	}

	private void _handleException() throws IOException, ServletException {
		if (!(_httpServletResponse instanceof HttpServletResponseWrapper)) {
			throw new IllegalStateException("Response is not a wrapper");
		}

		HttpServletResponseWrapper httpServletResponseWrapper =
			(HttpServletResponseWrapper)_httpServletResponse;

		HttpServletResponseWrapperImpl httpServletResponseWrapperImpl = null;

		while (true) {
			if (httpServletResponseWrapper instanceof
					HttpServletResponseWrapperImpl) {

				httpServletResponseWrapperImpl =
					(HttpServletResponseWrapperImpl)httpServletResponseWrapper;
			}
			else if (httpServletResponseWrapper.getResponse() instanceof
						HttpServletResponseWrapper) {

				httpServletResponseWrapper =
					(HttpServletResponseWrapper)
						httpServletResponseWrapper.getResponse();

				continue;
			}

			break;
		}

		if (httpServletResponseWrapperImpl == null) {
			throw new IllegalStateException("Can not locate response impl");
		}

		HttpServletResponse wrappedHttpServletResponse =
			(HttpServletResponse)httpServletResponseWrapperImpl.getResponse();

		if (wrappedHttpServletResponse.isCommitted()) {
			_throwException(_exception);
		}

		ContextController contextController =
			_dispatchTargets.getContextController();

		Class<? extends Exception> clazz = _exception.getClass();

		final String className = clazz.getName();

		DispatchTargets errorDispatchTargets =
			contextController.getDispatchTargets(
				className, null, null, null, null, null, Match.EXACT, null);

		if (errorDispatchTargets == null) {
			_throwException(_exception);
		}

		HttpServletRequestWrapperImpl httpServletRequestWrapperImpl =
			HttpServletRequestWrapperImpl.findHttpRuntimeRequest(
				_httpServletRequest);

		try {
			errorDispatchTargets.setDispatcherType(DispatcherType.ERROR);

			httpServletRequestWrapperImpl.push(errorDispatchTargets);

			HttpServletRequest httpServletRequest =
				new HttpServletRequestWrapper(_httpServletRequest) {

					@Override
					public Object getAttribute(String attributeName) {
						if (getDispatcherType() == DispatcherType.ERROR) {
							if (attributeName.equals(
									RequestDispatcher.ERROR_EXCEPTION)) {

								return _exception;
							}
							else if (attributeName.equals(
										RequestDispatcher.
											ERROR_EXCEPTION_TYPE)) {

								return className;
							}
							else if (attributeName.equals(
										RequestDispatcher.ERROR_MESSAGE)) {

								return _exception.getMessage();
							}
							else if (attributeName.equals(
										RequestDispatcher.ERROR_REQUEST_URI)) {

								return _httpServletRequest.getRequestURI();
							}
							else if (attributeName.equals(
										RequestDispatcher.ERROR_SERVLET_NAME)) {

								EndpointRegistration<?> endpointRegistration =
									_dispatchTargets.getServletRegistration();

								return endpointRegistration.getName();
							}
							else if (attributeName.equals(
										RequestDispatcher.ERROR_STATUS_CODE)) {

								return HttpServletResponse.
									SC_INTERNAL_SERVER_ERROR;
							}
						}

						return super.getAttribute(attributeName);
					}

					@Override
					public DispatcherType getDispatcherType() {
						return DispatcherType.ERROR;
					}

				};

			HttpServletResponse httpServletResponse =
				new HttpServletResponseWrapperImpl(wrappedHttpServletResponse);

			ResponseStateHandler responseStateHandler =
				new ResponseStateHandler(
					httpServletRequest, httpServletResponse,
					errorDispatchTargets);

			responseStateHandler.processRequest();

			wrappedHttpServletResponse.setStatus(
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		finally {
			httpServletRequestWrapperImpl.pop();
		}
	}

	private void _handleResponseCode() throws IOException, ServletException {
		if (!(_httpServletResponse instanceof HttpServletResponseWrapper)) {
			throw new IllegalStateException("Response is not a wrapper");
		}

		HttpServletResponseWrapperImpl httpServletResponseWrapperImpl =
			HttpServletResponseWrapperImpl.findHttpRuntimeResponse(
				_httpServletResponse);

		if (httpServletResponseWrapperImpl == null) {
			throw new IllegalStateException("Can not locate response impl");
		}

		int status = httpServletResponseWrapperImpl.getInternalStatus();

		if (status < HttpServletResponse.SC_BAD_REQUEST) {
			return;
		}

		if (status == -1) {

			// There's nothing more we can do here.

			return;
		}

		HttpServletResponse wrappedHttpServletResponse =
			(HttpServletResponse)httpServletResponseWrapperImpl.getResponse();

		if (wrappedHttpServletResponse.isCommitted()) {

			// There's nothing more we can do here.

			return;
		}

		ContextController contextController =
			_dispatchTargets.getContextController();

		DispatchTargets errorDispatchTargets =
			contextController.getDispatchTargets(
				String.valueOf(status), null, null, null, null, null,
				Match.EXACT, null);

		if (errorDispatchTargets == null) {
			wrappedHttpServletResponse.sendError(
				status, httpServletResponseWrapperImpl.getMessage());

			return;
		}

		HttpServletRequestWrapperImpl httpServletRequestWrapperImpl =
			HttpServletRequestWrapperImpl.findHttpRuntimeRequest(
				_httpServletRequest);

		try {
			errorDispatchTargets.setDispatcherType(DispatcherType.ERROR);

			httpServletRequestWrapperImpl.push(errorDispatchTargets);

			HttpServletRequest httpServletRequest =
				new HttpServletRequestWrapper(_httpServletRequest) {

					@Override
					public Object getAttribute(String attributeName) {
						if (getDispatcherType() == DispatcherType.ERROR) {
							if (attributeName.equals(
									RequestDispatcher.ERROR_MESSAGE)) {

								return httpServletResponseWrapperImpl.
									getMessage();
							}
							else if (attributeName.equals(
										RequestDispatcher.ERROR_REQUEST_URI)) {

								return _httpServletRequest.getRequestURI();
							}
							else if (attributeName.equals(
										RequestDispatcher.ERROR_SERVLET_NAME)) {

								EndpointRegistration<?> endpointRegistration =
									_dispatchTargets.getServletRegistration();

								return endpointRegistration.getName();
							}
							else if (attributeName.equals(
										RequestDispatcher.ERROR_STATUS_CODE)) {

								return status;
							}
						}

						return super.getAttribute(attributeName);
					}

					@Override
					public DispatcherType getDispatcherType() {
						return DispatcherType.ERROR;
					}

				};

			HttpServletResponse httpServletResponse =
				new HttpServletResponseWrapperImpl(wrappedHttpServletResponse);

			ResponseStateHandler responseStateHandler =
				new ResponseStateHandler(
					httpServletRequest, httpServletResponse,
					errorDispatchTargets);

			wrappedHttpServletResponse.setStatus(status);

			responseStateHandler.processRequest();
		}
		finally {
			httpServletRequestWrapperImpl.pop();
		}
	}

	private void _throwException(Exception exception)
		throws IOException, ServletException {

		if (exception instanceof RuntimeException) {
			throw (RuntimeException)exception;
		}
		else if (exception instanceof IOException) {
			throw (IOException)exception;
		}
		else if (exception instanceof ServletException) {
			throw (ServletException)exception;
		}
	}

	private final DispatchTargets _dispatchTargets;
	private Exception _exception;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;

}