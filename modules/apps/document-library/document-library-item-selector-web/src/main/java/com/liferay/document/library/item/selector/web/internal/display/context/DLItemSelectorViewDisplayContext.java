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

package com.liferay.document.library.item.selector.web.internal.display.context;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.item.selector.web.internal.DLItemSelectorView;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.item.selector.taglib.servlet.taglib.util.RepositoryEntryBrowserTagUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.capabilities.FileEntryTypeCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.staging.StagingGroupHelper;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class DLItemSelectorViewDisplayContext<T extends ItemSelectorCriterion> {

	public DLItemSelectorViewDisplayContext(
		AssetVocabularyService assetVocabularyService,
		ClassNameLocalService classNameLocalService,
		DLItemSelectorView<T> dlItemSelectorView,
		ModelResourcePermission<Folder> folderModelResourcePermission,
		HttpServletRequest httpServletRequest, T itemSelectorCriterion,
		String itemSelectedEventName,
		ItemSelectorReturnTypeResolverHandler
			itemSelectorReturnTypeResolverHandler,
		PortletURL portletURL, boolean search,
		StagingGroupHelper stagingGroupHelper) {

		_assetVocabularyService = assetVocabularyService;
		_classNameLocalService = classNameLocalService;
		_dlItemSelectorView = dlItemSelectorView;
		_folderModelResourcePermission = folderModelResourcePermission;
		_httpServletRequest = httpServletRequest;
		_itemSelectorCriterion = itemSelectorCriterion;
		_itemSelectedEventName = itemSelectedEventName;
		_itemSelectorReturnTypeResolverHandler =
			itemSelectorReturnTypeResolverHandler;
		_portletURL = portletURL;
		_search = search;
		_stagingGroupHelper = stagingGroupHelper;

		_portalPreferences = PortletPreferencesFactoryUtil.getPortalPreferences(
			_httpServletRequest);
		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String[] getExtensions() {
		return _dlItemSelectorView.getExtensions();
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public ItemSelectorReturnTypeResolver getItemSelectorReturnTypeResolver() {
		return _itemSelectorReturnTypeResolverHandler.
			getItemSelectorReturnTypeResolver(
				_itemSelectorCriterion, _dlItemSelectorView, FileEntry.class);
	}

	public PortletURL getPortletURL(
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		PortletURL portletURL = PortletURLUtil.clone(
			_portletURL, liferayPortletResponse);

		portletURL.setParameter("folderId", String.valueOf(_getFolderId()));
		portletURL.setParameter("selectedTab", String.valueOf(getTitle()));

		return portletURL;
	}

	public List<Object> getRepositoryEntries() throws Exception {
		if (_search) {
			Hits hits = _getHits();

			Document[] docs = hits.getDocs();

			List<Object> repositoryEntries = new ArrayList<>(docs.length);

			List<SearchResult> searchResults =
				SearchResultUtil.getSearchResults(
					hits, _themeDisplay.getLocale());

			for (SearchResult searchResult : searchResults) {
				String className = searchResult.getClassName();

				if (className.equals(DLFileEntryConstants.getClassName())) {
					repositoryEntries.add(
						DLAppServiceUtil.getFileEntry(
							searchResult.getClassPK()));
				}
				else if (className.equals(
							DLFileShortcutConstants.getClassName())) {

					repositoryEntries.add(
						DLAppServiceUtil.getFileShortcut(
							searchResult.getClassPK()));
				}
				else if (className.equals(DLFolderConstants.getClassName())) {
					repositoryEntries.add(
						DLAppServiceUtil.getFolder(searchResult.getClassPK()));
				}
			}

			return repositoryEntries;
		}

		OrderByComparator<Object> repositoryModelOrderByComparator =
			DLUtil.getRepositoryModelOrderByComparator(
				RepositoryEntryBrowserTagUtil.getOrderByCol(
					_httpServletRequest, _portalPreferences),
				RepositoryEntryBrowserTagUtil.getOrderByType(
					_httpServletRequest, _portalPreferences),
				true);

		int[] startAndEnd = _getStartAndEnd();

		Repository repository = _getRepository();

		if (_isFilterByFileEntryType() &&
			repository.isCapabilityProvided(FileEntryTypeCapability.class)) {

			FileEntryTypeCapability fileEntryTypeCapability =
				repository.getCapability(FileEntryTypeCapability.class);

			return (List)
				fileEntryTypeCapability.
					getFoldersAndFileEntriesAndFileShortcuts(
						_getStagingAwareGroupId(), _getFolderId(),
						_getMimeTypes(), _getFileEntryTypeId(), false,
						WorkflowConstants.STATUS_APPROVED, startAndEnd[0],
						startAndEnd[1], repositoryModelOrderByComparator);
		}

		return DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
			_getStagingAwareGroupId(), _getFolderId(),
			WorkflowConstants.STATUS_APPROVED, _getMimeTypes(), false, false,
			startAndEnd[0], startAndEnd[1], repositoryModelOrderByComparator);
	}

	public int getRepositoryEntriesCount() throws PortalException {
		if (_search) {
			Hits hits = _getHits();

			return hits.getLength();
		}

		Repository repository = _getRepository();

		if (_isFilterByFileEntryType() &&
			repository.isCapabilityProvided(FileEntryTypeCapability.class)) {

			FileEntryTypeCapability fileEntryTypeCapability =
				repository.getCapability(FileEntryTypeCapability.class);

			return fileEntryTypeCapability.
				getFoldersAndFileEntriesAndFileShortcutsCount(
					_getStagingAwareGroupId(), _getFolderId(), _getMimeTypes(),
					_getFileEntryTypeId(), false,
					WorkflowConstants.STATUS_APPROVED);
		}

		return DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(
			_getStagingAwareGroupId(), _getFolderId(),
			WorkflowConstants.STATUS_APPROVED, _getMimeTypes(), false, false);
	}

	public String getTitle() {
		return _dlItemSelectorView.getTitle(_themeDisplay.getLocale());
	}

	public PortletURL getUploadURL(
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		if (!isShowDragAndDropZone()) {
			return null;
		}

		List<AssetVocabulary> assetVocabularies =
			_assetVocabularyService.getGroupVocabularies(
				_getStagingAwareGroupId());

		if (!assetVocabularies.isEmpty()) {
			long classNameId = _classNameLocalService.getClassNameId(
				DLFileEntryConstants.getClassName());
			long defaultFileEntryTypeId =
				DLFileEntryTypeLocalServiceUtil.getDefaultFileEntryTypeId(
					_getFolderId());

			for (AssetVocabulary assetVocabulary : assetVocabularies) {
				if (assetVocabulary.isRequired(
						classNameId, defaultFileEntryTypeId)) {

					return null;
				}
			}
		}

		PortletURL portletURL = liferayPortletResponse.createActionURL(
			PortletKeys.DOCUMENT_LIBRARY);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/document_library/upload_file_entry");
		portletURL.setParameter("folderId", String.valueOf(_getFolderId()));

		return portletURL;
	}

	public boolean isShowDragAndDropZone() throws PortalException {
		if (_showDragAndDropZone != null) {
			return _showDragAndDropZone;
		}

		if (!ModelResourcePermissionHelper.contains(
				_folderModelResourcePermission,
				_themeDisplay.getPermissionChecker(),
				_themeDisplay.getScopeGroupId(), _getFolderId(),
				ActionKeys.ADD_DOCUMENT)) {

			_showDragAndDropZone = false;
		}
		else {
			long defaultFileEntryTypeId =
				DLFileEntryTypeLocalServiceUtil.getDefaultFileEntryTypeId(
					_getFolderId());

			if (DLUtil.hasWorkflowDefinitionLink(
					_themeDisplay.getCompanyId(),
					_themeDisplay.getScopeGroupId(), _getFolderId(),
					defaultFileEntryTypeId)) {

				_showDragAndDropZone = false;
			}
			else {
				_showDragAndDropZone = true;
			}
		}

		return _showDragAndDropZone;
	}

	private long _getFileEntryTypeId() {
		InfoItemItemSelectorCriterion infoItemItemSelectorCriterion =
			(InfoItemItemSelectorCriterion)_itemSelectorCriterion;

		return GetterUtil.getLong(
			infoItemItemSelectorCriterion.getItemSubtype());
	}

	private long _getFolderId() {
		if (_folderId != null) {
			return _folderId;
		}

		_folderId = ParamUtil.getLong(
			_httpServletRequest, "folderId",
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _folderId;
	}

	private Hits _getHits() throws PortalException {
		if (_hits != null) {
			return _hits;
		}

		_hits = DLAppServiceUtil.search(
			_getStagingAwareGroupId(), _getSearchContext());

		return _hits;
	}

	private String[] _getMimeTypes() {
		if (_mimeTypes != null) {
			return _mimeTypes;
		}

		String[] mimeTypes = _dlItemSelectorView.getMimeTypes();

		ItemSelectorCriterion itemSelectorCriterion = _itemSelectorCriterion;

		if (itemSelectorCriterion instanceof InfoItemItemSelectorCriterion) {
			InfoItemItemSelectorCriterion infoItemItemSelectorCriterion =
				(InfoItemItemSelectorCriterion)itemSelectorCriterion;

			String[] infoItemSelectorMimeTypes =
				infoItemItemSelectorCriterion.getMimeTypes();

			if (ArrayUtil.isNotEmpty(infoItemSelectorMimeTypes)) {
				mimeTypes = infoItemItemSelectorCriterion.getMimeTypes();
			}
		}

		_mimeTypes = mimeTypes;

		return _mimeTypes;
	}

	private Repository _getRepository() throws PortalException {
		if (_repository != null) {
			return _repository;
		}

		Repository repository = null;

		if (_getFolderId() != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			repository = RepositoryProviderUtil.getFolderRepository(
				_getFolderId());
		}
		else {
			repository = RepositoryProviderUtil.getRepository(
				_getStagingAwareGroupId());
		}

		_repository = repository;

		return _repository;
	}

	private SearchContext _getSearchContext() throws PortalException {
		if (_searchContext != null) {
			return _searchContext;
		}

		int[] startAndEnd = _getStartAndEnd();

		SearchContext searchContext = SearchContextFactory.getInstance(
			_httpServletRequest);

		Repository repository = _getRepository();

		if (_isFilterByFileEntryType() &&
			repository.isCapabilityProvided(FileEntryTypeCapability.class)) {

			searchContext.setAttribute(
				"fileEntryTypeId", _getFileEntryTypeId());
		}

		searchContext.setAttribute("mimeTypes", _getMimeTypes());
		searchContext.setEnd(startAndEnd[1]);
		searchContext.setFolderIds(new long[] {_getFolderId()});
		searchContext.setGroupIds(new long[] {_getStagingAwareGroupId()});
		searchContext.setStart(startAndEnd[0]);

		_searchContext = searchContext;

		return _searchContext;
	}

	private long _getStagingAwareGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		long groupId = _themeDisplay.getScopeGroupId();

		if (_stagingGroupHelper.isStagingGroup(groupId) &&
			!_stagingGroupHelper.isStagedPortlet(
				groupId, DLPortletKeys.DOCUMENT_LIBRARY)) {

			Group group = _stagingGroupHelper.fetchLiveGroup(groupId);

			if (group != null) {
				groupId = group.getGroupId();
			}
		}

		_groupId = groupId;

		return groupId;
	}

	private int[] _getStartAndEnd() {
		if (_startAndEnd != null) {
			return _startAndEnd;
		}

		int cur = ParamUtil.getInteger(
			_httpServletRequest, SearchContainer.DEFAULT_CUR_PARAM,
			SearchContainer.DEFAULT_CUR);
		int delta = ParamUtil.getInteger(
			_httpServletRequest, SearchContainer.DEFAULT_DELTA_PARAM,
			SearchContainer.DEFAULT_DELTA);

		_startAndEnd = SearchPaginationUtil.calculateStartAndEnd(cur, delta);

		return _startAndEnd;
	}

	private boolean _isFilterByFileEntryType() {
		if (_filterByFileEntryType != null) {
			return _filterByFileEntryType;
		}

		boolean filterByFileEntryType = false;

		ItemSelectorCriterion itemSelectorCriterion = _itemSelectorCriterion;

		if (itemSelectorCriterion instanceof InfoItemItemSelectorCriterion) {
			InfoItemItemSelectorCriterion infoItemItemSelectorCriterion =
				(InfoItemItemSelectorCriterion)itemSelectorCriterion;

			if (Validator.isNotNull(
					infoItemItemSelectorCriterion.getItemSubtype())) {

				filterByFileEntryType = true;
			}
		}

		_filterByFileEntryType = filterByFileEntryType;

		return _filterByFileEntryType;
	}

	private final AssetVocabularyService _assetVocabularyService;
	private final ClassNameLocalService _classNameLocalService;
	private final DLItemSelectorView<T> _dlItemSelectorView;
	private Boolean _filterByFileEntryType;
	private Long _folderId;
	private final ModelResourcePermission<Folder>
		_folderModelResourcePermission;
	private Long _groupId;
	private Hits _hits;
	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;
	private final T _itemSelectorCriterion;
	private final ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;
	private String[] _mimeTypes;
	private final PortalPreferences _portalPreferences;
	private final PortletURL _portletURL;
	private Repository _repository;
	private final boolean _search;
	private SearchContext _searchContext;
	private Boolean _showDragAndDropZone;
	private final StagingGroupHelper _stagingGroupHelper;
	private int[] _startAndEnd;
	private final ThemeDisplay _themeDisplay;

}