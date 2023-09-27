/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.content.page.editor.web.internal.mentions.strategy;

import com.liferay.mentions.constants.MentionsPortletKeys;
import com.liferay.mentions.strategy.MentionsStrategy;
import com.liferay.mentions.util.MentionsUserFinder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.social.kernel.util.SocialInteractionsConfigurationUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = "mentions.strategy=pageEditorCommentStrategy",
	service = MentionsStrategy.class
)
public class PageEditorCommentMentionsStrategy implements MentionsStrategy {

	@Override
	public List<User> getUsers(
			long companyId, long groupId, long userId, String query,
			JSONObject jsonObject)
		throws PortalException {

		long plid = jsonObject.getLong("plid");

		return ListUtil.filter(
			_mentionsUserFinder.getUsers(
				companyId, groupId, userId, query,
				SocialInteractionsConfigurationUtil.
					getSocialInteractionsConfiguration(
						companyId, MentionsPortletKeys.MENTIONS)),
			user -> {
				try {
					return _layoutPermission.contains(
						_permissionCheckerFactory.create(user), plid,
						ActionKeys.UPDATE);
				}
				catch (PortalException portalException) {
					_log.error(
						"Unable to check permission for " + user,
						portalException);

					return false;
				}
			});
	}

	@Override
	public List<User> getUsers(
			long companyId, long userId, String query, JSONObject jsonObject)
		throws PortalException {

		return getUsers(
			companyId, GroupConstants.DEFAULT_PARENT_GROUP_ID, userId, query,
			jsonObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PageEditorCommentMentionsStrategy.class);

	@Reference
	private LayoutPermission _layoutPermission;

	@Reference
	private MentionsUserFinder _mentionsUserFinder;

	@Reference
	private PermissionCheckerFactory _permissionCheckerFactory;

}