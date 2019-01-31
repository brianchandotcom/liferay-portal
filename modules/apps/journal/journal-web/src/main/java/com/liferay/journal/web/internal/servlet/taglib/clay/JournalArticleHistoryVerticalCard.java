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

package com.liferay.journal.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseVerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.web.internal.constants.JournalWebConstants;
import com.liferay.journal.web.internal.servlet.taglib.util.JournalArticleActionDropdownItemsProvider;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.trash.TrashHelper;

import java.util.Date;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleHistoryVerticalCard extends BaseVerticalCard {

	public JournalArticleHistoryVerticalCard(
		BaseModel<?> baseModel, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker,
		TrashHelper trashHelper) {

		super(baseModel, renderRequest, rowChecker);

		_renderResponse = renderResponse;
		_trashHelper = trashHelper;

		_article = (JournalArticle)baseModel;
		_request = PortalUtil.getHttpServletRequest(renderRequest);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(renderRequest);

		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(_renderResponse);

		JournalArticleActionDropdownItemsProvider
			articleActionDropdownItemsProvider =
				new JournalArticleActionDropdownItemsProvider(
					_article, liferayPortletRequest, liferayPortletResponse,
					_trashHelper);

		try {
			return articleActionDropdownItemsProvider.
				getArticleHistoryActionDropdownItems();
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getDefaultEventHandler() {
		return JournalWebConstants.JOURNAL_ELEMENTS_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getIcon() {
		return "web-content";
	}

	@Override
	public String getImageSrc() {
		return HtmlUtil.escape(_article.getArticleImageURL(themeDisplay));
	}

	@Override
	public String getSubtitle() {
		Date createDate = _article.getModifiedDate();

		String modifiedDateDescription = LanguageUtil.getTimeDescription(
			_request, System.currentTimeMillis() - createDate.getTime(), true);

		return LanguageUtil.format(
			_request, "modified-x-ago", modifiedDateDescription);
	}

	@Override
	public String getTitle() {
		return HtmlUtil.escape(_article.getTitle(themeDisplay.getLocale()));
	}

	private final JournalArticle _article;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private final TrashHelper _trashHelper;

}