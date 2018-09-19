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

package com.liferay.mentions.internal.util;

import com.liferay.mentions.util.MentionsUserFinder;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.comparator.UserScreenNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.social.kernel.model.SocialRelationConstants;
import com.liferay.social.kernel.util.SocialInteractionsConfiguration;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = MentionsUserFinder.class)
public class DefaultMentionsUserFinder implements MentionsUserFinder {

	@Override
	public List<User> getUsers(
			long companyId, long userId, String query,
			SocialInteractionsConfiguration socialInteractionsConfiguration)
		throws PortalException {

		if (socialInteractionsConfiguration.
				isSocialInteractionsAnyUserEnabled()) {

			LinkedHashMap<String, Object> params = new LinkedHashMap<>();

			params.put("wildcardMode", WildcardMode.TRAILING);

			return _userLocalService.search(
				companyId, query, WorkflowConstants.STATUS_APPROVED, params, 0,
				_MAX_USERS, new UserScreenNameComparator());
		}

		User user = _userLocalService.getUser(userId);

		int[] types = {SocialRelationConstants.TYPE_BI_FRIEND};

		if (socialInteractionsConfiguration.
				isSocialInteractionsFriendsEnabled() &&
			socialInteractionsConfiguration.
				isSocialInteractionsSitesEnabled()) {

			return _userLocalService.searchSocial(
				user.getGroupIds(), userId, types, query, 0, _MAX_USERS);
		}

		if (socialInteractionsConfiguration.
				isSocialInteractionsSitesEnabled()) {

			return _userLocalService.searchSocial(
				companyId, user.getGroupIds(), query, 0, _MAX_USERS);
		}

		if (socialInteractionsConfiguration.
				isSocialInteractionsFriendsEnabled()) {

			return _userLocalService.searchSocial(
				userId, types, query, 0, _MAX_USERS);
		}

		return Collections.emptyList();
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final int _MAX_USERS = 20;

	private UserLocalService _userLocalService;

}