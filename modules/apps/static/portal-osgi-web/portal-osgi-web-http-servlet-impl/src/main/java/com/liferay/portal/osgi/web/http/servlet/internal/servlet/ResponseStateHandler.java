/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.http.servlet.internal.servlet;

import com.liferay.portal.osgi.web.http.servlet.internal.context.LiferayContextController;
import com.liferay.portal.osgi.web.http.servlet.internal.context.LiferayDispatchTargets;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.IOException;

import java.util.Collections;
import java.util.List;

import org.eclipse.equinox.http.servlet.internal.registration.EndpointRegistration;
import org.eclipse.equinox.http.servlet.internal.registration.FilterRegistration;
import org.eclipse.equinox.http.servlet.internal.servlet.FilterChainImpl;
import org.eclipse.equinox.http.servlet.internal.servlet.HttpServletRequestWrapperImpl;
import org.eclipse.equinox.http.servlet.internal.servlet.HttpServletResponseWrapperImpl;
import org.eclipse.equinox.http.servlet.internal.servlet.Match;
import org.eclipse.equinox.http.servlet.internal.util.EventListeners;

import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Dante Wang
 */
public class ResponseStateHandler {

	public ResponseStateHandler(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		LiferayDispatchTargets liferayDispatchTargets) {

		_httpServletRequest = httpServletRequest;
		_httpServletResponse = httpServletResponse;
		_liferayDispatchTargets = liferayDispatchTargets;
	}

	public void processRequest() throws IOException, ServletException {
		LiferayContextController liferayContextController =
			_liferayDispatchTargets.getContextController();

		EventListeners eventListeners =
			liferayContextController.getEventListeners();

		List<ServletRequestListener> servletRequestListeners =
			eventListeners.get(ServletRequestListener.class);

		EndpointRegistration<?> endpointRegistration =
			_liferayDispatchTargets.getServletRegistration();

		List<FilterRegistration> matchingFilterRegistrations =
			_liferayDispatchTargets.getMatchingFilterRegistrations();

		endpointRegistration.addReference();

		for (FilterRegistration filterRegistration :
				matchingFilterRegistrations) {

			filterRegistration.addReference();
		}

		ServletRequestEvent servletRequestEvent = null;

		try {
			if ((_liferayDispatchTargets.getDispatcherType() ==
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

				if (matchingFilterRegistrations.isEmpty()) {
					endpointRegistration.service(
						_httpServletRequest, _httpServletResponse);
				}
				else {
					Collections.sort(matchingFilterRegistrations);

					FilterChain filterChain = new FilterChainImpl(
						matchingFilterRegistrations, endpointRegistration,
						_liferayDispatchTargets.getDispatcherType());

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

			if (_liferayDispatchTargets.getDispatcherType() !=
					DispatcherType.REQUEST) {

				_throwException(exception);
			}
		}
		finally {
			endpointRegistration.removeReference();

			for (FilterRegistration filterRegistration :
					matchingFilterRegistrations) {

				filterRegistration.removeReference();
			}

			if (_liferayDispatchTargets.getDispatcherType() ==
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
			throw new IllegalStateException(
				"Response is not a HttpServletResponseWrapper");
		}

		HttpServletResponseWrapper httpServletResponseWrapper1 =
			(HttpServletResponseWrapper)_httpServletResponse;
		HttpServletResponseWrapperImpl httpServletResponseWrapperImpl = null;

		while (true) {
			if (httpServletResponseWrapper1 instanceof
					HttpServletResponseWrapperImpl) {

				httpServletResponseWrapperImpl =
					(HttpServletResponseWrapperImpl)httpServletResponseWrapper1;

				break;
			}

			if (!(httpServletResponseWrapper1.getResponse() instanceof
					HttpServletResponseWrapper)) {

				break;
			}

			httpServletResponseWrapper1 =
				(HttpServletResponseWrapper)
					httpServletResponseWrapper1.getResponse();
		}

		if (httpServletResponseWrapperImpl == null) {
			throw new IllegalStateException(
				"Can not locate HttpServletResponseWrapperImpl");
		}

		HttpServletResponse wrappedHttpServletResponse =
			(HttpServletResponse)httpServletResponseWrapperImpl.getResponse();

		if (wrappedHttpServletResponse.isCommitted()) {
			_throwException(_exception);
		}

		LiferayContextController liferayContextController =
			_liferayDispatchTargets.getContextController();

		Class<? extends Exception> clazz = _exception.getClass();

		String className = clazz.getName();

		LiferayDispatchTargets errorLiferayDispatchTargets =
			liferayContextController.getDispatchTargets(
				className, (String)null, (String)null, (String)null,
				(String)null, (String)null, Match.EXACT);

		if (errorLiferayDispatchTargets == null) {
			_throwException(_exception);
		}

		HttpServletRequestWrapperImpl httpServletRequestWrapperImpl =
			HttpServletRequestWrapperImpl.findHttpRuntimeRequest(
				_httpServletRequest);

		try {
			errorLiferayDispatchTargets.setDispatcherType(DispatcherType.ERROR);

			httpServletRequestWrapperImpl.push(errorLiferayDispatchTargets);

			HttpServletRequest httpServletRequest =
				new HttpServletRequestWrapper(_httpServletRequest) {

					public Object getAttribute(String attributeName) {
						if (getDispatcherType() == DispatcherType.ERROR) {
							if (attributeName.equals(
									"jakarta.servlet.error.exception")) {

								return ResponseStateHandler.this._exception;
							}

							if (attributeName.equals(
									"jakarta.servlet.error.exception_type")) {

								return className;
							}

							if (attributeName.equals(
									"jakarta.servlet.error.message")) {

								return ResponseStateHandler.this._exception.
									getMessage();
							}

							if (attributeName.equals(
									"jakarta.servlet.error.request_uri")) {

								return ResponseStateHandler.this.
									_httpServletRequest.getRequestURI();
							}

							if (attributeName.equals(
									"jakarta.servlet.error.servlet_name")) {

								return ResponseStateHandler.this.
									_liferayDispatchTargets.
										getServletRegistration(
										).getName();
							}

							if (attributeName.equals(
									"jakarta.servlet.error.status_code")) {

								return 500;
							}
						}

						return super.getAttribute(attributeName);
					}

					public DispatcherType getDispatcherType() {
						return DispatcherType.ERROR;
					}

				};

			HttpServletResponseWrapper httpServletResponseWrapper2 =
				new HttpServletResponseWrapperImpl(wrappedHttpServletResponse);

			ResponseStateHandler responseStateHandler =
				new ResponseStateHandler(
					httpServletRequest, httpServletResponseWrapper2,
					(LiferayDispatchTargets)errorLiferayDispatchTargets);

			responseStateHandler.processRequest();

			wrappedHttpServletResponse.setStatus(500);
		}
		finally {
			httpServletRequestWrapperImpl.pop();
		}
	}

	private void _handleResponseCode() throws IOException, ServletException {
		if (!(_httpServletResponse instanceof HttpServletResponseWrapper)) {
			throw new IllegalStateException(
				"Response is not a HttpServletResponseWrapper");
		}

		HttpServletResponseWrapperImpl httpServletResponseWrapperImpl =
			HttpServletResponseWrapperImpl.findHttpRuntimeResponse(
				_httpServletResponse);

		if (httpServletResponseWrapperImpl == null) {
			throw new IllegalStateException(
				"Can not locate HttpServletResponseWrapperImpl");
		}

		final int status = httpServletResponseWrapperImpl.getInternalStatus();

		if ((status < 400) || (status == -1)) {
			return;
		}

		HttpServletResponse wrappedHttpServletResponse =
			(HttpServletResponse)httpServletResponseWrapperImpl.getResponse();

		if (wrappedHttpServletResponse.isCommitted()) {
			return;
		}

		LiferayContextController liferayContextController =
			_liferayDispatchTargets.getContextController();

		LiferayDispatchTargets errorLiferayDispatchTargets =
			liferayContextController.getDispatchTargets(
				String.valueOf(status), (String)null, (String)null,
				(String)null, (String)null, (String)null, Match.EXACT);

		if (errorLiferayDispatchTargets == null) {
			wrappedHttpServletResponse.sendError(
				status, httpServletResponseWrapperImpl.getMessage());

			return;
		}

		HttpServletRequestWrapperImpl httpServletRequestWrapperImpl =
			HttpServletRequestWrapperImpl.findHttpRuntimeRequest(
				_httpServletRequest);

		try {
			errorLiferayDispatchTargets.setDispatcherType(DispatcherType.ERROR);

			httpServletRequestWrapperImpl.push(errorLiferayDispatchTargets);

			HttpServletRequest httpServletRequest =
				new HttpServletRequestWrapper(_httpServletRequest) {

					public Object getAttribute(String attributeName) {
						if (getDispatcherType() == DispatcherType.ERROR) {
							if (attributeName.equals(
									"jakarta.servlet.error.message")) {

								return httpServletResponseWrapperImpl.
									getMessage();
							}

							if (attributeName.equals(
									"jakarta.servlet.error.request_uri")) {

								return ResponseStateHandler.this.
									_httpServletRequest.getRequestURI();
							}

							if (attributeName.equals(
									"jakarta.servlet.error.servlet_name")) {

								return ResponseStateHandler.this.
									_liferayDispatchTargets.
										getServletRegistration(
										).getName();
							}

							if (attributeName.equals(
									"jakarta.servlet.error.status_code")) {

								return status;
							}
						}

						return super.getAttribute(attributeName);
					}

					public DispatcherType getDispatcherType() {
						return DispatcherType.ERROR;
					}

				};

			HttpServletResponseWrapper httpServletResponseWrapper =
				new HttpServletResponseWrapperImpl(wrappedHttpServletResponse);

			ResponseStateHandler responseStateHandler =
				new ResponseStateHandler(
					httpServletRequest, httpServletResponseWrapper,
					(LiferayDispatchTargets)errorLiferayDispatchTargets);

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

	private Exception _exception;
	private final HttpServletRequest _httpServletRequest;
	private final HttpServletResponse _httpServletResponse;
	private final LiferayDispatchTargets _liferayDispatchTargets;

}