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

package com.liferay.social.privatemessaging.web.internal.util;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.util.comparator.MessageCreateDateComparator;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchRoleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.comparator.UserFirstNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.sites.kernel.util.SitesUtil;
import com.liferay.social.kernel.model.SocialRelationConstants;
import com.liferay.social.privatemessaging.configuration.PrivateMessagingConfiguration;
import com.liferay.social.privatemessaging.exception.NoSuchUserThreadException;
import com.liferay.social.privatemessaging.model.RoleConstants;
import com.liferay.social.privatemessaging.model.UserThread;
import com.liferay.social.privatemessaging.service.UserThreadLocalServiceUtil;
import com.liferay.social.privatemessaging.service.UserThreadServiceUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Scott Lee
 * @author Eudaldo Alonso
 */
public class PrivateMessagingUtil {

	public static JSONObject getJSONRecipients(
			long userId, String type, String keywords, int start, int end)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		User user = UserLocalServiceUtil.getUser(userId);

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		if (type.equals("site")) {
			params.put("inherit", Boolean.TRUE);

			LinkedHashMap<String, Object> groupParams = new LinkedHashMap<>();

			groupParams.put("inherit", Boolean.FALSE);
			groupParams.put("site", Boolean.TRUE);
			groupParams.put("usersGroups", userId);

			List<Group> groups = GroupLocalServiceUtil.search(
				user.getCompanyId(), groupParams, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			params.put(
				"usersGroups",
				SitesUtil.filterGroups(
					groups,
					getAutocompleteRecipientSiteExcludes(user.getCompanyId())));
		}
		else if (!type.equals("all")) {
			params.put(
				"socialRelationType",
				new Long[] {
					userId,
					Long.valueOf(SocialRelationConstants.TYPE_BI_CONNECTION)
				});
		}

		try {
			Role role = RoleLocalServiceUtil.getRole(
				user.getCompanyId(), RoleConstants.SOCIAL_OFFICE_USER);

			if (role != null) {
				params.put("inherit", Boolean.TRUE);
				params.put("usersRoles", Long.valueOf(role.getRoleId()));
			}
		}
		catch (NoSuchRoleException nsre) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(nsre, nsre);
			}
		}

		List<User> users = new ArrayList<>();

		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(User.class);

		if (indexer.isIndexerEnabled() && _USERS_SEARCH_WITH_INDEX &&
			type.equals("all")) {

			Sort sort = SortFactoryUtil.getSort(User.class, "firstName", "asc");

			BaseModelSearchResult<User> baseModelSearchResult =
				UserLocalServiceUtil.searchUsers(
					user.getCompanyId(), keywords, keywords, keywords, keywords,
					keywords, WorkflowConstants.STATUS_APPROVED, params, false,
					start, end, sort);

			jsonObject.put("total", baseModelSearchResult.getLength());

			users = baseModelSearchResult.getBaseModels();
		}
		else {
			int total = UserLocalServiceUtil.searchCount(
				user.getCompanyId(), keywords,
				WorkflowConstants.STATUS_APPROVED, params);

			jsonObject.put("total", total);

			users = UserLocalServiceUtil.search(
				user.getCompanyId(), keywords,
				WorkflowConstants.STATUS_APPROVED, params, start, end,
				new UserFirstNameComparator(true));
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (User curUser : users) {
			JSONObject userJSONObject = JSONFactoryUtil.createJSONObject();

			StringBundler sb = new StringBundler(5);

			sb.append(curUser.getFullName());
			sb.append(CharPool.SPACE);
			sb.append(CharPool.LESS_THAN);
			sb.append(curUser.getScreenName());
			sb.append(CharPool.GREATER_THAN);

			userJSONObject.put("name", sb.toString());

			jsonArray.put(userJSONObject);
		}

		jsonObject.put("users", jsonArray);

		return jsonObject;
	}

	/**
	 * Each thread has a user that represents that thread. This person is either
	 * the last user to post on that thread (exluding himself), or if he is the
	 * only person to have posted on the thread, then he will the represenative.
	 */
	public static long getThreadRepresentativeUserId(
		long userId, long mbThreadId) {

		List<MBMessage> mbMessages =
			MBMessageLocalServiceUtil.getThreadMessages(
				mbThreadId, WorkflowConstants.STATUS_ANY,
				new MessageCreateDateComparator(false));

		for (MBMessage mbMessage : mbMessages) {
			if (userId != mbMessage.getUserId()) {
				return mbMessage.getUserId();
			}
		}

		List<UserThread> userThreads =
			UserThreadLocalServiceUtil.getMBThreadUserThreads(mbThreadId);

		for (UserThread userThread : userThreads) {
			if (userId != userThread.getUserId()) {
				return userThread.getUserId();
			}
		}

		return userId;
	}

	public static String getThreadSubject(long mbThreadId) {
		List<MBMessage> mbMessages =
			MBMessageLocalServiceUtil.getThreadMessages(
				mbThreadId, WorkflowConstants.STATUS_ANY, 0, 1);

		MBMessage mbMessage = mbMessages.get(0);

		return mbMessage.getSubject();
	}

	public static List<User> getThreadUsers(long userId, long mbThreadId)
		throws PortalException {

		List<User> users = new ArrayList<>();

		// Users who have contributed to the thread

		List<MBMessage> mbMessages = UserThreadServiceUtil.getThreadMessages(
			mbThreadId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, false);

		for (MBMessage mbMessage : mbMessages) {
			if (userId == mbMessage.getUserId()) {
				continue;
			}

			User user = UserLocalServiceUtil.fetchUser(mbMessage.getUserId());

			if (user == null) {
				user = UserLocalServiceUtil.createUser(mbMessage.getUserId());

				user.setFirstName(mbMessage.getUserName());
				user.setStatus(WorkflowConstants.STATUS_INACTIVE);
			}

			if (!users.contains(user)) {
				users.add(user);
			}
		}

		// Users who can view the thread

		List<UserThread> userThreads =
			UserThreadLocalServiceUtil.getMBThreadUserThreads(mbThreadId);

		for (UserThread userThread : userThreads) {
			if (userId == userThread.getUserId()) {
				continue;
			}

			User user = UserLocalServiceUtil.fetchUser(userThread.getUserId());

			if (user == null) {
				user = UserLocalServiceUtil.createUser(userThread.getUserId());

				user.setFirstName(userThread.getUserName());
				user.setStatus(WorkflowConstants.STATUS_INACTIVE);
			}

			if (!users.contains(user)) {
				users.add(user);
			}
		}

		return users;
	}

	public static boolean isUserPartOfThread(long userId, long mbThreadId)
		throws PortalException {

		try {
			UserThreadLocalServiceUtil.getUserThread(userId, mbThreadId);

			return true;
		}
		catch (NoSuchUserThreadException nsute) {
			return false;
		}
	}

	protected static String[] getAutocompleteRecipientSiteExcludes(
		long companyId) {

		PrivateMessagingConfiguration privateMessagingConfiguration =
			getPortletConfiguration(companyId);

		return privateMessagingConfiguration.
			autocompleteRecipientSiteExcludes();
	}

	protected static PrivateMessagingConfiguration getPortletConfiguration(
		long companyId) {

		try {
			return ConfigurationProviderUtil.getConfiguration(
				PrivateMessagingConfiguration.class,
				new CompanyServiceSettingsLocator(
					companyId,
					"com.liferay.social.privatemessaging.configuration." +
						"PrivateMessagingConfiguration"));
		}
		catch (ConfigurationException ce) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(ce, ce);
			}

			return null;
		}
	}

	private static final boolean _USERS_SEARCH_WITH_INDEX =
		GetterUtil.getBoolean(PropsUtil.get(PropsKeys.USERS_SEARCH_WITH_INDEX));

	private static final Log _log = LogFactoryUtil.getLog(
		PrivateMessagingUtil.class);

}