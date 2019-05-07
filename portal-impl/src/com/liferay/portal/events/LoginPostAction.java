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

package com.liferay.portal.events;

import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class LoginPostAction extends Action {

	@Override
	public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Running " + httpServletRequest.getRemoteUser());
			}

			HttpSession session = httpServletRequest.getSession();

			long companyId = PortalUtil.getCompanyId(httpServletRequest);
			long userId = 0;

			// Language

			session.removeAttribute(WebKeys.LOCALE);

			// Live users

			if (PropsValues.LIVE_USERS_ENABLED) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				ClusterNode clusterNode =
					ClusterExecutorUtil.getLocalClusterNode();

				if (clusterNode != null) {
					jsonObject.put(
						"clusterNodeId", clusterNode.getClusterNodeId());
				}

				jsonObject.put(
					"command", "signIn"
				).put(
					"companyId", companyId
				).put(
					"remoteAddr", httpServletRequest.getRemoteAddr()
				).put(
					"remoteHost", httpServletRequest.getRemoteHost()
				).put(
					"sessionId", session.getId()
				);

				String userAgent = httpServletRequest.getHeader(
					HttpHeaders.USER_AGENT);

				jsonObject.put("userAgent", userAgent);

				userId = PortalUtil.getUserId(httpServletRequest);

				jsonObject.put("userId", userId);

				MessageBusUtil.sendMessage(
					DestinationNames.LIVE_USERS, jsonObject.toString());
			}

			if (PrefsPropsUtil.getBoolean(
					companyId, PropsKeys.ADMIN_SYNC_DEFAULT_ASSOCIATIONS)) {

				if (userId == 0) {
					userId = PortalUtil.getUserId(httpServletRequest);
				}

				UserLocalServiceUtil.addDefaultGroups(userId);
				UserLocalServiceUtil.addDefaultRoles(userId);
				UserLocalServiceUtil.addDefaultUserGroups(userId);

				Indexer userIndexer = IndexerRegistryUtil.getIndexer(
					User.class.getName());

				userIndexer.reindex(User.class.getName(), userId);
			}

			User user = PortalUtil.getUser(httpServletRequest);

			PasswordPolicy passwordPolicy = user.getPasswordPolicy();

			if ((passwordPolicy != null) && passwordPolicy.isExpireable() &&
				(passwordPolicy.getWarningTime() > 0)) {

				_setPasswordExpirationMessage(
					httpServletRequest, passwordPolicy, user);
			}
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	private void _setPasswordExpirationMessage(
			HttpServletRequest httpServletRequest,
			PasswordPolicy passwordPolicy, User user)
		throws PortalException {

		Date now = new Date();

		if (user.getPasswordModifiedDate() == null) {
			user.setPasswordModifiedDate(now);

			UserLocalServiceUtil.updateUser(user);
		}

		Date passwordModifiedDate = user.getPasswordModifiedDate();

		long passwordExpirationTime =
			(passwordPolicy.getMaxAge() * 1000) +
				passwordModifiedDate.getTime();

		long startWarningTime =
			passwordExpirationTime - (passwordPolicy.getWarningTime() * 1000);

		if (now.getTime() > startWarningTime) {
			int passwordExpiresInXDays =
				(int)((passwordExpirationTime - now.getTime()) / Time.DAY);

			if (passwordExpiresInXDays >= 0) {
				SessionMessages.add(
					httpServletRequest, "passwordExpiresInXDays",
					passwordExpiresInXDays);
			}
			else {
				int remainingGraceLogins =
					passwordPolicy.getGraceLimit() - user.getGraceLoginCount();

				SessionMessages.add(
					httpServletRequest, "remainingGraceLogins",
					remainingGraceLogins);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LoginPostAction.class);

}