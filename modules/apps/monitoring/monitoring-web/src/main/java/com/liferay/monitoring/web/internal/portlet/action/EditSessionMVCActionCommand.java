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

package com.liferay.monitoring.web.internal.portlet.action;

import com.liferay.monitoring.web.internal.constants.MonitoringPortletKeys;
import com.liferay.portal.kernel.cluster.ClusterExecutor;
import com.liferay.portal.kernel.cluster.ClusterInvokeThreadLocal;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.PortalSessionContext;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;

import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Philip Jones
 */
@Component(
	property = {
		"javax.portlet.name=" + MonitoringPortletKeys.MONITORING,
		"mvc.command.name=/monitoring/edit_session"
	},
	service = MVCActionCommand.class
)
public class EditSessionMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin()) {
			SessionErrors.add(
				actionRequest,
				PrincipalException.MustBeCompanyAdmin.class.getName());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

			return;
		}

		invalidateSession(actionRequest);

		sendRedirect(actionRequest, actionResponse);
	}

	protected void invalidateSession(ActionRequest actionRequest)
		throws Exception {

		String sessionId = ParamUtil.getString(actionRequest, "sessionId");

		try {
			PortletSession portletSession = actionRequest.getPortletSession();

			String portletSessionId = portletSession.getId();

			if (!portletSessionId.equals(sessionId)) {
				HttpSession userSession = PortalSessionContext.get(sessionId);

				if (userSession != null) {
					userSession.invalidate();

					return;
				}

				if (!_clusterExecutor.isEnabled()) {
					return;
				}

				try {
					MethodHandler methodHandler = new MethodHandler(
						_invalidateSessionMethodKey, sessionId);

					ClusterRequest clusterRequest =
						ClusterRequest.createMulticastRequest(
							methodHandler, true);

					clusterRequest.setFireAndForget(true);

					_clusterExecutor.execute(clusterRequest);
				}
				catch (Throwable t) {
					_log.error("Unable to notify cluster ", t);
				}
			}
		}
		catch (Exception e) {
			_log.error("Unable to invalidate session", e);
		}
	}

	private static void _invalidateSession(String sessionId) {
		HttpSession userSession = PortalSessionContext.get(sessionId);

		if (userSession != null) {
			boolean eanbled = ClusterInvokeThreadLocal.isEnabled();

			ClusterInvokeThreadLocal.setEnabled(true);

			try {
				userSession.invalidate();
			}
			finally {
				ClusterInvokeThreadLocal.setEnabled(eanbled);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditSessionMVCActionCommand.class);

	private static final MethodKey _invalidateSessionMethodKey = new MethodKey(
		EditSessionMVCActionCommand.class, "_invalidateSession", String.class);

	@Reference
	private ClusterExecutor _clusterExecutor;

}