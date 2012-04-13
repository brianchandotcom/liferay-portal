/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.PortletContainer;
import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.PortletDisplayFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.SerializableUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.Event;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Shuyang Zhou
 */
public class PortletContainerImpl implements PortletContainer {

	public void preparePortletServing(
			HttpServletRequest request, Portlet portlet)
		throws PortletContainerException {

		try {
			_doPreparePortletServing(request, portlet);
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	public void processAction(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		try {
			_doProcessAction(request, response, portlet);
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	public void processEvent(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, Layout layout, Event event)
		throws PortletContainerException {

		try {
			_doProcessEvent(request, response, portlet, layout, event);
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	public void render(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		try {
			_doRender(request, response, portlet);
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	public void serveResource(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		try {
			_doServeResource(request, response, portlet);
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	protected List<LayoutTypePortlet> getLayoutTypePortlets(
			Layout requestLayout)
		throws Exception {

		if (PropsValues.PORTLET_EVENT_DISTRIBUTION_LAYOUT_SET) {
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				requestLayout.getGroupId(), requestLayout.isPrivateLayout(),
				LayoutConstants.TYPE_PORTLET);

			List<LayoutTypePortlet> layoutTypePortlets =
				new ArrayList<LayoutTypePortlet>(layouts.size());

			for (Layout layout : layouts) {
				LayoutTypePortlet layoutTypePortlet =
					(LayoutTypePortlet)layout.getLayoutType();

				layoutTypePortlets.add(layoutTypePortlet);
			}

			return layoutTypePortlets;
		}

		if (requestLayout.isTypePortlet()) {
			List<LayoutTypePortlet> layoutTypePortlets =
				new ArrayList<LayoutTypePortlet>(1);

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)requestLayout.getLayoutType();

			layoutTypePortlets.add(layoutTypePortlet);

			return layoutTypePortlets;
		}

		return Collections.emptyList();
	}

	protected long getScopeGroupId(
			HttpServletRequest request, Layout layout, String portletId)
		throws PortalException, SystemException {

		long scopeGroupId = 0;

		Layout requestLayout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		try {
			request.setAttribute(WebKeys.LAYOUT, layout);

			scopeGroupId = PortalUtil.getScopeGroupId(request, portletId);
		}
		finally {
			request.setAttribute(WebKeys.LAYOUT, requestLayout);
		}

		if (scopeGroupId <= 0) {
			scopeGroupId = PortalUtil.getScopeGroupId(layout, portletId);
		}

		return scopeGroupId;
	}

	protected void processEvents(
			HttpServletRequest request, HttpServletResponse response,
			List<Event> events)
		throws Exception {

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		for (LayoutTypePortlet layoutTypePortlet :
			getLayoutTypePortlets(layout)) {

			List<Portlet> portlets = layoutTypePortlet.getAllPortlets();

			for (Portlet portlet : portlets) {

				for (Event event : events) {
					javax.xml.namespace.QName qName = event.getQName();

					QName processingQName = portlet.getProcessingEvent(
						qName.getNamespaceURI(), qName.getLocalPart());

					if (processingQName != null) {
						_doProcessEvent(
							request, response, portlet,
							layoutTypePortlet.getLayout(), event);
					}
				}
			}
		}
	}

	protected void processPublicRenderParameters(
		HttpServletRequest request, Layout layout, Portlet portlet) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String[]> publicRenderParameters =
			PublicRenderParametersPool.get(request, layout.getPlid());

		Map<String, String[]> parameters = request.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String name = entry.getKey();

			QName qName = PortletQNameUtil.getQName(name);

			if (qName == null) {
				continue;
			}

			PublicRenderParameter publicRenderParameter =
				portlet.getPublicRenderParameter(
					qName.getNamespaceURI(), qName.getLocalPart());

			if (publicRenderParameter == null) {
				continue;
			}

			String publicRenderParameterName =
				PortletQNameUtil.getPublicRenderParameterName(qName);

			if (name.startsWith(
					PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE)) {

				String[] values = entry.getValue();

				if (themeDisplay.isLifecycleAction()) {
					String[] oldValues = publicRenderParameters.get(
						publicRenderParameterName);

					if ((oldValues != null) && (oldValues.length != 0)) {
						values = ArrayUtil.append(values, oldValues);
					}
				}

				publicRenderParameters.put(publicRenderParameterName, values);
			}
			else {
				publicRenderParameters.remove(publicRenderParameterName);
			}
		}
	}

	protected Event serializeEvent(
		Event event, ClassLoader portletClassLoader) {

		Serializable value = event.getValue();

		if (value == null) {
			return event;
		}

		Class<?> valueClass = value.getClass();

		String valueClassName = valueClass.getName();

		try {
			Class<?> loadedValueClass = portletClassLoader.loadClass(
				valueClassName);

			if (loadedValueClass.equals(valueClass)) {
				return event;
			}
		}
		catch (ClassNotFoundException cnfe) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					portletClassLoader.toString() + " does not contain " +
						valueClassName,
					cnfe);
			}
		}

		EventImpl eventImpl = (EventImpl)event;

		byte[] serializedValue = eventImpl.getSerializedValue();

		value = (Serializable)SerializableUtil.deserialize(
			serializedValue, portletClassLoader);

		return new EventImpl(event.getName(), event.getQName(), value);
	}

	private void _doPreparePortletServing(
			HttpServletRequest request, Portlet portlet)
		throws Exception {

		User user = PortalUtil.getUser(request);
		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		String portletId = portlet.getPortletId();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = PortalUtil.getScopeGroupId(request, portletId);

		themeDisplay.setScopeGroupId(scopeGroupId);

		if (user != null) {
			HttpSession session = request.getSession();

			InvokerPortletImpl.clearResponse(
				session, layout.getPrimaryKey(), portletId,
				LanguageUtil.getLanguageId(request));
		}

		processPublicRenderParameters(request, layout, portlet);

		if (themeDisplay.isLifecycleRender() ||
			themeDisplay.isLifecycleResource()) {

			WindowState windowState = WindowStateFactory.getWindowState(
				ParamUtil.getString(request, "p_p_state"));

			if (layout.isTypeControlPanel() &&
				((windowState == null) ||
					windowState.equals(WindowState.NORMAL) ||
					Validator.isNull(windowState.toString()))) {

				windowState = WindowState.MAXIMIZED;
			}

			PortletMode portletMode = PortletModeFactory.getPortletMode(
				ParamUtil.getString(request, "p_p_mode"));

			PortalUtil.updateWindowState(
				portletId, user, layout, windowState, request);

			PortalUtil.updatePortletMode(
				portletId, user, layout, portletMode, request);
		}
	}

	private void _doProcessAction(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws Exception {

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		String portletId = portlet.getPortletId();

		WindowState windowState = WindowStateFactory.getWindowState(
			ParamUtil.getString(request, "p_p_state"));

		if (layout.isTypeControlPanel() &&
			((windowState == null) || windowState.equals(WindowState.NORMAL) ||
			 Validator.isNull(windowState.toString()))) {

			windowState = WindowState.MAXIMIZED;
		}

		PortletMode portletMode = PortletModeFactory.getPortletMode(
			ParamUtil.getString(request, "p_p_mode"));

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				request, portletId);

		PortletPreferences portletPreferences = null;

		if (PortalUtil.isAllowAddPortletDefaultResource(request, portlet)) {
			portletPreferences =
				PortletPreferencesLocalServiceUtil.getPreferences(
					portletPreferencesIds);
		}
		else {
			portletPreferences =
				PortletPreferencesLocalServiceUtil.getStrictPreferences(
					portletPreferencesIds);
		}

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		InvokerPortlet invokerPortlet = PortletInstanceFactoryUtil.create(
			portlet, servletContext);

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);
		PortletContext portletContext = portletConfig.getPortletContext();

		String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);

		if (_log.isDebugEnabled()) {
			_log.debug("Content type " + contentType);
		}

		UploadServletRequest uploadServletRequest = null;

		try {
			if ((contentType != null) &&
				contentType.startsWith(ContentTypes.MULTIPART_FORM_DATA)) {

				PortletConfigImpl invokerPortletConfigImpl =
					(PortletConfigImpl)invokerPortlet.getPortletConfig();

				if (invokerPortlet.isStrutsPortlet() ||
					((invokerPortletConfigImpl != null) &&
						!invokerPortletConfigImpl.isWARFile())) {

					uploadServletRequest = new UploadServletRequestImpl(
						request);

					request = uploadServletRequest;
				}
			}

			if (PropsValues.AUTH_TOKEN_CHECK_ENABLED &&
				invokerPortlet.isCheckAuthToken()) {

				AuthTokenUtil.check(request);
			}

			ActionRequestImpl actionRequestImpl =
				ActionRequestFactory.create(
					request, portlet, invokerPortlet, portletContext,
					windowState, portletMode, portletPreferences,
					layout.getPlid());

			User user = PortalUtil.getUser(request);

			ActionResponseImpl actionResponseImpl =
				ActionResponseFactory.create(
					actionRequestImpl, response, portletId, user, layout,
					windowState, portletMode);

			actionRequestImpl.defineObjects(portletConfig, actionResponseImpl);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequestImpl);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			long scopeGroupId = themeDisplay.getScopeGroupId();

			boolean access = PortletPermissionUtil.hasAccessPermission(
				permissionChecker, scopeGroupId, layout, portlet, portletMode);

			if (access) {
				invokerPortlet.processAction(
					actionRequestImpl, actionResponseImpl);

				actionResponseImpl.transferHeaders(response);
			}

			RenderParametersPool.put(
				request, layout.getPlid(), portletId,
				actionResponseImpl.getRenderParameterMap());

			List<Event> events = actionResponseImpl.getEvents();

			if (!events.isEmpty()) {
				processEvents(request, response, events);

				actionRequestImpl.defineObjects(
					portletConfig, actionResponseImpl);
			}

			String redirectLocation = actionResponseImpl.getRedirectLocation();

			if (Validator.isNotNull(redirectLocation)) {
				response.sendRedirect(redirectLocation);
			}
			else if (portlet.isActionURLRedirect()) {
				PortletURL portletURL = new PortletURLImpl(
					actionRequestImpl, actionRequestImpl.getPortletName(),
					layout.getPlid(), PortletRequest.RENDER_PHASE);

				Map<String, String[]> renderParameters =
					actionResponseImpl.getRenderParameterMap();

				for (Map.Entry<String, String[]> entry :
					renderParameters.entrySet()) {

					String key = entry.getKey();
					String[] value = entry.getValue();

					portletURL.setParameter(key, value);
				}

				response.sendRedirect(portletURL.toString());
			}
		}
		finally {
			if (uploadServletRequest != null) {
				uploadServletRequest.cleanUp();
			}

			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private void _doProcessEvent(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, Layout layout, Event event)
		throws Exception {

		String portletId = portlet.getPortletId();

		ServletContext servletContext =
			(ServletContext)request.getAttribute(WebKeys.CTX);

		InvokerPortlet invokerPortlet = PortletInstanceFactoryUtil.create(
			portlet, servletContext);

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);
		PortletContext portletContext = portletConfig.getPortletContext();

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		WindowState windowState = null;

		if (layoutTypePortlet.hasStateMaxPortletId(portletId)) {
			windowState = WindowState.MAXIMIZED;
		}
		else if (layoutTypePortlet.hasStateMinPortletId(portletId)) {
			windowState = WindowState.MINIMIZED;
		}
		else {
			windowState = WindowState.NORMAL;
		}

		PortletMode portletMode = null;

		if (layoutTypePortlet.hasModeAboutPortletId(portletId)) {
			portletMode = LiferayPortletMode.ABOUT;
		}
		else if (layoutTypePortlet.hasModeConfigPortletId(portletId)) {
			portletMode = LiferayPortletMode.CONFIG;
		}
		else if (layoutTypePortlet.hasModeEditPortletId(portletId)) {
			portletMode = PortletMode.EDIT;
		}
		else if (layoutTypePortlet.hasModeEditDefaultsPortletId(portletId)) {
			portletMode = LiferayPortletMode.EDIT_DEFAULTS;
		}
		else if (layoutTypePortlet.hasModeEditGuestPortletId(portletId)) {
			portletMode = LiferayPortletMode.EDIT_GUEST;
		}
		else if (layoutTypePortlet.hasModeHelpPortletId(portletId)) {
			portletMode = PortletMode.HELP;
		}
		else if (layoutTypePortlet.hasModePreviewPortletId(portletId)) {
			portletMode = LiferayPortletMode.PREVIEW;
		}
		else if (layoutTypePortlet.hasModePrintPortletId(portletId)) {
			portletMode = LiferayPortletMode.PRINT;
		}
		else {
			portletMode = PortletMode.VIEW;
		}

		long scopeGroupId = getScopeGroupId(request, layout, portletId);

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				scopeGroupId, layout, portletId, null);

		EventRequestImpl eventRequestImpl = EventRequestFactory.create(
			request, portlet, invokerPortlet, portletContext, windowState,
			portletMode, portletPreferences, layout.getPlid());

		eventRequestImpl.setEvent(
			serializeEvent(event, invokerPortlet.getPortletClassLoader()));

		User user = PortalUtil.getUser(request);
		Layout requestLayout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		EventResponseImpl eventResponseImpl = EventResponseFactory.create(
			eventRequestImpl, response, portletId, user, requestLayout);

		eventRequestImpl.defineObjects(portletConfig, eventResponseImpl);

		try {
			invokerPortlet.processEvent(eventRequestImpl, eventResponseImpl);

			if (eventResponseImpl.isCalledSetRenderParameter()) {
				Map<String, String[]> renderParameterMap =
					new HashMap<String, String[]>();

				renderParameterMap.putAll(
					eventResponseImpl.getRenderParameterMap());

				RenderParametersPool.put(
					request, requestLayout.getPlid(), portletId,
					renderParameterMap);
			}

			processEvents(request, response, eventResponseImpl.getEvents());
		}
		finally {
			eventRequestImpl.cleanUp();
		}
	}

	private void _doRender(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws Exception {

		if ((portlet != null) && portlet.isInstanceable() &&
			!portlet.isAddDefaultResource()) {

			String instanceId = portlet.getInstanceId();

			if (!Validator.isPassword(instanceId)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Portlet " + portlet.getPortletId() +
							" is instanceable but does not have a " +
								"valid instance id");
				}

				portlet = null;
			}
		}

		if (portlet == null) {
			return;
		}

		// Capture the current portlet's settings to reset them once the child
		// portlet is rendered

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletDisplay portletDisplayClone = PortletDisplayFactory.create();

		portletDisplay.copyTo(portletDisplayClone);

		PortletConfig portletConfig = (PortletConfig)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		if (!(portletRequest instanceof RenderRequest)) {
			portletRequest = null;
		}

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if (!(portletResponse instanceof RenderResponse)) {
			portletResponse = null;
		}

		String lifecycle = (String)request.getAttribute(
			PortletRequest.LIFECYCLE_PHASE);

		request.setAttribute(WebKeys.RENDER_PORTLET, portlet);

		String path = (String)request.getAttribute(WebKeys.RENDER_PATH);

		if (path == null) {
			path = "/html/portal/render_portlet.jsp";
		}

		RequestDispatcher requestDispatcher =
			DirectRequestDispatcherUtil.getRequestDispatcher(request, path);

		StringServletResponse stringServletResponse = null;

		boolean writeOutput = false;

		if (response instanceof StringServletResponse) {
			stringServletResponse = (StringServletResponse)response;
		}
		else {
			writeOutput = true;
			stringServletResponse = new StringServletResponse(response);
		}

		try {
			requestDispatcher.include(request, stringServletResponse);

			Boolean portletConfiguratorVisibility =
				(Boolean)request.getAttribute(
					WebKeys.PORTLET_CONFIGURATOR_VISIBILITY);

			if (portletConfiguratorVisibility != null) {
				request.removeAttribute(
					WebKeys.PORTLET_CONFIGURATOR_VISIBILITY);

				Layout layout = themeDisplay.getLayout();

				if (!layout.isTypeControlPanel() &&
					!LayoutPermissionUtil.contains(
						themeDisplay.getPermissionChecker(), layout,
						ActionKeys.UPDATE) &&
					!PortletPermissionUtil.contains(
						themeDisplay.getPermissionChecker(),
						themeDisplay.getPlid(), portlet.getPortletId(),
						ActionKeys.CONFIGURATION)) {

					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

					stringServletResponse.setString(StringPool.BLANK);

					return;
				}
			}

			if (writeOutput) {
				response.setContentType(ContentTypes.TEXT_HTML_UTF8);

				stringServletResponse.writeTo(response.getWriter());
			}
		}
		finally {
			portletDisplay.copyFrom(portletDisplayClone);

			portletDisplayClone.recycle();

			if (portletConfig != null) {
				request.setAttribute(
					JavaConstants.JAVAX_PORTLET_CONFIG, portletConfig);
			}

			if (portletRequest != null) {
				request.setAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST, portletRequest);
			}

			if (portletResponse != null) {
				request.setAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE, portletResponse);
			}

			if (lifecycle != null) {
				request.setAttribute(PortletRequest.LIFECYCLE_PHASE, lifecycle);
			}
		}
	}

	private void _doServeResource(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws Exception {

		String portletId = portlet.getPortletId();

		WindowState windowState =
			(WindowState)request.getAttribute(WebKeys.WINDOW_STATE);

		PortletMode portletMode = PortletModeFactory.getPortletMode(
			ParamUtil.getString(request, "p_p_mode"));

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				request, portletId);

		PortletPreferences portletPreferences = null;

		if (PortalUtil.isAllowAddPortletDefaultResource(request, portlet)) {
			portletPreferences =
				PortletPreferencesLocalServiceUtil.getPreferences(
					portletPreferencesIds);
		}
		else {
			portletPreferences =
				PortletPreferencesLocalServiceUtil.getStrictPreferences(
					portletPreferencesIds);
		}

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		InvokerPortlet invokerPortlet = PortletInstanceFactoryUtil.create(
			portlet, servletContext);

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);
		PortletContext portletContext = portletConfig.getPortletContext();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		String portletPrimaryKey = PortletPermissionUtil.getPrimaryKey(
			layout.getPlid(), portletId);

		portletDisplay.setId(portletId);
		portletDisplay.setRootPortletId(portlet.getRootPortletId());
		portletDisplay.setInstanceId(portlet.getInstanceId());
		portletDisplay.setResourcePK(portletPrimaryKey);
		portletDisplay.setPortletName(portletConfig.getPortletName());
		portletDisplay.setNamespace(PortalUtil.getPortletNamespace(portletId));

		WebDAVStorage webDAVStorage = portlet.getWebDAVStorageInstance();

		if (webDAVStorage != null) {
			portletDisplay.setWebDAVEnabled(true);
		}
		else {
			portletDisplay.setWebDAVEnabled(false);
		}

		ResourceRequestImpl resourceRequestImpl =
			ResourceRequestFactory.create(
				request, portlet, invokerPortlet, portletContext, windowState,
				portletMode, portletPreferences, layout.getPlid());

		long companyId = PortalUtil.getCompanyId(request);

		ResourceResponseImpl resourceResponseImpl =
			ResourceResponseFactory.create(
				resourceRequestImpl, response, portletId, companyId);

		resourceRequestImpl.defineObjects(portletConfig, resourceResponseImpl);

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				resourceRequestImpl);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			long scopeGroupId = themeDisplay.getScopeGroupId();

			boolean access = PortletPermissionUtil.hasAccessPermission(
				permissionChecker, scopeGroupId, layout, portlet, portletMode);

			if (access) {
				invokerPortlet.serveResource(
					resourceRequestImpl, resourceResponseImpl);

				resourceResponseImpl.transferHeaders(response);
			}
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortletContainerImpl.class);

}