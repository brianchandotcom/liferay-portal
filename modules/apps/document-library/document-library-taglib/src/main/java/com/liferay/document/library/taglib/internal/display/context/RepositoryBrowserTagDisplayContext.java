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
import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

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

	public VerticalCard getVerticalCard(RepositoryEntry repositoryEntry) {
		if (repositoryEntry instanceof FileEntry) {
			FileEntry fileEntry = (FileEntry)repositoryEntry;

			return fileEntry::getTitle;
		}
		else if (repositoryEntry instanceof FileShortcut) {
			FileShortcut fileShortcut = (FileShortcut)repositoryEntry;

			return fileShortcut::getToTitle;
		}
		else if (repositoryEntry instanceof Folder) {
			Folder folder = (Folder)repositoryEntry;

			return folder::getName;
		}

		throw new IllegalArgumentException(
			"Invalid repository model " + repositoryEntry);
	}

	private final long _folderId;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final PortletRequest _portletRequest;
	private final long _repositoryId;

}