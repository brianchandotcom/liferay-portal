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

package com.liferay.portal.liveusers;

import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.UserTracker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserTrackerLocalServiceUtil;
import com.liferay.portal.service.persistence.UserTrackerUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.util.WebKeys;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Charles May
 * @author Brian Wing Shun Chan
 */
public class LiveUsers {

	public static void deleteGroup(long companyId, long groupId) {
		_instance._deleteGroup(companyId, groupId);
	}

	public static Set<Long> getGroupUsers(long companyId, long groupId) {
		return _instance._getGroupUsers(
			_instance._getLiveUsers(companyId), groupId);
	}

	public static int getGroupUsersCount(long companyId, long groupId) {
		return getGroupUsers(companyId, groupId).size();
	}

	public static Map<String, UserTracker> getSessionUsers(long companyId) {
		return _instance._getSessionUsers(companyId);
	}

	public static int getSessionUsersCount(long companyId) {
		return getSessionUsers(companyId).size();
	}

	public static UserTracker getUserTracker(long companyId, String sessionId) {
		return _instance._getUserTracker(companyId, sessionId);
	}

	public static void joinGroup(long companyId, long groupId, long userId) {
		_instance._joinGroup(companyId, groupId, userId);
	}

	public static void joinGroup(long companyId, long groupId, long[] userIds) {
		_instance._joinGroup(companyId, groupId, userIds);
	}

	public static void leaveGroup(long companyId, long groupId, long userId) {
		_instance._leaveGroup(companyId, groupId, userId);
	}

	public static void leaveGroup(
		long companyId, long groupId, long[] userIds) {

		_instance._leaveGroup(companyId, groupId, userIds);
	}

	public static void signIn(
			long companyId, long userId, String sessionId, String remoteAddr,
			String remoteHost, String userAgent)
		throws SystemException {

		_instance._signIn(
			companyId, userId, sessionId, remoteAddr, remoteHost, userAgent);
	}

	public static void signOut(long companyId, long userId, String sessionId)
		throws SystemException {

		_instance._signOut(companyId, userId, sessionId);
	}

	private LiveUsers() {
	}

	private void _addUserTracker(
		long companyId, long userId, UserTracker userTracker) {

		List<UserTracker> userTrackers = _getUserTrackers(companyId, userId);

		if (userTrackers != null) {
			userTrackers.add(userTracker);
		}
		else {
			userTrackers = new ArrayList<UserTracker>();

			userTrackers.add(userTracker);

			Map<Long, List<UserTracker>> userTrackersMap =
				_getUserTrackersMap(companyId);

			userTrackersMap.put(userId, userTrackers);
		}
	}

	private void _deleteGroup(long companyId, long groupId) {
		Map<Long, Set<Long>> liveUsers = _getLiveUsers(companyId);

		liveUsers.remove(groupId);
	}

	private Set<Long> _getGroupUsers(
		Map<Long, Set<Long>> liveUsers, long groupId) {

		Set<Long> groupUsers = liveUsers.get(groupId);

		if (groupUsers == null) {
			groupUsers = new ConcurrentHashSet<Long>();

			liveUsers.put(groupId, groupUsers);
		}

		return groupUsers;
	}

	private Map<Long, Set<Long>> _getLiveUsers(long companyId) {
		Map<Long, Set<Long>> liveUsers = (Map<Long, Set<Long>>)WebAppPool.get(
			companyId, WebKeys.LIVE_USERS);

		if (liveUsers == null) {
			liveUsers = new ConcurrentHashMap<Long, Set<Long>>();

			WebAppPool.put(companyId, WebKeys.LIVE_USERS, liveUsers);
		}

		return liveUsers;
	}

	private Map<String, UserTracker> _getSessionUsers(long companyId) {
		Map<String, UserTracker> sessionUsers =
			(Map<String, UserTracker>)WebAppPool.get(
				companyId, WebKeys.LIVE_SESSION_USERS);

		if (sessionUsers == null) {
			sessionUsers = new ConcurrentHashMap<String, UserTracker>();

			WebAppPool.put(companyId, WebKeys.LIVE_SESSION_USERS, sessionUsers);
		}

		return sessionUsers;
	}

	private UserTracker _getUserTracker(long companyId, String sessionId) {
		Map<String, UserTracker> sessionUsers = _getSessionUsers(companyId);

		return sessionUsers.get(sessionId);
	}

	private List<UserTracker> _getUserTrackers(long companyId, long userId) {
		Map<Long, List<UserTracker>> userTrackersMap = _getUserTrackersMap(
			companyId);

		return userTrackersMap.get(userId);
	}

	private Map<Long, List<UserTracker>> _getUserTrackersMap(long companyId) {
		Map<Long, List<UserTracker>> userTrackersMap =
			(Map<Long, List<UserTracker>>)WebAppPool.get(
				companyId, WebKeys.LIVE_USER_TRACKERS);

		if (userTrackersMap == null) {
			userTrackersMap = new ConcurrentHashMap<Long, List<UserTracker>>();

			WebAppPool.put(
				companyId, WebKeys.LIVE_USER_TRACKERS, userTrackersMap);
		}

		return userTrackersMap;
	}

	private void _joinGroup(long companyId, long groupId, long userId) {
		Map<Long, Set<Long>> liveUsers = _getLiveUsers(companyId);

		Set<Long> groupUsers = _getGroupUsers(liveUsers, groupId);

		if (_getUserTrackers(companyId, userId) != null) {
			groupUsers.add(userId);
		}
	}

	private void _joinGroup(long companyId, long groupId, long[] userIds) {
		Map<Long, Set<Long>> liveUsers = _getLiveUsers(companyId);

		Set<Long> groupUsers = _getGroupUsers(liveUsers, groupId);

		for (long userId : userIds) {
			if (_getUserTrackers(companyId, userId) != null) {
				groupUsers.add(userId);
			}
		}
	}

	private void _leaveGroup(long companyId, long userId, long groupId) {
		Map<Long, Set<Long>> liveUsers = _getLiveUsers(companyId);

		Set<Long> groupUsers = _getGroupUsers(liveUsers, groupId);

		groupUsers.remove(userId);
	}

	private void _leaveGroup(long companyId, long groupId, long[] userIds) {
		Map<Long, Set<Long>> liveUsers = _getLiveUsers(companyId);

		Set<Long> groupUsers = _getGroupUsers(liveUsers, groupId);

		for (long userId : userIds) {
			groupUsers.remove(userId);
		}
	}

	private void _removeUserTracker(
		long companyId, long userId, UserTracker userTracker) {

		List<UserTracker> userTrackers = _getUserTrackers(companyId, userId);

		if (userTrackers != null) {
			String sessionId = userTracker.getSessionId();

			Iterator<UserTracker> itr = userTrackers.iterator();

			while (itr.hasNext()) {
				UserTracker curUserTracker = itr.next();

				if (sessionId.equals(curUserTracker.getSessionId())) {
					itr.remove();
				}
			}

			if (userTrackers.size() == 0) {
				Map<Long, List<UserTracker>> userTrackersMap =
					_getUserTrackersMap(companyId);

				userTrackersMap.remove(userId);
			}
		}
	}

	private void _signIn(
			long companyId, long userId, String sessionId, String remoteAddr,
			String remoteHost, String userAgent)
		throws SystemException {

		_updateGroupStatus(companyId, userId, true);

		Map<String, UserTracker> sessionUsers = _getSessionUsers(companyId);

		UserTracker userTracker = sessionUsers.get(sessionId);

		if ((userTracker == null) &&
			(PropsValues.SESSION_TRACKER_MEMORY_ENABLED)) {

			userTracker = UserTrackerUtil.create(0);

			userTracker.setCompanyId(companyId);
			userTracker.setUserId(userId);
			userTracker.setModifiedDate(new Date());
			userTracker.setSessionId(sessionId);
			userTracker.setRemoteAddr(remoteAddr);
			userTracker.setRemoteHost(remoteHost);
			userTracker.setUserAgent(userAgent);

			sessionUsers.put(sessionId, userTracker);

			_addUserTracker(companyId, userId, userTracker);
		}
	}

	private void _signOut(long companyId, long userId, String sessionId)
		throws SystemException {

		List<UserTracker> userTrackers = _getUserTrackers(companyId, userId);

		if ((userTrackers == null) || (userTrackers.size() <= 1)) {
			_updateGroupStatus(companyId, userId, false);
		}

		Map<String, UserTracker> sessionUsers = _getSessionUsers(companyId);

		UserTracker userTracker = sessionUsers.remove(sessionId);

		if (userTracker != null) {
			try {
				UserTrackerLocalServiceUtil.addUserTracker(
					userTracker.getCompanyId(), userTracker.getUserId(),
					userTracker.getModifiedDate(), sessionId,
					userTracker.getRemoteAddr(), userTracker.getRemoteHost(),
					userTracker.getUserAgent(), userTracker.getPaths());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e.getMessage());
				}
			}

			_removeUserTracker(companyId, userId, userTracker);
		}
	}

	private Map<Long, Set<Long>> _updateGroupStatus(
			long companyId, long userId, boolean signedIn)
		throws SystemException {

		Map<Long, Set<Long>> liveUsers = _getLiveUsers(companyId);

		LinkedHashMap<String, Object> groupParams =
			new LinkedHashMap<String, Object>();

		groupParams.put("usersGroups", userId);

		List<Group> groups = GroupLocalServiceUtil.search(
			companyId, null, null, groupParams, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		for (Group group : groups) {
			Set<Long> groupUsers = _getGroupUsers(
				liveUsers, group.getGroupId());

			if (signedIn) {
				groupUsers.add(userId);
			}
			else {
				groupUsers.remove(userId);
			}
		}

		return liveUsers;
	}

	private static Log _log = LogFactoryUtil.getLog(LiveUsers.class);

	private static LiveUsers _instance = new LiveUsers();

}