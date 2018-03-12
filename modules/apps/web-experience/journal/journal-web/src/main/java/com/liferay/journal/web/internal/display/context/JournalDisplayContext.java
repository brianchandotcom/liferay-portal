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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.frontend.taglib.servlet.taglib.ManagementBarFilterItem;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.constants.JournalWebKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.util.JournalConverter;
import com.liferay.journal.util.comparator.FolderArticleArticleIdComparator;
import com.liferay.journal.util.comparator.FolderArticleDisplayDateComparator;
import com.liferay.journal.util.comparator.FolderArticleModifiedDateComparator;
import com.liferay.journal.util.comparator.FolderArticleTitleComparator;
import com.liferay.journal.web.configuration.JournalWebConfiguration;
import com.liferay.journal.web.internal.portlet.action.ActionUtil;
import com.liferay.journal.web.internal.search.EntriesChecker;
import com.liferay.journal.web.internal.search.EntriesMover;
import com.liferay.journal.web.internal.search.JournalSearcher;
import com.liferay.journal.web.util.JournalPortletUtil;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.trash.TrashHelper;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
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
		PortletPreferences portletPreferences, TrashHelper trashHelper) {

		_request = request;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_portletPreferences = portletPreferences;
		_trashHelper = trashHelper;

		_journalWebConfiguration =
			(JournalWebConfiguration)_request.getAttribute(
				JournalWebConfiguration.class.getName());
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

	public JournalArticleDisplay getArticleDisplay() throws Exception {
		if (_articleDisplay != null) {
			return _articleDisplay;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(_request, "groupId");
		String articleId = ParamUtil.getString(_request, "articleId");
		double version = ParamUtil.getDouble(_request, "version");
		int page = ParamUtil.getInteger(_request, "page");

		JournalArticle article = JournalArticleLocalServiceUtil.fetchArticle(
			groupId, articleId, version);

		if (article == null) {
			return _articleDisplay;
		}

		_articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(
			article, null, null, themeDisplay.getLanguageId(), page,
			new PortletRequestModel(
				_liferayPortletRequest, _liferayPortletResponse),
			themeDisplay);

		return _articleDisplay;
	}

	public List<Locale> getAvailableArticleLocales() throws PortalException {
		JournalArticle article = getArticle();

		if (article == null) {
			return Collections.emptyList();
		}

		List<Locale> availableLocales = new ArrayList<>();

		for (String languageId : article.getAvailableLanguageIds()) {
			availableLocales.add(LocaleUtil.fromLanguageId(languageId));
		}

		return availableLocales;
	}

	public String[] getCharactersBlacklist() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JournalServiceConfiguration journalServiceConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				JournalServiceConfiguration.class, themeDisplay.getCompanyId());

		return journalServiceConfiguration.charactersblacklist();
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

		searchContext.setAttribute("discussion", Boolean.TRUE);

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

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
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

	public List<DDMStructure> getDDMStructures() throws PortalException {
		Integer restrictionType = getRestrictionType();

		return getDDMStructures(restrictionType);
	}

	public List<DDMStructure> getDDMStructures(Integer restrictionType)
		throws PortalException {

		if (_ddmStructures != null) {
			return _ddmStructures;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (restrictionType == null) {
			restrictionType = getRestrictionType();
		}

		_ddmStructures = JournalFolderServiceUtil.getDDMStructures(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(
				themeDisplay.getScopeGroupId()),
			getFolderId(), restrictionType);

		Locale locale = themeDisplay.getLocale();

		if (_journalWebConfiguration.journalBrowseByStructuresSortedByName()) {
			_ddmStructures.sort(
				(ddmStructure1, ddmStructure2) -> {
					String name1 = ddmStructure1.getName(locale);
					String name2 = ddmStructure2.getName(locale);

					return name1.compareTo(name2);
				});
		}

		return _ddmStructures;
	}

	public String getDDMTemplateKey() {
		if (_ddmTemplateKey != null) {
			return _ddmTemplateKey;
		}

		_ddmTemplateKey = ParamUtil.getString(_request, "ddmTemplateKey");

		return _ddmTemplateKey;
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		String[] displayViews = getDisplayViews();

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_request);

		_displayStyle = ParamUtil.getString(_request, "displayStyle");

		if (Validator.isNull(_displayStyle)) {
			_displayStyle = portalPreferences.getValue(
				JournalPortletKeys.JOURNAL, "display-style",
				_journalWebConfiguration.defaultDisplayView());
		}
		else if (ArrayUtil.contains(displayViews, _displayStyle)) {
			portalPreferences.setValue(
				JournalPortletKeys.JOURNAL, "display-style", _displayStyle);

			_request.setAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
		}

		if (!ArrayUtil.contains(displayViews, _displayStyle)) {
			_displayStyle = displayViews[0];
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		if (_displayViews == null) {
			_displayViews = StringUtil.split(
				PrefsParamUtil.getString(
					_portletPreferences, _request, "displayViews",
					StringUtil.merge(_journalWebConfiguration.displayViews())));
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

	public JSONArray getFoldersJSONArray() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = _getFoldersJSONArray(
			themeDisplay.getScopeGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("children", jsonArray);
		jsonObject.put("icon", "folder");
		jsonObject.put("id", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		jsonObject.put(
			"name", LanguageUtil.get(themeDisplay.getLocale(), "home"));

		JSONArray rootJSONArray = JSONFactoryUtil.createJSONArray();

		rootJSONArray.put(jsonObject);

		return rootJSONArray;
	}

	public String getFolderTitle() throws PortalException {
		JournalFolder folder = getFolder();

		if (folder != null) {
			return folder.getName();
		}

		return StringPool.BLANK;
	}

	public String getFriendlyURLBase() {
		StringBundler sb = new StringBundler(4);

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		sb.append(themeDisplay.getPortalURL());

		Group group = themeDisplay.getScopeGroup();

		sb.append(group.getPathFriendlyURL(false, themeDisplay));
		sb.append(group.getFriendlyURL());

		sb.append(JournalArticleConstants.CANONICAL_URL_SEPARATOR);

		return sb.toString();
	}

	public List<NavigationItem> getInfoPanelNavigationItems() {
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(
							LanguageUtil.get(_request, "details"));
					});
			}
		};
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

	public String getLayoutBreadcrumb(Layout layout) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Locale locale = themeDisplay.getLocale();

		List<Layout> ancestors = layout.getAncestors();

		StringBundler sb = new StringBundler(4 * ancestors.size() + 5);

		if (layout.isPrivateLayout()) {
			sb.append(LanguageUtil.get(_request, "private-pages"));
		}
		else {
			sb.append(LanguageUtil.get(_request, "public-pages"));
		}

		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);

		Collections.reverse(ancestors);

		for (Layout ancestor : ancestors) {
			sb.append(HtmlUtil.escape(ancestor.getName(locale)));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
		}

		sb.append(HtmlUtil.escape(layout.getName(locale)));

		return sb.toString();
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

		_maxAddMenuItems = _journalWebConfiguration.maxAddMenuItems();

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

		if (isNavigationRecent()) {
			return "desc";
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

	public String[] getOrderColumns() {
		String[] orderColumns = {"display-date", "modified-date", "title"};

		if (!_journalWebConfiguration.journalArticleForceAutogenerateId()) {
			orderColumns = ArrayUtil.append(orderColumns, "id");
		}

		return orderColumns;
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

		String delta = ParamUtil.getString(_request, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
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

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		if (!isShowEditActions()) {
			portletURL.setParameter(
				"showEditActions", String.valueOf(isShowEditActions()));
		}

		String tabs1 = getTabs1();

		if (Validator.isNotNull(tabs1)) {
			portletURL.setParameter("tabs1", tabs1);
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

	public SearchContainer getSearchContainer(boolean showVersions)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		SearchContainer articleSearchContainer = new SearchContainer(
			_liferayPortletRequest, getPortletURL(), null, null);

		if (isSearch()) {
			articleSearchContainer.setSearch(true);
		}

		OrderByComparator<JournalArticle> orderByComparator =
			JournalPortletUtil.getArticleOrderByComparator(
				getOrderByCol(), getOrderByType());

		articleSearchContainer.setOrderByCol(getOrderByCol());
		articleSearchContainer.setOrderByComparator(orderByComparator);
		articleSearchContainer.setOrderByType(getOrderByType());

		if (!showVersions) {
			EntriesChecker entriesChecker = new EntriesChecker(
				_liferayPortletRequest, _liferayPortletResponse, _trashHelper);

			entriesChecker.setCssClass("entry-selector");
			entriesChecker.setRememberCheckBoxStateURLRegex(
				StringBundler.concat(
					"^(?!.*", _liferayPortletResponse.getNamespace(),
					"redirect).*(folderId=", String.valueOf(getFolderId()),
					")"));

			articleSearchContainer.setRowChecker(entriesChecker);

			EntriesMover entriesMover = new EntriesMover(
				_trashHelper.isTrashEnabled(themeDisplay.getScopeGroupId()));

			articleSearchContainer.setRowMover(entriesMover);
		}

		if (isNavigationMine() || isNavigationRecent()) {
			boolean includeOwner = true;

			if (isNavigationMine()) {
				includeOwner = false;
			}

			if (isNavigationRecent()) {
				articleSearchContainer.setOrderByCol("modified-date");
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

			if (_journalWebConfiguration.journalArticlesSearchWithIndex()) {
				boolean orderByAsc = false;

				if (Objects.equals(getOrderByType(), "asc")) {
					orderByAsc = true;
				}

				Sort sort = null;

				if (Objects.equals(getOrderByCol(), "display-date")) {
					sort = new Sort("displayDate", Sort.LONG_TYPE, orderByAsc);
				}
				else if (Objects.equals(getOrderByCol(), "id")) {
					sort = new Sort(
						DocumentImpl.getSortableFieldName(Field.ARTICLE_ID),
						Sort.STRING_TYPE, !orderByAsc);
				}
				else if (Objects.equals(getOrderByCol(), "modified-date")) {
					sort = new Sort(
						Field.MODIFIED_DATE, Sort.LONG_TYPE, orderByAsc);
				}
				else if (Objects.equals(getOrderByCol(), "title")) {
					sort = new Sort("title", Sort.STRING_TYPE, !orderByAsc);
				}

				LinkedHashMap<String, Object> params = new LinkedHashMap<>();

				params.put("expandoAttributes", getKeywords());

				Indexer indexer = null;

				if (!showVersions) {
					indexer = JournalSearcher.getInstance();
				}
				else {
					indexer = IndexerRegistryUtil.getIndexer(
						JournalArticle.class);
				}

				SearchContext searchContext = buildSearchContext(
					themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
					folderIds, JournalArticleConstants.CLASSNAME_ID_DEFAULT,
					getDDMStructureKey(), getDDMTemplateKey(), getKeywords(),
					params, articleSearchContainer.getStart(),
					articleSearchContainer.getEnd(), sort, showVersions);

				Hits hits = indexer.search(searchContext);

				int total = hits.getLength();

				articleSearchContainer.setTotal(total);

				List results = new ArrayList<>();

				Document[] documents = hits.getDocs();

				for (Document document : documents) {
					JournalArticle article = null;
					JournalFolder folder = null;

					String className = document.get(Field.ENTRY_CLASS_NAME);
					long classPK = GetterUtil.getLong(
						document.get(Field.ENTRY_CLASS_PK));

					if (className.equals(JournalArticle.class.getName())) {
						if (!showVersions) {
							article =
								JournalArticleLocalServiceUtil.
									fetchLatestArticle(
										classPK, WorkflowConstants.STATUS_ANY,
										false);
						}
						else {
							String articleId = document.get(Field.ARTICLE_ID);
							long groupId = GetterUtil.getLong(
								document.get(Field.GROUP_ID));
							double version = GetterUtil.getDouble(
								document.get(Field.VERSION));

							article =
								JournalArticleLocalServiceUtil.fetchArticle(
									groupId, articleId, version);
						}

						results.add(article);
					}
					else if (className.equals(JournalFolder.class.getName())) {
						folder = JournalFolderLocalServiceUtil.getFolder(
							classPK);

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
			else if (Objects.equals(getOrderByCol(), "id")) {
				folderOrderByComparator = new FolderArticleArticleIdComparator(
					orderByAsc);
			}
			else if (Objects.equals(getOrderByCol(), "modified-date")) {
				folderOrderByComparator =
					new FolderArticleModifiedDateComparator(orderByAsc);
			}
			else if (Objects.equals(getOrderByCol(), "title")) {
				folderOrderByComparator = new FolderArticleTitleComparator(
					orderByAsc);
			}

			List results = JournalFolderServiceUtil.getFoldersAndArticles(
				themeDisplay.getScopeGroupId(), 0, getFolderId(), getStatus(),
				themeDisplay.getLocale(), articleSearchContainer.getStart(),
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

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(_request, "tabs1");

		return _tabs1;
	}

	public int getTotal() throws PortalException {
		SearchContainer articleSearch = getSearchContainer(false);

		return articleSearch.getTotal();
	}

	public int getVersionsTotal() throws PortalException {
		SearchContainer articleSearch = getSearchContainer(true);

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

	public boolean hasVersionsResults() throws PortalException {
		if (getVersionsTotal() > 0) {
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
		long companyId, long groupId, List<Long> folderIds, long classNameId,
		String ddmStructureKey, String ddmTemplateKey, String keywords,
		LinkedHashMap<String, Object> params, int start, int end, Sort sort,
		boolean showVersions) {

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

		searchContext.setAttribute("head", !showVersions);
		searchContext.setAttribute("latest", !showVersions);
		searchContext.setAttribute("params", params);

		if (!showVersions) {
			searchContext.setAttribute("showNonindexable", Boolean.TRUE);
		}

		searchContext.setEnd(end);
		searchContext.setFolderIds(folderIds);
		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		return searchContext;
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

	private JSONArray _getFoldersJSONArray(long groupId, long folderId)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<JournalFolder> folders = JournalFolderLocalServiceUtil.getFolders(
			groupId, folderId);

		for (JournalFolder folder : folders) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			JSONArray childrenJSONArray = _getFoldersJSONArray(
				groupId, folder.getFolderId());

			if (childrenJSONArray.length() > 0) {
				jsonObject.put("children", childrenJSONArray);
			}

			jsonObject.put("icon", "folder");
			jsonObject.put("id", folder.getFolderId());
			jsonObject.put("name", folder.getName());

			if (folder.getFolderId() == getFolderId()) {
				jsonObject.put("selected", true);
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalDisplayContext.class);

	private String[] _addMenuFavItems;
	private JournalArticle _article;
	private JournalArticleDisplay _articleDisplay;
	private DDMFormValues _ddmFormValues;
	private String _ddmStructureKey;
	private String _ddmStructureName;
	private List<DDMStructure> _ddmStructures;
	private String _ddmTemplateKey;
	private String _displayStyle;
	private String[] _displayViews;
	private JournalFolder _folder;
	private Long _folderId;
	private final JournalWebConfiguration _journalWebConfiguration;
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
	private String _tabs1;
	private final TrashHelper _trashHelper;

}