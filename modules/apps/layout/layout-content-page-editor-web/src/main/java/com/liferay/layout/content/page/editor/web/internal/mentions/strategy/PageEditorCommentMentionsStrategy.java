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

package com.liferay.layout.content.page.editor.web.internal.mentions.strategy;

import com.liferay.mentions.constants.MentionsPortletKeys;
import com.liferay.mentions.strategy.MentionsStrategy;
import com.liferay.mentions.util.MentionsUserFinder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.permission.LayoutPermission;
import com.liferay.social.kernel.util.SocialInteractionsConfiguration;
import com.liferay.social.kernel.util.SocialInteractionsConfigurationUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	public List<User> getUsers(
			long companyId, long userId, String query, JSONObject jsonObject)
		throws PortalException {

		SocialInteractionsConfiguration socialInteractionsConfiguration =
			SocialInteractionsConfigurationUtil.
				getSocialInteractionsConfiguration(
					companyId, MentionsPortletKeys.MENTIONS);

		List<User> users = _mentionsUserFinder.getUsers(
			companyId, userId, query, socialInteractionsConfiguration);

		long plid = jsonObject.getLong("plid");

		Stream<User> stream = users.stream();

		return stream.filter(
			user -> {
				try {
					return _layoutPermission.contains(
						_permissionCheckerFactory.create(user), plid,
						ActionKeys.UPDATE);
				}
				catch (PortalException pe) {
					_log.error("Unable to check permission for " + user, pe);

					return false;
				}
			}
		).collect(
			Collectors.toList()
		);
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