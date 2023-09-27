/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mentions.internal.util;

import com.liferay.mentions.util.MentionsUserFinder;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.comparator.UserScreenNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.social.kernel.model.SocialRelationConstants;
import com.liferay.social.kernel.service.SocialRelationLocalService;
import com.liferay.social.kernel.util.SocialInteractionsConfiguration;

import java.util.Collections;
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
			long companyId, long groupId, long userId, String query,
			SocialInteractionsConfiguration socialInteractionsConfiguration)
		throws PortalException {

		if (socialInteractionsConfiguration.
				isSocialInteractionsAnyUserEnabled()) {

			return _userLocalService.search(
				companyId, query, WorkflowConstants.STATUS_APPROVED,
				LinkedHashMapBuilder.<String, Object>put(
					"wildcardMode", WildcardMode.TRAILING
				).build(),
				0, _MAX_USERS, new UserScreenNameComparator());
		}

		User user = _userLocalService.getUser(userId);

		long[] groupIds = ListUtil.toLongArray(
			_groupLocalService.getUserSitesGroups(user.getUserId()),
			Group.GROUP_ID_ACCESSOR);

		if (socialInteractionsConfiguration.
				isSocialInteractionsFriendsEnabled() &&
			socialInteractionsConfiguration.
				isSocialInteractionsSitesEnabled()) {

			return ListUtil.filter(
				_userLocalService.searchBySocial(
					groupIds, userId, _TYPES, query, 0, _MAX_USERS),
				mentionedUser -> {
					try {
						if (_socialRelationLocalService.hasRelation(
								user.getUserId(), mentionedUser.getUserId(),
								SocialRelationConstants.TYPE_BI_FRIEND)) {

							return true;
						}

						long[] siteGroupIds = ListUtil.toLongArray(
							_groupLocalService.getUserSitesGroups(
								mentionedUser.getUserId()),
							Group.GROUP_ID_ACCESSOR);

						if ((groupId ==
								GroupConstants.DEFAULT_PARENT_GROUP_ID) ||
							ArrayUtil.contains(siteGroupIds, groupId)) {

							return true;
						}
					}
					catch (PortalException portalException) {
						_log.error(portalException);

						return false;
					}

					return false;
				});
		}

		if (socialInteractionsConfiguration.
				isSocialInteractionsSitesEnabled()) {

			return _userLocalService.searchBySocial(
				companyId, groupIds, query, 0, _MAX_USERS);
		}

		if (socialInteractionsConfiguration.
				isSocialInteractionsFriendsEnabled()) {

			return _userLocalService.searchBySocial(
				userId, _TYPES, query, 0, _MAX_USERS);
		}

		return Collections.emptyList();
	}

	@Override
	public List<User> getUsers(
			long companyId, long userId, String query,
			SocialInteractionsConfiguration socialInteractionsConfiguration)
		throws PortalException {

		return getUsers(
			companyId, GroupConstants.DEFAULT_PARENT_GROUP_ID, userId, query,
			socialInteractionsConfiguration);
	}

	private static final int _MAX_USERS = 20;

	private static final int[] _TYPES = {
		SocialRelationConstants.TYPE_BI_FRIEND
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultMentionsUserFinder.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private SocialRelationLocalService _socialRelationLocalService;

	@Reference
	private UserLocalService _userLocalService;

}