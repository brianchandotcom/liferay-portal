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

package com.liferay.portal.kernel.portlet.bridges.mvc;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @see    com.liferay.portal.struts.PortletAction
 */
public abstract class BaseMVCActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		try {
			doProcessAction(actionRequest, actionResponse);

			return SessionErrors.isEmpty(actionRequest);
		}
		catch (PortletException pe) {
			throw pe;
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	protected void addDeleteSuccessData(
		PortletRequest portletRequest, Object data) {

		SessionMessages.add(
			portletRequest,
			PortalUtil.getPortletId(portletRequest) +
				SessionMessages.KEY_SUFFIX_DELETE_SUCCESS_DATA,
			data);

		hideDefaultSuccessMessage(portletRequest);
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

	protected abstract void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception;

	protected PortletConfig getPortletConfig(PortletRequest portletRequest) {
		String portletId = PortalUtil.getPortletId(portletRequest);

		return PortletConfigFactoryUtil.get(
			PortletIdCodec.decodePortletName(portletId));
	}

	protected void hideDefaultErrorMessage(PortletRequest portletRequest) {
		SessionMessages.add(
			portletRequest,
			PortalUtil.getPortletId(portletRequest) +
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
	}

	protected void hideDefaultSuccessMessage(PortletRequest portletRequest) {
		SessionMessages.add(
			portletRequest,
			PortalUtil.getPortletId(portletRequest) +
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
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

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		if (layoutTypePortlet.hasPortletId(portletId)) {
			return true;
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

			sendRedirect(
				actionRequest, actionResponse, themeDisplay.getURLSignIn());

			return true;
		}

		return false;
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
				redirect, CharPool.POUND, redirectToken);
		}

		redirect = PortalUtil.escapeRedirect(redirect);

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

}