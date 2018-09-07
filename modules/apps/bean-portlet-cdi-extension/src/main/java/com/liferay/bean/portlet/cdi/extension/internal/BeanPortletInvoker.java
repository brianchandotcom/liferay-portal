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

import com.liferay.bean.portlet.cdi.extension.internal.scope.ScopedBeanHolder;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.PrintWriter;

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionParameters;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Event;
import javax.portlet.EventPortlet;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.HeaderPortlet;
import javax.portlet.HeaderRequest;
import javax.portlet.HeaderResponse;
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
import javax.portlet.ResourceServingPortlet;

/**
 * @author Neil Griffin
 */
public class BeanPortletInvoker
	implements EventPortlet, HeaderPortlet, Portlet, ResourceServingPortlet {

	public BeanPortletInvoker(Map<MethodType, List<BeanMethod>> beanMethods) {
		_beanMethods = beanMethods;
	}

	@Override
	public void destroy() {
		try {
			invokeBeanMethods(_beanMethods.get(MethodType.DESTROY));
		}
		catch (PortletException pe) {
			_log.error(pe, pe);
		}
	}

	@Override
	public void init(PortletConfig portletConfig) throws PortletException {
		invokeBeanMethods(_beanMethods.get(MethodType.INIT), portletConfig);

		_portletConfig = portletConfig;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortletException {

		invokeBeanMethods(
			actionRequest, actionResponse, _beanMethods.get(MethodType.ACTION));
	}

	@Override
	public void processEvent(
			EventRequest eventRequest, EventResponse eventResponse)
		throws IOException, PortletException {

		List<BeanMethod> beanMethods = _beanMethods.get(MethodType.EVENT);

		if (beanMethods == null) {
			return;
		}

		Event event = eventRequest.getEvent();

		List<BeanMethod> eventMethods = new ArrayList<>();

		for (BeanMethod beanMethod : beanMethods) {
			if (beanMethod.isEventProcessor(event.getQName())) {
				eventMethods.add(beanMethod);
			}
		}

		if (!eventMethods.isEmpty()) {
			invokeBeanMethods(eventRequest, eventResponse, eventMethods);
		}
	}

	@Override
	public void render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		invokeBeanMethods(
			renderRequest, renderResponse, _beanMethods.get(MethodType.RENDER));
	}

	@Override
	public void renderHeaders(
			HeaderRequest headerRequest, HeaderResponse headerResponse)
		throws IOException, PortletException {

		invokeBeanMethods(
			headerRequest, headerResponse, _beanMethods.get(MethodType.HEADER));
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortletException {

		invokeBeanMethods(
			resourceRequest, resourceResponse,
			_beanMethods.get(MethodType.SERVE_RESOURCE));
	}

	protected void invokeBeanMethod(BeanMethod beanMethod, Object... args)
		throws IllegalAccessException, InvocationTargetException, IOException,
			   PortletException {

		if (beanMethod.getType() == MethodType.ACTION) {
			ActionRequest actionRequest = (ActionRequest)args[0];

			ActionParameters actionParameters =
				actionRequest.getActionParameters();

			String actionName = actionParameters.getValue(
				ActionRequest.ACTION_NAME);

			if ((actionName == null) ||
				actionName.equals(beanMethod.getActionName())) {

				beanMethod.invoke(args);
			}
		}
		else if (beanMethod.getType() == MethodType.RENDER) {
			RenderRequest renderRequest = (RenderRequest)args[0];

			PortletMode portletMode = renderRequest.getPortletMode();

			PortletMode beanMethodPortletMode = beanMethod.getPortletMode();

			if ((beanMethodPortletMode == null) ||
				portletMode.equals(beanMethodPortletMode)) {

				if (beanMethod.getParameterCount() == 0) {
					String markup = (String)beanMethod.invoke();

					if (markup != null) {
						RenderResponse renderResponse = (RenderResponse)
							args[1];

						PrintWriter writer = renderResponse.getWriter();

						writer.write(markup);
					}
				}
				else {
					beanMethod.invoke(args);
				}
			}
		}
		else if ((beanMethod.getType() == MethodType.SERVE_RESOURCE) &&
				 (beanMethod.getParameterCount() == 0)) {

			String markup = (String)beanMethod.invoke();

			if (markup != null) {
				ResourceResponse resourceResponse = (ResourceResponse)args[1];

				PrintWriter writer = resourceResponse.getWriter();

				writer.write(markup);
			}
		}
		else {
			beanMethod.invoke(args);
		}

		String include = beanMethod.getInclude();

		if (Validator.isNotNull(include)) {
			PortletContext portletContext = _portletConfig.getPortletContext();

			PortletRequestDispatcher portletRequestDispatcher =
				portletContext.getRequestDispatcher(include);

			if (portletRequestDispatcher != null) {
				portletRequestDispatcher.include(
					(PortletRequest)args[0], (PortletResponse)args[1]);
			}
			else {
				_log.error(
					"Unable to acquire dispatcher for include=" + include);
			}
		}
	}

	protected void invokeBeanMethods(
			List<BeanMethod> beanMethods, Object... args)
		throws PortletException {

		if (beanMethods == null) {
			return;
		}

		for (BeanMethod beanMethod : beanMethods) {
			try {
				invokeBeanMethod(beanMethod, args);
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

	protected void invokeBeanMethods(
			PortletRequest portletRequest, PortletResponse portletResponse,
			List<BeanMethod> beanMethods)
		throws PortletException {

		if (beanMethods == null) {
			return;
		}

		ScopedBeanHolder scopedBeanHolder = new ScopedBeanHolder(
			portletRequest, portletResponse, _portletConfig);

		ScopedBeanHolder.setCurrentInstance(scopedBeanHolder);

		invokeBeanMethods(beanMethods, portletRequest, portletResponse);
		scopedBeanHolder.release();
		ScopedBeanHolder.setCurrentInstance(null);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BeanPortletInvoker.class);

	private final Map<MethodType, List<BeanMethod>> _beanMethods;
	private PortletConfig _portletConfig;

}