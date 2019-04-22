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

package com.liferay.bean.portlet.cdi.extension.internal;

import com.liferay.bean.portlet.cdi.extension.internal.scope.ScopedBeanManager;
import com.liferay.bean.portlet.cdi.extension.internal.scope.ScopedBeanManagerThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.PrintWriter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.ActionParameters;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
import javax.portlet.MimeResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Neil Griffin
 */
public class BeanPortletInvokerPortlet implements InvokerPortlet {

	public BeanPortletInvokerPortlet(
		Map<MethodType, List<BeanMethod>> beanMethods) {

		_beanMethods = beanMethods;

		boolean facesPortlet = false;

		for (Map.Entry<MethodType, List<BeanMethod>> entry :
				beanMethods.entrySet()) {

			List<BeanMethod> beanMethodList = entry.getValue();

			for (BeanMethod beanMethod : beanMethodList) {
				Method method = beanMethod.getMethod();

				Class<?> declaringClass = method.getDeclaringClass();

				if (ClassUtil.isSubclass(
						declaringClass,
						"javax.portlet.faces.GenericFacesPortlet")) {

					facesPortlet = true;

					break;
				}
			}

			if (facesPortlet) {
				break;
			}
		}

		_facesPortlet = facesPortlet;
	}

	@Override
	public void destroy() {
		try {
			_invokeBeanMethods(_beanMethods.get(MethodType.DESTROY));
		}
		catch (PortletException pe) {
			_log.error(pe, pe);
		}
	}

	@Override
	public Integer getExpCache() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Portlet getPortlet() {
		return this;
	}

	@Override
	public ClassLoader getPortletClassLoader() {
		Class<? extends BeanPortletInvokerPortlet> portletClass = getClass();

		return portletClass.getClassLoader();
	}

	@Override
	public PortletConfig getPortletConfig() {
		return _portletConfig;
	}

	@Override
	public PortletContext getPortletContext() {
		return _portletConfig.getPortletContext();
	}

	@Override
	public Portlet getPortletInstance() {
		return this;
	}

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		_invokeBeanMethods(_beanMethods.get(MethodType.INIT), portletConfig);

		_portletConfig = portletConfig;
	}

	@Override
	public boolean isCheckAuthToken() {
		return GetterUtil.getBoolean(
			_portletConfig.getInitParameter("check-auth-token"));
	}

	public boolean isFacesPortlet() {
		return _facesPortlet;
	}

	@Override
	public boolean isHeaderPortlet() {
		return true;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isStrutsBridgePortlet() {
		return false;
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isStrutsPortlet() {
		return false;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		_invokeBeanMethods(
			actionRequest, actionResponse, _beanMethods.get(MethodType.ACTION));
	}

	@Override
	public void processEvent(
			EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, PortletException {

		List<BeanMethod> beanMethods = _beanMethods.get(MethodType.EVENT);

		if ((beanMethods == null) || beanMethods.isEmpty()) {
			return;
		}

		Event event = eventRequest.getEvent();

		List<BeanMethod> eventMethods = new ArrayList<>();

		for (BeanMethod beanMethod : beanMethods) {
			if (beanMethod.isEventProcessor(event.getQName())) {
				eventMethods.add(beanMethod);
			}
		}

		_invokeBeanMethods(eventRequest, eventResponse, eventMethods);
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		_invokeBeanMethods(
			renderRequest, renderResponse, _beanMethods.get(MethodType.RENDER));
	}

	@Override
	public void renderHeaders(
			HeaderRequest headerRequest, HeaderResponse headerResponse)
		throws IOException, PortletException {

		_invokeBeanMethods(
			headerRequest, headerResponse, _beanMethods.get(MethodType.HEADER));
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		_invokeBeanMethods(
			resourceRequest, resourceResponse,
			_beanMethods.get(MethodType.SERVE_RESOURCE));
	}

	@Override
	public void setPortletFilters() throws PortletException {
		throw new UnsupportedOperationException();
	}

	private void _invokeBeanMethod(BeanMethod beanMethod, Object... args)
		throws IOException, PortletException, ReflectiveOperationException {

		String include = null;
		Method method = beanMethod.getMethod();

		MethodType methodType = beanMethod.getMethodType();

		if (methodType == MethodType.ACTION) {
			ActionRequest actionRequest = (ActionRequest)args[0];

			ActionParameters actionParameters =
				actionRequest.getActionParameters();

			String actionName = actionParameters.getValue(
				ActionRequest.ACTION_NAME);

			String beanMethodActionName = beanMethod.getActionName();

			if (Validator.isNull(beanMethodActionName) ||
				beanMethodActionName.equals(actionName)) {

				beanMethod.invoke(args);
			}
		}
		else if ((methodType == MethodType.HEADER) ||
				 (methodType == MethodType.RENDER)) {

			PortletRequest portletRequest = (PortletRequest)args[0];

			PortletMode portletMode = portletRequest.getPortletMode();

			PortletMode beanMethodPortletMode = beanMethod.getPortletMode();

			if ((beanMethodPortletMode == null) ||
				portletMode.equals(beanMethodPortletMode)) {

				if (method.getParameterCount() == 0) {
					String markup = (String)beanMethod.invoke();

					if (markup != null) {
						MimeResponse mimeResponse = (MimeResponse)args[1];

						PrintWriter printWriter = mimeResponse.getWriter();

						printWriter.write(markup);
					}
				}
				else {
					beanMethod.invoke(args);
				}

				include = methodType.getInclude(method);
			}
		}
		else if (methodType == MethodType.SERVE_RESOURCE) {
			ResourceRequest resourceRequest = (ResourceRequest)args[0];

			String resourceID = resourceRequest.getResourceID();

			String beanMethodResourceID = beanMethod.getResourceID();

			if (Validator.isNull(beanMethodResourceID) ||
				beanMethodResourceID.equals(resourceID)) {

				ResourceResponse resourceResponse = (ResourceResponse)args[1];

				String contentType = methodType.getContentType(method);

				if (Validator.isNotNull(contentType) &&
					!Objects.equals(contentType, "*/*")) {

					resourceResponse.setContentType(contentType);
				}

				String characterEncoding = methodType.getCharacterEncoding(
					method);

				if (Validator.isNotNull(characterEncoding)) {
					resourceResponse.setCharacterEncoding(characterEncoding);
				}

				if (method.getParameterCount() == 0) {
					String markup = (String)beanMethod.invoke();

					if (Validator.isNotNull(markup)) {
						PrintWriter printWriter = resourceResponse.getWriter();

						printWriter.write(markup);
					}
				}
				else {
					beanMethod.invoke(args);
				}

				include = methodType.getInclude(method);
			}
		}
		else {
			beanMethod.invoke(args);
		}

		if (Validator.isNull(include)) {
			return;
		}

		PortletMode beanMethodPortletMode = beanMethod.getPortletMode();

		if (beanMethodPortletMode != null) {
			PortletRequest portletRequest = (PortletRequest)args[0];

			if (!beanMethodPortletMode.equals(
					portletRequest.getPortletMode())) {

				return;
			}
		}

		PortletContext portletContext = _portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(include);

		if (portletRequestDispatcher == null) {
			_log.error("Unable to acquire dispatcher to include " + include);
		}
		else {
			portletRequestDispatcher.include(
				(PortletRequest)args[0], (PortletResponse)args[1]);
		}
	}

	private void _invokeBeanMethods(
			List<BeanMethod> beanMethods, Object... args)
		throws PortletException {

		if ((beanMethods == null) || beanMethods.isEmpty()) {
			return;
		}

		for (BeanMethod beanMethod : beanMethods) {
			try {
				_invokeBeanMethod(beanMethod, args);
			}
			catch (InvocationTargetException ite) {
				Throwable cause = ite.getCause();

				if (cause instanceof PortletException) {
					throw (PortletException)cause;
				}

				throw new PortletException(cause);
			}
			catch (PortletException pe) {
				throw pe;
			}
			catch (Exception e) {
				throw new PortletException(e);
			}
		}
	}

	private void _invokeBeanMethods(
			PortletRequest portletRequest, PortletResponse portletResponse,
			List<BeanMethod> beanMethods)
		throws PortletException {

		if ((beanMethods == null) || beanMethods.isEmpty()) {
			return;
		}

		ScopedBeanManagerThreadLocal.invokeWithScopedBeanManager(
			() -> _invokeBeanMethods(
				beanMethods, portletRequest, portletResponse),
			() -> new ScopedBeanManager(
				portletRequest, portletResponse, _portletConfig));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanPortletInvokerPortlet.class);

	private final Map<MethodType, List<BeanMethod>> _beanMethods;
	private final boolean _facesPortlet;
	private PortletConfig _portletConfig;

}