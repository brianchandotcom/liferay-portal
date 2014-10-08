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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.StrictPortletPreferencesImpl;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.util.MessageResources;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletAction extends Action {

	public static String getForwardKey(HttpServletRequest request) {
		String portletId = (String)request.getAttribute(WebKeys.PORTLET_ID);

		String portletNamespace = PortalUtil.getPortletNamespace(portletId);

		return portletNamespace.concat(WebKeys.PORTLET_STRUTS_FORWARD);
	}

	public static String getForwardKey(PortletRequest portletRequest) {
		String portletId = (String)portletRequest.getAttribute(
			WebKeys.PORTLET_ID);

		String portletNamespace = PortalUtil.getPortletNamespace(portletId);

		return portletNamespace.concat(WebKeys.PORTLET_STRUTS_FORWARD);
	}

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		PortletConfig portletConfig = (PortletConfig)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		PortletRequest portletRequest = (PortletRequest)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		Boolean strutsExecute = (Boolean)request.getAttribute(
			WebKeys.PORTLET_STRUTS_EXECUTE);

		if ((strutsExecute != null) && strutsExecute.booleanValue()) {
			return strutsExecute(actionMapping, actionForm, request, response);
		}
		else if (portletRequest instanceof RenderRequest) {
			return render(
				actionMapping, actionForm, portletConfig,
				(RenderRequest)portletRequest, (RenderResponse)portletResponse);
		}
		else {
			if (portletRequest instanceof EventRequest) {
				processEvent(
					actionMapping, actionForm, portletConfig,
					(EventRequest)portletRequest,
					(EventResponse)portletResponse);
			}
			else {
				serveResource(
					actionMapping, actionForm, portletConfig,
					(ResourceRequest)portletRequest,
					(ResourceResponse)portletResponse);
			}

			return actionMapping.findForward(ActionConstants.COMMON_NULL);
		}
	}

	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {
	}

	public void processEvent(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, EventRequest eventRequest,
			EventResponse eventResponse)
		throws Exception {
	}

	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		if (_log.isDebugEnabled()) {
			_log.debug("Forward to " + getForward(renderRequest));
		}

		return actionMapping.findForward(getForward(renderRequest));
	}

	public void serveResource(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws Exception {

		String resourceId = resourceRequest.getResourceID();

		if (Validator.isNull(resourceId)) {
			return;
		}

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(resourceId);

		if (portletRequestDispatcher == null) {
			return;
		}

		portletRequestDispatcher.forward(resourceRequest, resourceResponse);
	}

	public ActionForward strutsExecute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		return super.execute(actionMapping, actionForm, request, response);
	}

	protected void addSuccessMessage(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		PortletConfig portletConfig = (PortletConfig)actionRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG);

		boolean addProcessActionSuccessMessage = GetterUtil.getBoolean(
			portletConfig.getInitParameter("add-process-action-success-action"),
			true);

		if (!addProcessActionSuccessMessage) {
			return;
		}

		String successMessage = ParamUtil.getString(
			actionRequest, "successMessage");

		SessionMessages.add(actionRequest, "requestProcessed", successMessage);
	}

	protected String getForward(PortletRequest portletRequest) {
		return getForward(portletRequest, null);
	}

	protected String getForward(
		PortletRequest portletRequest, String defaultValue) {

		String forward = (String)portletRequest.getAttribute(
			getForwardKey(portletRequest));

		if (forward == null) {
			return defaultValue;
		}
		else {
			return forward;
		}
	}

	protected ModuleConfig getModuleConfig(PortletRequest portletRequest) {
		return (ModuleConfig)portletRequest.getAttribute(Globals.MODULE_KEY);
	}

	protected MessageResources getResources() {
		ServletContext servletContext = getServlet().getServletContext();

		return (MessageResources)servletContext.getAttribute(
			Globals.MESSAGES_KEY);
	}

	@Override
	protected MessageResources getResources(HttpServletRequest request) {
		return getResources();
	}

	protected MessageResources getResources(PortletRequest portletRequest) {
		return getResources();
	}

	protected PortletPreferences getStrictPortletSetup(
			Layout layout, String portletId)
		throws PortalException {

		if (Validator.isNull(portletId)) {
			return null;
		}

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.getStrictPortletSetup(
				layout, portletId);

		if (portletPreferences instanceof StrictPortletPreferencesImpl) {
			throw new PrincipalException();
		}

		return portletPreferences;
	}

	protected PortletPreferences getStrictPortletSetup(
			PortletRequest portletRequest)
		throws PortalException {

		String portletResource = ParamUtil.getString(
			portletRequest, "portletResource");

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return getStrictPortletSetup(themeDisplay.getLayout(), portletResource);
	}

	protected void hideDefaultErrorMessage(PortletRequest portletRequest) {
		SessionMessages.add(
			portletRequest,
			PortalUtil.getPortletId(portletRequest) +
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
	}

	/**
	 * @deprecated As of 6.2.0 {@link
	 *             #hideDefaultSuccessMessage(PortletRequest)}
	 */
	@Deprecated
	protected void hideDefaultSuccessMessage(
		PortletConfig portletConfig, PortletRequest portletRequest) {

		hideDefaultSuccessMessage(portletRequest);
	}

	protected void hideDefaultSuccessMessage(PortletRequest portletRequest) {
		SessionMessages.add(
			portletRequest,
			PortalUtil.getPortletId(portletRequest) +
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
	}

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected boolean isDisplaySuccessMessage(PortletRequest portletRequest) {
		if (!SessionErrors.isEmpty(portletRequest)) {
			return false;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		if (layout.isTypeControlPanel()) {
			return true;
		}

		String portletId = (String)portletRequest.getAttribute(
			WebKeys.PORTLET_ID);

		try {
			LayoutTypePortlet layoutTypePortlet =
				themeDisplay.getLayoutTypePortlet();

			if (layoutTypePortlet.hasPortletId(portletId)) {
				return true;
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), portletId);

		if (portlet.isAddDefaultResource()) {
			return true;
		}

		return false;
	}

	protected boolean redirectToLogin(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		if (actionRequest.getRemoteUser() == null) {
			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);

			SessionErrors.add(request, PrincipalException.class.getName());

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			actionResponse.sendRedirect(themeDisplay.getURLSignIn());

			return true;
		}
		else {
			return false;
		}
	}

	protected void sendRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException {

		sendRedirect(actionRequest, actionResponse, null);
	}

	protected void sendRedirect(
			ActionRequest actionRequest, ActionResponse actionResponse,
			String redirect)
		throws IOException {

		sendRedirect(null, actionRequest, actionResponse, redirect, null);
	}

	protected void sendRedirect(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse, String redirect,
			String closeRedirect)
		throws IOException {

		if (isDisplaySuccessMessage(actionRequest)) {
			addSuccessMessage(actionRequest, actionResponse);
		}

		if (Validator.isNull(redirect)) {
			redirect = (String)actionRequest.getAttribute(WebKeys.REDIRECT);
		}

		if (Validator.isNull(redirect)) {
			redirect = ParamUtil.getString(actionRequest, "redirect");
		}

		if ((portletConfig != null) && Validator.isNotNull(redirect) &&
			Validator.isNotNull(closeRedirect)) {

			redirect = HttpUtil.setParameter(
				redirect, "closeRedirect", closeRedirect);

			SessionMessages.add(
				actionRequest,
				PortalUtil.getPortletId(actionRequest) +
					SessionMessages.KEY_SUFFIX_CLOSE_REDIRECT,
				closeRedirect);
		}

		if (Validator.isNull(redirect)) {
			return;
		}

		// LPS-1928

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			actionRequest);

		if (BrowserSnifferUtil.isIe(request) &&
			(BrowserSnifferUtil.getMajorVersion(request) == 6.0) &&
			redirect.contains(StringPool.POUND)) {

			String redirectToken = "&#";

			if (!redirect.contains(StringPool.QUESTION)) {
				redirectToken = StringPool.QUESTION + redirectToken;
			}

			redirect = StringUtil.replace(
				redirect, StringPool.POUND, redirectToken);
		}

		redirect = PortalUtil.escapeRedirect(redirect);

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	protected void setForward(PortletRequest portletRequest, String forward) {
		portletRequest.setAttribute(getForwardKey(portletRequest), forward);
	}

	protected void writeJSON(
			PortletRequest portletRequest, ActionResponse actionResponse,
			Object json)
		throws IOException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		String contentType = ContentTypes.APPLICATION_JSON;

		if (BrowserSnifferUtil.isIe(request)) {
			contentType = ContentTypes.TEXT_HTML;
		}

		HttpServletResponse response = PortalUtil.getHttpServletResponse(
			actionResponse);

		response.setContentType(contentType);

		ServletResponseUtil.write(response, json.toString());

		response.flushBuffer();

		setForward(portletRequest, ActionConstants.COMMON_NULL);
	}

	protected void writeJSON(
			PortletRequest portletRequest, MimeResponse mimeResponse,
			Object json)
		throws IOException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		String contentType = ContentTypes.APPLICATION_JSON;

		if (BrowserSnifferUtil.isIe(request)) {
			contentType = ContentTypes.TEXT_HTML;
		}

		mimeResponse.setContentType(contentType);

		PortletResponseUtil.write(mimeResponse, json.toString());

		mimeResponse.flushBuffer();
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = true;

	private static final Log _log = LogFactoryUtil.getLog(PortletAction.class);

}