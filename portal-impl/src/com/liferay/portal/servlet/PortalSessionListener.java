/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.servlet;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.dependency.manager.DependencyManagerSyncUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.servlet.PortalSessionContext;
import com.liferay.portal.kernel.servlet.filters.compoundsessionid.CompoundSessionIdHttpSession;
import com.liferay.portal.kernel.servlet.filters.compoundsessionid.CompoundSessionIdSplitterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalSessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		HttpSession httpSession = httpSessionEvent.getSession();

		if (_log.isDebugEnabled()) {
			_log.debug("Session " + httpSession.getId() + " was created");
		}

		if (CompoundSessionIdSplitterUtil.hasSessionDelimiter()) {
			httpSession = new CompoundSessionIdHttpSession(
				httpSessionEvent.getSession());
		}

		HttpSession finalHttpSession = httpSession;

		DependencyManagerSyncUtil.registerSyncCallable(
			() -> {
				try {
					PortalSessionContext.put(
						finalHttpSession.getId(), finalHttpSession);
				}
				catch (IllegalStateException illegalStateException) {
					if (_log.isWarnEnabled()) {
						_log.warn(illegalStateException);
					}
				}

				// Process session created events

				try {
					EventsProcessorUtil.process(
						PropsKeys.SERVLET_SESSION_CREATE_EVENTS,
						PropsValues.SERVLET_SESSION_CREATE_EVENTS,
						finalHttpSession);
				}
				catch (ActionException actionException) {
					_log.error(actionException);
				}

				return null;
			});

		if ((PropsValues.SESSION_MAX_ALLOWED > 0) &&
			(_counter.incrementAndGet() > PropsValues.SESSION_MAX_ALLOWED)) {

			httpSession.setAttribute(WebKeys.SESSION_MAX_ALLOWED, Boolean.TRUE);

			_log.error(
				StringBundler.concat(
					"Exceeded maximum number of ",
					PropsValues.SESSION_MAX_ALLOWED,
					" sessions allowed. You may be experiencing a DoS ",
					"attack."));
		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession httpSession = httpSessionEvent.getSession();

		if (_log.isDebugEnabled()) {
			_log.debug("Session " + httpSession.getId() + " was destroyed");
		}

		if (CompoundSessionIdSplitterUtil.hasSessionDelimiter()) {
			httpSession = new CompoundSessionIdHttpSession(
				httpSessionEvent.getSession());
		}

		HttpSession finalHttpSession = httpSession;

		DependencyManagerSyncUtil.registerSyncCallable(
			() -> {
				PortalSessionContext.remove(finalHttpSession.getId());

				try {
					Long userIdObj = (Long)finalHttpSession.getAttribute(
						WebKeys.USER_ID);

					if (userIdObj == null) {
						if (_log.isWarnEnabled()) {
							_log.warn("User id is not in the session");
						}

						return null;
					}

					// Live users

					if (PropsValues.LIVE_USERS_ENABLED) {
						JSONObject jsonObject =
							JSONFactoryUtil.createJSONObject();

						ClusterNode clusterNode =
							ClusterExecutorUtil.getLocalClusterNode();

						if (clusterNode != null) {
							jsonObject.put(
								"clusterNodeId",
								clusterNode.getClusterNodeId());
						}

						jsonObject.put("command", "signOut");

						long userId = userIdObj.longValue();

						long companyId =
							CompanyLocalServiceUtil.getCompanyIdByUserId(
								userId);

						jsonObject.put(
							"companyId", companyId
						).put(
							"sessionId", finalHttpSession.getId()
						).put(
							"userId", userId
						);

						MessageBusUtil.sendMessage(
							DestinationNames.LIVE_USERS, jsonObject.toString());
					}
				}
				catch (Exception exception) {
					_log.error(exception);
				}

				// Process session destroyed events

				try {
					EventsProcessorUtil.process(
						PropsKeys.SERVLET_SESSION_DESTROY_EVENTS,
						PropsValues.SERVLET_SESSION_DESTROY_EVENTS,
						finalHttpSession);
				}
				catch (ActionException actionException) {
					_log.error(actionException);
				}

				return null;
			});

		if (PropsValues.SESSION_MAX_ALLOWED > 0) {
			_counter.decrementAndGet();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalSessionListener.class);

	private final AtomicInteger _counter = new AtomicInteger();

}