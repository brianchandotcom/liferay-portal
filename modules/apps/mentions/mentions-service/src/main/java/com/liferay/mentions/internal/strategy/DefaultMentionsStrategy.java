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

package com.liferay.mentions.internal.strategy;

import com.liferay.mentions.constants.MentionsPortletKeys;
import com.liferay.mentions.strategy.MentionsStrategy;
import com.liferay.mentions.util.MentionsUserFinder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.social.kernel.util.SocialInteractionsConfiguration;
import com.liferay.social.kernel.util.SocialInteractionsConfigurationUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = "mentions.strategy=default", service = MentionsStrategy.class
)
public class DefaultMentionsStrategy implements MentionsStrategy {

	public List<User> getUsers(
			long companyId, long userId, String query, JSONObject jsonObject)
		throws PortalException {

		SocialInteractionsConfiguration socialInteractionsConfiguration =
			SocialInteractionsConfigurationUtil.
				getSocialInteractionsConfiguration(
					companyId, MentionsPortletKeys.MENTIONS);

		return _mentionsUserFinder.getUsers(
			companyId, userId, query, socialInteractionsConfiguration);
	}

	@Reference
	private MentionsUserFinder _mentionsUserFinder;

}