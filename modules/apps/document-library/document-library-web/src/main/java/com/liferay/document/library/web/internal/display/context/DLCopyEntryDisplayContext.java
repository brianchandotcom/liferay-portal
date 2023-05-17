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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalServiceUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.FolderItemSelectorReturnType;
import com.liferay.item.selector.criteria.folder.criterion.FolderItemSelectorCriterion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Galluzzi
 */
public class DLCopyEntryDisplayContext {

	public DLCopyEntryDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletResponse liferayPortletResponse,
		ThemeDisplay themeDisplay) {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_themeDisplay = themeDisplay;
	}

	public String getActionURL() {
		String actionName = "/document_library/copy_file_entry";

		if (getFileShortcutId() > 0) {
			actionName = "/document_library/copy_file_shortcut";
		}

		return PortletURLBuilder.createActionURL(
			_liferayPortletResponse
		).setActionName(
			actionName
		).setMVCRenderCommandName(
			actionName
		).buildString();
	}

	public long getFileEntryId() {
		if (_fileEntryId < 0) {
			_fileEntryId = ParamUtil.getLong(
				_httpServletRequest, "fileEntryId");
		}

		return _fileEntryId;
	}

	public String getFileName() throws PortalException {
		if (_fileName != null) {
			return _fileName;
		}

		if (getFileShortcutId() > 0) {
			DLFileShortcut dlFileShortcut =
				DLFileShortcutLocalServiceUtil.getDLFileShortcut(
					getFileShortcutId());

			_fileName = dlFileShortcut.getToTitle();
		}
		else {
			DLFileEntry dlFileEntry =
				DLFileEntryLocalServiceUtil.getDLFileEntry(getFileEntryId());

			_fileName = dlFileEntry.getTitle();
		}

		return _fileName;
	}

	public long getFileShortcutId() {
		if (_fileShortcutId < 0) {
			_fileShortcutId = ParamUtil.getLong(
				_httpServletRequest, "fileShortcutId");
		}

		return _fileShortcutId;
	}

	public String getRedirect() {
		if (_redirect != null) {
			return _redirect;
		}

		_redirect = ParamUtil.getString(_httpServletRequest, "redirect");

		return _redirect;
	}

	public String getSelectFolderURL() throws PortalException {
		ItemSelector itemSelector =
			(ItemSelector)_httpServletRequest.getAttribute(
				ItemSelector.class.getName());

		return String.valueOf(
			itemSelector.getItemSelectorURL(
				RequestBackedPortletURLFactoryUtil.create(_httpServletRequest),
				_getGroup(_getSourceRepositoryId()),
				_themeDisplay.getScopeGroupId(), _getItemSelectedEventName(),
				_getFolderItemSelectorCriterion(
					_getSourceFolderId(), _getSourceRepositoryId())));
	}

	public void setViewAttributes() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(getRedirect());

		if (_liferayPortletResponse instanceof RenderResponse) {
			RenderResponse renderResponse =
				(RenderResponse)_liferayPortletResponse;

			renderResponse.setTitle(
				LanguageUtil.get(_httpServletRequest, "copy-to"));
		}
	}

	private FolderItemSelectorCriterion _getFolderItemSelectorCriterion(
		long folderId, long repositoryId) {

		FolderItemSelectorCriterion folderItemSelectorCriterion =
			new FolderItemSelectorCriterion();

		folderItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			new FolderItemSelectorReturnType());
		folderItemSelectorCriterion.setFolderId(folderId);
		folderItemSelectorCriterion.setIgnoreRootFolder(true);
		folderItemSelectorCriterion.setRepositoryId(repositoryId);
		folderItemSelectorCriterion.setSelectedFolderId(folderId);
		folderItemSelectorCriterion.setSelectedRepositoryId(repositoryId);
		folderItemSelectorCriterion.setShowGroupSelector(true);
		folderItemSelectorCriterion.setShowMountFolder(false);

		return folderItemSelectorCriterion;
	}

	private Group _getGroup(long repositoryId) throws PortalException {
		com.liferay.portal.kernel.model.Repository repository =
			RepositoryLocalServiceUtil.fetchRepository(repositoryId);

		if (repository == null) {
			return GroupLocalServiceUtil.getGroup(repositoryId);
		}

		return GroupLocalServiceUtil.getGroup(repository.getGroupId());
	}

	private String _getItemSelectedEventName() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		return portletDisplay.getNamespace() + "folderSelected";
	}

	private long _getSourceFolderId() throws PortalException {
		Repository repository = _getSourceRepository();

		if (getFileShortcutId() > 0) {
			FileShortcut fileShortcut = repository.getFileShortcut(
				getFileShortcutId());

			return fileShortcut.getFolderId();
		}

		FileEntry fileEntry = repository.getFileEntry(getFileEntryId());

		return fileEntry.getFolderId();
	}

	private Repository _getSourceRepository() throws PortalException {
		if (_sourceRepository == null) {
			if (getFileShortcutId() > 0) {
				_sourceRepository =
					RepositoryProviderUtil.getFileShortcutRepository(
						getFileShortcutId());
			}
			else {
				_sourceRepository =
					RepositoryProviderUtil.getFileEntryRepository(
						getFileEntryId());
			}
		}

		return _sourceRepository;
	}

	private long _getSourceRepositoryId() throws PortalException {
		Repository repository = _getSourceRepository();

		return repository.getRepositoryId();
	}

	private long _fileEntryId = -1;
	private String _fileName;
	private long _fileShortcutId = -1;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private Repository _sourceRepository;
	private final ThemeDisplay _themeDisplay;

}