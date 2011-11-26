/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.usersession;

import com.liferay.portal.kernel.cluster.ClusterLinkUtil;
import com.liferay.portal.kernel.cluster.Priority;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.util.PropsValues;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

/**
 * @author Edward Han
 */
public class UserSessionManager {

	public static int getPortalInstanceUserCount(long companyId)
		throws PortalException {

		return _userSessionManager._getPortalInstanceUserCount(companyId);
	}

	public static int getUserCount() {
		return _userSessionManager._getUserCount();
	}

	public static void startPortalInstance(long companyId) {
		_userSessionManager._startPortalInstance(companyId);
	}

	public static void startUserSession(
			long companyId, long userId, HttpSession session)
		throws PortalException {

		_userSessionManager._startUserSession(companyId, userId, session);
	}

	public static void stopPortalInstance(long companyId) {
		_userSessionManager._stopPortalInstance(companyId);
	}

	public static void stopUserSession(long companyId, long userId)
		throws PortalException {

		_userSessionManager._stopUserSession(companyId, userId);
	}

	private ConcurrentHashMap<Long, HttpSession> _getPortalInstance(
			long companyId)
		throws PortalException {

		ConcurrentHashMap<Long, HttpSession> portalInstance =
			_portalInstances.get(companyId);

		if (portalInstance == null) {
			throw new PortalException(
				"UserSessionManager for company not initialized: " + companyId);
		}

		return portalInstance;
	}

	private int _getPortalInstanceUserCount(long companyId)
		throws PortalException {

		ConcurrentHashMap<Long, HttpSession> portalInstance =
			_getPortalInstance(companyId);

		return portalInstance.size();
	}

	private int _getUserCount() {
		int userCount = 0;

		for (ConcurrentHashMap<Long, HttpSession> portalInstance :
				_portalInstances.values()) {

			userCount += portalInstance.size();
		}

		return userCount;
	}

	private void _notifyCluster(long companyId, long userId) {
		Message message = new Message();

		message.setDestinationName(DestinationNames.USER_SESSION_MANAGER);

		message.put(Constants.CMD, Constants.ADD);
		message.put("companyId", companyId);
		message.put("userId", userId);

		ClusterLinkUtil.sendMulticastMessage(message, Priority.LEVEL5);
	}

	private void _startPortalInstance(long companyId) {
		ConcurrentHashMap<Long, HttpSession> portalInstance =
			new ConcurrentHashMap<Long, HttpSession>();

		_portalInstances.putIfAbsent(companyId, portalInstance);
	}

	private void _startUserSession(
			long companyId, long userId, HttpSession session)
		throws PortalException {

		if (!PropsValues.USERS_ENFORCE_SINGLE_SESSION) {
			return;
		}

		ConcurrentHashMap<Long, HttpSession> portalInstance =
			_getPortalInstance(companyId);

		_notifyCluster(companyId, userId);

		HttpSession oldSession = portalInstance.put(userId, session);

		if (oldSession != null) {
			if (_log.isInfoEnabled()) {
				_log.info("User already has a session: " + userId);
			}

			try {
				oldSession.invalidate();
			}
			catch (IllegalStateException e) {
			}
		}
	}

	private void _stopPortalInstance(long companyId) {
		_portalInstances.remove(companyId);
	}

	private void _stopUserSession(long companyId, long userId)
		throws PortalException {

		if (!PropsValues.USERS_ENFORCE_SINGLE_SESSION) {
			return;
		}

		ConcurrentHashMap<Long, HttpSession> portalInstance =
			_getPortalInstance(companyId);

		HttpSession session = portalInstance.remove(userId);

		if (session != null) {
			try {
				session.invalidate();
			}
			catch (IllegalStateException e) {
				//	Session is already invalid, ignore
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Session invalidated: " + session.getId());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserSessionManager.class);

	private static UserSessionManager _userSessionManager =
		new UserSessionManager();

	private ConcurrentHashMap<Long, ConcurrentHashMap<Long, HttpSession>>
		_portalInstances =
		new ConcurrentHashMap<Long, ConcurrentHashMap<Long, HttpSession>>();
}