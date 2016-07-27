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

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.frontend.taglib.servlet.taglib.ManagementBarFilterItem;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.comparator.FolderArticleDisplayDateComparator;
import com.liferay.journal.util.comparator.FolderArticleModifiedDateComparator;
import com.liferay.journal.web.configuration.JournalWebConfiguration;
import com.liferay.journal.web.internal.portlet.action.ActionUtil;
import com.liferay.journal.web.internal.search.ArticleSearch;
import com.liferay.journal.web.internal.search.EntriesChecker;
import com.liferay.journal.web.internal.search.EntriesMover;
import com.liferay.journal.web.internal.search.JournalSearcher;
import com.liferay.journal.web.util.JournalPortletUtil;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalDisplayContext {

	public JournalDisplayContext(
		HttpServletRequest request, LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		PortletPreferences portletPreferences) {

		_request = request;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_portletPreferences = portletPreferences;

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_request);
	}

	public String[] getAddMenuFavItems() throws PortalException {
		if (_addMenuFavItems != null) {
			return _addMenuFavItems;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		String key = JournalPortletUtil.getAddMenuFavItemKey(
			_liferayPortletRequest, _liferayPortletResponse);

		_addMenuFavItems = portalPreferences.getValues(
			JournalPortletKeys.JOURNAL, key, new String[0]);

		return _addMenuFavItems;
	}

	public int getAddMenuFavItemsLength() throws PortalException {
		String[] addMenuFavItems = getAddMenuFavItems();

		return addMenuFavItems.length;
	}

	public JournalArticle getArticle() throws PortalException {
		if (_article != null) {
			return _article;
		}

		_article = ActionUtil.getArticle(_request);

		return _article;
	}

	public SearchContainer<MBMessage> getCommentsSearchContainer()
		throws PortalException {

		SearchContainer<MBMessage> searchContainer = new SearchContainer(
			_liferayPortletRequest, _liferayPortletResponse.createRenderURL(),
			null, null);

		SearchContext searchContext = SearchContextFactory.getInstance(
			_liferayPortletRequest.getHttpServletRequest());

		searchContext.setAttribute(
			Field.CLASS_NAME_ID,
			PortalUtil.getClassNameId(JournalArticle.class));

		searchContext.setAttribute("discussion", true);

		List<MBMessage> mbMessages = new ArrayList<>();

		Indexer indexer = IndexerRegistryUtil.getIndexer(MBMessage.class);

		Hits hits = indexer.search(searchContext);

		for (Document document : hits.getDocs()) {
			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			MBMessage mbMessage = MBMessageLocalServiceUtil.fetchMBMessage(
				entryClassPK);

			mbMessages.add(mbMessage);
		}

		searchContainer.setResults(mbMessages);

		searchContainer.setTotal(hits.getLength());

		return searchContainer;
	}

	public int getCommentsTotal() throws PortalException {
		SearchContainer<MBMessage> commentsSearchContainer =
			getCommentsSearchContainer();

		return commentsSearchContainer.getTotal();
	}

	public DDMFormValues getDDMFormValues(DDMStructure ddmStructure)
		throws PortalException {

		if (_ddmFormValues != null) {
			return _ddmFormValues;
		}

		JournalArticle article = getArticle();

		if (article == null) {
			return _ddmFormValues;
		}

		String content = article.getContent();

		if (Validator.isNull(content)) {
			return _ddmFormValues;
		}

		JournalConverter journalConverter = getJournalConverter();

		Fields fields = journalConverter.getDDMFields(ddmStructure, content);

		if (fields == null) {
			return _ddmFormValues;
		}

		_ddmFormValues = journalConverter.getDDMFormValues(
			ddmStructure, fields);

		return _ddmFormValues;
	}

	public String getDDMStructureKey() {
		if (_ddmStructureKey != null) {
			return _ddmStructureKey;
		}

		_ddmStructureKey = ParamUtil.getString(_request, "ddmStructureKey");

		return _ddmStructureKey;
	}

	public String getDDMStructureName() throws PortalException {
		if (_ddmStructureName != null) {
			return _ddmStructureName;
		}

		_ddmStructureName = LanguageUtil.get(_request, "basic-web-content");

		if (Validator.isNull(getDDMStructureKey())) {
			return _ddmStructureName;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay) _request.getAttribute(
			WebKeys.THEME_DISPLAY);

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			themeDisplay.getSiteGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			getDDMStructureKey(), true);

		if (ddmStructure != null) {
			_ddmStructureName = ddmStructure.getName(themeDisplay.getLocale());
		}

		return _ddmStructureName;
	}

	public long getDDMStructurePrimaryKey() throws PortalException {
		String ddmStructureKey = getDDMStructureKey();

		if (Validator.isNull(ddmStructureKey)) {
			return 0;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			themeDisplay.getSiteGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			getDDMStructureKey(), true);

		if (ddmStructure == null) {
			return 0;
		}

		return ddmStructure.getPrimaryKey();
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		_ddmTemplateKey = ParamUtil.getString(_request, "ddmTemplateKey");

		return _ddmTemplateKey;
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = getDisplayStyle(_request, getDisplayViews());
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		if (_displayViews == null) {
			JournalWebConfiguration journalWebConfiguration =
				(JournalWebConfiguration)_request.getAttribute(
					JournalWebConfiguration.class.getName());

			_displayViews = StringUtil.split(
				PrefsParamUtil.getString(
					_portletPreferences, _request, "displayViews",
					StringUtil.merge(journalWebConfiguration.displayViews())));
		}

		return _displayViews;
	}

	public JournalFolder getFolder() throws PortalException {
		if (_folder != null) {
			return _folder;
		}

		_folder = (JournalFolder)_request.getAttribute(WebKeys.JOURNAL_FOLDER);

		if (_folder != null) {
			return _folder;
		}

		_folder = ActionUtil.getFolder(_request);

		return _folder;
	}

	public long getFolderId() throws PortalException {
		if (_folderId != null) {
			return _folderId;
		}

		JournalFolder folder = getFolder();

		_folderId = BeanParamUtil.getLong(
			folder, _request, "folderId",
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _folderId;
	}

	public String getFolderTitle() throws PortalException {
		JournalFolder folder = getFolder();

		if (folder != null) {
			return folder.getName();
		}

		return StringPool.BLANK;
	}

	public JournalConverter getJournalConverter() {
		return (JournalConverter)_request.getAttribute(
			JournalWebKeys.JOURNAL_CONVERTER);
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(_request, "keywords");

		return _keywords;
	}

	public List<ManagementBarFilterItem> getManagementBarStatusFilterItems()
		throws PortalException, PortletException {

		List<ManagementBarFilterItem> managementBarFilterItems =
			new ArrayList<>();

		managementBarFilterItems.add(
			getManagementBarFilterItem(WorkflowConstants.STATUS_ANY));
		managementBarFilterItems.add(
			getManagementBarFilterItem(WorkflowConstants.STATUS_DRAFT));

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		int workflowDefinitionLinksCount =
			WorkflowDefinitionLinkLocalServiceUtil.
				getWorkflowDefinitionLinksCount(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					JournalFolder.class.getName());

		if (workflowDefinitionLinksCount > 0) {
			managementBarFilterItems.add(
				getManagementBarFilterItem(WorkflowConstants.STATUS_PENDING));
			managementBarFilterItems.add(
				getManagementBarFilterItem(WorkflowConstants.STATUS_DENIED));
		}

		managementBarFilterItems.add(
			getManagementBarFilterItem(WorkflowConstants.STATUS_SCHEDULED));
		managementBarFilterItems.add(
			getManagementBarFilterItem(WorkflowConstants.STATUS_APPROVED));
		managementBarFilterItems.add(
			getManagementBarFilterItem(WorkflowConstants.STATUS_EXPIRED));

		return managementBarFilterItems;
	}

	public String getManagementBarStatusFilterValue() {
		return WorkflowConstants.getStatusLabel(getStatus());
	}

	public int getMaxAddMenuItems() {
		if (_maxAddMenuItems != null) {
			return _maxAddMenuItems;
		}

		JournalWebConfiguration journalWebConfiguration =
			(JournalWebConfiguration)_request.getAttribute(
				JournalWebConfiguration.class.getName());

		_maxAddMenuItems = journalWebConfiguration.maxAddMenuItems();

		return _maxAddMenuItems;
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_request, "navigation", "all");

		return _navigation;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_request, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			_orderByCol = _portalPreferences.getValue(
				JournalPortletKeys.JOURNAL, "order-by-col", "modified-date");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(_request, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					JournalPortletKeys.JOURNAL, "order-by-col", _orderByCol);
			}
		}

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType");

		if (Validator.isNull(_orderByType)) {
			_orderByType = _portalPreferences.getValue(
				JournalPortletKeys.JOURNAL, "order-by-type", "asc");
		}
		else {
			boolean saveOrderBy = ParamUtil.getBoolean(_request, "saveOrderBy");

			if (saveOrderBy) {
				_portalPreferences.setValue(
					JournalPortletKeys.JOURNAL, "order-by-type", _orderByType);
			}
		}

		return _orderByType;
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String navigation = ParamUtil.getString(_request, "navigation");

		if (Validator.isNotNull(navigation)) {
			portletURL.setParameter(
				"navigation", HtmlUtil.escapeJS(getNavigation()));
		}

		portletURL.setParameter("folderId", String.valueOf(getFolderId()));

		String ddmStructureKey = getDDMStructureKey();

		if (isNavigationStructure()) {
			portletURL.setParameter("ddmStructureKey", ddmStructureKey);
		}

		String status = ParamUtil.getString(_request, "status");

		if (Validator.isNotNull(status)) {
			portletURL.setParameter("status", String.valueOf(getStatus()));
		}

		String deltaEntry = ParamUtil.getString(_request, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String displayStyle = ParamUtil.getString(_request, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		String keywords = ParamUtil.getString(_request, "keywords");

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		if (!isShowEditActions()) {
			portletURL.setParameter(
				"showEditActions", String.valueOf(isShowEditActions()));
		}

		return portletURL;
	}

	public int getRestrictionType() throws PortalException {
		if (_restrictionType != null) {
			return _restrictionType;
		}

		JournalFolder folder = getFolder();

		if (folder != null) {
			_restrictionType = folder.getRestrictionType();
		}
		else {
			_restrictionType = JournalFolderConstants.RESTRICTION_TYPE_INHERIT;
		}

		return _restrictionType;
	}

	public ArticleSearch getSearchContainer() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		ArticleSearch articleSearchContainer = new ArticleSearch(
			_liferayPortletRequest, getPortletURL());

		if (!isSearch()) {
			articleSearchContainer.setEmptyResultsMessageCssClass(
				"taglib-empty-result-message-header-has-plus-btn");
		}
		else {
			articleSearchContainer.setSearch(true);
		}

		OrderByComparator<JournalArticle> orderByComparator =
			JournalPortletUtil.getArticleOrderByComparator(
				getOrderByCol(), getOrderByType());

		articleSearchContainer.setOrderByCol(getOrderByCol());
		articleSearchContainer.setOrderByComparator(orderByComparator);
		articleSearchContainer.setOrderByType(getOrderByType());

		EntriesChecker entriesChecker = new EntriesChecker(
			_liferayPortletRequest, _liferayPortletResponse);

		entriesChecker.setCssClass("entry-selector");
		entriesChecker.setRememberCheckBoxStateURLRegex(
			"^(?!.*" + _liferayPortletResponse.getNamespace() +
				"redirect).*(folderId=" + getFolderId() + ")");

		articleSearchContainer.setRowChecker(entriesChecker);

		EntriesMover entriesMover = new EntriesMover(
			themeDisplay.getScopeGroupId());

		articleSearchContainer.setRowMover(entriesMover);

		if (isNavigationMine() || isNavigationRecent()) {
			boolean includeOwner = true;

			if (isNavigationMine()) {
				includeOwner = false;
			}

			if (isNavigationRecent()) {
				articleSearchContainer.setOrderByCol("create-date");
				articleSearchContainer.setOrderByType(getOrderByType());
			}

			int total = JournalArticleServiceUtil.getGroupArticlesCount(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				getFolderId(), getStatus(), includeOwner);

			articleSearchContainer.setTotal(total);

			List results = JournalArticleServiceUtil.getGroupArticles(
				themeDisplay.getScopeGroupId(), themeDisplay.getUserId(),
				getFolderId(), getStatus(), includeOwner,
				articleSearchContainer.getStart(),
				articleSearchContainer.getEnd(),
				articleSearchContainer.getOrderByComparator());

			articleSearchContainer.setResults(results);
		}
		else if (Validator.isNotNull(getDDMStructureKey())) {
			int total = JournalArticleServiceUtil.getArticlesCountByStructureId(
				themeDisplay.getScopeGroupId(), getDDMStructureKey(),
				getStatus());

			articleSearchContainer.setTotal(total);

			List results = JournalArticleServiceUtil.getArticlesByStructureId(
				themeDisplay.getScopeGroupId(), getDDMStructureKey(),
				getStatus(), articleSearchContainer.getStart(),
				articleSearchContainer.getEnd(),
				articleSearchContainer.getOrderByComparator());

			articleSearchContainer.setResults(results);
		}
		else if (Validator.isNotNull(getDDMTemplateKey())) {
			List<Long> folderIds = new ArrayList<>(1);

			if (getFolderId() !=
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				folderIds.add(getFolderId());
			}

			int total = JournalArticleServiceUtil.searchCount(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				folderIds, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				getKeywords(), -1.0, getDDMStructureKey(), getDDMTemplateKey(),
				null, null, getStatus(), null);

			articleSearchContainer.setTotal(total);

			List results = JournalArticleServiceUtil.search(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				folderIds, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				getKeywords(), -1.0, getDDMStructureKey(), getDDMTemplateKey(),
				null, null, getStatus(), null,
				articleSearchContainer.getStart(),
				articleSearchContainer.getEnd(),
				articleSearchContainer.getOrderByComparator());

			articleSearchContainer.setResults(results);
		}
		else if (isSearch()) {
			List<Long> folderIds = new ArrayList<>(1);

			if (getFolderId() !=
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				folderIds.add(getFolderId());
			}

			JournalWebConfiguration journalWebConfiguration =
				(JournalWebConfiguration)_request.getAttribute(
					JournalWebConfiguration.class.getName());

			if (journalWebConfiguration.journalArticlesSearchWithIndex()) {
				boolean orderByAsc = false;

				if (Objects.equals(getOrderByType(), "asc")) {
					orderByAsc = true;
				}

				Sort sort = null;

				if (Objects.equals(getOrderByCol(), "display-date")) {
					sort = new Sort("displayDate", Sort.LONG_TYPE, orderByAsc);
				}
				else if (Objects.equals(getOrderByCol(), "modified-date")) {
					sort = new Sort(
						Field.MODIFIED_DATE, Sort.LONG_TYPE, orderByAsc);
				}

				LinkedHashMap<String, Object> params = new LinkedHashMap<>();

				params.put("expandoAttributes", getKeywords());

				Indexer indexer = JournalSearcher.getInstance();

				SearchContext searchContext = buildSearchContext(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					folderIds, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
					getDDMStructureKey(), getDDMTemplateKey(), getKeywords(),
					params, articleSearchContainer.getStart(),
					articleSearchContainer.getEnd(), sort);

				Hits hits = indexer.search(searchContext);

				int total = hits.getLength();

				articleSearchContainer.setTotal(total);

				List results = new ArrayList<>();

				List<SearchResult> searchResults =
					SearchResultUtil.getSearchResults(
						hits, searchContext.getLocale(), _liferayPortletRequest,
						_liferayPortletResponse);

				for (int i = 0; i < searchResults.size(); i++) {
					SearchResult searchResult = searchResults.get(i);

					Summary summary = searchResult.getSummary();

					summary.setQueryTerms(hits.getQueryTerms());

					JournalArticle article = null;
					JournalFolder folder = null;

					String className = searchResult.getClassName();

					if (className.equals(JournalArticle.class.getName())) {
						article =
							JournalArticleLocalServiceUtil.fetchLatestArticle(
								searchResult.getClassPK(),
								WorkflowConstants.STATUS_ANY, false);

						results.add(article);
					}
					else if (className.equals(JournalFolder.class.getName())) {
						folder = JournalFolderLocalServiceUtil.getFolder(
							searchResult.getClassPK());

						results.add(folder);
					}
				}

				articleSearchContainer.setResults(results);
			}
			else {
				int total = JournalArticleServiceUtil.searchCount(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					folderIds, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
					getKeywords(), -1.0, getDDMStructureKey(),
					getDDMTemplateKey(), null, null, getStatus(), null);

				articleSearchContainer.setTotal(total);

				List results = JournalArticleServiceUtil.search(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					folderIds, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
					getKeywords(), -1.0, getDDMStructureKey(),
					getDDMTemplateKey(), null, null, getStatus(), null,
					articleSearchContainer.getStart(),
					articleSearchContainer.getEnd(),
					articleSearchContainer.getOrderByComparator());

				articleSearchContainer.setResults(results);
			}
		}
		else {
			int total = JournalFolderServiceUtil.getFoldersAndArticlesCount(
				themeDisplay.getScopeGroupId(), 0, getFolderId(), getStatus());

			articleSearchContainer.setTotal(total);

			OrderByComparator<Object> folderOrderByComparator = null;

			boolean orderByAsc = false;

			if (Objects.equals(getOrderByType(), "asc")) {
				orderByAsc = true;
			}

			if (Objects.equals(getOrderByCol(), "display-date")) {
				folderOrderByComparator =
					new FolderArticleDisplayDateComparator(orderByAsc);
			}
			else if (Objects.equals(getOrderByCol(), "modified-date")) {
				folderOrderByComparator =
					new FolderArticleModifiedDateComparator(orderByAsc);
			}

			List results = JournalFolderServiceUtil.getFoldersAndArticles(
				themeDisplay.getScopeGroupId(), 0, getFolderId(), getStatus(),
				articleSearchContainer.getStart(),
				articleSearchContainer.getEnd(), folderOrderByComparator);

			articleSearchContainer.setResults(results);
		}

		return articleSearchContainer;
	}

	public int getStatus() {
		if (_status != null) {
			return _status;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		int defaultStatus = WorkflowConstants.STATUS_APPROVED;

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (permissionChecker.isContentReviewer(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId()) ||
			isNavigationMine()) {

			defaultStatus = WorkflowConstants.STATUS_ANY;
		}

		_status = ParamUtil.getInteger(_request, "status", defaultStatus);

		return _status;
	}

	public int getTotal() throws PortalException {
		ArticleSearch articleSearch = getSearchContainer();

		return articleSearch.getTotal();
	}

	public boolean hasCommentsResults() throws PortalException {
		if (getCommentsTotal() > 0) {
			return true;
		}

		return false;
	}

	public boolean hasResults() throws PortalException {
		if (getTotal() > 0) {
			return true;
		}

		return false;
	}

	public boolean isDisabledManagementBar() throws PortalException {
		if (hasResults()) {
			return false;
		}

		if (isSearch()) {
			return false;
		}

		if (!isNavigationHome() ||
			(getStatus() != WorkflowConstants.STATUS_ANY)) {

			return false;
		}

		return true;
	}

	public boolean isNavigationHome() {
		if (Objects.equals(getNavigation(), "all")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationMine() {
		if (Objects.equals(getNavigation(), "mine")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationRecent() {
		if (Objects.equals(getNavigation(), "recent")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationStructure() {
		if (Objects.equals(getNavigation(), "structure")) {
			return true;
		}

		return false;
	}

	public boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	public boolean isShowBreadcrumb() throws PortalException {
		if (isNavigationStructure()) {
			return false;
		}

		if (isNavigationRecent()) {
			return false;
		}

		if (isNavigationMine()) {
			return false;
		}

		if (isSearch()) {
			return false;
		}

		if (!hasResults()) {
			return false;
		}

		return true;
	}

	public boolean isShowEditActions() {
		if (_showEditActions != null) {
			return _showEditActions;
		}

		_showEditActions = ParamUtil.getBoolean(
			_request, "showEditActions", true);

		return _showEditActions;
	}

	public boolean isShowInfoPanel() {
		if (Validator.isNotNull(getDDMStructureKey())) {
			return false;
		}

		if (isNavigationMine()) {
			return false;
		}

		if (isNavigationRecent()) {
			return false;
		}

		if (isSearch()) {
			return false;
		}

		return true;
	}

	public boolean isShowSearch() throws PortalException {
		if (hasResults()) {
			return true;
		}

		if (isSearch()) {
			return true;
		}

		return false;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, List<java.lang.Long> folderIds,
		long classNameId, String ddmStructureKey, String ddmTemplateKey,
		String keywords, LinkedHashMap<String, Object> params, int start,
		int end, Sort sort) {

		String articleId = null;
		String title = null;
		String description = null;
		String content = null;
		boolean andOperator = false;

		if (Validator.isNotNull(keywords)) {
			articleId = keywords;
			title = keywords;
			description = keywords;
			content = keywords;
		}
		else {
			andOperator = true;
		}

		if (params != null) {
			params.put("keywords", keywords);
		}

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(andOperator);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.ARTICLE_ID, articleId);
		attributes.put(Field.CLASS_NAME_ID, classNameId);
		attributes.put(Field.CONTENT, content);
		attributes.put(Field.DESCRIPTION, description);
		attributes.put(Field.STATUS, getStatus());
		attributes.put(Field.TITLE, title);
		attributes.put("ddmStructureKey", ddmStructureKey);
		attributes.put("ddmTemplateKey", ddmTemplateKey);
		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setFolderIds(folderIds);
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setIncludeDiscussions(
			GetterUtil.getBoolean(params.get("includeDiscussions"), true));

		if (params != null) {
			keywords = (String)params.remove("keywords");

			if (Validator.isNotNull(keywords)) {
				searchContext.setKeywords(keywords);
			}
		}

		searchContext.setAttribute("head", Boolean.FALSE.toString());
		searchContext.setAttribute("params", params);
		searchContext.setEnd(end);
		searchContext.setFolderIds(folderIds);
		searchContext.setStart(start);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		return searchContext;
	}

	protected String getDisplayStyle(
		HttpServletRequest request, String[] displayViews) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(request);

		String displayStyle = ParamUtil.getString(request, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			JournalWebConfiguration journalWebConfiguration =
				(JournalWebConfiguration)_request.getAttribute(
					JournalWebConfiguration.class.getName());

			displayStyle = portalPreferences.getValue(
				JournalPortletKeys.JOURNAL, "display-style",
				journalWebConfiguration.defaultDisplayView());
		}
		else {
			if (ArrayUtil.contains(displayViews, displayStyle)) {
				portalPreferences.setValue(
					JournalPortletKeys.JOURNAL, "display-style", displayStyle);

				request.setAttribute(
					WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
			}
		}

		if (!ArrayUtil.contains(displayViews, displayStyle)) {
			displayStyle = displayViews[0];
		}

		return displayStyle;
	}

	protected ManagementBarFilterItem getManagementBarFilterItem(int status)
		throws PortalException, PortletException {

		boolean active = false;

		if (status == getStatus()) {
			active = true;
		}

		PortletURL portletURL = PortletURLUtil.clone(
			getPortletURL(), _liferayPortletResponse);

		portletURL.setParameter("status", String.valueOf(status));

		return new ManagementBarFilterItem(
			active, WorkflowConstants.getStatusLabel(status),
			portletURL.toString());
	}

	private String[] _addMenuFavItems;
	private JournalArticle _article;
	private DDMFormValues _ddmFormValues;
	private String _ddmStructureKey;
	private String _ddmStructureName;
	private String _ddmTemplateKey;
	private String _displayStyle;
	private String[] _displayViews;
	private JournalFolder _folder;
	private Long _folderId;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private Integer _maxAddMenuItems;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final PortalPreferences _portalPreferences;
	private final PortletPreferences _portletPreferences;
	private final HttpServletRequest _request;
	private Integer _restrictionType;
	private Boolean _showEditActions;
	private Integer _status;

}