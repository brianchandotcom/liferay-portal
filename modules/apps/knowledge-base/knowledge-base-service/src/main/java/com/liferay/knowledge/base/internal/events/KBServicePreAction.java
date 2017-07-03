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

package com.liferay.knowledge.base.internal.events;

import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.AuthTokenUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Peter Shin
 */
@Component(
	property = {"key=servlet.service.events.pre"},
	service = LifecycleAction.class
)
public class KBServicePreAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) {
		try {
			doRun(request, response);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void doRun(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!_PORTLET_ADD_DEFAULT_RESOURCE_CHECK_ENABLED) {
			return;
		}

		if (!themeDisplay.isLifecycleRender()) {
			return;
		}

		String portletId = ParamUtil.getString(request, "p_p_id");

		if (Validator.isNull(portletId)) {
			return;
		}

		if (!portletId.equals(
				KBPortletKeys.KNOWLEDGE_BASE_ARTICLE_DEFAULT_INSTANCE)) {

			return;
		}

		String request_p_p_auth = ParamUtil.getString(request, "p_p_auth");

		if (Validator.isNull(request_p_p_auth)) {
			return;
		}

		String actual_p_p_auth = AuthTokenUtil.getToken(
			request, themeDisplay.getPlid(), portletId);

		if (request_p_p_auth.equals(actual_p_p_auth)) {
			return;
		}

		// A guest user that signs in will cause the original portlet
		// authentication token to become stale. See SessionAuthToken.

		String redirect = _portal.escapeRedirect(themeDisplay.getURLCurrent());

		if (Validator.isNull(redirect)) {
			return;
		}

		redirect = _http.setParameter(redirect, "p_p_auth", actual_p_p_auth);

		response.sendRedirect(redirect);
	}

	private static final boolean _PORTLET_ADD_DEFAULT_RESOURCE_CHECK_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(
				PropsKeys.PORTLET_ADD_DEFAULT_RESOURCE_CHECK_ENABLED));

	private static final Log _log = LogFactoryUtil.getLog(
		KBServicePreAction.class);

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

}