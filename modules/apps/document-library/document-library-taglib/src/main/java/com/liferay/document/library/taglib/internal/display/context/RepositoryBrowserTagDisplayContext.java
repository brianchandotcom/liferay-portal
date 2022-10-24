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

package com.liferay.document.library.taglib.internal.display.context;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.taglib.internal.frontend.taglib.clay.servlet.FileEntryVerticalCard;
import com.liferay.document.library.taglib.internal.frontend.taglib.clay.servlet.FileShortcutVerticalCard;
import com.liferay.document.library.taglib.internal.frontend.taglib.clay.servlet.FolderHorizontalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.HorizontalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.ManagementToolbarDisplayContext;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.RelatedSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class RepositoryBrowserTagDisplayContext {

	public RepositoryBrowserTagDisplayContext(
		DLAppService dlAppService,
		ModelResourcePermission<FileEntry> fileEntryModelResourcePermission,
		ModelResourcePermission<FileShortcut>
			fileShortcutModelResourcePermission,
		ModelResourcePermission<Folder> folderModelResourcePermission,
		long folderId, HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		PortletRequest portletRequest, long repositoryId) {

		_dlAppService = dlAppService;
		_fileEntryModelResourcePermission = fileEntryModelResourcePermission;
		_fileShortcutModelResourcePermission =
			fileShortcutModelResourcePermission;
		_folderModelResourcePermission = folderModelResourcePermission;
		_folderId = folderId;
		_httpServletRequest = httpServletRequest;
		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_portletRequest = portletRequest;
		_repositoryId = repositoryId;

		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"repositoryBrowserURL", _getRepositoryBrowserURL()
		).build();
	}

	public List<BreadcrumbEntry> getBreadcrumbEntries() throws PortalException {
		LinkedList<BreadcrumbEntry> breadcrumbEntries = new LinkedList<>();

		long folderId = _folderId;

		while (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = _dlAppService.getFolder(folderId);

			if (folder.isMountPoint()) {
				break;
			}

			breadcrumbEntries.addFirst(
				_createBreadcrumbEntry(folderId, folder.getName()));

			folderId = folder.getParentFolderId();
		}

		breadcrumbEntries.addFirst(
			_createBreadcrumbEntry(
				folderId, LanguageUtil.get(_httpServletRequest, "home")));

		return breadcrumbEntries;
	}

	public String getDeleteFileEntryURL(FileEntry fileEntry) {
		return HttpComponentsUtil.addParameter(
			_getRepositoryBrowserURL(), "fileEntryId",
			fileEntry.getFileEntryId());
	}

	public String getDeleteFileShortcutURL(FileShortcut fileShortcut) {
		return HttpComponentsUtil.addParameter(
			_getRepositoryBrowserURL(), "fileShortcutId",
			fileShortcut.getFileShortcutId());
	}

	public String getDeleteFolderURL(Folder folder) {
		return HttpComponentsUtil.addParameter(
			_getRepositoryBrowserURL(), "folderId", folder.getFolderId());
	}

	public String getFolderURL(Folder folder) {
		return _getFolderURL(folder.getFolderId());
	}

	public HorizontalCard getHorizontalCard(RepositoryEntry repositoryEntry)
		throws PortalException {

		if (!(repositoryEntry instanceof Folder)) {
			throw new IllegalArgumentException(
				"Invalid repository model " + repositoryEntry);
		}

		return new FolderHorizontalCard(
			(Folder)repositoryEntry, _folderModelResourcePermission,
			_httpServletRequest, this);
	}

	public ManagementToolbarDisplayContext getManagementToolbarDisplayContext()
		throws PortalException {

		return new RepositoryBrowserManagementToolbarDisplayContext(
			_folderId, _folderModelResourcePermission, _httpServletRequest,
			_liferayPortletRequest, _liferayPortletResponse, _repositoryId,
			getSearchContainer());
	}

	public String getRenameFileEntryURL(FileEntry fileEntry) {
		return HttpComponentsUtil.addParameter(
			_getRepositoryBrowserURL(), "fileEntryId",
			fileEntry.getFileEntryId());
	}

	public String getRenameFolderURL(Folder folder) {
		return HttpComponentsUtil.addParameter(
			_getRepositoryBrowserURL(), "folderId", folder.getFolderId());
	}

	public Map<String, Object> getRepositoryBrowserComponentContext() {
		return HashMapBuilder.<String, Object>put(
			"parentFolderId", String.valueOf(_folderId)
		).put(
			"repositoryBrowserURL", _getRepositoryBrowserURL()
		).put(
			"repositoryId", String.valueOf(_repositoryId)
		).build();
	}

	public ResultRowSplitter getResultRowSplitter() {
		return resultRows -> {
			List<ResultRowSplitterEntry> resultRowSplitterEntries =
				new ArrayList<>();

			List<ResultRow> fileEntryResultRows = new ArrayList<>();
			List<ResultRow> folderResultRows = new ArrayList<>();

			for (ResultRow resultRow : resultRows) {
				if (resultRow.getObject() instanceof Folder) {
					folderResultRows.add(resultRow);
				}
				else {
					fileEntryResultRows.add(resultRow);
				}
			}

			if (!folderResultRows.isEmpty()) {
				resultRowSplitterEntries.add(
					new ResultRowSplitterEntry("folders", folderResultRows));
			}

			if (!fileEntryResultRows.isEmpty()) {
				resultRowSplitterEntries.add(
					new ResultRowSplitterEntry(
						"documents", fileEntryResultRows));
			}

			return resultRowSplitterEntries;
		};
	}

	public SearchContainer<Object> getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		String keywords = ParamUtil.getString(
			_liferayPortletRequest, "keywords");

		if (Validator.isNull(keywords)) {
			_searchContainer = _getDLSearchContainer();
		}
		else {
			_searchContainer = _getSearchSearchContainer();
		}

		return _searchContainer;
	}

	public VerticalCard getVerticalCard(RepositoryEntry repositoryEntry)
		throws PortalException {

		if (repositoryEntry instanceof FileEntry) {
			return new FileEntryVerticalCard(
				(FileEntry)repositoryEntry, _fileEntryModelResourcePermission,
				_httpServletRequest, this);
		}

		if (repositoryEntry instanceof FileShortcut) {
			return new FileShortcutVerticalCard(
				(FileShortcut)repositoryEntry,
				_fileShortcutModelResourcePermission, _httpServletRequest,
				this);
		}

		throw new IllegalArgumentException(
			"Invalid repository model " + repositoryEntry);
	}

	public boolean isVerticalCard(RepositoryEntry repositoryEntry) {
		if (repositoryEntry instanceof Folder) {
			return false;
		}

		return true;
	}

	private BreadcrumbEntry _createBreadcrumbEntry(
		long folderId, String title) {

		BreadcrumbEntry breadcrumbEntry = new BreadcrumbEntry();

		breadcrumbEntry.setTitle(title);
		breadcrumbEntry.setURL(_getFolderURL(folderId));

		return breadcrumbEntry;
	}

	private SearchContainer<Object> _getDLSearchContainer()
		throws PortalException {

		SearchContainer<Object> searchContainer = new SearchContainer<>(
			_portletRequest,
			PortletURLUtil.getCurrent(
				_liferayPortletRequest, _liferayPortletResponse),
			null, "there-are-no-documents-in-this-folder");

		searchContainer.setResultsAndTotal(
			() -> DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
				_repositoryId, _folderId, WorkflowConstants.STATUS_APPROVED,
				null, false, false, searchContainer.getStart(),
				searchContainer.getEnd(), null),
			DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(
				_repositoryId, _folderId, WorkflowConstants.STATUS_APPROVED,
				null, false, false));
		searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_liferayPortletResponse));

		return searchContainer;
	}

	private String _getFolderURL(long folderId) {
		return PortletURLBuilder.create(
			PortletURLUtil.getCurrent(
				_liferayPortletRequest, _liferayPortletResponse)
		).setParameter(
			"folderId", folderId
		).buildString();
	}

	private Hits _getHits(SearchContainer<Object> searchContainer)
		throws PortalException {

		SearchContext searchContext = SearchContextFactory.getInstance(
			_httpServletRequest);

		searchContext.setAttribute("paginationType", "regular");
		searchContext.setAttribute("searchRepositoryId", _repositoryId);
		searchContext.setEnd(searchContainer.getEnd());
		searchContext.setFolderIds(
			new long[] {DLFolderConstants.DEFAULT_PARENT_FOLDER_ID});
		searchContext.setKeywords(
			ParamUtil.getString(_httpServletRequest, "keywords"));
		searchContext.setLocale(_themeDisplay.getSiteDefaultLocale());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setSearchSubfolders(true);

		searchContext.setStart(searchContainer.getStart());

		return DLAppServiceUtil.search(_repositoryId, searchContext);
	}

	private String _getRepositoryBrowserURL() {
		if (_repositoryBrowserURL != null) {
			return _repositoryBrowserURL;
		}

		_repositoryBrowserURL =
			PortalUtil.getPortalURL(_httpServletRequest) + Portal.PATH_MODULE +
				"/repository_browser";

		return _repositoryBrowserURL;
	}

	private List<Object> _getSearchResults(Hits hits) throws PortalException {
		List<Object> searchResults = new ArrayList<>();

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

	private SearchContainer<Object> _getSearchSearchContainer()
		throws PortalException {

		SearchContainer<Object> searchContainer = new SearchContainer<>(
			_liferayPortletRequest, _liferayPortletResponse.createRenderURL(),
			null, null);

		Hits hits = _getHits(searchContainer);

		searchContainer.setResultsAndTotal(
			() -> _getSearchResults(hits), hits.getLength());

		return searchContainer;
	}

	private final DLAppService _dlAppService;
	private final ModelResourcePermission<FileEntry>
		_fileEntryModelResourcePermission;
	private final ModelResourcePermission<FileShortcut>
		_fileShortcutModelResourcePermission;
	private final long _folderId;
	private final ModelResourcePermission<Folder>
		_folderModelResourcePermission;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PortletRequest _portletRequest;
	private String _repositoryBrowserURL;
	private final long _repositoryId;
	private SearchContainer<Object> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}