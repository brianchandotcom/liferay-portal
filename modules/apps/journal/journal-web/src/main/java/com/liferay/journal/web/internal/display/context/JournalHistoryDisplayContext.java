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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.web.internal.util.JournalPortletUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalHistoryDisplayContext {

	public JournalHistoryDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		JournalArticle article) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_article = article;

		_httpServletRequest = PortalUtil.getHttpServletRequest(renderRequest);
	}

	public SearchContainer getArticleSearchContainer() {
		SearchContainer articleSearchContainer = new SearchContainer(
			_renderRequest, getPortletURL(), null, null);

		articleSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		articleSearchContainer.setOrderByCol(getOrderByCol());

		OrderByComparator<JournalArticle> orderByComparator =
			JournalPortletUtil.getArticleOrderByComparator(
				getOrderByCol(), getOrderByType());

		articleSearchContainer.setOrderByComparator(orderByComparator);

		articleSearchContainer.setOrderByType(getOrderByType());

		int articleVersionsCount =
			JournalArticleServiceUtil.getArticlesCountByArticleId(
				_article.getGroupId(), _article.getArticleId());

		articleSearchContainer.setTotal(articleVersionsCount);

		List<JournalArticle> articleVersions =
			JournalArticleServiceUtil.getArticlesByArticleId(
				_article.getGroupId(), _article.getArticleId(),
				articleSearchContainer.getStart(),
				articleSearchContainer.getEnd(), orderByComparator);

		articleSearchContainer.setResults(articleVersions);

		return articleSearchContainer;
	}

	public String getBackURL() {
		if (_backURL != null) {
			return _backURL;
		}

		_backURL = ParamUtil.getString(_renderRequest, "backURL");

		return _backURL;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_renderRequest, "displayStyle", "list");

		return _displayStyle;
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(true);
				navigationItem.setHref(StringPool.BLANK);
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "versions"));
			}
		).build();
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_renderRequest, "orderByCol", "version");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(
			_renderRequest, "orderByType", "asc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/view_article_history.jsp");
		portletURL.setParameter("redirect", _getRedirect());
		portletURL.setParameter("backURL", getBackURL());
		portletURL.setParameter(
			"referringPortletResource", getReferringPortletResource());
		portletURL.setParameter(
			"groupId", String.valueOf(_article.getGroupId()));
		portletURL.setParameter("articleId", _article.getArticleId());
		portletURL.setParameter("displayStyle", getDisplayStyle());
		portletURL.setParameter("orderByCol", getOrderByCol());
		portletURL.setParameter("orderByType", getOrderByType());

		return portletURL;
	}

	public String getReferringPortletResource() {
		if (_referringPortletResource != null) {
			return _referringPortletResource;
		}

		_referringPortletResource = ParamUtil.getString(
			_renderRequest, "referringPortletResource");

		return _referringPortletResource;
	}

	private String _getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_renderRequest, "redirect");

		return _redirect;
	}

	private final JournalArticle _article;
	private String _backURL;
	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private String _orderByCol;
	private String _orderByType;
	private String _redirect;
	private String _referringPortletResource;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}