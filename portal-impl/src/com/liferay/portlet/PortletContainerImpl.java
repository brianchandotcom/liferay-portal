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

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ActionResult;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.PortletContainer;
import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.TempAttributesServletRequest;
import com.liferay.portal.kernel.struts.LastPath;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletPreferencesIds;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
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
import java.io.Writer;

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
 * @author Raymond Augé
 */
@DoPrivileged
public class PortletContainerImpl implements PortletContainer {

	public void preparePortlet(HttpServletRequest request, Portlet portlet)
		throws PortletContainerException {

		try {
			_doPreparePortlet(request, portlet);
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	public ActionResult processAction(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		try {
			return _doProcessAction(request, response, portlet);
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	public List<Event> processEvent(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, Layout layout, Event event)
		throws PortletContainerException {

		try {
			return _doProcessEvent(request, response, portlet, layout, event);
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

	protected HttpServletRequest getOwnerLayoutRequestWrapper(
			HttpServletRequest request, Portlet portlet)
		throws Exception {

		if (!PropsValues.PORTLET_EVENT_DISTRIBUTION_LAYOUT_SET) {
			return request;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout currentLayout = themeDisplay.getLayout();

		Layout requestLayout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		List<LayoutTypePortlet> layoutTypePortlets =
			PortletContainerUtil.getLayoutTypePortlets(requestLayout);

		Layout ownerLayout = null;
		LayoutTypePortlet ownerLayoutTypePortlet = null;

		for (LayoutTypePortlet layoutTypePortlet : layoutTypePortlets) {
			if (layoutTypePortlet.hasPortletId(portlet.getPortletId())) {
				ownerLayoutTypePortlet = layoutTypePortlet;

				ownerLayout = layoutTypePortlet.getLayout();

				break;
			}
		}

		if ((ownerLayout != null) && !currentLayout.equals(ownerLayout)) {
			ThemeDisplay themeDisplayClone = (ThemeDisplay)themeDisplay.clone();

			themeDisplayClone.setLayout(ownerLayout);
			themeDisplayClone.setLayoutTypePortlet(ownerLayoutTypePortlet);

			TempAttributesServletRequest tempAttributesServletRequest =
				new TempAttributesServletRequest(request);

			tempAttributesServletRequest.setTempAttribute(
				WebKeys.THEME_DISPLAY, themeDisplayClone);
			tempAttributesServletRequest.setTempAttribute(
				WebKeys.LAYOUT, ownerLayout);

			return tempAttributesServletRequest;
		}

		return request;
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
			throw new RuntimeException(cnfe);
		}

		byte[] serializedValue = SerializableUtil.serialize(value);

		value = (Serializable)SerializableUtil.deserialize(
			serializedValue, portletClassLoader);

		return new EventImpl(event.getName(), event.getQName(), value);
	}

	private void _doPreparePortlet(HttpServletRequest request, Portlet portlet)
		throws Exception {

		User user = PortalUtil.getUser(request);
		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = PortalUtil.getScopeGroupId(
			request, portlet.getPortletId());
		long siteGroupId = PortalUtil.getSiteGroupId(scopeGroupId);

		themeDisplay.setScopeGroupId(scopeGroupId);
		themeDisplay.setSiteGroupId(siteGroupId);

		if (user != null) {
			HttpSession session = request.getSession();

			InvokerPortletImpl.clearResponse(
				session, layout.getPrimaryKey(), portlet.getPortletId(),
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
				portlet.getPortletId(), user, layout, windowState, request);

			PortalUtil.updatePortletMode(
				portlet.getPortletId(), user, layout, portletMode, request);
		}
	}

	private ActionResult _doProcessAction(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws Exception {

		HttpServletRequest ownerLayoutRequest = getOwnerLayoutRequestWrapper(
			request, portlet);

		Layout ownerLayout = (Layout)ownerLayoutRequest.getAttribute(
			WebKeys.LAYOUT);

		boolean allowAddPortletDefaultResource =
			PortalUtil.isAllowAddPortletDefaultResource(
				ownerLayoutRequest, portlet);

		if (!allowAddPortletDefaultResource) {
			String url = null;

			LastPath lastPath = (LastPath)request.getAttribute(
				WebKeys.LAST_PATH);

			if (lastPath != null) {
				StringBundler sb = new StringBundler(3);

				sb.append(PortalUtil.getPortalURL(request));
				sb.append(lastPath.getContextPath());
				sb.append(lastPath.getPath());

				url = sb.toString();
			}
			else {
				url = String.valueOf(request.getRequestURI());
			}

			_log.error(
				"Reject processAction for " + url + " on " +
					portlet.getPortletId());

			return ActionResult.EMPTY_ACTION_RESULT;
		}

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

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
				request, portlet.getPortletId());

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getPreferences(
				portletPreferencesIds);

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

				LiferayPortletConfig liferayPortletConfig =
					(LiferayPortletConfig)invokerPortlet.getPortletConfig();

				if (invokerPortlet.isStrutsPortlet() ||
					liferayPortletConfig.isCopyRequestParameters() ||
					!liferayPortletConfig.isWARFile()) {

					uploadServletRequest = new UploadServletRequestImpl(
						request);

					request = uploadServletRequest;
				}
			}

			if (PropsValues.AUTH_TOKEN_CHECK_ENABLED &&
				invokerPortlet.isCheckAuthToken()) {

				AuthTokenUtil.check(request);
			}

			ActionRequestImpl actionRequestImpl = ActionRequestFactory.create(
				request, portlet, invokerPortlet, portletContext, windowState,
				portletMode, portletPreferences, layout.getPlid());

			User user = PortalUtil.getUser(request);

			ActionResponseImpl actionResponseImpl =
				ActionResponseFactory.create(
					actionRequestImpl, response, portlet.getPortletId(), user,
					layout, windowState, portletMode);

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
				permissionChecker, scopeGroupId, ownerLayout, portlet,
				portletMode);

			if (access) {
				invokerPortlet.processAction(
					actionRequestImpl, actionResponseImpl);

				actionResponseImpl.transferHeaders(response);
			}

			RenderParametersPool.put(
				request, layout.getPlid(), portlet.getPortletId(),
				actionResponseImpl.getRenderParameterMap());

			List<Event> events = actionResponseImpl.getEvents();

			String redirectLocation = actionResponseImpl.getRedirectLocation();

			if (Validator.isNull(redirectLocation) &&
				portlet.isActionURLRedirect()) {

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

				redirectLocation = portletURL.toString();
			}

			return new ActionResult(events, redirectLocation);
		}
		finally {
			if (uploadServletRequest != null) {
				uploadServletRequest.cleanUp();
			}

			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private List<Event> _doProcessEvent(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, Layout layout, Event event)
		throws Exception {

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		InvokerPortlet invokerPortlet = PortletInstanceFactoryUtil.create(
			portlet, servletContext);

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);
		PortletContext portletContext = portletConfig.getPortletContext();

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		WindowState windowState = null;

		if (layoutTypePortlet.hasStateMaxPortletId(portlet.getPortletId())) {
			windowState = WindowState.MAXIMIZED;
		}
		else if (layoutTypePortlet.hasStateMinPortletId(
					portlet.getPortletId())) {

			windowState = WindowState.MINIMIZED;
		}
		else {
			windowState = WindowState.NORMAL;
		}

		PortletMode portletMode = null;

		if (layoutTypePortlet.hasModeAboutPortletId(portlet.getPortletId())) {
			portletMode = LiferayPortletMode.ABOUT;
		}
		else if (layoutTypePortlet.hasModeConfigPortletId(
					portlet.getPortletId())) {

			portletMode = LiferayPortletMode.CONFIG;
		}
		else if (layoutTypePortlet.hasModeEditPortletId(
					portlet.getPortletId())) {

			portletMode = PortletMode.EDIT;
		}
		else if (layoutTypePortlet.hasModeEditDefaultsPortletId(
					portlet.getPortletId())) {

			portletMode = LiferayPortletMode.EDIT_DEFAULTS;
		}
		else if (layoutTypePortlet.hasModeEditGuestPortletId(
					portlet.getPortletId())) {

			portletMode = LiferayPortletMode.EDIT_GUEST;
		}
		else if (layoutTypePortlet.hasModeHelpPortletId(
					portlet.getPortletId())) {

			portletMode = PortletMode.HELP;
		}
		else if (layoutTypePortlet.hasModePreviewPortletId(
					portlet.getPortletId())) {

			portletMode = LiferayPortletMode.PREVIEW;
		}
		else if (layoutTypePortlet.hasModePrintPortletId(
					portlet.getPortletId())) {

			portletMode = LiferayPortletMode.PRINT;
		}
		else {
			portletMode = PortletMode.VIEW;
		}

		long scopeGroupId = getScopeGroupId(
			request, layout, portlet.getPortletId());

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				scopeGroupId, layout, portlet.getPortletId(), null);

		EventRequestImpl eventRequestImpl = EventRequestFactory.create(
			request, portlet, invokerPortlet, portletContext, windowState,
			portletMode, portletPreferences, layout.getPlid());

		eventRequestImpl.setEvent(
			serializeEvent(event, invokerPortlet.getPortletClassLoader()));

		User user = PortalUtil.getUser(request);
		Layout requestLayout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		EventResponseImpl eventResponseImpl = EventResponseFactory.create(
			eventRequestImpl, response, portlet.getPortletId(), user,
			requestLayout);

		eventRequestImpl.defineObjects(portletConfig, eventResponseImpl);

		try {
			invokerPortlet.processEvent(eventRequestImpl, eventResponseImpl);

			if (eventResponseImpl.isCalledSetRenderParameter()) {
				Map<String, String[]> renderParameterMap =
					new HashMap<String, String[]>();

				renderParameterMap.putAll(
					eventResponseImpl.getRenderParameterMap());

				RenderParametersPool.put(
					request, requestLayout.getPlid(), portlet.getPortletId(),
					renderParameterMap);
			}

			return eventResponseImpl.getEvents();
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
							" is instanceable but does not have a valid " +
								"instance id");
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
			DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
				request, path);

		BufferCacheServletResponse bufferCacheServletResponse = null;

		boolean writeOutput = false;

		if (response instanceof BufferCacheServletResponse) {
			bufferCacheServletResponse = (BufferCacheServletResponse)response;
		}
		else {
			writeOutput = true;
			bufferCacheServletResponse = new BufferCacheServletResponse(
				response);
		}

		try {
			requestDispatcher.include(request, bufferCacheServletResponse);

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
						themeDisplay.getPermissionChecker(), layout,
						portlet.getPortletId(), ActionKeys.CONFIGURATION)) {

					bufferCacheServletResponse.setCharBuffer(null);

					return;
				}
			}

			if (writeOutput) {
				Writer writer = response.getWriter();

				writer.write(bufferCacheServletResponse.getString());
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

			request.removeAttribute(WebKeys.RENDER_PORTLET);
		}
	}

	private void _doServeResource(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws Exception {

		HttpServletRequest ownerLayoutRequest = getOwnerLayoutRequestWrapper(
			request, portlet);

		Layout ownerLayout = (Layout)ownerLayoutRequest.getAttribute(
			WebKeys.LAYOUT);

		boolean allowAddPortletDefaultResource =
			PortalUtil.isAllowAddPortletDefaultResource(
				ownerLayoutRequest, portlet);

		if (!allowAddPortletDefaultResource) {
			String url = null;

			LastPath lastPath = (LastPath)request.getAttribute(
				WebKeys.LAST_PATH);

			if (lastPath != null) {
				StringBundler sb = new StringBundler(3);

				sb.append(PortalUtil.getPortalURL(request));
				sb.append(lastPath.getContextPath());
				sb.append(lastPath.getPath());

				url = sb.toString();
			}
			else {
				url = String.valueOf(request.getRequestURI());
			}

			response.setHeader(
				HttpHeaders.CACHE_CONTROL,
				HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

			_log.error(
				"Reject serveResource for " + url + " on " +
					portlet.getPortletId());

			return;
		}

		WindowState windowState = (WindowState)request.getAttribute(
			WebKeys.WINDOW_STATE);

		PortletMode portletMode = PortletModeFactory.getPortletMode(
			ParamUtil.getString(request, "p_p_mode"));

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				request, portlet.getPortletId());

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getPreferences(
				portletPreferencesIds);

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
			layout.getPlid(), portlet.getPortletId());

		portletDisplay.setId(portlet.getPortletId());
		portletDisplay.setRootPortletId(portlet.getRootPortletId());
		portletDisplay.setInstanceId(portlet.getInstanceId());
		portletDisplay.setResourcePK(portletPrimaryKey);
		portletDisplay.setPortletName(portletConfig.getPortletName());
		portletDisplay.setNamespace(
			PortalUtil.getPortletNamespace(portlet.getPortletId()));
		portletDisplay.setControlPanelCategory(
			portlet.getControlPanelEntryCategory());

		WebDAVStorage webDAVStorage = portlet.getWebDAVStorageInstance();

		if (webDAVStorage != null) {
			portletDisplay.setWebDAVEnabled(true);
		}
		else {
			portletDisplay.setWebDAVEnabled(false);
		}

		ResourceRequestImpl resourceRequestImpl = ResourceRequestFactory.create(
			request, portlet, invokerPortlet, portletContext, windowState,
			portletMode, portletPreferences, layout.getPlid());

		long companyId = PortalUtil.getCompanyId(request);

		ResourceResponseImpl resourceResponseImpl =
			ResourceResponseFactory.create(
				resourceRequestImpl, response, portlet.getPortletId(),
				companyId);

		resourceRequestImpl.defineObjects(portletConfig, resourceResponseImpl);

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				resourceRequestImpl);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			long scopeGroupId = themeDisplay.getScopeGroupId();

			boolean access = PortletPermissionUtil.hasAccessPermission(
				permissionChecker, scopeGroupId, ownerLayout, portlet,
				portletMode);

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