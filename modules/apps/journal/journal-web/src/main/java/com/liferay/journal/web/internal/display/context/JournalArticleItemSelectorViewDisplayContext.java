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

import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.util.comparator.FolderArticleArticleIdComparator;
import com.liferay.journal.util.comparator.FolderArticleModifiedDateComparator;
import com.liferay.journal.util.comparator.FolderArticleTitleComparator;
import com.liferay.journal.web.internal.configuration.JournalWebConfiguration;
import com.liferay.journal.web.internal.item.selector.JournalArticleItemSelectorView;
import com.liferay.journal.web.internal.search.JournalSearcher;
import com.liferay.journal.web.internal.util.JournalPortletUtil;
import com.liferay.journal.web.internal.util.SiteConnectedGroupUtil;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.GroupServiceUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalArticleItemSelectorViewDisplayContext {

	public JournalArticleItemSelectorViewDisplayContext(
		HttpServletRequest httpServletRequest,
		InfoItemItemSelectorCriterion infoItemItemSelectorCriterion,
		String itemSelectedEventName,
		JournalArticleItemSelectorView journalArticleItemSelectorView,
		JournalWebConfiguration journalWebConfiguration, PortletURL portletURL,
		boolean search) {

		_httpServletRequest = httpServletRequest;
		_infoItemItemSelectorCriterion = infoItemItemSelectorCriterion;
		_itemSelectedEventName = itemSelectedEventName;
		_journalArticleItemSelectorView = journalArticleItemSelectorView;
		_journalWebConfiguration = journalWebConfiguration;
		_portletURL = portletURL;
		_search = search;

		_portletRequest = (PortletRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		_portletResponse = (RenderResponse)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String getDDMStructureKey() {
		if (_ddmStructureKey != null) {
			return _ddmStructureKey;
		}

		String ddmStructureKey = ParamUtil.getString(
			_httpServletRequest, "ddmStructureKey");

		if (Validator.isNull(ddmStructureKey)) {
			ddmStructureKey = _infoItemItemSelectorCriterion.getItemSubtype();
		}

		_ddmStructureKey = ddmStructureKey;

		return _ddmStructureKey;
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle", "descriptive");

		return _displayStyle;
	}

	public String getGroupCssIcon(long groupId) throws PortalException {
		Group group = GroupServiceUtil.getGroup(groupId);

		return group.getIconCssClass();
	}

	public String getGroupLabel(long groupId, Locale locale)
		throws PortalException {

		Group group = GroupServiceUtil.getGroup(groupId);

		return group.getDescriptiveName(locale);
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_httpServletRequest, "keywords");

		return _keywords;
	}

	public JournalArticle getLatestArticle(JournalArticle journalArticle) {
		JournalArticle latestArticle =
			JournalArticleLocalServiceUtil.fetchLatestArticle(
				journalArticle.getGroupId(), journalArticle.getArticleId(),
				WorkflowConstants.STATUS_ANY);

		if (latestArticle != null) {
			return latestArticle;
		}

		return journalArticle;
	}

	public List<BreadcrumbEntry> getPortletBreadcrumbEntries()
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries = new ArrayList<>();

		breadcrumbEntries.add(_getSiteBreadcrumb());

		breadcrumbEntries.add(_getHomeBreadcrumb());

		JournalFolder folder = _getFolder();

		if (folder == null) {
			return breadcrumbEntries;
		}

		List<JournalFolder> ancestorFolders = folder.getAncestors();

		Collections.reverse(ancestorFolders);

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter(
			"folderId",
			String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID));

		for (JournalFolder ancestorFolder : ancestorFolders) {
			BreadcrumbEntry folderBreadcrumbEntry = new BreadcrumbEntry();

			folderBreadcrumbEntry.setTitle(ancestorFolder.getName());

			portletURL.setParameter(
				"folderId", String.valueOf(ancestorFolder.getFolderId()));

			folderBreadcrumbEntry.setURL(portletURL.toString());

			breadcrumbEntries.add(folderBreadcrumbEntry);
		}

		if (folder.getFolderId() !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			BreadcrumbEntry folderBreadcrumbEntry = new BreadcrumbEntry();

			JournalFolder unescapedFolder = folder.toUnescapedModel();

			folderBreadcrumbEntry.setTitle(unescapedFolder.getName());

			portletURL.setParameter(
				"folderId", String.valueOf(folder.getFolderId()));

			folderBreadcrumbEntry.setURL(portletURL.toString());

			breadcrumbEntries.add(folderBreadcrumbEntry);
		}

		return breadcrumbEntries;
	}

	public PortletURL getPortletURL() throws PortletException {
		PortletURL portletURL = PortletURLUtil.clone(
			_portletURL,
			PortalUtil.getLiferayPortletResponse(_portletResponse));

		portletURL.setParameter("displayStyle", getDisplayStyle());
		portletURL.setParameter(
			"selectedTab",
			String.valueOf(_getTitle(_httpServletRequest.getLocale())));

		return portletURL;
	}

	public SearchContainer<?> getSearchContainer() throws Exception {
		if (_articleSearchContainer != null) {
			return _articleSearchContainer;
		}

		if (Validator.isNotNull(getDDMStructureKey())) {
			SearchContainer<JournalArticle> articleSearchContainer =
				new SearchContainer<>(
					_portletRequest, getPortletURL(), null, null);

			OrderByComparator<JournalArticle> orderByComparator =
				JournalPortletUtil.getArticleOrderByComparator(
					_getOrderByCol(), _getOrderByType());

			articleSearchContainer.setOrderByCol(_getOrderByCol());
			articleSearchContainer.setOrderByComparator(orderByComparator);
			articleSearchContainer.setOrderByType(_getOrderByType());

			int total = JournalArticleServiceUtil.getArticlesCountByStructureId(
				_themeDisplay.getScopeGroupId(), getDDMStructureKey(),
				WorkflowConstants.STATUS_APPROVED);

			articleSearchContainer.setTotal(total);

			List<JournalArticle> results =
				JournalArticleServiceUtil.getArticlesByStructureId(
					_themeDisplay.getScopeGroupId(), getDDMStructureKey(),
					WorkflowConstants.STATUS_APPROVED,
					articleSearchContainer.getStart(),
					articleSearchContainer.getEnd(), orderByComparator);

			articleSearchContainer.setResults(results);

			_articleSearchContainer = articleSearchContainer;

			return _articleSearchContainer;
		}

		SearchContainer<Object> articleAndFolderSearchContainer =
			new SearchContainer<>(_portletRequest, getPortletURL(), null, null);

		articleAndFolderSearchContainer.setOrderByCol(_getOrderByCol());
		articleAndFolderSearchContainer.setOrderByType(_getOrderByType());

		if (isSearch()) {
			List<Long> folderIds = new ArrayList<>(1);

			if (_getFolderId() !=
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				folderIds.add(_getFolderId());
			}

			boolean orderByAsc = false;

			if (Objects.equals(_getOrderByCol(), "asc")) {
				orderByAsc = true;
			}

			Sort sort = null;

			if (Objects.equals(_getOrderByCol(), "id")) {
				sort = new Sort(
					Field.getSortableFieldName(Field.ARTICLE_ID),
					Sort.STRING_TYPE, !orderByAsc);
			}
			else if (Objects.equals(_getOrderByCol(), "modified-date")) {
				sort = new Sort(
					Field.MODIFIED_DATE, Sort.LONG_TYPE, !orderByAsc);
			}
			else if (Objects.equals(_getOrderByCol(), "relevance")) {
				sort = new Sort(null, Sort.SCORE_TYPE, false);
			}
			else if (Objects.equals(_getOrderByCol(), "title")) {
				sort = new Sort(
					Field.getSortableFieldName(
						"localized_title_" + _themeDisplay.getLanguageId()),
					!orderByAsc);
			}

			Indexer<?> indexer = JournalSearcher.getInstance();

			SearchContext searchContext = buildSearchContext(
				folderIds, articleAndFolderSearchContainer.getStart(),
				articleAndFolderSearchContainer.getEnd(), sort);

			Hits hits = indexer.search(searchContext);

			int total = hits.getLength();

			articleAndFolderSearchContainer.setTotal(total);

			List<Object> results = new ArrayList<>();

			Document[] documents = hits.getDocs();

			for (Document document : documents) {
				String className = document.get(Field.ENTRY_CLASS_NAME);
				long classPK = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				if (className.equals(JournalArticle.class.getName())) {
					JournalArticle article =
						JournalArticleLocalServiceUtil.fetchLatestArticle(
							classPK, WorkflowConstants.STATUS_ANY, false);

					results.add(article);
				}
				else if (className.equals(JournalFolder.class.getName())) {
					results.add(
						JournalFolderLocalServiceUtil.getFolder(classPK));
				}
			}

			articleAndFolderSearchContainer.setResults(results);
		}
		else {
			int total = JournalFolderServiceUtil.getFoldersAndArticlesCount(
				_themeDisplay.getScopeGroupId(), 0, _getFolderId(),
				_infoItemItemSelectorCriterion.getStatus());

			articleAndFolderSearchContainer.setTotal(total);

			OrderByComparator<Object> folderOrderByComparator = null;

			boolean orderByAsc = false;

			if (Objects.equals(_getOrderByType(), "asc")) {
				orderByAsc = true;
			}

			if (Objects.equals(_getOrderByCol(), "id")) {
				folderOrderByComparator = new FolderArticleArticleIdComparator(
					orderByAsc);
			}
			else if (Objects.equals(_getOrderByCol(), "modified-date")) {
				folderOrderByComparator =
					new FolderArticleModifiedDateComparator(orderByAsc);
			}
			else if (Objects.equals(_getOrderByCol(), "title")) {
				folderOrderByComparator = new FolderArticleTitleComparator(
					orderByAsc);
			}

			List<Object> results =
				JournalFolderServiceUtil.getFoldersAndArticles(
					_themeDisplay.getScopeGroupId(), 0, _getFolderId(),
					_infoItemItemSelectorCriterion.getStatus(),
					_themeDisplay.getLocale(),
					articleAndFolderSearchContainer.getStart(),
					articleAndFolderSearchContainer.getEnd(),
					folderOrderByComparator);

			articleAndFolderSearchContainer.setResults(results);
		}

		_articleSearchContainer = articleAndFolderSearchContainer;

		return _articleSearchContainer;
	}

	public boolean isSearch() {
		if (_isEverywhereScopeFilter()) {
			return true;
		}

		return _search;
	}

	public boolean isSearchEverywhere() {
		if (_searchEverywhere != null) {
			return _searchEverywhere;
		}

		if (Objects.equals(
				ParamUtil.getString(_httpServletRequest, "scope"),
				"everywhere")) {

			_searchEverywhere = true;
		}
		else {
			_searchEverywhere = false;
		}

		return _searchEverywhere;
	}

	public boolean showArticleId() {
		if (!_journalWebConfiguration.journalArticleForceAutogenerateId() ||
			_journalWebConfiguration.journalArticleShowId()) {

			return true;
		}

		return false;
	}

	protected SearchContext buildSearchContext(
			List<Long> folderIds, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(false);
		searchContext.setAttribute("head", Boolean.TRUE);
		searchContext.setAttribute("latest", Boolean.TRUE);

		LinkedHashMap<String, Object> params =
			LinkedHashMapBuilder.<String, Object>put(
				"expandoAttributes", getKeywords()
			).put(
				"keywords", getKeywords()
			).build();

		searchContext.setAttribute("params", params);

		searchContext.setAttribute("showNonindexable", Boolean.TRUE);
		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.ARTICLE_ID, getKeywords()
			).put(
				Field.CLASS_NAME_ID,
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT
			).put(
				Field.CONTENT, getKeywords()
			).put(
				Field.DESCRIPTION, getKeywords()
			).put(
				Field.STATUS, _infoItemItemSelectorCriterion.getStatus()
			).put(
				Field.TITLE, getKeywords()
			).put(
				"ddmStructureKey", getDDMStructureKey()
			).put(
				"params", params
			).build());

		searchContext.setCompanyId(_themeDisplay.getCompanyId());
		searchContext.setEnd(end);
		searchContext.setFolderIds(folderIds);
		searchContext.setGroupIds(_getGroupIds());
		searchContext.setIncludeInternalAssetCategories(true);
		searchContext.setKeywords(getKeywords());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		return searchContext;
	}

	private JournalFolder _getFolder() {
		if (_folder != null) {
			return _folder;
		}

		long folderId = ParamUtil.getLong(_httpServletRequest, "folderId");

		_folder = JournalFolderLocalServiceUtil.fetchFolder(folderId);

		return _folder;
	}

	private long _getFolderId() {
		if (_folderId != null) {
			return _folderId;
		}

		_folderId = BeanParamUtil.getLong(
			_getFolder(), _httpServletRequest, "folderId",
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _folderId;
	}

	private long[] _getGroupIds() throws PortalException {
		if (_isEverywhereScopeFilter()) {
			return SiteConnectedGroupUtil.
				getCurrentAndAncestorSiteAndDepotGroupIds(
					_themeDisplay.getScopeGroupId());
		}

		return PortalUtil.getCurrentAndAncestorSiteGroupIds(
			_themeDisplay.getScopeGroupId());
	}

	private BreadcrumbEntry _getHomeBreadcrumb() throws Exception {
		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(_themeDisplay.getSiteGroupName());

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter(
			"folderId",
			String.valueOf(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID));

		breadcrumbEntry.setURL(portletURL.toString());

		return breadcrumbEntry;
	}

	private String _getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		String defaultOrderByCol = "modified-date";

		if (isSearch()) {
			defaultOrderByCol = "relevance";
		}

		_orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol", defaultOrderByCol);

		return _orderByCol;
	}

	private String _getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		if (Objects.equals(_getOrderByCol(), "relevance")) {
			return "desc";
		}

		_orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType", "asc");

		return _orderByType;
	}

	private BreadcrumbEntry _getSiteBreadcrumb() throws Exception {
		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(
			LanguageUtil.get(_httpServletRequest, "sites-and-libraries"));

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("groupType", "site");
		portletURL.setParameter("showGroupSelector", Boolean.TRUE.toString());

		breadcrumbEntry.setURL(portletURL.toString());

		return breadcrumbEntry;
	}

	private String _getTitle(Locale locale) {
		return _journalArticleItemSelectorView.getTitle(locale);
	}

	private boolean _isEverywhereScopeFilter() {
		if (Objects.equals(
				ParamUtil.getString(_httpServletRequest, "scope"),
				"everywhere")) {

			return true;
		}

		return false;
	}

	private SearchContainer<?> _articleSearchContainer;
	private String _ddmStructureKey;
	private String _displayStyle;
	private JournalFolder _folder;
	private Long _folderId;
	private final HttpServletRequest _httpServletRequest;
	private final InfoItemItemSelectorCriterion _infoItemItemSelectorCriterion;
	private final String _itemSelectedEventName;
	private final JournalArticleItemSelectorView
		_journalArticleItemSelectorView;
	private final JournalWebConfiguration _journalWebConfiguration;
	private String _keywords;
	private String _orderByCol;
	private String _orderByType;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;
	private final PortletURL _portletURL;
	private final boolean _search;
	private Boolean _searchEverywhere;
	private final ThemeDisplay _themeDisplay;

}