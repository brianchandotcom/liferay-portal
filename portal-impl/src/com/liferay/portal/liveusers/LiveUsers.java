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

package com.liferay.portal.liveusers;

import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.concurrent.ConcurrentHashSet;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.PortalSessionContext;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.UserTracker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserTrackerLocalServiceUtil;
import com.liferay.portal.service.persistence.UserTrackerUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

/**
 * @author Charles May
 * @author Brian Wing Shun Chan
 */
public class LiveUsers {

	public static void addClusterNode(
		String clusterNodeId, Map<Long, Map<Long, Set<String>>> clusterUsers) {

		_instance._addClusterNode(clusterNodeId, clusterUsers);
	}

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

	public static Map<Long, Map<Long, Set<String>>> getLocalClusterUsers() {
		return _instance._getLocalClusterUsers();
	}

	public static Map<String, UserTracker> getSessionUsers(long companyId) {
		return _instance._getSessionUsers(companyId);
	}

	public static int getSessionUsersCount(long companyId) {
		return getSessionUsers(companyId).size();
	}

	// PLACEHOLDER 01
	// PLACEHOLDER 02
	// PLACEHOLDER 03

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

	public static void removeClusterNode(String clusterNodeId) {
		_instance._removeClusterNode(clusterNodeId);
	}

	public static void signIn(
		String clusterNodeId, long companyId, long userId, String sessionId,
		String remoteAddr, String remoteHost, String userAgent) {

		_instance._signIn(
			clusterNodeId, companyId, userId, sessionId, remoteAddr, remoteHost,
			userAgent);
	}

	public static void signOut(
		String clusterNodeId, long companyId, long userId, String sessionId) {

		_instance._signOut(clusterNodeId, companyId, userId, sessionId);
	}

	private LiveUsers() {
	}

	private void _addClusterNode(
		String clusterNodeId,
		Map<Long, Map<Long, Set<String>>> clusterUsers) {

		if (Validator.isNull(clusterNodeId)) {
			return;
		}

		for (Map.Entry<Long, Map<Long, Set<String>>> companyUsers :
				clusterUsers.entrySet()) {

			long companyId = companyUsers.getKey();
			Map<Long, Set<String>> userSessionsMap = companyUsers.getValue();

			for (Map.Entry<Long, Set<String>> userSessions :
					userSessionsMap.entrySet()) {

				long userId = userSessions.getKey();

				for (String sessionId : userSessions.getValue()) {
					_signIn(
						clusterNodeId, companyId, userId, sessionId, null, null,
						null);
				}
			}
		}
	}

	private void _addClusterUser(
		String clusterNodeId, long companyId, long userId, String sessionId) {

		if (Validator.isNull(clusterNodeId)) {
			return;
		}

		Map<Long, Map<Long, Set<String>>> clusterUsers = _clusterUsers.get(
			clusterNodeId);

		if (clusterUsers == null) {
			clusterUsers =
				new ConcurrentHashMap<Long, Map<Long, Set<String>>>();

			_clusterUsers.put(clusterNodeId, clusterUsers);
		}

		Map<Long, Set<String>> companyUsers = clusterUsers.get(companyId);

		if (companyUsers == null) {
			companyUsers = new ConcurrentHashMap<Long, Set<String>>();

			clusterUsers.put(companyId, companyUsers);
		}

		Set<String> userSessions = companyUsers.get(userId);

		if (userSessions == null) {
			userSessions = new ConcurrentHashSet<String>();

			companyUsers.put(userId, userSessions);
		}

		userSessions.add(sessionId);
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

			Map<Long, List<UserTracker>> userTrackersMap = _getUserTrackersMap(
				companyId);

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
		Map<Long, Set<Long>> liveUsers = _liveUsers.get(companyId);

		if (liveUsers == null) {
			liveUsers = new ConcurrentHashMap<Long, Set<Long>>();

			_liveUsers.put(companyId, liveUsers);
		}

		return liveUsers;
	}

	private Map<Long, Map<Long, Set<String>>> _getLocalClusterUsers() {
		ClusterNode clusterNode = ClusterExecutorUtil.getLocalClusterNode();

		if (clusterNode == null) {
			return null;
		}

		return _clusterUsers.get(clusterNode.getClusterNodeId());
	}

	private Map<String, UserTracker> _getSessionUsers(long companyId) {
		Map<String, UserTracker> sessionUsers = _sessionUsers.get(companyId);

		if (sessionUsers == null) {
			sessionUsers = new ConcurrentHashMap<String, UserTracker>();

			_sessionUsers.put(companyId, sessionUsers);
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
		Map<Long, List<UserTracker>> userTrackersMap = _userTrackers.get(
			companyId);

		if (userTrackersMap == null) {
			userTrackersMap = new ConcurrentHashMap<Long, List<UserTracker>>();

			_userTrackers.put(companyId, userTrackersMap);
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

	private void _removeClusterNode(String clusterNodeId) {
		if (Validator.isNull(clusterNodeId)) {
			return;
		}

		Map<Long, Map<Long, Set<String>>> clusterUsers = _clusterUsers.remove(
			clusterNodeId);

		if (clusterUsers == null) {
			return;
		}

		for (Map.Entry<Long, Map<Long, Set<String>>> companyUsers :
				clusterUsers.entrySet()) {

			long companyId = companyUsers.getKey();
			Map<Long, Set<String>> userSessionsMap = companyUsers.getValue();

			for (Map.Entry<Long, Set<String>> userSessions :
					userSessionsMap.entrySet()) {

				long userId = userSessions.getKey();

				for (String sessionId : userSessions.getValue()) {
					_signOut(clusterNodeId, companyId, userId, sessionId);
				}
			}
		}
	}

	private void _removeClusterUser(
		String clusterNodeId, long companyId, long userId, String sessionId) {

		if (Validator.isNull(clusterNodeId)) {
			return;
		}

		Map<Long, Map<Long, Set<String>>> clusterUsers = _clusterUsers.get(
			clusterNodeId);

		if (clusterUsers == null) {
			return;
		}

		Map<Long, Set<String>> companyUsers = clusterUsers.get(companyId);

		if (companyUsers == null) {
			return;
		}

		Set<String> userSessions = companyUsers.get(userId);

		if (userSessions == null) {
			return;
		}

		userSessions.remove(sessionId);
	}

	private void _removeUserTracker(
		long companyId, long userId, UserTracker userTracker) {

		List<UserTracker> userTrackers = _getUserTrackers(companyId, userId);

		if (userTrackers == null) {
			return;
		}

		String sessionId = userTracker.getSessionId();

		Iterator<UserTracker> itr = userTrackers.iterator();

		while (itr.hasNext()) {
			UserTracker curUserTracker = itr.next();

			if (sessionId.equals(curUserTracker.getSessionId())) {
				itr.remove();
			}
		}

		if (userTrackers.isEmpty()) {
			Map<Long, List<UserTracker>> userTrackersMap = _getUserTrackersMap(
				companyId);

			userTrackersMap.remove(userId);
		}
	}

	private void _signIn(
		String clusterNodeId, long companyId, long userId, String sessionId,
		String remoteAddr, String remoteHost, String userAgent) {

		_addClusterUser(clusterNodeId, companyId, userId, sessionId);

		_updateGroupStatus(companyId, userId, true);

		Map<String, UserTracker> sessionUsers = _getSessionUsers(companyId);

		UserTracker userTracker = sessionUsers.get(sessionId);

		// PLACEHOLDER 04
		// PLACEHOLDER 05
		// PLACEHOLDER 06
		// PLACEHOLDER 07
		// PLACEHOLDER 08
		// PLACEHOLDER 09
		// PLACEHOLDER 10
		// PLACEHOLDER 11
		// PLACEHOLDER 12

		if ((userTracker == null) &&
			PropsValues.SESSION_TRACKER_MEMORY_ENABLED) {

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

	private void _signOut(
		String clusterNodeId, long companyId, long userId, String sessionId) {

		_removeClusterUser(clusterNodeId, companyId, userId, sessionId);

		List<UserTracker> userTrackers = _getUserTrackers(companyId, userId);

		if ((userTrackers == null) || (userTrackers.size() <= 1)) {
			_updateGroupStatus(companyId, userId, false);
		}

		Map<String, UserTracker> sessionUsers = _getSessionUsers(companyId);

		UserTracker userTracker = sessionUsers.remove(sessionId);

		// PLACEHOLDER 13
		// PLACEHOLDER 14
		// PLACEHOLDER 15
		// PLACEHOLDER 16
		// PLACEHOLDER 17
		// PLACEHOLDER 18
		// PLACEHOLDER 19
		// PLACEHOLDER 20
		// PLACEHOLDER 21
		// PLACEHOLDER 22
		// PLACEHOLDER 23
		// PLACEHOLDER 24
		// PLACEHOLDER 25

		if (userTracker == null) {
			return;
		}

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

		try {
			HttpSession session = PortalSessionContext.get(sessionId);

			if (session != null) {
				session.invalidate();
			}
		}
		catch (Exception e) {
		}

		_removeUserTracker(companyId, userId, userTracker);
	}

	private Map<Long, Set<Long>> _updateGroupStatus(
		long companyId, long userId, boolean signedIn) {

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

	// PLACEHOLDER 26

	private Map<String, Map<Long, Map<Long, Set<String>>>> _clusterUsers =
		new ConcurrentHashMap<String, Map<Long, Map<Long, Set<String>>>>();
	private Map<Long, Map<Long, Set<Long>>> _liveUsers =
		new ConcurrentHashMap<Long, Map<Long, Set<Long>>>();
	private Map<Long, Map<String, UserTracker>> _sessionUsers =
		new ConcurrentHashMap<Long, Map<String, UserTracker>>();
	private Map<Long, Map<Long, List<UserTracker>>> _userTrackers =
		new ConcurrentHashMap<Long, Map<Long, List<UserTracker>>>();

}