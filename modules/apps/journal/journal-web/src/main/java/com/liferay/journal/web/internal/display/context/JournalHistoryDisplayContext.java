/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.web.internal.util.JournalPortletUtil;
import com.liferay.journal.web.internal.util.JournalSearcherUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.SearchDisplayStyleUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.ParseException;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.searcher.SearchResponse;

import jakarta.portlet.PortletURL;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_httpServletRequest);
	}

	public SearchContainer<JournalArticle> getArticleSearchContainer() {
		SearchContainer<JournalArticle> articleSearchContainer =
			new SearchContainer(_renderRequest, getPortletURL(), null, null);

		articleSearchContainer.setOrderByCol(getOrderByCol());
		articleSearchContainer.setOrderByComparator(
			JournalPortletUtil.getArticleOrderByComparator(
				getOrderByCol(), getOrderByType()));
		articleSearchContainer.setOrderByType(getOrderByType());

		if (isSearch()) {
			SearchResponse searchResponse =
				JournalSearcherUtil.searchJournalArticles(
					searchContext -> _populateSearchContext(
						articleSearchContainer.getStart(),
						articleSearchContainer.getEnd(), searchContext));

			articleSearchContainer.setResultsAndTotal(
				() -> JournalSearcherUtil.transformJournalArticles(
					searchResponse.getDocuments71()),
				searchResponse.getTotalHits());
		}
		else {
			articleSearchContainer.setResultsAndTotal(
				() -> JournalArticleServiceUtil.getArticlesByArticleId(
					_article.getGroupId(), _article.getArticleId(),
					articleSearchContainer.getStart(),
					articleSearchContainer.getEnd(),
					articleSearchContainer.getOrderByComparator()),
				JournalArticleServiceUtil.getArticlesCountByArticleId(
					_article.getGroupId(), _article.getArticleId()));
		}

		articleSearchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_renderResponse));

		return articleSearchContainer;
	}

	public String getBackURL() {
		if (_backURL != null) {
			return _backURL;
		}

		_backURL = ParamUtil.getString(
			_httpServletRequest, "backURL", _getRedirect());

		return _backURL;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		_displayStyle = SearchDisplayStyleUtil.getDisplayStyle(
			_renderRequest, JournalPortletKeys.JOURNAL, "history-display-style",
			"list");

		return _displayStyle;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	public List<NavigationItem> getNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(true);
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "versions"));
			}
		).build();
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest, JournalPortletKeys.JOURNAL,
			"history-order-by-col", "version");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest, JournalPortletKeys.JOURNAL,
			"history-order-by-type", "desc");

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		return PortletURLBuilder.createRenderURL(
			_renderResponse
		).setMVCPath(
			"/view_article_history.jsp"
		).setRedirect(
			_getRedirect()
		).setBackURL(
			getBackURL()
		).setParameter(
			"articleId", _article.getArticleId()
		).setParameter(
			"displayStyle", getDisplayStyle()
		).setParameter(
			"groupId", _article.getGroupId()
		).setParameter(
			"orderByCol", getOrderByCol()
		).setParameter(
			"orderByType", getOrderByType()
		).setParameter(
			"referringPortletResource", getReferringPortletResource()
		).buildPortletURL();
	}

	public String getReferringPortletResource() {
		if (_referringPortletResource != null) {
			return _referringPortletResource;
		}

		_referringPortletResource = ParamUtil.getString(
			_renderRequest, "referringPortletResource");

		return _referringPortletResource;
	}

	public boolean isSearch() {
		return Validator.isNotNull(getKeywords());
	}

	private String _getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_renderRequest, "redirect");

		return _redirect;
	}

	private Sort _getSort() {
		boolean orderByAsc = Objects.equals(getOrderByType(), "asc");

		if (Objects.equals(getOrderByCol(), "display-date")) {
			return new Sort(Field.DISPLAY_DATE, Sort.LONG_TYPE, !orderByAsc);
		}

		return new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, !orderByAsc);
	}

	private void _populateSearchContext(
		int start, int end, SearchContext searchContext) {

		searchContext.setAndSearch(false);

		Map<String, Serializable> attributes = searchContext.getAttributes();

		attributes.put(Field.ARTICLE_ID, getKeywords());
		attributes.put(Field.CONTENT, getKeywords());
		attributes.put(Field.DESCRIPTION, getKeywords());
		attributes.put(Field.TITLE, getKeywords());
		attributes.put("head", false);
		attributes.put(
			"params",
			LinkedHashMapBuilder.<String, Object>put(
				"expandoAttributes", getKeywords()
			).put(
				"keywords", getKeywords()
			).build());

		searchContext.setAttributes(attributes);

		try {
			BooleanQuery booleanQuery = new BooleanQueryImpl();

			booleanQuery.add(
				new TermQueryImpl(Field.ARTICLE_ID, _article.getArticleId()),
				BooleanClauseOccur.MUST);

			searchContext.setBooleanClauses(
				new BooleanClause[] {
					BooleanClauseFactoryUtil.create(
						booleanQuery, BooleanClauseOccur.MUST.getName())
				});
		}
		catch (ParseException parseException) {
			throw new SystemException(parseException);
		}

		searchContext.setCompanyId(_article.getCompanyId());
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {_article.getGroupId()});
		searchContext.setIncludeInternalAssetCategories(true);
		searchContext.setKeywords(getKeywords());
		searchContext.setSorts(_getSort());
		searchContext.setStart(start);
	}

	private final JournalArticle _article;
	private String _backURL;
	private String _displayStyle;
	private final HttpServletRequest _httpServletRequest;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;
	private String _redirect;
	private String _referringPortletResource;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}