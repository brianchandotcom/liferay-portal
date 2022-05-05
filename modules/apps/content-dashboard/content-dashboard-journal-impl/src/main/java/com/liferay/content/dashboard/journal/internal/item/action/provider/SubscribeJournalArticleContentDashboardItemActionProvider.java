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

package com.liferay.content.dashboard.journal.internal.item.action.provider;

import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemActionProvider;
import com.liferay.content.dashboard.journal.internal.item.action.SubscribeJournalArticleContentDashboardItemAction;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.subscription.service.SubscriptionLocalService;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = ContentDashboardItemActionProvider.class)
public class SubscribeJournalArticleContentDashboardItemActionProvider
	implements ContentDashboardItemActionProvider<JournalArticle> {

	@Override
	public ContentDashboardItemAction getContentDashboardItemAction(
		JournalArticle journalArticle, HttpServletRequest httpServletRequest) {

		if (!isShow(journalArticle, httpServletRequest)) {
			return null;
		}

		return new SubscribeJournalArticleContentDashboardItemAction(
			httpServletRequest, journalArticle, _language, _portal,
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest));
	}

	@Override
	public String getKey() {
		return "subscribe";
	}

	@Override
	public ContentDashboardItemAction.Type getType() {
		return ContentDashboardItemAction.Type.SUBSCRIBE;
	}

	@Override
	public boolean isShow(
		JournalArticle journalArticle, HttpServletRequest httpServletRequest) {

		return !_subscriptionLocalService.isSubscribed(
			journalArticle.getCompanyId(),
			_portal.getUserId(httpServletRequest),
			JournalArticle.class.getName(),
			journalArticle.getResourcePrimKey());
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

}