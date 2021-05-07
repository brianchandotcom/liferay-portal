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

import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.security.auth.AuthenticatedUserUUIDStoreUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.servlet.PortalSessionContext;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import javax.servlet.http.HttpSession;

/**
 * @author Michael Young
 */
public class PortalSessionDestroyer extends BasePortalLifecycle {

	public PortalSessionDestroyer(HttpSession httpSession) {
		_httpSession = httpSession;

		registerPortalLifecycle(METHOD_INIT);
	}

	@Override
	protected void doPortalDestroy() {
	}

	@Override
	protected void doPortalInit() {
		PortalSessionContext.remove(_httpSession.getId());

		try {
			Long userIdObj = (Long)_httpSession.getAttribute(WebKeys.USER_ID);

			if (userIdObj == null) {
				if (_log.isWarnEnabled()) {
					_log.warn("User id is not in the session");
				}
			}

			if (userIdObj == null) {
				return;
			}

			// Live users

			if (PropsValues.LIVE_USERS_ENABLED) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				ClusterNode clusterNode =
					ClusterExecutorUtil.getLocalClusterNode();

				if (clusterNode != null) {
					jsonObject.put(
						"clusterNodeId", clusterNode.getClusterNodeId());
				}

				jsonObject.put("command", "signOut");

				long userId = userIdObj.longValue();

				long companyId = CompanyLocalServiceUtil.getCompanyIdByUserId(
					userId);

				jsonObject.put(
					"companyId", companyId
				).put(
					"sessionId", _httpSession.getId()
				).put(
					"userId", userId
				);

				MessageBusUtil.sendMessage(
					DestinationNames.LIVE_USERS, jsonObject.toString());
			}

			if (PropsValues.AUTH_USER_UUID_STORE_ENABLED) {
				String userUUID = (String)_httpSession.getAttribute(
					CookieKeys.USER_UUID);

				if (Validator.isNotNull(userUUID)) {
					AuthenticatedUserUUIDStoreUtil.unregister(userUUID);
				}
			}
		}
		catch (IllegalStateException illegalStateException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Please upgrade to a Servlet 2.4 compliant container",
					illegalStateException);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		// Process session destroyed events

		try {
			EventsProcessorUtil.process(
				PropsKeys.SERVLET_SESSION_DESTROY_EVENTS,
				PropsValues.SERVLET_SESSION_DESTROY_EVENTS, _httpSession);
		}
		catch (ActionException actionException) {
			_log.error(actionException, actionException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalSessionDestroyer.class);

	private final HttpSession _httpSession;

}