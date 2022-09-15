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

import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.HorizontalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.dao.search.ResultRowSplitterEntry;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class RepositoryBrowserTagDisplayContext {

	public RepositoryBrowserTagDisplayContext(
		long folderId, HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse,
		PortletRequest portletRequest, long repositoryId) {

		_folderId = folderId;
		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_portletRequest = portletRequest;
		_repositoryId = repositoryId;
	}

	public HorizontalCard getHorizontalCard(RepositoryEntry repositoryEntry) {
		if (!(repositoryEntry instanceof Folder)) {
			throw new IllegalArgumentException(
				"Invalid repository model " + repositoryEntry);
		}

		Folder folder = (Folder)repositoryEntry;

		return folder::getName;
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
		SearchContainer<Object> searchContainer = new SearchContainer<>(
			_portletRequest, _liferayPortletResponse.createRenderURL(), null,
			null);

		searchContainer.setResultsAndTotal(
			() -> DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
				_repositoryId, _folderId, WorkflowConstants.STATUS_APPROVED,
				null, false, false, searchContainer.getStart(),
				searchContainer.getEnd(), null),
			DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(
				_repositoryId, _folderId, WorkflowConstants.STATUS_APPROVED,
				null, false, false));

		return searchContainer;
	}

	public VerticalCard getVerticalCard(RepositoryEntry repositoryEntry)
		throws PortalException {

		if (repositoryEntry instanceof FileEntry) {
			FileEntry fileEntry = (FileEntry)repositoryEntry;

			FileVersion fileVersion = fileEntry.getFileVersion();

			return new VerticalCard() {

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

	private final long _folderId;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PortletRequest _portletRequest;
	private final long _repositoryId;

}