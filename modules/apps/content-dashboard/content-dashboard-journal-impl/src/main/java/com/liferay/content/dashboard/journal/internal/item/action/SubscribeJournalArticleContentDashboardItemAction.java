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

package com.liferay.content.dashboard.journal.internal.item.action;

import com.liferay.content.dashboard.item.action.ContentDashboardItemAction;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class SubscribeJournalArticleContentDashboardItemAction
	implements ContentDashboardItemAction {

	public SubscribeJournalArticleContentDashboardItemAction(
		HttpServletRequest httpServletRequest, JournalArticle journalArticle,
		ModelResourcePermission<JournalArticle>
			journalArticleModelResourcePermission,
		Language language,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		_httpServletRequest = httpServletRequest;
		_journalArticle = journalArticle;
		_journalArticleModelResourcePermission =
			journalArticleModelResourcePermission;
		_language = language;
		_requestBackedPortletURLFactory = requestBackedPortletURLFactory;
	}

	@Override
	public String getIcon() {
		return "bell-on";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "subscribe");
	}

	@Override
	public String getName() {
		return "subscribe";
	}

	@Override
	public Type getType() {
		return Type.SUBSCRIBE;
	}

	@Override
	public String getURL() {
		return PortletURLBuilder.create(
			_requestBackedPortletURLFactory.createActionURL(
				JournalPortletKeys.JOURNAL)
		).setActionName(
			"/journal/subscribe_article"
		).setRedirect(
			ParamUtil.getString(_httpServletRequest, "backURL")
		).setParameter(
			"articleId", _journalArticle.getResourcePrimKey()
		).buildString();
	}

	@Override
	public String getURL(Locale locale) {
		return getURL();
	}

	public boolean isDisabled() {
		try {
			_journalArticleModelResourcePermission.check(
				GuestOrUserUtil.getPermissionChecker(),
				_journalArticle.getResourcePrimKey(), ActionKeys.SUBSCRIBE);

			return false;
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return true;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SubscribeJournalArticleContentDashboardItemAction.class);

	private final HttpServletRequest _httpServletRequest;
	private final JournalArticle _journalArticle;
	private final ModelResourcePermission<JournalArticle>
		_journalArticleModelResourcePermission;
	private final Language _language;
	private final RequestBackedPortletURLFactory
		_requestBackedPortletURLFactory;

}