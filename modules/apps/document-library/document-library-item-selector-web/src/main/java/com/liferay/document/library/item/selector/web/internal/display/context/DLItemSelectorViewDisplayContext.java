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
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnTypeResolver;
import com.liferay.item.selector.ItemSelectorReturnTypeResolverHandler;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.dao.search.SearchPaginationUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
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
		HttpServletRequest httpServletRequest, T itemSelectorCriterion,
		DLItemSelectorView<T> dlItemSelectorView,
		ItemSelectorReturnTypeResolverHandler
			itemSelectorReturnTypeResolverHandler,
		String itemSelectedEventName, boolean search, PortletURL portletURL,
		AssetVocabularyService assetVocabularyService,
		ClassNameLocalService classNameLocalService,
		StagingGroupHelper stagingGroupHelper) {

		_httpServletRequest = httpServletRequest;
		_itemSelectorCriterion = itemSelectorCriterion;
		_dlItemSelectorView = dlItemSelectorView;
		_itemSelectorReturnTypeResolverHandler =
			itemSelectorReturnTypeResolverHandler;
		_itemSelectedEventName = itemSelectedEventName;
		_search = search;
		_portletURL = portletURL;
		_assetVocabularyService = assetVocabularyService;
		_classNameLocalService = classNameLocalService;
		_stagingGroupHelper = stagingGroupHelper;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public String[] getExtensions() {
		return _dlItemSelectorView.getExtensions();
	}

	public long getFolderId() {
		if (_folderId != null) {
			return _folderId;
		}

		_folderId = ParamUtil.getLong(
			_httpServletRequest, "folderId",
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return _folderId;
	}

	public String getItemSelectedEventName() {
		return _itemSelectedEventName;
	}

	public T getItemSelectorCriterion() {
		return _itemSelectorCriterion;
	}

	public ItemSelectorReturnTypeResolver getItemSelectorReturnTypeResolver() {
		return _itemSelectorReturnTypeResolverHandler.
			getItemSelectorReturnTypeResolver(
				_itemSelectorCriterion, _dlItemSelectorView, FileEntry.class);
	}

	public String[] getMimeTypes() {
		return _dlItemSelectorView.getMimeTypes();
	}

	public PortletURL getPortletURL(
			LiferayPortletResponse liferayPortletResponse)
		throws PortletException {

		PortletURL portletURL = PortletURLUtil.clone(
			_portletURL, liferayPortletResponse);

		portletURL.setParameter("folderId", String.valueOf(getFolderId()));
		portletURL.setParameter("selectedTab", String.valueOf(getTitle()));

		return portletURL;
	}

	public List<Object> getRepositoryEntries() throws Exception {
		List<Object> repositoryEntries = null;

		long folderId = getFolderId();
		String[] mimeTypes = getMimeTypes();

		if (isSearch()) {
			Hits hits = _getHits();

			Document[] docs = hits.getDocs();

			repositoryEntries = new ArrayList(docs.length);

			List<SearchResult> searchResults =
				SearchResultUtil.getSearchResults(
					hits, _themeDisplay.getLocale());

			for (SearchResult searchResult : searchResults) {
				String className = searchResult.getClassName();

				if (className.equals(DLFileEntryConstants.getClassName()) ||
					FileEntry.class.isAssignableFrom(
						Class.forName(className))) {

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
		}
		else {
			String orderByCol = ParamUtil.getString(
				_httpServletRequest, "orderByCol", "title");
			String orderByType = ParamUtil.getString(
				_httpServletRequest, "orderByType", "asc");

			int[] startAndEnd = _getStartAndEnd();

			repositoryEntries =
				DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
					getStagingAwareGroupId(), folderId,
					WorkflowConstants.STATUS_APPROVED, mimeTypes, false, false,
					startAndEnd[0], startAndEnd[1],
					DLUtil.getRepositoryModelOrderByComparator(
						orderByCol, orderByType, true));
		}

		return repositoryEntries;
	}

	public int getRepositoryEntriesCount() throws PortalException {
		int repositoryEntriesCount = 0;

		if (isSearch()) {
			Hits hits = _getHits();

			repositoryEntriesCount = hits.getLength();
		}
		else {
			repositoryEntriesCount =
				DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(
					getStagingAwareGroupId(), getFolderId(),
					WorkflowConstants.STATUS_APPROVED, getMimeTypes(), false,
					false);
		}

		return repositoryEntriesCount;
	}

	public long getStagingAwareGroupId() {
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

	public String getTitle() {
		return _dlItemSelectorView.getTitle(_themeDisplay.getLocale());
	}

	public PortletURL getUploadURL(
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		List<AssetVocabulary> assetVocabularies =
			_assetVocabularyService.getGroupVocabularies(
				getStagingAwareGroupId());

		if (!assetVocabularies.isEmpty()) {
			long classNameId = _classNameLocalService.getClassNameId(
				DLFileEntryConstants.getClassName());

			for (AssetVocabulary assetVocabulary : assetVocabularies) {
				if (assetVocabulary.isRequired(
						classNameId,
						DLFileEntryTypeConstants.
							FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT)) {

					return null;
				}
			}
		}

		PortletURL portletURL = liferayPortletResponse.createActionURL(
			PortletKeys.DOCUMENT_LIBRARY);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "/document_library/upload_file_entry");
		portletURL.setParameter("folderId", String.valueOf(getFolderId()));

		return portletURL;
	}

	public boolean isSearch() {
		return _search;
	}

	private Hits _getHits() throws SearchException {
		if (_hits != null) {
			return _hits;
		}

		_hits = DLAppServiceUtil.search(
			getStagingAwareGroupId(), _getSearchContext());

		return _hits;
	}

	private SearchContext _getSearchContext() {
		if (_searchContext != null) {
			return _searchContext;
		}

		long groupId = getStagingAwareGroupId();
		long folderId = getFolderId();
		String[] mimeTypes = getMimeTypes();

		int[] startAndEnd = _getStartAndEnd();

		SearchContext searchContext = SearchContextFactory.getInstance(
			_httpServletRequest);

		searchContext.setAttribute("mimeTypes", mimeTypes);
		searchContext.setEnd(startAndEnd[1]);
		searchContext.setFolderIds(new long[] {folderId});
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setStart(startAndEnd[0]);

		_searchContext = searchContext;

		return _searchContext;
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

	private final AssetVocabularyService _assetVocabularyService;
	private final ClassNameLocalService _classNameLocalService;
	private final DLItemSelectorView<T> _dlItemSelectorView;
	private Long _folderId;
	private Long _groupId;
	private Hits _hits;
	private final HttpServletRequest _httpServletRequest;
	private final String _itemSelectedEventName;
	private final T _itemSelectorCriterion;
	private final ItemSelectorReturnTypeResolverHandler
		_itemSelectorReturnTypeResolverHandler;
	private final PortletURL _portletURL;
	private final boolean _search;
	private SearchContext _searchContext;
	private final StagingGroupHelper _stagingGroupHelper;
	private int[] _startAndEnd;
	private final ThemeDisplay _themeDisplay;

}