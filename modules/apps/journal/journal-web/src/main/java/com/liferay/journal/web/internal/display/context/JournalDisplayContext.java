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

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.depot.util.SiteConnectedGroupGroupProviderUtil;
import com.liferay.dynamic.data.mapping.item.selector.DDMStructureItemSelectorReturnType;
import com.liferay.dynamic.data.mapping.item.selector.criterion.DDMStructureItemSelectorCriterion;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItemListBuilder;
import com.liferay.item.selector.ItemSelector;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.util.JournalHelper;
import com.liferay.journal.util.comparator.FolderArticleArticleIdComparator;
import com.liferay.journal.util.comparator.FolderArticleDisplayDateComparator;
import com.liferay.journal.util.comparator.FolderArticleModifiedDateComparator;
import com.liferay.journal.util.comparator.FolderArticleTitleComparator;
import com.liferay.journal.web.internal.asset.model.JournalArticleAssetRenderer;
import com.liferay.journal.web.internal.configuration.JournalWebConfiguration;
import com.liferay.journal.web.internal.constants.JournalWebConstants;
import com.liferay.journal.web.internal.portlet.action.ActionUtil;
import com.liferay.journal.web.internal.search.EntriesChecker;
import com.liferay.journal.web.internal.search.EntriesMover;
import com.liferay.journal.web.internal.security.permission.resource.JournalArticlePermission;
import com.liferay.journal.web.internal.security.permission.resource.JournalFolderPermission;
import com.liferay.journal.web.internal.servlet.taglib.util.JournalArticleActionDropdownItemsProvider;
import com.liferay.journal.web.internal.servlet.taglib.util.JournalFolderActionDropdownItems;
import com.liferay.journal.web.internal.util.JournalArticleTranslation;
import com.liferay.journal.web.internal.util.JournalArticleTranslationRowChecker;
import com.liferay.journal.web.internal.util.JournalPortletUtil;
import com.liferay.journal.web.internal.util.JournalSearcherUtil;
import com.liferay.journal.web.internal.util.JournalUtil;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.trash.TrashHelper;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class JournalDisplayContext {

	public static JournalDisplayContext create(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		TrashHelper trashHelper) {

		JournalDisplayContext journalDisplayContext =
			(JournalDisplayContext)liferayPortletRequest.getAttribute(
				JournalWebConstants.JOURNAL_DISPLAY_CONTEXT);

		if (journalDisplayContext == null) {
			journalDisplayContext = new JournalDisplayContext(
				httpServletRequest, liferayPortletRequest,
				liferayPortletResponse, assetDisplayPageFriendlyURLProvider,
				trashHelper);

			liferayPortletRequest.setAttribute(
				JournalWebConstants.JOURNAL_DISPLAY_CONTEXT,
				journalDisplayContext);
		}

		return journalDisplayContext;
	}

	public String getAbsolutePath(long folderId) throws PortalException {
		return _journalHelper.getAbsolutePath(_liferayPortletRequest, folderId);
	}

	public long[] getAddMenuFavItems() throws PortalException {
		if (_addMenuFavItems != null) {
			return _addMenuFavItems;
		}

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_httpServletRequest);

		List<Long> addMenuFavItemsList = new ArrayList<>();

		String[] addMenuFavItems = portalPreferences.getValues(
			JournalPortletKeys.JOURNAL,
			JournalPortletUtil.getAddMenuFavItemKey(
				_journalHelper, _liferayPortletRequest),
			new String[0]);

		for (DDMStructure ddmStructure : getDDMStructures()) {
			if (ArrayUtil.contains(
					addMenuFavItems,
					String.valueOf(ddmStructure.getStructureId()))) {

				addMenuFavItemsList.add(ddmStructure.getStructureId());
			}
		}

		_addMenuFavItems = ArrayUtil.toLongArray(addMenuFavItemsList);

		return _addMenuFavItems;
	}

	public int getAddMenuFavItemsLength() throws PortalException {
		long[] addMenuFavItems = getAddMenuFavItems();

		return addMenuFavItems.length;
	}

	public JournalArticle getArticle() throws PortalException {
		if (_article != null) {
			return _article;
		}

		_article = ActionUtil.getArticle(_httpServletRequest);

		return _article;
	}

	public List<DropdownItem> getArticleActionDropdownItems(
			JournalArticle article)
		throws Exception {

		JournalArticleActionDropdownItemsProvider
			articleActionDropdownItemsProvider =
				new JournalArticleActionDropdownItemsProvider(
					article, _liferayPortletRequest, _liferayPortletResponse,
					_assetDisplayPageFriendlyURLProvider, _trashHelper);

		return articleActionDropdownItemsProvider.getActionDropdownItems();
	}

	public JournalArticleDisplay getArticleDisplay() throws Exception {
		if (_articleDisplay != null) {
			return _articleDisplay;
		}

		JournalArticle article = JournalArticleLocalServiceUtil.fetchArticle(
			ParamUtil.getLong(_httpServletRequest, "groupId"),
			ParamUtil.getString(_httpServletRequest, "articleId"),
			ParamUtil.getDouble(_httpServletRequest, "version"));

		if (article == null) {
			return _articleDisplay;
		}

		_articleDisplay = JournalArticleLocalServiceUtil.getArticleDisplay(
			article, article.getDDMTemplateKey(), null,
			_themeDisplay.getLanguageId(),
			ParamUtil.getInteger(_httpServletRequest, "page"),
			new PortletRequestModel(
				_liferayPortletRequest, _liferayPortletResponse),
			_themeDisplay);

		return _articleDisplay;
	}

	public List<DropdownItem> getArticleHistoryActionDropdownItems(
			JournalArticle article)
		throws Exception {

		JournalArticleActionDropdownItemsProvider
			articleActionDropdownItemsProvider =
				new JournalArticleActionDropdownItemsProvider(
					article, _liferayPortletRequest, _liferayPortletResponse,
					_assetDisplayPageFriendlyURLProvider, _trashHelper);

		return articleActionDropdownItemsProvider.
			getArticleHistoryActionDropdownItems();
	}

	public List<DropdownItem> getArticleInfoPanelDropdownItems(
			JournalArticle article)
		throws Exception {

		return getArticleActionDropdownItems(article);
	}

	public SearchContainer<JournalArticleTranslation>
			getArticleTranslationsSearchContainer()
		throws Exception {

		if (_articleTranslationsSearchContainer != null) {
			return _articleTranslationsSearchContainer;
		}

		PortletURL portletURL = PortletURLBuilder.create(
			PortletURLUtil.clone(
				PortletURLUtil.getCurrent(
					_liferayPortletRequest, _liferayPortletResponse),
				_liferayPortletResponse)
		).setMVCPath(
			"/select_article_translations.jsp"
		).buildPortletURL();

		SearchContainer<JournalArticleTranslation>
			articleTranslationsSearchContainer = new SearchContainer<>(
				_liferayPortletRequest, portletURL, null, null);

		articleTranslationsSearchContainer.setId("articleTranslations");

		List<JournalArticleTranslation> articleTranslations = new ArrayList<>();

		JournalArticle article = getArticle();

		String keywords = getKeywords();

		for (String languageId : article.getAvailableLanguageIds()) {
			JournalArticleTranslation articleTranslation =
				new JournalArticleTranslation(
					StringUtil.equalsIgnoreCase(
						article.getDefaultLanguageId(), languageId),
					LocaleUtil.fromLanguageId(languageId));

			if (Validator.isNotNull(keywords) &&
				!StringUtil.containsIgnoreCase(
					LocaleUtil.getLongDisplayName(
						articleTranslation.getLocale(), Collections.emptySet()),
					keywords, StringPool.BLANK)) {

				continue;
			}

			articleTranslations.add(articleTranslation);
		}

		articleTranslationsSearchContainer.setResultsAndTotal(
			articleTranslations);
		articleTranslationsSearchContainer.setRowChecker(
			new JournalArticleTranslationRowChecker(_liferayPortletResponse));

		_articleTranslationsSearchContainer =
			articleTranslationsSearchContainer;

		return _articleTranslationsSearchContainer;
	}

	public List<DropdownItem> getArticleVersionActionDropdownItems(
			JournalArticle article)
		throws Exception {

		JournalArticleActionDropdownItemsProvider
			articleActionDropdownItemsProvider =
				new JournalArticleActionDropdownItemsProvider(
					article, _liferayPortletRequest, _liferayPortletResponse,
					_assetDisplayPageFriendlyURLProvider, _trashHelper);

		return articleActionDropdownItemsProvider.
			getArticleVersionActionDropdownItems();
	}

	public String getAvailableActions(JournalArticle article)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (JournalArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), article,
				ActionKeys.DELETE)) {

			availableActions.add("deleteEntries");
		}

		if (JournalArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), article,
				ActionKeys.EXPIRE) &&
			(article.getStatus() == WorkflowConstants.STATUS_APPROVED)) {

			availableActions.add("expireEntries");
		}

		if (JournalArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), article,
				ActionKeys.UPDATE)) {

			availableActions.add("moveEntries");
		}

		if (JournalArticlePermission.contains(
				_themeDisplay.getPermissionChecker(), article,
				ActionKeys.VIEW)) {

			availableActions.add("exportTranslation");
		}

		return com.liferay.petra.string.StringUtil.merge(
			availableActions, StringPool.COMMA);
	}

	public String getAvailableActions(JournalFolder folder)
		throws PortalException {

		List<String> availableActions = new ArrayList<>();

		if (JournalFolderPermission.contains(
				_themeDisplay.getPermissionChecker(), folder,
				ActionKeys.UPDATE)) {

			availableActions.add("deleteEntries");
		}

		if (JournalFolderPermission.contains(
				_themeDisplay.getPermissionChecker(), folder,
				ActionKeys.DELETE)) {

			availableActions.add("moveEntries");
		}

		return com.liferay.petra.string.StringUtil.merge(
			availableActions, StringPool.COMMA);
	}

	public String[] getCharactersBlacklist() throws PortalException {
		JournalServiceConfiguration journalServiceConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				JournalServiceConfiguration.class,
				_themeDisplay.getCompanyId());

		return journalServiceConfiguration.charactersblacklist();
	}

	public int getCommentsTotal() throws PortalException {
		SearchContainer<MBMessage> commentsSearchContainer =
			_getCommentsSearchContainer();

		return commentsSearchContainer.getTotal();
	}

	public Map<String, Object> getComponentContext() throws Exception {
		return Collections.singletonMap(
			"trashEnabled",
			_trashHelper.isTrashEnabled(_themeDisplay.getScopeGroupId()));
	}

	public List<TabsItem> getConfigurationTabsItems() {
		TabsItemList tabsItemList = TabsItemListBuilder.add(
			tabsItem -> {
				tabsItem.setActive(true);
				tabsItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "email-from"));
			}
		).add(
			tabsItem -> tabsItem.setLabel(
				LanguageUtil.get(
					_httpServletRequest, "web-content-added-email"))
		).add(
			tabsItem -> tabsItem.setLabel(
				LanguageUtil.get(
					_httpServletRequest, "web-content-expired-email"))
		).add(
			tabsItem -> tabsItem.setLabel(
				LanguageUtil.get(
					_httpServletRequest, "web-content-moved-from-folder-email"))
		).add(
			tabsItem -> tabsItem.setLabel(
				LanguageUtil.get(
					_httpServletRequest, "web-content-moved-to-folder-email"))
		).add(
			tabsItem -> tabsItem.setLabel(
				LanguageUtil.get(
					_httpServletRequest, "web-content-review-email"))
		).add(
			tabsItem -> tabsItem.setLabel(
				LanguageUtil.get(
					_httpServletRequest, "web-content-updated-email"))
		).build();

		if (JournalUtil.hasWorkflowDefinitionsLinks(_themeDisplay)) {
			tabsItemList.add(
				tabsItem -> tabsItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest,
						"web-content-approval-denied-email")));
			tabsItemList.add(
				tabsItem -> tabsItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest,
						"web-content-approval-granted-email")));
			tabsItemList.add(
				tabsItem -> tabsItem.setLabel(
					LanguageUtil.get(
						_httpServletRequest,
						"web-content-approval-requested-email")));
		}

		return tabsItemList;
	}

	public long getDDMStructureId() {
		if (_ddmStructureId != null) {
			return _ddmStructureId;
		}

		_ddmStructureId = ParamUtil.getLong(
			_httpServletRequest, "ddmStructureId");

		return _ddmStructureId;
	}

	public String getDDMStructureName() {
		if (_ddmStructureName != null) {
			return _ddmStructureName;
		}

		_ddmStructureName = LanguageUtil.get(
			_httpServletRequest, "basic-web-content");

		if (getDDMStructureId() <= 0) {
			return _ddmStructureName;
		}

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
			getDDMStructureId());

		if (ddmStructure != null) {
			_ddmStructureName = ddmStructure.getName(_themeDisplay.getLocale());
		}

		return _ddmStructureName;
	}

	public List<DDMStructure> getDDMStructures() throws PortalException {
		return getDDMStructures(getRestrictionType());
	}

	public List<DDMStructure> getDDMStructures(Integer restrictionType)
		throws PortalException {

		if (_ddmStructures != null) {
			return _ddmStructures;
		}

		if (restrictionType == null) {
			restrictionType = getRestrictionType();
		}

		_ddmStructures = JournalFolderServiceUtil.getDDMStructures(
			SiteConnectedGroupGroupProviderUtil.
				getCurrentAndAncestorSiteAndDepotGroupIds(
					_themeDisplay.getScopeGroupId(), true),
			getFolderId(), restrictionType);

		if (_journalWebConfiguration.journalBrowseByStructuresSortedByName()) {
			Locale locale = _themeDisplay.getLocale();

			_ddmStructures.sort(
				(ddmStructure1, ddmStructure2) -> {
					String name1 = ddmStructure1.getName(locale);
					String name2 = ddmStructure2.getName(locale);

					return name1.compareTo(name2);
				});
		}

		return _ddmStructures;
	}

	public int getDefaultStatus() {
		PermissionChecker permissionChecker =
			_themeDisplay.getPermissionChecker();

		if (permissionChecker.isContentReviewer(
				_themeDisplay.getCompanyId(),
				_themeDisplay.getScopeGroupId()) ||
			isNavigationMine()) {

			return WorkflowConstants.STATUS_ANY;
		}

		return WorkflowConstants.STATUS_APPROVED;
	}

	public String getDeleteTranslationsEventName() {
		return _liferayPortletResponse.getNamespace() + "selectTranslations";
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		String[] displayViews = getDisplayViews();

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_httpServletRequest);

		_displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle");

		if (Validator.isNull(_displayStyle)) {
			_displayStyle = portalPreferences.getValue(
				JournalPortletKeys.JOURNAL, "display-style",
				_journalWebConfiguration.defaultDisplayView());
		}

		if (!ArrayUtil.contains(displayViews, _displayStyle)) {
			_displayStyle = displayViews[0];
		}

		portalPreferences.setValue(
			JournalPortletKeys.JOURNAL, "display-style", _displayStyle);

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		return _journalWebConfiguration.displayViews();
	}

	public JournalFolder getFolder() {
		if (_folder != null) {
			return _folder;
		}

		_folder = (JournalFolder)_httpServletRequest.getAttribute(
			WebKeys.JOURNAL_FOLDER);

		if (_folder != null) {
			return _folder;
		}

		_folder = JournalFolderLocalServiceUtil.fetchFolder(
			ParamUtil.getLong(_httpServletRequest, "folderId"));

		return _folder;
	}

	public List<DropdownItem> getFolderActionDropdownItems(JournalFolder folder)
		throws Exception {

		JournalFolderActionDropdownItems folderActionDropdownItems =
			new JournalFolderActionDropdownItems(
				folder, _liferayPortletRequest, _liferayPortletResponse,
				_trashHelper);

		return folderActionDropdownItems.getActionDropdownItems();
	}

	public long getFolderId() {
		if (_folderId != null) {
			return _folderId;
		}

		_folderId = BeanParamUtil.getLong(
			getFolder(), _httpServletRequest, "folderId",
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _folderId;
	}

	public List<DropdownItem> getFolderInfoPanelDropdownItems(
			JournalFolder folder)
		throws Exception {

		JournalFolderActionDropdownItems folderActionDropdownItems =
			new JournalFolderActionDropdownItems(
				folder, _liferayPortletRequest, _liferayPortletResponse,
				_trashHelper);

		return folderActionDropdownItems.getInfoPanelActionDropdownItems();
	}

	public JSONArray getFoldersJSONArray() {
		return JSONUtil.put(
			JSONUtil.put(
				"children",
				_getFoldersJSONArray(
					_themeDisplay.getScopeGroupId(),
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID)
			).put(
				"id", JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID
			).put(
				"name", LanguageUtil.get(_themeDisplay.getLocale(), "home")
			));
	}

	public String getFolderTitle() {
		JournalFolder folder = getFolder();

		if (folder != null) {
			return folder.getName();
		}

		return StringPool.BLANK;
	}

	public List<NavigationItem> getInfoPanelNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(true);
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "details"));
			}
		).build();
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

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "all");

		return _navigation;
	}

	public List<NavigationItem> getNavigationItems(String currentItem) {
		Group group = _themeDisplay.getScopeGroup();

		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(currentItem.equals("web-content"));
				navigationItem.setHref(
					_liferayPortletResponse.createRenderURL());
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "web-content"));
			}
		).add(
			() -> !group.isLayout(),
			navigationItem -> {
				navigationItem.setActive(currentItem.equals("structures"));
				navigationItem.setHref(
					_liferayPortletResponse.createRenderURL(), "mvcPath",
					"/view_ddm_structures.jsp");
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "structures"));
			}
		).add(
			() -> !group.isLayout(),
			navigationItem -> {
				navigationItem.setActive(currentItem.equals("templates"));
				navigationItem.setHref(
					_liferayPortletResponse.createRenderURL(), "mvcPath",
					"/view_ddm_templates.jsp");
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "templates"));
			}
		).add(
			() ->
				_journalWebConfiguration.showFeeds() &&
				PortalUtil.isRSSFeedsEnabled(),
			navigationItem -> {
				navigationItem.setActive(currentItem.equals("feeds"));
				navigationItem.setHref(_getFeedsURL());
				navigationItem.setLabel(
					LanguageUtil.get(_httpServletRequest, "feeds"));
			}
		).build();
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(_httpServletRequest, "orderByCol");

		if (Validator.isNull(_orderByCol)) {
			if (isSearch()) {
				_orderByCol = _portalPreferences.getValue(
					JournalPortletKeys.JOURNAL, "order-by-col", "relevance");
			}
			else {
				_orderByCol = _portalPreferences.getValue(
					JournalPortletKeys.JOURNAL, "order-by-col",
					"modified-date");

				if (Objects.equals(_orderByCol, "relevance")) {
					_orderByCol = "modified-date";

					_portalPreferences.setValue(
						JournalPortletKeys.JOURNAL, "order-by-col", null);
				}
			}
		}
		else {
			_portalPreferences.setValue(
				JournalPortletKeys.JOURNAL, "order-by-col", _orderByCol);
		}

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		if (isNavigationRecent() ||
			Objects.equals(getOrderByCol(), "relevance")) {

			return "desc";
		}

		_orderByType = ParamUtil.getString(_httpServletRequest, "orderByType");

		if (Validator.isNull(_orderByType)) {
			String defaultOrderByType = "asc";

			if (Objects.equals(getOrderByCol(), "modified-date")) {
				defaultOrderByType = "desc";
			}

			_orderByType = _portalPreferences.getValue(
				JournalPortletKeys.JOURNAL, "order-by-type",
				defaultOrderByType);
		}
		else {
			_portalPreferences.setValue(
				JournalPortletKeys.JOURNAL, "order-by-type", _orderByType);
		}

		return _orderByType;
	}

	public String[] getOrderColumns() {
		String[] orderColumns = {"display-date", "modified-date", "title"};

		if (isSearch()) {
			orderColumns = ArrayUtil.append(orderColumns, "relevance");
		}

		if (!_journalWebConfiguration.journalArticleForceAutogenerateId() ||
			_journalWebConfiguration.journalArticleShowId()) {

			orderColumns = ArrayUtil.append(orderColumns, "id");
		}

		return orderColumns;
	}

	public String getOriginalAuthorUserName(JournalArticle article) {
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			JournalArticle.class.getName(),
			JournalArticleAssetRenderer.getClassPK(article));

		if (assetEntry != null) {
			return assetEntry.getUserName();
		}

		return article.getUserName();
	}

	public long getParentFolderId() {
		if (_parentFolderId != null) {
			return _parentFolderId;
		}

		_parentFolderId = ParamUtil.getLong(
			_httpServletRequest, "parentFolderId",
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _parentFolderId;
	}

	public PortletURL getPortletURL(String tab) {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		String navigation = ParamUtil.getString(
			_httpServletRequest, "navigation");

		if (Validator.isNotNull(navigation)) {
			portletURL.setParameter(
				"navigation", HtmlUtil.escapeJS(getNavigation()));
		}

		portletURL.setParameter("folderId", String.valueOf(getFolderId()));

		if (isNavigationStructure()) {
			portletURL.setParameter(
				"ddmStructureId", String.valueOf(getDDMStructureId()));
		}

		String status = ParamUtil.getString(_httpServletRequest, "status");

		if (Validator.isNotNull(status)) {
			portletURL.setParameter("status", String.valueOf(getStatus()));
		}

		String delta = ParamUtil.getString(_httpServletRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String deltaEntry = ParamUtil.getString(
			_httpServletRequest, "deltaEntry");

		if (Validator.isNotNull(deltaEntry)) {
			portletURL.setParameter("deltaEntry", deltaEntry);
		}

		String displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
		}

		String keywords = ParamUtil.getString(_httpServletRequest, "keywords");

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

		if (Validator.isNotNull(tab)) {
			portletURL.setParameter("tabs1", tab);
		}

		return portletURL;
	}

	public int getRestrictionType() {
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

	public SearchContainer<?> getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		if (!isSearch() || isWebContentTabSelected()) {
			_searchContainer = _getArticlesSearchContainer();

			return _searchContainer;
		}

		if (isVersionsTabSelected()) {
			_searchContainer = _getVersionsSearchContainer();

			return _searchContainer;
		}

		if (isCommentsTabSelected()) {
			_searchContainer = _getCommentsSearchContainer();

			return _searchContainer;
		}

		_searchContainer = _getArticlesSearchContainer();

		return _searchContainer;
	}

	public String getSelectDDMStructureURL() {
		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(_liferayPortletRequest);

		DDMStructureItemSelectorCriterion ddmStructureItemSelectorCriterion =
			new DDMStructureItemSelectorCriterion();

		ddmStructureItemSelectorCriterion.setClassNameId(
			PortalUtil.getClassNameId(JournalArticle.class));
		ddmStructureItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new DDMStructureItemSelectorReturnType());

		return String.valueOf(
			_itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory,
				_liferayPortletResponse.getNamespace() + "selectDDMStructure",
				ddmStructureItemSelectorCriterion));
	}

	public int getStatus() {
		if (_status != null) {
			return _status;
		}

		_status = ParamUtil.getInteger(
			_httpServletRequest, "status", getDefaultStatus());

		return _status;
	}

	public String getTabs1() {
		if (_tabs1 != null) {
			return _tabs1;
		}

		_tabs1 = ParamUtil.getString(_httpServletRequest, "tabs1");

		return _tabs1;
	}

	public int getTotalItems() throws PortalException {
		SearchContainer<?> articleSearch = _getArticlesSearchContainer();

		return articleSearch.getTotal();
	}

	public int getVersionsTotal() throws PortalException {
		SearchContainer<JournalArticle> articleSearch =
			_getVersionsSearchContainer();

		return articleSearch.getTotal();
	}

	public boolean hasCommentsResults() throws PortalException {
		if (getCommentsTotal() > 0) {
			return true;
		}

		return false;
	}

	public boolean hasResults() throws PortalException {
		if (getTotalItems() > 0) {
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

	public boolean isCommentsTabSelected() throws PortalException {
		if (Objects.equals(getTabs1(), "comments") ||
			(hasCommentsResults() && Validator.isNull(getTabs1()))) {

			return true;
		}

		return false;
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

	public boolean isShowInfoButton() {
		if (isNavigationMine() || isNavigationRecent() || isSearch()) {
			return false;
		}

		return true;
	}

	public boolean isVersionsTabSelected() throws PortalException {
		if (Objects.equals(getTabs1(), "versions") ||
			(hasVersionsResults() && Validator.isNull(getTabs1()))) {

			return true;
		}

		return false;
	}

	public boolean isWebContentTabSelected() throws PortalException {
		if (Objects.equals(getTabs1(), "web-content") ||
			(hasResults() && Validator.isNull(getTabs1()))) {

			return true;
		}

		return false;
	}

	private JournalDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		AssetDisplayPageFriendlyURLProvider assetDisplayPageFriendlyURLProvider,
		TrashHelper trashHelper) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_assetDisplayPageFriendlyURLProvider =
			assetDisplayPageFriendlyURLProvider;
		_trashHelper = trashHelper;

		_itemSelector = (ItemSelector)httpServletRequest.getAttribute(
			ItemSelector.class.getName());
		_journalHelper = (JournalHelper)httpServletRequest.getAttribute(
			JournalHelper.class.getName());
		_journalWebConfiguration =
			(JournalWebConfiguration)httpServletRequest.getAttribute(
				JournalWebConfiguration.class.getName());
		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			httpServletRequest);
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	private SearchContainer<?> _getArticlesSearchContainer()
		throws PortalException {

		if (_articleSearchContainer != null) {
			return _articleSearchContainer;
		}

		SearchContainer<JournalArticle> articleSearchContainer =
			new SearchContainer<>(
				_liferayPortletRequest, getPortletURL(), null, null);

		articleSearchContainer.setOrderByCol(getOrderByCol());
		articleSearchContainer.setOrderByComparator(
			JournalPortletUtil.getArticleOrderByComparator(
				getOrderByCol(), getOrderByType()));
		articleSearchContainer.setOrderByType(getOrderByType());

		if (isNavigationMine() || isNavigationRecent()) {
			boolean includeOwner = true;

			if (isNavigationMine()) {
				includeOwner = false;
			}

			if (isNavigationRecent()) {
				articleSearchContainer.setOrderByCol("modified-date");
				articleSearchContainer.setOrderByType(getOrderByType());
			}

			boolean includeArticleOwner = includeOwner;

			articleSearchContainer.setResultsAndTotal(
				() -> JournalArticleServiceUtil.getGroupArticles(
					_themeDisplay.getScopeGroupId(), _themeDisplay.getUserId(),
					getFolderId(), getStatus(), includeArticleOwner,
					articleSearchContainer.getStart(),
					articleSearchContainer.getEnd(),
					articleSearchContainer.getOrderByComparator()),
				JournalArticleServiceUtil.getGroupArticlesCount(
					_themeDisplay.getScopeGroupId(), _themeDisplay.getUserId(),
					getFolderId(), getStatus(), includeArticleOwner));

			articleSearchContainer.setRowChecker(_getEntriesChecker());

			if (!BrowserSnifferUtil.isMobile(_httpServletRequest)) {
				articleSearchContainer.setRowMover(
					new EntriesMover(
						_trashHelper.isTrashEnabled(
							_themeDisplay.getScopeGroupId())));
			}

			_articleSearchContainer = articleSearchContainer;

			return _articleSearchContainer;
		}

		if (getDDMStructureId() > 0) {
			articleSearchContainer.setResultsAndTotal(
				() -> JournalArticleServiceUtil.getArticlesByStructureId(
					_themeDisplay.getScopeGroupId(), getFolderId(),
					JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
					getDDMStructureId(), getStatus(),
					articleSearchContainer.getStart(),
					articleSearchContainer.getEnd(),
					articleSearchContainer.getOrderByComparator()),
				JournalArticleServiceUtil.getArticlesCountByStructureId(
					_themeDisplay.getScopeGroupId(), getFolderId(),
					JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
					getDDMStructureId(), getStatus()));
			articleSearchContainer.setRowChecker(_getEntriesChecker());

			if (!BrowserSnifferUtil.isMobile(_httpServletRequest)) {
				articleSearchContainer.setRowMover(
					new EntriesMover(
						_trashHelper.isTrashEnabled(
							_themeDisplay.getScopeGroupId())));
			}

			_articleSearchContainer = articleSearchContainer;

			return _articleSearchContainer;
		}

		SearchContainer<Object> articleAndFolderSearchContainer =
			new SearchContainer<>(
				_liferayPortletRequest, getPortletURL(), null, null);

		articleAndFolderSearchContainer.setOrderByCol(getOrderByCol());
		articleAndFolderSearchContainer.setOrderByComparator(
			_getFolderOrderByComparator());
		articleAndFolderSearchContainer.setOrderByType(getOrderByType());

		if (isSearch()) {
			SearchResponse searchResponse =
				JournalSearcherUtil.searchJournalArticleAndFolders(
					searchContext -> _populateSearchContext(
						articleAndFolderSearchContainer.getStart(),
						articleAndFolderSearchContainer.getEnd(), searchContext,
						false));

			articleAndFolderSearchContainer.setResultsAndTotal(
				() -> JournalSearcherUtil.transformJournalArticleAndFolders(
					searchResponse.getDocuments71()),
				searchResponse.getTotalHits());

			articleAndFolderSearchContainer.setRowChecker(_getEntriesChecker());

			if (!BrowserSnifferUtil.isMobile(_httpServletRequest)) {
				articleAndFolderSearchContainer.setRowMover(
					new EntriesMover(
						_trashHelper.isTrashEnabled(
							_themeDisplay.getScopeGroupId())));
			}

			_articleSearchContainer = articleAndFolderSearchContainer;

			return _articleSearchContainer;
		}

		articleAndFolderSearchContainer.setResultsAndTotal(
			() -> JournalFolderServiceUtil.getFoldersAndArticles(
				_themeDisplay.getScopeGroupId(), 0, getFolderId(), getStatus(),
				_themeDisplay.getLocale(),
				articleAndFolderSearchContainer.getStart(),
				articleAndFolderSearchContainer.getEnd(),
				articleAndFolderSearchContainer.getOrderByComparator()),
			JournalFolderServiceUtil.getFoldersAndArticlesCount(
				_themeDisplay.getScopeGroupId(), 0, getFolderId(),
				getStatus()));
		articleAndFolderSearchContainer.setRowChecker(_getEntriesChecker());

		if (!BrowserSnifferUtil.isMobile(_httpServletRequest)) {
			articleAndFolderSearchContainer.setRowMover(
				new EntriesMover(
					_trashHelper.isTrashEnabled(
						_themeDisplay.getScopeGroupId())));
		}

		_articleSearchContainer = articleAndFolderSearchContainer;

		return _articleSearchContainer;
	}

	private SearchContainer<MBMessage> _getCommentsSearchContainer()
		throws PortalException {

		SearchContainer<MBMessage> searchContainer = new SearchContainer<>(
			_liferayPortletRequest, getPortletURL(), null, null);

		SearchContext searchContext = SearchContextFactory.getInstance(
			_liferayPortletRequest.getHttpServletRequest());

		searchContext.setAttribute(
			Field.CLASS_NAME_ID,
			PortalUtil.getClassNameId(JournalArticle.class));

		searchContext.setAttribute("discussion", Boolean.TRUE);

		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setStart(searchContainer.getStart());

		List<MBMessage> mbMessages = new ArrayList<>();

		Indexer<MBMessage> indexer = IndexerRegistryUtil.getIndexer(
			MBMessage.class);

		Hits hits = indexer.search(searchContext);

		for (Document document : hits.getDocs()) {
			mbMessages.add(
				MBMessageLocalServiceUtil.fetchMBMessage(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK))));
		}

		searchContainer.setResultsAndTotal(() -> mbMessages, hits.getLength());

		return searchContainer;
	}

	private EntriesChecker _getEntriesChecker() {
		EntriesChecker entriesChecker = new EntriesChecker(
			_liferayPortletRequest, _liferayPortletResponse);

		entriesChecker.setCssClass("entry-selector");
		entriesChecker.setRememberCheckBoxStateURLRegex(
			StringBundler.concat(
				"^(?!.*", _liferayPortletResponse.getNamespace(),
				"redirect).*(folderId=", getFolderId(), ")"));

		return entriesChecker;
	}

	private String _getFeedsURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCPath(
			"/view_feeds.jsp"
		).setRedirect(
			_themeDisplay.getURLCurrent()
		).buildString();
	}

	private List<Long> _getFolderIds() {
		List<Long> folderIds = new ArrayList<>(1);

		if (getFolderId() != JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			folderIds.add(getFolderId());
		}

		return folderIds;
	}

	private OrderByComparator<Object> _getFolderOrderByComparator() {
		boolean orderByAsc = false;

		if (Objects.equals(getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		if (Objects.equals(getOrderByCol(), "display-date")) {
			return new FolderArticleDisplayDateComparator(orderByAsc);
		}
		else if (Objects.equals(getOrderByCol(), "id")) {
			return new FolderArticleArticleIdComparator(orderByAsc);
		}
		else if (Objects.equals(getOrderByCol(), "modified-date")) {
			return new FolderArticleModifiedDateComparator(orderByAsc);
		}
		else if (Objects.equals(getOrderByCol(), "title")) {
			return new FolderArticleTitleComparator(orderByAsc);
		}

		return null;
	}

	private JSONArray _getFoldersJSONArray(long groupId, long folderId) {
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

			jsonObject.put(
				"id", folder.getFolderId()
			).put(
				"name", folder.getName()
			);

			if (folder.getFolderId() == getParentFolderId()) {
				jsonObject.put("selected", true);
			}

			if (folder.getFolderId() == getFolderId()) {
				jsonObject.put("disabled", true);
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private Sort _getSort() {
		boolean orderByAsc = false;

		if (Objects.equals(getOrderByType(), "asc")) {
			orderByAsc = true;
		}

		if (Objects.equals(getOrderByCol(), "display-date")) {
			return new Sort("displayDate", Sort.LONG_TYPE, !orderByAsc);
		}

		if (Objects.equals(getOrderByCol(), "id")) {
			return new Sort(
				Field.getSortableFieldName(Field.ARTICLE_ID), Sort.STRING_TYPE,
				!orderByAsc);
		}

		if (Objects.equals(getOrderByCol(), "modified-date")) {
			return new Sort(Field.MODIFIED_DATE, Sort.LONG_TYPE, !orderByAsc);
		}

		if (Objects.equals(getOrderByCol(), "relevance")) {
			return new Sort(null, Sort.SCORE_TYPE, false);
		}

		if (Objects.equals(getOrderByCol(), "title")) {
			return new Sort(
				Field.getSortableFieldName(
					"localized_title_" + _themeDisplay.getLanguageId()),
				!orderByAsc);
		}

		return null;
	}

	private SearchContainer<JournalArticle> _getVersionsSearchContainer()
		throws PortalException {

		if (_articleVersionsSearchContainer != null) {
			return _articleVersionsSearchContainer;
		}

		SearchContainer<JournalArticle> articleVersionsSearchContainer =
			new SearchContainer<>(
				_liferayPortletRequest, getPortletURL(), null, null);

		articleVersionsSearchContainer.setOrderByCol(getOrderByCol());
		articleVersionsSearchContainer.setOrderByComparator(
			JournalPortletUtil.getArticleOrderByComparator(
				getOrderByCol(), getOrderByType()));
		articleVersionsSearchContainer.setOrderByType(getOrderByType());

		SearchResponse searchResponse =
			JournalSearcherUtil.searchJournalArticles(
				searchContext -> _populateSearchContext(
					articleVersionsSearchContainer.getStart(),
					articleVersionsSearchContainer.getEnd(), searchContext,
					true));

		articleVersionsSearchContainer.setResultsAndTotal(
			() -> JournalSearcherUtil.transformJournalArticles(
				searchResponse.getDocuments71(), true),
			searchResponse.getTotalHits());

		_articleVersionsSearchContainer = articleVersionsSearchContainer;

		return _articleVersionsSearchContainer;
	}

	private void _populateSearchContext(
		int start, int end, SearchContext searchContext, boolean showVersions) {

		searchContext.setAndSearch(false);

		Map<String, Serializable> attributes = searchContext.getAttributes();

		attributes.put(Field.ARTICLE_ID, getKeywords());
		attributes.put(
			Field.CLASS_NAME_ID, JournalArticleConstants.CLASS_NAME_ID_DEFAULT);
		attributes.put(Field.CONTENT, getKeywords());
		attributes.put(Field.DESCRIPTION, getKeywords());
		attributes.put(Field.STATUS, getStatus());
		attributes.put(Field.TITLE, getKeywords());
		attributes.put("head", !showVersions);
		attributes.put("latest", !showVersions);
		attributes.put(
			"params",
			LinkedHashMapBuilder.<String, Object>put(
				"expandoAttributes", getKeywords()
			).put(
				"keywords", getKeywords()
			).build());
		attributes.put("showNonindexable", !showVersions);

		searchContext.setAttributes(attributes);

		long ddmStructureId = ParamUtil.getLong(
			_httpServletRequest, "ddmStructureId");

		if (ddmStructureId > 0) {
			searchContext.setClassTypeIds(new long[] {ddmStructureId});
		}

		searchContext.setCompanyId(_themeDisplay.getCompanyId());
		searchContext.setEnd(end);
		searchContext.setFolderIds(_getFolderIds());
		searchContext.setGroupIds(new long[] {_themeDisplay.getScopeGroupId()});
		searchContext.setIncludeInternalAssetCategories(true);
		searchContext.setKeywords(getKeywords());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Sort sort = _getSort();

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);
	}

	private long[] _addMenuFavItems;
	private JournalArticle _article;
	private JournalArticleDisplay _articleDisplay;
	private SearchContainer<?> _articleSearchContainer;
	private SearchContainer<JournalArticleTranslation>
		_articleTranslationsSearchContainer;
	private SearchContainer<JournalArticle> _articleVersionsSearchContainer;
	private final AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;
	private Long _ddmStructureId;
	private String _ddmStructureName;
	private List<DDMStructure> _ddmStructures;
	private String _displayStyle;
	private JournalFolder _folder;
	private Long _folderId;
	private final HttpServletRequest _httpServletRequest;
	private final ItemSelector _itemSelector;
	private final JournalHelper _journalHelper;
	private final JournalWebConfiguration _journalWebConfiguration;
	private String _keywords;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private Long _parentFolderId;
	private final PortalPreferences _portalPreferences;
	private Integer _restrictionType;
	private SearchContainer<?> _searchContainer;
	private Integer _status;
	private String _tabs1;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;

}