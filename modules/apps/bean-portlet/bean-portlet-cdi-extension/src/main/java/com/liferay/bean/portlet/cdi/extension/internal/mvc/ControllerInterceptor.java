/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bean.portlet.cdi.extension.internal.mvc;

import com.liferay.bean.portlet.extension.BeanPortletMethodType;
import com.liferay.bean.portlet.extension.ViewRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.Serializable;

import java.lang.reflect.Method;

import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Priority;

import javax.enterprise.event.Event;

import javax.inject.Inject;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import javax.mvc.View;
import javax.mvc.event.MvcEvent;

import javax.portlet.ActionResponse;
import javax.portlet.BaseURL;
import javax.portlet.MimeResponse;
import javax.portlet.MutableRenderParameters;
import javax.portlet.MutableResourceParameters;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderParameters;
import javax.portlet.ResourceResponse;
import javax.portlet.annotations.ActionMethod;
import javax.portlet.annotations.DestroyMethod;
import javax.portlet.annotations.EventMethod;
import javax.portlet.annotations.InitMethod;
import javax.portlet.annotations.RenderMethod;
import javax.portlet.filter.RenderURLWrapper;
import javax.portlet.filter.ResourceURLWrapper;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Neil Griffin
 */
@ControllerInterceptorBinding
@Interceptor
@Priority(Interceptor.Priority.LIBRARY_BEFORE)
public class ControllerInterceptor implements Serializable {

	@AroundInvoke
	public Object processView(InvocationContext invocationContext)
		throws Exception {

		// Set the viewName request attribute before invoking the controller
		// @RenderMethod or @ServeResourceMethod. This makes it possible for
		// the developer to determine the viewName that may have been set by an
		// @ActionMethod in the ACTION_PHASE by calling the
		// ViewEngineContext.getView() method.

		String actionPhaseViewName = null;

		Method invocationContextMethod = invocationContext.getMethod();

		BeanPortletMethodType beanPortletMethodType = _getMethodType(
			invocationContextMethod);

		if (((beanPortletMethodType == BeanPortletMethodType.RENDER) ||
			 (beanPortletMethodType == BeanPortletMethodType.SERVE_RESOURCE)) &&
			(_renderParameters.getValue(ViewRenderer.REDIRECTED_VIEW) !=
				null)) {

			PortletSession portletSession = _portletRequest.getPortletSession(
				true);

			actionPhaseViewName = (String)portletSession.getAttribute(
				ViewRenderer.VIEW_NAME);

			portletSession.removeAttribute(ViewRenderer.VIEW_NAME);

			if (actionPhaseViewName != null) {
				_portletRequest.setAttribute(
					ViewRenderer.VIEW_NAME, actionPhaseViewName);
			}
		}

		Object target = invocationContext.getTarget();

		_mvcEvent.fire(
			new BeforeControllerEventImpl(
				new ResourceInfoImpl(
					target.getClass(), invocationContextMethod),
				new UriInfoImpl()));

		Object result = invocationContext.proceed();

		BaseURL redirectURL = null;

		boolean renderView = true;

		String viewName = null;

		if (Validator.isNull(result)) {
			View view = invocationContextMethod.getAnnotation(View.class);

			if (view != null) {
				viewName = view.value();
			}
		}
		else {
			viewName = result.toString();
		}

		if (Validator.isNotNull(viewName) &&
			((beanPortletMethodType == BeanPortletMethodType.ACTION) ||
			 (beanPortletMethodType == BeanPortletMethodType.SERVE_RESOURCE))) {

			PortletSession portletSession = _portletRequest.getPortletSession(
				true);

			if (viewName.startsWith(ViewRenderer.REDIRECT_PREFIX)) {
				viewName = viewName.substring(
					ViewRenderer.REDIRECT_PREFIX.length());

				if (beanPortletMethodType == BeanPortletMethodType.ACTION) {
					redirectURL = new ActionRedirectURL(_actionResponse);
				}
				else {
					redirectURL = new ResourceRedirectURL(_resourceResponse);
				}
			}

			portletSession.setAttribute(ViewRenderer.VIEW_NAME, viewName);

			if (beanPortletMethodType == BeanPortletMethodType.ACTION) {
				if (redirectURL == null) {
					MutableRenderParameters mutableRenderParameters =
						_actionResponse.getRenderParameters();

					mutableRenderParameters.setValue(
						ViewRenderer.REDIRECTED_VIEW, Boolean.TRUE.toString());
				}
				else {
					try {
						_actionResponse.sendRedirect(redirectURL.toString());
					}
					catch (IOException ioException) {
						_log.error(ioException);
					}
				}
			}
			else {
				if (redirectURL != null) {
					_resourceResponse.setStatus(
						HttpServletResponse.SC_MOVED_TEMPORARILY);
					_resourceResponse.addProperty(
						HttpHeaders.LOCATION, redirectURL.toString());

					renderView = false;
				}
			}
		}

		if (renderView) {
			if (Validator.isNull(viewName)) {
				viewName = actionPhaseViewName;
			}

			if (Validator.isNotNull(viewName)) {
				_portletRequest.setAttribute(ViewRenderer.VIEW_NAME, viewName);
			}
		}

		target = invocationContext.getTarget();

		_mvcEvent.fire(
			new AfterControllerEventImpl(
				new ResourceInfoImpl(
					target.getClass(), invocationContextMethod),
				new UriInfoImpl()));

		if (redirectURL != null) {
			try {
				URI uri = new URI(redirectURL.toString());

				_mvcEvent.fire(
					new ControllerRedirectEventImpl(
						uri,
						new ResourceInfoImpl(
							target.getClass(), invocationContextMethod),
						new UriInfoImpl()));
			}
			catch (URISyntaxException uriSyntaxException) {
				_log.error(uriSyntaxException);
			}
		}

		return null;
	}

	private BeanPortletMethodType _getMethodType(
		Method invocationContextMethod) {

		if (invocationContextMethod.isAnnotationPresent(ActionMethod.class)) {
			return BeanPortletMethodType.ACTION;
		}

		if (invocationContextMethod.isAnnotationPresent(DestroyMethod.class)) {
			return BeanPortletMethodType.DESTROY;
		}

		if (invocationContextMethod.isAnnotationPresent(EventMethod.class)) {
			return BeanPortletMethodType.EVENT;
		}

		if (invocationContextMethod.isAnnotationPresent(InitMethod.class)) {
			return BeanPortletMethodType.INIT;
		}

		if (invocationContextMethod.isAnnotationPresent(RenderMethod.class)) {
			return BeanPortletMethodType.RENDER;
		}

		return BeanPortletMethodType.SERVE_RESOURCE;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ControllerInterceptor.class);

	private static final long serialVersionUID = 1573287230987622411L;

	@Inject
	private ActionResponse _actionResponse;

	@Inject
	private Event<MvcEvent> _mvcEvent;

	@Inject
	private PortletRequest _portletRequest;

	@Inject
	private RenderParameters _renderParameters;

	@Inject
	private ResourceResponse _resourceResponse;

	private static class ActionRedirectURL extends RenderURLWrapper {

		private ActionRedirectURL(ActionResponse actionResponse) {
			super(actionResponse.createRedirectURL(MimeResponse.Copy.ALL));

			MutableRenderParameters mutableRenderParameters =
				getRenderParameters();

			mutableRenderParameters.setValue(
				ViewRenderer.REDIRECTED_VIEW, Boolean.TRUE.toString());
		}

	}

	private static class ResourceRedirectURL extends ResourceURLWrapper {

		private ResourceRedirectURL(ResourceResponse resourceResponse) {
			super(resourceResponse.createResourceURL());

			MutableResourceParameters mutableResourceParameters =
				getResourceParameters();

			mutableResourceParameters.setValue(
				ViewRenderer.REDIRECTED_VIEW, Boolean.TRUE.toString());
		}

	}

}