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

package com.liferay.journal.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pavel Savinov
 */
public class JournalSelectArticleTranslationsManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public JournalSelectArticleTranslationsManagementToolbarDisplayContext(
			HttpServletRequest httpServletRequest,
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			JournalDisplayContext journalDisplayContext)
		throws Exception {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			journalDisplayContext.getArticleTranslationsSearchContainer());

		_journalDisplayContext = journalDisplayContext;
	}

	@Override
	public String getClearResultsURL() {
		PortletURL clearResultsURL = getPortletURL();

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	@Override
	public String getSearchActionURL() {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/select_article_translations.jsp");

		try {
			JournalArticle article = _journalDisplayContext.getArticle();

			portletURL.setParameter(
				"groupId", String.valueOf(article.getGroupId()));
			portletURL.setParameter("articleId", article.getArticleId());
			portletURL.setParameter(
				"status", String.valueOf(article.getStatus()));
		}
		catch (PortalException portalException) {
			_log.error("Unable to get the article", portalException);
		}

		return portletURL.toString();
	}

	@Override
	protected String[] getDisplayViews() {
		return new String[] {"list"};
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all"};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalSelectArticleTranslationsManagementToolbarDisplayContext.class);

	private final JournalDisplayContext _journalDisplayContext;

}