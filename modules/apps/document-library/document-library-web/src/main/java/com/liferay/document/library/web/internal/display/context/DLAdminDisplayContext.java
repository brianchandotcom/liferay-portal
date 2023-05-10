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

package com.liferay.document.library.web.internal.display.context;

import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.kernel.versioning.VersioningStrategy;
import com.liferay.document.library.web.internal.display.context.helper.DLPortletInstanceSettingsHelper;
import com.liferay.document.library.web.internal.display.context.helper.DLRequestHelper;
import com.liferay.document.library.web.internal.settings.DLPortletInstanceSettings;
import com.liferay.document.library.web.internal.util.DLFolderUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.FolderItemSelectorReturnType;
import com.liferay.item.selector.criteria.folder.criterion.FolderItemSelectorCriterion;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.RelatedSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.util.PropsValues;
import com.liferay.trash.TrashHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Alejandro Tardín
 */
public class DLAdminDisplayContext {

	public DLAdminDisplayContext(
		AssetAutoTaggerConfiguration assetAutoTaggerConfiguration,
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse, TrashHelper trashHelper,
		VersioningStrategy versioningStrategy) {

		_assetAutoTaggerConfiguration = assetAutoTaggerConfiguration;
		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_trashHelper = trashHelper;
		_versioningStrategy = versioningStrategy;

		_dlRequestHelper = new DLRequestHelper(_httpServletRequest);

		_dlPortletInstanceSettings =
			_dlRequestHelper.getDLPortletInstanceSettings();
		_dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(
			_dlRequestHelper);

		_httpSession = httpServletRequest.getSession();
		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			httpServletRequest);

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_permissionChecker = _themeDisplay.getPermissionChecker();

		_computeFolders();
	}

	public String getDisplayStyle() {
		if (_displayStyle != null) {
			return _displayStyle;
		}

		String displayStyle = ParamUtil.getString(
			_httpServletRequest, "displayStyle");

		String[] displayViews = _dlPortletInstanceSettings.getDisplayViews();

		if (Validator.isNull(displayStyle)) {
			displayStyle = _getPortletPreference(
				"display-style", PropsValues.DL_DEFAULT_DISPLAY_VIEW);
		}
		else {
			if (ArrayUtil.contains(displayViews, displayStyle)) {
				_setPortletPreference("display-style", displayStyle);

				_httpServletRequest.setAttribute(
					WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
			}
		}

		if (!ArrayUtil.contains(displayViews, displayStyle)) {
			displayStyle = displayViews[0];
		}

		_displayStyle = displayStyle;

		return _displayStyle;
	}

	public Folder getFolder() {
		return _folder;
	}

	public long getFolderId() {
		return _folderId;
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "home");

		return _navigation;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		String orderByCol = ParamUtil.getString(
			_httpServletRequest, "orderByCol");

		long fileEntryTypeId = ParamUtil.getLong(
			_httpServletRequest, "fileEntryTypeId", -1);

		if (orderByCol.equals("downloads") && (fileEntryTypeId >= 0)) {
			orderByCol = "modifiedDate";
		}

		if (Validator.isNull(orderByCol)) {
			orderByCol = _getPortletPreference("order-by-col", "modifiedDate");
		}
		else {
			_setPortletPreference("order-by-col", orderByCol);
		}

		_orderByCol = orderByCol;

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		if (isNavigationRecent()) {
			return "desc";
		}

		String orderByType = ParamUtil.getString(
			_httpServletRequest, "orderByType");

		if (Validator.isNull(orderByType)) {
			orderByType = _getPortletPreference("order-by-type", "desc");
		}
		else {
			_setPortletPreference("order-by-type", orderByType);
		}

		_orderByType = orderByType;

		return _orderByType;
	}

	public String getRememberCheckBoxStateURLRegex() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		if (!DLPortletKeys.DOCUMENT_LIBRARY.equals(
				portletDisplay.getRootPortletId())) {

			return StringBundler.concat(
				"^(?!.*", portletDisplay.getNamespace(),
				"redirect).*(folderId=", _folderId, ")");
		}

		if (_folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return "^[^?]+/" + portletDisplay.getInstanceId() + "\\?";
		}

		return StringBundler.concat(
			"^[^?]+/", portletDisplay.getInstanceId(), "/view/", _folderId,
			"\\?");
	}

	public long getRepositoryId() {
		if (_repositoryId != 0) {
			return _repositoryId;
		}

		long repositoryId = 0;

		Folder folder = getFolder();

		if (folder != null) {
			repositoryId = folder.getRepositoryId();
		}
		else {
			repositoryId = _dlPortletInstanceSettings.getSelectedRepositoryId();
		}

		if (repositoryId == 0) {
			repositoryId = ParamUtil.getLong(
				_httpServletRequest, "repositoryId",
				_themeDisplay.getScopeGroupId());
		}

		_repositoryId = repositoryId;

		return _repositoryId;
	}

	public long getRootFolderId() {
		return _rootFolderId;
	}

	public String getRootFolderName() {
		return _rootFolderName;
	}

	public SearchContainer<RepositoryEntry> getSearchContainer() {
		if (_searchContainer == null) {
			try {
				if (isSearch()) {
					_searchContainer = _getSearchSearchContainer();
				}
				else {
					_searchContainer = _getDLSearchContainer();
				}
			}
			catch (PortalException portalException) {
				throw new SystemException(portalException);
			}
		}

		return _searchContainer;
	}

	public PortletURL getSearchSearchContainerURL() {
		return PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/document_library/search"
		).setRedirect(
			ParamUtil.getString(_httpServletRequest, "redirect")
		).setKeywords(
			ParamUtil.getString(_httpServletRequest, "keywords")
		).setParameter(
			"searchFolderId",
			ParamUtil.getLong(_httpServletRequest, "searchFolderId")
		).buildPortletURL();
	}

	public long getSelectedRepositoryId() {
		if (_selectedRepositoryId != 0) {
			return _selectedRepositoryId;
		}

		long repositoryId =
			_dlPortletInstanceSettings.getSelectedRepositoryId();

		if (repositoryId != 0) {
			_selectedRepositoryId = repositoryId;

			return _selectedRepositoryId;
		}

		_selectedRepositoryId = getRepositoryId();

		return _selectedRepositoryId;
	}

	public PortletURL getSelectFolderURL(HttpServletRequest httpServletRequest)
		throws PortalException {

		ItemSelector itemSelector =
			(ItemSelector)httpServletRequest.getAttribute(
				ItemSelector.class.getName());

		FolderItemSelectorCriterion folderItemSelectorCriterion =
			new FolderItemSelectorCriterion();

		folderItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FolderItemSelectorReturnType());
		folderItemSelectorCriterion.setFolderId(getRootFolderId());
		folderItemSelectorCriterion.setIgnoreRootFolder(true);
		folderItemSelectorCriterion.setRepositoryId(getSelectedRepositoryId());
		folderItemSelectorCriterion.setSelectedFolderId(getRootFolderId());
		folderItemSelectorCriterion.setSelectedRepositoryId(
			getSelectedRepositoryId());
		folderItemSelectorCriterion.setShowGroupSelector(true);
		folderItemSelectorCriterion.setShowMountFolder(false);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		long groupId = getSelectedRepositoryId();

		Repository repository = RepositoryLocalServiceUtil.fetchRepository(
			getSelectedRepositoryId());

		if (repository != null) {
			groupId = repository.getGroupId();
		}

		return itemSelector.getItemSelectorURL(
			RequestBackedPortletURLFactoryUtil.create(httpServletRequest),
			GroupLocalServiceUtil.getGroup(
				GetterUtil.getLong(groupId, _themeDisplay.getScopeGroupId())),
			_themeDisplay.getScopeGroupId(),
			portletDisplay.getNamespace() + "folderSelected",
			folderItemSelectorCriterion);
	}

	public boolean isAutoTaggingEnabled() {
		return _assetAutoTaggerConfiguration.isEnabled();
	}

	public boolean isDefaultFolderView() {
		return _defaultFolderView;
	}

	public boolean isNavigationRecent() {
		if (Objects.equals(getNavigation(), "recent")) {
			return true;
		}

		return false;
	}

	public boolean isRootFolderInTrash() {
		return _rootFolderInTrash;
	}

	public boolean isRootFolderNotFound() {
		return _rootFolderNotFound;
	}

	public boolean isSearch() {
		String mvcRenderCommandName = ParamUtil.getString(
			_httpServletRequest, "mvcRenderCommandName");

		return mvcRenderCommandName.equals("/document_library/search");
	}

	public boolean isUpdateAutoTags() {
		return _assetAutoTaggerConfiguration.isUpdateAutoTags();
	}

	public boolean isVersioningStrategyOverridable() {
		return _versioningStrategy.isOverridable();
	}

	private void _computeFolders() {
		try {
			_computeRootFolder();

			_folder = (Folder)_httpServletRequest.getAttribute(
				WebKeys.DOCUMENT_LIBRARY_FOLDER);

			if (_folder == null) {
				_folderId = getRootFolderId();
			}
			else {
				_folderId = _folder.getFolderId();
			}

			_defaultFolderView = false;

			if ((_folder == null) &&
				(_folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

				_defaultFolderView = true;
			}

			if (_defaultFolderView) {
				try {
					_folder = DLAppLocalServiceUtil.getFolder(_folderId);
				}
				catch (NoSuchFolderException noSuchFolderException) {
					_folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

					if (_log.isWarnEnabled()) {
						_log.warn(
							"Unable to get folder " + _folderId,
							noSuchFolderException);
					}
				}
			}
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	private void _computeRootFolder() {
		_rootFolderId = _dlPortletInstanceSettings.getRootFolderId();
		_rootFolderName = StringPool.BLANK;

		if (_rootFolderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			_rootFolderName = LanguageUtil.get(_httpServletRequest, "home");

			return;
		}

		try {
			Folder rootFolder = DLAppLocalServiceUtil.getFolder(_rootFolderId);

			_rootFolderName = rootFolder.getName();

			if (rootFolder.isRepositoryCapabilityProvided(
					TrashCapability.class)) {

				TrashCapability trashCapability =
					rootFolder.getRepositoryCapability(TrashCapability.class);

				_rootFolderInTrash = trashCapability.isInTrash(rootFolder);

				if (_rootFolderInTrash) {
					_rootFolderName = _trashHelper.getOriginalTitle(
						rootFolder.getName());
				}
			}

			DLFolderUtil.validateDepotFolder(
				_rootFolderId, rootFolder.getGroupId(),
				_themeDisplay.getScopeGroupId());
		}
		catch (NoSuchFolderException noSuchFolderException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					StringBundler.concat(
						"Could not find folder {folderId=", _rootFolderId, "}"),
					noSuchFolderException);
			}

			_rootFolderNotFound = true;
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}
	}

	private Filter _getAssetTagNamesFilter(String[] assetTagNames) {
		if (ArrayUtil.isEmpty(assetTagNames)) {
			return null;
		}

		BooleanFilter booleanFilter = new BooleanFilter();

		for (String assetTagName : assetTagNames) {
			booleanFilter.addTerm(
				Field.ASSET_TAG_NAMES + ".raw", assetTagName,
				BooleanClauseOccur.MUST);
		}

		return booleanFilter;
	}

	private BooleanClause<Query>[] _getBooleanClauses(
		String[] assetTagNames, String[] extensions, long fileEntryTypeId,
		long userId) {

		BooleanQuery booleanQuery = new BooleanQueryImpl();

		BooleanFilter booleanFilter = new BooleanFilter();

		if (ArrayUtil.isNotEmpty(assetTagNames)) {
			booleanFilter.add(
				_getAssetTagNamesFilter(assetTagNames),
				BooleanClauseOccur.MUST);
		}

		if (ArrayUtil.isNotEmpty(extensions)) {
			booleanFilter.add(
				_getExtensionsFilter(extensions), BooleanClauseOccur.MUST);
		}

		if (fileEntryTypeId >= 0) {
			booleanFilter.addTerm(
				"fileEntryTypeId", String.valueOf(fileEntryTypeId),
				BooleanClauseOccur.MUST);
		}

		if (userId > 0) {
			booleanFilter.addTerm(
				Field.USER_ID, String.valueOf(userId), BooleanClauseOccur.MUST);
		}

		booleanQuery.setPreBooleanFilter(booleanFilter);

		return new BooleanClause[] {
			BooleanClauseFactoryUtil.create(
				booleanQuery, BooleanClauseOccur.MUST.getName())
		};
	}

	private SearchContainer<RepositoryEntry> _getDLSearchContainer()
		throws PortalException {

		String navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "home");
		String currentFolder = ParamUtil.getString(
			_httpServletRequest, "curFolder");
		String deltaFolder = ParamUtil.getString(
			_httpServletRequest, "deltaFolder");
		long fileEntryTypeId = ParamUtil.getLong(
			_httpServletRequest, "fileEntryTypeId", -1);
		String[] extensions = ParamUtil.getStringValues(
			_httpServletRequest, "extension");
		String[] assetTagIds = ParamUtil.getStringValues(
			_httpServletRequest, "assetTagId");

		int status = WorkflowConstants.STATUS_APPROVED;

		User user = _themeDisplay.getUser();

		if (_permissionChecker.isContentReviewer(
				user.getCompanyId(), _themeDisplay.getScopeGroupId())) {

			status = WorkflowConstants.STATUS_ANY;
		}

		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		long folderId = getFolderId();

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/view");
		}
		else {
			portletURL.setParameter(
				"mvcRenderCommandName", "/document_library/view_folder");
		}

		portletURL.setParameter("navigation", navigation);
		portletURL.setParameter("curFolder", currentFolder);
		portletURL.setParameter("deltaFolder", deltaFolder);
		portletURL.setParameter("folderId", String.valueOf(folderId));
		portletURL.setParameter("extension", extensions);

		if (fileEntryTypeId >= 0) {
			portletURL.setParameter(
				"fileEntryTypeId", String.valueOf(fileEntryTypeId));
		}

		SearchContainer<RepositoryEntry> dlSearchContainer =
			new SearchContainer<>(
				_liferayPortletRequest, null, null, "curEntry",
				_dlPortletInstanceSettings.getEntriesPerPage(), portletURL,
				null, _getEmptyResultsMessage(fileEntryTypeId));

		dlSearchContainer.setHeaderNames(
			ListUtil.fromArray(
				_dlPortletInstanceSettingsHelper.getEntryColumns()));
		dlSearchContainer.setOrderByCol(getOrderByCol());
		dlSearchContainer.setOrderByType(getOrderByType());

		if ((fileEntryTypeId >= 0) || ArrayUtil.isNotEmpty(assetTagIds) ||
			ArrayUtil.isNotEmpty(extensions) || navigation.equals("mine") ||
			navigation.equals("recent")) {

			if (navigation.equals("recent")) {
				dlSearchContainer.setOrderByCol("modifiedDate");
				dlSearchContainer.setOrderByType("desc");
			}

			SearchContext searchContext = _getSearchContext(dlSearchContainer);

			long userId = 0;

			if (navigation.equals("mine") && _themeDisplay.isSignedIn()) {
				status = WorkflowConstants.STATUS_ANY;
				userId = _themeDisplay.getUserId();
			}

			searchContext.setAttribute("status", status);
			searchContext.setBooleanClauses(
				_getBooleanClauses(
					assetTagIds, extensions, fileEntryTypeId, userId));

			if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
				searchContext.setFolderIds(new long[] {folderId});
			}

			Indexer<?> indexer = IndexerRegistryUtil.getIndexer(
				DLFileEntryConstants.getClassName());

			Hits hits = indexer.search(searchContext);

			dlSearchContainer.setResultsAndTotal(
				() -> _getRepositoryEntries(hits), hits.getLength());

			return dlSearchContainer;
		}

		dlSearchContainer.setOrderByComparator(
			DLUtil.getRepositoryModelOrderByComparator(
				getOrderByCol(), getOrderByType(), true));

		long repositoryId = getRepositoryId();

		long categoryId = ParamUtil.getLong(_httpServletRequest, "categoryId");
		String tagName = ParamUtil.getString(_httpServletRequest, "tag");

		if ((categoryId > 0) || Validator.isNotNull(tagName)) {
			long[] classNameIds = {
				PortalUtil.getClassNameId(DLFileEntryConstants.getClassName()),
				PortalUtil.getClassNameId(
					DLFileShortcutConstants.getClassName())
			};

			AssetEntryQuery assetEntryQuery = new AssetEntryQuery(
				classNameIds, dlSearchContainer);

			assetEntryQuery.setEnablePermissions(true);
			assetEntryQuery.setExcludeZeroViewCount(false);

			List<RepositoryEntry> results = new ArrayList<>();

			for (AssetEntry assetEntry :
					AssetEntryServiceUtil.getEntries(assetEntryQuery)) {

				if (Objects.equals(
						assetEntry.getClassName(),
						DLFileEntryConstants.getClassName())) {

					FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
						assetEntry.getClassPK());

					if (_isAncestorFolder(folderId, fileEntry) ||
						((folderId ==
							DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
						 (fileEntry.getRepositoryId() == repositoryId))) {

						results.add(fileEntry);
					}
				}
				else {
					results.add(
						DLAppLocalServiceUtil.getFileShortcut(
							assetEntry.getClassPK()));
				}
			}

			dlSearchContainer.setResultsAndTotal(
				() -> results,
				AssetEntryServiceUtil.getEntriesCount(assetEntryQuery));
		}
		else {
			int dlAppStatus = status;

			dlSearchContainer.setResultsAndTotal(
				() ->
					(List)
						DLAppServiceUtil.
							getFoldersAndFileEntriesAndFileShortcuts(
								repositoryId, folderId, dlAppStatus, true,
								dlSearchContainer.getStart(),
								dlSearchContainer.getEnd(),
								dlSearchContainer.getOrderByComparator()),
				DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(
					repositoryId, folderId, dlAppStatus, true));
		}

		return dlSearchContainer;
	}

	private String _getEmptyResultsMessage(long fileEntryTypeId)
		throws PortalException {

		if (fileEntryTypeId < 0) {
			return "there-are-no-documents-or-media-files-in-this-folder";
		}

		String dlFileEntryTypeName = LanguageUtil.get(
			_httpServletRequest, "basic-document");

		if (fileEntryTypeId > 0) {
			DLFileEntryType dlFileEntryType =
				DLFileEntryTypeLocalServiceUtil.getFileEntryType(
					fileEntryTypeId);

			dlFileEntryTypeName = dlFileEntryType.getName(
				_httpServletRequest.getLocale());
		}

		return LanguageUtil.format(
			_httpServletRequest,
			"there-are-no-documents-or-media-files-of-type-x",
			HtmlUtil.escape(dlFileEntryTypeName));
	}

	private Filter _getExtensionsFilter(String[] extensions) {
		if (ArrayUtil.isEmpty(extensions)) {
			return null;
		}

		TermsFilter termsFilter = new TermsFilter("extension");

		for (String extension : extensions) {
			termsFilter.addValue(extension);
		}

		return termsFilter;
	}

	private Hits _getHits(SearchContainer<RepositoryEntry> searchContainer)
		throws PortalException {

		SearchContext searchContext = SearchContextFactory.getInstance(
			_httpServletRequest);

		searchContext.setAttribute("paginationType", "regular");

		long searchRepositoryId = ParamUtil.getLong(
			_httpServletRequest, "searchRepositoryId",
			_themeDisplay.getScopeGroupId());

		searchContext.setAttribute("searchRepositoryId", searchRepositoryId);

		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setFolderIds(
			new long[] {
				ParamUtil.getLong(_httpServletRequest, "searchFolderId")
			});

		Group group = GroupLocalServiceUtil.fetchGroup(searchRepositoryId);

		if ((group != null) &&
			GroupPermissionUtil.contains(
				_themeDisplay.getPermissionChecker(), group, ActionKeys.VIEW)) {

			searchContext.setGroupIds(new long[] {searchRepositoryId});
		}

		searchContext.setIncludeDiscussions(true);
		searchContext.setIncludeInternalAssetCategories(true);
		searchContext.setKeywords(
			ParamUtil.getString(_httpServletRequest, "keywords"));
		searchContext.setLocale(_themeDisplay.getSiteDefaultLocale());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setSearchSubfolders(true);

		searchContext.setStart(searchContainer.getStart());

		return DLAppServiceUtil.search(searchRepositoryId, searchContext);
	}

	private String _getPortletPreference(String name, String defaultValue) {
		if (_themeDisplay.isSignedIn()) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				PortletPreferences portletPreferences =
					_getPortletPreferences();

				return portletPreferences.getValue(name, defaultValue);
			}
		}

		return GetterUtil.getString(
			_httpSession.getAttribute(
				_dlRequestHelper.getPortletId() + StringPool.UNDERLINE + name),
			defaultValue);
	}

	private PortletPreferences _getPortletPreferences() {
		if (_portletPreferences != null) {
			return _portletPreferences;
		}

		_portletPreferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				_themeDisplay.getCompanyId(), _themeDisplay.getUserId(),
				PortletKeys.PREFS_OWNER_TYPE_USER, _themeDisplay.getPlid(),
				_dlRequestHelper.getPortletId(), StringPool.BLANK);

		return _portletPreferences;
	}

	private List<RepositoryEntry> _getRepositoryEntries(Hits hits) {
		List<RepositoryEntry> results = new ArrayList<>();

		for (Document doc : hits.getDocs()) {
			long fileEntryId = GetterUtil.getLong(
				doc.get(Field.ENTRY_CLASS_PK));

			FileEntry fileEntry = null;

			try {
				fileEntry = DLAppLocalServiceUtil.getFileEntry(fileEntryId);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						StringBundler.concat(
							"Documents and Media search index is stale and ",
							"contains file entry ", fileEntryId),
						exception);
				}

				continue;
			}

			results.add(fileEntry);
		}

		return results;
	}

	private SearchContext _getSearchContext(
		SearchContainer<RepositoryEntry> searchContainer) {

		SearchContext searchContext = SearchContextFactory.getInstance(
			new long[0], new String[0], new HashMap<>(),
			_themeDisplay.getCompanyId(), null, _themeDisplay.getLayout(),
			_themeDisplay.getLocale(), _themeDisplay.getScopeGroupId(),
			_themeDisplay.getTimeZone(), _themeDisplay.getUserId());

		searchContext.setAttribute("paginationType", "none");
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setSorts(
			_getSort(
				searchContainer.getOrderByCol(),
				searchContainer.getOrderByType()));
		searchContext.setStart(searchContainer.getStart());

		return searchContext;
	}

	private List<RepositoryEntry> _getSearchResults(Hits hits)
		throws PortalException {

		List<RepositoryEntry> searchResults = new ArrayList<>();

		for (SearchResult searchResult :
				SearchResultUtil.getSearchResults(
					hits, _httpServletRequest.getLocale())) {

			String className = searchResult.getClassName();

			try {
				List<RelatedSearchResult<FileEntry>>
					fileEntryRelatedSearchResults =
						searchResult.getFileEntryRelatedSearchResults();

				if (!fileEntryRelatedSearchResults.isEmpty()) {
					fileEntryRelatedSearchResults.forEach(
						fileEntryRelatedSearchResult -> searchResults.add(
							fileEntryRelatedSearchResult.getModel()));
				}
				else if (className.equals(DLFileEntry.class.getName()) ||
						 FileEntry.class.isAssignableFrom(
							 Class.forName(className))) {

					searchResults.add(
						DLAppLocalServiceUtil.getFileEntry(
							searchResult.getClassPK()));
				}
				else if (className.equals(DLFolder.class.getName()) ||
						 className.equals(Folder.class.getName())) {

					searchResults.add(
						DLAppLocalServiceUtil.getFolder(
							searchResult.getClassPK()));
				}
			}
			catch (ClassNotFoundException classNotFoundException) {
				throw new PortalException(classNotFoundException);
			}
		}

		return searchResults;
	}

	private SearchContainer<RepositoryEntry> _getSearchSearchContainer()
		throws PortalException {

		SearchContainer<RepositoryEntry> searchContainer =
			new SearchContainer<>(
				_liferayPortletRequest, getSearchSearchContainerURL(), null,
				null);

		Hits hits = _getHits(searchContainer);

		searchContainer.setResultsAndTotal(
			() -> _getSearchResults(hits), hits.getLength());

		return searchContainer;
	}

	private Sort _getSort(String orderByCol, String orderByType) {
		int type = Sort.STRING_TYPE;
		String fieldName = orderByCol;

		if (Objects.equals(orderByCol, "creationDate")) {
			fieldName = Field.CREATE_DATE;
			type = Sort.LONG_TYPE;
		}
		else if (Objects.equals(orderByCol, "modifiedDate")) {
			fieldName = Field.MODIFIED_DATE;
			type = Sort.LONG_TYPE;
		}
		else if (Objects.equals(orderByCol, "size")) {
			type = Sort.LONG_TYPE;
		}

		return SortFactoryUtil.create(
			fieldName, type, !StringUtil.equalsIgnoreCase(orderByType, "asc"));
	}

	private boolean _isAncestorFolder(long folderId, FileEntry fileEntry) {
		LiferayFileEntry liferayFileEntry = (LiferayFileEntry)fileEntry;

		DLFileEntry dlFileEntry = liferayFileEntry.getDLFileEntry();

		List<String> treePaths = Arrays.asList(
			StringUtil.split(
				dlFileEntry.getTreePath(), CharPool.FORWARD_SLASH));

		return treePaths.contains(String.valueOf(folderId));
	}

	private void _setPortletPreference(String name, String value) {
		if (_themeDisplay.isSignedIn()) {
			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				PortletPreferences portletPreferences =
					_getPortletPreferences();

				try {
					portletPreferences.setValue(name, value);

					portletPreferences.store();
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception);
					}
				}
			}
		}
		else {
			_httpSession.setAttribute(
				_dlRequestHelper.getPortletId() + StringPool.UNDERLINE + name,
				value);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLAdminDisplayContext.class);

	private final AssetAutoTaggerConfiguration _assetAutoTaggerConfiguration;
	private boolean _defaultFolderView;
	private String _displayStyle;
	private final DLPortletInstanceSettings _dlPortletInstanceSettings;
	private final DLPortletInstanceSettingsHelper
		_dlPortletInstanceSettingsHelper;
	private final DLRequestHelper _dlRequestHelper;
	private Folder _folder;
	private long _folderId;
	private final HttpServletRequest _httpServletRequest;
	private final HttpSession _httpSession;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final PermissionChecker _permissionChecker;
	private final PortalPreferences _portalPreferences;
	private PortletPreferences _portletPreferences;
	private long _repositoryId;
	private long _rootFolderId;
	private boolean _rootFolderInTrash;
	private String _rootFolderName;
	private boolean _rootFolderNotFound;
	private SearchContainer<RepositoryEntry> _searchContainer;
	private long _selectedRepositoryId;
	private final ThemeDisplay _themeDisplay;
	private final TrashHelper _trashHelper;
	private final VersioningStrategy _versioningStrategy;

}