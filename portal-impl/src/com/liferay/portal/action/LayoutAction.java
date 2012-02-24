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

package com.liferay.portal.action;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouterUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletMode;
import com.liferay.portal.kernel.portlet.PortletModeFactory;
import com.liferay.portal.kernel.portlet.WindowStateFactory;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.HeaderCacheServletResponse;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.StringServletResponse;
import com.liferay.portal.kernel.upload.UploadServletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
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
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionRequestFactory;
import com.liferay.portlet.ActionRequestImpl;
import com.liferay.portlet.ActionResponseFactory;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.EventImpl;
import com.liferay.portlet.EventRequestFactory;
import com.liferay.portlet.EventRequestImpl;
import com.liferay.portlet.EventResponseFactory;
import com.liferay.portlet.EventResponseImpl;
import com.liferay.portlet.InvokerPortlet;
import com.liferay.portlet.InvokerPortletImpl;
import com.liferay.portlet.PortletConfigFactoryUtil;
import com.liferay.portlet.PortletConfigImpl;
import com.liferay.portlet.PortletInstanceFactoryUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletQName;
import com.liferay.portlet.PortletQNameUtil;
import com.liferay.portlet.PortletRequestImpl;
import com.liferay.portlet.PortletURLImpl;
import com.liferay.portlet.PublicRenderParametersPool;
import com.liferay.portlet.RenderParametersPool;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.RenderResponseImpl;
import com.liferay.portlet.ResourceRequestFactory;
import com.liferay.portlet.ResourceRequestImpl;
import com.liferay.portlet.ResourceResponseFactory;
import com.liferay.portlet.ResourceResponseImpl;
import com.liferay.portlet.StateAwareResponseImpl;
import com.liferay.portlet.login.util.LoginUtil;
import com.liferay.util.servlet.filters.CacheResponseUtil;

import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.Event;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.UnavailableException;
import javax.portlet.WindowState;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutAction extends Action {

	@Override
	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		HeaderCacheServletResponse headerCacheServletResponse = null;

		if (response instanceof HeaderCacheServletResponse) {
			headerCacheServletResponse = (HeaderCacheServletResponse)response;
		}
		else {
			headerCacheServletResponse = new HeaderCacheServletResponse(
				response);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		Boolean layoutDefault = (Boolean)request.getAttribute(
			WebKeys.LAYOUT_DEFAULT);

		if ((layoutDefault != null) && (layoutDefault.booleanValue())) {
			Layout requestedLayout = (Layout)request.getAttribute(
				WebKeys.REQUESTED_LAYOUT);

			if (requestedLayout != null) {
				String redirectParam = "redirect";

				if (Validator.isNotNull(PropsValues.AUTH_LOGIN_PORTLET_NAME)) {
					redirectParam =
						PortalUtil.getPortletNamespace(
							PropsValues.AUTH_LOGIN_PORTLET_NAME) +
						redirectParam;
				}

				String authLoginURL = null;

				if (PrefsPropsUtil.getBoolean(
						themeDisplay.getCompanyId(), PropsKeys.CAS_AUTH_ENABLED,
						PropsValues.CAS_AUTH_ENABLED) ||
					PrefsPropsUtil.getBoolean(
						themeDisplay.getCompanyId(),
						PropsKeys.OPEN_SSO_AUTH_ENABLED,
						PropsValues.OPEN_SSO_AUTH_ENABLED)) {

					authLoginURL = themeDisplay.getURLSignIn();
				}

				if (Validator.isNull(authLoginURL)) {
					authLoginURL = PortalUtil.getSiteLoginURL(themeDisplay);
				}

				if (Validator.isNull(authLoginURL)) {
					authLoginURL = PropsValues.AUTH_LOGIN_URL;
				}

				if (Validator.isNull(authLoginURL)) {
					PortletURL loginURL = LoginUtil.getLoginURL(
						request, themeDisplay.getPlid());

					authLoginURL = loginURL.toString();
				}

				authLoginURL = HttpUtil.setParameter(
					authLoginURL, "p_p_id",
					PropsValues.AUTH_LOGIN_PORTLET_NAME);

				String currentURL = PortalUtil.getCurrentURL(request);

				authLoginURL = HttpUtil.setParameter(
					authLoginURL, redirectParam, currentURL);

				if (_log.isDebugEnabled()) {
					_log.debug("Redirect requested layout to " + authLoginURL);
				}

				headerCacheServletResponse.sendRedirect(authLoginURL);
			}
			else {
				String redirect = PortalUtil.getLayoutURL(layout, themeDisplay);

				if (_log.isDebugEnabled()) {
					_log.debug("Redirect default layout to " + redirect);
				}

				headerCacheServletResponse.sendRedirect(redirect);
			}

			return null;
		}

		long plid = ParamUtil.getLong(request, "p_l_id");

		if (_log.isDebugEnabled()) {
			_log.debug("p_l_id is " + plid);
		}

		if (plid > 0) {
			ActionForward actionForward = processLayout(
				mapping, request, headerCacheServletResponse, plid);

			String contentType = response.getContentType();

			CacheResponseUtil.setHeaders(
				response, headerCacheServletResponse.getHeaders());

			if (contentType != null) {
				response.setContentType(contentType);
			}

			return actionForward;
		}
		else {
			try {
				forwardLayout(request);

				return mapping.findForward(ActionConstants.COMMON_FORWARD_JSP);
			}
			catch (Exception e) {
				PortalUtil.sendError(e, request, headerCacheServletResponse);

				CacheResponseUtil.setHeaders(
					response, headerCacheServletResponse.getHeaders());

				return null;
			}
		}
	}

	protected void forwardLayout(HttpServletRequest request) throws Exception {
		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		long plid = LayoutConstants.DEFAULT_PLID;

		String layoutFriendlyURL = null;

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (layout != null) {
			plid = layout.getPlid();

			layoutFriendlyURL = PortalUtil.getLayoutFriendlyURL(
				layout, themeDisplay);
		}

		String forwardURL = layoutFriendlyURL;

		if (Validator.isNull(forwardURL)) {
			forwardURL =
				themeDisplay.getPathMain() + "/portal/layout?p_l_id=" + plid;
		}

		if (Validator.isNotNull(themeDisplay.getDoAsUserId())) {
			forwardURL = HttpUtil.addParameter(
				forwardURL, "doAsUserId", themeDisplay.getDoAsUserId());
		}

		if (Validator.isNotNull(themeDisplay.getDoAsUserLanguageId())) {
			forwardURL = HttpUtil.addParameter(
				forwardURL, "doAsUserLanguageId",
				themeDisplay.getDoAsUserLanguageId());
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Forward layout to " + forwardURL);
		}

		request.setAttribute(WebKeys.FORWARD_URL, forwardURL);
	}

	protected List<LayoutTypePortlet> getLayoutTypePortlets(
			long groupId, boolean privateLayout)
		throws Exception {

		List<LayoutTypePortlet> layoutTypePortlets =
			new ArrayList<LayoutTypePortlet>();

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, LayoutConstants.TYPE_PORTLET);

		for (Layout layout : layouts) {
			if (!layout.isTypePortlet()) {
				continue;
			}

			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			layoutTypePortlets.add(layoutTypePortlet);
		}

		return layoutTypePortlets;
	}

	protected long getScopeGroupId(
			HttpServletRequest request, LayoutTypePortlet layoutTypePortlet,
			String portletId)
		throws PortalException, SystemException {

		long scopeGroupId = 0;

		Layout layoutTypePortletLayout = layoutTypePortlet.getLayout();

		Layout requestLayout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		try {
			request.setAttribute(WebKeys.LAYOUT, layoutTypePortletLayout);

			scopeGroupId = PortalUtil.getScopeGroupId(request, portletId);
		}
		finally {
			request.setAttribute(WebKeys.LAYOUT, requestLayout);
		}

		if (scopeGroupId <= 0) {
			scopeGroupId = PortalUtil.getScopeGroupId(
				layoutTypePortletLayout, portletId);
		}

		return scopeGroupId;
	}

	protected void includeLayoutContent(
			HttpServletRequest request, HttpServletResponse response,
			ThemeDisplay themeDisplay, Layout layout)
		throws Exception {

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		String path = StrutsUtil.TEXT_HTML_DIR;

		if (BrowserSnifferUtil.isWap(request)) {
			path = StrutsUtil.TEXT_WAP_DIR;
		}

		// Manually check the p_p_id. See LEP-1724.

		if (themeDisplay.isStateExclusive() ||
			Validator.isNotNull(ParamUtil.getString(request, "p_p_id"))) {

			if (layout.isTypePanel()) {
				path += "/portal/layout/view/panel.jsp";
			}
			else if (layout.isTypeControlPanel()) {
				path += "/portal/layout/view/control_panel.jsp";
			}
			else {
				path += "/portal/layout/view/portlet.jsp";
			}
		}
		else {
			path += PortalUtil.getLayoutViewPage(layout);
		}

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(path);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		PipingServletResponse pipingServletResponse = new PipingServletResponse(
			response, unsyncStringWriter);

		String contentType = pipingServletResponse.getContentType();

		requestDispatcher.include(request, pipingServletResponse);

		if (contentType != null) {
			response.setContentType(contentType);
		}

		request.setAttribute(
			WebKeys.LAYOUT_CONTENT, unsyncStringWriter.getStringBundler());
	}

	protected void processEvent(
			PortletRequestImpl portletRequestImpl,
			StateAwareResponseImpl stateAwareResponseImpl,
			List<LayoutTypePortlet> layoutTypePortlets,
			LayoutTypePortlet layoutTypePortlet, Portlet portlet, Event event)
		throws Exception {

		HttpServletRequest request = portletRequestImpl.getHttpServletRequest();
		HttpServletResponse response =
			stateAwareResponseImpl.getHttpServletResponse();
		HttpSession session = request.getSession();

		String portletId = portlet.getPortletId();

		ServletContext servletContext =
			(ServletContext)request.getAttribute(WebKeys.CTX);

		InvokerPortlet invokerPortlet = PortletInstanceFactoryUtil.create(
			portlet, servletContext);

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);
		PortletContext portletContext = portletConfig.getPortletContext();

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

		long scopeGroupId = getScopeGroupId(
			request, layoutTypePortlet, portletId);

		Layout layoutTypePortletLayout = layoutTypePortlet.getLayout();

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getPortletSetup(
				scopeGroupId, layoutTypePortletLayout, portletId, null);

		EventRequestImpl eventRequestImpl = EventRequestFactory.create(
			request, portlet, invokerPortlet, portletContext, windowState,
			portletMode, portletPreferences, layoutTypePortletLayout.getPlid());

		eventRequestImpl.setEvent(
			serializeEvent(event, invokerPortlet.getPortletClassLoader()));

		Layout layout = stateAwareResponseImpl.getLayout();

		EventResponseImpl eventResponseImpl = EventResponseFactory.create(
			eventRequestImpl, response, portletId,
			stateAwareResponseImpl.getUser(), layout);

		eventRequestImpl.defineObjects(portletConfig, eventResponseImpl);

		try {
			try {
				InvokerPortletImpl.clearResponse(
					session, layout.getPrimaryKey(), portletId,
					LanguageUtil.getLanguageId(eventRequestImpl));

				invokerPortlet.processEvent(
					eventRequestImpl, eventResponseImpl);

				if (eventResponseImpl.isCalledSetRenderParameter()) {
					Map<String, String[]> renderParameterMap =
						new HashMap<String, String[]>();

					MapUtil.copy(
						eventResponseImpl.getRenderParameterMap(),
						renderParameterMap);

					RenderParametersPool.put(
						request, layout.getPlid(), portletId,
						renderParameterMap);
				}
			}
			catch (UnavailableException ue) {
				throw ue;
			}

			processEvents(
				eventRequestImpl, eventResponseImpl, layoutTypePortlets);
		}
		finally {
			eventRequestImpl.cleanUp();
		}
	}

	protected void processEvents(
			PortletRequestImpl portletRequestImpl,
			StateAwareResponseImpl stateAwareResponseImpl,
			List<LayoutTypePortlet> layoutTypePortlets)
		throws Exception {

		List<Event> events = stateAwareResponseImpl.getEvents();

		if (events.size() == 0) {
			return;
		}

		for (Event event : events) {
			javax.xml.namespace.QName qName = event.getQName();

			for (LayoutTypePortlet layoutTypePortlet : layoutTypePortlets) {
				List<Portlet> portlets = layoutTypePortlet.getAllPortlets();

				for (Portlet portlet : portlets) {
					QName processingQName = portlet.getProcessingEvent(
						qName.getNamespaceURI(), qName.getLocalPart());

					if (processingQName != null) {
						processEvent(
							portletRequestImpl, stateAwareResponseImpl,
							layoutTypePortlets, layoutTypePortlet, portlet,
							event);
					}
				}
			}
		}
	}

	protected ActionForward processLayout(
			ActionMapping mapping, HttpServletRequest request,
			HttpServletResponse response, long plid)
		throws Exception {

		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			Layout layout = themeDisplay.getLayout();

			boolean resetLayout = ParamUtil.getBoolean(
				request, "p_l_reset", PropsValues.LAYOUT_DEFAULT_P_L_RESET);

			String portletId = ParamUtil.getString(request, "p_p_id");

			Layout previousLayout = (Layout)session.getAttribute(
				WebKeys.PREVIOUS_LAYOUT);

			if ((previousLayout == null) ||
				(layout.getPlid() != previousLayout.getPlid())) {

				session.setAttribute(WebKeys.PREVIOUS_LAYOUT, layout);

				if (themeDisplay.isSignedIn() &&
					PropsValues.
						AUDIT_MESSAGE_COM_LIFERAY_PORTAL_MODEL_LAYOUT_VIEW &&
					MessageBusUtil.hasMessageListener(DestinationNames.AUDIT)) {

					User user = themeDisplay.getUser();

					AuditMessage auditMessage = new AuditMessage(
						ActionKeys.VIEW, user.getCompanyId(), user.getUserId(),
						user.getFullName(), Layout.class.getName(),
						String.valueOf(layout.getPlid()));

					AuditRouterUtil.route(auditMessage);
				}
			}

			if (!PropsValues.TCK_URL && resetLayout &&
				(Validator.isNull(portletId) ||
				 ((previousLayout != null) &&
				  (layout.getPlid() != previousLayout.getPlid())))) {

				// Always clear render parameters on a layout url, but do not
				// clear on portlet urls invoked on the same layout

				RenderParametersPool.clear(request, plid);
			}

			if (themeDisplay.isLifecycleAction()) {
				Portlet portlet = processPortletRequest(
					request, response, PortletRequest.ACTION_PHASE);

				if (portlet != null) {
					ActionResponseImpl actionResponseImpl =
						(ActionResponseImpl)request.getAttribute(
							JavaConstants.JAVAX_PORTLET_RESPONSE);

					String redirectLocation =
						actionResponseImpl.getRedirectLocation();

					if (Validator.isNotNull(redirectLocation)) {
						response.sendRedirect(redirectLocation);

						return null;
					}

					if (portlet.isActionURLRedirect()) {
						redirectActionURL(
							request, response, actionResponseImpl, portlet);

						return null;
					}
				}
			}
			else if (themeDisplay.isLifecycleRender()) {
				processPortletRequest(
					request, response, PortletRequest.RENDER_PHASE);
			}

			if (themeDisplay.isLifecycleResource()) {
				processPortletRequest(
					request, response, PortletRequest.RESOURCE_PHASE);

				return null;
			}
			else {
				if (response.isCommitted()) {
					return null;
				}

				if (layout != null) {

					// Include layout content before the page loads because
					// portlets on the page can set the page title and page
					// subtitle

					includeLayoutContent(
						request, response, themeDisplay, layout);

					if (themeDisplay.isStateExclusive()) {
						renderExclusive(request, response, themeDisplay);

						return null;
					}
				}

				return mapping.findForward("portal.layout");
			}
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
		finally {
			if (!ServerDetector.isResin()) {
				PortletRequest portletRequest =
					(PortletRequest)request.getAttribute(
						JavaConstants.JAVAX_PORTLET_REQUEST);

				if (portletRequest != null) {
					PortletRequestImpl portletRequestImpl =
						(PortletRequestImpl)portletRequest;

					portletRequestImpl.cleanUp();
				}
			}
		}
	}

	protected Portlet processPortletRequest(
			HttpServletRequest request, HttpServletResponse response,
			String lifecycle)
		throws Exception {

		HttpSession session = request.getSession();

		long companyId = PortalUtil.getCompanyId(request);
		User user = PortalUtil.getUser(request);
		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		String portletId = ParamUtil.getString(request, "p_p_id");

		if (Validator.isNull(portletId)) {
			return null;
		}

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			companyId, portletId);

		if (portlet == null) {
			return null;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long scopeGroupId = PortalUtil.getScopeGroupId(request, portletId);

		themeDisplay.setScopeGroupId(scopeGroupId);

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		InvokerPortlet invokerPortlet = PortletInstanceFactoryUtil.create(
			portlet, servletContext);

		if (user != null) {
			InvokerPortletImpl.clearResponse(
				session, layout.getPrimaryKey(), portletId,
				LanguageUtil.getLanguageId(request));
		}

		PortletConfig portletConfig = PortletConfigFactoryUtil.create(
			portlet, servletContext);
		PortletContext portletContext = portletConfig.getPortletContext();

		WindowState windowState = WindowStateFactory.getWindowState(
			ParamUtil.getString(request, "p_p_state"));

		if (layout.isTypeControlPanel() &&
			((windowState == null) || windowState.equals(WindowState.NORMAL) ||
			 (Validator.isNull(windowState.toString())))) {

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

		processPublicRenderParameters(request, layout, portlet);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (lifecycle.equals(PortletRequest.ACTION_PHASE)) {
			String contentType = request.getHeader(HttpHeaders.CONTENT_TYPE);

			if (_log.isDebugEnabled()) {
				_log.debug("Content type " + contentType);
			}

			UploadServletRequest uploadServletRequest = null;

			try {
				if ((contentType != null) &&
					(contentType.startsWith(
						ContentTypes.MULTIPART_FORM_DATA))) {

					PortletConfigImpl invokerPortletConfigImpl =
						(PortletConfigImpl)invokerPortlet.getPortletConfig();

					if (invokerPortlet.isStrutsPortlet() ||
						((invokerPortletConfigImpl != null) &&
						 (!invokerPortletConfigImpl.isWARFile()))) {

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

				ActionResponseImpl actionResponseImpl =
					ActionResponseFactory.create(
						actionRequestImpl, response, portletId, user, layout,
						windowState, portletMode);

				actionRequestImpl.defineObjects(
					portletConfig, actionResponseImpl);

				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(actionRequestImpl);

				ServiceContextThreadLocal.pushServiceContext(serviceContext);

				boolean access = PortletPermissionUtil.hasAccessPermission(
					permissionChecker, scopeGroupId, layout, portlet,
					portletMode);

				if (access) {
					invokerPortlet.processAction(
						actionRequestImpl, actionResponseImpl);

					actionResponseImpl.transferHeaders(response);
				}

				RenderParametersPool.put(
					request, layout.getPlid(), portletId,
					actionResponseImpl.getRenderParameterMap());

				List<LayoutTypePortlet> layoutTypePortlets = null;

				if (!actionResponseImpl.getEvents().isEmpty()) {
					if (PropsValues.PORTLET_EVENT_DISTRIBUTION_LAYOUT_SET) {
						layoutTypePortlets = getLayoutTypePortlets(
							layout.getGroupId(), layout.isPrivateLayout());
					}
					else {
						if (layout.isTypePortlet()) {
							LayoutTypePortlet layoutTypePortlet =
								(LayoutTypePortlet)layout.getLayoutType();

							layoutTypePortlets =
								new ArrayList<LayoutTypePortlet>();

							layoutTypePortlets.add(layoutTypePortlet);
						}
					}

					processEvents(
						actionRequestImpl, actionResponseImpl,
						layoutTypePortlets);

					actionRequestImpl.defineObjects(
						portletConfig, actionResponseImpl);
				}
			}
			finally {
				if (uploadServletRequest != null) {
					uploadServletRequest.cleanUp();
				}

				ServiceContextThreadLocal.popServiceContext();
			}
		}
		else if (lifecycle.equals(PortletRequest.RENDER_PHASE) ||
				 lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {

			PortalUtil.updateWindowState(
				portletId, user, layout, windowState, request);

			PortalUtil.updatePortletMode(
				portletId, user, layout, portletMode, request);
		}

		if (lifecycle.equals(PortletRequest.RESOURCE_PHASE)) {
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			String portletPrimaryKey = PortletPermissionUtil.getPrimaryKey(
				layout.getPlid(), portletId);

			portletDisplay.setId(portletId);
			portletDisplay.setRootPortletId(portlet.getRootPortletId());
			portletDisplay.setInstanceId(portlet.getInstanceId());
			portletDisplay.setResourcePK(portletPrimaryKey);
			portletDisplay.setPortletName(portletConfig.getPortletName());
			portletDisplay.setNamespace(
				PortalUtil.getPortletNamespace(portletId));

			WebDAVStorage webDAVStorage = portlet.getWebDAVStorageInstance();

			if (webDAVStorage != null) {
				portletDisplay.setWebDAVEnabled(true);
			}
			else {
				portletDisplay.setWebDAVEnabled(false);
			}

			ResourceRequestImpl resourceRequestImpl =
				ResourceRequestFactory.create(
					request, portlet, invokerPortlet, portletContext,
					windowState, portletMode, portletPreferences,
					layout.getPlid());

			ResourceResponseImpl resourceResponseImpl =
				ResourceResponseFactory.create(
					resourceRequestImpl, response, portletId, companyId);

			resourceRequestImpl.defineObjects(
				portletConfig, resourceResponseImpl);

			try {
				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(resourceRequestImpl);

				ServiceContextThreadLocal.pushServiceContext(serviceContext);

				boolean access = PortletPermissionUtil.hasAccessPermission(
					permissionChecker, scopeGroupId, layout, portlet,
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

		return portlet;
	}

	protected void processPublicRenderParameters(
		HttpServletRequest request, Layout layout, Portlet portlet) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, String[]> publicRenderParameters =
			PublicRenderParametersPool.get(request, layout.getPlid());

		Enumeration<String> enu = request.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			String[] values = request.getParameterValues(name);

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

	protected void redirectActionURL(
			HttpServletRequest request, HttpServletResponse response,
			ActionResponseImpl actionResponseImpl, Portlet portlet)
		throws Exception {

		ActionRequestImpl actionRequestImpl =
			(ActionRequestImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		Layout layout = (Layout)request.getAttribute(WebKeys.LAYOUT);

		PortletURL portletURL = new PortletURLImpl(
			actionRequestImpl, actionRequestImpl.getPortletName(),
			layout.getPlid(), PortletRequest.RENDER_PHASE);

		Map<String, String[]> renderParameters =
			actionResponseImpl.getRenderParameterMap();

		for (Map.Entry<String, String[]> entry : renderParameters.entrySet()) {
			String key = entry.getKey();
			String[] value = entry.getValue();

			portletURL.setParameter(key, value);
		}

		response.sendRedirect(portletURL.toString());
	}

	protected void renderExclusive(
			HttpServletRequest request, HttpServletResponse response,
			ThemeDisplay themeDisplay)
		throws Exception {

		RenderRequestImpl renderRequestImpl =
			(RenderRequestImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		RenderResponseImpl renderResponseImpl =
			(RenderResponseImpl)request.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		StringServletResponse stringResponse =
			(StringServletResponse)renderRequestImpl.getAttribute(
				WebKeys.STRING_SERVLET_RESPONSE);

		if (stringResponse == null) {
			stringResponse = (StringServletResponse)
				renderResponseImpl.getHttpServletResponse();

			Portlet portlet = processPortletRequest(
				request, response, PortletRequest.RENDER_PHASE);

			InvokerPortlet invokerPortlet = PortletInstanceFactoryUtil.create(
				portlet, null);

			invokerPortlet.render(renderRequestImpl, renderResponseImpl);

			if (Validator.isNull(stringResponse.getString())) {
				stringResponse.setString(null);
			}
		}

		renderResponseImpl.transferHeaders(response);

		if (stringResponse.isCalledGetOutputStream()) {
			UnsyncByteArrayOutputStream ubaos =
				stringResponse.getUnsyncByteArrayOutputStream();

			InputStream is = new UnsyncByteArrayInputStream(
				ubaos.unsafeGetByteArray(), 0, ubaos.size());

			ServletResponseUtil.sendFile(
				request, response, renderResponseImpl.getResourceName(), is,
				renderResponseImpl.getContentType());
		}
		else if (stringResponse.isCalledGetWriter()) {
			byte[] content = stringResponse.getString().getBytes(
				StringPool.UTF8);

			ServletResponseUtil.sendFile(
				request, response, renderResponseImpl.getResourceName(),
				content, renderResponseImpl.getContentType());
		}

		renderRequestImpl.cleanUp();
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

		String base64Value = eventImpl.getBase64Value();

		value = (Serializable)Base64.stringToObject(
			base64Value, portletClassLoader);

		return new EventImpl(event.getName(), event.getQName(), value);
	}

	private static Log _log = LogFactoryUtil.getLog(LayoutAction.class);

}