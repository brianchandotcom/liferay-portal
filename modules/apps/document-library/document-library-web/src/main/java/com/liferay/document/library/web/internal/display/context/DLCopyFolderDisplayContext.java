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

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.criteria.FolderItemSelectorReturnType;
import com.liferay.item.selector.criteria.folder.criterion.FolderItemSelectorCriterion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Galluzzi
 */
public class DLCopyFolderDisplayContext {

	public DLCopyFolderDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_httpServletRequest = PortalUtil.getHttpServletRequest(
			liferayPortletRequest);

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_setViewAttributes();
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
				_getGroup(getSourceRepositoryId()),
				_themeDisplay.getScopeGroupId(), _getItemSelectedEventName(),
				_getFolderItemSelectorCriterion(
					getSourceFolderId(), getSourceRepositoryId())));
	}

	public long getSourceFolderId() {
		if (_sourceFolderId < 0) {
			_sourceFolderId = ParamUtil.getLong(
				_httpServletRequest, "sourceFolderId");
		}

		return _sourceFolderId;
	}

	public String getSourceFolderName() {
		if (_sourceFolderName != null) {
			return _sourceFolderName;
		}

		_sourceFolderName = "Home";

		DLFolder folder = DLFolderLocalServiceUtil.fetchFolder(
			getSourceFolderId());

		if (folder != null) {
			_sourceFolderName = folder.getName();
		}

		return _sourceFolderName;
	}

	public long getSourceRepositoryId() {
		if (_sourceRepositoryId < 0) {
			_sourceRepositoryId = ParamUtil.getLong(
				_httpServletRequest, "sourceRepositoryId");
		}

		return _sourceRepositoryId;
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
		long groupId = repositoryId;

		Repository repository = RepositoryLocalServiceUtil.fetchRepository(
			repositoryId);

		if (repository != null) {
			groupId = repository.getGroupId();
		}

		return GroupLocalServiceUtil.getGroup(groupId);
	}

	private String _getItemSelectedEventName() {
		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		return portletDisplay.getNamespace() + "folderSelected";
	}

	private void _setViewAttributes() {
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

	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _redirect;
	private long _sourceFolderId = -1;
	private String _sourceFolderName;
	private long _sourceRepositoryId = -1;
	private final ThemeDisplay _themeDisplay;

}