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
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Cristina González
 */
public class UnsubscribeJournalArticleContentDashboardItemAction
	implements ContentDashboardItemAction {

	public UnsubscribeJournalArticleContentDashboardItemAction(
		HttpServletRequest httpServletRequest, JournalArticle journalArticle,
		Language language, Portal portal,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		_httpServletRequest = httpServletRequest;
		_journalArticle = journalArticle;
		_language = language;
		_portal = portal;
		_requestBackedPortletURLFactory = requestBackedPortletURLFactory;
	}

	@Override
	public String getIcon() {
		return "bell-off";
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "unsubscribe");
	}

	@Override
	public String getName() {
		return "unsubscribe";
	}

	@Override
	public Type getType() {
		return Type.UNSUBSCRIBE;
	}

	@Override
	public String getURL() {
		return PortletURLBuilder.create(
			_requestBackedPortletURLFactory.createActionURL(
				JournalPortletKeys.JOURNAL)
		).setActionName(
			"/journal/unsubscribe_article"
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

	private final HttpServletRequest _httpServletRequest;
	private final JournalArticle _journalArticle;
	private final Language _language;
	private final Portal _portal;
	private final RequestBackedPortletURLFactory
		_requestBackedPortletURLFactory;

}