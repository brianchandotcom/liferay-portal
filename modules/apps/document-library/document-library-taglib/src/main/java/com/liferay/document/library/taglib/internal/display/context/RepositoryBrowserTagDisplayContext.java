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
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.taglib.internal.frontend.taglib.clay.servlet.FolderHorizontalCard;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.HorizontalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.ManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.RelatedSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
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
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class RepositoryBrowserTagDisplayContext {

	public RepositoryBrowserTagDisplayContext(
		long folderId, HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		PortletRequest portletRequest, long repositoryId) {

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
			"deleteURL", _getRepositoryBrowserURL()
		).build();
	}

	public String getDeleteFolderURL(Folder folder) {
		return HttpComponentsUtil.addParameter(
			_getRepositoryBrowserURL(), "folderId", folder.getFolderId());
	}

	public HorizontalCard getHorizontalCard(RepositoryEntry repositoryEntry)
		throws PortalException {

		if (!(repositoryEntry instanceof Folder)) {
			throw new IllegalArgumentException(
				"Invalid repository model " + repositoryEntry);
		}

		return new FolderHorizontalCard(
			(Folder)repositoryEntry, _httpServletRequest, this);
	}

	public ManagementToolbarDisplayContext getManagementToolbarDisplayContext()
		throws PortalException {

		return new SearchContainerManagementToolbarDisplayContext(
			_httpServletRequest, _liferayPortletRequest,
			_liferayPortletResponse, getSearchContainer()) {

			@Override
			public List<DropdownItem> getActionDropdownItems() {
				return DropdownItemListBuilder.add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteEntries");
						dropdownItem.setIcon("trash");
						dropdownItem.setLabel(
							LanguageUtil.get(_httpServletRequest, "delete"));
						dropdownItem.setQuickAction(true);
					}
				).build();
			}

			@Override
			public String getClearResultsURL() {
				return PortletURLBuilder.create(
					getPortletURL()
				).setKeywords(
					StringPool.BLANK
				).buildString();
			}

			@Override
			public String[] getDisplayViews() {
				return new String[] {"icon"};
			}

			@Override
			public String getSearchActionURL() {
				return String.valueOf(getPortletURL());
			}

			@Override
			public String getSearchContainerId() {
				return "repositoryEntries";
			}

			@Override
			protected String getDefaultDisplayStyle() {
				return "icon";
			}

		};
	}

	public String getRenameFolderURL(Folder folder) {
		return HttpComponentsUtil.addParameter(
			_getRepositoryBrowserURL(), "folderId", folder.getFolderId());
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

		SearchContainer<Object> searchContainer = getSearchContainer();

		if (repositoryEntry instanceof FileEntry) {
			FileEntry fileEntry = (FileEntry)repositoryEntry;

			FileVersion fileVersion = fileEntry.getFileVersion();

			return new VerticalCard() {

				@Override
				public List<DropdownItem> getActionDropdownItems() {
					return DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.putData("action", "rename");
							dropdownItem.putData(
								"renameURL", _getRenameFileEntryURL(fileEntry));
							dropdownItem.putData("value", fileEntry.getTitle());
							dropdownItem.setIcon("pencil");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "rename"));
						}
					).add(
						dropdownItem -> {
							dropdownItem.putData("action", "delete");
							dropdownItem.putData(
								"deleteURL", _getDeleteFileEntryURL(fileEntry));
							dropdownItem.setIcon("trash");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "delete"));
						}
					).build();
				}

				@Override
				public String getDefaultEventHandler() {
					return "repositoryBrowserEventHandler";
				}

				@Override
				public String getImageSrc() {
					try {
						return DLURLHelperUtil.getThumbnailSrc(
							fileEntry, fileVersion,
							(ThemeDisplay)_httpServletRequest.getAttribute(
								WebKeys.THEME_DISPLAY));
					}
					catch (Exception exception) {
						return ReflectionUtil.throwException(exception);
					}
				}

				@Override
				public String getInputName() {
					RowChecker rowChecker = searchContainer.getRowChecker();

					if (rowChecker == null) {
						return null;
					}

					return rowChecker.getRowIds();
				}

				@Override
				public String getInputValue() {
					return String.valueOf(fileEntry.getFileEntryId());
				}

				@Override
				public String getTitle() {
					return fileEntry.getTitle();
				}

			};
		}

		if (repositoryEntry instanceof FileShortcut) {
			FileShortcut fileShortcut = (FileShortcut)repositoryEntry;

			FileVersion fileVersion = fileShortcut.getFileVersion();

			return new VerticalCard() {

				@Override
				public List<DropdownItem> getActionDropdownItems() {
					return DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.putData("action", "delete");
							dropdownItem.putData(
								"deleteURL",
								_getDeleteFileShortcutURL(fileShortcut));
							dropdownItem.setIcon("trash");
							dropdownItem.setLabel(
								LanguageUtil.get(
									_httpServletRequest, "delete"));
						}
					).build();
				}

				@Override
				public String getDefaultEventHandler() {
					return "repositoryBrowserEventHandler";
				}

				@Override
				public String getImageSrc() {
					try {
						return DLURLHelperUtil.getThumbnailSrc(
							fileVersion.getFileEntry(), fileVersion,
							(ThemeDisplay)_httpServletRequest.getAttribute(
								WebKeys.THEME_DISPLAY));
					}
					catch (Exception exception) {
						return ReflectionUtil.throwException(exception);
					}
				}

				@Override
				public String getInputName() {
					RowChecker rowChecker = searchContainer.getRowChecker();

					if (rowChecker == null) {
						return null;
					}

					return rowChecker.getRowIds();
				}

				@Override
				public String getInputValue() {
					return String.valueOf(fileShortcut.getFileShortcutId());
				}

				@Override
				public String getTitle() {
					return fileShortcut.getToTitle();
				}

			};
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

	private String _getDeleteFileEntryURL(FileEntry fileEntry) {
		return HttpComponentsUtil.addParameter(
			_getRepositoryBrowserURL(), "fileEntryId",
			fileEntry.getFileEntryId());
	}

	private String _getDeleteFileShortcutURL(FileShortcut fileShortcut) {
		return HttpComponentsUtil.addParameter(
			_getRepositoryBrowserURL(), "fileShortcutId",
			fileShortcut.getFileShortcutId());
	}

	private SearchContainer<Object> _getDLSearchContainer()
		throws PortalException {

		SearchContainer<Object> searchContainer = new SearchContainer<>(
			_portletRequest,
			PortletURLUtil.getCurrent(
				_liferayPortletRequest, _liferayPortletResponse),
			null, null);

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

	private String _getRenameFileEntryURL(FileEntry fileEntry) {
		return HttpComponentsUtil.addParameter(
			_getRepositoryBrowserURL(), "fileEntryId",
			fileEntry.getFileEntryId());
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

	private final long _folderId;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PortletRequest _portletRequest;
	private String _repositoryBrowserURL;
	private final long _repositoryId;
	private SearchContainer<Object> _searchContainer;
	private final ThemeDisplay _themeDisplay;

}