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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouterUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.PortletContainerUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.servlet.BufferCacheServletResponse;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.sso.SSOUtil;
import com.liferay.portal.struts.Action;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.model.ActionForward;
import com.liferay.portal.struts.model.ActionMapping;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.LiferayPortletUtil;
import com.liferay.portlet.RenderParametersPool;
import com.liferay.portlet.internal.RenderData;
import com.liferay.portlet.internal.RenderStateUtil;

import java.io.PrintWriter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Neil Griffin
 */
public class LayoutAction implements Action {

	@Override
	public ActionForward execute(
			ActionMapping actionMapping, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Boolean layoutDefault = (Boolean)httpServletRequest.getAttribute(
			WebKeys.LAYOUT_DEFAULT);

		if (Boolean.TRUE.equals(layoutDefault)) {
			Layout requestedLayout = (Layout)httpServletRequest.getAttribute(
				WebKeys.REQUESTED_LAYOUT);

			if (requestedLayout != null) {
				String redirectParam = "redirect";

				if (Validator.isNotNull(PropsValues.AUTH_LOGIN_PORTLET_NAME)) {
					String portletNamespace = PortalUtil.getPortletNamespace(
						PropsValues.AUTH_LOGIN_PORTLET_NAME);

					redirectParam = portletNamespace + redirectParam;
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
					PortletURL loginURL = PortletURLFactoryUtil.create(
						httpServletRequest, PortletKeys.LOGIN,
						PortletRequest.RENDER_PHASE);

					loginURL.setParameter(
						"saveLastPath", Boolean.FALSE.toString());
					loginURL.setParameter(
						"mvcRenderCommandName", "/login/login");
					loginURL.setPortletMode(PortletMode.VIEW);
					loginURL.setWindowState(WindowState.MAXIMIZED);

					authLoginURL = loginURL.toString();
				}

				authLoginURL = HttpUtil.setParameter(
					authLoginURL, "p_p_id",
					PropsValues.AUTH_LOGIN_PORTLET_NAME);

				authLoginURL = HttpUtil.setParameter(
					authLoginURL, redirectParam,
					PortalUtil.getCurrentURL(httpServletRequest));

				if (_log.isDebugEnabled()) {
					_log.debug("Redirect requested layout to " + authLoginURL);
				}

				httpServletResponse.sendRedirect(authLoginURL);
			}
			else {
				String redirect = PortalUtil.getLayoutURL(
					themeDisplay.getLayout(), themeDisplay);

				if (_log.isDebugEnabled()) {
					_log.debug("Redirect default layout to " + redirect);
				}

				httpServletResponse.sendRedirect(redirect);
			}

			return null;
		}

		long plid = ParamUtil.getLong(httpServletRequest, "p_l_id");

		if (_log.isDebugEnabled()) {
			_log.debug("p_l_id is " + plid);
		}

		if (plid > 0) {
			Layout layout = themeDisplay.getLayout();

			if (layout != null) {
				plid = layout.getPlid();
			}

			return processLayout(
				actionMapping, httpServletRequest, httpServletResponse, plid);
		}

		try {
			forwardLayout(httpServletRequest);

			return actionMapping.getActionForward(
				ActionConstants.COMMON_FORWARD_JSP);
		}
		catch (Exception e) {
			PortalUtil.sendError(e, httpServletRequest, httpServletResponse);

			return null;
		}
	}

	protected void forwardLayout(HttpServletRequest httpServletRequest)
		throws Exception {

		Layout layout = (Layout)httpServletRequest.getAttribute(WebKeys.LAYOUT);

		long plid = LayoutConstants.DEFAULT_PLID;

		String layoutFriendlyURL = null;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
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

		httpServletRequest.setAttribute(WebKeys.FORWARD_URL, forwardURL);
	}

	protected String getRenderStateJSON(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, ThemeDisplay themeDisplay,
			String portletId, LayoutTypePortlet layoutTypePortlet)
		throws Exception {

		Map<String, RenderData> renderDataMap = new HashMap<>();

		List<Portlet> allPortlets = layoutTypePortlet.getAllPortlets();

		for (Portlet curPortlet : allPortlets) {
			String curPortletId = curPortlet.getPortletId();

			if (curPortletId.equals(portletId) ||
				curPortlet.isPartialActionServeResource()) {

				BufferCacheServletResponse bufferCacheServletResponse =
					new BufferCacheServletResponse(httpServletResponse);

				PortletContainerUtil.preparePortlet(
					httpServletRequest, curPortlet);

				PortletContainerUtil.serveResource(
					httpServletRequest, bufferCacheServletResponse, curPortlet);

				RenderData renderData = new RenderData(
					bufferCacheServletResponse.getContentType(),
					bufferCacheServletResponse.getString());

				renderDataMap.put(curPortletId, renderData);
			}
		}

		return RenderStateUtil.generateJSON(
			httpServletRequest, themeDisplay, renderDataMap);
	}

	protected ActionForward processLayout(
			ActionMapping actionMapping, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long plid)
		throws Exception {

		HttpSession session = httpServletRequest.getSession();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			Layout layout = themeDisplay.getLayout();

			if ((layout != null) && layout.isTypeURL()) {
				String redirect = PortalUtil.getLayoutActualURL(layout);

				httpServletResponse.sendRedirect(redirect);

				return null;
			}

			Long previousLayoutPlid = (Long)session.getAttribute(
				WebKeys.PREVIOUS_LAYOUT_PLID);

			if ((previousLayoutPlid == null) ||
				(layout.getPlid() != previousLayoutPlid.longValue())) {

				session.setAttribute(
					WebKeys.PREVIOUS_LAYOUT_PLID, layout.getPlid());

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
				httpServletRequest, "p_l_reset",
				PropsValues.LAYOUT_DEFAULT_P_L_RESET);

			String portletId = ParamUtil.getString(
				httpServletRequest, "p_p_id");

			if (resetLayout &&
				(Validator.isNull(portletId) ||
				 ((previousLayoutPlid != null) &&
				  (layout.getPlid() != previousLayoutPlid.longValue())))) {

				// Always clear render parameters on a layout url, but do not
				// clear on portlet urls invoked on the same layout

				RenderParametersPool.clear(httpServletRequest, plid);
			}

			Portlet portlet = null;

			if (Validator.isNotNull(portletId)) {
				portlet = PortletLocalServiceUtil.getPortletById(
					PortalUtil.getCompanyId(httpServletRequest), portletId);
			}

			if (portlet != null) {
				PortletContainerUtil.preparePortlet(
					httpServletRequest, portlet);

				if (themeDisplay.isLifecycleAction()) {
					PortletContainerUtil.processAction(
						httpServletRequest, httpServletResponse, portlet);

					if (httpServletResponse.isCommitted()) {
						return null;
					}

					String renderStateJSON = StringPool.BLANK;

					if (themeDisplay.isHubAction()) {
						renderStateJSON = RenderStateUtil.generateJSON(
							httpServletRequest, themeDisplay);
					}
					else if (themeDisplay.isHubPartialAction()) {
						LayoutTypePortlet layoutTypePortlet =
							themeDisplay.getLayoutTypePortlet();

						if (layoutTypePortlet != null) {
							renderStateJSON = getRenderStateJSON(
								httpServletRequest, httpServletResponse,
								themeDisplay, portlet.getPortletId(),
								layoutTypePortlet);
						}
					}

					if (themeDisplay.isHubAction() ||
						themeDisplay.isHubPartialAction()) {

						httpServletResponse.setContentLength(
							renderStateJSON.length());
						httpServletResponse.setContentType(
							ContentTypes.APPLICATION_JSON);

						PrintWriter printWriter =
							httpServletResponse.getWriter();

						printWriter.write(renderStateJSON);

						return null;
					}
				}
				else if (themeDisplay.isLifecycleResource()) {
					PortletContainerUtil.serveResource(
						httpServletRequest, httpServletResponse, portlet);

					return null;
				}
			}

			if (layout != null) {
				if (themeDisplay.isStateExclusive()) {
					PortletContainerUtil.renderHeaders(
						httpServletRequest, httpServletResponse, portlet);

					PortletContainerUtil.render(
						httpServletRequest, httpServletResponse, portlet);

					return null;
				}

				// Include layout content before the page loads because portlets
				// on the page can set the page title and page subtitle

				PortletContainerUtil.processPublicRenderParameters(
					httpServletRequest, layout, portlet);

				if (layout.includeLayoutContent(
						httpServletRequest, httpServletResponse)) {

					return null;
				}
			}

			return actionMapping.getActionForward("portal.layout");
		}
		catch (Exception e) {
			PortalUtil.sendError(e, httpServletRequest, httpServletResponse);

			return null;
		}
		finally {
			PortletRequest portletRequest =
				(PortletRequest)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			if (portletRequest != null) {
				LiferayPortletRequest liferayPortletRequest =
					LiferayPortletUtil.getLiferayPortletRequest(portletRequest);

				if (liferayPortletRequest instanceof ResourceRequest) {
					ResourceRequest resourceRequest =
						(ResourceRequest)liferayPortletRequest;

					if (!resourceRequest.isAsyncStarted()) {
						liferayPortletRequest.cleanUp();
					}
				}
				else {
					liferayPortletRequest.cleanUp();
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(LayoutAction.class);

}