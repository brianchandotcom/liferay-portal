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

import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionAction;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;

import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class CompareVersionsJournalArticleContentDashboardItemVersionAction
	implements ContentDashboardItemVersionAction {

	public CompareVersionsJournalArticleContentDashboardItemVersionAction(
		HttpServletRequest httpServletRequest, JournalArticle journalArticle,
		Language language, JournalArticle latestJournalArticle, Portal portal,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		_httpServletRequest = httpServletRequest;
		_journalArticle = journalArticle;
		_language = language;
		_latestJournalArticle = latestJournalArticle;
		_portal = portal;
		_requestBackedPortletURLFactory = requestBackedPortletURLFactory;
	}

	@Override
	public String getIcon() {
		return null;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "compare-to");
	}

	@Override
	public String getName() {
		return "compare-versions";
	}

	public Type getType() {
		return Type.NAVIGATE;
	}

	@Override
	public String getURL() {
		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(
				(PortletResponse)_httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_RESPONSE));

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		return PortletURLBuilder.create(
			_requestBackedPortletURLFactory.createActionURL(
				JournalPortletKeys.JOURNAL)
		).setMVCRenderCommandName(
			"/journal/compare_versions"
		).setRedirect(
			portletURL
		).setBackURL(
			portletURL.toString()
		).setParameter(
			"articleId", _journalArticle.getArticleId()
		).setParameter(
			"groupId", _journalArticle.getGroupId()
		).setParameter(
			"sourceVersion", _journalArticle.getVersion()
		).setParameter(
			"targetVersion", _latestJournalArticle.getVersion()
		).buildString();
	}

	private final HttpServletRequest _httpServletRequest;
	private final JournalArticle _journalArticle;
	private final Language _language;
	private final JournalArticle _latestJournalArticle;
	private final Portal _portal;
	private final RequestBackedPortletURLFactory
		_requestBackedPortletURLFactory;

}