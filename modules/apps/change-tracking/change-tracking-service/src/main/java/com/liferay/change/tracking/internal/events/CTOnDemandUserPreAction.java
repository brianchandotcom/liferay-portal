/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.events;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.session.AuthenticatedSessionManager;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.audit.event.generators.constants.EventTypes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Objects;

/**
 * @author Pei-Jung Lan
 */
@Component(
		property = "key=servlet.service.events.pre", service = LifecycleAction.class
)
public class CTOnDemandUserPreAction extends Action {

	@Override
	public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		try {
			_run(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}
	}

	private void _run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
			throws Exception {

		User user = _portal.getUser(httpServletRequest);

		if ((user == null) || !user.isOnDemandUser()) {
			return;
		}

		String portletId = ParamUtil.getString(
				httpServletRequest, "p_p_id");

		if (!Objects.equals(portletId, CTPortletKeys.PUBLICATIONS)) {
			_authenticatedSessionManager.logout(
					httpServletRequest, httpServletResponse);

			httpServletRequest.setAttribute(WebKeys.LOGOUT, Boolean.TRUE);

			httpServletResponse.sendRedirect(_portal.getCurrentURL(httpServletRequest));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTOnDemandUserPreAction.class);

	@Reference
	private AuthenticatedSessionManager _authenticatedSessionManager;

	@Reference
	private Portal _portal;

}