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

package com.liferay.journal.web.social;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portlet.social.manager.BaseSocialActivityManager;
import com.liferay.portlet.social.manager.SocialActivityManager;
import com.liferay.portlet.social.service.SocialActivityLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = "model.className=com.liferay.portlet.journal.model.JournalArticle",
	service = SocialActivityManager.class
)
public class JournalArticleSocialActivityManager
	extends BaseSocialActivityManager<JournalArticle> {

	@Override
	protected long getPrimaryKey(JournalArticle journalArticle) {
		return journalArticle.getResourcePrimKey();
	}

	@Override
	protected SocialActivityLocalService getSocialActivityLocalService() {
		return _socialActivityLocalService;
	}

	@Reference(unbind = "-")
	protected void setSocialActivityLocalService(
		SocialActivityLocalService socialActivityLocalService) {

		_socialActivityLocalService = socialActivityLocalService;
	}

	private SocialActivityLocalService _socialActivityLocalService;

}