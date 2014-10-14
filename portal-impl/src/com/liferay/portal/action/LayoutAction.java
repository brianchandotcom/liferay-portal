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

package com.liferay.portal.action;

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouterUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.servlet.MetaInfoCacheServletResponse;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.sso.SSOUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletRequestImpl;
import com.liferay.portlet.RenderParametersPool;
import com.liferay.portlet.login.util.LoginUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class LayoutAction extends Action {

	public LayoutAction() {
		_layoutResetPortletIds = new HashSet<String>(
			Arrays.asList(PropsValues.LAYOUT_RESET_PORTLET_IDS));

		_layoutResetPortletIds.add(StringPool.BLANK);
	}

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		MetaInfoCacheServletResponse metaInfoCacheServletResponse =
			new MetaInfoCacheServletResponse(response);

		try {
			return doExecute(
				actionMapping, actionForm, request,
				metaInfoCacheServletResponse);
		}
		finally {
			metaInfoCacheServletResponse.finishResponse();
		}
	}

	protected ActionForward doExecute(
			ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Boolean layoutDefault = (Boolean)request.getAttribute(
			WebKeys.LAYOUT_DEFAULT);

		if (Boolean.TRUE.equals(layoutDefault)) {
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

				String authLoginURL = SSOUtil.getSignInURL(
					themeDisplay.getCompanyId(), themeDisplay.getURLSignIn());

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

				response.sendRedirect(authLoginURL);
			}
			else {
				Layout layout = themeDisplay.getLayout();

				String redirect = PortalUtil.getLayoutURL(layout, themeDisplay);

				if (_log.isDebugEnabled()) {
					_log.debug("Redirect default layout to " + redirect);
				}

				response.sendRedirect(redirect);
			}

			return null;
		}

		long plid = ParamUtil.getLong(request, "p_l_id");

		if (_log.isDebugEnabled()) {
			_log.debug("p_l_id is " + plid);
		}

		if (plid > 0) {
			Layout layout = themeDisplay.getLayout();

			if (layout != null) {
				plid = layout.getPlid();
			}

			ActionForward actionForward = processLayout(
				actionMapping, request, response, plid);

			return actionForward;
		}

		try {
			forwardLayout(request);

			return actionMapping.findForward(
				ActionConstants.COMMON_FORWARD_JSP);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
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

	protected ActionForward processLayout(
			ActionMapping actionMapping, HttpServletRequest request,
			HttpServletResponse response, long plid)
		throws Exception {

		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			Layout layout = themeDisplay.getLayout();

			Layout previousLayout = (Layout)session.getAttribute(
				WebKeys.PREVIOUS_LAYOUT);

			if ((previousLayout == null) ||
				(layout.getPlid() != previousLayout.getPlid())) {

				session.setAttribute(WebKeys.PREVIOUS_LAYOUT, layout);

				if (themeDisplay.isSignedIn() &&
					PropsValues.
						AUDIT_MESSAGE_COM_LIFERAY_PORTAL_MODEL_LAYOUT_VIEW &&
					AuditRouterUtil.isDeployed()) {

					User user = themeDisplay.getUser();

					AuditMessage auditMessage = new AuditMessage(
						ActionKeys.VIEW, user.getCompanyId(), user.getUserId(),
						user.getFullName(), Layout.class.getName(),
						String.valueOf(layout.getPlid()));

					AuditRouterUtil.route(auditMessage);
				}
			}

			boolean resetLayout = ParamUtil.getBoolean(
				request, "p_l_reset", PropsValues.LAYOUT_DEFAULT_P_L_RESET);

			String portletId = ParamUtil.getString(request, "p_p_id");

			if (!PropsValues.TCK_URL && resetLayout &&
				(_layoutResetPortletIds.contains(portletId) ||
				 ((previousLayout != null) &&
				  (layout.getPlid() != previousLayout.getPlid())))) {

				// Always clear render parameters on a layout url, but do not
				// clear on portlet urls invoked on the same layout

				RenderParametersPool.clear(request, plid);
			}

			Portlet portlet = null;

			if (Validator.isNotNull(portletId)) {
				long companyId = PortalUtil.getCompanyId(request);

				portlet = PortletLocalServiceUtil.getPortletById(
					companyId, portletId);
			}

			if (portlet != null) {
				PortletContainerUtil.preparePortlet(request, portlet);

				if (themeDisplay.isLifecycleAction()) {
					PortletContainerUtil.processAction(
						request, response, portlet);

					if (response.isCommitted()) {
						return null;
					}
				}
				else if (themeDisplay.isLifecycleResource()) {
					PortletContainerUtil.serveResource(
						request, response, portlet);

					return null;
				}
			}

			if (layout != null) {
				if (themeDisplay.isStateExclusive()) {
					PortletContainerUtil.render(request, response, portlet);

					return null;
				}

				// Include layout content before the page loads because portlets
				// on the page can set the page title and page subtitle

				if (layout.includeLayoutContent(request, response)) {
					return null;
				}
			}

			return actionMapping.findForward("portal.layout");
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
						PortletRequestImpl.getPortletRequestImpl(
							portletRequest);

					portletRequestImpl.cleanUp();
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(LayoutAction.class);

	private final Set<String> _layoutResetPortletIds;

}