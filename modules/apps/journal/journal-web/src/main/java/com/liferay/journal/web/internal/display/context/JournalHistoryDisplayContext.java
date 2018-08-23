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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.web.internal.security.permission.resource.JournalArticlePermission;
import com.liferay.journal.web.util.JournalPortletUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Objects;

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

		_request = PortalUtil.getHttpServletRequest(renderRequest);
	}

	public List<DropdownItem> getActionItemsDropdownItems()
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return new DropdownItemList() {
			{
				if (JournalArticlePermission.contains(
						themeDisplay.getPermissionChecker(), _article,
						ActionKeys.DELETE)) {

					add(
						dropdownItem -> {
							dropdownItem.putData("action", "deleteArticles");
							dropdownItem.setIcon("trash");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "delete"));
							dropdownItem.setQuickAction(true);
						});
				}

				if (JournalArticlePermission.contains(
						themeDisplay.getPermissionChecker(), _article,
						ActionKeys.EXPIRE)) {

					add(
						dropdownItem -> {
							dropdownItem.putData("action", "expireArticles");
							dropdownItem.setIcon("time");
							dropdownItem.setLabel(
								LanguageUtil.get(_request, "expire"));
							dropdownItem.setQuickAction(true);
						});
				}
			}
		};
	}

	public SearchContainer getArticleSearchContainer() {
		SearchContainer articleSearchContainer = new SearchContainer(
			_renderRequest, getPortletURL(), null, null);

		articleSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		int articleVersionsCount =
			JournalArticleServiceUtil.getArticlesCountByArticleId(
				_article.getGroupId(), _article.getArticleId());

		articleSearchContainer.setTotal(articleVersionsCount);

		OrderByComparator<JournalArticle> orderByComparator =
			JournalPortletUtil.getArticleOrderByComparator(
				getOrderByCol(), getOrderByType());

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

	public List<DropdownItem> getFilterItemsDropdownItems() {
		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							_getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(_request, "order-by"));
					});
			}
		};
	}

	public List<NavigationItem> getNavigationItems() {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(
							LanguageUtil.get(_request, "versions"));
					});
			}
		};
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

	public String getSortingURL() {
		PortletURL sortingURL = getPortletURL();

		sortingURL.setParameter(
			"orderByType",
			Objects.equals(getOrderByType(), "asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public int getTotalItems() {
		return JournalArticleServiceUtil.getArticlesCountByArticleId(
			_article.getGroupId(), _article.getArticleId());
	}

	public List<ViewTypeItem> getViewTypeItems() {
		return new ViewTypeItemList(getPortletURL(), getDisplayStyle()) {
			{
				addCardViewTypeItem();
				addListViewTypeItem();
				addTableViewTypeItem();
			}
		};
	}

	private List<DropdownItem> _getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);
						dropdownItem.setHref(_renderResponse.createRenderURL());
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "all"));
					});
			}
		};
	}

	private List<DropdownItem> _getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "version"));
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "version");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "version"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "display-date"));
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "display-date");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "display-date"));
					});

				add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(getOrderByCol(), "modified-date"));
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", "modified-date");
						dropdownItem.setLabel(
							LanguageUtil.get(_request, "modified-date"));
					});
			}
		};
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
	private String _orderByCol;
	private String _orderByType;
	private String _redirect;
	private String _referringPortletResource;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;

}