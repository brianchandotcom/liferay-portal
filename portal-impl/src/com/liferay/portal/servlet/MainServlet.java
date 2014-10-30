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

package com.liferay.portal.servlet;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.PortalSessionThreadLocal;
import com.liferay.portal.kernel.servlet.ProtectedServletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.jaas.JAASHelper;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.servlet.filters.absoluteredirects.AbsoluteRedirectsResponse;
import com.liferay.portal.struts.PortletRequestProcessor;
import com.liferay.portal.struts.StrutsUtil;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.ShutdownUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.ContentUtil;
import com.liferay.util.servlet.EncryptedServletRequest;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.RequestProcessor;
import org.apache.struts.config.ControllerConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.tiles.TilesUtilImpl;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Brian Myunghun Kim
 */
public class MainServlet extends ActionServlet {

	@Override
	public void destroy() {
		if (_log.isDebugEnabled()) {
			_log.debug("Destroy");
		}

		callParentDestroy();
	}

	@Override
	public void init() throws ServletException {
		if (_log.isDebugEnabled()) {
			_log.debug("Initialize");
		}

		ServletContext servletContext = getServletContext();

		servletContext.setAttribute(MainServlet.class.getName(), Boolean.TRUE);

		callParentInit();

		ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);
	}

	@Override
	public void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (_log.isDebugEnabled()) {
			_log.debug("Process service request");
		}

		if (processShutdownRequest(request, response)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processed shutdown request");
			}

			return;
		}

		if (processMaintenanceRequest(request, response)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processed maintenance request");
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Get company id");
		}

		long companyId = getCompanyId(request);

		if (processCompanyInactiveRequest(request, response, companyId)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processed company inactive request");
			}

			return;
		}

		try {
			if (processGroupInactiveRequest(request, response)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Processed site inactive request");
				}

				return;
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchLayoutException) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
			else {
				_log.error(e, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Set portal port");
		}

		setPortalInetSocketAddresses(request);

		if (_log.isDebugEnabled()) {
			_log.debug("Check variables");
		}

		checkServletContext(request);
		checkPortletRequestProcessor(request);
		checkTilesDefinitionsFactory();

		if (_log.isDebugEnabled()) {
			_log.debug("Handle non-serializable request");
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Encrypt request");
		}

		request = encryptRequest(request, companyId);

		long userId = getUserId(request);

		String remoteUser = getRemoteUser(request, userId);

		if (_log.isDebugEnabled()) {
			_log.debug("Protect request");
		}

		request = protectRequest(request, remoteUser);

		if (_log.isDebugEnabled()) {
			_log.debug("Set principal");
		}

		String password = getPassword(request);

		setPrincipal(companyId, userId, remoteUser, password);

		try {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Authenticate user id " + userId + " and remote user " +
						remoteUser);
			}

			userId = loginUser(
				request, response, companyId, userId, remoteUser);

			if (_log.isDebugEnabled()) {
				_log.debug("Authenticated user id " + userId);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Set session thread local");
		}

		PortalSessionThreadLocal.setHttpSession(request.getSession());

		if (_log.isDebugEnabled()) {
			_log.debug("Process service pre events");
		}

		if (processServicePre(request, response, userId)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Processing service pre events has errors");
			}

			return;
		}

		if (hasAbsoluteRedirect(request)) {
			if (_log.isDebugEnabled()) {
				String currentURL = PortalUtil.getCurrentURL(request);

				_log.debug(
					"Current URL " + currentURL + " has absolute redirect");
			}

			return;
		}

		if (!hasThemeDisplay(request)) {
			if (_log.isDebugEnabled()) {
				String currentURL = PortalUtil.getCurrentURL(request);

				_log.debug(
					"Current URL " + currentURL +
						" does not have a theme display");
			}

			return;
		}

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Call parent service");
			}

			callParentService(request, response);
		}
		finally {
			if (_log.isDebugEnabled()) {
				_log.debug("Process service post events");
			}

			processServicePost(request, response);
		}
	}

	protected void callParentDestroy() {
		super.destroy();
	}

	protected void callParentInit() throws ServletException {
		super.init();
	}

	protected void callParentService(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		super.service(request, response);
	}

	protected void checkPortletRequestProcessor(HttpServletRequest request)
		throws ServletException {

		ServletContext servletContext = getServletContext();

		PortletRequestProcessor portletReqProcessor =
			(PortletRequestProcessor)servletContext.getAttribute(
				WebKeys.PORTLET_STRUTS_PROCESSOR);

		if (portletReqProcessor == null) {
			ModuleConfig moduleConfig = getModuleConfig(request);

			portletReqProcessor = PortletRequestProcessor.getInstance(
				this, moduleConfig);

			servletContext.setAttribute(
				WebKeys.PORTLET_STRUTS_PROCESSOR, portletReqProcessor);
		}
	}

	protected void checkServletContext(HttpServletRequest request) {
		ServletContext servletContext = getServletContext();

		request.setAttribute(WebKeys.CTX, servletContext);

		String contextPath = request.getContextPath();

		servletContext.setAttribute(WebKeys.CTX_PATH, contextPath);
	}

	protected void checkTilesDefinitionsFactory() {
		ServletContext servletContext = getServletContext();

		if (servletContext.getAttribute(
				TilesUtilImpl.DEFINITIONS_FACTORY) != null) {

			return;
		}

		servletContext.setAttribute(
			TilesUtilImpl.DEFINITIONS_FACTORY,
			servletContext.getAttribute(TilesUtilImpl.DEFINITIONS_FACTORY));
	}

	protected HttpServletRequest encryptRequest(
		HttpServletRequest request, long companyId) {

		boolean encryptRequest = ParamUtil.getBoolean(request, WebKeys.ENCRYPT);

		if (!encryptRequest) {
			return request;
		}

		try {
			Company company = CompanyLocalServiceUtil.getCompanyById(companyId);

			request = new EncryptedServletRequest(request, company.getKeyObj());
		}
		catch (Exception e) {
		}

		return request;
	}

	protected long getCompanyId(HttpServletRequest request) {
		return PortalInstances.getCompanyId(request);
	}

	protected String getPassword(HttpServletRequest request) {
		return PortalUtil.getUserPassword(request);
	}

	protected String getRemoteUser(HttpServletRequest request, long userId) {
		String remoteUser = request.getRemoteUser();

		if (!PropsValues.PORTAL_JAAS_ENABLE) {
			HttpSession session = request.getSession();

			String jRemoteUser = (String)session.getAttribute("j_remoteuser");

			if (jRemoteUser != null) {
				remoteUser = jRemoteUser;

				session.removeAttribute("j_remoteuser");
			}
		}

		if ((userId > 0) && (remoteUser == null)) {
			remoteUser = String.valueOf(userId);
		}

		return remoteUser;
	}

	@Override
	protected synchronized RequestProcessor getRequestProcessor(
			ModuleConfig moduleConfig)
		throws ServletException {

		ServletContext servletContext = getServletContext();

		String key = Globals.REQUEST_PROCESSOR_KEY + moduleConfig.getPrefix();

		RequestProcessor requestProcessor =
			(RequestProcessor)servletContext.getAttribute(key);

		if (requestProcessor == null) {
			ControllerConfig controllerConfig =
				moduleConfig.getControllerConfig();

			try {
				requestProcessor =
					(RequestProcessor)InstanceFactory.newInstance(
						ClassLoaderUtil.getPortalClassLoader(),
						controllerConfig.getProcessorClass());
			}
			catch (Exception e) {
				throw new ServletException(e);
			}

			requestProcessor.init(this, moduleConfig);

			servletContext.setAttribute(key, requestProcessor);
		}

		return requestProcessor;
	}

	protected long getUserId(HttpServletRequest request) {
		return PortalUtil.getUserId(request);
	}

	protected boolean hasAbsoluteRedirect(HttpServletRequest request) {
		if (request.getAttribute(
				AbsoluteRedirectsResponse.class.getName()) == null) {

			return false;
		}
		else {
			return true;
		}
	}

	protected boolean hasThemeDisplay(HttpServletRequest request) {
		if (request.getAttribute(WebKeys.THEME_DISPLAY) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	protected long loginUser(
			HttpServletRequest request, HttpServletResponse response,
			long companyId, long userId, String remoteUser)
		throws PortalException {

		if ((userId > 0) || (remoteUser == null)) {
			return userId;
		}

		if (PropsValues.PORTAL_JAAS_ENABLE) {
			userId = JAASHelper.getJaasUserId(companyId, remoteUser);
		}
		else {
			userId = GetterUtil.getLong(remoteUser);
		}

		EventsProcessorUtil.process(
			PropsKeys.LOGIN_EVENTS_PRE, PropsValues.LOGIN_EVENTS_PRE, request,
			response);

		User user = UserLocalServiceUtil.getUserById(userId);

		if (PropsValues.USERS_UPDATE_LAST_LOGIN && !user.isDefaultUser()) {
			user = UserLocalServiceUtil.updateLastLogin(
				userId, request.getRemoteAddr());
		}

		HttpSession session = request.getSession();

		session.setAttribute(WebKeys.USER, user);
		session.setAttribute(WebKeys.USER_ID, new Long(userId));
		session.setAttribute(Globals.LOCALE_KEY, user.getLocale());

		EventsProcessorUtil.process(
			PropsKeys.LOGIN_EVENTS_POST, PropsValues.LOGIN_EVENTS_POST, request,
			response);

		return userId;
	}

	protected boolean processCompanyInactiveRequest(
			HttpServletRequest request, HttpServletResponse response,
			long companyId)
		throws IOException {

		if (PortalInstances.isCompanyActive(companyId)) {
			return false;
		}

		processInactiveRequest(
			request, response,
			"this-instance-is-inactive-please-contact-the-administrator");

		return true;
	}

	protected boolean processGroupInactiveRequest(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, PortalException {

		long plid = ParamUtil.getLong(request, "p_l_id");

		if (plid <= 0) {
			return false;
		}

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		Group group = layout.getGroup();

		if (group.isActive()) {
			return false;
		}

		processInactiveRequest(
			request, response,
			"this-site-is-inactive-please-contact-the-administrator");

		return true;
	}

	protected void processInactiveRequest(
			HttpServletRequest request, HttpServletResponse response,
			String messageKey)
		throws IOException {

		response.setContentType(ContentTypes.TEXT_HTML_UTF8);

		Locale locale = LocaleUtil.getDefault();

		String message = LanguageUtil.get(locale, messageKey);

		String html = ContentUtil.get(
			"com/liferay/portal/dependencies/inactive.html");

		html = StringUtil.replace(html, "[$MESSAGE$]", message);

		PrintWriter printWriter = response.getWriter();

		printWriter.print(html);
	}

	protected boolean processMaintenanceRequest(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (!MaintenanceUtil.isMaintaining()) {
			return false;
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(
			"/html/portal/maintenance.jsp");

		requestDispatcher.include(request, response);

		return true;
	}

	protected void processServicePost(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_POST,
				PropsValues.SERVLET_SERVICE_EVENTS_POST, request, response);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected boolean processServicePre(
			HttpServletRequest request, HttpServletResponse response,
			long userId)
		throws IOException, ServletException {

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SERVICE_EVENTS_PRE,
				PropsValues.SERVLET_SERVICE_EVENTS_PRE, request, response);
		}
		catch (Exception e) {
			Throwable cause = e.getCause();

			if (cause instanceof NoSuchLayoutException) {
				sendError(
					HttpServletResponse.SC_NOT_FOUND, cause, request, response);

				return true;
			}
			else if (cause instanceof PrincipalException) {
				processServicePrePrincipalException(
					cause, userId, request, response);

				return true;
			}

			_log.error(e, e);

			request.setAttribute(PageContext.EXCEPTION, e);

			ServletContext servletContext = getServletContext();

			StrutsUtil.forward(
				PropsValues.SERVLET_SERVICE_EVENTS_PRE_ERROR_PAGE,
				servletContext, request, response);

			return true;
		}

		if (_HTTP_HEADER_VERSION_VERBOSITY_DEFAULT) {
		}
		else if (_HTTP_HEADER_VERSION_VERBOSITY_PARTIAL) {
			response.addHeader(
				_LIFERAY_PORTAL_REQUEST_HEADER, ReleaseInfo.getName());
		}
		else {
			response.addHeader(
				_LIFERAY_PORTAL_REQUEST_HEADER, ReleaseInfo.getReleaseInfo());
		}

		return false;
	}

	protected void processServicePrePrincipalException(
			Throwable t, long userId, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		if (userId > 0) {
			sendError(
				HttpServletResponse.SC_UNAUTHORIZED, t, request, response);

			return;
		}

		String redirect = PortalUtil.getPathMain().concat("/portal/login");

		String currentURL = PortalUtil.getCurrentURL(request);

		redirect = HttpUtil.addParameter(redirect, "redirect", currentURL);

		long plid = ParamUtil.getLong(request, "p_l_id");

		if (plid > 0) {
			try {
				redirect = HttpUtil.addParameter(redirect, "refererPlid", plid);

				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				Group group = layout.getGroup();

				plid = group.getDefaultPublicPlid();

				if ((plid == LayoutConstants.DEFAULT_PLID) ||
					group.isStagingGroup()) {

					Group guestGroup = GroupLocalServiceUtil.getGroup(
						layout.getCompanyId(), GroupConstants.GUEST);

					plid = guestGroup.getDefaultPublicPlid();
				}

				redirect = HttpUtil.addParameter(redirect, "p_l_id", plid);
			}
			catch (Exception e) {
			}
		}

		response.sendRedirect(redirect);
	}

	protected boolean processShutdownRequest(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		if (!ShutdownUtil.isShutdown()) {
			return false;
		}

		String messageKey = ShutdownUtil.getMessage();

		if (Validator.isNull(messageKey)) {
			messageKey = "the-system-is-shutdown-please-try-again-later";
		}

		processInactiveRequest(request, response, messageKey);

		return true;
	}

	protected HttpServletRequest protectRequest(
		HttpServletRequest request, String remoteUser) {

		// WebSphere will not return the remote user unless you are
		// authenticated AND accessing a protected path. Other servers will
		// return the remote user for all threads associated with an
		// authenticated user. We use ProtectedServletRequest to ensure we get
		// similar behavior across all servers.

		return new ProtectedServletRequest(request, remoteUser);
	}

	protected void sendError(
			int status, Throwable t, HttpServletRequest request,
			HttpServletResponse response)
		throws IOException, ServletException {

		DynamicServletRequest dynamicRequest = new DynamicServletRequest(
			request);

		// Reset layout params or there will be an infinite loop

		dynamicRequest.setParameter("p_l_id", StringPool.BLANK);

		dynamicRequest.setParameter("groupId", StringPool.BLANK);
		dynamicRequest.setParameter("layoutId", StringPool.BLANK);
		dynamicRequest.setParameter("privateLayout", StringPool.BLANK);

		PortalUtil.sendError(status, (Exception)t, dynamicRequest, response);
	}

	protected void setPortalInetSocketAddresses(HttpServletRequest request) {
		PortalUtil.setPortalInetSocketAddresses(request);
	}

	protected void setPrincipal(
		long companyId, long userId, String remoteUser, String password) {

		if ((userId == 0) && (remoteUser == null)) {
			return;
		}

		String name = String.valueOf(userId);

		if (PropsValues.PORTAL_JAAS_ENABLE) {
			long remoteUserId = 0;

			try {
				remoteUserId = JAASHelper.getJaasUserId(companyId, remoteUser);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}

			if (remoteUserId > 0) {
				name = String.valueOf(remoteUserId);
			}
		}
		else if (remoteUser != null) {
			name = remoteUser;
		}

		PrincipalThreadLocal.setName(name);

		PrincipalThreadLocal.setPassword(password);
	}

	private static final boolean _HTTP_HEADER_VERSION_VERBOSITY_DEFAULT =
		StringUtil.equalsIgnoreCase(
			PropsValues.HTTP_HEADER_VERSION_VERBOSITY, ReleaseInfo.getName());

	private static final boolean _HTTP_HEADER_VERSION_VERBOSITY_PARTIAL =
		StringUtil.equalsIgnoreCase(
			PropsValues.HTTP_HEADER_VERSION_VERBOSITY, "partial");

	private static final String _LIFERAY_PORTAL_REQUEST_HEADER =
		"Liferay-Portal";

	private static final Log _log = LogFactoryUtil.getLog(MainServlet.class);

}