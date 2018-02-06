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

package com.liferay.portlet;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutType;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.model.PublicRenderParameter;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.ActionResult;
import com.liferay.portal.kernel.portlet.InvokerPortlet;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletContainer;
import com.liferay.portal.kernel.portlet.PortletContainerException;
import com.liferay.portal.kernel.portlet.PortletInstanceFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletQName;
import com.liferay.portal.kernel.portlet.PortletQNameUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIconMenu;
import com.liferay.portal.kernel.portlet.toolbar.PortletToolbar;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.TransferHeadersHelperUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.PortletConfigurationIconComparator;
import com.liferay.portal.kernel.webdav.WebDAVStorage;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.theme.PortletDisplayFactory;
import com.liferay.util.SerializableUtil;

import java.io.Serializable;
import java.io.Writer;

import java.util.Arrays;
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
public class PortletContainerImpl implements PortletContainer {

	@Override
	public void preparePortlet(HttpServletRequest request, Portlet portlet)
		throws PortletContainerException {

		try {
			_preparePortlet(request, portlet);
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	@Override
	public ActionResult processAction(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		try {
			return _processAction(request, response, portlet);
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	@Override
	public List<Event> processEvent(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet, Layout layout, Event event)
		throws PortletContainerException {

		try {
			return _processEvent(request, response, portlet, layout, event);
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	@Override
	public void processPublicRenderParameters(
		HttpServletRequest request, Layout layout) {

		processPublicRenderParameters(request, layout, null);
	}

	@Override
	public void processPublicRenderParameters(
		HttpServletRequest request, Layout layout, Portlet portlet) {

		LayoutType layoutType = layout.getLayoutType();

		if (!(layoutType instanceof LayoutTypePortlet)) {
			return;
		}

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layoutType;

		List<Portlet> portlets = layoutTypePortlet.getPortlets();

		portlets.remove(portlet);

		_processPublicRenderParameters(request, layout, portlets, false);
	}

	@Override
	public void render(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		try {
			_render(request, response, portlet);
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	@Override
	public void serveResource(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws PortletContainerException {

		try {
			_serveResource(request, response, portlet);
		}
		catch (Exception e) {
			throw new PortletContainerException(e);
		}
	}

	public void setPortletConfigurationIconMenu(
		PortletConfigurationIconMenu portletConfigurationIconMenu) {

		_portletConfigurationIconMenu = portletConfigurationIconMenu;
	}

	public void setPortletToolbar(PortletToolbar portletToolbar) {
		_portletToolbar = portletToolbar;
	}

	protected long getScopeGroupId(
			HttpServletRequest request, Layout layout, String portletId)
		throws PortalException {

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

	private void _preparePortlet(HttpServletRequest request, Portlet portlet)
		throws Exception {

		User user = PortalUtil.getUser(request);
		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = PortalUtil.getScopeGroupId(
			request, portlet.getPortletId());

		themeDisplay.setScopeGroupId(scopeGroupId);

		long siteGroupId = 0;

		if (layout.isTypeControlPanel()) {
			siteGroupId = PortalUtil.getSiteGroupId(scopeGroupId);
		}
		else {
			siteGroupId = PortalUtil.getSiteGroupId(layout.getGroupId());
		}

		themeDisplay.setSiteGroupId(siteGroupId);

		if (user != null) {
			HttpSession session = request.getSession();

			InvokerPortletImpl.clearResponse(
				session, layout.getPrimaryKey(), portlet.getPortletId(),
				LanguageUtil.getLanguageId(request));
		}

		_processPublicRenderParameters(
			request, layout, Arrays.asList(portlet),
			themeDisplay.isLifecycleAction());

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

	private ActionResult _processAction(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setId(portlet.getPortletId());

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
			PortletPreferencesLocalServiceUtil.getStrictPreferences(
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

		try {
			ActionRequestImpl actionRequestImpl = ActionRequestFactory.create(
				request, portlet, invokerPortlet, portletContext, windowState,
				portletMode, portletPreferences, layout.getPlid());

			User user = PortalUtil.getUser(request);

			ActionResponseImpl actionResponseImpl =
				ActionResponseFactory.create(
					actionRequestImpl, response, user, layout);

			actionRequestImpl.defineObjects(portletConfig, actionResponseImpl);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequestImpl);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			invokerPortlet.processAction(actionRequestImpl, actionResponseImpl);

			actionResponseImpl.transferHeaders(response);

			RenderParametersPool.clear(
				request, layout.getPlid(), portlet.getPortletId());

			RenderParametersPool.put(
				request, layout.getPlid(), portlet.getPortletId(),
				actionResponseImpl.getRenderParameterMap());

			List<Event> events = actionResponseImpl.getEvents();

			String redirectLocation = actionResponseImpl.getRedirectLocation();

			if (Validator.isNull(redirectLocation) &&
				portlet.isActionURLRedirect()) {

				PortletURL portletURL = PortletURLFactoryUtil.create(
					actionRequestImpl, portlet, layout,
					PortletRequest.RENDER_PHASE);

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
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private List<Event> _processEvent(
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
			eventRequestImpl, response, user, requestLayout);

		eventRequestImpl.defineObjects(portletConfig, eventResponseImpl);

		try {
			invokerPortlet.processEvent(eventRequestImpl, eventResponseImpl);

			eventResponseImpl.transferHeaders(response);

			if (eventResponseImpl.isCalledSetRenderParameter()) {
				Map<String, String[]> renderParameterMap =
					eventResponseImpl.getRenderParameterMap();

				if (!renderParameterMap.isEmpty()) {
					RenderParametersPool.put(
						request, requestLayout.getPlid(),
						portlet.getPortletId(),
						new HashMap<>(renderParameterMap));
				}
			}

			return eventResponseImpl.getEvents();
		}
		finally {
			eventRequestImpl.cleanUp();
		}
	}

	private void _processPublicRenderParameters(
		HttpServletRequest request, Layout layout, List<Portlet> portlets,
		boolean lifecycleAction) {

		PortletQName portletQName = PortletQNameUtil.getPortletQName();
		Map<String, String[]> publicRenderParameters = null;
		Map<String, String[]> parameters = request.getParameterMap();

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String name = entry.getKey();

			QName qName = portletQName.getQName(name);

			if (qName == null) {
				continue;
			}

			for (Portlet portlet : portlets) {
				PublicRenderParameter publicRenderParameter =
					portlet.getPublicRenderParameter(
						qName.getNamespaceURI(), qName.getLocalPart());

				if (publicRenderParameter == null) {
					continue;
				}

				if (publicRenderParameters == null) {
					publicRenderParameters = PublicRenderParametersPool.get(
						request, layout.getPlid());
				}

				String publicRenderParameterName =
					portletQName.getPublicRenderParameterName(qName);

				if (name.startsWith(
						PortletQName.PUBLIC_RENDER_PARAMETER_NAMESPACE)) {

					String[] values = entry.getValue();

					if (lifecycleAction) {
						String[] oldValues = publicRenderParameters.get(
							publicRenderParameterName);

						if ((oldValues != null) && (oldValues.length != 0)) {
							values = ArrayUtil.append(values, oldValues);
						}
					}

					publicRenderParameters.put(
						publicRenderParameterName, values);
				}
				else {
					publicRenderParameters.remove(publicRenderParameterName);
				}
			}
		}
	}

	private void _render(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws Exception {

		if ((portlet != null) && portlet.isInstanceable() &&
			!portlet.isAddDefaultResource()) {

			String instanceId = portlet.getInstanceId();

			if (!Validator.isPassword(instanceId)) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Portlet ", portlet.getPortletId(),
							" is instanceable but does not have a valid ",
							"instance id"));
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

		PortletPreferencesFactoryUtil.checkControlPanelPortletPreferences(
			themeDisplay, portlet);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_portletConfigurationIconMenu.setComparator(
			PortletConfigurationIconComparator.INSTANCE);

		portletDisplay.setPortletConfigurationIconMenu(
			_portletConfigurationIconMenu);

		portletDisplay.setPortletToolbar(_portletToolbar);

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
			TransferHeadersHelperUtil.getTransferHeadersRequestDispatcher(
				DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
					request, path));

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

	private void _serveResource(
			HttpServletRequest request, HttpServletResponse response,
			Portlet portlet)
		throws Exception {

		WindowState windowState = (WindowState)request.getAttribute(
			WebKeys.WINDOW_STATE);

		PortletMode portletMode = PortletModeFactory.getPortletMode(
			ParamUtil.getString(request, "p_p_mode"));

		PortletPreferencesIds portletPreferencesIds =
			PortletPreferencesFactoryUtil.getPortletPreferencesIds(
				request, portlet.getPortletId());

		PortletPreferences portletPreferences =
			PortletPreferencesLocalServiceUtil.getStrictPreferences(
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
		portletDisplay.setInstanceId(portlet.getInstanceId());
		portletDisplay.setNamespace(
			PortalUtil.getPortletNamespace(portlet.getPortletId()));
		portletDisplay.setPortletName(portletConfig.getPortletName());
		portletDisplay.setResourcePK(portletPrimaryKey);
		portletDisplay.setRootPortletId(portlet.getRootPortletId());

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

		ResourceResponseImpl resourceResponseImpl =
			ResourceResponseFactory.create(resourceRequestImpl, response);

		resourceRequestImpl.defineObjects(portletConfig, resourceResponseImpl);

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				resourceRequestImpl);

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			invokerPortlet.serveResource(
				resourceRequestImpl, resourceResponseImpl);

			resourceResponseImpl.transferHeaders(response);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletContainerImpl.class);

	private PortletConfigurationIconMenu _portletConfigurationIconMenu;
	private PortletToolbar _portletToolbar;

}